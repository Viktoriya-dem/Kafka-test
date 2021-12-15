package com.vika.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vika.kafka.dto.TableDto;
import com.vika.kafka.service.impl.ConsumerService;
import com.vika.kafka.service.impl.ProducerService;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

import static java.lang.System.exit;


@EnableKafka
@SpringBootApplication
public class TestApplication implements CommandLineRunner {

    public static void main(String[] args) {

    	SpringApplication.run(TestApplication.class, args);
    	
    }

    @Autowired
	private ProducerService producerService;

	@Autowired
	private ConsumerService consumerService;

	@Override
	public void run(String... args) {
		if (args[0].equals("1")) {
			producerService.sendTable();
		} if (args[0].equals("2")) {
		consumerService.takeTable();
		}
		exit(0);
	}

}
