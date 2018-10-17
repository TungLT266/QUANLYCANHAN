package geso.traphaco.erp.beans.hosokiemnghiem.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.erp.beans.hosokiemnghiem.IErpHoSoKiemNghiem;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.IItemLoader;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.imp.ItemLoader;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpHoSoKiemNghiem implements IErpHoSoKiemNghiem {

	String congtyId;
	String userId;
	String userTen;
	String id;
	String ma;
	String ten;
	String msg;
	dbutils db;
	String nppId;
	
	String ngaychungtu;
	String phongbanTH;
	String maSoKiemNghiem;
	String soPhieuKiemNghiem;
	String thoiGianMau;
	String nguoiGuiMau;
	
	String phieuYeuCauId;
	ResultSet phieuYeuCauRs;
	
	String sanPhamYcID;
	
	String tieuchuanKNId;
	ResultSet tieuchuenKNRs;
	
	String loaiKNId;
	ResultSet loaiKNRs;
	
	String thoidiemlaymau;
	
	String trangthai;
	String diengiai;
	
	public ErpHoSoKiemNghiem (){
		 this.id="";
		 this.nppId="";
		 this.congtyId ="";
		 this.userId="";
		 this.userTen="";
		 this. ma="";
		 this.ten="";
		 this.msg="";
		 
		 this.ngaychungtu="";
		 this.phongbanTH="";
		 this.maSoKiemNghiem="";
		 this.soPhieuKiemNghiem="";
		 this.thoiGianMau="";
		 this.nguoiGuiMau="";
			
		 this.phieuYeuCauId="";
		 this.sanPhamYcID="";
			
		 this.tieuchuanKNId="";
		 this.loaiKNId="";
		 this.thoidiemlaymau="";
			
		 this.trangthai="";
		 this.diengiai="";
		 
		 this.db = new dbutils();
	}
	
	
	public void init() {
		
		
	}
	
	public void createRs() {
		
		
	}
	
	public void DBclose() 
	{
		try
		{
			
		}
		catch(Exception er){
			er.printStackTrace();
		}
		finally
		{
			if(this.db!=null)
				this.db.shutDown();
		}
	}
	
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public boolean createHSKN() {

		try {
			
			db.getConnection().setAutoCommit(false);
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean UpdateHSKN() {
		
		try {
			
			db.getConnection().setAutoCommit(false);
			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
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


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getNppId() {
		return nppId;
	}


	public void setNppId(String nppId) {
		this.nppId = nppId;
	}
}
