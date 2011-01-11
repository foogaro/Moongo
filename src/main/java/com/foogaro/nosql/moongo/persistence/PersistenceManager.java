package com.foogaro.nosql.moongo.persistence;

import com.foogaro.nosql.moongo.core.ABaseManager;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Abstract class used for CRUD operations.
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository
public class PersistenceManager extends ABaseManager implements IPersistenceManager {

    private static final Logger log = LoggerFactory.getLogger(PersistenceManager.class);

    public DBObject create(DBObject dbObject, Class classType) {
        log.debug("Creating document: " + dbObject);
        getDBCollection(classType).insert(dbObject);
        return dbObject;
    }

    public DBObject read(DBObject dbObject, Class classType) {
        log.debug("Reading document: " + dbObject);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", dbObject.get("_id"));
        DBObject result = getDBCollection(classType).findOne(query);
        return result;
    }

    public DBObject update(DBObject dbObject, Class classType) {
        log.debug("Updating document: " + dbObject);
        getDBCollection(classType).save(dbObject);
        return dbObject;
    }

    public void delete(DBObject dbObject, Class classType) {
        log.debug("Deleting document: " + dbObject);
        getDBCollection(classType).remove(dbObject);
    }

}
