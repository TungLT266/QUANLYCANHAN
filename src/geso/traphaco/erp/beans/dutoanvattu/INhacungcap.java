package geso.traphaco.erp.beans.dutoanvattu;

import geso.traphaco.erp.beans.dutoanvattu.INhacungcap;
import geso.traphaco.erp.beans.dutoanvattu.ISanpham;

import java.io.Serializable;
import java.util.List;

public interface INhacungcap extends Serializable {
	
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getTongtienBvatNT();
	public void setTongtienBvatNT(String bvatNT);
	
	public String getTongtienAvatNT();
	public void setTongtienAvatNT(String avatNT);
	
	public String getTongtienBvat();
	public void setTongtienBvat(String bvat);
	
	public String getTongtienAvat();
	public void setTongtienAvat(String avat);
	
	public String getVat();
	public void setVat(String vat);
	
	public void setSanPham(List<ISanpham> list);
	public List<ISanpham> getSanPham();
	
}