package geso.traphaco.erp.beans.kiemdinhchatluong;

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
	
	public void setQuycach(String string);
	public String getQuycach();
	public void setRsKhu(ResultSet resultSet);
	public ResultSet getRsKhu();
	public void setXk_Id(String xkId);
	public String getXk_Id();
	
	public double getDongia();
	public void setDongia(double Dongia);
	 
	public void setNgayNhapKho(String ngayNhapKho);
	public String getNgayNhapkho();
	
	
}
