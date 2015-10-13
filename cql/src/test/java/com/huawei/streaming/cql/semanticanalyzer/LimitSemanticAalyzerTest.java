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

package com.huawei.streaming.cql.semanticanalyzer;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.huawei.streaming.api.streams.Column;
import com.huawei.streaming.api.streams.Schema;
import com.huawei.streaming.cql.ConstInTestCase;
import com.huawei.streaming.cql.Driver;
import com.huawei.streaming.cql.DriverContext;
import com.huawei.streaming.cql.exception.SemanticAnalyzerException;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.LimitClauseAnalzyeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.SelectAnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.parser.IParser;
import com.huawei.streaming.cql.semanticanalyzer.parser.ParserFactory;
import static org.junit.Assert.assertTrue;

/**
 * Limit语义分析测试用例
 *
 */
public class LimitSemanticAalyzerTest
{
    private static IParser parser = ParserFactory.createApplicationParser();
    
    private static Driver driver = null;
    
    /**
     * 初始化测试类之前要执行的初始化方法
     *
     * @throws Exception 初始化中可能抛出的异常
     */
    @BeforeClass
    public static void setUpBeforeClass()
        throws Exception
    {
        if (DriverContext.getFunctions().get() == null)
        {
            driver = new Driver();
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
        if (driver != null)
        {
            driver.clean();
        }
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testAnalyze()
        throws Exception
    {
        String sql = "select * from s limit 10";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        
        LimitClauseAnalzyeContext parseContext = analyzeConext.getLimitClauseContext();
        assertTrue(parseContext.getLimit().equals(ConstInTestCase.I_10));
    }
    
    private List<Schema> initSchema()
        throws SemanticAnalyzerException
    {
        List<Schema> schemas = new ArrayList<Schema>();
        Schema s1 = new Schema("S");
        
        s1.addCol(new Column("id", String.class));
        s1.addCol(new Column("name", String.class));
        schemas.add(s1);
        return schemas;
    }
    
}
