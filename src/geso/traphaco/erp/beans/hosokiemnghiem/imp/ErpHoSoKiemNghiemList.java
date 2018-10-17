package geso.traphaco.erp.beans.hosokiemnghiem.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.hosokiemnghiem.IErpHoSoKiemNghiemList;
import geso.traphaco.erp.beans.hosokiemnghiemchitiet.IHoSoKiemNghiemChiTiet;
import geso.traphaco.erp.beans.hosokiemnghiemthietbi.IHoSoKiemNghiemThietBi;

public class ErpHoSoKiemNghiemList implements IErpHoSoKiemNghiemList {

	String congtyId;
	String id;
	String ma;
	String ten;
	String tungay;
	String denngay;
	String msg;
	
	ResultSet hosoRs;
	dbutils db;
	ResultSet SanPhamRs;
	String spId;
	
	
	private String pk_seq;
	private String NgayChungTu;
	private String MaSoKN;
	private String MaPhongBan;
	private String SoPhieuKN;
	private String ThoiGianGiaoMau;
	private String NguoiGuiMau;
	private String MaPhieuYeuCauLayMau;
	private String MaSanPham;
	private String TenSanPham;
	private String MaLoaiMauKN;
	private String TenLoaiKN;
	private String MaTieuChuanKiemNghiem;
	private String LoaiKiemTra;
	private String ThoiDiemLayMau;
	private String DienGiai;
	private String TrangThai;
	private String UserId;
	private String UserName;
	
	ResultSet RsPhongBan;
	ResultSet RsYCLayMau;
	ResultSet RsTCKiemNghiem;
	ResultSet RsNhanVien;
	
	List<IHoSoKiemNghiemChiTiet> listCT;
	List<IHoSoKiemNghiemThietBi> listTB;
	
