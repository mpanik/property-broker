package cz.fi.muni.pb138.broker.data.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * @author Milan
 * @author Viki
 */
@Entity
public class Property extends BaseModel {
    @Column(nullable = false)
    private Integer area;

    @Column(nullable = false)
    private BigDecimal price;

    @Embedded
    @Column(nullable = false)
    private Address address;

    //TODO: enum for type
    @Column
    private String type;

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;

        Property property = (Property) o;

        if (!area.equals(property.area)) return false;
        if (!price.equals(property.price)) return false;
        if (!address.equals(property.address)) return false;
        return !(type != null ? !type.equals(property.type) : property.type != null);

    }

    @Override
    public int hashCode() {
        int result = area.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Property{" +
                "area=" + area +
                ", price=" + price +
                ", address=" + address +
                ", type='" + type + '\'' +
                '}';
    }
}
