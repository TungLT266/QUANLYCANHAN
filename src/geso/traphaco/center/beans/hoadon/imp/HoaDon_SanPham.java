package geso.traphaco.center.beans.hoadon.imp;

import geso.traphaco.center.beans.hoadon.IHoaDon_SanPham;

public class HoaDon_SanPham implements IHoaDon_SanPham{

	String Id;
	String IdSanPham;
	String TenSanPham;
	double DonGia;
	double ChietKhau;
	double Vat;
	int SoLuong;
	int SoLuongDat;
	String MaSanPham;
	String DonViTinh;
	double thanhtien;
	String ctkmid;
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
	}

	@Override
	public String getIdSanPham() {
		// TODO Auto-generated method stub
		return this.IdSanPham;
	}

	@Override
	public void setIdSanPham(String idsanpham) {
		// TODO Auto-generated method stub
		this.IdSanPham=idsanpham;
	}

	@Override
	public String getTenSanPham() {
		// TODO Auto-generated method stub
		return this.TenSanPham;
	}

	@Override
	public void setTenSanPham(String tensanpham) {
		// TODO Auto-generated method stub
		this.TenSanPham=tensanpham;
	}

	@Override
	public void setSoLuong(int soluong) {
		// TODO Auto-generated method stub
		this.SoLuong=soluong;
	}

	@Override
	public int getSoLuong() {
		// TODO Auto-generated method stub
		return this.SoLuong;
	}

	@Override
	public double getDonGia() {
		// TODO Auto-generated method stub
		return this.DonGia;
	}

	@Override
	public void setDonGia(double dongia) {
		// TODO Auto-generated method stub
		this.DonGia=dongia;
	}

	@Override
	public void setVAT(double vat) {
		// TODO Auto-generated method stub
		this.Vat=vat;
	}

	@Override
	public double getVAT() {
		// TODO Auto-generated method stub
		return this.Vat;
	}

	@Override
	public void setChietKhau(double chietkhau) {
		// TODO Auto-generated method stub
		this.ChietKhau=chietkhau;
	}

	@Override
	public double getChietKhau() {
		// TODO Auto-generated method stub
		return this.ChietKhau;
	}

	@Override
	public double getThanhTien() {
		// TODO Auto-generated method stub
		return (this.SoLuong* this.DonGia);
	}

	@Override
	public String getMaSanPham() {
		// TODO Auto-generated method stub
		return this.MaSanPham;
	}

	@Override
	public void setMaSanPham(String masanpham) {
		// TODO Auto-generated method stub
		this.MaSanPham=masanpham;
	}

	@Override
	public void setDonViTinh(String donvitinh) {
		// TODO Auto-generated method stub
		this.DonViTinh=donvitinh;
	}

	@Override
	public String getDonViTinh() {
		// TODO Auto-generated method stub
		return this.DonViTinh;
	}

	@Override
	public void setSoLuongDat(int soluongdat) {
		// TODO Auto-generated method stub
		this.SoLuongDat=soluongdat;
	}

	@Override
	public int getSoLuongDat() {
		// TODO Auto-generated method stub
		return this.SoLuongDat;
	}

	@Override
	public void setThanhTien(double thanhtien) {
		// TODO Auto-generated method stub
		this.thanhtien=thanhtien;
	}

	@Override
	public void setCTKMID(String _ctkmid) {
		// TODO Auto-generated method stub
		this.ctkmid=_ctkmid;
	}

	@Override
	public String getCTKMId() {
		// TODO Auto-generated method stub
		return this.ctkmid;
	}



}
