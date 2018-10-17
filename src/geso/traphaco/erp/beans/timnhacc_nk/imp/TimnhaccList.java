package geso.traphaco.erp.beans.timnhacc_nk.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.timnhacc_nk.ITimnhaccList; 

public class TimnhaccList extends Phan_Trang implements ITimnhaccList{

	String congtyId;
	String userId;
	String mamh;
	ResultSet dnmhRs;
	String trangthai;
	String msg;
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	
	ResultSet timncclist;
	
	dbutils db;
	Utility util;

	public TimnhaccList()
	{
		this.congtyId = "";
		this.userId = "";
		this.mamh = "";
		this.trangthai = "";
		this.msg = "";
		this.soItems = 25;
		this.util=new Utility();
		this.db = new dbutils();
	}
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as id, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, isnull(a.diengiai, 'na') as diengiai, d.pk_seq as muahang_id "
					+ " from erp_timncc a, nhanvien b, nhanvien c, ERP_MUAHANG d "
					+ " where a.congty_fk = "+ this.congtyId +" and a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.denghimua_fk = d.pk_seq"; 			
			System.out.println("Query la: " + query + "\n");
		}
		else
		{
			query = search;
		}
		
		String query_init = createSplittingData_ListNew(this.db, soItems, 10, "id desc", query);
		System.out.println("timncclist " + query_init);
		this.timncclist = db.get(query_init);
	}
	public void createRs() {
		
		String query = "select pk_seq, sodenghi from erp_denghimuahang where congty_fk = "+ this.congtyId +" and trangthai = 0 and istruongbpduyet = 1";
		System.out.println("timncclist " + query);
		this.dnmhRs = db.get(query);
		
	}
	public ResultSet getDnmhRs() {
		
		return this.dnmhRs;
	}

	public void setDnmhRs(ResultSet dnmhRs) {
		
		this.dnmhRs = dnmhRs;
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
		ResultSet rs = db.get("select count(*) as c from ERP_TIMNCC");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
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
	public String getUserId() {
		
		return this.userId;
	}
	@Override
	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	@Override
	public String getSodenghi() {
		
		return this.mamh;
	}
	@Override
	public void setSodenghi(String mamh) {
		
		this.mamh = mamh;
	}
	@Override
	public ResultSet getTimNccList() {
		
		return this.timncclist;
	}
	@Override
	public void setTimNccList(ResultSet timncclist) {
		
		this.timncclist = timncclist;
	}
	@Override
	public void DBclose() {
		
		this.db.shutDown();
	}

	@Override
	public String getTrangthai() {
		
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		
		this.trangthai = trangthai;
	}

	@Override
	public void setMsg(String Msg) {
		
		this.msg = Msg;
	}

	@Override
	public String getMsg() {
		
		return this.msg;
	}
	@Override
	public void setSoItems(int soItems) {
		
		this.soItems = soItems;
	}
	@Override
	public int getSoItems() {
		
		return this.soItems;
	}
}
