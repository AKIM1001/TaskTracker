package com.tracker.userservice.config;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import com.tracker.userservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;



import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, UserDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "user_service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "org.examle.userservice.dto.UserDTO");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "org.example.*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ReplyingKafkaTemplate<String, UserDto, UserDto> replyingKafkaTemplate(
            ProducerFactory<String, UserDto> producerFactory,
            ConcurrentMessageListenerContainer<String, UserDto> container
    ) {
        return new ReplyingKafkaTemplate<>(producerFactory, container);
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, UserDto> container(
            ConsumerFactory<String, UserDto> cf
    ) {
        ContainerProperties containerProperties =
                new ContainerProperties("user-response-topic");

        return new ConcurrentMessageListenerContainer<>(cf, containerProperties);
    }

}
