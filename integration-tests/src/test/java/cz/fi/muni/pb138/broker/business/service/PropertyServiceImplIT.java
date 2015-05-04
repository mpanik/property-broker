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
@ContextConfiguration({"classpath:test-context.xml"})
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
        assertEquals(null, propertyService.findOne(2l));
        propertyService.save(propertyInDistrictBrnoStred);
        assertThat(propertyService.findOne(propertyInDistrictBrnoStred.getId()), is(equalTo(propertyInDistrictBrnoStred)));
    }

    @Test
    public void testDelete() throws Exception {
        propertyService.save(propertyInDistrictBrnoStred);
        propertyService.save(propertyInDistrictBrnoJih);
        propertyService.delete(propertyInDistrictBrnoStred.getId());

        assertEquals(null, propertyService.findOne(propertyInDistrictBrnoStred.getId()));
        assertEquals(propertyInDistrictBrnoJih, propertyService.findOne(propertyInDistrictBrnoJih.getId()));
    }

    @Test
    public void testUpdate() throws Exception {

        propertyService.save(propertyInDistrictBrnoStred);

        int areaOfPropertyNotUpdated=propertyService.findOne(propertyInDistrictBrnoStred.getId()).getArea();
        propertyService.findOne(propertyInDistrictBrnoStred.getId()).setArea(48);
        assertEquals(50, areaOfPropertyNotUpdated);

        propertyService.update(propertyInDistrictBrnoStred);

        int areaOfPropertyUpdated=propertyService.findOne(propertyInDistrictBrnoStred.getId()).getArea();
        assertEquals(48, areaOfPropertyUpdated);

    }

    @Test
    public void testFindOne() throws Exception {
        propertyService.save(propertyInDistrictBrnoStred);

        assertEquals(null, propertyService.findOne(2l));
        assertEquals(propertyInDistrictBrnoStred, propertyService.findOne(propertyInDistrictBrnoStred.getId()));

        propertyService.save(propertyInDistrictBrnoJih);

        assertEquals(propertyInDistrictBrnoJih, propertyService.findOne(propertyInDistrictBrnoJih.getId()));
        assertEquals(propertyInDistrictBrnoStred, propertyService.findOne(propertyInDistrictBrnoStred.getId()));
    }

    @Test
    public void testFindAll() throws Exception {

        assertThat(propertyService.findAll(), is(equalTo(Collections.emptyList())));

        propertyService.save(propertyInDistrictBrnoStred);
        propertyService.save(propertyInDistrictBrnoJih);

        assertEquals(Arrays.asList(propertyInDistrictBrnoStred,propertyInDistrictBrnoJih), propertyService.findAll());
        assertEquals(2, (propertyService.findAll()).size());
    }

    @Test
    public void testFindByAddressDistrictContainingIgnoreCase_MatchOneProperty() throws Exception {

        assertThat(propertyService.findByAddressDistrictContainingIgnoreCase("Stred"), is(equalTo(Collections.emptyList())));

        propertyService.save(propertyInDistrictBrnoStred);
        propertyService.save(propertyInDistrictBrnoJih);

        assertEquals(Arrays.asList(propertyInDistrictBrnoStred), propertyService.findByAddressDistrictContainingIgnoreCase("Stred"));
        assertEquals( Arrays.asList(propertyInDistrictBrnoStred,propertyInDistrictBrnoJih), propertyService.findByAddressDistrictContainingIgnoreCase("Brno"));
    }

    @Test
    public void testFindByAddressDistrictContainingIgnoreCase_MatchNoneProperty() throws Exception {
        propertyService.save(propertyInDistrictBrnoStred);
        propertyService.save(propertyInDistrictBrnoJih);

        assertThat(propertyService.findByAddressDistrictContainingIgnoreCase("Sever"), is(equalTo(Collections.emptyList())));
    }

}