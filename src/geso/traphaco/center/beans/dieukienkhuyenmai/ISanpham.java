package geso.traphaco.center.beans.dieukienkhuyenmai;

import java.io.Serializable;

public interface ISanpham extends Serializable
{
	public String getId();
	public void setId(String id);
	public String getMasanpham();
	public void setMasanpham(String masp);
	public String getTensanpham();
	public void setTensanpham(String tensp);
	public String getSoluong();
	public void setSoluong(String soluong);
	public String getDongia();
	public void setDongia(String dongia);
	public String getBatbuoc();
	public void setBatbuoc(String batbuoc);
	
	public String getThuongSS();
	public void setThuongSS(String thuongSS);
	public String getThuongTDSS();
	public void setThuongTDSS(String thuongTDSS);
	public String getThuongSR();
	public void setThuongSR(String thuongSR);
	public String getThuongTDSR();
	public void setThuongTDSR(String thuongTDSR);
	public String getThuongASM();
	public void setThuongASM(String thuongASM);
	public String getThuongTDASM();
	public void setThuongTDASM(String thuongTDASM);

	
}
