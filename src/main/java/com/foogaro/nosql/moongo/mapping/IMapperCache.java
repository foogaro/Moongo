package com.foogaro.nosql.moongo.mapping;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public interface IMapperCache {

    public String[] getFields(Object object);
    public Object getFieldValue(Object object, String fieldName);
    public Class getFieldClass(Object object, String fieldName);
    public String getFieldClassName(Object object, String fieldName);
    public Method getGetter(Object object, String fieldName);
    public Method getSetter(Object object, String fieldName);
    public Type getCollectionType(Object object, String fieldName);
    public Type getCollectionArgumentType(Object object, String fieldName);
    public boolean isCustom(Object object, String fieldName);
    public boolean isCollection(Object object, String fieldName);
    public boolean isSomeDBReferenced(Object object);
    public boolean isDbReferenced(Object object, String fieldName);

}
