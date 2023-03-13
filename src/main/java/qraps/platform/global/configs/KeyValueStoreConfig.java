package qraps.platform.global.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class KeyValueStoreConfig {


    @Bean
    public ConcurrentHashMap<String, Object> keyValueStore() {
        return new ConcurrentHashMap<>();
    }

}
