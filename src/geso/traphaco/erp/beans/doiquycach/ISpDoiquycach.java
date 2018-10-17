package geso.traphaco.erp.beans.doiquycach;

import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;

import java.util.List;

public interface ISpDoiquycach {
 
	public void setSpId1(String spId1);

	public String getSpId1();
	
	public void setSpTen1(String spTen1);
	public String getSpTen1();
	
	public void setMa(String Ma);
	public String getMa();
	
	public void setdonvitinh(String donvitinh);
	public String getdonvitinh();
	public void setreload_sp(String isreload);
	public String getreload_sp();

	public void setSoluong1(double soluong1);
	
	public double getSoluong1();
	
	public double getDongia1();
	public void setDongia1(double dongia1);
	
	public double getChiphi1();
	public void setChiphi1(double value);
   
	public List<ISpDetail> getSpDetailList();
	public void setSpDetailList(List<ISpDetail> spDetailList);
 
	
}
