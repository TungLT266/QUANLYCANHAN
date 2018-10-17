package geso.traphaco.erp.beans.vayvon.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.beans.vayvon.IHopdongvayList;
import geso.traphaco.erp.db.sql.dbutils;

public class HopdongvayList implements IHopdongvayList{
	   
	String SoHD;
	String ngayBD;
	String thoihan;
	ResultSet HDrs;
	String userId;
	String msg;
	String ctyId;
	dbutils db;
	public HopdongvayList()
	{   
		this.SoHD = "";
		this.ngayBD = "";
	    this.thoihan ="";
	    this.ctyId = "";
	    this.msg ="";
	    db = new dbutils();
	}

	public void setSoHD(String SoHD) {
		this.SoHD = SoHD;
		
	}

	public String getSoHD() {
		return this.SoHD;
	}
	
	public void setNgayBD(String ngayBD) {
		this.ngayBD = ngayBD;
		
	}

	public String getNgayBD() {
			return this.ngayBD;
	}

	public void setThoihan(String thoihan) {
		this.thoihan = thoihan;
		
	}

	public String getThoihan() {
			return this.thoihan;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}
	
	public ResultSet getHDrs() {
		
		return this.HDrs;
	}

	
	public void init(String st) {
		
		String sql="";
		if(st.length()>0)
		{
			sql =	"SELECT	HDV.PK_SEQ, HDV.SOHD, ISNULL(HDV.THAYTHECHO, '') AS THAYCHO, HDV.DIENGIAI, NH.TEN AS NGANHANG, HDV.TONGTIEN, TT.MA AS TIENTE, " + 
					"ISNULL(HDV.TONGNGOAITE, 0) AS TONGNGOAITE, ISNULL(TT2.MA,'') AS NGOAITE, " +
					"HDV.LOAI, NV2.TEN AS NGUOISUA, HDV.NGAYSUA, HDV.TRANGTHAI " +
					"FROM ERP_HOPDONGVAY HDV " +
					"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = HDV.NGANHANG_FK " +
					"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = HDV.TIENTE_FK " +
					"LEFT JOIN ERP_TIENTE TT2 ON TT2.PK_SEQ = HDV.NGOAITE_FK " +
					"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = HDV.NGUOITAO " + 
					"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = HDV.NGUOISUA " +
					"WHERE HDV.CONGTY_FK = " + this.ctyId + " " + st + " ORDER BY HDV.NGAYTAO desc, HDV.PK_SEQ desc " ;
		}else
			sql =	"SELECT	HDV.PK_SEQ, HDV.SOHD, ISNULL(HDV.THAYTHECHO, '') AS THAYCHO, HDV.DIENGIAI, NH.TEN AS NGANHANG, HDV.TONGTIEN, TT.MA AS TIENTE, " +
					"ISNULL(HDV.TONGNGOAITE, 0) AS TONGNGOAITE, ISNULL(TT2.MA,'') AS NGOAITE, " +
					"HDV.LOAI, NV2.TEN AS NGUOISUA, HDV.NGAYSUA, HDV.TRANGTHAI " +
					"FROM ERP_HOPDONGVAY HDV " +
					"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = HDV.NGANHANG_FK " +
					"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = HDV.TIENTE_FK " +
					"LEFT JOIN ERP_TIENTE TT2 ON TT2.PK_SEQ = HDV.NGOAITE_FK " +
					"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = HDV.NGUOITAO " + 
					"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = HDV.NGUOISUA " +
					"WHERE HDV.CONGTY_FK = " + this.ctyId + " ORDER BY HDV.NGAYTAO desc, HDV.PK_SEQ desc ";
			
		System.out.print("chuoi: "+ sql);
		this.HDrs = db.get(sql);
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
			if(HDrs !=null){
				HDrs.close();
			}
			db.shutDown();
		
		}catch(Exception err){
			
		}
	}
	
}
