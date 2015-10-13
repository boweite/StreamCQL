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

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.huawei.streaming.api.AnnotationUtils;
import com.huawei.streaming.config.StreamingConfig;
import com.huawei.streaming.cql.exception.SemanticAnalyzerException;
import com.huawei.streaming.cql.mapping.CQLSimpleLexerMapping;
import com.huawei.streaming.cql.mapping.InputOutputOperatorMapping;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.AnalyzeContext;
import com.huawei.streaming.cql.semanticanalyzer.parser.context.ClassNameContext;
import com.huawei.streaming.cql.semanticanalyzer.parser.context.ColumnNameTypeListContext;
import com.huawei.streaming.cql.semanticanalyzer.parser.context.CreateInputStatementContext;
import com.huawei.streaming.cql.semanticanalyzer.parser.context.ParseContext;
import com.huawei.streaming.cql.semanticanalyzer.parser.context.StreamPropertiesContext;
import com.huawei.streaming.exception.ErrorCode;

/**
 * 创建输入流语义分析
 *
 */
public class CreateInputStreamAnalyzer extends CreateStreamAnalyzer
{
    private static final Logger LOG = LoggerFactory.getLogger(CreateInputStreamAnalyzer.class);
    
    private CreateInputStatementContext createInputStreamParseContext = null;
    
    /**
     * <默认构造函数>
     *
     * @param parseContext 语法解析内容
     * @throws SemanticAnalyzerException 语义分析内容
     */
    public CreateInputStreamAnalyzer(ParseContext parseContext)
        throws SemanticAnalyzerException
    {
        super(parseContext);
        createInputStreamParseContext = (CreateInputStatementContext)parseContext;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public AnalyzeContext analyze()
        throws SemanticAnalyzerException
    {
        String streamName = createInputStreamParseContext.getStreamName();
        ColumnNameTypeListContext columns = createInputStreamParseContext.getColumns();
        
        getAnalyzeContext().setStreamName(streamName);
        getAnalyzeContext().setSchema(createSchema(streamName, columns));
        
        setSerDeDefine();
        setSourceDefine();
        setParallelNumber();
        
        return getAnalyzeContext();
    }
    
    private void setSourceDefine()
        throws SemanticAnalyzerException
    {
        setSourceClass();
        setSourceProperties();
    }
    
    private void setSourceClass()
    {
        ClassNameContext sourceClassName = createInputStreamParseContext.getSourceClassName();
        
        String newSourceClassName = sourceClassName.getClassName();
        if (sourceClassName.isInnerClass())
        {
            newSourceClassName = CQLSimpleLexerMapping.getFullName(newSourceClassName);
        }
        getAnalyzeContext().setRecordReaderClassName(newSourceClassName);
    }
    
    private void setSourceProperties()
        throws SemanticAnalyzerException
    {
        StreamPropertiesContext sourceProperties1 = createInputStreamParseContext.getSourceProperties();
        Map<String, String> sourceProperties = analyzeStreamProperties(sourceProperties1);
        getAnalyzeContext().setReadWriterProperties(convertSourceSimpleConf(sourceProperties));
    }
    
    private void setSerDeDefine()
        throws SemanticAnalyzerException
    {
        setSerDeClass();
        setSerDeProperties();
    }
    
    private void setSerDeClass()
        throws SemanticAnalyzerException
    {
        ClassNameContext deserClassName = createInputStreamParseContext.getDeserClassName();
        if (deserClassName == null)
        {
            setSerDeByDefault();
            return;
        }
        
        setSerDeByCQL(deserClassName);
    }
    
    private void setSerDeProperties()
        throws SemanticAnalyzerException
    {
        StreamPropertiesContext deserProperties = createInputStreamParseContext.getDeserProperties();
        Map<String, String> serdeProperties = analyzeStreamProperties(deserProperties);
        getAnalyzeContext().setSerDeProperties(convertSerDeSimpleConf(serdeProperties));
    }
    
    /**
     * 当使用了内部的输入输出的时候，允许配置属性进行简写
     * 这里对简写的配置属性进行还原，替换为全称
     *
     * @param serdeProperties 配置属性
     * @return 全称的配置属性
     */
    private Map<String, String> convertSerDeSimpleConf(final Map<String, String> serdeProperties)
        throws SemanticAnalyzerException
    {
        String deserClassName = getAnalyzeContext().getDeserializerClassName();
        return convertSimpleConf(serdeProperties, deserClassName);
    }
    
    private Map<String, String> convertSourceSimpleConf(final Map<String, String> sourceConf)
        throws SemanticAnalyzerException
    {
        String sourceClassName = getAnalyzeContext().getRecordReaderClassName();
        return convertSimpleConf(sourceConf, sourceClassName);
    }
    
    private Map<String, String> convertSimpleConf(Map<String, String> serdeProperties, String deserClassName)
        throws SemanticAnalyzerException
    {
        String apiOperator = InputOutputOperatorMapping.getAPIOperatorByPlatform(deserClassName);
        if (apiOperator == null)
        {
            return serdeProperties;
        }
        
        Map<String, String> configMapping = AnnotationUtils.getConfigMapping(apiOperator);
        Map<String, String> confs = Maps.newHashMap();
        
        for (Map.Entry<String, String> et : serdeProperties.entrySet())
        {
            String fullName = et.getKey().toLowerCase(Locale.US);
            String value = et.getValue();
            if (configMapping.containsKey(fullName))
            {
                fullName = configMapping.get(fullName);
            }
            confs.put(fullName, value);
        }
        
        return confs;
    }
    
    private void setSerDeByCQL(ClassNameContext deserClassName)
    {
        String newDeserClassName = deserClassName.getClassName();
        
        if (deserClassName.isInnerClass())
        {
            newDeserClassName = CQLSimpleLexerMapping.getFullName(newDeserClassName);
        }
        getAnalyzeContext().setDeserializerClassName(newDeserClassName);
    }
    
    private void setSerDeByDefault()
        throws SemanticAnalyzerException
    {
        StreamingConfig conf = new StreamingConfig();
        if (conf.containsKey(StreamingConfig.STREAMING_SERDE_DEFAULT))
        {
            getAnalyzeContext().setDeserializerClassName((String)conf.get(StreamingConfig.STREAMING_SERDE_DEFAULT));
        }
        else
        {
            SemanticAnalyzerException exception =
                new SemanticAnalyzerException(ErrorCode.SEMANTICANALYZE_UNKNOWN_SERDE);
            LOG.error("Not config default serialize/deserialize class.", exception);
            
            throw exception;
        }
    }
    
    private void setParallelNumber()
        throws SemanticAnalyzerException
    {
        if (createInputStreamParseContext.getParallelNumber() != null)
        {
            String number = createInputStreamParseContext.getParallelNumber().getNumber();
            Integer parallelNumber = ConstUtils.formatInt(number);
            getAnalyzeContext().setParallelNumber(parallelNumber);
        }
    }
    
}
