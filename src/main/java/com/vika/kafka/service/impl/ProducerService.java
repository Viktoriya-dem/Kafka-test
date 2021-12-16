package com.vika.kafka.service.impl;

import com.vika.kafka.config.KafkaProducerConfig;
import com.vika.kafka.dto.TableDto;
import com.vika.kafka.entity.TableEn1;
import com.vika.kafka.repo.TableRepo1;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    @Value(value = "${tpd.topicName}")
    private String topicName;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TableRepo1 tableRepo1;

    @Autowired
    private KafkaProducerConfig kafkaProducerConfig;


    @Transactional
    public void sendTable() {
        KafkaProducer<String, TableDto> producer = kafkaProducerConfig.createKafkaProducer();
        for (TableEn1 tableEn : tableRepo1.findAll()) {
            TableDto tableDto = modelMapper.map(tableEn, TableDto.class);
            logger.info(String.format("#### -> Producing data -> %s", tableDto));
            ProducerRecord<String, TableDto> producerRecord = new ProducerRecord<String, TableDto>(topicName, tableDto.getId().toString(), tableDto);
            producer.send(producerRecord);
        }
        producer.close();
    }

}
