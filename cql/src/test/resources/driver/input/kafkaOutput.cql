
create input stream S1
  (
      C1   STRING,
      C2   STRING
   )
 SOURCE randomgen
           PROPERTIES
           ( timeUnit = "SECONDS", period = "1",
           eventNumPerPeriod = "1", isSchedule = "true",
           totalNumber = "20000", delayTime = "0");


create output stream rs
	(
    C1 STRING,
	C2  STRING
	)
	 SINK KafkaOutput
		 PROPERTIES (topic = "agg_1_1",brokers="160.163.0.164:9092", zookeepers = "160.163.0.164:2181");


insert into stream rs select * from S1;

explain application send;
