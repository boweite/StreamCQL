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

package com.huawei.streaming.operator;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.streaming.config.StreamingConfig;
import com.huawei.streaming.event.IEventType;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.exception.ErrorCode;
import com.huawei.streaming.exception.StreamSerDeException;
import com.huawei.streaming.exception.StreamingException;
import com.huawei.streaming.serde.StreamSerDe;

/**
 * 输入流
 * 
 */
public final class InputOperator extends AbsOperator
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -9060757385287426494L;

    private static final Logger LOG = LoggerFactory.getLogger(InputOperator.class);

    private StreamSerDe serde;

    private IEventType outputSchema;

    private String outputStreamName;

    private IInputStreamOperator inputStream;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConfig(StreamingConfig conf) throws StreamingException
    {
        super.setConfig(conf);
        this.outputSchema = (IEventType)(conf.get(StreamingConfig.STREAMING_INNER_OUTPUT_SCHEMA));
        this.outputStreamName = (String)conf.get(StreamingConfig.STREAMING_INNER_OUTPUT_STREAM_NAME);
        if (conf.containsKey(StreamingConfig.STREAMING_INNER_OUTPUT_SCHEMA))
        {
            if (null != serde)
            {
                serde.setSchema((TupleEventType)conf.get(StreamingConfig.STREAMING_INNER_OUTPUT_SCHEMA));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void initialize()
     throws StreamingException
    {
        initializeSerDe();
        inputStream.setEmitter(getEmitter());
        inputStream.setSerDe(getSerDe());
        inputStream.initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void execute(String streamName, TupleEvent event)
        throws StreamingException
    {
        inputStream.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() throws StreamingException
    {
        inputStream.destroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setInputStream(List<String> streamNames)
    {
    }

    /**
     * 设置反序列化类
     * @param streamSerDe 反序列化类
     */
    public void setSerDe(StreamSerDe streamSerDe)
    {
        this.serde = streamSerDe;
    }

    /**
     * 获取反序列化类
     * @return 反序列化类
     */
    public StreamSerDe getSerDe()
    {
        return serde;
    }

    /**
     * 设置输入流算子
     * @param stream 输入流算子
     */
    public void setInputStreamOperator(IInputStreamOperator stream)
    {
        this.inputStream = stream;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<String> getInputStream()
    {
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void setInputSchema(Map<String, IEventType> schemas)
        throws StreamingException
    {
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final Map<String, IEventType> getInputSchema()
    {
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setOutputStream(String streamName)
        throws StreamingException
    {
        this.outputStreamName = streamName;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getOutputStream()
    {
        return outputStreamName;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setOutputSchema(IEventType schema)
        throws StreamingException
    {
        this.outputSchema = schema;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public IEventType getOutputSchema()
    {
        return this.outputSchema;
    }

    private void initializeSerDe() throws StreamingException
    {
        if(getSerDe() == null)
        {
            StreamingException exception = new StreamingException(ErrorCode.SEMANTICANALYZE_UNKNOWN_SERDE);
            LOG.error(exception.getMessage());
            throw exception;
        }

        try
        {
            getSerDe().initialize();
        }
        catch (StreamSerDeException e)
        {
            throw StreamingException.wrapException(e);
        }
    }

}
