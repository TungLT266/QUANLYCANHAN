package geso.traphaco.center.beans.huynhaphangtt;

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
	public String getDonvitinh();
	public void setDonvitinh(String donvitinh);
	public String getDongia();
	public void setDongia(String dongia);
	
	public void setSoLo(String solo);
	public String getSoLo();
	
	public void setGiagross(String Giagross);
	public String getGiagross();
	
	public void setGianet(String Gianet);
	public String getGianet();
	
	public void setNgayhethan(String ngayhethan);
	public String getNgayhethan();
	
	public void setISXUATKHO(String isxuatkho);
	public String getISXUATKHO();
	
}
