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

package com.huawei.streaming.window;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.IEventType;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.support.SupportConst;
import com.huawei.streaming.support.SupportEventMng;
import com.huawei.streaming.support.SupportView;
import com.huawei.streaming.support.SupportViewDataCheck;
import com.huawei.streaming.view.FirstLevelStream;

/**
 * 
 * <LengthSlideWindowTest>
 * <功能详细描述>
 * 
 */
public class LengthSlideWindowTest
{
    private LengthSlideWindow myView = null;
    
    private SupportView childView = null;
    
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
        myView = new LengthSlideWindow(SupportConst.I_FIVE);
        childView = new SupportView();
        myView.addView(childView);
        mng = new SupportEventMng();
        eventType = mng.getInput();
    }
    
    /**
     * <cleanup>
     * @throws Exception 异常
     */
    @After
    public void tearDown()
        throws Exception
    {
        myView.removeAllViews();
        mng = null;
        eventType = null;
        myView = null;
        childView = null;
    }
    
    /**
     * <测试构造函数>
     * <功能详细描述>
     */
    @Test
    public void testLengthSlideWindow()
    {
        try
        {
            myView = new LengthSlideWindow(0);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            assertTrue(true);
        }
    }
    
    /**
     * <测试窗口更新>
     * <功能详细描述>
     */
    @Test
    public void testUpdate()
    {
        FirstLevelStream stream = new FirstLevelStream();
        stream.addView(myView);
        
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("a", 1);
        values.put("b", 1);
        values.put("c", "c1");
        
        IEvent event1 = new TupleEvent("stream", eventType, values);
        stream.add(event1);
        SupportViewDataCheck.checkOldData(childView, null);
        SupportViewDataCheck.checkNewData(childView, new IEvent[] {event1});
        
        values.put("a", SupportConst.I_TWO);
        values.put("b", SupportConst.I_TWO);
        values.put("c", "c2");
        
        IEvent event2 = new TupleEvent("stream", eventType, values);
        stream.add(event2);
        SupportViewDataCheck.checkOldData(childView, null);
        SupportViewDataCheck.checkNewData(childView, new IEvent[] {event2});
        
        values.put("a", SupportConst.I_THREE);
        values.put("b", SupportConst.I_THREE);
        values.put("c", "c3");
        
        IEvent event3 = new TupleEvent("stream", eventType, values);
        stream.add(event3);
        SupportViewDataCheck.checkOldData(childView, null);
        SupportViewDataCheck.checkNewData(childView, new IEvent[] {event3});
        
        values.put("a", SupportConst.I_FOUR);
        values.put("b", SupportConst.I_FOUR);
        values.put("c", "c4");
        
        IEvent event4 = new TupleEvent("stream", eventType, values);
        stream.add(event4);
        SupportViewDataCheck.checkOldData(childView, null);
        SupportViewDataCheck.checkNewData(childView, new IEvent[] {event4});
        
        values.put("a", SupportConst.I_FIVE);
        values.put("b", SupportConst.I_FIVE);
        values.put("c", "c5");
        
        IEvent event5 = new TupleEvent("stream", eventType, values);
        stream.add(event5);
        SupportViewDataCheck.checkOldData(childView, null);
        SupportViewDataCheck.checkNewData(childView, new IEvent[] {event5});
        
        values.put("a", SupportConst.I_SIX);
        values.put("b", SupportConst.I_SIX);
        values.put("c", "c6");
        
        IEvent event6 = new TupleEvent("stream", eventType, values);
        stream.add(event6);
        SupportViewDataCheck.checkOldData(childView, new IEvent[] {event1});
        SupportViewDataCheck.checkNewData(childView, new IEvent[] {event6});
        
        values.put("a", SupportConst.I_SEVEN);
        values.put("b", SupportConst.I_SEVEN);
        values.put("c", "c7");
        
        IEvent event7 = new TupleEvent("stream", eventType, values);
        stream.add(event7);
        SupportViewDataCheck.checkOldData(childView, new IEvent[] {event2});
        SupportViewDataCheck.checkNewData(childView, new IEvent[] {event7});
        
        values.put("a", SupportConst.I_EIGHT);
        values.put("b", SupportConst.I_EIGHT);
        values.put("c", "c8");
        
        IEvent event8 = new TupleEvent("stream", eventType, values);
        stream.add(event8);
        SupportViewDataCheck.checkOldData(childView, new IEvent[] {event3});
        SupportViewDataCheck.checkNewData(childView, new IEvent[] {event8});
        
        values.put("a", SupportConst.I_NINE);
        values.put("b", SupportConst.I_NINE);
        values.put("c", "c9");
        
        IEvent event9 = new TupleEvent("stream", eventType, values);
        stream.add(event9);
        SupportViewDataCheck.checkOldData(childView, new IEvent[] {event4});
        SupportViewDataCheck.checkNewData(childView, new IEvent[] {event9});
        
        values.put("a", SupportConst.I_TEN);
        values.put("b", SupportConst.I_TEN);
        values.put("c", "c10");
        
        IEvent event10 = new TupleEvent("stream", eventType, values);
        stream.add(event10);
        SupportViewDataCheck.checkOldData(childView, new IEvent[] {event5});
        SupportViewDataCheck.checkNewData(childView, new IEvent[] {event10});
    }
    
    /**
     * <测试返回窗口长度>
     * <功能详细描述>
     */
    @Test
    public void testGetKeepLength()
    {
        assertEquals(SupportConst.I_FIVE, myView.getKeepLength());
    }
    
}
