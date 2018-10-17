package geso.traphaco.center.beans.tinhthanh.imp;

import geso.traphaco.center.beans.tinhthanh.ITinhThanhList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public class TinhThanhList extends Phan_Trang implements ITinhThanhList, Serializable 
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String userId;
	String trangthai;
	String Msg;
	String  ttId;
	ResultSet  ttlist;
	String tenqg = "";
	String ten = "";
	
	int currentPages;
	int[] listPages;

	dbutils db;
	
	public TinhThanhList(String[] param)
	{
		this.trangthai = param[0];
		//this.vmId = param[1];
		db = new dbutils();
	}
	
	public TinhThanhList()
	{
		this.trangthai = "";
		/*this.pxId = "";*/
		this.ttId = "";
		//this.qhId = "";
		
		this.Msg ="";
		db = new dbutils();
		init("");
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

	public void createKvBeanList(String query)
	{
		this.ttlist = super.createSplittingData(super.getItems(), super.getSplittings(), "ngaytao desc", query); //db.get(query);
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query =
				 " SELECT TT.pk_Seq, TT.Ten AS TENTT, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA, TT.NGAYTAO, TT.NGAYSUA, TT.TRANGTHAI,(select tenqg from quocgia where pk_seq = TT.quocgia_fk) as tenqg " + 
				 " FROM TINHTHANH TT " + 
				 " INNER JOIN NHANVIEN NT ON NT.PK_SEQ = TT.NGUOITAO " + 
				 " INNER JOIN NHANVIEN NS ON NS.PK_SEQ = TT.NGUOISUA";

		}
		else
		{
			query = search;
		}
		
		createKvBeanList(query);  
		createRS();
	}

	public void setMsg(String Msg) {
	     this.Msg = Msg;
		
	}

	
	public String getMsg() {
		return this.Msg;
	}


	@Override
	public ResultSet getTinhThanhRs() {
		
		return this.ttlist;
	}

	@Override
	public void setTinhThanhRs(ResultSet ttRs) {
		
		this.ttlist = ttRs;
	}

	@Override
	public void createRS() {
		
		if(this.db == null)
			db = new dbutils();
		//this.ttlist = db.get("select * from tinhthanh");
		
		/*String sql = "select * from quanhuyen";
		if(!this.ttId.equals(""))
		{
			sql += " where TINHTHANH_FK = '"+ this.ttId +"' ";
		}
		this.qhlist = db.get(sql);*/
	}

	/*@Override
	public ResultSet getTinhthanhRs() {
		
		return this.ttlist;
	}

	@Override
	public void setTinhthanhRs(ResultSet ttRs) {
		
		this.ttlist = ttRs;
	}

	@Override
	public String getTinhthanhId() {
		
		return this.ttId;
	}

	@Override
	public void setTinhthanhId(String ttId) {
		
		this.ttId = ttId;
	}*/

	/*@Override
	public ResultSet getQuanhuyenRs() {
		
		return this.qhlist;
	}

	@Override
	public void setQuanhuyenRs(ResultSet qhRs) {
		
		this.qhlist = qhRs;
	}

	@Override
	public String getQuanhuyenId() {
		
		return this.qhId;
	}

	@Override
	public void setQuanhuyenId(String qhId) {
		
		this.qhId = qhId;
	}
*/
	@Override
	public int getCurrentPage() {
		
		return this.currentPages;
	}

	@Override
	public void setCurrentPage(int current) {
		
		this.currentPages = current;
	}

	@Override
	public int[] getListPages() {
		
		return this.listPages;
	}

	@Override
	public void setListPages(int[] listPages) {
		
		this.listPages = listPages;
	}

	@Override
	public int getLastPage() {
		
		ResultSet rs = db.get("select count(*) as tt from tinhthanh");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("tt"));
			rs.close();
			return count;
		}
		catch(Exception e) {}
		
		return 0;
	}

	@Override
	public String getTenqg() {
		// TODO Auto-generated method stub
		return this.tenqg;
	}

	@Override
	public void setTenqg(String tenqg) {
	this.tenqg = tenqg;
		
	}

	@Override
	public String getTen() {
		// TODO Auto-generated method stub
		return this.ten;
	}

	@Override
	public void setTen(String ten) {
		this.ten = ten;
		
	}
}
