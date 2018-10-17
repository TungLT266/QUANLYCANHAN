package geso.traphaco.erp.beans.thietbikiemnghiem;

public interface IThietBiKiemNghiem {
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

	public String getMa();
	public void setMa(String ma);

	public String getTen();
	public void setTen(String ten);

	public String getGhichu();
	public void setGhichu(String ghichu);
}
