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
package com.huawei.streaming.cql.functions;

import java.util.Map;

import com.huawei.streaming.udfs.UDF;

/**
 * 一个用于输出的udf函数
 *
 */
public class DirectOutputUDF extends UDF
{

    private static final long serialVersionUID = 3309273009812610926L;

    private static final String OUTPUT_CONF = "cql.test.direct.output";

    private static final String DEFAULT_VALUE = "default";

    private String outputValue = DEFAULT_VALUE;

    /**
     * <默认构造函数>
     *
     * @param config udf函数中需要的参数，这些参数要在cql中通过全局变量进行设置。
     */
    public DirectOutputUDF(Map< String, String > config)
    {
        super(config);
        if (config.containsKey(OUTPUT_CONF))
        {
            outputValue = config.get(OUTPUT_CONF);
        }
    }

    /**
     * 输出字符串
     * @return 输出字符串
     */
    public String evaluate()
    {
        return outputValue;
    }

}
