create input stream s (id int, name string)
SOURCE KafkaInput  
PROPERTIES ( groupid = "gidkpi_1_1",topic="agg_1_1");

create output stream rs 
(id string, name string, type int, cc int) 
SINK KafkaOutput properties(topic="agg_2");

--不带properties
create operator userOp as "com.huawei.UserOperator" 
		input (id string, name string, cc int) 
		output (id string, name string, type int, cc int);

insert into s1 select *, count(id) as cc from S[range 20 seconds batch] where id > 5 group by id;

--只有并发没有分发
insert into rs using operator userOp from s1 parallel 2;


explain application tt;