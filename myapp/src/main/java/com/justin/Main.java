package com.justin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    // Initialize the Log4j2 Logger directly
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        // Create a single-threaded scheduler
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        logger.info("Log4j2 Interval logger application started.");

        // Define the task to be executed
        Runnable logTask = () -> {
            try {
                logger.info("Periodic status log: Application is running normally.");
            } catch (Exception e) {
                logger.error("An error occurred during scheduled logging", e);
            }
        };

        // Schedule the task: initial delay = 0, repeat period = 5, time unit = MINUTES
        scheduler.scheduleAtFixedRate(logTask, 0, 2, TimeUnit.SECONDS);

        // Graceful shutdown handling
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down scheduler...");
            scheduler.shutdown();
        }));
    }
}