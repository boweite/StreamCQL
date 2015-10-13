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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.huawei.streaming.cql.DriverContext;
import com.huawei.streaming.cql.exception.CQLException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 函数注册测试用例
 *
 */
public class FunctionRegistryTest
{
    private static DriverContext driverContext;
    
    private static FunctionRegistry functionRegistry;
    
    /**
     * 初始化测试类之前要执行的初始化方法
     *
     * @throws Exception 初始化中可能抛出的异常
     */
    @BeforeClass
    public static void setUpBeforeClass()
        throws Exception
    {
        driverContext = new DriverContext();
        functionRegistry = DriverContext.getFunctions().get();
    }
    
    /**
     * 所有测试用例执行完毕之后执行的方法
     *
     * @throws Exception 异常
     */
    @AfterClass
    public static void tearDownAfterClass()
        throws Exception
    {
        driverContext.clean();
        driverContext = null;
        functionRegistry = null;
    }
    
    /**
     * 测试函数注册
     *
     * @throws CQLException 执行异常
     */
    @Test
    public void testRegisterUDF()
        throws CQLException
    {
        functionRegistry.registerUDF("udftest", TestUDF.class, null);
        assertTrue(functionRegistry.getFunctionInfoByFunctionName("udftest").getName().equals("udftest"));
        assertFalse(functionRegistry.getFunctionInfoByFunctionName("udftest").isNative());
        assertTrue(functionRegistry.getFunctionInfoByFunctionName("udftest").getClazz() == TestUDF.class);
    }
    
    /**
     * 测试通过函数名获取类
     *
     * @throws CQLException 执行异常
     */
    @Test
    public void testGetFunctionNameByClass()
        throws CQLException
    {
        functionRegistry.registerUDF("udftest", TestUDF.class, null);
        assertTrue(functionRegistry.getFunctionNameByClass(TestUDF.class.getName()).equals("udftest"));
    }
    
}
