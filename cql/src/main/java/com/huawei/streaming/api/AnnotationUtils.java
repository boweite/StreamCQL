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

package com.huawei.streaming.api;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.huawei.streaming.api.opereators.serdes.UserDefinedSerDeAPI;
import com.huawei.streaming.cql.CQLUtils;
import com.huawei.streaming.cql.exception.ApplicationBuildException;
import com.huawei.streaming.cql.exception.SemanticAnalyzerException;
import com.huawei.streaming.cql.executor.expressioncreater.ExpressionCreator;
import com.huawei.streaming.cql.executor.operatorinfocreater.ExpressionCreatorAnnotation;
import com.huawei.streaming.cql.executor.operatorinfocreater.OperatorInfoCreator;
import com.huawei.streaming.cql.executor.operatorinfocreater.OperatorInfoCreatorAnnotation;
import com.huawei.streaming.exception.ErrorCode;
import com.huawei.streaming.serde.StreamSerDe;

/**
 * 申明属性处理的一些公共方法
 *
 */
public class AnnotationUtils
{
    private static final Logger LOG = LoggerFactory.getLogger(AnnotationUtils.class);
    
    /**
     * 将类中configannotation的申明转为streamingconfig中的属性
     *
     * @param obj 待转义bean
     * @return 配置属性对象
     * @throws ApplicationBuildException 反射异常
     */
    public static TreeMap<String, String> getAnnotationsToConfig(Object obj)
        throws ApplicationBuildException
    {
        TreeMap<String, String> config = Maps.newTreeMap();
        Field[] fs = obj.getClass().getDeclaredFields();
        for (Field f : fs)
        {
            f.setAccessible(true);
            ConfigAnnotation annotation = f.getAnnotation(ConfigAnnotation.class);
            if (null != annotation)
            {
                String key = annotation.value();
                if (key == null)
                {
                    continue;
                }
                Object value = getOperatorConfigValue(obj, f);
                if (value == null)
                {
                    continue;
                }
                config.put(key, String.valueOf(value));
            }
        }
        
        if (obj instanceof UserDefinedSerDeAPI)
        {
            UserDefinedSerDeAPI udfDeser = (UserDefinedSerDeAPI)obj;
            if (udfDeser.getConfig() != null)
            {
                config.putAll(udfDeser.getConfig());
            }
        }
        
        return config;
    }
    
    private static Object getOperatorConfigValue(Object obj, Field field)
        throws ApplicationBuildException
    {
        try
        {
            return field.get(obj);
        }
        catch (IllegalAccessException e)
        {
            ApplicationBuildException exception = new ApplicationBuildException(e, ErrorCode.UNKNOWN_SERVER_COMMON_ERROR);
            LOG.error("Failed to get configuration value from operator '" + obj.getClass().getSimpleName()
                + "' field '{" + field.getName() + "}'.", exception);
            throw exception;
        }
    }
    
    /**
     * 获取API类中的fields和streamingconf的映射关系
     *
     * @param clazz api类
     * @return 配置属性映射关系
     * @throws SemanticAnalyzerException 语义分析异常
     */
    public static Map<String, String> getConfigMapping(String clazz)
        throws SemanticAnalyzerException
    {
        Map<String, String> mapping = Maps.newHashMap();
        Class< ? > apiClass = getaClass(clazz);
        Field[] fs = apiClass.getDeclaredFields();
        for (Field f : fs)
        {
            f.setAccessible(true);
            ConfigAnnotation annotaion = f.getAnnotation(ConfigAnnotation.class);
            if (annotaion == null)
            {
                continue;
            }
            String value = annotaion.value();
            if (value == null)
            {
                continue;
            }
            
            String key = f.getName().toLowerCase(Locale.US);
            mapping.put(key, value);
        }
        return mapping;
    }
    
    private static Class< ? > getaClass(String clazz)
        throws SemanticAnalyzerException
    {
        try
        {
            return Class.forName(clazz, true, CQLUtils.getClassLoader());
        }
        catch (ClassNotFoundException e)
        {
            SemanticAnalyzerException exception =
                new SemanticAnalyzerException(e, ErrorCode.SEMANTICANALYZE_UNKOWN_CLASS, clazz);
            LOG.error("Can't find class.", exception);
            
            throw exception;
        }
    }
    
    /**
     * 将config中的配置属性对应到该对象中
     *
     * @param obj 待转义bean
     * @param config 配置属性
     * @return 配置属性对象
     * @throws ApplicationBuildException 应用程序构建异常
     */
    public static Object setConfigToObject(Object obj, Map<String, String> config)
        throws ApplicationBuildException
    {
        Field[] fs = obj.getClass().getDeclaredFields();
        
        for (Field f : fs)
        {
            try
            {
                resetFieldValue(obj, config, f);
            }
            catch (IllegalAccessException e)
            {
                LOG.error("Illegal access,failed to set configuration value to operator '" + obj.getClass().getSimpleName()
                    + "' field '" + f.getName() + "'.");
                throw new ApplicationBuildException(ErrorCode.UNKNOWN_SERVER_COMMON_ERROR);
            }
        }
        
        return obj;
    }
    
    private static void resetFieldValue(Object obj, Map<String, String> config, Field field)
        throws IllegalAccessException
    {
        field.setAccessible(true);
        ConfigAnnotation annotaion = field.getAnnotation(ConfigAnnotation.class);
        if (null != annotaion)
        {
            String key = annotaion.value();
            if (key == null)
            {
                return;
            }
            
            String value = config.get(key);
            if (value == null)
            {
                return;
            }
            
            field.set(obj, changeVlaueByType(value, field.getType()));
        }
    }
    
    /**
     * 根据注解获取创建算子实例的类
     *
     * @param clazz 注解所在类
     * @return 算子实例类
     */
    public static Class< ? extends OperatorInfoCreator> getOperatorCreatorAnnotation(Class< ? > clazz)
    {
        OperatorInfoCreatorAnnotation annotation = clazz.getAnnotation(OperatorInfoCreatorAnnotation.class);
        return annotation == null ? null : annotation.value();
    }
    
    /**
     * 获取类上面的序列化和反序列化申明
     *
     * @param clazz 待获取的类
     * @return 映射申明名称
     */
    public static Class< ? extends StreamSerDe> getStreamSerDeAnnoationOverClass(Class< ? > clazz)
    {
        StreamSerDeAnnoation annotation = clazz.getAnnotation(StreamSerDeAnnoation.class);
        return annotation == null ? null : annotation.value();
    }
    
    /**
     * 获取类上面的表达式对象创建实例
     *
     * @param clazz 待获取的类
     * @return 映射申明名称
     */
    public static Class< ? extends ExpressionCreator> getExpressionCreatorAnnoationOverClass(Class< ? > clazz)
    {
        ExpressionCreatorAnnotation annotation = clazz.getAnnotation(ExpressionCreatorAnnotation.class);
        return annotation == null ? null : annotation.value();
    }
    
    private static Object changeVlaueByType(String value, Class< ? > type)
    {
        if (type == Integer.class || type == int.class)
        {
            return Integer.valueOf(value);
        }
        
        if (type == Long.class || type == long.class)
        {
            return Long.valueOf(value);
        }
        if (type == Double.class || type == double.class)
        {
            return Double.valueOf(value);
        }
        if (type == Float.class || type == float.class)
        {
            return Float.valueOf(value);
        }
        if (type == Boolean.class || type == boolean.class)
        {
            return Boolean.valueOf(value);
        }
        if (type == TimeUnit.class)
        {
            return TimeUnit.valueOf(value);
        }
        return value;
    }
}
