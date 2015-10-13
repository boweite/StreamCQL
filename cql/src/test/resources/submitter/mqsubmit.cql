 
set "operator.kafka.messageserializerclass"="kafka.serializer.StringEncoder";
set "operator.kafka.zookeepers"="127.0.0.1:2181";
set "operator.kafka.brokers"="127.0.0.1:9092";
set "operator.kafka.zksessiontimeout"="20000";
set "operator.kafka.zksynctime"="20000";
set "serde.simpleserde.separator" = ",";

create input stream transaction 
(  SEQ_NO               LONG,
   SM_USER_ID           STRING,
   DD_APDATE            LONG,
   SD_SEQ_NUM           STRING,
   SD_KEY               STRING,
   SD_RETL_ID           STRING,
   SD_TERM_ID           STRING,
   MGROUP_ID           	STRING,
   SD_TERM_NAME_LOC     STRING,
   SD_ACQ_INST_ID_NUM   STRING,
   SD_TERM_CNTRY        STRING,
   SD_TERM_ST           STRING,
   SD_TERM_CITY         STRING,
   SD_RETL_SIC_CDE      STRING,
   MD_TRAN_AMT1         DOUBLE,
   MD_TRAN_AMT3         DOUBLE,
   SD_ORIG_CRNCY_CDE    STRING,
   MD_TRAN_AMT2         DOUBLE,
   DD_TRAN_DAT_TIM      LONG,
   DD_TRAN_DAT          STRING,
   DD_TRAN_TIM          STRING,
   SD_PIN_IND           STRING,
   SD_PIN_TRIES         STRING,
   SD_TRAN_TYP          INT,
   SD_TRAN_CDE          STRING,
   SD_RESP_CDE          STRING,
   SD_MSG_TYP           STRING,
   SD_FILLER_FIELD      STRING,
   SD_AUTH_DAT          STRING,
   SD_AUTH_TIM          STRING,
   SD_PT_SRV_ENTRY_MDE  STRING,
   SD_TRAN_MEDIA        STRING,
   SD_APPRV_CDE         STRING,
   SD_PT_SRV_COND_CDE   STRING,
   TERM_ENTRY_CAPA      STRING,
   TERM_VERIF_RSUT      STRING,
   TERM_CAPABIT         STRING,
   CADHLD_VERIF_METH_RSUT STRING,
   TRAN_CHANNEL_TYPE    STRING,
   ISSU_SCRIPT_RSUT     STRING,
   CHIP_COND_CDE        STRING,
   SM_USER_MOB_PHN      STRING,
   SM_USER_MOB_PHN1     STRING,
   SM_USER_PRI_GOVT_ID  STRING,
   SM_USER_POSTAL_CDE   STRING,
   SM_EXP_DAT           STRING,
   SM_CRD_BLK_CDE       STRING,
   MM_OPEN_TO_BUY       DOUBLE,
   SCORE                INT,
   OBJECT_NO            STRING,
   OBJECT_LEVEL         STRING,
   IS_ICE               STRING,
   IS_WATCH             STRING,
   IS_MARK             	STRING,
   FRAUD_TYPE           STRING,
   FRAUD_DATE           LONG,
   RULE_COUNT           INT,
   SD_IP_ADDR           STRING,
   SD_MAC_ADDR          STRING,
   SD_ACCEPT_OUTSID_VOID_CCK STRING,
   SD_AUTH_SRC          STRING,
   SD_CUP_COUN          INT,
   SD_CVD_PRESENT_FLG   STRING,
   E_CASH_BALANCE       DOUBLE,
   SD_E_COM_FLG         STRING,
   INTERACT_FLAG        STRING,
   TRAN_LAUNCH_WAY      STRING,
   SD_ACCT_NUM          STRING
)
	 SERDE SimpleSerDe
	 SOURCE 'com.huawei.streaming.operator.inputstream.KafkaSourceOp'
	 PROPERTIES ("operator.kafka.groupid" = "trans_1","operator.kafka.topic"="trans");

	
create output stream output_where 
	(
	SEQ_NO               LONG,
	SM_USER_ID           STRING
	)
	 SERDE SimpleSerDe
	 SINK 'com.huawei.streaming.operator.outputstream.ConsolePrintOp';
insert into stream output_where select SEQ_NO, SM_USER_ID from transaction[range 10 seconds batch];
submit application prm;