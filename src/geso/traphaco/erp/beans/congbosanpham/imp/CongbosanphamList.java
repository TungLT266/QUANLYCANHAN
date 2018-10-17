package geso.traphaco.erp.beans.congbosanpham.imp;

import geso.traphaco.erp.beans.congbosanpham.ICongbosanphamList;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author linhvn
 *
 */
public class CongbosanphamList extends Phan_Trang implements ICongbosanphamList
{
	private HttpServletRequest request;
	String ctyId;
	String ctyTen;
	String userId;

	String thoihan;
	String msg;
	String sanpham;

	ResultSet congbosanphamRs;
	String congbosanphamId;

	dbutils db;

	public CongbosanphamList()
	{
		this.ctyId = "";
		this.ctyTen = "";
		this.userId = "";
		this.msg = "";
		this.thoihan = "";
		this.congbosanphamId = "";
		this.sanpham = "";
		this.db = new dbutils();
	}

	public HttpServletRequest getRequestObj()
	{
		return this.request;
	}

	public void setRequestObj(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
	
	public String getCtyTen()
	{
		return this.ctyTen;
	}

	public void setCtyTen(String ctyTen)
	{
		this.ctyTen = ctyTen;
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
	
	public void setThoihan(String thoihan)
	{
		this.thoihan = thoihan;
	}
	
	public String getThoihan()
	{
		return this.thoihan;
	}

	public void init(String query)
	{
		String sql = "";

		if(query.length() > 0)
			sql = query;
		else
		{
			sql = "select ROW_NUMBER() OVER(ORDER BY sn.hancongbo ASC) AS 'stt', sn.pk_seq as id, sp.ma as masanpham, sp.TEN as sanpham, " +
				" sp.loaisanpham_fk as loaisanpham, isnull(ncc.ma,ct.ma) as manhacungcap, isnull(ncc.TEN,ct.TEN) as nhacungcap, sn.hancongbo, sn.filename, sn.hinhcongbo " + 
				" from erp_sanpham_nhacungcap sn left join erp_SANPHAM sp on sn.sanpham_fk = sp.PK_SEQ " +
				" left join ERP_LOAISANPHAM lsp on lsp.PK_SEQ = sp.LOAISANPHAM_FK left join ERP_CONGTY ct on ct.PK_SEQ = sp.CONGTY_FK " +
				" left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = sn.nhacungcap_fk " + 
				//" where (ncc.TRANGTHAI='1' or (sn.nhacungcap_fk is null and lsp.MA like '%TP%')) and sp.trangthai='1' and sp.congty_fk='" + this.ctyId + "' ";
				" where sp.trangthai='1' and sp.congty_fk='" + this.ctyId + "' ";
		}
		System.out.println("init cong o sp: \n" + sql + "\n===============================");
		this.congbosanphamRs = db.get(sql);
		this.congbosanphamRs = super.createSplittingDataNew(this.db, super.getItems(), super.getSplittings(), "hancongbo asc", sql);
	}

	public void DbClose()	
	{
		try
		{
			if(this.congbosanphamRs != null)
				this.congbosanphamRs.close();
			
		}
		catch (SQLException e) {}
		finally
		{
			if(this.db!=null)
			this.db.shutDown();
		}
	}

	public ResultSet getCongbosanphamRs()
	{
		return this.congbosanphamRs;
	}

	public void setCongbosanphamRs(ResultSet congbosanphamRs)
	{
		this.congbosanphamRs = congbosanphamRs;
	}

	public String getCongbosanphamId()
	{
		return this.congbosanphamId;
	}

	public void setCongbosanphamId(String congbosanphamId)
	{
		this.congbosanphamId = congbosanphamId;
	}
	
	public String convertDate(String date)
	{
		if (!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if (arr[0].length() > arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}
	
	public String getSanPham() {
		return this.sanpham;
	}


	public void setSanPham(String sanpham) {
		this.sanpham=sanpham;
		
	}

}
