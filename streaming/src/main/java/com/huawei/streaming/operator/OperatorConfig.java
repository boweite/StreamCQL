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

package com.huawei.streaming.operator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * 用于给算子设置用户自定义的配置数据
 * <功能详细描述>
 * 
 */
public class OperatorConfig
{
    /**
     * 获得对象属性
     * <功能详细描述>
     * @param o 输入对象
     * @return 属性列表
     */
    protected Map<String, Object> getFieldInfo(Object o)
    {
        Map<String, Object> para = new HashMap<String, Object>();
        
        Field[] fields = o.getClass().getDeclaredFields();
        
        for (int i = 0; i < fields.length; i++)
        {
            try
            {
                para.put(fields[i].getName(), fields[i].get(o));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new RuntimeException("Error get operator config map", e);
            }
        }
        
        return para;
    }
    
    /**
     * 设置对象属性值
     * <功能详细描述>
     * @param map 对象属性值
     * @param o 对象
     */
    protected void setFieldsInfo(Map<String, Object> map, Object o)
    {
        Field[] fields = o.getClass().getDeclaredFields();
        
        String f;
        Object v = null;
        for (int i = 0; i < fields.length; i++)
        {
            f = fields[i].getName();
            for (Entry<String, Object> m : map.entrySet())
            {
                if (f.equals(m.getKey()))
                {
                    v = m.getValue();
                    break;
                }
            }
            try
            {
                if (null != v)
                {
                    fields[i].setAccessible(true);
                    fields[i].set(o, v);
                    v = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new RuntimeException("Error set operator custom config", e);
            }
        }
    }
}
