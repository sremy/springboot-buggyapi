package com.ycrash.springboot.buggy.app.service.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Maxime
 */
@Service
public class GCStruggleService {

    private static final Logger log = LoggerFactory.getLogger(GCStruggleService.class);

    public void start() {

        try {
            Map<String, String> map = new WeakHashMap<>();

            long counter = 0;
            while (counter < 100_000_000) {

                map.put("key-" + counter, "value-" + counter);

                counter++;
                if (counter % 1000 == 0) {
                    log.info("Thread : {} : Added {} elements", Thread.currentThread().getName(), counter);
                }
            }
        } catch (Throwable e) {
            log.error("{} caught! Application will not crash.", e.getClass());
        }
    }
}
