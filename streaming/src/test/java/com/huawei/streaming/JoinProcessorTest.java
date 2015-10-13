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

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.common.Pair;
import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.exception.StreamingException;
import com.huawei.streaming.expression.AggregateExpression;
import com.huawei.streaming.expression.AggregateGroupedExpression;
import com.huawei.streaming.expression.ConstExpression;
import com.huawei.streaming.expression.ExpressionOperator;
import com.huawei.streaming.expression.IExpression;
import com.huawei.streaming.expression.LogicExpression;
import com.huawei.streaming.expression.PropertyValueExpression;
import com.huawei.streaming.expression.RelationExpression;
import com.huawei.streaming.output.OutPutPrint;
import com.huawei.streaming.output.OutputType;
import com.huawei.streaming.process.GroupBySubProcess;
import com.huawei.streaming.process.SelectSubProcess;
import com.huawei.streaming.process.agg.aggregator.IAggregate;
import com.huawei.streaming.process.agg.aggregator.count.AggregateCount;
import com.huawei.streaming.process.agg.compute.AggsComputeGrouped;
import com.huawei.streaming.process.agg.compute.AggsComputeNull;
import com.huawei.streaming.process.agg.compute.IAggregationService;
import com.huawei.streaming.process.agg.resultmerge.AggResultSetMerge;
import com.huawei.streaming.process.agg.resultmerge.AggResultSetMergeOnlyGrouped;
import com.huawei.streaming.process.join.CrossBiJoinComposer;
import com.huawei.streaming.process.join.FullOutBiJoinComposer;
import com.huawei.streaming.process.join.IJoinSetProcessor;
import com.huawei.streaming.process.join.IndexedMultiPropertyEventCollection;
import com.huawei.streaming.process.join.InnerBiJoinComposer;
import com.huawei.streaming.process.join.JoinFilterProcessor;
import com.huawei.streaming.process.join.SideBiJoinComposer;
import com.huawei.streaming.process.join.SideJoinType;
import com.huawei.streaming.process.join.SimpleEventCollection;
import com.huawei.streaming.processor.JoinProcessor;
import com.huawei.streaming.support.SupportConst;
import com.huawei.streaming.view.FirstLevelStream;
import com.huawei.streaming.view.JoinProcessView;
import com.huawei.streaming.window.LengthSlideWindow;

/**
 * 
 * <JoinProcessorTest 总流程测试>
 * <功能详细描述>
 * 
 */
public class JoinProcessorTest
{
    private TupleEventType item = new TupleEventType("item", new Attribute(Integer.class, "id"), new Attribute(
        String.class, "description"), new Attribute(Double.class, "price"));
    
    private TupleEventType sell = new TupleEventType("sell", new Attribute(Integer.class, "id"), new Attribute(
        String.class, "buyer"), new Attribute(Double.class, "price"), new Attribute(Integer.class, "number"));
    
    //输出流定义
    private TupleEventType out = new TupleEventType("out", new Attribute(Integer.class, "item.id"), new Attribute(
        String.class, "sellrecord.buyer"));
    
    private TupleEventType joinout = new TupleEventType("joinout", new Attribute(Integer.class, "item.id"),
        new Attribute(Integer.class, "count"));
    
    private FirstLevelStream itemstream;
    
    private FirstLevelStream sellstream;
    
    private JoinProcessView joinview;
    
    private PropertyValueExpression[] eleft;
    
    private PropertyValueExpression[] exprright;
    
    private IExpression[] selectexprs;
    
    private RelationExpression itemexp;
    
    private RelationExpression sellexp;
    
    private LogicExpression andexp;
    
