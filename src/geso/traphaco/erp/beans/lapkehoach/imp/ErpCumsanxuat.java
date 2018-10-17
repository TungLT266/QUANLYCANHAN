package geso.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import geso.erp.beans.lapkehoach.IErpCumsanxuat;
import java.util.Date;

import geso.dms.db.sql.dbutils;

public class ErpCumsanxuat implements IErpCumsanxuat
{
	String ctyId;
	String userId;
	String id;
	
	String ma;
	String diengiai;
	String sonhancong;
	
	ResultSet nhamayRs;
	String nhamayIds;
	
	ResultSet tbRs;
	String tbIds;
	
	String trangthai;
	String msg;
	
	dbutils db;
	
	public ErpCumsanxuat()
	{
		this.ctyId = "";
		this.userId = "";
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.sonhancong = "";
		this.tbIds = "";
		this.nhamayIds = "";

		this.trangthai = "1";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public ErpCumsanxuat(String id)
	{
		this.id = id;
		this.ma = "";
		this.diengiai = "";
		this.sonhancong = "";
		this.tbIds = "";
		this.nhamayIds = "";

		this.trangthai = "1";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;	
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
		String query = "select ma, diengiai, sonhancong, nhamay_fk, trangthai from Erp_CumSanXuat where PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.diengiai = rs.getString("diengiai");
					this.ma =  rs.getString("ma");
					this.sonhancong = rs.getString("sonhancong");
					this.nhamayIds = rs.getString("nhamay_fk");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		//create Ids
		query = "select thietbi_fk from Erp_CumSanXuat_ThietBi where cumsanxuat_fk = '" + this.id + "'";
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String str = "";
				while(rs.next())
				{
					str += rs.getString("thietbi_fk") + ",";
				}
				rs.close();
				
				if(str.trim().length() > 0)
				{
					str = str.substring(0, str.length() - 1);
					this.tbIds = str;
				}
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception 2: " + e.getMessage());
			}
		}
		
		this.createRs();
	}
	


	public boolean createCumsanxuat()
	{	
		try 
		{
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã cụm sản xuất";
				return false;
			}
			
			if(this.sonhancong.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số nhân công cụm sản xuất";
				return false;
			}
			
			if(this.tbIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn thiết bị cho cụm sản xuất";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert Erp_CumSanXuat(ma, diengiai, sonhancong, nhamay_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
							"values('" + this.ma + "', N'" + this.diengiai + "', '" + this.sonhancong + "', '" + this.nhamayIds + "', '" + this.trangthai + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới Erp_CumSanXuat " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert Erp_CumSanXuat_ThietBi(cumsanxuat_fk, thietbi_fk) " +
					"select IDENT_CURRENT('Erp_CumSanXuat'), pk_seq from ERP_TAISANCODINH where pk_seq in ( " + this.tbIds + " )";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới Erp_CumSanXuat_ThietBi " + query;
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
	
	public boolean updateCumsanxuat() 
	{
		try 
		{
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã cụm sản xuất";
				return false;
			}
			
			if(this.sonhancong.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số nhân công cụm sản xuất";
				return false;
			}
			
			if(this.tbIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn thiết bị cho cụm sản xuất";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update Erp_CumSanXuat set ma = '" + this.ma + "', diengiai = N'" + this.diengiai + "', sonhancong = '" + this.sonhancong + "', nhamay_fk = '" + this.nhamayIds + "', trangthai = '" + this.trangthai + "', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Erp_CumSanXuat " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete Erp_CumSanXuat_ThietBi where cumsanxuat_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Erp_CumSanXuat_ThietBi " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert Erp_CumSanXuat_ThietBi(cumsanxuat_fk, thietbi_fk) " +
					"select IDENT_CURRENT('Erp_CumSanXuat'), pk_seq from ERP_TAISANCODINH where pk_seq in ( " + this.tbIds + " )";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới Erp_CumSanXuat_ThietBi " + query;
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

	public void createRs() 
	{
		this.nhamayRs = db.get("select pk_seq, manhamay + ', ' + tennhamay as nhamayTen from ERP_NHAMAY where congty_fk = " + this.ctyId + " ");
		this.tbRs = db.get("select pk_seq, ma, ten from ERP_TAISANCODINH where congdung_fk = '9000000' and congty_fk = " + this.ctyId + " ");
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
			if(this.tbRs != null)
				this.tbRs.close();
			
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

	public ResultSet getThietbiRs() 
	{
		return this.tbRs;
	}

	public void setThietbiRs(ResultSet tbRs)
	{
		this.tbRs = tbRs;
	}

	public String getTbIds() 
	{
		return this.tbIds;
	}

	public void setTbIds(String tbIds) 
	{
		this.tbIds = tbIds;
	}

	public String getSonhancong() 
	{
		return this.sonhancong;
	}

	public void setSonhancong(String sonhancong) 
	{
		this.sonhancong = sonhancong;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public ResultSet getNhamayRs() 
	{
		return this.nhamayRs;
	}

	public void setNhamayRs(ResultSet nmRs) 
	{
		this.nhamayRs = nmRs;
	}

	public String getNhamayIds() 
	{
		return this.nhamayIds;
	}

	public void setNhamayIds(String nmIds)
	{
		this.nhamayIds = nmIds;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	
	

}
