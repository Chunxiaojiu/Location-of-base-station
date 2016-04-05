package com.example.dinweidemo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.JieguoModel;
import com.example.adapter.Jieguoadapter;
import com.example.sql.Db;
import com.example.sql.DbJieguo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class Direction extends Activity {
	private ListView jieguo_list;
	private List<JieguoModel> list;
	private Jieguoadapter jieguoadapt;
	private Button btn_getmap;
	private Db db = new Db(this);
	private DbJieguo dbjieguo = new DbJieguo(this);

	Handler getjieguo = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 5:
				String jsonstr = (String) msg.obj;
				Log.i("123456789", jsonstr);
				try {
					JSONArray jsjieguo = new JSONArray(jsonstr);
					for (int i = 0; i < jsjieguo.length(); i++) {
						JSONObject jsobj = jsjieguo.getJSONObject(i);
						String jieguo = jsobj.getString("jieguo");
						String LAT = jsobj.getString("LAT");
						String LON = jsobj.getString("LON");
						String RSSI = jsobj.getString("RSSI");
						list.add(new JieguoModel(LAT, LON, RSSI, jieguo));
					}
					jieguoadapt.notifyDataSetChanged();
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
		setContentView(R.layout.jieguo);
		new Thread(new Runnable() {

			@Override
			public void run() {
				Log.i("DB", "qidong");
				int n = 0;
				int m = 0;
				double getlat = 0;
				double getlon = 0;
				double getrssi = 0;
				JSONArray jsarr = new JSONArray();
				String jieguo_text;
				SQLiteDatabase dbread = db.getReadableDatabase();
				Cursor c = dbread.query("jizhan", null, null, null, null, null,
						null);
				while (c.moveToNext()) {
					double rssi = c.getDouble(c.getColumnIndex("RSSI"));
					double lat = c.getDouble(c.getColumnIndex("LAT"));
					double lon = c.getDouble(c.getColumnIndex("LON"));

					if (rssi > 0) {
						n = n + 1;
						m = (int) rssi;
						jieguo_text = "第" + n + "次定位有" + (int) rssi + "个基站经纬度为：";

					} else {
						jieguo_text = "";
						getlat = getlat - lat / rssi;
						getlon = getlon - lon / rssi;
						getrssi = getrssi - 1 / rssi;
						Log.i("LAT+LON", String.valueOf(getlat) + "=========="
								+ String.valueOf(getlon)+String.valueOf(getrssi));
					}
					JSONObject jsobj = new JSONObject();
					try {
						jsobj.put("LAT", lat);
						jsobj.put("LON", lon);
						jsobj.put("RSSI", rssi);
						jsobj.put("jieguo", jieguo_text);
						jsarr.put(jsobj);

						if (m == 0) {
							Message msg = new Message();
							msg.obj = jsarr.toString();
							msg.what = 5;
							Log.i("one111111", jsarr.toString());
							getjieguo.sendMessage(msg);
							jsarr = new JSONArray();
							getlat = getlat / getrssi;
							getlon = getlon / getrssi;
							Log.i("jisuanjieguo", String.valueOf(getlat) + "=========="
									+ String.valueOf(getlon));
							SQLiteDatabase dbjieguow = dbjieguo
									.getWritableDatabase();
							ContentValues cv = new ContentValues();
							cv.put("GETLAT", getlat);
							cv.put("GETLON", getlon);
							dbjieguow.insert("jieguo", null, cv);
							getlat = 0;
							getlon = 0;
							getrssi = 0;
							
						}
						m = m - 1;
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}
		}).start();
		jieguo_list = (ListView) findViewById(R.id.list_jieguo);
		list = new ArrayList<JieguoModel>();
		jieguoadapt = new Jieguoadapter(this, list);
		jieguo_list.setAdapter(jieguoadapt);
		btn_getmap = (Button) findViewById(R.id.get_map);
		btn_getmap.setOnClickListener(new MyonclickListener());

	}

	public class MyonclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.get_map:
				SQLiteDatabase jieguoread = dbjieguo.getReadableDatabase();
				Cursor c = jieguoread.query("jieguo", null, null, null, null,
						null, null);
				while (c.moveToNext()) {
					String str = c.getString(c.getColumnIndex("GETLAT"))
							+ "++++++++++"
							+ c.getString(c.getColumnIndex("GETLON"));
					Log.i("JIEGUObiao", str);
				}
				break;

			default:
				break;
			}
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		SQLiteDatabase dbdelet = dbjieguo.getWritableDatabase();
		dbdelet.delete("jieguo", null, null);
		String sql = "DELETE FROM sqlite_sequence";
		dbdelet.execSQL(sql);
		dbdelet.close();
	}
}