    /**
     * <setup>
     * @throws Exception 异常
     */
    @Before
    public void setUp()
        throws Exception
    {
        itemstream = new FirstLevelStream();
        sellstream = new FirstLevelStream();
        joinview = new JoinProcessView();
        
        eleft = new PropertyValueExpression[1];
        exprright = new PropertyValueExpression[1];
        
        //SELECT item.id, sell.buyer
        selectexprs = new IExpression[out.getSize()];
        selectexprs[0] = new PropertyValueExpression(0, "id", Integer.class);
        selectexprs[1] = new PropertyValueExpression(1, "buyer", String.class);
        
        //WHERE item.price >3 and sell.price <= 5
        itemexp =
            new RelationExpression(ExpressionOperator.GREATERTHAN,
                new PropertyValueExpression(0, "price", Double.class), new ConstExpression(SupportConst.I_THREE));
        sellexp =
            new RelationExpression(ExpressionOperator.LESSTHAN_EQUAL, new PropertyValueExpression(1, "price",
                Double.class), new ConstExpression(SupportConst.I_FIVE));
        andexp = new LogicExpression(ExpressionOperator.LOGICAND, itemexp, sellexp);
    }
    
    /**
     * testInnerJoin
     * 结果为打印两条匹配数据 
     * item.id=4    sellrecord.buyer=custom:4 
     * item.id=5    sellrecord.buyer=custom:4 
     */
    //    @Ignore
    @Test
    public void testInnerJoin()
    {
        // SELECT item.id, sell.buyer FROM item inner join sell
        // ON item.id=sell.id WHERE item.price >-1 and sell.price <= 10
        /***************计算定义*********************************/
        //窗口定义
        LengthSlideWindow itemlengthslidewin = new LengthSlideWindow(SupportConst.I_TEN);
        LengthSlideWindow selllengthslidewin = new LengthSlideWindow(SupportConst.I_TEN);
        itemstream.addView(itemlengthslidewin);
        sellstream.addView(selllengthslidewin);
        //两个窗口同一个JOIN VIEW
        itemlengthslidewin.addView(joinview);
        selllengthslidewin.addView(joinview);
        
        //ON条件：item.id
        eleft[0] = new PropertyValueExpression("id", Integer.class);
        //ON条件：sell.id
        exprright[0] = new PropertyValueExpression("id", Integer.class);
        IndexedMultiPropertyEventCollection left = new IndexedMultiPropertyEventCollection("item", item, eleft);
        IndexedMultiPropertyEventCollection right = new IndexedMultiPropertyEventCollection("sell", sell, exprright);
        
        //InnerJoin
        InnerBiJoinComposer cbjc = new InnerBiJoinComposer(left, right, false);
        //WHERE
        JoinFilterProcessor jfilter = new JoinFilterProcessor(andexp);
        //SELECT
        SelectSubProcess jselet = new SelectSubProcess("joinout", selectexprs, null, out);
        IAggregationService aggregator = new AggsComputeNull();
        IJoinSetProcessor setProcess = new AggResultSetMerge(aggregator, jselet, null, null, null);
        //组装JOIN处理
        JoinProcessor joinprocessor =
            new JoinProcessor(cbjc, new String[] {"item", "sell"}, jfilter, setProcess, new OutPutPrint(), OutputType.I);
        //JOIN处理加入VIEW
        joinview.setProcessor(joinprocessor);
        
        itemstream.start();
        sellstream.start();
        
        //发送数据
        Map<String, Object> values = new HashMap<String, Object>();
        int itemid = 0;
        Random rand = new SecureRandom();
        for (int i = 0; i < SupportConst.I_TWENTY; i++)
        {
            itemid = rand.nextInt(SupportConst.I_TEN);
            //item数据
            //            values.put("id", itemid);
            values.put("id", i);
            values.put("description", "item:" + itemid);
            values.put("price", (double)i);
            
            TupleEvent tupleEvent2 = new TupleEvent("item", item, values);
            itemstream.add(tupleEvent2);
            
            values.clear();
            itemid = rand.nextInt(SupportConst.I_TEN);
            //            values.put("id", itemid);
            values.put("id", i);
            values.put("buyer", "custom:" + i);
            values.put("price", (double)i);
            values.put("number", rand.nextInt(SupportConst.I_FIVE));
            
            TupleEvent tupleEvent3 = new TupleEvent("sell", sell, values);
            sellstream.add(tupleEvent3);
            values.clear();
        }
        
        itemstream.stop();
        sellstream.stop();
    }
    
