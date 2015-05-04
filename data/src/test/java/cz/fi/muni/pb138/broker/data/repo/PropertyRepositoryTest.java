package cz.fi.muni.pb138.broker.data.repo;

import com.ui4j.api.browser.BrowserFactory;
import com.ui4j.api.dom.Element;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class PropertyRepositoryTest {

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

        propertyInDistrictBrnoStred.setType(Type.TWO_ONE);
        propertyInDistrictBrnoStred.setArea(50);

        propertyInDistrictBrnoStred.setPrice(BigDecimal.valueOf(10_000_000));
        propertyInDistrictBrnoStred.setAddress(address);
    }

    @Test
    public void testSave() throws Exception {
        assertEquals(null, repository.findOne(1l));

        repository.save(propertyInDistrictBrnoStred);

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

    @Test
    public void testJsoup() throws IOException {
        String url = "http://www.sreality.cz/hledani/prodej/byty?region=brno&strana=1";

        List<Element> strings = new ArrayList<>();

        BrowserFactory
                .getWebKit()
                .navigate(url)
                .getDocument()
                .queryAll(".info > div > span")
                .forEach(e -> {
                    strings.add(e);
//                    System.out.println(e.getText());
                });

        for (Element s : strings) {
            System.out.println("----");
            s.queryAll("span").forEach(element -> {
                System.out.println(element.getText().trim());
            });
            System.out.println("----");
        }
    }
}