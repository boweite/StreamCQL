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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.huawei.streaming.support.SupportConst;

/**
 * 
 * <ObjectComparatorTest>
 * <功能详细描述>
 * 
 */
public class ObjectComparatorTest
{
    private SortCondition condition = null;
    
    private ObjectComparator oc = null;
    
    /**
     * <setup>
     * @throws Exception 异常
     */
    @Before
    public void setUp()
        throws Exception
    {
        
    }
    
    /**
     * <cleanup>
     * @throws Exception 异常
     */
    @After
    public void tearDown()
        throws Exception
    {
        condition = null;
        oc = null;
    }
    
    /**
     * <测试构造函数>
     * <功能详细描述>
     */
    @Test
    public void testObjectComparator()
    {
        try
        {
            oc = new ObjectComparator(null);
            fail();
        }
        catch (IllegalArgumentException e)
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
        String attr = "attr";
        int i1 = 1;
        int i2 = SupportConst.I_TWO;
        Integer i3 = null;
        
        String s1 = "aaaa";
        String s2 = "ab";
        String s3 = null;
        
        //ASC 升序
        condition = new SortCondition(attr, SortEnum.ASC);
        oc = new ObjectComparator(condition);
        
        //Integer
        Assert.assertEquals(-1, oc.compare(i1, i2));
        Assert.assertEquals(1, oc.compare(i1, i3));
        Assert.assertEquals(0, oc.compare(i1, i1));
        Assert.assertEquals(0, oc.compare(i3, i3));
        
        //String
        Assert.assertEquals(-1, oc.compare(s1, s2));
        Assert.assertEquals(1, oc.compare(s1, s3));
        Assert.assertEquals(0, oc.compare(s1, s1));
        Assert.assertEquals(0, oc.compare(s3, s3));
        
        //降序
        condition = new SortCondition(attr, SortEnum.DESC);
        oc = new ObjectComparator(condition);
        
        //Integer
        Assert.assertEquals(1, oc.compare(i1, i2));
        Assert.assertEquals(-1, oc.compare(i1, i3));
        Assert.assertEquals(0, oc.compare(i1, i1));
        Assert.assertEquals(0, oc.compare(i3, i3));
        
        //String
        Assert.assertEquals(1, oc.compare(s1, s2));
        Assert.assertEquals(-1, oc.compare(s1, s3));
        Assert.assertEquals(0, oc.compare(s1, s1));
        Assert.assertEquals(0, oc.compare(s3, s3));
    }
    
}
