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

package com.huawei.streaming.cql.mapping;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * 输入输出映射测试用例
 *
 */
public class InputOutputOperatorMappingTest
{

    /**
     * 测试用例
     * @throws Exception
     */
    @Test
    public void testUnRegisterMapping()
    {
        InputOutputOperatorMapping.registerOperator(
         com.huawei.streaming.cql.toolkits.api.TCPServerInputOperator.class,
         com.huawei.streaming.cql.toolkits.operators.TCPServerInputOperator.class);
        assertEquals(InputOutputOperatorMapping.getPlatformOperatorByAPI(com.huawei.streaming.cql.toolkits.api.TCPServerInputOperator.class.getName()),
         com.huawei.streaming.cql.toolkits.operators.TCPServerInputOperator.class.getName());
        InputOutputOperatorMapping.unRegisterMapping(com.huawei.streaming.cql.toolkits.api.TCPServerInputOperator.class);
        assertNull(InputOutputOperatorMapping.getPlatformOperatorByAPI(com.huawei.streaming.cql.toolkits.api.TCPServerInputOperator.class.getName()));
        InputOutputOperatorMapping.unRegisterMapping(null);
    }
}
