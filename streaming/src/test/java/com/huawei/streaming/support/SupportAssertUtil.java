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

package com.huawei.streaming.support;

import java.lang.reflect.Array;

import org.junit.Assert;

import com.huawei.streaming.common.MultiKey;
import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.IEventType;

/**
 * <断言工具支持类>
 * <功能详细描述>
 * 
 */
public class SupportAssertUtil
{
    /**
     * <数据是否一致>
     * <功能详细描述>
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEqualsExactOrder(Object[] expected, Object[] actual)
    {
        if (compareArraySize(expected, actual))
        {
            return;
        }
        for (int i = 0; i < expected.length; i++)
        {
            Object value = actual[i];
            Object expectedValue = expected[i];
            assertEquals("Failed to assert at element " + i, expectedValue, value);
        }
    }
    
    private static boolean compareArraySize(Object expected, Object actual)
    {
        if ((expected == null) && (actual == null || Array.getLength(actual) == 0))
        {
            return true;
        }
        if (expected == null || actual == null)
        {
            if (expected == null)
            {
                SupportHelper.assertNull("Expected is null but actual is not null", actual);
            }
            SupportHelper.assertNull("Actual is null but expected is not null", expected);
        }
        else
        {
            int expectedLength = Array.getLength(expected);
            int actualLength = Array.getLength(actual);
            SupportHelper.assertEquals("Mismatch in the number of expected and actual number of values asserted",
                expectedLength,
                actualLength);
        }
        return false;
    }
    
    private static void assertEquals(String message, Object expected, Object actual)
    {
        SupportHelper.assertEquals(message, expected, actual);
    }
    
    /**
     * 对比IEvent事件数组是否值相同
     * <功能详细描述>
     * @param expected 期望IEvent事件数组
     * @param actual 实际IEvent事件数组
     */
    public static void assertEuqalsArrayValue(IEvent[] expected, IEvent[] actual)
    {
        if (compareArraySize(expected, actual))
        {
            return;
        }
        for (int i = 0; i < expected.length; i++)
        {
            IEvent value = actual[i];
            IEvent expectedValue = expected[i];
            Assert.assertEquals(expectedValue.getStreamName(), value.getStreamName());
            
            assertIEventEqualsValue(expectedValue, value);
            //            Object[] actvalue = value.getAllValues();
            //            Object[] expvalue = expectedValue.getAllValues();
            //            
            //            for (int j = 0; j < actvalue.length; j++)
            //            {
            //                Assert.assertEquals(actvalue[i], expvalue[i]);
            //            }
        }
    }
    
    /**
     * 对比IEvent事件是否值相同
     * <功能详细描述>
     * @param expected 期望事件
     * @param actual 实际事件
     */
    public static void assertIEventEqualsValue(IEvent expected, IEvent actual)
    {
        IEventType actualtype = actual.getEventType();
        IEventType expectedtype = expected.getEventType();
        Assert.assertEquals(expectedtype, actualtype);
        
        Object[] actvalue = actual.getAllValues();
        Object[] expvalue = expected.getAllValues();
        
        for (int i = 0; i < actvalue.length; i++)
        {
            Assert.assertEquals(actvalue[i], expvalue[i]);
        }
    }
    
    /**
     * 对比Multikey是否值相同
     * <功能详细描述>
     * @param expected 期望Multikey
     * @param actual 实际Multikey
     */
    public static void assertMultikeyEqualsValue(MultiKey expected, MultiKey actual)
    {
        Object[] exobj = expected.getKeys();
        Object[] acobj = actual.getKeys();
        
        Assert.assertEquals(exobj.length, acobj.length);
        
        for (int i = 0; i < acobj.length; i++)
        {
            assertIEventEqualsValue((IEvent)exobj[i], (IEvent)acobj[i]);
        }
    }
    
    /**
     * 对比Multikey数组是否值相同
     * <功能详细描述>
     * @param expected 期望Multikey数组
     * @param actual 实际Multikey数组
     */
    public static void assertEuqalsArrayValue(MultiKey[] expected, MultiKey[] actual)
    {
        if (compareArraySize(expected, actual))
        {
            return;
        }
        for (int i = 0; i < expected.length; i++)
        {
            MultiKey value = actual[i];
            MultiKey expectedValue = expected[i];
            
            assertMultikeyEqualsValue(value, expectedValue);
        }
    }
}
