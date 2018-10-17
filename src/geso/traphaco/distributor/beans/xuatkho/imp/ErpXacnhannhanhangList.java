package geso.traphaco.distributor.beans.xuatkho.imp;

import java.sql.ResultSet;

import geso.traphaco.distributor.beans.xuatkho.IErpXacnhannhanhangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class ErpXacnhannhanhangList extends Phan_Trang implements IErpXacnhannhanhangList
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
	
	ResultSet nvgnRs;
	String nvgnId;
	
	// 24/12/2015 thêm trường người xác nhận( trình dược viên) và ô ghi chú
	String nguoixacnhan;
	ResultSet nguoixacnhanRs;
	
	String ghichu;
	String machungtu;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	
	public ErpXacnhannhanhangList()
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
		this.machungtu = "";
		currentPages = 1;
		num = 1;
		
		this.nguoixacnhan = "";
		this.ghichu = "";
		
		this.db = new dbutils();
	}
	
	public String getMachungtu() {
		return machungtu;
	}

	public void setMachungtu(String machungtu) {
		this.machungtu = machungtu;
	}

	public String getNguoixacnhan() {
		return nguoixacnhan;
	}

	public void setNguoixacnhan(String nguoixacnhan) {
		this.nguoixacnhan = nguoixacnhan;
	}

	public ResultSet getNguoixacnhanRs() {
		return nguoixacnhanRs;
	}

	public void setNguoixacnhanRs(ResultSet nguoixacnhanRs) {
		this.nguoixacnhanRs = nguoixacnhanRs;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
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
			query = "select a.PK_SEQ, a.machungtu, a.trangthai, a.ngayyeucau, isnull( isnull(c.ten, d.ten), '') as nppTEN, isnull(b.ten, '') as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
					"	 (	Select hd.sohoadon + ', ' AS [text()]  " +
					"		From ERP_XACNHANNHANHANG_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadon_fk = hd.pk_seq  " +
					"		Where YCXK1.xacnhannhanhang_fk = a.pk_seq  " +
					"		For XML PATH ('') )  as ddhIds    " +
					"from ERP_XACNHANNHANHANG a left join KHO b on a.kho_fk = b.pk_seq " +
					"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
					"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " + util.getPhanQuyen_TheoNhanVien("KHACHHANG", "d", "pk_seq", this.getLoainhanvien(), this.getDoituongId() ) +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
					" where a.npp_fk = '" + this.nppId + "' "; 
		} 
		
		if(this.loaidonhang.trim().length() > 0)
			query += " AND a.xuatcho = '" + this.loaidonhang + "' ";
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingDataNew(this.db, 50, 10, "ngayyeucau desc, trangthai asc ", query);
		this.rskhoid=db.get("select pk_seq,ten from kho where trangthai ='1'");
		
		/*String sql = " select PK_SEQ, maFAST + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'   and loaiNPP = '4' and TRUCTHUOC_FK='"+this.nppId+"' ";
		
		sql += " union all select PK_SEQ, maFAST + ' - ' + TEN as TEN from KHACHHANG kh where TRANGTHAI = '1' and NPP_FK='"+this.nppId+"'";
		sql += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "d", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		this.khRs = db.get(sql);*/
		
		String sql = "select b.pk_seq, b.ten from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk=b.PK_SEQ where a.npp_fk="+this.nppId;
		sql += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "b", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		this.rsnvbanhang=db.get(sql);
		
		// lấy thông tin người xác nhận, chính là trình dược viên
		sql = "select PK_SEQ, TEN from DAIDIENKINHDOANH a where TRANGTHAI = '1' AND pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
		sql += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		this.nguoixacnhanRs = db.get(sql);
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
		this.nvgnRs = db.get("select pk_seq, ten from NHANVIENGIAONHAN where trangthai = '1' and npp_fk = '" + this.nppId + "' order by ten asc ");
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
