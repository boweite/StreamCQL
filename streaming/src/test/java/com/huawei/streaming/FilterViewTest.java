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

package com.huawei.streaming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.EventTypeMng;
import com.huawei.streaming.event.TupleEvent;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.exception.IllegalDataTypeException;
import com.huawei.streaming.expression.ConstExpression;
import com.huawei.streaming.expression.ExpressionOperator;
import com.huawei.streaming.expression.IExpression;
import com.huawei.streaming.expression.PropertyValueExpression;
import com.huawei.streaming.expression.RelationExpression;
import com.huawei.streaming.output.OutPutPrint;
import com.huawei.streaming.output.OutputType;
import com.huawei.streaming.process.SelectSubProcess;
import com.huawei.streaming.processor.SimpleOutputProcessor;
import com.huawei.streaming.support.SupportConst;
import com.huawei.streaming.view.FilterView;
import com.huawei.streaming.view.FirstLevelStream;
import com.huawei.streaming.view.ProcessView;
import com.huawei.streaming.window.LengthBatchWindow;

/**
 * 
 * <FilterViewTest 总流程测试>
 * <功能详细描述>
 * 
 */
public class FilterViewTest
{
    private static final int INT_180 = 180;
    
    private static final int INT_200 = 200;
    
    /**
     * <Test>
     * <功能详细描述>
     * @throws IllegalDataTypeException 异常
     */
    @Test
    public void test()
        throws IllegalDataTypeException
    {
        //构造输入数据的schema
        EventTypeMng eventTypeMng = new EventTypeMng();
        List<Attribute> schema = new ArrayList<Attribute>();
        Attribute attributeA = new Attribute(Integer.class, "A");
        Attribute attributeB = new Attribute(Integer.class, "B");
        Attribute attributeC = new Attribute(Integer.class, "C");
        schema.add(attributeA);
        schema.add(attributeB);
        schema.add(attributeC);
        TupleEventType tupleEventType = new TupleEventType("schemName", schema);
        eventTypeMng.addEventType(tupleEventType);
        Map<String, Object> values = new HashMap<String, Object>();
        
        FirstLevelStream firststream = new FirstLevelStream();
        LengthBatchWindow lengthbatchwin = new LengthBatchWindow(SupportConst.I_TWO);
        
        IExpression exp =
            new RelationExpression(ExpressionOperator.GREATERTHAN, new PropertyValueExpression("A", Integer.class),
                new ConstExpression(Integer.valueOf(INT_180)));
        FilterView filter = new FilterView(exp);
        
        ProcessView processview = new ProcessView();
        
        String[] names = new String[SupportConst.I_THREE];
        names[SupportConst.I_ZERO] = "A";
        names[SupportConst.I_ONE] = "B";
        names[SupportConst.I_TWO] = "C";
        
        IExpression[] exprNodes = new IExpression[SupportConst.I_THREE];
        exprNodes[SupportConst.I_ZERO] = new PropertyValueExpression("A", Integer.class);
        exprNodes[SupportConst.I_ONE] = new PropertyValueExpression("B", Integer.class);
        exprNodes[SupportConst.I_TWO] = new PropertyValueExpression("C", Integer.class);
        
        SelectSubProcess selector = new SelectSubProcess("streamA", exprNodes, null, tupleEventType);
        SimpleOutputProcessor simple = new SimpleOutputProcessor(selector, null, new OutPutPrint(), OutputType.I);
        
        processview.setProcessor(simple);
        filter.addView(processview);
        firststream.addView(lengthbatchwin);
        lengthbatchwin.addView(filter);
        
        firststream.start();
        
        //发送数据
        //        long startTime = System.currentTimeMillis();
        for (int i = 0; i < INT_200; i++)
        {
            values.put("A", i);
            values.put("B", i);
            values.put("C", i);
            TupleEvent tupleEvent2 = new TupleEvent("streamB", "schemName", values, eventTypeMng);
            firststream.add(tupleEvent2);
        }
        
        firststream.stop();
        
        //        fail("Not yet implemented");
    }
}
