package org.recluit.customerapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaProducerProperties {
    private String topic;
    private String groupId;
}
