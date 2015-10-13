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

package com.huawei.streaming.expression;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.IEventType;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.exception.StreamingException;
import com.huawei.streaming.support.SupportEventMng;

/**
 * <ExprIsNullExpressionTest>
 * <功能详细描述>
 * 
 */
public class ExprIsNullExpressionTest
{
    private IExpression expr;
    
    private IExpression toCompare;
    
    private SupportEventMng mng = null;
    
    private IEventType eventType = null;
    
    /** <setup>
     * <功能详细描述>
     * @throws java.lang.Exception Exception
     */
    @Before
    public void setUp()
        throws Exception
    {
        toCompare = new PropertyValueExpression("a", Integer.class);
        mng = new SupportEventMng();
        eventType = mng.getInput();
    }
    
    /** <cleanup>
     * <功能详细描述>
     * @throws java.lang.Exception Exception
     */
    @After
    public void tearDown()
        throws Exception
    {
        toCompare = null;
        mng = null;
        eventType = null;
    }
    
    /**
     * testExprIsNullExpression
     * 
     */
    @Test
    public void testExprIsNullExpression()
    {
        try
        {
            expr = new ExprIsNullExpression(null, true);
            fail();
        }
        catch (StreamingException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            expr = new ExprIsNullExpression(toCompare, null);
            fail();
        }
        catch (StreamingException e)
        {
            Assert.assertTrue(true);
        }
    }
    
    /**
     * testEvaluate(IEvent)
     * @throws StreamingException 异常
     */
    @Test
    public void testEvaluateIEvent()
        throws StreamingException
    {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("a", 1);
        values.put("b", 1);
        values.put("c", "c1");
        
        IEvent event1 = new TupleEvent("stream", eventType, values);
        
        values = new HashMap<String, Object>();
        values.put("a", null);
        values.put("b", 1);
        values.put("c", "c1");
        
        IEvent event2 = new TupleEvent("stream", eventType, values);
        
        expr = new ExprIsNullExpression(toCompare, true);
        Assert.assertEquals(false, expr.evaluate(event1));
        Assert.assertEquals(true, expr.evaluate(event2));
        
        expr = new ExprIsNullExpression(toCompare, false);
        Assert.assertEquals(true, expr.evaluate(event1));
        Assert.assertEquals(false, expr.evaluate(event2));
    }
    
    /**
     * testEvaluate(IEvent[])
     * @throws StreamingException 异常
     */
    @Test
    public void testEvaluateIEventArray()
        throws StreamingException
    {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("a", 1);
        values.put("b", 1);
        values.put("c", "c1");
        
        IEvent event1 = new TupleEvent("stream", eventType, values);
        
        values = new HashMap<String, Object>();
        values.put("a", null);
        values.put("b", 1);
        values.put("c", "c1");
        
        IEvent event2 = new TupleEvent("stream", eventType, values);
        
        expr = new ExprIsNullExpression(toCompare, true);
        Assert.assertEquals(false, expr.evaluate(new IEvent[] {event1, null}));
        Assert.assertEquals(true, expr.evaluate(new IEvent[] {event2, null}));
        
        expr = new ExprIsNullExpression(toCompare, false);
        Assert.assertEquals(true, expr.evaluate(new IEvent[] {event1, null}));
        Assert.assertEquals(false, expr.evaluate(new IEvent[] {event2, null}));
    }
    
    /**
     * testGetType
     * @throws StreamingException 异常
     */
    @Test
    public void testGetType()
        throws StreamingException
    {
        expr = new ExprIsNullExpression(toCompare, true);
        Assert.assertEquals(Boolean.class, expr.getType());
    }
    
}
