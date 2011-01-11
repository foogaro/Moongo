package com.foogaro.nosql.moongo;

import com.foogaro.nosql.moongo.annotation.DBReferenced;
import org.bson.types.ObjectId;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public class Nation  {

    private ObjectId _id;
    private String code;
    private String name;
    @DBReferenced
    private City city;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Nation{" +
                "_id=" + _id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}
