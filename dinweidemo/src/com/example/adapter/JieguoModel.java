package com.example.adapter;

public class JieguoModel {
	private String lat;
	private String lon;
	private String jianjie;
private String rssi;
	public JieguoModel(String lat, String lon,String rssi, String jianjie) {
		setLat(lat);
		setLon(lon);
		setRssi(rssi);
		setJianjie(jianjie);
	}

	public String getRssi() {
		return rssi;
	}

	public void setRssi(String rssi) {
		this.rssi = rssi;
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
