package geso.traphaco.erp.beans.dinhtinheo;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IDinhTinhEOList extends Serializable, IPhan_Trang {
	public String getUserId();
	public void setUserId(String userId);
	public String getMsg() ;
	public void setMsg(String msg);
	public String getSophieu();
	public void setSophieu(String sophieu);
	public String getTrangthai();
	public void setTrangthai(String trangthai) ;
	public String getLoai();
	public void setLoai(String loai);
	public String getKhoId();
	public void setKhoId(String khoId);
	public ResultSet getRsKho();
	public void setRsKho(ResultSet rsKho);
	public int getNum();
	public void setNum(int num);
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getCurrentPages();
	public void setCurrentPages(int currentPages);
	public ResultSet getRsDinhTinhEO();
	public void setRsDinhTinhEO(ResultSet rsDinhTinhEO);
	public dbutils getDb();
	public void setDb(dbutils db) ;
	public void init(String query);
	public void DBclose();
	
	public String getSolo() ;
	public void setSolo(String solo) ;
}
