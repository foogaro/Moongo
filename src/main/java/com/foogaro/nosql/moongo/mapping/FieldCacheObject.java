package com.foogaro.nosql.moongo.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public class FieldCacheObject {

    private static final Logger log = LoggerFactory.getLogger(FieldCacheObject.class);

    private String name;
    private Method getter;
    private Method setter;
    private String className;

    //Used for collections
    private Type fieldType;
    private Type collectionType;
    private Type collectionArgumentType;
    private boolean custom = false;
    private boolean collection = false;
    private boolean dbReferenced = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    public Type getFieldType() {
        return fieldType;
    }

    public void setFieldType(Field field) {
        this.fieldType = field.getType();
        if (field.getGenericType() instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Type[] types = parameterizedType.getActualTypeArguments();
            if (types.length == 1) {
                this.collection = true;
                this.collectionType = parameterizedType.getRawType();
                this.collectionArgumentType = types[0];
            }
        }

        if (
            ((Class)this.fieldType).getPackage() == null ||
            ((Class)this.fieldType).getPackage().getName().indexOf("java.") == 0 ||
            ((Class)this.fieldType).getPackage().getName().indexOf("org.bson") == 0 ||
            ((Class)this.fieldType).getPackage().getName().indexOf("com.mongodb") == 0
                ) {
            this.custom = false;
        } else {
            this.custom = true;
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Type getCollectionType() {
        return collectionType;
    }

    public Type getCollectionArgumentType() {
        return collectionArgumentType;
    }

    public boolean isCustom() {
        return custom;
    }

    public boolean isCollection() {
        return collection;
    }

    public boolean isDbReferenced() {
        return dbReferenced;
    }

    public void setDbReferenced(boolean dbReferenced) {
        this.dbReferenced = dbReferenced;
    }

    @Override
    public String toString() {
        return "FieldCacheObject{" +
                "name='" + name + '\'' +
                ", getter=" + getter +
                ", setter=" + setter +
                ", className='" + className + '\'' +
                ", fieldType=" + fieldType +
                ", collectionType=" + collectionType +
                ", collectionArgumentType=" + collectionArgumentType +
                ", custom=" + custom +
                ", collection=" + collection +
                ", dbReferenced=" + dbReferenced +
                '}';
    }
}
