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

import org.junit.Assert;
import org.junit.Test;

import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.exception.StreamingException;
import com.huawei.streaming.support.SupportConst;

/**
 * <NotExpressionTest>
 * <功能详细描述>
 * 
 */
public class NotExpressionTest
{
    private IEvent theEvent = new TupleEvent();
    
    private IEvent[] theEvents = new IEvent[] {new TupleEvent()};
    
    private NotExpression notExpr = null;
    
    private Object actualResult = null;
    
    /**
     * Test NotExpression(IExpression).
     */
    @Test
    public void testNotExpression()
    {
        try
        {
            notExpr = new NotExpression(null);
            Assert.fail();
        }
        catch (StreamingException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            notExpr = new NotExpression(new ConstExpression(SupportConst.I_TWO));
            Assert.fail();
        }
        catch (StreamingException e)
        {
            Assert.assertTrue(true);
        }
        
    }
    
    /**
     * Test evaluate(IEvent).
     * @throws StreamingException 异常
     */
    @Test
    public void testEvaluateIEvent()
        throws StreamingException
    {
        notExpr = new NotExpression(new ConstExpression(false));
        actualResult = notExpr.evaluate(theEvent);
        Assert.assertEquals(true, actualResult);
        
        notExpr = new NotExpression(new ConstExpression(true));
        actualResult = notExpr.evaluate(theEvent);
        Assert.assertEquals(false, actualResult);
        
        notExpr = new NotExpression(new ConstExpression(null, Boolean.class));
        actualResult = notExpr.evaluate(theEvent);
        Assert.assertEquals(null, actualResult);
    }
    
    /**
     * Test evaluate(IEvent[]).
     * @throws StreamingException 异常
     */
    @Test
    public void testEvaluateIEventArray()
        throws StreamingException
    {
        notExpr = new NotExpression(new ConstExpression(false));
        actualResult = notExpr.evaluate(theEvents);
        Assert.assertEquals(true, actualResult);
        
        notExpr = new NotExpression(new ConstExpression(true));
        actualResult = notExpr.evaluate(theEvents);
        Assert.assertEquals(false, actualResult);
        
        notExpr = new NotExpression(new ConstExpression(null, Boolean.class));
        actualResult = notExpr.evaluate(theEvents);
        Assert.assertEquals(null, actualResult);
    }
    
    /**
     * Test getType().
     * @throws StreamingException 异常
     */
    @Test
    public void testGetType()
        throws StreamingException
    {
        notExpr = new NotExpression(new ConstExpression(false));
        Assert.assertEquals(Boolean.class, notExpr.getType());
    }
    
}
