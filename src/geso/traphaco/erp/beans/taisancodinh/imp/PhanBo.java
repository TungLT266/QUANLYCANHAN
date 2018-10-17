package geso.traphaco.erp.beans.taisancodinh.imp;

import geso.traphaco.erp.beans.taisancodinh.IPhanBo;



public class PhanBo implements IPhanBo{

	String taisanid;
	String khoanMucId;
	String phanTram;
	
	
	public  PhanBo() 
	{
			this.taisanid="";;
			this.khoanMucId="";
			this.phanTram="";
	}


	public String getTaisanid() {
		return taisanid;
	}


	public void setTaisanid(String taisanid) {
		this.taisanid = taisanid;
	}


	public String getKhoanMucId() {
		return khoanMucId;
	}


	public void setKhoanMucId(String khoanMucId) {
		this.khoanMucId = khoanMucId;
	}


	public String getPhanTram() {
		return phanTram;
	}


	public void setPhanTram(String phanTram) {
		this.phanTram = phanTram;
	}
}