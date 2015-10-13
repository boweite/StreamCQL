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
package com.huawei.streaming.storm;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.utils.Utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huawei.streaming.config.StreamingConfig;
import com.huawei.streaming.exception.ErrorCode;
import com.huawei.streaming.exception.StreamingException;

/**
 * storm用到的配置属性
 *
 */
public class StormConf
{
    private static final String ZKADDRESS_SEPRATOR = ",";

    private static final Logger LOG = LoggerFactory.getLogger(StormApplication.class);
    
    private static final String STORM_ZOOKEEPER_CONNECTION_TIMEOUT = Config.STORM_ZOOKEEPER_CONNECTION_TIMEOUT;
    
    private static final String STORM_ZOOKEEPER_SESSION_TIMEOUT = Config.STORM_ZOOKEEPER_SESSION_TIMEOUT;
    
    /**
     * 提交storm应用程序的时候，jar包所在的property名称
     */
    private static final String STORM_SUBMIT_JAR_PROPERTY = "storm.jar";
    
    /**
     * kill 任务的时候的等待时间，storm config中的配置项
     */
    private static final String TASK_KILL_WAIT_SECONDS = "task.kill.wait.seconds";
    
    private String defaultJarPath = null;
    
    private int haZKConnectionTimeOut = -1;
    
    private int haZKSessionTimeOut = -1;
    
    private boolean isTestModel = false;
    
    private int killApplicationOverTime;
    
    /**
     * kill storm应用程序的时候的默认等待时间，单位秒
     */
    private int killWaitingSeconds;
    
    private long localTaskAliveTime;
    
    private String nimbusHost = null;
    
    private int nimbusPort;
    
    private boolean submitLocal = false;
    
    private String thriftTransportPlugin;
    
    private int workerNumber = 1;
    
    private int rebalanceWaitSecs;
    
    private List<String> zkAddresses = null;
    
    private int zkPort;
    
    private String saslQop = null;

    private String userPrincipalInstance = null;

    private Boolean isSecurity = false;
    
    /**
     * 构造函数
     *
     * @param config 配置属性
     * @throws StreamingException 初始化异常
     */
    public StormConf(StreamingConfig config)
        throws StreamingException
    {
        getSecurityType(config);
        initStormConf(config);
        initStreamingConfig(config);
    }
    
    /**
     * 创建storm需要的配置属性
     *
     * @return storm需要的配置属性
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> createStormConf()
    {
        Map<String, Object> conf = Maps.newHashMap();
        
        Map<String, Object> stormConf = Utils.readStormConfig();
        if (stormConf != null)
        {
            conf.putAll(stormConf);
        }
        
        conf.put(Config.NIMBUS_HOST, nimbusHost);
        conf.put(Config.NIMBUS_THRIFT_PORT, nimbusPort);
        conf.put(Config.STORM_THRIFT_TRANSPORT_PLUGIN, thriftTransportPlugin);
        conf.put(TASK_KILL_WAIT_SECONDS, killWaitingSeconds);
        conf.put(Config.TOPOLOGY_WORKERS, workerNumber);
        conf.put(Config.STORM_ZOOKEEPER_SERVERS, zkAddresses);
        conf.put(Config.STORM_ZOOKEEPER_PORT, zkPort);

        // 之所以在StormConf内部定义常量，而不引用Storm中的Config对象，就是为了保证在纯社区版下也可以执行
        /**
         * 本来计划移除内部storm的配置属性映射，
         * 但是由于Storm的配置属性是复合结构，中间可能包含Map或者List
         * 但是Streaming只允许字符串形式的配置属性，所以就没办法进行类型转换
         * 所以只能使用这种映射的方式
         */
        if (haZKConnectionTimeOut > 0)
        {
            conf.put(STORM_ZOOKEEPER_CONNECTION_TIMEOUT, haZKConnectionTimeOut);
        }
        
        if (haZKSessionTimeOut > 0)
        {
            conf.put(STORM_ZOOKEEPER_SESSION_TIMEOUT, haZKConnectionTimeOut);
        }
        
