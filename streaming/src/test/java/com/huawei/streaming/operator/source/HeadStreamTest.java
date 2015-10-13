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

package com.huawei.streaming.operator.source;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.IEventType;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.operator.inputstream.HeadStreamSourceOp;

/**
 * <HeadStreamTest>
 * <功能详细描述>
 * 
 */
public class HeadStreamTest
{
    private HeadStreamSourceOp.HeadStream head;
    
    //    private HashMap<String, IExpression> outExp;
    
    private IEventType eType;
    
    /**
     * <setup>
     * @throws Exception 异常
     */
    @Before
    public void setUp()
        throws Exception
    {
        //        outExp = new HashMap<String, IExpression>();
        List<Attribute> schema = new ArrayList<Attribute>();
        Attribute attributeA = new Attribute(Integer.class, "a");
        Attribute attributeB = new Attribute(Double.class, "b");
        Attribute attributeC = new Attribute(String.class, "c");
        Attribute attributeD = new Attribute(Float.class, "d");
        Attribute attributeE = new Attribute(Boolean.class, "e");
        Attribute attributeF = new Attribute(Long.class, "f");
        
        schema.add(attributeA);
        schema.add(attributeB);
        schema.add(attributeC);
        schema.add(attributeD);
        schema.add(attributeE);
        schema.add(attributeF);
        eType = new TupleEventType("output", schema);
    }
    
    /**
     * <cleanup>
     * @throws Exception 异常
     */
    @After
    public void tearDown()
        throws Exception
    {
        head = null;
        eType = null;
    }
    
    /**
     * 生成用户数据
     * <功能详细描述>
     */
    @Test
    public void testGetOutput()
    {
        head = new HeadStreamSourceOp().new HeadStream(eType, null);
        
        Object[] result = head.getOutput();
        
        Assert.assertEquals(eType.getSize(), result.length);
    }
}
