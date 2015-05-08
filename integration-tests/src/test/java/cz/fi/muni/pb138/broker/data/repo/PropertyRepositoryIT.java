package cz.fi.muni.pb138.broker.data.repo;

import cz.fi.muni.pb138.broker.BasicTestSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Milan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-context-persistence.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PropertyRepositoryIT extends BasicTestSuite {

    @Inject
    private PropertyRepository repository;

    @Test
    public void testSave() throws Exception {
        assertThat(repository.findOne(1l), is(equalTo(null)));

        repository.save(propertyInDistrictBrnoStred);

        assertThat(repository.findOne(1l), is(equalTo(propertyInDistrictBrnoStred)));
    }

    @Test
    public void testFindByNameContaining_MatchOneProperty() throws Exception {
        repository.save(propertyInDistrictBrnoStred);

        assertThat(repository.findByAddressDistrictContainingIgnoreCase("stred"), is(equalTo(Collections.singletonList(propertyInDistrictBrnoStred))));
    }

    @Test
    public void testFindByNameContaining_MatchNoneProperty() throws Exception {
        repository.save(propertyInDistrictBrnoStred);

        assertThat(repository.findByAddressDistrictContainingIgnoreCase("juh"), is(equalTo(Collections.emptyList())));
    }
}