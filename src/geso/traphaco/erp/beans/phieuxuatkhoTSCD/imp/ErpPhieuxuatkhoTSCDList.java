package geso.traphaco.erp.beans.phieuxuatkhoTSCD.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phieuxuatkhoTSCD.ISanpham;

import geso.traphaco.erp.beans.phieuxuatkhoTSCD.IErpPhieuxuatkhoTSCDList;

public class ErpPhieuxuatkhoTSCDList extends Phan_Trang implements IErpPhieuxuatkhoTSCDList
{
	private static final long serialVersionUID = 1L;
	
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	
	String sophieu;
	
	String trangthai;
	String msg;
	
	ResultSet pxkRs;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	Utility util;
	
	public ErpPhieuxuatkhoTSCDList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.msg = "";
		this.sophieu = "";
		
		
		this.dvkdId = "";
		this.khachhang ="";
		this.spId ="";
		currentPages = 1;
		num = 1;
		util=new Utility();
		spList= new ArrayList<ISanpham>();
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

	public String getSoPhieu()
	{
		return this.sophieu;
	}

	public void setSoPhieu(String sophieu) 
	{
		this.sophieu = sophieu;
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

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public ResultSet getPxkList() 
	{
		return this.pxkRs;
	}

	public void setPxkList(ResultSet pxkList) 
	{
		this.pxkRs = pxkList;
	}
	
	public void setmsg(String msg) 
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public void init(String search)
	{
		String query = "";
		if(search.length() <= 0)
		{
			 query = " select a.PK_SEQ as pxkId, a.NGAYXUAT, b.MA + ' - ' + b.TEN as ndxTen, " +
				"  cast(a.PK_SEQ as varchar(20)) as soxuatkho, " +
				" g.PREFIX + f.PREFIX + cast(a.TRAHANGNCC_fk as varchar(20))   as SOCHUNGTU, " +
				" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua , " +
				" ( select  isnull(ma,'')+' ' +Ten from ERP_NHACUNGCAP where pk_seq =f.nhacungcap_fk) as DoiTuong   "+ 
				" from ERP_XUATTRATSCD a inner join ERP_NOIDUNGNHAP b on a.NOIDUNGXUAT = b.PK_SEQ " +
				"inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ " +
				"left join ERP_MUAHANG f on a.TRAHANGNCC_FK = f.pk_seq " +
				" left join ERP_DONVITHUCHIEN g on f.donvithuchien_fk = g.pk_seq and g.pk_seq in  "+this.util.quyen_donvithuchien(this.userId) +
				" where a.congty_fk = '" + this.congtyId + "' ";
		}
		else
			query = search;
		
		System.out.println("Query init xuat tra tscd: " + query);
		
		this.pxkRs = createSplittingDataNew(this.db, 50, 10, "NGAYXUAT desc, pxkId desc, trangthai asc ", query);
		this.dvkdRs = db.get("select PK_SEQ, DIENGIAI from DONVIKINHDOANH");
	}

	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
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
		ResultSet rs = db.get("select count(*) as c from ERP_XUATKHO");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getCongtyId()
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId)
	{
		this.congtyId = congtyId;
	}
	
	String khachhang;

	public String getKhachhang()
	{
		return khachhang;
	}

	public void setKhachhang(String khachhang)
	{
		this.khachhang = khachhang;
	}

	
	String spId;
	
	public String getSanPhamId()
	{
		return this.spId;
	}

	
	public void setSanPhamId(String spId)
	{
		this.spId = spId;
	}

	List<ISanpham> spList;
	
	public List<ISanpham> getSpList()
	{
		return this.spList;
	}

	
	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;	
	}
	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getDvkdList() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdList(ResultSet dvkdList) {
		
		this.dvkdRs = dvkdList;
	}
	
}
