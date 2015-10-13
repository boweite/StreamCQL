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
package com.huawei.streaming.cql;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.streaming.exception.StreamingException;

/**
 * cql语法解析测试公共类
 *
 */
public class DriverTestUtil
{
    private static final Logger LOG = LoggerFactory.getLogger(DriverTestUtil.class);
    
    private List<String> sqls = new ArrayList<String>();
    
    private File resultFile;
    
    private File outputFile;
    
    private Driver driver;
    
    /**
     * <默认构造函数>
     *
     * @param inputFile 待解析的SQL文件
     * @param outputFile 要和resultfile进行对比的文件，这个文件只生成一次，之后每次和该文件进行内容对比
     * @param resultFile 输出的结果文件，这个文件是每次都会生成的。
     * @throws Exception 文件初始化失败
     */
    public DriverTestUtil(String inputFile, String outputFile, String resultFile)
        throws Exception
    {
        this.resultFile = new File(resultFile);
        this.outputFile = new File(outputFile);
        driver = new Driver();
        sqls.addAll(CQLTestCommons.addFile(new File(inputFile)));
    }
    
    /**
     * 正常 的功能用例解析
     *
     * @throws IOException 文件读写错误
     * @throws StreamingException 流处理异常
     */
    public void executeAndWrite()
        throws IOException, StreamingException
    {
        for (int i = 0; i < sqls.size(); i++)
        {
            driver.run(sqls.get(i));
        }
        LOG.info("start to write driver run result to result file");
        writeResultFiles(driver.getResult());
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
    
    private void writeResultFiles(CQLResult result)
        throws IOException
    {
        if (null == result)
        {
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.getResults().size(); i++)
        {
            String[] resultCols = result.getResults().get(i);
            for (int j = 0; j < resultCols.length; j++)
            {
                sb.append(resultCols[i]);
                if (j != resultCols.length - 1)
                {
                    sb.append("\t");
                }
            }
        }
        
        CQLTestCommons.appendToFile(resultFile, sb.toString());
    }
}
