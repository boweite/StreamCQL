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
 * 文 件 名:  PropertyIsNullExpressionTest.java
 * 版 本 号:  V1.0.0
 * 版    权:  Huawei Technologies Co., Ltd. Copyright 1988-2008,  All rights reserved
 * 描    述:  <描述>
 * 作    者:  z00221388
 * 创建日期:  2013-6-25
 */
package com.huawei.streaming.expression;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.EventTypeMng;
import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.exception.StreamingRuntimeException;

/**
 * PropertyIsNullExpression单元测试类
 */
public class PropertyIsNullExpressionTest
{
    private EventTypeMng eventTypeMng;
    
    /**
     * setup
     */
    @Before
    public void setUp()
    {
        List<Attribute> schema = new ArrayList<Attribute>();
        Attribute attributeA = new Attribute(Integer.class, "a");
        Attribute attributeB = new Attribute(Integer.class, "b");
        Attribute attributeC = new Attribute(String.class, "c");
        schema.add(attributeA);
        schema.add(attributeB);
        schema.add(attributeC);
        TupleEventType tupleEventType = new TupleEventType("schemName", schema);
        eventTypeMng = new EventTypeMng();
        eventTypeMng.addEventType(tupleEventType);
    }
    
    /**
     * 测试用例一：所测试列的值为null
     */
    @Test
    public void testEvaluate1()
    {
        //构造测试数据
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("a", 1);
        values.put("b", 1);
        values.put("c", null);
        IEvent event = new TupleEvent("stream", "schemName", values, eventTypeMng);
        PropertyIsNullExpression propertyIsNullExpression = new PropertyIsNullExpression("c", String.class);
        
        //操作测试数据
        Object actualResult = propertyIsNullExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertTrue((Boolean)actualResult);
    }
    
    /**
     * 测试用例二：所测试列的值为非null
     */
    @Test
    public void testEvaluate2()
    {
        //构造测试数据
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("a", 1);
        values.put("b", 1);
        values.put("c", null);
        IEvent event = new TupleEvent("stream", "schemName", values, eventTypeMng);
        PropertyIsNullExpression propertyIsNullExpression = new PropertyIsNullExpression("a", Integer.class);
        
        //操作测试数据
        Object actualResult = propertyIsNullExpression.evaluate(event);
        
        //检验操作是否得到期望结果
        Assert.assertFalse((Boolean)actualResult);
    }
    
    /**
     * 测试用例三：所测试列不存在
     * TODO:列不存在 也认为是null？
     */
    @Test
    public void testEvaluate3()
    {
        //构造测试数据
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("a", 1);
        values.put("b", 1);
        values.put("c", null);
        IEvent event = new TupleEvent("stream", "schemName", values, eventTypeMng);
        PropertyIsNullExpression propertyIsNullExpression = new PropertyIsNullExpression("d", Integer.class);
        
        try
        {
            //操作测试数据
            propertyIsNullExpression.evaluate(event);
            fail();
        }
        catch (StreamingRuntimeException ex)
        {
            Assert.assertTrue(true);
        }
    }
}
