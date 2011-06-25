package com.foogaro.nosql.moongo.mapping;

import com.foogaro.nosql.moongo.annotation.DBReferenced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public class CacheObject {

    private static final Logger log = LoggerFactory.getLogger(CacheObject.class);

    private Map<String, FieldCacheObject> cache = new HashMap<String, FieldCacheObject>();
    private boolean someDBReferenced = false;

    private FieldCacheObject get(String key) {
        return cache.get(key);
    }

    public String[] getFields() {
        return (String[])cache.keySet().toArray(new String[cache.size()]);
    }

    public Object getFieldValue(Object object, String fieldName) {
        FieldCacheObject fieldCacheObject = get(fieldName);

        if (fieldCacheObject == null) return null;

        Object value = null;
        try {
            value = fieldCacheObject.getGetter().invoke(object);
        } catch (IllegalAccessException e) {
            log.error("Object: " + object);
            log.error("fieldName: " + fieldName);
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error("Object: " + object);
            log.error("fieldName: " + fieldName);
            log.error(e.getMessage(), e);
        }

        return value;
    }

    public Class getFieldClass(String fieldName) {
        FieldCacheObject fieldCacheObject = get(fieldName);

        if (fieldCacheObject == null) return null;

        return (Class)fieldCacheObject.getFieldType();
    }

    public String getFieldClassName(String fieldName) {
        FieldCacheObject fieldCacheObject = get(fieldName);

        if (fieldCacheObject == null) return null;

        return fieldCacheObject.getClassName();
    }

    public Method getGetter(String fieldName) {
        FieldCacheObject fieldCacheObject = get(fieldName);

        if (fieldCacheObject == null) return null;

        return fieldCacheObject.getGetter();
    }

    public Method getSetter(String fieldName) {
        FieldCacheObject fieldCacheObject = get(fieldName);

        if (fieldCacheObject == null) return null;

        return fieldCacheObject.getSetter();
    }

    public Type getCollectionType(String fieldName) {
        FieldCacheObject fieldCacheObject = get(fieldName);

        if (fieldCacheObject == null) return null;

        return fieldCacheObject.getCollectionType();
    }

    public Type getCollectionArgumentType(String fieldName) {
        FieldCacheObject fieldCacheObject = get(fieldName);

        if (fieldCacheObject == null) return null;

        return fieldCacheObject.getCollectionArgumentType();
    }

    public boolean isCustom(String fieldName) {
        FieldCacheObject fieldCacheObject = get(fieldName);

        if (fieldCacheObject == null) return false;

        return fieldCacheObject.isCustom();
    }

    public boolean isCollection(String fieldName) {
        FieldCacheObject fieldCacheObject = get(fieldName);

        if (fieldCacheObject == null) return false;

        return fieldCacheObject.isCollection();
    }

    public boolean isDbReferenced(String fieldName) {
        FieldCacheObject fieldCacheObject = get(fieldName);

        if (fieldCacheObject == null) return false;

        return fieldCacheObject.isDbReferenced();
    }

    protected FieldCacheObject newFieldCacheObject(Object object, Field field) {
        FieldCacheObject fieldCacheObject = new FieldCacheObject();
        fieldCacheObject.setName(field.getName());
        fieldCacheObject.setFieldType(field);
        fieldCacheObject.setClassName(((Class)fieldCacheObject.getFieldType()).getName());
        fieldCacheObject.setGetter(getGetterMethod(object, fieldCacheObject));
        fieldCacheObject.setSetter(getSetterMethod(object, fieldCacheObject));
        fieldCacheObject.setDbReferenced(field.isAnnotationPresent(DBReferenced.class));

        cache.put(fieldCacheObject.getName(), fieldCacheObject);
        this.someDBReferenced = someDBReferenced || fieldCacheObject.isDbReferenced();

        return fieldCacheObject;
    }

    private Method getGetterMethod(Object object, FieldCacheObject fieldCacheObject) {
        Method method = null;
        try {
            method = object.getClass().getMethod(getGetterMethodName(fieldCacheObject));
        } catch (NoSuchMethodException e) {
            log.error("Object: " + object);
            log.error("FieldCacheObject: " + fieldCacheObject);
            log.error(e.getMessage(), e);
        }
        return method;
    }

    private Method getSetterMethod(Object object, FieldCacheObject fieldCacheObject) {
        Method method = null;
        try {
            if (fieldCacheObject.isCollection()){
                method = object.getClass().getMethod(getSetterMethodName(fieldCacheObject), (Class)fieldCacheObject.getCollectionType());
            } else {
                method = object.getClass().getMethod(getSetterMethodName(fieldCacheObject), (Class)fieldCacheObject.getFieldType());
            }
        } catch (NoSuchMethodException e) {
            log.error("Object: " + object);
            log.error("FieldCacheObject: " + fieldCacheObject);
            log.error(e.getMessage(), e);
        }
        return method;
    }

    private String getGetterMethodName(FieldCacheObject fieldCacheObject) {
        try {
            boolean isBoolean = false;
            if (fieldCacheObject.getClassName().equals(Boolean.class.getName()) || fieldCacheObject.getClassName().equals("boolean")) {
                isBoolean = true;
            }
            if (isBoolean) {
                return "is" + fieldCacheObject.getName().substring(0,1).toUpperCase() + fieldCacheObject.getName().substring(1);
            } else {
                return "get" + fieldCacheObject.getName().substring(0,1).toUpperCase() + fieldCacheObject.getName().substring(1);
            }
        } catch (Exception e) {
            log.error("FieldCacheObject: " + fieldCacheObject);
            log.error(e.getMessage(), e);
        }

        return null;
    }

    private String getSetterMethodName(FieldCacheObject fieldCacheObject) {
        try {
            boolean isBoolean = false;
            if (fieldCacheObject.getClassName().equals(Boolean.class.getName()) || fieldCacheObject.getClassName().equals("boolean")) {
                isBoolean = true;
            }
            if (isBoolean) {
                return "set" + fieldCacheObject.getName().substring(0,1).toUpperCase() + fieldCacheObject.getName().substring(1);
            } else {
                return "set" + fieldCacheObject.getName().substring(0,1).toUpperCase() + fieldCacheObject.getName().substring(1);
            }
        } catch (Exception e) {
            log.error("FieldCacheObject: " + fieldCacheObject);
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public boolean isSomeDBReferenced() {
        return someDBReferenced;
    }

    @Override
    public String toString() {
        return "CacheObject{" +
                "cache=" + cache +
                ", someDBReferenced=" + someDBReferenced +
                '}';
    }
}
