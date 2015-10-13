package com.huawei.streaming.udfs;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 字符串拼接函数测试
 */
public class StringConcatTest
{

    /**
     * 测试用例
     */
    @Test
    public void testEvaluate()
    {
        StringConcat udf = new StringConcat(null);
        assertTrue(udf.evaluate("a", "b").equals("ab"));
        assertTrue(udf.evaluate("ab", "").equals("ab"));
        assertTrue(udf.evaluate("ab", null) == null);
    }

    /**
     * 测试用例
     */
    @Test
    public void testEvaluate1()
    {
        StringConcat udf = new StringConcat(null);
        assertTrue(udf.evaluate("a", "b", "c").equals("abc"));
        assertTrue(udf.evaluate("ab", "", "c").equals("abc"));
        assertTrue(udf.evaluate("ab", null, "c") == null);
    }

    /**
     * 测试用例
     */
    @Test
    public void testEvaluate2()
    {
        StringConcat udf = new StringConcat(null);
        assertTrue(udf.evaluate("a", "b", "c", "d").equals("abcd"));
        assertTrue(udf.evaluate("ab", "", "c", "d").equals("abcd"));
        assertTrue(udf.evaluate("ab", null, "c", "d") == null);
    }

    /**
     * 测试用例
     */
    @Test
    public void testEvaluate3()
    {
        StringConcat udf = new StringConcat(null);
        assertTrue(udf.evaluate("a", "b", "c", "d", "e").equals("abcde"));
        assertTrue(udf.evaluate("ab", "", "c", "d", "e").equals("abcde"));
        assertTrue(udf.evaluate("ab", null, "c", "d", "e") == null);
    }

    /**
     * 测试用例
     */
    @Test
    public void testEvaluate4()
    {
        StringConcat udf = new StringConcat(null);
        assertTrue(udf.evaluate("a", "b", "c", "d", "e", "f").equals("abcdef"));
        assertTrue(udf.evaluate("ab", "", "c", "d", "e", "f").equals("abcdef"));
        assertTrue(udf.evaluate("ab", null, "c", "d", "e", "f") == null);
    }

    /**
     * 测试用例
     */
    @Test
    public void testEvaluate5()
    {
        StringConcat udf = new StringConcat(null);
        assertTrue(udf.evaluate("a", "b", "c", "d", "e", "f", "g").equals("abcdefg"));
        assertTrue(udf.evaluate("ab", "", "c", "d", "e", "f", "g").equals("abcdefg"));
        assertTrue(udf.evaluate("ab", null, "c", "d", "e", "f", "g") == null);
    }
}
