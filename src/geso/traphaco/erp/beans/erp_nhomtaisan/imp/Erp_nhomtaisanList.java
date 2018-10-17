package geso.traphaco.erp.beans.erp_nhomtaisan.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.erp_nhomtaisan.IErp_nhomtaisanList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Erp_nhomtaisanList extends Phan_Trang implements IErp_nhomtaisanList
{
	private static final long serialVersionUID = -796740377599462235L;
	dbutils db;
	String Id, Ten, Ma, Msg, Nguoitao, Nguoisua, Ngaytao, Ngaysua, Trangthai, userTen, userId;

	String ltsId, cdtsId;
	ResultSet ltsRs, cdtsRs;
	
	ResultSet TsList;
	

	public Erp_nhomtaisanList()
	{
		this.Id = "";
		this.Ma = "";
		this.Ten = "";
		this.Msg = "";
		this.ltsId = "";
		this.cdtsId = "";
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

	public ResultSet getRslts()
	{

		return this.ltsRs;
	}

	public void setRslts(ResultSet ltsRs)
	{

		this.ltsRs = ltsRs;
	}


	public void setLtsId(String ltsId)
	{

		this.ltsId = ltsId;
	}

	public String getLtsId()
	{

		return ltsId;
	}
	
	
	public ResultSet getRsCdts()
	{

		return this.cdtsRs;
	}

	public void setRsCdts(ResultSet cdtsRs)
	{

		this.cdtsRs = cdtsRs;
	}


	public void setCdtsId(String cdtsId)
	{

		this.cdtsId = cdtsId;
	}

	public String getCdtsId()
	{

		return cdtsId;
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
			if (this.ltsRs != null)
			{
				ltsRs.close();
			}
			if (this.cdtsRs != null)
			{
				this.cdtsRs.close();
			}
			if (TsList != null)
			{
				TsList.close();
			}

		
		} catch (Exception er)
		{
			er.printStackTrace();
		}
		finally
		{
			if (db != null)
			{
				db.shutDown();
			}

		}
	}

	public void init()
	{
		String query = 	"select nts.pk_seq, nts.ma , nts.diengiai, lts.diengiai as loaitaisan, " +
						" nts.trangthai, nts.ngaysua, ns.ten as nguoisua" +
						" from erp_nhomtaisan nts"	+
						" inner join nhanvien nt on nt.pk_seq = nts.nguoitao" +
						" inner join nhanvien ns on ns.pk_seq = nts.nguoisua " +
						" left join erp_loaitaisan lts on lts.pk_seq = nts.loaitaisan_fk " +
						" WHERE 1=1 ";
		
		System.out.println("Du lieu dau vao: " + query);
		
		if (this.Ma.length() > 0)
		{
			query = query + " and nts.ma like N'%" + Ma + "%'";
		}

		if (this.Ten.length() > 0)
		{
			query = query + " and nts.diengiai like N'%" + Ten + "%'";
		}

		if (this.ltsId.length() > 0)
		{
			query = query + " and nts.loaitaisan_fk = " + this.ltsId + " ";
		}

		if (this.cdtsId.length() > 0)
		{
			query = query + " and nts.pk_seq in (select NHOMTAISAN_FK from ERP_NHOMTAISAN_CONGDUNG where CONGDUNG_FK ='"+ cdtsId +"')";
		}

		this.TsList = createSplittingDataNew(this.db, 50, 10, " TRANGTHAI, MA, DIENGIAI  ", query);

		System.out.println("query list " + query);

		query = "SELECT PK_SEQ AS LTSID, DIENGIAI FROM ERP_LOAITAISAN WHERE TrangThai = 1";
		
		this.ltsRs = this.db.get(query);
		
		query = "SELECT PK_SEQ AS CDTSID, TEN AS DIENGIAI FROM ERP_CONGDUNG WHERE TrangThai = 1";
				
		this.cdtsRs = this.db.get(query);
		
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

		if (!CheckReferences("NhomTaiSan_FK", "Erp_TaiSanCoDinh"))
		{
			this.Msg = " Nhóm tài sản này đã có tài sản bạn phải xóa tài sản trước ";
			return false;
		}
		
		String query = "DELETE ERP_NHOMTAISAN_CONGDUNG WHERE NHOMTAISAN_FK = '" + this.Id + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa nhóm công cụ dụng cụ " + query;
			return false;
		}
		
		query = "DELETE ERP_NHOMTAISAN_PHANBOKHAUHAO WHERE NHOMTAISAN_FK = '" + this.Id + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa nhóm công cụ dụng cụ " + query;
			return false;
		}
		
		query = "DELETE Erp_NhomTaiSan WHERE PK_SEQ='" + this.Id + "'";
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
		try
		{// kiem tra ben san pham
			while (rs.next())
			{
				String tmp = rs.getString("num");
				rs.close();
				
				if (tmp.equals("0"))
					return true;
			}
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		return false;
	}

}
