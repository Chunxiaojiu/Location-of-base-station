package com.example.dinweidemo;

import java.util.ArrayList;
import java.util.List;

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
import android.view.Window;
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
					JSONObject jsjieguo = new JSONObject(jsonstr);
					String jieguo = jsjieguo.getString("jieguo");
					String LAT = jsjieguo.getString("LAT");
					String LON = jsjieguo.getString("LON");
					list.add(new JieguoModel(LAT, LON, jieguo));
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
						jieguo_text = "第" + n + "次定位有" + rssi + "个基站经纬度为：";
					} else {
						jieguo_text = "";
					}
					JSONObject jsobj = new JSONObject();
					try {
						jsobj.put("LAT", lat);
						jsobj.put("LON", lon);
						jsobj.put("jieguo", jieguo_text);
						Message msg = new Message();
						msg.obj = jsobj.toString();
						msg.what = 5;
						Log.i("one111111", jsobj.toString());
						getjieguo.sendMessage(msg);
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

	}
}
