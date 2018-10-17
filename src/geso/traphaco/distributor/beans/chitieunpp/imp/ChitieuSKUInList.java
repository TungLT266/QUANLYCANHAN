package geso.traphaco.distributor.beans.chitieunpp.imp;

import java.sql.ResultSet;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.beans.chitieunpp.IChitieuSKUInList;
import geso.traphaco.distributor.util.Utility;

public class ChitieuSKUInList  extends Phan_Trang implements IChitieuSKUInList 
{
	/**
   * 
   */
  private static final long serialVersionUID = -5128201183534703064L;
	String userId;
	String nppId;
	String nppTen;
	String sitecode;
	
	String thang;
	String nam;
	String trangthai;
	String msg;
	String diengiai;
	
	ResultSet rsChitieu;
	dbutils db;
	private int num;
	private int[] listPages;
	private int currentPages;
	public ChitieuSKUInList()
	{
		this.thang = "";
		this.nam = "";
		this.trangthai = "";
		this.msg = "";
		this.diengiai = "";
		num = 1;
	//	listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		currentPages = 1;
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
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
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
	
	private void getNppInfo()
	{		
		Utility util= new Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		this.sitecode = util.getSitecode();
	}

	public String getThang() 
	{
		return this.thang;
	}
	
	public void setThang(String thang) 
	{
		this.thang = thang;
	}
	
	public String getNam() 
	{
		return this.nam;
	}
	
	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public ResultSet getChitieuSKUInRs() 
	{
		return this.rsChitieu;
	}

	public void setChitieuSKUInRs(ResultSet ctskuIn) 
	{
		this.rsChitieu = ctskuIn;
	}

/*	public void init(String query)
	{
		this.getNppInfo();
		
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{
			sql = "select a.thang,a.pk_seq, a.nam, a.diengiai, a.trangthai, a.nhapp_fk, a.ngaytao, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
					"from CHITIEUSKUIN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
					"where a.nhapp_fk = '" + this.nppId + "'";
		}
		System.out.println(sql);
		this.rsChitieu = db.get(sql);
	}
*/
	
	public void init(String query)
	{
		this.getNppInfo();
		
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{
			sql = "select a.thang,a.pk_seq, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua " +
					"from CHITIEUSKUIN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " ;
					/*"where a.nhapp_fk = '" + this.nppId + "'";*/
		}
		System.out.println(sql);
		this.rsChitieu = createSplittingData(50, 10, " nam desc, thang desc", sql);
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	
	public void initTT(String query)//Duoi nha pha  phoi
	{
		this.getNppInfo();
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{
			sql = "select distinct a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
				  "  from CHITIEUSKUIN a" +
				  " inner join CHITIEUSKUIN_SKU sub on a.PK_SEQ = sub.CHITIEUSKUIN_FK  " +
				  " inner join NHANVIEN b on a.nguoitao = b.pk_seq " +
				  " inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
				  " where a.trangthai='1' and sub.NPP_FK = "+this.nppId ;
		}
		
		System.out.println("Query khoi tao: " + sql);
		this.rsChitieu = createSplittingData(50, 10, " nam desc, thang desc", sql);
	}
	public int getNum()
	{
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
		this.getNppInfo();
		ResultSet rs = db.get("select count(*) as c  from CHITIEUSKUIN a inner join CHITIEUSKUIN_SKU sub on a.PK_SEQ = sub.CHITIEUSKUIN_FK where sub.npp_fk="+this.nppId);
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	public void DbClose() 
	{
		try 
		{
			if(this.db != null)
				this.db.shutDown();
			if(rsChitieu != null)
				rsChitieu.close();
		} 
		catch (Exception e) {}
	}

	
}
