package geso.traphaco.erp.beans.cophieu;

import geso.traphaco.center.util.*;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpCoPhieuList extends Serializable, IPhan_Trang
{
	public String getID();

	public String getMA();

	public String getTEN();

	public String getNGAYTAO();

	public String getNGAYSUA();

	public String getNGUOITAO();

	public String getNGUOISUA();

	public String getMsg();

	public String gettrangthai();

	public ResultSet getCpList();

	public void setID(String ID);

	public void setMA(String MA);

	public void setTEN(String TEN);

	public void setNGAYTAO(String NGAYTAO);

	public void setNGAYSUA(String NGAYSUA);

	public void setNGUOITAO(String NGUOITAO);

	public void setNGUOISUA(String NGUOISUA);

	public void setMsg(String Msg);

	public void setTrangthai(String trangthai);

	public void setCpList(ResultSet CpList);

	public void init();
	
	public boolean CheckReferences(String column, String table);

	public boolean DeleteCoPhieu();


	public void setUserid(String user);

	public String getUserid();

	public void setUserTen(String userten);

	public String getUserTen();

	public void setCongtyphathanh(String congtyphathanh);

	public String getCongtyphathanh();
	
	public void DBClose();
}
