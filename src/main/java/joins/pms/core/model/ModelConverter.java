package joins.pms.core.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Component
public class ModelConverter {
    public <D extends BaseDto, E extends BaseEntity> D convert (E entity, Class<D> dtoClass) {
        return change(entity, dtoClass);
    }

    public <E extends BaseEntity, D extends BaseDto> E convert(D dto, Class<E> entityClass) {
        return change(dto, entityClass);
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
