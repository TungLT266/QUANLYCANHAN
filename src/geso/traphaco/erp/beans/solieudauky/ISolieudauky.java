package geso.traphaco.erp.beans.solieudauky;

public interface ISolieudauky {
	public void setUserId(String userId);
	public String getUserId();
	public void setCheck(String check);
	public String getCheck();
	public void setThang(String thang);
	public String getThang();
	public void setNam(String nam);
	public String getNam();
	public void setNPPid(String nppId);
	public String getNPPid();
	public void setMsg(String msg);
	public String getMsg();
	public void init();
	public boolean TinhDauKy();
	public void DBclose();
}
