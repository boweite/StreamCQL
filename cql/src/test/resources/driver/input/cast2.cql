create input stream S1 (column1 string)
   SOURCE KafkaInput
        PROPERTIES (groupid = "zhou123", topic = "STRV1R2_CQL_EXP_FUNC_006_0001r", zookeepers = "127.0.0.1:2181");

create output stream S2 (column1 Boolean, column2 int ,column3 long ,column4 float ,column5 double ,column6 string ,column7 Timestamp,column8 Date,column9 time,column10 Decimal)
    SINK KafkaOutput
        PROPERTIES ( topic = "STRV1R2_CQL_EXP_FUNC_006_0001t", zookeepers = "127.0.0.1:2181", brokers = "127.0.0.1:9092" );

insert into stream S2 select CAST (S1.column1 as Boolean ), CAST (S1.column1 as int ), CAST (S1.column1 as long), CAST (S1.column1 as float), CAST (S1.column1 as Double), CAST (S1.column1 as string), CAST (S1.column1 as Timestamp  ), CAST ( S1.column1 as Date), CAST ( S1.column1 as Time ), CAST ( S1.column1 as Decimal) from S1;

explain application cast2;

