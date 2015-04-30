package cz.fi.muni.pb138.broker.data.repo;

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
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Milan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-context-persistence.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PropertyRepositoryIT {

    @Inject
    private PropertyRepository repository;
    private Property propertyInDistrictBrnoStred;

    @Before
    public void setUp() throws Exception {
        Address address = new Address();
        address.setCity("Brno");
        address.setDistrict("Brno-Stred");
        address.setStreet("Orli");

        propertyInDistrictBrnoStred = new Property();
        propertyInDistrictBrnoStred.setType(Type.fromString("2+1"));
        propertyInDistrictBrnoStred.setArea(50);
        propertyInDistrictBrnoStred.setPrice(BigDecimal.valueOf(10_000_000));
        propertyInDistrictBrnoStred.setAddress(address);
    }

    @Test
    public void testSave() throws Exception {
        assertEquals(null, repository.findOne(1l));

        repository.save(propertyInDistrictBrnoStred);
        System.out.println(propertyInDistrictBrnoStred);

        assertThat(repository.findOne(1l), is(equalTo(propertyInDistrictBrnoStred)));
    }

    @Test
    public void testFindByNameContaining_MatchOneProperty() throws Exception {
        repository.save(propertyInDistrictBrnoStred);

        assertEquals(Collections.singletonList(propertyInDistrictBrnoStred), repository.findByAddressDistrictContainingIgnoreCase("stred"));
    }

    @Test
    public void testFindByNameContaining_MatchNoneProperty() throws Exception {
        repository.save(propertyInDistrictBrnoStred);

        assertThat(repository.findByAddressDistrictContainingIgnoreCase("juh"), is(equalTo(Collections.emptyList())));
    }
}