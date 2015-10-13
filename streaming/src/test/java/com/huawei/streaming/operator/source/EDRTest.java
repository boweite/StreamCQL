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
package com.huawei.streaming.operator.source;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.FixedLengthFrameDecoder;
import org.jboss.netty.handler.codec.socks.SocksMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.streaming.exception.StreamingException;

/**
 * 起一个TCP的Server端，监听指定端口，接收数据
 * 
 */
public class EDRTest
{
    private static final int DEFAULT_LISTENER_PORT = 9999;
    
    private static final Logger LOG = LoggerFactory.getLogger(EDRTest.class);
    
    private int listenerPort = DEFAULT_LISTENER_PORT;
    
    public static void main(String[] args)
        throws StreamingException
    {
        new EDRTest().execute();
    }
    
    /**
     * 执行TCP Server
     * @throws StreamingException 流处理异常
     */
    public void execute()
        throws StreamingException
    {
        ServerBootstrap bootstrap =
            new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ServerChannelPipelineFactory());
        LOG.info("tcp server will start on {}", listenerPort);
        bootstrap.bind(new InetSocketAddress(listenerPort));
    }
    
    /**
     * netty server channel
     * 
     */
    private class ServerChannelPipelineFactory implements ChannelPipelineFactory
    {
        private static final int FIX_LENGTH = 1165;
        
        /**
         * {@inheritDoc}
         */
        @Override
        public ChannelPipeline getPipeline()
            throws Exception
        {
            ChannelPipeline pipleline = Channels.pipeline();
            pipleline.addLast("encode", new SocksMessageEncoder());
            pipleline.addLast("decode", new FixedLengthFrameDecoder(FIX_LENGTH));
            pipleline.addLast("handler", new NettyServerHandler());
            return pipleline;
        }
        
    }
    
    /**
     * 
     * server处理句柄
     * 
     */
    private class NettyServerHandler extends SimpleChannelUpstreamHandler
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception
        {
            BigEndianHeapChannelBuffer buffer = (BigEndianHeapChannelBuffer)e.getMessage();
            String results = parseEDR(buffer.array());
            System.out.println(results);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
            throws Exception
        {
            LOG.error("Client has an error,Error cause:" + e.getCause().getMessage());
            e.getChannel().close();
        }
        
        private String parseEDR(byte[] bt)
        {
            int EDR_MESSAGE_MSISDN_OFFSET = 101;
            int EDR_MESSAGE_MSISDN_LENGTH = 16;
            int EDR_MESSAGE_QUOTA_NAME_OFFSET = 707;
            int EDR_MESSAGE_QUOTA_NAME_LENGTH = 32;
            int EDR_MESSAGE_QUOTA_CONSUMPTION_OFFSET = 740;
            int EDR_MESSAGE_QUOTA_AVAILABLE_OFFSET = 744;
            int EDR_MESSAGE_CASEID_OFFSET = 1163;
            StringBuffer sb = new StringBuffer();
            int triggerType = (int)bt[0];
            sb.append(triggerType);
            sb.append(",");
            for (int i = EDR_MESSAGE_MSISDN_OFFSET; i < EDR_MESSAGE_MSISDN_OFFSET + EDR_MESSAGE_MSISDN_LENGTH; ++i)
            {
                if (bt[i] == 0x0)
                {
                    break;
                }
                sb.append((char)bt[i]);
            }
            sb.append(",");
            
            for (int i = EDR_MESSAGE_QUOTA_NAME_OFFSET; i < EDR_MESSAGE_QUOTA_NAME_OFFSET
                + EDR_MESSAGE_QUOTA_NAME_LENGTH; ++i)
            {
                if (bt[i] == 0x0)
                {
                    break;
                }
                sb.append((char)bt[i]);
            }
            sb.append(",");
            byte[] bytes = new byte[4];
            for (int i = 0; i < 4; ++i)
            {
                bytes[i] = bt[EDR_MESSAGE_QUOTA_CONSUMPTION_OFFSET + i];
            }
            int consumption = bytesToInt(bytes);
            sb.append(consumption);
            sb.append(",");
            
            for (int i = 0; i < 4; ++i)
            {
                bytes[i] = bt[EDR_MESSAGE_QUOTA_AVAILABLE_OFFSET + i];
            }
            int AVAILABLE = bytesToInt(bytes);
            sb.append(AVAILABLE);
            sb.append(",");
            
            for (int i = 0; i < 2; ++i)
            {
                bytes[i] = bt[EDR_MESSAGE_CASEID_OFFSET + i];
            }
            int caseid = bytesToShort(bytes);
            sb.append(caseid);
            return sb.toString();
        }
        
        public int bytesToInt(byte[] bt)
        {
            int targets = (bt[3] & 0xff) | ((bt[2] << 8) & 0xff00) // | 表示安位或
                | ((bt[1] << 24) >>> 8) | (bt[0] << 24);
            return targets;
        }
        
        public short bytesToShort(byte[] bt)
        {
            short targets = (short)((bt[1] & 0xff) | ((bt[0] << 8) & 0xff00)); // | 表示安位或
            
            return targets;
        }
    }
}
