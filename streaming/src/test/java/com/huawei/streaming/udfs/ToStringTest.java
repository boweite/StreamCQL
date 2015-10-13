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

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * UDFtoBoolean 数据类型转换测试
 * 
 */
public class ToStringTest
{
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluate()
    {
        ToString udf = new ToString(null);
        ToTimeStamp toTimeStamp = new ToTimeStamp(null);
        ToDate toDate = new ToDate(null);
        ToTime toTime = new ToTime(null);

        assertEquals(udf.evaluate(true), "true");
        assertEquals(udf.evaluate(false), "false");
        assertEquals(udf.evaluate(null), null);
        assertEquals(udf.evaluate(1), "1");
        assertEquals(udf.evaluate(1L), "1");
        assertEquals(udf.evaluate(1.0F), "1.0");
        assertEquals(udf.evaluate(1.0D), "1.0");
        assertEquals(udf.evaluate(new BigDecimal("1.0")), "1.0");
        assertEquals(udf.evaluate(toTime.evaluate("15:40:00")), "15:40:00");
        assertEquals(udf.evaluate(toDate.evaluate("2013-10-17")), "2013-10-17");
        assertEquals(udf.evaluate(toDate.evaluate("2013-1-17")), "2013-01-17");
        //2014-09-25 17:07:00.56
        assertTrue(udf.evaluate(toTimeStamp.evaluate("2013-10-17 15:40:00.000000")).equals("2013-10-17 15:40:00.000"));
        assertTrue(udf.evaluate(toTimeStamp.evaluate("2013-10-17 15:40:00")).equals("2013-10-17 15:40:00.000"));

        assertFalse(udf.evaluate(true).equals("True"));
        assertFalse(udf.evaluate(false).equals("False"));
    }
    
}
