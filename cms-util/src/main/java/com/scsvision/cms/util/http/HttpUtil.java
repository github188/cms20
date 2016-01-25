package com.scsvision.cms.util.http;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.RandomUtils;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;

/**
 * HttpUtil
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月5日 下午8:01:54
 */
public class HttpUtil {

	public static HttpURLConnection multiPost(String url, InputStream in,
			String fileName) {
		// 换行
		String end = "\r\n";
		String twoHyphens = "--";
		long now = System.currentTimeMillis();
		String boundary = now + "" + RandomUtils.nextInt(100, 999);

		try {
			URL address = new URL(url);
			HttpURLConnection con = (HttpURLConnection) address
					.openConnection();
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + boundary);

			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"fileData\"; filename=\"" + fileName + "\"");
			ds.writeBytes(end);
			ds.writeBytes("Content-Type: application/octet-stream");
			ds.writeBytes(end);
			ds.writeBytes("Content-Transfer-Encoding: binary");
			ds.writeBytes(end);
			ds.writeBytes(end);

			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			while ((length = in.read(buffer)) != -1) {
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

			in.close();
			ds.flush();
			ds.close();

			return con;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.URL_ERROR, url
					+ " is not a valid address !");
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					e.getMessage());
		}
	}

	public static HttpURLConnection delete(String url) {
		try {
			URL address = new URL(url);
			HttpURLConnection con = (HttpURLConnection) address
					.openConnection();
			con.setUseCaches(false);
			con.setRequestMethod("DELETE");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			return con;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.URL_ERROR, url
					+ " is not a valid address !");
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					e.getMessage());
		}
	}
}
