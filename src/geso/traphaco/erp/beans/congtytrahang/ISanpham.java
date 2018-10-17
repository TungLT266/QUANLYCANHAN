package geso.traphaco.erp.beans.congtytrahang;

import java.util.List;

public interface ISanpham
{
	public String getId();
	public void setId(String id);
	public String getMasanpham();
	public void setMasanpham(String masp);
	public String getTensanpham();
	public void setTensanpham(String tensp);
	
	public String getSoluong();
	public void setSoluong(String soluong);
	public String getTiente();
	public void setTiente(String tiente);
	public String getDonvitinh();
	public void setDonvitinh(String donvitinh);
	public String getDongia();
	public void setDongia(String dongia);
	
	public String getThanhtien();
	public void setThanhtien(String thanhtien);	
	public String getNhomhang();
	public void setNhomhang(String nhomhang);
	
	public String getNgaynhan();
	public void setNgaynhan(String ngaynhan);
	public String getKhonhan();
	public void setKhonhan(String khonhan);
	
	public String getDungsai();
	public void setDungsai(String dungsai);
	
	public List<ISanpham> getListSanPham();
	public void setListSanPham(List<ISanpham> spList);
	
}
