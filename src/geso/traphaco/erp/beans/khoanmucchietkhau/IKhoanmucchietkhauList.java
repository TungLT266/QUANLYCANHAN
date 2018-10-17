package geso.traphaco.erp.beans.khoanmucchietkhau;

import java.sql.ResultSet;

public interface IKhoanmucchietkhauList {
	public void setMsg(String msg);
	public String getMsg();
	public void setPK_SEQ(String pk_seq);
	public String getPK_SEQ();
	public void setCongty_fk(String congty_fk);
	public String getCongty_fk();
	public void setTen(String ten);
	public String getTen();
	public void setDiachi(String diachi);
	public String getDiachi();
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
	public void setTrangthai(String trangthai);
	public String getTrangthai();
	public void setRsKMCK(ResultSet rs);
	public ResultSet getRsKMCK();
	public boolean init();
	public String Delete();
	public void DBClose();
	
	public void setChixem(String chixem);
	public String getChixem();
	
}
