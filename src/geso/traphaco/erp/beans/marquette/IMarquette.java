package geso.traphaco.erp.beans.marquette;
import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
public interface IMarquette extends Serializable, IPhan_Trang {
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getMarket();
	public void setMarket(String market);
	
	public String getMasp();
	public void setMasp(String masp);
	
	public String getTensp();
	public void setTensp(String tensp);
	
	public String getLoaispId();
	public void setLoaispId(String loaispid);
	
	public String getNhanhangId();
	public void setNhanhangId(String nhanhangid);
	
	public String getChungloaiId();
	public void setChungloaiId(String chungloaiid);
	
	public String getDenngay();
	public void setDenngay(String denngay);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getMsg();
	public void setMsg(String msg);

	public String getSpIds();
	public void setSpIds(String spIds);
	public ResultSet getMkList();
	public void setMkList(ResultSet mkList);
	
	public ResultSet getNhanhangIdRs();
	public void setNhanhangIdRs(ResultSet nhanhangidrs);

	public ResultSet getLoaiSpRs();
	public void setLoaiSpRs(ResultSet loaisprs);
	
	public ResultSet getChungloaiIdRs();
	public void setChungloaiIdRs(ResultSet chungloaiid);
	public String getQuery();
	
	public void setQuery(String query);
	public void Init();
	public void createRs();

	public boolean save();
	public boolean delete();
	public boolean update();
	
	public void DBClose();
	public String getCtyId();
	public void setCtyId(String ctyId);

	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);
	public ResultSet getThongtinmarketList();
	public void setThongtinmarketList(ResultSet mklist);
}
