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
import com.huawei.streaming.expression.AggregateGroupedExpression;
import com.huawei.streaming.expression.ConstExpression;
import com.huawei.streaming.expression.IExpression;
import com.huawei.streaming.expression.PropertyValueExpression;
import com.huawei.streaming.output.OutPutPrint;
import com.huawei.streaming.output.OutputType;
import com.huawei.streaming.process.GroupBySubProcess;
import com.huawei.streaming.process.SelectSubProcess;
import com.huawei.streaming.process.agg.aggregator.AggregateDistinctValue;
import com.huawei.streaming.process.agg.aggregator.IAggregate;
import com.huawei.streaming.process.agg.aggregator.avg.AggregateAvg;
import com.huawei.streaming.process.agg.aggregator.sum.AggregateSum;
import com.huawei.streaming.process.agg.compute.AggsComputeGrouped;
import com.huawei.streaming.process.agg.resultmerge.AggResultSetMergeOnlyGrouped;
import com.huawei.streaming.processor.AggregateProcessor;
import com.huawei.streaming.support.SupportConst;
import com.huawei.streaming.view.FirstLevelStream;
import com.huawei.streaming.view.ProcessView;
import com.huawei.streaming.window.LengthBatchWindow;

/**
 * <GroupByAggregateOnlyTest 总流程测试>
 * <功能详细描述>
 * 
 */
public class GroupByAggregateOnlyTest
{
    /**
     * <Test>
     * <功能详细描述>
     */
    @Test
    public void test()
    {
        //select sum(distinct a) , count(a) from schemName.WinLengthBatch(10) grouby b;
        
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
        Attribute attributeSumA = new Attribute(Integer.class, "sum(distinct a)");
        Attribute attributeAvgA = new Attribute(Integer.class, "avg(a)");
        outschema.add(attributeSumA);
        outschema.add(attributeAvgA);
        TupleEventType outtupleEventType = new TupleEventType("outschema", outschema);
        
        eventTypeMng.addEventType(tupleEventType);
        eventTypeMng.addEventType(outtupleEventType);
        
        FirstLevelStream firststream = new FirstLevelStream();
        LengthBatchWindow lengthbatchwin = new LengthBatchWindow(SupportConst.I_TEN);
        ProcessView processview = new ProcessView();
        
        /**
         * aggregator
         */
        List<Pair<IExpression, IExpression>> exprs = new ArrayList<Pair<IExpression, IExpression>>(SupportConst.I_TWO);
        
        Pair<IExpression, IExpression> pair =
            new Pair<IExpression, IExpression>(new PropertyValueExpression("a", Integer.class), new ConstExpression(
                true));
        exprs.add(pair);
        exprs.add(pair);
        
        IAggregate[] aggregators = new IAggregate[SupportConst.I_TWO];
        aggregators[0] = new AggregateDistinctValue(new AggregateSum(Integer.class));
        aggregators[1] = new AggregateAvg(Long.class);
        
        AggsComputeGrouped aggregator = new AggsComputeGrouped(exprs, aggregators);
        
        /**
         * select
         */
        String[] names = new String[SupportConst.I_TWO];
        names[0] = "sum(distinct a)";
        names[1] = "avg(a)";
        
        IExpression[] exprNodes = new IExpression[SupportConst.I_TWO];
        exprNodes[0] = new AggregateGroupedExpression(aggregator, 0);
        exprNodes[1] = new AggregateGroupedExpression(aggregator, 1);
        
        SelectSubProcess selector = new SelectSubProcess("out", exprNodes, null, outtupleEventType);
        
        /**
         * groupby
         */
        IExpression[] grouped = new IExpression[1];
        grouped[0] = new PropertyValueExpression("b", Integer.class);
        
        GroupBySubProcess groupby = new GroupBySubProcess(grouped);
        
        /**
         * resultmerge
         */
        AggResultSetMergeOnlyGrouped aggresultsetmerge =
            new AggResultSetMergeOnlyGrouped(aggregator, selector, groupby, null, null);
        
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
            
            values.put("a", i);
            values.put("b", i);
            values.put("c", "bbc");
            
            TupleEvent tupleEvent3 = new TupleEvent("stream", "schemName", values, eventTypeMng);
            firststream.add(tupleEvent3);
        }
        long endTime = System.currentTimeMillis();
        
        firststream.stop();
        System.out.println(endTime - startTime);
    }
    
}
