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

package com.huawei.streaming.config;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.huawei.streaming.exception.ErrorCode;
import com.huawei.streaming.exception.StreamingException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * StreamingConfig测试用例
 *
 */
public class StreamingConfigTest
{
    private static StreamingConfig config;

    private static String CONF_VAR = "test";

    /**
     * 测试类启动之前的初始化
     *
     * @throws Exception 初始化异常
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        config = new StreamingConfig();
    }

    /**
     * 测试类启动之后的清理
     *
     * @throws Exception 清理异常
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        config = null;
    }


    /**
     * 测试前准备
     *
     * @throws Exception 测试异常
     */
    @Before
    public void setUp() throws Exception
    {

    }

    /**
     * 测试后清理
     *
     * @throws Exception 测试异常
     */
    @After
    public void tearDown() throws Exception
    {
        config.remove(CONF_VAR);
    }

    /**
     * 测试int类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetIntValue() throws Exception
    {
        config.put(CONF_VAR, "1");
        assertTrue(config.getIntValue(CONF_VAR) == 1);
    }

    /**
     * 测试int类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetIntValue2() throws Exception
    {
        config.put(CONF_VAR, "1L");
        try
        {
            assertFalse(config.getIntValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_FORMAT);
        }
    }

    /**
     * 测试int类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetIntValue3() throws Exception
    {
        config.put(CONF_VAR, "1d");
        try
        {
            assertFalse(config.getIntValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_FORMAT);
        }
    }

    /**
     * 测试int类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetIntValue4() throws Exception
    {
        config.put(CONF_VAR, "1x");
        try
        {
            assertFalse(config.getIntValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_FORMAT);
        }
    }

    /**
     * 测试int类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetIntValue5() throws Exception
    {
        try
        {
            assertFalse(config.getIntValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_NOT_FOUND);
        }
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetLongValue() throws Exception
    {
        config.put(CONF_VAR, "1");
        assertTrue(config.getLongValue(CONF_VAR) == 1l);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetLongValue2() throws Exception
    {
        config.put(CONF_VAR, "1");
        assertTrue(config.getLongValue(CONF_VAR) == 1);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetLongValue3() throws Exception
    {
        config.put(CONF_VAR, "1x");
        try
        {
            assertFalse(config.getIntValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_FORMAT);
        }
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetLongValue4() throws Exception
    {
        try
        {
            assertFalse(config.getIntValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_NOT_FOUND);
        }
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetDoubleValue() throws Exception
    {
        config.put(CONF_VAR, "1.0");
        assertTrue(config.getDoubleValue(CONF_VAR) == 1d);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetDoubleValue2() throws Exception
    {
        config.put(CONF_VAR, "1");
        assertTrue(config.getDoubleValue(CONF_VAR) == 1.0d);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetDoubleValue3() throws Exception
    {
        config.put(CONF_VAR, "1x");
        try
        {
            assertFalse(config.getDoubleValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_FORMAT);
        }
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetDoubleValue4() throws Exception
    {
        try
        {
            assertFalse(config.getDoubleValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_NOT_FOUND);
        }
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetFloatValue() throws Exception
    {
        config.put(CONF_VAR, "1.0");
        assertTrue(config.getFloatValue(CONF_VAR) == 1.0f);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetFloatValue2() throws Exception
    {
        config.put(CONF_VAR, "1");
        assertTrue(config.getLongValue(CONF_VAR) == 1.0f);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetFloatValue3() throws Exception
    {
        config.put(CONF_VAR, "1x");
        try
        {
            assertFalse(config.getIntValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_FORMAT);
        }
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetFloatValue4() throws Exception
    {
        try
        {
            assertFalse(config.getIntValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_NOT_FOUND);
        }
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetStringValue() throws Exception
    {
        config.put(CONF_VAR, "1");
        assertEquals(config.getStringValue(CONF_VAR), "1");
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetStringValue2() throws Exception
    {
        try
        {
            assertFalse(config.getIntValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_NOT_FOUND);
        }
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetBooleanValue() throws Exception
    {
        config.put(CONF_VAR, "1");
        assertTrue(config.getBooleanValue(CONF_VAR) == false);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetBooleanValue2() throws Exception
    {
        config.put(CONF_VAR, "0");
        assertTrue(config.getBooleanValue(CONF_VAR) == false);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetBooleanValue3() throws Exception
    {
        config.put(CONF_VAR, "2");
        assertTrue(config.getBooleanValue(CONF_VAR) == false);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetBooleanValue4() throws Exception
    {
        config.put(CONF_VAR, "true");
        assertTrue(config.getBooleanValue(CONF_VAR) == true);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetBooleanValue5() throws Exception
    {
        config.put(CONF_VAR, "false");
        assertTrue(config.getBooleanValue(CONF_VAR) == false);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetBooleanValue6() throws Exception
    {
        config.put(CONF_VAR, "abc");
        assertTrue(config.getBooleanValue(CONF_VAR) == false);
    }

    /**
     * 测试类型转换
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetBooleanValue7() throws Exception
    {
        try
        {
            assertFalse(config.getIntValue(CONF_VAR) == 1);
            fail("Expected an StreamingException to be thrown");
        }
        catch (StreamingException e)
        {
            assertEquals(e.getErrorCode(), ErrorCode.CONFIG_NOT_FOUND);
        }
    }
}
