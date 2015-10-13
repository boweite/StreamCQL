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

package com.huawei.streaming.util;

import org.junit.Test;

import com.huawei.streaming.exception.StreamingException;
import static org.junit.Assert.*;

/**
 * 数据源参数替换测试
 *
 */
public class StreamingDataTypeTest
{

    /**
     * 测试用例
     * @throws  StreamingException 数据类型转换异常
     */
    @Test
    public void testCreateValue() throws StreamingException
    {
       assertTrue(StreamingDataType.LONG.createValue("1").equals(1l));
       assertTrue(StreamingDataType.FLOAT.createValue("1.0f").equals(1.0f));
       assertTrue(StreamingDataType.DOUBLE.createValue("1.0d").equals(1.0d));
    }

    /**
     * 测试用例
     * @throws  StreamingException 数据类型转换异常
     */
    @Test
    public void testToStringValue() throws StreamingException
    {
        assertTrue(StreamingDataType.LONG.toStringValue(1L).equals("1"));
        assertTrue(StreamingDataType.LONG.toStringValue(1.0F).equals("1.0"));
        assertTrue(StreamingDataType.LONG.toStringValue(1.0D).equals("1.0"));
    }
}
