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

package com.huawei.streaming.udfs;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import org.junit.Test;

import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.exception.ErrorCode;
import com.huawei.streaming.exception.StreamingRuntimeException;
import com.huawei.streaming.expression.ConstExpression;
import com.huawei.streaming.expression.IExpression;
import com.huawei.streaming.expression.MethodExpression;

/**
 * 绝对值测试
 * 
 */
public class AbsTest
{
    /**
     * 测试int值
     */
    @Test
    public void testEvaluateInteger()
    {
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate", new IExpression[] {new ConstExpression(-1)});
        
        Integer value = (Integer)methodExpression.evaluate(new TupleEvent());
        assertTrue(value.equals(1));
    }
    
    /**
     * 测试int值
     */
    @Test
    public void testEvaluateInteger2()
    {
        /**
         * Integer的最小值执行abs会溢出，之后会转成原来的值。
         */
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate", new IExpression[] {new ConstExpression(Integer.MIN_VALUE)});
        
        Integer value = (Integer)methodExpression.evaluate(new TupleEvent());
        assertTrue(value.equals(Integer.MIN_VALUE));
    }
    
    /**
     * 测试Long值
     */
    @Test
    public void testEvaluateLong()
    {
        
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate", new IExpression[] {new ConstExpression(-1l)});
        
        Long value = (Long)methodExpression.evaluate(new TupleEvent());
        assertTrue(value.equals(1l));
    }
    
    /**
     * 测试Float值
     */
    @Test
    public void testEvaluateFloat()
    {
        
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate", new IExpression[] {new ConstExpression(-1.0f)});
        
        Float value = (Float)methodExpression.evaluate(new TupleEvent());
        assertTrue(value.equals(1.0f));
    }
    
    /**
     * 测试double值
     */
    @Test
    public void testEvaluateDouble()
    {
        
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate", new IExpression[] {new ConstExpression(-1.0d)});
        
        Double value = (Double)methodExpression.evaluate(new TupleEvent());
        assertTrue(value.equals(1.0d));
    }
    
    /**
     * 测试BigDecimal值
     */
    @Test
    public void testEvaluateBigDecimal()
    {
        
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate", new IExpression[] {new ConstExpression(
                new BigDecimal(-1))});
        
        BigDecimal value = (BigDecimal)methodExpression.evaluate(new TupleEvent());
        assertTrue(value.intValue() == 1);
    }
    
    /**
     * 测试BigDecimal值
     */
    @Test
    public void testEvaluateString()
    {
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate", new IExpression[] {new ConstExpression("-1")});
        
        try
        {
            methodExpression.evaluate(new TupleEvent());
            fail("Expected an StreamingRuntimeException to be thrown");
        }
        catch (StreamingRuntimeException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.FUNCTION_UNSUPPORTED_PARAMETERS);
        }
        
    }
    
    /**
     * 测试BigDecimal值
     */
    @Test
    public void testEvaluateBoolean()
    {
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate", new IExpression[] {new ConstExpression(true)});
        
        try
        {
            methodExpression.evaluate(new TupleEvent());
            fail("Expected an StreamingRuntimeException to be thrown");
        }
        catch (StreamingRuntimeException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.FUNCTION_UNSUPPORTED_PARAMETERS);
        }
        
    }
    
    /**
     * 测试BigDecimal值
     */
    @Test
    public void testEvaluateTime()
    {
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate", new IExpression[] {new ConstExpression(new Time(0))});
        
        try
        {
            methodExpression.evaluate(new TupleEvent());
            fail("Expected an StreamingRuntimeException to be thrown");
        }
        catch (StreamingRuntimeException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.FUNCTION_UNSUPPORTED_PARAMETERS);
        }
        
    }
    
    /**
     * 测试BigDecimal值
     */
    @Test
    public void testEvaluateDate()
    {
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate", new IExpression[] {new ConstExpression(new Date(0))});
        
        try
        {
            methodExpression.evaluate(new TupleEvent());
            fail("Expected an StreamingRuntimeException to be thrown");
        }
        catch (StreamingRuntimeException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.FUNCTION_UNSUPPORTED_PARAMETERS);
        }
        
    }
    
    /**
     * 测试BigDecimal值
     */
    @Test
    public void testEvaluateTimeStamp()
    {
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate",
                new IExpression[] {new ConstExpression(new Timestamp(0))});
        
        try
        {
            methodExpression.evaluate(new TupleEvent());
            fail("Expected an StreamingRuntimeException to be thrown");
        }
        catch (StreamingRuntimeException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.FUNCTION_UNSUPPORTED_PARAMETERS);
        }
        
    }
    
    /**
     * 测试BigDecimal值
     */
    @Test
    public void testEvaluateNull()
    {
        MethodExpression methodExpression =
            new MethodExpression(new Abs(null), "evaluate", new IExpression[] {new ConstExpression(null)});
        
        assertNull(methodExpression.evaluate(new TupleEvent()));
    }

    /**
     * 测试int值
     */
    @Test
    public void testEvaluate()
    {
        System.out.println("Integer max value : " + Integer.MAX_VALUE + ", Integer min value " + Integer.MIN_VALUE);
        Abs abs = new Abs(null);
        assertTrue(abs.evaluate(1).equals(1));
        assertTrue(abs.evaluate(-1).equals(1));
        assertTrue(abs.evaluate(0).equals(0));
        assertTrue(abs.evaluate(-0).equals(0));
        assertTrue(abs.evaluate(Integer.MAX_VALUE).equals(Integer.MAX_VALUE));
        assertTrue(abs.evaluate(Integer.MIN_VALUE).equals(Integer.MIN_VALUE));


        System.out.println("Long max value : " + Long.MAX_VALUE + ", Long min value " + Long.MIN_VALUE);
        assertTrue(abs.evaluate(1L).equals(1L));
        assertTrue(abs.evaluate(-1L).equals(1L));
        assertTrue(abs.evaluate(0L).equals(0L));
        assertTrue(abs.evaluate(-0L).equals(0L));
        assertTrue(abs.evaluate(Long.MAX_VALUE).equals(Long.MAX_VALUE));
        assertTrue(abs.evaluate(Long.MIN_VALUE).equals(Long.MIN_VALUE));

        assertTrue(abs.evaluate(1.0f).equals(1.0f));
        assertTrue(abs.evaluate(-1.0f).equals(1.0f));
        assertTrue(abs.evaluate(0.0f).equals(0.0f));
        assertTrue(abs.evaluate(-0.0f).equals(0.0f));

        assertTrue(abs.evaluate(1.0d).equals(1.0d));
        assertTrue(abs.evaluate(-1.0d).equals(1.0d));
        assertTrue(abs.evaluate(0.0d).equals(0.0d));
        assertTrue(abs.evaluate(-0.0d).equals(0.0d));

        assertTrue(abs.evaluate(new BigDecimal("1.0")).intValue() == 1);
        assertTrue(abs.evaluate(new BigDecimal("-1.0")).intValue() == 1);
        assertTrue(abs.evaluate(new BigDecimal("0.0")).intValue() == 0);
        assertTrue(abs.evaluate(new BigDecimal("-0.0")).intValue() == 0);
    }
}
