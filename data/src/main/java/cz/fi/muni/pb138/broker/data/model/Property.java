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
 * This class represent property model
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
    @Column(nullable = false)
    private Point2D.Double coords;

    protected Property() {

    }

    private Property(Builder builder) {
        this.id = builder.id;
        this.area = builder.area;
        this.address = builder.address;
        this.price = builder.price;
        this.type = builder.type;
        this.coords = builder.coords;
    }

    /**
     * Property class builder
     */
    public static class Builder {
        private Long id;
        private Integer area;
        private BigDecimal price;
        private Address address;
        private Type type;
        private Point2D.Double coords;

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

        public Builder coords(Point2D.Double coords) {
            this.coords = coords;
            return this;
        }

        public Property build() {
            return new Property(this);
        }
    }

    /**
     * @return id of property
     */
    public Long getId() {
        return id;
    }

    /**
     * @return area of property
     */
    public Integer getArea() {
        return area;
    }

    /**
     * @param area area of property
     */
    public void setArea(Integer area) {
        this.area = area;
    }

    /**
     * @return price of property
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price price of property
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return address of property
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address address of property
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return type of proeprty
     */
    public Type getType() {
        return type;
    }

    /**
     * @param type type of proeprty
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return map coords of property
     */
    public Point2D.Double getCoords() {
        return coords;
    }

    /**
     * @param coords map coords of property
     */
    public void setCoords(Point2D.Double coords) {
        this.coords = coords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;

        Property property = (Property) o;

        if (area != null ? !area.equals(property.area) : property.area != null) return false;
        if (price != null ? !price.equals(property.price) : property.price != null) return false;
        if (address != null ? !address.equals(property.address) : property.address != null) return false;
        if (type != property.type) return false;
        return !(coords != null ? !coords.equals(property.coords) : property.coords != null);

    }

    @Override
    public int hashCode() {
        int result = area != null ? area.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (coords != null ? coords.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Property{" +
                "area=" + area +
                ", price=" + price +
                ", address=" + address +
                ", type=" + type +
                ", coords=" + coords +
                '}';
    }
}
