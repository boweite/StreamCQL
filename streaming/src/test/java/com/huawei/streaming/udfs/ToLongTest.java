package com.huawei.streaming.udfs;

import java.sql.Timestamp;

import org.junit.Test;

import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.expression.MethodExpression;
import static org.junit.Assert.assertTrue;

/**
 * 数据转化成Long类型测试
 */
public class ToLongTest
{

    @Test
    public void testEvaluate() throws Exception
    {
        long x = 1000l;
        Timestamp ts = new Timestamp(x);
        ToLong tt = new ToLong(null);
        assertTrue(tt.evaluate(ts) == 1000L);

        MethodExpression mtExpression = new MethodExpression(System.class,"currentTimeMillis",null);
        System.out.println(mtExpression.evaluate(new TupleEvent()));
    }


    /**
     * 测试数据类型转换
     */
    @Test
    public void testEvaluate2()
    {
        ToLong toLong = new ToLong(null);
        ToDate toDate = new ToDate(null);
        ToTime toTime = new ToTime(null);
        ToTimeStamp toTimeStamp = new ToTimeStamp(null);
        ToDecimal toDecimal = new ToDecimal(null);
        assertTrue(toLong.evaluate(1).equals(1L));
        assertTrue(toLong.evaluate(1L).equals(1L));
        assertTrue(toLong.evaluate(1.0f).equals(1L));
        assertTrue(toLong.evaluate(1.4f).equals(1L));
        assertTrue(toLong.evaluate(1.5f).equals(1L));
        assertTrue(toLong.evaluate(1.6f).equals(1L));
        assertTrue(toLong.evaluate(1.9f).equals(1L));
        assertTrue(toLong.evaluate(1.9d).equals(1L));
        assertTrue(toLong.evaluate("1").equals(1L));
        assertTrue(toLong.evaluate("1.9") == null);
        assertTrue(toLong.evaluate(toDecimal.evaluate("1.9")).equals(1L));
        assertTrue(toLong.evaluate(toDate.evaluate("1970-01-01")).equals(-28800000L));
        assertTrue(toLong.evaluate(toTime.evaluate("15:40:00")).equals(27600000L));
        assertTrue(toLong.evaluate(toTimeStamp.evaluate("1970-01-01 15:40:00.000000")).equals(27600000L));
    }

    /**
     * 测试数据类型转换
     */
    @Test
    public void testEvaluate3()
    {
        ToLong toLong = new ToLong(null);
        assertTrue(toLong.evaluate("1L") == null);
        assertTrue(toLong.evaluate("-1").equals(-1L));
        assertTrue(toLong.evaluate("-1").equals(-1L));
    }

}
