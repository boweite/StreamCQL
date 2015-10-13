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

import com.huawei.streaming.api.opereators.JoinType;
import com.huawei.streaming.api.streams.Column;
import com.huawei.streaming.api.streams.Schema;
import com.huawei.streaming.cql.ConstInTestCase;
import com.huawei.streaming.cql.Driver;
import com.huawei.streaming.cql.DriverContext;
import com.huawei.streaming.cql.exception.SemanticAnalyzerException;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.FromClauseAnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.SelectAnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.BinaryExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.ConstExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.ExpressionDescribe;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.JoinExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.PropertyValueExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.StreamAliasDesc;
import com.huawei.streaming.cql.semanticanalyzer.parser.IParser;
import com.huawei.streaming.cql.semanticanalyzer.parser.ParserFactory;
import com.huawei.streaming.expression.ExpressionOperator;
import static org.junit.Assert.assertTrue;

/**
 * from子句语义分析测试用例
 *
 */
public class FromClauseSemanticAnalyzerTest
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
    public void testOneStream()
        throws Exception
    {
        SemanticAnalyzer analyzer =
            SemanticAnalyzerFactory.createAnalyzer(parser.parse("select * from s1"), initSchema());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        assertTrue(fromContext.getInputSchemas().size() == 1);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getStreamName() == null);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getId().equalsIgnoreCase("s1"));
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getName().equalsIgnoreCase("s1"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testOneStreamWithAlias1()
        throws Exception
    {
        SemanticAnalyzer analyzer =
            SemanticAnalyzerFactory.createAnalyzer(parser.parse("select * from s1 as b"), initSchema());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == 1);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getStreamName() == null);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getId().equals("s1"));
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getName().equalsIgnoreCase("b"));
        assertTrue(fromContext.getWindows().size() == 1);
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testOneStreamWithAlias2()
        throws Exception
    {
        SemanticAnalyzer analyzer =
            SemanticAnalyzerFactory.createAnalyzer(parser.parse("select * from s1 b"), initSchema());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        assertTrue(fromContext.getInputSchemas().size() == 1);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getStreamName() == null);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getId().equals("s1"));
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getName().equalsIgnoreCase("b"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testOneStreamWithWindow1()
        throws Exception
    {
        SemanticAnalyzer analyzer =
            SemanticAnalyzerFactory.createAnalyzer(parser.parse("select * from s1[range unbounded] b"), initSchema());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == 1);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getStreamName() == null);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getId().equals("s1"));
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getName().equalsIgnoreCase("b"));
        assertTrue(fromContext.getWindows().get("b").getName().equals("keepall"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testOneStreamWithWindow2()
        throws Exception
    {
        SemanticAnalyzer analyzer =
            SemanticAnalyzerFactory.createAnalyzer(parser.parse("select * from s1[range 100 milliseconds slide] b"),
                initSchema());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == 1);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getStreamName() == null);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getId().equals("s1"));
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getName().equalsIgnoreCase("b"));
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testOneStreamWithWindow3()
        throws Exception
    {
        String sql = "select * from s1[range 100 milliseconds slide partition by id] b";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchema());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == 1);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getStreamName() == null);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getId().equals("s1"));
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getName().equalsIgnoreCase("b"));
    }

    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin1()
        throws Exception
    {
        SemanticAnalyzer analyzer =
            SemanticAnalyzerFactory.createAnalyzer(parser.parse("select * from S1 join S2 on s1.id = s2.id"),
                initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().size() == ConstInTestCase.I_2);
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.INNER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias() == null);
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin2()
        throws Exception
    {
        String sql = "select * from s1 join s2[range unbounded] on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.INNER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias() == null);
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin3()
        throws Exception
    {
        String cql = "select * from s1 c join s2[range unbounded] on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(cql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.INNER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin4()
        throws Exception
    {
        SemanticAnalyzer analyzer =
            SemanticAnalyzerFactory.createAnalyzer(parser.parse("select * from s1[range 100 milliseconds slide] c "
                + "join s2[range unbounded] on s1.id = s2.id"), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.INNER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin5()
        throws Exception
    {
        SemanticAnalyzer analyzer =
            SemanticAnalyzerFactory.createAnalyzer(parser.parse("select * from s1[range 100 milliseconds slide] c "
                + "inner join s2[range unbounded] on s1.id = s2.id"), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.INNER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin6()
        throws Exception
    {
        SemanticAnalyzer analyzer =
            SemanticAnalyzerFactory.createAnalyzer(parser.parse("select * from s1[range 100 milliseconds slide] c "
                + "left outer join s2[range unbounded] on s1.id = s2.id"), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.LEFT_OUTER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin7()
        throws Exception
    {
        String sql = "select * from s1[range 100 milliseconds slide] c left join s2[range unbounded] on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.LEFT_OUTER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin8()
        throws Exception
    {
        String sql =
            "select * from s1[range 100 milliseconds slide] c "
                + "right outer join s2[range unbounded] on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.RIGHT_OUTER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin9()
        throws Exception
    {
        String sql =
            "select * from s1[range 100 milliseconds slide] c " + "right join s2[range unbounded] on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.RIGHT_OUTER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin10()
        throws Exception
    {
        String sql =
            "select * from s1[range 100 milliseconds slide] c "
                + "full outer join s2[range unbounded] on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.FULL_OUTER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin11()
        throws Exception
    {
        String sql =
            "select * from s1[range 100 milliseconds slide] c " + "full join s2[range unbounded] on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.FULL_OUTER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin12()
        throws Exception
    {
        String sql = "select * from s1[range 100 milliseconds slide] c " + "cross join s2[range unbounded]";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.CROSS_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin13()
        throws Exception
    {
        String sql =
            "select * from s1[range 100 milliseconds slide] c"
                + " cross join s2[range unbounded] on s1.id = s2.id join s3 on s2.id=s3.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_3);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        assertTrue(fromContext.getWindows().get("s3") == null);
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.CROSS_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof JoinExpressionDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
        
        JoinExpressionDesc desc2 = (JoinExpressionDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc2.getJointype().equals(JoinType.INNER_JOIN));
        StreamAliasDesc desc3 = (StreamAliasDesc)desc2.getLeftExpression();
        StreamAliasDesc desc4 = (StreamAliasDesc)desc2.getRightExpression();
        assertTrue(desc3.getStreamAlias() == null);
        assertTrue(desc3.getStreamName().equals("s2"));
        assertTrue(desc4.getStreamAlias() == null);
        assertTrue(desc4.getStreamName().equals("s3"));
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin14()
        throws Exception
    {
        String sql =
            "select * from s1[range 100 milliseconds slide] c cross join s2[range unbounded] on s1.id = s2.id "
                + "left join s3[range unbounded] on s3.id=s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_3);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        assertTrue(fromContext.getWindows().get("s3").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.CROSS_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof JoinExpressionDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
        
        JoinExpressionDesc desc2 = (JoinExpressionDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc2.getJointype().equals(JoinType.LEFT_OUTER_JOIN));
        StreamAliasDesc desc3 = (StreamAliasDesc)desc2.getLeftExpression();
        StreamAliasDesc desc4 = (StreamAliasDesc)desc2.getRightExpression();
        assertTrue(desc3.getStreamAlias() == null);
        assertTrue(desc3.getStreamName().equals("s2"));
        assertTrue(desc4.getStreamAlias() == null);
        assertTrue(desc4.getStreamName().equals("s3"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin15()
        throws Exception
    {
        String sql = "select * from S1 join S2 on s1.id = s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().size() == ConstInTestCase.I_2);
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.INNER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias() == null);
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
        
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testJoin16()
        throws Exception
    {
        String sql = "select * from s1[range 100 milliseconds slide] c join s2[range unbounded]";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        
        //这里暂时是inner join，后面在整个句子的解析阶段，会转换为inner join
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.INNER_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamName().equals("s2"));
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testStreamFilter1()
        throws Exception
    {
        String sql = "select * from s1(id >1)";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        ExpressionDescribe desc = fromContext.getFilterBeForeWindow().get("s1");
        
        BinaryExpressionDesc exp1 = (BinaryExpressionDesc)desc;
        assertTrue(exp1.getBexpression().getType().equals(ExpressionOperator.GREATERTHAN));
        
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_0);
        ConstExpressionDesc exp3 = (ConstExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_1);
        
        assertTrue(exp2.getProperty().equals("id"));
        assertTrue((int)exp3.getConstValue() == ConstInTestCase.I_1);
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testStreamFilter2()
        throws Exception
    {
        String sql = "select 1 from s1 (id >1)";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        ExpressionDescribe desc = fromContext.getFilterBeForeWindow().get("s1");
        BinaryExpressionDesc exp1 = (BinaryExpressionDesc)desc;
        assertTrue(exp1.getBexpression().getType().equals(ExpressionOperator.GREATERTHAN));
        
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_0);
        ConstExpressionDesc exp3 = (ConstExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_1);
        
        assertTrue(exp2.getProperty().equals("id"));
        assertTrue((int)exp3.getConstValue() == ConstInTestCase.I_1);
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testStreamFilter3()
        throws Exception
    {
        String sql = "select 1 from s1(id >1) as b";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == 1);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getStreamName() == null);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getId().equals("s1"));
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getName().equalsIgnoreCase("b"));
        assertTrue(fromContext.getWindows().size() == 1);
        
        ExpressionDescribe desc = fromContext.getFilterBeForeWindow().get("b");
        BinaryExpressionDesc exp1 = (BinaryExpressionDesc)desc;
        assertTrue(exp1.getBexpression().getType().equals(ExpressionOperator.GREATERTHAN));
        
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_0);
        ConstExpressionDesc exp3 = (ConstExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_1);
        
        assertTrue(exp2.getProperty().equals("id"));
        assertTrue((int)exp3.getConstValue() == ConstInTestCase.I_1);
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testStreamFilter4()
        throws Exception
    {
        String sql = "select 1 from s1(id >1)[range 100 milliseconds slide] b";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == 1);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getStreamName() == null);
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getId().equals("s1"));
        assertTrue(fromContext.getInputSchemas().get(ConstInTestCase.I_0).getName().equalsIgnoreCase("b"));
        
        ExpressionDescribe desc = fromContext.getFilterBeForeWindow().get("b");
        BinaryExpressionDesc exp1 = (BinaryExpressionDesc)desc;
        assertTrue(exp1.getBexpression().getType().equals(ExpressionOperator.GREATERTHAN));
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_0);
        ConstExpressionDesc exp3 = (ConstExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_1);
        assertTrue(exp2.getProperty().equals("id"));
        assertTrue((int)exp3.getConstValue() == ConstInTestCase.I_1);
    }
    
    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testStreamFilter5()
        throws Exception
    {
        String sql =
            "select * from s1(id >1)[range 100 milliseconds slide] c  "
                + "cross join s2(id >1)[range unbounded]  on s1.id = s2.id "
                + "left join s3(id >1)[range unbounded]  on s3.id=s2.id";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        SelectAnalyzeContext selectContext = (SelectAnalyzeContext)analyzer.analyze();
        FromClauseAnalyzeContext fromContext = selectContext.getFromClauseContext();
        
        assertTrue(fromContext.getInputSchemas().size() == ConstInTestCase.I_3);
        assertTrue(fromContext.getWindows().get("c").getName().equals("time_slide"));
        assertTrue(fromContext.getWindows().get("s2").getName().equals("keepall"));
        assertTrue(fromContext.getWindows().get("s3").getName().equals("keepall"));
        
        assertTrue(fromContext.getJoinexpression().getJointype().equals(JoinType.CROSS_JOIN));
        assertTrue(fromContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(fromContext.getJoinexpression().getRightExpression() instanceof JoinExpressionDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)fromContext.getJoinexpression().getLeftExpression();
        assertTrue(desc1.getStreamAlias().equals("c"));
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(fromContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)fromContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
        
        JoinExpressionDesc desc2 = (JoinExpressionDesc)fromContext.getJoinexpression().getRightExpression();
        assertTrue(desc2.getJointype().equals(JoinType.LEFT_OUTER_JOIN));
        StreamAliasDesc desc3 = (StreamAliasDesc)desc2.getLeftExpression();
        StreamAliasDesc desc4 = (StreamAliasDesc)desc2.getRightExpression();
        assertTrue(desc3.getStreamAlias() == null);
        assertTrue(desc3.getStreamName().equals("s2"));
        assertTrue(desc4.getStreamAlias() == null);
        assertTrue(desc4.getStreamName().equals("s3"));
        
        ExpressionDescribe desc = fromContext.getFilterBeForeWindow().get("c");
        BinaryExpressionDesc exp1 = (BinaryExpressionDesc)desc;
        assertTrue(exp1.getBexpression().getType().equals(ExpressionOperator.GREATERTHAN));
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_0);
        ConstExpressionDesc exp3 = (ConstExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_1);
        assertTrue(exp2.getProperty().equals("id"));
        assertTrue((int)exp3.getConstValue() == ConstInTestCase.I_1);
        
        ExpressionDescribe desc22 = fromContext.getFilterBeForeWindow().get("s2");
        BinaryExpressionDesc exp21 = (BinaryExpressionDesc)desc22;
        assertTrue(exp21.getBexpression().getType().equals(ExpressionOperator.GREATERTHAN));
        PropertyValueExpressionDesc exp22 =
            (PropertyValueExpressionDesc)exp21.getArgExpressions().get(ConstInTestCase.I_0);
        ConstExpressionDesc exp23 = (ConstExpressionDesc)exp21.getArgExpressions().get(ConstInTestCase.I_1);
        assertTrue(exp22.getProperty().equals("id"));
        assertTrue((int)exp23.getConstValue() == ConstInTestCase.I_1);
        
        ExpressionDescribe desc5 = fromContext.getFilterBeForeWindow().get("s3");
        BinaryExpressionDesc exp51 = (BinaryExpressionDesc)desc5;
        assertTrue(exp51.getBexpression().getType().equals(ExpressionOperator.GREATERTHAN));
        PropertyValueExpressionDesc exp52 =
            (PropertyValueExpressionDesc)exp51.getArgExpressions().get(ConstInTestCase.I_0);
        ConstExpressionDesc exp53 = (ConstExpressionDesc)exp51.getArgExpressions().get(ConstInTestCase.I_1);
        assertTrue(exp52.getProperty().equals("id"));
        assertTrue((int)exp53.getConstValue() == ConstInTestCase.I_1);
    }
    
    private List<Schema> initSchemas()
        throws SemanticAnalyzerException
    {
        List<Schema> schemas = new ArrayList<Schema>();
        Schema s1 = new Schema("s1");
        Schema s2 = new Schema("s2");
        Schema s3 = new Schema("s3");
        s1.setStreamName("s1");
        s2.setStreamName("s2");
        s3.setStreamName("s3");
        
        s1.addCol(new Column("id", String.class));
        s1.addCol(new Column("name", String.class));
        
        s2.addCol(new Column("id", String.class));
        s2.addCol(new Column("name", String.class));
        
        s3.addCol(new Column("id", String.class));
        s3.addCol(new Column("name", String.class));
        
        schemas.add(s1);
        schemas.add(s2);
        schemas.add(s3);
        return schemas;
    }
    
    private List<Schema> initSchema()
        throws SemanticAnalyzerException
    {
        List<Schema> schemas = new ArrayList<Schema>();
        Schema s1 = new Schema("s1");
        s1.setStreamName("s1");
        s1.addCol(new Column("id", String.class));
        s1.addCol(new Column("name", String.class));
        schemas.add(s1);
        return schemas;
    }
}
