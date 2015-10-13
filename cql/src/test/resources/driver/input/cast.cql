set "operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder";
set "operator.kafka.zookeepers"="localhost:2181,158.1.130.21:2181";
set "operator.kafka.brokers"="158.1.130.12:9092";
set "operator.kafka.zksessiontimeout"="20000";
set "operator.kafka.zksynctime"="20000";
set "serde.simpleserde.separator" = ",";

create input stream S 
(id string )
 SERDE SimpleSerDe
 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="cast_1");
							
create output stream rs 
(idi int,idl long,idf float,idd double,ids string,idb boolean)
 SERDE SimpleSerDe
 SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
 PROPERTIES ("operator.kafka.topic"="cast_2");


insert into stream rs select cast(id as int),cast(id as long),cast(id as float),cast(id as double),cast(id as String),cast(id as boolean) from s;
explain application having1;