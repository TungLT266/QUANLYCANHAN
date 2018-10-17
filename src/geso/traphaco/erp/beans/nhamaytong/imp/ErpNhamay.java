package geso.traphaco.erp.beans.nhamaytong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.nhamaytong.IErpNhamay;

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
	
	dbutils db;
	
	public ErpNhamay()
	{
		this.userId = "";
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.diachi = "";

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
		String query = "select  manhamay, tennhamay, diachi, trangthai from ERP_NHAMAYTONG where PK_SEQ = '" + this.id + "'";
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
					
					
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
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
			
			db.getConnection().setAutoCommit(false);
			
			if (this.isExistNhaMay()) {
				this.msg = "Mã nhà máy đã tồn tại. Vui lòng nhập mã nhà máy khác";
				return false;
			} else {
				String query = "insert ERP_NHAMAYTONG(manhamay, tennhamay, diachi, congty_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
						"values('" + this.ma + "', N'" + this.diengiai + "', N'" + this.diachi + "', '" + this.congtyId + "', '" + this.trangthai + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
		
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_NHAMAYTONG " + query;
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
			
			if (this.isExistNhaMay()) {
				this.msg = "Mã nhà máy đã bị trùng với mã nhà máy khác. Vui lòng nhập lại";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			
			
			String query = "update ERP_NHAMAYTONG set  manhamay = '" + this.ma + "', tennhamay = N'" + this.diengiai + "', diachi = N'" + this.diachi + "', congty_fk = '" + this.congtyId + "', trangthai = '" + this.trangthai + "', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";

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
		String query = "select manhamay from ERP_NHAMAYTONG";
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

	@Override
	public String getNhamayId() {
		// TODO Auto-generated method stub
		return this.NhamayId;
	}

	@Override
	public void setNhamayId(String NhamayId) {
		// TODO Auto-generated method stub
		this.NhamayId=NhamayId;
	}

	@Override
	public ResultSet getRsNhamay() {
		// TODO Auto-generated method stub
		return this.nhamayrs;
	}

	@Override
	public void setRsNhamay(ResultSet rs) {
		// TODO Auto-generated method stub
		this.nhamayrs=rs;
	}

	@Override
	public void creaters() {
		// TODO Auto-generated method stub
		try{
			
			String query="select pk_Seq ,manhamay +' - ' +tennhamay as ten from [ERP_NHAMAYTONG]";
			this.nhamayrs=db.get(query);
			
			
		}catch(Exception er){}
	}
	
	
	

}
