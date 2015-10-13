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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.IEventType;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.support.SupportEventMng;
import com.huawei.streaming.support.SupportView;

/**
 * 
 * <FirstLevelStreamTest>
 * <功能详细描述>
 * 
 */
public class FirstLevelStreamTest
{
    private FirstLevelStream stream = null;
    
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
        stream = new FirstLevelStream();
        childView = new SupportView();
        stream.addView(childView);
        mng = new SupportEventMng();
        eventType = mng.getInput();
    }
    
    /**
     * <testAdd>
     * <功能详细描述>
     */
    @Test
    public void testAdd()
    {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("a", 1);
        values.put("b", 1);
        values.put("c", "c1");
        
        IEvent event1 = new TupleEvent("stream", eventType, values);
        
        childView.clearLastNewData();
        stream.add(event1);
        
        assertTrue(childView.getLastNewData() != null);
        assertEquals(1, childView.getLastNewData().length);
        assertEquals(event1, childView.getLastNewData()[0]);
        
        // Remove view
        childView.clearLastNewData();
        stream.removeView(childView);
        stream.add(event1);
        assertTrue(childView.getLastNewData() == null);
    }
    
}
