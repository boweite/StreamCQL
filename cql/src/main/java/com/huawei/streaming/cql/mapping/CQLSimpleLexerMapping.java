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
package com.huawei.streaming.cql.mapping;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.huawei.streaming.datasource.IDataSource;
import com.huawei.streaming.operator.IStreamOperator;
import com.huawei.streaming.operator.inputstream.HeadStreamSourceOp;
import com.huawei.streaming.operator.inputstream.KafkaSourceOp;
import com.huawei.streaming.operator.outputstream.ConsolePrintOp;
import com.huawei.streaming.operator.outputstream.KafkaFunctionOp;
import com.huawei.streaming.serde.CSVSerDe;
import com.huawei.streaming.serde.SimpleSerDe;
import com.huawei.streaming.serde.StreamSerDe;

/**
 * CQL内置的输入流和输出流简写映射
 * <p/>
 * 不区分大小写
 *
 */
public class CQLSimpleLexerMapping
{
    private static final Logger LOG = LoggerFactory.getLogger(CQLSimpleLexerMapping.class);

    private static final Map< String, String > MAPPING = Maps.newConcurrentMap();

    static
    {
        //序列化反序列化类
        MAPPING.put("SimpleSerDe", SimpleSerDe.class.getName());
        MAPPING.put("CsvSerDe", CSVSerDe.class.getName());

        MAPPING.put("KafkaInput", KafkaSourceOp.class.getName());
        MAPPING.put("KafkaOutput", KafkaFunctionOp.class.getName());
        MAPPING.put("RandomGen", HeadStreamSourceOp.class.getName());
        MAPPING.put("ConsoleOutput", ConsolePrintOp.class.getName());
    }

    /**
     * 通过检称获得序列化类和反序列化类的全称
     * 如果没有映射名称，返回原来的名称
     *
     * @param simpleName 简称
     * @return 全称
     */
    public static String getFullName(final String simpleName)
    {
        String fullName = getFullNameWithNull(simpleName);
        return fullName == null ? simpleName : fullName;
    }

    private static String getFullNameWithNull(final String simpleName)
    {
        if (Strings.isNullOrEmpty(simpleName))
        {
            return null;
        }

        for (Map.Entry< String, String > et : MAPPING.entrySet())
        {
            if (simpleName.equalsIgnoreCase(et.getKey()))
            {
                return et.getValue();
            }
        }

        return null;
    }

    /**
     * 根据类的全程获取简称
     *
     * @param fullName 类全称
     * @return 简称
     */
    public static String getSimpleName(String fullName)
    {
        if (fullName == null)
        {
            return null;
        }

        for (Map.Entry< String, String > et : MAPPING.entrySet())
        {
            if (fullName.equals(et.getValue()))
            {
                return et.getKey();
            }
        }

        return null;
    }


    /**
     * 注册序列化类/反序列化类的简称
     * 由于没有办法区分哪些是系统注册的，哪些是用户注册的，所以默认后注册的覆盖先注册的。
     *
     * @param name 简称，这里的简称不区分大小写
     * @param clazz 简称所对应的类
     */
    public static void registerSerDe(String name, Class< ? extends StreamSerDe > clazz)
    {
        register(name, clazz);
    }

    /**
     * 注册各类算子简称，这里的算子，一定是包含了可执行接口的算子
     * 由于没有办法区分哪些是系统注册的，哪些是用户注册的，所以默认后注册的覆盖先注册的。
     *
     * @param name 简称，这里的简称不区分大小写
     * @param clazz 简称所对应的类
     */
    public static void registerOperator(String name, Class< ? extends IStreamOperator > clazz)
    {
        register(name, clazz);
    }

    /**
     * 注册数据源简称，这里的数据源，一定是包含了可执行接口的数据源接口实现
     * 由于没有办法区分哪些是系统注册的，哪些是用户注册的，所以默认后注册的覆盖先注册的。
     *
     * @param name 简称，这里的简称不区分大小写
     * @param clazz 简称所对应的类
     */
    public static void registerDataSource(String name, Class< ? extends IDataSource > clazz)
    {
        register(name, clazz);
    }

    /**
     * 移除语法简称
     *
     * @param name 简称，这里的简称不区分大小写
     */
    public static void unRegisterSimpleLexerMapping(String name)
    {
        if (Strings.isNullOrEmpty(name))
        {
            LOG.warn("Failed to unRegister simple lexer mapping, name is null.");
            return;
        }

        //由于可能移除系统注册的简称，所以这里级别是Warn
        LOG.warn("UnRegister '{}' from simple lexers.", name);
        if (MAPPING.containsKey(name))
        {
            MAPPING.remove(name);
        }
    }

    private static void register(String name, Class< ? > clazz)
    {
        if (Strings.isNullOrEmpty(name) || clazz == null)
        {
            LOG.warn("Failed to register simple lexer mapping, name or class is null.");
            return;
        }

        LOG.info("register simple lexer {}, class {}.", name, clazz);
        MAPPING.put(name, clazz.getName());
    }
}
