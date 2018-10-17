package geso.traphaco.erp.beans.hoadonkhacncc;

import java.util.List;

public interface ISanpham
{
	//Khoa chinh(tu dong tang) cua bang Erp_MuaHang_SP
	public String getPK_SEQ();
	public void setPK_SEQ(String pk_seq);
	
	public String getId();

	public void setId(String id);
	
	public String getTaikhoanKTId();
	public void setTaikhoanKTId(String TaikhoanKTId);
	public String getSohieutaikhoan();
	public void setSohieutaikhoan(String Sohieutaikhoan);
	

	public String getMasanpham();

	public void setMasanpham(String masp);

	public String getTensanpham();

	public void setTensanpham(String tensp);
	
	public String getTenXHD();

	public void setTenXHD(String tenXHD);

	public String getSoluong();

	public void setSoluong(String soluong);
	
	public String getSoluongduyet();

	public void setSoluongduyet(String soluongduyet);
	
	public String getSoluongOLD();

	public void setSoluongOLD(String soluongOLD);
	
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
 
	// Them Trung tam chi phi,Thue Nhap Khau

	public String getDonvitinh();

	public void setDonvitinh(String donvitinh);

	public String getDongia();

	public void setDongia(String dongia);
	
	public String getDongiaOLD();

	public void setDongiaOLD(String dongiaOLD);
	
 

	public double getThanhtien();

	public void setThanhtien(double thanhtien);
	
	public double getPhantramthue();
	public void setPhantramthue(double Phantramthue);
	
	public double getTienthue();
	public void setTienthue(double Tienthue);
	
	public double getTiensauthue();
	public void setTiensauthue(double Tiensauthue);
	
 

	public double getThanhtienNguyenTe();

	public void setThanhtienNguyenTe(double thanhtien);
	
	
	
	
	public String getNhomhang();

	public void setNhomhang(String nhomhang);

	 
	public String getKhonhan();

	public void setKhonhan(String khonhan);

	public String getDungsai();

	public void setDungsai(String dungsai);

	public String getMNLId(); 

	public void setMNLId(String mnlId); 


	public String getTenHD(); 
	public void setTenHD(String tenHD);
	
	public String getQuycachsanpham();
	public void setQuycachsanpham(String quycachsanpham);
	
	public String getDvkd();
	public void setDvkd(String Dvkd);
	
	public String getInraHd();
	public void setInraHd(String inraHd);

	public String getQuyDoiStr();
	public void setQuyDoiStr(String quydoi);

	public List<INgaynhan> getNgaynhan();
	public void setNgaynhan(List<INgaynhan> list);
	public void setNgaynhanstr(String string);
	public String getNgaynhanstr();
	
	public List<ISanPhamPhanBo> getSanphamPB();
	public void setSanphamPB(List<ISanPhamPhanBo> list);
	
	public String getIdmarquette();
	public void setIdmarquette(String idmarquette);

	public String getNgaynhandukien() ;
	public void setNgaynhandukien(String ngaynhandukien) ;
}
