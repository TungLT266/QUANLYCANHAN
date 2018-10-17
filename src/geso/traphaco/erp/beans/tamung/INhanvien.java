package geso.traphaco.erp.beans.tamung;

import java.sql.ResultSet;

public interface INhanvien {

	public void setMa(String Ma);
	public String getMa();	
	
	public void setCtyId(String ctyId);
	public String getCtyId();

	public void setId(String Id);
	public String getId();

	public void setTaikhoan(String taikhoan);
	public String getTaikhoan();
	
	public void setuserId(String userId);
	public String getuserId();
	
    public void setTen(String Ten);
    public String  getTen();
    
    public void setEmail(String email);
    public String getEmail();
    
    public void setdienthoai(String dienthoai);
    public String getdienthoai();
        
    public void settrangthai(String trangthai);
    public String gettrangthai();
    
	public void setSotaikhoan(String sotaikhoan);
	public String getSotaikhoan();
    
	public void setNhId(String nhId);
	public String getNhId();
	
	public void setCnId(String cnId);
	public String getCnId();
	
	public void setPbId(String pbId);
	public String getPbId();
	
	public void setTkktId(String tkktId);
	public String getTkktId();
	
	public void setNhRs(ResultSet nhRs);	
	public ResultSet getNhRs();
	
	public void setCnRs(ResultSet cnRs);	
	public ResultSet getCnRs();
	
	public void setPbRs(ResultSet pbRs);	
	public ResultSet getPbRs();
	
	public void setTkktRs(ResultSet tkktRs);	
	public ResultSet getTkktRs();

	public void setmsg(String msg);
    public String getmsg();
        
    public boolean save();
    
    public void init();
	public void DbClose();
	
	public void setdiachi(String diachi);
    public String getdiachi();
    
    
    public void setcmnd(String cmnd);
    public String getcmnd();
    
    
    public void setngaycap(String ngaycap);
    public String getngaycap();
    
    
    public void setnoicap(String noicap);
    public String getnoicap();
    
    public void setTkduId(String tkduId);
	public String getTkduId();
	
    public void setTkkqId(String tkkqId);
	public String getTkkqId();
}