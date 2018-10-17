package geso.traphaco.erp.beans.danhgiatigia;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IDanhgiatigiaList {
	public void setUserId(String userId);
	
	public String getUserId();

	public void setCtyId(String ctyId);
	
	public String getCtyId();

	public void setNam(String nam);
	
	public String getNam();
	
	public void setNamRS(ResultSet namlist);
	
	public ResultSet getNamRS();

	public void setQuyRS(ResultSet quy);
	
	public ResultSet getQuyRS();

	public void setTienteRS(ResultSet tienteRS);
	
	public ResultSet getTienteRS();
	
	public void setGhidao(String ghidao);
	
	public String getGhidao();
	
	public void setNgaychungtu(String ngaychungtu);
	
	public String getNgaychungtu();

	public void setDanhgiaRS(ResultSet dgtigialist);
	
	public ResultSet getDanhgiaRS();

	public void setQuy(String quy);
	
	public String getQuy();
	
	public void setTienteIds(String[] ttIds);
		
	public String[] getTienteIds();
	
	public String[] getTigia();
	
	public void setTigia(String[] tigia);
	
	public void setTigiaHashtable(Hashtable<String, String> ht);
	
	public Hashtable<String, String> getTigiaHashtable();

	public boolean checkTigia();
	
	public void setMsg(String msg);
	
	public String getMsg();
	
	public void setAction(String action);
	
	public String getAction();

	public void setTrangthai(String trangthai);
	
	public String getTrangthai();
	
	public void Chot();
	
	public void init();
	
	public void init_new();
	
	public boolean Save();
	
	public void setDienGiaiCT(String dienGiaiCT);
	public String getDienGiaiCT();
	
	public void DBClose();
}