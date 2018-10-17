package geso.traphaco.center.beans.banggiablkh.imp;

import geso.traphaco.center.beans.banggiablkh.IGiaKh;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GiaKh implements IGiaKh
{
	private static final long serialVersionUID = -9217977546733610214L;
	String khId;
	String khTen;
	String giaban;
	String makh;
	
	public GiaKh()
	{	
		this.khId = "";
		this.khTen = "";
		this.giaban = "0";
		this.makh = "";
	}

	
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}



	@Override
	public String getKhId() {
		// TODO Auto-generated method stub
		return this.khId;
	}



	@Override
	public void setKhId(String id) {
		this.khId = id;
	}



	@Override
	public String getKhTen() {
		return this.khTen;
	}



	@Override
	public void setKhTen(String ten) {
		this.khTen = ten;
	}



	@Override
	public void setGiaban(String giaban) {
		this.giaban = giaban;
	}



	@Override
	public String getGiaban() {
		// TODO Auto-generated method stub
		return this.giaban;
	}



	@Override
	public String getKhMa() {
		// TODO Auto-generated method stub
		return this.makh;
	}



	@Override
	public void setKhMa(String ma) {
		this.makh = ma;
	}
	
}