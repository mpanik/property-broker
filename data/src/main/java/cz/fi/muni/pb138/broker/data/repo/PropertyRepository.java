package cz.fi.muni.pb138.broker.data.repo;

import cz.fi.muni.pb138.broker.data.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Milan
 */
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByNameContaining(String text);
}
