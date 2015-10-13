set "operator.kafka.zookeepers"="127.0.0.1:2181";
set "operator.kafka.zksessiontimeout"="20000";
set "operator.kafka.zksynctime"="20000";
set "operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder";
set "serde.simpleserde.separator"= ",";

create input stream input_join_right_1           
(Id_P int ,LastName String ,FirstName String ,Address String ,City String)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="join_right_in_1");
                                         
create input stream input_join_right_2    
(Id_O int ,OrderNo long ,Id_P int)       
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
		 PROPERTIES ("operator.kafka.groupid" = "gidkpi_1_1","operator.kafka.topic"="join_right_in_2");
							                                
create output stream output_join_right                                            
(LastName String ,FirstName String,OrderNo long)                   
	 SERDE SimpleSerDe
	 SINK 'com.huawei.streaming.operator.outputstream.KafkaFunctionOp'
		 PROPERTIES ("operator.kafka.topic"="join_right_out");
						                                        
insert into stream output_join_right select input_join_right_1.LastName,input_join_right_1.FirstName,input_join_right_2.OrderNo 
from input_join_right_1[range unbounded] right join input_join_right_2[range 10 seconds batch] on input_join_right_1.Id_P=input_join_right_2.Id_P;
submit application app_join_right;