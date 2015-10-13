set "operator.kafka.brokers"="158.1.130.12:9092";

set "operator.kafka.zookeepers"="158.1.130.12:2181";
set "operator.kafka.zksessiontimeout"="20000";
set "operator.kafka.zksynctime"="20000";
set "operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder";
set "serde.simpleserde.separator"=",";

create input stream S1 
(id int ,name String,type int)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "chineline_1_1","operator.kafka.topic"="join_1");

create input stream S2 
(id int ,name String,type int)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "chineline_1_2","operator.kafka.topic"="join_2");

create stream s3
(id int,name int,type int,nullcol string);

create input stream S4
(id int ,name String,type int)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "chineline_1_3","operator.kafka.topic"="join_2");

create output stream rs 
(id string ,name String,type string,id2 string ,name2 String,type2 string,c1 string) 
     SERDE SimpleSerDe
     SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
     PROPERTIES ("operator.kafka.topic"="chineline_1_4");
				
insert into s3 select *,null from S1;
insert into s3 select *,1 from S2;

insert into stream rs select * from S4[range 20 seconds batch] inner join S3[range unbounded] on s4.id=s3.type where s4.id > 5;	

explain application jointest;