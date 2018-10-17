package geso.traphaco.erp.beans.loaitaisan.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.loaitaisan.IErp_loaitaisanList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Erp_loaitaisanList extends Phan_Trang implements IErp_loaitaisanList
{
	private static final long serialVersionUID = -796740377599462235L;
	dbutils db;
	String Id, Ten, Ma, TkId, Msg, Nguoitao, Nguoisua, Ngaytao, Ngaysua, Trangthai, userTen, userId;
	ResultSet TsList;
	ResultSet TkList;

	public Erp_loaitaisanList()
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
		} catch (Exception er)
		{
			er.printStackTrace();
		}finally{
			if (db != null)
			{
				db.shutDown();
			}
		}
	}

	public void init()
	{
		Utility util = new Utility();
		
		String query = "select ts.pk_seq, ts.ma, ts.diengiai,ISNULL(TK.SOHIEUTAIKHOAN,'') AS SOHIEUTAIKHOAN ,ISNULL( TK.TENTAIKHOAN ,'')AS TENTAIKHOAN , ts.taikhoan_fk, ts.trangthai, ts.ngaytao, nt.ten as nguoitao, ts.ngaysua, ns.ten as nguoisua"
				+ " from erp_loaitaisan ts"
				+ " inner join nhanvien nt on nt.pk_seq = ts.nguoitao"
				+ " inner join nhanvien ns on ns.pk_seq = ts.nguoisua "
				+ " LEFT JOIN Erp_TaiKhoanKT TK ON TK.PK_SEQ=ts.TaiKhoan_FK  " + " WHERE 1=1 ";
		if (this.Ma.length() > 0)
		{
			query = query + " and ts.ma like N'%" + Ma + "%'";
		}

		if (this.Ten.length() > 0)
		{
			query = query + " and dbo.ftBoDau(ts.diengiai) like N'%" + util.replaceAEIOU(Ten) + "%'";
		}

		if (this.TkId.length() > 0)
		{
			query = query + " and ts.taikhoan_fk = '" + TkId + "'";
		}
		this.TsList = createSplittingDataNew(this.db, 50, 10, " PK_SEQ DESC,TRANGTHAI  ", query);
		System.out.println("query list " + query);
		System.out.println("query list " + query);
		query = "select * from erp_taikhoankt where trangthai = '1' and npp_fk = 1 ";
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

	public boolean DeleteNhts()
	{

		if (!CheckReferences("loaitaisan_FK", "Erp_TaiSanCoDinh"))
		{
			this.Msg = " Nhóm tài sản này đã có tài sản bạn phải xóa tài sản trước ";
			return false;
		}
		String query = "DELETE Erp_loaitaisan WHERE PK_SEQ='" + this.Id + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa nhóm tài sản " + query;
			return false;
		}
		return true;
	}

	public void setTrangthai(String trangthai)
	{

		this.Trangthai = trangthai;
	}

	public boolean CheckReferences(String column, String table)
	{
		String query = "SELECT count(" + column + ") AS NUM  FROM " + table + " WHERE " + column + " =" + this.Id + "";
		System.out.println("CheckReferences " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
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
			}finally{
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
		return false;
	}
}
