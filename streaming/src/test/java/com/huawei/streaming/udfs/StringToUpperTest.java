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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 字符串转大写测试用例
 * 
 */
public class StringToUpperTest
{
    /**
     * 测试用例
     */
    @Test
    public void testEvaluate()
    {
        String s = null;
        StringToUpper udf = new StringToUpper(null);
        assertTrue(udf.evaluate(s) == null);
        s = "";
        assertTrue(udf.evaluate(s) != null);
        assertTrue(udf.evaluate(s).equals(""));
        s = "AbC";
        assertTrue(udf.evaluate(s).equals("ABC"));
        s = " AbC";
        assertTrue(udf.evaluate(s).equals(" ABC"));
        s = " A b C";
        assertTrue(udf.evaluate(s).equals(" A B C"));

        s = "12345\t";
        assertTrue(udf.evaluate(s).equals("12345\t"));
    }
}
