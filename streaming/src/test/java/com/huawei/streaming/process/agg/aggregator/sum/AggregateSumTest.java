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

package com.huawei.streaming.process.agg.aggregator.sum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

/**
 * <AggregateSumTest>
 * <功能详细描述>
 * 
 */
public class AggregateSumTest
{
    
    private static final double D_ONE = 1d;
    
    private static final double D_TWO = 2d;
    
    private static final double D_THREE = 3d;
    
    private static final float F_ONE = 1f;
    
    private static final float F_TWO = 2f;
    
    private static final float F_THREE = 3f;
    
    private static final long L_ONE = 1L;
    
    private static final long L_TWO = 2L;
    
    private static final long L_THREE = 3L;
    
    private static final int I_ONE = 1;
    
    private static final int I_TWO = 2;
    
    private static final int I_THREE = 3;
    
    private AggregateSum agg = null;
    
    /**
     * Test AggregateSum#AggregateSum(java.lang.Class).
     */
    @Test
    public void testAggregateSum()
    {
        try
        {
            agg = new AggregateSum(null);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            agg = new AggregateSum(String.class);
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
        agg = new AggregateSum(Double.class);
        
        assertEquals(Double.class, agg.cloneAggregate().getValueType());
        assertEquals(true, agg.cloneAggregate() instanceof AggregateSum);
    }
    
    /**
     * <testAggregateSumFunction (enter, leave, getValue)>
     * <功能详细描述>
     */
    @Test
    public void testAggregateSumFunction()
    {
        //Double
        agg = new AggregateSum(Double.class);
        assertEquals(Double.class, agg.getValueType());
        
        assertNull(agg.getValue());
        
        agg.enter(D_ONE, true);
        assertEquals(D_ONE, agg.getValue());
        
        agg.enter(D_TWO, true);
        assertEquals(D_THREE, agg.getValue());
        
        agg.leave(D_ONE, true);
        assertEquals(D_TWO, agg.getValue());
        
        agg.leave(D_TWO, true);
        assertNull(agg.getValue());
        
        //Float
        agg = new AggregateSum(Float.class);
        assertEquals(Float.class, agg.getValueType());
        
        assertNull(agg.getValue());
        
        agg.enter(F_ONE, true);
        assertEquals(F_ONE, agg.getValue());
        
        agg.enter(F_TWO, true);
        assertEquals(F_THREE, agg.getValue());
        
        agg.leave(F_ONE, true);
        assertEquals(F_TWO, agg.getValue());
        
        agg.leave(F_TWO, true);
        assertNull(agg.getValue());
        
        //Integer
        agg = new AggregateSum(Integer.class);
        assertEquals(Integer.class, agg.getValueType());
        
        assertNull(agg.getValue());
        
        agg.enter(I_ONE, true);
        assertEquals(I_ONE, agg.getValue());
        
        agg.enter(I_TWO, true);
        assertEquals(I_THREE, agg.getValue());
        
        agg.leave(I_ONE, true);
        assertEquals(I_TWO, agg.getValue());
        
        agg.leave(I_TWO, true);
        assertNull(agg.getValue());
        
        //Long
        agg = new AggregateSum(Long.class);
        assertEquals(Long.class, agg.getValueType());
        
        assertNull(agg.getValue());
        
        agg.enter(L_ONE, true);
        assertEquals(L_ONE, agg.getValue());
        
        agg.enter(L_TWO, true);
        assertEquals(L_THREE, agg.getValue());
        
        agg.leave(L_ONE, true);
        assertEquals(L_TWO, agg.getValue());
        
        agg.leave(L_TWO, true);
        assertNull(agg.getValue());
    }
    
}
