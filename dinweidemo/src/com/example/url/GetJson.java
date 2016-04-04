package com.example.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

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
						+ cID + "&coord=bd09";
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
	 * 
	 */
	public static void Getstraddress(final double lat, final double lon,
			final Handler handler) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				BufferedReader readergd = null;
				StringBuffer sbfgd = new StringBuffer();
				String targetgd = "http://api.map.baidu.com/geocoder/v2/?";
				targetgd += "ak=d6snIPGxcpWyZxD6UnYCecMk"
						+ "&callback=renderReverse&location=" + lat + "," + lon
						+ "&output=json&pois=0";
				try {
					URL url = new URL(targetgd);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					readergd = new BufferedReader(new InputStreamReader(is,
							"UTF-8"));
					String strRead = null;
					while ((strRead = readergd.readLine()) != null) {
						sbfgd.append(strRead);
					}
					is.close();
					Message message = new Message();
					message.what = 3;
					message.obj = sbfgd.toString();
					Log.i("hjhjhjjh", sbfgd.toString());
					handler.sendMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
/*
 * 该函数用于返回标准路径经纬度，需要传入移动获取的开始结束点去进行路径选择，最好是短时间的
 * 基站数据存储，这样起点终点在一段路内，路段处于直线情况，可以采用经纬度差分判别去除错误点
 * 或者修正错误点
 *JSON数据格式： 数组中有用的为path标准路径经纬度
 *{
    "status": 0,
    "message": "ok",
    "type": 2,
    "info": {
        "copyright": {
            "text": "@2016 Baidu - Data",
            "imageUrl": "http://api.map.baidu.com/images/copyright_logo.png"
        }
    },
    "result": {
        "routes": [
            {
                "distance": 647,
                "duration": 642,
                "steps": [
                    {
                        "area": 0,
                        "direction": 3,
                        "distance": 2,
                        "duration": 1,
                        "instructions": "从<b>起点</b>向正东方向出发,沿<b>北湖北路</b>走10米,<b>向左前方转</b>",
                        "path": "113.34677438349,23.17164430521;113.34678516316,23.171640899767",
                        "pois": [],
                        "type": 36,
                        "stepOriginLocation": {
                            "lng": 113.34678067163,
                            "lat": 23.171659255936
                        },
                        "stepDestinationLocation": {
                            "lng": 113.34678516316,
                            "lat": 23.171640899767
                        },
                        "stepOriginInstruction": "从起点向正东方向出发 - 10米",
                        "stepDestinationInstruction": "向左前方转 - 120米"
                    },
                    {
                        "area": 0,
                        "direction": 3,
                        "distance": 116,
                        "duration": 92,
                        "instructions": "走120米,<b>向左前方转</b>",
                        "path": "113.34689565473,23.171871057699;113.34694506154,23.171890991965;113.34701512937,23.171890991965;113.34771490936,23.171691649168",
                        "pois": [],
                        "type": 0,
                        "stepOriginLocation": {
                            "lng": 113.34678516316,
                            "lat": 23.171640899767
                        },
                        "stepDestinationLocation": {
                            "lng": 113.34771490936,
                            "lat": 23.171691649168
                        },
                        "stepOriginInstruction": "",
                        "stepDestinationInstruction": "向左前方转 - 70米"
                    },
                    {
                        "area": 0,
                        "direction": 2,
                        "distance": 74,
                        "duration": 59,
                        "instructions": "走70米,<b>左转</b>进入<b>岷山路</b>",
                        "path": "113.34800506204,23.171771386323;113.3481946045,23.171851954025;113.34836528255,23.171972390188",
                        "pois": [],
                        "type": 0,
                        "stepOriginLocation": {
                            "lng": 113.34771490936,
                            "lat": 23.171691649168
                        },
                        "stepDestinationLocation": {
                            "lng": 113.34836528255,
                            "lat": 23.171972390188
                        },
                        "stepOriginInstruction": "",
                        "stepDestinationInstruction": "左转进入岷山路 - 10米"
                    },
                    {
                        "area": 0,
                        "direction": 10,
                        "distance": 13,
                        "duration": 10,
                        "instructions": "沿<b>岷山路</b>走10米,<b>右转</b>",
                        "path": "113.348275452,23.172002208504;113.34824490961,23.172012258687",
                        "pois": [],
                        "type": 36,
                        "stepOriginLocation": {
                            "lng": 113.34836528255,
                            "lat": 23.171972390188
                        },
                        "stepDestinationLocation": {
                            "lng": 113.34824490961,
                            "lat": 23.172012258687
                        },
                        "stepOriginInstruction": "",
                        "stepDestinationInstruction": "右转 - 390米"
                    },
                    {
                        "area": 0,
                        "direction": 1,
                        "distance": 387,
                        "duration": 309,
                        "instructions": "继续沿<b>岷山路</b>走390米,<b>右转</b>",
                        "path": "113.3483248588,23.17212264453;113.34838504527,23.172242332914;113.34841558766,23.172332867428;113.34842546902,23.172402637103;113.34848565549,23.17272241431;113.34849643515,23.173052905317;113.34852697754,23.174352767697;113.34846768938,23.174662656326;113.34799787559,23.174912659757;113.34787840096,23.17505219635;113.34776790938,23.175111997702",
                        "pois": [],
                        "type": 36,
                        "stepOriginLocation": {
                            "lng": 113.34824490961,
                            "lat": 23.172012258687
                        },
                        "stepDestinationLocation": {
                            "lng": 113.34776790938,
                            "lat": 23.175111997702
                        },
                        "stepOriginInstruction": "",
                        "stepDestinationInstruction": "右转 - 50米"
                    },
                    {
                        "area": 0,
                        "direction": 1,
                        "distance": 54,
                        "duration": 43,
                        "instructions": "走50米,<b>左转</b>进入<b>Ｇ３２４</b>",
                        "path": "113.34781821449,23.175182596486;113.34789816368,23.175312082862;113.34800865526,23.175552201267",
                        "pois": [],
                        "type": 0,
                        "stepOriginLocation": {
                            "lng": 113.34776790938,
                            "lat": 23.175111997702
                        },
                        "stepDestinationLocation": {
                            "lng": 113.34800865526,
                            "lat": 23.175552201267
                        },
                        "stepOriginInstruction": "",
                        "stepDestinationInstruction": "左转进入Ｇ３２４ - 10米"
                    },
                    {
                        "area": 0,
                        "direction": 9,
                        "distance": 1,
                        "duration": 0,
                        "instructions": "沿<b>Ｇ３２４</b>走10米,到达<b>终点</b>",
                        "path": "113.34799248576,23.175552948781",
                        "pois": [],
                        "type": 3,
                        "stepOriginLocation": {
                            "lng": 113.34800865526,
                            "lat": 23.175552201267
                        },
                        "stepDestinationLocation": {
                            "lng": 113.34799158746,
                            "lat": 23.175543895553
                        },
                        "stepOriginInstruction": "",
                        "stepDestinationInstruction": "到达终点"
                    }
                ],
                "originLocation": {
                    "lng": 113.34678067163,
                    "lat": 23.171659255936
                },
                "destinationLocation": {
                    "lng": 113.34799158746,
                    "lat": 23.175543895553
                }
            }
        ],
        "origin": {
            "area_id": 257,
            "cname": "广州市",
            "uid": "",
            "wd": "",
            "originPt": {
                "lng": 113.34677887502,
                "lat": 23.171655102956
            }
        },
        "destination": {
            "area_id": 257,
            "cname": "广州市",
            "uid": "",
            "wd": "",
            "destinationPt": {
                "lng": 113.34799158746,
                "lat": 23.17554223441
            }
        }
    }
}
 */
	public static void Direction(final double startlat, final double startlon,
			final double overlat, final double overlon, final Handler handler) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				BufferedReader readergd = null;
				StringBuffer sbfgd = new StringBuffer();
				String targetgd = "http://api.map.baidu.com/direction/v1?mode=walking&";
				targetgd += "origin=" + startlat + "," + startlon
						+ "&destination=" + overlat + "," + overlon
						+ "&region=广州&output=json&ak=d6snIPGxcpWyZxD6UnYCecMk";
				URL url;
				try {
					url = new URL(targetgd);

					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					readergd = new BufferedReader(new InputStreamReader(is,
							"UTF-8"));
					String strRead = null;
					while ((strRead = readergd.readLine()) != null) {
						sbfgd.append(strRead);
					}
					is.close();
					Message message = new Message();
					message.what = 4;
					message.obj = sbfgd.toString();
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
	/*
	 * 下面函数用于获取百度静态图，该函数需要传入imageview控件，和地图地址。
	 */
	public static void getpic(final ImageView pic,final String pic_url){
		new Thread(new Runnable() {
			@Override
			public void run() {
			try {
				HttpURLConnection con=(HttpURLConnection) new URL(pic_url).openConnection();
				con.connect();
				InputStream is=con.getInputStream();
				Bitmap picmap=BitmapFactory.decodeStream(is);
				pic.setImageBitmap(picmap);
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			}
		}).start();
	}
}
