package com.tracker.airecommendationservice.config;

import com.tracker.airecommendationservice.dto.UserDto;
import com.tracker.airecommendationservice.event.HabitDeletedEvent;
import com.tracker.airecommendationservice.event.HabitSentEvent;
import com.tracker.airecommendationservice.event.PrimeSubscriptionEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public <T> Map<String, Object> consumerConfig(ErrorHandlingDeserializer<T> deserializer) {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return props;
    }

    private <T> ConsumerFactory<String, T> consumerFactory(Class<T> clazz) {
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(clazz);

        deserializer.addTrustedPackages("*");

        deserializer.setUseTypeMapperForKey(false);

        ErrorHandlingDeserializer<T> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(deserializer);

        return new DefaultKafkaConsumerFactory<>(consumerConfig(errorHandlingDeserializer),
                new StringDeserializer(), errorHandlingDeserializer);
    }

    private <T> KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, T>> kafkaListenerContainerFactory(ConsumerFactory<String, T> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }

    @Bean
    public ConsumerFactory<String, HabitSentEvent> habitDtoConsumerFactory() {
        return consumerFactory(HabitSentEvent.class);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, HabitSentEvent>> kafkaListenerContainerFactoryHabitSentEvent(ConsumerFactory<String, HabitSentEvent> consumerFactory) {
        return kafkaListenerContainerFactory(consumerFactory);
    }

    @Bean
    public ConsumerFactory<String, HabitDeletedEvent> habitDeletedEventConsumerFactory() {
        return consumerFactory(HabitDeletedEvent.class);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, HabitDeletedEvent>> kafkaListenerContainerFactoryHabitDeletedEvent(ConsumerFactory<String, HabitDeletedEvent> consumerFactory) {
        return kafkaListenerContainerFactory(consumerFactory);
    }

    @Bean
    public ConsumerFactory<String, UserDto> userDtoConsumerFactory() {
        return consumerFactory(UserDto.class);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UserDto>> kafkaListenerContainerFactoryUserDto(ConsumerFactory<String, UserDto> consumerFactory) {
        return kafkaListenerContainerFactory(consumerFactory);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PrimeSubscriptionEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PrimeSubscriptionEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PrimeSubscriptionEvent> consumerFactory() {
        JsonDeserializer<PrimeSubscriptionEvent> deserializer = new JsonDeserializer<>(PrimeSubscriptionEvent.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "habit-service-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }
}
