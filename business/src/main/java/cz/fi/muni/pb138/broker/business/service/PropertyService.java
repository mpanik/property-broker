package cz.fi.muni.pb138.broker.business.service;

import cz.fi.muni.pb138.broker.data.model.Property;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @Author by Martin
 */

public interface PropertyService {

    /**
     * Insert given property.
     *
     * @param property property to be inserted
     */
    @PreAuthorize("hasAuthority('CREATE')")
    void createProperty(Property property);

    /**
     * Delete property by given id.
     *
     * @param id identification of property to be deleted
     */
    @PreAuthorize("hasAuthority('DELETE')")
    void deleteProperty(Long id);

    /**
     * Update given song.
     *
     * @param property property to be updated
     */
    @PreAuthorize("hasAuthority('EDIT')")
    void updateProperty(Property property);

    /**
     * Get property by given id.
     *
     * @param id identification of property to be found
     * @return found property
     */
    Property findProperty(Long id);

    /**
     * Get all properties.
     *
     * @return list of all properties
     */
    List<Property> getAllProperties();

    /**
     * Get all properties on district/address as given parameter.
     *
     * @param text name of district/address
     * @return return list of properties in given district/address
     */
    List<Property> findByAddressDistrictContainingIgnoreCase(String text);
}
