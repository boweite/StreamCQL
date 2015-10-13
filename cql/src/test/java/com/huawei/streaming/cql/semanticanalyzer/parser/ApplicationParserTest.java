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

package com.huawei.streaming.cql.semanticanalyzer.parser;

import org.junit.Test;

import com.huawei.streaming.cql.exception.ParseException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * parser的异常测试
 *
 */
public class ApplicationParserTest
{
    
    /**
     * 测试语法解析异常
     *
     */
    @Test
    public void testParse()
    {
        String cql = "select  from S";
        IParser parser = new ApplicationParser();
        try
        {
            parser.parse(cql);
        }
        catch (ParseException e)
        {
            System.out.println(e.getCql());
            System.out.println(e.getCqlSummary());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorCode().getFormattedMessage());
            System.out.println(e.getErrorCode().getErrorCode());
            System.out.println(e.getErrorCode().getSqlState());
            assertTrue(true);
            return;
        }
        
        fail("test failed!");
    }
}
