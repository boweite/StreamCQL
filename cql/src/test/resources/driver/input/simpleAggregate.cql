set "operator.kafka.brokers"="158.1.130.12:9092";
set "topic"="158.1.130.12:9092";

create input stream S 
(id int ,name String,type int)
	 SERDE SimpleSerDe
		 PROPERTIES (separator = "&")
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES (groupId = "gidkpi_1_1",topic = "agg_1_1",
						zookeepers = "localhost:2181,158.1.130.21:2181",
						zkSessionTimeout = "20000",zkSyncTime = "20000",
							messageSerializerClass = "kafka.serializer.StringEncoder");
							
	create output stream rs 
		(id string,name string,type int,cc int)
	 SERDE SimpleSerDe
		 PROPERTIES (separator = "|")
	 SINK KafkaOutput
		 PROPERTIES (topic = "agg_1_2",zkSessionTimeout = "20000", zookeepers = "158.1.130.12:2181",
						zkSyncTime = "20000",messageSerializerClass = "kafka.serializer.StringEncoder");
	--最一般的聚合查询。仅仅带了窗口							
	insert into stream rs select *,count(id) from S[range 20 seconds batch] where id > 5 group by type;	
	explain application simple;