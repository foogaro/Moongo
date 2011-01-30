package com.foogaro.nosql.moongo;

import com.foogaro.nosql.moongo.core.DBManager;
import com.foogaro.nosql.moongo.mapping.MappingHelper;
import com.foogaro.nosql.moongo.persistence.IPersistenceManager;
import com.foogaro.nosql.moongo.query.IQueryManager;
import com.foogaro.nosql.moongo.query.QueryObject;
import com.mongodb.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
@Named
@ApplicationScoped
public class MoongoAdmin {

    @Inject
    private DBManager dbManager;

    public Mongo getMongo() {
        return dbManager.getMongo();
    }

    public DB getDB(String databaseName) {
        return dbManager.getMongo().getDB(databaseName);
    }

    public List<String> getDatabaseNames() {
        return dbManager.getMongo().getDatabaseNames();
    }

    public Set<String> getCollectionNames() {
        return dbManager.getDB().getCollectionNames();
    }

    public DBCollection getDBCollection(String collectionName) {
        return dbManager.getDBCollection(collectionName);
    }

}
