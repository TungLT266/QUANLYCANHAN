package geso.traphaco.erp.beans.lapngansach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.lapngansach.IDubaokinhdoanhNam_Giay_List;

public class DubaokinhdoanhNam_Giay_List extends Phan_Trang implements IDubaokinhdoanhNam_Giay_List
{
	
	String CTYID, USERID,ID,MSG,KHO,PHUONGPHAP,NGAYDUBAO,TRANGTHAI, DIENGIAI, HIEULUC, NAM, DVKDID, COPYID;
	String nsId;
	ResultSet rsKho,rsPhuongphap,rsDubao, dvkd, rsDubaoCopy, nsList;
	dbutils db;
	private int num;
	private int[] listPages;
	private int currentPages;
	
	public DubaokinhdoanhNam_Giay_List()
	{
		this.db=new dbutils();
		this.CTYID = "";
		this.USERID="";
		this.NAM = "" + (Integer.parseInt(this.getDateTime().substring(0, 4)) + 1);
		this.ID="";
		this.COPYID = "";
		this.DVKDID = "";
		this.MSG="";
		this.KHO="";
		this.PHUONGPHAP="";
		this.DIENGIAI = "";
		this.NGAYDUBAO="";
		this.TRANGTHAI="";
		this.HIEULUC = "0";
		this.nsId = "";
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

	public String getCopyId() 
	{
		
		return this.COPYID;
	}

	
	public void setCopyId(String copyId) 
	{
		
		this.COPYID = copyId;
	}

	public String getNsId() 
	{
		
		return this.nsId;
	}

	
	public void setNsId(String nsId) 
	{
		
		this.nsId = nsId;
	}
	
	public String getDvkdId() 
	{
		
		return this.DVKDID;
	}

	
	public void setDvkdId(String dvkdId) 
	{
		
		this.DVKDID= dvkdId;
	}
	
	public ResultSet getDvkd() 
	{
		
		return this.dvkd;
	}

	
	public void setDvkd(ResultSet dvkd) 
	{
		
		this.dvkd= dvkd;
	}

	public String getNamdubao() 
	{
		
		return this.NAM;
	}

	
	public void setNamdubao(String nam) 
	{
		
		this.NAM=nam;
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
//		this.rsKho=this.db.get("select PK_SEQ,TEN,DIACHI  from ERP_KHOTT" +
//				" union" +
//		" select 100002,N'Tất cả',N'Tất cả các kho'  from ERP_KHOTT" );
//		this.rsPhuongphap=this.db.get("select PK_SEQ,TENPHUONGPHAP  from ERP_PHUONGPHAPDUBAO" );
		
		this.nsList = this.db.get("SELECT PK_SEQ AS NSID, DIENGIAI FROM ERP_LAPNGANSACH WHERE CONGTY_FK = " + this.CTYID + " ");
		
		this.dvkd = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH WHERE CONGTY_FK = " + this.CTYID + " ");
		
		this.rsDubaoCopy = this.db.get("SELECT DB.PK_SEQ AS DBID, DB.DIENGIAI, DVKD.DONVIKINHDOANH AS DVKD " +
									   "FROM ERP_LAPNGANSACH_DUBAO DB " +
									   "INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = DB.DVKD_FK " +
									   "WHERE DB.CONGTY_FK = " + this.CTYID + " ");
	}
	
	public void init(String query) 
	{
		String sql="";
		 if(query.length() == 0){
			 sql=	"select NV.TEN as TENNV,a.NGAYSUA,a.NGAYTAO, b.DONVIKINHDOANH AS DVKD, NV.PK_SEQ as MANV, " +
			 		"NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS ,a.TRANGTHAI,a.PK_SEQ, " +
			 		"a.NAM, a.DIENGIAI , KBH.DIENGIAI AS KBH, LNS.DIENGIAI AS NGANSACH " +
			 		"from ERP_LAPNGANSACH_DUBAO a " +
			 		"inner join ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = a.LAPNGANSACH_FK " +
			 		"inner join DONVIKINHDOANH b ON b.PK_SEQ = a.DVKD_FK " +
			 		"INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = a.KBH_FK " +	 		
			 		"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ " +
			 		"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
			 		"WHERE a.CONGTY_FK = " + this.CTYID + " ";
		 	System.out.println(sql);
		 }
		 else{ 
			 sql=query;
		 }
		 
		 
		 this.rsDubao = createSplittingDataNew(this.db, 50, 10, "PK_SEQ desc,NAM desc", sql);
		 this.createRs();
		
	}

	public void Copy(){
		String query = 	"INSERT INTO ERP_LAPNGANSACH_DUBAO (DVKD_FK, KBH_FK, DIENGIAI, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, CONGTY_FK,  NAM, LAPNGANSACH_FK) " +
						"SELECT DVKD_FK, KBH_FK, N'Vui lòng cập nhật diễn giải' , 0, " + this.USERID + ", " + this.getDateTime() + ", " + this.USERID + ", " + this.getDateTime() + ", CONGTY_FK,  NAM, LAPNGANSACH_FK " +
						"FROM ERP_LAPNGANSACH_DUBAO WHERE PK_SEQ = '" + this.COPYID + "' ";
		this.db.update(query);
		
		query = "SELECT SCOPE_IDENTITY() AS ID ";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try{
				String Id = "";
				if (rs.next())
					Id = rs.getString("ID");
				
				query = "INSERT INTO ERP_LAPNGANSACH_DUBAOSANPHAM (DUBAO_FK, SANPHAM_FK, THANG, NAM, DUKIENBAN, DONGIA, THANHTIEN, AVG3M, AVG6M, LASTYEAR) " +
						"SELECT " + Id + ", SANPHAM_FK, THANG, NAM, DUKIENBAN, DONGIA, THANHTIEN, AVG3M, AVG6M, LASTYEAR " +
						"FROM ERP_LAPNGANSACH_DUBAOSANPHAM WHERE DUBAO_FK = " + this.COPYID + " ";
				this.db.update(query);
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
	}

	public ResultSet getKhoList() 
	{
		return this.rsKho;
	}
	
	public void setKhoList(ResultSet kholist) {
		this.rsKho=kholist;
	}

	public ResultSet getNsList() 
	{
		return this.nsList;
	}
	
	public void setNsList(ResultSet nslist) {
		
		this.nsList = nslist;
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


	public ResultSet getDubaoCopy() {
		return this.rsDubaoCopy;
	}


	public void setDubaoCopy(ResultSet dubaocopy) {

		this.rsDubaoCopy=dubaocopy;
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

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void close() 
	{
		
		try 
		{
			if(this.nsList != null)
				this.nsList.close();
			
			if(this.rsKho != null)
				this.rsKho.close();
			
			if(this.rsPhuongphap != null)
				this.rsPhuongphap.close();
			
			if(rsDubao != null)
				this.rsDubao.close();
			
			if(dvkd != null)
				this.dvkd.close();
			
			if(rsDubaoCopy != null)
				rsDubaoCopy.close();
			
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


}
