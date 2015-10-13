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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取一年的第几个星期
 * 
 */
@UDFAnnotation(name = "weekofyear")
public class WeekOfYear extends UDF
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -5028072424388328846L;

    private static final Logger LOG = LoggerFactory.getLogger(WeekOfYear.class);

    private SimpleDateFormat formatter1 = null;
    
    private SimpleDateFormat formatter2 = null;
    
    private Calendar calendar = null;

    /**
     * <默认构造函数>
     * @param config udf函数中需要的参数，这些参数要在cql中通过全局变量进行设置。
     */
    public WeekOfYear(Map<String, String> config)
    {
        super(config);
    }
    
    /**
     * 计算函数
     * @param dateString the dateString in the format of "yyyy-MM-dd HH:mm:ss" or yyyy-MM-dd".
     * @return week number of year. null if the dateString is not a valid date string
     */
    public Integer evaluate(String dateString)
    {
        if (dateString == null) {
            return null;
        }

        initDateFormat();
        try {
            Date date = null;
            if (dateString.length() > UDFConstants.DATE_FORMAT.length())
            {
                date = formatter1.parse(dateString);
            }
            else
            {
                date = formatter2.parse(dateString);
            }            
            calendar.setTime(date);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        } catch (ParseException e) {
            LOG.warn(EVALUATE_IGNORE_MESSAGE);
            return null;
        }
    }

    /**
     * 返回指定日期属于全年的周数
     * @param d date类型时间
     * @return 该日期在全年中所在的周数
     */
    public Integer evaluate(java.sql.Date d) {
        if (d == null) {
            return null;
        }
        initDateFormat();
        calendar.setTime(d);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 返回指定日期属于全年的周数
     * @param t timestamp类型时间参数
     * @return 该日期在全年中所在的周数
     */
    public Integer evaluate(Timestamp t) {
        if (t == null) {
            return null;
        }
        initDateFormat();
        calendar.setTime(t);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    private  void initDateFormat()
    {
        if (formatter1 != null)
        {
            return;
        }

        formatter1 = new SimpleDateFormat(UDFConstants.TIMESTAMP_FORMAT);
        formatter2 = new SimpleDateFormat(UDFConstants.DATE_FORMAT);
        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
            /*
             * 设置时间严格匹配
             */
        formatter1.setLenient(false);
        formatter2.setLenient(false);
    }

}
