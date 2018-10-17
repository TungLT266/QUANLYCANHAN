package geso.traphaco.erp.beans.dubaokinhdoanh.imp;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.db.sql.dbutils;
import geso.traphaco.erp.beans.dubaokinhdoanh.IDubaokinhdoanhList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DubaokinhdoanhList extends Phan_Trang implements IDubaokinhdoanhList
{
	
	String CTYID, USERID,ID,MSG,KHO,PHUONGPHAP,NGAYDUBAO,TRANGTHAI, DIENGIAI;
	ResultSet rsKho,rsPhuongphap,rsDubao;
	dbutils db;
	private int num;
	private int[] listPages;
	private int currentPages;
	public DubaokinhdoanhList()
	{
		this.db=new dbutils();
		this.CTYID = "";
		this.USERID="";
		this.ID="";
		this.MSG="";
		this.KHO="";
		this.PHUONGPHAP="";
		this.DIENGIAI = "";
		this.NGAYDUBAO="";
		this.TRANGTHAI="";
	}

	public String getCtyId() 
	{
		
		return this.CTYID;
	}

	
	public void setCtyId(String ctyId) 
	{
		
		this.CTYID = ctyId;
	}

	public String getUserId() 
	{
		
		return this.USERID;
	}

	
	public void setUserId(String userId) 
	{
		
		this.USERID=userId;
	}

	
	public String getId() 
	{
		
		return this.ID;
	}

	
	public void setId(String id) 
	{
		
		this.ID=id;
	}

	
	public String getNgaydubao() 
	{
		
		return this.NGAYDUBAO;
	}

	
	public void setNgaydubao(String ngaydubao) 
	{
		
		this.NGAYDUBAO=ngaydubao;
	}

	public String getDiengiai() 
	{
		
		return this.DIENGIAI;
	}

	
	public void setDiengiai(String diengiai) 
	{
		
		this.DIENGIAI = diengiai;
	}
	
	public String getTrangthai() 
	{
		
		return this.TRANGTHAI;
	}

	
	public void setTrangthai(String trangthai) 
	{
		
		this.TRANGTHAI=trangthai;
	}

	
	public String getMsg() 
	{
		
		return this.MSG;
	}

	
	public void setMsg(String msg) 
	{
		
		this.MSG=msg;
	}	
	public void createRs() 
	{
		this.rsKho=this.db.get("select PK_SEQ,TEN,DIACHI  from ERP_KHOTT" +
				" union" +
		" select 100002,N'Tất cả',N'Tất cả các kho'  from ERP_KHOTT" );
		this.rsPhuongphap=this.db.get("select PK_SEQ,TENPHUONGPHAP  from ERP_PHUONGPHAPDUBAO" );
	}
	public void init(String query) 
	{
		String sql="";
		 if(query=="")
			 sql=	"select NV.TEN as TENNV,a.NGAYSUA,a.NGAYTAO,NV.PK_SEQ as MANV, " +
			 		"NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS ,a.TRANGTHAI,a.PK_SEQ, " +
			 		"a.NGAYDUBAO, a.DIENGIAI, c.TEN AS KHO_FK, c.TEN as KHO " +
			 		"from ERP_DUBAO a " +
			 		"left join ERP_KHOTT c on c.pk_seq=a.KHO_FK " +			 		
			 		"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ " +
			 		"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
			 		"WHERE a.CONGTY_FK = " + this.CTYID + " ";
		 else 
			 sql=query;
		 
		 this.rsDubao = createSplittingDataNew(this.db, 50, 10, "PK_SEQ desc,NGAYDUBAO desc", sql);
		 this.createRs();
		
	}

	
	public void close() 
	{
		
		try 
		{
			if(this.rsKho != null)
				this.rsKho.close();
			if(this.rsPhuongphap != null)
				this.rsPhuongphap.close();
		}
		catch (SQLException e) 
		{
			System.out.println(e.toString());
		}
		finally
		{
			if(this.db!=null)
			this.db.shutDown();
		}
		
	}



	public ResultSet getKhoList() 
	{
		return this.rsKho;
	}


	
	public void setKhoList(ResultSet kholist) {
		
		this.rsKho=kholist;
	}


	
	public ResultSet getPhuongphapList() {
		
		return this.rsPhuongphap;
	}


	
	public void setPhuongphapList(ResultSet phuongphaplist) {
		
		this.rsPhuongphap=phuongphaplist;
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
		ResultSet rs = db.get("select count(*) as c from ERP_DUBAO ");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}


	public ResultSet getDubaoList() {
		return this.rsDubao;
	}


	public void setDubaoList(ResultSet dubaolist) {

		this.rsDubao=dubaolist;
	}


	public String getKho() {
		
		return this.KHO;
	}


	public void setKho(String kho) {
		this.KHO=kho;
		
	}


	public String getPhuongphap() {
		return this.PHUONGPHAP;
	}


	public void setPhuongphap(String phuongphap) {
		this.PHUONGPHAP=phuongphap;
	}

}
