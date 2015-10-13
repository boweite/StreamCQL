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

package com.huawei.streaming.cql.builder.physicoptimizer;

import com.huawei.streaming.api.Application;
import com.huawei.streaming.cql.exception.ApplicationBuildException;

/**
 * 物理优化器接口
 * 
 */
public interface Optimizer
{
    /**
     * 执行优化
     * @param app application 完成解析的应用程序
     * @return 优化之后的application
     * @throws ApplicationBuildException 优化期间可能会抛出的异常
     */
    Application optimize(Application app)
        throws ApplicationBuildException;
}
