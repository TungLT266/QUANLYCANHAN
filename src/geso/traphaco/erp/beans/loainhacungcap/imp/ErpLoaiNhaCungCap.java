package geso.traphaco.erp.beans.loainhacungcap.imp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.loainhacungcap.IErpLoaiNhaCungCap;

public class ErpLoaiNhaCungCap  extends Phan_Trang implements IErpLoaiNhaCungCap{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7098075456466668852L;
	String id;
	String Ten;
	String trangthai;
	String Ma;
	String ngaytao;
	String ngaysua;
	String userid;
	String msg;
	ResultSet rsLoaiNhaCungCap;
	dbutils db;

	String chixem;
	
	public ErpLoaiNhaCungCap()
	{
		this.id = "";
		this.Ten = "";
		this.trangthai = "";
		this.Ma = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.userid = "";
		this.msg = "";
		this.db = new dbutils();
		this.chixem = "0";
	}

	public ErpLoaiNhaCungCap(String id)
	{
		this.id = id;
		this.Ten = "";
		this.trangthai = "";
		this.Ma = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.userid = "";
		this.msg = "";
		this.db = new dbutils();
		this.chixem = "0";
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTen()
	{
		return this.Ten;
	}

	public void setTen(String Ten)
	{
		this.Ten = Ten;
	}

	public void setMa(String Ma)
	{
		this.Ma = Ma;
	}

	public String getMa()
	{
		return this.Ma;
	}

	public String getNgayTao()
	{
		return this.ngaytao;
	}

	public String getNgaySua()
	{
		return this.ngaysua;
	}

	public void setNgayTao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}

