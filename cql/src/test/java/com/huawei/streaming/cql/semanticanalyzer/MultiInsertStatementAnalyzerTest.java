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

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.huawei.streaming.api.streams.Column;
import com.huawei.streaming.api.streams.Schema;
import com.huawei.streaming.cql.Driver;
import com.huawei.streaming.cql.DriverContext;
import com.huawei.streaming.cql.exception.SemanticAnalyzerException;
import com.huawei.streaming.cql.semanticanalyzer.parser.IParser;
import com.huawei.streaming.cql.semanticanalyzer.parser.ParserFactory;
import static org.junit.Assert.fail;

/**
 * 多级插入语义分析测试
 *
 */
public class MultiInsertStatementAnalyzerTest
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
    public void testSimpleSelect1()
        throws Exception
    {
        String sql =
            "FROM s1 INSERT INTO STREAM s1 SELECT * " + "INSERT INTO STREAM s2 SELECT id "
                + "INSERT INTO STREAM s3 SELECT id, name WHERE id > 10";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        analyzer.analyze();
        org.junit.Assert.assertEquals(1, 1);
    }
    
    /**
     * 子查询测试
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSimpleSelect2()
        throws Exception
    {
        String sql =
            "FROM ( SELECT * FROM s1 ) INSERT INTO STREAM s1 SELECT * " + "INSERT INTO STREAM s2 SELECT id "
                + "INSERT INTO STREAM s3 SELECT id, name WHERE id > 10";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        analyzer.analyze();
        org.junit.Assert.assertEquals(1, 1);
    }
    
    /**
     * 多级Insert语句中不能包含窗口
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSimpleSelect3()
        throws Exception
    {
        String sql =
            "FROM ( SELECT * FROM s1 )[RANGE 1 SECONDS SLIDE ] " + "INSERT INTO STREAM s1 SELECT * "
                + "INSERT INTO STREAM s2 SELECT id " + "INSERT INTO STREAM s3 SELECT id, name WHERE id > 10";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        try
        {
            analyzer.analyze();
            fail("multi insert test cast window test failed.");
        }
        catch (SemanticAnalyzerException e)
        {
            org.junit.Assert.assertEquals(1, 1);
        }
    }
    
    /**
     * 多级insert中不能包含group by和聚合操作
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testSimpleSelect4()
        throws Exception
    {
        String sql =
            "FROM " + " ( SELECT count(id) as id, 'sss' as name "
                + "     FROM s1( id > 5 )[RANGE 1 SECONDS SLIDE ] GROUP BY name ) "
                + "INSERT INTO STREAM s1 SELECT count(*) group by id " + "INSERT INTO STREAM s2 SELECT id "
                + "INSERT INTO STREAM s3 SELECT id, name WHERE id > 10";
        SemanticAnalyzer analyzer = SemanticAnalyzerFactory.createAnalyzer(parser.parse(sql), initSchemas());
        try
        {
            analyzer.analyze();
            fail("multi insert test cast group by test failed.");
        }
        catch (SemanticAnalyzerException e)
        {
            org.junit.Assert.assertEquals(1, 1);
        }
    }
    
    private List<Schema> initSchemas()
        throws SemanticAnalyzerException
    {
        List<Schema> schemas = Lists.newArrayList();
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
