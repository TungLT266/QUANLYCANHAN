package geso.traphaco.erp.beans.thuenhapkhau.imp;

import java.sql.ResultSet;
import java.util.List;

public class ErpSanPhamNhapKhau
{
	private String id;
	private String hoaDonNCCId;
	private int loaiHangHoa;//0:sp, 1: tài sản, 2: chi phí
	private String ten;
	private String donViTinh;
	private String soLo;
	private double soLuong;
	private double donGiaNT;
	private double donGiaVND;
	private double thueSuat;
	private double thanhTien;
	private double tienTinhThueNhapKhau;
	private double thueSuatNK;
	private double thueNK;
	private double phanTramVATNK;
	private double VATNK;
	
	
	public ErpSanPhamNhapKhau()
	{
		this.id = "";
		this.hoaDonNCCId = "";
		this.setLoaiHangHoa(0); 
		this.ten = "";
		this.donViTinh = "";
		this.soLo = "";
		this.soLuong = 0;
		this.donGiaNT = 0;
		this.donGiaVND = 0;
		this.thueSuat = 0;
		this.thanhTien = 0;
		this.tienTinhThueNhapKhau = 0;
		this.thueSuatNK = 0;
		this.thueNK = 0;
		this.phanTramVATNK = 0;
		this.VATNK = 0;
	}
	
	public ErpSanPhamNhapKhau(String id, String hoaDonNCCId, int loaiHangHoa, String ten
			, String donViTinh, String soLo, double soLuong, double donGiaNT
			, double donGiaVND, double thueSuat, double thanhTien, double tienTinhThueNhapKhau
			, double thueSuatNK, double thueNK, double phanTramVATNK, double VATNK)
	{
		this.id = id;
		this.hoaDonNCCId = hoaDonNCCId;
		this.loaiHangHoa = loaiHangHoa; 
		this.ten = ten;
		this.donViTinh = donViTinh;
		this.soLo = soLo;
		this.soLuong = soLuong;
		this.donGiaNT = donGiaNT;
		this.donGiaVND = donGiaVND;
		this.thueSuat = thueSuat;
		this.thanhTien = thanhTien;
		this.tienTinhThueNhapKhau = tienTinhThueNhapKhau;
		this.thueSuatNK = thueSuatNK;
		this.thueNK = thueNK;
		this.phanTramVATNK = phanTramVATNK;
		this.VATNK = VATNK;
	}
	
