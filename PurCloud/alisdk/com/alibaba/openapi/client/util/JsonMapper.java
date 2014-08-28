/**
 * Project: ocean.common-1.1.0-SNAPSHOT
 *
 * File Created at 2010-6-4
 * $Id: JsonMapper.java 311300 2013-12-23 06:15:28Z yichun.wangyc $
 *
 * Copyright 2008 Alibaba.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.alibaba.openapi.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

/**
 * JSON Mapper
 *
 * @author jade
 */
public class JsonMapper {
    public static final String DATE_FORMAT        = "yyyyMMddHHmmssSSSZ";
    private static final JsonFactory  jf = new JsonFactory();
    static{
        jf.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); //支持不加引号的字段名
        jf.configure(Feature.ALLOW_SINGLE_QUOTES, true); //支持单引号
        jf.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true); 
    }
    private static final ObjectMapper m  = new ObjectMapper(jf);
    static{
        SerializationConfig sf = m.getSerializationConfig();
        m.setSerializationConfig(sf.with(SerializationConfig.Feature.USE_ANNOTATIONS).withDateFormat(new SimpleDateFormat(DATE_FORMAT)));
        DeserializationConfig df =  m.getDeserializationConfig();
        m.setDeserializationConfig(df.with(DeserializationConfig.Feature.USE_ANNOTATIONS).withDateFormat(new SimpleDateFormat(DATE_FORMAT)));
    }

    public static <T> T json2pojo(String jsonAsString, Class<T> pojoClass)
            throws JsonMappingException, JsonParseException, IOException {
        return m.readValue(jsonAsString, pojoClass);
    }

    public static Map<?, ?> json2map(String jsonAsString) throws JsonMappingException,
            JsonParseException, IOException {
        return m.readValue(jsonAsString, Map.class);
    }

    public static Map<?, ?> json2map(InputStream istream) throws JsonMappingException,
            JsonParseException, IOException {
        return m.readValue(istream, Map.class);
    }

    public static JsonNode json2node(String jsonAsString) throws JsonProcessingException, IOException {
        return m.readTree(jsonAsString);
    }

    public static JsonNode json2node(InputStream istream) throws JsonProcessingException, IOException {
        return m.readTree(istream);
    }

    public static JsonNode json2node(Reader reader) throws JsonProcessingException, IOException {
        return m.readTree(reader);
    }

    public static <T> T json2value(Reader reader, Class<T> type) throws IOException, JsonParseException, JsonMappingException {
        return m.readValue(reader, type);
    }

    public static Map<?, ?> node2map(JsonNode json) throws JsonProcessingException, IOException {
        if(json == null){
            return null;
        }
        JsonParser jp = null;
        try{
            jp = json.traverse();
            return m.readValue(jp, Map.class);
        } finally {
            if(jp != null){
                try {
                    jp.close();
                } catch (IOException ioe) { }
            }
        }
    }

    public static <T> T node2pojo(JsonNode json, Class<T> pojoClass) throws JsonProcessingException, IOException {
        if(json == null){
            return null;
        }
        JsonParser jp = null;
        try{
            jp = json.traverse();
            return m.readValue(jp, pojoClass);
        } finally {
            if(jp != null){
                try {
                    jp.close();
                } catch (IOException ioe) { }
            }
        }
    }

    public static void pojo2Json(Object pojo, Writer w) throws JsonGenerationException, JsonMappingException, IOException{
        JsonGenerator jg = null;
        try {
            jg = jf.createJsonGenerator(w);
            m.writeValue(jg, pojo);
        } finally{
            if(jg != null){
                try {
                    jg.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static String pojo2json(Object pojo) throws JsonGenerationException, JsonMappingException, IOException {
        final StringWriter sw = new StringWriter();
        JsonGenerator jg = null;
        try {
            jg = jf.createJsonGenerator(sw);
            m.writeValue(jg, pojo);
            return sw.toString();
        } finally{
            if(jg != null){
                try {
                    jg.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static String node2json(JsonNode node) throws JsonProcessingException, IOException{
        final StringWriter sw = new StringWriter();
        JsonGenerator jg = null;
        try {
            jg = jf.createJsonGenerator(sw);
            m.writeTree(jg, node);
            return sw.toString();
        } finally{
            if(jg != null){
                try {
                    jg.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static void node2json(JsonNode node, Writer w) throws JsonGenerationException, JsonMappingException, IOException{
        JsonGenerator jg = null;
        try {
            jg = jf.createJsonGenerator(w);
            m.writeTree(jg, node);
        } finally{
            if(jg != null){
                try {
                    jg.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static ObjectNode createObjectNode(){
        return m.createObjectNode();
    }

    public static ArrayNode createArrayNode(){
        return m.createArrayNode();
    }

}
