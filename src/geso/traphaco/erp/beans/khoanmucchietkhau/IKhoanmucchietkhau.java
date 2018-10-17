package geso.traphaco.erp.beans.khoanmucchietkhau;


import java.sql.ResultSet;
import java.util.List;

public interface IKhoanmucchietkhau {
	public void setPK_SEQ(String pk_seq);
	public String getPK_SEQ();
	public void setCongty_fk(String congty_fk);
	public String getCongty_fk();
	public void setTen(String ten);
	public String getTen();
	public void setNguoitao(String nguoitao);
	public String getNguoitao();
	public void setNguoisua(String nguoisua);
	public String getNguoisua();
	public void setNgaytao(String ngaytao);
	public String getNgaytao();
	public void setNgaysua(String ngaysua);
	public String getNgaysua();
	
	public void setMa(String ma);
	public String getMa();
	public void setMsg(String msg);
	public String getMsg();
	public void setTrangthai(String trangthai);
	public String getTrangthai();
	
	public void setLoai(String loai);
	public String getLoai();
	
	
	public void setTaikhoanRs(ResultSet TaikhoanRs);
	public ResultSet getTaikhoanRs();
	
	public void setTaikhoanId(String TaikhoanId);
	public String getTaikhoanId();
	public void setKenhBanHangRs(ResultSet kenhBanHangRs);
	public ResultSet getKenhBanHangRs();
	
	public List<IKhoanmucCK> getChiTietList();
	public void setChiTietList(List<IKhoanmucCK> chiTietList);
	
	public boolean init();
	//public boolean Create();
	public boolean Update();
	public boolean New();
	public void CreateRs();
	public void DBClose();
}