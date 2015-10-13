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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.streaming.cql.executor.PhysicalPlanLoader;
import com.huawei.streaming.cql.mapping.CQLSimpleLexerMapping;
import com.huawei.streaming.cql.mapping.InputOutputOperatorMapping;
import com.huawei.streaming.cql.toolkits.operators.TCPServerInputOperator;
import static org.junit.Assert.assertTrue;

/**
 * Driver正常测试用例
 *
 */
public class SubmitterTest
{
    
    private static final Logger LOG = LoggerFactory.getLogger(SubmitterTest.class);
    
    /**
     * 最基本的文件路径
     */
    private static final String BASICPATH = File.separator + "submitter" + File.separator;
    
    /**
     * 原始SQL文件路径
     */
    private static String inPutDir = null;
    
    /**
     * 初始化测试类之前要执行的初始化方法
     *
     * @throws Exception 初始化中可能抛出的异常
     */
    @BeforeClass
    public static void setUpBeforeClass()
        throws Exception
    {
        CQLSimpleLexerMapping.registerOperator("TCPServerInput", TCPServerInputOperator.class);
        PhysicalPlanLoader.registerPhysicalPlanAlias("TCPServerInput",
         com.huawei.streaming.cql.toolkits.api.TCPServerInputOperator.class);
        InputOutputOperatorMapping.registerOperator(
         com.huawei.streaming.cql.toolkits.api.TCPServerInputOperator.class,
         com.huawei.streaming.cql.toolkits.operators.TCPServerInputOperator.class);
        setDir();
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
        CQLSimpleLexerMapping.unRegisterSimpleLexerMapping("TCPServerInput");
        PhysicalPlanLoader.unRegisterPhysicalPlanAlias("TCPServerInput");
        InputOutputOperatorMapping.unRegisterMapping(
         com.huawei.streaming.cql.toolkits.api.TCPServerInputOperator.class);
    }

    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Ignore
    @Test
    public void testtt()
        throws Exception
    {
        executeCase("test");
    }
    
    private static void setDir()
    {
        String classPath = SubmitterTest.class.getResource("/").getPath();
        
        try
        {
            classPath = URLDecoder.decode(classPath, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        
        inPutDir = classPath + BASICPATH;
    }
    
    private void executeCase(String caseName)
        throws Exception
    {
        long startTime = System.currentTimeMillis();
        List<String> sqls = CQLTestCommons.addFile(new File(inPutDir + caseName + CQLTestCommons.INPUT_POSTFIX));
        Driver driver = new Driver();
        for (String sql : sqls)
        {
            driver.run(sql);
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        LOG.info("Done query: " + caseName + " elapsedTime=" + elapsedTime / CQLTestCommons.BASICTIMESTAMP + "s");
        assertTrue("Test passed", true);
    }
    
}
