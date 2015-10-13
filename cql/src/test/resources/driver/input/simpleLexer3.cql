set operator.kafka.brokers="158.1.130.12:9092";

create input stream S 
(id int ,name String,type int)
	 SOURCE kafkainput
		 PROPERTIES ( operator.kafka.groupid = "gidkpi_1_1","operator.kafka.topic"="agg_1_1",
						"operator.kafka.zookeepers"="localhost:2181,158.1.130.21:2181",
						"operator.kafka.zksessiontimeout"="20000","operator.kafka.zksynctime"="20000",
							"operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder");
							
	create output stream rs 
		(id string,name string,type int,cc int)
	 SINK kafkaoutput
		 PROPERTIES ("operator.kafka.topic"="agg_1_2","operator.kafka.zksessiontimeout"="20000",	"operator.kafka.zookeepers"="158.1.130.12:2181",
						"operator.kafka.zksynctime"="20000","operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder");
	--最一般的聚合查询。仅仅带了窗口							
	insert into stream rs select *,count(id) from S[range 20 seconds batch] where id > 5 group by type;	
	explain application simple;