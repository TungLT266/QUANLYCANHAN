package geso.traphaco.erp.beans.erp_nhomtaisan.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.erp_nhomtaisan.IErp_nhomCCDCList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Erp_nhomCCDCList extends Phan_Trang implements IErp_nhomCCDCList
{
	private static final long serialVersionUID = -796740377599462235L;
	dbutils db;
	String Id, Ten, Ma, Msg, Nguoitao, Nguoisua, Ngaytao, Ngaysua, Trangthai, userTen, userId;
	
	private String congTyId;
	
	String ltsId, cdtsId;
	ResultSet ltsRs, cdtsRs;
	
	ResultSet TsList;
	

	public Erp_nhomCCDCList()
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
		this.congTyId = "";
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
				this.ltsRs.close();
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
			if (this.db != null)
			{
				this.db.shutDown();
			}
		}
	}

	public void init()
	{
		Utility util = new Utility();
		
		String query = 	"select nts.pk_seq, nts.ma , nts.diengiai, lts.diengiai as loaitaisan, \n" +
						" nts.trangthai, nts.ngaysua, ns.ten as nguoisua\n" +
						" from erp_nhomCCDC nts\n"	+
						" inner join nhanvien nt on nt.pk_seq = nts.nguoitao\n" +
						" inner join nhanvien ns on ns.pk_seq = nts.nguoisua \n" +
//						" LEFT JOIN ERP_NHOMCCDC_CONGDUNG NCD_CD ON NCD_CD.NHOMCCDC_FK = NTS.PK_SEQ\n" +
						" left join erp_loaiCCDC lts on lts.pk_seq = nts.loaiCCDC_fk \n" +
						" WHERE 1=1 \n";
		
		if (this.congTyId.length() > 0)
			query += "and nts.CONGTY_FK = " + this.congTyId + "\n";
		
		if (this.Ma.length() > 0)
		{
			query = query + " and nts.ma like N'%" + Ma + "%\n'";
		}

		if (this.Ten.length() > 0)
		{
			query = query + " and dbo.ftBoDau(nts.diengiai) like N'%" + util.replaceAEIOU(Ten) + "%'\n";
		}

		if (this.ltsId.length() > 0)
		{
			query = query + " and nts.loaiCCDC_fk = " + this.ltsId + " \n";
		}

		if (this.cdtsId.length() > 0)
		{
			query = query + " and NTS_CD.congdung_fk = " + this.cdtsId + " \n";
		}

		System.out.println("query tai san list:\n" + query + "\n-----------------------------------------------");
		this.TsList = createSplittingDataNew(this.db, 50, 10, " pk_seq, TRANGTHAI, MA, DIENGIAI  ", query);

		System.out.println("query list: " + query);

		query = "SELECT PK_SEQ AS LTSID, DIENGIAI FROM ERP_LOAICCDC WHERE TrangThai = 1 and congty_fk = " + this.congTyId;
		
		this.ltsRs = this.db.get(query);
		
		query = "SELECT PK_SEQ AS CDTSID, TEN AS DIENGIAI FROM ERP_CONGDUNGCCDC WHERE TrangThai = 1 and congty_fk = " + this.congTyId;
				
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

/*		if (!CheckReferences("NhomCCDC_FK", "Erp_CONGCUDUNGCU"))
		{
			this.Msg = " Nhóm tài sản này đã có tài sản bạn phải xóa tài sản trước ";
			return false;
		}*/
		String query = "DELETE ERP_NHOMCCDC_CONGDUNG WHERE NHOMCCDC_FK = '" + this.Id + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa nhóm công cụ dụng cụ " + query;
			return false;
		}
		
		query = "DELETE ERP_NHOMCCDC_PHANBOKHAUHAO WHERE NHOMCCDC_FK = '" + this.Id + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa nhóm công cụ dụng cụ " + query;
			return false;
		}
		
		query = "DELETE Erp_NhomCCDC WHERE PK_SEQ='" + this.Id + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa nhóm công cụ dụng cụ " + query;
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

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getCongTyId() {
		return congTyId;
	}
}