package joins.pms.core.model;

import joins.pms.api.v1.dto.ScheduleDto;
import joins.pms.api.v1.entity.Schedule;
import joins.pms.core.dto.BaseDto;
import joins.pms.core.entity.BaseEntity;
import joins.pms.core.model.exception.FailedConvertException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ModelConverter {
    public static <D extends BaseDto, E extends BaseEntity> D convert (E entity, Class<D> dtoClass) throws FailedConvertException {
        return change(entity, dtoClass);
    }

    public static <E extends BaseEntity, D extends BaseDto> E convert(D dto, Class<E> entityClass) throws FailedConvertException {
        return change(dto, entityClass);
    }

    private static <D, T> D change (T target, Class<D> distClass) throws FailedConvertException {
        try {
            D dist = distClass.newInstance();

            List<Field> targetFields = new ArrayList<>();
            for (Field field : target.getClass().getDeclaredFields()) targetFields.add(field);
            for (Field field : target.getClass().getFields()) targetFields.add(field);

            List<Field> distFields = new ArrayList<>();
            for (Field field : distClass.getDeclaredFields()) distFields.add(field);
            for (Field field : distClass.getFields()) distFields.add(field);

            for (Method method : distClass.getMethods()) {
                String methodName = method.getName();

                if (methodName.startsWith("set")) {
                    Method getter = target.getClass().getMethod("get" + methodName.substring(3));
                    method.invoke(dist, getter.invoke(target));
                }
            }
            return dist;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
            throw new FailedConvertException(ex.getCause().getMessage());
        }
    }
}
