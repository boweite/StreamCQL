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

package com.huawei.streaming.serde;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.huawei.streaming.config.StreamingConfig;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.exception.StreamSerDeException;
import com.huawei.streaming.exception.StreamingException;
import com.huawei.streaming.util.DataTypeUtils;
import com.huawei.streaming.util.StreamingDataType;

/**
 * 序列化基础类，实现了数据实例化的一些基本方法
 * 
 */
public abstract class BaseSerDe implements StreamSerDe
{
    private static final long serialVersionUID = 6699441541558471301L;
    
    private static final Logger LOG = LoggerFactory.getLogger(BaseSerDe.class);
    
    private TupleEventType schema;
    
    private StreamingConfig config;

    /**
     * 转换tupleevent到数据列表
     * @param event 事件
     * @return 数据
     */
    public static List<Object[]> changeEventsToList(TupleEvent event)
    {
        List<Object[]> results = Lists.newArrayList();
        if (event == null)
        {
            return results;
        }
        results.add(event.getAllValues());
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConfig(StreamingConfig conf) throws StreamingException
    {
        config = conf;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StreamingConfig getConfig()
    {
        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSchema(TupleEventType outputSchema)
    {
        this.schema = outputSchema;
    }

    /**
     * 根据数据列的数组创建所有datatype的实例
     * @param values 数据列数组
     * @return Datatype数据数组
     * @throws StreamSerDeException 数据反序列化异常
     */
    protected List<Object[]> createAllInstance(List<Object[]> values)
        throws StreamSerDeException
    {
        if (values == null || values.size() == 0)
        {
            return Lists.newArrayList();
        }
        
        List<Object[]> list = new ArrayList<Object[]>(values.size());
        for (Object[] strarr : values)
        {
            validateColumnSize(strarr);
            Object[] arr = new Object[schema.getAllAttributes().length];
            for (int i = 0; i < schema.getAllAttributeTypes().length; i++)
            {
                arr[i] = createInstance(schema.getAllAttributeTypes()[i], strarr[i]);
            }
            list.add(arr);
        }
        return list;
    }
    
    /**
     * 将计算结果输出为字符串形式
     * @param value 计算结果
     * @return 字符串形式的计算结果
     * @throws StreamSerDeException 无法转化为字符串类型
     */
    protected String serializeToString(Object value) throws StreamSerDeException
    {
        if(null == value)
        {
            return "";
        }
        
        try
        {
            StreamingDataType dataType = StreamingDataType.getDataType(value.getClass());
            return dataType.toStringValue(value);
        }
        catch (StreamingException e)
        {
            LOG.warn("Failed to convert value to string type, this output line will ignore.");
            throw new StreamSerDeException("Failed to convert value to string type, this output line will ignord.");
        }
    }
    
    private void validateColumnSize(Object[] strArr)
        throws StreamSerDeException
    {
        if(schema == null || schema.getAllAttributeTypes() == null)
        {
            LOG.warn("Can not found output schema. ");
            throw new StreamSerDeException("Can not found output schema. ");
        }
        
        if (strArr.length != schema.getAllAttributeTypes().length)
        {
            //序列化反序列化异常使用warn级别，因为仅仅会导致当前数据丢掉，不会导致进程退出
            LOG.warn("Deserializer result array size doesn't equals with schema column size, "
                + "schema size :{}, deserializer size :{}.", schema.getAllAttributeTypes().length, strArr.length);
            throw new StreamSerDeException(
                "Deserializer result array size doesn't equals with schema column size, schema size :"
                    + schema.getAllAttributeTypes().length + ", deserializer size :" + strArr.length);
        }
    }
    
    /**
     * 为所有的数据列创建空对象
     * @return 空的数据类型数组
     * @throws StreamSerDeException 数据反序列化异常
     */
    protected List<Object[]> createAllNull()
        throws StreamSerDeException
    {
        Object[] arr = new Object[schema.getAllAttributes().length];
        for (int i = 0; i < schema.getAllAttributeTypes().length; i++)
        {
            arr[i] = createInstance(schema.getAllAttributeTypes()[i]);
        }
        List<Object[]> list = Lists.newArrayList();
        list.add(arr);
        return list;
    }
    
    /**
     * 创建一个空数据。数据类型来自clazz
     * @param clazz 空数据对应的数据类型class
     * @return 创建好的空数据
     * @throws StreamSerDeException 数据反序列化异常
     */
    protected Object createInstance(Class< ? extends Object> clazz)
        throws StreamSerDeException
    {
        return createInstance(clazz, null);
    }
    
    /**
     * 根据指定类型创建数据实例
     * @param clazz 数据类型
     * @param value 数据值
     * @return 数据实例
     * @throws StreamSerDeException 数据反序列化异常
     */
    protected Object createInstance(Class< ? > clazz, Object value)
        throws StreamSerDeException
    {
        try
        {
            return DataTypeUtils.createValue(clazz, value == null ? null : value.toString());
        }
        catch (StreamingException e)
        {
            throw new StreamSerDeException(e.getMessage(), e);
        }
        
    }

    /**
     * {@inheritDoc}
     */
    public TupleEventType getSchema()
    {
        return schema;
    }
}
