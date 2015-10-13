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

package com.huawei.streaming.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.huawei.streaming.exception.ErrorCode;
import com.huawei.streaming.exception.StreamingException;
import com.huawei.streaming.udfs.UDFConstants;

/**
 * Streaming数据类型
 *
 */
public enum StreamingDataType
{
    
    /**
     * 字符串数据类型
     */
    STRING("STRING", String.class, null)
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public Object createValue(String value)
            throws StreamingException
        {
            if (Strings.isNullOrEmpty(value))
            {
                return null;
            }
            
            return String.valueOf(value);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toStringValue(Object value)
            throws StreamingException
        {
            if (null == value)
            {
                return null;
            }
            return value.toString();
        }
    },
    
    /**
     * Integer数据类型
     */
    INT("INT", Integer.class, int.class)
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public Object createValue(String value)
            throws StreamingException
        {
            if (Strings.isNullOrEmpty(value))
            {
                return null;
            }
            
            try
            {
                return Integer.valueOf(value);
            }
            catch (Exception e)
            {
                LOG.warn("Failed to create {} instance.", getWrapperClass().getName());
                throw new StreamingException("Failed to create " + getWrapperClass().getName() + " instance.");
            }
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toStringValue(Object value)
            throws StreamingException
        {
            if (null == value)
            {
                return null;
            }
            return value.toString();
        }
    },
    
