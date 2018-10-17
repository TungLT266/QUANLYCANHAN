package geso.traphaco.center.beans.tieuchithuong;

import java.sql.ResultSet;

public interface IDuyetsuungho 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	
	public String getLoaict();
	public void setLoaict(String loaict);
	
	public String getThang();
	public void setThang(String thang);
	public String getQuy();
	public void setQuy(String quy);
	public String getNam();
	public void setNam(String nam);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getLanduyet();
	public void setLanduyet(String landuyet);
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	
	public String getMsg();
	public void setMsg(String msg);

	public void setKhRs(ResultSet khRs);
    public ResultSet getKhRs();
    public String getKhIds();
	public void setKhIds(String khIds);
	
	public void setNppRs(ResultSet nppRs);
    public ResultSet getNppRs();
    public String getNppIds();
	public void setNppIds(String nppIds);
	
	public void setVungRs(ResultSet vungRs);
    public ResultSet getVungRs();
    public String getVungIds();
	public void setVungIds(String vungIds);
	
	public void setKhuvucRs(ResultSet kvRs);
    public ResultSet getKhuvucRs();
    public String getKhuvucIds();
	public void setKhuvucIds(String kvIds);

	public void init();
	public void createRs();
	public int getSl() ;

	public void setSl(int sl);

	
	public boolean createTctSKU();
	public boolean updateTctSKU();
    
}
