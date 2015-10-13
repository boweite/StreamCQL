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

package com.huawei.streaming.process.agg.aggregator.count;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.huawei.streaming.support.SupportConst;

/**
 * <AggregateCountTest>
 * <功能详细描述>
 * 
 */
public class AggregateCountTest
{
    private AggregateCount agg = null;
    
    /**
     * Test AggregateCount(java.lang.Class).
     */
    @Test
    public void testAggregateCount()
    {
        /*try
        {
            agg = new AggregateCount(String.class);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertTrue(true);
        }*/
    }
    
    /**
     * Test cloneAggregate().
     */
    @Test
    public void testCloneAggregate()
    {
        agg = new AggregateCount(Double.class);
        
        assertEquals(Long.class, agg.cloneAggregate().getValueType());
        assertEquals(true, agg.cloneAggregate() instanceof AggregateCount);
    }
    
    /**
     * <testAggregateCountFunction (enter, leave, getValue)>
     * <功能详细描述>
     */
    @Test
    public void testAggregateCountFunction()
    {
        agg = new AggregateCount(Long.class);
        assertEquals(Long.class, agg.getValueType());
        assertEquals(0L, agg.getValue());
        
        agg.enter(SupportConst.I_TEN);
        assertEquals(1L, agg.getValue());
        
        agg.enter(SupportConst.I_TWO);
        assertEquals(SupportConst.L_TWO, agg.getValue());
        
        agg.leave(SupportConst.I_TWO);
        assertEquals(1L, agg.getValue());
        
        agg.leave(SupportConst.I_TEN);
        assertEquals(0L, agg.getValue());
    }
    
}
