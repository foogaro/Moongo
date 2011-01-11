package com.foogaro.nosql.moongo;

import com.foogaro.nosql.moongo.annotation.DBReferenced;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public class User {

    private ObjectId _id;
    private String name;
    private Date birthdate;
    @DBReferenced
    private Nation nation;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", nation=" + nation +
                '}';
    }
}
