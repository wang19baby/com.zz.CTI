package com.zz.CTI.Util.HttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.zz.CTI.Util.HttpClient.Bean.HttpClientResult;

/**
 * 
 * @Title:HttpClientUtil
 * @Description:TODO(httpClient工具类)
 * @Company: 
 * @author zhou.zhang
 * @date 2018年7月6日 上午11:02:33
 */
public class HttpClientUtil {

	// 编码格式。发送编码格式统一用UTF-8
	private static final String ENCODING = "UTF-8";

	// 设置连接超时时间，单位毫秒。
	private static final int CONNECT_TIMEOUT = 6000;

	// 请求获取数据的超时时间(即响应时间)，单位毫秒。
	private static final int SOCKET_TIMEOUT = 6000;

	/**
	 * 
	 * @Title:doGet
	 * @Description: TODO(发送get请求；不带请求头和请求参数)
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url) throws Exception {
		return doGet(url, null, null);
	}

	/**
	 * 
	 * @Title:doGet
	 * @Description: TODO(发送get请求；带请求参数)
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url, Map<String, String> params) throws Exception {
		return doGet(url, null, params);
	}

	/**
	 * 
	 * @Title:doGet
	 * @Description: TODO(发送get请求；带请求头和请求参数)
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params)
			throws Exception {
		CloseableHttpClient httpClient = getHttpClient(url);

		// 创建访问的地址
		URIBuilder uriBuilder = new URIBuilder(url);
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue());
			}
		}

		HttpGet httpGet = new HttpGet(uriBuilder.build());
		/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。
		 * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
		 * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。
		 * 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpGet.setConfig(requestConfig);

		packageHeader(headers, httpGet);

		CloseableHttpResponse httpResponse = null;

		try {
			return getHttpClientResult(httpResponse, httpClient, httpGet);
		} finally {
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 
	 * @Title:doPost
	 * @Description: TODO(发送post请求；不带请求头和请求参数)
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url) throws Exception {
		return doPost(url, null, null);
	}

	/**
	 * 
	 * @Title:doPost
	 * @Description: TODO(发送post请求；带请求参数)
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url, Object params) throws Exception {
		return doPost(url, null, params);
	}

	/**
	 * 
	 * @Title:doPost
	 * @Description: TODO(发送post请求；带请求头和请求参数)
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url, Map<String, String> headers, Object params) throws Exception {
		// 创建httpClient对象
		CloseableHttpClient httpClient = getHttpClient(url);

		// 创建http对象
		HttpPost httpPost = new HttpPost(url);
		/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。
		 * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
		 * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。
		 * 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPost.setConfig(requestConfig);
		// 设置请求头
		packageHeader(headers, httpPost);

		// 封装请求参数
		packageParam(params, httpPost);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpPost);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 
	 * @Title:doPut
	 * @Description: TODO(发送put请求；不带请求参数)
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPut(String url) throws Exception {
		return doPut(url);
	}

	/**
	 * 
	 * @Title:doPut
	 * @Description: TODO(发送put请求；带请求参数)
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPut(String url, Map<String, String> params) throws Exception {
		CloseableHttpClient httpClient = getHttpClient(url);
		HttpPut httpPut = new HttpPut(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPut.setConfig(requestConfig);

		packageParam(params, httpPut);

		CloseableHttpResponse httpResponse = null;

		try {
			return getHttpClientResult(httpResponse, httpClient, httpPut);
		} finally {
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 
	 * @Title:doDelete
	 * @Description: TODO(发送delete请求；不带请求参数)
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doDelete(String url) throws Exception {
		CloseableHttpClient httpClient = getHttpClient(url);
		HttpDelete httpDelete = new HttpDelete(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpDelete.setConfig(requestConfig);

		CloseableHttpResponse httpResponse = null;
		try {
			return getHttpClientResult(httpResponse, httpClient, httpDelete);
		} finally {
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 
	 * @Title:doDelete
	 * @Description: TODO(发送delete请求；带请求参数)
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doDelete(String url, Map<String, String> params) throws Exception {
		if (params == null) {
			params = new HashMap<String, String>();
		}

		params.put("_method", "delete");
		return doPost(url, params);
	}

	/**
	 * 
	 * @Title:postFileMultiPart
	 * @Description: TODO(MultipartFile file 文件上传)
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult postFileMultiPart(String url, Map<String, ContentBody> params) throws Exception {
		return postFileMultiPart(url, null, params);
	}

	/**
	 * 
	 * @Title:postFileMultiPart
	 * @Description: TODO(MultipartFile file 文件上传)
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult postFileMultiPart(String url, Map<String, String> headers,
			Map<String, ContentBody> params) throws Exception {
		CloseableHttpClient httpClient = getHttpClient(url);
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPost.setConfig(requestConfig);
		packageHeader(headers, httpPost);
		packageMultiPartParam(params, httpPost);

		CloseableHttpResponse httpResponse = null;
		try {
			return getHttpClientResult(httpResponse, httpClient, httpPost);
		} finally {
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 
	 * @Title:getHttpClient
	 * @Description: TODO(创建自定义的httpclient对象)
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static CloseableHttpClient getHttpClient(String url) throws Exception {
		if (url.startsWith("https")) {
			// 采用绕过验证的方式处理https请求
			SSLContext sslcontext = SSLUtil.createIgnoreVerifySSL();

			// 设置协议http和https对应的处理socket链接工厂的对象
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslcontext)).build();
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			return HttpClients.custom().setConnectionManager(connManager).build();
		}
		return HttpClients.createDefault();
	}

	/**
	 * 
	 * @Title:packageHeader
	 * @Description: TODO(封装请求头)
	 * @param params
	 * @param httpMethod
	 */
	public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
		// 封装请求头
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				// 设置到请求头到HttpRequestBase对象中
				httpMethod.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * 
	 * @Title:packageParam
	 * @Description: TODO(封装请求参数)
	 * @param params
	 * @param httpMethod
	 * @throws UnsupportedEncodingException
	 */
	public static void packageParam(Object params, HttpEntityEnclosingRequestBase httpMethod)
			throws UnsupportedEncodingException {
		if (!org.springframework.util.ObjectUtils.isEmpty(params)) {
			if (ObjectUtils.toString(params).startsWith("{\"")) {
				// Json数据
				StringEntity stringEntity = new StringEntity(params.toString(), ENCODING);// 解决中文乱码问题
				stringEntity.setContentEncoding(ENCODING);
				stringEntity.setContentType("application/json");
				// 设置到请求的http对象中
				httpMethod.setEntity(stringEntity);
			} else {
				// Map数据
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				Set<Entry<String, String>> entrySet = com.zz.CTI.Util.ObjectUtils.objectToMap(params).entrySet();
				for (Entry<String, String> entry : entrySet) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				// 设置到请求的http对象中
				httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
			}
		}
	}

