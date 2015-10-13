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

package com.huawei.streaming.cql.hooks;

import com.huawei.streaming.cql.CQLUtils;
import com.huawei.streaming.cql.DriverContext;
import com.huawei.streaming.cql.semanticanalyzer.parser.context.ParseContext;
import com.huawei.streaming.cql.semanticanalyzer.parser.context.SubmitApplicationContext;

/**
 * 在driver的run方法执行前后进行一些clean操作
 * 
 */
public class DriverCleanerHook implements DriverRunHook
{
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void preDriverRun(DriverContext context, ParseContext parseContext)
    {
        context.setQueryResult(null);
        if (CQLUtils.isChangeableCommond(parseContext))
        {
            context.cleanApp();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void postDriverRun(DriverContext context, ParseContext parseContext)
    {
        /*
         * submit是CQL一段命令结束的标志，会将任务提交至运行平台，
         * 是没有执行结果的，可以直接清除
         */
        if (isSubmit(parseContext))
        {
            context.clean();
        }
    }
    
    private boolean isSubmit(ParseContext parseContext)
    {
        return parseContext instanceof SubmitApplicationContext;
    }

}
