package geso.traphaco.erp.beans.vayvon.imp;

import java.sql.ResultSet;
import geso.traphaco.erp.beans.vayvon.INhantienvayList;
import geso.traphaco.erp.db.sql.dbutils;

public class NhantienvayList implements INhantienvayList{
	   
	String SoHD;
	String ngay;
	String hinhthuc;
	String userId;
	String msg;
	String ctyId;
	dbutils db;
	ResultSet NTRS;
	ResultSet HDRS;
	String nppId;
	
	String tkvay;
	
	public NhantienvayList()
	{   
		this.SoHD = "";
		this.ngay = "";
	    this.hinhthuc ="";
	    this.ctyId = "";
	    this.nppId ="";
	    this.msg ="";
	    this.tkvay = "";
	    this.db = new dbutils();
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
	
	public void setNgay(String ngayBD) {
		this.ngay = ngayBD;
		
	}

	public String getNgay() {
			return this.ngay;
	}

	public void setHinhthuc(String hinhthuc) {
		this.hinhthuc = hinhthuc;
		
	}

	public String getHinhthuc() {
			return this.hinhthuc;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}
	
	public ResultSet getNTRS() {
		
		return this.NTRS;
	}

	
	public void init(String st) {
		
		String sql="";
		if(st.length()>0)
		{
			sql =	" SELECT NTV.PK_SEQ AS NTVID, NTV.NGAYNHAN, HDV.SOHD, isnull(NTV.SOTIEN,0) AS SOTIENVND,tt.MA ,  NTV.LAISUAT, NTV.HINHTHUC, NTV.TRANGTHAI, " +
					" NV.TEN AS NGUOISUA, NTV.NGAYSUA , NTV.TKVAY " +
					" FROM ERP_NHANTIENVAY NTV " +
					" INNER JOIN ERP_HOPDONGVAY HDV ON HDV.PK_SEQ = NTV.HOPDONG_FK " +
					" INNER JOIN NHANVIEN NV ON NV.PK_SEQ = NTV.NGUOISUA " +
					" inner join ERP_TIENTE tt on tt.PK_SEQ=NTV.TIENTE_FK " +
					" WHERE NTV.CONGTY_FK = " + this.ctyId+ st + " " +
					" ORDER BY ntv.PK_SEQ desc, NTV.NGAYTAO DESC, NTV.NGAYSUA DESC, NTV.NGAYNHAN  DESC, HDV.SOHD";
		}else
			sql =	" SELECT NTV.PK_SEQ AS NTVID, NTV.NGAYNHAN, HDV.SOHD, isnull(NTV.SOTIEN,0) AS SOTIENVND,tt.MA , NTV.LAISUAT, NTV.HINHTHUC, NTV.TRANGTHAI, " +
					" NV.TEN AS NGUOISUA, NTV.NGAYSUA  , NTV.TKVAY " +
					" FROM ERP_NHANTIENVAY NTV " +
					" INNER JOIN ERP_HOPDONGVAY HDV ON HDV.PK_SEQ = NTV.HOPDONG_FK " +
					" INNER JOIN NHANVIEN NV ON NV.PK_SEQ = NTV.NGUOISUA " +
					" inner join ERP_TIENTE tt on tt.PK_SEQ=NTV.TIENTE_FK " +
					" WHERE NTV.CONGTY_FK = " + this.ctyId + "  " +
					" ORDER BY ntv.PK_SEQ desc, NTV.NGAYTAO DESC, NTV.NGAYSUA DESC, NTV.NGAYNHAN  DESC, HDV.SOHD";
			
		System.out.print("chuoi : \n"+ sql + "\n");
		this.NTRS = this.db.get(sql);
		
		this.HDRS = this.db.get("SELECT PK_SEQ AS HDID, SOHD + ' - ' + DIENGIAI AS HD FROM ERP_HOPDONGVAY WHERE CONGTY_FK = " + this.ctyId + " AND TRANGTHAI = 0 ");
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
			if(NTRS !=null){
				NTRS.close();
			}
			this.db.shutDown();
		
		}catch(Exception err){
			
		}
	}



	public String getNppId() {
		return nppId;
	}



	public void setNppId(String nppId) {
		this.nppId = nppId;
	}
	
}
