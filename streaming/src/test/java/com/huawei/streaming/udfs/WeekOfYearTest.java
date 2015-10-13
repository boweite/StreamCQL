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
 * 时间类udf函数测试用例
 * 
 */
public class WeekOfYearTest
{
    /**
     * 测试用例
     */
    @Test
    public void testEvaluate()
    {
        WeekOfYear udf = new WeekOfYear(null);
        assertTrue(udf.evaluate("2013-01-01 09:58:00.111") == TestUDFCommon.I_1);
        assertTrue(udf.evaluate("2013-01-01 09:58:00") == TestUDFCommon.I_1);
        assertTrue(udf.evaluate("2013-12-28") == TestUDFCommon.I_52);
        assertTrue(udf.evaluate("2013-12-52") == null);
        assertTrue(udf.evaluate("2013-01-01 09:58:70") == null);
    }

    /**
     * 测试用例
     */
    @Test
    public void testEvaluate2()
    {
        WeekOfYear udf = new WeekOfYear(null);
        ToDate todate = new ToDate(null);
        assertTrue(udf.evaluate(todate.evaluate("2013-1-1")) == TestUDFCommon.I_1);
        assertTrue(udf.evaluate(todate.evaluate("2013-01-01")) == TestUDFCommon.I_1);
        assertTrue(udf.evaluate(todate.evaluate("2013-12-28")) == TestUDFCommon.I_52);
        assertTrue(udf.evaluate(todate.evaluate("2013-12-52")) == null);
    }

    /**
     * 测试用例
     */
    @Test
    public void testEvaluate3()
    {
        WeekOfYear udf = new WeekOfYear(null);
        ToTimeStamp toTimeStamp = new ToTimeStamp(null);
        assertTrue(udf.evaluate(toTimeStamp.evaluate("2013-01-01 09:58:00.111")) == TestUDFCommon.I_1);
        assertTrue(udf.evaluate(toTimeStamp.evaluate("2013-01-01 09:58:00")) == TestUDFCommon.I_1);
        assertTrue(udf.evaluate(toTimeStamp.evaluate("2013-01-01 09:58:70")) == null);
    }

}
