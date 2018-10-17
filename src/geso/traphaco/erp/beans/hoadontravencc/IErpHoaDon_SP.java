package geso.traphaco.erp.beans.hoadontravencc;

import geso.traphaco.erp.beans.nhapkhoNK.ISpDetail;

import java.util.List;

public interface IErpHoaDon_SP {
	public String getId();
	public void setId(String id);
	public String getIdSanPham();
	public void setIdSanPham(String idsanpham);
	public String getTenSanPham();
	
	public void setDonhangId(String Donhangid);
	public String getDonhangId();
	
	public void setTenSanPham(String tensanpham);
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
	//get total of product sell
	public double getThanhTien();
	public void setThanhTien(double thanhtien);
	public void setDonViTinh(String donvitinh);
	public String getDonViTinh();
	public void setSoLuongDat(int soluongdat);
	public int getSoLuongDat();
	public void setCTKMID(String ctkmid);
	public String getCTKMId();
	public int getQuyDoi();
	public void setQuyDoi(int quydoi);
	
	public double getGiaNet();
	public void setGiaNet(double gianet);
	
	
	
	public void setDvtId(String dvtId);
	public String getDvtId();
	
	
	public List<ISpDetail> getSpDetail() ;
	public void setSpDetail(List<ISpDetail> spDetail);
	
	
	public double getTienvat() ;
	public void setTienvat(double tienvat) ;
	public double getThanhtienavat();
	public void setThanhtienavat(double thanhtienavat) ;
	
	
	public String getHansudung() ;
	public void setHansudung(String hansudung) ;
	
	
}
