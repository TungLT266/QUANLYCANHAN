package geso.traphaco.erp.beans.kiemkho.imp;

import geso.traphaco.erp.beans.kiemkho.IErpKiemKho_SanPham;

public class ErpKiemKho_SanPham implements IErpKiemKho_SanPham
{
	String DieuChinhTonKho_FK, SanPham_FK, MaSanPham, TenSanPham, SoLuongDieuChinh, TonThucTe, TonHienTai, TonKhoCu,
	Bin, Bin_FK, TrangThai, TrangThai_FK, SoLo, Khu_FK,SoLoMoi,NgayHetHan,GiaTri,Tonkhomoi;
	
	
	String Loaisanpham;
	String Donvi;
	public ErpKiemKho_SanPham()
	{
		DieuChinhTonKho_FK = "";
		SanPham_FK = "";
		SoLuongDieuChinh = "";
		TonThucTe = "";
		TonHienTai = "";
		TonKhoCu = "";
		MaSanPham = "";
		TenSanPham = "";
		Bin = "";
		Bin_FK = "";
		GiaTri="";
		SoLoMoi="";
		NgayHetHan="";
		this.Loaisanpham="";
		this.Donvi="";
		this.Tonkhomoi="";
		
	}
	
	public String getDieuChinhTonKho_FK()
	{
		return this.DieuChinhTonKho_FK;
	}
	
	public void setDieuChinhTonKho_FK(String dieuChinhTonKhoTT_FK)
	{
		this.DieuChinhTonKho_FK = dieuChinhTonKhoTT_FK;
	}
	
	public String getSanPham_FK()
	{
		return this.SanPham_FK;
	}
	
	public void setSanPham_FK(String sanPham_FK)
	{
		this.SanPham_FK = sanPham_FK;
	}
	
	public String getSoLuongDieuChinh()
	{
		return this.SoLuongDieuChinh;
	}
	
	public void setSoLuongDieuChinh(String soLuongDieuChinh)
	{
		 
		
		this.SoLuongDieuChinh =soLuongDieuChinh;
	}
	
	public void setMaSanPham(String MaSanPham)
	{
		this.MaSanPham = MaSanPham;
	}
	
	public String getMaSanPham()
	{
		return this.MaSanPham;
	}
	
	public String getTenSanPham()
	{
		return this.TenSanPham;
	}
	
	public void setTenSanPham(String tensanpham)
	{
		this.TenSanPham = tensanpham;
	}
	
	public String GetTonThucTe()
	{
		return this.TonThucTe;
	}
	
	public void setTonThucTe(String _TonThucTe)
	{
		 
		
		this.TonThucTe =_TonThucTe;
	}
	
	public String getTonHienTai()
	{
		return this.TonHienTai;
	}
	
	public void setTonHienTai(String _TonHienTai)
	{
	 
		this.TonHienTai =_TonHienTai;
	}
	
	public String getTonKhoCu()
	{
		
		return this.TonKhoCu;
	}
	
	public void setTonKhoCu(String _TonKhoCu)
	{ 
		
		this.TonKhoCu =_TonKhoCu;
		
		
	}
	
	public String getKhu_FK()
	{
		
		return this.Khu_FK;
	}
	
	public void setKhu_FK(String Khu_FK)
	{
		this.Khu_FK = Khu_FK;
	}
	
	public String getBin()
	{
		
		return this.Bin;
	}
	
	public void setBin(String bin)
	{
		
		this.Bin = bin;
	}
	
	public String getBin_FK()
	{
		
		return this.Bin_FK;
	}
	
	public void setBin_FK(String bin)
	{
		this.Bin_FK = bin;
		
	}
	
	public String getSoLo()
	{
		
		return this.SoLo;
	}
	
	public void setSoLo(String SoLo)
	{
		this.SoLo = SoLo;
	}
	
	public String getTrangThai()
	{
		
		return this.TrangThai;
	}
	
	public void setTrangThai(String TrangThai)
	{
		this.TrangThai = TrangThai;
	}
	
	public String getTrangThai_FK()
	{
		
		return this.TrangThai_FK;
	}
	
	public void setTrangThai_FK(String TrangThai_FK)
	{
		this.TrangThai_FK = TrangThai_FK;
		
	}

	@Override
	public String getSoLoMoi() {
		// TODO Auto-generated method stub
		return this.SoLoMoi;
	}

	@Override
	public void setSoLoMoi(String SoLoMoi) {
		// TODO Auto-generated method stub
		this.SoLoMoi=SoLoMoi;	
	}

	@Override
	public String getNgayHetHan() {
		// TODO Auto-generated method stub
		return this.NgayHetHan;
	}

	@Override
	public void setNgayHetHan(String _NgayHetHan) {
		// TODO Auto-generated method stub
		this.NgayHetHan=_NgayHetHan;
	}

	@Override
	public String getGiaTri() {
		// TODO Auto-generated method stub
		return this.GiaTri;
	}

	@Override
	public void setGiaTri(String _GiaTri) {
		// TODO Auto-generated method stub
		GiaTri=_GiaTri;
	}

	@Override
	public String getLoaisanpham() {
		// TODO Auto-generated method stub
		return this.Loaisanpham;
	}

	@Override
	public void setLoaisanpham(String loaisanpham) {
		// TODO Auto-generated method stub
		this.Loaisanpham=loaisanpham;
	}

	@Override
	public String getDonvi() {
		// TODO Auto-generated method stub
		return this.Donvi;
	}

	@Override
	public void setDonvi(String donvi) {
		// TODO Auto-generated method stub
		this.Donvi=donvi;
	}

	@Override
	public String getTonKhoMoi() {
		// TODO Auto-generated method stub
		return this.Tonkhomoi;
	}

	@Override
	public void setTonKhoMoi(String TonKhoMoi) {
		// TODO Auto-generated method stub
		this.Tonkhomoi=TonKhoMoi;
	}
	
}
