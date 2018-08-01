package com.zz.CTI.Util.HttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.zz.CTI.Util.Logs;

import net.sf.json.JSONObject;

/**
 * 
 * @Title:Md5Util
 * @Description:TODO(MD5加密工具)
 * @Company: 
 * @author zhou.zhang
 * @date 2018年7月6日 上午11:11:20
 */
public class Md5Util {
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };
	private static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			Logs.error("MD5FileUtil messagedigest初始化失败: " + e.getMessage());
		}
	}

	/**
	 * 
	 * @Title:getFileMD5String
	 * @Description: TODO(获取文件的MD5值)
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException {
		return getFileMD5String(file, 0, -1);
	}

	/**
	 * 
	 * @Title:getFileMD5String
	 * @Description: TODO(获取文件的MD5值)
	 * @param file
	 * @param start
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file, long start, long length) throws IOException {
		@SuppressWarnings("resource")
		FileChannel ch = new FileInputStream(file).getChannel();

		long remain = file.length() - start;

		if (length < 0 || length > remain) {
			length = remain;
		}

		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, start, length);

		messagedigest.update(byteBuffer);
		return bufferToHex(messagedigest.digest());
	}

	/**
	 * 
	 * @Title:getMD5String
	 * @Description: TODO(获取字符串的MD5加密的结果)
	 * @param s
	 * @return
	 */
	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
	
	/**
	 * 
	 * @Title:main
	 * @Description: TODO(测试)
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String url = "http://www.dh3t.com/json/sms/Balance";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("account", "dh82741");
		jsonObject.put("password", getMD5String("85p152D1"));
		System.out.println(HttpClientUtil.doPost(url, jsonObject));
	}
}
