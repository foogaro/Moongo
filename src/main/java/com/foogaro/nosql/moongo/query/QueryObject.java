package com.foogaro.nosql.moongo.query;

import com.foogaro.nosql.moongo.query.exception.QueryManagerException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import java.util.LinkedList;
import java.util.List;


/**
* When you need to query on your MongoDB, belong to your QueryManager implementation, create your query conditions with it.
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
*/
public class QueryObject implements IQueryObject {

    public class Operators {
        public final static String OR = "$or";
        public final static String NOR = "$nor";
        public final static String EQ = "$eq";
        public final static String NE = QueryOperators.NE;
        public final static String AS = "$as";
        public final static String STARTS = "$starts";
        public final static String LIKE = "$like";
        public final static String ENDS = "$ends";
        public final static String IN = QueryOperators.IN;
        public final static String NIN = QueryOperators.NIN;
        public final static String GT = QueryOperators.GT;
        public final static String LT = QueryOperators.LT;
        public final static String GTE = QueryOperators.GTE;
        public final static String LTE = QueryOperators.LTE;
        public static final String MOD = QueryOperators.MOD;
        public static final String ALL = QueryOperators.ALL;
        public static final String SIZE = QueryOperators.SIZE;
        public static final String EXISTS = QueryOperators.EXISTS;
        public static final String WHERE = QueryOperators.WHERE;
        public static final String NEAR = QueryOperators.NEAR;
    }

    private LinkedList<String> queryOperators = new LinkedList<String>();
    private LinkedList<DBObject> queryParameters = new LinkedList<DBObject>();

    protected QueryObject() {
    }

    public static QueryObject newInstance() {
        return new QueryObject();
    }

    public QueryObject or(String key, Object... values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, values);
            queryOperators.add(Operators.OR);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject nor(String key, Object... values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, values);
            queryOperators.add(Operators.NOR);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject eq(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(Operators.EQ);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject ne(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(Operators.NE);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject as(Object value) {
        if (value != null) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(Operators.AS, value);
            queryOperators.add(Operators.AS);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject starts(String key, String value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, java.util.regex.Pattern.compile("^" + value));
            queryOperators.add(Operators.STARTS);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject like(String key, String value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, java.util.regex.Pattern.compile(value));
            queryOperators.add(Operators.LIKE);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject ends(String key, String value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, java.util.regex.Pattern.compile(value + "$"));
            queryOperators.add(Operators.ENDS);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject in(String key, List<Object> values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, values);
            queryOperators.add(Operators.IN);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject nin(String key, List<Object> values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, values);
            queryOperators.add(Operators.NIN);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject gt(Object object) {
        throw new QueryManagerException("Not yet implemented");
    }

    public QueryObject lt(Object object) {
        throw new QueryManagerException("Not yet implemented");
    }

    public QueryObject gte(Object object) {
        throw new QueryManagerException("Not yet implemented");
    }

    public QueryObject lte(Object object) {
        throw new QueryManagerException("Not yet implemented");
    }

    public QueryObject gt(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(Operators.GT);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject lt(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(Operators.LT);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject gte(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(Operators.GTE);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject lte(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(Operators.LTE);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject mod(Object object) {
        throw new QueryManagerException("Not yet implemented");
    }

    public QueryObject all(Object object) {
        throw new QueryManagerException("Not yet implemented");
    }

    public QueryObject size(Object object) {
        throw new QueryManagerException("Not yet implemented");
    }

    public QueryObject exists(Object object) {
        throw new QueryManagerException("Not yet implemented");
    }

    public QueryObject where(Object object) {
        throw new QueryManagerException("Not yet implemented");
    }

    public QueryObject near(Object object) {
        throw new QueryManagerException("Not yet implemented");
    }
    
    private boolean isValidKey(String key) {
        return (key != null && key.trim().length() > 0);
    }

    public LinkedList<DBObject> getQueryParameters() {
        return queryParameters;
    }

    public LinkedList<String> getQueryOperators() {
        return queryOperators;
    }
}
