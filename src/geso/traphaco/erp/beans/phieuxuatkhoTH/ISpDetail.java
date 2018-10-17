package geso.traphaco.erp.beans.phieuxuatkhoTH;

import java.sql.ResultSet;

public interface ISpDetail
{
	// id kho chi tiết nhé.
	public String getKhoChiTietId();
	public void setKhoChiTietId(String khoChiTietId);
	
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
	
	public String getNgaynhapkho();
	public void setNgaynhapkho(String ngaynhapkho);
	
	public String getHamluong();
	public void setHamluong(String hamluong);
	
	public String getHamam();
	public void setHamam(String hamam);
	
	
	
	public void setQuycach(String string);
	public String getQuycach();
	public void setRsKhu(ResultSet resultSet);
	public ResultSet getRsKhu();
	public void setXk_Id(String xkId);
	public String getXk_Id();
	
	public double getDongia();
	public void setDongia(double Dongia);
	
	public String getMathung();
	public void setMathung(String mathung);

	public String getMaphieu();
	public void setMaphieu(String maphieu);
	
	public String getMaphieudinhtinh();
	public void setMaphieudinhtinh(String maphieudinhtinh);
	
	public String getPhieuEO();
	public void setPhieuEO(String PhieuEO);
	
	public String getMame();
	public void setMame(String mame);
	
	public String getIdmarquette();
	public void setIdmarquette(String idmarquette);

	public String getMamarquette();
	public void setMamarquette(String mamarquette);
	
	public String getIdnsx();

	public void setIdnsx(String idnsx);

	public String getTennsx();
	public void setTennsx(String tennsx);
}
