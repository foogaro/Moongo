package com.foogaro.nosql.moongo.query;

import com.foogaro.nosql.moongo.query.exception.QueryManagerException;
import com.mongodb.DBObject;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
*/
public interface IQueryManager {

    public List<DBObject> find(Class classType) throws QueryManagerException;
    public List<DBObject> find(LinkedList<DBObject> dbObjects, Class classType) throws QueryManagerException;
    public DBObject findOne(LinkedList<DBObject> dbObjects, Class classType) throws QueryManagerException;

}
