package geso.traphaco.erp.beans.doiquycach;

import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;

import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IDoiquycach {
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
	
	public void setMa(String Ma);
	public String getMa();
	
	public void setdonvitinh(String donvitinh);
	public String getdonvitinh();
	

	public void setSoluong1(String soluong1);
	
	public String getSoluong1();
	
	public String getDongia1();
	public void setDongia1(String dongia1);
	
	public String getChiphi1();
	public void setChiphi1(String value);
	
	
	public String getChiphikho();
	public void setChiphikho(String value);
	
	
	
	public void setTonggiatri1(String tonggiatri1);

	public String getTonggiatri1();
	
	public void setSpId2(String[] spId2);

	public String[] getSpId2();
	
	public void setSpTen2(String[] spTen2);

	public String[] getSpTen2();
	
	public void setSpMa2(String[] SpMa2);

	public String[] getSpMa2();
	 
	public void setSoluong2(String[] soluong2);
	
	public String[] getDVT2();
	 
	public void setDVT2(String[] dvt2);
	
	
	public String[] getSoluong2();
	
	public String[] getDongia2();
	public void setDongia2(String[] dongia2);
	
	public String[] getPhanbo2();
	
	public void setTonggiatri2(String[] tonggiatri2);

	public String[] getTonggiatri2();

	public void setKhoRs(ResultSet khoRs);
	
	public ResultSet getKhoRs();

	public void setUserId(String userId);
	
	public String getUserId();
	
	public void init();

	public void setmsg(String msg);
	
	public String getmsg();
	
    public boolean save();

	public void Xoa();
	
	public boolean Hoantat();
	
	public void Tinhgiatri1();
	
	public void setSpId2(HttpServletRequest request);
	
	public void setSoluong2(HttpServletRequest request);
	
	public void setDongia2(HttpServletRequest request);
	
	public void setPhanbo2(HttpServletRequest request);

	public void setLspId(String lspId);

	public String getLspId();
	
	public String getTotalLuong();
	public void setTotalLuong(String totalLuong);
	public String getTotalTien();
	public void setTotalTien(String totalTien);

	
	public void DbClose();

	public void Reload_Sp();
	
	public void setghichu(String ghichu);
	public String getghichu();

	public void setTonggiatri2(HttpServletRequest request);
	
	public String getIsKhuvuc();
	public ResultSet getKhuvuc_Lo();
	
	public void setSolo1(String value);
	public String getSolo1();
	
	public void setKhuvucId(String value);
	public String getKhuvucId();
	
	
	
	public void set_xuatkho_doiquycach(String doiquycach);
	public String get_xuatkho_doiquycach();
	
	public void set_xuatkhoId_DQC(String xuatkhoId);
	public String get_xuatkhoId_DQC();
	
 
	public List<ISpDoiquycach> getSpDoiquycachlist();
	public void setSpDoiquycachlist(List<ISpDoiquycach> SpDoiquycachlist);

	
	public List<ISpDetail> getSpDetailList();
	public void setSpDetailList(List<ISpDetail> spDetailList);

	public void createRs();

	public void ReLoad_DonGia();

	public void setSpNhanDoiQuycach(HttpServletRequest request);
	
	
}
