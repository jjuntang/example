package com.example.kafka3;

import com.example.kafka3.kafka.KafkaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Kafka3Application {

	public static void main(String[] args) {


		//AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(KafkaConfig.class);
		//KafkaConfig appConfig = ac.getBean(KafkaConfig.class);

		//System.out.println(appConfig);
		//System.out.println("Kafka3Application : " + ac.getBean(KafkaConfig.class));
		SpringApplication.run(Kafka3Application.class, args);
	}

}
