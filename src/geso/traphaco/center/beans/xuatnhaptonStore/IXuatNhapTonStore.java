package geso.traphaco.center.beans.xuatnhaptonStore;

import java.sql.ResultSet;

public interface IXuatNhapTonStore
{
	public ResultSet getNppRs();

	public void setNppRs(ResultSet nppRs);

	public ResultSet getGsbhRs();

	public void setGsbhRs(ResultSet gsbhRs);

	public ResultSet getKbhRs();

	public String getVungId();

	public void setVungId(String vungId);

	public String getKhuvucId();

	public void setKhuvucId(String khuvucId);

	public ResultSet getVungRs();

	public void setVungRs(ResultSet vungRs);

	public ResultSet getKhuvucRs();

	public void setKhuvucRs(ResultSet khuvucRs);

	public void setKbhRs(ResultSet kbhRs);

	public ResultSet getDvkdRs();

	public void setDvkdRs(ResultSet dvkdRs);

	public ResultSet getChungloaiRs();

	public void setChungloaiRs(ResultSet chungloaiRs);

	public ResultSet getNhanhangRs();

	public void setNhanhangRs(ResultSet nhanhangRs);

	public ResultSet getSpRs();

	public void setSpRs(ResultSet spRs);

	public String getUserId();

	public void setUserId(String userId);

	public String getNppId();

	public void setNppId(String nppId);

	public String getGsbhId();

	public void setGsbhId(String gsbhId);

	public String getDvkdId();

	public void setDvkdId(String dvkdId);

	public String getKbhId();

	public void setKbhId(String kbhId);

	public String getNhanhangId();

	public void setNhanhangId(String nhanhangId);

	public String getChungloaiId();

	public void setChungloaiId(String chungloaiId);

	public String getFromMonth();

	public void setFromMonth(String fromMonth);

	public String getToMonth();

	public void setToMonth(String toMonth);

	public String getFromYear();

	public void setFromYear(String fromYear);

	public String getToYear();

	public void setToYear(String toYear);

	public String getMsg();

	public void setMsg(String msg);

	public String getSpId();

	public void setSpId(String spId);

	public void createRs();

	public void closeDB();

}
