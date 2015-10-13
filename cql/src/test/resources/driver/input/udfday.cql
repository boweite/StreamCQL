create input stream S1 (c1 string)
     SOURCE KafkaInput
         PROPERTIES (groupid = "zhou123", topic = "Previous_input_0070_0001", zookeepers = "127.0.0.1:2181");
create output stream S2  (c1 int,c2 int, c3 int, c4 int, c5 int, c6 int, c7 int, c8 int, c9 string)
    SINK KafkaOutput
        PROPERTIES ( topic = "Previous_output_0070_0001", zookeepers = "127.0.0.1:2181", brokers = "127.0.0.1:9092" );
insert into stream S2 select Year(S1.c1), Month(S1.c1), Day(S1.c1), DayofMonth(S1.c1), Hour(S1.c1), Minute(S1.c1), Second(S1.c1), WeekofYear(S1.c1), ToDate(S1.c1) from S1;

explain application tt;