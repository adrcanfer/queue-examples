# Kafka Examples
The purpose of this repository is to have a full example of Kafka with Spring Boot. 
Kafka will be running in a container.

## How to run Kafka
To create a Kafka container is necessary to execute the following command:
```docker run -p -d --name kafka 9092:9092 apache/kafka:3.7.0```

## Kafka CMD
All commands will be executed in the path /opt/kafka/bin inside de container.

### Create a topic
```./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic <TOPIC NAME> --partitions <PARTITIONS NUMBER> --replication-factor <REPLICATION NUMBER>```
```./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic emails --partitions 3 --replication-factor 1```

### List topics
```./kafka-topics.sh --list --bootstrap-server localhost:9092```

### Description of a topic
```./kafka-topics.sh --describe --topic <TOPIC NAME> --bootstrap-server localhost:9092```
```./kafka-topics.sh --describe --topic emails --bootstrap-server localhost:9092```

### Update a topic
```./kafka-topics.sh --bootstrap-server localhost:9092 --alter --topic <TOPIC NAME> --partitions <PARTITIONS NUMBER>```
```./kafka-topics.sh --bootstrap-server localhost:9092 --alter --topic emails --partitions 5```

### Delete a topic
```./kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic <TOPIC NAME>```
```./kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic emails```

### Create a producer
```./kafka-console-producer.sh --topic <TOPIC NAME> --bootstrap-server localhost:9092```
```./kafka-console-producer.sh --topic emails --bootstrap-server localhost:9092```

### Create a consumer
```./kafka-console-consumer.sh --topic <TOPIC NAME> --bootstrap-server localhost:9092```
```./kafka-console-consumer.sh --topic emails --bootstrap-server localhost:9092```
Optional parameters:
- from-beginning: Read all messages
- property: Allow to set properties
  - print.key=true
  - key.separator="-"
  
## Examples
1. Kafka with Java Maven Project
2. Kafka with Spring Boot
  
## Documentation
- [Kafka Doc](https://kafka.apache.org/documentation/)