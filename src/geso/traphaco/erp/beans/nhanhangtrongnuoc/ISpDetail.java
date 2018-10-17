package geso.traphaco.erp.beans.nhanhangtrongnuoc;

import java.util.List;

public interface ISpDetail
{
	public String getMa();
	public void setMa(String masp);
	public String getSolo();
	public void setSolo(String solo);
	// them sothung cua moi lo
	public String getSothung();
	public void setSothung(String sothung);
	
	// so luong tong cua tat ca cac thung trong mot lo
	
	public String getSoluong();
	public void setSoluong(String soluong);
	
	public String getNgaySx();
	public void setNgaySx(String ngaysx);
	public String getNgayHethan();
	public void setNgayHethan(String ngayhh);
	public String getkhuid();
	public void setkhuid(String khuid);
	public String getNgaynhapkho();
	public void setNgaynhapkho(String Ngaynhapkho);
	
	public double getCPLukho();
	public void setCPLukho(double CPLukho);
	
	public double getCapdong();
	public void setCapdong(double Capdong);
	
	public double getDongia();
	public void setDongia(double Dongia);
	
	public String getSoluongmax();
	public void setSoluongmax(String soluongmax);
	
	public String getDongiaLo();
	public void setDongiaLo(String dongialo);
	
	public String getNsxId();
	public void setNsxId(String nsxId);
	public String getNsxTen();
	public void setNsxTen(String nsxTen);
	public String getMarrquet();
	public void setMarrquet(String marrquet);
}
