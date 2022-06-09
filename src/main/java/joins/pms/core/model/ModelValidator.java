package joins.pms.core.model;

import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class ModelValidator {
    private final String PERSISTENCE_ID = "interface javax.persistence.Id";
    public <D extends BaseDto, E extends BaseEntity> boolean existsAllFields (D dto, Class<E> entityClass) {
        for (Field entityField : entityClass.getDeclaredFields()) {
            String entityFieldName = entityField.getName();
            String getMethodName = "get" + entityFieldName.substring(0, 1).toUpperCase() + entityFieldName.substring(1);
            if (!(this.checkFieldAndExists(dto, entityFieldName) && this.checkGetMethodExists(dto, getMethodName))) {
                return false;
            }
        }
        return true;
    }

    public <D extends BaseDto, E extends BaseEntity> boolean existsFieldsWithoutPrimaryKey (D dto, Class<E> entityClass) {
        for (Field entityField : entityClass.getDeclaredFields()) {
            Annotation annotation = entityField.getDeclaredAnnotation(javax.persistence.Id.class);
            if (annotation != null) continue;

            String entityFieldName = entityField.getName();
            String getMethodName = "get" + entityFieldName.substring(0, 1).toUpperCase() + entityFieldName.substring(1);
            if (!(this.checkFieldAndExists(dto, entityFieldName) && this.checkGetMethodExists(dto, getMethodName))) {
                return false;
            }
        }
        return true;
    }

    private <D extends BaseDto> boolean checkFieldAndExists (D dto, String fieldName) {
        try {
            dto.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private <D extends BaseDto> boolean checkGetMethodExists (D dto, String methodName) {
        try {
            dto.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
