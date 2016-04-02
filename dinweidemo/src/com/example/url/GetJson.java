package com.example.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;

public class GetJson {
	public static void Getjizhan(final String mCC, final String mNC,
			final String lAC, final String cID, final Handler handler) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				BufferedReader reader = null;
				StringBuffer sbf = new StringBuffer();
				String target = "http://apis.baidu.com/lbs_repository/cell/query?";
				target += "mcc=" + mCC + "&mnc=" + mNC + "&lac=" + lAC + "&ci="
						+  cID+ "&coord=bd09";
				try {
					URL url = new URL(target);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.addRequestProperty("apikey",
							"6f0524873f93999561419da6b1efc51e"); // 添加参数
					conn.connect();
					InputStream is = conn.getInputStream();
					reader = new BufferedReader(new InputStreamReader(is,
							"UTF-8"));
					String strRead = null;
					while ((strRead = reader.readLine()) != null) {
						sbf.append(strRead);
						sbf.append("\r\n");
					}
					is.close();
					
					
					
					Message message = new Message();
					message.obj = sbf.toString();
					message.what = 2;
					handler.sendMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
/*
 * 下面代码有问题，没有线程的网络耗时操作
 */
	public static void Getstraddress(double lat, double lon,
			final Handler handler) {
		BufferedReader readergd = null;
		StringBuffer sbfgd = new StringBuffer();
		String targetgd = "http://api.map.baidu.com/geocoder/v2/?";
		targetgd += "ak=d6snIPGxcpWyZxD6UnYCecMk"
				+ "&callback=renderReverse&location=" + lat + "," + lon
				+ "&output=json&pois=0";
		try {
			URL url = new URL(targetgd);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			readergd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = readergd.readLine()) != null) {
				sbfgd.append(strRead);
			}
			is.close();
			Message message = new Message();
			message.what = 3;
			message.obj = sbfgd.toString();
			handler.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
