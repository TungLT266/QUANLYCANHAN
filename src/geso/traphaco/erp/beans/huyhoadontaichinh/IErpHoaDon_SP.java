package geso.traphaco.erp.beans.huyhoadontaichinh;

public interface IErpHoaDon_SP
{
	public String getId();
	public void setId(String id);

	public String getIdSanPham();
	public void setIdSanPham(String idsanpham);

	public String getTenSanPham();
	public void setTenSanPham(String tensanpham);

	public String getTen1();
	public void setTen1(String ten);
	public String getTen2();
	public void setTen2(String ten);
	public String getTen3();
	public void setTen3(String ten);

	/**
	 * Thiết lập khóa để xác định các sản phẩm DVKD CORE cùng mã lớn và cùng quy cách dktrong x daulon x dai x daunho x doday phục vụ cho in hóa đơn tài chính 
	 * @param quyCachGroup
	 */
	public void setQuyCachGroup(String quyCachGroup);
	public String getQuyCachGroup();

	public void setDvkdId(String dvkdId);
	public String getDvkdId();

	public String getTenXuatHoaDon();
	public void setTenXuatHoaDon(String tenXuatHD);

	public String getMaSanPham();
	public void setMaSanPham(String masanpham);

	public void setSoLuong(double soluong);
	public double getSoLuong();

	public double getDonGia();
	public void setDonGia(double dongia);
	
	public double getDonGiaViet();
	public void setDonGiaViet(double dongiaViet);

	public void setVAT(double vat);
	public double getVAT();

	public void setChietKhau(double chietkhau);
	public double getChietKhau();

	// get total of product sell
	public double getThanhTien();
	public void setThanhTien(double thanhtien);
	
	public double getThanhTien1();
	public void setThanhTien1(double thanhtien1);

	public void setDonViTinh(String donvitinh);
	public String getDonViTinh();

	public void setDonViTinhEng(String donvitinh);
	public String getDonViTinhEng();

	public void setDonViInAn(String donviInAn);
	public String getDonViInAn();

	public void setSoLuongDat(int soluongdat);
	public int getSoLuongDat();

	public void setCTKMID(String ctkmid);
	public String getCTKMId();

	public int getQuyDoi();
	public void setQuyDoi(int quydoi);

	public String getLoaisp();
	public void setLoaisp(String loaisp);

	public double getGiaNet();
	public void setGiaNet(double gianet);

	public String getKichthuoc();
	public void setKichthuoc(String kichthuoc);

	public String getTrongluong();
	public void setTrongluong(String trongluong);

	public String getQuycach();
	public void setQuycach(String quycach);

	public String getDinhluong();
	public void setDinhluong(String dinhluong);

	public String getQuyDoiStr();
	public void setQuyDoiStr(String quydoi);

	public String getGhiChu1();
	public void setGhiChu1(String ghichu1);
	public String getGhiChu2();
	public void setGhiChu2(String ghichu2);
	public String getGhiChu3();
	public void setGhiChu3(String ghichu3);
	
	public String getSoluongchuan();
	public void setSoluongchuan(String soluongchuan);
	
	
	public String getIn();
	public void setIn(String in);

	public int getSoDongSanPham(boolean nuocngoai, int sokytu1dong);
}