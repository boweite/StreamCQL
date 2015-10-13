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

package com.huawei.streaming.event;

import java.io.Serializable;

/**
 * 
 * 事件接口
 * <功能详细描述>
 * 
 */
public interface IEvent extends Serializable
{
    /**
     * 获取事件某个属性的值，如果属性名称不存在，返回null
     * @param propertyName 属性名称
     * @return 属性的值
     */
    public Object getValue(String propertyName);
    
    /**
     * 获取事件类型
     * @return 事件类型
     */
    public IEventType getEventType();
    
    /**
     * 获取第几个列的值
     * @param index 序号
     * @return 属性的值
     */
    public Object getValue(int index);
    
    /**
     * 获取所有值
     * <功能详细描述>
     * @return 事件所有属性值
     */
    public Object[] getAllValues();
    
    /**
     * 获取事件流名称
     * <功能详细描述>
     * @return 事件流名称
     */
    public String getStreamName();
    
    /**
     * 判断是否是标记事件
     * @return  true-标记事件；false-非标记事件
     */
    public boolean isFlagEvent();
    
    /**
     * 将该事件标记为标记事件
     * @exception/throws [违例类型] [违例说明]
     */
    void setFlagEvent();
    
    /**
     * 获取属性的索引
     * @param propertyName 属性名称
     * @return 属性索引
     */
    int getIndexByPropertyName(String propertyName);
}
