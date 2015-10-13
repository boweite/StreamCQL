package com.huawei.streaming.udfs;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 字符串长度测试用例
 */
public class StringLengthTest
{

    /**
     * 测试字符串长度
     */
    @Test
    public void testEvaluate()
    {
        StringLength udf = new StringLength(null);
        assertTrue(udf.evaluate(null) == 0);
        assertTrue(udf.evaluate("") == 0);
        assertTrue(udf.evaluate("abc") == 3);

    }
}
