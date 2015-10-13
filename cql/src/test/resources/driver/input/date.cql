set "streaming.storm.submit.islocal" = "true";

set "operator.kafka.brokers" = "158.1.130.12:9092";
set "operator.kafka.zookeepers" = "158.1.130.12:2181";
set "operator.kafka.zksessiontimeout" = "20000";
set "operator.kafka.zksynctime" = "20000";
set "operator.kafka.messageserializerclass" = "kafka.serializer.StringEncoder";
set "serde.simpleserde.separator" = ",";

create input stream S 
(d1 date ,t1 time,ts1 timestamp)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="agg_1_1");
							
create output stream rs 
(d1 date,t1 time,ts1 timestamp)
 SERDE SimpleSerDe
 SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
		 PROPERTIES ("operator.kafka.topic"="agg_1_2");

insert into rs select * from S;
insert into rs select cast('2014-03-04' as date),cast('10:50:00' as time),cast('2014-03-04 10:50:00' as timestamp) from S;
		
explain application simple;