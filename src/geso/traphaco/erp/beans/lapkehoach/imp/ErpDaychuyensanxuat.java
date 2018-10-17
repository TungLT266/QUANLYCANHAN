package geso.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import geso.erp.beans.lapkehoach.IErpDaychuyensanxuat;
import java.util.Date;
import geso.dms.db.sql.dbutils;

public class ErpDaychuyensanxuat implements IErpDaychuyensanxuat
{
	String ctyId;
	String userId;
	String id;
	
	String ma;
	String diengiai;
	String soluongchuan;
	String thoigian;
	
	ResultSet spRs;
	String spIds;
	
	ResultSet cumRs;
	String cumIds;
	
	String msg;
	
	dbutils db;
	
	public ErpDaychuyensanxuat()
	{
		this.ctyId = "";
		this.userId = "";
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.soluongchuan = "";
		this.thoigian = "";
		this.spIds = "";
		this.cumIds = "";

		this.msg = "";
		this.db = new dbutils();
	}
	
	public ErpDaychuyensanxuat(String id)
	{
		this.ctyId = "";
		this.id = id;
		this.ma = "";
		this.diengiai = "";
		this.soluongchuan = "";
		this.thoigian = "";
		this.spIds = "";
		this.cumIds = "";

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
		String query = "select ma, diengiai, soluongchuan, sanpham_fk, cumsanxuat_fk, thoigian from Erp_Daychuyensanxuat where PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				NumberFormat formatter = new DecimalFormat("#,###,###"); 
				while(rs.next())
				{
					this.diengiai = rs.getString("diengiai");
					this.ma =  rs.getString("ma");
					this.soluongchuan = formatter.format(rs.getDouble("soluongchuan"));
					
					this.thoigian = rs.getString("thoigian");
					this.spIds = rs.getString("sanpham_fk");
					this.cumIds = rs.getString("cumsanxuat_fk"); 
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		this.createRs();
	}
	


	public boolean createDaychuyensanxuat()
	{	
		try 
		{
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã cụm sản xuất";
				return false;
			}
			
			if(this.soluongchuan.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số lượng chuẩn của dây chuyền sản xuất";
				return false;
			}
			
			if(this.spIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm của dây chuyền sản xuất";
				return false;
			}
			
			if(this.cumIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn cụm sản xuất của dây chuyền sản xuất";
				return false;
			}
			
			if(this.thoigian.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn thời gian sản xuất của dây chuyền sản xuất";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert Erp_DayChuyenSanXuat(congty_fk, ma, diengiai, trangthai, sanpham_fk, cumsanxuat_fk, soluongchuan, thoigian, ngaytao, nguoitao, ngaysua, nguoisua) " +
							"values(" + this.ctyId + ", '" + this.ma + "', N'" + this.diengiai + "', '1', '" + this.spIds + "', '" + this.cumIds + "', '" + this.soluongchuan + "', '" + this.thoigian + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới Erp_Daychuyensanxuat " + query;
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
	
	public boolean updateDaychuyensanxuat() 
	{
		try 
		{
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã cụm sản xuất";
				return false;
			}
			
			if(this.soluongchuan.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số lượng chuẩn của dây chuyền sản xuất";
				return false;
			}
			
			if(this.spIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm của dây chuyền sản xuất";
				return false;
			}
			
			if(this.cumIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn cụm sản xuất của dây chuyền sản xuất";
				return false;
			}
			
			if(this.thoigian.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn thời gian sản xuất của dây chuyền sản xuất";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "Update Erp_DayChuyenSanXuat set ma = '" + this.ma + "', diengiai = N'" + this.diengiai + "', sanpham_fk = '" + this.spIds + "', cumsanxuat_fk = '" + this.cumIds + "', " +
							 "soluongchuan = '" + this.soluongchuan + "', thoigian = '" + this.thoigian + "', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";
							
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới Erp_Daychuyensanxuat " + query;
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
		this.spRs = db.get("select pk_seq, ten from sanpham where trangthai = '1' and loaisanpham_fk in ( 100005, 100011 ) and congty_fk = " + this.ctyId + " ");
		
		this.cumRs = db.get("select pk_seq, ma from Erp_CumSanXuat where trangthai = '1' and nhamay_fk in (select pk_seq from erp_nhamay where congty_fk = " + this.ctyId + " ) order by pk_seq desc");
	}
	
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
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

	
	public ResultSet getSpRs() 
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs) 
	{
		this.spRs = spRs;
	}

	public String getSpIds() 
	{
		return this.spIds;
	}

	public void setSpIds(String spIds) 
	{
		this.spIds = spIds;
	}

	public ResultSet getCumsxRs() 
	{
		return this.cumRs;
	}

	public void setCumsxRs(ResultSet cumsxRs) 
	{
		this.cumRs = cumsxRs;
	}

	public String getCumsxIds() 
	{
		return this.cumIds;
	}

	public void setCumsxIds(String cumsxIds)
	{
		this.cumIds = cumsxIds;
	}

	public String getSoluongchuan() 
	{
		return this.soluongchuan;
	}

	public void setSoluongchuan(String soluongchuan) 
	{
		this.soluongchuan = soluongchuan;
	}

	public String getThoigian() 
	{
		return this.thoigian;
	}

	public void setThoigian(String thoigian) 
	{
		this.thoigian = thoigian;
	}
	
	
	public void DbClose() 
	{
		try 
		{
			if(this.spRs != null)
				this.spRs.close();
			if(this.cumRs != null)
				this.cumRs.close();
			
			this.db.shutDown();
		} 
		catch (Exception e) {}
		
	}

}
