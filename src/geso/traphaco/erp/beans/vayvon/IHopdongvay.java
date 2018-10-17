package geso.traphaco.erp.beans.vayvon;

import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

public interface IHopdongvay {
	public void setId(String Id);
	
	public String getId();

	public void setCtyId(String ctyId);

	public String getCtyId();
	
	public void setSoHD(String soHD);

	public String getSoHD();
	
	public void setDiengiai(String diengiai);

	public String getDiengiai();

	public void setNgay(String ngay);

	public String getNgay();
	
	public void setTongtien(String tongtien);

	public String getTongtien();

	public void setTongngoaite(String tongngoaite);

	public String getTongngoaite();
	
	public void setLoai(String loai);

	public String getLoai();

	public void setNgayBD(String ngaybd);

	public String getNgayBD();

	public void setThoihan(String thoihan);

	public String getThoihan();

	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public void setNhId(String nhId);
			
	public String getNhId();

	public void setHDTT(String hdtt);
	
	public String getHDTT();
	
	public void setNhRs(ResultSet nhRs);
	
	public ResultSet getNhRs();
	
	public void setCnId(String cnId);
	
	public String getCnId();

	public void setCnRs(ResultSet cnRs);
	
	public ResultSet getCnRs();

	public void setTTId(String ttId);
	
	public String getTtId();

	public void setNTId(String ntId);
	
	public String getNtId();
	
	public void setTtRs(ResultSet ttRs);
	
	public ResultSet getTtRs1();
	
	public ResultSet getTtRs2();
	
	public void setNtvRs(ResultSet ntvRs);
	
	public ResultSet getNtvRs();
	
	public void setUserId(String userId);
	
	public String getUserId();

	public void init();

	public void setmsg(String msg);
	
	public String getmsg();
	
    public boolean save();

	public void Xoa();
	
	public void Hoantat();
    
	public void DbClose();
	
	public void CreateRs();
	
	public void setSession(HttpSession session);
}
