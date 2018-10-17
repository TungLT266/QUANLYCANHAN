package geso.traphaco.erp.beans.nhapkho.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhapkho.IErpNhapkhoList;

public class ErpNhapkhoList extends Phan_Trang implements IErpNhapkhoList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	
	String ndnId;
	ResultSet ndnRs;
	
	String trangthai;
	String soNhaphang;
	String msg;
	
	ResultSet nhapkhoRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	Utility util;
	public ErpNhapkhoList()
	{
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
			query = "select a.PK_SEQ as nhId, a.PREFIX + cast(a.PK_SEQ as varchar(20)) as sonhapkho, " +
				"case " +
					"when a.SOPHIEUNHAPHANG is not null then (g.PREFIX + f.PREFIX + cast(a.SOPHIEUNHAPHANG as varchar(20)) ) " +
					"when a.SODONTRAHANG is not null then (h.PREFIX + cast(a.SODONTRAHANG as varchar(20))) " +
					"else (k.PREFIX + cast(a.SOLENHSANXUAT as varchar(20))) " +
					"end as SOCHUNGTU, " +
					"a.NGAYNHAPKHO, a.NOIDUNGNHAP, b.TEN as ndnTen, a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua  " +
				" from ERP_NHAPKHO a inner join ERP_NOIDUNGNHAP b on a.NOIDUNGNHAP = b.PK_SEQ " +
					"inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  " +
					"left join ERP_NHANHANG f on a.SOPHIEUNHAPHANG = f.pk_seq left join ERP_DONVITHUCHIEN g on f.donvithuchien_fk = g.pk_seq " +
					"left join DONTRAHANG h on a.SODONTRAHANG = h.pk_seq left join erp_lenhsanxuat k on a.solenhsanxuat = k.pk_seq  " +
					" where  g.pk_seq in "+ this.util.quyen_donvithuchien(this.userId); ;
		}
		else
			query = search;
		
		System.out.println("Query init: " + query);
		
		this.nhapkhoRs = createSplittingData(50, 10, "NGAYNHAPKHO desc, trangthai asc, nhId desc ", query);
		this.ndnRs = db.get("select pk_seq, ten from ERP_NOIDUNGNHAP");
	}

	
	public void DBclose() 
	{
		try 
		{
						
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
}
