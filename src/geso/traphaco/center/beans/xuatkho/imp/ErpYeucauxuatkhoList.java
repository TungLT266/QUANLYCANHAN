package geso.traphaco.center.beans.xuatkho.imp;

import java.sql.ResultSet;

import geso.traphaco.center.beans.xuatkho.IErpYeucauxuatkhoList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class ErpYeucauxuatkhoList extends Phan_Trang implements IErpYeucauxuatkhoList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String khTen;
	String msg;
	String sohoadon;
	ResultSet khRs;
	ResultSet sanphamRs;
	ResultSet DondathangRs;
	String sanphamId;
	
	String loaidonhang;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	String nppId;
	String nppTen;
	String sitecode;
	String id;
	dbutils db;
	
	public ErpYeucauxuatkhoList()
	{
		this.tungay = "";
		this.denngay = "";
		this.khTen = "";
		this.sanphamId="";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.sohoadon="";
		currentPages = 1;
		num = 1;
		this.id = "";
		this.db = new dbutils();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		/*if(search.length() > 0)
			query = search;
		else*/
		{
			query = "select * from (select a.npp_fk,a.PK_SEQ, a.trangthai, a.ngayyeucau, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
					"	 (	Select HD.sohoadon + ',' AS [text()]  " +
					"		From ERP_YCXUATKHO_DDH YCXK1 inner join ERP_HOADON HD on YCXK1.hoadon_fk = HD.pk_seq   " +
					"		Where YCXK1.ycxk_fk = a.pk_seq  " +
					"		For XML PATH ('') )  as ddhIds, isnull( a.tooltip, '' ) as tooltip    " +
					"from ERP_YCXUATKHO a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq " +
					"	inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ ) data" + 
					" where 1 = 1 "; 
			
			if(tungay.length() > 0)
				query += " and data.ngayyeucau >= '" + tungay + "'";
			
			if(denngay.length() > 0)
				query += " and data.ngayyeucau <= '" + denngay + "'";
		
			if(trangthai.length() > 0)
			{
				if(trangthai.equals("0"))
					query += " and data.TrangThai = '" + trangthai + "'";
				else
					query += " and data.TrangThai >= '" + trangthai + "'";
			}
			System.out.println("doitac la "+this.khTen);
			if(this.khTen.length() > 0)
			{
				query += " and data.npp_fk = '" + this.khTen + "'";
			}
			
			if(this.sohoadon.length() > 0)
			{
				query += " and data.ddhIds like N'%" + this.sohoadon + "%'";
			}
			if(this.id.length() > 0)
			{
				query += " and data.pk_seq =" + this.id + "";
			}
			
			if(sanphamId.length() > 0){
				query += " and data.pk_Seq in (select ycxk_fk from ERP_YCXUATKHO_SANPHAM_CHITIET where sanpham_fk in ("+sanphamId+") ) ";
			}
		} 
		
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingData(50, 10, "ngayyeucau desc, trangthai asc ", query);
		
		this.khRs = db.get(" select PK_SEQ, maFAST + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'   and loaiNPP = '4'  union all select PK_SEQ, maFAST + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and isKhachhang = 1 ");
		
		query=
				" SELECT sp.PK_SEQ, sp.TEN + ' - ' + sp.MA AS TEN  \n"+ 
				 "    FROM ERP_YCXUATKHO_SANPHAM_CHITIET HDSP \n"+ 
				 "    LEFT JOIN erp_sanpham SP ON hdsp.sanpham_fk=sp.pk_Seq \n"
				 + " group by sp.PK_SEQ, sp.TEN + ' - ' + sp.MA ";
		System.out.println(query);
		sanphamRs = db.get(query);
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
	

	public String getSohoadon() {
		return sohoadon;
	}

	public void setSohoadon(String sohoadon) {
		this.sohoadon = sohoadon;
	}

	public ResultSet getSanphamRs() {
		return sanphamRs;
	}

	public void setSanphamRs(ResultSet sanphamRs) {
		this.sanphamRs = sanphamRs;
	}
	public String getSanphamId() {
		return sanphamId;
	}

	public void setSanphamId(String sanphamId) {
		this.sanphamId = sanphamId;
	}

	
	
}
