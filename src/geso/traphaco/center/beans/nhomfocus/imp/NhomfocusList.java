package geso.traphaco.center.beans.nhomfocus.imp;

import java.sql.ResultSet;
import geso.traphaco.center.beans.nhomfocus.INhomfocusList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;

public class NhomfocusList extends Phan_Trang  implements INhomfocusList
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String UserID,ID,THANG,NAM,TRANGTHAI,DONVIKINHDOANH,KENHBANHANG,DOITUONG,MSG;
	
	ResultSet rsNhomfocus,rsKenhbanhang,rsDvkd;
	dbutils db;
	private int num;
	private int[] listPages;
	private int currentPages;
	public NhomfocusList()
	{
		this.db=new dbutils();
		this.UserID="";
		this.ID="";
		this.THANG="";
		this.NAM="";
		this.TRANGTHAI="";
		this.DONVIKINHDOANH="";
		this.KENHBANHANG="";
		this.DOITUONG="";
		this.MSG="";
		num = 1;
		//	listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
			currentPages = 1;
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
		ResultSet rs = db.get("select count(*) as c from ERP_CHUYENKHO ");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	public String getUserId() 
	{
		
		return this.UserID;
	}

	public void setUserId(String userId) 
	{
		
		this.UserID=userId;
	}

	public String getId() 
	{
		
		return this.ID;
	}

	public void setId(String id) 
	{
		this.ID=id;
		
	}
	
	public String getThang()
	{
		
		return this.THANG;
	}
	
	public void setThang(String thang) 
	{
		this.THANG=thang;
		
	}
	
	public String getTrangthai() 
	{
		
		return this.TRANGTHAI;
	}
	
	public void setTrangthai(String trangthai) 
	{
		
		this.TRANGTHAI=trangthai;
	}
	
	public String getNam() 
	{
		
		return this.NAM;
	}

	
	public void setNam(String nam) 
	{
		
		this.NAM=nam;
	}
	
	public String getDvkd() 
	{
		
		return this.DONVIKINHDOANH;
	}
	
	public void setDvkd(String donvikinhdoanh) 
	{
		
		this.DONVIKINHDOANH=donvikinhdoanh;
	}
	
	public String getKenhbanhang() 
	{
		
		return this.KENHBANHANG;
	}
	
	public void setKenhbanhang(String kenhbanhang) 
	{
		
		this.KENHBANHANG=kenhbanhang;
	}
	
	public String getDoituong() 
	{
		
		return this.DOITUONG;
	}
	
	public void setDoituong(String doituong) 
	{
		
		this.DOITUONG=doituong;
	}
	
	public ResultSet getDvkdList()
	{	
		return this.rsDvkd;
	}
	
	public void setDvkdList(ResultSet Dvkdlist) 
	{		
		this.rsDvkd=Dvkdlist;
	}
	
	public ResultSet getKenhbanhangList() 
	{		
		return this.rsKenhbanhang;
	}

	public void setKenhbanhangList(ResultSet Kenhbanhanglist) 
	{		
		this.rsKenhbanhang=Kenhbanhanglist;
	}
	
	public ResultSet getNhomfocusList() 
	{	
		return this.rsNhomfocus;
	}
	
	public void setNhomfocusList(ResultSet Kenhbanhanglist) 
	{		
		this.rsNhomfocus=Kenhbanhanglist;
	}

	public void createRs() 
	{
		String query="";
		query="Select * from donvikinhdoanh where trangthai='1'";
		this.rsDvkd=db.get(query);
		query=" Select * from kenhbanhang where trangthai='1'";
		this.rsKenhbanhang=db.get(query);
	}

	public void init(String query) 
	{
		String sql="";
		 if(query=="")
			 sql="select a.pk_seq,a.DienGiai,a.trangthai,a.thang,a.nam,a.dvkd_fk,b.diengiai as tendvkd,a.doituong,c.diengiai as tenkbh,a.kbh_fk,NV.TEN as TENNV,a.NGAYSUA,a.NGAYTAO,NV.PK_SEQ as MANV,NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS" +
			 		" from nhomfocus a  inner join donvikinhdoanh b on a.dvkd_fk=b.pk_seq inner join kenhbanhang c on a.kbh_fk=c.pk_seq" +
			 		" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " ;
		 else 
			 sql=query;
		 this.rsNhomfocus = createSplittingData(50, 10, " PK_SEQ desc", sql);
		 this.createRs();	
	}	
	public void close() 
	{
		this.db.shutDown();
		try 
		{
			if(this.rsKenhbanhang != null)
				this.rsKenhbanhang.close();
			if(this.rsDvkd != null)
				this.rsDvkd.close();
			if(this.rsNhomfocus!= null)
				this.rsNhomfocus.close();
		}
		catch (Exception e) 
		{
			
		}	
	}
	public String getMsg() {
		return this.MSG;
	}

	public void setMsg(String msg) {
		this.MSG=msg;
	}
	
}
