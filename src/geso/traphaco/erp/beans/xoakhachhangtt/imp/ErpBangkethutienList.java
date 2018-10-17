package geso.traphaco.erp.beans.xoakhachhangtt.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.xoakhachhangtt.IErpBangkethutienList;

public class ErpBangkethutienList  extends Phan_Trang  implements IErpBangkethutienList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String maphieu;

	String trangthai;
	String nppId;
	String congtyId;
	String msg;
	
	ResultSet bangkeRs;
	
	String sotien;
	
	ResultSet nvgnRs;
	String nvgnId;
	
	ResultSet ddkdRs;
	String ddkdId;
	
	ResultSet khuvucRs;
	String khuvucId;
	
	String makhachhang;
	String sohoadon;

	ResultSet rskhoid;
	String khohh;
	String nguoitao;
	

	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpBangkethutienList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.msg = "";
		this.maphieu = "";
		this.sotien = "";
		this.congtyId = "";
		this.nppId = "";
		
		currentPages = 1;
		num = 1;
		
		this.nvgnId = "";
		this.ddkdId = "";
		this.khuvucId = "";
		this.makhachhang = "";
		this.khohh="";
		this.nguoitao="";
		this.sohoadon = "";
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

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public void setmsg(String msg)
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}
	
	public int getCurrentPage() 
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages() 
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as c from ERP_xoakhachhangtt");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public void init(String search)
	{		
		Utility util = new Utility();
		this.getNppInfo();
		
		String query = "";
		
		String dmquyen = 
			" SELECT DISTINCT C2.DMQ_fk, \n"+
			" SUBSTRING( \n"+
			"	( \n"+
			"		SELECT ','+ cast( C1.DMQ_fk as nvarchar(50)) \n"+
			"		FROM phanquyen C1 \n"+
			"		WHERE C1.dmq_fk = C2.dmq_fk \n"+
			"		ORDER BY C1.dmq_fk \n"+
			"		FOR XML PATH ('') \n"+
			"	), 2, 1000) danhmucq \n"+
			" FROM phanquyen C2 WHERE C2.NHANVIEN_FK = "+this.userId+" \n";
		
		System.out.println(dmquyen);
		ResultSet Rsdmq = db.get(dmquyen);
		String dmq = "";
		
		try
		{
			while(Rsdmq.next())
			{
				dmq = Rsdmq.getString("danhmucq");
			}
			Rsdmq.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		if(search.length() <= 0)
		{
			if(dmq.contains("100044") || dmq.contains("100045") || dmq.contains("100026") || dmq.contains("100096")) // QUYỀN KẾ TOÁN TRƯỞNG || QUYỀN KẾ TOÁN || CS MTV + KT Thu Tiền         
			{
				query = "SELECT distinct a.pk_seq, a.trangthai, a.ngaychungtu, a.ngayghiso, a.ngaytao, a.ngaysua, \n" +
						" 		d.ten as nguoitao, e.ten as nguoisua,  isnull(a.tongtientt,0) TIENTT, ( SELECT count(BANGKE_FK) FROM ERP_THUTIEN WHERE TRANGTHAI IN (1) AND BANGKE_FK = a.PK_SEQ  ) isThuTien \n" +
						"FROM ERP_BANGKETHUTIEN a  \n" +
						"INNER JOIN ERP_BANGKETHUTIEN_HOADON b on a.pk_seq=b.bangke_fk  \n"+
					    "INNER JOIN ERP_HOADONNPP c on c.PK_SEQ=b.hoadon_fk  \n"+
						"INNER JOIN NHANVIEN d on a.nguoitao = d.pk_seq inner join NHANVIEN e on a.nguoisua = e.pk_seq \n"+
						"WHERE a.npp_fk =  " + this.nppId + " ";
			}
			else
			{
				query = "SELECT distinct a.pk_seq, a.trangthai, a.ngaychungtu, a.ngayghiso, a.ngaytao, a.ngaysua, \n" +
						" 		d.ten as nguoitao, e.ten as nguoisua,  isnull(a.tongtientt,0) TIENTT, ( SELECT count(BANGKE_FK) FROM ERP_THUTIEN WHERE TRANGTHAI IN (1) AND BANGKE_FK = a.PK_SEQ  ) isThuTien \n" +
						"FROM ERP_BANGKETHUTIEN a  \n" +
						"INNER JOIN ERP_BANGKETHUTIEN_HOADON b on a.pk_seq=b.bangke_fk  \n"+
					    "INNER JOIN ERP_HOADONNPP c on c.PK_SEQ=b.hoadon_fk  \n"+
						"INNER JOIN NHANVIEN d on a.nguoitao = d.pk_seq inner join NHANVIEN e on a.nguoisua = e.pk_seq \n"+
						"WHERE a.npp_fk =  " + this.nppId + " and ( a.NGUOITAO = "+this.userId +" )";
			}
		}
		else
			query = search;
		
		System.out.println("BANG KE 123: " + query);
		
		this.bangkeRs = createSplittingDataNew(this.db, 50, 10, "ngaychungtu desc, trangthai asc ", query);
		
		query = "select pk_seq, ten from NHANVIENGIAONHAN a where trangthai = 1 and npp_fk = '" + this.nppId + "' "
				+ util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.nvgnRs = db.get(query);
		
		query = "select PK_SEQ, TEN from DAIDIENKINHDOANH where trangthai = 1"
				+ util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		this.ddkdRs = db.get(query);
		
/*		query = "select pk_seq, ten from KHUVUC where trangthai = 1  ";
		this.khuvucRs = db.get(query);*/
		this.rskhoid=db.get("select pk_seq ,ten from kho where 1 = 1 and PK_SEQ IN ( " + util.quyen_kho(this.userId) + ")" );
	}

	public void DBclose() 
	{
		try 
		{
			if(this.bangkeRs != null)
				this.bangkeRs.close();
			this.db.shutDown();		
		} 
		catch (SQLException e) {e.printStackTrace();
		}

	}

	public String getMaPhieu() {
		return this.maphieu;
	}

	
	public void setMaPhieu(String maphieu) {
		this.maphieu=maphieu;
		
	}

	
	public String getSotien() {
		
		return this.sotien;
	}


	public void setSotien(String Sotien) {
		
		this.sotien = Sotien;
	}

	
	public String getCongtyId() {
		
		return null;
	}

	
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}

	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
	}
	
	public String getNvgnId() {
		
		return this.nvgnId;
	}

	
	public void setNvgnId(String nvgnId) {
		
		this.nvgnId = nvgnId;
	}

	
	public ResultSet getNvgnRs() {
		
		return this.nvgnRs;
	}

	
	public void setNvgnRs(ResultSet nvgnRs) {
		
		this.nvgnRs = nvgnRs;
	}

	
	public String getDdkdId() {
		
		return this.ddkdId;
	}

	
	public void setDdkdId(String ddkdId) {
		
		this.ddkdId = ddkdId;
	}

	
	public ResultSet getDdkdRs() {
		
		return this.ddkdRs;
	}

	
	public void setDdkdRs(ResultSet ddkdRs) {
		
		this.ddkdRs = ddkdRs;
	}

	
	public String getKhuvucId() {
		
		return this.khuvucId;
	}

	
	public void setKhuvucId(String kvId) {
		
		this.khuvucId = kvId;
	}

	
	public ResultSet getKhuvucRs() {
		
		return this.khuvucRs;
	}

	
	public void setKhuvucRs(ResultSet kvRs) {
		
		this.khuvucRs = kvRs;
	}

	public String getMakhachhang() {

		return this.makhachhang;
	}

	public void setMakhachhang(String makhachhang) {
		
		this.makhachhang = makhachhang;
	}


	public ResultSet getBangkeRs() {

		return this.bangkeRs;
	}


	public void setBangkeRs(ResultSet bangkeRs) {

		this.bangkeRs = bangkeRs;
	}

	public String getNppId() {

		return this.nppId;
	}


	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}
	public ResultSet getRskhoid() {
		return rskhoid;
	}

	public void setRskhoid(ResultSet rskhoid) {
		this.rskhoid = rskhoid;
	}

	public String getKhohh() {
		return khohh;
	}

	public void setKhohh(String khohh) {
		this.khohh = khohh;
	}
	public String getNguoitao() {
		return nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}
	
	Object loainhanvien;
	Object doituongId;
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}


	public void setSohoadon(String sohoadon) {
	
		this.sohoadon = sohoadon;
	}


	public String getSohoadon() {
	
		return this.sohoadon;
	}

}
