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

import java.text.SimpleDateFormat;

import org.junit.Test;

/**
 * toTimestamp测试用例
 * 
 */
public class ToTimeStampTest
{
    
    /**
     * 测试用例
     */
    @Test
    public void testEvaluate()
    {
        ToTimeStamp udf = new ToTimeStamp(null);
        String s1 = "2014-09-25 17:07:00";
        assertNotNull(udf.evaluate(s1));
        String s2 = "2014-09-25 17:07:00.056";
        assertNotNull(udf.evaluate(s2));
        String s3 = "abc";
        assertNull(udf.evaluate(s3));
        String s4 = "2014-09-25 17:07:00.56";
        assertNotNull(udf.evaluate(s4));
        String s5 = "2014-09-25 17:07:00.9999999";
        assertNotNull(udf.evaluate(s5));
        String s7 = "2014-09-25 17:07:00.9999999999";
        assertNull(udf.evaluate(s7));
        String s6 = "2014-09-25 17:07:00.999999";
        assertNotNull(udf.evaluate(s6));
        SimpleDateFormat formatter = new SimpleDateFormat(UDFConstants.TIMESTAMP_MSTIME_FORMAT);
        formatter.setLenient(false);
        System.out.println(formatter.format(udf.evaluate("2014-09-25 17:07:00.9")));
        System.out.println(formatter.format(udf.evaluate("2014-09-25 17:07:00.999999999")));

        s1 = "2014-9-9 7:7:5";
        assertNotNull(udf.evaluate(s1));
        s1 = "2014-9-9 7:7:5.9";
        assertNotNull(udf.evaluate(s1));
    }
    
}
