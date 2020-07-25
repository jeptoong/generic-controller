package org.example.genericcontroller.support.generic.mapping;

import org.example.genericcontroller.utils.ObjectUtils;
import org.example.genericcontroller.utils.constant.Constants;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Field Type Resolved.
 *
 * @author hungp
 */
public class FieldTypeResolved {

    /**
     * Prevents new instance.
     */
    private FieldTypeResolved() {
    }

    /**
     * Get {@link FieldTypeResolved} instance.
     *
     * @return {@link FieldTypeResolved}
     */
    public static FieldTypeResolved getInstance() {
        return FieldTypeResolvedHelper.INSTANCE;
    }

    /**
     * Inner helper class to new {@link FieldTypeResolved} instance.
     */
    private static class FieldTypeResolvedHelper {
        private static final FieldTypeResolved INSTANCE = new FieldTypeResolved();
    }

    /**
     * Parse path to array {@link Field}.
     *
     * @param type class type
     * @param path path of field
     * @return array {@link Field}
     */
    public Field[] parseFieldByPath(Class<?> type, String path) {
        Assert.notNull(type, "Class type must be not null");
        Assert.hasLength(path, "Field path must be not empty");
        String[] segmentPath = path.split(Constants.DOT_REGEX);
        Field[] fieldPath = new Field[segmentPath.length];
        int index = 0;
        Field field;
        do {
            String segment = segmentPath[index];
            field = ObjectUtils.getField(type, segment, true);
            fieldPath[index] = field;
            type = field.getType();
            if (Collection.class.isAssignableFrom(type)) {
                type = ObjectUtils.getGenericField(field);
            }
            index++;
        } while (index < segmentPath.length);
        return fieldPath;
    }
}
