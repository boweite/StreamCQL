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
public class StreamingTest
{
    private static final int DEFAULT_LISTENER_PORT = 9999;
    
    private static final Logger LOG = LoggerFactory.getLogger(StreamingTest.class);
    
    private int listenerPort = DEFAULT_LISTENER_PORT;
    
    public static void main(String[] args)
        throws StreamingException
    {
        new StreamingTest().execute();
    }
    
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
    
    private class ServerChannelPipelineFactory implements ChannelPipelineFactory
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public ChannelPipeline getPipeline()
            throws Exception
        {
            ChannelPipeline pipleline = Channels.pipeline();
            pipleline.addLast("encode", new SocksMessageEncoder());
            pipleline.addLast("decode", new FixedLengthFrameDecoder(844));
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
            String results = parseSTR(buffer.array());
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
        
        public String parseSTR(byte[] bt)
        {
            StringBuffer sb = new StringBuffer();
            
            int UDFR_STREAMING_MESSAGE_MSISDN_OFFSET = 41;
            int UDFR_STREAMING_MESSAGE_MSISDN_LENGTH = 16;
            int UDFR_STREAMING_MESSAGE_HOST_OFFSET = 357;
            int UDFR_STREAMING_MESSAGE_HOST_LENGTH = 64;
            int UDFR_STREAMING_MESSAGE_CASEID_OFFSET = 842;
            for (int i = UDFR_STREAMING_MESSAGE_MSISDN_OFFSET; i < UDFR_STREAMING_MESSAGE_MSISDN_OFFSET
                + UDFR_STREAMING_MESSAGE_MSISDN_LENGTH; ++i)
            {
                if (bt[i] == 0x0)
                {
                    break;
                }
                sb.append((char)bt[i]);
            }
            sb.append(",");
            
            for (int i = UDFR_STREAMING_MESSAGE_HOST_OFFSET; i < UDFR_STREAMING_MESSAGE_HOST_OFFSET
                + UDFR_STREAMING_MESSAGE_HOST_LENGTH; ++i)
            {
                if (bt[i] == 0x0)
                {
                    break;
                }
                sb.append((char)bt[i]);
            }
            sb.append(",");
            
            byte[] bytes = new byte[2];
            for (int i = 0; i < 2; ++i)
            {
                bytes[i] = bt[UDFR_STREAMING_MESSAGE_CASEID_OFFSET + i];
            }
            int caseid = bytesToShort(bytes);
            sb.append(caseid);
            
            return sb.toString();
        }
        
        public short bytesToShort(byte[] bt)
        {
            short targets = (short)((bt[1] & 0xff) | ((bt[0] << 8) & 0xff00)); // | 表示安位或
            
            return targets;
        }
    }
}
