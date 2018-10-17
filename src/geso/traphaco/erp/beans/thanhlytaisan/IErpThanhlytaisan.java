package geso.traphaco.erp.beans.thanhlytaisan;

import java.util.List;

public interface IErpThanhlytaisan
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	// Them 28-08-2012
	public void setTienTe_FK(String tiente_fk);
	public String getTienTe_FK();

	public void setTyGiaQuyDoi(float tygiaquydoi);
	public Float GetTyGiaQuyDoi();

	public String getGhiChu();
	public void setGhiChu(String ghichu);

	public String getId();
	public void setId(String id);

	public String getSochungtu();
	public void setSochungtu(String sochungtu);

	public String getNgay();
	public void setNgay(String ngay);

	public String getKH();
	public void setKH(String kh);
	public String getKhTen();
	public void setKhTen(String khTen);
	public String getNguoiMuaHang();
	public void setNguoiMuaHang(String nguoimuahang);
	
	public String getDonVi();
	public void setDonVi(String donvi);
	
	public String getDiaChi();
	public void setDiaChi(String diachi);
	
	public String getMST();
	public void setMST(String masothue);
	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getTongtienchuaVat();
	public void setTongtienchuaVat(String ttchuavat);

	public String getVat();
	public void setVat(String vat);

	public String getTongtiensauVat();
	public void setTongtiensauVat(String ttsauvat);

	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);

	public List<IDonvi> getDvList();
	public void setDvList(List<IDonvi> dvList);

	public String getLoai();

	public void setLoai(String loai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void createRs();
	public void init();
	public boolean createDtlts();
	public boolean updateDtlts();
	
	public boolean hoadonDtlts();
	
	//Xuat hoa don
	public String getNgayhoadon();
	public void setNgayhoadon(String ngayhoadon);
	public String getKyhieuhoadon();
	public void setKyhieuhoadon(String kyhieuhd);
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	

	public void close();
}
