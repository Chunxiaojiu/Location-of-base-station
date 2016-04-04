package com.example.dinweidemo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.JieguoModel;
import com.example.adapter.Jieguoadapter;
import com.example.sql.Db;

import android.app.Activity;
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
						list.add(new JieguoModel(LAT, LON,RSSI, jieguo));
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
				JSONArray jsarr = new JSONArray();
				String jieguo_text;
				SQLiteDatabase dbread = db.getReadableDatabase();
				Cursor c = dbread.query("jizhan", null, null, null, null, null,
						null);
				while (c.moveToNext()) {
					String rssi = c.getString(c.getColumnIndex("RSSI"));
					String lat = c.getString(c.getColumnIndex("LAT"));
					String lon = c.getString(c.getColumnIndex("LON"));

					if (Integer.parseInt(rssi) > 0) {
						Log.i("title", lat + lon);
						n = n + 1;
						m = Integer.parseInt(rssi);
						jieguo_text = "第" + n + "次定位有" + rssi + "个基站经纬度为：";
						lat = "";
						lon = "";
						rssi = "";
					} else {
						jieguo_text = "";
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

		}

	}
}