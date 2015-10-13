set "operator.kafka.brokers"="127.0.0.1:9092";
set "operator.kafka.zookeepers"="127.0.0.1:2181";
set "operator.kafka.zksessiontimeout"="20000";
set "operator.kafka.zksynctime"="20000";
set "operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder";
set "serde.simpleserde.separator" = ",";

create input stream S1 
(id int ,name String,type int)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="join_1");

create input stream S2 
(id int ,name String,type int)
 SERDE SimpleSerDe
 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="join_2");

create output stream rs 
(id int ,name String,type int,id2 int ,name2 String,type2 int) 
 SERDE SimpleSerDe
 SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
 PROPERTIES ("operator.kafka.topic"="join_3");


insert into stream rs select * from (select * from s1)[range 20 seconds batch] as s3 inner join (select * from s2)[range unbounded] as s4 on s3.id=s4.type where s3.id > 5;	
submit application jointest;