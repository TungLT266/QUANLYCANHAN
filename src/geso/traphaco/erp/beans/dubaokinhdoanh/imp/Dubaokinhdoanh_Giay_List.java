package geso.traphaco.erp.beans.dubaokinhdoanh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.dubaokinhdoanh.IDubaokinhdoanh_Giay_List;

public class Dubaokinhdoanh_Giay_List extends Phan_Trang implements IDubaokinhdoanh_Giay_List
{
	
	String CTYID, USERID,ID,MSG,KHOID,PHUONGPHAP,NGAYDUBAO,TRANGTHAI, DIENGIAI, HIEULUC, DVKDID;
	ResultSet rsKho,rsPhuongphap,rsDubao, rsDvkd;
	dbutils db;
	private int num;
	private int[] listPages;
	private int currentPages;
	public Dubaokinhdoanh_Giay_List()
	{
		this.db=new dbutils();
		this.CTYID = "";
		this.DVKDID = "";
		this.USERID="";
		this.ID="";
		this.MSG="";
		this.KHOID="";
		this.PHUONGPHAP="";
		this.DIENGIAI = "";
		this.NGAYDUBAO="";
		this.TRANGTHAI="";
		this.HIEULUC = "0";
	}

	public String getCtyId() 
	{
		
		return this.CTYID;
	}

	
	public void setCtyId(String ctyId) 
	{
		
		this.CTYID = ctyId;
	}

	public String getDvkdId()
	{

		return this.DVKDID;
	}

	public void setDvkdId(String dvkdId)
	{

		this.DVKDID = dvkdId;
	}

	public ResultSet getDvkdRs()
	{

		return this.rsDvkd;
	}

	public void setDvkdRs(ResultSet rsDvkd)
	{

		this.rsDvkd = rsDvkd;
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
		this.rsKho=this.db.get(	" select PK_SEQ AS KHOID, TEN + ' - ' + DIACHI AS KHO  " +
								" from ERP_KHOTT where LOAI = 5 " ); //CHỈ DỰ BÁO CHO CÁC TRUNG TÂM PHÂN PHỐI
//		this.rsPhuongphap=this.db.get("select PK_SEQ,TENPHUONGPHAP  from ERP_PHUONGPHAPDUBAO" );
		
		this.rsDvkd = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DonViKinhDoanh WHERE CONGTY_FK = " + this.CTYID + " AND TRANGTHAI = '1' ");
	}
	
	public void init(String query) 
	{
		System.out.println("VAO DAY INIT: "+query+ " ABC");
		String sql="";
		 if(query.length()<=0)
			 sql=	"select NV.TEN as TENNV,a.NGAYSUA,a.NGAYTAO,NV.PK_SEQ as MANV, " +
			 		"NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS ,a.TRANGTHAI,a.PK_SEQ, " +
			 		"a.NGAYDUBAO, a.DIENGIAI, a.MOHINH,  a.HIEULUC " +
			 		"from ERP_DUBAO a " +
///			 		"inner join ERP_KHOTT c on c.pk_seq=a.KHO_FK " +			
			 		"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ " +
			 		"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
			 		"WHERE a.CONGTY_FK = " + this.CTYID + " ";
		 else 
			 sql=query;
		 
		 System.out.println("QUERY: "+sql+ " ABC");
		 
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


	public String getKhoId() {
		
		return this.KHOID;
	}


	public void setKhoId(String khoId) {
		this.KHOID = khoId;
		
	}


	public String getPhuongphap() {
		return this.PHUONGPHAP;
	}


	public void setPhuongphap(String phuongphap) {
		this.PHUONGPHAP=phuongphap;
	}

}
