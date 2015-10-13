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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

import com.huawei.streaming.operator.inputstream.Bytes;
import com.huawei.streaming.operator.inputstream.Input;


/**
 * tcp 测试
 * 
 */
public class TCPTest
{
    private static final int BUFSIZE = 883;
    
    public static void main(String[] args)
        throws IOException
    {
        int servPort = 9999;
        ServerSocket servSocket = new ServerSocket(servPort);
        int recvMsgSize = 0;
        byte[] recvBuf = new byte[BUFSIZE];
        try
        {
            while (true)
            {
                Socket clntSocket = servSocket.accept(); // 该方法会阻塞
                SocketAddress clientAddress = clntSocket.getRemoteSocketAddress();
                System.out.println("Handling client at " + clientAddress);
                InputStream in = clntSocket.getInputStream();
                OutputStream out = clntSocket.getOutputStream();
                int i = 0;
                while ((recvMsgSize = in.read(recvBuf)) != -1)
                {
                    System.out.println(Arrays.toString(recvBuf));
                    
                    ByteArrayInputStream stream = new ByteArrayInputStream(recvBuf);
                    Input input = new Input(stream);
                    AIu_HOSerializer serializer = new TCPTest().new AIu_HOSerializer();
                    Object[] arr = serializer.read(input);
                    System.out.println("start**************************************");
                    System.out.println(arr.length);
                    System.out.println(arr[12]);
                    System.out.println(arr[21]);
                    System.out.println(arr[96]);
                    for (int j = 0; j < arr.length; j++)
                    {
                        System.out.println(j + "-------" + arr[j]);
                    }
                    System.out.println("end**************************************");
                    if (i == 2)
                    {
                        System.exit(1);
                    }
                    i++;
                }
                clntSocket.close();
                System.out.println("finish");
            }
        }
        finally
        {
            servSocket.close();
        }
        
    }
    
    public class AIu_HOSerializer
    {
        
        public AIu_HOSerializer()
        {
            
        }
        
