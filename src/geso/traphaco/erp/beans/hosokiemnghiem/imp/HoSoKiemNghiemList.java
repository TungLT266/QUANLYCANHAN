package geso.traphaco.erp.beans.hosokiemnghiem.imp;

import java.sql.ResultSet;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.hosokiemnghiem.IHoSoKiemNghiemList;

public class HoSoKiemNghiemList implements IHoSoKiemNghiemList {

	String congtyId;
	String userId;
	String userTen;
	String id;
	String ma;
	String ten;
	String tungay;
	String denngay;
	String msg;
	
	ResultSet hosoRs;
	dbutils db;
	
	public HoSoKiemNghiemList(){
		 this.congtyId ="";
		 this.userId="";
		 this.id="";
		 this.userTen="";
		 this.ma="";
		 this.ten="";
		 this.tungay="";
		 this.denngay="";
		 this.msg="";
		 this.db = new dbutils();
	}
	
	public void init() {
		
		
	}

	public void Delete(String id, String userId) {
		
		
	}

	public String getCongtyId() {
		return congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserTen() {
		return userTen;
	}

	public void setUserTen(String userTen) {
		this.userTen = userTen;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTungay() {
		return tungay;
	}

	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

	public String getDenngay() {
		return denngay;
	}

	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResultSet getHosoRs() {
		return hosoRs;
	}

	public void setHosoRs(ResultSet hosoRs) {
		this.hosoRs = hosoRs;
	}

}
