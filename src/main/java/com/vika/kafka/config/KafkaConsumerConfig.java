package com.vika.kafka.config;

import com.vika.kafka.dto.TableDto;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Arrays;
import java.util.Properties;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${tpd.topicName}")
    private String topicName;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupName;

    @Value(value = "${kafka.bootstrap-servers}")
    private String server;

    public KafkaConsumerConfig() {
    }

    @Bean
    public Consumer<String, TableDto> createKafkaConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupName);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, TableDto.class);

        final Consumer<String, TableDto> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topicName));
        return consumer;
    }
}
