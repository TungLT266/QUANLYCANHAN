package geso.traphaco.distributor.beans.hopdong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hopdong.IDonhang;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETCList;
import geso.traphaco.distributor.beans.hopdong.ISanpham;

public class ErpDonhangNppETCList extends Phan_Trang implements IErpDonhangNppETCList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String mafast;
	String sodh;
	String maHopDong;
	String khTen;
	String msg;
	String soHopDong;
	
	ResultSet khRs;
	ResultSet DondathangRs;
	
	String loaidonhang;
	String nppId;
	String nppTen;
	String sitecode;
	
	String ngaygiao;
	String ngayno;
	String nguoigiao;
	String tensp;
	String chietkhau;
	String thuegtgt;
	String tongtien;
	String khohangid;
	String nvbanhang;
	String ghichu;
	String congty;
	String nguoitao;

	ResultSet rsnvbanhang;
	ResultSet rskhoid;
	ResultSet rsnvgnid;
	ResultSet rscongty;
	
	List<ISanpham> lstSP;
	List<IDonhang> lstDH;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	ResultSet rsTien;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	String hientimkiem;
	String KbhId = "",KvId = "",HtbhId = "";
	
	Object loainhanvien;
	Object doituongId;
	
	ResultSet KbhRs,KvRs,HtbhRs;
	public ErpDonhangNppETCList()
	{
		this.tungay = "";
		this.denngay = "";
		this.khTen = "";
		this.trangthai = "";
		this.maHopDong="";
		this.msg = "";
		this.nppId="";
		this.sodh="";
		this.loaidonhang = "";
		this.mafast="";
		this.soHopDong="";
		currentPages = 1;
		num = 1;
		this.ngaygiao="";
		this.ngayno="";
		this.nguoigiao="";
		this.tensp="";
		this.chietkhau="";
		this.thuegtgt="";
		this.tongtien="";
		this.khohangid="";
		this.nvbanhang="";
		this.ghichu="";
		this.congty="";
		this.nguoitao="";

		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		this.hientimkiem = "0";
		
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
			query = "select distinct a.PK_SEQ, isnull(a.machungtu, '') as machungtu, a.trangthai, isnull(a.hopdong_fk, -1) as hopdong_fk, a.ngaydonhang, a.ngaydenghi, a.tooltip, a.tooltip_scheme, " + 
					"	case a.loaidonhang when 0 then d.ten when 3 then e.ten else c.ten end as nppTEN, " + 
					" 	b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE , isnull(a.CAPDOGIAOHANG, 0) as CAPDOGIAOHANG, " + 
					"	a.CS_DUYET, a.SS_DUYET, isnull( lydokhongduyet, '' ) as lydokhongduyet, " + 
					" 		ISNULL ( ( select max( hd.PK_SEQ ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) as daraHOADON, " +
					" 		ISNULL ( ( select COUNT( PK_SEQ ) from ERP_YCXUATKHONPP where TRANGTHAI != 2 and DDH_FK = a.pk_seq ), 0 ) as daraPGH, " + 
					"		a.daraSXH, a.mohoadon, a.tongthanhtoan as doanhthu " +
					"from ERP_DonDatHangNPP a inner join KHO b on a.kho_fk = b.pk_seq " + 
					"   inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk=a.PK_SEQ "+
					"	left join KHACHHANG c on a.khachhang_fk = c.pk_seq " +
					" 	left join NHAPHANPHOI d on a.npp_dat_fk = d.pk_seq " +
					" 	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
					"where a.pk_seq > 0 and a.npp_fk = '" + this.nppId + "'  and a.kho_fk in "+ util.quyen_kho(this.userId);
			
		} 
		
		if(this.loaidonhang.trim().length() > 0)
			query += " and a.loaidonhang = '" + this.loaidonhang + "' ";
		
		//Mặc định lúc đầu trạng thái từ đã duyệt CS đến đã ra PGH chỉ hiện đơn hàng của ngày hiện tại 
		if( search.trim().length() <= 0 )
		{
			this.tungay = this.getDateTime();
			query += " and ( a.trangthai <= 2 or ( a.trangthai > 2 and a.ngaydonhang = '" + this.getDateTime() + "' ) ) ";
		}
		
		//if( this.tdv_dangnhap_id.trim().length() > 0 )
			//query += " and a.ddkd_fk = '" + this.tdv_dangnhap_id + "' ";
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "c", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		query += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or ( a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ) ) ";
		
		//this.DondathangRs = createSplittingData(50, 10, "ngaydonhang desc, trangthai asc ", query);
		
		//PHAN QUYEN THEO KENH
		query += " and a.kbh_fk in " + util.quyen_kenh( this.userId ) + " ";
		
		System.out.println("___CHUYEN KHO 111: " + query);
		this.DondathangRs = createSplittingDataNew(this.db , 50, 10, "trangthai asc, ngaydonhang desc ", query);
		
		query = "select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId;
		if( this.tdv_dangnhap_id.trim().length() > 0 )
			query += " and b.pk_seq = '" + this.tdv_dangnhap_id + "' ";
		
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "b", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.rsnvbanhang = db.get(query);
		
		this.rskhoid = db.get("select pk_seq,ten from kho where 1 = 1 and pk_seq in " +  util.quyen_kho( this.userId ) );
		this.rscongty = db.get("select pk_seq,ten from nhaphanphoi where loainpp=0 and pk_seq <>1");
		
		/*query = "select PK_SEQ, MAFAST + ', ' + TEN as TEN from KHACHHANG where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ";
		if(this.loaidonhang.equals("1"))
			query += " and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100000' )  ";
		else 
			query += " and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' )  ";
		
		this.khRs = db.get(query);*/
		
		String sql = "select pk_seq,diengiai from HETHONGBANHANG where trangthai = 1 ";
		sql += " and pk_seq in ( select htbh_fk from hethongbanhang_kenhbanhang where kbh_fk in " + util.quyen_kenh( this.userId ) + " ) ";
		this.HtbhRs = db.get(sql);
		
		if(this.HtbhId.length() > 0)
		{
			sql = "select pk_seq,ten from kenhbanhang a inner join hethongbanhang_kenhbanhang b on a.PK_SEQ = b.kbh_fk where a.TRANGTHAI = 1 and b.htbh_fk = '"+this.HtbhId+"' ";
			sql += " and pk_seq in " + util.quyen_kenh( this.userId );
			
			this.KbhRs = db.get(sql);
		}
		else		
		{
			sql = "select pk_seq,ten from kenhbanhang where trangthai = 1";
			sql += " and pk_seq in " + util.quyen_kenh( this.userId );
			this.KbhRs = db.get( sql );
		}
		
		this.KvRs = db.get("select pk_seq, ten from khuvuc where trangthai = 1");
	}
	
	public void initTHEODOIDONHANG(String search)
	{
		this.getNppInfo();
		
		String query = "";
		
		Utility util = new Utility();
        
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select distinct a.PK_SEQ, isnull(a.machungtu, '') as machungtu, a.trangthai, isnull(a.hopdong_fk, -1) as hopdong_fk, a.Created_Date as ngaydonhang, a.ngaydenghi, a.tooltip, a.tooltip_scheme, " + 
					"	isnull( c.maFAST, d.maFAST ) nppMA, isnull( c.ten, d.ten ) nppTEN, a.mohoadon, a.donhangmuon, " + 
					//" ddkd.ten as ddkdTEN, " + 
					" 	b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE , isnull(a.CAPDOGIAOHANG, 0) as CAPDOGIAOHANG, " + 
					"	a.CS_DUYET, a.SS_DUYET, a.thoigianxuly, " + 
					" 		ISNULL ( ( select count( hd.PK_SEQ ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) as daraHOADON, " +
					//" 		ISNULL ( ( select COUNT( PK_SEQ ) from ERP_YCXUATKHONPP where TRANGTHAI != 2 and DDH_FK = a.pk_seq ), 0 ) as daraPGH " +
					" 		ISNULL ( ( select COUNT(sxh_dh.hoadon_fk) from ERP_SOXUATHANGNPP sxh inner join ERP_SOXUATHANGNPP_DDH sxh_dh on sxh.PK_SEQ = sxh_dh.soxuathang_fk " + 
					" 				   where sxh.TRANGTHAI != 0 and sxh_dh.hoadon_fk in ( select hd.PK_SEQ from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq )  ), 0 ) as daraPGH " +
					"from ERP_DonDatHangNPP a inner join KHO b on a.kho_fk = b.pk_seq " + 
					"	left join KHACHHANG c on a.khachhang_fk = c.pk_seq " +
					" 	left join NHAPHANPHOI d on a.npp_dat_fk = d.pk_seq " +
					" 	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
					"	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on a.pk_seq = dh_sp.dondathang_fk "+
					//"	inner join DAIDIENKINHDOANH ddkd on a.ddkd_fk = ddkd.PK_SEQ   " +
					"where a.pk_seq > 0 and a.npp_fk = '" + this.nppId + "'  and a.kho_fk in " + util.quyen_kho(this.userId);
		} 
		
		if( search.trim().length() <= 0 && this.tungay.trim().length() <= 0 )
		{
			this.tungay = this.getDateTime();
			query += " and a.ngaydonhang >= '" + tungay + "' ";
		}
		
		if(this.loaidonhang.trim().length() > 0)
			query += " and a.loaidonhang = '" + this.loaidonhang + "' ";
		
		/*if( this.tdv_dangnhap_id.trim().length() > 0 )
			query += " and a.ddkd_fk = '" + this.tdv_dangnhap_id + "' ";*/
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "khachhang_fk", this.getLoainhanvien(), this.getDoituongId() );
		query += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or ( a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ) ) ";
		
		//PHAN QUYEN THEO KENH
		query += " and a.kbh_fk in " + util.quyen_kenh( this.userId ) + " ";
		
		System.out.println("___CHUYEN KHO 111: " + query);
		this.DondathangRs = createSplittingDataNew(this.db, 50, 10, "pk_seq desc, ngaydonhang desc, trangthai asc ", query);
		
		query = "select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId;
		if( this.tdv_dangnhap_id.trim().length() > 0 )
			query += " and b.pk_seq = '" + this.tdv_dangnhap_id + "' ";
		
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "b", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.rsnvbanhang = db.get(query);
		
		this.rskhoid = db.get("select pk_seq, ten from kho where 1 = 1 and pk_seq in " + util.quyen_kho( this.userId ) );
	}
	
	public void initDCDH(String search)
	{
		this.getNppInfo();
		
		String query = "";
		
		Utility util = new Utility();
        
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select distinct a.PK_SEQ, isnull(a.machungtu, '') as machungtu, a.trangthai, isnull(a.hopdong_fk, -1) as hopdong_fk, a.Created_Date, a.ngaydenghi, a.tooltip, a.tooltip_scheme, " + 
					"	isnull( c.maFAST, d.maFAST ) nppMA, isnull( c.ten, d.ten ) nppTEN, " + 
					//" ddkd.ten as ddkdTEN, " + 
					" 	b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE , isnull(a.CAPDOGIAOHANG, 0) as CAPDOGIAOHANG, " + 
					"	a.CS_DUYET, a.SS_DUYET, a.thoigianxuly, datepart(hh, a.Created_Date ) as giodonhang, a.NgayDonHang " + 
					"from ERP_DonDatHangNPP a inner join KHO b on a.kho_fk = b.pk_seq " + 
					"	left join KHACHHANG c on a.khachhang_fk = c.pk_seq " +
					" 	left join NHAPHANPHOI d on a.npp_dat_fk = d.pk_seq " +
					" 	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
					"	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on a.pk_seq = dh_sp.dondathang_fk "+
					"where a.pk_seq > 0 and a.npp_fk = '" + this.nppId + "'  and a.kho_fk in " + util.quyen_kho(this.userId);
		} 
		
		if(this.loaidonhang.trim().length() > 0)
			query += " and a.loaidonhang = '" + this.loaidonhang + "' ";
		

		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "khachhang_fk", this.getLoainhanvien(), this.getDoituongId() );
		query += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or ( a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ) ) ";
		
		query += " and a.kbh_fk in " + util.quyen_kenh( this.userId ) ;
		
		System.out.println("___CHUYEN KHO 111: " + query);
		this.DondathangRs = createSplittingDataNew(this.db, 50, 10, "pk_seq desc, ngaydonhang desc, trangthai asc ", query);
		
		query = "select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId;
		if( this.tdv_dangnhap_id.trim().length() > 0 )
			query += " and b.pk_seq = '" + this.tdv_dangnhap_id + "' ";
		
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "b", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.rsnvbanhang = db.get(query);
		
		this.rskhoid = db.get("select pk_seq, ten from kho where 1 = 1 and pk_seq in " + util.quyen_kho( this.userId ) );
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

	


	
	public String getSodh() {
		
		return this.sodh;
	}

	
	public void setSodh(String sodh) {
		
		this.sodh=sodh;
	}

	
	public List<ISanpham> getListSP() {
		return this.lstSP;
	}

	
	public void setListSP(List<ISanpham> lstSP) {
		this.lstSP = lstSP;
	}

	
	public List<IDonhang> getListDH() {
		return this.lstDH;
	}

	
	public void setListDH(List<IDonhang> lstDH) {
		this.lstDH = lstDH;
	}

	
	public String getSoHopDong() {
		return this.soHopDong;
	}

	
	public void setSoHopDong(String soHopDong) {
		this.soHopDong = soHopDong;
	}

	
	public String getMaHopDong() {		
		return this.maHopDong;
	}

	
	public void setMaHopDong(String maHopDong) {
		this.maHopDong = maHopDong;		
	}
	public String getNgaygiao() {
		return ngaygiao;
	}

	public void setNgaygiao(String ngaygiao) {
		this.ngaygiao = ngaygiao;
	}

	public String getNgayno() {
		return ngayno;
	}

	public void setNgayno(String ngayno) {
		this.ngayno = ngayno;
	}

	public String getNguoigiao() {
		return nguoigiao;
	}

	public void setNguoigiao(String nguoigiao) {
		this.nguoigiao = nguoigiao;
	}

	public String getTensp() {
		return tensp;
	}

	public void setTensp(String tensp) {
		this.tensp = tensp;
	}

	public String getChietkhau() {
		return chietkhau;
	}

	public void setChietkhau(String chietkhau) {
		this.chietkhau = chietkhau;
	}

	public String getThuegtgt() {
		return thuegtgt;
	}

	public void setThuegtgt(String thuegtgt) {
		this.thuegtgt = thuegtgt;
	}

	public String getTongtien() {
		return tongtien;
	}

	public void setTongtien(String tongtien) {
		this.tongtien = tongtien;
	}

	public String getKhohangid() {
		return khohangid;
	}

	public void setKhohangid(String khohangid) {
		this.khohangid = khohangid;
	}

	public String getNvbanhang() {
		return nvbanhang;
	}

	public void setNvbanhang(String nvbanhang) {
		this.nvbanhang = nvbanhang;
	}
	public ResultSet getRsnvbanhang() {
		return rsnvbanhang;
	}

	public void setRsnvbanhang(ResultSet rsnvbanhang) {
		this.rsnvbanhang = rsnvbanhang;
	}
	public ResultSet getRskhoid() {
		return rskhoid;
	}

	public void setRskhoid(ResultSet rskhoid) {
		this.rskhoid = rskhoid;
	}
	public ResultSet getNvgnid() {
		return rsnvgnid;
	}

	public void setNvgnid(ResultSet nvgnid) {
		this.rsnvgnid = nvgnid;
	}
	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}
	public ResultSet getRscongty() {
		return rscongty;
	}

	public void setRscongty(ResultSet rscongty) {
		this.rscongty = rscongty;
	}
	public String getCongty() {
		return congty;
	}

	public void setCongty(String congty) {
		this.congty = congty;
	}

	public String getNguoitao() {
		return nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}
	public ResultSet getRsTien() {
		return rsTien;
	}

	public void setRsTien(ResultSet rsTien) {
		this.rsTien = rsTien;
	}
	public void laytien(String sql)
	{
		this.rsTien=db.get(sql);
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

	public String getHientimkiem() {
		
		return this.hientimkiem;
	}

	public void setHientiemkiem(String hientimkiem) {
		
		this.hientimkiem = hientimkiem;
	}
	public String getKbhId() {
		return KbhId;
	}

	public void setKbhId(String kbhId) {
		KbhId = kbhId;
	}

	public String getKvId() {
		return KvId;
	}

	public void setKvId(String kvId) {
		KvId = kvId;
	}

	public ResultSet getKbhRs() {
		return KbhRs;
	}

	public ResultSet getKvRs() {
		return KvRs;
	}
	
	public String getHtbhId() {
		return HtbhId;
	}

	public void setHtbhId(String htbhId) {
		HtbhId = htbhId;
	}

	public ResultSet getHtbhRs() {
		return HtbhRs;
	}

	
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

	
	public String ChangeDonHang(String[] iddonhangNEW, String[] loaidonhangNEW, String[] ngaydonhangNEW, String[] giodonhangNEW, String[] ngaydonhangOLD, String[] giodonhangOLD) 
	{
		String msg = "";
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			if( iddonhangNEW != null )
			{
				String query = "";
				for( int i = 0; i < iddonhangNEW.length; i++ )
				{
					if( !ngaydonhangNEW[i].equals(ngaydonhangOLD[i]) || !giodonhangNEW[i].equals(giodonhangOLD[i]) )
					{
						if( loaidonhangNEW[i].equals("0") && ngaydonhangNEW[i].trim().length() > 0 )
						{
							String gio = giodonhangNEW[i];
							if( giodonhangNEW[i].trim().length() <= 1 )
								gio = "0" + giodonhangNEW[i];
							
							//LUU LAI LOG
							query = "Insert LOG_DOINGAY( donhang_fk, loaidonhang, ngaydonhangOLD, giodonhangOLD, ngaydonhangNEW, giodonhangNEW, nguoisua ) " +
									"select pk_seq, '" + loaidonhangNEW[i] + "' as loaidonhang, NgayDonHang, datepart(hh, Created_Date ) as giodonhangOLD, '" + ngaydonhangNEW[i] + "' as ngayNEW, '" + giodonhangNEW[i] + "' as gioNEW, '" + this.userId + "' " + 
									"from ERP_DONDATHANGNPP where pk_seq = '" + iddonhangNEW[i] + "' "	;
							System.out.println("::: CHEN LOG: " + query);
							if( !db.update(query) )
							{
								this.msg += "Lỗi khi cập nhật đơn hàng: " + iddonhangNEW[i] + " \n";
								db.getConnection().rollback();
								return this.msg;
							}
							
							query = " update dh "+
									 " set dh.ngaydonhang = '" + ngaydonhangNEW[i] + "',"+
									 " 	dh.Created_Date = '" + ngaydonhangNEW[i] + "' + ' " + gio + ":' "+
									 " 						+  case when datepart(mm, Created_Date ) < 10 then '0' else '' end + CAST( datepart(mm, Created_Date ) as varchar(10) ) + ':' "+
									 " 						+  case when datepart(ss, Created_Date ) < 10 then '0' else '' end + CAST( datepart(ss, Created_Date ) as varchar(10) ) "+
									 " from ERP_DONDATHANGNPP dh "+
									 " where dh.pk_seq = '" + iddonhangNEW[i] + "'" ;
							System.out.println("::: CAP NHAT: " + query);
							if( !db.update(query) )
							{
								this.msg += "Lỗi khi cập nhật đơn hàng: " + iddonhangNEW[i] + " \n";
								db.getConnection().rollback();
								return this.msg;
							}
							
							//NEU CREATED DATE < NGAYKS KINH DOANH THI CAP NHAT LAI LA CHUYEN SALE
							query =  " update dh "+
									 " 	set dh.chuyenSALES = case when DATEDIFF(mi, INFO.thoidiemkhoaso, dh.Created_Date ) > 0 then 1 else 0 end"+
									 " from ERP_DONDATHANGNPP dh inner join"+
									 " ("+
									 " 	select PK_SEQ, NgayDonHang, MONTH(NgayDonHang) as thang, YEAR( NgayDonHang ) as nam ,"+
									 " 			( select COUNT(*) from ERP_KHOASOKINHDOANH where NPP_FK = dh.NPP_FK and THANGKS = MONTH(NgayDonHang) and NAM = YEAR( NgayDonHang ) ) as dakhoaso,"+
									 " 			( select THOIDIEM from ERP_KHOASOKINHDOANH where NPP_FK = dh.NPP_FK and THANGKS = MONTH(NgayDonHang) and NAM = YEAR( NgayDonHang ) ) as thoidiemkhoaso"+
									 " 	from ERP_DONDATHANGNPP dh where PK_SEQ = '" + iddonhangNEW[i] + "' "+
									 " )"+
									 " INFO on dh.PK_SEQ = INFO.PK_SEQ";
							System.out.println("::: CAP NHAT CHUYEN SALES: " + query);
							if( !db.update(query) )
							{
								this.msg += "Lỗi khi cập nhật đơn hàng: " + iddonhangNEW[i] + " \n";
								db.getConnection().rollback();
								return this.msg;
							}
							
							query = " update b set b.chuyenSALES = c.chuyenSALES "+
									 " from ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK = b.PK_SEQ"+
									 " 		inner join ERP_DONDATHANGNPP c on a.DDH_FK = c.PK_SEQ"+
									 " where a.DDH_FK = '" + iddonhangNEW[i] + "' and b.trangthai not in ( 3, 5 ) ";
							System.out.println("::: CAP NHAT CHUYEN SALES: " + query);
							if( !db.update(query) )
							{
								this.msg += "Lỗi khi cập nhật đơn hàng: " + iddonhangNEW[i] + " \n";
								db.getConnection().rollback();
								return this.msg;
							}
							
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
			} 
			catch (SQLException e1) { }
			
			this.msg = e.getMessage();
		}
		
		return msg;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
