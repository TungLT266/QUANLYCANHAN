package geso.traphaco.erp.beans.congdoansanxuatgiay.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.congdoansanxuatgiay.IErpCongDoanSanXuatGiayList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ErpCongDoanSanXuatGiayList extends Phan_Trang implements IErpCongDoanSanXuatGiayList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String ctyId;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String ma;
	String diengiai;
	String msg,sanphamId;

	ResultSet CumsanxuatRs,rsSp;


	dbutils db;

	public ErpCongDoanSanXuatGiayList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.diengiai = "";
		this.ma = "";
		this.msg = "";
		sanphamId="";
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

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getMa()
	{
		return this.ma;
	}

	public void setMa(String ma)
	{
		this.ma = ma;
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public void init(String query)
	{
		String sql = "";

		if (query.length() > 0)
			sql = query;
		else
		{
			sql =
		"select  a.pk_seq, a.diengiai  "+
		"		, a.sonhancong, a.trangthai, c.ten as nguoitao, a.ngaytao, d.ten as nguoisua, a.ngaysua ,nm.tennhamay as nhamay  "+
		" from Erp_CongDoanSanXuat_Giay a  left join erp_nhamay nm on nm.pk_seq=a.nhamay_fk " + 
		"	inner join NhanVien c on a.nguoitao = c.pk_seq "+           
		"	inner join nhanvien d on a.nguoisua = d.pk_seq "+       
		" where a.pk_seq > 0 and  a.nhamay_fk in (select pk_seq from erp_nhamay where congty_fk = " + this.ctyId + ") and a.congty_fk= "+this.ctyId;
		}

		System.out.println("__Cong doan san xuat : " + sql);
		this.CumsanxuatRs = db.get(sql);
	}

	public void DbClose()
	{
		try
		{
			if (this.CumsanxuatRs != null)
				this.CumsanxuatRs.close();
			if(this.rsSp!=null)
				this.rsSp.close();
			this.db.shutDown();
		} catch (SQLException e)
		{
		}
	}

	public ResultSet getCumsanxuatRs()
	{
		return this.CumsanxuatRs;
	}

	public void setCumsanxuatRs(ResultSet CumsanxuatRs)
	{
		this.CumsanxuatRs = CumsanxuatRs;
	}


	public String getSanPhamId()
	{
		
		return this.sanphamId;
	}


	public void setSanPhamId(String sanphamId)
	{
		this.sanphamId=sanphamId;
		
	}


	public ResultSet getRsSp()
	{
		
		return this.rsSp;
	}


	public void setRsSp(ResultSet rsSp)
	{
		this.rsSp=rsSp;
		
	}

}
