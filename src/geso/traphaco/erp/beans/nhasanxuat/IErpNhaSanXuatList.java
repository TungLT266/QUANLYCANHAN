package geso.traphaco.erp.beans.nhasanxuat;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpNhaSanXuatList extends Serializable, IPhan_Trang
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

	public ResultSet getNsxList();

	public void setID(String ID);

	public void setMA(String MA);

	public void setTEN(String TEN);

	public void setNGAYTAO(String NGAYTAO);

	public void setNGAYSUA(String NGAYSUA);

	public void setNGUOITAO(String NGUOITAO);

	public void setNGUOISUA(String NGUOISUA);

	public void setMsg(String Msg);

	public void setTRANGTHAI(String TRANGTHAI);

	public void setNsxList(ResultSet NsxList);

	public void init();
	
	public boolean deleteNhaSanXuat();

	public void setUserid(String user);

	public String getUserid();

	public void setUserTen(String userten);
	public String getUserTen();
	
	public void DBClose();
}
