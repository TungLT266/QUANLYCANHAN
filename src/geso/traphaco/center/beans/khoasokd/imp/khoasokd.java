package geso.traphaco.center.beans.khoasokd.imp;

import geso.traphaco.center.beans.khoasokd.IKhoasokinhdoanh;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import geso.traphaco.center.beans.khoasongay.IKhoasotudong;
import geso.traphaco.center.db.sql.dbutils;

public class khoasokd  implements IKhoasokinhdoanh{

	String ngaytt = "";
	String giott = "";
	String ngaynpp = "";
	String gionpp = "";
	String ngayctv = "";
	String gioctv = "";
	String userid = "";
	String msg = "";
	String acti = "0";
	String trangthai1 = "";
	String trangthai2 = "";
	String trangthai3 = "";
	String phuttt = "";
	String phutnpp = "";
	String phutctv = "";



	public String getUserId() {
	
		return this.userid;
	}


	public void setUserId(String userId) {
		this.userid = userId;
		
	}


	public String getMsg() {
	
		return this.msg;
	}


	public void setMsg(String msg) {
	
				this.msg = msg;
	}


	public void init() 
	{
		dbutils db = new dbutils();
		String sql1 = "select * from TLKSKDTT";
		String sql2 = "select * from TLKSKDNPP";
		String sql3 = "select * from TLKSKDCTV";
		ResultSet rs1 = db.get(sql1);
		ResultSet rs2 = db.get(sql2);
		ResultSet rs3 = db.get(sql3);
		try {
			if(rs1.next())
			{
				this.ngaytt = rs1.getString("ngay")== null?"":rs1.getString("ngay");
				this.giott = rs1.getString("gio")== null?"":rs1.getString("gio");
				this.phuttt = rs1.getString("phut") == null?"":rs1.getString("phut");
				
				if(rs1.getString("trangthai") == null || rs1.getString("trangthai").equals("0"))
					this.trangthai1 = "0";
				else 
					this.trangthai1 = "1";
				System.out.println("Trang thai in "+trangthai1);
				
			}
			if(rs2.next())
			{
				this.ngaynpp = rs2.getString("ngay").trim();
				this.gionpp = rs2.getString("gio").trim();
				this.phutnpp = rs2.getString("phut") == null?"":rs2.getString("phut");
				
				if(rs2.getString("trangthai") == null || rs2.getString("trangthai").equals("0"))
					this.trangthai2 = "0";
				else 
					this.trangthai2 = "1";
			}
			if(rs3.next())
			{
				this.ngayctv = rs3.getString("ngay");
				this.gioctv = rs3.getString("gio");
				this.phutctv = rs3.getString("phut") == null?"":rs3.getString("phut");
				if(rs3.getString("trangthai") == null || rs3.getString("trangthai").equals("0") )
					this.trangthai3 = "0";
				else 
					this.trangthai3 = "1";
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		
	}


	public void createRs() {
	
		
	}


	public boolean updateKhoasokd() {
	
		return false;
	}


	public void SetGioTT(String gio) {
			this.giott = gio;
		
	}


	public String getGioTT() {
	
		return this.giott;
	}


	public void SetGioNPP(String gio) {
		this.gionpp = gio;
		
	}


	public String getGioNPP() {
	
		return this.gionpp;
	}


	public void SetGioCTV(String gio) {
		this.gioctv = gio;
	}


	public String getGioCTV() {
	
		return this.gioctv;
	}


	public void SetNgayKSKDTT(String ngayKsTT) {
	
		this.ngaytt = ngayKsTT;
	}


	public String getNgayKSKDTT() {
	
		return this.ngaytt;
	}


	public void DBclose() {
	
		
	}


	public void SetNgayKSKDNPP(String NgayKsNpp) {
		this.ngaynpp = NgayKsNpp;
		
	}


	public String GetNgayKSKDNPP() {
	
		return this.ngaynpp;
	}


	public void SetNgayKSKDCTV(String NgayKsCTV) {
		this.ngayctv = NgayKsCTV;
		
	}


	public String GetNgayKSKDCTV() {
	
		return this.ngayctv;
	}


	public String getActiveTab() {
	
		return this.acti;
	}


	public void setActiveTab(String tab) {
		this.acti = tab;
		
	}


	public void SetTrangthai2(String trangthai2) {
				this.trangthai2 = trangthai2;
		
	}


	public String getTrangthai2() {
		if (this.trangthai2.equals("1")){
			return "Hoat dong";
		}
		return "Ngung hoat dong";
		
	}


	public void SetTrangthai3(String trangthai3) {
		this.trangthai3 = trangthai3;
		
	}


	public String getTrangthai3() {
	
		if (this.trangthai3.equals("1")){
			return "Hoat dong";
		}
		return "Ngung hoat dong";
	}


	public void SetTrangthai1(String trangthai1) {
		this.trangthai1 = trangthai1;
		
	}


	public String getTrangthai1() {
	
		if (this.trangthai1.equals("1")){
			return "Hoat dong";
		}
		return "Ngung hoat dong";
	}


	
	public void SetPhutTT(String phuttt) {
		
		this.phuttt = phuttt;
	}


	
	public String getPhutTT() {
		
		return this.phuttt;
	}


	
	public void SetPhutNPP(String gio) {
		
		this.phutnpp = gio;
	}


	
	public String getPhutNPP() {
		
		return this.phutnpp;
	}


	
	public void SetPhutCTV(String gio) {
		
		this.phutctv = gio;
	}


	
	public String getPhutCTV() {
		
		return this.phutctv;
	}


	
	

}
