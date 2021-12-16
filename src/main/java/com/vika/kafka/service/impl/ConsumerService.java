package com.vika.kafka.service.impl;

import com.vika.kafka.config.KafkaConsumerConfig;
import com.vika.kafka.dto.TableDto;
import com.vika.kafka.entity.TableEn2;
import com.vika.kafka.repo.TableRepo2;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;


@Service
@RequiredArgsConstructor
public class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    private TableRepo2 tableRepo2;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private KafkaConsumerConfig kafkaConsumerConfig;

    @Transactional
    public void takeTable() {

        final Consumer<String, TableDto> consumer = kafkaConsumerConfig.createKafkaConsumer();

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
    }
}



