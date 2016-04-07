package com.example.dinweidemo;

import com.example.sql.DbJieguo;
import com.example.sql.Dbbiaozhun;
import com.example.url.GetJson;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Mapget extends Activity {
	private ImageView mapget;
	private Button getdian, getbiaozhun;
	private DbJieguo dbjieguo = new DbJieguo(this);
	private Dbbiaozhun dbbiaozhun = new Dbbiaozhun(this);
	Handler imagehandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 9:
				mapget.setImageBitmap((Bitmap) msg.obj);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mapget);
		mapget = (ImageView) findViewById(R.id.map);
		getdian = (Button) findViewById(R.id.getdian);
		getbiaozhun = (Button) findViewById(R.id.getbaiozhun);
		getdian.setOnClickListener(new MyonclickListener());
		getbiaozhun.setOnClickListener(new MyonclickListener());
	}

	public class MyonclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.getdian:
				String str = "http://api.map.baidu.com/staticimage/v2?ak=d6snIPGxcpWyZxD6UnYCecMk&width=500&height=500&zoom=18&paths=";
				SQLiteDatabase jieguoread = dbjieguo.getReadableDatabase();
				Cursor jieguoc = jieguoread.query("jieguo", null, null, null,
						null, null, null);
				while (jieguoc.moveToNext()) {
					str = str
							+ jieguoc.getString(jieguoc
									.getColumnIndex("GETLON"))
							+ ","
							+ jieguoc.getString(jieguoc
									.getColumnIndex("GETLAT")) + ";";

				}
				str = str + "&pathStyles=0xff0000,5,1";

				Log.i("JIEGUObiao", str);
				GetJson.getpic(mapget, str, imagehandler);

				break;
			case R.id.getbaiozhun:
				String str2 = "http://api.map.baidu.com/staticimage/v2?ak=d6snIPGxcpWyZxD6UnYCecMk&width=500&height=500&zoom=18&paths=";
				SQLiteDatabase biaozhunread = dbbiaozhun.getReadableDatabase();
				Cursor biaozhunc = biaozhunread.query("biaozhun", null, null,
						null, null, null, null);
				while (biaozhunc.moveToNext()) {
					str2 = str2
							+ biaozhunc.getString(biaozhunc
									.getColumnIndex("TRUELATLON")) + ";";

				}
				str2 = str2 + "&pathStyles=0x66ccff,5,1";
				Log.i("biaozhunc", str2);

				GetJson.getpic(mapget, str2, imagehandler);
				break;
			
			default:
				break;
			}
		}
	}


}
