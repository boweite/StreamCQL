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

package com.huawei.streaming.process.sort;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.common.MultiKey;
import com.huawei.streaming.support.SupportConst;

/**
 * 
 * <MultiKeyComparatorTest>
 * <功能详细描述>
 * 
 */
public class MultiKeyComparatorTest
{
    private static final double TWO_DOUBLE = 2.0;
    
    private List<SortCondition> sclist = null;
    
    private MultiKeyComparator mc = null;
    
    /**
     * <setup>
     * @throws Exception 异常
     */
    @Before
    public void setUp()
        throws Exception
    {
        sclist = new ArrayList<SortCondition>();
    }
    
    /**
     * <cleanup>
     * @throws Exception 异常
     */
    @After
    public void tearDown()
        throws Exception
    {
        sclist = null;
        mc = null;
    }
    
    /**
     * <测试构造函数>
     * <功能详细描述>
     */
    @Test
    public void testMultiKeyComparator()
    {
        try
        {
            mc = new MultiKeyComparator(null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            assertTrue(true);
        }
        
        try
        {
            mc = new MultiKeyComparator(sclist);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            assertTrue(true);
        }
    }
    
    /**
     * <测试比较函数>
     * <功能详细描述>
     */
    @Test
    public void testCompare()
    {
        Object[] obj1 = new Object[] {1, 1.0, "aaa"};
        Object[] obj2 = new Object[] {SupportConst.I_TWO, 1.0, "aaa"};
        Object[] obj3 = new Object[] {1, TWO_DOUBLE, "aaa"};
        Object[] obj4 = new Object[] {1, 1.0, "ab"};
        
        Object[] obj5 = new Object[] {SupportConst.I_TWO, TWO_DOUBLE, "aaa"};
        
        MultiKey m1 = new MultiKey(obj1);
        MultiKey m2 = new MultiKey(obj2);
        MultiKey m3 = new MultiKey(obj3);
        MultiKey m4 = new MultiKey(obj4);
        MultiKey m5 = new MultiKey(obj5);
        
        sclist.add(new SortCondition("i", SortEnum.ASC));
        sclist.add(new SortCondition("d", SortEnum.ASC));
        sclist.add(new SortCondition("s", SortEnum.ASC));
        mc = new MultiKeyComparator(sclist);
        
        Assert.assertEquals(-1, mc.compare(m1, m2));
        Assert.assertEquals(-1, mc.compare(m1, m3));
        Assert.assertEquals(-1, mc.compare(m1, m4));
        
        Assert.assertEquals(1, mc.compare(m2, m4));
        
        sclist.clear();
        sclist.add(new SortCondition("i", SortEnum.ASC));
        sclist.add(new SortCondition("d", SortEnum.DESC));
        sclist.add(new SortCondition("s", SortEnum.ASC));
        mc = new MultiKeyComparator(sclist);
        
        Assert.assertEquals(-1, mc.compare(m1, m2));
        Assert.assertEquals(1, mc.compare(m1, m3));
        Assert.assertEquals(-1, mc.compare(m1, m4));
        
        Assert.assertEquals(1, mc.compare(m2, m5));
        
        sclist.clear();
        sclist.add(new SortCondition("i", SortEnum.ASC));
        sclist.add(new SortCondition("d", SortEnum.DESC));
        mc = new MultiKeyComparator(sclist);
        
        try
        {
            mc.compare(m1, m2);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(true);
        }
        
    }
    
    /**
     * <测试比较单值函数>
     * <功能详细描述>
     */
    @Test
    public void testCompareValues()
    {
        int i1 = 1;
        int i2 = SupportConst.I_TWO;
        
        double d1 = 1.0;
        double d2 = TWO_DOUBLE;
        
        String s1 = "aaaa";
        String s2 = "b";
        
        Assert.assertEquals(-1, MultiKeyComparator.compareValues(i1, i2, SortEnum.ASC));
        Assert.assertEquals(1, MultiKeyComparator.compareValues(i1, i2, SortEnum.DESC));
        Assert.assertEquals(1, MultiKeyComparator.compareValues(i1, null, SortEnum.ASC));
        Assert.assertEquals(-1, MultiKeyComparator.compareValues(i1, null, SortEnum.DESC));
        
        Assert.assertEquals(-1, MultiKeyComparator.compareValues(s1, s2, SortEnum.ASC));
        Assert.assertEquals(1, MultiKeyComparator.compareValues(s1, s2, SortEnum.DESC));
        Assert.assertEquals(1, MultiKeyComparator.compareValues(s1, null, SortEnum.ASC));
        Assert.assertEquals(-1, MultiKeyComparator.compareValues(s1, null, SortEnum.DESC));
        
        Assert.assertEquals(-1, MultiKeyComparator.compareValues(d1, d2, SortEnum.ASC));
        Assert.assertEquals(1, MultiKeyComparator.compareValues(d1, d2, SortEnum.DESC));
        Assert.assertEquals(1, MultiKeyComparator.compareValues(d1, null, SortEnum.ASC));
        Assert.assertEquals(-1, MultiKeyComparator.compareValues(d1, null, SortEnum.DESC));
        
        /*Assert.assertEquals(-1, MultiKeyComparator.compareValues(i1, d2, SortEnum.ASC)); */
        
        try
        {
            MultiKeyComparator.compareValues(i1, s1, SortEnum.ASC);
            fail();
        }
        catch (ClassCastException e)
        {
            assertTrue(true);
        }
    }
    
}
