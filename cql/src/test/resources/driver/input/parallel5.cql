set "operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder";
set "operator.kafka.zookeepers"="localhost:2181,158.1.130.21:2181";
set "operator.kafka.brokers"="158.1.130.12:9092";
set "operator.kafka.zksessiontimeout"="20000";
set "operator.kafka.zksynctime"="20000";
set "serde.simpleserde.separator" = ",";

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

insert into stream rs select *,count(a) from (select id as a,id as b, id c from S parallel 5)[range 20 seconds batch] where a > 5 group by c parallel 4;
explain application subquery;