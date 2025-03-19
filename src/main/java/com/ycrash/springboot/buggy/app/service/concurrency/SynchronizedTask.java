package com.ycrash.springboot.buggy.app.service.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.locks.LockSupport;

public class SynchronizedTask implements IntTask {

    private static final Logger log = LoggerFactory.getLogger(SynchronizedTask.class);

    private int a;
    private int b;

    public SynchronizedTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Integer call() {

        synchronized (log) {
            log.info("Sleeping...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            LockSupport.parkNanos(2000_000_000);


            log.info("Done.");
        }
        return a + b;
    }



    private static String getTemperature() {
        log.info("GET on meteo");
        try {
            URL url = new URL("https://www.meteo-paris.com/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // The connection is opened on con.connect() or con.getInputStream() or con.getResponseCode()
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

//            log.info("responseCode: " + con.getResponseCode());

            return content.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
