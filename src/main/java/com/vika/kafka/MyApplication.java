package com.vika.kafka;

import com.vika.kafka.service.impl.ConsumerService;
import com.vika.kafka.service.impl.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

import static java.lang.System.exit;


@EnableKafka
@SpringBootApplication
public class MyApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(MyApplication.class, args);

    }

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ConsumerService consumerService;

    @Override
    public void run(String... args) {
        if (args[0].equals("1")) {
            producerService.sendTable();
        }
        if (args[0].equals("2")) {
            consumerService.takeTable();
        }
        exit(0);
    }

}
