package geso.traphaco.distributor.beans.donhang.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.donhang.IDonhang;
import geso.traphaco.distributor.beans.donhang.IDonhangList;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public class DonhangList extends Phan_Trang implements IDonhangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String tungay;
	String denngay;
	String trangthai;
	String sohoadon;
	String khachhang;
	String msg;
	String mafast;
	String nppId;
	String nppTen;
	String sitecode;

	String nvgnId;

	List<IDonhang> dhlist;

	ResultSet daidienkd;
	String ddkdId;

	ResultSet dhRs;
	ResultSet nvgnRs;
	dbutils db;

	int currentPages;
	int[] listPages;

	String capduyet;
	
	ResultSet tructhuocRs;
	String tructhuocId;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	public DonhangList(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.ddkdId = param[3];
		this.msg = "";
		this.mafast="";
		this.nvgnId="";

		currentPages = 1;
		this.ttId="";
		this.qhId="";
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

		this.capduyet = "";
		this.tructhuocId = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		this.db = new dbutils();
	}

	public DonhangList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.ddkdId = "";
		this.sohoadon = "";
		this.khachhang = "";
		this.msg = "";
		this.mafast="";
		this.nvgnId="";

		currentPages = 1;
		this.ttId="";
		this.qhId="";
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

		this.capduyet = "";
		this.tructhuocId = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
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

	public ResultSet getDaidienkd() 
	{	
		return this.daidienkd;
	}

	public void setDaidienkd(ResultSet daidienkd) 
	{
		this.daidienkd = daidienkd;		
	}

	public String getDdkdId()
	{	
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;		
	}

	public String getTrangthai() 
	{	
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;	
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

	public void createDdkd()
	{
		String query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh a where pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
		if(this.tdv_dangnhap_id.trim().length() > 0)
			query += " and pk_seq = '" + this.tdv_dangnhap_id + "' ";
		
		//PHAN QUYEN
		Utility util = new Utility();
		query += util.getPhanQuyen_TheoNhanVien("daidienkinhdoanh", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		query += " order by TEN asc ";
		
		this.daidienkd = db.get(query);
	}

	public void createnvgn()
	{
		this.nvgnRs = db.get("select * from nhanviengiaonhan where npp_fk = '" + this.nppId +"'");
	}

	private void getNppInfo()
	{
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
			this.nppId=util.getIdNhapp(this.userId,this.db);
			this.nppTen=util.getTenNhaPP();
			this.sitecode=util.getSitecode();
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
	}

	public void createDhBeanList(String query)
	{
		this.dhRs = super.createSplittingDataNew(this.db, super.getItems(), super.getSplittings(), "ngaynhap desc, trangthai asc", query); //db.get(query);
	}

	public void init(String search, String duyet) 
	{
		this.getNppInfo();
		String query = "";	
		if (search.length() == 0)
		{
			query = "select a.machungtu, a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, d.maFAST, isnull(a.DAXUATHOADON,0) as DAXUATHOADON , isnull(DAIN, '0') as DAINDH,     " +
					"			c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen,        " +
					"			a.tonggiatri, d.THANHTOAN, a.VAT " +
					", ' ' as nguoitao, 0 as exitPXK ,  f.ten as nppTEN,   " +
					"STUFF   "+     
					"(  "+      
					"(  "+     
					"	select DISTINCT TOP 100 PERCENT ' , ' + RTRIM(ltrim(isnull(aa.pk_seq,''))) +' '+isnull(cast(aa.LOAIHOADON as nvarchar),'') "+ 
					"	from HOADON aa inner join HOADON_DDH bb on bb.HOADON_FK=aa.PK_SEQ    "+ 
					"	where aa.TRANGTHAI in (2,4) and bb.DDH_FK=A.PK_SEQ    "+ 
					"	ORDER BY ' , ' +  RTRIM(ltrim(isnull(aa.pk_seq,''))) +' '+isnull(cast(aa.LOAIHOADON as nvarchar),'')  "+  
					"	FOR XML PATH('')         "+ 
					" ), 1, 2, ''      "+ 
					") AS SoHoaDon, d.CAPDOGIAOHANG, a.CS_DUYET, a.SS_DUYET, a.tooltip  "+
					" from donhang a   " +
					"		inner  join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq        " +
					"		left join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join NHAPHANPHOI f on a.npp_fk = f.pk_seq  " +
					//"where a.npp_fk = '" + this.nppId + "'  ";
					"where a.npp_fk in (  select pk_seq from NHAPHANPHOI where tructhuoc_fk = '" + this.nppId + "'  )  ";
		}
		else
		{
			query = search;
		}
		
		if(this.tdv_dangnhap_id.trim().length() > 0)
			query += " and a.ddkd_fk = '" + this.tdv_dangnhap_id + "' ";
		
		//PHAN QUYEN
		Utility util = new Utility();
		query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "khachhang_fk", this.getLoainhanvien(), this.getDoituongId() );
		
		if(duyet.equals("1"))
			query += " AND a.trangthai = '1' ";
		
		System.out.println("LAY DON HANG----------------: " + query);
		this.createDhBeanList(query); 

		this.createDdkd();
		this.createnvgn();

		query="Select * from tinhthanh where pk_seq in ( select Distinct TINHTHANH_FK from KHACHHANG where npp_fk ='" + this.nppId + "' ) order by ten ";
		this.ttRs=db.get(query);

		query="Select * from quanhuyen where pk_seq in ( select Distinct QUANHUYEN_FK from KHACHHANG where npp_fk ='" + this.nppId + "' )  ";
		if(this.ttId.length()>0)
		{
			query+=" and tinhthanh_fk='"+this.ttId+"'";
		}
		query+=" order by TEN ";

		this.qhRs = this.db.get(query);

		String sql = " select pk_seq, ten from NHAPHANPHOI where tructhuoc_fk = '" + this.nppId + "' and trangthai = '1' ";
		sql += " and pk_seq in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ";
		this.tructhuocRs = db.get(sql);

	}

	public void DBclose() 
	{		
		try 
		{
			if(!(this.daidienkd == null))
				this.daidienkd.close();
			if(dhRs!=null){
				dhRs.close();
			}
			if(nvgnRs!=null){
				nvgnRs.close();
			}
			if(db != null)
				this.db.shutDown();

		} 
		catch(Exception e) {}
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
		ResultSet rs = db.get("select count(*) as skh from khachhang");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("skh"));
			rs.close();
			return count;
		}
		catch(Exception e) {}

		return 0;
	}

	public String getSohoadon() 
	{
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) 
	{
		this.sohoadon = sohoadon;
	}

	public String getKhachhang() 
	{
		return this.khachhang;
	}

	public void setKhachhang(String khachhang)
	{
		this.khachhang = khachhang;
	}

	public ResultSet getDonhangRs() 
	{
		return this.dhRs;
	}

	public void setDonhangRs(ResultSet dhRs) 
	{
		this.dhRs = dhRs;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}


	public String getMafast() {

		return this.mafast;
	}


	public void setMafast(String mafast) {
		this.mafast=mafast;

	}
	double tongTruoc = 0 ;
	double tongCK = 0;
	double tongSau = 0;

	public void getSumBySearch(String sumqr) {
		if(isSearch){
			ResultSet rs = db.get(sumqr);
			try 
			{
				rs.next();
				this.tongTruoc = rs.getDouble("TONGTRUOCCK");
				this.tongCK = rs.getDouble("TONGCK");
				this.tongSau = rs.getDouble("TONGSAUCK");
				rs.close();
			}
			catch(Exception e) {}
		}
		else{
			this.tongTruoc = 0;
			this.tongCK = 0;
			this.tongSau = 0;
		}
	}


	public double getTongTruoc() {
		return this.tongTruoc;
	}


	public double getTongCK() {
		return this.tongCK;
	}


	public double getTongSau() {
		return this.tongSau;
	}
	boolean isSearch = false;

	public boolean getIsSearch() {
		return this.isSearch;
	}


	public void setIsSearch(boolean search) {
		this.isSearch = search;
	}


	public String getnvgnId() {

		return this.nvgnId;
	}


	public void setnvgnId(String nvgnId) {

		this.nvgnId=nvgnId;
	}


	public ResultSet getnvgnRs() {

		return this.nvgnRs;
	}


	public void setnvgnRs(ResultSet nvgnRs) {

		this.nvgnRs=nvgnRs;
	}

	String ttId,qhId;
	ResultSet ttRs,qhRs;

	public String getTtId()
	{
		return ttId;
	}

	public void setTtId(String ttId)
	{
		this.ttId = ttId;
	}

	public String getQhId()
	{
		return qhId;
	}

	public void setQhId(String qhId)
	{
		this.qhId = qhId;
	}

	public ResultSet getTtRs()
	{
		return ttRs;
	}

	public void setTtRs(ResultSet ttRs)
	{
		this.ttRs = ttRs;
	}

	public ResultSet getQhRs()
	{
		return qhRs;
	}

	public void setQhRs(ResultSet qhRs)
	{
		this.qhRs = qhRs;
	}

	public String getCapduyet() {

		return this.capduyet;
	}


	public void setCapduyet(String capduyet) {

		this.capduyet = capduyet;
	}

	
	public ResultSet getNppTructhuocRs() {
		
		return this.tructhuocRs;
	}

	
	public void setNppTructhuocRs(ResultSet tructhuocRs) {
		
		this.tructhuocRs = tructhuocRs;
	}

	
	public String getNppTructhuocIds() {
		
		return this.tructhuocId;
	}

	
	public void setNppTructhuocIds(String tructhuocId) {
		
		this.tructhuocId = tructhuocId;
	}

	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}

	
	public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
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

}

