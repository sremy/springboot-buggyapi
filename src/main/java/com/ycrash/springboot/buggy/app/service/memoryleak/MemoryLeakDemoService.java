package com.ycrash.springboot.buggy.app.service.memoryleak;

import com.google.common.base.Stopwatch;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Ram Lakshmanan
 */

@Slf4j
@Service
public class MemoryLeakDemoService {

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

    public void sortBigList() {
        var list = generateOrGetBigList();
        var comp = new MyComp();
        log.info("Sorting big list");
        var started = Stopwatch.createStarted();
        list.sort(comp);
        log.info("Sorted big list in {}", started.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    private static class MyComp implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            return Integer.compare(
                    Integer.parseInt(a.substring(3)),
                    Integer.parseInt(b.substring(3))
            );
        }
    }

    @SneakyThrows
    private static List<String> generateOrGetBigList() {
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
    }
}
