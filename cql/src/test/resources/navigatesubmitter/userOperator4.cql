create input stream s (name string, id int)
SOURCE KafkaInput  
PROPERTIES ( groupid = "gidkpi_1_1",topic="agg_1_1");

create output stream rs 
(newName string, type1 int, type2 float) 
SINK KafkaOutput properties(topic="agg_2");

-- 输出schema类大小不一致
create operator userOp as "com.huawei.streaming.cql.executor.userdefined.UserOperatorTest" 
		input (name string, id int) 
		output (newName string, type1 int) 
		properties ("client.port" = "8080");

insert into rs using operator userOp from s;

submit application tt;