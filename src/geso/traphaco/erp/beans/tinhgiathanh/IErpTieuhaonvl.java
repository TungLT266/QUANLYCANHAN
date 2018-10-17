package geso.traphaco.erp.beans.tinhgiathanh;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;

import java.sql.ResultSet;
import java.util.List;

public interface IErpTieuhaonvl 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	public String getSpId();
	public void setSpId(String spId);
	
	public String getSoluongchuan();
	public void setSoluongchuan(String slgchuan);
	
	public String getChophepTT();
	public void setChophepTT(String chophepTT);
	
	public void setListDanhMuc(List<IDanhmucvattu_SP> list);
	public List<IDanhmucvattu_SP> getListDanhMuc();
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createBom();
	public boolean updateBom();
	
	public void createRs();
	public void init();
	
	public void DbClose();
	
}
