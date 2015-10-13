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

package com.huawei.streaming.process;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.event.IEvent;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.support.SupportConst;

/**
 * LimitProcess单元测试类
 */
public class LimitProcessTest
{
    /**
     * <setup>
     */
    @Before
    public void setUp()
    {
        
    }
    
    /**
     * <cleanup>
     */
    @After
    public void setDown()
    {
        
    }
    
    /**
     * Test method for {@link com.huawei.streaming.process.LimitProcess#process(com.huawei.streaming.event.IEvent[])}.
     * 测试用例一：构造参数为0，抛出异常
     */
    @Test
    public void testProcess1()
    {
        try
        {
            //构造测试数据
            LimitProcess limitProcess = new LimitProcess(0);
            IEvent[] events = new TupleEvent[SupportConst.I_TEN];
            
            //操作测试数据
            IEvent[] actualResult = limitProcess.process(events);
            
            //检验操作是否得到期望的结果
            IEvent[] expectedResult = null;
            Assert.assertArrayEquals(expectedResult, actualResult);
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertEquals("The limitNum is invalid,limitNum=0", e.getMessage());
        }
    }
    
    /**
     * Test method for {@link com.huawei.streaming.process.LimitProcess#process(com.huawei.streaming.event.IEvent[])}.
     * 测试用例二：事件为空
     */
    @Test
    public void testProcess2()
    {
        //构造测试数据
        LimitProcess limitProcess = new LimitProcess(SupportConst.I_FIVE);
        
        //操作测试数据
        IEvent[] actualResult = limitProcess.process(null);
        
        //检验操作是否得到期望的结果
        IEvent[] expectedResult = null;
        Assert.assertArrayEquals(expectedResult, actualResult);
        Assert.assertArrayEquals(null, actualResult);
    }
    
    /**
     * Test method for {@link com.huawei.streaming.process.LimitProcess#process(com.huawei.streaming.event.IEvent[])}.
     * 测试用例三：事件条数为0
     */
    @Test
    public void testProcess3()
    {
        //构造测试数据
        LimitProcess limitProcess = new LimitProcess(SupportConst.I_FIVE);
        IEvent[] events = new TupleEvent[0];
        
        //操作测试数据
        IEvent[] actualResult = limitProcess.process(events);
        
        //检验操作是否得到期望的结果
        IEvent[] expectedResult = events;
        Assert.assertArrayEquals(expectedResult, actualResult);
    }
    
    /**
     * Test method for {@link com.huawei.streaming.process.LimitProcess#process(com.huawei.streaming.event.IEvent[])}.
     * 测试用例四：limit数目等于事件条数
     */
    @Test
    public void testProcess4()
    {
        //构造测试数据
        LimitProcess limitProcess = new LimitProcess(SupportConst.I_TEN);
        IEvent[] events = new TupleEvent[SupportConst.I_TEN];
        
        //操作测试数据
        IEvent[] actualResult = limitProcess.process(events);
        
        //检验操作是否得到期望的结果
        IEvent[] expectedResult = events;
        Assert.assertEquals(expectedResult.length, actualResult.length);
    }
    
    /**
     * Test method for {@link com.huawei.streaming.process.LimitProcess#process(com.huawei.streaming.event.IEvent[])}.
     * 测试用例五：limit数目大于事件条数
     */
    @Test
    public void testProcess5()
    {
        //构造测试数据
        LimitProcess limitProcess = new LimitProcess(SupportConst.I_TWENTY);
        IEvent[] events = new TupleEvent[SupportConst.I_TEN];
        
        //操作测试数据
        IEvent[] actualResult = limitProcess.process(events);
        
        //检验操作是否得到期望的结果
        IEvent[] expectedResult = events;
        Assert.assertEquals(expectedResult.length, actualResult.length);
    }
    
    /**
     * Test method for {@link com.huawei.streaming.process.LimitProcess#process(com.huawei.streaming.event.IEvent[])}.
     * 测试用例六：limit数目大于0且小于事件条数
     */
    @Test
    public void testProcess6()
    {
        //构造测试数据
        LimitProcess limitProcess = new LimitProcess(SupportConst.I_FIVE);
        IEvent[] events = new TupleEvent[SupportConst.I_TEN];
        
        //操作测试数据
        IEvent[] actualResult = limitProcess.process(events);
        
        //检验操作是否得到期望的结果
        Assert.assertEquals(SupportConst.I_FIVE, actualResult.length);
    }
}
