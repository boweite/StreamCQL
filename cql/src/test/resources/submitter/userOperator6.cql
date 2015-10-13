create input stream S1 (id int, name string) SOURCE KafkaInput 
PROPERTIES ( groupid = "gidkpi_1_1",topic="agg_1_1");
create input stream S2 (id int, name string, cc int) SOURCE KafkaInput 
PROPERTIES ( groupid = "gidkpi_1_2",topic="agg_1_2");

create output stream rs (id string, name string, type int, cc int) SINK KafkaOutput properties(topic="agg_2");

create operator userOp as "com.huawei.streaming.cql.executor.userdefined.UserOperatorTest" 
		input (id int, name string, cc long) 
		output (id string, name string, type int, cc int) 
		properties ("userop.filename" = "/home/omm/kv.properties");

--多流输入用户自定义算子场景
insert into s3 select *, count(id) as cc from S1[range 20 seconds batch] where id > 5 group by id;
insert into s3 select * from s2;
insert into s4 using operator userOp from s3 distribute by id;
insert into rs select * from s4 where type = 1;

submit application tt;