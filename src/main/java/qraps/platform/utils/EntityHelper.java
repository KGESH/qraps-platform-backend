package qraps.platform.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityHelper {
    public static Map<String, Object> convertEntityToMap(Object object) {
        Map<String, Object> map = new HashMap<>();

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return map;
    }


    public static <T> T convertMapToEntity(Object source, Class<T> toValueType) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(source, toValueType);
    }

}
