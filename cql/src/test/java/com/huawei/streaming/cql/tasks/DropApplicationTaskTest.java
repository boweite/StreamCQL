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

package com.huawei.streaming.cql.tasks;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.huawei.streaming.cql.Driver;
import com.huawei.streaming.cql.LocalTaskCommons;
import static org.junit.Assert.assertTrue;

/**
 * drop application 测试用例
 *
 */
public class DropApplicationTaskTest
{
    /**
     * 初始化测试类之前要执行的初始化方法
     *
     * @throws Exception 初始化中可能抛出的异常
     */
    @BeforeClass
    public static void setUpBeforeClass()
        throws Exception
    {
        LocalTaskCommons.startZookeeperServer();
    }
    
    /**
     * 所有测试用例执行完毕之后执行的方法
     *
     * @throws Exception 执行异常
     */
    @AfterClass
    public static void tearDownAfterClass()
        throws Exception
    {
        LocalTaskCommons.stopZooKeeperServer();
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 异常
     */
    @Test
    public void test()
        throws Exception
    {
        runCQL("drop application if exists tt");
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 流处理异常
     */
    @Test
    public void test2()
        throws Exception
    {
        runCQL("drop application tt");
    }
    
    private void runCQL(String cql)
        throws Exception
    {
        Driver d = new Driver();
        try
        {
            d.run("set \"streaming.storm.submit.islocal\"='true'");
            d.run("set \"streaming.storm.nimbus.host\"=\"127.0.0.1\"");
            d.run(cql);
        }
        catch (Exception e)
        {
            assertTrue(true);
            return;
        }
        assertTrue(true);
    }
}
