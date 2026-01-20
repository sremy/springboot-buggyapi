package com.ycrash.springboot.buggy.app.service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CustomSortService {
    record Item(int id, String name) {
    }
    Random random = new Random();

    private static final Logger log = LoggerFactory.getLogger(CustomSortService.class);

    public void run() {
        var list = getListOf1KInterger();
        try {
            list.sort((o1, o2) -> {
                int diff = o2.name().length() - o1.name().length();
                return 100 / diff;
            });
        } catch (Exception e) {
            log.error("Exception during sort", e);
        }
    }

    private List<Item> getListOf1KInterger() {
        var list = new ArrayList<Item>();
        for (int i = 0; i < 1000; i++) {
            var item = new Item(random.nextInt(1000), "Item " + i);
            list.add(item);
        }
        return list;
    }
}
