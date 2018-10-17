package geso.traphaco.erp.beans.phieuthanhtoan;


public interface IHoadon
{
	//Khoa chinh(tu dong tang) cua bang Erp_MuaHang_SP
	public String getPK_SEQ();
	public void setPK_SEQ(String pk_seq);
 
	//pk_seq của bảng muahang
	
	public String getMuahang_Sp_fk();
	public void setMuahang_Sp_fk(String Muahang_Sp_fk);

	public void setMahoadon(String str);
	public String getMahoadon();
	
	public void setMauhoadon(String str);
	public String getMauhoadon();
	
	public void setSohoadon(String str);
	public String getSohoadon();
	
	public void setNgayhoadon(String str);
	public String getNgayhoadon();

	public void setTenNCC(String str);
	public String getTenNCC();
	
	public void setMasothue(String str);
	public String getMasothue();
	
	public void setKyhieu(String str);
	public String getKyhieu();

	public void setTienhang(String str);
	public String getTienhang();
	
	public void setThuesuat(String str);
	public String getThuesuat();
	
	public void setTienthue(String str);
	public String getTienthue();
	
	public void setCong(String str);
	public String getCong();
	
	public void setGhichu(String str);
	public String getGhichu();
	
	public String getNccHDId();
	public void setNccHDId(String nccHDId);

	public String getKenhBhId();
	public void setKenhBhId(String KenhBhId);
	
	public String getKenhBhTen();
	public void setKenhBhTen(String KenhBhTen);
	
	public String getPhongBanId();
	public void setPhongBanId(String PhongBanId);
	
	public String getPhongBanTen();
	public void setPhongBanTen(String PhongBanTen);
	
	public String getSanPhamId();
	public void setSanPhamId(String SanPhamId);
	
	public String getDiaBanId();
	public void setDiaBanId(String DiaBanId);
	
	public String getBenhVienId();
	public void setBenhVienId(String BenhvienId);
	
	public void setTienThanhToan(String tienThanhToan);
	public String getTienThanhToan();

	public void setDiaChiNCC(String diaChiNCC);
	public String getDiaChiNCC();
}