	public ErpHoSoKiemNghiemList(){
		 this.congtyId ="";
		 this.UserId="";
		 this.id="";
		 this.UserName="";
		 this.ma="";
		 this.ten="";
		 this.tungay="";
		 this.denngay="";
		 this.msg="";
		 this.spId="";
		 
		this.pk_seq="";
		this.NgayChungTu="";
		this.MaSoKN="";
		this.MaPhongBan="";
		this.SoPhieuKN="";
		this.ThoiGianGiaoMau="";
		this.NguoiGuiMau="";
		this.MaPhieuYeuCauLayMau="";
		this.MaSanPham="";
		this.TenSanPham="";
		this.MaLoaiMauKN="";
		this.TenLoaiKN="";
		this.MaTieuChuanKiemNghiem="";
		this.LoaiKiemTra="";
		this.ThoiDiemLayMau="";
		this.DienGiai="";
		this.TrangThai="";
		this.UserId="";
		this.UserName="";
		this.listCT=new ArrayList<IHoSoKiemNghiemChiTiet>();
		this.listTB=new ArrayList<IHoSoKiemNghiemThietBi>();
		this.db = new dbutils();
	}
	public String getSpId() {
		return spId;
	}
	public void setSpId(String spId) {
		this.spId = spId;
	}
	public ResultSet getSanPhamRs() {
		return SanPhamRs;
	}
	public void setSanPhamRs(ResultSet sanPhamRs) {
		SanPhamRs = sanPhamRs;
	}
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}
	public String getPk_seq() {
		return pk_seq;
	}
	public void setPk_seq(String pk_seq) {
		this.pk_seq = pk_seq;
	}
	public String getNgayChungTu() {
		return NgayChungTu;
	}
	public void setNgayChungTu(String ngayChungTu) {
		NgayChungTu = ngayChungTu;
	}
	public String getMaSoKN() {
		return MaSoKN;
	}
	public void setMaSoKN(String maSoKN) {
		MaSoKN = maSoKN;
	}
	public String getMaPhongBan() {
		return MaPhongBan;
	}
	public void setMaPhongBan(String maPhongBan) {
		MaPhongBan = maPhongBan;
	}
	public String getSoPhieuKN() {
		return SoPhieuKN;
	}
	public void setSoPhieuKN(String soPhieuKN) {
		SoPhieuKN = soPhieuKN;
	}
	public String getThoiGianGiaoMau() {
		return ThoiGianGiaoMau;
	}
	public void setThoiGianGiaoMau(String thoiGianGiaoMau) {
		ThoiGianGiaoMau = thoiGianGiaoMau;
	}
	public String getNguoiGuiMau() {
		return NguoiGuiMau;
	}
	public void setNguoiGuiMau(String nguoiGuiMau) {
		NguoiGuiMau = nguoiGuiMau;
	}
	public String getMaPhieuYeuCauLayMau() {
		return MaPhieuYeuCauLayMau;
	}
	public void setMaPhieuYeuCauLayMau(String maPhieuYeuCauLayMau) {
		MaPhieuYeuCauLayMau = maPhieuYeuCauLayMau;
	}
	public String getMaSanPham() {
		return MaSanPham;
	}
	public void setMaSanPham(String maSanPham) {
		MaSanPham = maSanPham;
	}
	public String getTenSanPham() {
		return TenSanPham;
	}
	public void setTenSanPham(String tenSanPham) {
		TenSanPham = tenSanPham;
	}
	public String getMaLoaiMauKN() {
		return MaLoaiMauKN;
	}
	public void setMaLoaiMauKN(String maLoaiMauKN) {
		MaLoaiMauKN = maLoaiMauKN;
	}
	public String getTenLoaiKN() {
		return TenLoaiKN;
	}
	public void setTenLoaiKN(String tenLoaiKN) {
		TenLoaiKN = tenLoaiKN;
	}
	public String getMaTieuChuanKiemNghiem() {
		return MaTieuChuanKiemNghiem;
	}
	public void setMaTieuChuanKiemNghiem(String maTieuChuanKiemNghiem) {
		MaTieuChuanKiemNghiem = maTieuChuanKiemNghiem;
	}
	public String getLoaiKiemTra() {
		return LoaiKiemTra;
	}
	public void setLoaiKiemTra(String loaiKiemTra) {
		LoaiKiemTra = loaiKiemTra;
	}
	public String getThoiDiemLayMau() {
		return ThoiDiemLayMau;
	}
	public void setThoiDiemLayMau(String thoiDiemLayMau) {
		ThoiDiemLayMau = thoiDiemLayMau;
	}
	public String getDienGiai() {
		return DienGiai;
	}
	public void setDienGiai(String dienGiai) {
		DienGiai = dienGiai;
	}
	public String getTrangThai() {
		return TrangThai;
	}
	public void setTrangThai(String trangThai) {
		TrangThai = trangThai;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public ResultSet getRsPhongBan() {
		return RsPhongBan;
	}
	public void setRsPhongBan(ResultSet rsPhongBan) {
		RsPhongBan = rsPhongBan;
	}
	public ResultSet getRsYCLayMau() {
		return RsYCLayMau;
	}
	public void setRsYCLayMau(ResultSet rsYCLayMau) {
		RsYCLayMau = rsYCLayMau;
	}
	public ResultSet getRsTCKiemNghiem() {
		return RsTCKiemNghiem;
	}
	public void setRsTCKiemNghiem(ResultSet rsTCKiemNghiem) {
		RsTCKiemNghiem = rsTCKiemNghiem;
	}
	public ResultSet getRsNhanVien() {
		return RsNhanVien;
	}
	public void setRsNhanVien(ResultSet rsNhanVien) {
		RsNhanVien = rsNhanVien;
	}
	public List<IHoSoKiemNghiemChiTiet> getListCT() {
		return listCT;
	}
	public void setListCT(List<IHoSoKiemNghiemChiTiet> listCT) {
		this.listCT = listCT;
	}
	public List<IHoSoKiemNghiemThietBi> getListTB() {
		return listTB;
	}
	public void setListTB(List<IHoSoKiemNghiemThietBi> listTB) {
		this.listTB = listTB;
	}
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	public void init(){
		String query = " select nvsua.ten nvsuaten, sp.ma as spma, sp.ten as spten, a.pk_seq,a.NGAYCHUNGTU,a.MASOKIEMNGHIEM, a.SOPHIEUKIEMNGHIEM, "
				+ "a.thoigiangiaomau,a.nguoiguimau,a.yeucaulaymau_fk,a.sanpham_fk,a.TIEUCHUANKIEMNGHIEM_FK, \n" + 
				" a.LOAIMAUKIEMNGHIEM_FK,a.DIENGIAI,a.PHONGBAN_FK,a.NGUOITAO,a.NGUOISUA, a.ngaytao,a.ngaysua,a.trangthai "
				+ "from erp_hosokiemnghiem a \n" + 
				"inner join erp_sanpham sp on a.sanpham_fk = sp.pk_Seq \r\n" + 
				"inner join nhanvien nvsua on a.nguoisua = nvsua.pk_seq  ";
		
		if(this.tungay.trim().length()>0){
			query += " and a.NGAYCHUNGTU >= '"+this.tungay+"' ";
		}
		
		if(this.denngay.trim().length()>0){
			query += " and a.NGAYCHUNGTU <= '"+this.denngay+"' ";
		}
		
		if(this.ma.trim().length()>0){
			query += " and a.pk_seq like '%"+this.ma+"%' ";
		}
		if(this.MaSoKN.trim().length()>0){
			query += " and a.MASOKIEMNGHIEM like '%"+this.MaSoKN+"%' ";
		}
		
		if(this.MaSanPham.trim().length()>0){
			query += " and sp.ma like '%"+this.MaSanPham+"%' ";
		}
		
		if(this.SoPhieuKN.trim().length()>0){
			query += " and a.SOPHIEUKIEMNGHIEM like '%"+this.SoPhieuKN+"%' ";
		}
		
		if(this.TrangThai.trim().length()>0){
			query += " and a.trangthai like '%"+this.TrangThai+"%' ";
		}
		
		query += " order by a.pk_seq desc ";
		System.out.println("query: " + query);
		this.hosoRs = this.db.get(query);
		this.creates();
		
	}

	public String Delete(String id, String userId) {
		try {
			String msg="";
		String query = "update erp_hosokiemnghiem set trangthai=2 where pk_seq=" + id + " and trangthai=0";
		System.out.println("query xoa la: "+query);
		if( db.updateReturnInt(query)!=1 )
		{
			msg = "Lỗi khi chốt: " + query;
			db.shutDown();
			return msg;
		}
		}catch (Exception e)
		{
			e.printStackTrace();
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.shutDown();
		}
		return msg;
		
	}
	public void creates(){
		String query="select pk_seq,ma, ma +' - '+ ten as ten,* from erp_sanpham where trangthai=1";
		System.out.println("Lay sanphamRS: " +query);
		this.SanPhamRs=this.db.get(query);
		
	}
	public void DBclose() 
	{
		try
		{
			if(this.SanPhamRs !=null) this.SanPhamRs.close();
		}
		catch(Exception er){
			er.printStackTrace();
		}
		finally
		{
			if(this.db!=null)
				this.db.shutDown();
		}
	}

	public String getCongtyId() {
		return congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTungay() {
		return tungay;
	}

	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

	public String getDenngay() {
		return denngay;
	}

	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResultSet getHosoRs() {
		return hosoRs;
	}

	public void setHosoRs(ResultSet hosoRs) {
		this.hosoRs = hosoRs;
	}

}
