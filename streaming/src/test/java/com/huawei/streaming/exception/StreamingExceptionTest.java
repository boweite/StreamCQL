package com.huawei.streaming.exception;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Streaming异常测试
 * 
 */
public class StreamingExceptionTest
{
    /**
     * 异常测试
     */
    @Test
    public void messageTest()
    {
        StreamingException exp =
            new StreamingException(ErrorCode.SEMANTICANALYZE_EXPRESSION_DATATYPE_COMPARE, String.class.getName(),
                Integer.class.getName());
        System.out.println(exp.getMessage());
        assertTrue(exp.getErrorCode().equals(ErrorCode.SEMANTICANALYZE_EXPRESSION_DATATYPE_COMPARE));
    }
}
