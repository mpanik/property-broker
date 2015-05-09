package cz.fi.muni.pb138.broker.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        return street.equals(address.street) && district.equals(address.district) && city.equals(address.city);
    }

    @Override
    public int hashCode() {
        int result = street.hashCode();
        result = 31 * result + district.hashCode();
        result = 31 * result + city.hashCode();
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
