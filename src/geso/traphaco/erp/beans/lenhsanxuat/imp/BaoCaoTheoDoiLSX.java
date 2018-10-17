package geso.traphaco.erp.beans.lenhsanxuat.imp;

import geso.traphaco.erp.beans.lenhsanxuat.IBaoCaoTheoDoiLSX;
import geso.traphaco.center.db.sql.dbutils;

public class BaoCaoTheoDoiLSX implements IBaoCaoTheoDoiLSX {

	String userId;
	String tungay;
	String denngay;
	String msg;
	dbutils db;
	
	@Override
	public void setuserId(String userId) {
		this.userId = userId;
		
	}

	@Override
	public String getuserId() {
		return this.userId;
	}

	@Override
	public void settungay(String tungay) {
		this.tungay = tungay;
		
	}

	@Override
	public String gettungay() {
		return this.tungay;
	}

	@Override
	public void setdenngay(String denngay) {
		this.denngay = denngay;
		
	}

	@Override
	public String getdenngay() {
		return this.denngay;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
		
	}

	@Override
	public String getMsg() {
		return this.msg;
	}
	
	public BaoCaoTheoDoiLSX(){
		this.tungay = "";
		this.denngay = "";
		this.db = new dbutils();
		
	}

}
