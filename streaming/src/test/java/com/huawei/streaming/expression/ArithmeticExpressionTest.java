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
/*
 * 文 件 名:  ArithmeticExpressionTest.java
 * 版 本 号:  V1.0.0
 * 版    权:  Huawei Technologies Co., Ltd. Copyright 1988-2008,  All rights reserved
 * 描    述:  <描述>
 * 作    者:  z00221388
 * 创建日期:  2013-6-25
 */
package com.huawei.streaming.expression;

import org.junit.Assert;
import org.junit.Test;

import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.exception.StreamingException;
import com.huawei.streaming.support.SupportConst;

/**
 * 算术表达式ArithmeticExpression的单元测试类
 */
public class ArithmeticExpressionTest
{
    
    private static final float NUM_2_4 = 2.4f;
    
    /**
     * 测试用例一：2+3
     * @throws StreamingException 异常
     */
    @Test
    public void testEvaluate1()
        throws StreamingException
    {
        //构造测试数据,2+3
        ArithmeticExpression arithmeticExpression =
            new ArithmeticExpression(ExpressionOperator.ADD, new ConstExpression(SupportConst.I_TWO),
                new ConstExpression(SupportConst.I_THREE));
        IEvent theEvent = new TupleEvent();
        
        //操作测试数据
        Object actualResult = arithmeticExpression.evaluate(theEvent);
        
        //检验操作是否得到期望结果
        int expectedResult = SupportConst.I_FIVE;
        Assert.assertEquals(expectedResult, actualResult);
    }
    
    /**
     * 测试用例二：2-3
     * @throws StreamingException 异常
     */
    @Test
    public void testEvaluate2()
        throws StreamingException
    {
        //构造测试数据,2-3
        ArithmeticExpression arithmeticExpression =
            new ArithmeticExpression(ExpressionOperator.SUBTRACT, new ConstExpression(SupportConst.I_TWO),
                new ConstExpression(SupportConst.I_THREE));
        IEvent theEvent = new TupleEvent();
        
        //操作测试数据
        Object actualResult = arithmeticExpression.evaluate(theEvent);
        
        //检验操作是否得到期望结果
        int expectedResult = -1;
        Assert.assertEquals(expectedResult, actualResult);
    }
    
    /**
     * 测试用例三：2*3
     * @throws StreamingException 异常
     */
    @Test
    public void testEvaluate3()
        throws StreamingException
    {
        //构造测试数据,2*3
        ArithmeticExpression arithmeticExpression =
            new ArithmeticExpression(ExpressionOperator.MULTIPLY, new ConstExpression(SupportConst.I_TWO),
                new ConstExpression(SupportConst.I_THREE));
        IEvent theEvent = new TupleEvent();
        
        //操作测试数据
        Object actualResult = arithmeticExpression.evaluate(theEvent);
        
        //检验操作是否得到期望结果
        int expectedResult = SupportConst.I_SIX;
        Assert.assertEquals(expectedResult, actualResult);
    }
    
    /**
     * 测试用例四：2/3
     * @throws StreamingException 异常
     */
    @Test
    public void testEvaluate4()
        throws StreamingException
    {
        //构造测试数据,2/3
        ArithmeticExpression arithmeticExpression =
            new ArithmeticExpression(ExpressionOperator.DIVIDE, new ConstExpression(SupportConst.I_TWO),
                new ConstExpression(SupportConst.I_THREE));
        IEvent theEvent = new TupleEvent();
        
        //操作测试数据
        Object actualResult = arithmeticExpression.evaluate(theEvent);
        
        //检验操作是否得到期望结果
        int expectedResult = SupportConst.I_TWO / SupportConst.I_THREE;
        Assert.assertEquals(expectedResult, actualResult);
    }
    
    /**
     * 测试用例五：2.4/0.1
     * 该用例测出一个问题：精度问题
     * 该用例目前仍测试不过
     */
    //    @Test
    //    public void testEvaluate5()
    //    {
    //        //构造测试数据,2.4/0.1
    //        ArithmeticExpression arithmeticExpression =
    //            new ArithmeticExpression(ExpressionOperator.DIVIDE, new ConstExpression(2.4), 
    //                    new ConstExpression(0.1));
    //        IEvent theEvent = new TupleEvent();
    //        
    //        //操作测试数据
    //        Object actualResult = arithmeticExpression.evaluate(theEvent);
    //        
    //        //检验操作是否得到期望结果
    //        double expectedResult = 24;
    //        Assert.assertEquals(expectedResult, actualResult);
    //    }
    
    /**
     * 测试用例六：2.4/0
     * 该用例测出一个问题：除数为0问题
     * @throws StreamingException 异常
     */
    @Test
    public void testEvaluate6()
        throws StreamingException
    {
        //构造测试数据,2.4/0
        ArithmeticExpression arithmeticExpression =
            new ArithmeticExpression(ExpressionOperator.DIVIDE, new ConstExpression(NUM_2_4), new ConstExpression(0));
        IEvent theEvent = new TupleEvent();
        
        //操作测试数据
        Object actualResult = arithmeticExpression.evaluate(theEvent);
        
        //检验操作是否得到期望结果
        Double expectedResult = null;
        Assert.assertEquals(expectedResult, actualResult);
    }
    
    /**
     * 测试用例七：2.4/null,有一个参数为null
     * 该用例测出一个问题：除数为0问题
     * @throws StreamingException 异常
     */
    @Test
    public void testEvaluate7()
        throws StreamingException
    {
        //构造测试数据,2.4/null
        ArithmeticExpression arithmeticExpression =
            new ArithmeticExpression(ExpressionOperator.DIVIDE, new ConstExpression(NUM_2_4), new ConstExpression(null,
                Double.class));
        IEvent theEvent = new TupleEvent();
        
        //操作测试数据
        Object actualResult = arithmeticExpression.evaluate(theEvent);
        
        //检验操作是否得到期望结果
        Double expectedResult = null;
        Assert.assertEquals(expectedResult, actualResult);
    }
}
