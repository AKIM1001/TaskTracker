package com.tracker.userservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.tracker.userservice.dto.UserDto;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final ReplyingKafkaTemplate<String, String, UserDto> replyingKafkaTemplate;

    public UserDto getUser(String name) throws ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<String, String> record = new ProducerRecord<>("user-request-topic", null, name);

        RequestReplyFuture<String, String, UserDto> future = replyingKafkaTemplate.sendAndReceive(record);

        ConsumerRecord<String, UserDto> response = future.get(1, TimeUnit.SECONDS);

        return response.value();
    }
}
