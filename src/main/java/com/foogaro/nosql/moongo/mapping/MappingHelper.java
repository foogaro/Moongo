package com.foogaro.nosql.moongo.mapping;

import com.foogaro.nosql.moongo.mapping.exception.MappingException;
import com.foogaro.nosql.moongo.persistence.DBReferenceManager;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * This class gives you the capability to convert any POJO to a DBObject and vice-versa.
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
@Named
public class MappingHelper {

    private static final Logger log = LoggerFactory.getLogger(MappingHelper.class);

    private IMapperCache mapperCache = new MapperCache();

    @Inject
    private DBReferenceManager dbReferenceManager;

    public Map<String, Object> toMQL(Object object) throws MappingException {

        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(toMap(object, false));

        Set<String> keys = map.keySet();
        Object value = null;

        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                value = map.get(key);
                if (value != null){
                    if (value instanceof BasicDBObject) {
                        map.remove(key);
                        map.putAll(toMQL((BasicDBObject)value, key));
                    }
                }
            }
        }

        return map;
    }

    private Map<String, Object> toMQL(BasicDBObject basicDBObject, String parentKey) throws MappingException {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(basicDBObject.toMap());

        Set<String> keys = map.keySet();
        Object value = null;
        String newKey = null;
        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                value = map.get(key);
                if (value != null){
                    newKey = parentKey+"."+key;
                    if (value instanceof BasicDBObject) {
                        map.remove(key);
                        map.putAll(toMQL((BasicDBObject)value, newKey));
                    } else {
                        result.put(newKey, value);
                    }
                }
            }
        }

        return result;
    }

    public Object toObject(DBObject dbObject, Class clazz) throws MappingException {
        return toObject(dbObject, clazz, null);
    }

    public Object toObject(DBObject dbObject, Object object) throws MappingException {
        return toObject(dbObject, null, object);
    }

    private Object toObject(DBObject dbObject, Class clazz, Object instance) throws MappingException {
        Map<String, Object> map = dbObject.toMap();
        Set<String> keys = map.keySet();

        Object value = null;

        if (instance == null)  {
            try {
                instance = clazz.newInstance();
            } catch (InstantiationException e) {
                log.error("dbObject: " + dbObject);
                log.error("clazz: " + clazz);
                log.error("instance: " + instance);
                log.error(e.getMessage(), e);
                throw new MappingException(e);
            } catch (IllegalAccessException e) {
                log.error("dbObject: " + dbObject);
                log.error("clazz: " + clazz);
                log.error("instance: " + instance);
                log.error(e.getMessage(), e);
                throw new MappingException(e);
            }
        }

        Class cls = null;
        for (String key : keys) {
            value = map.get(key);
            log.debug("key: " + key);
            log.debug("value: " + value);
            if (value instanceof BasicDBObject) {
                if (mapperCache.isDbReferenced(instance, key)) {
                    DBObject ref = dbReferenceManager.read((DBObject)value, mapperCache.getFieldClass(instance, key));
                    log.debug("ref: " + ref);
                    Object ref2 = toObject(ref, mapperCache.getFieldClass(instance, key));
                    invokeSetter(instance, key, ref2);
                } else {
                    Object bm = toObject((DBObject)value, mapperCache.getFieldClass(instance, key));
                    invokeSetter(instance, key, bm);
                }
            } else if (value instanceof BasicDBList) {
                if (Set.class.equals(mapperCache.getCollectionType(instance, key))) {
                    Set set = toSet((BasicDBList)value, mapperCache.getCollectionArgumentType(instance, key));
                    invokeSetter(instance, key, (Set)set);
                } else if (List.class.equals(mapperCache.getCollectionType(instance, key))) {
                    List list = toList((BasicDBList)value, mapperCache.getCollectionArgumentType(instance, key));
                    invokeSetter(instance, key, list);
                }
            } else {
                invokeSetter(instance, key, value);
            }
        }
        log.debug("instance: " + instance);
        return instance;

    }

    private Set toSet(BasicDBList basicDBList, Type type) throws MappingException {
        Set set = null;
        BasicDBObject basicDBObject = null;

        Object obj = null;
        try {
            set = (Set)(new HashSet());
            obj = ((Class)type).newInstance();

            if (basicDBList != null && !basicDBList.isEmpty()) {
                for (Object object : basicDBList) {
                    basicDBObject = (BasicDBObject)object;
                    Map<String, Object> map = basicDBObject.toMap();
                    Set<String> keys = map.keySet();
                    for (String key : keys) {
                        setValue(obj, key, map.get(key));
                    }
                    set.add(obj);
                }
            }
        } catch (InstantiationException e) {
            log.error("BasicDBList: " + basicDBList);
            log.error("Type: " + type);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        } catch (IllegalAccessException e) {
            log.error("BasicDBList: " + basicDBList);
            log.error("Type: " + type);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        }

        return set;
    }

    private List toList(BasicDBList basicDBList, Type type) throws MappingException {
        List list = null;
        BasicDBObject basicDBObject = null;

        Object obj = null;
        try {
            list = (List)(new ArrayList());
            obj = ((Class)type).newInstance();

            if (basicDBList != null && !basicDBList.isEmpty()) {
                for (Object object : basicDBList) {
                    basicDBObject = (BasicDBObject)object;
                    Map<String, Object> map = basicDBObject.toMap();
                    Set<String> keys = map.keySet();
                    for (String key : keys) {
                        setValue(obj, key, map.get(key));
                    }
                    list.add(obj);
                }
            }
        } catch (InstantiationException e) {
            log.error("BasicDBList: " + basicDBList);
            log.error("Type: " + type);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        } catch (IllegalAccessException e) {
            log.error("BasicDBList: " + basicDBList);
            log.error("Type: " + type);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        }

        return list;
    }

    private void invokeSetter(Object object, String fieldName, Object value) throws MappingException {
        try {
            Method method = mapperCache.getSetter(object, fieldName);
            if (method != null) {
                method.invoke(object, value);
            }
        } catch (InvocationTargetException e) {
            log.error("object: " + object);
            log.error("fieldName: " + fieldName);
            log.error("value: " + value);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        } catch (IllegalAccessException e) {
            log.error("object: " + object);
            log.error("fieldName: " + fieldName);
            log.error("value: " + value);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        } catch (Exception e) {
            log.error("object: " + object);
            log.error("fieldName: " + fieldName);
            log.error("value: " + value);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        }
    }

    public DBObject toDBObject(Object object, boolean saving) throws MappingException {
        BasicDBObjectBuilder basicDBObjectBuilder = BasicDBObjectBuilder.start();
        basicDBObjectBuilder.get().putAll(toMap(object, saving));
        return basicDBObjectBuilder.get();
    }

    public Map<String, Object> toMap(Object object, boolean saving) throws MappingException {
        if (object != null) {
            return scanObject(object, saving);
        }
        return null;
    }

    public DBObject toDBObject(Map<String, Object> map) {
        BasicDBObjectBuilder basicDBObjectBuilder = BasicDBObjectBuilder.start(map);
        return basicDBObjectBuilder.get();
    }

    private Map<String, Object> scanObject(Object object, boolean saving) throws MappingException {

        Map<String, Object> documentMap = new HashMap<String, Object>();

        Object fieldValue = null;
        try {
            String [] fields = mapperCache.getFields(object);
            for (String field : fields) {
                if (field != null) {
                    fieldValue = mapperCache.getFieldValue(object, field);
                    if (fieldValue != null) {
                        if (mapperCache.isCustom(object, field)) {
                            log.debug("Looks like we have a custom type: " + mapperCache.getFieldClassName(object, field));
                            if (mapperCache.isDbReferenced(object,field)) {
                                log.debug("Looks like " + field + " is DBReferenced");
                                Object idValue = mapperCache.getFieldValue(object, "_id");
                                if (idValue == null) {
                                    DBObject dbObject = dbReferenceManager.insert(toDBObject(fieldValue, saving), fieldValue.getClass());
                                    log.debug("dbObject: " + dbObject);
                                    ObjectId objectId = (ObjectId)dbObject.get("_id");
                                    log.debug("objectId: " + objectId);
                                    String className = fieldValue.getClass().getName();
                                    log.debug("className: " + className);
                                    DBObject ref = new BasicDBObject();
                                    ref.put("ref", className);
                                    ref.put("_id", objectId);
                                    log.debug("ref: " + ref);
                                    documentMap.put(field, ref);
                                } else {
                                    DBObject dbObject = dbReferenceManager.read(toDBObject(fieldValue, saving), fieldValue.getClass());
                                    documentMap.put(field, dbObject);
                                }
                            } else {
                                documentMap.put(field, toDBObject(fieldValue, saving));
                            }
                        } else {
                            if (fieldValue.getClass().isArray()) {
                                Object[] objects =  (Object[])fieldValue;
                                BasicDBList basicDBList = new BasicDBList();
                                for (Object obj : objects) {
                                    basicDBList.add(obj);
                                }
                                documentMap.put(field, basicDBList);
                            } else if (List.class.isInstance(fieldValue)) {
                                List list = (List)fieldValue;
                                BasicDBList basicDBList = new BasicDBList();
                                for (Object obj : list) {
                                    basicDBList.add(obj);
                                }
                                documentMap.put(field, basicDBList);
                            } else if (Set.class.isInstance(fieldValue)) {
                                Set set = (Set)fieldValue;
                                BasicDBList basicDBList = new BasicDBList();
                                for (Object obj : set) {
                                    DBObject dbo = toDBObject(obj, saving);
                                    basicDBList.add(dbo);
                                }
                                documentMap.put(field, basicDBList);
                            } else {
                                documentMap.put(field, fieldValue);
                            }
                        }
                    } else {
                        if (saving) {
                            documentMap.put(field, null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("object: " + object);
            log.error("fieldValue: " + fieldValue);
            log.error("documentMap: " + documentMap);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        }

        return documentMap;
    }

    public Object setValue(Object instance, String fieldName, Object value) throws MappingException {
        try {
            Method method = mapperCache.getSetter(instance, fieldName);
            return method.invoke(instance, value);
        } catch (InvocationTargetException e) {
            log.error("instance: " + instance);
            log.error("fieldName: " + fieldName);
            log.error("value: " + value);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        } catch (IllegalAccessException e) {
            log.error("instance: " + instance);
            log.error("fieldName: " + fieldName);
            log.error("value: " + value);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        } catch (Throwable t) {
            log.error("instance: " + instance);
            log.error("fieldName: " + fieldName);
            log.error("value: " + value);
            log.error(t.getMessage(), t);
            throw new MappingException(t);
        }
    }

    public Object getValue(Object instance, String fieldName) throws MappingException {
        try {
            Method method = mapperCache.getGetter(instance, fieldName);
            return method.invoke(instance);
        } catch (InvocationTargetException e) {
            log.error("instance: " + instance);
            log.error("fieldName: " + fieldName);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        } catch (IllegalAccessException e) {
            log.error("instance: " + instance);
            log.error("fieldName: " + fieldName);
            log.error(e.getMessage(), e);
            throw new MappingException(e);
        } catch (Throwable t) {
            log.error("instance: " + instance);
            log.error("fieldName: " + fieldName);
            log.error(t.getMessage(), t);
            throw new MappingException(t);
        }
    }

}
