package geso.traphaco.erp.beans.lapkehoach;


public interface IErpDinhmuc
{
	public String getId();
	public String getTen();
	public String getDVT();
	public double getSoluong();
	public double getDongia();
	public double getThanhtien();
	public String getLoai();
	public void setId(String value);
	public void setTen(String value);
	public void setDVT(String value);
	public void setSoluong(double value);
	public void setDongia(double value);
	public void setThanhtien(double value);
	public void setLoai(String value);	
}