	/**
	 * 
	 * @Title:packageMultiPartParam
	 * @Description: TODO(封装MultiPart请求参数)
	 * @param params
	 * @param httpMethod
	 */
	public static void packageMultiPartParam(Map<String, ContentBody> params,
			HttpEntityEnclosingRequestBase httpMethod) {
		if (!MapUtils.isEmpty(params)) {
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			for (Entry<String, ContentBody> param : params.entrySet()) {
				multipartEntityBuilder.addPart(param.getKey(), param.getValue());
			}
			httpMethod.setEntity(multipartEntityBuilder.build());
		}
	}

	/**
	 * 
	 * @Title:getHttpClientResult
	 * @Description: TODO(获得响应结果)
	 * @param httpResponse
	 * @param httpClient
	 * @param httpMethod
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse,
			CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
		// 执行请求
		httpResponse = httpClient.execute(httpMethod);

		// 获取返回结果
		if (httpResponse != null && httpResponse.getStatusLine() != null) {
			String content = "";
			if (httpResponse.getEntity() != null) {
				content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
			}
			return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
		}
		return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * 
	 * @Title:release
	 * @Description: TODO(释放资源)
	 * @param httpResponse
	 * @param httpClient
	 * @throws IOException
	 */
	public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
		// 释放资源
		if (httpResponse != null) {
			httpResponse.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}

	/**
	 * 
	 * @Title:main
	 * @Description: TODO(测试)
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

	}
}