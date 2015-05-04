package cz.fi.muni.pb138.broker.business.service;

import cz.fi.muni.pb138.broker.data.model.Property;
import cz.fi.muni.pb138.broker.data.repo.PropertyRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Martin
 */
@Named
@Transactional
public class PropertyServiceImpl implements PropertyService {

    @Inject
    private PropertyRepository propertyRepo;

    @Override
    public void save(Property property) {
        propertyRepo.save(property);
    }

    @Override
    public void delete(Long id) {
        propertyRepo.delete(id);
    }

    @Override
    public void update(Property property) {
        Property propertyToBeUpdated = findOne(property.getId());
        propertyToBeUpdated = property;
        propertyRepo.flush();
    }

    @Override
    public Property findOne(Long id) {
        return propertyRepo.findOne(id);
    }

    @Override
    public List<Property> findAll() {
        return  propertyRepo.findAll();
    }

    @Override
    public List<Property> findByAddressDistrictContainingIgnoreCase(String text) {
        return propertyRepo.findByAddressDistrictContainingIgnoreCase(text);
    }
}
