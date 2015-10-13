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
		 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="agg_4_1");
							
	create output stream rs 
		(id int,name string,type int,cc int)
	 SERDE SimpleSerDe
	 SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
		 PROPERTIES ("operator.kafka.topic"="agg_4_2");
	-- 聚合查询测试，having中的列并没有出现在select的子句中
	insert into stream rs select *,count(id) from S[range 20 seconds batch] where id > 5 group by s.type having sum(id)>10;	
	submit application simple;