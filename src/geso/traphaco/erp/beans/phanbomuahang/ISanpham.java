package geso.traphaco.erp.beans.phanbomuahang;

import java.util.List;

public interface ISanpham
{
	public String getId();
	public void setId(String id);

	public String getMasanpham();
	public void setMasanpham(String masp);

	public String getTensanpham();
	public void setTensanpham(String tensp);

	public String getDonvi();
	public void setDonvi(String dvt);
	
	public String getSolo();
	public void setSolo(String solo);
	
	public String getSoluong();
	public void setSoluong(String soluong);
	
	public String getSoluongpb();
	public void setSoluongpb(String soluongpb);
	
	public String getDongia();
	public void setDongia(String dongia);
	
	public String getThuexuat();
	public void setThuexuat(String thuexuat);
	
	public String getNhomkenh();
	public void setNhomkenh(String nhomkenh);
	
	public List<ICongty> getCongty();
	public void setCongty(List<ICongty> lstCt);
}
