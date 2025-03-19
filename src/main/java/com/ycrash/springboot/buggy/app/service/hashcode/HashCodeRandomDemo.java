package com.ycrash.springboot.buggy.app.service.hashcode;

import java.util.*;

public class HashCodeRandomDemo {

    // Custom class with a bad hashCode implementation
    static class BadHashCodeObject {
        private final String value;

        public BadHashCodeObject(String value) {
            this.value = value;
        }

        // Always returns the same hash code
        @Override
        public int hashCode() {
            return 42; // Terrible hash code implementation
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            BadHashCodeObject that = (BadHashCodeObject) obj;
            return value.equals(that.value);
        }
    }

    // Custom class with a good hashCode implementation
    static class GoodHashCodeObject {
        private final String value;

        public GoodHashCodeObject(String value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return value.hashCode(); // Default good implementation
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            GoodHashCodeObject that = (GoodHashCodeObject) obj;
            return value.equals(that.value);
        }
    }

    public static void main(String[] args) {
        // Number of elements to test
        int[] testSizes = {1_000, 10_000, 20_000};

        for (int size : testSizes) {
            System.out.println("\nTesting with " + size + " elements:");

            // Test with good hashCode implementation
            long goodHashTime = testHashMapPerformance(size, false);
            System.out.printf("Good HashCode Time: %d ms%n", goodHashTime);

            // Test with bad hashCode implementation
            long badHashTime = testHashMapPerformance(size, true);
            System.out.printf("Bad HashCode Time:  %d ms%n", badHashTime);

            System.out.printf("Performance Difference: %.2fx slower%n",
                    (double) badHashTime / goodHashTime);
        }
    }

    private static long testHashMapPerformance(int testSize, boolean useBadHashCode) {
        Map<Object, Integer> map = new HashMap<>(testSize);
        Random random = new Random();
        List<String> strings = new ArrayList<>();

        // Populate the map
        for (int i = 0; i < testSize; i++) {
            String randomString = generateRandomString(10, random);
            strings.add(randomString);

            if (useBadHashCode) {
                map.put(new BadHashCodeObject(randomString), i);
            } else {
                map.put(new GoodHashCodeObject(randomString), i);
            }
        }

        // Measure lookup time
        long startTime = System.currentTimeMillis();

        // Perform multiple lookups to get a meaningful time measurement
        for (String searchedString : strings) {
            if (useBadHashCode) {
                map.containsKey(new BadHashCodeObject(searchedString));
            } else {
                map.containsKey(new GoodHashCodeObject(searchedString));
            }
        }

        return System.currentTimeMillis() - startTime;
    }

    // Helper method to generate random strings
    private static String generateRandomString(int length, Random random) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}