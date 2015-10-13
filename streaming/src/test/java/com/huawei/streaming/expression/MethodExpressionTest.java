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

package com.huawei.streaming.expression;

import org.junit.Assert;
import org.junit.Test;

import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.udfs.StringConcat;

/**
 * MethodExpression单元测试类
 */
public class MethodExpressionTest
{
    
    /**
     * Test method for {@link com.huawei.streaming.expression.MethodExpression#evaluate
     * (com.huawei.streaming.event.IEvent)}.
     * 测试用例一：调用对象的共有函数（String对象的substring函数）
     */
    @Test
    public void testEvaluateIEvent1()
    {
        //构造测试数据
        MethodExpression methodExpression =
            new MethodExpression(new String("abcdefg"), "substring", new IExpression[] {new ConstExpression(0),
                new ConstExpression(1)});
        
        //操作测试数据
        String actual = (String)methodExpression.evaluate(new TupleEvent());
        
        //检验操作是否得到期望的结果
        String expected = "a";
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * Test method for {@link com.huawei.streaming.expression.MethodExpression#evaluate
     * (com.huawei.streaming.event.IEvent)}.
     * 测试用例二：调用Math的工具函数（Math的abs函数）
     */
    @Test
    public void testEvaluateIEvent2()
    {
        //构造测试数据
        MethodExpression methodExpression =
            new MethodExpression(Math.class, "abs", new IExpression[] {new ConstExpression(-1)});
        
        //操作测试数据
        int actual = (Integer)methodExpression.evaluate(new TupleEvent());
        
        //检验操作是否得到期望的结果
        int expected = 1;
        Assert.assertEquals(expected, actual);
    }
    
    /**
     * 可变参数测试
     */
    @Test
    public void testEvaluateIEvent3()
    {
        //构造测试数据
        MethodExpression methodExpression =
            new MethodExpression(new StringConcat(null), "evaluate", new IExpression[] {new ConstExpression("a"),
                new ConstExpression("b")});
        
        //操作测试数据
        String res = (String)methodExpression.evaluate(new TupleEvent());
        
        Assert.assertEquals(res, "ab");
    }
}
