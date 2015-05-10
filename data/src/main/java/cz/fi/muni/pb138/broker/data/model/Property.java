package cz.fi.muni.pb138.broker.data.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import cz.fi.muni.pb138.broker.data.enums.Type;
import cz.fi.muni.pb138.broker.data.enums.TypeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Milan
 * @author Viki
 * @author Martin
 */
@Entity
@JsonRootName(value = "property")
public class Property extends BaseModel implements Serializable {

    private static final long serialVersionUID = 56481614648421145L;

    @Column(nullable = false)
    private Integer area;

    @Column(nullable = false)
    private BigDecimal price;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column
    @Convert(converter = TypeConverter.class)
    private Type type;

    @Embedded
    @Column
    private Point2D streetCoords;

    protected Property() {

    }

    private Property(Builder builder) {
        this.id = builder.id;
        this.area = builder.area;
        this.address = builder.address;
        this.price = builder.price;
        this.type = builder.type;
    }

    public static class Builder {
        private Long id;
        private Integer area;
        private BigDecimal price;
        private Address address;
        private Type type;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder area(Integer area) {
            this.area = area;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Property build() {
            return new Property(this);
        }
    }

    public Long getId() {
        return id;
    }

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Point2D getStreetCoords() {
        return streetCoords;
    }

    public void setStreetCoords(Point2D streetCoords) {
        this.streetCoords = streetCoords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property = (Property) o;

        if (area != null ? !area.equals(property.area) : property.area != null) return false;
        if (price != null ? !price.equals(property.price) : property.price != null) return false;
        if (address != null ? !address.equals(property.address) : property.address != null) return false;
        if (type != property.type) return false;
        return !(streetCoords != null ? !streetCoords.equals(property.streetCoords) : property.streetCoords != null);

    }

    @Override
    public int hashCode() {
        int result = area != null ? area.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (streetCoords != null ? streetCoords.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Property{" +
                "area=" + area +
                ", price=" + price +
                ", address=" + address +
                ", type=" + type +
                ", streetCoords=" + streetCoords +
                '}';
    }
}
