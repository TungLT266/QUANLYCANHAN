package geso.traphaco.erp.beans.nhanhang.imp;

import geso.traphaco.erp.beans.nhanhang.ISpLoDetail;

public class SpLoDetail implements ISpLoDetail {

	String solo,mathung;
	String soluong;
	public SpLoDetail() {
		this.solo="";
		this.mathung="";
		this.soluong="";
	}
	public SpLoDetail(String mathung,String soluong)
	{
		
		this.mathung=mathung;
		this.soluong=soluong;
	}
	@Override
	public String getSolo() {
		// TODO Auto-generated method stub
		return this.solo;
	}

	@Override
	public void setSolo(String solo) {
		// TODO Auto-generated method stub
		this.solo=solo;
	}

	@Override
	public String getSoluong() {
		// TODO Auto-generated method stub
		return this.soluong;
	}

	@Override
	public void setSoluong(String soluong) {
		// TODO Auto-generated method stub
		this.soluong=soluong;
	}

	@Override
	public String getMathung() {
		// TODO Auto-generated method stub
		return this.mathung;
	}

	@Override
	public void setMathung(String mathung) {
		// TODO Auto-generated method stub
		this.mathung=mathung;
	}

}
