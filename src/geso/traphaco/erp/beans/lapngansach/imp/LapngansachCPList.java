package geso.traphaco.erp.beans.lapngansach.imp;

import geso.traphaco.erp.beans.lapngansach.ILapngansachCPList;
import geso.traphaco.erp.db.sql.*;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LapngansachCPList implements ILapngansachCPList {
	String Id;
	String userId;
	String ngay;
	String ctyId;
	ResultSet ctylist, nslist, dvkdRs, lnsRs, rs;
	
	String diengiai;

	String nam;
	String msg;
	String hieuluc;
	String copyId;
	
	String dvkdId;
	
	dbutils db;
	
	
	public LapngansachCPList(){
		this.Id = "";
		this.ctyId = "";
		this.diengiai = "";
		this.nam = "" + (Integer.parseInt(this.getDateTime().substring(0, 4)) + 1);
		this.msg = "";
		this.dvkdId = "";
		this.hieuluc = "1";
		this.copyId = "";
		this.db = new dbutils();
		this.ngay = this.getDateTime();
		
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getId(){
		return this.Id;
	}
	
	public void setId(String Id){
		this.Id = Id;
	}

	public String getDvkdId() 
	{
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) 
	{
		
		this.dvkdId= dvkdId;
	}
	
	public ResultSet getDvkd() 
	{
		
		return this.dvkdRs;
	}

	
	public void setDvkd(ResultSet dvkdRs) 
	{
		
		this.dvkdRs = dvkdRs;
	}
	
	public String getNgay(){
		return this.ngay;
	}
	
	public void setNgay(String ngay){
		this.ngay = ngay;
	}
	
	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}

	public String getCopyId(){
		return this.copyId;
	}
	
	public void setCopyId(String copyId){
		this.copyId = copyId;
	}

	public ResultSet getLapngansachCPRs(){
		return this.db.get("SELECT PK_SEQ AS LNSID, NAM, DIENGIAI FROM ERP_LAPNGANSACH WHERE LOAI = '1' ORDER BY NAM DESC");
	}
	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}

	public String getHieuluc(){
		return this.hieuluc;
	}
	
	public void setHieuluc(String hieuluc){
		this.hieuluc = hieuluc;
	}

	public String getNamNew(){
		return "" + (Integer.parseInt(this.getDateTime().substring(0, 4)) + 1);
	}


	public String getNamUpdate(){
		return this.nam;
	}
	
	public String getNam(){
		return this.nam;
	}
	
	public void setNam(String nam){
		this.nam = nam;
	}

	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getCtylist(){
		return this.ctylist;
	}
	
	public void setCtylist(ResultSet ctylist){
		this.ctylist = ctylist;
	}

	public ResultSet getNgansach(){
		return this.nslist;
	}
	
	public void setNgansach(ResultSet nslist){
		this.nslist = nslist;
	}

	private void createCtyList(){
		this.ctylist =  this.db.get("SELECT PK_SEQ AS CTYID, TEN FROM ERP_CONGTY WHERE TRANGTHAI = 1");
	}
	
	public void createBudget(){
		String query = "";
		
		if(this.hieuluc.equals("1")){
			
			query = "UPDATE ERP_LAPNGANSACH SET HIEULUC = '0'";
			
			System.out.println(query);
			this.db.update(query);
			
		}
		
		query = 	"INSERT INTO ERP_LAPNGANSACH(DIENGIAI, CONGTY_FK,  DVKD_FK, NAM, NGAYTAO, NGUOITAO, LOAI, HIEULUC) " +
					"VALUES(N'" + this.diengiai + "', '" + this.ctyId + "', "+ this.dvkdId + ", " + this.nam + ",'" + this.getDateTime() + "','" + this.userId + "', '1', '"+ this.hieuluc +" ')";
		
		System.out.println(query);
		this.db.update(query);
		
		ResultSet rs = this.db.get("SELECT SCOPE_IDENTITY() AS ID");
		
		try{
			rs.next();
			String Id = rs.getString("ID");
			rs.close();
			
			query = "INSERT INTO ERP_LAPNGANSACH_CHIPHI(LAPNGANSACH_FK, DONVITHUCHIEN_FK, NHOMCHIPHI_FK, CHIPHI_FK, DUTOAN, THUCCHI) " +
					"( " +
					"	SELECT " + Id + ", DONVITHUCHIEN_FK, BOSS_FK, PK_SEQ, '0', '0' " +
					"	FROM ERP_NHOMCHIPHI WHERE LOAI = 2 AND TRANGTHAI = 1 " +
					") ";
			
			System.out.println(query);
			this.db.update(query);
		}catch(java.sql.SQLException e){}

		//this.msg = "Ngân sách của năm " + this.nam + " cho công ty đã được khởi tạo";
		
	}
	
	public void Copy(){
		String query = "";
				
		query = 	"INSERT INTO ERP_LAPNGANSACH(DIENGIAI, CONGTY_FK, DVKD_FK, NAM, NGAYTAO, NGUOITAO, LOAI, HIEULUC) " +
					"SELECT N'Vui lòng cập nhật diễn giai', CONGTY_FK,  DVKD_FK, NAM, '" + this.getDateTime() + "', " + this.userId + ", '1', '0' " +
					"FROM ERP_LAPNGANSACH WHERE PK_SEQ = '" + this.copyId + "' ";
		
		System.out.println(query);
		this.db.update(query);
		
		ResultSet rs = this.db.get("SELECT SCOPE_IDENTITY() AS ID");
		
		try{
			rs.next();
			String Id = rs.getString("ID");
			rs.close();
			
			query = "INSERT INTO ERP_LAPNGANSACH_CHIPHI(LAPNGANSACH_FK, DONVITHUCHIEN_FK, NHOMCHIPHI_FK, CHIPHI_FK, DUTOAN, THUCCHI, NGAYSUA, NGUOISUA, PHANBO, " +
					"T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 ) " +
					"( " +
					"	SELECT " + Id + ", DONVITHUCHIEN_FK, NHOMCHIPHI_FK, CHIPHI_FK, DUTOAN, THUCCHI, NGAYSUA, NGUOISUA, PHANBO, " +
					"	T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12  " +
					"	FROM ERP_LAPNGANSACH_CHIPHI WHERE LAPNGANSACH_FK =  '" + this.copyId + "' " +
					") ";
			
			System.out.println(query);
			this.db.update(query);
		}catch(java.sql.SQLException e){}
		
	}
	
	public void init(){
		String query = 	"SELECT LNS.PK_SEQ AS NSID, CTY.TEN AS CTY, LNS.DIENGIAI, LNS.NAM, DVKD.DONVIKINHDOANH AS DVKD, LNS.HIEULUC, NV.TEN AS NGUOITAO, LNS.NGAYTAO " +
						"FROM ERP_LAPNGANSACH LNS " +
						"INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = LNS.CONGTY_FK " +
						"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = LNS.DVKD_FK " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = LNS.NGUOITAO " +
						"WHERE LOAI = 1 ";
		
		if(this.ctyId.length() > 0){
			query = query + " AND CTY.PK_SEQ = '" + this.ctyId + "' ";
		}
		
		if(this.diengiai.length() > 0){
			query = query + " AND LNS.DIENGIAI LIKE '%" + this.diengiai + "%'" ;
		}
		
		if(this.nam.length() > 0){
			query = query + " AND LNS.NAM = " + this.nam + "" ;
		}

		if(this.dvkdId.length() > 0){
			query = query + " AND LNS.DVKD_FK = " + this.dvkdId + "" ;
		}

		query = query + " ORDER BY LNS.NAM DESC";
		
		System.out.println(query);
		
		this.nslist = this.db.get(query);
		
		this.dvkdRs = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH WHERE CONGTY_FK = " + this.ctyId + " ");

		
	}

	public void initUpdate(){
		String query = 	"SELECT LNS.PK_SEQ AS NSID, CTY.PK_SEQ AS CTYID, CTY.TEN AS CTY, LNS.DIENGIAI, LNS.NAM, LNS.TRANGTHAI, NV.TEN AS NGUOITAO, LNS.NGAYTAO " +
						"FROM ERP_LAPNGANSACH LNS " +
						"INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = LNS.CONGTY_FK " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = LNS.NGUOITAO " +
						"WHERE LNS.PK_SEQ = '" + this.Id + "' ";
		
		System.out.println(query);
		this.nslist = this.db.get(query);
		try{
			this.nslist.next();
			this.ctyId = this.nslist.getString("CTYID");
			this.diengiai = this.nslist.getString("DIENGIAI");
			this.nam = this.nslist.getString("NAM");
			this.nslist.close();
		}catch(java.sql.SQLException e){}
		
		this.createCtyList();
		
	}

	public void Delete(String Id){
			this.db.update("DELETE FROM ERP_LAPNGANSACH_CHIPHI WHERE LAPNGANSACH_FK = '" + Id + "' ");
			this.db.update("DELETE FROM ERP_LAPNGANSACH WHERE PK_SEQ = '" + Id + "' ");
	}
	
	private boolean isExist(){
		String query = "SELECT COUNT(*) AS NUM FROM ERP_LAPNGANSACH WHERE NAM = '" + this.getNamNew() + "' AND CONGTY_FK = '" + this.ctyId + "' ";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			String tmp = rs.getString("NUM");
			rs.close();
			
			if(!tmp.equals("0")){
				return true;
			}else{
				return false;
			}
		}catch(java.sql.SQLException e){return false;}
		
	}
	
	public void Save(){
		
	}
	
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	
	public void DBClose(){
		try{
			if(this.ctylist != null) this.ctylist.close();
			if(this.nslist != null) this.nslist.close();
			if(this.rs != null) this.rs.close();
			if(this.lnsRs != null) this.lnsRs.close();
			if(this.dvkdRs != null) this.dvkdRs.close();			
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
}