    /**
     * testCrossJoin
     * <功能详细描述>
     */
    //    @Ignore
    @Test
    public void testCrossJoin()
    {
        // SELECT item.id, sell.buyer FROM item cross join sell
        // 
        /***************计算定义*********************************/
        //窗口定义
        LengthSlideWindow itemlengthslidewin = new LengthSlideWindow(SupportConst.I_TEN);
        LengthSlideWindow selllengthslidewin = new LengthSlideWindow(SupportConst.I_TEN);
        itemstream.addView(itemlengthslidewin);
        sellstream.addView(selllengthslidewin);
        //两个窗口同一个JOIN VIEW
        itemlengthslidewin.addView(joinview);
        selllengthslidewin.addView(joinview);
        
        SimpleEventCollection left = new SimpleEventCollection("item", item);
        SimpleEventCollection right = new SimpleEventCollection("sell", sell);
        
        CrossBiJoinComposer crossjoin = new CrossBiJoinComposer(left, right, false);
        //WHERE
        //        JoinFilterProcessor jfilter = new JoinFilterProcessor(andexp);
        
        //SELECT
        SelectSubProcess jselet = new SelectSubProcess("joinout", selectexprs, null, out);
        IAggregationService aggregator = new AggsComputeNull();
        IJoinSetProcessor setProcess = new AggResultSetMerge(aggregator, jselet, null, null, null);
        
        //组装JOIN处理
        JoinProcessor joinprocessor =
            new JoinProcessor(crossjoin, new String[] {"item", "sell"}, null, setProcess, new OutPutPrint(),
                OutputType.I);
        //JOIN处理加入VIEW
        joinview.setProcessor(joinprocessor);
        
        itemstream.start();
        sellstream.start();
        
        //发送数据
        Map<String, Object> values = new HashMap<String, Object>();
        int itemid = 0;
        Random rand = new SecureRandom();
        for (int i = 0; i < SupportConst.I_TWENTY; i++)
        {
            itemid = rand.nextInt(SupportConst.I_TEN);
            //item数据
            //            values.put("id", itemid);
            values.put("id", i);
            values.put("description", "item:" + itemid);
            values.put("price", (double)i);
            
            TupleEvent tupleEvent2 = new TupleEvent("item", item, values);
            itemstream.add(tupleEvent2);
            
            values.clear();
            itemid = rand.nextInt(SupportConst.I_TEN);
            //            values.put("id", itemid);
            values.put("id", i + 1);
            values.put("buyer", "custom:" + (i + 1));
            values.put("price", (double)i);
            values.put("number", rand.nextInt(SupportConst.I_FIVE));
            
            TupleEvent tupleEvent3 = new TupleEvent("sell", sell, values);
            sellstream.add(tupleEvent3);
            values.clear();
        }
        
        itemstream.stop();
        sellstream.stop();
        
    }
    
