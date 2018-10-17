package geso.traphaco.erp.beans.parktrongnuoc;
public interface IErpHoadonSp  extends Comparable<IErpHoadonSp>
{
	public String getId();
	public void setId(String Id); 

	public String getPoId();
	public void setPoId(String poId);
	
	public String getPoTen();
	public void setPoTen(String poTen);
	
	public String getSoluong();
	public void setSoluong(String soluong);	
	
	public String getDongia();
	public void setDongia(String dongia);
	
	public String getDonvi();
	public void setDonvi(String donvi);
	
	public String getThanhtien();
	public void setThanhtien(String thanhtien);
	
	public String getSanphamMa(); 
	public void setSanphamMa(String sanphamMa); 
	
	public String getSanphamId(); 
	public void setSanphamId(String sanphamId); 
	
	public String getLoai(); 
	public void setLoai(String loai); 
	
	public String getTen(); 
	public void setTen(String ten); 
	
	public String getDungsai(); 
	public void setDungsai(String dungsai); 
	
	public String getHoantat(); 
	public void setHoantat(String hoantat);
	
	public void setChon(String chon);	
	public String getChon();
	
	public String getVAT();
	public void setVAT(String vat);	
	
	public String getSoluonghd();
	public void setSoluonghd(String soluonghd);
	
	public String getNgaynhan();
	public void setNgaynhan(String ngaynhan);

	public String getSoluongRaHD() ;
	public void setSoluongRaHD(String soluongRaHD);
	
	public String getTienVat(); 
	public void setTienVat(String tienvat); 
	
	public String getSolo(); 
	public void setSolo(String solo); 
}
