package com.foogaro.nosql.moongo.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public class MapperCache implements IMapperCache {

    private Map<String, CacheObject> cache = new HashMap<String, CacheObject>();

    public String[] getFields(Object object) {
        CacheObject cacheObject = get(object);
        return cache.get(unique(object)).getFields();
    }

    public Object getFieldValue(Object object, String fieldName) {
        CacheObject cacheObject = get(object);
        return cacheObject.getFieldValue(object, fieldName);
    }

    public Class getFieldClass(Object object, String fieldName) {
        CacheObject cacheObject = get(object);
        return cacheObject.getFieldClass(fieldName);
    }

    public String getFieldClassName(Object object, String fieldName) {
        CacheObject cacheObject = get(object);
        return cacheObject.getFieldClassName(fieldName);
    }

    public Method getGetter(Object object, String fieldName) {
        CacheObject cacheObject = get(object);
        return cacheObject.getGetter(fieldName);
    }

    public Method getSetter(Object object, String fieldName) {
        CacheObject cacheObject = get(object);
        return cacheObject.getSetter(fieldName);
    }

    public Type getCollectionType(Object object, String fieldName) {
        CacheObject cacheObject = get(object);
        return cacheObject.getCollectionType(fieldName);
    }

    public Type getCollectionArgumentType(Object object, String fieldName) {
        CacheObject cacheObject = get(object);
        return cacheObject.getCollectionArgumentType(fieldName);
    }

    public boolean isCustom(Object object, String fieldName) {
        CacheObject cacheObject = get(object);
        return cacheObject.isCustom(fieldName);
    }

    public boolean isCollection(Object object, String fieldName) {
        CacheObject cacheObject = get(object);
        return cacheObject.isCollection(fieldName);
    }

    public boolean isDbReferenced(Object object, String fieldName) {
        CacheObject cacheObject = get(object);
        return cacheObject.isDbReferenced(fieldName);
    }

    public boolean isSomeDBReferenced(Object object) {
        CacheObject cacheObject = get(object);
        return cacheObject.isSomeDBReferenced();
    }

    private CacheObject get(Object object) {
        CacheObject cacheObject = cache.get(unique(object));
        if (cacheObject == null) {
            cacheObject = newCacheObject(object);
        }

        return cacheObject;
    }

    private String unique(Object object) {
        return object.getClass().getName();
    }

    protected CacheObject newCacheObject(Object object) {
        CacheObject cacheObject = new CacheObject();
        Field[] fields = null;

        Class superClass = object.getClass().getSuperclass();
        if (superClass != null) {
            fields = superClass.getDeclaredFields();
            for (Field field : fields) {
                cacheObject.newFieldCacheObject(object, field);
            }
        }
        fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            cacheObject.newFieldCacheObject(object, field);
        }
        cache.put(unique(object), cacheObject);

        return cacheObject;
    }
}
