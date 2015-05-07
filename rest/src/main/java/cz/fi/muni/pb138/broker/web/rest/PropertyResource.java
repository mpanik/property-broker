package cz.fi.muni.pb138.broker.web.rest;

import cz.fi.muni.pb138.broker.business.service.PropertyService;
import cz.fi.muni.pb138.broker.data.model.Property;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Milan
 */
@RestController
@RequestMapping(value = "/properties")
public class PropertyResource {

    @Inject
    private PropertyService propertyService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Property> findAll() {
        return propertyService.findAll();
    }

}
