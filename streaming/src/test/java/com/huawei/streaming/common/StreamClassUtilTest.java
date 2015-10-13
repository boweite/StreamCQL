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

package com.huawei.streaming.common;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.huawei.streaming.exception.IllegalDataTypeException;

public class StreamClassUtilTest
{
    
    @Test
    public void testGetWrapType()
    {
        Assert.assertEquals(Boolean.class, StreamClassUtil.getWrapType(boolean.class));
        Assert.assertEquals(Boolean.class, StreamClassUtil.getWrapType(Boolean.class));
        Assert.assertEquals(Integer.class, StreamClassUtil.getWrapType(int.class)); 
        Assert.assertEquals(Integer.class, StreamClassUtil.getWrapType(Integer.class));
        Assert.assertEquals(Long.class, StreamClassUtil.getWrapType(long.class));
        Assert.assertEquals(Long.class, StreamClassUtil.getWrapType(Long.class)); 
        Assert.assertEquals(Double.class, StreamClassUtil.getWrapType(double.class));
        Assert.assertEquals(Double.class, StreamClassUtil.getWrapType(Double.class)); 
        Assert.assertEquals(Float.class, StreamClassUtil.getWrapType(float.class));
        Assert.assertEquals(Float.class, StreamClassUtil.getWrapType(Float.class)); 
        Assert.assertEquals(BigDecimal.class, StreamClassUtil.getWrapType(BigDecimal.class));
        Assert.assertEquals(Date.class, StreamClassUtil.getWrapType(Date.class));
        Assert.assertEquals(Time.class, StreamClassUtil.getWrapType(Time.class));
        Assert.assertEquals(Timestamp.class, StreamClassUtil.getWrapType(Timestamp.class)); 
    }
    
    @Test
    public void testIsNumberic()
    {
        Assert.assertEquals(false, StreamClassUtil.isNumberic(boolean.class));
        Assert.assertEquals(false, StreamClassUtil.isNumberic(Boolean.class));
        Assert.assertEquals(true, StreamClassUtil.isNumberic(int.class)); 
        Assert.assertEquals(true, StreamClassUtil.isNumberic(Integer.class));
        Assert.assertEquals(true, StreamClassUtil.isNumberic(long.class));
        Assert.assertEquals(true, StreamClassUtil.isNumberic(Long.class)); 
        Assert.assertEquals(true, StreamClassUtil.isNumberic(double.class));
        Assert.assertEquals(true, StreamClassUtil.isNumberic(Double.class)); 
        Assert.assertEquals(true, StreamClassUtil.isNumberic(float.class));
        Assert.assertEquals(true, StreamClassUtil.isNumberic(Float.class)); 
        Assert.assertEquals(true, StreamClassUtil.isNumberic(BigDecimal.class));
        Assert.assertEquals(false, StreamClassUtil.isNumberic(Date.class));
        Assert.assertEquals(false, StreamClassUtil.isNumberic(Time.class));
        Assert.assertEquals(false, StreamClassUtil.isNumberic(Timestamp.class));
    }
    
