package com.zz.CTI.huawei.util;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 特林处理工具
 * @author zhou.zhang
 *
 */
public class StringUtils {
    
	/**
	 * 判断字符串是否为空或空字符串（没有空格）
	 * @param str
	 * @return
	 */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
   /**
    * 确定字符串是否为空或空字符串（包括空格）
    * @param str
    * @return
    */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    
   /**
    * 对象转JSON字符
    * @param object
    * @return
    * @throws IOException
    */
    public static String beanToJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);
        mapper.writeValue(gen, object);
        gen.close();
        String json = writer.toString();
        writer.close();
        return json;
    }
}
