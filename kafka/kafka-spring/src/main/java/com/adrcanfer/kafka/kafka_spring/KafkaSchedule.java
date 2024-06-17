package com.adrcanfer.kafka.kafka_spring;

import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;

@Component
public class KafkaSchedule {
	
	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	private MeterRegistry meterRegistry;
	
	@Value("${kafka.topic}")
	private String topic;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private Integer count = 1;
	
	@Scheduled(fixedDelay = 5000, initialDelay = 1000)
	public void sendKafkaMessages() {
		for(int i = count; i < count + 100; i++) {
			CompletableFuture<SendResult<String, String>> res = kafkaTemplate.send(topic, "Sample key " + i,  "Sample message " + i);
			res.thenRunAsync(() -> LOGGER.info("Se envió el mensaje"));
		}
		count += 100;
		
		printMetrics();
	}
	
	public void printMetrics() {
		double count = meterRegistry.get("kafka.producer.record.send.total").functionCounter().count();
		LOGGER.warn("Count {}", count);
	}

}
