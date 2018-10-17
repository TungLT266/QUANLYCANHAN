package geso.traphaco.distributor.beans.donhangctv.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.beans.donhangctv.IDonhangctvList;
import geso.traphaco.distributor.beans.dontrahang.IDontrahangList;

public class DonhangctvList extends Phan_Trang  implements IDonhangctvList, Serializable
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String sophieu;
	String lydo;
	String msg;
	
	ResultSet nhapkhoRs;
	ResultSet khRs;
	String khId;
	
	String nppId;
	String nppTen;
	String sitecode;
	String sochungtu;
	String timkiem = "";
	String loaidonhang;
	String duyetss = "";
	dbutils db;
	ResultSet rsxemctv;
	
	String tenTDV;
	String tenSP;
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	public DonhangctvList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.sophieu="";
		this.sochungtu="";
		this.lydo = "";
		this.msg = "";
		this.loaidonhang = "";
		this.tenTDV = "";
		this.tenSP = "";
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}


	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String search)
	{
		this.getNppInfo();

		String query = "";
        
		if(search.length() > 0)
			query = search;
		else
		{
			
			query =	"	select a.pk_Seq,b.MA as nppMa,b.TEN as nppTen,a.ngaynhap, f.ten as tenkh, c.TEN as nguoiTao, d.TEN as nguoiSua, a.TRANGTHAI,a.SOTIENBVAT,CONVERT(varchar(10), a.Modified_Date,126) as Modified_Date , CONVERT(varchar(10), a.created_date,126) as created_date "+
					"		from donhangctv a inner join NHAPHANPHOI b on b.PK_SEQ = a.NPP_FK "+
					"		inner join NHANVIEN c on c.PK_SEQ=a.NGUOITAO  "+
					"		inner join NHANVIEN d on d.PK_SEQ=a.NGUOISUA "+
					"		inner join KHACHHANG f on f.pk_seq = a.khachhang_FK " +
					" where a.npp_fk = '" + this.nppId + "'";
			
			if(this.duyetss.length() > 0 )
			{
				query =	"	select a.pk_Seq,b.MA as nppMa,b.TEN as nppTen,a.ngaynhap, f.ten as tenkh, c.TEN as nguoiTao, d.TEN as nguoiSua, a.TRANGTHAI,a.SOTIENBVAT, CONVERT(varchar(10), a.Modified_Date,126) as Modified_Date , CONVERT(varchar(10), a.created_date,126) as created_date "+
						"		from donhangctv a inner join NHAPHANPHOI b on b.PK_SEQ = a.NPP_FK "+ 
						"		inner join NHANVIEN c on c.PK_SEQ=a.NGUOITAO  "+
						"		inner join NHANVIEN d on d.PK_SEQ=a.NGUOISUA "+
						"		inner join KHACHHANG f on f.pk_seq = a.khachhang_FK " +
						" where a.npp_fk = '" + this.nppId + "' and a.trangthai in(1,2) ";
			}
		} 
		
		//PHAN QUYEN
		Utility util = new Utility();
		query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "f", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
				
		System.out.println("Init " + query);
		
		this.nhapkhoRs = createSplittingDataNew(this.db, 50, 10, "pk_seq desc, ngaynhap desc, TRANGTHAI asc ", query);
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getSophieu()
	{
		return sophieu;
	}

	public void setSophieu(String sophieu) 
	{
		this.sophieu = sophieu;
	}

	public String getLydo() 
	{
		return lydo;
	}

	public void setLydo(String lydo) 
	{
		this.lydo = lydo;
	}

	public String getTungayTao() 
	{
		return this.tungay;
	}

	public void setTungayTao(String tungay) 
	{
		this.tungay =tungay;	
	}

	public String getDenngayTao() 
	{
		return this.denngay;
	}

	public void setDenngayTao(String denngay) 
	{
		this.denngay = denngay;
	}

	public ResultSet getNhapkhoRs() 
	{
		return this.nhapkhoRs;
	}

	public void setNhapkhoRs(ResultSet nkRs) 
	{
		this.nhapkhoRs = nkRs;
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
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppId = util.getIdNhapp(this.userId);
			this.nppTen = util.getTenNhaPP();
			this.sitecode = util.getSitecode();
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
	}

	public String getSochungtu() {
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu=sochungtu;
	}

	public ResultSet getKhRs() {
		return this.khRs;
	}

	public void setKhRs(ResultSet khrs) {
		this.khRs=khrs;
		
	}

	public String getKhId() {
		return this.khId;
	}

	public void setKhId(String KhId) {
		this.khId=KhId;
		
	}

	
	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}


	public String getDuyetSS() {
	
		return this.duyetss;
	}


	public void setDuyetSS(String duyetss) {
	
		this.duyetss = duyetss;
	}


	public String getTimkiem() {
	
		return this.timkiem;
	}


	public void setTimkiem(String timkiem) {
	
		this.timkiem = timkiem;
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
	
	public ResultSet getRsxemctv() {
		return rsxemctv;
	}

	public void setRsxemctv(ResultSet rsxemctv) {
		this.rsxemctv = rsxemctv;
	}
	public void setRsxemCTV(String sql) {
		this.rsxemctv=db.get(sql);
		
	}

	
	public String getTenTDV() {
		
		return this.tenTDV;
	}

	
	public void setTenTDV(String tenTDV) {
		
		this.tenTDV = tenTDV;
	}

	
	public String getTenSP() {
		
		return this.tenSP;
	}

	
	public void setTenSP(String tenSP) {
		
		this.tenSP = tenSP;
	}
	
	public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}
}