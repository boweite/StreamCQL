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

import java.util.LinkedList;
import java.util.List;

import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.view.IRenew;
import com.huawei.streaming.view.IView;

/**
 * <多实例测试支持类>
 * <功能详细描述>
 * 
 */
public class SupportMultiInstanceView extends SupportView implements IRenew
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 6357374387527120821L;
    
    private static List<SupportMultiInstanceView> instances = new LinkedList<SupportMultiInstanceView>();
    
    /**
     * <默认构造函数>
     *
     */
    public SupportMultiInstanceView()
    {
        instances.add(this);
    }
    
    /**
     * {@inheritDoc}
     */
    public void update(IEvent[] newData, IEvent[] oldData)
    {
        setLastNewData(newData);
        setLastOldData(oldData);
        
        updateChild(newData, oldData);
    }
    
    public static List<SupportMultiInstanceView> getInstances()
    {
        return instances;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public IView renewView()
    {
        return new SupportMultiInstanceView();
    }
}
