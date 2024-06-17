package com.adrcanfer.kafka.consumers;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyKafkaConsumer {

	private static final Logger LOGGER = LogManager.getLogger(MyKafkaConsumer.class);
	private static final String KAFKA_TOPIC = "emails";


	public static void runConsumer() {
		Consumer<String, String> consumer = initConsumer();
		consumer.subscribe(Arrays.asList(KAFKA_TOPIC));
		
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records)
				LOGGER.info("offset = {}, key = {}, value ={}", record.offset(), record.key(), record.value());
		}
	}

	public static void runMultiThreadConsumer() {
		ExecutorService executor = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 5; i++) {
			Consumer<String, String> consumer = initConsumer();
			KafkaThreadConsumer threadConsumer = new KafkaThreadConsumer(consumer);
			executor.execute(threadConsumer);
		}

		while (!executor.isTerminated());
	}

	private static Consumer<String, String> initConsumer() {
		LOGGER.info("Initializing consumer");
		
		Consumer<String, String> res = null; 
		try {
			res = new KafkaConsumer<>(getProperties());
		} catch (Exception e) {
			LOGGER.error("Error initializing consumer", e);
		}
		
		return res;
	}

	private static Properties getProperties() {
		Properties props = new Properties();

		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("group.id", "kafka-group");
		props.setProperty("enable.auto.commit", "true");
		props.setProperty("auto.commit.interval.ms", "1000");
		props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		return props;
	}

}
