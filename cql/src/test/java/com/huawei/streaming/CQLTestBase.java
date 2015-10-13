package com.huawei.streaming;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CQLTestBase
{
    private static final Logger LOG = LoggerFactory.getLogger(CQLTestBase.class);

    /**
     * 初始化测试类之前要执行的初始化方法
     *
     * @throws Exception 初始化中可能抛出的异常
     */
    @BeforeClass
    public static void setUpBeforeClass()
     throws Exception
    {
        LOG.info("set cql.dependency.jar in test.");

        //添加streaming-storm和streaming的jar包到服务端运行
        URLClassLoader classLoader = (URLClassLoader)Thread.currentThread().getContextClassLoader();
        for(URL url : classLoader.getURLs())
        {
            //随便找一个jar包进行填充
            System.setProperty("cql.dependency.jar", url.getPath());
            break;
        }

    }


    /**
     * 所有测试用例执行完毕之后执行的方法
     *
     * @throws Exception 执行异常
     */
    @AfterClass
    public static void tearDownAfterClass()
     throws Exception
    {

    }

}
