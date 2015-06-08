package cz.fi.muni.pb138.broker.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * This class represent model of property address
 * @author Milan
 * @author Martin
 */
@Embeddable
public class Address {
    @Column(length = 50)
    private String street;

    @Column(nullable = false, length = 35)
    private String district;

    @Column(nullable = false, length = 15)
    private String city;

    protected  Address() {
    }

    private Address(Builder builder) {
        this.street = builder.street;
        this.district = builder.district;
        this.city = builder.city;
    }

    public static class Builder {
        private String street;
        private String district;
        private String city;

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder district(String district) {
            this.district = district;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }

    /**
     * @return street of property
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street street of property
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return district of property
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district district of property
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * @return city where is property located
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city city where is property located
     */
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (district != null ? !district.equals(address.district) : address.district != null) return false;
        return !(city != null ? !city.equals(address.city) : address.city != null);

    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
