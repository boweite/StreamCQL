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

package com.huawei.streaming.cql.toolkits.api;

import com.huawei.streaming.api.ConfigAnnotation;
import com.huawei.streaming.api.opereators.InnerInputSourceOperator;
import com.huawei.streaming.config.StreamingConfig;

/**
 * TCP数据读取算子
 * 
 */
public class TCPServerInputOperator extends InnerInputSourceOperator
{
    /**
     * 端口
     */
    @ConfigAnnotation(StreamingConfig.OPERATOR_TCPSERVER_PORT)
    private String port;
    
    /**
     * 端口
     */
    @ConfigAnnotation(StreamingConfig.OPERATOR_TCPSERVER_FIXEDLENGTH)
    private String fixedLength;
    
    /**
     * <默认构造函数>
     * @param id 算子id
     * @param parallelNumber 算子并行度
     */
    public TCPServerInputOperator(String id, int parallelNumber)
    {
        super(id, parallelNumber);
    }
    
    public String getPort()
    {
        return port;
    }
    
    public void setPort(String port)
    {
        this.port = port;
    }
    
    public String getFixedLength()
    {
        return fixedLength;
    }
    
    public void setFixedLength(String fixedLength)
    {
        this.fixedLength = fixedLength;
    }
}
