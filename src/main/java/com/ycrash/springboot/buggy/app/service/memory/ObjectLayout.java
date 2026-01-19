package com.ycrash.springboot.buggy.app.service.memory;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.ArrayList;
import java.util.List;

public class ObjectLayout {

    public static void main(String[] args) throws InterruptedException {

        int n = 10;
        if (args.length > 0) {
            try { n = Integer.parseInt(args[0]); } catch (Exception e) { /* ignore */ }
        }

        // Construire une ArrayList<Long> remplie de Long objets
        List<Long> longList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            longList.add(Long.valueOf(i));
        }

        // Construire un tableau primitif long[]
        long[] primArray = new long[n];
        for (int i = 0; i < n; i++) {
            primArray[i] = i;
        }

        // Suggérer GC pour réduire le bruit
        System.gc();
        Thread.sleep(200);

        System.out.println("=== ClassLayout pour ArrayList<Long> ===");
        System.out.println(ClassLayout.parseInstance(longList).toPrintable() + "\n");

        System.out.println("=== GraphLayout (retained sizes) pour ArrayList<Long> ===");
        System.out.println(GraphLayout.parseInstance(longList).toPrintable());

        System.out.println();

        System.out.println("=== ClassLayout pour long[] ===");
        System.out.println(ClassLayout.parseInstance(primArray).toPrintable());
        System.out.println("=== GraphLayout (retained sizes) pour long[] ===");
        System.out.println(GraphLayout.parseInstance(primArray).toPrintable());

    }


}
