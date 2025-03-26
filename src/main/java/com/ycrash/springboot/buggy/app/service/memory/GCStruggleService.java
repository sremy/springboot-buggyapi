package com.ycrash.springboot.buggy.app.service.memory;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Maxime
 */
@Service
@Log4j2
public class GCStruggleService {


    public void start() {

        try {

            Map<String, String> map = new WeakHashMap<>();

            long counter = 0;
            while (true) {

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
