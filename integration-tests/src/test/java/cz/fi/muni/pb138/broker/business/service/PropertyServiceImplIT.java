package cz.fi.muni.pb138.broker.business.service;

import cz.fi.muni.pb138.broker.data.enums.Type;
import cz.fi.muni.pb138.broker.data.model.Address;
import cz.fi.muni.pb138.broker.data.model.Property;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Collections;


import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Milan
 * @author Martin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-context-business.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PropertyServiceImplIT {


    private Property propertyInDistrictBrnoJih;
    private Property propertyInDistrictBrnoStred;

    @Inject
    private PropertyService propertyService;


    @Before
    public void setUp() {

        Address addressBrnoSted = new Address();
        addressBrnoSted.setCity("Brno");
        addressBrnoSted.setDistrict("Brno-Stred");
        addressBrnoSted.setStreet("Orli");

        propertyInDistrictBrnoStred = new Property();
        propertyInDistrictBrnoStred.setType(Type.TWO_ONE);
        propertyInDistrictBrnoStred.setArea(50);
        propertyInDistrictBrnoStred.setPrice(BigDecimal.valueOf(10_000_000));
        propertyInDistrictBrnoStred.setAddress(addressBrnoSted);

        Address addressBrnoJih = new Address();
        addressBrnoJih.setCity("Brno");
        addressBrnoJih.setDistrict("Brno-Jih");
        addressBrnoJih.setStreet("Lomena");

        propertyInDistrictBrnoJih = new Property();
        propertyInDistrictBrnoJih.setType(Type.THREE_KK);
        propertyInDistrictBrnoJih.setArea(23);
        propertyInDistrictBrnoJih.setPrice(BigDecimal.valueOf(5_600_000));
        propertyInDistrictBrnoJih.setAddress(addressBrnoJih);

    }

    @Test
    public void testSave() throws Exception {

        int sizeOfAllProperties=propertyService.findAll().size();

        propertyService.save(propertyInDistrictBrnoStred);
        propertyService.save(propertyInDistrictBrnoJih);

        assertThat(propertyService.findOne(propertyInDistrictBrnoStred.getId()), is(equalTo(propertyInDistrictBrnoStred)));
        assertThat(propertyService.findOne(propertyInDistrictBrnoJih.getId()), is(equalTo(propertyInDistrictBrnoJih)));
        assertEquals(sizeOfAllProperties + 2, (propertyService.findAll()).size());
    }

    @Test
    public void testDelete() throws Exception {

        int sizeOfListAllProperties=propertyService.findAll().size();

        propertyService.delete(1l);

        assertEquals(null, propertyService.findOne(1l));
        assertEquals(sizeOfListAllProperties-1,propertyService.findAll().size());
    }

    @Test
    public void testUpdate() throws Exception {

        int areaOfPropertyNotUpdated=propertyService.findOne(1l).getArea();
        propertyService.findOne(1l).setArea(47);
        assertEquals(52, areaOfPropertyNotUpdated);

        propertyService.update(propertyService.findOne(1l));

        int areaOfPropertyUpdated=propertyService.findOne(1l).getArea();
        assertEquals(47, areaOfPropertyUpdated);
    }

    @Test
    public void testFindOne() throws Exception {

        propertyService.save(propertyInDistrictBrnoStred);
        assertEquals(propertyInDistrictBrnoStred, propertyService.findOne(propertyInDistrictBrnoStred.getId()));
    }

    @Test
    public void testFindAll() throws Exception {

        assertEquals(Arrays.asList(propertyService.findOne(1l),propertyService.findOne(2l)), propertyService.findAll());
        assertEquals(2, (propertyService.findAll()).size());
    }

    @Test
    public void testFindByAddressDistrictContainingIgnoreCase_MatchOneProperty() throws Exception {

        assertEquals(Arrays.asList(propertyService.findOne(1l)), propertyService.findByAddressDistrictContainingIgnoreCase("Stred"));
        assertEquals(Arrays.asList(propertyService.findOne(1l),propertyService.findOne(2l)), propertyService.findByAddressDistrictContainingIgnoreCase("Brno"));
    }

    @Test
    public void testFindByAddressDistrictContainingIgnoreCase_MatchNoneProperty() throws Exception {

        assertThat(propertyService.findByAddressDistrictContainingIgnoreCase("Sever"), is(equalTo(Collections.emptyList())));
    }

}