        return conf;
    }

    public int getKillApplicationOverTime()
    {
        return killApplicationOverTime;
    }
    
    /**
     * 获取kill任务的等待时间
     *
     * @return 等待时间
     */
    public int getKillWaitingSeconds()
    {
        return killWaitingSeconds;
    }
    
    /**
     * rebalance应用程序的等待时间
     * @return 等待时间
     */
    public int getRebalanceWaitSecs()
    {
        return rebalanceWaitSecs;
    }

    
    public long getLocalTaskAliveTime()
    {
        return localTaskAliveTime;
    }

    private void initStormConf(StreamingConfig config)
        throws StreamingException
    {
        killWaitingSeconds = config.getIntValue(StreamingConfig.STREAMING_STORM_KILLTASK_WAITSECONDS);
        rebalanceWaitSecs = config.getIntValue(StreamingConfig.STREAMING_STORM_REBALANCE_WAITSECONDS);
        nimbusHost = config.getStringValue(StreamingConfig.STREAMING_STORM_NIMBUS_HOST);
        nimbusPort = config.getIntValue(StreamingConfig.STREAMING_STORM_NIMBUS_PORT);

        String addresses = (String)config.get(StreamingConfig.STREAMING_STORM_HA_ZKADDRESS);
        zkAddresses = Lists.newArrayList(addresses.split(ZKADDRESS_SEPRATOR));

        if(isSecurity)
        {
            saslQop = config.getStringValue(StreamingConfig.STREAMING_SECURITY_SASL_QOP);
            userPrincipalInstance = config.getStringValue(StreamingConfig.STREAMING_SECURITY_USER_PRINCIPAL_INSTANCE);
        }
        zkPort = config.getIntValue(StreamingConfig.STREAMING_STORM_HA_ZKPORT);
        submitLocal = Boolean.valueOf((String)config.get(StreamingConfig.STREAMING_STORM_SUBMIT_ISLOCAL));
        workerNumber = Integer.valueOf((String)config.get(StreamingConfig.STREAMING_STORM_WORKER_NUMBER));
        thriftTransportPlugin = (String)config.get(StreamingConfig.STREAMING_STORM_THRIFT_TRANSPORT_PLUGIN);
        haZKConnectionTimeOut = config.getIntValue(StreamingConfig.STREAMING_STORM_HA_ZKSCONNECTIONTIMEOUT);
        haZKSessionTimeOut = config.getIntValue(StreamingConfig.STREAMING_STORM_HA_ZKSESSIONTIMEOUT);
    }

    private void initStreamingConfig(StreamingConfig config)
        throws StreamingException
    {
        localTaskAliveTime = config.getIntValue(StreamingConfig.STREAMING_LOCALTASK_ALIVETIME_MS);
        killApplicationOverTime = config.getIntValue(StreamingConfig.STREAMING_KILLAPPLICATION_OVERTIME);
        isTestModel = Boolean.valueOf((String)config.get(StreamingConfig.STREAMING_COMMON_ISTESTMODEL));
    }
    
    private void getSecurityType(StreamingConfig config)
        throws StreamingException
    {
        switch (SecurityFactory.getSecurityType(config))
        {
            case KERBEROS:
            {
                isSecurity = true;
                break;
            }
            default:
            {
                isSecurity = false;
                break;
            }
        }
    }
    
    public boolean isSubmitLocal()
    {
        return submitLocal;
    }
    
    /**
     * 是否是测试模式
     * 如果是测试模式，则不提交任务
     *
     * @return 如果是测试模式，返回true
     */
    public boolean isTestModel()
    {
        return isTestModel;
    }
    
    public void setDefaultJarPath(String newPath)
    {
        defaultJarPath = newPath;
    }
    
    /**
     * 设置storm jar
     *
     * @throws StreamingException jar包设置异常
     */
    public void setStormJar()
        throws StreamingException
    {
        if (defaultJarPath == null)
        {
            StreamingException exception = new StreamingException(ErrorCode.UNKNOWN_SERVER_COMMON_ERROR);
            LOG.error("Can not find 'storm.jar' before submit application.", exception);
            throw exception;
        }
        System.setProperty(STORM_SUBMIT_JAR_PROPERTY, defaultJarPath);
    }
    
}
