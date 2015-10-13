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

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * 数据类型转换测试
 *
 */
public class ToDecimalTest
{

    /**
     * 测试数据类型转换
     */
    @Test
    public void testEvaluate()
    {
        ToDecimal toDecimal = new ToDecimal(null);
        assertTrue(toDecimal.evaluate(1).toString().equals("1"));
        assertTrue(toDecimal.evaluate(1F).toString().equals("1.0"));
        assertTrue(toDecimal.evaluate(1.0f).toString().equals("1.0"));
        assertTrue(toDecimal.evaluate(1.9d).toString().equals("1.9"));
        assertTrue(toDecimal.evaluate("1").toString().equals("1.0"));
        assertTrue(toDecimal.evaluate("1.9").toString().equals("1.9"));
    }

    /**
     * 测试数据类型转换
     */
    @Test
    public void testEvaluate2()
    {
        ToDecimal toDecimal = new ToDecimal(null);
        assertTrue(toDecimal.evaluate(1L,2).toString().equals("0.01"));
        assertTrue(toDecimal.evaluate(100000L,2).toString().equals("1000.00"));
    }
}
