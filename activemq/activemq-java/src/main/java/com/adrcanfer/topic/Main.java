package com.adrcanfer.topic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.adrcanfer.topic.publisher.Publisher;
import com.adrcanfer.topic.consumer.Consumer;

public class Main {
	
	private static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		
		LOGGER.info("START CONSUMER 1");
		Consumer.startConsumer(1);
		
		LOGGER.info("START CONSUMER 2");
		Consumer.startConsumer(2);

		LOGGER.info("START CONSUMER 3");
		Consumer.startConsumer(3);


		
		LOGGER.info("PRODUCE MESSAGE 1");
		Publisher.publishMessage("Message 1");
		
		LOGGER.info("PRODUCE MESSAGE 2");
		Publisher.publishMessage("Message 2");
		
		LOGGER.info("PRODUCE MESSAGE 3");
		Publisher.publishMessage("Message 3");
		
		LOGGER.info("PRODUCE MESSAGE 4");
		Publisher.publishMessage("Message 4");

	}

}
