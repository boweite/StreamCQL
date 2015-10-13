/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.streaming.cql.executor;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huawei.streaming.api.Application;
import com.huawei.streaming.api.PhysicalPlan;
import com.huawei.streaming.api.UserFunction;
import com.huawei.streaming.api.opereators.AggregateOperator;
import com.huawei.streaming.api.opereators.FilterOperator;
import com.huawei.streaming.api.opereators.FunctorOperator;
import com.huawei.streaming.api.opereators.JoinFunctionOperator;
import com.huawei.streaming.api.opereators.JoinType;
import com.huawei.streaming.api.opereators.KafkaInputOperator;
import com.huawei.streaming.api.opereators.KafkaOutputOperator;
import com.huawei.streaming.api.opereators.Operator;
import com.huawei.streaming.api.opereators.OperatorTransition;
import com.huawei.streaming.api.opereators.RandomGenInputOperator;
import com.huawei.streaming.api.opereators.UnionOperator;
import com.huawei.streaming.api.opereators.Window;
import com.huawei.streaming.api.opereators.serdes.SimpleSerDeAPI;
import com.huawei.streaming.api.streams.Column;
import com.huawei.streaming.api.streams.Schema;
import com.huawei.streaming.application.DistributeType;
import com.huawei.streaming.cql.ConstInTestCase;

/**
 * 完成的各种算子序列化
 *
 */
public class FullXMLWriterTest
{
    
    private static final String LEFT_INPUT_TOPIC = "innerjoin6_left_in";
    
    private static final String RIGHT_INPUT_TOPIC = "innerjoin6_right_in";
    
    private static final String BASICPATH = File.separator + "executor" + File.separator + "planserde" + File.separator;
    
    private static String serDir = null;
    
    private static String serFileName = "fullser.xml";
    
    /**
     * 写入测试
     *
     * @throws Exception 执行异常
     */
    @Test
    public void writerTest()
        throws Exception
    {
        setDir();
        
        Application app = new Application("fullapptest");
        
        app.setSchemas(createSchemas());
        app.setOperators(createOperators());
        app.setOpTransition(createTransitions(app.getOperators(), app.getSchemas()));
        createUserDefineds(app);
        
        PhysicalPlan phyplan = new PhysicalPlan();
        phyplan.setApploication(app);
        
        PhysicalPlanWriter.write(phyplan, serDir + serFileName);
    }
    
    private static List<Schema> createSchemas()
    {
        List<Column> cols1 = new ArrayList<Column>();
        cols1.add(new Column("a", Integer.class));
        cols1.add(new Column("b", Integer.class));
        cols1.add(new Column("c", String.class));
        
        List<Column> cols2 = new ArrayList<Column>();
        cols2.add(new Column("a", Integer.class));
        cols2.add(new Column("b", Integer.class));
        cols2.add(new Column("c", String.class));
        
        Schema schema1 = new Schema("testSchema1");
        schema1.setCols(cols1);
        
        Schema schema2 = new Schema("testSchema2");
        schema2.setCols(cols2);
        
        List<Schema> results = Lists.newArrayList();
        results.add(schema1);
        results.add(schema2);
        
        return results;
    }
    
    private static List<Operator> createOperators()
    {
        List<Operator> operators = Lists.newArrayList();
        operators.add(createKafkaReaderOperator1());
        operators.add(createKafkaReaderOperator2());
        operators.add(createKafkaReaderOperator3());
        operators.add(createJoinOperator());
        operators.add(createUnionOperator());
        operators.add(createFilterOperator());
        operators.add(createAggregateOperator());
        operators.add(createFunctorOperator());
        operators.add(createKafkaWriterOperator());
        operators.add(createRandomGenSourceOperator());
        return operators;
    }
    
    private static Operator createRandomGenSourceOperator()
    {
        return RandomGenInputOperator.sendEverySeconds("randomgen",
            ConstInTestCase.I_2,
            ConstInTestCase.I_1000,
            Long.valueOf(ConstInTestCase.I_10));
    }
    
