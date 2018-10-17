package geso.traphaco.erp.beans.baocao;

public class BaoCaoVayTienPoJo {
	private String SoHopDong;
	private String TaiKhoanVay;
	private String NgayNhan;
	private String NgayDaoHan;
	private double SoTien;
	private String LoaiTien;
	private float LaiSuat;
	private double DaTra;
	private String GhiChu;
	
	public BaoCaoVayTienPoJo() {
		super();
		SoHopDong = "";
		TaiKhoanVay = "";
		NgayNhan = "";
		NgayDaoHan = "";
		SoTien = 0;
		LoaiTien = "";
		LaiSuat = 0;
		DaTra = 0;
		GhiChu = "";
	}
	
	public BaoCaoVayTienPoJo(String soHopDong, String taiKhoanVay,
			String ngayNhan, String ngayDaoHan, float soTien, String loaiTien,
			float laiSuat, float daTra, String ghiChu) {
		super();
		SoHopDong = soHopDong;
		TaiKhoanVay = taiKhoanVay;
		NgayNhan = ngayNhan;
		NgayDaoHan = ngayDaoHan;
		SoTien = soTien;
		LoaiTien = loaiTien;
		LaiSuat = laiSuat;
		DaTra = daTra;
		GhiChu = ghiChu;
	}
	public String getSoHopDong() {
		return SoHopDong;
	}
	public void setSoHopDong(String soHopDong) {
		SoHopDong = soHopDong;
	}
	public String getTaiKhoanVay() {
		return TaiKhoanVay;
	}
	public void setTaiKhoanVay(String taiKhoanVay) {
		TaiKhoanVay = taiKhoanVay;
	}
	public String getNgayNhan() {
		return NgayNhan;
	}
	public void setNgayNhan(String ngayNhan) {
		NgayNhan = ngayNhan;
	}
	public String getNgayDaoHan() {
		return NgayDaoHan;
	}
	public void setNgayDaoHan(String ngayDaoHan) {
		NgayDaoHan = ngayDaoHan;
	}
	public double getSoTien() {
		return SoTien;
	}
	public void setSoTien(double soTien) {
		SoTien = soTien;
	}
	public String getLoaiTien() {
		return LoaiTien;
	}
	public void setLoaiTien(String loaiTien) {
		LoaiTien = loaiTien;
	}
	public float getLaiSuat() {
		return LaiSuat;
	}
	public void setLaiSuat(float laiSuat) {
		LaiSuat = laiSuat;
	}
	public double getDaTra() {
		return DaTra;
	}
	public void setDaTra(double daTra) {
		DaTra = daTra;
	}
	public String getGhiChu() {
		return GhiChu;
	}
	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

}
