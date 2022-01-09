package com.example.kafka3;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consume {
    //@KafkaListener(topics = "test", groupId = "csms-biz-group")
    @KafkaListener(topics = "test", containerFactory = "MessageListenerContainerFactory")
    public void consume(String message) throws IOException {
        System.out.println(message);
    }
}