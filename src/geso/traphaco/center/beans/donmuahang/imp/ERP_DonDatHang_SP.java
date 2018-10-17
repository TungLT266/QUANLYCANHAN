package geso.traphaco.center.beans.donmuahang.imp;

import geso.traphaco.center.beans.donmuahang.IERP_DonDatHang_SP;

public class ERP_DonDatHang_SP implements IERP_DonDatHang_SP
{
	String Id;
	String IdSanPham;
	String TenSanPham;
	double DonGia;
	double ChietKhau;
	double Vat;
	float SoLuong;
	float SoLuongDat;
	String MaSanPham;
	String DonViTinh;
	double thanhtien;
	String ctkmid;
	String SHEME;
	int soluongton;
	float soluongduyet;

	double trongluong;
	double thetich;
	String Khott;
	String TenKhoTT;
	double Quycach;
	double goiVc;
	double giachuan;
	String donviduyetId;
	float soluongle;

	

	public ERP_DonDatHang_SP()
	{
		IdSanPham = "";
		TenSanPham = "";
		DonGia = 0;
		ChietKhau = 0;
		Vat = 0;
		SoLuong = 0;
		SoLuongDat = 0;
		MaSanPham = "";
		DonViTinh = "";
		thanhtien = 0;
		ctkmid = "";
		SHEME = "";
		soluongton = 0;
		soluongduyet = 0;

		trongluong = 0;
		thetich = 0;
		Khott = "";
		TenKhoTT = "";
		Quycach = 0;
		goiVc = 0;
		giachuan = 0;
		donviduyetId = "";
	}

	public String getId()
	{

		return this.Id;
	}

	public void setId(String id)
	{

		this.Id = id;
	}

	public String getIdSanPham()
	{

		return this.IdSanPham;
	}

	public void setIdSanPham(String idsanpham)
	{

		this.IdSanPham = idsanpham;
	}

	public String getTenSanPham()
	{

		return this.TenSanPham;
	}

	public void setTenSanPham(String tensanpham)
	{

		this.TenSanPham = tensanpham;
	}

	public void setSoLuong(float soluong)
	{

		this.SoLuong = soluong;
	}

	public float getSoLuong()
	{

		return this.SoLuong;
	}

	public double getDonGia()
	{

		return this.DonGia;
	}

	public void setDonGia(double dongia)
	{

		this.DonGia = dongia;
	}

	public void setVAT(double vat)
	{

		this.Vat = vat;
	}

	public double getVAT()
	{

		return this.Vat;
	}

	public void setChietKhau(double chietkhau)
	{

		this.ChietKhau = chietkhau;
	}

	public double getChietKhau()
	{

		return this.ChietKhau;
	}

	public double getThanhTien()
	{

		return this.thanhtien;
	}

	public String getMaSanPham()
	{

		return this.MaSanPham;
	}

	public void setMaSanPham(String masanpham)
	{

		this.MaSanPham = masanpham;
	}

	public void setDonViTinh(String donvitinh)
	{

		this.DonViTinh = donvitinh;
	}

	public String getDonViTinh()
	{

		return this.DonViTinh;
	}

	public void setSoLuongDat(float soluongdat)
	{

		this.SoLuongDat = soluongdat;
	}

	public float getSoLuongDat()
	{

		return this.SoLuongDat;
	}

	public void setThanhTien(double thanhtien)
	{

		this.thanhtien = thanhtien;
	}

	public void setCtkmId(String _ctkmid)
	{

		this.ctkmid = _ctkmid;
	}

	public String getCtkmId()
	{

		return this.ctkmid;
	}

	public void setSoluongton(int slton)
	{

		this.soluongton = slton;
	}

	public int getsoluongton()
	{

		return this.soluongton;
	}

	public void setSHEME(String ctkmid)
	{
		this.SHEME = ctkmid;
	}

	public String getSHEME()
	{

		return this.SHEME;
	}

	public double getTrongluong()
	{
		return this.trongluong;
	}

	public void setTrongluong(double trongluong)
	{
		this.trongluong = trongluong;
	}

	public double getThetich()
	{
		return this.thetich;
	}

	public void setThetich(double thetich)
	{
		this.thetich = thetich;
	}

	@Override
	public String getKhoTT()
	{

		return this.Khott;
	}

	@Override
	public void setKhoTT(String KhoTT)
	{

		this.Khott = KhoTT;
	}

	@Override
	public String getTenKhoTT()
	{

		return this.TenKhoTT;
	}

	@Override
	public void setTenKhoTT(String tenkho)
	{

		this.TenKhoTT = tenkho;
	}

	@Override
	public void setSoluongduyet(float _soluongduyet)
	{

		this.soluongduyet = _soluongduyet;
	}

	@Override
	public float getsoluongduyet()
	{

		return this.soluongduyet;
	}

	@Override
	public double getQuyCach()
	{

		return this.Quycach;
	}

	@Override
	public void setQuyCach(double Quycach)
	{

		this.Quycach = Quycach;
	}

	public double getGoiVc()
	{
		return goiVc;
	}

	public void setGoiVc(double goiVc)
	{
		this.goiVc = goiVc;
	}

	public double getGiachuan()
	{
		return giachuan;
	}

	public void setGiachuan(double giachuan)
	{
		this.giachuan = giachuan;
	}

	@Override
	public String getDonviduyetId()
	{
		return donviduyetId;
	}

	@Override
	public void setDonviduyetId(String donviduyetId)
	{
		this.donviduyetId = donviduyetId;
	}
	public float getSoluongle()
	{
		return soluongle;
	}

	public void setSoluongle(float soluongle)
	{
		this.soluongle = soluongle;
	}
}
