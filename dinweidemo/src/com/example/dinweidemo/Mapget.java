package com.example.dinweidemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class Mapget extends Activity {
	ImageView mapget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mapget);
		mapget = (ImageView) findViewById(R.id.map);
		/*
		 * http://api.map.baidu.com/staticimage/v2?ak=
		 * E4805d16520de693a3fe707cdc962045
		 * &center=116.403874,39.914888&width=500
		 * &height=500&zoom=11&paths=116.288891
		 * ,40.004261;116.487812,40.017524;116.525756
		 * ,39.967111;116.536105,39.872373
		 * |116.442968,39.797022;116.270494,39.851993
		 * ;116.275093,39.935251;116.383177,39.923743&pathStyles=0xff0000,5,1
		 */
		String str = "http://api.map.baidu.com/staticimage/v2?ak=d6snIPGxcpWyZxD6UnYCecMk";
	}
}
