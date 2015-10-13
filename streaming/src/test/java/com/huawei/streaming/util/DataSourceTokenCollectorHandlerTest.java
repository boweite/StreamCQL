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

package com.huawei.streaming.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.huawei.streaming.exception.StreamingException;
import com.huawei.streaming.support.SupportConst;

/**
 * 字符串分隔符测试用例
 * 
 */
public class DataSourceTokenCollectorHandlerTest
{
    /**
     * 测试用例
     * @throws StreamingException 测试异常
     */
    @Test
    public void testParse1()
        throws StreamingException
    {
        String parseStr = "select rid as id,rname,rtype from rdbtable where id = '${s.id}'";
        DataSourceTokenCollectorHandler handler = new DataSourceTokenCollectorHandler("${", "}");
        GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
        parser.parse(parseStr);
        assertTrue(handler.getContents().size() == 1);
        assertTrue(handler.getContents().contains("s.id"));
    }
    
    /**
     * 测试用例
     * @throws StreamingException 测试异常
     */
    @Test
    public void testParse2()
        throws StreamingException
    {
        String parseStr = "select rid as id,rname,rtype from rdbtable where id = '${s.id}' and name = '${udf(a,b,c)}'";
        DataSourceTokenCollectorHandler handler = new DataSourceTokenCollectorHandler("${", "}");
        GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
        parser.parse(parseStr);
        assertTrue(handler.getContents().size() == SupportConst.I_TWO);
        assertTrue(handler.getContents().contains("s.id"));
        assertTrue(handler.getContents().contains("udf(a,b,c)"));
    }
    
    /**
     * 测试用例
     * @throws StreamingException 测试异常
     */
    @Test
    public void testParse3()
        throws StreamingException
    {
        String parseStr = "select rid as id,rname,rtype from rdbtable where id = '${s.id}' and name = '${}'";
        DataSourceTokenCollectorHandler handler = new DataSourceTokenCollectorHandler("${", "}");
        GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
        parser.parse(parseStr);
        assertTrue(handler.getContents().size() == SupportConst.I_ONE);
        assertTrue(handler.getContents().contains("s.id"));
    }
    
    /**
     * 测试用例
     */
    @Test
    public void testParse4()
    {
        String parseStr = "select rid as id,rname,rtype from rdbtable where id = '${s.id+${udf(a,b,c}}'";
        DataSourceTokenCollectorHandler handler = new DataSourceTokenCollectorHandler("${", "}");
        GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
        try
        {
            parser.parse(parseStr);
        }
        catch (StreamingException e)
        {
            assertTrue(true);
        }
    }
}
