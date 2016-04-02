package com.example.adapter;

public class Jizhanmodel {
	private String MCC;
	private String MNC;
	private String CID;
	private String LAC;
	private String RSSI;
	private String LAT;
	private String LON;
	private String ADRESS;

	public Jizhanmodel(String mCC, String mNC, String cID, String lAC,
			String rSSI, String lAT, String lON, String aDRESS) {
		setMCC(mCC);
		setMNC(mNC);
		setCID(cID);
		setLAC(lAC);
		setRSSI(rSSI);
		setADRESS(aDRESS);
		setLAT(lAT);
		setLON(lON);
	}

	public String getLAT() {
		return LAT;
	}

	public void setLAT(String lAT) {
		LAT = lAT;
	}

	public String getLON() {
		return LON;
	}

	public void setLON(String lON) {
		LON = lON;
	}

	public String getADRESS() {
		return ADRESS;
	}

	public void setADRESS(String aDRESS) {
		ADRESS = aDRESS;
	}

	public String getMCC() {
		return MCC;
	}

	public void setMCC(String mCC) {
		MCC = mCC;
	}

	public String getMNC() {
		return MNC;
	}

	public void setMNC(String mNC) {
		MNC = mNC;
	}

	public String getCID() {
		return CID;
	}

	public void setCID(String cID) {
		CID = cID;
	}

	public String getLAC() {
		return LAC;
	}

	public void setLAC(String lAC) {
		LAC = lAC;
	}

	public String getRSSI() {
		return RSSI;
	}

	public void setRSSI(String rSSI) {
		RSSI = rSSI;
	}

	public String GetString() {
		String str = "当前基站信息：" + "\r\n";
		str += "MCC: " + MCC + "\r\n";
		str += "MNC: " + MNC + "\r\n";
		str += "CID: " + CID + "\r\n";
		str += "LAC: " + LAC + "\r\n";
		str += "RSSI:" + RSSI + "\r\n";
		return str;
	}

}
