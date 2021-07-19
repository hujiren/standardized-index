package com.sutpc.its.tools.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class NetUtils {

	public static String getUrlResponseString(String _url) {
		try {
			URL url = new URL(_url);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			// GET Request Define:
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

			// Connection Response From Test Servlet
			// System.out.println("Connection Response From Test Servlet");
			InputStream inputStream = urlConnection.getInputStream();

			// Convert Stream to String

			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			StringBuilder result = new StringBuilder();
			String line = null;
			try {
				while ((line = bufferedReader.readLine()) != null) {
					result.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

				try {
					inputStreamReader.close();
					inputStream.close();
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// System.out.println(result);

			return result.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static String sendPost(String url, String param) {

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