    /**
     * testLeftSideJoin
     * 左联
     * 
     */
    //    @Ignore
    @Test
    public void testLeftSideJoin()
    {
        // SELECT item.id, sell.buyer FROM item left join sell
        // ON item.id=sell.id
        /***************计算定义*********************************/
        //窗口定义
        LengthSlideWindow itemlengthslidewin = new LengthSlideWindow(SupportConst.I_TEN);
        LengthSlideWindow selllengthslidewin = new LengthSlideWindow(SupportConst.I_TEN);
        itemstream.addView(itemlengthslidewin);
        sellstream.addView(selllengthslidewin);
        //两个窗口同一个JOIN VIEW
        itemlengthslidewin.addView(joinview);
        selllengthslidewin.addView(joinview);
        
        //ON条件：item.id
        eleft[0] = new PropertyValueExpression("id", Integer.class);
        //ON条件：sell.id
        exprright[0] = new PropertyValueExpression("id", Integer.class);
        IndexedMultiPropertyEventCollection left = new IndexedMultiPropertyEventCollection("item", item, eleft);
        IndexedMultiPropertyEventCollection right = new IndexedMultiPropertyEventCollection("sell", sell, exprright);
        
        SideBiJoinComposer sidejoin = new SideBiJoinComposer(left, right, SideJoinType.LEFTJOIN, false);
        //WHERE
        //        JoinFilterProcessor jfilter = new JoinFilterProcessor(andexp);
        //SELECT
        SelectSubProcess jselet = new SelectSubProcess("joinout", selectexprs, null, out);
        IAggregationService aggregator = new AggsComputeNull();
        IJoinSetProcessor setProcess = new AggResultSetMerge(aggregator, jselet, null, null, null);
        //组装JOIN处理
        JoinProcessor joinprocessor =
            new JoinProcessor(sidejoin, new String[] {"item", "sell"}, null, setProcess, new OutPutPrint(),
                OutputType.I);
        //JOIN处理加入VIEW
        joinview.setProcessor(joinprocessor);
        
        itemstream.start();
        sellstream.start();
        
        //发送数据
        Map<String, Object> values = new HashMap<String, Object>();
        int itemid = 0;
        Random rand = new SecureRandom();
        for (int i = 0; i < SupportConst.I_TWENTY; i++)
        {
            itemid = rand.nextInt(SupportConst.I_TEN);
            //item数据
            //            values.put("id", itemid);
            values.put("id", i);
            values.put("description", "item:" + itemid);
            values.put("price", (double)i);
            
            TupleEvent tupleEvent2 = new TupleEvent("item", item, values);
            itemstream.add(tupleEvent2);
            
            values.clear();
            itemid = rand.nextInt(SupportConst.I_TEN);
            //            values.put("id", itemid);
            values.put("id", i + 1);
            values.put("buyer", "custom:" + (i + 1));
            values.put("price", (double)i);
            values.put("number", rand.nextInt(SupportConst.I_FIVE));
            
            TupleEvent tupleEvent3 = new TupleEvent("sell", sell, values);
            sellstream.add(tupleEvent3);
            values.clear();
        }
        
        itemstream.stop();
        sellstream.stop();
        
    }
    
    /**
     * testRightSideJoin
     * 右联
     * 
     */
    //    @Ignore
    @Test
    public void testRightSideJoin()
    {
        // SELECT item.id, sell.buyer FROM item right join sell
        // ON item.id=sell.id
        /***************计算定义*********************************/
        //窗口定义
        LengthSlideWindow itemlengthslidewin = new LengthSlideWindow(SupportConst.I_TEN);
        LengthSlideWindow selllengthslidewin = new LengthSlideWindow(SupportConst.I_TEN);
        itemstream.addView(itemlengthslidewin);
        sellstream.addView(selllengthslidewin);
        //两个窗口同一个JOIN VIEW
        itemlengthslidewin.addView(joinview);
        selllengthslidewin.addView(joinview);
        
        //ON条件：item.id
        eleft[0] = new PropertyValueExpression("id", Integer.class);
        //ON条件：sell.id
        exprright[0] = new PropertyValueExpression("id", Integer.class);
        IndexedMultiPropertyEventCollection left = new IndexedMultiPropertyEventCollection("item", item, eleft);
        IndexedMultiPropertyEventCollection right = new IndexedMultiPropertyEventCollection("sell", sell, exprright);
        
        SideBiJoinComposer sidejoin = new SideBiJoinComposer(left, right, SideJoinType.RIGHTJOIN, false);
        //WHERE
        //        JoinFilterProcessor jfilter = new JoinFilterProcessor(andexp);
        //SELECT
        SelectSubProcess jselet = new SelectSubProcess("joinout", selectexprs, null, out);
        IAggregationService aggregator = new AggsComputeNull();
        IJoinSetProcessor setProcess = new AggResultSetMerge(aggregator, jselet, null, null, null);
        //组装JOIN处理
        JoinProcessor joinprocessor =
            new JoinProcessor(sidejoin, new String[] {"item", "sell"}, null, setProcess, new OutPutPrint(),
                OutputType.I);
        //JOIN处理加入VIEW
        joinview.setProcessor(joinprocessor);
        
        itemstream.start();
        sellstream.start();
        
        //发送数据
        Map<String, Object> values = new HashMap<String, Object>();
        int itemid = 0;
        Random rand = new SecureRandom();
        for (int i = 0; i < SupportConst.I_TWENTY; i++)
        {
            itemid = rand.nextInt(SupportConst.I_TEN);
            //item数据
            //            values.put("id", itemid);
            values.put("id", i);
            values.put("description", "item:" + itemid);
            values.put("price", (double)i);
            
            TupleEvent tupleEvent2 = new TupleEvent("item", item, values);
            itemstream.add(tupleEvent2);
            
            values.clear();
            itemid = rand.nextInt(SupportConst.I_TEN);
            //            values.put("id", itemid);
            values.put("id", i + 1);
            values.put("buyer", "custom:" + (i + 1));
            values.put("price", (double)i);
            values.put("number", rand.nextInt(SupportConst.I_FIVE));
            
            TupleEvent tupleEvent3 = new TupleEvent("sell", sell, values);
            sellstream.add(tupleEvent3);
            values.clear();
        }
        
        itemstream.stop();
        sellstream.stop();
        
    }
    
