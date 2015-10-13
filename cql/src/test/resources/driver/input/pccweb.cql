create input stream aiuhotest 
(sid string, host string, fst_uri string) 
 SERDE 'com.huawei.streaming.serde.PCCWebSerDe'
 SOURCE 'com.huawei.streaming.cql.toolkits.operators.TCPServerInputOperator'
 PROPERTIES ("operator.tcpserver.port" = "9999","operator.tcpserver.fixedlength"="966") parallel 2;

create output Stream rs
(sid string, host string, fst_uri string) 
 SERDE SimpleSerDe
 PROPERTIES ("serde.simpleserde.separator" = ",")
 SINK 'com.huawei.streaming.operator.outputstream.ConsolePrintOp'
 PROPERTIES("operator.consoleprint.frequence"="1")  parallel 2;

insert into rs select * from aiuhotest where host = "http://www.huawei.com"  parallel 2;
explain application tt;
