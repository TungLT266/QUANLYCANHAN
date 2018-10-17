package geso.traphaco.distributor.beans.hopdong.imp;

import geso.traphaco.distributor.beans.hopdong.IDonhang;

public class Donhang implements IDonhang
{
	private static final long serialVersionUID = 1L;
	
	String id;
	String tongtien;
	String tongtienck;
	String tongvat;
	String tongsauck;
	String tongsauvat;
		
	public Donhang()
	{
		this.id = "";
		this.tongtien = "0";
		this.tongtienck = "0";
		this.tongvat = "0";
		this.tongsauck = "0";
		this.tongsauvat = "0";
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getTongtien() {
		return this.tongtien;
	}

	@Override
	public void setTongtien(String tongtien) {
		this.tongtien = tongtien;
	}

	@Override
	public String getTongCK() {
		return this.tongtienck;
	}

	@Override
	public void setTongCK(String tongtienck) {
		this.tongtienck = tongtienck;
	}

	@Override
	public String getTongVAT() {
		return this.tongvat;
	}

	@Override
	public void setTongVAT(String tongvat) {
		this.tongvat = tongvat;
	}

	@Override
	public String getTongSauCK() {
		return this.tongsauck;
	}

	@Override
	public void setTongSauCK(String tongsauck) {
		this.tongsauck = tongsauck;
	}

	@Override
	public String getTongSauVAT() {
		return this.tongsauvat;
	}

	@Override
	public void setTongSauVAT(String tongsauvat) {
		this.tongsauvat = tongsauvat;
	}

}
