package geso.traphaco.distributor.beans.dondathang.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.distributor.beans.dondathang.IErpDuyetddhNppList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class ErpDuyetddhNppList extends Phan_Trang implements IErpDuyetddhNppList
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
	String doituong = "";
	String kbhId;
	ResultSet kbhRs;
	ResultSet rsnvbanhang;
	ResultSet rskhoid;

	String nvbanhang;
	String ngaygiao;
	String khohh;
	String nguoigiao,KvId = "",SpId ="";

	String tensp;
	String solo;
	String chietkhau;
	String thuegtgt;
	String ghichu;
	String nguoitao;
	String nguoisua;
	ResultSet KvRs;
	ResultSet SpRs;

	private int num;
	private int[] listPages;
	private int currentPages;
	
	String phanloai;
	String capduyet,HtbhId = "";
	
	dbutils db;
	ResultSet rstien;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	ResultSet HtbhRs;
	
	// 22-12-2015
	String ngayno;
	
	public ErpDuyetddhNppList()
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
		
		this.kbhId = "";
		this.phanloai = "";
		this.capduyet = "";
		this.ngaygiao="";
		this.nvbanhang="";
		this.khohh="";
		this.nguoigiao="";
		this.tensp="";
		this.solo="";
		this.thuegtgt = "";
		this.chietkhau = "";
		this.ghichu = "";
		this.nguoitao = "";
		this.nguoisua = "";
		
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		 
		this.db = new dbutils();
		
		this.ngayno = "";
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
		String query = "";
        
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.machungtu, a.trangthai, a.ngaydonhang, case a.loaidonhang when 0 then d.ten when 3 then e.ten else c.ten end as nppTEN, a.tooltip, a.tooltip_scheme, " + 
					" 	a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk ,isnull(a.iskm,0) as isKm, a.CS_DUYET, a.SS_DUYET, a.ASM_DUYET, isnull(a.CAPDOGIAOHANG, 0) as CAPDOGIAOHANG, isnull( lydokhongduyet, '' ) lydokhongduyet " +
					"from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
					"	left join KHACHHANG c on a.khachhang_fk = c.pk_seq  " +
					"	left join NHAPHANPHOI d on a.NPP_DAT_FK = d.pk_seq  " +
					" 	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
					" where a.npp_fk = '" + this.nppId + "' and a.trangthai != 3 ";
				/*" union all	select a.PK_SEQ, a.trangthai, a.ngaydonhang,  "+
					"	 isnull(c.ten,'') as nppTEN, a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO,  "+ 
					"	NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk,a.iskm    "+
					"	from ERP_Dondathang a inner join KHO b on a.kho_fk = b.pk_seq 	  "+
					"		left join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  	  "+
					"		inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ     "+
					"		inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ  "+
					"	where a.tructhuoc_fk = '" + this.nppId + "'  and a.trangthai in ( 0, 4 ) and a.NPP_DACHOT=1 and a.LoaiDonHang=4  and a.kho_fk in "+util.quyen_kho(this.userId);*/
		} 		
	
		/*if(this.phanloai.equals("0"))
			query += " AND a.NPP_DAT_FK is not null ";
		else
		{	
			query += " AND a.loaidonhang = '" + this.phanloai + "' ";
			query += " AND a.khachhang_fk is not null ";
		
			if(this.kbhId.trim().length() > 0)
				query += " AND a.khachhang_fk  in ( select pk_seq from KHACHHANG where kbh_fk in ( " + this.kbhId + " ) )  ";
		}*/
		
		if( this.capduyet.equals("SS") ) //SS chỉ duyệt đơn OTC
		{
			query += " AND a.loaidonhang = '2' ";
			query += " AND a.khachhang_fk is not null ";
			
			if( search.trim().length() <= 0 )
				query += " AND ( a.ss_duyet = '0' AND a.cs_duyet = '0' ) ";
			else
				query += " AND a.cs_duyet = '0' ";
		}
		else if( this.capduyet.equals("ASM") )  //AMS chỉ duyệt đơn ETC
		{
			query += " AND a.loaidonhang = '1' ";
			query += " AND a.khachhang_fk is not null ";
		}
		else if( this.capduyet.equals("CS") )  //PHÂN QUYỀN DUYỆT CỦA CS THEO ĐỊA BÀN
		{
			query += " AND a.cs_duyet = '0' ";
			//query += " AND ( c.pk_seq is null or c.diaban in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + this.userId + "' ) ) ";
		}

		if( this.tdv_dangnhap_id.trim().length() > 0 )
			query += " AND a.ddkd_fk = '" + this.tdv_dangnhap_id + "' ";
		
