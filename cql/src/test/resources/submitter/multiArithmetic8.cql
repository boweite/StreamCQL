create input stream S1 (ci int,cl long,cf float,cd double,cde decimal )
     SOURCE KafkaInput
         PROPERTIES (groupid = "zhou123", topic = "demo_sin", zookeepers = "51.196.22.36:24002");
                 
create output stream S2  (cl1 long,cf1 float,cd1 double,cde1 decimal)
    SINK KafkaOutput
        PROPERTIES ( topic = "demo_sout", zookeepers = "51.196.22.36:24002", brokers = "51.196.22.36:9092" );

insert into stream S2 select S1.ci*S1.cl,S1.ci*S1.cl*S1.cf,S1.ci*S1.cl*S1.cf*S1.cd,S1.ci*S1.cl*S1.cf*S1.cd*S1.cde from S1;
submit application  STRV1R2_CQL_BASIC_FUNC_0050_0023;