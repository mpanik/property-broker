package cz.fi.muni.pb138.broker.data.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.deser.Deserializers;
import cz.fi.muni.pb138.broker.data.enums.Type;
import cz.fi.muni.pb138.broker.data.enums.TypeConverter;
import cz.fi.muni.pb138.broker.data.model.Address;
import cz.fi.muni.pb138.broker.data.model.BaseModel;
import cz.fi.muni.pb138.broker.data.model.Property;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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

    private Property(Builder builder){
        this.area = builder.area;
        this.address = builder.address;
        this.price = builder.price;
        this.type = builder.type;
    }

    public Long getId() {
        return  id;
    }

    public Integer getArea() { return area; }

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


    public static class Builder{

        private Integer area;
        private BigDecimal price;
        private Address address;
        private Type type;
        protected Long id;

        public Builder area(Integer area){
            this.area = area;
            return this;
        }

        public Builder price(BigDecimal price){
            this.price = price;
            return this;
        }

        public Builder address(Address address){
            this.address = address;
            return this;
        }

        public Builder type(Type type){
            this.type = type;
            return this;
        }

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Property build(){
            return new Property(this);
        }
    }

}
