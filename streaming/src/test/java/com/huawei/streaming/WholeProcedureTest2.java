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
import com.huawei.streaming.process.LimitProcess;
import com.huawei.streaming.process.OrderBySubProcess;
import com.huawei.streaming.process.SelectSubProcess;
import com.huawei.streaming.process.agg.aggregator.AggregateDistinctValue;
import com.huawei.streaming.process.agg.aggregator.IAggregate;
import com.huawei.streaming.process.agg.aggregator.avg.AggregateAvg;
import com.huawei.streaming.process.agg.aggregator.sum.AggregateSum;
import com.huawei.streaming.process.agg.compute.AggsComputeNoGroup;
import com.huawei.streaming.process.agg.resultmerge.AggResultSetMerge;
import com.huawei.streaming.process.agg.resultmerge.IResultSetMerge;
import com.huawei.streaming.process.sort.SortCondition;
import com.huawei.streaming.process.sort.SortEnum;
import com.huawei.streaming.processor.AggregateProcessor;
import com.huawei.streaming.support.SupportConst;
import com.huawei.streaming.view.FirstLevelStream;
import com.huawei.streaming.view.ProcessView;
import com.huawei.streaming.window.LengthBatchWindow;

/**
 * 
 * <WholeProcedureTest>
 * <功能详细描述>
 * 
 */
public class WholeProcedureTest2
{
    private static final int INT_200 = 200;
    
    /**
     * <Test>
     * <功能详细描述>
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void test()
        throws IllegalDataTypeException
    {
        
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
        Attribute attributeOA = new Attribute(Integer.class, "a");
        Attribute attributeSumA = new Attribute(Integer.class, "sum(distinct a)");
        Attribute attributeAvgA = new Attribute(Double.class, "avg(a)");
        Attribute attributeOC = new Attribute(String.class, "c");
        outschema.add(attributeOA);
        outschema.add(attributeSumA);
        outschema.add(attributeAvgA);
        outschema.add(attributeOC);
        TupleEventType outtupleEventType = new TupleEventType("outschema", outschema);
        
        eventTypeMng.addEventType(tupleEventType);
        eventTypeMng.addEventType(outtupleEventType);
        
        FirstLevelStream firststream = new FirstLevelStream();
        LengthBatchWindow lengthbatchwin = new LengthBatchWindow(SupportConst.I_TEN);
        ProcessView processview = new ProcessView();
        
        /*        IExpression[] exprs = new IExpression[SupportConst.I_TWO];
                exprs[0] = new PropertyValueExpression("a", Integer.class);
                exprs[1] = new PropertyValueExpression("a", Integer.class);*/
        
        List<Pair<IExpression, IExpression>> exprs = new ArrayList<Pair<IExpression, IExpression>>(SupportConst.I_TWO);
        
        Pair<IExpression, IExpression> pair =
            new Pair<IExpression, IExpression>(new PropertyValueExpression("a", Integer.class), new ConstExpression(
                true));
        exprs.add(pair);
        exprs.add(pair);
        
        IAggregate[] aggregators = new IAggregate[SupportConst.I_TWO];
        aggregators[0] = new AggregateDistinctValue(new AggregateSum(Integer.class));
        aggregators[1] = new AggregateAvg(Double.class);
        
        IExpression[] exprNodes = new IExpression[SupportConst.I_FOUR];
        exprNodes[SupportConst.I_ZERO] = new PropertyValueExpression("a", Integer.class);
        exprNodes[SupportConst.I_ONE] = new AggregateExpression(aggregators[0], true);
        exprNodes[SupportConst.I_TWO] = new AggregateExpression(aggregators[1], false);
        exprNodes[SupportConst.I_THREE] = new PropertyValueExpression("c", String.class);
        
        String[] names = new String[SupportConst.I_FOUR];
        names[SupportConst.I_ZERO] = "a";
        names[SupportConst.I_ONE] = "sum(distinct a)";
        names[SupportConst.I_TWO] = "avg(a)";
        names[SupportConst.I_THREE] = "c";
        
        IExpression leftExpression1 = new PropertyValueExpression("a", Integer.class);
        IExpression rightExpression1 = new ConstExpression(SupportConst.I_FIVE);
        RelationExpression relationExpression1 =
            new RelationExpression(ExpressionOperator.GREATERTHAN, leftExpression1, rightExpression1);
        
        SelectSubProcess selector = new SelectSubProcess("out", exprNodes, relationExpression1, outtupleEventType);
        
        AggsComputeNoGroup aggregator = new AggsComputeNoGroup(exprs, aggregators);
        
        //排序，先按c升序再按a降序
        List<SortCondition> sortCondition = new ArrayList<SortCondition>();
        sortCondition.add(new SortCondition("c", SortEnum.ASC));
        sortCondition.add(new SortCondition("a", SortEnum.DESC));
        OrderBySubProcess orderByProcess = new OrderBySubProcess(sortCondition);
        
        LimitProcess limit = new LimitProcess(SupportConst.I_SIX);
        IResultSetMerge aggresultsetmerge = new AggResultSetMerge(aggregator, selector, null, orderByProcess, limit);
        
        AggregateProcessor agg = new AggregateProcessor(aggresultsetmerge, new OutPutPrint(), OutputType.I);
        
        firststream.addView(lengthbatchwin);
        lengthbatchwin.addView(processview);
        processview.setProcessor(agg);
        
        firststream.start();
        
        Map<String, Object> values = new HashMap<String, Object>();
        
        //发送数据
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < INT_200; i++)
        {
            values.put("a", i);
            values.put("b", i);
            values.put("c", "abc");
            
            TupleEvent tupleEvent2 = new TupleEvent("stream", "schemName", values, eventTypeMng);
            firststream.add(tupleEvent2);
            
            values.put("a", i + 1);
            values.put("b", i);
            values.put("c", "bbc");
            
            TupleEvent tupleEvent3 = new TupleEvent("stream", "schemName", values, eventTypeMng);
            firststream.add(tupleEvent3);
        }
        long endTime = System.currentTimeMillis();
        
        firststream.stop();
        System.out.println(endTime - startTime);
        
        /*fail("Not yet implemented");*/
    }
}
