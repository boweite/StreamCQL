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

package com.huawei.streaming.process.agg.aggregator.max;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import com.huawei.streaming.support.SupportConst;

/**
 * <AggregateMaxFilterTest>
 * <功能详细描述>
 * 
 */
public class AggregateMaxFilterTest
{
    private AggregateMaxFilter agg = null;
    
    /**
     * Test AggregateMaxFilter(java.lang.Class).
     */
    @Test
    public void testAggregateAvgFilter()
    {
        try
        {
            agg = new AggregateMaxFilter(null);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            agg = new AggregateMaxFilter(String.class);
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
        agg = new AggregateMaxFilter(Long.class);
        
        assertEquals(Long.class, agg.cloneAggregate().getValueType());
        assertEquals(true, agg.cloneAggregate() instanceof AggregateMaxFilter);
    }
    
    /**
     * Test AggregateCountFilter(java.lang.Class).
     */
    @Test
    public void testAggregateMaxFilter()
    {
        //Integer
        agg = new AggregateMaxFilter(Integer.class);
        assertEquals(Integer.class, agg.getValueType());
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.I_TEN, true);
        assertEquals(SupportConst.I_TEN, agg.getValue());
        
        agg.enter(SupportConst.I_TEN, true);
        assertEquals(SupportConst.I_TEN, agg.getValue());
        
        agg.enter(SupportConst.I_TWO, true);
        assertEquals(SupportConst.I_TEN, agg.getValue());
        
        agg.leave(SupportConst.I_TEN, true);
        assertEquals(SupportConst.I_TEN, agg.getValue());
        
        agg.leave(SupportConst.I_TEN, true);
        assertEquals(SupportConst.I_TWO, agg.getValue());
        
        agg.leave(SupportConst.I_TWO, true);
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.I_TWO, true);
        assertEquals(SupportConst.I_TWO, agg.getValue());
        
        agg.enter(SupportConst.I_TEN, false);
        assertEquals(SupportConst.I_TWO, agg.getValue());
        
        agg.leave(SupportConst.I_TEN, false);
        assertEquals(SupportConst.I_TWO, agg.getValue());
        
        agg.leave(SupportConst.I_TWO, true);
        assertEquals(null, agg.getValue());
        
        //Long
        agg = new AggregateMaxFilter(Long.class);
        assertEquals(Long.class, agg.getValueType());
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.L_TEN, true);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.enter(SupportConst.L_TEN, true);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.enter(SupportConst.L_TWO, true);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.leave(SupportConst.L_TEN, true);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.leave(SupportConst.L_TEN, true);
        assertEquals(SupportConst.L_TWO, agg.getValue());
        
        agg.leave(SupportConst.L_TWO, true);
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.L_TWO, true);
        assertEquals(SupportConst.L_TWO, agg.getValue());
        
        agg.enter(SupportConst.L_TEN, false);
        assertEquals(SupportConst.L_TWO, agg.getValue());
        
        agg.leave(SupportConst.L_TEN, false);
        assertEquals(SupportConst.L_TWO, agg.getValue());
        
        agg.leave(SupportConst.L_TWO, true);
        assertEquals(null, agg.getValue());
        
        //Double
        agg = new AggregateMaxFilter(Double.class);
        assertEquals(Double.class, agg.getValueType());
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.D_TEN, true);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.enter(SupportConst.D_TEN, true);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.enter(SupportConst.D_TWO, true);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.leave(SupportConst.D_TEN, true);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.leave(SupportConst.D_TEN, true);
        assertEquals(SupportConst.D_TWO, agg.getValue());
        
        agg.leave(SupportConst.D_TWO, true);
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.D_TWO, true);
        assertEquals(SupportConst.D_TWO, agg.getValue());
        
        agg.enter(SupportConst.D_TEN, false);
        assertEquals(SupportConst.D_TWO, agg.getValue());
        
        agg.leave(SupportConst.D_TEN, false);
        assertEquals(SupportConst.D_TWO, agg.getValue());
        
        agg.leave(SupportConst.D_TWO, true);
        assertEquals(null, agg.getValue());
        
        //Float
        agg = new AggregateMaxFilter(Float.class);
        assertEquals(Float.class, agg.getValueType());
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.F_TEN, true);
        assertEquals(SupportConst.F_TEN, agg.getValue());
        
        agg.enter(SupportConst.F_TEN, true);
        assertEquals(SupportConst.F_TEN, agg.getValue());
        
        agg.enter(SupportConst.F_TWO, true);
        assertEquals(SupportConst.F_TEN, agg.getValue());
        
        agg.leave(SupportConst.F_TEN, true);
        assertEquals(SupportConst.F_TEN, agg.getValue());
        
        agg.leave(SupportConst.F_TEN, true);
        assertEquals(SupportConst.F_TWO, agg.getValue());
        
        agg.leave(SupportConst.F_TWO, true);
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.F_TWO, true);
        assertEquals(SupportConst.F_TWO, agg.getValue());
        
        agg.enter(SupportConst.F_TEN, false);
        assertEquals(SupportConst.F_TWO, agg.getValue());
        
        agg.leave(SupportConst.F_TEN, false);
        assertEquals(SupportConst.F_TWO, agg.getValue());
        
        agg.leave(SupportConst.F_TWO, true);
        assertEquals(null, agg.getValue());
    }
    
}
