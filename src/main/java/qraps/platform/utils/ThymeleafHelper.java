package qraps.platform.utils;

import java.util.Map;

public class ThymeleafHelper {
    public String getResultString(Map<String, String> resultsMap, String key1, String key2) {
        System.out.println("getResultString");
        System.out.println("key1" + key1);
        System.out.println("key2" + key2);

        if (resultsMap.get("result/" + key1) == null) {
            return getResultString(resultsMap, key2);
        } else if (resultsMap.get("result/" + key2) == null) {
            return getResultString(resultsMap, key1);
        }

        /** String can be 'O' or 'X' */
        String s1 = resultsMap.get("result/" + key1);
        String s2 = resultsMap.get("result/" + key2);


        if ("OK".equals(s1) && "OK".equals(s2)) {
            return "OK";
        } else {
            return "NG";
        }
    }

    public String getResultString(Map<String, String> resultsMap, String key) {
        return resultsMap.get("result/" + key);
    }
}
