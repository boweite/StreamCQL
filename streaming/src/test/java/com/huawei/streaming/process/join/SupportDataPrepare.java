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

package com.huawei.streaming.process.join;

import java.util.HashMap;
import java.util.Map;

import com.huawei.streaming.common.Pair;
import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.support.SupportConst;

/**
 * 
 * JOIN数据准备
 * <功能详细描述>
 * 
 */
public class SupportDataPrepare
{
    private static TupleEventType item = new TupleEventType("item", new Attribute(Integer.class, "id"), new Attribute(
        String.class, "description"), new Attribute(Double.class, "price"));
    
    private static TupleEventType sell = new TupleEventType("sell", new Attribute(Integer.class, "id"), new Attribute(
        String.class, "buyer"), new Attribute(Double.class, "price"), new Attribute(Integer.class, "number"));
    
    /**
     * 数据生成准备
     * <功能详细描述>
     * @param itemid itemid
     * @param sellid sellid
     * @return 数据生成
     */
    public static Pair<IEvent[][], IEvent[][]> dataPrepare(int itemid, int sellid)
    {
        IEvent[][] newDataPerStream = null;
        IEvent[][] oldDataPerStream = null;
        
        newDataPerStream = new IEvent[SupportConst.I_TWO][];
        newDataPerStream[0] = new IEvent[1];
        newDataPerStream[1] = new IEvent[1];
        oldDataPerStream = new IEvent[SupportConst.I_TWO][];
        oldDataPerStream[0] = new IEvent[1];
        oldDataPerStream[1] = new IEvent[1];
        
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("id", itemid);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        //加入item 一个事件 id=itemid
        newDataPerStream[0][0] = new TupleEvent("item", item, values);
        
        values.clear();
        values.put("id", sellid);
        values.put("buyer", "custom:");
        values.put("price", (double)SupportConst.I_NINE);
        values.put("number", SupportConst.I_FIVE);
        
        //加入sell 一个事件 id=sellid
        newDataPerStream[1][0] = new TupleEvent("sell", sell, values);
        
        //        crossJoin.maintainData(newDataPerStream, oldDataPerStream);
        
        return new Pair<IEvent[][], IEvent[][]>(newDataPerStream, oldDataPerStream);
    }
    
    /**
     * 生成ITEM事件
     * <功能详细描述>
     * @param itemid itemid
     * @return ITEM事件
     */
    public static IEvent genItemEvent(int itemid)
    {
        
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("id", itemid);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        return new TupleEvent("item", item, values);
    }
    
    /**
     * 生成SELL事件
     * <功能详细描述>
     * @param sellid sellid
     * @return SELL事件
     */
    public static IEvent genSellEvent(int sellid)
    {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("id", sellid);
        values.put("buyer", "custom:");
        values.put("price", (double)SupportConst.I_SIX);
        values.put("number", SupportConst.I_FIVE);
        
        return new TupleEvent("sell", sell, values);
    }
}
