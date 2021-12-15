package com.vika.kafka.service.impl;
import com.vika.kafka.dto.TableDto;
import com.vika.kafka.entity.TableEn2;
import com.vika.kafka.repo.TableRepo2;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    private TableRepo2 tableRepo2;
    @Autowired
    private ModelMapper modelMapper;

    private static final String TOPIC = "data1";

    @Transactional
    public void takeTable() {

        final Consumer<String, TableDto> consumer = createKafkaConsumer();

        final int giveUp = 1000;
        int noRecordsCount = 0;

        while (true) {

            final ConsumerRecords<String, TableDto> consumerRecords =
                    consumer.poll(Duration.ofMillis(50));
            if (consumerRecords.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }

            for (ConsumerRecord<String, TableDto> record : consumerRecords) {
                logger.info(String.format("#### -> Consumer data -> %s", record.value()));
                TableEn2 tableEn2 = modelMapper.map(record.value(), TableEn2.class);
                tableRepo2.save(tableEn2);
            }

        }
        consumer.close();
        System.out.println("DONE");
    }

    private static Consumer<String, TableDto> createKafkaConsumer() {

        String server = "localhost:9092";
        String topicName = "data1";
        String groupName = "group40";
        final Properties props = new Properties();

        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                groupName);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                server);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, TableDto.class);


        final Consumer<String, TableDto> consumer =
                new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topicName));
        return consumer;
    }
}



