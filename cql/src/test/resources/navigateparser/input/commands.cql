-- add Files
add file hdfs:///home/user/test.jar;
add file /home/user/test.jar';
add file '/home/user/test.jar;

-- add jar
add jars '/home/user/test.jar';

-- show
--function关键字错误
show function tostring;
--关键字顺序错误
show extended functions tostring;

--缺少as
--双引号和单引号不匹配
create function test 'com.huawei.streaming.cql.udf.testudf";
--缺少函数名称
create function  'com.huawei.streaming.cql.udf.testudf';

--缺少if 关键字
drop function exists test;
--缺少 exists关键字
drop function if test;

--错误的路径
explain application tt kkk;

deactive application;
deactive application 'app1';
deactive application app1 app2;
