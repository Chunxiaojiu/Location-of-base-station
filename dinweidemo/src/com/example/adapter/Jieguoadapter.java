package com.example.adapter;

import java.util.List;

import com.example.dinweidemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Jieguoadapter extends BaseAdapter {
	Context context;
	List<JieguoModel> list;

	public Jieguoadapter(Context context, List<JieguoModel> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.jieguo_item,
					null);
		}
		TextView jieguo_lat = (TextView) convertView
				.findViewById(R.id.jieguo_lat);
		TextView jieguo_lon = (TextView) convertView
				.findViewById(R.id.jieguo_lon);
		TextView jieguo_text = (TextView) convertView
				.findViewById(R.id.jieguo_text);
		JieguoModel jieguolist = list.get(position);
		jieguo_lat.setText(jieguolist.getLat());
		jieguo_lon.setText(jieguolist.getLon());
		jieguo_text.setText(jieguolist.getJianjie());

		return convertView;
	}

}
