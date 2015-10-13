set "operator.kafka.brokers"="158.1.130.12:9092";
set "operator.kafka.zookeepers"="158.1.130.12:2181";
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


insert into stream rs select * from (select * from s1)[range 20 seconds batch] as s1 inner join S2[range unbounded] on s1.id=s2.type where s1.id > 5;	
explain application jointest;