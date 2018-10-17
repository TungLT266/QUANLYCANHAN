package geso.traphaco.distributor.beans.duyetdonhangss.imp;

import java.sql.ResultSet;

import geso.traphaco.distributor.beans.dondathangotc.IErpDuyetddhNppListOtc;
import geso.traphaco.distributor.beans.duyetdonhangcs.IErpDuyetdonhangcsList;
import geso.traphaco.distributor.beans.duyetdonhangss.IErpDuyetdonhangssList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class ErpDuyetdonhangssList extends Phan_Trang implements IErpDuyetdonhangssList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String khTen;
	String msg;
	
	ResultSet khRs;
	ResultSet DondathangRs;
	
	String loaidonhang;
	String sodh;
	String nppId;
	String nppTen;
	String sitecode;
	String dhid = "";
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpDuyetdonhangssList()
	{
		this.tungay = "";
		this.denngay = "";
		this.sodh="";
		this.khTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "";
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
		ResultSet rs = db.get("select count(*) as c from ERP_Dondathang where trangthai = '1' ");
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
		Utility util = new Utility();
		String query = "";
        
		if(search.length() > 0)
			query = search;
		else
		{
//			query = 
//					"select a.PK_SEQ, a.trangthai, a.ngaydonhang, isnull(c.ten, d.ten) as nppTEN, a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk ,isnull(a.iskm,0) as isKm " +
//					"from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
//					"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq  " +
//					"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
//					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
//					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + this.nppId + "' and a.trangthai in ( '1') "+
//					" union all	select a.PK_SEQ, a.trangthai, a.ngaydonhang,  "+
//					"	 isnull(c.ten,'') as nppTEN, a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO,  "+ 
//					"	NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk,a.iskm    "+
//					"	from ERP_Dondathang a inner join KHO b on a.kho_fk = b.pk_seq 	  "+
//					"		left join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  	  "+
//					"		inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ     "+
//					"		inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ  "+
//					"	where a.tructhuoc_fk = '" + this.nppId + "'  and a.trangthai = '0' and a.NPP_DACHOT=1 and a.LoaiDonHang=4  and a.kho_fk in "+util.quyen_kho(this.userId);
				query = " select distinct a.TEN as nppten,b.NGAYNHAP as ngaydonhang,b.TRANGTHAI as trangthai, b.PK_SEQ as pk_seq,nv1.TEN as nguoitao, nv2.ten as nguoisua, b.NGAYTAO,b.NGAYSUA "
						+ " from  NHAPHANPHOI a, DONHANG b, nhanvien nv1,nhanvien nv2 "
						+ " where b.NGUOITAO = nv1.PK_SEQ and b.NGUOISUA = nv2.PK_SEQ and a.PK_SEQ = b.NPP_FK and b.npp_fk = "+this.nppId+ " and b.TRANGTHAI = 0 ";
			
		} 		

		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingData(50, 10, "ngaydonhang desc, trangthai asc ", query);
		
		//this.khRs = db.get("select PK_SEQ,isnull(maFAST,'')+ '-'+ TEN as TEN from KHACHHANG where TRANGTHAI = '1' and npp_fk ='"+ this.nppId +"' ");
		
		query = "	select PK_SEQ , TEN as TEN  " +
				"	from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk = '" + this.nppId + "' ";
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
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
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

	
	public String getSodh() {
		
		return this.sodh;
	}

	
	public void setSodh(String sodh) {
		
		this.sodh=sodh;
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

	@Override
	public String getDonHangID() {
		
		return this.dhid;
	}

	@Override
	public void setDonHangID(String dhid) {
		this.dhid = dhid;
		
	}
	

}
