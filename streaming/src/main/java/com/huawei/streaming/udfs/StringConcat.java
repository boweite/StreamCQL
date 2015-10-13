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

package com.huawei.streaming.udfs;

import java.util.Map;

/**
 * 字符串拼接的函数
 * 
 * 将多个字符串拼接成一个
 * 
 * 多个字符串中，如果有一个为空，就都返回空
 * 
 */
@UDFAnnotation(name = "concat")
public class StringConcat extends UDF
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -6107600743890077789L;

    /**
     * <默认构造函数>
     * @param config udf函数中需要的参数，这些参数要在cql中通过全局变量进行设置。
     */
    public StringConcat(Map<String, String> config)
    {
        super(config);
    }
    
    /**
     * 字符串拼接
     * @param arg1 待拼接字符串
     * @param arg2 待拼接字符串
     * @return 拼接好的字符串
     */
    public String evaluate(String arg1, String arg2)
    {
        return evaluate(new String[] {arg1, arg2});
    }
    
    /**
     * 字符串拼接
     * 
     * @param arg1 待拼接字符串
     * @param arg2 待拼接字符串
     * @param arg3 待拼接字符串
     * @return 拼接好的字符串
     */
    public String evaluate(String arg1, String arg2, String arg3)
    {
        return evaluate(new String[] {arg1, arg2, arg3});
    }
    
    /**
     * 字符串拼接
     * 
     * @param arg1 待拼接字符串
     * @param arg2 待拼接字符串
     * @param arg3 待拼接字符串
     * @param arg4 待拼接字符串
     * @return 拼接好的字符串
     */
    public String evaluate(String arg1, String arg2, String arg3, String arg4)
    {
        return evaluate(new String[] {arg1, arg2, arg3, arg4});
    }
    
    /**
     * 字符串拼接
     * 
     * @param arg1 待拼接字符串
     * @param arg2 待拼接字符串
     * @param arg3 待拼接字符串
     * @param arg4 待拼接字符串
     * @param arg5 待拼接字符串
     * @return 拼接好的字符串 拼接好的字符串
     */
    public String evaluate(String arg1, String arg2, String arg3, String arg4, String arg5)
    {
        return evaluate(new String[] {arg1, arg2, arg3, arg4, arg5});
    }
    
    /**
     * 字符串拼接
     * 
     * @param arg1 待拼接字符串
     * @param arg2 待拼接字符串
     * @param arg3 待拼接字符串
     * @param arg4 待拼接字符串
     * @param arg5 待拼接字符串
     * @param arg6 待拼接字符串
     * @return 拼接好的字符串
     */
    public String evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6)
    {
        return evaluate(new String[] {arg1, arg2, arg3, arg4, arg5, arg6});
    }
    
    /**
     * 字符串拼接
     * 
     * @param arg1 待拼接字符串
     * @param arg2 待拼接字符串
     * @param arg3 待拼接字符串
     * @param arg4 待拼接字符串
     * @param arg5 待拼接字符串
     * @param arg6 待拼接字符串
     * @param arg7 待拼接字符串
     * @return 拼接好的字符串
     */
    public String evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7)
    {
        return evaluate(new String[] {arg1, arg2, arg3, arg4, arg5, arg6, arg7});
    }
    
    /**
     * 字符串拼接
     * @param arguments 适合任意多字符串
     * @return 拼接好的字符串 拼接好的字符串
     */
    private String evaluate(String[] arguments)
    {
        StringBuilder sb = new StringBuilder();
        for (int idx = 0; idx < arguments.length; idx++)
        {
            String val = arguments[idx];
            if (val == null)
            {
                return null;
            }
            sb.append(val);
        }
        return new String(sb.toString());
    }
}
