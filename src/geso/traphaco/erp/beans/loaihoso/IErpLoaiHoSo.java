package geso.traphaco.erp.beans.loaihoso;

public interface IErpLoaiHoSo {
	public void init();
	public boolean create();
	public boolean update();
	public void DBClose();
	public String getUserId();
	public void setUserId(String userId);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getId();
	public void setId(String id);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public String getMaLoaihoso();
	public void setMaLoaihoso(String maLoaihoso);
	public String getMaBieumau();
	public void setMaBieumau(String maBieumau);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getNgaybanhanh();
	public void setNgaybanhanh(String ngaybanhanh);
	public String getLoaimauin();
	public void setLoaimauin(String loaimauin);
	public String getBieumauPath();
	public void setBieumauPath(String bieumauPath);
	public String getBieumauName();
	public void setBieumauName(String bieumauName);
}