    @Test
    public void testGetArithmaticType()
    {
        try
        {
            StreamClassUtil.getArithmaticType(boolean.class, int.class);
            fail();
        }
        catch (IllegalDataTypeException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            Assert.assertEquals(Integer.class, StreamClassUtil.getArithmaticType(int.class, int.class)); 
            Assert.assertEquals(Integer.class, StreamClassUtil.getArithmaticType(int.class, Integer.class)); 
            Assert.assertEquals(Integer.class, StreamClassUtil.getArithmaticType(Integer.class, Integer.class));
            Assert.assertEquals(Long.class, StreamClassUtil.getArithmaticType(int.class, Long.class));
            Assert.assertEquals(Long.class, StreamClassUtil.getArithmaticType(Integer.class, long.class));
            Assert.assertEquals(Double.class, StreamClassUtil.getArithmaticType(int.class, Double.class));
            Assert.assertEquals(Double.class, StreamClassUtil.getArithmaticType(Integer.class, double.class));
            Assert.assertEquals(Float.class, StreamClassUtil.getArithmaticType(int.class, Float.class));
            Assert.assertEquals(Float.class, StreamClassUtil.getArithmaticType(Integer.class, float.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getArithmaticType(int.class, BigDecimal.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getArithmaticType(Integer.class, BigDecimal.class));
            
            Assert.assertEquals(Long.class, StreamClassUtil.getArithmaticType(long.class, long.class));
            Assert.assertEquals(Long.class, StreamClassUtil.getArithmaticType(Long.class, Long.class));
            Assert.assertEquals(Double.class, StreamClassUtil.getArithmaticType(long.class, Double.class));
            Assert.assertEquals(Double.class, StreamClassUtil.getArithmaticType(Long.class, double.class));
            Assert.assertEquals(Float.class, StreamClassUtil.getArithmaticType(long.class, Float.class));
            Assert.assertEquals(Float.class, StreamClassUtil.getArithmaticType(Long.class, float.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getArithmaticType(long.class, BigDecimal.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getArithmaticType(Long.class, BigDecimal.class));
            
            Assert.assertEquals(Float.class, StreamClassUtil.getArithmaticType(float.class, Float.class));
            Assert.assertEquals(Float.class, StreamClassUtil.getArithmaticType(Float.class, Float.class));
            Assert.assertEquals(Double.class, StreamClassUtil.getArithmaticType(float.class, Double.class));
            Assert.assertEquals(Double.class, StreamClassUtil.getArithmaticType(Float.class, double.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getArithmaticType(float.class, BigDecimal.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getArithmaticType(Float.class, BigDecimal.class));
            
            Assert.assertEquals(Double.class, StreamClassUtil.getArithmaticType(double.class, Double.class));
            Assert.assertEquals(Double.class, StreamClassUtil.getArithmaticType(Double.class, Double.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getArithmaticType(double.class, BigDecimal.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getArithmaticType(Double.class, BigDecimal.class));
            
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getArithmaticType(BigDecimal.class, BigDecimal.class));
            Assert.assertTrue(true);
        }
        catch (IllegalDataTypeException e)
        {
            fail();
        }
        
    }
    
    @Test
    public void testGetCommonType()
    {
        List<Class< ? >> list = Lists.newArrayList();
        try
        {
            StreamClassUtil.getCommonType(null);    
            fail();
        }
        catch (IllegalDataTypeException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        { 
            StreamClassUtil.getCommonType(list);
            fail();
        }
        catch (IllegalDataTypeException e)
        {
            Assert.assertTrue(true);
        }

        try
        {
            list.clear();
            list.add(boolean.class);
            Assert.assertEquals(Boolean.class, StreamClassUtil.getCommonType(list));       
            list.add(Boolean.class);
            Assert.assertEquals(Boolean.class, StreamClassUtil.getCommonType(list));      
        }
        catch (IllegalDataTypeException e)
        {
            fail();
        }
        
        try
        {
            list.clear();
            list.add(boolean.class);
            list.add(String.class);           
            Assert.assertEquals(Boolean.class, StreamClassUtil.getCommonType(list));      
            fail();
        }
        catch (IllegalDataTypeException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            list.clear();
            list.add(String.class);
            Assert.assertEquals(String.class, StreamClassUtil.getCommonType(list));       
            list.add(String.class);
            Assert.assertEquals(String.class, StreamClassUtil.getCommonType(list));      
        }
        catch (IllegalDataTypeException e)
        {
            fail();
        }
        
        try
        {
            list.clear();
            list.add(String.class);
            list.add(int.class);           
            Assert.assertEquals(String.class, StreamClassUtil.getCommonType(list));      
            fail();
        }
        catch (IllegalDataTypeException e)
        {
            Assert.assertTrue(true);
        }
        
        
        try
        {
            list.clear();
            list.add(int.class);
            Assert.assertEquals(Integer.class, StreamClassUtil.getCommonType(list));       
            list.add(Integer.class);
            Assert.assertEquals(Integer.class, StreamClassUtil.getCommonType(list));      
            list.add(long.class);
            Assert.assertEquals(Long.class, StreamClassUtil.getCommonType(list));  
            list.add(Float.class);
            Assert.assertEquals(Float.class, StreamClassUtil.getCommonType(list));  
            list.add(double.class);
            Assert.assertEquals(Double.class, StreamClassUtil.getCommonType(list));
            list.add(BigDecimal.class);
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getCommonType(list));  
        }
        catch (IllegalDataTypeException e)
        {
            fail();
        }
        
        try
        {
            list.clear();
            list.add(int.class);
            list.add(Integer.class);  
            list.add(String.class); 
            Assert.assertEquals(Integer.class, StreamClassUtil.getCommonType(list));      
            fail();
        }
        catch (IllegalDataTypeException e)
        {
            Assert.assertTrue(true);
        }
    }
    
    @Test
    public void testGetNumbericValueForType()
    {
        try
        {
            StreamClassUtil.getNumbericValueForType(null, Integer.class);    
            fail();
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        { 
            StreamClassUtil.getNumbericValueForType(new Integer(1), null);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertTrue(true);
        }
        
        
        try
        {
            Assert.assertEquals(new Integer(1), StreamClassUtil.getNumbericValueForType(new Integer(1), Integer.class));   
            Assert.assertEquals(new Long(1), StreamClassUtil.getNumbericValueForType(new Integer(1), Long.class));
            Assert.assertEquals(new Double(1), StreamClassUtil.getNumbericValueForType(new Integer(1), Double.class));
            Assert.assertEquals(new Float(1), StreamClassUtil.getNumbericValueForType(new Integer(1), Float.class));
            
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }
        
    }
    
    @Test
    public void testGetBigDecimalValue()
    {
        try
        {
            StreamClassUtil.getBigDecimalValue(null);    
            fail();
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertTrue(true);
        }
        
        
        try
        {
            Assert.assertEquals(new BigDecimal(1), StreamClassUtil.getBigDecimalValue(new BigDecimal(1)));    
            Assert.assertEquals(new BigDecimal(1.0d), StreamClassUtil.getBigDecimalValue(new Float(1)));  
            Assert.assertEquals(new BigDecimal(1.0d), StreamClassUtil.getBigDecimalValue(new Double(1)));  
            Assert.assertEquals(new BigDecimal(1), StreamClassUtil.getBigDecimalValue(new Integer(1)));  
            Assert.assertEquals(new BigDecimal(1), StreamClassUtil.getBigDecimalValue(new Long(1)));  
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }
    }
    
    @Test
    public void testIsFloatingPointNumber()
    {
        Assert.assertEquals(true, StreamClassUtil.isFloatingPointNumber(new Float(1.0f)));
        Assert.assertEquals(true, StreamClassUtil.isFloatingPointNumber(new Double(1.0d)));
        
        Assert.assertEquals(false, StreamClassUtil.isFloatingPointNumber(new Integer(1)));
        Assert.assertEquals(false, StreamClassUtil.isFloatingPointNumber(new Long(1)));
        Assert.assertEquals(false, StreamClassUtil.isFloatingPointNumber(new BigDecimal(1.0f)));
    }
    
    @Test
    public void testGetCompareType()
    {
        try
        {
            Assert.assertEquals(String.class, StreamClassUtil.getCompareType(String.class, String.class));
            Assert.assertEquals(Boolean.class, StreamClassUtil.getCompareType(boolean.class, Boolean.class));
        }
        catch (IllegalDataTypeException e)
        {
            fail();
        }

        try
        {
            Assert.assertEquals(Integer.class, StreamClassUtil.getCompareType(String.class, Integer.class));
            fail();
        }
        catch (IllegalDataTypeException e)
        {
            Assert.assertTrue(true);
        }
        
        try
        {
            Assert.assertEquals(Integer.class, StreamClassUtil.getCompareType(int.class, Integer.class));
            Assert.assertEquals(Long.class, StreamClassUtil.getCompareType(long.class, Integer.class));
            Assert.assertEquals(Float.class, StreamClassUtil.getCompareType(float.class, Integer.class));
            Assert.assertEquals(Double.class, StreamClassUtil.getCompareType(double.class, Integer.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getCompareType(BigDecimal.class, Integer.class));
            
            Assert.assertEquals(Long.class, StreamClassUtil.getCompareType(long.class, Long.class));
            Assert.assertEquals(Float.class, StreamClassUtil.getCompareType(float.class, Long.class));
            Assert.assertEquals(Double.class, StreamClassUtil.getCompareType(double.class, Long.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getCompareType(BigDecimal.class, Long.class));
            
            Assert.assertEquals(Float.class, StreamClassUtil.getCompareType(float.class, Float.class));
            Assert.assertEquals(Double.class, StreamClassUtil.getCompareType(double.class, Float.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getCompareType(BigDecimal.class, Float.class));
            
            Assert.assertEquals(Double.class, StreamClassUtil.getCompareType(double.class, Double.class));
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getCompareType(BigDecimal.class, Double.class));
            
            Assert.assertEquals(BigDecimal.class, StreamClassUtil.getCompareType(BigDecimal.class, BigDecimal.class));
        }
        catch (IllegalDataTypeException e)
        {
            fail();
        }
        
    }
    
    @Test
    public void testIsBoolean()
    {
        Assert.assertEquals(true, StreamClassUtil.isBoolean(boolean.class));
        Assert.assertEquals(true, StreamClassUtil.isBoolean(Boolean.class));
        Assert.assertEquals(false, StreamClassUtil.isBoolean(int.class)); 
        Assert.assertEquals(false, StreamClassUtil.isBoolean(Integer.class));
        Assert.assertEquals(false, StreamClassUtil.isBoolean(long.class));
        Assert.assertEquals(false, StreamClassUtil.isBoolean(Long.class)); 
        Assert.assertEquals(false, StreamClassUtil.isBoolean(double.class));
        Assert.assertEquals(false, StreamClassUtil.isBoolean(Double.class)); 
        Assert.assertEquals(false, StreamClassUtil.isBoolean(float.class));
        Assert.assertEquals(false, StreamClassUtil.isBoolean(Float.class)); 
        Assert.assertEquals(false, StreamClassUtil.isBoolean(BigDecimal.class));
        Assert.assertEquals(false, StreamClassUtil.isBoolean(Date.class));
        Assert.assertEquals(false, StreamClassUtil.isBoolean(Time.class));
        Assert.assertEquals(false, StreamClassUtil.isBoolean(Timestamp.class));
    }
    
    @Test
    public void testIsDate()
    {
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(boolean.class));
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(Boolean.class));
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(int.class));
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(Integer.class));
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(long.class));
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(Long.class));
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(double.class));
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(Double.class));
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(float.class));
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(Float.class));
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(BigDecimal.class));
        Assert.assertEquals(true, StreamClassUtil.isDateOrTimestamp(Date.class));
        Assert.assertEquals(false, StreamClassUtil.isDateOrTimestamp(Time.class));
        Assert.assertEquals(true, StreamClassUtil.isDateOrTimestamp(Timestamp.class));
    }
    
}
