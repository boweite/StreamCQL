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
 * 获取时间内的分钟数测试用例
 * 
 */
public class SecondTest
{
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluate()
    {
        Second udf = new Second(null);
        assertTrue(udf.evaluate("2013-10-17 09:58:00.111") == 0);
        assertTrue(udf.evaluate("2013-10-17 09:58:00") == 0);
        assertTrue(udf.evaluate("09:58:00") == 0);
        assertTrue(udf.evaluate("24:58:60") == null);
        assertTrue(udf.evaluate("00:58:00") == 0);
        assertTrue(udf.evaluate("00:00:00") == 0);
        assertTrue(udf.evaluate("23:59:59") == 59);
        assertTrue(udf.evaluate("24:00:00") == null);
        assertTrue(udf.evaluate("2013-10-17") == null);
    }
    
}
