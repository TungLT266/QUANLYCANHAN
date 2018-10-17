package geso.traphaco.distributor.beans.xuatkho.imp;

import java.sql.ResultSet;

import geso.traphaco.distributor.beans.xuatkho.IErpXuathoadonKMList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;

public class ErpXuathoadonKMList extends Phan_Trang implements IErpXuathoadonKMList
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
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	public ErpXuathoadonKMList()
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
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
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
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.trangthai, a.ngayhoadon, c.ten as nppTEN, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua " +
					"from ERP_XUATHOADONKM a  " +
					"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
					" where a.npp_fk = '" + this.nppId + "' ";
		} 

		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingDataNew(this.db, 50, 10, "ngayhoadon desc, trangthai asc ", query);
		
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
