package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpYeucaunguyenlieuList;

public class ErpYeucaunguyenlieuList extends Phan_Trang implements IErpYeucaunguyenlieuList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungayTao;
	String denngayTao;
	String tungayDk;
	String denngayDk;
	
	String masanpham;
	String trangthai;
	String msg;
	String ischuyenNL;
	
	ResultSet lsxRs;
	String sochungtu="";
	String Soyeucau="";
	String Lydo="";
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpYeucaunguyenlieuList()
	{
		this.tungayTao = "";
		this.denngayTao = "";
		this.tungayDk = "";
		this.denngayDk = "";
		this.trangthai = "";
		this.masanpham = "";
		this.msg = "";	
		this.ischuyenNL = "0";
		currentPages = 1;
		this.Lydo="";
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
			query = " select isnull( phieuyeucau_fk,0) as phieuyeucau ,ISNULL(A.DUYET,'0') AS DUYET , a.PK_SEQ, a.trangthai, a.ngayyeucau, a.lydo, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
					" from ERP_YEUCAUNGUYENLIEU a   " +
					" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					" inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
					" where 1=1 and a.trangthai <> 3  ";
			
			if(this.ischuyenNL.equals("1"))
				query += " and a.trangthai != 3 ";
		}
					
		
		this.lsxRs = createSplittingData(50, 10, "ngayyeucau desc, trangthai asc ", query);
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
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

	public String getIschuyenNL() 
	{
		return this.ischuyenNL;
	}

	public void setIschuyenNL(String ischuyenNL) 
	{
		this.ischuyenNL = ischuyenNL;
	}

	@Override
	public String getSochungtu() {
		// TODO Auto-generated method stub
		return this.sochungtu;
	}

	@Override
	public void setSochungtu(String _sochungtu) {
		// TODO Auto-generated method stub
		this.sochungtu=_sochungtu;
	}

	@Override
	public String getSoyeucau() {
		// TODO Auto-generated method stub
		return this.Soyeucau;
	}

	@Override
	public void setSoyeucau(String Soyeucau) {
		// TODO Auto-generated method stub
		this.Soyeucau=Soyeucau;
	}

	@Override
	public String getLydo() {
		// TODO Auto-generated method stub
		return this.Lydo;
	}
	@Override
	public void setLydo(String Lydo) {
		// TODO Auto-generated method stub
		this.Lydo=Lydo;
	}
}
