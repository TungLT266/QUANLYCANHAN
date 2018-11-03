package geso.traphaco.center.util;

import geso.traphaco.erp.db.sql.dbutils;

import java.util.List;


public class PhatSinhKeToanItem
{
	private int stt;
	private String id;
	private String ngayChungTu;
	private String ngayGhiNhan;
	private String loaiChungTu;
	private String soChungTu;
	private String taiKhoan;
	private String soHieuTaiKhoan;
	private String tenTaiKhoan;
	private String soHieuTaiKhoanDoiUng;
	private String taiKhoanDoiUng;
	private String tenTaiKhoanDoiUng;
	private double no;
	private double co;
	private String noiDungNhapXuat;
	private String doiTuong;
	private String maDoiTuong;
	private String tenDoiTuong;
	private String loaiDoiTuong;
	private String tienTeGoc;
	private String tiGia;
	private String doiTuongDoiUng;
	private double tongGiaTri;
	private double tongGiaTriNguyenTe;
	private String khoanMuc;
	private String dienGiai;
	private String maChungTu;
	private double noDauKy;
	private double coDauKy;
	private double noCuoiKy;
	private double coCuoiKy;
	

	public PhatSinhKeToanItem()
	{
		this.stt = 0;
		this.id = "";
		this.ngayChungTu = "";
		this.ngayGhiNhan = "";
		this.loaiChungTu = "";
		this.soChungTu = "";
		this.taiKhoan = "";
		this.soHieuTaiKhoan = "";
		this.tenTaiKhoan = "";
		this.taiKhoanDoiUng = "";
		this.soHieuTaiKhoanDoiUng = "";
		this.tenTaiKhoanDoiUng = "";
		this.no = 0;
		this.co = 0;
		this.noiDungNhapXuat = "";
		this.doiTuong = "";
		this.maDoiTuong = "";
		this.tenDoiTuong = "";
		this.loaiDoiTuong = "";
		this.tienTeGoc = "";
		this.tiGia = "";
		this.tongGiaTri = 0;
		this.tongGiaTriNguyenTe = 0;
		this.khoanMuc = "";
		this.dienGiai = "";
		this.maChungTu = "";
		this.doiTuongDoiUng="";
		this.noDauKy = 0;
		this.coDauKy = 0;
		this.noCuoiKy = 0;
		this.coCuoiKy = 0;
	}

	public PhatSinhKeToanItem(int stt, String id, String ngayChungTu, String ngayGhiNhan
			, String loaiChungTu, String soChungTu, String taiKhoan, String soHieuTaiKhoan, String tenTaiKhoan, String taiKhoanDoiUng, String soHieuTaiKhoanDoiUng, String tenTaiKhoanDoiUng
			, double no, double co, String noiDungNhapXuat, String doiTuong
			, String maDoiTuong, String tenDoiTuong, String loaiDoiTuong, String tienTeGoc, String tiGia
			, double tongGiaTri, double tongGiaTriNguyenTe, String khoanMuc, String dienGiai, String maChungTu)
	{
		this.stt = stt;
		this.id = id;
		this.ngayChungTu = ngayChungTu;
		this.ngayGhiNhan = ngayGhiNhan;
		this.loaiChungTu = loaiChungTu;
		this.soChungTu = soChungTu;
		this.taiKhoan = taiKhoan;
		this.soHieuTaiKhoan = soHieuTaiKhoan;
		this.tenTaiKhoan = tenTaiKhoan;
		this.taiKhoanDoiUng = taiKhoanDoiUng;
		this.soHieuTaiKhoanDoiUng = soHieuTaiKhoanDoiUng;
		this.tenTaiKhoanDoiUng = tenTaiKhoanDoiUng;
		this.no = no;
		this.co = co;
		this.noiDungNhapXuat = noiDungNhapXuat;
		this.doiTuong = doiTuong;
		this.maDoiTuong = maDoiTuong;
		this.tenDoiTuong = tenDoiTuong;
		this.loaiDoiTuong = loaiDoiTuong;
		this.tienTeGoc = tienTeGoc;
		this.tiGia = tiGia;
		this.tongGiaTri = tongGiaTri;
		this.tongGiaTriNguyenTe = tongGiaTriNguyenTe;
		this.khoanMuc = khoanMuc;
		this.dienGiai = dienGiai;
		this.maChungTu = maChungTu;
		
		this.noDauKy = 0;
		this.coDauKy = 0;
		this.noCuoiKy = 0;
		this.coCuoiKy = 0;
	}
	
	//0: Tài khoản thường, 1: tài khoản công nợ
	static public String getLoaiTaiKhoan(String soHieuTaiKhoan)
	{
		if (soHieuTaiKhoan.startsWith("131") || soHieuTaiKhoan.startsWith("136") || soHieuTaiKhoan.startsWith("138")
				||soHieuTaiKhoan.startsWith("141") || soHieuTaiKhoan.startsWith("331") || soHieuTaiKhoan.startsWith("336")
				|| soHieuTaiKhoan.startsWith("338"))
			return "1";
		return "0";
	}
	
	public static void getLoaiTaiKhoan(dbutils db, List<PhatSinhKeToanItem> list, List<Erp_Item> taiKhoanCongNoList) {
		for (PhatSinhKeToanItem item : list)
		{
			if (Erp_Item.isContainValue(taiKhoanCongNoList, item.getSoHieuTaiKhoan()))
				item.setTaiKhoan("1");
			else
				item.setTaiKhoan("0");
		}
	}

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNgayChungTu() {
		return ngayChungTu;
	}

