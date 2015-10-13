package com.huawei.streaming.config;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 配置属性中引用系统变量测试
 *
 */
public class ConfVariableTest
{
    private static StreamingConfig config;

    private static String CONF_VAR = "test";

    /**
     * 测试类启动之前的初始化
     *
     * @throws Exception 初始化异常
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        config = new StreamingConfig();
    }

    /**
     * 测试类启动之后的清理
     *
     * @throws Exception 清理异常
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        config = null;
    }


    /**
     * 测试前准备
     *
     * @throws Exception 测试异常
     */
    @Before
    public void setUp() throws Exception
    {

    }

    /**
     * 测试后清理
     *
     * @throws Exception 测试异常
     */
    @After
    public void tearDown() throws Exception
    {
        config.remove(CONF_VAR);
    }

    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetSystemValue() throws Exception
    {
        ConfVariable confVariable = new ConfVariable("${System:java.runtime.name}");
        String value = ConfVariable.getValue(confVariable, config, null);
        assertEquals(value, "Java(TM) SE Runtime Environment");
    }

    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetSystemValue2() throws Exception
    {
        ConfVariable confVariable = new ConfVariable("${System:java.runtime.name}_abc");
        String value = ConfVariable.getValue(confVariable, config, null);
        assertEquals(value, "Java(TM) SE Runtime Environment_abc");
    }

    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetConfValue() throws Exception
    {
        ConfVariable confVariable = new ConfVariable("${conf:streaming.storm.worker.number}");
        String value = ConfVariable.getValue(confVariable, config, null);
        assertEquals(value, "1");
    }

    /**
     * 测试用例
     *
     * @throws Exception 测试异常
     */
    @Test
    public void testGetConfValue2() throws Exception
    {
        ConfVariable confVariable = new ConfVariable("${conf:streaming.storm.worker.number}11");
        String value = ConfVariable.getValue(confVariable, config, null);
        assertEquals(value, "111");
    }
}
