create input stream S (id int, name String) 
SOURCE KafkaInput 
PROPERTIES ( groupid = "gidkpi_1_1",topic="agg_1_1");

create output stream rs (id string, name string, type int, cc int) 
SINK KafkaOutput properties(topic="agg_2");

create operator userOp as "com.huawei.UserOperator" 
		input (id string, name string, cc int) 
		output (id string, name string, type int, cc int) 
		properties ("client.port" = "8080");

--单流输入中间算子拼接场景
insert into s1 select *, count(id) as cc from S[range 20 seconds batch] where id > 5 group by id;
insert into s2 using operator userOp from s1 distribute by id;
insert into rs select * from s2 where type = 1;
explain application tt;
