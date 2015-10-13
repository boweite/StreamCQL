
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
	sink MQOutput
	 PROPERTIES (
eventIds ="29043",
services = "127.0.0.1:22136");

insert into stream rs select * from S1;

submit application send;