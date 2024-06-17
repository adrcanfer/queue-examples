package com.adrcanfer.kafka.kafka_spring;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
public class Main implements CommandLineRunner {

private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${kafka.topic}")
	private String topic;

	@KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.consumerGroup}")
	public void listen(ConsumerRecord<String, String> message) {
		LOGGER.info("Message received: Partition = {}, Offset = {}, Key = {}, Message = {}", message.partition(), message.offset(), message.key(), message.value());
	}

	@Override
	public void run(String... args) throws Exception {
			CompletableFuture<SendResult<String, String>> res = kafkaTemplate.send(topic, "Sample key",  "Sample message");
			res.thenRunAsync(() -> LOGGER.info("Se envió el mensaje"));
	}
}
