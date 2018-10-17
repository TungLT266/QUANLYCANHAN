package geso.traphaco.center.beans.dieuchinhtonkho.imp;

import java.sql.ResultSet;

import geso.traphaco.center.beans.dieuchinhtonkho.IErpChuyenkhoTTList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;

public class ErpChuyenkhoTTList extends Phan_Trang implements IErpChuyenkhoTTList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String ctid;
	String sophieu;
	String lydo;
	String msg;
	String nppId;
	String nppTen;
	
	ResultSet nhapkhoRs;
	ResultSet chungtuRs;
	ResultSet nppRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();

	public ErpChuyenkhoTTList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.sophieu="";
		this.lydo = "";
		this.msg = "";
		this.ctid="";
		currentPages = 1;
		num = 1;
		this.loaidonhang="";
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
			query = "select isnull(a.LoaiDonHang,0) as LoaiDonHang,a.PK_SEQ, a.trangthai, a.ngaychuyen, a.ghichu as lydo,TT.TEN as DIABAN ,NV.TEN as nguoitao, b.ten as khoxuat, c.ten as nppTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
					"from ERP_CHUYENKHO a inner join ERP_KHOTT b on a.khoxuat_fk = b.pk_seq inner join NHAPHANPHOI c on a.npp_fk = c.pk_seq  " +
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					" inner join TINHTHANh TT on c.TINHTHANH_FK = tt.PK_SEQ "+
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ  where a.pk_seq > 0 and a.LoaiDonHang='"+loaidonhang+"' and c.tructhuoc_fk='1' ";
		} 
		
		if(!loaidonhang.equals("4"))
		{
			query+="  and c.loaiNPP not in ( 4, 5 ) and c.pk_seq in "+ util.quyen_npp(this.userId);
		}
		System.out.println("___CHUYEN KHO: " + query);
		this.nhapkhoRs = createSplittingData(50, 10, "ngaychuyen desc, trangthai asc ", query);
		this.chungtuRs = db.get("select a.PK_SEQ from ERP_CHUYENKHO a ");
		this.nppRs = db.get("select PK_SEQ, TEN from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk='1' and loainpp in (0,1,2,3) and pk_seq in "+ util.quyen_npp(this.userId)+" order by TEN");
		
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getSophieu()
	{
		return sophieu;
	}

	public void setSophieu(String sophieu) 
	{
		this.sophieu = sophieu;
	}

	public String getLydo() 
	{
		return lydo;
	}

	public void setLydo(String lydo) 
	{
		this.lydo = lydo;
	}

	public String getTungayTao() 
	{
		return this.tungay;
	}

	public void setTungayTao(String tungay) 
	{
		this.tungay =tungay;	
	}

	public String getDenngayTao() 
	{
		return this.denngay;
	}

	public void setDenngayTao(String denngay) 
	{
		this.denngay = denngay;
	}

	public ResultSet getNhapkhoRs() 
	{
		return this.nhapkhoRs;
	}

	public void setNhapkhoRs(ResultSet nkRs) 
	{
		this.nhapkhoRs = nkRs;
	}



	public void setChungtuRs(ResultSet chtuRs) {
		this.chungtuRs = chtuRs;
		
	}

	
	public ResultSet getChungtuRs() {
		
		return this.chungtuRs;
	}



	public void setctId(String ctId) {
		this.ctid = ctId;
		
	}

	public String getctId() {
		
		return this.ctid;
	}

	public ResultSet getNppRs() {
		return this.nppRs;
	}

	public void setNppRs(ResultSet npprs) {
		this.nppRs=npprs;
	}

	public String getNppTen() {
		return this.nppTen;
	}

	public void setNppTen(String nppTen) {
		this.nppTen=nppTen;
	}

	public String getNppId() {
		return this.nppId;
	}

	public void setNppId(String nppid) {
		this.nppId=nppid;
	}
	
	String loaidonhang;

	public String getLoaidonhang() {
		return loaidonhang;
	}

	public void setLoaidonhang(String loaidonhang) {
		this.loaidonhang = loaidonhang;
	}

}
