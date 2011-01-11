package com.foogaro.nosql.moongo.query;

import java.util.List;

/**
* @see QueryObject
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
*/
public interface IQueryObject {

    public QueryObject eq(String key, Object value);
    public QueryObject ne(String key, Object value);

    public QueryObject or(String key, Object... values);
    public QueryObject nor(String key, Object... values);

    public QueryObject as(Object Object);

    public QueryObject starts(String key, String value);
    public QueryObject like(String key, String value);
    public QueryObject ends(String key, String value);

    public QueryObject in(String key, List<Object> values);
    public QueryObject nin(String key, List<Object> values);

    public QueryObject gt(Object object);
    public QueryObject lt(Object object);
    public QueryObject gte(Object object);
    public QueryObject lte(Object object);

    public QueryObject gt(String key, Object value);
    public QueryObject lt(String key, Object value);
    public QueryObject gte(String key, Object value);
    public QueryObject lte(String key, Object value);

}
