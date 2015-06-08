package cz.fi.muni.pb138.broker.data.repo;

import cz.fi.muni.pb138.broker.data.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Milan
 */

public interface PropertyRepository extends JpaRepository<Property, Long> {

    /**
     * Find properties which are located on the address
     * @param text property address
     * @return properties found on address
     */
    List<Property> findByAddressDistrictContainingIgnoreCase(String text);
}
