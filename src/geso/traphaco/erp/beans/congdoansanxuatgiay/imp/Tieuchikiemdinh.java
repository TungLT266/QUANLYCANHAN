package geso.traphaco.erp.beans.congdoansanxuatgiay.imp;

import geso.traphaco.erp.beans.congdoansanxuatgiay.ITieuchikiemdinh;

public class Tieuchikiemdinh implements ITieuchikiemdinh
{
	String tieuchi;
	String toantu;
	String giatrichuan;
	String diemdat;
	String trangthai;
	String dat;

	public Tieuchikiemdinh()
	{
		this.tieuchi = "";
		this.toantu = "";
		this.giatrichuan = "";
		this.diemdat = "";
		this.trangthai = "";
		this.dat = "0";
	}

	public Tieuchikiemdinh(String tieuchi, String toantu, String giatrichuan)
	{
		this.tieuchi = tieuchi;
		this.toantu = toantu;
		this.giatrichuan = giatrichuan;

		this.diemdat = "";
		this.trangthai = "";
		this.dat = "0";
	}

	public void setTieuchi(String tieuchi)
	{
		this.tieuchi = tieuchi;
	}

	public String getTieuchi()
	{
		return this.tieuchi;
	}

	public void setToantu(String toantu)
	{
		this.toantu = toantu;
	}

	public String getToantu()
	{
		return this.toantu;
	}

	public void setGiatrichuan(String giatrichuan)
	{
		this.giatrichuan = giatrichuan;
	}

	public String getGiatrichuan()
	{
		return this.giatrichuan;
	}

	public void setDiemdat(String diemdat)
	{
		this.diemdat = diemdat;
	}

	public String getDiemdat()
	{
		return this.diemdat;
	}

	public void setTrangthai(String tt)
	{
		this.trangthai = tt;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setDat(String dat)
	{
		this.dat = dat;
	}

	public String getDat()
	{
		return this.dat;
	}

}
