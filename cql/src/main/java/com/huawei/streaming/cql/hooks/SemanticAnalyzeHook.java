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

import com.huawei.streaming.cql.DriverContext;
import com.huawei.streaming.cql.exception.SemanticAnalyzerException;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.AnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.parser.context.ParseContext;

/**
 * 语义分析前后的钩子
 * 
 */
public interface SemanticAnalyzeHook extends Hook
{
    /**
     *  验证是否属于该hook的执行范围
     * @param parseContext 语法解析内容
     * @return 如果属于执行返回，返回true
     * @throws SemanticAnalyzerException 语义分析异常
     */
    boolean validate(ParseContext parseContext)
        throws SemanticAnalyzerException;
    
    /**
     * 语义分析之前执行的动作
     * @param context driver实例中保存的临时对象
     * @param parseContext 语法解析内容
     * @throws SemanticAnalyzerException 语义分析异常
     */
    void preAnalyze(DriverContext context, ParseContext parseContext)
        throws SemanticAnalyzerException;
    
    /**
     * 语义分析之后执行的动作
     * @param context driver实例中保存的临时对象
     * @param analyzeConext 语义分析结果
     * @throws SemanticAnalyzerException 语义分析异常
     */
    void postAnalyze(DriverContext context, AnalyzeContext analyzeConext)
        throws SemanticAnalyzerException;
}
