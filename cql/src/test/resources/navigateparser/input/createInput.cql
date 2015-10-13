--测试可选部分
--关键字错误
CREATE int STREAM  S (id INT,name STRING)
SERDE 'com.huawei.streaming.sql.SerDe.CSVSerDe'
SOURCE 'com.huawei.streaming.spout.csvReader' PROPERTIES ("path" = "/local");

--数据类型错误
CREATE INPUT STREAM S (id INT,name STRNIG)
SERDE 'com.huawei.streaming.sql.SerDe.CSVSerDe'  properties ("ip" = "localhost")
SOURCE 'com.huawei.streaming.spout.csvReader' PROPERTIES ("path" = "/local");

--关键字错误
CREATE INPUT STREAM S (id INT,name String) 
DESER 'com.huawei.streaming.sql.SerDe.CSVSerDe'  pRoperties ("ip" = "localhost")
SOURCE 'com.huawei.streaming.spout.csvReader' PROPERTIES ("path" = "/local");

--缺少Stream关键字
CREATE INPUT S (id INT,name String)
SERDE 'com.huawei.streaming.sql.SerDe.CSVSerDe'
SOURCE 'com.huawei.streaming.spout.csvReader';

--缺少流名称
CREATE INPUT STREAM (id INT comment 'user id',name String comment 'user name')
COMMENT 'this is stream comment'
SERDE 'com.huawei.streaming.sql.SerDe.CSVSerDe'
SOURCE 'com.huawei.streaming.spout.csvReader';