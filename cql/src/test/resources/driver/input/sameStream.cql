set 'operator.kafka.zookeepers' = 'localhost:2181,158.1.130.12:2181';
set 'operator.kafka.zksessiontimeout'='20000';
set 'operator.kafka.zksynctime'='20000';
set 'operator.kafka.messageserializerclass'='kafka.serializer.StringEncoder';
set "serde.simpleserde.separator"=',';
set "operator.kafka.brokers"="158.1.130.12:9092";


create input stream S1 
(id int ,name String,type int)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "chineline_1_1","operator.kafka.topic"="join_1");

create stream s3
(id int,name int,type int,nullcol string);

create output stream rs 
(id int,name String,type int) 
  SERDE SimpleSerDe
  SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
  PROPERTIES ("operator.kafka.topic"="chineline_1_4");

insert into s3 select *,null from S1;
insert into rs select * from S1;
insert into rs select id,name,type from S3;

explain application sameStream;