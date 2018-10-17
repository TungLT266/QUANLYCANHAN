package geso.traphaco.erp.beans.vayvon.imp;

import java.sql.ResultSet;
import geso.traphaco.erp.beans.vayvon.IThanhtoannovayList;
import geso.traphaco.erp.db.sql.dbutils;

public class ThanhtoannovayList implements IThanhtoannovayList{
	   
	String SoHD;
	String ngay;
	ResultSet ttnvRS;
	String userId;
	String msg;
	String ctyId;
	dbutils db;
	
	String tkvay;
	
	public ThanhtoannovayList()
	{   
		this.SoHD = "";
	    this.ctyId = "";
	    this.msg ="";
	    this.tkvay = "";
	    db = new dbutils();
	}

	public String getTkvay() {
		return tkvay;
	}

	public void setTkvay(String tkvay) {
		this.tkvay = tkvay;
	}

	public void setSoHD(String SoHD) {
		this.SoHD = SoHD;
		
	}

	public String getSoHD() {
		return this.SoHD;
	}
	

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}
	
	public ResultSet getTtnvRS() {
		
		return this.ttnvRS;
	}

	
	public void init(String st) {
		
		String sql="";
		if(st.length()>0)
		{
			sql =	"SELECT	TTNV.PK_SEQ, HDV.SOHD, NTV.TKVAY, TTNV.NGAY, ISNULL(TTNV.TIENLAI, 0) AS TIENLAI, ISNULL(TTNV.TIENGOC, 0) AS TIENGOC, " +
					"ISNULL(TTNV.TIENPHAT, 0) AS TIENPHAT, ISNULL(TTNV.PHIKHAC, 0) AS PHIKHAC, TTNV.TRANGTHAI, TTNV.GHICHU " +
					"FROM ERP_HOPDONGVAY HDV " +
					"INNER JOIN ERP_THANHTOANNOVAY TTNV ON TTNV.HOPDONG_FK = HDV.PK_SEQ " +
					"INNER JOIN ERP_NHANTIENVAY NTV on NTV.PK_SEQ = TTNV.NHANTIENVAY_FK " +
					"WHERE TTNV.CONGTY_FK = " + this.ctyId + " " + st + " ORDER BY TTNV.NGAY DESC, HDV.SOHD ";
		}else
			sql =	"SELECT	TTNV.PK_SEQ, HDV.SOHD,  NTV.TKVAY, TTNV.NGAY, ISNULL(TTNV.TIENLAI, 0) AS TIENLAI, ISNULL(TTNV.TIENGOC, 0) AS TIENGOC, " +
					"ISNULL(TTNV.TIENPHAT, 0) AS TIENPHAT, ISNULL(TTNV.PHIKHAC, 0) AS PHIKHAC, TTNV.TRANGTHAI, TTNV.GHICHU " +
					"FROM ERP_HOPDONGVAY HDV " +
					"INNER JOIN ERP_THANHTOANNOVAY TTNV ON TTNV.HOPDONG_FK = HDV.PK_SEQ " +
					"INNER JOIN ERP_NHANTIENVAY NTV on NTV.PK_SEQ = TTNV.NHANTIENVAY_FK " +
					"WHERE TTNV.CONGTY_FK = " + this.ctyId + " ORDER BY TTNV.NGAY DESC, HDV.SOHD ";
			
		System.out.print("chuoi: "+ sql);
		this.ttnvRS = db.get(sql);
	}
	
	public void Xoa(String Id){
		
	}
	
	public void setUserId(String userId) {
	    this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
		
	public void setMsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getMsg() {
		
		return this.msg;
	}
	
	public void DbClose() {
		// TODO Auto-generated method stub
		try{
			if(ttnvRS !=null){
				ttnvRS.close();
			}
			db.shutDown();
		
		}catch(Exception err){
			
		}
	}
	
}