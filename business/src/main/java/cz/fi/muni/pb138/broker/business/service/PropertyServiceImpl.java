package cz.fi.muni.pb138.broker.business.service;

import cz.fi.muni.pb138.broker.data.model.Property;
import cz.fi.muni.pb138.broker.data.repo.PropertyRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin
 */

@Named
@Transactional
public class PropertyServiceImpl implements PropertyService {

    @Inject
    private PropertyRepository propertyRepo;

    public PropertyRepository getPropertyRepo() {
        return propertyRepo;
    }

    public void setProperty(PropertyRepository propertyRepo) {
        this.propertyRepo = propertyRepo;
    }

    @Override
    public void save(Property property) {
        getPropertyRepo().save(property);
    }

    @Override
    public void delete(Long id) {
        getPropertyRepo().delete(id);
    }

    @Override
    public void update(Property property) {
        Property updtProperty= getPropertyRepo().findOne(property.getId());
        updtProperty=property;
        getPropertyRepo().flush();
    }

    @Override
    public Property findOne(Long id) {
        return getPropertyRepo().findOne(id);
    }

    @Override
    public List<Property> findAll() {
        return  getPropertyRepo().findAll();
    }

    @Override
    public List<Property> findByAddressDistrictContainingIgnoreCase(String text) {
        return findByAddressDistrictContainingIgnoreCase(text);
    }
}
