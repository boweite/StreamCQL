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

import java.util.HashMap;

import com.huawei.streaming.common.MultiKey;
import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.expression.IExpression;
import com.huawei.streaming.view.ViewImpl;

/**
 * <分组保存视图>
 * 
 */
public class SupportGroupView extends ViewImpl
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -4662649721776665063L;
    
    private final HashMap<Object, IEvent[]> lastNewDataPerKey = new HashMap<Object, IEvent[]>();
    
    private final HashMap<Object, IEvent[]> lastOldDataPerKey = new HashMap<Object, IEvent[]>();
    
    /**
     * 分组表达式
     */
    private final IExpression[] exprs;
    
    /**
     * <默认构造函数>
     *@param exprs 分组表达式
     */
    public SupportGroupView(IExpression[] exprs)
    {
        if (null == exprs)
        {
            throw new IllegalArgumentException("Invalid group expression!");
        }
        
        this.exprs = exprs;
    }
    
    /**
     * <根据事件求分组值>
     * @param theEvent 事件
     * @return 分组值
     */
    protected Object getGroupKey(IEvent theEvent)
    {
        if (exprs.length == 1)
        {
            return exprs[0].evaluate(theEvent);
        }
        
        Object[] values = new Object[exprs.length];
        for (int i = 0; i < exprs.length; i++)
        {
            values[i] = exprs[i].evaluate(theEvent);
        }
        
        return new MultiKey(values);
    }
    
    private IEvent getCurrentEvent(IEvent[] newData, IEvent[] oldData)
    {
        if (null == newData && null == oldData)
        {
            return null;
        }
        
        IEvent event = null;
        
        if (null != newData)
        {
            event = newData[0];
            if (null != event)
            {
                return event;
            }
        }
        
        return oldData[0];
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(IEvent[] newData, IEvent[] oldData)
    {
        Object groupKey = getGroupKey(getCurrentEvent(newData, oldData));
        
        lastNewDataPerKey.put(groupKey, newData);
        lastOldDataPerKey.put(groupKey, oldData);
        
        updateChild(newData, oldData);
    }
    
    /**
     * <返回对应分组值的新数据>
     * <功能详细描述>
     * @param groupKey 分组值
     * @return 新数据
     */
    public IEvent[] getLastNewDataByGroupKey(Object groupKey)
    {
        return lastNewDataPerKey.get(groupKey);
    }
    
    /**
     * <清除对应分组值的新数据>
     * <功能详细描述>
     * @param groupKey 分组值
     */
    public void clearLastNewDataByGroupKey(Object groupKey)
    {
        lastNewDataPerKey.remove(groupKey);
        return;
    }
    
    /**
     * <返回对应分组值的旧数据>
     * <功能详细描述>
     * @param groupKey 分组值
     * @return 旧数据
     */
    public IEvent[] getLastOldDataByGroupKey(Object groupKey)
    {
        return lastOldDataPerKey.get(groupKey);
    }
    
    /**
     * <清除对应分组值的旧数据>
     * <功能详细描述>
     * @param groupKey 分组值
     */
    public void clearLastOldDataByGroupKey(Object groupKey)
    {
        lastOldDataPerKey.remove(groupKey);
        return;
    }
    
}
