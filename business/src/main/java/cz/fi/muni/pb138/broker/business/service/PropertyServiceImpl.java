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


    private PropertyRepository propertyRepo;

    public PropertyRepository getPropertyRepo() {
        return propertyRepo;
    }

    @Inject
    public void setProperty(PropertyRepository propertyRepo) {
        this.propertyRepo = propertyRepo;
    }

    @Override
    public void save(Property property) {
        if(property==null) {
            throw new NullPointerException("property is null");
        }
        getPropertyRepo().save(property);
    }

    @Override
    public void delete(Long id) {
        if(id==null) {
            throw new NullPointerException("id is null");
        }
        if(id<0){
            throw  new IllegalArgumentException("id is < 0");
        }
        getPropertyRepo().delete(id);
    }

    @Override
    public void update(Property property) {
        if(property==null) {
            throw new NullPointerException("property is null");
        }
     //   getPropertyRepo().update(property);                       //neviem co pouzit na update
    }

    @Override
    public Property findOne(Long id) {
        if(id==null) {
            throw new NullPointerException("id is null");
        }
        if(id<0){
            throw  new IllegalArgumentException("id is < 0");
        }
        return getPropertyRepo().findOne(id);
    }

    @Override
    public List<Property> findAll() {
        return  getPropertyRepo().findAll();
    }

    @Override
    public List<Property> findByAddressDistrictContainingIgnoreCase(String text) {
        String textLower=text.toLowerCase();
        List<Property> properties = new ArrayList<>();
        for(Property p : getPropertyRepo().findAll()) {
            if (p.getAddress().getDistrict().toLowerCase().contains(textLower)) {
                properties.add(p);
            }
        }
        return properties;
    }
}
