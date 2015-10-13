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

import static org.junit.Assert.assertArrayEquals;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.common.MultiKey;
import com.huawei.streaming.common.Pair;
import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.support.SupportAssertUtil;
import com.huawei.streaming.support.SupportConst;

/**
 * 
 * CrossBiJoinComposerTest
 * <功能详细描述>
 * 
 */
public class CrossBiJoinComposerTest
{
    private static final double DOUBLE_58 = 5.8d;
    
    private TupleEventType item = new TupleEventType("item", new Attribute(Integer.class, "id"), new Attribute(
        String.class, "description"), new Attribute(Double.class, "price"));
    
    private TupleEventType sell = new TupleEventType("sell", new Attribute(Integer.class, "id"), new Attribute(
        String.class, "buyer"), new Attribute(Double.class, "price"), new Attribute(Integer.class, "number"));
    
    //    private TupleEventType out = new TupleEventType("out", new Attribute(Integer.class, "item.id"), new Attribute(
    //        String.class, "sellrecord.buyer"));
    
    private CrossBiJoinComposer crossJoin = null;
    
    private SimpleEventCollection leftColle = null;
    
    private SimpleEventCollection rightColle = null;
    
    private IEvent[][] newDataPerStream = null;
    
    private IEvent[][] oldDataPerStream = null;
    
    private Pair<Set<MultiKey>, Set<MultiKey>> result = null;
    
    /**
     * <setup>
     * @throws Exception 异常
     */
    @Before
    public void setUp()
        throws Exception
    {
        leftColle = new SimpleEventCollection("item", item);
        rightColle = new SimpleEventCollection("sell", sell);
        crossJoin = new CrossBiJoinComposer(leftColle, rightColle, false);
    }
    
    /**
     * <cleanup>
     * @throws Exception 异常
     */
    @After
    public void tearDown()
        throws Exception
    {
        leftColle = null;
        rightColle = null;
        crossJoin = null;
    }
    
    /**
     * testJoinOneMatch
     * <功能详细描述>
     */
    @Test
    public void testJoinOneMatch()
    {
        IEvent[][] firstdata = dataPrepare(SupportConst.I_TWO, SupportConst.I_THREE);
        IEvent[][] seconddata = dataPrepare(SupportConst.I_THREE, SupportConst.I_FIVE);
        
        newDataPerStream = new IEvent[SupportConst.I_TWO][];
        newDataPerStream[0] = new IEvent[1];
        newDataPerStream[1] = new IEvent[1];
        oldDataPerStream = new IEvent[SupportConst.I_TWO][];
        oldDataPerStream[0] = new IEvent[1];
        oldDataPerStream[1] = new IEvent[1];
        
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("id", SupportConst.I_FIVE);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        //加入item 一个事件 id=itemid
        newDataPerStream[0][0] = new TupleEvent("item", item, values);
        
        //        values.clear();
        //        values.put("id", sellid);
        //        values.put("buyer", "custom:");
        //        values.put("price", DOUBLE_58);
        //        values.put("number", SupportConst.FIVE);
        //        
        //        //加入sell 一个事件 id=sellid
        //        newDataPerStream[1][0] = new TupleEvent("sell", sell, values);
        
        result = crossJoin.join(newDataPerStream, null);
        
        //        Pair<Set<MultiKey>, Set<MultiKey>> expecteds =          
        //new Pair<Set<MultiKey>, Set<MultiKey>>(new LinkedHashSet<MultiKey>(), new LinkedHashSet<MultiKey>());
        
        Set<MultiKey> expSet = new LinkedHashSet<MultiKey>();
        Object[] obj = new Object[SupportConst.I_TWO];
        obj[0] = newDataPerStream[0][0];
        obj[1] = firstdata[1][0];
        
        expSet.add(new MultiKey(obj));
        
        obj = new Object[SupportConst.I_TWO];
        obj[0] = newDataPerStream[0][0];
        obj[1] = seconddata[1][0];
        expSet.add(new MultiKey(obj));
        
        //匹配两组事件（每组两个事件）
        assertArrayEquals(expSet.toArray(new MultiKey[expSet.size()]),
            result.getFirst().toArray(new MultiKey[result.getFirst().size()]));
        
    }
    
    /**
     * testNoMatch
     * <功能详细描述>
     */
    @Test
    public void testNoMatch()
    {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("id", SupportConst.I_FIVE);
        values.put("description", "item:");
        values.put("price", (double)SupportConst.I_TWENTY);
        
        newDataPerStream = new IEvent[SupportConst.I_TWO][];
        newDataPerStream[0] = new IEvent[1];
        newDataPerStream[1] = new IEvent[1];
        //加入item 一个事件 id=5
        newDataPerStream[0][0] = new TupleEvent("item", item, values);
        
        result = crossJoin.join(newDataPerStream, null);
        
        Set<MultiKey> expSet = new LinkedHashSet<MultiKey>();
        Object[] obj = new Object[SupportConst.I_TWO];
        obj[0] = newDataPerStream[0][0];
        
        Object[] att = new Object[sell.getSize()];
        
        obj[1] = new TupleEvent("sell", sell, att);
        
        expSet.add(new MultiKey(obj));
        
        //匹配一组事件（每组两个事件）
        SupportAssertUtil.assertEuqalsArrayValue(expSet.toArray(new MultiKey[expSet.size()]), result.getFirst()
            .toArray(new MultiKey[result.getFirst().size()]));
    }
    
    private IEvent[][] dataPrepare(int itemid, int sellid)
    {
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
        values.put("price", DOUBLE_58);
        values.put("number", SupportConst.I_FIVE);
        
        //加入sell 一个事件 id=sellid
        newDataPerStream[1][0] = new TupleEvent("sell", sell, values);
        
        crossJoin.maintainData(newDataPerStream, oldDataPerStream);
        
        return newDataPerStream;
    }
}
