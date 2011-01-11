package com.foogaro.nosql.moongo.persistence;

import com.mongodb.DBObject;


/**
 * Interface for CRUD operations.
 * @see PersistenceManager
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public interface IPersistenceManager {

    public DBObject create(DBObject dbObject, Class classType);
    public DBObject read(DBObject dbObject, Class classType);
    public DBObject update(DBObject dbObject, Class classType);
    public void delete(DBObject dbObject, Class classType);
    
}
