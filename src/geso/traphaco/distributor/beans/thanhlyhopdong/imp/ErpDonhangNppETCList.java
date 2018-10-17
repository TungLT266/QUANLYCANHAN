package geso.traphaco.distributor.beans.hopdong.imp;

import java.sql.ResultSet;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETCList;

public class ErpDonhangNppETCList extends Phan_Trang implements IErpDonhangNppETCList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String mafast;
	String sodh;
	String mahopdong;
	String khTen;
	String msg;
	
	ResultSet khRs;
	ResultSet DondathangRs;
	
	String loaidonhang;
	String nppId;
	String nppTen;
	String sitecode;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpDonhangNppETCList()
	{
		this.tungay = "";
		this.denngay = "";
		this.khTen = "";
		this.trangthai = "";
		this.mahopdong="";
		this.msg = "";
		this.nppId="";
		this.sodh="";
		this.loaidonhang = "0";
		this.mafast="";
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
		this.getNppInfo();
		
		String query = "";
		
		Utility util = new Utility();
        
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.trangthai, isnull(a.hopdong_fk, -1) as hopdong_fk, a.ngaydonhang, a.ngaydenghi, isnull(c.ten, N'Chưa chọn khách hàng') as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE , isnull(c.CAPDOGIAOHANG, 0) as CAPDOGIAOHANG " +
					"from ERP_DonDatHangNPP a inner join KHO b on a.kho_fk = b.pk_seq " + 
					"left join KHACHHANG c on a.khachhang_fk = c.pk_seq "+
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
					" where  a.npp_dat_fk is null and  a.pk_seq > 0 and a.npp_fk = '" + this.nppId + "'  and a.kho_fk in "+ util.quyen_kho(this.userId);
		} 
		
		query += " and a.loaidonhang = '" + this.loaidonhang + "' ";
		
		System.out.println("___CHUYEN KHO init: " + query);
		this.DondathangRs = createSplittingData(50, 10, "ngaydonhang desc, trangthai asc ", query);
		
		query = "select PK_SEQ, MAFAST + ', ' + TEN as TEN from KHACHHANG where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ";
		if(this.loaidonhang.equals("1"))
			query += " and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100000' )  ";
		else
			query += " and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' )  ";
		
		this.khRs = db.get(query);
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
		System.out.println("user Id: "+this.userId);
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		System.out.println("NPP Id: "+this.nppId);
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	
	public String getKhTen() {
		
		return this.khTen;
	}

	
	public void setKhTen(String khTen) {
		
		this.khTen = khTen;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public String getMafast() {
		return this.mafast;
	}

	
	public void setMafast(String mafast) {
		this.mafast=mafast;
	}

	
	public String getMahopdong() {
		return this.mahopdong;
	}

	
	public void setMahopdong(String maHopdong) {
		this.mahopdong=maHopdong;
	}

	
	public String getSodh() {
		
		return this.sodh;
	}

	
	public void setSodh(String sodh) {
		
		this.sodh=sodh;
	}

	
}
