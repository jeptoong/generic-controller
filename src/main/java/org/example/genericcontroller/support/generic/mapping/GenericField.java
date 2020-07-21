package org.example.genericcontroller.support.generic.mapping;

import org.example.genericcontroller.support.generic.MappingClass;
import org.example.genericcontroller.support.generic.ObjectMappingCache;
import org.example.genericcontroller.utils.ObjectUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Generic Field.
 *
 * @author hungp
 */
public class GenericField {

    private ObjectMappingCache mappingCache;
    private Field field;
    private Class<?> fieldType;
    private boolean isCollection;
    @SuppressWarnings("rawtypes")
    private Class<? extends Collection> collectionType;

    @SuppressWarnings({"unchecked", "rawtypes"})
    private GenericField(Field field, ObjectMappingCache mappingCache) {
        Assert.notNull(field, "Generic field must be not null");
        this.field = field;
        this.mappingCache = mappingCache;
        if (Collection.class.isAssignableFrom(field.getType())) {
            isCollection = true;
            collectionType = (Class<? extends Collection>) field.getType();
            fieldType = ObjectUtils.getGenericField(field);
        } else {
            fieldType = field.getType();
        }
    }

    public Field getField() {
        return field;
    }

    public String getClassName() {
        return fieldType.getName();
    }

    public Class<?> getClassDeclaring() {
        return fieldType;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public String getFieldName() {
        return field.getName();
    }

    public boolean isInnerDTO() {
        return AnnotatedElementUtils.hasAnnotation(fieldType, MappingClass.class);
    }

    public boolean isInnerEntity() {
        return AnnotatedElementUtils.hasAnnotation(fieldType, Entity.class);
    }

    public static GenericField of(Field field, ObjectMappingCache mappingCache) {
        return new GenericField(field, mappingCache);
    }

    @Override
    public String toString() {
        return "GenericField{" +
                "field=" + field.getName() +
                '}';
    }
}