	public void setNgaySua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}

	public String getUserId()
	{
		return this.userid;
	}

	public void setUserId(String userid)
	{
		this.userid = userid;
	}

	public String getTrangThai()
	{
		return trangthai;
	}

	public void setTrangThai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public void Init()
	{
		this.rsLoaiNhaCungCap = null;
		PreparedStatement prep = null;
		String query = "Select PK_SEQ,Ma,isnull(Ten,'')Ten,NgaySua,isnull(TrangThai,0)TrangThai From ERP_LOAINHACUNGCAP Where PK_SEQ=?";
		try
		{
			prep = this.db.getConnection().prepareStatement(query);
			prep.setString(1, this.id);
			this.rsLoaiNhaCungCap = prep.executeQuery();
			while (rsLoaiNhaCungCap.next())
			{
				this.id = rsLoaiNhaCungCap.getString("PK_SEQ");
				this.Ma = rsLoaiNhaCungCap.getString("Ma");
				this.Ten = rsLoaiNhaCungCap.getString("Ten");
				this.ngaysua = rsLoaiNhaCungCap.getString("NgaySua");
				this.trangthai = rsLoaiNhaCungCap.getString("TrangThai");
			}
			rsLoaiNhaCungCap.close();
			prep.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void search()
	{
		String query = " Select l.PK_SEQ ,l.Ma,l.Ten,ISNULL(nt.NguoiTao,'')NguoiTao ,ISNULL(ns.NguoiSua,'')NguoiSua,ISNULL(l.NgayTao,'')NgayTao,"
			+ " ISNULL(l.NgaySua,'')NgaySua,ISNULL(l.TrangThai,0)TrangThai "
			+ " From ERP_LOAINHACUNGCAP l "
			+ " INNER JOIN "
			+ " ( Select PK_SEQ,Ten as NguoiTao "
			+ " From NHANVIEN)nt on nt.PK_SEQ=l.NGUOITAO "
			+ " INNER Join"
			+ " ( Select PK_SEQ ,Ten as NguoiSua " + " From NhanVien )ns on ns.PK_SEQ=l.NGUOISUA Where 1=1 ";
		System.out.println("Search" + query);
		if (this.Ma.length() > 0)
		{
			query += " and  l.Ma like N'%" + this.Ma + "%' ";
		}
		if (this.Ten.length() > 0)
		{
			query += " and l.Ten like N'%" + this.Ten + "%'";
		}
		this.rsLoaiNhaCungCap =createSplittingDataNew(this.db, 50, 10, " PK_SEQ DESC,TRANGTHAI  ", query); 
		System.out.println("Search LoaiTaiKhoan : " + query);
	}

	public boolean Create()
	{
		if(!CheckValid())
		{
			return false;
		}
		String query = "";
		try
		{
			query = "Insert into ERP_LOAINHACUNGCAP(Ma,Ten,NgayTao,NgaySua,NguoiTao,NguoiSua,TrangThai) values(" + "N'" +
				this.Ma + "',N'" + this.Ten + "','" + getDateTime() + "','" + getDateTime() + "','" + this.userid + "','" +
				this.userid + "' ,'" + this.trangthai + "')";
		
			System.out.println("Create Query " + query);
			this.db.getConnection().setAutoCommit(false);
			if (this.db.update(query))
			{
				System.out.println("Tao duoc ERP_LOAINHACUNGCAP ");

				String currentId = "";
				query = "Select IDENT_CURRENT('ERP_LOAINHACUNGCAP') as currentId";
				ResultSet rsId = this.db.get(query);
				if (rsId != null)
				{
					while (rsId.next())
					{
						currentId = rsId.getString("currentId");
						System.out.println("ID" + currentId);
					}
					rsId.close();
				}
			} else
			{
				this.msg = "Không thể tạo mới nhóm nhà cung cấp :" + query;
				this.db.update("rollback");
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch (SQLException e)
		{
			this.db.update("rollback");
			this.msg = "Không thể tạo mới nhóm nhà cung cấp :" + query;
			this.db.shutDown();
			System.out.println("Exception");
			return false;
		}
	}

	public boolean Update()
	{
		
		if(!CheckValid())
		{
			return false;
		}
		String query = "Update ERP_LOAINHACUNGCAP set Ma=N'" + this.Ma + "',Ten=N'" + this.Ten + "',NgaySua='" +
			getDateTime() + "',NguoiSua='" + this.userid + "',TrangThai='" + this.trangthai + "" + "' Where PK_SEQ='" +this.id + "'";
		
		System.out.println("update Loai nha cung cap: " + query);
		try
		{
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.msg = "Không thể cập nhật:" + query;
				this.db.update("rollback");
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch (SQLException e)
		{
			this.msg = "Không thể cập nhật:" + query;
			this.db.update("rollback");
			System.out.println(e.getMessage());
			this.db.shutDown();
		}
		return false;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public ResultSet getRsLoaiNhaCungCap()
	{
		return this.rsLoaiNhaCungCap;
	}

	public void setRsLoaiNhaCungCap(ResultSet rsLoaiNhaCungCap)
	{
		this.rsLoaiNhaCungCap = rsLoaiNhaCungCap;
	}

	
	public boolean DeleteLncc()
	{
	   	ResultSet rs = db.get("select distinct COUNT(b.Loainhacungcap_fk) as num from ERP_LOAINHACUNGCAP a, ERP_NHACUNGCAP b " +
	   								"where a.pk_seq = b.Loainhacungcap_fk and a.PK_SEQ ='" + this.id + "'");
	   	
	   	try{
	   	    while(rs.next())
	   		if(rs.getString("num").equals("0")){	   			
	   			String query="DELETE FROM ERP_LOAINHACUNGCAP WHERE PK_SEQ='"+this.id+"'";
	   			if (!this.db.update(query)) {
	   				this.msg="Không thể xóa loại nhà cung cấp này !"+query;
					return false;
	   			}
	   		}
	   		else
	   			this.msg="Loại nhà cung cấp này đã có nhà cung cấp rồi, không thể xóa được !";
	   	}catch(java.sql.SQLException e){}
	  
		return false;
	}
	
	public boolean CheckValid()
	{
		String query="";
		
		if(this.id.length() > 0)
			query="Select count(*) sodong FROM ERP_LOAINHACUNGCAP WHERE Ma=N'"+this.Ma+"' and pk_seq <> '" + this.id + "'";
		else
			query="Select count(*) sodong FROM ERP_LOAINHACUNGCAP WHERE Ma=N'"+this.Ma+"'";
		
		try
		{
			System.out.println("CheckValid ERP_LOAINHACUNGCAP : " + query);
			int sodong = 0;
			ResultSet rs = this.db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
				System.out.println("So dong la: " + sodong + "\n");
				if(sodong > 0)
				{
					this.msg="Mã loại tài khoản này đã có vui lòng chọn mã khác";
					return false;
				}
			}else
				return false;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void closeDB(){
		try{
			if(this.rsLoaiNhaCungCap != null) this.rsLoaiNhaCungCap.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
		
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}
	
}
