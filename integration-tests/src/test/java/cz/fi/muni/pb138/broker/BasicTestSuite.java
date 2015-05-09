package cz.fi.muni.pb138.broker;

import cz.fi.muni.pb138.broker.business.service.PropertyServiceImplIT;
import cz.fi.muni.pb138.broker.data.enums.Type;
import cz.fi.muni.pb138.broker.data.model.Address;
import cz.fi.muni.pb138.broker.data.model.Property;
import cz.fi.muni.pb138.broker.data.repo.PropertyRepositoryIT;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.math.BigDecimal;

/**
 * @author Milan
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        PropertyRepositoryIT.class,
        PropertyServiceImplIT.class
})
public class BasicTestSuite {

    protected Property propertyInDistrictBrnoJih;
    protected Property propertyInDistrictBrnoStred;

    @Before
    public void setUp() {
        Address addressBrnoStred = buildAddress("Brno", "Brno-Stred", "Orli");
        propertyInDistrictBrnoStred = buildProperty(1l, Type.FIVE_KK, addressBrnoStred, 50, new BigDecimal("10000000.00"));

        Address addressBrnoJih = buildAddress("Brno", "Brno-Jih", "Lomena");
        propertyInDistrictBrnoJih = buildProperty(2l, Type.THREE_KK, addressBrnoJih, 23, new BigDecimal("5600000.00"));
    }

    private Property buildProperty(Long id, Type type, Address addressBrnoJih, Integer area, BigDecimal price) {
        Property.Builder builder = new Property.Builder();
        builder.type(type);
        builder.area(area);
        builder.price(price);
        builder.address(addressBrnoJih);
        builder.id(id);
        return builder.build();
    }

    private Address buildAddress(String city, String district, String street) {
        Address.Builder builder = new Address.Builder();
        builder.city(city);
        builder.district(district);
        builder.street(street);
        return builder.build();
    }
}