        public Object[] read(Input input)
            throws IOException
        {
            Object[] fields = new Object[150];
            fields[0] = Bytes.toString(readBytes(input, 20)); //  TDR标识   Char[20]
            fields[1] = Bytes.toInt(readBytes(input, 4)); //共享层标识   Int
            fields[2] = Bytes.toString(readBytes(input, 20));
            fields[3] = Bytes.toString(readBytes(input, 20));
            fields[4] = Bytes.toString(readBytes(input, 20));
            fields[5] = Bytes.toLong(readBytes(input, 8));
            fields[6] = Bytes.toInt(readBytes(input, 4));
            fields[7] = Bytes.toInt(readBytes(input, 4));
            fields[8] = Bytes.toString(readBytes(input, 20));
            fields[9] = Bytes.toInt(readBytes(input, 4));
            fields[10] = Bytes.toInt(readBytes(input, 4));
            fields[11] = Bytes.toInt(readBytes(input, 4));
            fields[12] = Bytes.toInt(readBytes(input, 4));
            fields[13] = Bytes.toInt(readBytes(input, 4));
            fields[14] = Bytes.toInt(readBytes(input, 4));
            fields[15] = Bytes.toInt(readBytes(input, 4));
            fields[16] = Bytes.toInt(readBytes(input, 4));
            fields[17] = Bytes.toInt(readBytes(input, 4));
            fields[18] = Bytes.toInt(readBytes(input, 4));
            fields[19] = Bytes.toInt(readBytes(input, 4));
            fields[20] = Bytes.toInt(readBytes(input, 4));
            fields[21] = Bytes.toString(readBytes(input, 16));
            
            fields[22] = Bytes.toString(readBytes(input, 9));
            fields[23] = Bytes.toString(readBytes(input, 24));
            fields[24] = Bytes.toString(readBytes(input, 15));
            fields[25] = Bytes.toString(readBytes(input, 3));
            fields[26] = Bytes.toString(readBytes(input, 3));
            fields[27] = Bytes.toString(readBytes(input, 4));
            fields[28] = Bytes.toString(readBytes(input, 4));
            fields[29] = Bytes.toString(readBytes(input, 3));
            fields[30] = Bytes.toString(readBytes(input, 4));
            fields[31] = Bytes.toString(readBytes(input, 4));
            fields[32] = Bytes.toString(readBytes(input, 10));
            fields[33] = Bytes.toString(readBytes(input, 10));
            fields[34] = Bytes.toString(readBytes(input, 13));
            fields[35] = Bytes.toString(readBytes(input, 13));
            fields[36] = Bytes.toInt(readBytes(input, 4));
            fields[37] = Bytes.toInt(readBytes(input, 4));
            fields[38] = Bytes.toLong(readBytes(input, 8));
            fields[39] = Bytes.toInt(readBytes(input, 4));
            fields[40] = Bytes.toInt(readBytes(input, 4));
            fields[41] = Bytes.toInt(readBytes(input, 4));
            fields[42] = Bytes.toInt(readBytes(input, 4));
            fields[43] = Bytes.toInt(readBytes(input, 4));
            fields[44] = Bytes.toLong(readBytes(input, 8));
            fields[45] = Bytes.toLong(readBytes(input, 8));
            fields[46] = Bytes.toLong(readBytes(input, 8));
            fields[47] = Bytes.toLong(readBytes(input, 8));
            fields[48] = Bytes.toLong(readBytes(input, 8));
            fields[49] = Bytes.toLong(readBytes(input, 8));
            fields[50] = Bytes.toLong(readBytes(input, 8));
            
            fields[51] = Bytes.toInt(readBytes(input, 4));
            fields[52] = Bytes.toInt(readBytes(input, 4));
            fields[53] = Bytes.toInt(readBytes(input, 4)); //   全速率MR总数 int
            fields[54] = Bytes.toInt(readBytes(input, 4)); //   半速率MR总数 int
            fields[55] = Bytes.toInt(readBytes(input, 4)); // 0级上行信号质量次数  int
            fields[56] = Bytes.toInt(readBytes(input, 4)); // 1级上行信号质量次数  int
            fields[57] = Bytes.toInt(readBytes(input, 4)); // 2级上行信号质量次数  int
            fields[58] = Bytes.toInt(readBytes(input, 4)); // 3级上行信号质量次数  int
            fields[59] = Bytes.toInt(readBytes(input, 4)); // 4级上行信号质量次数  int
            fields[60] = Bytes.toInt(readBytes(input, 4)); // 5级上行信号质量次数  int
            fields[61] = Bytes.toInt(readBytes(input, 4)); // 6级上行信号质量次数  int
            fields[62] = Bytes.toInt(readBytes(input, 4)); // 7级上行信号质量次数  int
            fields[63] = Bytes.toInt(readBytes(input, 4)); // 0级下行信号质量次数  int
            fields[64] = Bytes.toInt(readBytes(input, 4)); // 1级下行信号质量次数  int
            fields[65] = Bytes.toInt(readBytes(input, 4)); // 2级下行信号质量次数  int
            fields[66] = Bytes.toInt(readBytes(input, 4)); // 3级下行信号质量次数  int
            fields[67] = Bytes.toInt(readBytes(input, 4)); // 4级下行信号质量次数  int
            fields[68] = Bytes.toInt(readBytes(input, 4)); // 5级下行信号质量次数  int
            fields[69] = Bytes.toInt(readBytes(input, 4)); // 6级下行信号质量次数  int
            fields[70] = Bytes.toInt(readBytes(input, 4)); // 7级下行信号质量次数  int
            fields[71] = Bytes.toInt(readBytes(input, 4)); //   TA为0或1的次数   int
            fields[72] = Bytes.toInt(readBytes(input, 4)); //    Rxlevel Down大于-85次数 int
            fields[73] = Bytes.toInt(readBytes(input, 4)); //   弱覆盖MR个数 int
            fields[74] = Bytes.toInt(readBytes(input, 4)); //   过覆盖MR个数 int
            fields[75] = Bytes.toInt(readBytes(input, 4)); //   上下行不平衡MR个数  int
            fields[76] = Bytes.toInt(readBytes(input, 4)); //    干扰MR个数  int
            fields[77] = Bytes.toInt(readBytes(input, 4)); //    切换要求时间  int
            fields[78] = Bytes.toInt(readBytes(input, 4)); // 切换请求时间  int
            fields[79] = Bytes.toInt(readBytes(input, 4)); // 切换请求响应时间    int
            fields[80] = Bytes.toInt(readBytes(input, 4)); // 切换命令时间  int
            fields[81] = Bytes.toInt(readBytes(input, 4)); //  切换检测到时间 int
            fields[82] = Bytes.toInt(readBytes(input, 4)); //   切换完成时间  int
            fields[83] = Bytes.toInt(readBytes(input, 4)); //   指配请求时间（RAB指配请求时间）   int
            fields[84] = Bytes.toInt(readBytes(input, 4)); //  指配完成时间（RAB指配完成时间）   int
            fields[85] = Bytes.toInt(readBytes(input, 4)); //  振铃时间（ms）    int
            fields[86] = Bytes.toInt(readBytes(input, 4)); // 应答时间（ms）    int
            fields[87] = Bytes.toInt(readBytes(input, 4)); //   切换方向    int
            fields[88] = Bytes.toInt(readBytes(input, 4)); //  切换信道类型  int
            fields[89] = Bytes.toInt(readBytes(input, 4)); // 切入故障时间  int
            fields[90] = Bytes.toInt(readBytes(input, 4)); //    切出故障时间  int
            fields[91] = Bytes.toInt(readBytes(input, 4)); //  切出要求拒绝时间    int
            fields[92] = Bytes.toInt(readBytes(input, 4)); //  掉话时间    int
            fields[93] = Bytes.toInt(readBytes(input, 4)); //    结束时间（ms）    int
            fields[94] = Bytes.toInt(readBytes(input, 4)); //  结束消息子协议类型   int
            fields[95] = Bytes.toInt(readBytes(input, 4)); //  结束消息    int
            fields[96] = Bytes.toInt(readBytes(input, 4)); //   结束原因    int
            fields[97] = Bytes.toInt(readBytes(input, 4)); // 加密版本号   int
            fields[98] = Bytes.toString(readBytes(input, 20)); //   备用字段1   Char[20]
            fields[99] = Bytes.toString(readBytes(input, 20)); //   备用字段2   Char[20]
            fields[100] = Bytes.toString(readBytes(input, 20)); //   备用字段3   Char[20]
            fields[101] = Bytes.toLong(readBytes(input, 8)); //   备用字段4   long
            fields[102] = Bytes.toInt(readBytes(input, 4)); //   备用字段5   int
            fields[103] = Bytes.toInt(readBytes(input, 4)); //   备用字段6   int
            fields[104] = Bytes.toInt(readBytes(input, 4)); //   备用字段7   int
            fields[105] = Bytes.toInt(readBytes(input, 4)); //   备用字段8   int
            fields[106] = Bytes.toString(readBytes(input, 20)); //    文件位置    Char[20]
            fields[107] = Bytes.toInt(readBytes(input, 4)); //  偏移量 int
            fields[108] = Bytes.toString(readBytes(input, 5)); // 归属运营商移动国家码  Char[5]
            fields[109] = Bytes.toString(readBytes(input, 5)); // 归属运营商移动网络码  Char[5]
            fields[110] = Bytes.toInt(readBytes(input, 4)); //   归属省ID   int
            fields[111] = Bytes.toInt(readBytes(input, 4)); //  归属地市    int
            fields[112] = Bytes.toInt(readBytes(input, 4)); // 探针ID    int
            fields[113] = Bytes.toInt(readBytes(input, 4)); //    探针ID2   int
            fields[114] = Bytes.toInt(readBytes(input, 4)); // 组ID     int
            fields[115] = Bytes.toInt(readBytes(input, 4)); //  链路源信令点  int
            fields[116] = Bytes.toInt(readBytes(input, 4)); //  链路目的信令点 int
            fields[117] = Bytes.toLong(readBytes(input, 8)); //    链路源IP地址 long
            fields[118] = Bytes.toInt(readBytes(input, 4)); //  链路源端口号  int
            fields[119] = Bytes.toLong(readBytes(input, 8)); //   链路目的IP地址    long
            fields[120] = Bytes.toInt(readBytes(input, 4)); // 链路目的端口号 int
            fields[121] = Bytes.toInt(readBytes(input, 4)); //  Setup时间 int
            fields[122] = Bytes.toInt(readBytes(input, 4)); // 首业务类型   int
            fields[123] = Bytes.toString(readBytes(input, 20)); // 首业务ID   Char[20]
            fields[124] = Bytes.toInt(readBytes(input, 4)); //   MSC切换类型 int
            fields[125] = Bytes.toInt(readBytes(input, 4)); // 切换呼叫状态  int
            fields[126] = Bytes.toInt(readBytes(input, 4)); // 呼叫业务类型  int
            fields[127] = Bytes.toInt(readBytes(input, 4)); //    切入类型    int
            fields[128] = Bytes.toInt(readBytes(input, 4)); //  起始MSC   int
            fields[129] = Bytes.toInt(readBytes(input, 4)); //  起始BSC   int
            fields[130] = Bytes.toString(readBytes(input, 3)); //  起始MCC   Char[3]
            fields[131] = Bytes.toString(readBytes(input, 3)); //  起始MNC   Char[3]
            fields[132] = Bytes.toString(readBytes(input, 4)); //  起始LAC   Char[4]
            fields[133] = Bytes.toString(readBytes(input, 4)); //   起始CI    Char[4]
            fields[134] = Bytes.toInt(readBytes(input, 4)); //   起始POOLID    int
            fields[135] = Bytes.toInt(readBytes(input, 4)); //  起始接入网类型 int
            fields[136] = Bytes.toInt(readBytes(input, 4)); // 切换源侧MSC int
            fields[137] = Bytes.toInt(readBytes(input, 4)); //  切换源侧NI  int
            fields[138] = Bytes.toInt(readBytes(input, 4)); //  切换源侧POOLID  int
            fields[139] = Bytes.toInt(readBytes(input, 4)); // 切换目标侧MSC    int
            fields[140] = Bytes.toInt(readBytes(input, 4)); //  切换目标侧NI int
            fields[141] = Bytes.toInt(readBytes(input, 4)); //  切换目标侧POOLID int
            fields[142] = Bytes.toInt(readBytes(input, 4)); //    呼叫接通持续时长    int
            fields[143] = Bytes.toInt(readBytes(input, 4)); // 第一拆线时间  int
            fields[144] = Bytes.toInt(readBytes(input, 4)); //    层级1 ID  int
            fields[145] = Bytes.toInt(readBytes(input, 4)); //    层级2 ID  int
            fields[146] = Bytes.toInt(readBytes(input, 4)); //    层级3 ID  int
            fields[147] = Bytes.toInt(readBytes(input, 4)); //    层级4 ID  int
            fields[148] = Bytes.toInt(readBytes(input, 4)); //    层级5 ID  int
            fields[149] = Bytes.toInt(readBytes(input, 4)); //    层级6 ID  int
            
            return fields;
        }
        
        public byte[] readBytes(Input input, int length)
            throws IOException
        {
            return input.readBytes(length);
        }
    }
    
}
