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

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Driver正常测试用例
 *
 */
public class SetGetTest
{
    /**
     * 测试
     *
     * @throws Exception 如果错误，抛出异常
     */
    @Test
    public void testString()
        throws Exception
    {
        String set = "set 'test.a.x_1' = 'a'";
        String get = "get 'test.a.x_1'";
        
        Driver driver = new Driver();
        
        driver.run(set);
        CQLResult setResult = driver.getResult();
        assertTrue(setResult == null);
        
        driver.run(get);
        CQLResult getResult = driver.getResult();
        assertTrue(getResult.getResults().get(0)[0].equals("a"));

        driver.run("get '${System:java.runtime.name}'");
        getResult = driver.getResult();
        assertTrue(getResult.getResults().get(0)[0].equals("Java(TM) SE Runtime Environment"));
        
        driver.run("set xx1= '${conf:test.a.x_1}bc'");
        driver.run("get xx1");
        getResult = driver.getResult();
        assertTrue(getResult.getResults().get(0)[0].equals("abc"));
        
        driver.run("set xx2= 'bc${conf:test.a.x_1}'");
        driver.run("get xx2");
        getResult = driver.getResult();
        assertTrue(getResult.getResults().get(0)[0].equals("bca"));
        
        driver.run("set xx3= 'bc${conf:test.a.x_1}bc'");
        driver.run("get xx3");
        getResult = driver.getResult();
        assertTrue(getResult.getResults().get(0)[0].equals("bcabc"));
    }
}
