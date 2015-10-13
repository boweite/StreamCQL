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
package com.huawei.streaming.storm;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

import com.google.common.base.Strings;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 */
public class SystemPropertyBackupTest
{

    private Properties backupSecurityConf = new Properties();

    @Test
    public void proptertyTest1()
    {
        SystemPropertyBackupTest tt = new SystemPropertyBackupTest();
        System.setProperty("dfa", "abc");
        tt.backupSecurityProperties("dfa");
        System.setProperty("dfa", "111");
        assertEquals(System.getProperty("dfa"), "111");
        tt.restoreSecurityProperties("dfa");
        assertEquals(System.getProperty("dfa"), "abc");


        System.clearProperty("dfa");
        System.setProperty("dfa", "");
        tt.backupSecurityProperties("dfa");
        System.setProperty("dfa", "222");
        assertEquals(System.getProperty("dfa"), "222");
        tt.restoreSecurityProperties("dfa");
        assertEquals(System.getProperty("dfa"), null);


        System.clearProperty("dfa");
        tt.backupSecurityProperties("dfa");
        System.setProperty("dfa", "333");
        assertEquals(System.getProperty("dfa"), "333");
        tt.restoreSecurityProperties("dfa");
        assertEquals(System.getProperty("dfa"), null);

    }

    private void backupSecurityProperties(String propertyKey)
    {
        if (System.getProperty(propertyKey) == null)
        {
            backupSecurityConf.put(propertyKey, "");
        }
        else
        {
            backupSecurityConf.put(propertyKey, System.getProperty(propertyKey));
        }
    }

    private void restoreSecurityProperties(String propertyKey)
    {
        if (Strings.isNullOrEmpty(backupSecurityConf.getProperty(propertyKey)))
        {
            System.clearProperty(propertyKey);
        }
        else
        {
            System.setProperty(propertyKey, backupSecurityConf.getProperty(propertyKey));
        }
    }
}
