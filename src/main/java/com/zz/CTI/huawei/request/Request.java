package com.zz.CTI.huawei.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.zz.CTI.huawei.common.GlobalObjects;
import com.zz.CTI.huawei.util.StringUtils;

/**
 * 请求工具类
 * @author zhou.zhang
 *
 */
@SuppressWarnings("deprecation")
public class Request {

	/**
     * 发送HTTP DELETE请求
     * @param url the address of the request
     * @param headers the field is used to set the header of http request
     * @return
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static Map<String, Object> delete(String workNo,String url, Map<String, Object> entityParams) {
    	CloseableHttpClient client = getHttpClient(url);
    	MyHttpDelete delete = null;
    	Map<String, Object> result = null;
    	try 
		{
			delete = new MyHttpDelete(url);
			if(null != entityParams)
	    	{	    		
				String jsonString = beanToJson(entityParams);
				HttpEntity entity = new StringEntity(jsonString);
				delete.setEntity(entity);
	    	}
			
//			Logs.info(workNo + " guid: " + GlobalObjects.guidMap.get(workNo));
//			Logs.info(workNo + " Cookie: " + GlobalObjects.elbSessionMap.get(workNo));
			if(!StringUtils.isNullOrBlank(workNo) && GlobalObjects.guidMap.containsKey(workNo))
	    	{
	    		delete.setHeader("guid", GlobalObjects.guidMap.get(workNo));
	    	}
			
			//For HEC
			if(!StringUtils.isNullOrBlank(workNo) && GlobalObjects.elbSessionMap.containsKey(workNo))
			{
				delete.setHeader("Cookie", GlobalObjects.elbSessionMap.get(workNo));
			}
			
			delete.setHeader("Content-Type", "application/json;charset=UTF-8");
			CloseableHttpResponse response = client.execute(delete);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
	    	{
	    		InputStream is = response.getEntity().getContent();  
                BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf-8"));  
                StringBuffer buffer = new StringBuffer();  
                String line = "";  
                while ((line = in.readLine()) != null) {  
                    buffer.append(line);  
                }
                
                result = jsonToMap(buffer.toString());
	    	}
		}
    	catch (Exception e)
        {
            e.printStackTrace();
        }
    	finally
		{
			delete.releaseConnection();
		}
    	return result;
    	
    }
	
    /**
     * 发送HTTP GET请求
     * @param url the address of the request
     * @param headers the field is used to set the header of http request
     * @return
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static Map<String, Object> get(String workNo,String url) {
    	CloseableHttpClient client = getHttpClient(url);
    	HttpGet get = null;
    	Map<String, Object> result = null;
    	try 
		{
			get = new HttpGet(url);
	    	
			if(!StringUtils.isNullOrBlank(workNo) && GlobalObjects.guidMap.containsKey(workNo))
	    	{
	    		get.setHeader("guid", GlobalObjects.guidMap.get(workNo));
	    	}

			//For HEC
			if(!StringUtils.isNullOrBlank(workNo) && GlobalObjects.elbSessionMap.containsKey(workNo))
			{
				get.setHeader("Cookie", GlobalObjects.elbSessionMap.get(workNo));
			}			
			
			get.setHeader("Content-Type", "application/json;charset=UTF-8");
			CloseableHttpResponse response = client.execute(get);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
	    	{
	    		InputStream is = response.getEntity().getContent();  
                BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf-8"));  
                StringBuffer buffer = new StringBuffer();  
                String line = "";  
                while ((line = in.readLine()) != null) {  
                    buffer.append(line);  
                }
                
                result = jsonToMap(buffer.toString());
	    	}
		}
    	catch (Exception e)
        {
    		 e.printStackTrace();
        }
    	finally
		{
			get.releaseConnection();
		}
		
    	return result;
    }

    
    /**
     * 发送HTTP post请求
     * @param url the address of the request
     * @param entityParams 实体参数
     * @param headers the field is used to set the header of http request
     * @return
     */
    public static Map<String, Object> post(String workNo,String url, Map<String, Object> entityParams) {
    	Map<String, Object> result = null;
    	HttpPost post = null;
    	CloseableHttpClient client = getHttpClient(url);
    	try {
    		post = new HttpPost(url);
	    	if(null != entityParams)
	    	{	    		
				String jsonString = beanToJson(entityParams);
				HttpEntity entity = new StringEntity(jsonString);
				post.setEntity(entity);
	    	}
	    	
//	    	Logs.info(workNo + " guid: " + GlobalObjects.guidMap.get(workNo));
//	    	Logs.info(workNo + " Cookie: " + GlobalObjects.elbSessionMap.get(workNo));
	    	if(!StringUtils.isNullOrBlank(workNo) && GlobalObjects.guidMap.containsKey(workNo))
	    	{
	    		post.setHeader("guid", GlobalObjects.guidMap.get(workNo));
	    	}

			//For HEC
			if(!StringUtils.isNullOrBlank(workNo) && GlobalObjects.elbSessionMap.containsKey(workNo))
			{
				post.setHeader("Cookie", GlobalObjects.elbSessionMap.get(workNo));
			}
			
	    	post.setHeader("Content-Type", "application/json;charset=UTF-8");
	    	CloseableHttpResponse response = client.execute(post);
	    	if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
	    	{
	    		InputStream is = response.getEntity().getContent();  
                BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf-8"));  
                StringBuffer buffer = new StringBuffer();  
                String line = "";  
                while ((line = in.readLine()) != null) {  
                    buffer.append(line);  
                }
                
                result = jsonToMap(buffer.toString());
	    	}
    	}
    	catch (Exception e)
        {
    		 e.printStackTrace();
        }
    	finally
    	{
			post.releaseConnection();
    	}
    	return result;
    }
    
