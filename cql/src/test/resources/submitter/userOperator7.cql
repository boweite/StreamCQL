create input stream S (id int, name string, type int) SOURCE KafkaInput 
PROPERTIES ( groupid = "gidkpi_1_1",topic="agg_1_1");

create output stream rs (id string, name string, type int, cc int) SINK KafkaOutput properties(topic="agg_2");

create operator userOp as "com.huawei.streaming.cql.executor.userdefined.UserOperatorTest" 
		input (id int, name string,type int, cc long) 
		output (id string, name string, type int, cc int) 
		properties ("userop.filename" = "/home/omm/kv.properties");
create output stream rs2 (id string, name string, type int) SINK consoleOutput;

insert into s1 select *,count(id) from S[range 20 seconds batch] where id > 5 group by type;
insert into s2 using operator userOp from s1 distribute by id;

from s2 insert into rs select * where type =1 insert into rs2 select id,name,type;

submit application tt;