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

package com.huawei.streaming.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.huawei.streaming.api.opereators.FilterOperator;
import com.huawei.streaming.api.opereators.JoinFunctionOperator;
import com.huawei.streaming.api.opereators.JoinType;
import com.huawei.streaming.api.opereators.KafkaInputOperator;
import com.huawei.streaming.api.opereators.KafkaOutputOperator;
import com.huawei.streaming.api.opereators.Operator;
import com.huawei.streaming.api.opereators.OperatorTransition;
import com.huawei.streaming.api.opereators.Window;
import com.huawei.streaming.api.opereators.serdes.SimpleSerDeAPI;
import com.huawei.streaming.api.streams.Column;
import com.huawei.streaming.api.streams.Schema;
import com.huawei.streaming.application.DistributeType;
import com.huawei.streaming.cql.ConstInTestCase;
import com.huawei.streaming.cql.LocalTaskCommons;

/**
 * 聚合API测试
 *
 */
public class InnerJoinTest
{
    
    private static final String LEFT_INPUT_TOPIC = "innerjoin6_left_in";
    
    private static final String RIGHT_INPUT_TOPIC = "innerjoin6_right_in";
    
    /**
     * 所有测试开始前 执行的方法
     *
     * @throws Exception 异常
     */
    @BeforeClass
    public static void setUpBeforeClass()
        throws Exception
    {
        LocalTaskCommons.startZookeeperServer();
    }
    
    /**
     * 所有测试执行之后执行的清理方法
     *
     * @throws Exception 异常
     */
    @AfterClass
    public static void tearDownAfterClass()
        throws Exception
    {
        LocalTaskCommons.stopZooKeeperServer();
    }
    
    /**
     * 简单聚合功能测试
     *
     * @throws Exception 测试异常
     */
    @Test
    public void innerJoinTest()
        
        throws Exception
    {
        Application app = new Application("innerjoinTest6");
        
        app.setSchemas(createSchemas());
        app.setOperators(createOperators());
        app.setConfs(LocalTaskCommons.createLocalConfs());
        app.setOpTransition(createTransitions(app.getOperators(), app.getSchemas()));
        LocalTaskCommons.localSubmit(app);
    }
    
    private static List<OperatorTransition> createTransitions(List<Operator> ops, List<Schema> schemas)
    {
        List<OperatorTransition> results = new ArrayList<OperatorTransition>();
        results.add(new OperatorTransition("s1", ops.get(ConstInTestCase.I_0), ops.get(ConstInTestCase.I_2),
            DistributeType.FIELDS, "a", schemas.get(ConstInTestCase.I_0)));
        results.add(new OperatorTransition("s2", ops.get(ConstInTestCase.I_1), ops.get(ConstInTestCase.I_2),
            DistributeType.FIELDS, "a", schemas.get(ConstInTestCase.I_0)));
        results.add(new OperatorTransition("s3", ops.get(ConstInTestCase.I_2), ops.get(ConstInTestCase.I_3),
            DistributeType.SHUFFLE, null, schemas.get(ConstInTestCase.I_1)));
        results.add(new OperatorTransition("s4", ops.get(ConstInTestCase.I_3), ops.get(ConstInTestCase.I_4),
            DistributeType.SHUFFLE, null, schemas.get(ConstInTestCase.I_1)));
        return results;
    }
    
    private static List<Operator> createOperators()
    {
        List<Operator> operators = new ArrayList<Operator>();
        operators.add(createKafkaReaderOperator1());
        operators.add(createKafkaReaderOperator2());
        operators.add(createJoinOperator());
        operators.add(createFilterOperator());
        operators.add(createKafkaWriterOperator());
        return operators;
    }
    
    private static Operator createKafkaWriterOperator()
    {
        SimpleSerDeAPI ser = new SimpleSerDeAPI();
        ser.setSeparator(",");
        
        KafkaOutputOperator op = new KafkaOutputOperator("kafkaWriter", 1);
        op.setTopic("innerjoin_out");
        op.setZookeepers("127.0.0.1:2181");
        op.setZkSessionTimeout(ConstInTestCase.I_20000);
        op.setZkSyncTime(ConstInTestCase.I_20000);
        op.setSerializer(ser);
        return op;
    }
    
    private static Operator createFilterOperator()
    {
        FilterOperator op = new FilterOperator("filter", 1);
        op.setOutputExpression("a,b,c");
        op.setFilterExpression("a> 0 and a<100");
        return op;
    }
    
    private static Operator createJoinOperator()
    {
        Window timeslide = Window.createTimeSlideWindow(ConstInTestCase.I_100);
        Window lengthslide = Window.createLengthSlideWindow(ConstInTestCase.I_100);
        
        JoinFunctionOperator op = new JoinFunctionOperator("join", 1);
        op.setLeftWindow(timeslide);
        op.setRightWindow(lengthslide);
        op.setFilterAfterJoinExpression("s1.a> 1 and s1.a<1000");
        op.setJoinExpression("s1.a=s2.a");
        op.setLeftStreamName("s1");
        op.setRightStreamName("s2");
        op.setJoinType(JoinType.INNER_JOIN);
        op.setOutputExpression("s1.a,s2.b,s2.c");
        return op;
    }
    
    private static Operator createKafkaReaderOperator2()
    {
        SimpleSerDeAPI deser = new SimpleSerDeAPI();
        deser.setSeparator(",");
        
        KafkaInputOperator op = new KafkaInputOperator("kafkaReader_right", 1);
        op.setGroupId("groupid");
        op.setTopic(RIGHT_INPUT_TOPIC);
        op.setZookeepers("127.0.0.1:2181");
        op.setZkSessionTimeout(ConstInTestCase.I_20000);
        op.setZkSyncTime(ConstInTestCase.I_20000);
        op.setDeserializer(deser);
        return op;
    }
    
    private static Operator createKafkaReaderOperator1()
    {
        SimpleSerDeAPI deser = new SimpleSerDeAPI();
        deser.setSeparator(",");
        
        KafkaInputOperator op = new KafkaInputOperator("kafkaReader_left", 1);
        op.setGroupId("groupid");
        op.setTopic(LEFT_INPUT_TOPIC);
        op.setZookeepers("127.0.0.1:2181");
        op.setZkSessionTimeout(ConstInTestCase.I_20000);
        op.setZkSyncTime(ConstInTestCase.I_20000);
        op.setDeserializer(deser);
        return op;
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
        
        List<Schema> results = new ArrayList<Schema>();
        results.add(schema1);
        results.add(schema2);
        
        return results;
    }
    
}
