create input stream S1(column1 string,column2 int)
     SOURCE KafkaInput
         PROPERTIES (groupid = "zhou123", topic = "Previous_input_0040_0002", zookeepers = "160.165.0.10:2181");
                 
create output stream S2 (column1 string,column2 int)
    SINK KafkaOutput
        PROPERTIES ( topic = "Previous_output_0040_0002", zookeepers = "160.165.0.10:2181", brokers = "160.138.1.22:9092" );
        
insert into stream S3 select S1.column1 as c1, sum(S1.column2) as c2 from S1[rows 6 batch ] group by S1.column1 having sum(S1.column2)<2000 ;
insert into stream S2 select * from  S3 where c2 is not null;
explain application  STRV1R2_CQL_FUN_FUNC_0040_0002;
