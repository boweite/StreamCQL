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

package com.huawei.streaming.process.join;

import static org.junit.Assert.assertArrayEquals;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.common.MultiKey;
import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.expression.PropertyValueExpression;
import com.huawei.streaming.support.SupportConst;

/**
 * 
 * InnerBiJoinComposerTest
 * <功能详细描述>
 * 
 */
public class InnerBiJoinComposerTest
{
    private static final double DOUBLE_58 = 5.8d;
    
    private TupleEventType item = new TupleEventType("item", new Attribute(Integer.class, "id"), new Attribute(
        String.class, "description"), new Attribute(Double.class, "price"));
    
    private TupleEventType sell = new TupleEventType("sell", new Attribute(Integer.class, "id"), new Attribute(
        String.class, "buyer"), new Attribute(Double.class, "price"), new Attribute(Integer.class, "number"));
    
    private InnerBiJoinComposer innerJoin = null;
    
    private IndexedMultiPropertyEventCollection leftColle = null;
    
    private IndexedMultiPropertyEventCollection rightColle = null;
    
    private PropertyValueExpression[] exprleft = null;
    
    private PropertyValueExpression[] exprright = null;
    
    private IEvent[][] newDataPerStream = null;
    
    private IEvent[][] oldDataPerStream = null;
    
    /**
     * <setup>
     * @throws Exception 异常
     */
    @Before
    public void setUp()
        throws Exception
    {
        exprleft = new PropertyValueExpression[1];
        //id
        exprleft[0] = new PropertyValueExpression("id", Integer.class);
        
        exprright = new PropertyValueExpression[1];
        //id
        exprright[0] = new PropertyValueExpression("id", Integer.class);
        
        //item.id=sell.id
        leftColle = new IndexedMultiPropertyEventCollection("item", item, exprleft);
        rightColle = new IndexedMultiPropertyEventCollection("sell", sell, exprright);
        
        innerJoin = new InnerBiJoinComposer(leftColle, rightColle, false);
        
    }
    
    /**
     * <cleanup>
     * @throws Exception 异常
     */
    @After
    public void tearDown()
        throws Exception
    {
        exprleft = null;
        exprright = null;
        leftColle = null;
        rightColle = null;
        innerJoin = null;
    }
    
    /**
     * testMaintainData
     * <功能详细描述>
     */
    @Test
    public void testMaintainData()
    {
        newDataPerStream = new IEvent[SupportConst.I_TWO][];
        newDataPerStream[0] = new IEvent[1];
        newDataPerStream[1] = new IEvent[1];
        oldDataPerStream = new IEvent[SupportConst.I_TWO][];
        oldDataPerStream[0] = new IEvent[1];
        oldDataPerStream[1] = new IEvent[1];
        
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("id", 1);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        //加入item 一个事件 id=1
        newDataPerStream[0][0] = new TupleEvent("item", item, values);
        
        values.clear();
        values.put("id", SupportConst.I_FOUR);
        values.put("buyer", "custom:");
        values.put("price", DOUBLE_58);
        values.put("number", SupportConst.I_FIVE);
        
        //加入sell 一个事件 id=4
        newDataPerStream[1][0] = new TupleEvent("sell", sell, values);
        
        innerJoin.maintainData(newDataPerStream, oldDataPerStream);
        
        innerJoin.maintainData(oldDataPerStream, newDataPerStream);
        
    }
    
    /**
     * testCompose
     * <功能详细描述>
     */
    @Test
    public void testCompose()
    {
        newDataPerStream = new IEvent[SupportConst.I_TWO][];
        newDataPerStream[0] = new IEvent[1];
        newDataPerStream[1] = new IEvent[1];
        oldDataPerStream = new IEvent[SupportConst.I_TWO][];
        oldDataPerStream[0] = new IEvent[1];
        oldDataPerStream[1] = new IEvent[1];
        
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("id", 1);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        //加入item 一个事件 id=1
        newDataPerStream[0][0] = new TupleEvent("item", item, values);
        
        values.clear();
        values.put("id", SupportConst.I_FIVE);
        values.put("buyer", "custom:");
        values.put("price", DOUBLE_58);
        values.put("number", SupportConst.I_FIVE);
        
        //加入sell 一个事件 id=5
        newDataPerStream[1][0] = new TupleEvent("sell", sell, values);
        
        innerJoin.maintainData(newDataPerStream, oldDataPerStream);
        
        Set<MultiKey> result = new LinkedHashSet<MultiKey>();
        
        IEvent[] newevent = new IEvent[1];
        values.clear();
        values.put("id", SupportConst.I_FIVE);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        //对item 一个事件 id=5进行匹配
        newevent[0] = new TupleEvent("item", item, values);
        
        innerJoin.compose(newevent, 0, result);
        
        Set<MultiKey> expecteds = new LinkedHashSet<MultiKey>();
        Object[] obj = new Object[SupportConst.I_TWO];
        obj[0] = newevent[0];
        obj[1] = newDataPerStream[1][0];
        expecteds.add(new MultiKey(obj));
        //匹配两条事件
        assertArrayEquals(expecteds.toArray(new MultiKey[expecteds.size()]),
            result.toArray(new MultiKey[result.size()]));
        
        //sell事件 id=5 过期
        oldDataPerStream[1][0] = newDataPerStream[1][0];
        //        newDataPerStream[0] = null;
        newDataPerStream[1] = null;
        //在新数据中为以前增加过的数据，由于使用Set，因此并不会增加新数据
        innerJoin.maintainData(newDataPerStream, oldDataPerStream);
        
        result.clear();
        expecteds.clear();
        innerJoin.compose(newevent, 0, result);
        //没有匹配事件
        assertArrayEquals(expecteds.toArray(new MultiKey[expecteds.size()]),
            result.toArray(new MultiKey[result.size()]));
        
        values.clear();
        values.put("id", 1);
        values.put("buyer", "custom:");
        values.put("price", DOUBLE_58);
        values.put("number", SupportConst.I_FIVE);
        
        //对sell 一个事件 id=1进行匹配
        newevent[0] = new TupleEvent("sell", sell, values);
        result.clear();
        expecteds.clear();
        innerJoin.compose(newevent, 1, result);
        
        obj[0] = newDataPerStream[0][0];
        obj[1] = newevent[0];
        expecteds.add(new MultiKey(obj));
        //匹配两条事件
        assertArrayEquals(expecteds.toArray(new MultiKey[expecteds.size()]),
            result.toArray(new MultiKey[result.size()]));
    }
    
    /**
     * testJoin
     * <功能详细描述>
     */
    @Test
    public void testJoin()
    {
        //TODO
    }
}
