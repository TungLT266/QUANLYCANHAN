package geso.traphaco.erp.beans.lenhsanxuat.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.lenhsanxuat.IErpLenhsanxuatList;

public class ErpLenhsanxuatList extends Phan_Trang implements IErpLenhsanxuatList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungayTao;
	String denngayTao;
	String tungayDk;
	String denngayDk;
	
	String masanpham;
	String trangthai;
	String msg;
	
	ResultSet lsxRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpLenhsanxuatList()
	{
		this.tungayTao = "";
		this.denngayTao = "";
		this.tungayDk = "";
		this.denngayDk = "";
		this.trangthai = "";
		this.masanpham = "";
		this.msg = "";	
		currentPages = 1;
		num = 1;
		//this.db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	public int getCurrentPage()
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages() 
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as c from ERP_LENHSANXUAT");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String search)
	{
		String query = "";
		
		if(search.length() > 0)
			query = search;
		else
			query = "select a.PK_SEQ ,a.trangthai, a.ngaybatdau, isnull(a.NgayDuKienHT, '') as NgayDuKienHT, sp.pk_seq as spId, sp.TEN as spTen, NV.TEN as nguoitao, " +
						"a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.dondathang_fk, '-1') as dondathang_fk, a.kichbansanxuat_fk, " +
						"( select COUNT(pk_seq) from ERP_KICHBANSANXUAT where sanpham_fk = sp.PK_SEQ and trangthai = '1' ) as sodong   " +
					"from ERP_LENHSANXUAT a  " +
					"inner Join ERP_SanPham sp on a.SANPHAM_FK = sp.PK_SEQ inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ ";
		
		this.lsxRs = createSplittingDataNew(this.db, 50, 10, "ngaybatdau desc, trangthai asc ", query);
	}
	
	public void DBclose() 
	{
		try
		{
			if(this.lsxRs!=null)
				this.lsxRs.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
		if(this.db!= null)
		this.db.shutDown();
		}
	}

	
	public String getTungayTao() 
	{
		return this.tungayTao;
	}

	public void setTungayTao(String tungay) 
	{
		this.tungayTao = tungay;
	}

	public String getDenngayTao() 
	{
		return this.denngayTao;
	}

	public void setDenngayTao(String denngay)
	{
		this.denngayTao = denngay;
	}

	public String getTungayDk() 
	{
		return this.tungayDk;
	}

	public void setTungayDk(String ngaydk)
	{
		this.tungayDk = ngaydk;
	}

	public String getDenngayDk() 
	{	
		return this.denngayDk;
	}

	public void setDenngayDk(String ngaydk) 
	{
		this.denngayDk = ngaydk;
	}

	public String getMasp() 
	{
		return this.masanpham;
	}

	public void setMasp(String masp) 
	{
		this.masanpham = masp;
	}

	public ResultSet getLsxRs() 
	{
		return this.lsxRs;
	}

	public void setLsxRs(ResultSet lsxRs) 
	{
		this.lsxRs = lsxRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
}
