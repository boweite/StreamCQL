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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.apache.zookeeper.server.persistence.FileTxnSnapLog;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huawei.streaming.api.Application;
import com.huawei.streaming.config.StreamingConfig;
import com.huawei.streaming.cql.exception.CQLException;
import com.huawei.streaming.cql.executor.PhysicalPlanExecutor;
import com.huawei.streaming.cql.executor.PhysicalPlanWriter;
import com.huawei.streaming.exception.ErrorCode;

public class LocalTaskCommons
{
    private static final String ZKLOG = "zklog";
    
    private static final String ZKDATA = "zkdata";
    
    private static final String DEFAULT_CHARSET = "UTF-8";
    
    private static final Logger LOG = LoggerFactory.getLogger(LocalTaskCommons.class);
    
    private static ServerCnxnFactory cnxnFactory = null;
    
    public static void write(String basepath, String result, Application app)
    {
        String serDir = setDir(basepath);
        PhysicalPlanWriter.write(app, serDir + result);
    }
    
    /**
     * 设置待序列化的文件路径
     *
     */
    public static String setDir(String basePath)
    {
        String classPath = LocalTaskCommons.class.getResource("/").getPath();
        
        try
        {
            classPath = URLDecoder.decode(classPath, DEFAULT_CHARSET);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        
        return classPath + basePath + File.separator;
    }
    
    /**
     * 比较文件内容
     * @param basepath 基本路径
     * @param resultFileName 结果文件
     * @param outputFileName 输出文件，正确的文件内容
     * @return 文件内容一样，返回true
     * @throws IOException 比较异常
     */
    public static boolean compare(String basepath, String resultFileName, String outputFileName)
        throws IOException
    {
        String serDir = setDir(basepath);
        File outFile = new File(serDir + outputFileName);
        File resultsFile = new File(serDir + resultFileName);
        return CQLTestCommons.compareFileContent(outFile, resultsFile);
    }
    
    /**
     * 生成本地任务的配置属性
     * @return 配置属性
     */
    public static TreeMap<String, String> createLocalConfs()
    {
        TreeMap<String, String> config = Maps.newTreeMap();
        config.put("streaming.storm.submit.islocal", "true");
        //                config.put("streaming.storm.submit.islocal", "false");
        //                config.put("streaming.storm.nimbus.host", "160.133.0.107");
        return config;
    }
    
    /**
     * 本地提交执行计划
     * @param basePath 基础路径
     * @param caseName 用例名称
     * @throws CQLException 提交异常
     */
    public static void localSubmitXML(String basePath, String caseName)
        throws CQLException
    {
        LOG.info("start to execute test case {}", caseName);
        String serDir = setDir(basePath);
        long startTime = System.currentTimeMillis();
        try
        {
            StreamingConfig conf = new StreamingConfig();
            conf.put(StreamingConfig.STREAMING_STORM_SUBMIT_ISLOCAL, "true");
            
            new PhysicalPlanExecutor().execute(serDir + caseName + ".xml");
        }
        catch (CQLException e1)
        {
            if (e1.getMessage() == null || !e1.getMessage().contains("Unable to delete file:"))
            {
                throw e1;
            }
        }
        
        long elapsedTime = System.currentTimeMillis() - startTime;
        LOG.info("Done query: {}.  elapsedTime={}s", caseName, elapsedTime / CQLTestCommons.BASICTIMESTAMP);
        org.junit.Assert.assertTrue("Test passed", true);
    }
    
    /**
     * 启动zookeeper服务
     * @throws ConfigException 配置文件异常
     * @throws IOException 启动异常
     */
    public static void startZookeeperServer()
        throws ConfigException, IOException
    {
        String classPath = LocalTaskCommons.class.getResource("/").getPath();
        String[] args = {classPath + File.separator + "zoo.cfg"};
        ServerConfig config = new ServerConfig();
        if (args.length == 1)
        {
            config.parse(args[0]);
        }
        else
        {
            config.parse(args);
        }
        
        LOG.info("start to startup zookeeper server");
        runFromConfig(config);
    }
    
    /**
     * 本地提交应用程序
     * @param apiApplication 应用程序
     * @throws Exception 提交异常
     */
    public static void localSubmit(Application apiApplication)
        throws Exception
    {
        long startTime = System.currentTimeMillis();
        try
        {
            waitingUtilClose();
            new PhysicalPlanExecutor().execute(apiApplication);
            waitingUtilClose();
        }
        catch (Exception e1)
        {
            if (!e1.getMessage().contains("Unable to delete file:"))
            {
                throw e1;
            }
        }
        
        long elapsedTime = System.currentTimeMillis() - startTime;
        LOG.info("Done query: user defined operator.  elapsedTime=" + elapsedTime / CQLTestCommons.BASICTIMESTAMP + "s");
        org.junit.Assert.assertTrue("Test passed", true);
    }
    
    /**
     * 本地提交应用程序
     * @param basePath 基本路径
     * @param caseName 用例名称
     * @throws Exception 异常信息
     */
    public static void localSubmit(String basePath, String caseName)
        throws Exception
    {
        LOG.info("start to execut test case {}", caseName);
        String serDir = setDir(basePath);
        long startTime = System.currentTimeMillis();
        try
        {
            List<String> sqls = CQLTestCommons.addFile(new File(serDir + caseName + CQLTestCommons.INPUT_POSTFIX));
            Driver driver = new Driver();
            
            List<String> submitConf = changeConfToCQL(createLocalConfs());
            for (String conf : submitConf)
            {
                driver.run(conf);
            }
            
            for (String sql : sqls)
            {
                driver.run(sql);
            }
        }
        catch (InterruptedException e)
        {
            
        }
        catch (IOException | CQLException e1)
        {
            if (e1.getMessage() == null || !e1.getMessage().contains("Unable to delete file:"))
            {
                throw e1;
            }
            
            LOG.info("got a exception in test");
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        LOG.info("Done query: {}.  elapsedTime={}s", caseName, elapsedTime / CQLTestCommons.BASICTIMESTAMP);
        org.junit.Assert.assertTrue("Test passed", true);
    }
    
    /**
     * 本地提交异常应用程序
     * @param basePath 基本路径
     * @param caseName 用例名称
     * @param errorCode 异常码
     * @throws Exception 异常信息
     */
    public static void navigageSubmit(String basePath, String caseName, ErrorCode errorCode)
        throws Exception
    {
        LOG.info("start to execut test case {}", caseName);
        String serDir = setDir(basePath);
        long startTime = System.currentTimeMillis();
        try
        {
            List<String> sqls = CQLTestCommons.addFile(new File(serDir + caseName + CQLTestCommons.INPUT_POSTFIX));
            Driver driver = new Driver();
            
            List<String> submitConf = changeConfToCQL(createLocalConfs());
            for (String conf : submitConf)
            {
                driver.run(conf);
            }
            
            for (String sql : sqls)
            {
                driver.run(sql);
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("Done query: {}.  elapsedTime={}s", caseName, elapsedTime / CQLTestCommons.BASICTIMESTAMP);
            org.junit.Assert.fail("Test failed");
            return;
        }
        catch (InterruptedException e)
        {
            
        }
        catch (IOException e1)
        {
            if (e1.getMessage() == null || !e1.getMessage().contains("Unable to delete file:"))
            {
                throw e1;
            }
            
            LOG.info("got a exception in test");
        }
        catch (CQLException e2)
        {
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOG.info("Done query: {}.  elapsedTime={}s", caseName, elapsedTime / CQLTestCommons.BASICTIMESTAMP);
            org.junit.Assert.assertEquals(e2.getErrorCode(), errorCode);
            org.junit.Assert.assertTrue("Test passed", true);
            return;
        }
        
        long elapsedTime = System.currentTimeMillis() - startTime;
        LOG.info("Done query: {}.  elapsedTime={}s", caseName, elapsedTime / CQLTestCommons.BASICTIMESTAMP);
        org.junit.Assert.fail("Test failed, got an unkown output.");
    }
    
    private static void waitingUtilClose()
    {
        while (true)
        {
            Socket socket = null;
            try
            {
                socket = new Socket("127.0.0.1", 7999);
                socket.setKeepAlive(true);
                LOG.info("port is open now, waiting one second to retry");
                TimeUnit.SECONDS.sleep(1);
            }
            catch (Exception e)
            {
                break;
            }
            finally
            {
                if (socket != null)
                {
                    try
                    {
                        socket.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    private static List<String> changeConfToCQL(Map<String, String> conf)
    {
        if (conf == null)
        {
            return Lists.newArrayList();
        }
        
        List<String> cqls = Lists.newArrayList();
        for (Entry<String, String> et : conf.entrySet())
        {
            cqls.add("set \"" + et.getKey() + "\" = \"" + et.getValue() + "\"");
        }
        return cqls;
    }
    
    /**
     * Run from a ServerConfig.
     *
     * @param config ServerConfig to use.
     * @throws IOException
     */
    private static void runFromConfig(ServerConfig config)
        throws IOException
    {
        LOG.info("Starting server");
        String newDataDir = LocalTaskCommons.setDir(ZKDATA);
        String newLogDir = LocalTaskCommons.setDir(ZKLOG);
        
        try
        {
            removeZKTmpDir(newDataDir, newLogDir);
            ZooKeeperServer zkServer = new ZooKeeperServer();
            FileTxnSnapLog ftxn = new FileTxnSnapLog(new File(newLogDir), new File(newDataDir));
            zkServer.setTxnLogFactory(ftxn);
            zkServer.setTickTime(config.getTickTime());
            zkServer.setMinSessionTimeout(config.getMinSessionTimeout());
            zkServer.setMaxSessionTimeout(config.getMaxSessionTimeout());
            cnxnFactory = ServerCnxnFactory.createFactory();
            cnxnFactory.configure(config.getClientPortAddress(), config.getMaxClientCnxns());
            cnxnFactory.startup(zkServer);
        }
        catch (InterruptedException e)
        {
            // warn, but generally this is ok
            LOG.warn("Server interrupted", e);
        }
    }
    
    private static void removeZKTmpDir(String newDataDir, String newLogDir)
    {
        try
        {
            File dataDir = new File(newDataDir);
            if (dataDir.exists())
            {
                FileUtils.deleteDirectory(dataDir);
            }
            
            File logdir = new File(newLogDir);
            if (logdir.exists())
            {
                FileUtils.deleteDirectory(logdir);
            }
        }
        catch (IOException e)
        {
            LOG.warn("ignore delete zookeeper tmp dir exception.");
        }
    }
    
    public static void stopZooKeeperServer()
    {
        LOG.info("start to shutdown zookeeper server");
        cnxnFactory.shutdown();
        cnxnFactory = null;
        LOG.info("remove zookeeper tmp dir");
        String newDataDir = LocalTaskCommons.setDir(ZKDATA);
        String newLogDir = LocalTaskCommons.setDir(ZKLOG);
        removeZKTmpDir(newDataDir, newLogDir);
    }
}
