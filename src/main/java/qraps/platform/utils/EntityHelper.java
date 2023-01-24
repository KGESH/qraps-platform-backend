package qraps.platform.utils;

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

}
