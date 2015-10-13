set "operator.kafka.brokers"="158.1.130.12:9092";

create input stream input_where_length_slide 
	(O_Id int ,OrderDate String ,OrderPrice long ,Customer String)
	 SERDE SimpleSerDe
		 PROPERTIES ("serde.simpleserde.separator" = ",")
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="where_length_slide_in",
						"operator.kafka.zookeepers"="158.1.130.12:2181",
						"operator.kafka.zksessiontimeout"="20000","operator.kafka.zksynctime"="20000",
							"operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder");
							
create output stream output_where_length_slide 
	--(Customer String ,sum_orderprice long)
	(sumprice long ,avgprice long ,countprice long)
	 SERDE SimpleSerDe
		 PROPERTIES ("serde.simpleserde.separator" = ",")
	 SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
		 PROPERTIES ("operator.kafka.topic"="where_length_slide_out","operator.kafka.zksessiontimeout"="20000",	"operator.kafka.zookeepers"="158.1.130.12:2181",
						"operator.kafka.zksynctime"="20000","operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder");
						
insert into stream output_where_length_slide select sum(OrderPrice),avg(OrderPrice),count(OrderPrice) from input_where_length_slide[rows 3 slide] where OrderPrice=100 or OrderPrice>=1000;	
explain application app_where_length_slide;