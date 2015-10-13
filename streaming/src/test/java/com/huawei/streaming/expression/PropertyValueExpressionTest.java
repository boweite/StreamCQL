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
 * 文 件 名:  PropertyValueExpressionTest.java
 * 版 本 号:  V1.0.0
 * 版    权:  Huawei Technologies Co., Ltd. Copyright 1988-2008,  All rights reserved
 * 描    述:  <描述>
 * 作    者:  z00221388
 * 创建日期:  2013-6-25
 */
package com.huawei.streaming.expression;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.EventTypeMng;
import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.event.TupleEventType;

/**
 * PropertyValueExpression单元测试类
 */
public class PropertyValueExpressionTest
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
        Attribute attributeB = new Attribute(Float.class, "b");
        Attribute attributeC = new Attribute(String.class, "c");
        schema.add(attributeA);
        schema.add(attributeB);
        schema.add(attributeC);
        TupleEventType tupleEventType = new TupleEventType("schemName", schema);
        eventTypeMng = new EventTypeMng();
        eventTypeMng.addEventType(tupleEventType);
    }
    
    /**
     * setDown
     */
    @After
    public void setDown()
    {
        eventTypeMng = null;
    }
    
    /**
     * 获得指定的属性值
     */
    @Test
    public void testEvaluate()
    {
        //构造测试数据
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("a", 1);
        values.put("b", 1.0f);
        values.put("c", "3");
        IEvent event = new TupleEvent("stream", "schemName", values, eventTypeMng);
        
        //操作测试数据
        PropertyValueExpression propertyValueExpressionA = new PropertyValueExpression("a", Integer.class);
        Object expectedResultA = propertyValueExpressionA.evaluate(event);
        PropertyValueExpression propertyValueExpressionB = new PropertyValueExpression("b", Float.class);
        Object expectedResultB = propertyValueExpressionB.evaluate(event);
        PropertyValueExpression propertyValueExpressionC = new PropertyValueExpression("c", String.class);
        Object expectedResultC = propertyValueExpressionC.evaluate(event);
        
        //检验操作是否得到期望结果
        assertEquals(expectedResultA, 1);
        assertEquals(expectedResultB, 1.0f);
        assertEquals(expectedResultC, "3");
    }
    
}
