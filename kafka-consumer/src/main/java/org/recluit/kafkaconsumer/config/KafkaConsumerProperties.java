package org.recluit.kafkaconsumer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaConsumerProperties {
    private String topic;
    private String groupId;
}
