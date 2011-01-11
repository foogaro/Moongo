package com.foogaro.nosql.moongo.core;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * This class is used as singleton to wrap the access to the MongoDB. 
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public class DBManager {

    private static final Logger log = LoggerFactory.getLogger(DBManager.class);

    @Autowired
    private Mongo mongo;
    private DB db;

    private String dbName;
    private String hostname;
    private int port;

    public DBManager() {
    }

    @PostConstruct
    private void init() {
        log.debug("init");
        if (dbName == null || dbName.trim().length() == 0) {
            throw new IllegalStateException("Check your configuration for database name (DBManager - property dbName)");
        }
        
        db = mongo.getDB(dbName);
        log.debug("DB retrieved: " + db);
    }

    public DB getDB() {
        return db;
    }

    public DBCollection getDBCollection(String collectionName) {
        if (db == null) {
            throw new IllegalStateException("Check your configuration concerning Mongo DB declaration");
        }

        return db.getCollection(collectionName);
    }

    public Mongo getMongo() {
        return mongo;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
