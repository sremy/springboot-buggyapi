package com.ycrash.springboot.buggy.app.service.memoryleak.max;

import java.util.ArrayList;
import java.util.List;

public class BigObject {
    private List<Object> objects;

    public BigObject() {
        this.objects = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            objects.add("Hello" + i);
        }
    }

    public String hello() {
        return "Hello from BigObject";
    }

    public class SmallObject {
        public String hello() {
            return "Hello from SmallObject";
        }
    }
}
