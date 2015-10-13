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

package com.huawei.streaming.process.agg.aggregator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import com.huawei.streaming.process.agg.aggregator.avg.AggregateAvg;
import com.huawei.streaming.process.agg.aggregator.count.AggregateCount;
import com.huawei.streaming.support.SupportConst;

/**
 * <AggregateDistinctValueFilterTest>
 * <功能详细描述>
 * 
 */
public class AggregateDistinctValueFilterTest
{
    private AggregateDistinctValueFilter agg = null;
    
    /**
     * Test AggregateDistinctValueFilter(com.huawei.streaming.process.agg.aggregator.IAggregate).
     */
    @Test
    public void testAggregateDistinctValueFilter()
    {
        try
        {
            agg = new AggregateDistinctValueFilter(null);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertTrue(true);
        }
    }
    
    /**
     * Test cloneAggregate().
     */
    @Test
    public void testCloneAggregate()
    {
        agg = new AggregateDistinctValueFilter(new AggregateAvg(Integer.class));
        
        assertEquals(Long.class, agg.getValueType());
        assertTrue(agg.cloneAggregate() instanceof AggregateDistinctValueFilter);
    }
    
    /**
     * Test aggregate function(enter / leave / getvalue).
     */
    @Test
    public void testFunction()
    {
        agg = new AggregateDistinctValueFilter(new AggregateCount(Long.class));
        assertEquals(Long.class, agg.getValueType());
        assertEquals("0", agg.getValue().toString());
        
        agg.enter(new Integer(SupportConst.I_TEN), Boolean.FALSE);
        assertEquals("0", agg.getValue().toString());
        
        agg.enter(new Integer(SupportConst.I_TEN), Boolean.TRUE);
        assertEquals("1", agg.getValue().toString());
        
        agg.leave(new Integer(SupportConst.I_TEN), Boolean.TRUE);
        assertEquals("0", agg.getValue().toString());
        
        agg.leave(new Integer(SupportConst.I_TEN), Boolean.FALSE);
        assertEquals("0", agg.getValue().toString());
    }
    
}
