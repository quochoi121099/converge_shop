package com.studyprogramming.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final List<String> customerList = List.of("customer1@example.com", "customer2@example.com", "customer3@example.com");
    private final AtomicBoolean sending = new AtomicBoolean(false);

    public void sendBulkMessages() {
        sending.set(true);
        new Thread(() -> {
            for (String customer : customerList) {
                if (!sending.get()) {
                    logger.info("Message sending stopped.");
                    break;
                }
                sendMessage(customer);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Error in sleep: ", e);
                }
            }
            sending.set(false);
        }).start();
    }

    public void stopSendingMessages() {
        sending.set(false);
    }

    private void sendMessage(String customer) {
        logger.info("Sending message to {}", customer);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error in sendMessage sleep: ", e);
        }
    }
}

