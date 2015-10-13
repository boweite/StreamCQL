
create input stream S1
  (
      C1   STRING,
      C2   STRING
   )
	 SOURCE kafkainput
		 PROPERTIES ( groupid = "gidkpi_1_1","topic"="agg_1_1",
						zookeepers="160.163.0.164:2181");


create output stream rs
	(
    C1 STRING,
	C2  STRING
	)
SINK ConsoleOutput
      PROPERTIES(printFrequence = "10");


insert into stream rs select * from S1;

explain application read;