    /**
     * testFullOutJoin
     * <功能详细描述>
     */
    //    @Ignore
    @Test
    public void testFullOutJoin()
    {
        // SELECT item.id, sell.buyer FROM item fullout join sell
        // ON item.id=sell.id
        /***************计算定义*********************************/
        //窗口定义
        LengthSlideWindow itemlengthslidewin = new LengthSlideWindow(SupportConst.I_TEN);
        LengthSlideWindow selllengthslidewin = new LengthSlideWindow(SupportConst.I_TEN);
        itemstream.addView(itemlengthslidewin);
        sellstream.addView(selllengthslidewin);
        //两个窗口同一个JOIN VIEW
        itemlengthslidewin.addView(joinview);
        selllengthslidewin.addView(joinview);
        
        //ON条件：item.id
        eleft[0] = new PropertyValueExpression("id", Integer.class);
        //ON条件：sell.id
        exprright[0] = new PropertyValueExpression("id", Integer.class);
        IndexedMultiPropertyEventCollection left = new IndexedMultiPropertyEventCollection("item", item, eleft);
        IndexedMultiPropertyEventCollection right = new IndexedMultiPropertyEventCollection("sell", sell, exprright);
        
        FullOutBiJoinComposer fulljoin = new FullOutBiJoinComposer(left, right, false);
        //WHERE
        //        JoinFilterProcessor jfilter = new JoinFilterProcessor(andexp);
        //SELECT
        SelectSubProcess jselet = new SelectSubProcess("joinout", selectexprs, null, out);
        IAggregationService aggregator = new AggsComputeNull();
        IJoinSetProcessor setProcess = new AggResultSetMerge(aggregator, jselet, null, null, null);
        //组装JOIN处理
        JoinProcessor joinprocessor =
            new JoinProcessor(fulljoin, new String[] {"item", "sell"}, null, setProcess, new OutPutPrint(),
                OutputType.I);
        //JOIN处理加入VIEW
        joinview.setProcessor(joinprocessor);
        
        itemstream.start();
        sellstream.start();
        
        //发送数据
        Map<String, Object> values = new HashMap<String, Object>();
        int itemid = 0;
        Random rand = new SecureRandom();
        for (int i = 0; i < SupportConst.I_TWENTY; i++)
        {
            itemid = rand.nextInt(SupportConst.I_TEN);
            //item数据
            //            values.put("id", itemid);
            values.put("id", i);
            values.put("description", "item:" + itemid);
            values.put("price", (double)i);
            
            TupleEvent tupleEvent2 = new TupleEvent("item", item, values);
            itemstream.add(tupleEvent2);
            
            values.clear();
            itemid = rand.nextInt(SupportConst.I_TEN);
            //            values.put("id", itemid);
            values.put("id", i + 1);
            values.put("buyer", "custom:" + (i + 1));
            values.put("price", (double)i);
            values.put("number", rand.nextInt(SupportConst.I_FIVE));
            
            TupleEvent tupleEvent3 = new TupleEvent("sell", sell, values);
            sellstream.add(tupleEvent3);
            values.clear();
        }
        
        itemstream.stop();
        sellstream.stop();
        
    }
    
