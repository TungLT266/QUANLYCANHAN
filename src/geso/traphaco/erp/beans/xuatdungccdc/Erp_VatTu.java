package geso.traphaco.erp.beans.xuatdungccdc;

import java.util.ArrayList;
import java.util.List;

public class Erp_VatTu
{
	private String id;
	private String sanPhamId;
	private String khoId;
	private String maSanPham;
	private String tenVatTu;
	private String donViTinh;
	private double soLuongTinh;
	private double donGia;
	private double thanhTien;
	private double tonHienTai;
	
	private List<Erp_VatTuSoLo> vatTuSoLoList;
	
	public Erp_VatTu() {
		this.id = "";
		this.sanPhamId = "";
		this.khoId = "";
		this.maSanPham = "";
		this.tenVatTu = "";
		this.donViTinh = "";
		this.soLuongTinh = 0;
		this.donGia = 0;
		this.thanhTien = 0;
		this.tonHienTai = 0;
		this.vatTuSoLoList = new ArrayList<Erp_VatTuSoLo>();
	}
	
	public Erp_VatTu(String id, String sanPhamId, String khoId, String maSanPham, String tenVatTu, String donViTinh, double soLuongTinh, double donGia, double thanhTien, double tonHienTai) {
		this.id = id;
		this.sanPhamId = sanPhamId;
		this.khoId = khoId;
		this.maSanPham = maSanPham;
		this.tenVatTu = tenVatTu;
		this.donViTinh = donViTinh;
		this.soLuongTinh = soLuongTinh;
		this.donGia = donGia;
		this.thanhTien = thanhTien;
		this.tonHienTai = tonHienTai;
		this.vatTuSoLoList = new ArrayList<Erp_VatTuSoLo>();
	}
	
	public Erp_VatTu(String sanPhamId, String khoId, String maSanPham, String tenVatTu, String donViTinh, double soLuongTinh, double donGia, double thanhTien, double tonHienTai) {
		this.sanPhamId = sanPhamId;
		this.khoId = khoId;
		this.maSanPham = maSanPham;
		this.tenVatTu = tenVatTu;
		this.donViTinh = donViTinh;
		this.soLuongTinh = soLuongTinh;
		this.donGia = donGia;
		this.thanhTien = thanhTien;
		this.tonHienTai = tonHienTai;
		this.vatTuSoLoList = new ArrayList<Erp_VatTuSoLo>();
	}
	
	public String getSanPhamId() {
		return sanPhamId;
	}
	public void setSanPhamId(String sanPhamId) {
		this.sanPhamId = sanPhamId;
	}
	public String getKhoId() {
		return khoId;
	}
	public void setKhoId(String khoId) {
		this.khoId = khoId;
	}
	public String getTenVatTu() {
		return tenVatTu;
	}
	public void setTenVatTu(String tenVatTu) {
		this.tenVatTu = tenVatTu;
	}
	public String getDonViTinh() {
		return donViTinh;
	}
	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}
	public double getSoLuongTinh() {
		return soLuongTinh;
	}
	public void setSoLuongTinh(double soLuongTinh) {
		this.soLuongTinh = soLuongTinh;
	}
	public void setVatTuSoLoList(List<Erp_VatTuSoLo> vatTuSoLoList) {
		this.vatTuSoLoList = vatTuSoLoList;
	}
	public List<Erp_VatTuSoLo> getVatTuSoLoList() {
		return vatTuSoLoList;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}

	public double getThanhTien() {
		return thanhTien;
	}

	public void setTonHienTai(double tonHienTai) {
		this.tonHienTai = tonHienTai;
	}

	public double getTonHienTai() {
		return tonHienTai;
	}
}
