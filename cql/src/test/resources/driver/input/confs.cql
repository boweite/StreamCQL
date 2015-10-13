set "operator.kafka.brokers"="158.1.130.12:9092";

set 'operator.kafka.zookeepers' = 'localhost:2181,158.1.130.21:2181';
set 'operator.kafka.zksessiontimeout'='20000';
set 'operator.kafka.zksynctime'='20000';
set 'operator.kafka.messageserializerclass'='kafka.serializer.StringEncoder';
set "serde.simpleserde.separator"=',';
set "a"="1";
set "b"="1.0";
set "c"="1.0";
set "x"="true";

create input stream S 
(id int ,name String,type int)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="agg_1_1");
							
create output stream rs 
	(id string,name string,type int,cc int)
 SERDE SimpleSerDe
 SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
 PROPERTIES ("operator.kafka.topic"="agg_1_2");
--最一般的聚合查询。仅仅带了窗口							
insert into stream rs select *,count(id) from S[range 20 seconds batch] where id > 5 group by type;	
explain application confs;