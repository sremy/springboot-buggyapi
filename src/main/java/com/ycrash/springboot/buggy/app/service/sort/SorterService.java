package com.ycrash.springboot.buggy.app.service.sort;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SorterService {

    private static final Logger log = LoggerFactory.getLogger(SorterService.class);

    public void sortBigList() {
        var list = generateOrGetBigList();
        var comp = new MyComp();
        log.info("Sorting big list");
        var started = Stopwatch.createStarted();
        list.sort(comp);
        log.info("Sorted big list in {} ms", started.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    static class MyComp implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            return Integer.compare(
                    Integer.parseInt(a.substring(3)),
                    Integer.parseInt(b.substring(3))
            );
        }
    }


    private static List<String> generateOrGetBigList() {
        try {
            Path path = Paths.get("biglist.txt");
            if (Files.exists(path)) {
                return Files.readAllLines(path);
            }
            var random = new Random();
            var list = new ArrayList<String>();
            for (int i = 0; i < 10_000_000; i++) {
                list.add("Day" + random.nextInt(0, 100_000_000));
            }
            Files.write(path, list);
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
