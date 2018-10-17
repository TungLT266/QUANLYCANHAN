package geso.traphaco.erp.beans.phanbomuahang;

import java.util.List;

public interface ICongty
{
	public String getId();
	public void setId(String id);

	public String getMacongty();
	public void setMacongty(String masp);

	public String getTencongty();
	public void setTencongty(String tensp);
	
	public double getTongtien();
	public void setTongtien(double tongtien);

	public String getSoluongpb();
	public void setSoluongpb(String soluongpb);
	
	public List<ISanpham> getSanphamList();
	public void setSanphamList(List<ISanpham> ctlist);
}
