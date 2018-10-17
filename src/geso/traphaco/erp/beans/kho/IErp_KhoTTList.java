package geso.traphaco.erp.beans.kho;

import java.sql.ResultSet;

public interface IErp_KhoTTList {
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
	public void setQuanlybin(String quanlybin);
	public String getQuanlybin();
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
	public void setRSKhoList(ResultSet rs);
	public ResultSet getRSKhoList();
	public void setRSCongty(ResultSet rs);
	public ResultSet getRSCongty();
	public boolean init();
	public boolean Search();
	public String Delete();
	public void DBClose();
}
