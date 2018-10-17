package geso.traphaco.erp.beans.bosanpham;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IBosanpham {
	public void setId(String Id);
	
	public String getId();

	public void setCtyId(String ctyId);

	public String getCtyId();

	public void setNgay(String ngay);

	public String getNgay();
	
	public void setKhoId(String khoId);

	public String getKhoId();
	
	public void setSpId1(String spId1);

	public String getSpId1();
	
	public void setSpTen1(String spTen1);

	public String getSpTen1();

	public void setSoluong1(String soluong1);
	
	public String getSoluong1();
	
	public void setTonggiatri1(String tonggiatri1);

	public String getTonggiatri1();
	
	public void setSpId2(String[] spId2);

	public String[] getSpId2();
	
	public void setSpTen2(String[] spTen2);

	public String[] getSpTen2();
	
	public void setSoluong2(String[] soluong2);
	
	public String[] getSoluong2();
	
	public void setTonggiatri2(String[] tonggiatri2);

	public String[] getTonggiatri2();

	public void setKhoRs(ResultSet khoRs);
	
	public ResultSet getKhoRs();

	public void setUserId(String userId);
	
	public String getUserId();
	
	public void init();

	public void setmsg(String msg);
	
	public String getmsg();
	
	public void Xoa();
	
	public boolean Hoantat();
	
	public boolean Luulai();
	
	public void setSpId2(HttpServletRequest request);
	
	public void setSoluong2(HttpServletRequest request);

	public void setLspId(String lspId);

	public String getLspId();
	
	public String getTotalLuong();
	public void setTotalLuong(String totalLuong);
	public String getTotalTien();
	public void setTotalTien(String totalTien);

	public void DbClose();
}
