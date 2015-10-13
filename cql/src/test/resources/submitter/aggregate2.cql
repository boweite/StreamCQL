set "operator.kafka.brokers"="127.0.0.1:9092";
set "operator.kafka.zookeepers"="127.0.0.1:2181";
set "operator.kafka.zksessiontimeout"="20000";
set "operator.kafka.zksynctime"="20000";
set "operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder";
set "serde.simpleserde.separator" = ",";

create input stream S 
(id int ,name String,type int)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="agg_2_1");
							
	create output stream rs 
		(id string,name string,type int,cc int)
	 SERDE SimpleSerDe
	 SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
		 PROPERTIES ("operator.kafka.topic"="agg_2_2");
	-- 带了having的聚合查询，having发生在聚合之后 但是having的列，是再outputschema中已经定义好的						
	insert into stream rs select *,count(id) as cc from S[range 20 seconds batch] where id > 5 group by s.type having cc>10;	
	submit application simple;