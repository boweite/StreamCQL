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

package com.huawei.streaming.cql.semanticanalyzer.expressiondescwalker;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.streaming.cql.exception.ApplicationBuildException;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.BinaryExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.ConstExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.ExpressionDescribe;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.FunctionExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.JoinExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.NullExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.PropertyValueExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.StreamAliasDesc;

/**
 * 遍历所有的表达式描述内容，获取需要的表达式
 *
 */
public class ExpressionDescsWalker
{
    private static final Logger LOG = LoggerFactory.getLogger(ExpressionDescsWalker.class);
    
    private ExpressionDescGetterStrategy strategy;
    
    /**
     * <默认构造函数>
     *
     * @param getStrategy 遍历的判定策略
     */
    public ExpressionDescsWalker(ExpressionDescGetterStrategy getStrategy)
    {
        this.strategy = getStrategy;
    }
    
    /**
     * 递归表达式中的Previous 函数
     *
     * @param expression 表达式
     * @param expressionContainer 表达式容器，不能为空
     * @throws ApplicationBuildException 执行异常
     */
    public void found(ExpressionDescribe expression, List<ExpressionDescribe> expressionContainer)
        throws ApplicationBuildException
    {
        if (expressionContainer == null || null == expression)
        {
            return;
        }
        
        /*
         * 执行策略
         */
        if (strategy.isEqual(expression))
        {
            expressionContainer.add(expression);
            return;
        }
        
        walkBinaryExpression(expression, expressionContainer);
        walkFunctions(expression, expressionContainer);
        walkOthers(expression, expressionContainer);
    }
    
    private void walkBinaryExpression(ExpressionDescribe expression, List<ExpressionDescribe> expressionContainer)
        throws ApplicationBuildException
    {
        if (expression instanceof BinaryExpressionDesc)
        {
            BinaryExpressionDesc opexp = (BinaryExpressionDesc)expression;
            for (ExpressionDescribe exp : opexp.getArgExpressions())
            {
                found(exp, expressionContainer);
            }
        }
    }
    
    private void walkOthers(ExpressionDescribe expression, List<ExpressionDescribe> expressionContainer)
        throws ApplicationBuildException
    {
        if (expression instanceof ConstExpressionDesc)
        {
            return;
        }
        
        if (expression instanceof JoinExpressionDesc)
        {
            return;
        }
        
        if (expression instanceof NullExpressionDesc)
        {
            NullExpressionDesc opexp = (NullExpressionDesc)expression;
            found(opexp.getExpression(), expressionContainer);
        }
        
        if (expression instanceof PropertyValueExpressionDesc)
        {
            return;
        }
        
        if (expression instanceof StreamAliasDesc)
        {
            return;
        }
    }
    
    private void walkFunctions(ExpressionDescribe expression, List<ExpressionDescribe> expressionContainer)
        throws ApplicationBuildException
    {
        if (expression instanceof FunctionExpressionDesc)
        {
            FunctionExpressionDesc opexp = (FunctionExpressionDesc)expression;
            for (ExpressionDescribe exp : opexp.getArgExpressions())
            {
                found(exp, expressionContainer);
            }
        }
    }

}
