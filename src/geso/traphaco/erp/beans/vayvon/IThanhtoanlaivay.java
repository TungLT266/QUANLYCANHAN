package geso.traphaco.erp.beans.vayvon;

import java.sql.ResultSet;

public interface IThanhtoanlaivay {
public void setId(String Id);
	
	public String getId();
	
	public void setCtyId(String ctyId);

	public String getCtyId();
	
	public void setTtId(String ttId);

	public String getTtId();

	public void setSoHD(String soHD);

	public String getSoHD();

	public void setNtvId(String ntvId);

	public String getNtvId();

	public void setHinhthuc(String hinhthuc);

	public String getHinhthuc();
	
	public void setNgay(String ngay);

	public String getNgay();
	
	public void setTkCtyId(String tkctyId);

	public String getTkCtyId();
	
	public void setTienlai(String tienlai);

	public String getTienlai();
	
	public void setTiengoc(String tiengoc);

	public String getTiengoc();
	
	public void setTienphat(String tienphat);

	public String getTienphat();
	
	public void setPhikhac(String phikhac);

	public String getPhikhac();

	public void setTiente(String tiente);

	public String getTiente();

	public void setGhichu(String ghichu);

	public String getGhichu();
	
	public void setHDRS(ResultSet HDRS);
	
	public ResultSet getHDRS();
	
	public void setNTVRS(ResultSet NTVRS);
	
	public ResultSet getNTVRS();
	
	public void setTKCTYRS(ResultSet TKCTYRS);
	
	public ResultSet getTKCTYRS();

	public void setTtRs(ResultSet ttRs);
	
	public ResultSet getTtRs();
	
	public void setUserId(String userId);
	
	public String getUserId();	
	
	public void init();

	public void init_RS();
	
	public void setmsg(String msg);
	
	public String getmsg();
	
    public boolean save();

	public void Xoa();
	
	public String Hoantat();
    
	public void DbClose();
}