//		if(this.kbhId.trim().length() > 0)
//			query += " AND a.kbh_fk  = '" + this.kbhId + "'  ";
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		Utility util = new Utility();
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "c", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		query += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or ( a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ) ) ";
		
		//PHAN QUYEN THEO KENH
		query += " and a.kbh_fk in " + util.quyen_kenh( this.userId ) + " ";
				
		System.out.println("___DUYET DON HANG: " + query);
		this.DondathangRs = createSplittingDataNew(this.db,50, 10, "pk_seq desc, ngaydonhang desc, trangthai asc ", query);
		
		if(this.phanloai.equals("0"))
		{
			query = "	select PK_SEQ,  isnull(maFAST,'') + '-' + TEN as TEN  " +
					"	from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk = '" + this.nppId + "' ";
		}
		else if(this.phanloai.equals("1"))
		{
			query = "select PK_SEQ,  isnull(maFAST,'') + '-' + TEN as TEN  " +
					"	from KHACHHANG where TRANGTHAI = '1' and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100000' ) and npp_fk = '" + this.nppId + "' ";
		}
		else
		{
			query = "select PK_SEQ, isnull(maFAST,'') + '-' + TEN as TEN  " +
					"	from KHACHHANG where TRANGTHAI = '1' and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' ) and npp_fk = '" + this.nppId + "' ";
		}
		
		this.khRs = db.get(query);
		
		System.out.println("::: KENH BAN HANG: select pk_seq, diengiai as ten from KENHBANHANG where trangthai = '1'");
		String sql = "select pk_seq,diengiai from HETHONGBANHANG where trangthai = 1 ";
		sql += " and pk_seq in ( select htbh_fk from hethongbanhang_kenhbanhang where kbh_fk in " + util.quyen_kenh( this.userId ) + " ) ";
		this.HtbhRs = db.get(sql);
		
		if(this.HtbhId.length() > 0)
		{
			sql = "select pk_seq,ten from kenhbanhang a inner join hethongbanhang_kenhbanhang b on a.PK_SEQ = b.kbh_fk where a.TRANGTHAI = 1 and b.htbh_fk = '"+this.HtbhId+"' ";
			sql += " and pk_seq in " + util.quyen_kenh( this.userId );
			
			this.kbhRs = db.get(sql);
		}
		else		
		{
			sql = "select pk_seq,ten from kenhbanhang where trangthai = 1";
			sql += " and pk_seq in " + util.quyen_kenh( this.userId );
			this.kbhRs = db.get( sql );
		}
		
		this.KvRs = db.get("select pk_seq, ten from khuvuc where trangthai = 1");
		this.SpRs = db.get("select pk_seq, ten from sanpham where trangthai = 1");
	}
	
	public void initLENHXUATHANG(String search)
	{	
		Utility util = new Utility();
		this.getNppInfo();
		String query = "";
        
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.machungtu, a.trangthai, a.ngaydonhang, case a.loaidonhang when 0 then d.ten when 3 then e.ten else c.ten end as nppTEN, a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk ,isnull(a.iskm,0) as isKm, a.CS_DUYET, a.tooltip, a.tooltip_scheme, a.GHICHU, isnull(a.CAPDOGIAOHANG, 0) as CAPDOGIAOHANG " +
					"from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
					"	left join KHACHHANG c on a.khachhang_fk = c.pk_seq  " +				
					"	left join NHAPHANPHOI d on a.NPP_DAT_FK = d.pk_seq  " +
					" 	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
					"where a.npp_fk = '" + this.nppId + "' and a.cs_duyet = '1'  ";
		} 		

		/*if(this.phanloai.equals("0"))
			query += " AND a.NPP_DAT_FK is not null ";
		else
		{	
			query += " AND a.loaidonhang = '" + this.phanloai + "' ";
			query += " AND a.khachhang_fk is not null AND CS_DUYET = '1' ";
		
			if(this.kbhId.trim().length() > 0)
				query += " AND a.khachhang_fk  in ( select pk_seq from KHACHHANG where kbh_fk in ( " + this.kbhId + " ) )  ";
		}*/
		
		if(this.loaidonhang.trim().length() > 0 )
			query += " AND a.loaidonhang = '" + this.loaidonhang + "' ";
		
		//Mặc định lúc đầu trạng thái từ đã duyệt CS đến đã ra PGH chỉ hiện đơn hàng của ngày hiện tại 
		if( search.trim().length() <= 0 )
		{
			this.tungay = this.getDateTime();
			query += " and ( a.trangthai <= 2 or ( a.trangthai > 2 and a.ngaydonhang = '" + this.getDateTime() + "' ) ) ";
		}
				
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "khachhang_fk", this.getLoainhanvien(), this.getDoituongId() );
		query += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or ( a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ) ) ";
		
		//PHAN QUYEN THEO KENH
		query += " and a.kbh_fk in " + util.quyen_kenh( this.userId ) + " ";
		
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingDataNew(this.db,50, 10, "pk_seq desc, ngaydonhang desc, trangthai asc ", query);
		
		this.rsnvbanhang=db.get("select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId);
		this.rskhoid=db.get("select pk_seq, ten from kho where 1 = 1 and pk_seq in " + util.quyen_kho( this.userId ) );
		
	    query = "select PK_SEQ,  isnull(maFAST,'') + '-' + TEN as TEN  from KHACHHANG where TRANGTHAI = '1' and  npp_fk = '" + this.nppId + "' ";
		
		System.out.println("câu truy vấn KHHHHH: "+ query);	
		this.khRs = db.get(query);
		
		String sql = "select pk_seq,diengiai from HETHONGBANHANG where trangthai = 1 ";
		sql += " and pk_seq in ( select htbh_fk from hethongbanhang_kenhbanhang where kbh_fk in " + util.quyen_kenh( this.userId ) + " ) ";
		this.HtbhRs = db.get(sql);
		
		if(this.HtbhId.length() > 0)
		{
			sql = "select pk_seq,ten from kenhbanhang a inner join hethongbanhang_kenhbanhang b on a.PK_SEQ = b.kbh_fk where a.TRANGTHAI = 1 and b.htbh_fk = '"+this.HtbhId+"' ";
			sql += " and pk_seq in " + util.quyen_kenh( this.userId );
			
			this.kbhRs = db.get(sql);
		}
		else		
		{
			sql = "select pk_seq,ten from kenhbanhang where trangthai = 1";
			sql += " and pk_seq in " + util.quyen_kenh( this.userId );
			this.kbhRs = db.get( sql );
		}
		
		this.KvRs = db.get("select pk_seq, ten from khuvuc where trangthai = 1");
		this.SpRs = db.get("select pk_seq, ten from sanpham where trangthai = 1 and pk_seq in " + util.quyen_sanpham( this.userId ) );
	}
	
	public void initTHONGKENHANHANG(String search)
	{
		this.getNppInfo();
		String query = "";
        
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.trangthai, a.ngaydonhang, case a.loaidonhang when 0 then d.ten when 3 then e.ten else c.ten end as nppTEN, a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk ,isnull(a.iskm,0) as isKm, CS_DUYET, a.tooltip, a.tooltip_scheme, " +
					" a.trangthaiSMS, a.ngaynhanSMS, a.trangthaiFAX, a.ngaynhanFAX	" +
					"from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
					"	left join KHACHHANG c on a.khachhang_fk = c.pk_seq  " +
					"	left join NHAPHANPHOI d on a.NPP_DAT_FK = d.pk_seq  " +
					" 	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
					"where a.npp_fk = '" + this.nppId + "' and a.trangthai in ( 1, 4 ) ";
		} 		

		if(this.loaidonhang.trim().length() > 0 )
			query += " AND a.loaidonhang = '" + this.loaidonhang + "' ";
		
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingDataNew(this.db,50, 10, "ngaydonhang desc, trangthai asc ", query);
		
		this.rsnvbanhang=db.get("select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId);
		this.rskhoid=db.get("select pk_seq,ten from kho ");
	}
	
	public void DBclose() 
	{
		try
		{
			if(DondathangRs!=null)DondathangRs.close();
			if(HtbhRs!=null)HtbhRs.close();
			if(kbhRs!=null)kbhRs.close();
			if(khRs!=null)kbhRs.close();
			if(KvRs!=null)KvRs.close();
				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
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


	public String getPhanloai() {

		return this.phanloai;
	}


	public void setPhanloai(String phanloai) {

		this.phanloai = phanloai;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}


	public String getCapduyet() {

		return this.capduyet;
	}


	public void setCapduyet(String capduyet) {

		this.capduyet = capduyet;
	}


	public String getDoiTuong() {
		
		return doituong;
	}


	public void setDoiTuong(String DoiTuong) {
		
		this.doituong = DoiTuong;
	}
	public ResultSet getRsnvbanhang() {
		return rsnvbanhang;
	}

	public void setRsnvbanhang(ResultSet rsnvbanhang) {
		this.rsnvbanhang = rsnvbanhang;
	}
	public String getNvbanhang() {
		return nvbanhang;
	}

	public void setNvbanhang(String nvbanhang) {
		this.nvbanhang = nvbanhang;
	}
	public String getNgaygiao() {
		return ngaygiao;
	}

	public void setNgaygiao(String ngaygiao) {
		this.ngaygiao = ngaygiao;
	}
	public ResultSet getRskhoid() {
		return rskhoid;
	}

	public void setRskhoid(ResultSet rskhoid) {
		this.rskhoid = rskhoid;
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

	public String getTensp() {
		return tensp;
	}

	public void setTensp(String tensp) {
		this.tensp = tensp;
	}

	public String getSolo() {
		return solo;
	}

	public void setSolo(String solo) {
		this.solo = solo;
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
	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}
	

	public String getNguoitao() {
		return nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}
	public ResultSet getRstien() {
		return rstien;
	}

	public void setRstien(ResultSet rstien) {
		this.rstien = rstien;
	}
	public void laytien(String sql)
	{
		this.rstien=db.getScrol(sql);
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
	public ResultSet getKvRs() {
		return KvRs;
	}

	public void setKvRs(ResultSet kvRs) {
		KvRs = kvRs;
	}
	public String getKvId() {
		return KvId;
	}

	public void setKvId(String kvId) {
		KvId = kvId;
	}
	public String getSpId() {
		return SpId;
	}
	public void setSpId(String spId) {
		SpId = spId;
	}
	public ResultSet getSpRs() {
		return SpRs;
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

	
	public String getNgayno() {
		return this.ngayno;
	}

	
	public void setNgayno(String ngayno) {
		this.ngayno = ngayno;
		
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getNguoisua() 
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;
	}
	
}
