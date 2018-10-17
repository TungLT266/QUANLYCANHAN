package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpChuyenkhoSXList;

public class ErpChuyenkhoSXList extends Phan_Trang implements IErpChuyenkhoSXList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungayTao;
	String denngayTao;

	String masanpham;
	String trangthai;
	String msg;
	String isnhanHang;
	
	ResultSet lsxRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpChuyenkhoSXList()
	{
		this.tungayTao = "";
		this.denngayTao = "";
		this.trangthai = "";
		this.masanpham = "";
		this.msg = "";	
		this.isnhanHang = "0";
		currentPages = 1;
		num = 1;
		
		this.db = new dbutils();
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
		ResultSet rs = db.get("select count(*) as c from ERP_YEUCAUNGUYENLIEU");
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
		{
			query = "select a.PK_SEQ, a.trangthai, a.ngaychuyen, a.lydo, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
					"from ERP_CHUYENKHO a   " +
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ  ";
			
			if(this.isnhanHang.equals("1"))
				query += " and a.trangthai in (1, 2) ";
		}
					
		
		this.lsxRs = createSplittingData(50, 10, "ngaychuyen desc, trangthai asc ", query);
	}
	
	public void DBclose() 
	{
		try{
			if(this.lsxRs!=null) this.lsxRs.close();
		}catch(Exception e){
			
		}finally{
			if(this.db!=null) this.db.shutDown();
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

	public String getIsnhanHang() 
	{
		return this.isnhanHang;
	}

	public void setIsnhanHang(String isnhanHang) 
	{
		this.isnhanHang = isnhanHang;
	}
}
