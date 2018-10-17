package geso.traphaco.erp.beans.hoadontrahang;

public interface IErpHdTraHang_SanPham {
	public String getId();

	public void setId(String Id);

	public String getMa();

	public void setMa(String Ma);

	public void setTen(String Ten);

	public String getTen();

	public void setSoLuong(double SoLuong);

	public double getSoLuong();

	public void setDonViTinh(String DonViTinh);

	public String getDonViTinh();

	public void setDVDL(String DVDL);

	public String getDVDL();
	
	public void setDonGia(double DonGia);

	public double getDonGia();

	public void setThanhTien(double ThanhTien);

	public double getThanhTien();
	
	public void setptVat(double ptVat);

	public double getptVat();

}
