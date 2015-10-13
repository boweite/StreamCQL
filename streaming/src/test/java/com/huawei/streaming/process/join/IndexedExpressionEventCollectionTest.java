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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.expression.PropertyValueExpression;
import com.huawei.streaming.support.SupportConst;

/**
 * IndexedExpressionEventCollectionTest
 * <功能详细描述>
 * 
 */
public class IndexedExpressionEventCollectionTest
{
    private PropertyValueExpression[] expr = null;
    
    private IndexedMultiPropertyEventCollection indexexp = null;
    
    private TupleEventType item = null;
    
    private IEvent[] events = null;
    
    /**
     * 测试前初始化
     * <功能详细描述>
     */
    @Before
    public void setUp()
    {
        expr = new PropertyValueExpression[1];
        expr[0] = new PropertyValueExpression("id", Integer.class);
        indexexp = new IndexedMultiPropertyEventCollection("item", item, expr);
        item =
            new TupleEventType("item", new Attribute(Integer.class, "id"), new Attribute(String.class, "description"),
                new Attribute(Double.class, "price"));
        events = new IEvent[SupportConst.I_TWO];
    }
    
    /**
     * 测试后清除
     * <功能详细描述>
     */
    @After
    public void clear()
    {
        indexexp = null;
        expr = null;
    }
    
    /**
     * testAdd
     * <功能详细描述>
     */
    @Test
    public void testAdd()
    {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("id", 1);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        //加入两个同值的事件1/事件2
        events[0] = new TupleEvent("item", item, values);
        events[1] = new TupleEvent("item", item, values);
        
        indexexp.add(events);
        
        assertFalse(indexexp.isEmpty());
        
        IEvent[] revents = new IEvent[SupportConst.I_TWO];
        revents[0] = events[0];
        //删除一个事件1后，还应有一个事件
        indexexp.remove(revents);
        
        assertFalse(indexexp.isEmpty());
        
        revents[0] = events[1];
        //删除第二个事件后，无事件保存
        indexexp.remove(revents);
        
        assertTrue(indexexp.isEmpty());
    }
    
    /**
     * testAddDuplicated
     * <功能详细描述>
     */
    @Test
    public void testAddDuplicated()
    {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("id", 1);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        TupleEvent event = new TupleEvent("item", item, values);
        events[0] = event;
        
        indexexp.add(events);
        
        assertFalse(indexexp.isEmpty());
        
    }
    
    /**
     * testRemoveNormal
     * <功能详细描述>
     */
    @Test
    public void testRemoveNormal()
    {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("id", 1);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        TupleEvent event = new TupleEvent("item", item, values);
        events[0] = event;
        
        indexexp.add(events);
        indexexp.remove(events);
        
        assertTrue(indexexp.isEmpty());
        
    }
    
    /**
     * testRemoveAbnormal
     * <功能详细描述>
     */
    @Test
    public void testRemoveAbnormal()
    {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("id", 1);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        TupleEvent event = new TupleEvent("item", item, values);
        events[0] = event;
        //加入事件1
        indexexp.add(events);
        
        values.clear();
        values.put("id", SupportConst.I_TWO);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        TupleEvent event2 = new TupleEvent("item", item, values);
        events[0] = event2;
        //删除事件2，不成功
        indexexp.remove(events);
        
        assertFalse(indexexp.isEmpty());
        
        values.clear();
        values.put("id", 1);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        TupleEvent event3 = new TupleEvent("item", item, values);
        events[0] = event3;
        
        //删除与事件1同值的事件3，不成功
        indexexp.remove(events);
        
        assertFalse(indexexp.isEmpty());
    }
    
}
