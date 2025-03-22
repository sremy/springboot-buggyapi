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

    static class MyComp implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            // Skip the "Day" prefix
            int aStartIndex = 3;
            int bStartIndex = 3;

            // Get lengths of the numeric parts
            int aLength = a.length() - aStartIndex;
            int bLength = b.length() - bStartIndex;

            // Different lengths - longer number is larger
            if (aLength != bLength) {
                return aLength - bLength;
            }

            // Same length - compare digit by digit
            for (int i = 0; i < aLength; i++) {
                char aDigit = a.charAt(aStartIndex + i);
                char bDigit = b.charAt(bStartIndex + i);

                if (aDigit != bDigit) {
                    return aDigit - bDigit;
                }
            }

            // Numbers are identical
            return 0;
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
