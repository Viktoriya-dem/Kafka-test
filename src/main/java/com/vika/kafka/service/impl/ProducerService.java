package com.vika.kafka.service.impl;

import com.vika.kafka.dto.TableDto;
import com.vika.kafka.entity.TableEn1;
import com.vika.kafka.repo.TableRepo1;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Properties;




@Service
@RequiredArgsConstructor
public class ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
    private static final String TOPIC = "data1";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TableRepo1 tableRepo1;

    private static KafkaProducer<String, TableDto> createKafkaProducer() {
        String bootstrapServers = "localhost:9092";
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return new KafkaProducer<>(properties);
    }


    @Transactional
    public void sendTable() {
        KafkaProducer<String, TableDto> producer = createKafkaProducer();
        for (TableEn1 tableEn : tableRepo1.findAll()) {
            TableDto tableDto = modelMapper.map(tableEn, TableDto.class);
            logger.info(String.format("#### -> Producing data -> %s", tableDto));
            ProducerRecord<String, TableDto> producerRecord = new ProducerRecord<String, TableDto>(TOPIC, tableDto.getId().toString(), tableDto);
            producer.send(producerRecord);
        }
        producer.close();
    }

}