    /**
     * testSelfJoin
     * <功能详细描述>
     * @throws StreamingException 异常
     */
    @Test
    public void testSelfJoin()
        throws StreamingException
    {
        // SELECT item.id, count(*) FROM item.win:length(1) unidirection, item.win:length(10) as sell
        // ON item.id=sell.id
        // WHERE item.price > 3 group by item.id
        /***************计算定义*********************************/
        //窗口定义
        LengthSlideWindow itemlengthslidewin = new LengthSlideWindow(SupportConst.I_ONE);
        LengthSlideWindow selllengthslidewin = new LengthSlideWindow(SupportConst.I_TEN);
        itemstream.addView(itemlengthslidewin);
        sellstream.addView(selllengthslidewin);
        //两个窗口同一个JOIN VIEW
        itemlengthslidewin.addView(joinview);
        selllengthslidewin.addView(joinview);
        
        //ON条件：item.id
        eleft[0] = new PropertyValueExpression("id", Integer.class);
        //ON条件：sell.id
        exprright[0] = new PropertyValueExpression("id", Integer.class);
        IndexedMultiPropertyEventCollection left = new IndexedMultiPropertyEventCollection("item", item, eleft);
        IndexedMultiPropertyEventCollection right = new IndexedMultiPropertyEventCollection("sell", item, exprright);
        
        //InnerJoin
        InnerBiJoinComposer cbjc = new InnerBiJoinComposer(left, right, false);
        
        //WHERE item.price > 3        
        JoinFilterProcessor jfilter = new JoinFilterProcessor(itemexp);
        
        //AGG计算
        List<Pair<IExpression, IExpression>> aggexp = new ArrayList<Pair<IExpression, IExpression>>(SupportConst.I_ONE);
        
        Pair<IExpression, IExpression> pair =
            new Pair<IExpression, IExpression>(new AggregateExpression(new AggregateCount(Integer.class), false),
                new ConstExpression(true));
        aggexp.add(pair);
        
        IAggregate[] aggregators = new IAggregate[SupportConst.I_ONE];
        aggregators[SupportConst.I_ZERO] = new AggregateCount(Integer.class);
        
        IAggregationService aggregator = new AggsComputeGrouped(aggexp, aggregators);
        
        //SELECT item.id, count(*)
        IExpression[] select = new IExpression[SupportConst.I_TWO];
        select[SupportConst.I_ZERO] = new PropertyValueExpression(0, "id", Integer.class);
        select[SupportConst.I_ONE] = new AggregateGroupedExpression(aggregator, 0);
        
        SelectSubProcess jselet = new SelectSubProcess("joinout", select, null, joinout);
        
        IExpression[] groupexp = new IExpression[SupportConst.I_ONE];
        groupexp[SupportConst.I_ZERO] = new PropertyValueExpression(0, "id", Integer.class);
        GroupBySubProcess group = new GroupBySubProcess(groupexp);
        
        IJoinSetProcessor setProcess = new AggResultSetMergeOnlyGrouped(aggregator, jselet, group, null, null);
        setProcess.setUnidirection(true);
        
        //组装JOIN处理
        JoinProcessor joinprocessor =
            new JoinProcessor(cbjc, new String[] {"item", "sell"}, jfilter, setProcess, new OutPutPrint(), OutputType.I);
        joinprocessor.setSelfJoin(true);
        joinprocessor.setUnidirectional(true);
        joinprocessor.setUniStreamIndex(0);
        
        //JOIN处理加入VIEW
        joinview.setProcessor(joinprocessor);
        
        itemstream.start();
        sellstream.start();
        
        //发送数据
        Map<String, Object> values = new HashMap<String, Object>();
        int itemid = 0;
        Random rand = new SecureRandom();
        for (int i = 0; i < SupportConst.I_TWENTY; i++)
        {
            itemid = rand.nextInt(SupportConst.I_TEN);
            values.put("id", itemid);
            values.put("description", "item:" + itemid);
            values.put("price", (double)itemid);
            
            TupleEvent tupleEvent2 = new TupleEvent("sell", item, values);
            
            sellstream.add(tupleEvent2);
            tupleEvent2.setStreamName("item");
            itemstream.add(tupleEvent2);
            values.clear();
        }
        
        itemstream.stop();
        sellstream.stop();
    }
}
