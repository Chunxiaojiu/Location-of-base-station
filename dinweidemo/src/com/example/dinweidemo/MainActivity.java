package com.example.dinweidemo;

import com.example.sql.Db;
import com.example.sql.DbJieguo;
import com.example.sql.Dbbiaozhun;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
private EditText edt1,edt2;
	private Button btn1;
	private Db db = new Db(this);
	private DbJieguo dbjieguo = new DbJieguo(this);
	private Dbbiaozhun dbbiaozhun = new Dbbiaozhun(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		btn1 = (Button) findViewById(R.id.jizhan_get);
		btn1.setOnClickListener(new MyonClickListener());
		edt1 = (EditText) findViewById(R.id.time);
		edt2 = (EditText) findViewById(R.id.number);
		

	}

	public class MyonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.jizhan_get:
				String time = edt1.getText().toString();
				String number = edt2.getText().toString();
				if(TextUtils.isEmpty(time)){
					Toast.makeText(MainActivity.this, "获取周期未输入，请输入！", Toast.LENGTH_SHORT).show();
				}
				else if (TextUtils.isEmpty(number)) {
					Toast.makeText(MainActivity.this, "获取次数未输入，请输入！", Toast.LENGTH_SHORT).show();
				}
				else{
					SQLiteDatabase dbbiaodelet = dbbiaozhun.getWritableDatabase();
					dbbiaodelet.delete("biaozhun", null, null);
					dbbiaodelet.close();
					SQLiteDatabase dbjieguonull = dbjieguo.getWritableDatabase();
					dbjieguonull.delete("jieguo", null, null);
					dbjieguonull.close();
					SQLiteDatabase dbdelet = db.getWritableDatabase();
					dbdelet.delete("jizhan", null, null);
					String sql3 = "DELETE FROM sqlite_sequence";
					dbdelet.execSQL(sql3);
					dbdelet.close();
					
					
				Intent intent = new Intent(MainActivity.this, Get_List.class);
				intent.putExtra("TIME", time);
				intent.putExtra("number", number);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				}
				break;
			default:
				break;
			}
		}
	}

}