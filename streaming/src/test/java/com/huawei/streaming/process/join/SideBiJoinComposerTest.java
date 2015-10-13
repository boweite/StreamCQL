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
import static org.junit.Assert.assertEquals;

import java.util.LinkedHashSet;
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
import com.huawei.streaming.expression.PropertyValueExpression;
import com.huawei.streaming.support.SupportAssertUtil;
import com.huawei.streaming.support.SupportConst;

/**
 * SideBiJoinComposerTest
 * <功能详细描述>
 * 
 */
public class SideBiJoinComposerTest
{
    private TupleEventType item = new TupleEventType("item", new Attribute(Integer.class, "id"), new Attribute(
        String.class, "description"), new Attribute(Double.class, "price"));
    
    private TupleEventType sell = new TupleEventType("sell", new Attribute(Integer.class, "id"), new Attribute(
        String.class, "buyer"), new Attribute(Double.class, "price"), new Attribute(Integer.class, "number"));
    
    private SideBiJoinComposer sideJoin = null;
    
    private IndexedMultiPropertyEventCollection leftColle = null;
    
    private IndexedMultiPropertyEventCollection rightColle = null;
    
    private PropertyValueExpression[] exprleft = null;
    
    private PropertyValueExpression[] exprright = null;
    
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
        exprleft = new PropertyValueExpression[1];
        //id
        exprleft[0] = new PropertyValueExpression("id", Integer.class);
        
        exprright = new PropertyValueExpression[1];
        //id
        exprright[0] = new PropertyValueExpression("id", Integer.class);
        
        //item.id=sell.id
        leftColle = new IndexedMultiPropertyEventCollection("item", item, exprleft);
        rightColle = new IndexedMultiPropertyEventCollection("sell", sell, exprright);
        
        sideJoin = new SideBiJoinComposer(leftColle, rightColle, SideJoinType.LEFTJOIN, false);
        
        newDataPerStream = new IEvent[SupportConst.I_TWO][];
        newDataPerStream[0] = new IEvent[1];
        newDataPerStream[1] = new IEvent[1];
        oldDataPerStream = new IEvent[SupportConst.I_TWO][];
        oldDataPerStream[0] = new IEvent[1];
        oldDataPerStream[1] = new IEvent[1];
        
    }
    
    /**
     * <cleanup>
     * @throws Exception 异常
     */
    @After
    public void tearDown()
        throws Exception
    {
        exprleft = null;
        exprright = null;
        leftColle = null;
        rightColle = null;
        sideJoin = null;
    }
    
    /**
     * testJoinOneMatch
     * 左流JOIN，左流事件匹配，右流中有一匹配事件，返回一组匹配事件
     */
    @Test
    public void testJoinOneMatch()
    {
        Pair<IEvent[][], IEvent[][]> prepare1 =
            SupportDataPrepare.dataPrepare(SupportConst.I_TWO, SupportConst.I_THREE);
        Pair<IEvent[][], IEvent[][]> prepare2 =
            SupportDataPrepare.dataPrepare(SupportConst.I_THREE, SupportConst.I_FIVE);
        
        sideJoin.maintainData(prepare1.getFirst(), prepare1.getSecond());
        sideJoin.maintainData(prepare2.getFirst(), prepare2.getSecond());
        
        newDataPerStream[0][0] = SupportDataPrepare.genItemEvent(SupportConst.I_FIVE);
        
        result = sideJoin.join(newDataPerStream, null);
        
        Set<MultiKey> expSet = new LinkedHashSet<MultiKey>();
        Object[] obj = new Object[SupportConst.I_TWO];
        obj[0] = newDataPerStream[0][0];
        obj[1] = prepare2.getFirst()[1][0];
        
        expSet.add(new MultiKey(obj));
        
        //匹配一组事件（每组两个事件）
        assertArrayEquals(expSet.toArray(new MultiKey[expSet.size()]),
            result.getFirst().toArray(new MultiKey[result.getFirst().size()]));
    }
    
    /**
     * testJoinNullMatch
     * 左流JOIN，左流事件匹配，右流中无匹配事件，返回一组匹配事件，其中右流事件为所有属性值为NULL的事件
     */
    @Test
    public void testJoinNullMatch()
    {
        Pair<IEvent[][], IEvent[][]> prepare1 =
            SupportDataPrepare.dataPrepare(SupportConst.I_TWO, SupportConst.I_THREE);
        Pair<IEvent[][], IEvent[][]> prepare2 =
            SupportDataPrepare.dataPrepare(SupportConst.I_THREE, SupportConst.I_FIVE);
        
        sideJoin.maintainData(prepare1.getFirst(), prepare1.getSecond());
        sideJoin.maintainData(prepare2.getFirst(), prepare2.getSecond());
        //左流JOIN，左流事件匹配，右流中无匹配事件，返回所有属性值为NULL的事件
        newDataPerStream[0][0] = SupportDataPrepare.genItemEvent(SupportConst.I_SIX);
        
        result = sideJoin.join(newDataPerStream, null);
        
        Set<MultiKey> expSet = new LinkedHashSet<MultiKey>();
        Object[] obj = new Object[SupportConst.I_TWO];
        obj[0] = newDataPerStream[0][0];
        Object[] att = new Object[sell.getSize()];
        obj[1] = new TupleEvent("sell", sell, att);
        
        expSet.add(new MultiKey(obj));
        //有一组结果事件
        SupportAssertUtil.assertEuqalsArrayValue(expSet.toArray(new MultiKey[expSet.size()]), result.getFirst()
            .toArray(new MultiKey[result.getFirst().size()]));
        assertEquals(1L, (long)result.getFirst().size());
    }
    
    /**
     * testJoinNoMatch
     * 左流JOIN，右流事件匹配，左流中无匹配事件，返回NULL结果
     */
    @Test
    public void testJoinNoMatch()
    {
        Pair<IEvent[][], IEvent[][]> prepare1 =
            SupportDataPrepare.dataPrepare(SupportConst.I_TWO, SupportConst.I_THREE);
        Pair<IEvent[][], IEvent[][]> prepare2 =
            SupportDataPrepare.dataPrepare(SupportConst.I_THREE, SupportConst.I_FIVE);
        
        sideJoin.maintainData(prepare1.getFirst(), prepare1.getSecond());
        sideJoin.maintainData(prepare2.getFirst(), prepare2.getSecond());
        
        //左流JOIN，右流事件匹配，左流中无匹配事件，返回NULL结果
        newDataPerStream[1][0] = SupportDataPrepare.genItemEvent(SupportConst.I_SIX);
        
        result = sideJoin.join(newDataPerStream, null);
        
        Set<MultiKey> expSet = new LinkedHashSet<MultiKey>();
        Object[] obj = new Object[SupportConst.I_TWO];
        obj[0] = newDataPerStream[0][0];
        obj[1] = prepare2.getFirst()[1][0];
        
        expSet.add(new MultiKey(obj));
        //没有匹配事件
        assertEquals(0L, (long)result.getFirst().size());
    }
}
