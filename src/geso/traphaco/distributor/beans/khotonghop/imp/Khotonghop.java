
package geso.traphaco.distributor.beans.khotonghop.imp;

import geso.traphaco.distributor.beans.khotonghop.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

public class Khotonghop implements IKhotonghop
{
	String userId;
	String userTen;
	String nppId;
	String thang;
	String nam;
	String tungay;
	String denngay;
	String msg;
	
	dbutils db;
	Utility ut;
	
	public Khotonghop()
	{
		this.userId = "";
		this.userTen = "";
		this.nppId = "";
		this.thang = "";
		this.nam = "";
		this.tungay = "";
		this.denngay = "";
		
		this.msg = "";
		
		ut = new Utility();
	}

	@Override
	public void setuserId(String userId) {
		this.userId = userId;
		this.nppId = ut.getIdNhapp(userId);
	}

	@Override
	public String getuserId() {
		return this.userId;
	}

	@Override
	public void setuserTen(String userTen) {
		this.userTen = userTen;
	}

	@Override
	public String getuserTen() {
		return this.userTen;
	}

	@Override
	public void setnppId(String nppId) {
		this.nppId = nppId;
	}

	@Override
	public String getnppId() {
		return this.nppId;
	}

	@Override
	public void setThang(String thang) {
		this.thang = thang;
	}

	@Override
	public String getThang() {
		return this.thang;
	}

	@Override
	public void setNam(String nam) {
		this.nam = nam;
	}

	@Override
	public String getNam() {
		return this.nam;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}

	@Override
	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

	@Override
	public String getTungay() {
		return this.tungay;
	}

	@Override
	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}

	@Override
	public String getDenngay() {
		return this.denngay;
	}

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		this.db.shutDown();
	}

}
