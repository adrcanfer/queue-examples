package com.adrcanfer.kafka;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.adrcanfer.kafka.consumers.MyKafkaConsumer;
import com.adrcanfer.kafka.producers.MyKafkaProducer;

public class Main {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

        EXECUTION: while (true) {
            System.out.println("What do you want to do:");
            System.out.println("1. Run Kafka Consumer");
            System.out.println("2. Run Multithread Kafka Consumer");
            System.out.println("3. Run Kafka Producer");
            System.out.println("Enter 1, 2 or 3 (or 'exit' to quit):");

            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                LOGGER.info("Exiting the application.");
                break;
            }

            switch (input) {
                case "1":
                    LOGGER.info("Running Kafka Consumer");
                    MyKafkaConsumer.runConsumer();
                    break;
                case "2":
                    LOGGER.info("Running Multithread Kafka Consumer");
                    MyKafkaConsumer.runMultiThreadConsumer();
                    break;
                case "3":
                    LOGGER.info("Running Kafka Producer");
                    MyKafkaProducer.produceMessages(1000);
                    break EXECUTION;
                default:
                    LOGGER.warn("Invalid input, please enter 1, 2, or 3.");
                    break;
            }
        }

        scanner.close();
	}

}
