package geso.traphaco.distributor.beans.hoadonphelieu;

public interface IErpHoaDonPL_SP
{
	public String getId();
	public void setId(String id);

	public String getIdSanPham();
	public void setIdSanPham(String idsanpham);

	public String getTenSanPham();
	public void setTenSanPham(String tensanpham);

	public String getMaSanPham();
	public void setMaSanPham(String masanpham);

	public void setSoLuong(String soluong);
	public String getSoLuong();

	public String getDonGia();
	public void setDonGia(String dongia);	
	
	public String getDonGiaDaGiam();
	public void setDonGiaDaGiam(String dongiadagiam);	

	public String getThanhTien();
	public void setThanhTien(String thanhtien);
	
	public void setDonViTinh(String donvitinh);
	public String getDonViTinh();

	public void setDVT_FK(String dvt_fk);
	public String getDVT_FK();

	public String getGhiChu1();
	public void setGhiChu1(String ghichu1);
	public String getGhiChu2();
	public void setGhiChu2(String ghichu2);
	public String getGhiChu3();
	public void setGhiChu3(String ghichu3);
	
	
	public String getTen1();
	public void setTen1(String ten);
	public String getTen2();
	public void setTen2(String ten);
	public String getTen3();
	public void setTen3(String ten);
	
	public int getSoDongSanPham(boolean nuocngoai, int sokytu1dong);

	public String getIn();
	public void setIn(String in);
}
