/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huawei.streaming.cql;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * 存放一些公共的方法或者属性
 *
 */
public class CQLTestCommons
{

    /**
     * 通用的测试文件后缀名
     */
    public static final String INPUT_POSTFIX = ".cql";

    /**
     * 待对比的结果文件后缀
     */
    public static final String OUTPUT_POSTFIX = ".cql.out";

    /**
     * 临时结果文件后缀
     */
    public static final String RESULT_POSTFIX = ".cql.res";

    /**
     * 输入的文件夹名称
     * 用来保存所有待测试的CQL语句
     */
    public static final String INPUT_DIR_NAME = "input";

    /**
     * 输出的文件夹名称
     * 用来保存待对比的结果文件
     */
    public static final String OUTPUT_DIR_NAME = "output";

    /**
     * 临时结果文件夹名称
     * 用来保存临时结果文件，和output中的文件进行对比
     */
    public static final String RESULT_DIR_NAME = "result";

    /**
     * Driver测试结果文件
     */
    public static final String DRIVER_TEST_POSTFIX = ".xml";

    /**
     * 默认的字符编码
     */
    public static final Charset CHARSET = Charset.forName("utf-8");

    /**
     * 最基本的时间单位 1000毫秒
     */
    public static final int BASICTIMESTAMP = 1000;

    private static final Logger LOG = LoggerFactory.getLogger(CQLTestCommons.class);

    /**
     * 追加内容到文件末尾
     *
     * @param f 文件
     * @param values 文件内容
     * @throws IOException 文件操作异常
     */
    public static void appendToFile(File f, String values)
     throws IOException
    {
        if (!f.exists())
        {
            if (!f.getParentFile().exists())
            {
                f.getParentFile().mkdirs();
            }
            LOG.warn("file " + f.getPath() + " does not exist! will create it.");
            if (!f.createNewFile())
            {
                LOG.error("failed to create file " + f.getPath());
                return;
            }
        }
        if (f.isDirectory())
        {
            LOG.error("file " + f.getPath() + " is a directory!");
        }
        FileUtils.write(f, values, CHARSET, true);
    }

    /**
     * 比较文件内容是否相同
     *
     * @param f1 文件1
     * @param f2 文件2
     * @return 如果相同，返回true
     * @throws IOException 文件操作异常
     */
    public static boolean compareFileContent(File f1, File f2)
     throws IOException
    {
        List< String > f1Contexts = Files.readAllLines(f1.toPath(), CHARSET);
        List< String > f2Contexts = Files.readAllLines(f2.toPath(), CHARSET);

        if (f1Contexts.size() != f2Contexts.size())
        {
            return false;
        }
        for (int i = 0; i < f1Contexts.size(); i++)
        {
            String s1 = f1Contexts.get(i).trim();
            String s2 = f2Contexts.get(i).trim();
            if (Strings.isNullOrEmpty(s1) && Strings.isNullOrEmpty(s2))
            {
               continue;
            }

            if(!s1.equals(s2))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 清空目录下的所有文件及子文件
     *
     * @param dir 目录
     * @throws IOException 文件操作异常
     */
    public static void emptyDir(File dir)
     throws IOException
    {
        if (!dir.isDirectory())
        {
            return;
        }
        FileUtils.cleanDirectory(dir);
    }

    /**
     * 解析文件内容，将文件中的sql解析出来
     *
     * @param qf 文件
     * @return 解析出来的sql列表
     * @throws Exception 解析异常
     */
    public static List< String > addFile(File qf)
     throws Exception
    {
        CQLFileReader reader = new CQLFileReader();
        reader.readCQLs(qf.getCanonicalPath());
        return reader.getResult();
    }
}
