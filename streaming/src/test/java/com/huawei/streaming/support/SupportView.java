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
import com.huawei.streaming.view.ViewImpl;

/**
 * <视图支持类>
 * <功能详细描述>
 * 
 */
public class SupportView extends ViewImpl
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -4242970049994206086L;
    
    private IEvent[] lastNewData;
    
    private IEvent[] lastOldData;
    
    /** {@inheritDoc} */
    
    @Override
    public void update(IEvent[] newData, IEvent[] oldData)
    {
        this.lastNewData = newData;
        this.lastOldData = oldData;
        
        updateChild(newData, oldData);
    }
    
    public IEvent[] getLastNewData()
    {
        return lastNewData;
    }
    
    public IEvent[] getLastOldData()
    {
        return lastOldData;
    }
    
    public void setLastNewData(IEvent[] lastNewData)
    {
        this.lastNewData = lastNewData;
    }
    
    public void setLastOldData(IEvent[] lastOldData)
    {
        this.lastOldData = lastOldData;
    }
    
    /**
     * <clearLastOldData>
     * <功能详细描述>
     */
    public void clearLastOldData()
    {
        lastOldData = null;
    }
    
    /**
     * <clearLastNewData>
     * <功能详细描述>
     */
    public void clearLastNewData()
    {
        lastNewData = null;
    }
}
