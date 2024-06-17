package com.adrcanfer.activemq;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adrcanfer.activemq.queue.ProducerService;

@Component
public class Main implements CommandLineRunner {

	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	private ProducerService producerService;
	
	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("INI APP");
		for(int i = 1; i <= 20; i++) {
			User u = new User();
			u.setName("Name " + i);
			u.setEmail("Name" + i + "@mail.es");
			u.setLastUpdate(LocalDateTime.now());
			producerService.sendToQueue(u);
		}
	}

}
