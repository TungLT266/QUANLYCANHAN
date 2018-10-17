package geso.traphaco.center.beans.bm.imp;

import geso.traphaco.center.beans.bm.IBmList;

import java.sql.ResultSet;

import geso.traphaco.center.db.sql.*;
import geso.traphaco.center.util.Utility;
public class BmList implements IBmList {
	public String bmTen;
	public String dienthoai;
	public String email;
	public String diachi;
	public String kbhId;
	public String dvkdId;
	public String vungId;
	public String trangthai;
	public String msg;
	public ResultSet kbh;
	public ResultSet vung;
	public ResultSet dvkd;
	public ResultSet bmlist;
	public dbutils db;
	
	String maFAST;
	
	public BmList(){
		this.bmTen = "";
		this.dienthoai = "";
		this.email = "";
		this.diachi = "";
		this.kbhId = "";
		this.dvkdId = "";
		this.vungId = "";
		this.trangthai = "";
		this.msg = "";
		this.maFAST = "";
		this.db = new dbutils();
	}
	
	public String getMaFAST() {
		return maFAST;
	}

	public void setMaFAST(String maFAST) {
		this.maFAST = maFAST;
	}
	
	public String getTen(){
		return this.bmTen;
	}
	
	public void setTen(String bmTen){
		this.bmTen = bmTen;
	}
	
	public String getDienthoai(){
		return this.dienthoai;
	}
	
	public void setDienthoai(String dienthoai){
		this.dienthoai = dienthoai;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}

	public String getDiachi(){
		return this.diachi;
	}
	
	public void setDiachi(String diachi){
		this.diachi = diachi;
	}

	public String getKbhId(){
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId){
		this.kbhId = kbhId;
	}

	public String getDvkdId(){
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId){
		this.dvkdId = dvkdId;
	}

	public String getVungId(){
		return this.vungId;
	}
	
	public void setVungId(String vungId){
		this.vungId = vungId;
	}

	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}

	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getKbh(){
		return this.kbh;
	}
	
	public void setKbh(ResultSet kbh){
		this.kbh = kbh;
	}


	public ResultSet getDvkd(){
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd){
		this.dvkd = dvkd;
	}

	public ResultSet getVung(){
		return this.vung;
	}
	
	public void setVung(ResultSet vung){
		this.vung = vung;
	}

	public ResultSet getBmlist(){
		return this.bmlist;
	}
	
	public void setBmlist(ResultSet bmlist){
		this.bmlist = bmlist;
	}
	
	public void init(){
		Utility util = new Utility();
		/*String sql = 	"SELECT PK_SEQ AS KBHID, TEN AS KBH FROM KENHBANHANG WHERE TRANGTHAI='1' "; //AND PK_SEQ='100000' OR PK_SEQ = '100002'";
		this.kbh = this.db.get(sql);
		*/
		String sql  	=		"SELECT PK_SEQ AS VUNGID, TEN AS VUNG FROM VUNG WHERE TRANGTHAI='1'";
		this.vung = this.db.get(sql);
		
		sql 	= 		"SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH WHERE TRANGTHAI='1'";
		this.dvkd = this.db.get(sql);
		
		sql 	=		"SELECT isnull(BM.maFast,'') as maFast , BM.PK_SEQ  AS BMID,BM.PK_SEQ AS MANHANVIEN, BM.TEN AS BMTEN, BM.DIENTHOAI, BM.TRANGTHAI, " + 
						"BM.NGAYTAO, NV1.TEN AS NGUOITAO, BM.NGAYSUA, NV2.TEN AS NGUOISUA, VUNG.PK_SEQ AS VUNGID," +
						"VUNG.TEN AS VUNG, DVKD.DONVIKINHDOANH AS DVKD,  isnull(BM.manhansu,'') as manhansu " +
						"FROM BM BM " +
						"Left JOIN BM_CHINHANH BM_CN ON BM_CN.BM_FK = BM.PK_SEQ " +
						"Left JOIN VUNG VUNG ON VUNG.PK_SEQ = BM_CN.VUNG_FK " +
						"Left JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = BM.DVKD_FK " +
						"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = BM.NGUOITAO " +
						"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = BM.NGUOISUA " +
						"WHERE BM.TRANGTHAI >= '0' ";
		
		//if (this.kbhId.length() > 0) sql = sql + "AND KBH.PK_SEQ = '" + this.kbhId + "' ";
		if (this.dvkdId.length() > 0) sql = sql + "AND DVKD.PK_SEQ = '" + this.dvkdId + "' ";
		if (this.vungId.length() > 0) sql = sql + "AND VUNG.PK_SEQ = '" + this.vungId + "' ";
		if (this.trangthai.length() > 0 & !this.trangthai.equals("2")) sql = sql + "AND BM.TRANGTHAI = '" + this.trangthai + "' ";
		if (this.bmTen.length() > 0) sql = sql + "and upper(dbo.ftBoDau(BM.TEN)) like upper(N'%" + util.replaceAEIOU(this.bmTen) + "%')";
		if (this.dienthoai.length() > 0) sql = sql + "AND BM.DIENTHOAI LIKE '%" + this.dienthoai + "%' ";
		if (this.maFAST.length() > 0) sql = sql + "AND BM.maFAST LIKE N'%" + this.maFAST + "%' ";
		
		sql = sql + " ORDER BY DVKD ASC, VUNG ASC, BMTEN ASC, TRANGTHAI DESC";
		
		System.out.println("BM init "+sql);
		
		this.bmlist = this.db.get(sql);
		
		
	}
	
	public void Delete(String bmid, String vungId){
		try{
			String sql = "DELETE FROM BM_CHINHANH WHERE BM_FK = '" + bmid + "' AND VUNG_FK ='" + vungId + "'";
			this.db.update(sql);
		
			sql = "SELECT COUNT(*) AS NUM FROM BM_CHINHANH WHERE BM_FK = '" + bmid + "'";
			ResultSet rs = this.db.get(sql);
			rs.next();
		
			if(rs.getString("NUM").equals("0")){
				sql = "DELETE FROM BM WHERE PK_SEQ ='" + bmid + "'";
				this.db.update(sql);				
			}
			rs.close();
		}catch(Exception e){}
	}
	
	public void DBClose(){
		try{
			if(this.dvkd != null) this.dvkd.close();
			if(this.kbh != null) this.kbh.close();
			if(this.bmlist != null) this.bmlist.close();
			if(this.vung != null) this.vung.close();
			if(this.db != null) this.db.shutDown();
		}catch(Exception e){}
	}
}
