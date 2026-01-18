package com.ycrash.springboot.buggy.app.service.cpuspike;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CPUSpikerThread extends Thread {
	
	private static final Logger log = LoggerFactory.getLogger(CPUSpikerThread.class);

	private volatile boolean running = false;

	@Override
	public void run() {
		running = true;

		log.info("Starting CPU Spike");
		try {
			execute();
		} catch (InterruptedException e) {
			log.error("CPUSpikerThread interrupted", e);
		}
		log.info("CPUSpikerThread {} finished running", Thread.currentThread().getName());
	}


	public void execute() throws InterruptedException {
		while(running) {
			Thread.onSpinWait();
		}
	}


	public void halt() {
		this.running = false;
	}
}
