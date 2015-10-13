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

package com.huawei.streaming.view;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.IEventType;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.support.SupportAssertUtil;
import com.huawei.streaming.support.SupportConst;
import com.huawei.streaming.support.SupportEventMng;
import com.huawei.streaming.support.SupportHelper;
import com.huawei.streaming.support.SupportView;
import com.huawei.streaming.support.SupportViewDataCheck;

/**
 * 
 * <ViewImplTest>
 * <功能详细描述>
 * 
 */
public class ViewImplTest
{
    private SupportView top = null;
    
    private SupportView child1 = null;
    
    private SupportView child2 = null;
    
    private SupportEventMng mng = null;
    
    private IEventType eventType = null;
    
    /**
     * <setup>
     * @throws Exception 异常
     */
    @Before
    public void setUp()
        throws Exception
    {
        top = new SupportView();
        
        child1 = new SupportView();
        child2 = new SupportView();
        
        mng = new SupportEventMng();
        eventType = mng.getInput();
    }
    
    /**
     * <testAddView>
     * <功能详细描述>
     */
    @Test
    public void testAddView()
    {
        top.addView(child1);
        SupportHelper.assertEquals(top, child1.getParent());
        SupportAssertUtil.assertEqualsExactOrder(new SupportView[] {child1}, top.getViews().toArray(new SupportView[1]));
        top.addView(child2);
        SupportAssertUtil.assertEqualsExactOrder(new SupportView[] {child1, child2},
            top.getViews().toArray(new SupportView[SupportConst.I_TWO]));
    }
    
    /**
     * <testRemoveAllViews>
     * <功能详细描述>
     */
    @Test
    public void testRemoveAllViews()
    {
        top.addView(child1);
        top.addView(child2);
        
        top.removeAllViews();
        SupportAssertUtil.assertEqualsExactOrder(null, top.getViews().toArray(new SupportView[top.getViews().size()]));
    }
    
    /**
     * <testRemoveView>
     * <功能详细描述>
     */
    @Test
    public void testRemoveView()
    {
        top.addView(child1);
        top.addView(child2);
        
        top.removeView(child1);
        SupportAssertUtil.assertEqualsExactOrder(new SupportView[] {child2}, top.getViews().toArray(new SupportView[1]));
    }
    
    /**
     * <testupdateChild>
     * <功能详细描述>
     */
    @Test
    public void testupdateChild()
    {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("a", 1);
        values.put("b", 1);
        values.put("c", "c1");
        
        IEvent event1 = new TupleEvent("stream", eventType, values);
        
        values.put("a", SupportConst.I_TWO);
        values.put("b", SupportConst.I_TWO);
        values.put("c", "c2");
        
        IEvent event2 = new TupleEvent("stream", eventType, values);
        
        values.put("a", SupportConst.I_THREE);
        values.put("b", SupportConst.I_THREE);
        values.put("c", "c3");
        
        IEvent event3 = new TupleEvent("stream", eventType, values);
        
        values.put("a", SupportConst.I_FOUR);
        values.put("b", SupportConst.I_FOUR);
        values.put("c", "c4");
        
        IEvent event4 = new TupleEvent("stream", eventType, values);
        
        values.put("a", SupportConst.I_FIVE);
        values.put("b", SupportConst.I_FIVE);
        values.put("c", "c5");
        
        IEvent event5 = new TupleEvent("stream", eventType, values);
        
        top.addView(child1);
        top.addView(child2);
        
        top.update(new IEvent[] {event3, event4, event5}, new IEvent[] {event1, event2});
        
        SupportViewDataCheck.checkOldData(child1, new IEvent[] {event1, event2});
        SupportViewDataCheck.checkNewData(child1, new IEvent[] {event3, event4, event5});
        
        SupportViewDataCheck.checkOldData(child2, new IEvent[] {event1, event2});
        SupportViewDataCheck.checkNewData(child2, new IEvent[] {event3, event4, event5});
    }
}
