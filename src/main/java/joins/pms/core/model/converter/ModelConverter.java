package joins.pms.core.model.converter;

import joins.pms.core.model.BaseDto;
import joins.pms.core.model.BaseEntity;
import joins.pms.core.model.converter.exception.ConvertFailedException;
import joins.pms.core.model.converter.exception.IllegalTargetClassException;
import joins.pms.core.model.converter.exception.NoSuchConvertCaseException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ModelConverter {
    private final List<Class> acceptedClasses = Arrays.asList(BaseDto.class, BaseEntity.class, Map.class, JSONObject.class);

    private <T> boolean isAcceptedClass (Class<T> target) {
        boolean isAccepted = false;
        for (Class acceptedClass : acceptedClasses) {
            if (acceptedClass.isAssignableFrom(target)) {
                isAccepted = true;
            }
        }
        return isAccepted;
    }

    private <T, D> D convertModelToModel (T target, Class<D> distClass) {
        try {
            D dist = distClass.getDeclaredConstructor().newInstance();

            for (Method method : distClass.getMethods()) {
                String methodName = method.getName();
                if (methodName.startsWith("set")) {
                    Method getter = target.getClass().getMethod("get" + methodName.substring(3));
                    method.invoke(dist, getter.invoke(target));
                }
            }

            return dist;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private <T, D> Map<String, Object> convertModelToMap (T target) {
        try {
            Map<String, Object> dist = new HashMap<>();
            Class<?> targetClass = target.getClass();

            for (Method method : targetClass.getDeclaredMethods()) {
                String methodName = method.getName();
                if (methodName.startsWith("get")) {
                    String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                    dist.put(fieldName, method.invoke(target));
                }
            }
            return dist;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ConvertFailedException(e);
        }
    }

    private <T, D> D convertMapToModel (T target, Class<D> distClass) {
        try {
            D dist = distClass.getDeclaredConstructor().newInstance();
            Map map = (Map) target;
            for (Method method : distClass.getMethods()) {
                String methodName = method.getName();
                if (methodName.startsWith("set")) {
                    String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                    if (map.containsKey(fieldName)) {
                        Field field = distClass.getDeclaredField(fieldName);
                        if (field.getType().isEnum()) {
                            method.invoke(dist, Enum.valueOf((Class<Enum>) field.getType(), map.get(fieldName).toString()));
                        } else {
                            method.invoke(dist, field.getType().cast(map.get(fieldName)));
                        }
                    }
                }
            }
            return dist;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 NoSuchFieldException e) {
            throw new ConvertFailedException(e);
        }
    }

    public <T, D extends Object> D convert (T target, Class<D> distClass) {
        if (!isAcceptedClass(target.getClass())) {
            throw new IllegalTargetClassException("Can't accept target class : " + target.getClass().getName());
        }
        if (!isAcceptedClass(distClass)) {
            throw new IllegalTargetClassException("Can't accept dist class : " + distClass.getName());
        }

        Class targetClass = target.getClass();
        if (BaseDto.class.isAssignableFrom(targetClass) && BaseEntity.class.isAssignableFrom(distClass)) {
            // DTO -> Entity
            return convertModelToModel(target, distClass);
        } else if (BaseEntity.class.isAssignableFrom(targetClass) && BaseDto.class.isAssignableFrom(distClass)) {
            // Entity -> DTO
            return convertModelToModel(target, distClass);
        } else if (Map.class.isAssignableFrom(targetClass) && BaseDto.class.isAssignableFrom(distClass)) {
            // Map -> DTO
            return convertMapToModel(target, distClass);
        } else if (BaseDto.class.isAssignableFrom(targetClass) && Map.class.isAssignableFrom(distClass)) {
            // DTO -> Map
            return (D) convertModelToMap(target);
        } else {
            throw new NoSuchConvertCaseException("Can't change " + targetClass.getName() + " to " + distClass.getName());
        }
    }
}
