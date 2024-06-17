package com.adrcanfer.topic.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.adrcanfer.utils.Constants;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

public class Consumer {

	private static final Logger LOGGER = LogManager.getLogger(Consumer.class);

	public static void startConsumer(Integer consumerNumber) {
		LOGGER.info("INI CONSUMER {}", consumerNumber);

		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(Constants.USERNAME, Constants.PASSWORD,
					Constants.BROKER_URL);
			Connection connection = factory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic(Constants.TOPIC);

			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(message -> {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						LOGGER.info("CONSUMER {}: Received message: {}", consumerNumber, textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