    /**
     * long数据类型
     */
    LONG("LONG", Long.class, long.class)
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public Object createValue(String value)
            throws StreamingException
        {
            if (Strings.isNullOrEmpty(value))
            {
                return null;
            }
            
            try
            {
                return Long.valueOf(value);
            }
            catch (Exception e)
            {
                LOG.warn("Failed to create {} instance.", getWrapperClass().getName());
                throw new StreamingException("Failed to create " + getWrapperClass().getName() + " instance.");
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toStringValue(Object value)
            throws StreamingException
        {
            if (null == value)
            {
                return null;
            }
            return value.toString();
        }
    },
    
    /**
     * float数据类型
     */
    FLOAT("FLOAT", Float.class, float.class)
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public Object createValue(String value)
            throws StreamingException
        {
            if (Strings.isNullOrEmpty(value))
            {
                return null;
            }
            
            try
            {
                return Float.valueOf(value);
            }
            catch (Exception e)
            {
                LOG.warn("Failed to create {} instance.", getWrapperClass().getName());
                throw new StreamingException("Failed to create " + getWrapperClass().getName() + " instance.");
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toStringValue(Object value)
            throws StreamingException
        {
            if (null == value)
            {
                return null;
            }
            return value.toString();
        }
    },
    
    /**
     * double数据类型
     */
    DOUBLE("DOUBLE", Double.class, double.class)
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public Object createValue(String value)
            throws StreamingException
        {
            if (Strings.isNullOrEmpty(value))
            {
                return null;
            }
            
            try
            {
                return Double.valueOf(value);
            }
            catch (Exception e)
            {
                LOG.warn("Failed to create {} instance.", getWrapperClass().getName());
                throw new StreamingException("Failed to create " + getWrapperClass().getName() + " instance.");
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toStringValue(Object value)
            throws StreamingException
        {
            if (null == value)
            {
                return null;
            }
            return value.toString();
        }
    },
    
    /**
     * boolean数据类型
     */
    BOOLEAN("BOOLEAN", Boolean.class, boolean.class)
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public Object createValue(String value)
            throws StreamingException
        {
            /*
             * 和Hive保持一致
             */
             if (value == null)
             {
                 return null;
             }

             if (CONST_TRUE.equalsIgnoreCase(value))
             {
                 return true;
             }
             else if (CONST_FALSE.equalsIgnoreCase(value))
             {
                 return false;
             }
             else
             {
                 LOG.debug("Data not in the boolean data type range so converted to null.");
                 return null;
             }
         }

         /**
          * {@inheritDoc}
          */
         @Override
         public String toStringValue(Object value)
          throws StreamingException
         {
             if (null == value)
             {
                 return null;
             }
             return value.toString();
         }
     },

    /**
     * 时间戳类型
     */
    TIMESTAMP("TIMESTAMP", Timestamp.class, null)
    {
        private final SimpleDateFormat stringFormatter = new SimpleDateFormat(UDFConstants.TIMESTAMP_MSTIME_FORMAT);
        
        /*
         * timestamp类型允许输入带毫秒，纳秒的数字
         * 所以格式精确到秒级别。
         */
        private final SimpleDateFormat timestampFormatter = new SimpleDateFormat(UDFConstants.TIMESTAMP_FORMAT);
        {
            timestampFormatter.setLenient(false);
            stringFormatter.setLenient(false);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Object createValue(String value)
            throws StreamingException
        {
            if (Strings.isNullOrEmpty(value))
            {
                return null;
            }
            
            try
            {
                //先进行时间校验，防止输入超过日期范围的时间，比如12月32日。
                timestampFormatter.parse(value);
                return Timestamp.valueOf(value);
            }
            catch (Exception e)
            {
                LOG.warn("Failed to create {} instance.", getWrapperClass().getName());
                throw new StreamingException("Failed to create " + getWrapperClass().getName() + " instance.");
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toStringValue(Object value)
            throws StreamingException
        {
            if (null == value)
            {
                return null;
            }
            try
            {
                return stringFormatter.format(value);
            }
            catch (IllegalArgumentException e)
            {
                LOG.warn("Failed to format timestamp value to string type.", e);
                throw new StreamingException("Failed to format timestamp value to string type.", e);
            }
        }
    },
    
    /**
     * 日期类型
     */
    DATE("DATE", Date.class, null)
    {
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(UDFConstants.DATE_FORMAT);
        {
            dateFormatter.setLenient(false);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Object createValue(String value)
            throws StreamingException
        {
            if (Strings.isNullOrEmpty(value))
            {
                return null;
            }
            
            try
            {
                dateFormatter.parse(value);
                return Date.valueOf(value);
            }
            catch (Exception e)
            {
                LOG.warn("Failed to create {} instance.", getWrapperClass().getName());
                throw new StreamingException("Failed to create " + getWrapperClass().getName() + " instance.");
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toStringValue(Object value)
            throws StreamingException
        {
            if (null == value)
            {
                return null;
            }
            //date类型的输出只有一种格式，所以无需格式化。
            return value.toString();
        }
    },
    
    /**
     * 时间类型
     */
    TIME("TIME", Time.class, null)
    {
        private final SimpleDateFormat timeFormatter = new SimpleDateFormat(UDFConstants.TIME_FORMAT);
        {
            timeFormatter.setLenient(false);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Object createValue(String value)
            throws StreamingException
        {
            if (Strings.isNullOrEmpty(value))
            {
                return null;
            }
            
            try
            {
                timeFormatter.parse(value);
                return Time.valueOf(value);
            }
            catch (Exception e)
            {
                //序列化反序列化异常使用warn级别，因为仅仅会导致当前数据丢掉，不会导致进程退出
                LOG.warn("Failed to create {} instance.", getWrapperClass().getName());
                throw new StreamingException("Failed to create " + getWrapperClass().getName() + " instance.");
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toStringValue(Object value)
            throws StreamingException
        {
            if (null == value)
            {
                return null;
            }
            
            //time类型的输出只有一种格式，无需格式化
            return value.toString();
        }
    },
    
    /**
     * decimal类型
     */
    DECIMAL("DECIMAL", BigDecimal.class, null)
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public Object createValue(String value)
            throws StreamingException
        {
            if (Strings.isNullOrEmpty(value))
            {
                return null;
            }
            
            try
            {
                return new BigDecimal(value);
            }
            catch (Exception e)
            {
                LOG.warn("Failed to create {} instance.", getWrapperClass().getName());
                throw new StreamingException("Failed to create " + getWrapperClass().getName() + " instance.");
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toStringValue(Object value)
        {
            if (null == value)
            {
                return null;
            }
            return value.toString();
        }
    };
    
    private static final Logger LOG = LoggerFactory.getLogger(StreamingDataType.class);

    /**
     * 常量true
     */
    private static final String CONST_TRUE = "true";

    /**
     * 常量false
     */
    private static final String CONST_FALSE = "false";


    /**
     * 数据类型在CQL中的描述
     * 所以该描述内容不允许随意修改
     */
    private String desc;
    
    /**
     * 包装类型
     * 包装类型不能为空
     */
    private Class< ? > wrapperClass;
    
    /**
     * 原始类型
     */
    private Class< ? > innerClass;
    
    private StreamingDataType(String t, Class< ? > wrapperClazz, Class< ? > innerClazz)
    {
        this.desc = t;
        this.wrapperClass = wrapperClazz;
        this.innerClass = innerClazz;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public Class< ? > getWrapperClass()
    {
        return wrapperClass;
    }
    
    public Class< ? > getInnerClass()
    {
        return innerClass;
    }
    
    /**
     * 是否和当前枚举类型书类型相符
     *
     * @param clazz 待匹配的数据类型
     * @return 如果相符，返回true，否则返回false
     */
    private boolean isEqualDataType(Class< ? > clazz)
    {
        return clazz.equals(wrapperClass) || clazz.equals(innerClass);
    }
    
    /**
     * 通过Class获取数据类型
     * @param clazz 类名称
     * @return 数据类型
     * @throws com.huawei.streaming.exception.StreamingException 不支持的数据类型
     */
    public static StreamingDataType getDataType(Class< ? > clazz)
        throws StreamingException
    {
        for (StreamingDataType dataType : StreamingDataType.values())
        {
            if (dataType.isEqualDataType(clazz))
            {
                return dataType;
            }
        }
        
        StreamingException exception =
            new StreamingException(ErrorCode.SEMANTICANALYZE_UNSUPPORTED_DATATYPE, clazz.getName());
        LOG.warn("Unsupport data type.", exception);
        throw exception;
    }
    
    /**
     * 创建对应数据类型实例，如果为空，返回null
     *
     * @param value 字符串数据
     * @return 指定数据类型的实例
     * @throws StreamingException 数据实例创建异常
     */
    public abstract Object createValue(String value)
        throws StreamingException;
    
    /**
     * 将原始数据类型转为对应的数据类型字符串形式
     * @param value 数据值
     * @return 数据值对应的字符串形式
     * @throws StreamingException 数据实例格式化异常
     */
    public abstract String toStringValue(Object value)
        throws StreamingException;
    
}
