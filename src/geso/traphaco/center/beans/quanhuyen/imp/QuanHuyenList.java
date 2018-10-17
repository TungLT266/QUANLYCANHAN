package geso.traphaco.center.beans.quanhuyen.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.traphaco.center.beans.quanhuyen.IQuanHuyenList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

public class QuanHuyenList extends Phan_Trang implements IQuanHuyenList, Serializable  {
private static final long serialVersionUID = -9217977546733610214L;
	
	String userId;
	String trangthai;
	String Msg;
	String  ttId, qhId;
	ResultSet  ttlist, qhlist;
	
	int currentPages;
	int[] listPages;

	dbutils db;
	
	public QuanHuyenList(String[] param)
	{
		this.trangthai = param[0];
		//this.vmId = param[1];
		db = new dbutils();
	}
	
	public QuanHuyenList()
	{
		this.trangthai = "";
		/*this.pxId = "";*/
		this.ttId = "";
		this.qhId = "";
		
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
		this.qhlist = super.createSplittingData(super.getItems(), super.getSplittings(), "ngaytao desc", query); //db.get(query);
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query =
				 " SELECT QH.pk_Seq, QH.Ten AS TENQH, TT.TEN AS TENTT, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA, QH.NGAYTAO, QH.NGAYSUA, QH.TRANGTHAI " + 
				 " FROM QUANHUYEN QH " + 
				 " INNER JOIN TINHTHANH TT ON TT.PK_SEQ = QH.TinhThanh_FK " + 
				 /*" INNER JOIN QUANHUYEN QH ON QH.PK_SEQ = PX.QuanHuyen_FK " +*/ 
				 " INNER JOIN NHANVIEN NT ON NT.PK_SEQ = QH.NGUOITAO " + 
				 " INNER JOIN NHANVIEN NS ON NS.PK_SEQ = QH.NGUOISUA";

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
	public ResultSet getQuanHuyenRs() {
		
		return this.qhlist;
	}

	@Override
	public void setQuanHuyenRs(ResultSet qhRs) {
		
		this.qhlist = qhRs;
	}

	@Override
	public void createRS() {
		
		if(this.db == null)
			db = new dbutils();
		this.ttlist = db.get("select * from tinhthanh");
		
		/*String sql = "select * from quanhuyen";
		if(!this.ttId.equals(""))
		{
			sql += " where TINHTHANH_FK = '"+ this.ttId +"' ";
		}
		this.qhlist = db.get(sql);*/
	}

	@Override
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
	}

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
		
		ResultSet rs = db.get("select count(*) as qh from quanhuyen");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("qh"));
			rs.close();
			return count;
		}
		catch(Exception e) {}
		
		return 0;
	}

}
