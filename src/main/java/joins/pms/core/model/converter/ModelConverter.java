package joins.pms.core.model.converter;

import joins.pms.core.model.BaseDto;
import joins.pms.core.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@Component
public class ModelConverter {
    public <D extends BaseDto, E extends BaseEntity> D convert (E entity, Class<D> dtoClass) {
        return change(entity, dtoClass);
    }

    public <E extends BaseEntity, D extends BaseDto> E convert(D dto, Class<E> entityClass) {
        return change(dto, entityClass);
    }
    public <D extends BaseDto> D convert(Map<String, Object> map, Class<D> dtoClass) {
        try {
            D dist = dtoClass.getDeclaredConstructor().newInstance();
            for (Method method : dtoClass.getMethods()) {
                String methodName = method.getName();
                if (methodName.startsWith("set")) {
                    String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                    if (map.keySet().contains(fieldName)) {
                        Field field = dtoClass.getDeclaredField(fieldName);
                        if (field.getType().isEnum()) {
                            method.invoke(dist, Enum.valueOf((Class<Enum>) field.getType(), map.get(fieldName).toString()));
                        } else {
                            method.invoke(dist, field.getType().cast(map.get(fieldName)));
                        }
                    }
                }
            }
            return dist;
        } catch (NoSuchMethodException | NoSuchFieldException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private <D, T> D change (T target, Class<D> distClass) {
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
}
