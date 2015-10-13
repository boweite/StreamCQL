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
package com.huawei.streaming.cql.executor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.huawei.streaming.api.opereators.WindowCommons;
import com.huawei.streaming.cql.executor.windowcreater.KeepAllWindowCreator;
import com.huawei.streaming.cql.executor.windowcreater.LengthBatchWindowCreator;
import com.huawei.streaming.cql.executor.windowcreater.LengthSlideWindowCreator;
import com.huawei.streaming.cql.executor.windowcreater.TimeBatchWindowCreator;
import com.huawei.streaming.cql.executor.windowcreater.TimeSlideWindowCreator;
import com.huawei.streaming.cql.executor.windowcreater.WindowCreator;
import com.huawei.streaming.window.IWindow;
import com.huawei.streaming.window.KeepAllWindow;
import com.huawei.streaming.window.LengthBatchWindow;
import com.huawei.streaming.window.LengthSlideWindow;
import com.huawei.streaming.window.TimeBatchWindow;
import com.huawei.streaming.window.TimeSlideWindow;
/**
 * 系统window类函数的注册
 * 之所以将window的函数注册独立开来，
 * 是由于window的位置十分明显，容易辨别，
 * 不像UDAF，UDTF，UDF函数之类，难以区分。
 * 
 */
public class WindowRegistry extends WindowCommons
{
    /**
     * 窗口名称和窗口底层具体实现映射关系
     */
    private static final Map<String, WindowInfo> WINDOW_FUNCTIONS =
        Collections.synchronizedMap(new LinkedHashMap<String, WindowInfo>());
    
    static
    {
        /**
         * 当前系统内没有now的窗口
         * select * from S 就没有窗口
         * 
         * 另外，只要CQL语句中没有显示的定义窗口，那么就没有窗口
         **/
        registerNativeSlideWindow(new WindowInfo(KEEPALL_WINDOW, KeepAllWindow.class, KeepAllWindowCreator.class, ""));
        
        registerNativeSlideWindow(new WindowInfo(TIME_SLIDE_WINDOW, TimeSlideWindow.class,
            TimeSlideWindowCreator.class, "length,[excludeNow]"));
        registerNativeBatchWindow(new WindowInfo(TIME_BATCH_WINDOW, TimeBatchWindow.class,
            TimeBatchWindowCreator.class, "length,[excludeNow]"));
        registerNativeSlideWindow(new WindowInfo(LENGTH_SLIDE_WINDOW, LengthSlideWindow.class,
            LengthSlideWindowCreator.class, "length,[excludeNow]"));
        registerNativeBatchWindow(new WindowInfo(LENGTH_BATCH_WINDOW, LengthBatchWindow.class,
            LengthBatchWindowCreator.class, "length,[excludeNow]"));

    }
    
    /**
     * 注册窗口
     * @param winInfo 窗口信息
     */
    public static void registerWindow(WindowInfo winInfo)
    {
        winInfo.setNative(false);
        winInfo.setIsSlide(null);
        WINDOW_FUNCTIONS.put(winInfo.getWidowName(), winInfo);
    }
    
    /**
     * 注册窗口
     * @param winInfo 窗口信息
     */
    private static void registerNativeSlideWindow(WindowInfo winInfo)
    {
        winInfo.setNative(true);
        winInfo.setIsSlide(true);
        WINDOW_FUNCTIONS.put(winInfo.getWidowName(), winInfo);
    }
    
    /**
     * 注册窗口
     * @param winInfo 窗口信息
     */
    private static void registerNativeBatchWindow(WindowInfo winInfo)
    {
        winInfo.setNative(true);
        winInfo.setIsSlide(false);
        WINDOW_FUNCTIONS.put(winInfo.getWidowName(), winInfo);
    }
    
    /**
     * 获取窗口信息
     * @param alias window别名
     * @return 窗口信息
     */
    public static WindowInfo getWindowInfo(String alias)
    {
        return WINDOW_FUNCTIONS.get(alias);
    }
    
    /**
     * 根据window的别名，获取窗口实例类
     * 
     * @param alias window别名
     * @return 窗口实例类
     */
    public static Class< ? extends WindowCreator> getWindowCreatorByAlias(String alias)
    {
        return WINDOW_FUNCTIONS.get(alias).getCreatorClass();
    }
    
    /**
     * 根据窗口函数短名称获取该窗口所在类
     * 
     * @param windowName 函数短名称
     * @return 窗口类
     */
    public static Class< ? extends IWindow> getWindowClassByName(String windowName)
    {
        return WINDOW_FUNCTIONS.get(windowName).getInstanceClass();
    }
    
    /**
     * 根据窗口类获取窗口所在函数短名称
     * 主要在IDE这边用到
     * 
     * @param clazz 窗口类
     * @return 窗口短名称
     */
    public static String getWindowNameByClass(Class< ? extends IWindow> clazz)
    {
        for (Entry<String, WindowInfo> et : WINDOW_FUNCTIONS.entrySet())
        {
            if (clazz == et.getValue().getInstanceClass())
            {
                return et.getKey();
            }
        }
        return null;
    }
    
    /**
     * 根据窗口类的全名称或者窗口函数短名称
     * 主要是IDE在用
     * 
     * @param clazz 窗口类的字符串形式
     * @return 函数短名称
     */
    public static String getWindowNameByClass(String clazz)
    {
        for (Entry<String, WindowInfo> et : WINDOW_FUNCTIONS.entrySet())
        {
            if (et.getValue().getInstanceClass().toString().equals(clazz))
            {
                return et.getKey();
            }
        }
        return null;
    }
}
