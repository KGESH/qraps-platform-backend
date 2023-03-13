package qraps.platform.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CacheService {

    private Map<String, Object> keyValueStore;

    @Autowired
    public CacheService(HashMap<String, Object> keyValueStore) {
        this.keyValueStore = keyValueStore;
    }


    public void storeValue(String key, Object value) {
        keyValueStore.put(key, value);
    }

    public Optional<Object> getValue(String key) {
        return Optional.ofNullable(keyValueStore.get(key));
    }
}
