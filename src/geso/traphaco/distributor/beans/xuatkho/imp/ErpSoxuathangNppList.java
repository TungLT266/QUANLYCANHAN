package geso.traphaco.distributor.beans.xuatkho.imp;

import java.sql.ResultSet;

import geso.traphaco.distributor.beans.xuatkho.IErpSoxuathangNppList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class ErpSoxuathangNppList extends Phan_Trang implements IErpSoxuathangNppList
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
	
	String khoId;
	String khachhangId;
	ResultSet khoRs;
	ResultSet khachhangRs;
	
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
	
	ResultSet nvgnRs;
	String nvgnId;
	
	ResultSet kbhRs;
	String kbhId;
	
	Object loainhanvien;
	Object doituongId;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	// 24/12/2015 -- nhân viên giao nhận lọc
	ResultSet NVgiaonhanRs;
	String MaChungTu;
	
	public ErpSoxuathangNppList()
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
		this.nvgnId = "";
		this.kbhId = "";
		
		this.khachhangId = "";
		
		currentPages = 1;
		num = 1;
		this.khoId = "";
		
		this.MaChungTu = "";
		this.db = new dbutils();
	}
	
	
	
	public String getMaChungTu() {
		return MaChungTu;
	}
	public void setMaChungTu(String maChungTu) {
		MaChungTu = maChungTu;
	}

	public ResultSet getKhoRs() {
		return khoRs;
	}
	public void setKhoRs(ResultSet khoRs) {
		this.khoRs = khoRs;
	}


	public ResultSet getKhachhangRs() {
		return khachhangRs;
	}


	public void setKhachhangRs(ResultSet khachhangRs) {
		this.khachhangRs = khachhangRs;
	}


	public String getKhachhangId() {
		return khachhangId;
	}
	public void setKhachhangId(String khachhangId) {
		this.khachhangId = khachhangId;
	}
	public String getKhoId() {
		return khoId;
	}
	public void setKhoId(String khId) {
		this.khoId = khId;
	}


	public ResultSet getNVgiaonhanRs() {
		return NVgiaonhanRs;
	}

	public void setNVgiaonhanRs(ResultSet nVgiaonhanRs) {
		NVgiaonhanRs = nVgiaonhanRs;
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
			query = "select distinct a.PK_SEQ, a.machungtu, a.trangthai, a.created_date as ngayyeucau, isnull( isnull(c.ten, d.ten), '') as nppTEN, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
					"	 (	Select hd.sohoadon + ', ' AS [text()]  " +
					"		From ERP_SOXUATHANGNPP_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadon_fk = hd.pk_seq  " +
					"		Where YCXK1.soxuathang_fk = a.pk_seq  " +
					"		For XML PATH ('') )  as ddhIds,    " +
					"	 N'Hàng bán' as khoTEN, isnull( nvgn.ten, '' ) as nvgn    " +
					"from ERP_SOXUATHANGNPP a " + 
					//" inner join KHO b on a.kho_fk = b.pk_seq " +
					"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
					"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
					"	inner join ERP_SOXUATHANGNPP_DDH sxh on a.pk_seq = sxh.soxuathang_fk	" +
					"	inner join ERP_HOADONNPP hd on sxh.hoadon_fk = hd.pk_seq	" +
					"	left join NHANVIENGIAONHAN nvgn on a.nvgn_fk = nvgn.pk_seq	" +
					" where a.npp_fk = '" + this.nppId + "' "; 
		} 
		
		if(this.loaidonhang.trim().length() > 0)
			query += " AND a.xuatcho = '" + this.loaidonhang + "' ";
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "hd", "khachhang_fk", this.getLoainhanvien(), this.getDoituongId() );
		query += " and ( ( hd.khachhang_fk is not null " + strQUYEN + " ) or ( hd.npp_dat_fk is not null and hd.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ) ) ";
		
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingDataNew(this.db, 50, 10, "ngayyeucau desc, trangthai asc ", query);
		
		this.rskhoid=db.get("select pk_seq,ten from kho");
		//this.khRs = db.get(" select PK_SEQ, maFAST + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'   and loaiNPP = '4' and TRUCTHUOC_FK='"+this.nppId+"' union all select PK_SEQ, maFAST + ' - ' + TEN as TEN from KHACHHANG where TRANGTHAI = '1' and NPP_FK='"+this.nppId+"'");
		this.rsnvbanhang=db.get("select b.pk_seq,b.ten from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId);
		
		// nhân viên giao nhận
		this.NVgiaonhanRs = db.get("select PK_SEQ, TEN from NHANVIENGIAONHAN where TRANGTHAI ='1'");
	
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
		
		/*geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
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
		}*/
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

	
	public void setNvgnId(String nvgnId) {
		
		this.nvgnId = nvgnId;
	}

	
	public String getNvgnId() {
		
		return this.nvgnId;
	}

	
	public void setNvgnRs(ResultSet nvgnRs) {
		
		this.nvgnRs = nvgnRs;
	}

	
	public ResultSet getNvgnRs() {
		
		return this.nvgnRs;
	}

	
	public void initLICHGIAOHANG(String search) {
		
		this.getNppInfo();
		
		String query = "select pk_seq, ten from NHANVIENGIAONHAN a where trangthai = '1' and npp_fk = '" + this.nppId + "'  ";
		
		Utility util = new Utility();
		
		int[] quyen = new int[10];
		quyen = util.GetquyenNew("ErpSoxuathangNppSvl", "&loai=1", this.userId );
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		if( quyen[util.HIENTHIALL] == 0 )
			query += util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		else //Nếu tích vào nút “hiển thị tất cả” trong bộ phân quyền thì phần “chọn nhân viên cung ứng” trong “lịch giao hàng” sẽ ứng theo địa bàn (hiển thị chỗ này sẽ show ra những cung ứng có cùng địa bàn với người đăng nhập, VD: 1,3,5)
		{
			query += util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN_ALL", "i", "pk_seq", this.getLoainhanvien(), this.userId );
			//query += " and a.pk_seq in ( select nvgn_fk from NHANVIEN where nvgn_fk is not null " + 
			//								" and pk_seq in ( select nhanvien_fk from nhanvien_diaban where diaban_fk in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + this.userId + "' ) ) ) ";
		}
		
		query += "order by ten asc";
		System.out.println("::: LAY NVGN: " + query);
		this.nvgnRs = db.get(query);
		
		this.kbhRs = db.get("select pk_seq, diengiai as ten from KENHBANHANG where trangthai = '1'  order by diengiai asc ");
		this.khoRs = db.get("select PK_SEQ, TEN from KHO where TRANGTHAI ='1'");
		
		// khách hàng
		query = "select PK_SEQ, MAFAST + ', ' + TEN as TEN " +
				" from KHACHHANG a where TRANGTHAI = '1' AND NPP_FK = '" + this.nppId + "' AND pk_seq in ( select khachhang_fk from KHACHHANG_KENHBANHANG " +
				" where kbh_fk in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) ) ";

		query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		System.out.println("::: LAY KHACH HANG: " + query);
		this.khachhangRs = db.get(query);
		
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
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
