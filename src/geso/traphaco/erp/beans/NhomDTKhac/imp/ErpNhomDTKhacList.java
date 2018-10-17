package geso.traphaco.erp.beans.NhomDTKhac.imp;

import java.io.Serializable;
import java.util.List;

import geso.traphaco.erp.beans.NhomDTKhac.IErpNhomDTKhac;
import geso.traphaco.erp.beans.NhomDTKhac.IErpNhomDTKhacList;
public class ErpNhomDTKhacList implements IErpNhomDTKhacList, Serializable{
	private static final long serialVersionUID = -9217977556733610214L;

	// Tieu chi tim kiem
	private String diengiai;
	private String trangthai;		 
	private String tungay;
	private String denngay;
	private List<IErpNhomDTKhac> Ncclist;
	
	private boolean search = false;

	private String maNcc;
	String chixem;
	
	public ErpNhomDTKhacList(String maKH)
	{
		this.maNcc = maKH;
			
	}
	public ErpNhomDTKhacList(String[] param)
	{
		this.diengiai = param[0];
		this.trangthai = param[1];
		this.tungay = param[2];
		this.denngay = param[3];	
		this.chixem = "0";
	}
	
	public ErpNhomDTKhacList()
	{
		this.maNcc = "";
		this.diengiai = "";
		this.trangthai = "";
		this.tungay = "";
		this.denngay = "";		
		this.chixem = "0";
	}


	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}
	


	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}
	
	public String getTungay(){
		return this.tungay;
	}
	
	public void setTungay(String tungay){
		this.tungay = tungay;
	}

	public String getDenngay(){
		return this.denngay;
	}
	
	public void setDenngay(String denngay){
		this.denngay = denngay;
	}
	

	public List<IErpNhomDTKhac> getNccList(){
		return this.Ncclist;
	}
	
	public void setNccList(List<IErpNhomDTKhac> Ncclist){
		this.Ncclist = Ncclist;
	}
	public boolean getSearch()
	{
		return this.search;
	}

	public void setSearch(boolean search)
	{
		this.search = search;
	}
	public String getMaNcc(){
		return this.maNcc;
	}
	public void setMaNcc(String maNcc)
	{
		this.maNcc = maNcc;
	}
	public void DBClose() {
	}
	
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}

}
