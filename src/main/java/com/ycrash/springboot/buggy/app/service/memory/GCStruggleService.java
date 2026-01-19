package com.ycrash.springboot.buggy.app.service.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Service that creates thousands of objects stored in a WeakHashMap
 * It puts a heavy load on the garbage collector, without triggering an OOM or crashing the application,
 * because these objects are removed from memory when it starts running low
 * @author Maxime
 */
@Service
public class GCStruggleService {

    private static final Logger log = LoggerFactory.getLogger(GCStruggleService.class);

    public void start() {

        try {
            Map<String, String> map = new WeakHashMap<>();

            long counter = 0;
            while (counter < 50_000_000) {

                map.put("key-" + counter, "value-" + counter);

                counter++;
                if (counter % 100_000 == 0) {
                    log.info("Thread : {} : Added {} elements", Thread.currentThread().getName(), counter);
                }
            }
            log.info("Value of key-0 " + map.get("key-0"));
            log.info("Value of key-10000 " + map.get("key-10000"));
            log.info("Value of key-49_999_999 " + map.get("key-49_999_999"));
        } catch (Throwable e) {
            log.error("{} caught! Application will not crash.", e.getClass());
        }
    }
}
