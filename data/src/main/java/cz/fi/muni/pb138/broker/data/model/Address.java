package cz.fi.muni.pb138.broker.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Milan
 */
@Embeddable
public class Address {
    @Column
    private String street;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

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
