package com.foogaro.nosql.moongo.annotation;

/**
 * Annotation used to reference an embedded document. If the reference is not presence the embedded document will first persisted and the referenced.
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
@java.lang.annotation.Documented
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.LOCAL_VARIABLE})
public @interface DBReferenced {

    String collection() default "";
    String db() default "";

}
