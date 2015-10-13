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

package com.huawei.streaming.udfs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取对应时间所在小时
 * 
 */
@UDFAnnotation(name = "hour")
public class Hour extends UDF
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -4165451004498228995L;

    private static final Logger LOG = LoggerFactory.getLogger(Hour.class);

    private SimpleDateFormat formatter1 = null;
    
    private SimpleDateFormat formatter2 = null;
    
    private Calendar calendar = null;
    
    /**
     * <默认构造函数>
     * @param config udf函数中需要的参数，这些参数要在cql中通过全局变量进行设置。
     */
    public Hour(Map<String, String> config)
    {
        super(config);
    }
    
    /**
     * 计算函数
     * @param dateString the dateString in the format of "yyyy-MM-dd HH:mm:ss" or HH:mm:ss".
     * @return an int from 0 to 23. null if the dateString is not a valid date string
     */
    public Integer evaluate(String dateString)
    {
        if (dateString == null)
        {
            return null;
        }
        
        if (formatter1 == null)
        {
            formatter1 = new SimpleDateFormat(UDFConstants.TIMESTAMP_FORMAT);
            formatter2 = new SimpleDateFormat(UDFConstants.TIME_FORMAT);
            calendar = Calendar.getInstance();
            /*
             * 设置时间严格匹配
             */
            formatter1.setLenient(false);
            formatter2.setLenient(false);
        }
        
        try
        {
            Date date = null;
            if (dateString.length() > UDFConstants.TIME_FORMAT.length())
            {
                date = formatter1.parse(dateString);
            }
            else
            {
                date = formatter2.parse(dateString);
            }
            calendar.setTime(date);
            return calendar.get(Calendar.HOUR_OF_DAY);
        }
        catch (ParseException e)
        {
            LOG.warn(EVALUATE_IGNORE_MESSAGE);
            return null;
        }
    }
}