	public static boolean getListFromResultset(ResultSet rs, List<ErpSanPhamNhapKhau> list)
	{
		try{
			if (rs != null)
			{
				while (rs.next())
				{
					String sanPhamId = rs.getString("SANPHAM_FK");
					String taiSanId = rs.getString("TAISAN_FK");
					String chiPhiId = rs.getString("CHIPHI_FK");
					String id;
					int loaiHangHoa;
					if (sanPhamId.trim().length() > 0)
					{
						loaiHangHoa = 0;
						id = sanPhamId;
					}
					else if (taiSanId.trim().length() > 0)
					{
						loaiHangHoa = 1;
						id = taiSanId;
					}
					else
					{
						loaiHangHoa = 2;
						id = chiPhiId;
					}
					
					String hoaDonNCCId = rs.getString("HOADONNCC_FK");
					String ten = rs.getString("ten");
					String donViTinh = rs.getString("dvt");
					String soLo = rs.getString("soLo");
					double soLuong = rs.getDouble("soLuong");
					double donGiaNT = rs.getDouble("DONGIANT");
					double donGiaVND = rs.getDouble("DONGIA");
					double thueSuat = rs.getDouble("THUESUAT");
					double thanhTien = rs.getDouble("THANHTIEN");
					double tienTinhThueNhapKhau = rs.getDouble("TIENTINHTHUENK");
					double thueSuatNK = rs.getDouble("THUESUATNK");
					double thueNK = rs.getDouble("THUENHAPKHAU");
					double phanTramVATNK = rs.getDouble("PHANTRAMVATNK");
					double VATNK = rs.getDouble("VATNK");
					
					ErpSanPhamNhapKhau item = new ErpSanPhamNhapKhau(id, hoaDonNCCId, loaiHangHoa, ten
							, donViTinh, soLo, soLuong, donGiaNT
							, donGiaVND, thueSuat, thanhTien, tienTinhThueNhapKhau
							, thueSuatNK, thueNK, phanTramVATNK, VATNK);
					System.out.println("don gia vnd: " + item.getDonGiaVND());
					list.add(item);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void nhanTiGia(Double tiGia, List<ErpSanPhamNhapKhau> list)
	{
		for (ErpSanPhamNhapKhau sanPham : list)
		{
			double donGiaVND = sanPham.getDonGiaNT() * tiGia;
			sanPham.setDonGiaVND(donGiaVND);
			double thanhTien = sanPham.getThanhTien() * tiGia;
//			sanPham.setThanhTien(thanhTien);
			sanPham.setTienTinhThueNhapKhau(thanhTien);
		}
	}
	
	public static double tinhTongThanhTien(List<ErpSanPhamNhapKhau> list)
	{
		double thanhTien = 0;
		for (ErpSanPhamNhapKhau sanPham : list)
		{
			thanhTien += sanPham.getThanhTien();
		}
		
		return thanhTien;
	}
	
	public static double tinhTongVATNK(List<ErpSanPhamNhapKhau> list)
	{
		double vatNK = 0;
		for (ErpSanPhamNhapKhau sanPham : list)
		{
			vatNK += sanPham.getVATNK();
		}
		
		return vatNK;
	}
	
	public static double tinhTongThueNK(List<ErpSanPhamNhapKhau> list)
	{
		double thueNK = 0;
		for (ErpSanPhamNhapKhau sanPham : list)
		{
			thueNK += sanPham.getThueNK();
		}
		
		return thueNK;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getDonViTinh() {
		return donViTinh;
	}
	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}
	public String getSoLo() {
		return soLo;
	}
	public void setSoLo(String soLo) {
		this.soLo = soLo;
	}
	public double getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(double soLuong) {
		this.soLuong = soLuong;
	}
	public double getDonGiaNT() {
		return donGiaNT;
	}
	public void setDonGiaNT(double donGiaNT) {
		this.donGiaNT = donGiaNT;
	}
	public double getDonGiaVND() {
		return donGiaVND;
	}
	public void setDonGiaVND(double donGiaVND) {
		this.donGiaVND = donGiaVND;
	}
	public double getThueSuat() {
		return thueSuat;
	}
	public void setThueSuat(double thueSuat) {
		this.thueSuat = thueSuat;
	}
	public double getThanhTien() {
		return thanhTien;
	}
	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}
	public double getTienTinhThueNhapKhau() {
		return tienTinhThueNhapKhau;
	}
	public void setTienTinhThueNhapKhau(double tienTinhThueNhapKhau) {
		this.tienTinhThueNhapKhau = tienTinhThueNhapKhau;
	}
	public double getThueSuatNK() {
		return thueSuatNK;
	}
	public void setThueSuatNK(double thueSuatNK) {
		this.thueSuatNK = thueSuatNK;
	}
	public double getThueNK() {
		return thueNK;
	}
	public void setThueNK(double thueNK) {
		this.thueNK = thueNK;
	}
	public double getPhanTramVATNK() {
		return phanTramVATNK;
	}
	public void setPhanTramVATNK(double phanTramVATNK) {
		this.phanTramVATNK = phanTramVATNK;
	}
	public double getVATNK() {
		return VATNK;
	}
	public void setVATNK(double vATNK) {
		VATNK = vATNK;
	}

	public void setLoaiHangHoa(int loaiHangHoa) {
		this.loaiHangHoa = loaiHangHoa;
	}

	public int getLoaiHangHoa() {
		return loaiHangHoa;
	}

	public void setHoaDonNCCId(String hoaDonNCCId) {
		this.hoaDonNCCId = hoaDonNCCId;
	}

	public String getHoaDonNCCId() {
		return hoaDonNCCId;
	}
}