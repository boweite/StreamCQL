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
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.huawei.streaming.api.opereators.ConsoleOutputOperator;
import com.huawei.streaming.api.opereators.KafkaInputOperator;
import com.huawei.streaming.api.opereators.KafkaOutputOperator;
import com.huawei.streaming.api.opereators.Operator;
import com.huawei.streaming.api.opereators.RandomGenInputOperator;
import com.huawei.streaming.api.opereators.serdes.CSVSerDeAPI;
import com.huawei.streaming.api.opereators.serdes.SerDeAPI;
import com.huawei.streaming.api.opereators.serdes.SimpleSerDeAPI;
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
 * 输入或者输出算子和API算子之间的映射关系
 *
 */
public class InputOutputOperatorMapping
{
    private static final Logger LOG = LoggerFactory.getLogger(CQLSimpleLexerMapping.class);

    private static final Map< String, String > API_PLAT_MAPPING = Maps.newConcurrentMap();

    static
    {
        /*
         * 输入输出算子
         */
        API_PLAT_MAPPING.put(KafkaInputOperator.class.getName(), KafkaSourceOp.class.getName());
        API_PLAT_MAPPING.put(KafkaOutputOperator.class.getName(), KafkaFunctionOp.class.getName());
        API_PLAT_MAPPING.put(RandomGenInputOperator.class.getName(), HeadStreamSourceOp.class.getName());
        API_PLAT_MAPPING.put(ConsoleOutputOperator.class.getName(), ConsolePrintOp.class.getName());

        /*
         * 序列化，反序列化类
         */
        API_PLAT_MAPPING.put(SimpleSerDeAPI.class.getName(), SimpleSerDe.class.getName());
        API_PLAT_MAPPING.put(CSVSerDeAPI.class.getName(), CSVSerDe.class.getName());
    }

    /**
     * 通过客户端api类名称倒找对应底层算子类名称
     *
     * @param apiOperatorClassName api算子名称
     * @return 底层对应算子名称
     */
    public static String getPlatformOperatorByAPI(String apiOperatorClassName)
    {
        return API_PLAT_MAPPING.get(apiOperatorClassName);
    }

    /**
     * 通过底层算子名称或者对应API类
     *
     * @param platformClassName 底层算子名称
     * @return API所在类
     */
    public static String getAPIOperatorByPlatform(String platformClassName)
    {
        for (Entry< String, String > et : API_PLAT_MAPPING.entrySet())
        {
            if (et.getValue().equals(platformClassName))
            {
                return et.getKey();
            }
        }
        return null;
    }

    /**
     * 注册API类和具体流处理算子之间的关系
     *
     * @param apiClass API算子所在类
     * @param serdeClass 序列化反序列化处理类
     */
    public static void registerSerDe(Class< ? extends SerDeAPI > apiClass, Class< ? extends StreamSerDe > serdeClass)
    {
        register(apiClass, serdeClass);
    }

    /**
     * 注册API类和具体流处理算子之间的关系
     *
     * @param apiClass API算子所在类
     * @param streamClass 流处理算子
     */
    public static void registerOperator(Class< ? extends Operator > apiClass,
     Class< ? extends IStreamOperator > streamClass)
    {
        register(apiClass, streamClass);
    }

    /**
     * 注册API类和具体流处理算子之间的关系
     *
     * @param apiClass API算子所在类
     * @param dataSourceClass 数据源处理算子
     */
    public static void registerDataSource(Class< ? extends Operator > apiClass,
     Class< ? extends IDataSource > dataSourceClass)
    {
        register(apiClass, dataSourceClass);
    }

    /**
     * 移除算子映射
     * 既然已经在注册时候做了控制，防止注册无效的类，移除这里就不用限制了
     *
     * @param apiClass api所在类
     */
    public static void unRegisterMapping(Class< ? > apiClass)
    {
        if (apiClass == null)
        {
            LOG.warn("Failed to unRegister operator mapping, apiClass is null.");
            return;
        }

        String className = apiClass.getName();
        //由于可能移除系统注册的简称，所以这里级别是Warn
        LOG.warn("UnRegister '{}' from operator mapping.", className);
        if (API_PLAT_MAPPING.containsKey(className))
        {
            API_PLAT_MAPPING.remove(className);
        }
    }

    private static void register(Class< ? > apiClass, Class< ? > streamClass)
    {
        if (apiClass == null || streamClass == null)
        {
            LOG.warn("Failed to register stream mapping, apiClass or streamClass is null.");
            return;
        }

        LOG.info("Register stream mapping, api class {}, stream class {}.", apiClass.getName(), streamClass.getName());
        API_PLAT_MAPPING.put(apiClass.getName(), streamClass.getName());
    }
}
