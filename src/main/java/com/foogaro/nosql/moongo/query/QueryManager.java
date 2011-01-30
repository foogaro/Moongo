package com.foogaro.nosql.moongo.query;

import com.foogaro.nosql.moongo.core.ABaseManager;
import com.foogaro.nosql.moongo.query.exception.QueryManagerException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
@Named
public class QueryManager extends ABaseManager implements IQueryManager {

    private static final Logger log = LoggerFactory.getLogger(QueryManager.class);

    public List<DBObject> find(Class classType) throws QueryManagerException {
        return find(null, classType);
    }

    public List<DBObject> find(LinkedList<DBObject> dbObjects, Class classType) throws QueryManagerException {
        List<DBObject> results = new ArrayList<DBObject>();
        try {
            DBObject query = new BasicDBObject();

            DBObject dbObject = null;
            while (dbObjects.size() > 0) {
                dbObject = dbObjects.poll();
                if (dbObject != null) {
                    query.putAll(dbObject.toMap());
                }
            }
            DBCursor dbCursor = getDBCollection(classType).find(query);
            while (dbCursor.hasNext()) {
                results.add(dbCursor.next());
            }
        } catch (Exception e) {
            throw new QueryManagerException(e);
        }
        return results;
    }

    public DBObject findOne(LinkedList<DBObject> dbObjects, Class classType) throws QueryManagerException {
        try {
            DBObject query = new BasicDBObject();
            DBObject dbObject = null;
            while (dbObjects.size() > 0) {
                dbObject = dbObjects.poll();
                if (dbObject != null) {
                    query.putAll(dbObject.toMap());
                }
            }
            DBCursor dbCursor = getDBCollection(classType).find(query);
            while (dbCursor.hasNext()) {
                return dbCursor.next();
            }
        } catch (Exception e) {
            throw new QueryManagerException(e);
        }
        return null;
    }

}
