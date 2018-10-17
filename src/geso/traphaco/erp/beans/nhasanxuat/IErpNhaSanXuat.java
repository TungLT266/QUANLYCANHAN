package geso.traphaco.erp.beans.nhasanxuat;

import geso.traphaco.center.db.sql.Idbutils;



public interface IErpNhaSanXuat
{
	public String getID();

	public String getMA();

	public String getTEN();

	public String getNGAYTAO();

	public String getNGAYSUA();

	public String getNGUOITAO();

	public String getNGUOISUA();

	public String getMsg();

	public String getTRANGTHAI();

	public void setID(String ID);

	public void setMA(String MA);

	public void setTEN(String TEN);

	public void setNGAYTAO(String NGAYTAO);

	public void setNGAYSUA(String NGAYSUA);

	public void setNGUOITAO(String NGUOITAO);

	public void setNGUOISUA(String NGUOISUA);

	public void setMsg(String Msg);

	public void setTRANGTHAI(String TRANGTHAI);

	public void init();

	public boolean update();

	public void setUserid(String user);

	public String getUserid();

	public void setUserTen(String userten);

	public String getUserTen();

	public boolean CheckUnique(Idbutils db);

	public void DBClose();
	
}
