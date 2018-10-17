package geso.traphaco.center.beans.donmuahang;

public interface IERP_DonDatHang_SP
{
	public String getId();

	public void setId(String id);

	public String getIdSanPham();

	public void setIdSanPham(String idsanpham);

	public String getTenSanPham();

	public void setTenSanPham(String tensanpham);

	public String getMaSanPham();

	public void setMaSanPham(String masanpham);

	public void setSoLuong(float soluong);

	public float getSoLuong();

	public float getSoluongle();

	public void setSoluongle(float soluong);

	public double getDonGia();

	public void setDonGia(double dongia);

	public void setVAT(double vat);

	public double getVAT();

	public void setChietKhau(double chietkhau);

	public double getChietKhau();

	// get total of product sell
	public double getThanhTien();

	public void setThanhTien(double thanhtien);

	public void setDonViTinh(String donvitinh);

	public String getDonViTinh();

	public void setSoLuongDat(float soluongdat);

	public float getSoLuongDat();

	public void setCtkmId(String ctkmid);

	public String getCtkmId();

	public void setSHEME(String ctkmid);

	public String getSHEME();

	public void setSoluongton(int cheme);

	public int getsoluongton();

	public void setSoluongduyet(float soluongduyet);

	public float getsoluongduyet();

	public double getTrongluong();

	public void setTrongluong(double trongluong);

	public double getThetich();

	public void setThetich(double thetich);

	public double getQuyCach();

	public void setQuyCach(double Quycach);

	public String getKhoTT();

	public void setKhoTT(String KhoTT);

	public String getTenKhoTT();

	public void setTenKhoTT(String TenKhoTT);

	public double getGiachuan();

	public void setGiachuan(double giachuan);

	public String getDonviduyetId();

	public void setDonviduyetId(String donviduyetId);

	public double getGoiVc();

	public void setGoiVc(double goivc);

}
