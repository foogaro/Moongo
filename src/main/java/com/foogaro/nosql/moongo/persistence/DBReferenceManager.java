package com.foogaro.nosql.moongo.persistence;

import com.foogaro.nosql.moongo.core.ABaseManager;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository
public class DBReferenceManager extends ABaseManager {

    private static final Logger log = LoggerFactory.getLogger(DBReferenceManager.class);

    public DBReferenceManager() {
        log.debug("DBReferenceManager created");
    }

    public DBObject read(DBObject dbObject, Class type) {
        log.debug("Reading referenced document " + type.getName() + ": " + dbObject);
        DBRef dbRef = new DBRef(getDbManager().getDB(), type.getName(), dbObject.get("_id"));
        DBObject result = dbRef.fetch();
        return result;
    }

    public DBObject insert(DBObject dbObject, Class type) {
        log.debug("Creating referenced document " + type.getName() + ": " + dbObject);
        getDBCollection(type).insert(dbObject);
        return dbObject;
    }

}
