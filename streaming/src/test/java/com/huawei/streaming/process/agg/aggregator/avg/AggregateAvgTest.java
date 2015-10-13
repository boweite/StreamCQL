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

package com.huawei.streaming.process.agg.aggregator.avg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import com.huawei.streaming.support.SupportConst;

/**
 * <AggregateAvgTest>
 * <功能详细描述>
 * 
 */
public class AggregateAvgTest
{
    private AggregateAvg agg = null;
    
    /**
     * <testAggregateAvg>
     * <功能详细描述>
     */
    @Test
    public void testAggregateAvg()
    {
        try
        {
            agg = new AggregateAvg(null);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            agg = new AggregateAvg(String.class);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertTrue(true);
        }
    }
    
    /**
     * <testCloneAggregate>
     * <功能详细描述>
     */
    @Test
    public void testCloneAggregate()
    {
        agg = new AggregateAvg(Double.class);
        
        assertEquals(Double.class, agg.cloneAggregate().getValueType());
        assertEquals(true, agg.cloneAggregate() instanceof AggregateAvg);
    }
    
    /**
     * <testAggregateAvgFunction (enter, leave, getValue)>
     * <功能详细描述>
     */
    @Test
    public void testAggregateAvgFunction()
    {
        //Double
        agg = new AggregateAvg(Double.class);
        assertEquals(Double.class, agg.getValueType());
        
        assertNull(agg.getValue());
        
        agg.enter(SupportConst.I_TEN, true);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.enter(SupportConst.I_TWO, true);
        assertEquals(SupportConst.D_SIX, agg.getValue());
        
        agg.leave(SupportConst.I_TWO, true);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.leave(SupportConst.I_TEN, true);
        assertNull(agg.getValue());
        
        //Float
        agg = new AggregateAvg(Float.class);
        assertEquals(Double.class, agg.getValueType());
        
        assertNull(agg.getValue());
        
        agg.enter(SupportConst.I_TEN, true);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.enter(SupportConst.I_TWO, true);
        assertEquals(SupportConst.D_SIX, agg.getValue());
        
        agg.leave(SupportConst.I_TWO, true);
        assertEquals(SupportConst.D_TEN, agg.getValue());
        
        agg.leave(SupportConst.I_TEN, true);
        assertNull(agg.getValue());
        
        //Integer
        agg = new AggregateAvg(Integer.class);
        assertEquals(Long.class, agg.getValueType());
        
        assertNull(agg.getValue());
        
        agg.enter(SupportConst.I_TEN, true);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.enter(SupportConst.I_THREE, true);
        assertEquals(SupportConst.L_SIX, agg.getValue());
        
        agg.leave(SupportConst.I_THREE, true);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.leave(SupportConst.I_TEN, true);
        assertNull(agg.getValue());
        
        //Long
        agg = new AggregateAvg(Long.class);
        assertEquals(Long.class, agg.getValueType());
        
        assertNull(agg.getValue());
        
        agg.enter(SupportConst.I_TEN, true);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.enter(SupportConst.I_TWO, true);
        assertEquals(SupportConst.L_SIX, agg.getValue());
        
        agg.leave(SupportConst.I_TWO, true);
        assertEquals(SupportConst.L_TEN, agg.getValue());
        
        agg.leave(SupportConst.I_TEN, true);
        assertNull(agg.getValue());
    }
    
}
