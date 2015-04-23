package cz.fi.muni.pb138.broker.data.repo;

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
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author Milan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-context-persistence.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PropertyRepositoryTest {

    @Inject
    private PropertyRepository repository;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSave() throws Exception {
        assertEquals(null, repository.findOne(1l));

        Property property = new Property();
        property.setName("test property");

        repository.save(property);

        assertEquals(property, repository.findOne(1l));
    }

    @Test
    public void testFindByNameContaining_OneProperty() throws Exception {
        Property property = new Property();
        property.setName("test property");
        repository.save(property);

        assertEquals(Collections.singletonList(property), repository.findByNameContaining("test"));
    }

    @Test
    public void testFindByNameContaining_MultipleProperties() throws Exception {
        Property property1Test = new Property();
        property1Test.setName("test property");
        repository.save(property1Test);
        Property property2Test = new Property();
        property2Test.setName("testing like hell");
        repository.save(property2Test);
        Property property3Test = new Property();
        property3Test.setName("this test property should be there");
        repository.save(property3Test);
        Property property4WithoutKeyword = new Property();
        property4WithoutKeyword.setName("this really shouldn't appear in result");
        repository.save(property4WithoutKeyword);

        List<Property> expected = asList(property1Test, property2Test, property3Test);
        List<Property> actual = repository.findByNameContaining("test");

        assertEquals(expected.size(), actual.size());
        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}