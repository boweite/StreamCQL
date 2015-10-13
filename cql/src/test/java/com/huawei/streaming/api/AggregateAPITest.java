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

import org.junit.Test;

import com.google.common.collect.Lists;
import com.huawei.streaming.CQLTestBase;
import com.huawei.streaming.api.opereators.AggregateOperator;
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
import com.huawei.streaming.cql.executor.PhysicalPlanExecutor;
import static org.junit.Assert.assertTrue;

/**
 * 聚合API测试
 *
 */
public class AggregateAPITest extends CQLTestBase
{
    
    /**
     * 简单聚合功能测试
     *
     * @throws Exception 测试异常
     */
    @Test
    public void aggregateAPITest()
        throws Exception
    {
        Application app = new Application("aggregateTest");
        
        app.setSchemas(createSchemas());
        app.setOperators(createOperators());
        app.setConfs(LocalTaskCommons.createLocalConfs());
        app.setOpTransition(createTransitions(app.getOperators(), app.getSchemas()));
        
        new PhysicalPlanExecutor().execute(app);
        assertTrue(true);
    }
    
    private static List<OperatorTransition> createTransitions(List<Operator> ops, List<Schema> schemas)
    {
        List<OperatorTransition> results = new ArrayList<OperatorTransition>();
        results.add(new OperatorTransition("s1", ops.get(ConstInTestCase.I_0), ops.get(ConstInTestCase.I_1),
            DistributeType.FIELDS, "c", schemas.get(0)));
        results.add(new OperatorTransition("s2", ops.get(ConstInTestCase.I_1), ops.get(ConstInTestCase.I_2),
            DistributeType.SHUFFLE, null, schemas.get(ConstInTestCase.I_1)));
        return results;
    }
    
    private static List<Operator> createOperators()
    {
        List<Operator> operators = Lists.newArrayList();
        operators.add(createKafkaReaderOperator());
        operators.add(createAggregateOperator());
        operators.add(createKafkaWriterOperator());
        return operators;
    }
    
    private static Operator createKafkaWriterOperator()
    {
        SimpleSerDeAPI ser = new SimpleSerDeAPI();
        ser.setSeparator(",");
        
        KafkaOutputOperator op = new KafkaOutputOperator("kafkaWriter", 1);
        op.setTopic("aggtopic_out");
        op.setZookeepers("158.1.130.21:2181");
        op.setZkSessionTimeout(ConstInTestCase.I_20000);
        op.setZkSyncTime(ConstInTestCase.I_20000);
        op.setSerializer(ser);
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
        op.setLimit(ConstInTestCase.I_10);
        return op;
    }
    
    private static Operator createKafkaReaderOperator()
    {
        SimpleSerDeAPI deser = new SimpleSerDeAPI();
        deser.setSeparator(",");
        
        KafkaInputOperator op = new KafkaInputOperator("kafkaReader", 1);
        op.setGroupId("groupid");
        op.setTopic("aggtopic_in");
        op.setZookeepers("158.1.130.21:2181");
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
        cols2.add(new Column("c", Integer.class));
        
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
