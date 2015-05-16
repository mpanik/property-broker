package cz.fi.muni.pb138.broker;

import cz.fi.muni.pb138.broker.business.service.PropertyServiceImplIT;
import cz.fi.muni.pb138.broker.data.enums.Type;
import cz.fi.muni.pb138.broker.data.model.Address;
import cz.fi.muni.pb138.broker.data.model.Property;
import cz.fi.muni.pb138.broker.data.repo.PropertyRepositoryIT;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.awt.geom.Point2D;
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
        Address addressBrnoStred = new Address.Builder().city("Brno").district("Brno-Stred").street("Orli").build();
        propertyInDistrictBrnoStred = new Property.Builder().id(1l).type(Type.FIVE_KK).address(addressBrnoStred).area(50).price(new BigDecimal("10000000.00")).coords(new Point2D.Double(49.2165092, 16.497189)).build();

        Address addressBrnoJih = new Address.Builder().city("Brno").district("Brno-Jih").street("Lomena").build();
        propertyInDistrictBrnoJih = new Property.Builder().id(2l).type(Type.THREE_KK).address(addressBrnoJih).area(23).price(new BigDecimal("5600000.00")).coords(new Point2D.Double(49.2862119, 16.497001)).build();
    }
}
