set "operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder";
set "operator.kafka.zookeepers"="localhost:2181,158.1.130.21:2181";
set "operator.kafka.brokers"="158.1.130.12:9092";
set "operator.kafka.zksessiontimeout"="20000";
set "operator.kafka.zksynctime"="20000";
set "serde.simpleserde.separator" = ",";
set "operator.kafka.brokers"="158.1.130.12:9092";

create input stream S 
(id string,type int )
 SERDE SimpleSerDe
 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="cast_1");
							
create output stream rs 
(c1 int,c2 int,c3 int)
 SERDE SimpleSerDe
 SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
 PROPERTIES ("operator.kafka.topic"="cast_2");


insert into stream rs select count(*),count(1),count(id) from s[rows 10 batch] group by type;
explain application having1;