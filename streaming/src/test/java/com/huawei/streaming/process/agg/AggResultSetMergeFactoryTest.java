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

package com.huawei.streaming.process.agg;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.huawei.streaming.common.Pair;
import com.huawei.streaming.event.IEventType;
import com.huawei.streaming.expression.AggregateExpression;
import com.huawei.streaming.expression.ConstExpression;
import com.huawei.streaming.expression.IExpression;
import com.huawei.streaming.expression.PropertyValueExpression;
import com.huawei.streaming.process.GroupBySubProcess;
import com.huawei.streaming.process.LimitProcess;
import com.huawei.streaming.process.OrderBySubProcess;
import com.huawei.streaming.process.SelectSubProcess;
import com.huawei.streaming.process.agg.aggregator.AggregateDistinctValue;
import com.huawei.streaming.process.agg.aggregator.IAggregate;
import com.huawei.streaming.process.agg.aggregator.avg.AggregateAvg;
import com.huawei.streaming.process.agg.aggregator.sum.AggregateSum;
import com.huawei.streaming.process.agg.compute.AggsComputeGrouped;
import com.huawei.streaming.process.agg.compute.AggsComputeNoGroup;
import com.huawei.streaming.process.agg.compute.IAggregationService;
import com.huawei.streaming.process.agg.resultmerge.AggResultSetMerge;
import com.huawei.streaming.process.agg.resultmerge.AggResultSetMergeFactory;
import com.huawei.streaming.process.agg.resultmerge.AggResultSetMergeGrouped;
import com.huawei.streaming.process.agg.resultmerge.AggResultSetMergeOnly;
import com.huawei.streaming.process.agg.resultmerge.AggResultSetMergeOnlyGrouped;
import com.huawei.streaming.process.agg.resultmerge.IResultSetMerge;
import com.huawei.streaming.support.SupportConst;
import com.huawei.streaming.support.SupportEventMng;

/**
 * <AggResultSetMergeFactoryTest>
 * <功能详细描述>
 * 
 */
public class AggResultSetMergeFactoryTest
{
    
    /**
     * testMakeAggResultSetMerge
     *
     */
    @Test
    public void testMakeAggResultSetMerge()
    {
        IAggregationService aggregator = null;
        SelectSubProcess selector = null;
        GroupBySubProcess groupby = null;
        OrderBySubProcess order = null;
        LimitProcess limit = null;
        
        try
        {
            AggResultSetMergeFactory.makeAggResultSetMerge(null, null, null, null, null);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertTrue(true);
        }
        
        //1--AggResultSetMergeOnly
        List<Pair<IExpression, IExpression>> exprs = new ArrayList<Pair<IExpression, IExpression>>(SupportConst.I_TWO);
        
        Pair<IExpression, IExpression> pair =
            new Pair<IExpression, IExpression>(new PropertyValueExpression("a", Integer.class), new ConstExpression(
                true));
        exprs.add(pair);
        exprs.add(pair);
        
        IAggregate[] aggregators = new IAggregate[SupportConst.I_TWO];
        aggregators[0] = new AggregateDistinctValue(new AggregateSum(Integer.class));
        aggregators[1] = new AggregateAvg(Integer.class);
        
        IExpression[] exprNodes = new IExpression[SupportConst.I_TWO];
        
        exprNodes[0] = new AggregateExpression(aggregators[0], true);
        exprNodes[1] = new AggregateExpression(aggregators[1], false);
        
        SupportEventMng mng = new SupportEventMng();
        ;
        
        IEventType eventType = mng.getOutputAggOnly();
        
        selector = new SelectSubProcess("out", exprNodes, null, eventType);
        aggregator = new AggsComputeNoGroup(exprs, aggregators);
        
        IResultSetMerge aggresultsetmerge = new AggResultSetMergeOnly(aggregator, selector, groupby, order, limit);
        
        Assert.assertEquals(aggresultsetmerge.getClass(),
            AggResultSetMergeFactory.makeAggResultSetMerge(aggregator, selector, groupby, order, limit).getClass());
        
        //2 -- AggResultSetMergeOnlyGrouped
        IExpression[] grouped = new IExpression[1];
        grouped[0] = new PropertyValueExpression("b", Integer.class);
        
        aggregator = new AggsComputeGrouped(exprs, aggregators);
        groupby = new GroupBySubProcess(grouped);
        
        aggresultsetmerge = new AggResultSetMergeOnlyGrouped(aggregator, selector, groupby, order, limit);
        
        Assert.assertEquals(aggresultsetmerge.getClass(),
            AggResultSetMergeFactory.makeAggResultSetMerge(aggregator, selector, groupby, order, limit).getClass());
        
        //3 -- AggResultSetMerge
        exprNodes = new IExpression[SupportConst.I_THREE];
        
        exprNodes[0] = new PropertyValueExpression("a", Integer.class);
        exprNodes[1] = new AggregateExpression(aggregators[0], true);
        exprNodes[SupportConst.I_TWO] = new AggregateExpression(aggregators[1], false);
        
        eventType = mng.getOutputAggAll();
        selector = new SelectSubProcess("out", exprNodes, null, eventType);
        aggregator = new AggsComputeNoGroup(exprs, aggregators);
        groupby = null;
        
        aggresultsetmerge = new AggResultSetMerge(aggregator, selector, groupby, order, limit);
        
        Assert.assertEquals(aggresultsetmerge.getClass(),
            AggResultSetMergeFactory.makeAggResultSetMerge(aggregator, selector, groupby, order, limit).getClass());
        
        //4 -- AggResultSetMergeGrouped
        aggregator = new AggsComputeGrouped(exprs, aggregators);
        groupby = new GroupBySubProcess(grouped);
        
        aggresultsetmerge = new AggResultSetMergeGrouped(aggregator, selector, groupby, order, limit);
        
        Assert.assertEquals(aggresultsetmerge.getClass(),
            AggResultSetMergeFactory.makeAggResultSetMerge(aggregator, selector, groupby, order, limit).getClass());
        
    }
    
}
