package geso.traphaco.distributor.beans.dontrahang.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.beans.dontrahang.IErpNhaphangtraveList;

public class ErpNhaphangtraveList extends Phan_Trang  implements IErpNhaphangtraveList, Serializable
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
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	String loaidonhang;
	
	dbutils db;
	
	public ErpNhaphangtraveList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.sophieu="";
		this.sochungtu="";
		this.lydo = "";
		this.msg = "";
		this.loaidonhang = "";
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
		Utility util = new Utility();
		this.getNppInfo();
		
		String query = "";
		if(search.length() > 0)
			query = search;
		else
		{
			query =	 "select a.pk_Seq, a.NGAYNHAN,  " + 
					"	case when a.isNPP = 1 then e.TEN else f.TEN end as khachhang, " + 
					"		c.TEN as nguoiTao, d.TEN as nguoiSua, a.TRANGTHAI, 0 as tonggiatri, a.NGAYTAO, a.NGAYSUA  " + 
					"from ERP_NHANHANG a   " + 
					"	inner join NHANVIEN c on c.PK_SEQ=a.NGUOITAO   " + 
					"	inner join NHANVIEN d on d.PK_SEQ=a.NGUOISUA  " + 
					"	left join NHAPHANPHOI e on e.PK_SEQ = a.NCC_KH_FK  " + 
					"	left join KHACHHANG f on f.pk_seq = a.NCC_KH_FK  " + 
					"where a.NHAPHANPHOI_FK = '" + this.nppId + "'  and a.TRAHANG_FK is not null ";
		} 
		//query+= " and a.kho_fk in " + util.quyen_kho(this.userId);
		System.out.println("___CHUYEN KHO: " + query);

		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "NCC_KH_FK", this.getLoainhanvien(), this.getDoituongId() );
		query += " and ( ( a.isNPP = 0 " + strQUYEN + " ) or ( a.isNPP = 1 and  a.NCC_KH_FK in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ) ) ";
		
		this.nhapkhoRs = createSplittingDataNew(this.db, 50, 10, "NGAYNHAN desc, pk_Seq desc, TRANGTHAI asc ", query);
	
		//query += " and a.kbh_fk in " + util.quyen_kenh( this.userId ) ;

		query = "	select PK_SEQ, 'NPP / ' + isnull(maFAST, '') + '-' + TEN as TEN  " +
				"	from NHAPHANPHOI where TRANGTHAI = '1' and pk_seq in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "'  ) " +
				" UNION ALL	" +
				"	select PK_SEQ, 'KH / ' + isnull(maFAST,'') + '-' + TEN as TEN " +
				"	from KHACHHANG a where TRANGTHAI = '1'  and npp_fk = '" + this.nppId + "' " + util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );

		this.khRs = db.get(query);
		
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