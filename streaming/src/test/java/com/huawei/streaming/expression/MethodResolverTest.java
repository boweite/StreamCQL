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
/*
 * 文 件 名:  MethodResolverTest.java
 * 版 本 号:  V1.0.0
 * 版    权:  Huawei Technologies Co., Ltd. Copyright 1988-2008,  All rights reserved
 * 描    述:  <描述>
 * 作    者:  z00221388
 * 创建日期:  2013-6-24
 */
package com.huawei.streaming.expression;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

/**
 * MethodResolver单元测试类
 */
public class MethodResolverTest
{
    
    /**
     * 测试用例一：该用例测出151行一个问题 if (!method.getName().equals(methodName))
     * 构造不需要转型就可以匹配的函数（绝对匹配）
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod1()
        throws ClassNotFoundException, NoSuchMethodException
    {
        //构造测试数据
        Class< ? > declareClass = Class.forName("java.lang.Math");
        String methodName = "abs";
        Class< ? >[] paramTypes = new Class< ? >[] {int.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = declareClass.getMethod(methodName, int.class);
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 测试用例二：测试基本类型的包装类型的匹配
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常 
     */
    @Test
    public void testResolveMethod2()
        throws ClassNotFoundException, NoSuchMethodException
    {
        //构造测试数据
        Class< ? > declareClass = Class.forName("java.lang.Math");
        String methodName = "abs";
        Class< ? >[] paramTypes = new Class< ? >[] {Integer.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = declareClass.getMethod(methodName, int.class);
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 测试用例三：没有匹配的函数
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod3()
        throws ClassNotFoundException, NoSuchMethodException
    {
        //构造测试数据
        Class< ? > declareClass = Class.forName("java.lang.Math");
        String methodName = "abd";
        Class< ? >[] paramTypes = new Class< ? >[] {Integer.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = null;
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 该用例测出一个问题，将byte错误匹配为long
     * 测试用例四：支持byte类型自动转型为int
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常 
     */
    @Test
    public void testResolveMethod4()
        throws ClassNotFoundException, NoSuchMethodException
    {
        //构造测试数据
        Class< ? > declareClass = Class.forName("java.lang.Math");
        String methodName = "abs";
        Class< ? >[] paramTypes = new Class< ? >[] {byte.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = declareClass.getMethod(methodName, int.class);
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 测试用例五：支持char类型自动转型为int
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod5()
        throws ClassNotFoundException, NoSuchMethodException
    {
        //构造测试数据
        Class< ? > declareClass = Class.forName("java.lang.Math");
        String methodName = "abs";
        Class< ? >[] paramTypes = new Class< ? >[] {char.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = declareClass.getMethod(methodName, int.class);
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 测试用例六：支持int类型自动转型为float
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod6()
        throws ClassNotFoundException, NoSuchMethodException
    {
        //构造测试数据
        Class< ? > declareClass = Class.forName("java.lang.Math");
        String methodName = "round";
        Class< ? >[] paramTypes = new Class< ? >[] {int.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = declareClass.getMethod(methodName, float.class);
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 测试用例七：支持int类型自动转型为float
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod7()
        throws ClassNotFoundException, NoSuchMethodException
    {
        //构造测试数据
        Class< ? > declareClass = Class.forName("java.lang.Math");
        String methodName = "round";
        Class< ? >[] paramTypes = new Class< ? >[] {long.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = declareClass.getMethod(methodName, float.class);
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 测试用例八：支持int类型自动转型为double
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod8()
        throws ClassNotFoundException, NoSuchMethodException
    {
        //构造测试数据
        Class< ? > declareClass = Class.forName("java.lang.Math");
        String methodName = "log";
        Class< ? >[] paramTypes = new Class< ? >[] {int.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = declareClass.getMethod(methodName, double.class);
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 测试用例九：支持float类型自动转型为double
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod9()
        throws ClassNotFoundException, NoSuchMethodException
    {
        //构造测试数据
        Class< ? > declareClass = Class.forName("java.lang.Math");
        String methodName = "log";
        Class< ? >[] paramTypes = new Class< ? >[] {float.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = declareClass.getMethod(methodName, double.class);
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 测试用例十：子类转为父类(String转为Object)
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod10()
        throws ClassNotFoundException, NoSuchMethodException
    {
        //构造测试数据
        Class< ? > declareClass = Class.forName("java.lang.String");
        String methodName = "valueOf";
        Class< ? >[] paramTypes = new Class< ? >[] {String.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = declareClass.getMethod(methodName, Object.class);
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 测试用例十一：int不能转换为Long，即实际参数类型为int，函数定义参数类型为Long，不能匹配该函数
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod11() throws ClassNotFoundException, NoSuchMethodException{
        //构造测试数据
        Class< ? > declareClass = Class.forName("com.huawei.streaming.expression.MethodTest");
        String methodName = "f";
        Class< ? >[] paramTypes = new Class< ? >[] {int.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = null;
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 测试用例十二：Integer不能转换为Long，即实际参数类型为Integer，函数定义参数类型为Long，不能匹配该函数
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod12() throws ClassNotFoundException, NoSuchMethodException{
        //构造测试数据
        Class< ? > declareClass = Class.forName("com.huawei.streaming.expression.MethodTest");
        String methodName = "f";
        Class< ? >[] paramTypes = new Class< ? >[] {Integer.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = null;
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    
    /**
     * 测试用例十三：包装类型测试，即实际类型为int，函数参数类型为Integer，能够匹配该函数
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod13() throws ClassNotFoundException, NoSuchMethodException{
        //构造测试数据
        Class< ? > declareClass = Class.forName("com.huawei.streaming.expression.MethodTest");
        String methodName = "f2";
        Class< ? >[] paramTypes = new Class< ? >[] {int.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = declareClass.getMethod(methodName, Integer.class);;
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
    /**
     * 测试用例十四：包装类型测试，即实际类型为Integer，函数参数类型为int，能够匹配该函数
     * @exception ClassNotFoundException 异常
     * @exception NoSuchMethodException 异常
     */
    @Test
    public void testResolveMethod14() throws ClassNotFoundException, NoSuchMethodException{
        //构造测试数据
        Class< ? > declareClass = Class.forName("com.huawei.streaming.expression.MethodTest");
        String methodName = "f3";
        Class< ? >[] paramTypes = new Class< ? >[] {Integer.class};
        
        //操作测试数据
        Method actualMethod = MethodResolver.resolveMethod(declareClass, methodName, paramTypes);
        
        //检验操作是否得到期望的结果
        Method expectedMethod = declareClass.getMethod(methodName, int.class);;
        Assert.assertEquals(expectedMethod, actualMethod);
    }
    
}

/**
 * 为测试构造的测试类
 */
class MethodTest
{
    
    public static void f(Long s)
    {
        
    }
    
    public static void f2(Integer s)
    {
        
    }
    
    public static void f3(int s)
    {
        
    }
    
}
