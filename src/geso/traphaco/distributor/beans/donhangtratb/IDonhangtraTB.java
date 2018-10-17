package geso.traphaco.distributor.beans.donhangtratb;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IDonhangtraTB 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getNgaynhap();
	public void setNgaynhap(String ngaynhap);

	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public String getMsg();
	public void setMsg(String msg);
	
	public void setCttbRs(ResultSet cttbRs);
    public ResultSet getCttbRs();
    public String getCttbId();
	public void setCttbId(String diengiai);
	
	public void setKhRs(ResultSet khRs);
    public ResultSet getKhRs();
    public void setKhachhang_Thanhtoan(Hashtable<String, Double> khachhang_thanhtoan);
    public Hashtable<String, Double> getKhachhang_thanhtoan();
	
    public String[] getKhId();
	public void setKhId(String[] khId);
	public String[] getSpId();
	public void setSpId(String[] spId);
	public String[] getKhTen();
	public void setKhTen(String[] khTen);
	public String[] getSpTen();
	public void setSpTen(String[] spTen);
	
	public String[] getSoxuat();
	public void setSoxuat(String[] soxuat);
	public String[] getTotal();
	public void setTotal(String[] total);
	public String[] getThanhtoan();
	public void setThanhtoan(String[] thanhtoan);
    
    public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
    
	public int getSoLanthanhtoan();
	public void setSoLanthanhtoan(int solantt);
	public int getLanthanhtoan();
	public void setLanthanhtoan(int lantt);
	
	public void setThuongDhdautien(ResultSet thuongRs);
    public ResultSet getThuongDhdautien();
	
	public void init();
	public void createRs();
	public void DBClose();
	public boolean update();
	
}
