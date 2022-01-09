package com.example.kafka3.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {
    @Value(value = "${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value(value = "${kafka.group-id}")
    private String groupId;

    @Value(value = "${kafka.deserializer}")
    private String deserializer;

//    @Bean
//    public KafkaTemplate<String, String> messageKafkaTemplate() {
//        return new KafkaTemplate<>(receivedMessageProducerFactory());
//    }
//
//    //@Bean
//    private ProducerFactory<String, String> receivedMessageProducerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        System.out.println("ssssssssssssssssssssss");
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }

    @Bean
    //@ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> MessageListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        //factory.getContainerProperties().setAckMode(MANUAL);
        //factory.setConcurrency(3);
        //factory.getContainerProperties().setPollTimeout(5000);
        //factory.setRecordFilterStrategy(sendingMessageFilterStrategy());

        factory.setConsumerFactory(MessageConsumerFactory());

        return factory;
    }

    //@Bean
    private ConsumerFactory<String, String> MessageConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        System.out.println("ConsumerFactory : " + this);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // value 가 String 이므로 String 에 대한
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // value 가 String 이므로 String 에 대한
//        if(deserializer.equals("String")){
//            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // value 가 String 이므로 String 에 대한
//            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // value 가 String 이므로 String 에 대한
//        }
//        else if(deserializer.equals("Json")) {
//            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);  // value 가 String 이므로 String 에 대한
//            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class); // value 가 String 이므로 String 에 대한
//        }

        System.out.println(bootstrapServers);
        System.out.println(groupId);
        return new DefaultKafkaConsumerFactory<>(props);
    }
}