    private static Operator createKafkaReaderOperator3()
    {
        SimpleSerDeAPI deser = new SimpleSerDeAPI();
        deser.setSeparator(",");
        
        KafkaInputOperator op = new KafkaInputOperator("kafkaReader_union", 1);
        op.setGroupId("groupid");
        op.setTopic(RIGHT_INPUT_TOPIC);
        op.setZookeepers("158.1.130.21:2181");
        op.setZkSessionTimeout(ConstInTestCase.I_20000);
        op.setZkSyncTime(ConstInTestCase.I_20000);
        op.setDeserializer(deser);
        return op;
        
    }
    
    private static Operator createUnionOperator()
    {
        return new UnionOperator("union", ConstInTestCase.I_1);
    }
    
    private static Operator createKafkaReaderOperator1()
    {
        SimpleSerDeAPI deser = new SimpleSerDeAPI();
        deser.setSeparator(",");
        
        KafkaInputOperator op = new KafkaInputOperator("kafkaReader_left", 1);
        op.setGroupId("groupid");
        op.setTopic(LEFT_INPUT_TOPIC);
        op.setZookeepers("158.1.130.21:2181");
        op.setZkSessionTimeout(ConstInTestCase.I_20000);
        op.setZkSyncTime(ConstInTestCase.I_20000);
        op.setDeserializer(deser);
        return op;
    }
    
    private static Operator createKafkaReaderOperator2()
    {
        SimpleSerDeAPI deser = new SimpleSerDeAPI();
        deser.setSeparator(",");
        
        KafkaInputOperator op = new KafkaInputOperator("kafkaReader_right", 1);
        op.setGroupId("groupid");
        op.setTopic(RIGHT_INPUT_TOPIC);
        op.setZookeepers("158.1.130.21:2181");
        op.setZkSessionTimeout(ConstInTestCase.I_20000);
        op.setZkSyncTime(ConstInTestCase.I_20000);
        op.setDeserializer(deser);
        return op;
    }
    
    private static Operator createJoinOperator()
    {
        Window timeslide = Window.createTimeSlideWindow(ConstInTestCase.I_100);
        Window lengthslide = Window.createLengthSlideWindow(ConstInTestCase.I_100);
        
        JoinFunctionOperator op = new JoinFunctionOperator("join", 1);
        op.setLeftWindow(timeslide);
        op.setRightWindow(lengthslide);
        op.setFilterAfterJoinExpression("a> 0 and a<100");
        op.setJoinExpression("s1.a=s2.a");
        op.setLeftStreamName("s1");
        op.setRightStreamName("s2");
        op.setJoinType(JoinType.INNER_JOIN);
        op.setOutputExpression("s1.a,s2.b,s2.c");
        return op;
    }
    
    private static Operator createFilterOperator()
    {
        FilterOperator op = new FilterOperator("filter", 1);
        op.setOutputExpression("a,b,c");
        op.setFilterExpression("a> 0 and a<100");
        return op;
    }
    
    private static Operator createAggregateOperator()
    {
        Window timebatchwin = Window.createTimeBatchWindow(ConstInTestCase.I_100);
        AggregateOperator op = new AggregateOperator("agg", 1);
        op.setWindow(timebatchwin);
        op.setFilterAfterAggregate("b>10");
        op.setFilterBeforeAggregate("a>1");
        op.setOutputExpression("a,sum(b),count(b)");
        op.setGroupbyExpression("c");
        op.setOrderBy("c,a desc");
        op.setLimit(ConstInTestCase.I_10);
        return op;
    }
    
    private static Operator createFunctorOperator()
    {
        FunctorOperator op = new FunctorOperator("functor", 1);
        op.setFilterExpression("a>1 and b<100");
        op.setOutputExpression("a,tostring(b)");
        return op;
    }
    
