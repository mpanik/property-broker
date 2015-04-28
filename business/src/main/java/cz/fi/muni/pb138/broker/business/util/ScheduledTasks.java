package cz.fi.muni.pb138.broker.business.util;

import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Named;

/**
 * @author Milan
 */
@Named
public class ScheduledTasks {

    private final String everyDayAtMidnight = "0 0 0 * * *";

    @Scheduled(cron = everyDayAtMidnight)
    public void parsePropertiesFromWebsites() {
        //TODO
    }
}
