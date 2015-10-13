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

import com.huawei.streaming.cql.exception.CQLException;
import com.huawei.streaming.exception.ErrorCode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * 命令行客户端测试用例
 *
 */
public class CQLClientTest
{
    /**
     * 最基本的文件路径
     */
    private static final String BASICPATH = File.separator + "cqlclient" + File.separator;
    
    /**
     * 原始SQL文件路径
     */
    private static String inPutDir = null;
    
    /**
     * 测试用例
     *
     * @throws IOException 测试异常
     * @throws ReflectiveOperationException 反射异常
     */
    @Test
    public void testSubmit()
        throws IOException, ReflectiveOperationException
    {
        setDir();
        String f = inPutDir + "localSubmit2" + CQLTestCommons.INPUT_POSTFIX;
        String[] arg = {"-f", f};
        
        int result = CQLSessionState.STATE_OK;
        CQLClient client = new CQLClient();
        
        result = client.initSessionState();
        if (result != CQLSessionState.STATE_OK)
        {
            System.exit(result);
        }
        
        if (!client.parseArgs(arg))
        {
            System.exit(CQLSessionState.STATE_NORMAL_EXIT);
        }
        
        try
        {
            Method processFileMethod = client.getClass().getDeclaredMethod("processFile", String.class);
            processFileMethod.setAccessible(true);
            processFileMethod.invoke(client, f);
            client.executeDriver();
        }
        catch (IOException e)
        {
            throw e;
        }
        catch (InvocationTargetException e)
        {
            if (e.getTargetException() instanceof CQLException)
            {
                assertEquals(((CQLException)e.getTargetException()).getErrorCode(), ErrorCode.TOP_ONE_FILE_ONE_TOP);
                return;
            }
            throw e;
        }
        catch (SecurityException e)
        {
            throw e;
        }
        
        fail("submit test execute failed.");
    }
    
    private static void setDir()
        throws UnsupportedEncodingException
    {
        String classPath = CQLClientTest.class.getResource("/").getPath();
        classPath = URLDecoder.decode(classPath, "UTF-8");
        inPutDir = classPath + BASICPATH;
    }
}
