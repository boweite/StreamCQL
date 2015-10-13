create input stream s (name string, id int)
SOURCE KafkaInput  
PROPERTIES ( groupid = "gidkpi_1_1",topic="agg_1_1");

create output stream rs 
(newName string, type1 int, type2 float) 
SINK KafkaOutput properties(topic="agg_2");

create operator userOp as "com.huawei.streaming.cql.executor.userdefined.UserOperatorTest" 
		input (name string, id int, num int) 
		output (newName string, type1 int, type2 float) 
		properties ("client.port" = "8080");

--输入输出列数量不一致
insert into rs using operator userOp from s;

submit application tt;