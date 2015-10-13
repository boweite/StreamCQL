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

package com.huawei.streaming.support;

import com.huawei.streaming.event.IEvent;

/**
 * <视图数据检测类>
 * <功能详细描述>
 * 
 */
public class SupportViewDataCheck
{
    /**
     * <checkOldData>
     * <功能详细描述>
     * @param childView 视图
     * @param events 期望值
     */
    public static void checkOldData(SupportView childView, IEvent[] events)
    {
        IEvent[] oldData = childView.getLastOldData();
        SupportAssertUtil.assertEqualsExactOrder(events, oldData);
        childView.clearLastOldData();
        
    }
    
    /**
     * <checkNewData>
     * <功能详细描述>
     * @param childView 视图
     * @param events 期望值
     */
    public static void checkNewData(SupportView childView, IEvent[] events)
    {
        IEvent[] newData = childView.getLastNewData();
        SupportAssertUtil.assertEqualsExactOrder(events, newData);
        childView.clearLastNewData();
    }
    
}
