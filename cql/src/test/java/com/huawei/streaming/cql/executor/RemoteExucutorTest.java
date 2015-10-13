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

package com.huawei.streaming.cql.executor;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.huawei.streaming.cql.LocalTaskCommons;

/**
 * xml执行器测试
 *
 */
public class RemoteExucutorTest
{
    
    private static final String BASICPATH = File.separator + "executor" + File.separator + "plans" + File.separator;
    
    /**
     * 所有测试开始前 执行的方法
     *
     * @throws Exception 异常
     */
    @BeforeClass
    public static void setUpBeforeClass()
        throws Exception
    {
        LocalTaskCommons.startZookeeperServer();
    }
    
    /**
     * 所有测试执行之后执行的清理方法
     *
     * @throws Exception 异常
     */
    @AfterClass
    public static void tearDownAfterClass()
        throws Exception
    {
        LocalTaskCommons.stopZooKeeperServer();
    }
    
    /**
     * 简单功能测试
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testExecuteSimpleFilter()
        throws Exception
    {
        LocalTaskCommons.localSubmitXML(BASICPATH, "SimpleFilter");
    }
    
    /**
     * 简单功能测试
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testExecuteSimplePlusFilter()
        throws Exception
    {
        LocalTaskCommons.localSubmitXML(BASICPATH, "SimplePlusFilter");
    }
}
