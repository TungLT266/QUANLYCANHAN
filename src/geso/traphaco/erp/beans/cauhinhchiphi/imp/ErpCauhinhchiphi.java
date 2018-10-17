package geso.traphaco.erp.beans.cauhinhchiphi.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import  geso.traphaco.erp.beans.cauhinhchiphi.IErpCauhinhchiphi;

public class ErpCauhinhchiphi implements IErpCauhinhchiphi
{
	String Id;
	String userId;
	
	ResultSet khoanmucCPRs;
	String khoanmucCPId;
	
	ResultSet groupCPRs;
	String groupCPId;
	
	String ctyId;
	String ma;
	String diengiai;
	String trangthai;
	String congansach;
	
	String timkiem;
	
	String msg;
	dbutils db;
	Utility util;
	
	public ErpCauhinhchiphi()
	{
		this.Id = "";
		this.ctyId = "";
		this.ma = "";
		this.diengiai = "";
		this.trangthai = "";		
		this.congansach = "";
		this.khoanmucCPId = "";
		this.groupCPId = "";
		
		this.timkiem = "";
		
		this.msg = "";
		this.db = new dbutils();
		this.util=new Utility();
	}


	public String getId(){
		return this.Id;
	}
	
	public void setId(String Id){
		this.Id = Id;
	}


	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}

	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getMa(){
		return this.ma;
	}
	
	public void setMa(String ma){
		this.ma = ma;
	}

	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}
	
	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}
	

	public String getCongansach(){
		return this.congansach;
	}
	
	public void setCongansach(String congansach){
		this.congansach = congansach;
	}

	

	public void init()
	{
		String query = 	" SELECT MA, DIENGIAI, TRANGTHAI " +
						" FROM GROUP_TAIKHOAN_NHOM where pk_seq = '" + this.Id + "' "; 		
		System.out.println("init: " + query);
		
		ResultSet rs = this.db.get(query);
		try{
			while(rs.next())
			{
				this.ma = rs.getString("MA");
				this.diengiai = rs.getString("DIENGIAI");
				this.trangthai = rs.getString("TRANGTHAI");
						
			}
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("__Exception INIT: " + e.getMessage());
		}
		
		query = "select taikhoan_fk from GROUP_TAIKHOAN_NHOM_TK where group_taikhoan_nhom_fk = '" + this.Id + "'";
		ResultSet rsKhoanmuc = db.get(query);
		if(rsKhoanmuc != null)
		{
			try 
			{
				String khoanmucId = "";
				while(rsKhoanmuc.next())
				{
					khoanmucId += rsKhoanmuc.getString("taikhoan_fk") + ",";
				}
				rsKhoanmuc.close();
				
				if(khoanmucId.trim().length() > 0)
				{
					khoanmucId = khoanmucId.substring(0, khoanmucId.length() - 1);
					this.khoanmucCPId = khoanmucId;
				}
			} 
			catch (Exception e) {
				System.out.println("__Exception INIT 2: " + e.getMessage());
			}
		}
		
		this.createRs();
		
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
		
	}

	public String getMsg() {
		return this.msg;
	}

	
	public void DBClose()
	{
		try
		{
			if(this.khoanmucCPRs != null) 
				this.khoanmucCPRs.close();
			if(this.groupCPRs != null) 
				this.groupCPRs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.db.shutDown();
	}


	
	public ResultSet getKhoanmucCPRs() {
		
		return this.khoanmucCPRs;
	}


	
	public void setKhoanmucCPList(ResultSet khoanmucRs) {
		
		this.khoanmucCPRs = khoanmucRs;
	}


	
	public void setKhoanmucCpIds(String khoanmucIs) {
		
		this.khoanmucCPId = khoanmucIs;
	}


	
	public String getKhoanmucCPIds() {
		
		return this.khoanmucCPId;
	}


	
	public ResultSet getGroupCPRs() {
		
		return this.groupCPRs;
	}


	
	public void setGroupCPRs(ResultSet groupRs) {
		
		this.groupCPRs = groupRs;
	}


	
	public void setGroupCpIds(String khoanmucIs) {
		
		this.groupCPId = khoanmucIs;
	}


	
	public String getGroupCPIds() {
		
		return this.groupCPId;
	}


	
	public void createRs() 
	{
		this.khoanmucCPRs = db.get("select PK_SEQ, SOHIEUTAIKHOAN, TENTAIKHOAN from ERP_TAIKHOANKT where TRANGTHAI = '1' ");
		
	}

	
	public boolean New() 
	{
		if(this.ma.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập mã";
			return false;
		}
		
		if(this.khoanmucCPId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn Số hiệu tài khoản";
			return false;
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "insert GROUP_TAIKHOAN_NHOM(ma, diengiai, trangthai, nguoitao, ngaytao, nguoisua, ngaysua) " +
						   "values(N'" + this.ma + "', N'" + this.diengiai + "', '1', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "')";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo mới GROUP_TAIKHOAN_NHOM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert GROUP_TAIKHOAN_NHOM_TK(group_taikhoan_nhom_fk, taikhoan_fk) " +
					" select IDENT_CURRENT('GROUP_TAIKHOAN_NHOM'), pk_seq from ERP_TAIKHOANKT where pk_seq in ( " + this.khoanmucCPId + " ) ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo mới GROUP_TAIKHOAN_NHOM_TK " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Lỗi khi tạo mới nhóm tài khoản: " + e.getMessage();
			return false;
		}
		
		return true;
	}
	
	
	public boolean Update() 
	{
		
		if(this.ma.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập mã";
			return false;
		}
		
		if(this.khoanmucCPId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn số hiệu tài khoản";
			return false;
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update GROUP_TAIKHOAN_NHOM set ma = N'" + this.ma + "', " +
						   "diengiai = N'" + this.diengiai + "', nguoisua = '" + this.userId + "', " +
						   "ngaysua = '" + getDateTime() + "', trangthai = '" + this.trangthai + "' " +
						   "where pk_seq = '" + this.Id + "' ";
						
			System.out.println(query);
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo mới GROUP_TAIKHOAN_NHOM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete GROUP_TAIKHOAN_NHOM_TK where group_taikhoan_nhom_fk = '" + this.Id + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo mới GROUP_TAIKHOAN_NHOM_TK " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert GROUP_TAIKHOAN_NHOM_TK(group_taikhoan_nhom_fk, taikhoan_fk) " +
					" select '" + this.Id + "', pk_seq from ERP_TAIKHOANKT where pk_seq in ( " + this.khoanmucCPId + " ) ";
			
			System.out.println("___Phan bo UPDATE: " + query);
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo mới GROUP_TAIKHOAN_NHOM_KT " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Lỗi khi tạo mới nhóm tài khoản: " + e.getMessage();
			return false;
		}
		
		return true;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


	
	public void setTimkiem(String timkiem) {
		
		this.timkiem = timkiem;
	}


	
	public String getTimkiem() {
		
		return this.timkiem ;
	}
	
	public ResultSet getChophanbothem(String Id) {
		String query;
		
		if(Id.trim().length() >0)
		{
			query = "select PK_SEQ, SOHIEUTAIKHOAN, TENTAIKHOAN from ERP_TAIKHOANKT where TRANGTHAI = '1'";
			
			if(this.timkiem.length() > 0){
				query = query + " AND SOHIEUTAIKHOAN LIKE N'%" + this.timkiem + "%' ";
			}
			
			return this.db.get(query);
		}
		else{
			
			query = "select PK_SEQ, SOHIEUTAIKHOAN, TENTAIKHOAN from ERP_TAIKHOANKT where TRANGTHAI = '1'";
			
			if(this.timkiem.length() > 0){
				query = query + " AND SOHIEUTAIKHOAN LIKE N'%" + this.timkiem + "%' ";
			}
			
			return this.db.get(query);
		}
	}
	
}
