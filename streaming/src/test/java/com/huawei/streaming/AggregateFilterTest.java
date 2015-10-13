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

package com.huawei.streaming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.huawei.streaming.common.Pair;
import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.EventTypeMng;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.exception.IllegalDataTypeException;
import com.huawei.streaming.expression.AggregateExpression;
import com.huawei.streaming.expression.ConstExpression;
import com.huawei.streaming.expression.ExpressionOperator;
import com.huawei.streaming.expression.IExpression;
import com.huawei.streaming.expression.PropertyValueExpression;
import com.huawei.streaming.expression.RelationExpression;
import com.huawei.streaming.output.OutPutPrint;
import com.huawei.streaming.output.OutputType;
import com.huawei.streaming.process.SelectSubProcess;
import com.huawei.streaming.process.agg.aggregator.IAggregate;
import com.huawei.streaming.process.agg.aggregator.avg.AggregateAvgFilter;
import com.huawei.streaming.process.agg.aggregator.sum.AggregateSumFilter;
import com.huawei.streaming.process.agg.compute.AggsComputeNoGroup;
import com.huawei.streaming.process.agg.resultmerge.AggResultSetMergeOnly;
import com.huawei.streaming.processor.AggregateProcessor;
import com.huawei.streaming.support.SupportConst;
import com.huawei.streaming.view.FirstLevelStream;
import com.huawei.streaming.view.ProcessView;
import com.huawei.streaming.window.LengthBatchWindow;

/**
 * <AggregateFilterTest>
 * <功能详细描述>
 * 
 */
public class AggregateFilterTest
{
    /**
     * <Test>
     * <功能详细描述>
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void test()
        throws IllegalDataTypeException
    {
        //select sum(a, a>10) , count(a, a>10) from schemName.WinLengthBatch(10);
        
        EventTypeMng eventTypeMng = new EventTypeMng();
        //输入流类型
        List<Attribute> schema = new ArrayList<Attribute>();
        Attribute attributeA = new Attribute(Integer.class, "a");
        Attribute attributeB = new Attribute(Integer.class, "b");
        Attribute attributeC = new Attribute(String.class, "c");
        schema.add(attributeA);
        schema.add(attributeB);
        schema.add(attributeC);
        TupleEventType tupleEventType = new TupleEventType("schemName", schema);
        //输出流类型
        List<Attribute> outschema = new ArrayList<Attribute>();
        Attribute attributeSumA = new Attribute(Integer.class, "sum(a, a > 1)");
        Attribute attributeAvgA = new Attribute(Integer.class, "avg(a, a > 1)");
        outschema.add(attributeSumA);
        outschema.add(attributeAvgA);
        TupleEventType outtupleEventType = new TupleEventType("outschema", outschema);
        
        eventTypeMng.addEventType(tupleEventType);
        eventTypeMng.addEventType(outtupleEventType);
        
        FirstLevelStream firststream = new FirstLevelStream();
        LengthBatchWindow lengthbatchwin = new LengthBatchWindow(SupportConst.I_FIVE);
        ProcessView processview = new ProcessView();
        
        /**
         * aggregator
         */
        List<Pair<IExpression, IExpression>> exprs = new ArrayList<Pair<IExpression, IExpression>>(SupportConst.I_TWO);
        
        Pair<IExpression, IExpression> pair =
            new Pair<IExpression, IExpression>(new PropertyValueExpression("a", Integer.class),
                new RelationExpression(ExpressionOperator.GREATERTHAN, new PropertyValueExpression("a", Integer.class),
                    new ConstExpression(1)));
        exprs.add(pair);
        exprs.add(pair);
        
        IAggregate[] aggregators = new IAggregate[SupportConst.I_TWO];
        aggregators[0] = new AggregateSumFilter(Integer.class);
        aggregators[1] = new AggregateAvgFilter(Long.class);
        
        AggsComputeNoGroup aggregator = new AggsComputeNoGroup(exprs, aggregators);
        
        /**
         * select
         */
        IExpression[] exprNodes = new IExpression[SupportConst.I_TWO];
        exprNodes[0] = new AggregateExpression(aggregators[0], true);
        exprNodes[1] = new AggregateExpression(aggregators[1], false);
        
        SelectSubProcess selector = new SelectSubProcess("out", exprNodes, null, outtupleEventType);
        
        /**
         * resultmerge
         */
        AggResultSetMergeOnly aggresultsetmerge = new AggResultSetMergeOnly(aggregator, selector, null, null, null);
        
        /**
         * process
         * //传入具体的处理类和输出类和输出类别
         */
        AggregateProcessor agg = new AggregateProcessor(aggresultsetmerge, new OutPutPrint(), OutputType.I);
        
        firststream.addView(lengthbatchwin);
        lengthbatchwin.addView(processview);
        processview.setProcessor(agg);
        
        firststream.start();
        
        Map<String, Object> values = new HashMap<String, Object>();
        
        //发送数据
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < SupportConst.I_FIVE; i++)
        {
            values.put("a", i);
            values.put("b", i);
            values.put("c", "abc");
            
            TupleEvent tupleEvent2 = new TupleEvent("stream", "schemName", values, eventTypeMng);
            firststream.add(tupleEvent2);
        }
        long endTime = System.currentTimeMillis();
        
        firststream.stop();
        System.out.println(endTime - startTime);
    }
}
