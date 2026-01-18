package com.ycrash.springboot.buggy.app.service.cpuspike;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CPUSpikeDemoService {

	private static final Logger log = LoggerFactory.getLogger(CPUSpikeDemoService.class);
	private static final short THREAD_GROUP_SIZE = 4;

	private final List<CPUSpikerThread> cpuSpikerThreads = Collections.synchronizedList(new ArrayList<>());

	/**
	 * Create 4 more threads
	 */
	public void start() {
		for (int i = 0; i < THREAD_GROUP_SIZE; i++) {
			CPUSpikerThread cpuSpikerThread = new CPUSpikerThread();
			cpuSpikerThreads.add(cpuSpikerThread);
			cpuSpikerThread.start();
		}

		log.info("Started {} new threads, now {} threads running", THREAD_GROUP_SIZE, cpuSpikerThreads.size());
	}

	/**
	 * Stop all threads
	 */
	public void stop() {
		log.info("Asked to stop all {} threads", cpuSpikerThreads.size());
        for (int i = cpuSpikerThreads.size() - 1; i >= 0; i--) {
            CPUSpikerThread cpuSpikerThread = cpuSpikerThreads.get(i);
            cpuSpikerThread.halt();
            cpuSpikerThreads.remove(i);
        }
	}
}
