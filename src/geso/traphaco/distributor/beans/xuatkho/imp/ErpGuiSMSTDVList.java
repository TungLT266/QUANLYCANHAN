package geso.traphaco.distributor.beans.xuatkho.imp;

import java.sql.ResultSet;

import geso.traphaco.distributor.beans.xuatkho.IErpGuiSMSTDVList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class ErpGuiSMSTDVList extends Phan_Trang implements IErpGuiSMSTDVList
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
	
	String npp_duocchon_id;
	String tdv_dangnhap_id;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	dbutils db;
	
	String nguoitao;
    
	
	// thêm các trường lọc 24/12/2015
	// mã số SMS
	String masoSMS ;
	
	// Tên khu vực- tỉnh thành
	String khuvuc;
	ResultSet khuvucRs;
	// Mã chứng từ
	String machungtu;
	// Mã KH
	String khachhang ;
	ResultSet khachhangRs;
	// Ngày giao
	String ngaygiao;
	// Ngày giao dự kién
	String ngaygiaodukien;
	// Số lượng
	String soluong ;
	// Ghi chú
	String ghichu;
	// Chành xe
	String chanhxe ;
	// NV bán hàng-trình dược viên
	String nvbanhangId ;
	ResultSet nvbanhangIdRs;
	// Ngày xác nhân SMS
	String ngayxacnhanSMS ;
	// Ngày xác nhận Fax
	String ngayxacnhanFax ;
	// Xác nhận bởi SMS
	String xacnhanSMS ;
	// Xác nhận bởi Fax
	String xacnhanFax ;

	String khohh;
	ResultSet rsnvbanhang;
	
	String donhangid;
	
	
	public String getDonhangid() {
		return donhangid;
	}

	public void setDonhangid(String donhangid) {
		this.donhangid = donhangid;
	}

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

	public ErpGuiSMSTDVList()
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
		
		masoSMS = "";
		khuvuc = "";
		machungtu ="";
		khachhang  = "";
		ngaygiao = "";
		ngaygiaodukien = "";
		soluong = "";
		chanhxe = "" ;
		nvbanhangId = "";
		ngayxacnhanSMS = "";
		ngayxacnhanFax = "" ;
		xacnhanSMS  ="";
		xacnhanFax = "";
		donhangid = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		this.ghichu = "";
		this.db = new dbutils();
	}
	public String getMasoSMS() {
		return masoSMS;
	}

	public void setMasoSMS(String masoSMS) {
		this.masoSMS = masoSMS;
	}

	public String getKhuvuc() {
		return khuvuc;
	}

	public void setKhuvuc(String khuvuc) {
		this.khuvuc = khuvuc;
	}

	public ResultSet getKhuvucRs() {
		return khuvucRs;
	}

	public void setKhuvucRs(ResultSet khuvucRs) {
		this.khuvucRs = khuvucRs;
	}

	public String getMachungtu() {
		return machungtu;
	}

	public void setMachungtu(String machungtu) {
		this.machungtu = machungtu;
	}

	public String getKhachhang() {
		return khachhang;
	}

	public void setKhachhang(String khachhang) {
		this.khachhang = khachhang;
	}

	public ResultSet getKhachhangRs() {
		return khachhangRs;
	}

	public void setKhachhangRs(ResultSet khachhangRs) {
		this.khachhangRs = khachhangRs;
	}

	public String getNgaygiao() {
		return ngaygiao;
	}

	public void setNgaygiao(String ngaygiao) {
		this.ngaygiao = ngaygiao;
	}

	public String getNgaygiaodukien() {
		return ngaygiaodukien;
	}

	public void setNgaygiaodukien(String ngaygiaodukien) {
		this.ngaygiaodukien = ngaygiaodukien;
	}

	public String getSoluong() {
		return soluong;
	}

	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public String getChanhxe() {
		return chanhxe;
	}

	public void setChanhxe(String chanhxe) {
		this.chanhxe = chanhxe;
	}

	public String getNvbanhangId() {
		return nvbanhangId;
	}

	public void setNvbanhangId(String nvbanhangId) {
		this.nvbanhangId = nvbanhangId;
	}

	public ResultSet getNvbanhangIdRs() {
		return nvbanhangIdRs;
	}

	public void setNvbanhangIdRs(ResultSet nvbanhangIdRs) {
		this.nvbanhangIdRs = nvbanhangIdRs;
	}

	public String getNgayxacnhanSMS() {
		return ngayxacnhanSMS;
	}

	public void setNgayxacnhanSMS(String ngayxacnhanSMS) {
		this.ngayxacnhanSMS = ngayxacnhanSMS;
	}

	public String getNgayxacnhanFax() {
		return ngayxacnhanFax;
	}

	public void setNgayxacnhanFax(String ngayxacnhanFax) {
		this.ngayxacnhanFax = ngayxacnhanFax;
	}

	public String getXacnhanSMS() {
		return xacnhanSMS;
	}

	public void setXacnhanSMS(String xacnhanSMS) {
		this.xacnhanSMS = xacnhanSMS;
	}

	public String getXacnhanFax() {
		return xacnhanFax;
	}

	public void setXacnhanFax(String xacnhanFax) {
		this.xacnhanFax = xacnhanFax;
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
		Utility util = new Utility();
		this.getNppInfo();
		
		String query = "";
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
		this.DondathangRs = createSplittingDataNew(this.db, 50, 10, "pk_seq desc, ngaygiaohang desc, trangthai asc ", query);
		
		this.rsnvbanhang = db.get("select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId);
		
		this.khuvucRs = db.get("select pk_seq, ten from khuvuc where trangthai = 1");
		
		// khách hàng
		query = "select PK_SEQ, MAFAST + ', ' + TEN as TEN " +
		" from KHACHHANG a where TRANGTHAI = '1' AND NPP_FK = '" + this.nppId + "' AND pk_seq in ( select khachhang_fk from KHACHHANG_KENHBANHANG " +
		" where kbh_fk in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) ) ";

		query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		System.out.println("::: LAY KHACH HANG: " + query);
		this.khachhangRs = db.get(query);
		
		// nhân viên bán hàng - trình dược viên
		this.nvbanhangIdRs = db.get("select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a " +
		" inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId);
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
			
			query = "select distinct a.PK_SEQ, a.machungtu, a.ngaygiaohang, ( convert( varchar(10), DATEADD(dd, cast( ISNULL(a.ngayvanchuyen, 0) as int), a.ngaygiaohang ), 120 ) ) as ngaydukienHANGDEN, a.ngayNVBH_XACNHAN, a.trangthaiSMS, a.ngaynhanSMS, a.trangthaiFAX, a.ngaynhanFAX, a.chanhxe, a.soluong + a.donvitinh as soluong,   "+
					 " NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.ghichu, "+
					 "	 DATEDIFF( dd, getdate(), DATEADD(dd, cast( isnull(a.ngayvanchuyen, 0) as int ), a.ngaygiaohang) ) as SOS, "+
					 "	 ISNULL ( ( select ten from DAIDIENKINHDOANH where PK_SEQ = ( select ddkd_fk from ERP_DONDATHANGNPP where PK_SEQ = ( select top(1) DDH_FK from ERP_GUISMSTDV_DDH order by stt asc ) ) ), '' ) as tdv, "+
					 "	 ISNULL ( ( select ten from GIAMSATBANHANG where PK_SEQ = ( select GSBH_FK from ERP_DONDATHANGNPP where PK_SEQ = ( select top(1) DDH_FK from ERP_GUISMSTDV_DDH order by stt asc ) ) ), '' ) as ss "+
					 "from ERP_GUISMSTDV a  inner join ERP_GUISMSTDV_DDH b on a.pk_seq = b.guisms_fk inner join ERP_DONDATHANGNPP c on b.ddh_fk = c.pk_seq "+
					 "	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ    "+
					 "	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ   "+
					 " where a.npp_fk = '" + this.nppId + "' and a.trangthai = '1'  ";
		} 
		
		Utility util = new Utility();
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "c", "khachhang_fk", this.getLoainhanvien(), this.getDoituongId() );
		
		System.out.println("___THONG KE NHAN HANG: " + query);
		this.DondathangRs = createSplittingDataNew(this.db, 50, 10, "pk_seq desc, ngaygiaohang desc ", query);
		
		query = "select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId;
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "b", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		this.rsnvbanhang = db.get(query);
		
        this.khuvucRs = db.get("select pk_seq, ten from khuvuc where trangthai = 1");
		
		// khách hàng
		query = "select PK_SEQ, MAFAST + ', ' + TEN as TEN " +
		" from KHACHHANG a where TRANGTHAI = '1' AND NPP_FK = '" + this.nppId + "'";

		query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		System.out.println("::: LAY KHACH HANG: " + query);
		this.khachhangRs = db.get(query);
		
		// nhân viên bán hàng - trình dược viên
		this.nvbanhangIdRs = db.get("select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a " +
		" inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId);
	
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
	
	Object loainhanvien;
	Object doituongId;
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
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
