package geso.traphaco.erp.beans.nhamay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.nhamay.IErpNhamay;

public class ErpNhamay implements IErpNhamay
{
	String userId;
	String congtyId;
	String id;
	
	String ma;
	String diengiai;
	String diachi;
	
	String trangthai;
	String msg;
	String NhamayId="";
	ResultSet nhamayrs;
	
	String dvthId="";
	ResultSet dvthRs;
	
	dbutils db;
	
	public ErpNhamay()
	{
		this.userId = "";
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.diachi = "";
		
		this.dvthId = "";
		this.NhamayId = "";

		this.trangthai = "1";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public ErpNhamay(String id)
	{
		this.id = id;
		this.ma = "";
		this.diengiai = "";
		this.diachi = "";
		
		this.dvthId = "";
		this.NhamayId = "";

		this.trangthai = "1";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		String query = "select isnull(NHAMAYTONG_FK,0) as NHAMAYTONG_FK ,manhamay, tennhamay, diachi, trangthai, dvth_fk " + 
					  " from Erp_NhaMay where PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ma =  rs.getString("manhamay");
					this.diengiai = rs.getString("tennhamay");
					this.diachi = rs.getString("diachi");
					this.trangthai = rs.getString("trangthai");
					this.NhamayId=rs.getString("NHAMAYTONG_FK");
					this.dvthId=rs.getString("dvth_fk") == null ? "" : rs.getString("dvth_fk");
					
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean createNhamay()
	{	
		try 
		{
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã nhà máy";
				return false;
			}
			
			if(this.dvthId.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập phòng ban";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			if (this.isExistNhaMay()) {
				this.msg = "Mã nhà máy đã tồn tại. Vui lòng nhập mã nhà máy khác";
				return false;
			} else {
				String query = "insert Erp_NhaMay(NHAMAYTONG_FK,manhamay, tennhamay, diachi, congty_fk, dvth_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
						"values("+this.NhamayId+",N'" + this.ma + "', N'" + this.diengiai + "', N'" + this.diachi + "', '" + this.congtyId + "', '" + this.dvthId + "', '" + this.trangthai + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
		
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới Erp_NhaMay " + query;
					db.getConnection().rollback();
					return false;
				}
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
			
			
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean updateNhamay() 
	{
		try 
		{
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã cụm sản xuất";
				return false;
			}
			
			if(this.dvthId.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập phòng ban";
				return false;
			}
			
			
			if (this.isExistNhaMay()) {
				this.msg = "Mã nhà máy đã bị trùng với mã nhà máy khác. Vui lòng nhập lại";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			
			System.out.println("manhamay: "+ this.ma);
			String query = "update Erp_NhaMay set NHAMAYTONG_Fk="+this.NhamayId+", manhamay = N'" + this.ma + "', tennhamay = N'" + this.diengiai + "', diachi = N'" + this.diachi + "', congty_fk = '" + this.congtyId + "', dvth_fk = '" + this.dvthId + "', trangthai = '" + this.trangthai + "', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";
			System.out.println("query manhamay: "+ query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Erp_NhaMay " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean isExistNhaMay() {
		String query = "select manhamay from Erp_NhaMay";
		if (this.id.trim().length() > 0) {
			query += " where pk_seq <> "+this.id;
		}
		ResultSet rs = db.get(query);
		if(rs != null){
			try {
				while(rs.next()){ 
					if (rs.getString("manhamay").equals(this.ma)) {
						rs.close();
						return true;
					}
				}
				rs.close();
			} 
			catch (Exception e) {
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		return false;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	
	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
		} 
		catch (Exception e) {}
		
	}
	
	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}

	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public String getDiachi() 
	{
		return this.diachi;
	}

	public void setDiachi(String diachi) 
	{
		this.diachi = diachi;
	}

	
	public String getNhamayId() {
		
		return this.NhamayId;
	}

	
	public void setNhamayId(String NhamayId) {
		
		this.NhamayId=NhamayId;
	}

	
	public ResultSet getRsNhamay() {
		
		return this.nhamayrs;
	}

	
	public void setRsNhamay(ResultSet rs) {
		
		this.nhamayrs=rs;
	}

	
	public void creaters() 
	{
		try
		{
			String query="select pk_Seq ,manhamay +' - ' +tennhamay as ten from [ERP_NHAMAYTONG]  order by ten asc ";
			this.nhamayrs=db.get(query);
			
			query="select pk_Seq, ten from ERP_DONVITHUCHIEN order by ten asc ";
			this.dvthRs=db.get(query);
			
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}
	}

	
	public String getDvthId() {
		
		return this.dvthId;
	}

	
	public void setDvthId(String dvthId) {
		
		this.dvthId = dvthId;
	}

	
	public ResultSet getDvthRs() {
		
		return this.dvthRs;
	}

	
	public void setDvthRs(ResultSet dvthRs) {
		
		this.dvthRs = dvthRs;
	}
	
	
	

}
