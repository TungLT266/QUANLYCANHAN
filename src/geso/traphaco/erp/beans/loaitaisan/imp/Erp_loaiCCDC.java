package geso.traphaco.erp.beans.loaitaisan.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.loaitaisan.IErp_loaiCCDC;

public class Erp_loaiCCDC extends Phan_Trang implements IErp_loaiCCDC
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -796740377599462235L;
	dbutils db;
	String Id, Ten, Ma, TkId, Msg, Nguoitao, Nguoisua, Ngaytao, Ngaysua, Trangthai, userTen, userId, ctyId;
	ResultSet TsList;
	ResultSet TkList;

	public Erp_loaiCCDC()
	{
		this.Id = "";
		this.Ma = "";
		this.Ten = "";
		this.TkId = "";
		this.Msg = "";
		this.Nguoitao = "";
		this.Nguoisua = "";
		this.Ngaytao = "";
		this.Ngaysua = "";
		this.Trangthai = "1";
		this.userId = "";
		this.userTen = "";
		this.ctyId = "";
		db = new dbutils();
	}

	public Erp_loaiCCDC(String id)
	{
		this.Id=id;
		this.Ma = "";
		this.Ten = "";
		this.TkId = "";
		this.Msg = "";
		this.Nguoitao = "";
		this.Nguoisua = "";
		this.Ngaytao = "";
		this.Ngaysua = "";
		this.Trangthai = "1";
		this.userId = "";
		this.userTen = "";
		this.ctyId = "";
		db = new dbutils();
	}

	public String getId()
	{

		return Id;
	}

	public String getMa()
	{

		return Ma;
	}

	public String getTen()
	{

		return Ten;
	}

	public String getTkId()
	{

		return TkId;
	}

	public String getMsg()
	{

		return Msg;
	}

	public void setId(String id)
	{

		this.Id = id;
	}

	public void setMa(String ma)
	{

		this.Ma = ma;
	}

	public void setTen(String ten)
	{

		this.Ten = ten;
	}

	public void setTkId(String tkId)
	{

		this.TkId = tkId;
	}

	public void setMsg(String Msg)
	{

		this.Msg = Msg;
	}

	public ResultSet getRsts()
	{

		return TsList;
	}

	public void setRsts(ResultSet Rsts)
	{

		this.TsList = Rsts;
	}

	public ResultSet getRsTk()
	{

		return TkList;
	}

	public void setRsTk(ResultSet Rstk)
	{

		this.TkList = Rstk;
	}

	public void setUserid(String userId)
	{

		this.userId = userId;
	}

	public String getUserid()
	{

		return userId;
	}

	public void setUserTen(String userTen)
	{

		this.userTen = userTen;
	}

	public String getUserTen()
	{

		return userTen;
	}

	public boolean ThemMoiMa()
	{

		if (!CheckUnique())
		{
			this.Msg = "Mã nhóm tài khoản này đã có,vui lòng chọn mã khác";
			return false;
		}
		String query = "INSERT erp_loaiCCDC(ma, diengiai, taikhoan_fk, trangthai, nguoitao, nguoisua, ngaytao, ngaysua, congty_fk) VALUES('"
				+ this.Ma+ "', N'"+ this.Ten+ "', '"+ this.TkId+ "','"+ this.Trangthai+ "','"+ this.Nguoitao
				+ "','" + this.Nguoisua + "','" + this.Ngaytao + "','" + this.Ngaysua + "', "+ this.ctyId +")";
		System.out.println("them moi: " + query);
		if (db.update(query))
		{
			return true;
		} else
		{
			this.Msg = "Khong the them moi nhom tai san "+query;
			return false;
		}
	}

	public boolean UpdateMa()
	{

		if (!CheckUnique())
		{
			this.Msg = "Mã nhóm tài khoản này đã có,vui lòng chọn mã khác";
			return false;
		}
		String query = "UPDATE erp_loaiCCDC SET ma = '" + this.Ma + "', diengiai = N'" + this.Ten
				+ "', taikhoan_fk = '" + this.TkId + "',nguoisua = '" + this.Nguoisua + "',ngaysua = '" + this.Ngaysua
				+ "', trangthai = '" + this.Trangthai + "' " + " WHERE pk_seq = '" + this.Id + "'";

		System.out.println("update: " + query);
		if (db.update(query))
		{
			return true;
		} else
		{
			this.Msg = "Khong the cap nhat  nhom tai san "+query;
			return false;
		}
	}

	public void DBClose()
	{

		try
		{
			if (TsList != null)
			{
				TsList.close();
			}

			if (TkList != null)
			{
				TkList.close();
			}
			if (db != null)
			{
				db.shutDown();
			}

		} catch (Exception er)
		{
		}
	}

	public void init()
	{
		db = new dbutils();
		this.Msg = "";
		String query = " SELECT * FROM erp_loaiCCDC WHERE pk_seq = '" + this.Id + "' ";
		
		System.out.println(query);
		ResultSet obj = db.get(query);
		try
		{
			if (obj.next())
			{
				this.Id = obj.getString("pk_seq");
				this.Ma = obj.getString("ma");
				this.Ten = obj.getString("diengiai");
				this.Nguoitao = obj.getString("nguoitao");
				this.Nguoisua = obj.getString("nguoisua");
				this.Ngaytao = obj.getString("ngaytao");
				this.Ngaysua = obj.getString("ngaysua");
				this.Trangthai = obj.getString("trangthai");
				this.TkId = obj.getString("taikhoan_fk")==null?"0":obj.getString("taikhoan_fk");

			}

			if (obj != null)
			{
				obj.close();
			}

		} catch (Exception er)
		{
			this.Msg = "Loi" + er.toString();
		}
		
	}

	public void createRs()
	{
		String query = "select * from erp_taikhoankt where trangthai = '1' and CONGTY_FK = " + this.ctyId + " and npp_fk = 1 order by soHieuTaiKhoan";
		System.out.println(query);
		this.TkList = this.db.get(query);
	}

	public String getNgaytao()
	{
		return Ngaytao;
	}

	public String getNguoitao()
	{

		return Nguoitao;
	}

	public String getNgaysua()
	{

		return Ngaysua;
	}

	public String getNguoisua()
	{

		return Nguoisua;
	}


	public void setNgaytao(String ngaytao)
	{

		this.Ngaytao = ngaytao;
	}

	public void setNguoitao(String nguoitao)
	{

		this.Nguoitao = nguoitao;
	}

	public void setNgaysua(String ngaysua)
	{

		this.Ngaysua = ngaysua;
	}

	public void setNguoisua(String nguoisua)
	{

		this.Nguoisua = nguoisua;
	}

	public String getTrangthai()
	{

		return Trangthai;
	}
	
	public void setCtyId(String ctyId)
	{

		this.ctyId = ctyId;
	}
	
	public String getCtyId()
	{

		return this.ctyId;
	}

	public boolean Delete()
	{

		if (!CheckReferences("loaiCCDC_FK", "Erp_CONGCUDUNGCU"))
		{
			this.Msg = " Loại Công cụ dụng cụ này đã được sử dụng, bạn phải xóa Công cụ dụng cụ dùng nó trước ";
			return false;
		}
		String query = "DELETE Erp_loaiCCDC WHERE PK_SEQ='" + this.Id + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa Loai công cụ dụng cụ này " + query;
			return false;
		}
		return true;
	}

	public void setTrangthai(String trangthai)
	{

		this.Trangthai = trangthai;
	}

	public boolean CheckUnique()
	{
		String query = "";
		if (this.Id.length() > 0)
			query = "Select count(*) as count From Erp_loaiCCDC Where MA=N'" + this.Ma + "' AND PK_SEQ !='" + this.Id
					+ " '";
		else
			query = "Select count(*) as count From Erp_loaiCCDC Where MA=N'" + this.Ma + "' AND CONGTY_FK = " + this.ctyId + "";
		int count = 0;
		ResultSet rs = this.db.get(query);
		if (rs != null)
			try
			{
				while (rs.next())
				{
					count = rs.getInt("count");
				}
				rs.close();
				if (count > 0)
					return false;
			} catch (SQLException e)
			{
				return false;
			}
		return true;
	}

	public boolean CheckReferences(String column, String table)
	{
		String query = "SELECT count(" + column + ") AS NUM  FROM " + table + " WHERE " + column + " =" + this.Id + "";
		System.out.println("CheckReferences " + query);
		ResultSet rs = db.get(query);
		try
		{// kiem tra ben san pham
			while (rs.next())
			{
				if (rs.getString("num").equals("0"))
					return true;
			}
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		return false;
	}

}
