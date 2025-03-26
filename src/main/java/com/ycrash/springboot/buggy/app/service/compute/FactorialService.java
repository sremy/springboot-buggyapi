package com.ycrash.springboot.buggy.app.service.compute;


import org.springframework.stereotype.Service;

@Service
public class FactorialService {

    public long computeFactorialRecursive(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * computeFactorialRecursive(n - 1);
    }

    public long computeFactorialIterative(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

}