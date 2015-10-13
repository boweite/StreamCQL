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

package com.huawei.streaming.cql.builder.operatorconverter;

import com.huawei.streaming.api.opereators.Operator;

/**
 * API转换模块
 * 将通用的input、output operator转换成API中规定的特殊的模块
 * 
 */
public interface Converter
{
    
    /**
     * 验证是否满足转换要求
     * @param op 待转换的算子
     * @return 如果满足，返回true
     */
    boolean validate(Operator op);
}
