package geso.traphaco.distributor.beans.chinhanhthuho;



public interface IChinhanhthuho
{
	public String getID();

	
	public String getMA();

	
	public String getTEN();
	
	
	
	public String getMsg();

	
	public String gettrangthai();

	
	public void setID(String ID);
	
	public void setMA(String MA);

	
	public void setTEN(String TEN);

	
	public String getUserId();

	public void setUserId(String userId);

	
	public void setTrangthai(String trangthai);

	public void setMsg(String Msg);
	public void init();
	

	public String getNgaytao();

	public void setNgaytao(String ngaytao);

	public String getNgaysua();

	public void setNgaysua(String ngaysua);
	
	public String getSotien();

	public void setSotien(String sotien);
	public String getChinhanh();

	public void setChinhanh(String chinhanh);
	
}
