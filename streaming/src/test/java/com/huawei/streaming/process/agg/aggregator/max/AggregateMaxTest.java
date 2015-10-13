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
 * <AggregateMaxTest>
 * <功能详细描述>
 * 
 */
public class AggregateMaxTest
{
    private AggregateMax agg = null;
    
    /**
     * Test AggregateCount(java.lang.Class).
     */
    @Test
    public void testAggregateMax()
    {
        try
        {
            agg = new AggregateMax(null);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            agg = new AggregateMax(String.class);
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
        agg = new AggregateMax(Long.class);
        
        assertEquals(Long.class, agg.cloneAggregate().getValueType());
        assertEquals(true, agg.cloneAggregate() instanceof AggregateMax);
    }
    
    /**
     * <testAggregateMaxFunction (enter, leave, getValue)>
     * <功能详细描述>
     */
    @Test
    public void testAggregateMaxFunction()
    {
        //Integer
        agg = new AggregateMax(Integer.class);
        assertEquals(Integer.class, agg.getValueType());
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.I_TEN);
        assertEquals(SupportConst.I_TEN, agg.getValue());
        
        agg.enter(SupportConst.I_TEN);
        assertEquals(SupportConst.I_TEN, agg.getValue());
        
        agg.enter(SupportConst.I_TWO);
        assertEquals(SupportConst.I_TEN, agg.getValue());
        
        agg.leave(SupportConst.I_TEN);
        assertEquals(SupportConst.I_TEN, agg.getValue());
        
        agg.leave(SupportConst.I_TEN);
        assertEquals(SupportConst.I_TWO, agg.getValue());
        
        agg.leave(SupportConst.I_TWO);
        assertEquals(null, agg.getValue());
        
        //Long
        agg = new AggregateMax(Long.class);
        assertEquals(Long.class, agg.getValueType());
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.L_TEN);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.enter(SupportConst.L_TEN);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.enter(SupportConst.L_TWO);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.leave(SupportConst.L_TEN);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.leave(SupportConst.L_TEN);
        assertEquals(SupportConst.L_TWO, agg.getValue());
        
        agg.leave(SupportConst.L_TWO);
        assertEquals(null, agg.getValue());
        
        //Double
        agg = new AggregateMax(Double.class);
        assertEquals(Double.class, agg.getValueType());
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.D_TEN);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.enter(SupportConst.D_TEN);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.enter(SupportConst.D_TWO);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.leave(SupportConst.D_TEN);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.leave(SupportConst.D_TEN);
        assertEquals(SupportConst.D_TWO, agg.getValue());
        
        agg.leave(SupportConst.D_TWO);
        assertEquals(null, agg.getValue());
        
        //Float
        agg = new AggregateMax(Float.class);
        assertEquals(Float.class, agg.getValueType());
        assertEquals(null, agg.getValue());
        
        agg.enter(SupportConst.F_TEN);
        assertEquals(SupportConst.F_TEN, agg.getValue());
        
        agg.enter(SupportConst.F_TEN);
        assertEquals(SupportConst.F_TEN, agg.getValue());
        
        agg.enter(SupportConst.F_TWO);
        assertEquals(SupportConst.F_TEN, agg.getValue());
        
        agg.leave(SupportConst.F_TEN);
        assertEquals(SupportConst.F_TEN, agg.getValue());
        
        agg.leave(SupportConst.F_TEN);
        assertEquals(SupportConst.F_TWO, agg.getValue());
        
        agg.leave(SupportConst.F_TWO);
        assertEquals(null, agg.getValue());
        
    }
    
}
