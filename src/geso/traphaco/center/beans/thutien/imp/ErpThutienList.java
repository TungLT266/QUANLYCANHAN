package geso.traphaco.center.beans.thutien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.thutien.IErpThutienList;

public class ErpThutienList  extends Phan_Trang  implements IErpThutienList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	
	String nccId;
	ResultSet nppRs;
	String htttId;
	ResultSet htttRs;   
	String ctyId;
	String trangthai;
	String msg;
	String machungtu;
	
	ResultSet tthdRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	String bangke="";
	String MaPhieu = "";
	dbutils db;
	Utility util;
	
	public ErpThutienList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nccId = "";
		this.htttId = "";
		this.trangthai = "";
		this.msg = "";
		this.ctyId = "";
		currentPages = 1;
		this.bangke= "";
		this.MaPhieu= "";
		this.machungtu="";
		num = 1;
		util=new Utility();
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

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
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

	public String getNccId() 
	{
		return this.nccId;
	}

	public void setNccId(String nccid) 
	{
		this.nccId = nccid;
	}

	public ResultSet getNccList() 
	{
		return this.nppRs;
	}

	public void setNccList(ResultSet ncclist) 
	{
		this.nppRs = ncclist;
	}

	public String getHtttId() 
	{
		return this.htttId;
	}

	public void setHtttId(String htttid) 
	{
		this.htttId = htttid;
	}

	public ResultSet getHtttList()
	{
		return this.htttRs;
	}

	public void setHtttList(ResultSet htttlist)
	{
		this.htttRs = htttlist;	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public void setmsg(String msg)
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getTThoadonList() 
	{
		return this.tthdRs;
	}

	public void setTThoadonList(ResultSet tthdlist) 
	{
		this.tthdRs = tthdlist;	
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
		ResultSet rs = db.get("select count(*) as c from ERP_thutien");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	public void init(String search)
	{
		String query = "";

		query =  "select a.pk_seq as tthdId, a.trangthai, a.ngaychungtu,  a.ngaytao, a.ngaysua,  "+
				     "  d.ten as nguoitao, e.ten as nguoisua,  isnull(a.SOTIENTHU,0) AS THUCTHU, isnull(a.machungtu,'') machungtu  "+
				     " from ERP_THUTIEN a  inner join NHANVIEN d on a.nguoitao = d.pk_seq   "+
				     " inner join NHANVIEN e on a.nguoisua = e.pk_seq  " +
				     " WHERE a.HTTT_FK = 100000 "+
				     " order by a.pk_seq ";

		if(tungay.length() > 0)
			query += " and a.ngaychungtu >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaychungtu <= '" + denngay + "'";
		
		if(this.nccId.length() > 0)
			query += " and a.pk_seq in (select HOADON_FK from ERP_THUTIEN_HOADON where NPP_FK = '" + this.nccId + "')";
		
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(this.machungtu.trim().length() > 0)
			query += " and a.machungtu = '" + this.machungtu.trim() + "'";
		System.out.println("Query init: " + query);
		
		this.tthdRs = createSplittingDataNew(this.db, 50, 10, "ngaychungtu desc, trangthai asc, tthdId desc ", query);    
		this.nppRs = db.get("select pk_seq,isnull(maFAST,'') +'-' +  ten as nppTen from KHACHHANG where trangthai = '1' ");
	
	}

	public void DBclose() 
	{
		
			try {
				if(this.nppRs != null)
					this.nppRs.close();
				
				if(this.htttRs != null)
					this.htttRs.close();
				
				if(this.tthdRs != null)
					this.tthdRs.close();
				this.db.shutDown();		
				
			} catch (SQLException e) {e.printStackTrace();
}
		
		
	}

	public String getBangKe() {
		
		return this.bangke;
	}

	
	public void setBangke(String bangke) {
		
		this.bangke = bangke;
	}

	
	public String getMaPhieu() {
		
		return this.MaPhieu;
	}

	
	public void setMaPhieu(String MaPhieu) {
		
		this.MaPhieu = MaPhieu;
	}

	public String getMachungtu() {
		return machungtu;
	}

	public void setMachungtu(String machungtu) {
		this.machungtu = machungtu;
	}

	
}
