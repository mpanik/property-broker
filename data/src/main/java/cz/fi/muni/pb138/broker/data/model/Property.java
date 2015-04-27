package cz.fi.muni.pb138.broker.data.model;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * @author Milan
 */
@Entity
public class Property extends BaseModel {
    @Basic
    private String type;
    private int area;
    private String location;
    private int price;

    public Property() {}

    public Property(String type, int area, String location, int price) {
        this.type = type;
        this.area = area;
        this.location = location;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Property{" +
                "type='" + type + '\'' +
                ", area=" + area +
                ", location='" + location + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property = (Property) o;

        if (area != property.area) return false;
        if (price != property.price) return false;
        if (type != null ? !type.equals(property.type) : property.type != null) return false;
        return !(location != null ? !location.equals(property.location) : property.location != null);

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + area;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }

}
