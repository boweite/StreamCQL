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

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;
import com.huawei.streaming.exception.StreamingException;

/**
 * 数据源参数替换测试
 * 
 */
public class DataSourceTokenReplacedHandlerTest
{
    private static Map<String, Object> expValues = Maps.newHashMap();
    static
    {
        expValues.put("s.id", new String("1"));
        expValues.put(" s.id", new String("2"));
        expValues.put("s.id ", new String("3"));
        expValues.put("udf(a,b,c)", new String("udfvalues"));
        expValues.put("", new String("nullvalues"));
    }
    
    /**
     * 测试用例
     * @throws StreamingException 测试异常
     */
    @Test
    public void testParse1()
        throws StreamingException
    {
        String parseStr = "select rid as id,rname,rtype from rdbtable where id = '${s.id}'";
        DataSourceTokenReplacedHandler handler = new DataSourceTokenReplacedHandler(expValues);
        GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
        String str = parser.parse(parseStr);
        assertTrue(str.equals("select rid as id,rname,rtype from rdbtable where id = '1'"));
        
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
        DataSourceTokenReplacedHandler handler = new DataSourceTokenReplacedHandler(expValues);
        GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
        String str = parser.parse(parseStr);
        assertTrue(str.equals("select rid as id,rname,rtype from rdbtable where id = '1' and name = 'udfvalues'"));
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
        DataSourceTokenReplacedHandler handler = new DataSourceTokenReplacedHandler(expValues);
        GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
        String str = parser.parse(parseStr);
        assertTrue(str.equals("select rid as id,rname,rtype from rdbtable where id = '1' and name = ''"));
    }
    
    /**
     * 测试用例
     * @throws StreamingException 测试异常
     */
    @Test
    public void testParse4()
        throws StreamingException
    {
        String parseStr = "select rid as id,rname,rtype from rdbtable where id = '${s.id+${udf(a,b,c}}'";
        DataSourceTokenReplacedHandler handler = new DataSourceTokenReplacedHandler(expValues);
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
