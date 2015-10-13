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
package com.huawei.streaming.cql.executor;

import java.util.Map;

import com.huawei.streaming.udfs.UDF;

/**
 * 测试的udf函数
 *
 */
public class TestUDF extends UDF
{
    
    private static final long serialVersionUID = -2851475951678164450L;
    
    /**
     * <默认构造函数>
     *
     * @param config 参数
     */
    public TestUDF(Map<String, String> config)
    {
        super(config);
    }
    
    /**
     * 测试函数
     *
     * @return 返回结果
     */
    public String evaluate()
    {
        return new String("a");
    }
}
