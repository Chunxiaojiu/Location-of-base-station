package com.example.dinweidemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.JizhanAdapter;
import com.example.adapter.Jizhanmodel;
import com.example.sql.Db;
import com.example.sql.DbJieguo;
import com.example.sql.Dbbiaozhun;
import com.example.url.GetJson;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

public class Get_List extends Activity {
	private ListView newlist;
	private ProgressBar ps;
	private List<Jizhanmodel> list;
	private JizhanAdapter jzadapter;
	private int[][] InfoBuf = new int[7][4];
	private Button btn_get,countbutton;
	private static int num, count;
	private static int n, processnum = 0;
	private Db db = new Db(this);
	
	/*
	 * 主函数数据处理
	 */
	Handler getmsg = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String jsonstr = (String) msg.obj;
				Log.i("PATH", jsonstr.toString());
				try {
					JSONArray jsarr = new JSONArray(jsonstr);
					for (int i = 0; i < jsarr.length(); i++) {
						JSONObject jsobj = jsarr.getJSONObject(i);
						String MCC = (String) jsobj.getString("MCC");
						String MNC = (String) jsobj.getString("MNC");
						String CID = (String) jsobj.getString("CID");
						String LAC = (String) jsobj.getString("LAC");
						String RSSI = (String) jsobj.getString("RSSI");
						list.add(new Jizhanmodel(MCC, MNC, CID, LAC, RSSI, "?",
								"?", "?"));

					}
					jzadapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			/*
			 * 获取对应的经纬度，存入用于更新数据库；
			 */
			case 2:
				String json_jiwei = (String) msg.obj;
				
				ps.setProgress(10 * processnum / count);//进度条专用！
				try {
					JSONObject jsob1 = new JSONObject(json_jiwei);
					double lon = jsob1.getDouble("lon");
					double lat = jsob1.getDouble("lat");
					String adr = jsob1.getString("address");
					SQLiteDatabase dbwrite = db.getWritableDatabase();
					ContentValues jinwei = new ContentValues();
					jinwei.put("LAT", lat);
					jinwei.put("LON", lon);
					String[] charn = { String.valueOf(n + 1) };
					dbwrite.update("jizhan", jinwei, "_id=?", charn);
					dbwrite.close();
					SQLiteDatabase dbread = db.getReadableDatabase();
					Cursor c = dbread.query("jizhan", null, null, null, null,
							null, null);
					c.moveToPosition(n);
					
						String MCC = c.getString(c.getColumnIndex("MCC"));
						String MNC = c.getString(c.getColumnIndex("MNC"));
						String CID = c.getString(c.getColumnIndex("CID"));
						String LAC = c.getString(c.getColumnIndex("LAC"));
						String RSSI = c.getString(c.getColumnIndex("RSSI"));
						String LAT = c.getString(c.getColumnIndex("LAT"));
						String LON = c.getString(c.getColumnIndex("LON"));
						list.set(n, new Jizhanmodel(MCC, MNC, CID, LAC, RSSI,
								LAT, LON, adr));
						if (c.moveToNext()) {
						n = n + 1;
						processnum = processnum + 1;
					}
					dbread.close();
					jzadapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			
			default:
				break;
			}
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_jizhan);
		Intent intent = getIntent();
		String timeinstring = intent.getStringExtra("TIME");
		String numberinstring = intent.getStringExtra("number");
		int time = Integer.parseInt(timeinstring);
		int number = Integer.parseInt(numberinstring);
		num = number;
		count = number;
		/*
		 * 定时刷新获取所需的基站数据
		 */
		final Timer tGet = new Timer();
		tGet.schedule(new TimerTask() {

			@Override
			public void run() {
				
				JSONArray jsarray = new JSONArray();
				try {JSONObject jsobj = new JSONObject();
					SQLiteDatabase dbwrite = db.getWritableDatabase();
					TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					int type = tm.getNetworkType();// 获取网络类型
					String operator = tm.getNetworkOperator();
					int mcc = Integer.parseInt(operator.substring(0, 3));
					int mnc = Integer.parseInt(operator.substring(3));
					InfoBuf[0][0] = mcc;
					InfoBuf[0][1] = mnc;

					if (type != 0) {
						// 中国移动和中国联通获取LAC、CID的方式
						GsmCellLocation location = (GsmCellLocation) tm
								.getCellLocation();
						InfoBuf[0][2] = location.getCid();
						InfoBuf[0][3] = location.getLac();
						
						jsobj.put("MCC", mcc);
						jsobj.put("MNC", mnc);
						jsobj.put("CID", InfoBuf[0][2]);
						jsobj.put("LAC", InfoBuf[0][3]);
						jsobj.put("RSSI", num); // 验证数据的不一样
						jsarray.put(jsobj);
						List<NeighboringCellInfo> infos = tm
								.getNeighboringCellInfo();
						ContentValues cv = new ContentValues();
						cv.put("MCC", mcc);
						cv.put("MNC", mnc);
						cv.put("CID", InfoBuf[0][2]);
						cv.put("LAC", InfoBuf[0][3]);
						cv.put("RSSI", infos.size());
						long id = dbwrite.insert("jizhan", null, cv);
						Log.i("id", String.valueOf(id));
						char i = 0x40;
						
						for (NeighboringCellInfo info : infos) {
							i = (char) (i + 1);
							// 邻居小区号
							long cid = info.getCid();
							
							// 位置区域码
							int lac = info.getLac();
							// 获取邻居小区信号强度
							int rssi = -113 + 2 * info.getRssi();
							JSONObject jsobj1 = new JSONObject();
							jsobj1.put("MCC", mcc);
							jsobj1.put("MNC", mnc);
							jsobj1.put("CID", cid);
							jsobj1.put("LAC", lac);
							jsobj1.put("RSSI", rssi);
							jsarray.put(jsobj1);

							cv = new ContentValues();
							cv.put("MCC", mcc);
							cv.put("MNC", mnc);
							cv.put("CID", cid);
							cv.put("LAC", lac);
							cv.put("RSSI", rssi);
							dbwrite.insert("jizhan", null, cv);

						}
						Message msg = new Message();
						msg.obj = jsarray.toString();
						msg.what = 1;
						getmsg.sendMessage(msg);

						Log.i("JSON", jsarray.toString());
					}
					num = num - 1;
					if (num == 0) {
						tGet.cancel();
						dbwrite.close();

						SQLiteDatabase dbread = db.getReadableDatabase();
						Cursor c = dbread.query("jizhan", null, null, null,
								null, null, null);
						while (c.moveToNext()) {
							String str = c.getString(c.getColumnIndex("MCC"))
									+ c.getString(c.getColumnIndex("MNC"))
									+ c.getString(c.getColumnIndex("CID"))
									+ c.getString(c.getColumnIndex("LAC"))
									+ c.getString(c.getColumnIndex("RSSI"));
							Log.i("DB", str);
						}

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, 0, time);

		newlist = (ListView) findViewById(R.id.result);
		list = new ArrayList<Jizhanmodel>();
		jzadapter = new JizhanAdapter(this, list);
		newlist.setAdapter(jzadapter);
		btn_get = (Button) findViewById(R.id.get_lon);
		btn_get.setOnClickListener(new MyonclickListener());
		countbutton = (Button) findViewById(R.id.count);
		countbutton.setOnClickListener(new MyonclickListener());
		ps = (ProgressBar) findViewById(R.id.processbar);
	}

	public class MyonclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.get_lon:

				final Timer t = new Timer();
				t.schedule(new TimerTask() {
					SQLiteDatabase dbget = db.getReadableDatabase();
					Cursor c = dbget.query("jizhan", null, null, null, null,
							null, null);

					@Override
					public void run() {

						if (c.moveToNext()) {
							String MCC = c.getString(c.getColumnIndex("MCC"));
							String MNC = c.getString(c.getColumnIndex("MNC"));
							String CID = c.getString(c.getColumnIndex("CID"));
							String LAC = c.getString(c.getColumnIndex("LAC"));
							String RSSI = c.getString(c.getColumnIndex("RSSI"));
							Log.i("getdata", MCC + MNC + CID + LAC + RSSI);
							GetJson.Getjizhan(MCC, MNC, LAC, CID, getmsg);
						} else {
							t.cancel();
							dbget.close();
						}
					}

				}, 0, 1000);

				break;
			case R.id.count:
				Intent intent = new Intent(Get_List.this,Direction.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				break;
			default:
				break;
			}

		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		processnum = 0;
		n = 0;
	}
}
