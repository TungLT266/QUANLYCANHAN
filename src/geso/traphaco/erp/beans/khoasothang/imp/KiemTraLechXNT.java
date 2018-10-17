package geso.traphaco.erp.beans.khoasothang.imp;

import geso.traphaco.erp.beans.khoasothang.*;

public class KiemTraLechXNT implements IKiemTraLechXNT
{
	String spid;
	String spma;
	String spten;
	String kho;
	String doituong;
	String solo;
	double soluong;
	double soluong_xnt;// tính ra từ câu lệnh
	
	public KiemTraLechXNT()
	{
		this.spid = "";
		this.spma = "";
		this.spten = "";
		this.kho = "";
		this.doituong = "";
		this.solo = "";
		this.soluong = 0;
		this.soluong_xnt=0;
	}
	
	public KiemTraLechXNT(String spid, String spma, String spten, String kho, String doituong, String solo, double soluong)
	{
		this.spid = spid;
		this.spma = spma;
		this.spten = spten;
		this.kho = kho;
		this.doituong = doituong;
		this.solo = solo;
		this.soluong = soluong;
	}

	
	@Override
	public String getSPID() {
		return this.spid;
	}

	@Override
	public void setSPID(String spid) {
		this.spid = spid;
	}

	@Override
	public String getSPMA() {
		return this.spma;
	}

	@Override
	public void setSPMA(String spma) {
		this.spma = spma;
	}

	@Override
	public String getSPTEN() {
		return this.spten;
	}

	@Override
	public void setSPTEN(String spten) {
		this.spten = spten;
	}

	@Override
	public String getKHO() {
		return this.kho;
	}

	@Override
	public void setKHO(String kho) {
		this.kho = kho;
	}

	@Override
	public String getDOITUONG() {
		return this.doituong;
	}

	@Override
	public void setDOITUONG(String doituong) {
		this.doituong = doituong;
	}

	@Override
	public String getSOLO() {
		return this.solo;
	}

	@Override
	public void setSOLO(String solo) {
		this.solo = solo;
	}

	@Override
	public double getSOLUONG() {
		return this.soluong;
	}

	@Override
	public void setSOLUONG(double soluong) {
		this.soluong = soluong;
	}

	@Override
	public double getSOLUONG_XNT() {
		// TODO Auto-generated method stub
		return this.soluong_xnt;
	}

	@Override
	public void setSOLUONG_XNT(double soluongxnt) {
		// TODO Auto-generated method stub
		 this.soluong_xnt=soluongxnt ;
	}
}
