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

package com.huawei.streaming.serde;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.huawei.streaming.event.Attribute;
import com.huawei.streaming.event.TupleEventType;
import com.huawei.streaming.exception.StreamSerDeException;
import static org.junit.Assert.*;

/**
 *
 * <csvserde>
 *
 */
public class CSVSerDeTest
{

    private static TupleEventType schema;
    private static TupleEventType schema2;

    static
    {
        List<Attribute> atts = Lists.newArrayList();
        atts.add(new Attribute(String.class, "a"));
        atts.add(new Attribute(String.class, "b"));
        atts.add(new Attribute(String.class, "c"));
        schema = new TupleEventType("S1", atts);

    }

    @Test
    public void testDeSerialize1() throws StreamSerDeException
    {
        CSVSerDe csvSerDe = new CSVSerDe();
        csvSerDe.setSchema(schema);
        SimpleSerDe deser = new SimpleSerDe();
        deser.setSchema(schema);
        String s =  "0,USER4,1379927089198";
        List<Object[]> results = csvSerDe.deSerialize(s);
        assertTrue(results.get(0).length == 3);
        assertTrue(results.get(0)[0].equals("0"));
        assertTrue(results.get(0)[1].equals("USER4"));
    }

    @Test
    public void testDeSerialize2() throws StreamSerDeException
    {
        CSVSerDe csvSerDe = new CSVSerDe();
        csvSerDe.setSchema(schema);
        SimpleSerDe deser = new SimpleSerDe();
        deser.setSchema(schema);
        String s =  "2,\"\"\"a\"\"\",\"\"\"b\"\"\"";
        List<Object[]> results = csvSerDe.deSerialize(s);
        assertTrue(results.get(0).length == 3);
        assertTrue(results.get(0)[0].equals("2"));
        assertTrue(results.get(0)[1].equals("\"a\""));
        assertTrue(results.get(0)[2].equals("\"b\""));
    }

    @Test
    public void testDeSerialize3() throws StreamSerDeException
    {
        CSVSerDe csvSerDe = new CSVSerDe();
        csvSerDe.setSchema(schema);
        SimpleSerDe deser = new SimpleSerDe();
        deser.setSchema(schema);
        String s =  "3,\"a,b\",\"\"\"a,b\"\"\"";
        List<Object[]> results = csvSerDe.deSerialize(s);
        assertTrue(results.get(0).length == 3);
        assertTrue(results.get(0)[0].equals("3"));
        assertTrue(results.get(0)[1].equals("a,b"));
        assertTrue(results.get(0)[2].equals("\"a,b\""));
    }


    @Test
    public void testSerialize() throws StreamSerDeException
    {
        CSVSerDe csvSerDe = new CSVSerDe();
        csvSerDe.setSchema(schema);
        SimpleSerDe deser = new SimpleSerDe();
        deser.setSchema(schema);
        String s =  "3,\"a,b\",\"\"\"a,b\"\"\"";
        List<Object[]>  results= csvSerDe.deSerialize(s);
        assertTrue(csvSerDe.serialize(results).equals(s));
    }
}
