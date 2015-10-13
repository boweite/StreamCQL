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

import java.util.ArrayList;
import java.util.List;

import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.EventTypeMng;
import com.huawei.streaming.event.TupleEventType;

/**
 * <事件管理支持类>
 * <功能详细描述>
 * 
 */
public class SupportEventMng
{
    private EventTypeMng mng;
    
    private TupleEventType input;
    
    private TupleEventType outputAggOnly;
    
    private TupleEventType outputAggAll;
    
    private TupleEventType outputAll;
    
    private TupleEventType output;
    
    private TupleEventType timeEventType;
    
    /**
     * <默认构造函数>
     *
     */
    public SupportEventMng()
    {
        mng = new EventTypeMng();
        //输入流类型
        List<Attribute> schema = new ArrayList<Attribute>();
        Attribute attributeA = new Attribute(Integer.class, "a");
        Attribute attributeB = new Attribute(Integer.class, "b");
        Attribute attributeC = new Attribute(String.class, "c");
        
        schema.add(attributeA);
        schema.add(attributeB);
        schema.add(attributeC);
        input = new TupleEventType("schemName", schema);
        //输出流类型
        List<Attribute> outschema = new ArrayList<Attribute>();
        Attribute attributeSumA = new Attribute(Integer.class, "sum(distinct a)");
        Attribute attributeAvgA = new Attribute(Integer.class, "avg(a)");
        
        outschema.add(attributeSumA);
        outschema.add(attributeAvgA);
        
        outputAggOnly = new TupleEventType("outschema", outschema);
        
        //输出流类型
        List<Attribute> outschema1 = new ArrayList<Attribute>();
        
        outschema1.add(attributeA);
        outschema1.add(attributeSumA);
        outschema1.add(attributeAvgA);
        
        outputAggAll = new TupleEventType("outschema1", outschema1);
        
        //输出流类型
        List<Attribute> outschema2 = new ArrayList<Attribute>();
        
        outschema2.add(attributeA);
        output = new TupleEventType("outschema2", outschema2);
        
        //输出流类型
        List<Attribute> outschema3 = new ArrayList<Attribute>();
        
        outschema3.add(attributeA);
        outschema3.add(attributeB);
        outschema3.add(attributeC);
        outputAll = new TupleEventType("outschema3", outschema3);
        
        //输出流类型
        List<Attribute> timeschema = new ArrayList<Attribute>();
        
        timeschema.add(attributeA);
        timeschema.add(attributeB);
        timeschema.add(attributeC);
        
        Attribute attributeTime = new Attribute(Long.class, "timestamp");
        timeschema.add(attributeTime);
        timeEventType = new TupleEventType("attributeTime", timeschema);
        
        mng.addEventType(input);
        mng.addEventType(outputAggOnly);
        mng.addEventType(outputAggAll);
        mng.addEventType(outputAll);
        mng.addEventType(output);
        mng.addEventType(timeEventType);
    }
    
    public EventTypeMng getEventMng()
    {
        return mng;
    }
    
    public TupleEventType getInput()
    {
        return input;
    }
    
    public TupleEventType getOutputAggOnly()
    {
        return outputAggOnly;
    }
    
    public TupleEventType getOutputAggAll()
    {
        return outputAggAll;
    }
    
    public TupleEventType getOutputAll()
    {
        return outputAll;
    }
    
    public TupleEventType getOutput()
    {
        return output;
    }
    
    /**
     * 返回 timeEventType
     * @return 返回 timeEventType
     */
    public TupleEventType getTimeEventType()
    {
        return timeEventType;
    }
}
