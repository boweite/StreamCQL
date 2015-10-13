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

package com.huawei.streaming.cql.executor.userdefined;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.huawei.streaming.config.StreamingConfig;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.exception.StreamingException;
import com.huawei.streaming.exception.StreamingRuntimeException;
import com.huawei.streaming.operator.IEmitter;
import com.huawei.streaming.operator.IFunctionStreamOperator;

public class UserOperatorTest implements IFunctionStreamOperator
{
    private static final Logger LOG = LoggerFactory.getLogger(UserOperatorTest.class);
    
    public static final Charset CHARSET = Charset.forName("UTF-8");
    
    private static final long serialVersionUID = -4438239751340766284L;
    
    private static final String CONF_FILE_NAME = "userop.filename";
    
    private String fileName;
    
    private Properties properties;
    
    private Map<String, IEmitter> emitters = null;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setConfig(StreamingConfig conf)
        throws StreamingException
    {
        if (!conf.containsKey(CONF_FILE_NAME))
        {
            LOG.error("can not found config value {}.", CONF_FILE_NAME);
            throw new StreamingException("can not found config value " + CONF_FILE_NAME + ".");
        }
        
        fileName = conf.getStringValue(CONF_FILE_NAME);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setEmitter(Map<String, IEmitter> emitterMap)
    {
        if (emitterMap == null || emitterMap.isEmpty())
        {
            LOG.error("can not found emitter.");
            throw new StreamingRuntimeException("can not found config value " + CONF_FILE_NAME + ".");
        }
        emitters = emitterMap;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize()
        throws StreamingException
    {
        File file = new File(fileName);
        validateFile(file);
        loadProperties(file);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String streamName, TupleEvent event)
        throws StreamingException
    {
        Object[] values = event.getAllValues();
        Object[] result = new Object[3];
        if (properties.containsKey(values[0]))
        {
            result[0] = properties.get(values[0]);
            result[1] = 1;
            result[2] = 1.0f;
        }
        else
        {
            result[0] = "NONE";
            result[1] = 1;
            result[2] = 1.0F;
        }
        
        for (IEmitter emitter : emitters.values())
        {
            emitter.emit(result);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy()
        throws StreamingException
    {
        
    }
    
    private void validateFile(File file)
        throws StreamingException
    {
        if (!file.exists())
        {
            LOG.error("file in path is not exists.");
            throw new StreamingException("file in path is not exists.");
        }
        
        if (!file.isFile())
        {
            LOG.error("file in path is not a file type.");
            throw new StreamingException("file in path is not a file type.");
        }
    }
    
    private void loadProperties(File file)
        throws StreamingException
    {
        properties = new Properties();
        BufferedReader reader = null;
        try
        {
            reader = Files.newReader(file, CHARSET);
            properties.load(Files.newReader(file, CHARSET));
        }
        catch (IOException e)
        {
            LOG.error("failed to read property files.", e);
            throw new StreamingException("failed to read property files.");
        }
        finally
        {
            Closeables.closeQuietly(reader);
        }
    }
    
}
