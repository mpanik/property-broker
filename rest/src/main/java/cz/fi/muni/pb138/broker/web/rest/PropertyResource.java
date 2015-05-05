package cz.fi.muni.pb138.broker.web.rest;

import cz.fi.muni.pb138.broker.business.service.PropertyService;
import cz.fi.muni.pb138.broker.data.model.Property;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author Milan
 */
@RestController
@RequestMapping(value = "/properties")
public class PropertyResource {

    @Inject
    private PropertyService propertyService;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<Property> findAll() {
        return new Resources<>(propertyService.findAll());
    }
    @RequestMapping(value = "/oneprop/@id",method = RequestMethod.GET)
    public Property findOne(Long id) {
        return propertyService.findOne(id);
    }
}