	public void setNgayChungTu(String ngayChungTu) {
		this.ngayChungTu = ngayChungTu;
	}

	public String getNgayGhiNhan() {
		return ngayGhiNhan;
	}

	public void setNgayGhiNhan(String ngayGhiNhan) {
		this.ngayGhiNhan = ngayGhiNhan;
	}

	public String getLoaiChungTu() {
		return loaiChungTu;
	}

	public void setLoaiChungTu(String loaiChungTu) {
		this.loaiChungTu = loaiChungTu;
	}

	public String getSoChungTu() {
		return soChungTu;
	}

	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getTaiKhoanDoiUng() {
		return taiKhoanDoiUng;
	}

	public void setTaiKhoanDoiUng(String taiKhoanDoiUng) {
		this.taiKhoanDoiUng = taiKhoanDoiUng;
	}

	public double getNo() {
		return no;
	}

	public void setNo(double no) {
		this.no = no;
	}

	public double getCo() {
		return co;
	}

	public void setCo(double co) {
		this.co = co;
	}

	public String getNoiDungNhapXuat() {
		return noiDungNhapXuat;
	}

	public void setNoiDungNhapXuat(String noiDungNhapXuat) {
		this.noiDungNhapXuat = noiDungNhapXuat;
	}

	public String getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(String doiTuong) {
		this.doiTuong = doiTuong;
	}

	public String getMaDoiTuong() {
		return maDoiTuong;
	}

	public void setMaDoiTuong(String maDoiTuong) {
		this.maDoiTuong = maDoiTuong;
	}

	public String getLoaiDoiTuong() {
		return loaiDoiTuong;
	}

	public void setLoaiDoiTuong(String loaiDoiTuong) {
		this.loaiDoiTuong = loaiDoiTuong;
	}

	public String getTienTeGoc() {
		return tienTeGoc;
	}

	public void setTienTeGoc(String tienTeGoc) {
		this.tienTeGoc = tienTeGoc;
	}

	public String getTiGia() {
		return tiGia;
	}

	public void setTiGia(String tiGia) {
		this.tiGia = tiGia;
	}

	public double getTongGiaTri() {
		return tongGiaTri;
	}

	public void setTongGiaTri(double tongGiaTri) {
		this.tongGiaTri = tongGiaTri;
	}

	public double getTongGiaTriNguyenTe() {
		return tongGiaTriNguyenTe;
	}

	public void setTongGiaTriNguyenTe(double tongGiaTriNguyenTe) {
		this.tongGiaTriNguyenTe = tongGiaTriNguyenTe;
	}

	public String getKhoanMuc() {
		return khoanMuc;
	}

	public void setKhoanMuc(String khoanMuc) {
		this.khoanMuc = khoanMuc;
	}

	public String getDienGiai() {
		return dienGiai;
	}

	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}

	public String getMaChungTu() {
		return maChungTu;
	}

	public void setMaChungTu(String maChungTu) {
		this.maChungTu = maChungTu;
	}

	public void setTenDoiTuong(String tenDoiTuong) {
		this.tenDoiTuong = tenDoiTuong;
	}

	public String getTenDoiTuong() {
		return tenDoiTuong;
	}
	
	public double getNoDauKy() {
		return noDauKy;
	}

	public void setNoDauKy(double noDauKy) {
		this.noDauKy = noDauKy;
	}

	public double getCoDauKy() {
		return coDauKy;
	}

	public void setCoDauKy(double coDauKy) {
		this.coDauKy = coDauKy;
	}

	public double getNoCuoiKy() {
		return noCuoiKy;
	}

	public void setNoCuoiKy(double noCuoiKy) {
		this.noCuoiKy = noCuoiKy;
	}

	public double getCoCuoiKy() {
		return coCuoiKy;
	}

	public void setCoCuoiKy(double coCuoiKy) {
		this.coCuoiKy = coCuoiKy;
	}

	public void setTenTaiKhoan(String tenTaiKhoan) {
		this.tenTaiKhoan = tenTaiKhoan;
	}

	public String getTenTaiKhoan() {
		return tenTaiKhoan;
	}

	public void setTenTaiKhoanDoiUng(String tenTaiKhoanDoiUng) {
		this.tenTaiKhoanDoiUng = tenTaiKhoanDoiUng;
	}

	public String getTenTaiKhoanDoiUng() {
		return tenTaiKhoanDoiUng;
	}

	public void setSoHieuTaiKhoan(String soHieuTaiKhoan) {
		this.soHieuTaiKhoan = soHieuTaiKhoan;
	}

	public String getSoHieuTaiKhoan() {
		return soHieuTaiKhoan;
	}

	public void setSoHieuTaiKhoanDoiUng(String soHieuTaiKhoanDoiUng) {
		this.soHieuTaiKhoanDoiUng = soHieuTaiKhoanDoiUng;
	}

	public String getSoHieuTaiKhoanDoiUng() {
		return soHieuTaiKhoanDoiUng;
	}

	public String getDoiTuongDoiUng() {
		return doiTuongDoiUng;
	}

	public void setDoiTuongDoiUng(String doiTuongDoiUng) {
		this.doiTuongDoiUng = doiTuongDoiUng;
	}
}