package geso.traphaco.erp.beans.thanhlytaisan.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.thanhlytaisan.IErpThanhlytaisanList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ErpThanhlytaisanList extends Phan_Trang implements IErpThanhlytaisanList
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	String loai;
	String dvthId;
	ResultSet dvthRs;
	
	String khTen;
	String tongtien;
	String msg;
	String task;
	String sodonthanhlytaisan;
	String soHD;
	String ngayHD;
	ResultSet loaisanphamRs;
	
	ResultSet dtltsRs;
	
	ResultSet khRs;    
	String khIds;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
//	private Utility util;
	
	public ErpThanhlytaisanList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.dvthId = "";
		this.khTen = "";
		this.tongtien = "";
		this.task = "";
		this.msg = "";
		this.loai = "";
		this.sodonthanhlytaisan = "";
		this.soHD = "";
		this.ngayHD = "";
		currentPages = 1;
		num = 1;
		this.db = new dbutils();
//		util = new Utility();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
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

	public String getLoai() 
	{
		return this.loai;
	}

	public void setLoai(String loai) 
	{
		this.loai = loai;
	}


	public String getKH() 
	{
		return this.khTen;
	}

	public void setKH(String kh) 
	{
		this.khTen = kh;
	}

	public String getTongtiensauVat() 
	{
		return this.tongtien;
	}

	public void setTongtiensauVat(String ttsauvat)
	{
		this.tongtien = ttsauvat;
	}

	public String getSoHD() 
	{
		return this.soHD;
	}

	public void setSoHD(String soHD)
	{
		this.soHD = soHD;
	}

	public String getNgayHD() 
	{
		return this.ngayHD;
	}

	public void setNgayHD(String ngayHD)
	{
		this.ngayHD = ngayHD;
	}

	public void setmsg(String msg) 
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getDtltsList() 
	{
		return this.dtltsRs;
	}

	public void setDtltsList(ResultSet dtltslist) 
	{
		this.dtltsRs = dtltslist;
	}

	public void init(String search)
	{
		String query = "";
		if(search.length() <= 0)
		{
			query = " select a.PK_SEQ as dtltsId, a.NGAY, c.TEN as khTen, c.MA as khMa, \n" +
					" CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, \n" +
					" a.TONGTIENAVAT, a.VAT, \n" +
					" a.TONGTIENBVAT, \n" +
					" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, a.LOAI  \n" +
					" FROM ERP_THANHLYTAISAN a  \n" +
					
					" INNER JOIN NHAPHANPHOI c on a.KHACHHANG_FK = c.PK_SEQ \n" +
					" INNER JOIN NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ \n" +
					" WHERE A.CONGTY_FK = '" + this.congtyId + "'\n" ;
			
			if(this.task.length() > 0)
					query += " and a.trangthai = 1 \n";
			//query += "order by a.NGAYMUA desc, a.trangthai asc, a.pk_seq desc";
		}
		else
			query = search;
	
		System.out.println("Query init 111 232 : \n" + query + "\n---------------------------------------");
		
		this.dtltsRs = createSplittingDataNew(this.db, 50, 10, "NGAY desc, trangthai asc, dtltsId desc", query);
	}

	
	public void DBclose() 
	{
		try 
		{
			if(this.dtltsRs != null) 
				this.dtltsRs.close(); 
			
			if(this.khRs != null) 
				this.khRs.close(); 
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
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
		ResultSet rs = db.get("select count(*) as c from ERP_thanhlytaisan");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public ResultSet getKhRs() 
	{
		return this.khRs;
	}

	public void setKhRs(ResultSet khRs) 
	{
		this.khRs = khRs;
	}

	public void setKhIds(String khIds) 
	{
		this.khIds = khIds;
	}

	public String getKhIds() 
	{
		return this.khIds;
	}


	public void initBaoCao() 
	{
		this.khRs = db.get("select pk_seq, ma as khMa, ten as khTen from erp_khachhang");
	}

	public String getSodonthanhlytaisan()
	{
		return this.sodonthanhlytaisan;
	}

	public void setSodonthanhlytaisan(String sodonthanhlytaisan) 
	{
		this.sodonthanhlytaisan = sodonthanhlytaisan;
	}

	
	public ResultSet getLoaisanpham() 
	{
		
		return this.loaisanphamRs;
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
	public String getCtyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCtyId(String ctyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCty(String cty) {
		// TODO Auto-generated method stub
		
	}
}
