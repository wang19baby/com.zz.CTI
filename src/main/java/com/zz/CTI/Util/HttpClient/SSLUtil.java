package com.zz.CTI.Util.HttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;

import com.zz.CTI.Util.Logs;

/**
 * 
 * @Title:SSLUtil
 * @Description:TODO(证书工具类)
 * @Company: 
 * @author zhou.zhang
 * @date 2018年7月6日 上午11:12:23
 */
public class SSLUtil {

	/**
	 * 
	 * @Title:createIgnoreVerifySSL
	 * @Description: TODO(绕过验证)
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}
	
	/**
	 * 
	 * @Title:getTrustStoreSSLContext
	 * @Description: TODO(设置信任自签名证书)
	 * @param keyStorePath 密钥库路径
	 * @param keyStorePass 密钥库密码
	 * @return
	 */
	public static SSLContext getTrustStoreSSLContext(String keyStorePath, String keyStorePass) {
		SSLContext sc = null;
		try {
			sc = SSLContexts.custom()
					.loadTrustMaterial(getTrustKeyStore(keyStorePath, keyStorePass), new TrustSelfSignedStrategy())
					.build();
		} catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
			Logs.error("设置信任自签名证书异常：" + e.getMessage());
		}
		return sc;
	}

	/**
	 * 
	 * @Title:getSSLContext
	 * @Description: TODO(初始化双向认证SSLContext)
	 * @param keyStorePath
	 * @param keyStorePass
	 * @param trustStorePath
	 * @param trustStorePass
	 * @return
	 */
	public static SSLContext getSSLContext(String keyStorePath, String keyStorePass, String trustStorePath,
			String trustStorePass) {
		SSLContext sslContext = null;
		try {
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(getClientKeyStore(keyStorePath, keyStorePass), keyStorePass.toCharArray());
			KeyManager[] keyManagers = kmf.getKeyManagers();

			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
			trustManagerFactory.init(getTrustKeyStore(trustStorePath, trustStorePass));
			TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(keyManagers, trustManagers, new SecureRandom());
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
		} catch (Exception e) {
			Logs.error("SSLContext 初始化异常：" + e.getMessage());
		}
		return sslContext;
	}
	
	/**
	 * 
	 * @Title:getClientKeyStore
	 * @Description: TODO(获取客户端证书签名)
	 * @param keyStorePath
	 * @param keyStorePass
	 * @return
	 */
	public static KeyStore getClientKeyStore(String keyStorePath, String keyStorePass) {
		KeyStore clientKeySotre = null;
		FileInputStream fis = null;
		try {
			clientKeySotre = KeyStore.getInstance("PKCS12");
			fis = new FileInputStream(new File(keyStorePath));
			clientKeySotre.load(fis, keyStorePass.toCharArray());
		} catch (Exception e) {
			Logs.error("获取客户端证书签名异常：" + e.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				Logs.error("关闭客户端证书文件流异常：" + e.getMessage());
			}
		}
		return clientKeySotre;
	}
	
	/**
	 * 
	 * @Title:getTrustKeyStore
	 * @Description: TODO(获取服务器证书签名)
	 * @param keyStorePath
	 * @param keyStorePass
	 * @return
	 */
	public static KeyStore getTrustKeyStore(String keyStorePath, String keyStorePass) {
		KeyStore trustKeyStore = null;
		FileInputStream fis = null;
		try {
			trustKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			fis = new FileInputStream(new File(keyStorePath));
			trustKeyStore.load(fis, keyStorePass.toCharArray());
		} catch (Exception e) {
			Logs.error("获取服务器证书签名异常：" + e.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				Logs.error("关闭服务器证书文件流异常：" + e.getMessage());
			}
		}
		return trustKeyStore;
	}
}
