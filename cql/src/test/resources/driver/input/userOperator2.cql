create input stream s (id int, name string)
SOURCE KafkaInput  
PROPERTIES ( groupid = "gidkpi_1_1",topic="agg_1_1");

create output stream rs 
(id string, name string, type int, cc int) 
SINK KafkaOutput properties(topic="agg_2");


create operator userOp as "com.huawei.UserOperator" 
		input (id string, name string, cc int) 
		output (id string, name string, type int, cc int) 
		properties ("client.port" = "8080");

insert into s1 select *, count(id) as cc from S[range 20 seconds batch] where id > 5 group by id;

--有分发没有并发
insert into rs using operator userOp from s1 distribute by id;

explain application tt;