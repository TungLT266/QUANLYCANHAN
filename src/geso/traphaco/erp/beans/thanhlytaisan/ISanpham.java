package geso.traphaco.erp.beans.thanhlytaisan;

public interface ISanpham
{
	//Khoa chinh(tu dong tang) cua bang Erp_MuaHang_SP
	public String getPK_SEQ();
	public void setPK_SEQ(String pk_seq);
	
	public String getId();

	public void setId(String id);

	public String getMasanpham();

	public void setMasanpham(String masp);

	public String getTensanpham();

	public void setTensanpham(String tensp);
	
	public String getTenXHD();

	public void setTenXHD(String tenXHD);

	public String getSoluong();

	public void setSoluong(String soluong);
	
	public String getSoluong_bk();
	
	public void setSoluong_bk(String soluong);
	
	public String getTiente();

	public void setTiente(String tiente);

	// Them Trung tam chi phi,Thue Nhap Khau

	public String getTrungTamChiPhi_FK();

	public void setTrungTamChiPhi_FK(String TrungTamChiPhi_FK);

	public String getThueNhapKhau();

	public void setThueNhapKhau(String thuenhapkhau);

	public void setTyGiaQuyDoi(float tygiaquydoi);

	public float getTyGiaQuyDoi();

	public void setPhanTramThue(String phantramthue);

	public String getPhanTramThue();

	// Them Trung tam chi phi,Thue Nhap Khau

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

	public String getMNLId(); 

	public void setMNLId(String mnlId); 
	
	public String getChietkhau();	
	public void setChietkhau(String chietkhau);
	public String getSoHD();
	public void setSoHD(String soHD);
	public String getNgayHD();
	public void setNgayHD(String ngayHD);
	
}
