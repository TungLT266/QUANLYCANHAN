package geso.traphaco.distributor.beans.hopdong.imp;

import java.sql.ResultSet;

import geso.traphaco.distributor.beans.hopdong.IErpPhieumuahangNppList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class ErpPhieumuahangNppList extends Phan_Trang implements IErpPhieumuahangNppList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	
	String maphieu;
	String msg;

	ResultSet pmhRs;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	Utility util;
	
	public ErpPhieumuahangNppList()
	{
		this.tungay = "";
		this.denngay = "";
		this.maphieu = "";
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		
		currentPages = 1;
		num = 1;
		this.util = new Utility();
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
		this.getNppInfo();
		
		String query = "";
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.trangthai, a.maphieu, a.tungay, a.denngay, a.soluong, a.giatri, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua  " +
					"from ERP_PHIEUMUAHANGNPP a   " +
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + this.nppId + "'";
		} 
		
		System.out.println("___CHUYEN KHO: " + query);
		this.pmhRs = createSplittingData(50, 10, "pk_seq desc, tungay desc, trangthai asc ", query);
		
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		System.out.println("nha phan phoi id"+this.nppId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	
	public String getMaphieu() {
		
		return this.maphieu;
	}

	
	public void setMaphieu(String maphieu) {
		
		this.maphieu = maphieu;
	}

	
	public ResultSet getPmhRs() {
		
		return this.pmhRs;
	}

	
	public void setPmhRs(ResultSet pmhRs) {
		
		this.pmhRs = pmhRs;
	}
	

	
	
	


	
	
}
