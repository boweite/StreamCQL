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

package com.huawei.streaming.cql.builder.operatorconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.streaming.api.AnnotationUtils;
import com.huawei.streaming.api.opereators.BaseDataSourceOperator;
import com.huawei.streaming.api.opereators.DataSourceOperator;
import com.huawei.streaming.api.opereators.Operator;
import com.huawei.streaming.cql.exception.ApplicationBuildException;
import com.huawei.streaming.cql.mapping.InputOutputOperatorMapping;
import com.huawei.streaming.exception.ErrorCode;

/**
 * 数据源转换
 *
 */
public class DataSourceConverter implements OperatorConverter
{
    private static final Logger LOG = LoggerFactory.getLogger(DataSourceConverter.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(Operator op)
    {
        return op instanceof DataSourceOperator;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public BaseDataSourceOperator convert(Operator op)
        throws ApplicationBuildException
    {
        DataSourceOperator datasourceOperator = (DataSourceOperator)op;
        String rss = InputOutputOperatorMapping.getAPIOperatorByPlatform(datasourceOperator.getDataSourceClassName());
        if (null == rss)
        {
            return datasourceOperator;
        }
        
        return convertToPrivateDataSource(datasourceOperator, rss);
    }
    
    /**
     * 将通用的数据源转化为私有的数据源
     *
     * @param dataSourceOperator 通用的数据源算子
     * @param dataSourceClass 私有的数据源所在类
     * @return 转换之后的私有数据源
     * @throws ApplicationBuildException 转换异常
     */
    private BaseDataSourceOperator convertToPrivateDataSource(DataSourceOperator dataSourceOperator,
        String dataSourceClass)
        throws ApplicationBuildException
    {
        ApplicationBuildException exception =
            new ApplicationBuildException(ErrorCode.SEMANTICANALYZE_DATASOURCE_UNKNOWN, dataSourceClass);
        LOG.error("Unsupport datasource type.", exception);
        throw exception;
    }
}
