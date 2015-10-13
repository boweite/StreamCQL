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
import com.huawei.streaming.common.Pair;
import com.huawei.streaming.cql.Driver;
import com.huawei.streaming.cql.DriverContext;
import com.huawei.streaming.cql.exception.SemanticAnalyzerException;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.OrderByClauseAnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.ExpressionDescribe;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.PropertyValueExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.parser.IParser;
import com.huawei.streaming.cql.semanticanalyzer.parser.ParserFactory;
import com.huawei.streaming.process.sort.SortEnum;
import static org.junit.Assert.assertTrue;

/**
 * orderby语义分析测试用例
 *
 */
public class OrderbySemanticAnalyzerTest
{
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
    public void testOneCol()
        throws Exception
    {
        String sql = "id desc";
        IParser orderbyParser = ParserFactory.createOrderbyClauseParser();
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(orderbyParser.parse(sql), initSchema());
        OrderByClauseAnalyzeContext sortby = (OrderByClauseAnalyzeContext)analyzer.analyze();
        
        assertTrue(sortby.getOrderbyExpressions().size() == 1);
        
        List<Pair<ExpressionDescribe, SortEnum>> orderbys = sortby.getOrderbyExpressions();
        for (Pair<ExpressionDescribe, SortEnum> et : orderbys)
        {
            ExpressionDescribe des = et.getFirst();
            SortEnum type = et.getSecond();
            assertTrue(type == SortEnum.DESC);
            assertTrue(des instanceof PropertyValueExpressionDesc);
            assertTrue(((PropertyValueExpressionDesc)des).getProperty().equals("id"));
        }
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testOneColDefault()
        throws Exception
    {
        String sql = "id ASC";
        IParser orderbyParser = ParserFactory.createOrderbyClauseParser();
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(orderbyParser.parse(sql), initSchema());
        OrderByClauseAnalyzeContext sortby = (OrderByClauseAnalyzeContext)analyzer.analyze();
        
        List<Pair<ExpressionDescribe, SortEnum>> orderbys = sortby.getOrderbyExpressions();
        for (Pair<ExpressionDescribe, SortEnum> et : orderbys)
        {
            ExpressionDescribe des = et.getFirst();
            SortEnum type = et.getSecond();
            assertTrue(type == SortEnum.ASC);
            assertTrue(des instanceof PropertyValueExpressionDesc);
            assertTrue(((PropertyValueExpressionDesc)des).getProperty().equals("id"));
        }
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testTwoCol()
        throws Exception
    {
        String sql = "id ASC,name desc";
        IParser orderbyParser = ParserFactory.createOrderbyClauseParser();
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(orderbyParser.parse(sql), initSchema());
        OrderByClauseAnalyzeContext sortby = (OrderByClauseAnalyzeContext)analyzer.analyze();
        
        List<Pair<ExpressionDescribe, SortEnum>> orderbys = sortby.getOrderbyExpressions();
        for (Pair<ExpressionDescribe, SortEnum> et : orderbys)
        {
            ExpressionDescribe des = et.getFirst();
            SortEnum type = et.getSecond();
            if (((PropertyValueExpressionDesc)des).getProperty().equals("id"))
            {
                assertTrue(type == SortEnum.ASC);
            }
            else
            {
                assertTrue(type == SortEnum.DESC);
            }
        }
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testTwoColDefault()
        throws Exception
    {
        String sql = "id desc,name asc";
        IParser orderbyParser = ParserFactory.createOrderbyClauseParser();
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(orderbyParser.parse(sql), initSchema());
        OrderByClauseAnalyzeContext sortby = (OrderByClauseAnalyzeContext)analyzer.analyze();
        
        List<Pair<ExpressionDescribe, SortEnum>> orderbys = sortby.getOrderbyExpressions();
        for (Pair<ExpressionDescribe, SortEnum> et : orderbys)
        {
            ExpressionDescribe des = et.getFirst();
            SortEnum type = et.getSecond();
            if (((PropertyValueExpressionDesc)des).getProperty().equals("id"))
            {
                assertTrue(type == SortEnum.DESC);
            }
            else
            {
                assertTrue(type == SortEnum.ASC);
            }
        }
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testTwoTablesCol()
        throws Exception
    {
        String sql = "s1.id desc,s2.name asc";
        IParser orderbyParser = ParserFactory.createOrderbyClauseParser();
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(orderbyParser.parse(sql), initSchemas());
        OrderByClauseAnalyzeContext sortby = (OrderByClauseAnalyzeContext)analyzer.analyze();
        
        List<Pair<ExpressionDescribe, SortEnum>> orderbys = sortby.getOrderbyExpressions();
        for (Pair<ExpressionDescribe, SortEnum> et : orderbys)
        {
            ExpressionDescribe des = et.getFirst();
            SortEnum type = et.getSecond();
            if (((PropertyValueExpressionDesc)des).getProperty().equals("id"))
            {
                assertTrue(type == SortEnum.DESC);
            }
            else
            {
                assertTrue(type == SortEnum.ASC);
            }
        }
        
    }
    
    private List<Schema> initSchemas()
        throws SemanticAnalyzerException
    {
        List<Schema> schemas = new ArrayList<Schema>();
        Schema s1 = new Schema("S1");
        Schema s2 = new Schema("S2");
        
        s1.addCol(new Column("id", String.class));
        s1.addCol(new Column("name", String.class));
        
        s2.addCol(new Column("id", String.class));
        s2.addCol(new Column("name", String.class));
        
        schemas.add(s1);
        schemas.add(s2);
        return schemas;
    }
    
    private List<Schema> initSchema()
        throws SemanticAnalyzerException
    {
        List<Schema> schemas = new ArrayList<Schema>();
        Schema s1 = new Schema("S1");
        
        s1.addCol(new Column("id", String.class));
        s1.addCol(new Column("name", String.class));
        
        schemas.add(s1);
        return schemas;
    }
    
}
