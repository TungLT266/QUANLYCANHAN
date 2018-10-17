package geso.traphaco.erp.beans.donbanhang;

public interface IErp_Donbanhang_SP
{
	public String getId();
	public void setId(String id);
	public String getIdSanPham();
	public void setIdSanPham(String idsanpham);
	public String getTenSanPham();
	public void setTenSanPham(String tensanpham);
	
	public String getTenXuatHoaDon();
	public void setTenXuatHoaDon(String tensanpham);
	
	public String getMaSanPham();
	public void setMaSanPham(String masanpham);
	public void setSoLuong(double soluong);
	public double getSoLuong();
	public  double getDonGia();
	public void setDonGia(double dongia);
	public void setVAT(double vat);
	public double getVAT();
	public void setChietKhau(double chietkhau);
	public double getChietKhau();
	
	public void setSoLuongChuan(double soluongChuan);
	public double getSoLuongChuan();
	
	//get total of product sell
	public double getThanhTien();
	public void setThanhTien(double thanhtien);
	public void setDonViTinh(String donvitinh);
	public String getDonViTinh();
	public void setSoLuongDat(double soluongdat);
	public double getSoLuongDat();
	public void setCTKMID(String ctkmid);
	public String getCTKMId();
	public void setSHEME(String ctkmid);
	public String getSHEME();
	public void setSoluongton(double cheme);
	public double getsoluongton();
	public String getTrongluong();
	public void setTrongluong(String trongluong);
	public String getThetich();
	public void setThetich(String thetich);
	
	public String getNgaydukiengiao();
	public void setNgaydukiengiao(String ngaydukiengiao);
	

	
	public String getGhichusp();
	public void setGhichusp(String ghichusp);
	
	public String getQuycach();
	public void setQuycach(String quycach);
	
	public String getGrossWeight();
	public void setGrossWeight(String grossweight);

	public String getSoPallet();
	public void setSoPallet(String sopallet);
	
	public String getNetWeight();
	public void setNetWeight(String netweight);
	
	public String getDonviEng();
	public void setDonviEng(String dvEng);
	
	
	public String getWeight();
	public void setWeight(String weight);
	
	public String getKhoid();
	public void setKhoid(String khoid);
	
	public String get_DongiaCK();
	public void set_DongiaCK(String dongiaCK);
	
}
