create input stream s (id int, name string)
SOURCE KafkaInput  
PROPERTIES ( groupid = "gidkpi_1_1",topic="agg_1_1");

create output stream rs 
(id string, name string, type int, cc int) 
SINK KafkaOutput properties(topic="agg_2");

create operator userOp as "com.huawei.streaming.cql.executor.userdefined.UserOperatorTest" 
		input (id int, name string, cc long) 
		output (id string, name string, type int, cc int) 
		properties ("userop.filename" = "/home/omm/kv.properties");

insert into s1 select *, count(id) as cc from S[range 20 seconds batch] where id > 5 group by id;

--分发并发都没有
insert into rs using operator userOp from s1;

submit application tt;