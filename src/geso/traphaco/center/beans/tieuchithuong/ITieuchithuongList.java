package geso.traphaco.center.beans.tieuchithuong;

import java.sql.ResultSet;

public interface ITieuchithuongList {
	
	public void setDvkdId(String dvkdId);
	
	public String getdvkdId();

	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getdvkd();
	
	public void setKbhId(String kbhId);
	
	public String getkbhId();

	public void setKbh(ResultSet kbh);
	
	public ResultSet getKbh();

	public void setMsg(String msg);
	
	public String getMsg();

	public void setUserId(String userId);
	
	public String getUserId();
	
	public void setLoai(String loai);
	
	public String getLoai();
	
	public void sethethongBH(String htbh);
	public String gethethongBH();
	
	public void setMonth(String month);
	
	public String getMonth();
	
	public void setYear(String year);
	
	public String getYear();
	
	public void setTct(ResultSet tct);
	
	public ResultSet getTct();
	
	public void closeDB();
	
	public void init();
}
