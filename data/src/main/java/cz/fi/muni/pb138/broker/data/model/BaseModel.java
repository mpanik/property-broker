package cz.fi.muni.pb138.broker.data.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This class represent property Id
 * @author Milan
 */
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    /**
     * @return id of property
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id of property
     */
    public void setId(Long id) {
        this.id = id;
    }
}
