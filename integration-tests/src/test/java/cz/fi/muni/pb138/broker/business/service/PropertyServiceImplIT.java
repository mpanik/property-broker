package cz.fi.muni.pb138.broker.business.service;

import cz.fi.muni.pb138.broker.BasicTestSuite;
import cz.fi.muni.pb138.broker.data.model.Property;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
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
public class PropertyServiceImplIT extends BasicTestSuite {

    @Inject
    private PropertyService propertyService;

    @Test
    public void testSave() throws Exception {
        assertThat(propertyService.findAll(), is(equalTo(asList(propertyInDistrictBrnoStred, propertyInDistrictBrnoJih))));
    }

    @Test
    public void testDelete() throws Exception {
        assertThat(propertyService.findAll().size(), is(equalTo(2)));

        propertyService.delete(propertyInDistrictBrnoStred.getId());

        assertThat(propertyService.findOne(propertyInDistrictBrnoStred.getId()), is(equalTo(null)));

        assertThat(propertyService.findAll().size(), is(equalTo(1)));
    }

    @Test
    public void testUpdate() throws Exception {
        Property propertyToBeUpdated = propertyService.findOne(propertyInDistrictBrnoStred.getId());

        assertThat(propertyToBeUpdated.getArea(), is(not(equalTo(150))));

        propertyToBeUpdated.setArea(150);
        propertyService.update(propertyToBeUpdated);

        assertThat(propertyService.findOne(propertyInDistrictBrnoStred.getId()).getArea(), is(equalTo(150)));
    }

    @Test
    public void testFindOne() throws Exception {
        assertThat(propertyService.findOne(propertyInDistrictBrnoStred.getId()), is(equalTo(propertyInDistrictBrnoStred)));
    }

    @Test
    public void testFindAll() throws Exception {
        assertThat(propertyService.findAll().size(), is(equalTo(2)));
        assertThat(propertyService.findAll(), is(equalTo(asList(propertyInDistrictBrnoStred, propertyInDistrictBrnoJih))));
    }

    @Test
    public void testFindByAddressDistrictContainingIgnoreCase_MatchOneProperty() throws Exception {
        assertThat(propertyService.findAll().size(), is(equalTo(2)));
        assertThat(propertyService.findByAddressDistrictContainingIgnoreCase("StrEd"), is(equalTo(Collections.singletonList(propertyService.findOne(propertyInDistrictBrnoStred.getId())))));
    }

    @Test
    public void testFindByAddressDistrictContainingIgnoreCase_MatchNoneProperty() throws Exception {
        assertThat(propertyService.findAll().size(), is(equalTo(2)));
        assertThat(propertyService.findByAddressDistrictContainingIgnoreCase("Sever"), is(equalTo(Collections.emptyList())));
    }

}