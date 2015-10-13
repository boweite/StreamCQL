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

package com.huawei.streaming.cql.executor.planserde.views;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.huawei.streaming.api.AnnotationUtils;
import com.huawei.streaming.api.opereators.KafkaInputOperator;
import com.huawei.streaming.config.StreamingConfig;
import com.huawei.streaming.cql.ConstInTestCase;
import com.huawei.streaming.cql.exception.ApplicationBuildException;
import com.huawei.streaming.cql.executor.operatorinfocreater.InputInfoCreator;
import com.huawei.streaming.cql.executor.operatorinfocreater.OperatorInfoCreator;
import static org.junit.Assert.assertTrue;

/**
 * 申明测试类
 *
 */
public class AnnotationUtilsTest
{
    
    /**
     * 测试bean中的字段申明
     *
     * @throws ApplicationBuildException 异常信息
     */
    @Test
    public void testGetAnnotationsToConfig()
        throws ApplicationBuildException
    {
        AnnotationTestBean bean = new AnnotationTestBean(ConstInTestCase.I_1, "a", ConstInTestCase.I_2);
        Map<String, String> config = AnnotationUtils.getAnnotationsToConfig(bean);
        assertTrue(config.get("test.id").equals(String.valueOf(bean.getId())));
        assertTrue(config.get("test.name").equals(bean.getName()));
    }
    
    /**
     * 测试输入父类上的申明
     *
     * @throws Exception 异常信息
     */
    @Test
    public void testInputCreator()
        throws Exception
    {
        Class< ? extends OperatorInfoCreator> clazz =
            AnnotationUtils.getOperatorCreatorAnnotation(KafkaInputOperator.class.getSuperclass());
        assertTrue(clazz == InputInfoCreator.class);
    }
    
    /**
     * 测试config对象的转换
     *
     * @throws ApplicationBuildException 异常信息
     */
    @Test
    public void testConfigConvert()
        throws ApplicationBuildException
    {
        Map<String, String> conf = new HashMap<String, String>();
        conf.put(StreamingConfig.OPERATOR_KAFKA_ZOOKEEPERS, "localhost:2181,158.1.130.21:2181");
        conf.put(StreamingConfig.OPERATOR_KAFKA_ZKSYNCTIME, "20000");
        conf.put(StreamingConfig.OPERATOR_KAFKA_ZKSESSIONTIMEOUT, "20000");
        conf.put(StreamingConfig.OPERATOR_KAFKA_GROUPID, "gidkpi_1_1");
        conf.put(StreamingConfig.OPERATOR_KAFKA_MESSAGESERIALIZERCLASS, "kafka.serializer.StringEncoder");
        conf.put(StreamingConfig.SERDE_SIMPLESERDE_SEPARATOR, ",");
        conf.put(StreamingConfig.OPERATOR_KAFKA_TOPIC, "topic2");
        KafkaInputOperator kop = new KafkaInputOperator("test", 1);
        AnnotationUtils.setConfigToObject(kop, conf);
        assertTrue(kop.getZookeepers().equals("localhost:2181,158.1.130.21:2181"));
        assertTrue(kop.getZkSyncTime() == ConstInTestCase.I_20000);
        assertTrue(kop.getZkSessionTimeout() == ConstInTestCase.I_20000);
        assertTrue(kop.getGroupId().equals("gidkpi_1_1"));
        assertTrue(kop.getMessageSerializerClass().equals("kafka.serializer.StringEncoder"));
        assertTrue(kop.getTopic().equals("topic2"));
    }
}
