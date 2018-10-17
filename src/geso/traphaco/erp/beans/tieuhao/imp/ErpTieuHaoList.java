package geso.traphaco.erp.beans.tieuhao.imp;

import java.sql.ResultSet;
import java.util.List;
 

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
 

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.tieuhao.*;

public class ErpTieuHaoList extends Phan_Trang implements IErpTieuHaoList
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String id;
	String tungay;
	String denngay;
	
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String nhanhangId;
	String sanphamId;
	String soluong;
	String trangthai;
	String Sopo;
	String msg;
	
	ResultSet thRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	Utility util;
	
	public ErpTieuHaoList()
	{
		this.id = "";
		this.congtyId = "";
		this.userId = "";
		
		this.tungay = "";
		this.denngay = "";
		
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		
		this.nhanhangId = "";
		this.sanphamId = "";
		this.soluong = "";
		
		this.trangthai = "";
		
		this.msg = "";
		this.util=new Utility();
		currentPages = 1;
		num = 1;
		this.Sopo="";
		
		
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
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	
	public String getMsg()
	{
		return this.msg;
	}
	
	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}

	@Override
	public String getCongtyId() {		
		return this.congtyId;
	}

	@Override
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	@Override
	public String getNgayTao() {
		return this.ngaytao;
	}

	@Override
	public void setNgayTao(String ngaytao) {
		this.ngaytao = ngaytao;
	}

	@Override
	public String getNgaySua() {
		return this.ngaysua;
	}

	@Override
	public void setNgaySua(String ngaysua) {
		this.ngaysua = ngaysua;
	}

	@Override
	public String getNguoiTao() {
		return nguoitao;
	}

	@Override
	public void Nguoi(String nguoitao) {
		this.nguoitao = nguoitao;
	}

	@Override
	public String getNguoiSua() {
		return this.nguoisua;
	}

	@Override
	public void setNguoiSua(String nguoisua) {
		this.nguoisua = nguoisua;
	}

	@Override
	public String getNhanHangId() {
		return nhanhangId;
	}

	@Override
	public void setNhanHangId(String nhanhangId) {
		this.nhanhangId = nhanhangId;
	}

	@Override
	public String getSanphamId() {
		return sanphamId;
	}

	@Override
	public void setSanphamId(String spId) {
		this.sanphamId = spId;
	}

	@Override
	public String getSoLuong() {
		return this.soluong;
	}

	@Override
	public void setSoLuong(String soluong) {
		this.soluong = soluong;
	}

	@Override
	public String getTrangthai() {
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	
	public void init(String search)
	{
		String query = "";
		if(search.length() <= 0)
		{
			query = 
				" SELECT  ISNULL(ISBOOKTIEUHAO,0 ) AS  ISBOOKTIEUHAO , A.PK_SEQ, A.NHANHANG_FK, A.SANPHAM_FK, B.MA AS SPMA, B.TEN AS SPTEN, ISNULL(A.SOLUONG, 0) AS SOLUONG, C.TEN AS NGUOITAO, D.TEN AS NGUOISUA, A.NGAYTAO, A.NGAYSUA, ISNULL(A.TRANGTHAI, '0') AS TRANGTHAI, A.PREFIX " +
				" FROM ERP_TIEUHAO A " +
				" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ " +
				" INNER JOIN NHANVIEN C ON C.PK_SEQ = A.NGUOITAO " +
				" INNER JOIN NHANVIEN D ON D.PK_SEQ = A.NGUOISUA " +
				" WHERE A.CONGTY_FK = '" + this.congtyId + "'";
			
			if(tungay.length() > 0){
				query+= " and a.NGAYTAO >= '" + tungay + "'";
			}
			if(denngay.length() > 0)
				query += " and a.NGAYTAO <= '" + denngay + "'";
			
			
			if(trangthai.length() > 0)
				query += " and a.trangthai = '" + trangthai + "'";
			
			if(nhanhangId.length() > 0)
				query += " and '120' + CAST(a.NHANHANG_FK as varchar(20)) like N'%" + nhanhangId + "%'  ";
			
			if(id.length() > 0)
				query += " and '210' + CAST(a.PK_SEQ as varchar(20)) like N'%" + id + "%'  ";
			if(this.Sopo!=null && this.Sopo.trim().length()>0){
				query += "  and  a.PK_SEQ  in (select nh.pk_seq from ERP_TIEUHAO nh inner join erp_muahang mh on mh.pk_seq=nh.muahang_fk where mh.isgiacong='1' and mh.sopo   like '%"+this.Sopo+"%') ";
			}
		}
		else
			query = search;
		
		System.out.println("[ErpTieuHaoList.init] query = " + query);
		
		this.thRs = createSplittingData(50, 10, " PK_SEQ DESC  ", query);
	}

	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

	}

	
	public int getCurrentPage() {
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		this.currentPages = current;
	}

	
	public int[] getListPages() {
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		ResultSet rs = db.get(" select count(*) as c from ERP_TIEUHAO WHERE CONGTY_FK = '" + this.congtyId + "' ");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	@Override
	public ResultSet getTieuHaoRs() {
		return this.thRs;
	}

	@Override
	public void setTieuHaoRs(ResultSet rs) {
		this.thRs = rs;
	}

	@Override
	public String getSoPo() {
		// TODO Auto-generated method stub
		return this.Sopo;
	}

	@Override
	public void setSoPo(String SoPo) {
		// TODO Auto-generated method stub
		this.Sopo=SoPo;
	}
}
