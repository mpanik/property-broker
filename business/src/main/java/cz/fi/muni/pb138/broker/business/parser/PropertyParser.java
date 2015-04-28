package cz.fi.muni.pb138.broker.business.parser;

import cz.fi.muni.pb138.broker.data.model.Property;

import java.util.List;

/**
 * @author Milan
 */
public interface PropertyParser {
    List<Property> parse() throws Exception;
}
