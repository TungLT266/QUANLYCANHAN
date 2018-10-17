package geso.traphaco.erp.beans.nhapkho;

import java.util.List;

public interface ISanpham 
{
	public String getNhapKhoId();
	public void setNhapKhoId(String nhapkhoId);
	
	public String getId();
	public void setId(String id);
	
	public String getMa();
	public void setMa(String masp);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getSoluongnhan();
	public void setSoluongnhan(String soluongnhan);
	
	public String getSoluongnhapkho();
	public void setSoluongnhapkho(String soluongnhap);
	
	public String getSolo();
	public void setSolo(String solo);
	
	public String getNgayhethan();
	public void setNgayhethan(String ngayhethan);
	
	public String getNgayNhapKho();
	public void setNgayNhapKho(String ngaynhapkho);
	
	public String getNgaySanXuat();
	public void setNgaySanXuat(String ngaysanxuat);
	
	public String getDVT();
	public void setDVT(String dvt);
	
	public String getQuycach();
	public void setQuycach(String quycach);
	
	public String getThung();
	public void setThung(String thung);
	
	public String getLe();
	public void setLe(String le);
	
	public String getTrongluong();
	public void setTrongluong(String trongluong);
	
	public String getThetich();
	public void setThetich(String thetich);
	
	public String getDongia();
	public void setDongia(String dongia);
	
	public String getTiente();
	public void setTiente(String tiente);
	
	public String getDongiaViet();
	public void setDongiaViet(String dongiaViet);
	
	public List<ISanphamCon> getSpConList();
	public void setSpConList(List<ISanphamCon> spConList);
}
