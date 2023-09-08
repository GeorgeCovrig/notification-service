package com.cool.programmer.websocket.notif.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = "cool_programmer_topic", groupId = "cool_programmer")
    public void consume(String message) {
        String[] messageArray  = message.split(";");
        simpMessagingTemplate.convertAndSend("/topic/greetings/"+ messageArray[1], messageArray[0]);
        System.out.println("message = " + message);
    }
}