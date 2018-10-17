package geso.traphaco.erp.beans.duyetbom.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.duyetbom.IErpDuyetbom;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ErpDuyetbom implements IErpDuyetbom
{
	String Msg, ctyId, bomId, UserId, chungloaiId, masp,diengiai,tensanpham;
	ResultSet SanphamRs, ChungloaiRs;
	dbutils db;

	public ErpDuyetbom()
	{
		this.ctyId = "";
		this.bomId = "";
		this.UserId = "";
		this.Msg = "";
		this.chungloaiId = "";
		this.masp = "";
		this.tensanpham="";
		this.diengiai="";
		this.db = new dbutils();
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}
	
	public void setMsg(String msg)
	{
		this.Msg = msg;
	}

	public String getMsg()
	{
		return this.Msg;
	}

	public void setUserId(String userId)
	{
		this.UserId = userId;
	}

	public String getUserId()
	{
		return this.UserId;
	}

	public boolean Init()
	{
		String condition="";
		Utility util = new Utility();
		if(this.diengiai.length() > 0 )
			condition=" and dbo.ftBoDau(upper(diengiai)) like upper(N'%" + util.replaceAEIOU(this.diengiai) + "%')";
		
		String query = 	"select sp.ma,sp.ten ,(select max(pk_seq) from erp_Danhmucvattu a where a.MASANPHAM =sp.ma  ) as iddmvt  from ERP_SANPHAM sp  " +
				" where MA in (select MASANPHAM from ERP_DANHMUCVATTU WHERE 1 = 1 "+condition+") and TRANGTHAI = 1";
		

		if(this.tensanpham.length() > 0)
			query += " and ( dbo.ftBoDau(upper(MA)) like upper(N'%" + util.replaceAEIOU(this.tensanpham) + "%') or dbo.ftBoDau(upper(ten)) like upper(N'%" + util.replaceAEIOU(this.tensanpham) + "%') )";
		
		if(this.chungloaiId.length() > 0)
			query += " and CHUNGLOAI_FK = " + this.chungloaiId;
		
		this.SanphamRs = this.db.get("Init: " + query);
		query = "SELECT * FROM CHUNGLOAI WHERE TRANGTHAI = 1";
		this.ChungloaiRs = this.db.get(query);
		return true;
	}

	public void Close()
	{
		try
		{
			if (SanphamRs != null)
				SanphamRs.close();
			if (ChungloaiRs != null)
				ChungloaiRs.close();
			this.db.shutDown();
			
		} catch (Exception e)
		{
			System.out.print("Exception  Close" + e.getMessage());
			e.printStackTrace();
		}
	}


	public void setChungloaiId(String value) {
		this.chungloaiId = value;
	}


	public String getChungloaiId() {
		return this.chungloaiId;
	}


	public void setBomId(String bomid) {
		this.bomId = bomid;
	}


	public String getBomId() {
		return this.bomId;
	}


	public boolean Update() {
		return false;
	}


	public ResultSet getSanphamRs() {
		return this.SanphamRs;
	}


	public ResultSet getChungloaiRs() {
		return this.ChungloaiRs;
	}


	public void setMaSP(String spma) {
		this.masp = spma;
	}


	public boolean Save() {
		String query = " update ERP_DANHMUCVATTU SET UUTIEN = 1 WHERE MAVATTU = '"+this.masp+"' and PK_SEQ = '"+this.bomId+"'";
		if(!Boduyet() || this.masp.length() <= 0 || this.bomId.length() <= 0)
			return false;
		try {
			db.getConnection().setAutoCommit(false);
			if(!db.update(query))
			{
				this.Msg = "Không thể cập nhật ERP_DANHMUCVATTU: " + query;
				db.getConnection().rollback();
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public boolean Boduyet() {
		if(this.masp.length() != 0){
			try {
				db.getConnection().setAutoCommit(false);
				String query = "update ERP_DANHMUCVATTU SET UUTIEN = 0 WHERE MAVATTU = '"+this.masp+"' AND TRANGTHAI = 1";
				if(!db.update(query))
				{
					this.Msg = "Không thể cập nhật ERP_DANHMUCVATTU: " + query;
					db.getConnection().rollback();
					return false;
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
			} catch (SQLException e) {
				try 
				{
					db.getConnection().rollback();
				} 
				catch (SQLException e1) {}
				e.printStackTrace();
				return false;
			}
			
		}
		return true;
	}


	public void setTenSanPham(String tensanpham) {
		this.tensanpham=tensanpham;
		
	}


	public String getTenSanPham() {

		return this.tensanpham;
	}


	public void setDienGiai(String diengiai) {
		this.diengiai=diengiai;
		
	}


	public String getDienGiai() {

		return this.diengiai;
	}
}
