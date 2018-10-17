package geso.traphaco.distributor.beans.khotonghop;

public interface IKhotonghop
{
	public void setuserId(String userId);
	public String getuserId();

	public void setuserTen(String userTen);
	public String getuserTen();

	public void setnppId(String nppId);
	public String getnppId();

	public void setThang(String thang);
	public String getThang();

	public void setNam(String nam);
	public String getNam();
	
	public void setTungay(String tungay);
	public String getTungay();

	public void setDenngay(String denngay);
	public String getDenngay();
	
	public void setMsg(String msg);
	public String getMsg();
	public void DBClose();
}
