package com.adrcanfer.queue.consumer;

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

	public static void startConsumer() {
		LOGGER.info("INI CONSUMER 1");

		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(Constants.USERNAME, Constants.PASSWORD,
					Constants.BROKER_URL);
			Connection connection = factory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(Constants.QUEUE);

			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(message -> {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						LOGGER.info("CONSUMER 1: Received message: {}", textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void startConsumerManualACK() {
		LOGGER.info("INI CONSUMER 2 (MANUAL ACK)");

		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(Constants.USERNAME, Constants.PASSWORD,
					Constants.BROKER_URL);
			Connection connection = factory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Destination destination = session.createQueue(Constants.QUEUE);

			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(message -> {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						LOGGER.info("CONSUMER 2: Received message: {}", textMessage.getText());
						textMessage.acknowledge();
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
