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

import java.io.StringWriter;
import java.lang.reflect.Constructor;

/**
 * <断言帮助类>
 * <功能详细描述>
 * 
 */
public class SupportHelper
{
    private static final String JUNIT_ASSERTIONFAILED_ERROR = "org.junit.AssertionFailedError";
    
    /**
     * Assert a condition is false.
     * @param condition to assert
     */
    public static void assertFalse(boolean condition)
    {
        assertTrue(!condition);
    }
    
    /**
     * Assert a condition is true.
     * @param condition to assert
     */
    public static void assertTrue(boolean condition)
    {
        assertTrue(null, condition);
    }
    
    /**
     * Assert a condition is true.
     * @param message an optional message
     * @param condition to assert
     */
    public static void assertTrue(String message, boolean condition)
    {
        if (!condition)
        {
            fail(message);
        }
    }
    
    /**
     * Assert a condition is false.
     * @param message an optional message
     * @param condition to assert
     */
    public static void assertFalse(String message, boolean condition)
    {
        if (condition)
        {
            fail(message);
        }
    }
    
    /**
     * Assert that two values equal.
     * @param message an optional message
     * @param expected expected value
     * @param actual actual value
     */
    public static void assertEquals(String message, Object expected, Object actual)
    {
        if (expected == null && actual == null)
        {
            return;
        }
        if (expected != null && expected.equals(actual))
        {
            return;
        }
        failNotEquals(message, expected, actual);
    }
    
    /**
     * Assert that two values equal.
     * @param expected expected value
     * @param actual actual value
     */
    public static void assertEquals(Object expected, Object actual)
    {
        assertEquals(null, expected, actual);
    }
    
    /**
     * Fail assertion.
     */
    public static void fail()
    {
        fail(null);
    }
    
    /**
     * Assert that two values are the same.
     * @param message an optional message
     * @param expected expected value
     * @param actual actual value
     */
    public static void assertSame(String message, Object expected, Object actual)
    {
        if (expected == actual)
        {
            return;
        }
        failNotSame(message, expected, actual);
    }
    
    /**
     * Assert that two values are the same.
     * @param expected expected value
     * @param actual actual value
     */
    public static void assertSame(Object expected, Object actual)
    {
        if (expected == actual)
        {
            return;
        }
        failNotSame(null, expected, actual);
    }
    
    /**
     * Assert that a value is null.
     * @param message an optional message
     * @param object the object to check
     */
    public static void assertNull(String message, Object object)
    {
        assertTrue(message, object == null);
    }
    
    /**
     * Assert that a value is not null.
     * @param object the object to check
     */
    public static void assertNotNull(Object object)
    {
        assertTrue(object != null);
    }
    
    /**
     * Assert that a value is null.
     * @param object the object to check
     */
    public static void assertNull(Object object)
    {
        assertTrue(object == null);
    }
    
    /**
     * Fail assertion formatting a message for not-same.
     * @param message an optional message
     * @param expected expected value
     * @param actual actual value
     */
    public static void failNotSame(String message, Object expected, Object actual)
    {
        fail(format(message, expected, actual, true));
    }
    
    /**
     * Fail assertion formatting a message for not-equals.
     * @param message an optional message
     * @param expected expected value
     * @param actual actual value
     */
    public static void failNotEquals(String message, Object expected, Object actual)
    {
        fail(format(message, expected, actual, false));
    }
    
    /**
     * Fail assertion.
     * @param message an optional message
     */
    public static void fail(String message)
    {
        
        // Find JUnit Assert class in classpath
        Class< ? > junitAssertionFailedError = null;
        try
        {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            junitAssertionFailedError = Class.forName(JUNIT_ASSERTIONFAILED_ERROR, true, cl);
        }
        catch (ClassNotFoundException ex)
        {
            throw new AssertionError();
        }
        
        // no JUnit found
        if (junitAssertionFailedError == null)
        {
            throw new AssertionError("Failed assertion and no JUnit found in classpath: " + message);
        }
        
        // throw JUnit AssertionFailedError instead, to be consistent with code that uses JUnit to assert
        Constructor< ? > ctor;
        try
        {
            ctor = junitAssertionFailedError.getConstructor(new Class[] {String.class});
        }
        catch (NoSuchMethodException e)
        {
            throw new AssertionError("Failed to find JUnit method 'fail' method: " + e.getMessage());
        }
        
        try
        {
            throw (AssertionError)ctor.newInstance(new Object[] {message});
        }
        catch (Exception e)
        {
            throw new AssertionError("Failed to call ctor of '" + JUNIT_ASSERTIONFAILED_ERROR + "': " + e.getMessage());
        }
    }
    
    private static String format(String message, Object expected, Object actual, boolean isSame)
    {
        StringWriter buf = new StringWriter();
        if (message != null && !message.isEmpty())
        {
            buf.append(message);
            buf.append(' ');
        }
        buf.append("expected");
        if (isSame)
        {
            buf.append(" same");
        }
        buf.append(":<");
        buf.append(expected == null ? "null" : expected.toString());
        buf.append("> but was:<");
        buf.append(actual == null ? "null" : actual.toString());
        buf.append(">");
        return buf.toString();
    }
}
