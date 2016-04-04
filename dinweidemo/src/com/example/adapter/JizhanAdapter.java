package com.example.adapter;

import java.util.List;

import com.example.dinweidemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JizhanAdapter extends BaseAdapter {
	Context context;
	List<Jizhanmodel> list;

	public JizhanAdapter(Context context, List<Jizhanmodel> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Jizhanmodel getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item,
					null);
		}
		TextView mcc = (TextView) convertView.findViewById(R.id.mcc);
		TextView mnc = (TextView) convertView.findViewById(R.id.mnc);
		TextView cid = (TextView) convertView.findViewById(R.id.cid);
		TextView lac = (TextView) convertView.findViewById(R.id.lac);
		TextView rssi = (TextView) convertView.findViewById(R.id.rssi);
		TextView lat = (TextView) convertView.findViewById(R.id.lat);
		TextView lon = (TextView) convertView.findViewById(R.id.lon);
		TextView adress = (TextView) convertView.findViewById(R.id.adress);
		Jizhanmodel jizhanlist = list.get(position);
		mcc.setText(jizhanlist.getMCC());
		mnc.setText(jizhanlist.getMNC());
		cid.setText(jizhanlist.getCID());
		lac.setText(jizhanlist.getLAC());
		rssi.setText(jizhanlist.getRSSI());
		lat.setText(jizhanlist.getLAT());
		lon.setText(jizhanlist.getLON());
		adress.setText(jizhanlist.getADRESS());
		return convertView;
	}

}
