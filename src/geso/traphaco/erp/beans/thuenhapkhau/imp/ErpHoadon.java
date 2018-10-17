package geso.traphaco.erp.beans.thuenhapkhau.imp;
import geso.traphaco.erp.beans.thuenhapkhau.IErpHoadon;

public class ErpHoadon implements IErpHoadon
{
	String ID, SOHOADON, NGAYHOADON, SOLUONGTRA;
	
	public ErpHoadon()
	{
		this.ID="";
		this.SOHOADON="";
		this.NGAYHOADON="";
		this.SOLUONGTRA="";
	}
	public ErpHoadon(String id,String sohoadon,String ngayhoadon,String soluongtra )
	{
		this.ID=id;
		this.SOHOADON=sohoadon;
		this.NGAYHOADON=ngayhoadon;
		this.SOLUONGTRA=soluongtra;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getSOHOADON() {
		return SOHOADON;
	}
	public void setSOHOADON(String sOHOADON) {
		SOHOADON = sOHOADON;
	}
	public String getNGAYHOADON() {
		return NGAYHOADON;
	}
	public void setNGAYHOADON(String nGAYHOADON) {
		NGAYHOADON = nGAYHOADON;
	}
	public String getSOLUONGTRA() {
		return SOLUONGTRA;
	}
	public void setSOLUONGTRA(String sOLUONGTRA) {
		SOLUONGTRA = sOLUONGTRA;
	}
}