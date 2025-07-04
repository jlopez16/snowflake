package org.recluit.kafkaconsumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.recluit.kafkaconsumer.model.CustomerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    private final KafkaConsumerProperties properties;
    private final String bootstrapServers;

    public KafkaConsumerConfig(KafkaConsumerProperties properties, 
                              @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        this.properties = properties;
        this.bootstrapServers = bootstrapServers;
    }

    @Bean
    public ConsumerFactory<String, CustomerDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "org.recluit.kafkaconsumer.model.CustomerDto");

        return new DefaultKafkaConsumerFactory<>(props);
    }
}