package geso.traphaco.erp.beans.doiquycach.imp;
import geso.traphaco.erp.beans.doiquycach.ISpDoiquycach;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.util.Utility_Kho;

import java.util.ArrayList;
import java.util.List;
 
public class SpDoiquycach implements ISpDoiquycach {
	 
	List<ISpDetail> spDetailList;
	String SpId;
	String SpTen;
	String SpMa;
	String Donvitinh;
	double dongia;
	double chiphi;
	double soluong;
	String Isreloadsp;
	
	Utility_Kho util_kho=new Utility_Kho();
	
	public SpDoiquycach()
	{   
		this.spDetailList = new ArrayList<ISpDetail>();	
	}

	@Override
	public void setSpId1(String spId1) {
		// TODO Auto-generated method stub
		this.SpId=spId1;
	}

	@Override
	public String getSpId1() {
		// TODO Auto-generated method stub
		return this.SpId;
	}

	@Override
	public void setSpTen1(String spTen1) {
		// TODO Auto-generated method stub
		this.SpTen=spTen1;
	}

	@Override
	public String getSpTen1() {
		// TODO Auto-generated method stub
		return this.SpTen;
	}

	@Override
	public void setMa(String Ma) {
		// TODO Auto-generated method stub
		this.SpMa=Ma;
	}

	@Override
	public String getMa() {
		// TODO Auto-generated method stub
		return this.SpMa;
	}

	@Override
	public void setdonvitinh(String donvitinh) {
		// TODO Auto-generated method stub
		this.Donvitinh=donvitinh;
	}

	@Override
	public String getdonvitinh() {
		// TODO Auto-generated method stub
		return this.Donvitinh;
	}
 
	@Override
	public List<ISpDetail> getSpDetailList() {
		// TODO Auto-generated method stub
		return this.spDetailList;
	}

	@Override
	public void setSpDetailList(List<ISpDetail> spDetailList) {
		// TODO Auto-generated method stub
		this.spDetailList=spDetailList;
	}

	@Override
	public void setSoluong1(double soluong1) {
		// TODO Auto-generated method stub
		this.soluong=soluong1;
	}

	@Override
	public double getSoluong1() {
		// TODO Auto-generated method stub
		return this.soluong;
	}

	@Override
	public double getDongia1() {
		// TODO Auto-generated method stub
		return this.dongia;
	}

	@Override
	public void setDongia1(double dongia1) {
		// TODO Auto-generated method stub
		this.dongia=dongia1;
	}

	@Override
	public double getChiphi1() {
		// TODO Auto-generated method stub
		return this.chiphi;
	}

	@Override
	public void setChiphi1(double chiphi) {
		// TODO Auto-generated method stub
		this.chiphi = chiphi; 
	}

	@Override
	public void setreload_sp(String isreload) {
		// TODO Auto-generated method stub
		this.Isreloadsp=isreload;
	}

	@Override
	public String getreload_sp() {
		// TODO Auto-generated method stub
		return this.Isreloadsp;
	}
	 
}
