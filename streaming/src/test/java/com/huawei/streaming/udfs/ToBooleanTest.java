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

/**
 * UDFtoBoolean 数据类型转换测试
 * 
 */
public class ToBooleanTest
{
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluateInteger()
    {
        Integer i = null;
        ToBoolean udf = new ToBoolean(null);
        assertEquals(udf.evaluate(0), false);
        assertEquals(udf.evaluate(1), true);
        assertEquals(udf.evaluate(2), true);
        assertEquals(udf.evaluate(i), null);
    }
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluateLong()
    {
        Long i = null;
        ToBoolean udf = new ToBoolean(null);
        assertEquals(udf.evaluate(0l), false);
        assertEquals(udf.evaluate(1l), true);
        assertEquals(udf.evaluate(2l), true);
        assertEquals(udf.evaluate(i), null);
    }
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluateFloat()
    {
        Float i = null;
        ToBoolean udf = new ToBoolean(null);
        assertEquals(udf.evaluate(0F), false);
        assertEquals(udf.evaluate(1f), true);
        assertEquals(udf.evaluate(2f), true);
        assertEquals(udf.evaluate(0.1f), false);
        assertEquals(udf.evaluate(0.4f), false);
        assertEquals(udf.evaluate(0.5f), false);
        assertEquals(udf.evaluate(0.9f), false);
        assertEquals(udf.evaluate(-0.9f), false);
        assertEquals(udf.evaluate(i), null);
        //新加测试用例，和数据库有些不一致，需要讨论如何修改
        //TODO 待讨论修改
        assertEquals(udf.evaluate(0.9f), false);
    }
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluateDouble()
    {
        Double i = null;
        ToBoolean udf = new ToBoolean(null);
        assertEquals(udf.evaluate(0d), false);
        assertEquals(udf.evaluate(1D), true);
        assertEquals(udf.evaluate(2d), true);
        assertEquals(udf.evaluate(i), null);
        //新加测试用例，和数据库有些不一致，需要讨论如何修改
        //TODO 待讨论修改
        assertEquals(udf.evaluate(0.9d), false);
    }
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluateBigDecimal()
    {
        BigDecimal i = null;
        ToBoolean udf = new ToBoolean(null);
        assertEquals(udf.evaluate(new BigDecimal(0)), false);
        assertEquals(udf.evaluate(new BigDecimal(0.0)), false);
        assertEquals(udf.evaluate(new BigDecimal(1)), true);
        assertEquals(udf.evaluate(new BigDecimal(1.0)), true);
        assertEquals(udf.evaluate(new BigDecimal(2.0)), true);
        assertEquals(udf.evaluate(i), null);
    }
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluateString()
    {
        Double i = null;
        ToBoolean udf = new ToBoolean(null);
        assertEquals(udf.evaluate(""), false);
        assertEquals(udf.evaluate("0"), true);
        assertEquals(udf.evaluate("1"), true);
        assertEquals(udf.evaluate("true"), true);
        assertEquals(udf.evaluate("false"), true);
        assertEquals(udf.evaluate(i), null);
    }
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluateTime()
    {
        Time i = null;
        ToBoolean udf = new ToBoolean(null);
        assertEquals(udf.evaluate(new Time(0)), false);
        assertEquals(udf.evaluate(new Time(1)), true);
        assertEquals(udf.evaluate(new Time(2)), true);
        assertEquals(udf.evaluate(i), null);
    }
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluateDate()
    {
        Date i = null;
        ToBoolean udf = new ToBoolean(null);
        assertEquals(udf.evaluate(new Date(0)), false);
        assertEquals(udf.evaluate(new Date(1)), true);
        assertEquals(udf.evaluate(new Date(2)), true);
        assertEquals(udf.evaluate(i), null);
    }
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluateTimestamp()
    {
        Timestamp i = null;
        ToBoolean udf = new ToBoolean(null);
        assertEquals(udf.evaluate(new Timestamp(0)), false);
        assertEquals(udf.evaluate(new Timestamp(1)), true);
        assertEquals(udf.evaluate(new Timestamp(2)), true);
        assertEquals(udf.evaluate(i), null);
    }
    
}
