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
 * 文 件 名:  SupportGroupViewDataCheck.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  z00134314
 * 修改时间:  2013-7-1
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.huawei.streaming.support;

import com.huawei.streaming.event.IEvent;

/**
 * <分组窗口数据检测类>
 * <功能详细描述>
 * 
 */
public class SupportGroupViewDataCheck
{
    /**
     * <检测分组值对应窗口中的旧事件>
     * <功能详细描述>
     * @param groupKey 分组值
     * @param childView 分组窗口支持类
     * @param events 旧事件
     */
    public static void checkOldData(Object groupKey, SupportGroupView childView, IEvent[] events)
    {
        IEvent[] oldData = childView.getLastOldDataByGroupKey(groupKey);
        SupportAssertUtil.assertEqualsExactOrder(events, oldData);
        childView.clearLastOldDataByGroupKey(groupKey);
    }
    
    /**
     * <检测分组值对应窗口中的新事件>
     * <功能详细描述>
     * @param groupKey 分组值
     * @param childView 分组窗口支持类
     * @param events 新事件
     */
    public static void checkNewData(Object groupKey, SupportGroupView childView, IEvent[] events)
    {
        IEvent[] newData = childView.getLastNewDataByGroupKey(groupKey);
        SupportAssertUtil.assertEqualsExactOrder(events, newData);
        childView.clearLastNewDataByGroupKey(groupKey);
    }
}
