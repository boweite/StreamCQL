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

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.streaming.exception.StreamingException;
import com.huawei.streaming.util.StreamingDataType;

/**
 * 转换为字符串的udf函数
 * 
 */
@UDFAnnotation(name = "tostring")
public class ToString extends UDF
{
    private static final Logger LOG = LoggerFactory.getLogger(ToString.class);
    
    private static final long serialVersionUID = -4516472038115224500L;
    
    /**
     * <默认构造函数>
     * @param config 参数
     */
    public ToString(Map<String, String> config)
    {
        super(config);
    }
    
    /**
     * 类型转换实现
     * @param s 待转换数据
     * @return 转换之后结果
     */
    public String evaluate(Object s)
    {
        if (s == null)
        {
            return null;
        }
        
        try
        {
            StreamingDataType dataType = StreamingDataType.getDataType(s.getClass());
            return dataType.toStringValue(s);
        }
        catch (StreamingException e)
        {
            LOG.warn(EVALUATE_IGNORE_MESSAGE);
            return null;
        }
    }
}
