create input stream S (id int, name string, type int) 
SOURCE KafkaInput 
PROPERTIES ( groupid = "gidkpi_1_1",topic="agg_1_1");

create input stream SX (id string, name string, cc int) 
SOURCE KafkaInput 
PROPERTIES ( groupid = "gidkpi_1_2",topic="agg_1_2");

create output stream rs (id string, name string, type int, cc int) SINK KafkaOutput properties(topic="agg_2");

create operator userOp as "com.huawei.UserOperator" 
		input (id string, name string, cc int) 
		output (id string, name string, type int, cc int) 
		properties ("client.port" = "8080");
		
Create output stream rs2 (id string, name string, type int, cc int) SINK consoleOutput;

insert into s1 select *,count(id) from S[range 20 seconds batch] where id > 5 group by type;

insert into rs using operator userOp from s1 distribute by id;
insert into rs2 using operator userOp from sx;
--数据发送到rs和rs2中，rs按照字段进行分发，rs2随机分发。


explain application tt;