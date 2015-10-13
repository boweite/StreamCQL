
set 'operator.kafka.zookeepers' = '158.1.130.10:24002';
set "serde.simpleserde.separator"=',';
set "operator.kafka.brokers"="127.0.0.1:9092";

Create Input Stream Input_Case_When
	(id decimal)
	 Source  KafkaINput
		 Properties ("Operator.Kafka.Groupid" = "Gidkpi_1_1","Operator.Kafka.Topic"="Case_When_In");

Create Output Stream Output_Case_When
	(id decimal,suma decimal,sumf decimal,avga decimal,avgf decimal)
	 Sink consoleOutput;

Insert Into Stream Output_Case_When Select id,sum(id),sum(id,id > 10),avg(id),avg(id,id > 10) From Input_Case_When[range unbounded] group by id;
explain Application Previous1;
