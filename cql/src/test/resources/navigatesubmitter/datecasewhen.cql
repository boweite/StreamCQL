set "operator.kafka.brokers"="127.0.0.1:9092";
set "operator.kafka.zookeepers"="127.0.0.1:2181";
set "operator.kafka.zksessiontimeout"="20000";
set "operator.kafka.zksynctime"="20000";
set "operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder";
set "serde.simpleserde.separator" = ",";
create input stream S1
(c1 date,c2 String,c3 string,c4 string)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="agg_1_1");
							
	create output stream rs 
		(c string)
	 SERDE SimpleSerDe
	 SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
		 PROPERTIES ("operator.kafka.topic"="agg_1_2");
INSERT INTO STREAM s2 
	SELECT CASE s1.c1 
		WHEN  cast( "1900-01-01" AS DATE )  THEN s1.c2 
		WHEN  cast( "1925-02-01" AS DATE )  THEN s1.c3  ELSE s1.c4 END  FROM s1;
	submit application simple;