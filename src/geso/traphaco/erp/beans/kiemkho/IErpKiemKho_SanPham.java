/* Author tai bui 21-08-2012 */
package geso.traphaco.erp.beans.kiemkho;

public interface IErpKiemKho_SanPham
{
	public String getDieuChinhTonKho_FK();
	
	public void setDieuChinhTonKho_FK(String dieuChinhTonKhoTT_FK);
	
	public String getSanPham_FK();
	
	public void setSanPham_FK(String sanPham_FK);
	//so luong kiem kho
	public String getSoLuongDieuChinh();
	
	public void setSoLuongDieuChinh(String soluongdieuchinh);
	
	public String getTonHienTai();
	
	public void setTonHienTai(String TonHienTai);
	
	public String getTonKhoCu();
	
	public void setTonKhoCu(String TonKhoCu);
	
	public String getTonKhoMoi();
	
	public void setTonKhoMoi(String TonKhoMoi);
	
	public void setMaSanPham(String MaSanPham);
	
	public String getMaSanPham();
	
	public String getTenSanPham();
	
	public void setTenSanPham(String tensanpham);
	
	public String GetTonThucTe();
	
	public void setTonThucTe(String TonThucTe);
	
	public String getKhu_FK();
	
	public void setKhu_FK(String Khu_FK);
	
	public String getBin();
	
	public void setBin(String bin);
	
	public String getSoLo();
	
	public void setSoLo(String SoLo);
	
	public String getTrangThai();
	
	public void setTrangThai(String TrangThai);
	
	public String getTrangThai_FK();
	
	public void setTrangThai_FK(String TrangThai_FK);
	
	public String getBin_FK();
	
	public void setBin_FK(String bin);
	
	
	public String getSoLoMoi();
	public void setSoLoMoi(String SoLoMoi);
	
	public String getGiaTri();
	public void setGiaTri(String GiaTri);
	
	public String getLoaisanpham();
	public void setLoaisanpham(String loaisanpham);
	public String getDonvi();
	public void setDonvi(String donvi);

	
	public String getNgayHetHan();
	public void setNgayHetHan(String NgayHetHan);
}
