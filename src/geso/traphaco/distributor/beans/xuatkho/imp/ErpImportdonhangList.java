package geso.traphaco.distributor.beans.xuatkho.imp;

import java.sql.ResultSet;
import geso.traphaco.distributor.beans.xuatkho.IErpImportdonhangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;

public class ErpImportdonhangList extends Phan_Trang implements IErpImportdonhangList
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
	ResultSet rskhoid;
	

	String loaidonhang;
	String phanloai;
	String nguoigiao;
	

	private int num;
	private int[] listPages;
	private int currentPages;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	dbutils db;
	
	String nguoitao;


	String khohh;
	ResultSet rsnvbanhang;
	public ResultSet getRsnvbanhang() {
		return rsnvbanhang;
	}

	public void setRsnvbanhang(ResultSet rsnvbanhang) {
		this.rsnvbanhang = rsnvbanhang;
	}
	String nvbanhang;
	public String getNvbanhang() {
		return nvbanhang;
	}

	public void setNvbanhang(String nvbanhang) {
		this.nvbanhang = nvbanhang;
	}

	public ErpImportdonhangList()
	{
		this.tungay = "";
		this.denngay = "";
		this.khTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "";
		this.phanloai = "";
		this.khohh="";
		this.nguoitao="";
		this.nguoigiao="";
		this.nvbanhang="";
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
		
		/*String query = "";
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.machungtu, a.trangthai, a.ngaygiaohang, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.ghichu, " +
					"	 (	Select cast(YCXK1.DDH_FK as varchar(10)) + ', ' AS [text()]  " +
					"		From ERP_GUISMSTDV_DDH YCXK1   " +
					"		Where YCXK1.guisms_fk = a.pk_seq  " +
					"		For XML PATH ('') )  as ddhIds    " +
					"from ERP_GUISMSTDV a  " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
					" where a.npp_fk = '" + this.nppId + "' "; 
		} 
		
		if(this.loaidonhang.trim().length() > 0)
			query += " AND a.xuatcho = '" + this.loaidonhang + "' ";
		
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingData(50, 10, "pk_seq desc, ngaygiaohang desc, trangthai asc ", query);
		
		this.rsnvbanhang = db.get("select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId);*/
	
	}
	
	
	public void initTHONGKENHANHANG(String search)
	{
		this.getNppInfo();
		
		String query = "";
		if(search.length() > 0)
			query = search;
		else
		{
			/*query = "select a.PK_SEQ, a.machungtu, a.ngaygiaohang, a.ngaydukienHANGDEN, a.ngayNVBH_XACNHAN, trangthaiSMS, ngaynhanSMS, trangthaiFAX, ngaynhanFAX, a.chanhxe, a.soluong + a.donvitinh as soluong, " + 
					" NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.ghichu " +
					"from ERP_GUISMSTDV a  " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
					" where a.npp_fk = '" + this.nppId + "' and a.trangthai = '1' "; */
			
			query = "select a.PK_SEQ, a.machungtu, a.ngaygiaohang, ( convert( varchar(10), DATEADD(dd, cast( ISNULL(ngayvanchuyen, 0) as int), ngaygiaohang ), 120 ) ) as ngaydukienHANGDEN, a.ngayNVBH_XACNHAN, trangthaiSMS, ngaynhanSMS, trangthaiFAX, ngaynhanFAX, a.chanhxe, a.soluong + a.donvitinh as soluong,   "+
					 " NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.ghichu, "+
					 "	 DATEDIFF( dd, getdate(), DATEADD(dd, cast( isnull(a.ngayvanchuyen, 0) as int ), a.ngaygiaohang) ) as SOS, "+
					 "	 ( select ten from DAIDIENKINHDOANH where PK_SEQ = ( select ddkd_fk from ERP_DONDATHANGNPP where PK_SEQ = ( select top(1) DDH_FK from ERP_GUISMSTDV_DDH order by stt asc ) ) ) as tdv, "+
					 "	 ( select ten from GIAMSATBANHANG where PK_SEQ = ( select GSBH_FK from ERP_DONDATHANGNPP where PK_SEQ = ( select top(1) DDH_FK from ERP_GUISMSTDV_DDH order by stt asc ) ) ) as ss "+
					 "from ERP_GUISMSTDV a   "+
					 "	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ    "+
					 "	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ   "+
					 " where a.npp_fk = '" + this.nppId + "' and a.trangthai = '1'  ";
		} 
		
		System.out.println("___THONG KE NHAN HANG: " + query);
		this.DondathangRs = createSplittingData(50, 10, "pk_seq desc, ngaygiaohang desc ", query);
		
		this.rsnvbanhang = db.get("select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId);
	
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

	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}
	
	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String khId) {
		
		this.nppId = khId;
	}
	

	public String getNppTen() {

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

	public String getPhanloai() 
	{
		return this.phanloai;
	}

	public void setPhanloai(String phanloai) 
	{
		this.phanloai = phanloai;
	}

	public ResultSet getRskhoid() {
		return rskhoid;
	}

	public void setRskhoid(ResultSet rskhoid) {
		this.rskhoid = rskhoid;
	}

	public String getNguoitao() {
		return nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}

	public String getKhohh() {
		return khohh;
	}

	public void setKhohh(String khohh) {
		this.khohh = khohh;
	}
	public String getNguoigiao() {
		return nguoigiao;
	}

	public void setNguoigiao(String nguoigiao) {
		this.nguoigiao = nguoigiao;
	}

	
	public ResultSet getChungtuRs(String smstdvId)
	{
		String query = "select c.machungtu, a.NGAYTAO as ngaylap, ISNULL(d.maFAST, e.maFAST) as maKH, ISNULL(d.TEN, e.TEN) as tenKH, ISNULL(f.ten, '') as tinhthanh  "+
					 "from ERP_GUISMSTDV a inner join ERP_GUISMSTDV_DDH b on a.PK_SEQ = b.guisms_fk "+
					 "	inner join ERP_DONDATHANGNPP c on b.ddh_fk = c.PK_SEQ "+
					 "	left join KHACHHANG d on c.KHACHHANG_FK = d.PK_SEQ "+
					 "	left join NHAPHANPHOI e on c.NPP_DAT_FK = e.PK_SEQ "+
					 "	left join TINHTHANH f on d.TINHTHANH_FK = f.PK_SEQ "+
					 "where a.PK_SEQ = '" + smstdvId + "' ";
		
		return db.get(query);
	}
}
