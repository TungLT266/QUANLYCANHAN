package geso.traphaco.center.beans.duyettrakhuyenmai;

import java.sql.ResultSet;

public interface IDuyettrakhuyenmai 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	
	public void setCtkmRs(ResultSet ctkmRs);
    public ResultSet getCtkmRs();
	public String getCtkmId();
	public void setCtkmId(String ctkmId);
	
	public String getNgayduyet();
	public void setNgayduyet(String ngayduyet);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void setKhachhangRs(ResultSet khRs);
    public ResultSet getKhachhangRs();
    //public String getKhId();
	//public void setKhId(String khId);
	
	
	//Xuat ra dinh dang gan giong nhu BC
	public String[] getNppId();
	public void setNppId(String[] nppId);
	public String[] getNppTen();
	public void setNppTen(String[] nppTen);
	public String[] getKhId();
	public void setKhId(String[] khId);
	public String[] getKhTen();
	public void setKhTen(String[] khTen);
	public String[] getDoanhso();
	public void setDoanhso(String[] doanhso);
	public String[] getDat();
	public void setDat(String[] dat);
	public String[] getSoxuat();
	public void setSoxuat(String[] soxuat);
	public String[] getTongtien();
	public void setTongtien(String[] tongtien);
	public String[] getDonvithuong();
	public void setDonvithuong(String[] donvithuong);
	
 
	public void init();
	public void createRs();
	
	public boolean createTctSKU();
	public boolean updateTctSKU();

}
