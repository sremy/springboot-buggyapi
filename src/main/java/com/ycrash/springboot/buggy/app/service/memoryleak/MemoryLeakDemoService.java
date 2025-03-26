package com.ycrash.springboot.buggy.app.service.memoryleak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Ram Lakshmanan
 */

@Service
public class MemoryLeakDemoService {

    private static final Logger log = LoggerFactory.getLogger(MemoryLeakDemoService.class);

    static Object1 object1 = new Object1();

    public void start() {

        try {
            object1.grow();
        } catch (Throwable e) {
            System.out.println(e);
        }

        System.out.println("Application is still running :-)");
        try {
            Thread.sleep(5 * 1000);
        } catch (Exception e) {
        }

        System.out.println("Application terminated only now :-)");
    }

}
