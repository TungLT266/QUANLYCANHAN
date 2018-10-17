package geso.traphaco.distributor.beans.dondathang.imp;

import java.sql.ResultSet;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangDoitacList;

public class ErpDondathangDoitacList extends Phan_Trang implements IErpDondathangDoitacList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String msg;
	
	ResultSet DondathangRs;
	
	String loaidonhang;
	String sodonhang;
	
	String nppId;
	String nppTen;
	String sitecode;
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	String khTen;
	ResultSet khRs;
	String maddh;
	public String getMaddh() {
		return maddh;
	}

	public void setMaddh(String maddh) {
		this.maddh = maddh;
	}
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpDondathangDoitacList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.sodonhang = "";
		this.khTen = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		currentPages = 1;
		num = 1;
		maddh="";
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
		Utility util = new Utility();
		this.getNppInfo();
		
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.trangthai, a.ngaydonhang, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, ISNULL(cast(a.from_dondathang as nvarchar),'')as maddh,b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.NPP_DACHOT," +
					"		nk.diengiai as KenhBanHang,isnull(a.iskm,0) as iskm  " +
					"from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq inner join NHOMKENH nk on a.nhomkenh_fk = nk.pk_seq " +
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0  and a.NPP_FK = '" + this.nppId + "' and a.kho_fk in "+ util.quyen_kho(this.userId);
		} 
		
		query += " and a.loaidonhang = '" + this.loaidonhang + "' ";
		
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingData(50, 10, "ngaydonhang desc, trangthai asc ", query);
		
		query = 
				"	select PK_SEQ,  isnull(maFAST, '') + ', ' + TEN as TEN  " +
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
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppId = util.getIdNhapp(this.userId);
			this.nppTen = util.getTenNhaPP();
			this.sitecode = util.getSitecode();
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
	}


	public String getSodonhang() {

		return this.sodonhang;
	}

	public void setSodonhang(String sodonhang) {
		
		this.sodonhang = sodonhang;
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
	

	String iskm;
	public String getIsKm()
  {
  	return iskm;
  }

	public void setIsKm(String iskm)
  {
  	this.iskm = iskm;
  }
	
public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	} 
}
