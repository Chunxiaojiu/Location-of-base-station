package com.example.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
							"6f0524873f93999561419da6b1efc51e"); // ��Ӳ���
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
 * �ú������ڷ��ر�׼·����γ�ȣ���Ҫ�����ƶ���ȡ�Ŀ�ʼ������ȥ����·��ѡ������Ƕ�ʱ���
 * ��վ���ݴ洢����������յ���һ��·�ڣ�·�δ���ֱ����������Բ��þ�γ�Ȳ���б�ȥ�������
 * �������������
 *JSON���ݸ�ʽ�� ���������õ�Ϊpath��׼·����γ��
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
                        "instructions": "��<b>���</b>�������������,��<b>������·</b>��10��,<b>����ǰ��ת</b>",
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
                        "stepOriginInstruction": "������������������ - 10��",
                        "stepDestinationInstruction": "����ǰ��ת - 120��"
                    },
                    {
                        "area": 0,
                        "direction": 3,
                        "distance": 116,
                        "duration": 92,
                        "instructions": "��120��,<b>����ǰ��ת</b>",
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
                        "stepDestinationInstruction": "����ǰ��ת - 70��"
                    },
                    {
                        "area": 0,
                        "direction": 2,
                        "distance": 74,
                        "duration": 59,
                        "instructions": "��70��,<b>��ת</b>����<b>�ɽ·</b>",
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
                        "stepDestinationInstruction": "��ת�����ɽ· - 10��"
                    },
                    {
                        "area": 0,
                        "direction": 10,
                        "distance": 13,
                        "duration": 10,
                        "instructions": "��<b>�ɽ·</b>��10��,<b>��ת</b>",
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
                        "stepDestinationInstruction": "��ת - 390��"
                    },
                    {
                        "area": 0,
                        "direction": 1,
                        "distance": 387,
                        "duration": 309,
                        "instructions": "������<b>�ɽ·</b>��390��,<b>��ת</b>",
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
                        "stepDestinationInstruction": "��ת - 50��"
                    },
                    {
                        "area": 0,
                        "direction": 1,
                        "distance": 54,
                        "duration": 43,
                        "instructions": "��50��,<b>��ת</b>����<b>�ǣ�����</b>",
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
                        "stepDestinationInstruction": "��ת����ǣ����� - 10��"
                    },
                    {
                        "area": 0,
                        "direction": 9,
                        "distance": 1,
                        "duration": 0,
                        "instructions": "��<b>�ǣ�����</b>��10��,����<b>�յ�</b>",
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
                        "stepDestinationInstruction": "�����յ�"
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
            "cname": "������",
            "uid": "",
            "wd": "",
            "originPt": {
                "lng": 113.34677887502,
                "lat": 23.171655102956
            }
        },
        "destination": {
            "area_id": 257,
            "cname": "������",
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
						+ "&region=����&output=json&ak=d6snIPGxcpWyZxD6UnYCecMk";
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
}
