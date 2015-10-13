create input stream S1 (c1 int, c2 Timestamp)
     SOURCE KafkaInput
         PROPERTIES (groupid = "zhou123", topic = "Previous_input_0110_0001", zookeepers = "160.163.0.164:2181");

create output stream S2  (c1 Timestamp)
    SINK consoleOutput;

insert into stream S2 select c2 from S1 [range 15 seconds slide sort by S1.c2];
submit application force STRV1R2_CQL_WIN_FUNC_0110_0001;
