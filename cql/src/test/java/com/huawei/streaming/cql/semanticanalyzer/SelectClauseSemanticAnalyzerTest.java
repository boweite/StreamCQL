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
import com.huawei.streaming.cql.executor.FunctionType;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.SelectAnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.SelectClauseAnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.BinaryExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.ConstExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.FunctionExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.PropertyValueExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.parser.IParser;
import com.huawei.streaming.cql.semanticanalyzer.parser.ParserFactory;
import com.huawei.streaming.expression.ExpressionOperator;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * select查询子句语义分析正常场景测试用例
 *
 */
public class SelectClauseSemanticAnalyzerTest
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
    public void testSimpleSelect()
        throws Exception
    {
        String sql = "select id from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == 1);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        
        assertTrue(parseContext.getExpdes().size() == 1);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc exp =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp.getProperty().equals("id"));
        assertFalse(exp.getProperty().equals("Id"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSimpleSelect1()
        throws Exception
    {
        String sql = "select id,Name from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_2);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertFalse(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("Name"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_2);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc exp1 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_1);
        assertTrue(exp1.getProperty().equals("id"));
        assertTrue(exp2.getProperty().equals("name"));
        assertFalse(exp2.getProperty().equals("Name"));
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSimpleSelect2()
        throws Exception
    {
        try
        {
            String sql = "select id,Name,id from s1,s2 on s1.id = s2.id";
            SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
            analyzer.analyze();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof SemanticAnalyzerException);
        }
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testConstSelect()
        throws Exception
    {
        String sql = "select 1 from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == 1);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("x_col_0"));
        
        assertTrue(parseContext.getExpdes().size() == 1);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof ConstExpressionDesc);
        
        ConstExpressionDesc exp1 = (ConstExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getConstValue().toString().equals("1"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testConstSelect1()
        throws Exception
    {
        String sql = "select 1 from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == 1);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("x_col_0"));
        
        assertTrue(parseContext.getExpdes().size() == 1);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof ConstExpressionDesc);
        
        ConstExpressionDesc exp1 = (ConstExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getConstValue().toString().equals("1"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testConstSelect2()
        throws Exception
    {
        String sql = "select 1f from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == 1);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("x_col_0"));
        
        assertTrue(parseContext.getExpdes().size() == 1);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof ConstExpressionDesc);
        
        ConstExpressionDesc exp1 = (ConstExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getConstValue().toString().equals("1.0"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testConstSelect3()
        throws Exception
    {
        String sql = "select 1l from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == 1);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("x_col_0"));
        
        assertTrue(parseContext.getExpdes().size() == 1);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof ConstExpressionDesc);
        
        ConstExpressionDesc exp1 = (ConstExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getConstValue().toString().equals("1"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testConstSelect4()
        throws Exception
    {
        String sql = "select true from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == 1);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("x_col_0"));
        
        assertTrue(parseContext.getExpdes().size() == 1);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof ConstExpressionDesc);
        
        ConstExpressionDesc exp1 = (ConstExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getConstValue().toString().equals("true"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSimpleSelectWithAlias()
        throws Exception
    {
        String sql = "select id as ids,name from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_2);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("ids"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_2);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        PropertyValueExpressionDesc exp1 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_1);
        assertTrue(exp1.getProperty().equals("id"));
        assertFalse(exp1.getProperty().equals("ids"));
        assertTrue(exp2.getProperty().equals("name"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testUDFTest()
        throws Exception
    {
        String sql = "select id as ids,Name,sum(1) from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("ids"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertFalse(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("Name"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("x_col_0"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_1) instanceof PropertyValueExpressionDesc);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_2) instanceof FunctionExpressionDesc);
        PropertyValueExpressionDesc exp1 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_1);
        FunctionExpressionDesc exp3 = (FunctionExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_2);
        assertTrue(exp1.getProperty().equals("id"));
        assertFalse(exp1.getProperty().equals("ids"));
        assertTrue(exp2.getProperty().equals("name"));
        assertTrue(exp3.getFinfo().getType().equals(FunctionType.UDAF) == true);
        assertTrue(exp3.getFinfo().getName().equals("sum"));
        assertTrue(exp3.getArgExpressions().size() == 1);
        ConstExpressionDesc exp4 = (ConstExpressionDesc)exp3.getArgExpressions().get(ConstInTestCase.I_0);
        assertTrue(exp4.getConstValue().toString().equals("1"));
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testUDFAliasTest()
        throws Exception
    {
        String sql = "select id as IDs,Name,tostring(Name) as F from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("ids"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertFalse(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("Name"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("f"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_2) instanceof FunctionExpressionDesc);
        PropertyValueExpressionDesc exp1 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        FunctionExpressionDesc exp2 = (FunctionExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_2);
        assertTrue(exp1.getProperty().equals("id"));
        assertTrue(exp2.getFinfo().getType().equals(FunctionType.UDAF) == false);
        assertTrue(exp2.getFinfo().getName().equals("tostring"));
        assertTrue(exp2.getArgExpressions().size() == 1);
        
        assertTrue(exp2.getArgExpressions().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        PropertyValueExpressionDesc exp3 =
            (PropertyValueExpressionDesc)exp2.getArgExpressions().get(ConstInTestCase.I_0);
        assertTrue(exp3.getProperty().equals("name"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testMathExpresstionTest()
        throws Exception
    {
        String sql = "select id as dd,id+Name,tostring(id) as d from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initIntegerSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("dd"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("x_col_0"));
        assertFalse(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("a+B"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("d"));
        
        //id as dd,id+Name,udf(id) as d
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_1) instanceof BinaryExpressionDesc);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_2) instanceof FunctionExpressionDesc);
        PropertyValueExpressionDesc exp1 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        BinaryExpressionDesc exp2 = (BinaryExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_1);
        FunctionExpressionDesc exp3 = (FunctionExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_2);
        assertTrue(exp1.getProperty().equals("id"));
        assertTrue(exp2.getBexpression().getType().equals(ExpressionOperator.ADD));
        assertTrue(exp2.getArgExpressions().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        assertTrue(exp2.getArgExpressions().get(ConstInTestCase.I_1) instanceof PropertyValueExpressionDesc);
        PropertyValueExpressionDesc exp5 =
            (PropertyValueExpressionDesc)exp2.getArgExpressions().get(ConstInTestCase.I_0);
        PropertyValueExpressionDesc exp6 =
            (PropertyValueExpressionDesc)exp2.getArgExpressions().get(ConstInTestCase.I_1);
        assertTrue(exp5.getProperty().equals("id"));
        assertTrue(exp6.getProperty().equals("name"));
        
        assertTrue(exp3.getFinfo().getType().equals(FunctionType.UDAF) == false);
        assertTrue(exp3.getFinfo().getName().equals("tostring"));
        assertTrue(exp3.getArgExpressions().size() == 1);
        
        assertTrue(exp2.getArgExpressions().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        PropertyValueExpressionDesc exp4 =
            (PropertyValueExpressionDesc)exp3.getArgExpressions().get(ConstInTestCase.I_0);
        assertTrue(exp4.getProperty().equals("id"));
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testUDFMathExpressionTest()
        throws Exception
    {
        String sql = "select id as D,name+id,tostring(id+name) as F from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initIntegerSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("d"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("x_col_0"));
        assertFalse(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("a+B"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("f"));
        
        //id as D,name+id,tostring(id+name) as F
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_1) instanceof BinaryExpressionDesc);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_2) instanceof FunctionExpressionDesc);
        FunctionExpressionDesc exp3 = (FunctionExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_2);
        
        assertTrue(exp3.getFinfo().getType().equals(FunctionType.UDAF) == false);
        assertTrue(exp3.getFinfo().getName().equals("tostring"));
        assertTrue(exp3.getArgExpressions().size() == 1);
        BinaryExpressionDesc exp6 = (BinaryExpressionDesc)exp3.getArgExpressions().get(ConstInTestCase.I_0);
        
        PropertyValueExpressionDesc exp4 =
            (PropertyValueExpressionDesc)exp6.getArgExpressions().get(ConstInTestCase.I_0);
        PropertyValueExpressionDesc exp5 =
            (PropertyValueExpressionDesc)exp6.getArgExpressions().get(ConstInTestCase.I_1);
        assertTrue(exp4.getProperty().equals("id"));
        assertTrue(exp5.getProperty().equals("name"));
        
    }
    
    /**
     * select distinct * 测试
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSelectDistinctStar()
        throws Exception
    {
        String sql = "select distinct * from s1,s2 on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.isDistinct() == true);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("id_1"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_3).getName().equals("name_1"));
        
        //id as dd,id+Name,udf(id) as d
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_3) instanceof PropertyValueExpressionDesc);
        PropertyValueExpressionDesc exp1 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_3);
        assertTrue(exp1.getProperty().equals("name"));
        
    }
    
    /**
     * select count(*) 测试
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testUDAFStar()
        throws Exception
    {
        String sql = "select count(*) from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == 1);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("x_col_0"));
        
        //id as dd,id+Name,udf(id) as d
        assertTrue(parseContext.getExpdes().size() == 1);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof FunctionExpressionDesc);
        FunctionExpressionDesc exp1 = (FunctionExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getFinfo().getType().equals(FunctionType.UDAF) == true);
        assertTrue(exp1.getFinfo().getName().equals("count"));
        assertTrue(exp1.isSelectStar() == true);
        assertTrue(exp1.getArgExpressions().size() == 0);
        
    }
    
    /**
     * select count(*) 测试
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testUDAFDistinctStar()
        throws Exception
    {
        String sql = "select count(distinct *) from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == 1);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("x_col_0"));
        
        assertTrue(parseContext.getExpdes().size() == 1);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof FunctionExpressionDesc);
        FunctionExpressionDesc exp1 = (FunctionExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getFinfo().getType().equals(FunctionType.UDAF) == true);
        assertTrue(exp1.getFinfo().getName().equals("count"));
        assertTrue(exp1.isSelectStar() == true);
        assertTrue(exp1.isDistinct() == true);
        assertTrue(exp1.getArgExpressions().size() == 0);
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testColRepeat()
        throws Exception
    {
        String sql = "select id,name,id from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("id_1"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_2) instanceof PropertyValueExpressionDesc);
        PropertyValueExpressionDesc exp1 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_2);
        assertTrue(exp1.getProperty().equals("id"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testUDFRepeat()
        throws Exception
    {
        String sql = "select sum(id),sum(id),sum(id) from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initIntegerSchema());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("x_col_0"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("x_col_1"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("x_col_2"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_2) instanceof FunctionExpressionDesc);
        FunctionExpressionDesc exp1 = (FunctionExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_2);
        assertTrue(exp1.getArgExpressions().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_0);
        assertTrue(exp2.getProperty().equals("id"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSelectWithStreamName()
        throws Exception
    {
        String sql = "select S1.id,S2.id,S2.name from s1,s2 on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("id_1"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("name"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
    }
    
    /**
     * select * 测试
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSelectStar()
        throws Exception
    {
        String sql = "select * from s1,s2 on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.isDistinct() == false);
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("id_1"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_3).getName().equals("name_1"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        PropertyValueExpressionDesc exp1 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getProperty().equals("id"));
        
    }
    
    /**
     * select * 测试
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSelectStar6()
        throws Exception
    {
        String sql = "select * from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.isDistinct() == false);
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_2);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_2);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        PropertyValueExpressionDesc exp1 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getProperty().equals("id"));
        
    }
    
    /**
     * select *,* 测试
     * 异常用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSelectTwoStar()
        throws Exception
    {
        String sql = "select *,* from s1,s2 on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.isDistinct() == false);
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_8);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("id_1"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_3).getName().equals("name_1"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_6).getName().equals("id_3"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_7).getName().equals("name_3"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_8);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        PropertyValueExpressionDesc exp1 =
            (PropertyValueExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getProperty().equals("id"));
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSelectStar1()
        throws Exception
    {
        String sql = "select s1.* from s1,s2 on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.isDistinct() == false);
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_2);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_2);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSelectStar2()
        throws Exception
    {
        String sql = "select s1.*,s1.* from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("id_1"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_3).getName().equals("name_1"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSelectStar3()
        throws Exception
    {
        String sql = "select s1.*,s2.* from s1,s2 on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("id_1"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_3).getName().equals("name_1"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSelectStar4()
        throws Exception
    {
        String sql = "select s1.*,s1.id,s1.name from s1";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("id_1"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_3).getName().equals("name_1"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSelectStar5()
        throws Exception
    {
        String sql = "select s1.*,s1.name,s2.name from s1,s2 on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext analyzeConext = (SelectAnalyzeContext)analyzer.analyze();
        SelectClauseAnalyzeContext parseContext = analyzeConext.getSelectClauseContext();
        
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("id"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("name_1"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_3).getName().equals("name_2"));
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_4);
        assertTrue(parseContext.getExpdes().get(ConstInTestCase.I_0) instanceof PropertyValueExpressionDesc);
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
    
    private List<Schema> initIntegerSchema()
        throws SemanticAnalyzerException
    {
        List<Schema> schemas = new ArrayList<Schema>();
        Schema s1 = new Schema("S1");
        
        s1.addCol(new Column("id", Integer.class));
        s1.addCol(new Column("name", Integer.class));
        
        schemas.add(s1);
        return schemas;
    }
    
}
