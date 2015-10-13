set "operator.kafka.brokers"="158.1.130.12:9092";

create input stream S 
(id int ,name String,type int)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.HeadStreamSourceOp'
		 PROPERTIES ("operator.headstream.timeunit" = "SECONDS","operator.headstream.period"="1",
						"operator.headstream.eventnumperperiod"="1",
						"operator.headstream.isschedule"="true","operator.headstream.totalnumber"="20000",
							"operator.headstream.delaytime"="0");
							
	create output stream rs 
		(id string,name string,cc int)
	 SERDE SimpleSerDe
		 PROPERTIES ("serde.simpleserde.separator" = ",")
	 SINK 'com.huawei.streaming.operator.outputstream.ConsolePrintOp';
	
	--最一般的聚合查询。仅仅带了窗口							
	insert into stream rs select id,name,count(id) from S[range 200 milliseconds batch] where id > 5 group by type;
	submit application simple;