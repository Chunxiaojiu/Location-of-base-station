package com.example.adapter;

public class JieguoModel {
	private String lat;
	private String lon;
	private String jianjie;

	public JieguoModel(String lat, String lon, String jianjie) {
		setLat(lat);
		setLon(lon);
		setJianjie(jianjie);
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getJianjie() {
		return jianjie;
	}

	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}

}
