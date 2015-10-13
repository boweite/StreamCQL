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

package com.huawei.streaming.cql;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdk.nashorn.internal.ir.annotations.Ignore;

/**
 * Driver正常测试用例
 *
 */
public class LocalSubmitterTest
{
    private static final String TMP_TEST_DEPENDS = "/tmp/testDepends/";
    
    private static final Logger LOG = LoggerFactory.getLogger(LocalSubmitterTest.class);
    
    private static final String BASICPATH = File.separator + "submitter" + File.separator;
    
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
    
    private static void removeTmpDir(File tmpDir)
    {
        try
        {
            FileUtils.deleteDirectory(tmpDir);
            
        }
        catch (IOException e)
        {
            LOG.error("failed to delete tmp dir", e);
        }
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
        
        File tmpDir = new File(TMP_TEST_DEPENDS);
        LOG.info("delete tmp user dir {} in test case tear down", tmpDir.getAbsolutePath());
        removeTmpDir(tmpDir);
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testAggregate()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "aggregate");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testAggregate2()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "aggregate2");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testAggregate3()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "aggregate3");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testAggregate4()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "aggregate4");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testAggregate5()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "aggregate5");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testAggregateFilter()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "aggregateFilter");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testAggregateFilter2()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "aggregateFilter2");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testAggregateFilter3()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "aggregateFilter3");
    }

    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testCast2()
     throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "cast2");
    }

    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testChineline()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "chineline");
    }

    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testConfs()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "confs");
    }

    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testDate()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "date");
    }
    

    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testGroupby1()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "groupby1");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testLocalSubmit()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "localSubmit");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testRightJoin()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "right_join");
    }

    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testSubQuery6()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "subQuery6");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testWhereLengthSlide()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "where_length_slide");
    }

    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testSimpleLexer3()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "simpleLexer3");
    }

    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testUdfWithProperties()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "udfWithProperties");
    }


    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testKafkaInput()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "kafkaInput");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testKafkaOutput()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "kafkaOutput");
    }
    
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testPipeLineHaving()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "pipelinehaving");
    }

    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testDecimalAgg()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "decimalagg");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testMultiArithmetic1()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "multiArithmetic1");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testMultiArithmetic2()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "multiArithmetic2");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testMultiArithmetic3()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "multiArithmetic3");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testMultiArithmetic4()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "multiArithmetic4");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testMultiArithmetic5()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "multiArithmetic5");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testMultiArithmetic6()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "multiArithmetic6");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testMultiArithmetic7()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "multiArithmetic7");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testMultiArithmetic8()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "multiArithmetic8");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testUserOperator1()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "userOperator1");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testUserOperator2()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "userOperator2");
    }
    
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testUserOperator3()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "userOperator3");
    }
    
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testUserOperator4()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "userOperator4");
    }
    
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testUserOperator5()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "userOperator5");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testUserOperator6()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "userOperator6");
    }
    
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testUserOperator7()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "userOperator7");
    }
    
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testUserOperator8()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "userOperator8");
    }
    
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testUserOperator9()
        throws Exception
    {
        LocalTaskCommons.localSubmit(BASICPATH, "userOperator9");
    }
}
