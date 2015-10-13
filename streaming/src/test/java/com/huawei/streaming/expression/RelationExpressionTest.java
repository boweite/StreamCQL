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
 * 文 件 名:  RelationExpressionTest.java
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
import com.huawei.streaming.exception.IllegalDataTypeException;
import com.huawei.streaming.support.SupportConst;

/**
 * RelationExpression单元测试类
 */
public class RelationExpressionTest
{
    /**
     * 测试用例一：2>3
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void testEvaluate1()
        throws IllegalDataTypeException
    {
        //构造测试数据
        RelationExpression relationExpression =
            new RelationExpression(ExpressionOperator.GREATERTHAN, new ConstExpression(SupportConst.I_TWO),
                new ConstExpression(SupportConst.I_THREE));
        IEvent event = new TupleEvent();
        
        //操作测试数据
        Object actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertFalse((Boolean)actualResult);
    }
    
    /**
     * 测试用例二：2>=3
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void testEvaluate2()
        throws IllegalDataTypeException
    {
        //构造测试数据
        RelationExpression relationExpression =
            new RelationExpression(ExpressionOperator.GREATERTHAN_EQUAL, new ConstExpression(SupportConst.I_TWO),
                new ConstExpression(SupportConst.I_THREE));
        IEvent event = new TupleEvent();
        
        //操作测试数据
        Object actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertFalse((Boolean)actualResult);
    }
    
    /**
     * 测试用例三：2<3
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void testEvaluate3()
        throws IllegalDataTypeException
    {
        //构造测试数据
        RelationExpression relationExpression =
            new RelationExpression(ExpressionOperator.LESSTHAN, new ConstExpression(SupportConst.I_TWO),
                new ConstExpression(SupportConst.I_THREE));
        IEvent event = new TupleEvent();
        
        //操作测试数据
        Object actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertTrue((Boolean)actualResult);
    }
    
    /**
     * 测试用例四：2<=3
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void testEvaluate4()
        throws IllegalDataTypeException
    {
        //构造测试数据
        RelationExpression relationExpression =
            new RelationExpression(ExpressionOperator.LESSTHAN_EQUAL, new ConstExpression(SupportConst.I_TWO),
                new ConstExpression(SupportConst.I_THREE));
        IEvent event = new TupleEvent();
        
        //操作测试数据
        Object actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertTrue((Boolean)actualResult);
    }
    
    /**
     * 测试用例五：2==3
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void testEvaluate5()
        throws IllegalDataTypeException
    {
        //构造测试数据
        RelationExpression relationExpression =
            new RelationExpression(ExpressionOperator.EQUAL, new ConstExpression(SupportConst.I_TWO),
                new ConstExpression(SupportConst.I_THREE));
        IEvent event = new TupleEvent();
        
        //操作测试数据
        Object actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertFalse((Boolean)actualResult);
    }
    
    /**
     * 测试用例五：2!=3
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void testEvaluate6()
        throws IllegalDataTypeException
    {
        //构造测试数据
        RelationExpression relationExpression =
            new RelationExpression(ExpressionOperator.NOT_EQUAL, new ConstExpression(SupportConst.I_TWO),
                new ConstExpression(SupportConst.I_THREE));
        IEvent event = new TupleEvent();
        
        //操作测试数据
        Object actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertTrue((Boolean)actualResult);
    }
    
    /**
     * 测试用例五：1==1.0
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void testEvaluate7()
        throws IllegalDataTypeException
    {
        //构造测试数据
        RelationExpression relationExpression =
            new RelationExpression(ExpressionOperator.EQUAL, new ConstExpression(1), new ConstExpression(1.0));
        IEvent event = new TupleEvent();
        
        //操作测试数据
        Object actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertTrue((Boolean)actualResult);
    }
    
    /**
     * 测试用例一：true = true  / true = false  / false = false
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void testEvaluate8()
        throws IllegalDataTypeException
    {
        //构造测试数据
        RelationExpression relationExpression =
            new RelationExpression(ExpressionOperator.EQUAL, new ConstExpression(true), new ConstExpression(true));
        IEvent event = new TupleEvent();
        
        //操作测试数据
        Object actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertEquals(true, actualResult);
        
        relationExpression =
            new RelationExpression(ExpressionOperator.EQUAL, new ConstExpression(true), new ConstExpression(false));
        
        actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertEquals(false, actualResult);
        
        relationExpression =
            new RelationExpression(ExpressionOperator.EQUAL, new ConstExpression(false), new ConstExpression(false));
        actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertEquals(true, actualResult);
    }
    
    /**
     * 测试用例一：true != true  / true != false  / false != false
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void testEvaluate9()
        throws IllegalDataTypeException
    {
        //构造测试数据
        RelationExpression relationExpression =
            new RelationExpression(ExpressionOperator.NOT_EQUAL, new ConstExpression(true), new ConstExpression(true));
        IEvent event = new TupleEvent();
        
        //操作测试数据
        Object actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertEquals(false, actualResult);
        
        relationExpression =
            new RelationExpression(ExpressionOperator.NOT_EQUAL, new ConstExpression(true), new ConstExpression(false));
        
        actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertEquals(true, actualResult);
        
        relationExpression =
            new RelationExpression(ExpressionOperator.NOT_EQUAL, new ConstExpression(false), new ConstExpression(false));
        actualResult = relationExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertEquals(false, actualResult);
    }
    
}
