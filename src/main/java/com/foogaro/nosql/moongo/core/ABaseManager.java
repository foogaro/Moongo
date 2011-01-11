package com.foogaro.nosql.moongo.core;

import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is used as base class for thoseones which need direct access to the MongoDB. 
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class ABaseManager {

    protected static final Logger log = LoggerFactory.getLogger(ABaseManager.class);

    @Autowired
    private DBManager dbManager;

    public DBManager getDbManager() {
        return dbManager;
    }

    public DBCollection getDBCollection(Class type) {
        return dbManager.getDBCollection(type.getName());
    }

    public CommandResult getLastError() {
        return dbManager.getDB().getLastError();
    }

    public CommandResult getStats() {
        return dbManager.getDB().getStats();
    }
}
