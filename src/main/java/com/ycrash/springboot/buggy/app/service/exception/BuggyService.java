package com.ycrash.springboot.buggy.app.service.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service
@Log4j2
public class BuggyService {
    record Item(int id, String name) {
    }
    Random random = new Random();

    public void run() {
        var list = getListOf1KInterger();
        try {
            list.sort((o1, o2) -> {
                int diff = o2.name().length() - o1.name().length();
                return 100 / diff;
            });
        } catch (Exception e) {
            log.error(e);
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
