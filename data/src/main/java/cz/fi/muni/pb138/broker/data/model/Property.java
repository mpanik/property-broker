package cz.fi.muni.pb138.broker.data.model;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * @author Milan
 */
@Entity
public class Property extends BaseModel {
    @Basic
    private String name;

    //TODO: fields

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //TODO: equals, hash

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Property{");
        sb.append("id='").append(id).append('\'');
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
