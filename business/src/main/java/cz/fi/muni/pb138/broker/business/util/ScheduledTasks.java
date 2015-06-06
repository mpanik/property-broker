package cz.fi.muni.pb138.broker.business.util;

import cz.fi.muni.pb138.broker.business.parser.BrnenskeRealityParser;
import cz.fi.muni.pb138.broker.business.parser.PropertyParser;
import cz.fi.muni.pb138.broker.business.parser.SRealityParser;
import cz.fi.muni.pb138.broker.business.service.PropertyService;
import cz.fi.muni.pb138.broker.data.model.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * Class contains methods to manage properties data in database, delete those data and update fresh properties data from websites
 * @author Milan
 */
@Named
public class ScheduledTasks {

    public static final String EVERY_DAY_AT_MIDNIGHT = "0 0 0 * * *";
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private List<PropertyParser> parsers;
    private CoordsEnquirer enquirer;
    @Inject
    private PropertyService propertyService;


    /**
     * Initializes a newly created ScheduledTasks object.
     */
    public ScheduledTasks() {
        this.parsers = new ArrayList<>(asList(new SRealityParser(), new BrnenskeRealityParser()));
        this.enquirer = new CoordsEnquirer();
    }

    /**
     * Initializes a newly created ScheduledTasks with data from parameters
     * @param parsers list of property parsers
     * @param enquirer coords enquirer
     */
    public ScheduledTasks(List<PropertyParser> parsers, CoordsEnquirer enquirer) {
        this.parsers = parsers;
        this.enquirer = enquirer;
    }

    /**
     * Update properties with fresh data from websites
     */
    @Scheduled(cron = EVERY_DAY_AT_MIDNIGHT)
    public void updateDbWithFreshPropertiesFromWebsites() {
        dropCurrentEntitiesFromDb();
        for (PropertyParser parser : parsers) {
            saveProperties(parseAndAddCoords(parser));
        }
        log.info("Number of properties added after action: " + propertyService.findAll().size());
    }

    /**
     * Remove all properties data from database
     */
    private void dropCurrentEntitiesFromDb() {
        log.info("Dropping all entities from DB.");
        propertyService.deleteAll();
    }

    /**
     * Save properies into database
     * @param properties list of properties
     */
    private void saveProperties(Set<Property> properties) {
        log.info("Saving result of operation to DB.");
        propertyService.save(properties);
    }

    /**
     * Parse properties and add properties coords
     * @param parser property parser
     * @return set of properies with coords
     */
    private Set<Property> parseAndAddCoords(PropertyParser parser) {
        return enquirer.addCoords(parse(parser));
    }

    /**
     * Parse properties from Property Parser
     * @param parser  Property Parser
     * @return set of properties
     */
    private Set<Property> parse(PropertyParser parser) {
        try {
            log.info("[" + parser.toString() + "] started.");
            return parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("[" + parser.toString() + "] failed to parse due: " + e.getMessage());
        }
        return new HashSet<>();
    }
}
