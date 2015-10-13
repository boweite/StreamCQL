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
import com.huawei.streaming.common.Pair;
import com.huawei.streaming.cql.ConstInTestCase;
import com.huawei.streaming.cql.Driver;
import com.huawei.streaming.cql.DriverContext;
import com.huawei.streaming.cql.exception.SemanticAnalyzerException;
import com.huawei.streaming.cql.executor.FunctionType;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.AnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.FilterClauseAnalzyeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.FromClauseAnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.InsertAnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.LimitClauseAnalzyeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.OrderByClauseAnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.SelectAnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.SelectClauseAnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.BinaryExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.ConstExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.ExpressionDescribe;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.FunctionExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.PropertyValueExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.StreamAliasDesc;
import com.huawei.streaming.cql.semanticanalyzer.parser.IParser;
import com.huawei.streaming.cql.semanticanalyzer.parser.ParserFactory;
import com.huawei.streaming.expression.ExpressionOperator;
import com.huawei.streaming.process.sort.SortEnum;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * insert into 子句解析
 *
 */
public class InsertIntoSemanticAnalyzerTest
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
        SemanticAnalyzer analyzer =
            SemanticAnalyzerFactory.createAnalyzer(parser.parse("insert into Stream SS "
                + "select S1.id as aid,S2.name,count(1) as c " + "from S1(id > 1)[range unbounded] "
                + "inner join S2(s3.id >1)[range 100 milliseconds slide] as S3 on s1.id = s3.id "
                + "where s1.id=1 group by aid having c>10 order by aid ASC limit 10"), initSchemas());
        InsertAnalyzeContext insertContext = (InsertAnalyzeContext)analyzer.analyze();
        
        assertTrue(insertContext.getOutputStreamName().equals("ss"));
        
        SelectAnalyzeContext parseContext = insertContext.getSelectContext();
        
        //From clause
        assertFromClause(parseContext.getFromClauseContext());
        //select clause
        assertSelectClause(parseContext.getSelectClauseContext());
        //where clause
        assertWhereClause(parseContext.getWhereClauseContext());
        //groupby clause
        assertGroupbyClause(parseContext.getGroupbyClauseContext());
        //having clause
        assertHavingClause(parseContext.getHavingClauseContext());
        //orderby clause
        assertSortbyClause(parseContext.getOrderbyClauseContext());
        //limit clause
        assertLimitClause(parseContext.getLimitClauseContext());
    }
    
    private void assertLimitClause(LimitClauseAnalzyeContext parseContext)
    {
        assertTrue(parseContext.getLimit().equals(ConstInTestCase.I_10));
    }
    
    private void assertSortbyClause(OrderByClauseAnalyzeContext parseContext)
    {
        // order by aid 
        List<Pair<ExpressionDescribe, SortEnum>> orderbys = parseContext.getOrderbyExpressions();
        for (Pair<ExpressionDescribe, SortEnum> et : orderbys)
        {
            ExpressionDescribe des = et.getFirst();
            SortEnum type = et.getSecond();
            assertTrue(type == SortEnum.ASC);
            assertTrue(des instanceof PropertyValueExpressionDesc);
            assertTrue(((PropertyValueExpressionDesc)des).getProperty().equals("aid"));
        }
    }
    
    private void assertHavingClause(FilterClauseAnalzyeContext parseContext)
    {
        // c>10
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_1);
        assertTrue(parseContext.getExpdes().get(0) instanceof BinaryExpressionDesc);
        
        BinaryExpressionDesc exp1 = (BinaryExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getBexpression().getType().equals(ExpressionOperator.GREATERTHAN));
        
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_0);
        ConstExpressionDesc exp3 = (ConstExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_1);
        
        assertTrue(exp2.getProperty().equals("c"));
        assertTrue((int)exp3.getConstValue() == ConstInTestCase.I_10);
    }
    
    private void assertGroupbyClause(SelectClauseAnalyzeContext parseContext)
    {
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_1);
        assertTrue(parseContext.getExpdes().get(0).toString().equals("s1.id"));
    }
    
    private void assertWhereClause(AnalyzeContext whereClauseContext)
    {
        // s1.id=1
        FilterClauseAnalzyeContext parseContext = (FilterClauseAnalzyeContext)whereClauseContext;
        
        assertTrue(parseContext.getExpdes().size() == ConstInTestCase.I_1);
        assertTrue(parseContext.getExpdes().get(0) instanceof BinaryExpressionDesc);
        
        BinaryExpressionDesc exp1 = (BinaryExpressionDesc)parseContext.getExpdes().get(ConstInTestCase.I_0);
        assertTrue(exp1.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_0);
        ConstExpressionDesc exp3 = (ConstExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_1);
        
        assertTrue(exp2.getProperty().equals("id"));
        assertTrue(exp3.getConstValue().toString().equals("1"));
    }
    
    private void assertSelectClause(SelectClauseAnalyzeContext parseContext)
    {
        // S1.id as aid,S2.name,count(*) as c 
        assertTrue(parseContext.getOutputSchema().getCols().size() == ConstInTestCase.I_3);
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_0).getName().equals("aid"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_1).getName().equals("name"));
        assertTrue(parseContext.getOutputSchema().getCols().get(ConstInTestCase.I_2).getName().equals("c"));
        
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
        assertFalse(exp1.getProperty().equals("aid"));
        assertTrue(exp2.getProperty().equals("name"));
        assertTrue(exp3.getFinfo().getType().equals(FunctionType.UDAF) == true);
        assertTrue(exp3.getFinfo().getName().equals("count"));
        assertTrue(!exp3.isSelectStar());
        
    }
    
    private void assertFromClause(FromClauseAnalyzeContext parseContext)
        throws SemanticAnalyzerException
    {
        /*
         *   analyzer.analyze(parser.parse("insert into Stream SS "
            + "select S1.id as aid,S2.name,count(*) as c " + "from S1[range unbounded] (id > 1) "
            + "inner join S2[range 100 milliseconds slide] as S3 (s3.id >1) on s1.id = s3.id "
            + "where s1.id=1 group by aid having c>10 order by aid limit 10"));
         */
        
        assertTrue(parseContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(parseContext.getInputSchemas().get(0).getId().equals("s1"));
        assertTrue(parseContext.getInputSchemas().get(1).getId().equals("s2"));
        assertTrue(parseContext.getInputSchemas().get(1).getName().equals("s3"));
        
        assertTrue(parseContext.getInputSchemas().size() == ConstInTestCase.I_2);
        assertTrue(parseContext.getWindows().size() == ConstInTestCase.I_2);
        assertTrue(parseContext.getWindows().get("s1").getName().equals("keepall"));
        assertTrue(parseContext.getWindows().get("s3").getName().equals("time_slide"));
        
        assertTrue(parseContext.getJoinexpression().getJointype().equals(JoinType.INNER_JOIN));
        assertTrue(parseContext.getJoinexpression().getLeftExpression() instanceof StreamAliasDesc);
        assertTrue(parseContext.getJoinexpression().getRightExpression() instanceof StreamAliasDesc);
        StreamAliasDesc desc1 = (StreamAliasDesc)parseContext.getJoinexpression().getLeftExpression();
        StreamAliasDesc desc2 = (StreamAliasDesc)parseContext.getJoinexpression().getRightExpression();
        assertTrue(desc1.getStreamAlias() == null);
        assertTrue(desc1.getStreamName().equals("s1"));
        assertTrue(desc2.getStreamAlias().equals("s3"));
        assertTrue(desc2.getStreamName().equals("s2"));
        
        assertTrue(parseContext.getJoinexpression().getJoinCondition() instanceof BinaryExpressionDesc);
        BinaryExpressionDesc bdesc = (BinaryExpressionDesc)parseContext.getJoinexpression().getJoinCondition();
        assertTrue(bdesc.getBexpression().getType().equals(ExpressionOperator.EQUAL));
        assertTrue(bdesc.getArgExpressions().get(0) instanceof PropertyValueExpressionDesc);
        assertTrue(bdesc.getArgExpressions().get(1) instanceof PropertyValueExpressionDesc);
        
        PropertyValueExpressionDesc pv1 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(0);
        PropertyValueExpressionDesc pv2 = (PropertyValueExpressionDesc)bdesc.getArgExpressions().get(1);
        
        assertTrue(pv1.getProperty().equals("id"));
        assertTrue(pv1.getSchemaId().equals("s1"));
        assertTrue(pv2.getProperty().equals("id"));
        assertTrue(pv2.getSchemaId().equals("s2"));
        
        ExpressionDescribe desc = parseContext.getFilterBeForeWindow().get("s1");
        BinaryExpressionDesc exp1 = (BinaryExpressionDesc)desc;
        assertTrue(exp1.getBexpression().getType().equals(ExpressionOperator.GREATERTHAN));
        PropertyValueExpressionDesc exp2 =
            (PropertyValueExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_0);
        ConstExpressionDesc exp3 = (ConstExpressionDesc)exp1.getArgExpressions().get(ConstInTestCase.I_1);
        assertTrue(exp2.getProperty().equals("id"));
        assertTrue((int)exp3.getConstValue() == ConstInTestCase.I_1);
        
        ExpressionDescribe desc22 = parseContext.getFilterBeForeWindow().get("s3");
        BinaryExpressionDesc exp21 = (BinaryExpressionDesc)desc22;
        assertTrue(exp21.getBexpression().getType().equals(ExpressionOperator.GREATERTHAN));
        PropertyValueExpressionDesc exp22 =
            (PropertyValueExpressionDesc)exp21.getArgExpressions().get(ConstInTestCase.I_0);
        ConstExpressionDesc exp23 = (ConstExpressionDesc)exp21.getArgExpressions().get(ConstInTestCase.I_1);
        assertTrue(exp22.getProperty().equals("id"));
        assertTrue((int)exp23.getConstValue() == ConstInTestCase.I_1);
        
        ExpressionDescribe desc5 = parseContext.getFilterBeForeWindow().get("s3");
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
        Schema ss = new Schema("ss");
        Schema s1 = new Schema("s1");
        Schema s2 = new Schema("s2");
        Schema s3 = new Schema("s3");
        s1.setStreamName("s1");
        s2.setStreamName("s2");
        s3.setStreamName("s3");
        
        ss.addCol(new Column("aid", String.class));
        ss.addCol(new Column("name", String.class));
        ss.addCol(new Column("c", String.class));
        
        s1.addCol(new Column("id", String.class));
        s1.addCol(new Column("name", String.class));
        
        s2.addCol(new Column("id", String.class));
        s2.addCol(new Column("name", String.class));
        
        s3.addCol(new Column("id", String.class));
        s3.addCol(new Column("name", String.class));
        
        schemas.add(s1);
        schemas.add(s2);
        schemas.add(s3);
        schemas.add(ss);
        return schemas;
    }
}
