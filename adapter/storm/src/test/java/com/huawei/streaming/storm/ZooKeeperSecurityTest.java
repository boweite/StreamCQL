package com.huawei.streaming.storm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.huawei.streaming.config.StreamingConfig;
import com.huawei.streaming.exception.StreamingException;

/**
 * Streaming访问zookeeper安全测试
 */
public class ZooKeeperSecurityTest
{
    @Test
    public void testNoNSecurity()
        throws StreamingException
    {
        StreamingConfig config = new StreamingConfig();
        config.put(StreamingConfig.STREAMING_SECURITY_AUTHENTICATION,"None");

        StreamingSecurity zkSecurity = SecurityFactory.createSecurity(config);
        zkSecurity.initSecurity();
        
        assertEquals(System.getProperty(KerberosSecurity.ZOOKEEPER_AUTH_JASSCONF), null);
        assertEquals(System.getProperty(KerberosSecurity.ZOOKEEPER_AUTH_PRINCIPAL), null);
        zkSecurity.destroySecurity();
        assertEquals(System.getProperty(KerberosSecurity.ZOOKEEPER_AUTH_JASSCONF), null);
        assertEquals(System.getProperty(KerberosSecurity.ZOOKEEPER_AUTH_PRINCIPAL), null);
    }
    
    @Test
    public void testSecurity()
        throws StreamingException
    {
        StreamingConfig config = new StreamingConfig();
        config.put(StreamingConfig.STREAMING_SECURITY_AUTHENTICATION,"Kerberos");
        config.put(StreamingConfig.STREAMING_SECURITY_USER_PRINCIPAL, "ransom/streaming");
        config.put(StreamingConfig.STREAMING_SECURITY_KEYTAB_PATH, "d:\\t est\\abc.keytab");
        config.put(StreamingConfig.STREAMING_SECURITY_ZOOKEEPER_PRINCIPAL, "zookeeper/hadoop");
        config.put(StreamingConfig.STREAMING_SECURITY_STORM_PRINCIPAL, "streaming/hadoop@HADOOP.COM");

        StreamingSecurity zkSecurity = SecurityFactory.createSecurity(config);
        zkSecurity.initSecurity();
        
        assertEquals(System.getProperty(KerberosSecurity.ZOOKEEPER_AUTH_PRINCIPAL), "zookeeper/hadoop");
        zkSecurity.destroySecurity();
        assertEquals(System.getProperty(KerberosSecurity.ZOOKEEPER_AUTH_JASSCONF), null);
        assertEquals(System.getProperty(KerberosSecurity.ZOOKEEPER_AUTH_PRINCIPAL), null);
    }
}
