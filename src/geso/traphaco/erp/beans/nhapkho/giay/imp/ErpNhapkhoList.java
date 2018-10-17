package geso.traphaco.erp.beans.nhapkho.giay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapkhoList;

public class ErpNhapkhoList extends Phan_Trang implements IErpNhapkhoList 
{
	private static final long serialVersionUID = 1L;
	String congtyId, xuongId;
	String userId;
	String tungay;
	String denngay;
	
	String ndnId;
	ResultSet ndnRs;
	
	String trangthai;
	String soNhaphang;
	String msg;
	String soLSX="";
	
	ResultSet nhapkhoRs;
	ResultSet rsDvkd, xuongrs;
	String DvkdId;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	Utility util;
	public ErpNhapkhoList()
	{	
		this.soLSX="";
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.ndnId = "";
		this.trangthai = "";
		this.soNhaphang = "";
		this.msg = "";
		this.util=new Utility();
		currentPages = 1;
		num = 1;
		this.DvkdId="";
		this.db = new dbutils();
	}
	
	public String getSoLSX() {
		return soLSX;
	}
	public void setSoLSX(String soLSX) {
		this.soLSX = soLSX;
	}
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
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
			query =
			" select a.PK_SEQ as nhId, a.PREFIX + cast(a.PK_SEQ as varchar(20)) as sonhapkho, (k.PREFIX + cast(a.SOLENHSANXUAT as varchar(20)))  as SOCHUNGTU,cd.PK_SEQ as CongDoanId, cast( cd.PK_SEQ as varchar(20)) +'--'+ cd.DienGiai as CongDoan ,"+  
			"	a.NGAYNHAPKHO, a.NOIDUNGNHAP, b.TEN as ndnTen, a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua   "+
			"	from ERP_NHAPKHO a left join ERP_NOIDUNGNHAP b on a.NOIDUNGNHAP = b.PK_SEQ "+  
			"		inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ "+   
			"		inner join erp_lenhsanxuat_giay k on a.solenhsanxuat = k.pk_seq " +
			" inner join Erp_CongDoanSanXuat_giay cd on cd.pk_Seq=a.CongDoan_FK " +
			" where a.CongTy_FK='"+this.congtyId+"' " ;
			 
					
		}
		else
			query = search;
		
		System.out.println("Query init: " + query);
		
		this.xuongrs = db.get("SELECT PK_SEQ, tennhamay FROM ERP_NHAMAY WHERE TRANGTHAI = 1");
		this.nhapkhoRs = createSplittingData(50, 10, "NGAYNHAPKHO desc, trangthai asc, nhId desc ", query);
		this.ndnRs = db.get("select pk_seq, ten from ERP_NOIDUNGNHAP");
		this.rsDvkd=db.get("SELECT PK_SEQ,DONVIKINHDOANH AS TEN FROM DONVIKINHDOANH");
	}

	
	public void DBclose() 
	{
		try 
		{
			if(this.rsDvkd != null)
				this.rsDvkd.close();
			
			if(this.ndnRs != null)
				this.ndnRs.close();
			
			if(this.nhapkhoRs != null)
				this.nhapkhoRs.close();
		} 
		catch (SQLException e) {}
		
		this.db.shutDown();
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getSoPnh() 
	{
		return this.soNhaphang;
	}

	public void setSoPnh(String soPnh) 
	{
		this.soNhaphang = soPnh;
	}

	public ResultSet getNhapKhoList()
	{
		return this.nhapkhoRs;
	}

	public void setNhapKhoList(ResultSet nhapkhoList) 
	{
		this.nhapkhoRs = nhapkhoList;
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
		ResultSet rs = db.get("select count(*) as c from ERP_NHAPKHO");
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

	
	public ResultSet getRsDvkd() {
		
		return rsDvkd;
	}

	
	public void setRsDvkd(ResultSet RsDvkd) {
		
		rsDvkd=RsDvkd;
	}

	
	public String getIdDvkd() {
		
		return this.DvkdId;
	}

	
	public void setIdDvkd(String IdDvkd) {
		
		this.DvkdId=IdDvkd;
	}
	
	public String getXuongId() {
		
		return this.xuongId;
	}


	
	public void setXuongId(String xuongId) {
		this.xuongId = xuongId;
		
	}


	
	public ResultSet getXuongRs() {
		
		return this.xuongrs;
	}


	
	public void setXuongRs(ResultSet xuongRs) {
		
		this.xuongrs = xuongRs;
	}
}
