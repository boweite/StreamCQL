create input stream S 
(num int)
	 SOURCE KafkaInput
		 PROPERTIES (groupid = "gidkpi_1_1",topic="agg_1_1",zookeepers="158.1.130.21:2181");
							
	create output stream rs 
		(id int)
	 SINK ConsoleOutput;

 insert into stream rs select num+1+num+2+num from S;	
 submit application simple;