package com.huawei.streaming.udfs;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 获取一个月内的第几天
 */
public class DayTest
{

    /**
     * 测试用例
     *
     */
    @Test
    public void testEvaluate()
    {
        Day udf = new Day(null);
        assertTrue(udf.evaluate("2013-10-17") == TestUDFCommon.I_17);
        assertTrue(udf.evaluate("2013-10-16") == TestUDFCommon.I_16);
        assertTrue(udf.evaluate("2013-02-28") == TestUDFCommon.I_28);
        assertTrue(udf.evaluate("2013-02-29") == null);
        assertTrue(udf.evaluate("2013-02-29 09:00:01") == null);
        assertTrue(udf.evaluate("2013-02-28 09:00:01") == TestUDFCommon.I_28);
        assertTrue(udf.evaluate("2013-02-28 09:00:01.0") == TestUDFCommon.I_28);
    }
}
