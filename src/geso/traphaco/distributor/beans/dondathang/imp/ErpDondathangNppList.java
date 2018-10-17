package geso.traphaco.distributor.beans.dondathang.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangNppList;

import java.sql.ResultSet;

public class ErpDondathangNppList extends Phan_Trang implements IErpDondathangNppList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String msg;
	
	ResultSet DondathangRs;
	
	String loaidonhang;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	String maddh;

	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpDondathangNppList()
	{
		this.maddh = "";
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "0";
		currentPages = 1;
		num = 1;
		this.iskm="";
		
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
		this.getNppInfo();
		
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.trangthai, a.ngaydonhang, c.ten as nppTEN,  " +
					"	case when len ( isnull(c.tructhuoc_fk, -1) ) < 6 then  b.ten else d.ten end as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.NPP_DACHOT,isnull(a.isKm,0) as isKm   " +
					"from ERP_Dondathang a inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq " +
					"	left join ERP_KHOTT b on a.kho_fk = b.pk_seq  " +
					"	left join KHO d on a.kho_fk = d.pk_seq  " +
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0  and a.NPP_FK = '" + this.nppId + "' ";
		} 
		
		query += " and a.loaidonhang = '" + this.loaidonhang + "'  ";
		
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingDataNew(this.db, 50, 10, "ngaydonhang desc, trangthai asc ", query);

	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public ResultSet getDondathangRs() 
	{
		return this.DondathangRs;
	}

	public void setDondathangRs(ResultSet nkRs) 
	{
		this.DondathangRs = nkRs;
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


	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
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
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	@Override
	public String getMaDDH() {
		// TODO Auto-generated method stub
		return this.maddh;
	}

	@Override
	public void setMaDDH(String maddh) {
		// TODO Auto-generated method stub
		this.maddh = maddh;
	}
	
	String iskm;
	public String getIsKm()
  {
  	return iskm;
  }

	public void setIsKm(String iskm)
  {
  	this.iskm = iskm;
  }

}