    /**
     * 发送HTTP PUT请求
     * @param url  请求的地址
     * @param entityParams 实体参数
     * @param headers HTTP请求头
     * @return
     */
    public static Map<String, Object> put(String workNo,String url, Map<String, Object> entityParams) {
    	CloseableHttpClient client = getHttpClient(url);
    	HttpPut put = null;
    	Map<String, Object> result = null;
    	try {	    	
	    	put = new HttpPut(url);
	    	if (null != entityParams) {
	    		String jsonString = beanToJson(entityParams);
                HttpEntity entity = new StringEntity(jsonString);
                put.setEntity(entity);
            }
	    	
//	    	Logs.info(workNo + " guid: " + GlobalObjects.guidMap.get(workNo));
//	    	Logs.info(workNo + " Cookie: " + GlobalObjects.elbSessionMap.get(workNo));
	    	if(!StringUtils.isNullOrBlank(workNo) && GlobalObjects.guidMap.containsKey(workNo)) 
	    	{
	    		put.setHeader("guid", GlobalObjects.guidMap.get(workNo)); //将guid添加到请求头
	    	}			

			//For HEC
			if(!StringUtils.isNullOrBlank(workNo) && GlobalObjects.elbSessionMap.containsKey(workNo))
			{
				put.setHeader("Cookie", GlobalObjects.elbSessionMap.get(workNo)); //将Cookie添加到请求头
			}
				    	
	    	put.setHeader("Content-Type", "application/json;charset=UTF-8");
	    	CloseableHttpResponse response = client.execute(put);	    	
	    	if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
	    	{
	    		InputStream is = response.getEntity().getContent();  
                BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf-8"));  
                StringBuffer buffer = new StringBuffer();  
                String line = "";  
                while ((line = in.readLine()) != null) {  
                    buffer.append(line);  
                }
                
                result = jsonToMap(buffer.toString());
                
                dealGuid(workNo,response);
                
	    	}
    	}
    	catch (Exception e)
        {
    		return null;
        }
    	finally
    	{
			put.releaseConnection();
    	}
    	return result;
    }


    /*
     * for https request
     */
    private static CloseableHttpClient creatSSLInsecureClent() throws GeneralSecurityException {
    	try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				
				@Override
				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					// TODO Auto-generated method stub
					return true;
				}
			}).build();
			
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext
					, new X509HostnameVerifier(){
				public boolean verify(String arg0, SSLSession arg1) {
			        return true;
			    }
			    public void verify(String host, SSLSocket ssl)
			            throws IOException {
			    }
			    public void verify(String host, X509Certificate cert)
			            throws SSLException {
			    }
			    public void verify(String host, String[] cns,
			            String[] subjectAlts) throws SSLException {
			    }
			}
			);
			
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (GeneralSecurityException e) {
			throw e;
		}
    }
    
    private static CloseableHttpClient getHttpClient(String url) {
    	if(url.startsWith("https"))
    	{
    		try 
    		{
    			return creatSSLInsecureClent();
			} 
    		catch (GeneralSecurityException e) 
    		{
				// TODO Auto-generated catch block
    			e.printStackTrace();
			}
    	}
    	CloseableHttpClient client = HttpClients.custom().build();
        return client;
    }
    
    /*
     * get guid for authentication
     */
	private static void dealGuid(String workNo,CloseableHttpResponse response) {
		Header[] allHeaders = response.getAllHeaders();
		if (allHeaders != null && allHeaders.length > 0)
		{
		    for (Header header : allHeaders)
		    {
		        if (header.getName().equals("Set-GUID"))
		        {
		            String setGuid = header.getValue();
		            if (setGuid != null)
		            {
		            	GlobalObjects.guidMap.put(workNo, setGuid.replace("JSESSIONID=", ""));
		            }
		            break;
		        }else if (header.getName().equals("Set-Cookie"))//For HEC
		        {
		        	String setCookie = header.getValue();
		        	setCookie = setCookie.substring(0, setCookie.indexOf(";"));
		        	GlobalObjects.elbSessionMap.put(workNo, setCookie);
		        }
		    }
		}
	}
    
    /**
     * Change object to json-string
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
    
    @SuppressWarnings("unchecked")
	public static HashMap<String, Object> jsonToMap(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> result;
        try
        {
            result = objectMapper.readValue(json, HashMap.class);
            return result;
        }
        catch (JsonParseException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}