    private static Operator createKafkaWriterOperator()
    {
        SimpleSerDeAPI ser = new SimpleSerDeAPI();
        ser.setSeparator(",");
        
        KafkaOutputOperator op = new KafkaOutputOperator("kafkaWriter", 1);
        op.setTopic("innerjoin_out");
        op.setZookeepers("158.1.130.21:2181");
        op.setZkSessionTimeout(ConstInTestCase.I_20000);
        op.setZkSyncTime(ConstInTestCase.I_20000);
        op.setSerializer(ser);
        return op;
    }
    
    private static void createUserDefineds(Application app)
    {
        /**
         * 自定义函数
         * 自定义窗口
         * 自定义参数
         */
        TreeMap<String, String> udsargs = Maps.newTreeMap();
        udsargs.put("app.args.1", "true");
        
        String[] files = {"d:\\conf.xml,e:\\abc.jar"};
        
        /**
         * 窗口定义好之后，直接用名称就可以识别出来
         * 系统自动进行窗口的注册
         */
        
        Map<String, String> testcomonArgs6 = new HashMap<String, String>();
        testcomonArgs6.put("udw.length", "5");
        
        List<UserFunction> udfs = Lists.newArrayList();
        UserFunction userFunction = new UserFunction();
        userFunction.setName("udf1");
        userFunction.setClazz("com.huawei.streaming.udf.ToChar");
        udfs.add(userFunction);

        app.setConfs(udsargs);
        app.setUserFiles(files);
        app.setUserFunctions(udfs);
        
    }
    
    private static List<OperatorTransition> createTransitions(List<Operator> ops, List<Schema> schemas)
    {
        List<OperatorTransition> results = Lists.newArrayList();
        results.add(new OperatorTransition("s1", ops.get(ConstInTestCase.I_0), ops.get(ConstInTestCase.I_3),
            DistributeType.FIELDS, "a", schemas.get(ConstInTestCase.I_0)));
        results.add(new OperatorTransition("s2", ops.get(ConstInTestCase.I_1), ops.get(ConstInTestCase.I_3),
            DistributeType.FIELDS, "a", schemas.get(ConstInTestCase.I_0)));
        results.add(new OperatorTransition("s3", ops.get(ConstInTestCase.I_2), ops.get(ConstInTestCase.I_4),
            DistributeType.FIELDS, "a", schemas.get(ConstInTestCase.I_1)));
        results.add(new OperatorTransition("s4", ops.get(ConstInTestCase.I_3), ops.get(ConstInTestCase.I_4),
            DistributeType.FIELDS, "a", schemas.get(ConstInTestCase.I_1)));
        results.add(new OperatorTransition("s5", ops.get(ConstInTestCase.I_4), ops.get(ConstInTestCase.I_5),
            DistributeType.SHUFFLE, null, schemas.get(ConstInTestCase.I_1)));
        results.add(new OperatorTransition("s6", ops.get(ConstInTestCase.I_5), ops.get(ConstInTestCase.I_6),
            DistributeType.FIELDS, "a", schemas.get(ConstInTestCase.I_1)));
        results.add(new OperatorTransition("s7", ops.get(ConstInTestCase.I_6), ops.get(ConstInTestCase.I_7),
            DistributeType.SHUFFLE, null, schemas.get(ConstInTestCase.I_1)));
        results.add(new OperatorTransition("s8", ops.get(ConstInTestCase.I_7), ops.get(ConstInTestCase.I_8),
            DistributeType.SHUFFLE, null, schemas.get(ConstInTestCase.I_1)));
        return results;
    }
    
    /**
     * 设置待序列化的文件路径
     *
     */
    private static void setDir()
    {
        String classPath = FullXMLWriterTest.class.getResource("/").getPath();
        
        try
        {
            classPath = URLDecoder.decode(classPath, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        
        serDir = classPath + BASICPATH + File.separator;
    }
    
}
