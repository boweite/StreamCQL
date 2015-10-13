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
package com.huawei.streaming.cql.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.streaming.cql.CQLTestCommons;
import com.huawei.streaming.cql.exception.ParseException;
import com.huawei.streaming.cql.semanticanalyzer.parser.IParser;
import com.huawei.streaming.cql.semanticanalyzer.parser.context.ParseContext;

/**
 * cql语法解析测试公共类
 *
 */
public class ParseTaskUtil
{
    private static final Logger LOG = LoggerFactory.getLogger(ParseTaskUtil.class);
    
    private List<String> sqls = new ArrayList<String>();
    
    private final IParser pd;
    
    private File resultFile;
    
    private File outputFile;
    
    /**
     * <默认构造函数>
     *
     * @param parser 解析器
     * @param inputFile 待解析的SQL文件
     * @param outputFile 要和resultfile进行对比的文件，这个文件只生成一次，之后每次和该文件进行内容对比
     * @param resultFile 输出的结果文件，这个文件是每次都会生成的。
     * @throws Exception 文件初始化失败
     */
    public ParseTaskUtil(IParser parser, String inputFile, String outputFile, String resultFile)
        throws Exception
    {
        this.pd = parser;
        this.resultFile = new File(resultFile);
        this.outputFile = new File(outputFile);
        sqls.addAll(CQLTestCommons.addFile(new File(inputFile)));
    }
    
    /**
     * 正常 的功能用例解析
     *
     * @throws ParseException 解析错误
     * @throws IOException 文件读写错误
     */
    public void parseAndWrite()
        throws ParseException, IOException
    {
        for (String sql : sqls)
        {
            ParseContext parseContext = null;
            try
            {
                parseContext = pd.parse(sql);
            }
            catch (ParseException e)
            {
                LOG.error("failed to parse sql:" + sql, e);
                throw e;
            }
            
            writeResultFiles(sql, parseContext.toString());
        }
    }
    
    /**
     * 解析cql并且在遇到异常的时候抛出异常
     *
     * @throws IOException 结果写入失败的时候抛出异常
     */
    public void parseAndWriteWithParseException()
        throws IOException
    {
        for (String sql : sqls)
        {
            ParseContext parseContext = null;
            try
            {
                parseContext = pd.parse(sql);
            }
            catch (ParseException e)
            {
                LOG.warn(e.getMessage(), e);
                writeResultFiles(sql, e.getMessage());
                continue;
            }
            
            writeResultFiles(sql, parseContext.toString());
        }
    }
    
    /**
     * 对比测试结果
     *
     * @return 如果结果无误，返回true
     * @throws IOException 文件异常
     */
    public boolean compareResults()
        throws IOException
    {
        return CQLTestCommons.compareFileContent(outputFile, resultFile);
    }
    
    private void writeResultFiles(String sql, String result)
        throws IOException
    {
        if (null == result)
        {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("##sqlstart:\n");
        sb.append(sql + "\n");
        sb.append("##sqlend\n");
        sb.append(result + "\n");
        
        CQLTestCommons.appendToFile(resultFile, sb.toString());
    }
    
}
