package com.ycrash.springboot.buggy.app.service.concurrency;

import java.util.concurrent.Callable;

public interface IntTask extends Callable<Integer> {
    @Override
    Integer call() throws Exception;
}
