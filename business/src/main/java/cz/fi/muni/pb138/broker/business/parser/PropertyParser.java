package cz.fi.muni.pb138.broker.business.parser;

import cz.fi.muni.pb138.broker.data.model.Property;

import java.util.Set;

/**
 * @author Milan
 */


/**
 * Parse property data
 */
public interface PropertyParser {
    Set<Property> parse() throws Exception;
}
