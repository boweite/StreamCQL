--测试用例无需add jar
create function udf1 as "com.huawei.streaming.cql.functions.DirectOutputUDF"
    properties ("cql.test.direct.output" = "a");

create function udf2 as "com.huawei.streaming.cql.functions.DirectOutputUDF"
    properties ("cql.test.direct.output" = "b");

create function udf3 as "com.huawei.streaming.cql.functions.DirectOutputUDF";

	CREATE INPUT STREAM HISPACE
	(
    id String
	)
	 SERDE simpleSerde
		PROPERTIES ( separator = "|" )
	 SOURCE  kafkaInput
		PROPERTIES (
			groupid = "udf",
			topic = "udf",
			zookeepers = "127.0.0.1:2181"
			);

	CREATE OUTPUT STREAM OPEN_COUNT
	(
	    value1  	String,
	    value2  	String,
	    defaultValue  	String
	)
	 SINK ConsoleOutput
		  PROPERTIES(isPrintCount = "true", printFrequence = "1") ;

    insert into OPEN_COUNT select udf1(),udf2(),udf3() from HISPACE;

	submit application udfWithProperties;