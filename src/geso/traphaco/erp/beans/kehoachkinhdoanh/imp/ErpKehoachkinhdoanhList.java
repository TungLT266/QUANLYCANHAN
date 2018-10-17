package geso.traphaco.erp.beans.kehoachkinhdoanh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kehoachkinhdoanh.IErpKehoachkinhdoanhList;

public class ErpKehoachkinhdoanhList extends Phan_Trang implements IErpKehoachkinhdoanhList
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	
	String nam;
	String msg;
	String task;
	ResultSet khkdRs;
	String loai;
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	private Utility util;
	
	public ErpKehoachkinhdoanhList()
	{
		this.userId = "";
		this.nam = "0";
		currentPages = 1;
		this.msg = "";
		num = 1;
		this.loai = "1";
		this.db = new dbutils();
		 util=new Utility();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}

	public String getNam() 
	{
		return this.nam;
	}

	public void setmsg(String msg) 
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getKhkdList() 
	{
		return this.khkdRs;
	}

	public void setKhkdList(ResultSet khkdlist) 
	{
		this.khkdRs = khkdlist;
	}

	public void init(String search)
	{
		String query = "";
		if(search.length() <= 0)
		{
			query = " select a.PK_SEQ as id, a.nam , a.diengiai,\n " +
					" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua \n " +
					" from erp_kehoachkinhdoanh a  \n " +
					" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n " +
 					" where  a.congty_fk = '" + this.congtyId + "' and a.loai = '"+this.loai+"' \n " ;
					 
			System.out.println(" in ne : "+ query);
			
			if(nam.length() > 0)
				query += " and a.nam = '" + nam + "'";
			
	

	
		}
		
		else
			query = search;
		
		System.out.println("Query init 111 232 : " + query);
		
		String query_init = createSplittingData_ListNew(this.db, 50, 10, "id desc", query);
		this.khkdRs = db.get(query_init);
	}

	
	public void DBclose() 
	{
		try 
		{
			if(this.khkdRs != null) 
				this.khkdRs.close();
			 
		}
		catch (SQLException e) {}
		this.db.shutDown();
	}

	public String getTask()
	{
		return this.task;
	}

	public void setTask(String task)
	{
		this.task = task;
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
		ResultSet rs = db.get("select count(*) as c from Erp_kehoachkinhdoanh");
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

	@Override
	public String getLoai() {
		// TODO Auto-generated method stub
		return this.loai;
	}

	@Override
	public void setLoai(String loai) {
		// TODO Auto-generated method stub
		this.loai = loai;
	}

}
