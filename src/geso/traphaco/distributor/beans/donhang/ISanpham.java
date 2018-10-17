package geso.traphaco.distributor.beans.donhang;

import java.io.Serializable;

public interface ISanpham extends Serializable
{
	public String getId();
	public void setId(String id);
	public String getMasanpham();
	
	public void setSoluong1(String soluong1);
	public String getSoluong1();
	public void setSoluong2(String soluong2);
	public String getSoluong2();
	public void setQuicach(String quicach);
	public String getQuicach();
	
	public void setMasanpham(String masp);
	public String getTensanpham();
	public void setTensanpham(String tensp);
	public String getTonhientai();
	public void setTonhientai(String tonhientai);
	
	public String getSoluongThung();
	public void setSoluongThung(String soluongThung);
	
	public String getSoluong();
	public void setSoluong(String soluong);
	public String getDonvitinh();
	public void setDonvitinh(String donvitinh);
	public String getDongia();
	public void setDongia(String dongia);
	public String getCTKM();
	public void setCTKM(String ctkm);	
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getBarcode();
	public void setBarcode(String barcode);
	public void setSPId(String spid);
	public String getSPId();
	public String getDongiaGoc();
	public void setDongiaGoc(String dongiaGoc);
	

	public void setNgayHetHan(String ngayhethan);
	public String getNgayHetHan();
	
	public void setKhoTen(String KhoTen);
	public String getKhoTen();
	
	public void setKenhTen(String KenhTen);
	public String getKenhTen();
	
	public void setKhoNVBH(String khoNVBH);
	public String getKhoNVBH();
	
	public String getVat();
	public void setVat(String vat);
	
	public void setSolo(String solo);
	public String getSOLo();
	public String getDongiaold() ;

	public void setDongiaold(String dongiaold) ;

	
}
