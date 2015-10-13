set "operator.kafka.brokers"="127.0.0.1:9092";
set "serde.simpleserde.separator" = ",";
create input stream input_where 
(O_Id int ,OrderDate String ,OrderPrice float ,Customer String)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.HeadStreamSourceOp'
		 PROPERTIES ("operator.headstream.timeunit" = "SECONDS",
						"operator.headstream.period"="1",
						"operator.headstream.eventnumperperiod"="1000",
						"operator.headstream.isschedule"="true",
						"operator.headstream.totalnumber"="10000",
						"operator.headstream.delaytime"="3");
							
create output stream output_where 
	(Customer String ,sum_orderprice long)
 SERDE SimpleSerDe
 SINK 'com.huawei.streaming.operator.outputstream.ConsolePrintOp';
	
insert into stream output_where 
	select Customer,OrderPrice from input_where[range 10 seconds batch] where OrderPrice in (100,1000.00f,2000,10.35f);
submit application simple;