set "operator.kafka.brokers"="158.1.130.12:9092";

create input stream S 
(id int ,name String,type int)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.HeadStreamSourceOp'
		 PROPERTIES ("operator.headstream.timeunit" = "SECONDS","operator.headstream.period"="1",
						"operator.headstream.eventnumperperiod"="1",
						"operator.headstream.isschedule"="true","operator.headstream.totalnumber"="20",
							"operator.headstream.delaytime"="10");
							
	create output stream rs 
		(id string,name string,cc int)
	 SERDE SimpleSerDe
		 PROPERTIES ("serde.simpleserde.separator" = ",")
	 SINK 'com.huawei.streaming.operator.outputstream.ConsolePrintOp';
	
	--case key when '1' then 1 when '2' then 2 when '3' then 3 else 99 end						
	insert into stream rs select case id when 1 then 'id1' when 2 then 'id2' else 'id_99' end,name,count(id)   from S[range 200 milliseconds batch] where id > 5 group by type;
	explain application simple;