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
public class ToDoubleTest
{

    /**
     * 测试数据类型转换
     */
    @Test
    public void testEvaluate()
    {
        ToDouble toDouble = new ToDouble(null);
        ToDate toDate = new ToDate(null);
        ToTime toTime = new ToTime(null);
        ToTimeStamp toTimeStamp = new ToTimeStamp(null);
        ToDecimal toDecimal = new ToDecimal(null);
        assertTrue(toDouble.evaluate(1).equals(1.0D));
        assertTrue(toDouble.evaluate(1F).equals(1.0D));
        assertTrue(toDouble.evaluate(1.0f).equals(1.0D));
        assertTrue(toDouble.evaluate(1.9d).equals(1.9D));
        assertTrue(toDouble.evaluate("1").equals(1.0D));
        assertTrue(toDouble.evaluate("1.9").equals(1.9D));
        assertTrue(toDouble.evaluate(toDecimal.evaluate("1.9")).equals(1.9D));
        assertTrue(toDouble.evaluate(toDate.evaluate("1970-01-01")).equals(-28800000.0D));
        assertTrue(toDouble.evaluate(toTime.evaluate("15:40:00")).equals(27600000.0D));
        assertTrue(toDouble.evaluate(toTimeStamp.evaluate("1970-01-01 15:40:00.000000")).equals(27600000.0D));
    }
}
