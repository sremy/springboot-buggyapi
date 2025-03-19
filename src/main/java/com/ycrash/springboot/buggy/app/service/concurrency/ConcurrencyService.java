package com.ycrash.springboot.buggy.app.service.concurrency;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ConcurrencyService {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyService.class);

    ExecutorService executorService = Executors.newFixedThreadPool(5);


    public List<Integer> start() {
        log.info("ConcurrencyService started");

        IntTask myTask1 = new MyTask(2, 3);
        IntTask myTask2 = new MyTask(10, 80);
        IntTask myTask3 = new MyTask(1, 2);
        IntTask myTask4 = new MyTask(20, 80);
        IntTask myTask5 = new MyTask(20, 80);
        IntTask myTask6 = new MyTask(20, 80);
        ArrayList<IntTask> taksList = Lists.newArrayList(myTask1, myTask2, myTask3, myTask4, myTask5, myTask6);

        try {
            List<Future<Integer>> futureList = executorService.invokeAll(taksList);
            List<Integer> resultList = new ArrayList<>();

            for (Future<Integer> future : futureList) {
                Integer result = future.get();
                resultList.add(result);
            }

            log.info("Result = " + resultList);


//            executorService.submit(myTask1).get();

            return resultList;

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

}
