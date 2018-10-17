package geso.traphaco.erp.beans.phieuxuatkho;

import java.sql.ResultSet;

public interface ISpDetail
{
	public String getId();
	public void setId(String id);

	public String getMa();
	public void setMa(String masp);

	public String getTen();
	public void setTen(String ten);
	
	
	
	public String getDvt();
	public void setDvt(String dvt);
	
	public double getHaoHut();
	public void setHaoHut(double haohut);

	public String getSolo();

	public void setSolo(String solo);

	public String getSoluong();

	public void setSoluong(String soluong);
	
	public String getSoluongton();

	public void setSoluongton(String soluongton);

	public String getKhu();
	public void setKhu(String khu);
	
	public String getKhuId();
	public void setKhuId(String khuId);

	public String getVitri();

	public void setVitri(String vitri);

	public String getVitriId();

	public void setVitriId(String vitriId);
	
	public double getTrongLuong();
	public void setTrongLuong(double trongluong);
	
	public double getTheTich();
	public void setTheTich(double thetich);
	
	public String getNgaysanxuat();
	public void setNgaysanxuat(String ngaysanxuat);
	
	public String getNgayhethan();
	public void setNgayhethan(String ngayhethan);
	
	public String getNgaybatdau();
	public void setNgaybatdau(String ngaybatdau);
	
	public String getHamluong();
	public void setHamluong(String Hamluong);
	
	public String getHamam();
	public void setHamam(String Hamam);
	
	public void setQuycach(String string);
	public String getQuycach();
	public void setRsKhu(ResultSet resultSet);
	public ResultSet getRsKhu();
	public void setXk_Id(String xkId);
	public String getXk_Id();
	
	public double getDongia();
	public void setDongia(double Dongia);
	

	public String getMarq();
	public void setMarq(String Marq);

	public String getMathung();
	public void setMathung(String mathung);

	public String getNgaynhapkho();
	public void setNgaynhapkho(String Ngaynhapkho);

	
	
	public String getMame();
	public void setMame(String mame);
	
	public void setMaphieu(String maphieu);
	public String getMaphieu();
	public void setPHIEUEO(String PHIEUEO);
	public String getPHIEUEO();
	public void setMAPHIEUDINHTINH(String MAPHIEUDINHTINH);
	public String getMAPHIEUDINHTINH();
	
	
	public String getMaNSX();
	public void setMaNSX(String MaNSX);

	public String getNSX_FK();
	public void setNSX_FK(String NSX_FK);
	
	
}
