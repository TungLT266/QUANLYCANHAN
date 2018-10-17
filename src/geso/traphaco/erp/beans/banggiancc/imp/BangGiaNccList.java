package geso.traphaco.erp.beans.banggiancc.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.banggiancc.IBangGiaNccList;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BangGiaNccList   extends Phan_Trang implements IBangGiaNccList, Serializable
{
	
	/**
   * 
   */
  private static final long serialVersionUID = 1L;
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	
	ResultSet nccrs;
	String nccId;
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet vungrs;
	String vungid;
	ResultSet diabanrs;
	String diabanid;
	ResultSet kenhIds;
	String kenhId;	
	ResultSet bangiaRs;
	
	dbutils db;
	
	public BangGiaNccList()
	{
		this.userId = "";

		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		this.dvkdId = "";
		this.kenhId = "";
		this.nccId = "";
		this.msg = "";
		this.vungid="";
		this.diabanid="";
		this.db = new dbutils();
		
		this.nccrs  =  this.db.get("select pk_seq, ten from erp_nhacungcap where trangthai = '1'  and duyet = '1'");
		this.dvkdIds  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from DonViKinhDoanh where trangthai = '1' ");
		this.kenhIds  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from NHOMKENH where trangthai='1'");
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String query) 
	{
		this.getNppInfo();
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = "\n select a.pk_seq, a.ten, a.trangthai , d.ten as nguoitao, a.ngaytao as ngaytao,  " +
				  "\n 	e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.DIENGIAI as kenh, c.pk_seq as nhomkenhId," +
				  "\n 	a.TrangThai, a.TuNgay, ISNULL(a.DADUYET,0) AS DADUYET " +
				  "\n from ERP_BANGGIAMUANCC a, donvikinhdoanh b, NHOMKENH c, nhanvien d, nhanvien e   " +
				  "\n  where a.dvkd_fk = b.pk_seq and a.NHOMKENH_FK = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq   ";
		}
		
		if(this.ma.length() > 0)
			sql += "\n and  a.ten like N'%" + this.ma.trim() + "%' ";
		
		if(this.nccId.length() > 0)
			sql += "\n and a.ncc_fk = '" +this.nccId.trim() + "' ";
		
		if(this.dvkdId.length() > 0)
			sql += "\n and a.dvkd_fk = '" + this.dvkdId.trim() + "' ";
		
		if( this.kenhId.length() > 0)
			sql += "\n and a.NHOMKENH_FK = '" + this.kenhId.trim() + "' ";
		
		if(this.trangthai.length() > 0)
			sql += "\n and a.DADUYET = '" + this.trangthai.trim() + "' ";
		
		
		
		System.out.println("__Bang gia : " + sql);
		
		
		this.bangiaRs = db.get(sql);
		this.vungrs = db.get("SELECT PK_SEQ,TEN FROM VUNG WHERE TRANGTHAI='1'");
		query = " SELECT PK_SEQ,TEN FROM TINHTHANH WHERE 1=1";
		if(this.vungid.length()>0)
		{
			query+= " and VUNG_FK="+this.vungid;
		}
		this.diabanrs=db.get(query);
		
	}

	public void DbClose() 
	{
		try 
		{
			if(this.bangiaRs != null)
				this.bangiaRs.close();
			this.vungrs.close();
			this.diabanrs.close();
			this.db.shutDown();
			this.db = null;
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	public void NewDbUtil()
	{
		  if(this.db == null )
		  {
			   this.db  = new dbutils();
		  }
	}

	public ResultSet getBanggiaRs() 
	{
		return this.bangiaRs;
	}

	public void setBanggiaRs(ResultSet banggiaRs) 
	{
		this.bangiaRs = banggiaRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public String getKenhId() 
	{
		return this.kenhId;
	}

	public void setKenhId(String kenhId) 
	{
		this.kenhId = kenhId;
	}
	
	public ResultSet getDvkd() 
	{
		return this.dvkdIds;
	}

	public void setDvkd(ResultSet dvkdIds) 
	{
		this.dvkdIds = dvkdIds;
	}

	public ResultSet getKenh() 
	{
		return this.kenhIds;
	}

	public void setKenh(ResultSet kenhIds) 
	{
		this.kenhIds = kenhIds;
	}
	
	String nppId;
	String nppTen;
	String sitecode;

	public String getNppId()
  {
  	return nppId;
  }

	public void setNppId(String nppId)
  {
  	this.nppId = nppId;
  }

	public String getNppTen()
  {
  	return nppTen;
  }

	public void setNppTen(String nppTen)
  {
  	this.nppTen = nppTen;
  }

	public String getSitecode()
  {
  	return sitecode;
  }

	public void setSitecode(String sitecode)
  {
  	this.sitecode = sitecode;
  }
	
	private void getNppInfo()
	{
		Utility util=new Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}


	public String getVungId() {

		return this.vungid;
	}


	public void setVungId(String vungid) {

		this.vungid=vungid;
	}


	public String getDiabanId() {

		return this.diabanid;
	}


	public void setDiabanId(String diabanid) {

		this.diabanid=diabanid;
	}


	public ResultSet getVungRs() {

		return this.vungrs;
	}


	public void setVungRs(ResultSet vungrs) {

		this.vungrs=vungrs;
	}


	public ResultSet getDiabanRs() {

		return this.diabanrs;
	}


	public void setDiabanRs(ResultSet diabanrs) {

		this.diabanrs=diabanrs;
	}

	@Override
	public String Duyet(String bgId) {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_BANGGIAMUANCC SET DADUYET = 1 WHERE PK_SEQ = " + bgId;
			if(!db.update(query)){
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Lỗi duyệt bảng giá (2)";
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			this.msg = e.getMessage();
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public String BoDuyet(String bgId) {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_BANGGIAMUANCC SET DADUYET = 0 WHERE PK_SEQ = " + bgId;
			if(!db.update(query)){
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Lỗi duyệt bảng giá (2)";
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			this.msg = e.getMessage();
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getNccId() {
		return this.nccId;
	}

	@Override
	public void setNccId(String nccId) {
		this.nccId = nccId;
	}

	@Override
	public ResultSet getNccRs() {
		return this.nccrs;
	}

	@Override
	public void setNccRs(ResultSet nccrs) {
		this.nccrs = nccrs;
	}

}
