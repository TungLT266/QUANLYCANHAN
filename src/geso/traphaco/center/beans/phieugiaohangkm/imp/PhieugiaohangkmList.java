package geso.traphaco.center.beans.phieugiaohangkm.imp;

import java.sql.ResultSet;
import geso.traphaco.center.beans.phieugiaohangkm.IPhieugiaohangkmList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;

public class PhieugiaohangkmList extends Phan_Trang implements IPhieugiaohangkmList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String nppTen;
	String msg;
	
	String madonhang;
	
	ResultSet nppRs;
	ResultSet DondathangRs;
	
	String khTen;
	ResultSet khRs;
	String nppId;
	
	String loaidonhang;
	String sohoadon;
	String type;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public PhieugiaohangkmList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "0";
	    this.khTen= "";
	    this.nppId="";
	    this.sohoadon="";
	    this.madonhang = "";
		currentPages = 1;
		num = 1;
		this.hoadonId="";
		this.type = "";
		
		this.db = new dbutils();
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
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
		ResultSet rs = db.get("select count(*) as c from HOADON ");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
	}
	
	public void init(String search)
	{
		this.getNppInfo();
		String query = "";
       
		if(search.length() > 0)
			query = search ;
		else
		{
			query = "select  a.sohoadon, a.kyhieu ,  a.PK_SEQ as hdId, a.trangthai, a.ngayxuatHD as ngaynhap, NV.TEN as nguoitao, KH.TEN as TENKH, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua," +
					"        a.tongtienavat as avat, case a.trangthai when 1 then 1 when 2 then 2 when 3 then 4 when 4 then 3 when 5 then 5 end as STT_SORT,isnull(d.InPhieuGiaoHangKM, 0) as DAINPGH,c.ddh_fk as pk_seq  " +
					"from ERP_HOADON a  " +
					"	inner join nhaphanphoi KH on a.npp_fk=KH.PK_SEQ  " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
					" inner join  ERP_hoadon_ddh c on c.hoadon_fk=a.pk_Seq  " +
					" inner join ERP_dondathang  d on c.DDH_FK=d.PK_SEQ "+
					"where a.pk_seq > 0  and isnull(loaihoadon,0) in ( 1, 2 ) " +
					"  and a.trangthai not in (1, 3, 5) ";	
		} 
			
		System.out.println("__DONHANG: " + query);
		this.DondathangRs = createSplittingDataNew(this.db, 50, 10,"  DAINPGH asc , ngaynhap desc ", query);
		//this.DondathangRs = createSplittingData(50, 10, " kyhieu desc, STT_SORT asc, SORT_SOHOADON desc, ngayxuatHD desc ", query);
		
		//this.khRs = db.get("select  PK_SEQ, maFAST + ' - ' + TEN AS TEN from KHACHHANG where TRANGTHAI = '1'");
		

		String KH = "select PK_SEQ, maFAST + ' - ' + TEN AS TENKH from nhaphanphoi where TRANGTHAI = '1' and pk_seq <> 1 " ;
		this.khRs= db.get(KH);
		 
		System.out.println("khách hàng: "+KH);
		 
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
		try
		{
			if(nppRs!=null)nppRs.close();
			if(DondathangRs!=null)DondathangRs.close();
			if(khRs!=null)khRs.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public ResultSet getDondathangRs() 
	{
		return this.DondathangRs;
	}

	public void setDondathangRs(ResultSet nkRs) 
	{
		this.DondathangRs = nkRs;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getNppTen() {
		
		return this.nppTen;
	}

	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getKhTen() {
		return this.khTen;
	}

	
	public void setKhTen(String KhTen) {
		this.khTen = KhTen;
		
	}

	
	public ResultSet getKhRs() {
		return this.khRs;
	}

	
	public void setKhRs(ResultSet KhRs) {
		this.khRs = KhRs;
		
	}

	public String getSoHoaDon()
	{
		return this.sohoadon;
	}
	public void setSoHoaDon(String sohoadon)
	{
		this.sohoadon =sohoadon;
	}

	
	public String getMadonhang() {
		
		return this.madonhang;
	}

	
	public void setMadonhang(String madonhang) {
		this.madonhang = madonhang;
		
	}

	
	public void init_dieuchinh(String search) {
		
		this.getNppInfo();
		String query = "";
       
		if(search.length() > 0)
			query = search ;
		else
		{
			query = "select  a.sohoadon, a.kyhieu ,  a.PK_SEQ, a.trangthai, a.ngayxuatHD, NV.TEN as nguoitao, KH.TEN as nppTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua," +
					"        a.tongtienavat as avat, case a.trangthai when 1 then 1 when 2 then 2 when 3 then 4 when 4 then 3 when 5 then 5 end as STT_SORT   " +
					"from HOADON a  " +
					"	inner join KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ  " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
					"where a.pk_seq > 0 and a.NPP_FK ="+ this.nppId +" and isnull(loaihoadon,0) in ( 1, 2 ) " +
					" and a.nguoitao = "+ this.userId +" and a.trangthai not in (1, 3, 5)  ";
		} 
		
		if(this.type.equals("PGH"))
			query += " and a.ngayxuatHD >= '2014-10-01' ";
		
		System.out.println("___HOADON: " + query);
		
		this.DondathangRs = createSplittingDataNew(this.db, 50, 10, "  STT_SORT asc, ngayxuatHD desc, sohoadon desc ", query);
		//this.DondathangRs = createSplittingData(50, 10, "  trangthai asc , sohoadon desc ", query);
		
		System.out.println("select  PK_SEQ, maFAST + ' - ' + TEN AS TEN from KHACHHANG where TRANGTHAI = '1'");
		
		this.khRs = db.get("select  PK_SEQ, maFAST + ' - ' + TEN AS TEN from KHACHHANG where TRANGTHAI = '1'");
		
	}

	String hoadonId;

	public String getHoadonId()
  {
  	return hoadonId;
  }

	public void setHoadonId(String hoadonId)
  {
  	this.hoadonId = hoadonId;
  }

	
	public String getType() {
		
		return this.type;
	}

	
	public void setType(String type) {
		
		this.type = type;
	}
	
}
