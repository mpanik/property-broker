package cz.fi.muni.pb138.broker.business.parser;

import cz.fi.muni.pb138.broker.data.model.Property;

import java.util.Set;

/**
 * Parse property data class
 * @author Milan
 */

public interface PropertyParser {

    /**
     * Save all found properties to set
     * @return set of properties
     * @throws Exception if property cannot be added to set
     */
    Set<Property> parse() throws Exception;
}
