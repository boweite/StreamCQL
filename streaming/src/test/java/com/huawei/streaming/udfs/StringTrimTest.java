package com.huawei.streaming.udfs;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 字符串trim测试
 */
public class StringTrimTest
{

    /**
     * 测试用例
     */
    @Test
    public void testEvaluate()
    {
        StringTrim udf = new StringTrim(null);
        assertTrue(udf.evaluate(null).equals(""));
        assertTrue(udf.evaluate("").equals(""));
        assertTrue(udf.evaluate("ab").equals("ab"));
        assertTrue(udf.evaluate("ab ").equals("ab"));
        assertTrue(udf.evaluate("ab\t").equals("ab"));
        assertTrue(udf.evaluate("ab\n").equals("ab"));
        assertTrue(udf.evaluate(" ").equals(""));
    }
}
