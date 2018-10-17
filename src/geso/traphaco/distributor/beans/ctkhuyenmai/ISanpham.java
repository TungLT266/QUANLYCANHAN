package geso.traphaco.distributor.beans.ctkhuyenmai;

public interface ISanpham 
{
	public void setMasp(String maSp);
	public String getMasp();
	public void setTensp(String tenSp);
	public String getTensp();
	
	public void setSoluongcan(float soluongcan);
	public float getSoluongcan();
	
	public void setDongia(float dongia);
	public float getDongia();
	public void setThanhtien(float thanhtien);
	public float getThanhtien();
	
	public void setSoluongAvaiable(float slAvaiable);
	public float getSoluongAvaiable();
	
	public void setSoluongThungCan(float slThungAvaiable);
	public float getSoluongThungCan();
	public void setSoluongThungAvaiable(float slThungAvaiable);
	public float getSoluongThungAvaiable();
	
}
