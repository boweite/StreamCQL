set "operator.kafka.brokers"="158.1.130.12:9092";

create input stream input_group_by 
	(O_Id int ,OrderDate String ,OrderPrice long ,Customer String)
	 SERDE SimpleSerDe
		 PROPERTIES ("serde.simpleserde.separator" = ",")
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="group_by_in",
						"operator.kafka.zookeepers"="158.1.130.12:2181",
						"operator.kafka.zksessiontimeout"="20000","operator.kafka.zksynctime"="20000",
							"operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder");
							
create output stream output_group_by 
	(Customer String ,sum_orderprice long)
	 SERDE SimpleSerDe
		 PROPERTIES ("serde.simpleserde.separator" = ",")
	 SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
		 PROPERTIES ("operator.kafka.topic"="group_by_out","operator.kafka.zksessiontimeout"="20000",	"operator.kafka.zookeepers"="158.1.130.12:2181",
						"operator.kafka.zksynctime"="20000","operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder");
						
insert into stream output_group_by select Customer,sum(OrderPrice) from input_group_by[range 10 seconds batch] group by Customer;	
explain application app_group_by;
	