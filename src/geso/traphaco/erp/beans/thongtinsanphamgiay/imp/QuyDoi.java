package geso.traphaco.erp.beans.thongtinsanphamgiay.imp;

import geso.traphaco.erp.beans.thongtinsanphamgiay.IQuyDoi;

public class QuyDoi implements IQuyDoi
{
	private String spId = "";
	private String dvdl1_fk = "";
	private String dvdl2_fk = "";
	private double sl1 = 1;
	private double sl2 = 0;
	
	public QuyDoi()
	{
		
	}

	@Override
	public void setSpId(String spId) {
		this.spId = spId;
	}

	@Override
	public String getSpId() {
		return this.spId;
	}

	@Override
	public String getDvdl1() {
		return this.dvdl1_fk;
	}

	@Override
	public void setDvdl1(String dvdl1) {
		this.dvdl1_fk = dvdl1;
	}

	@Override
	public String getDvdl2() {
		return this.dvdl2_fk;
	}

	@Override
	public void setDvdl2(String dvdl2) {
		this.dvdl2_fk = dvdl2;
	}

	@Override
	public double getSl1() {
		return this.sl1;
	}

	@Override
	public void setSl1(double sl1) {
		this.sl1 = sl1;
	}

	@Override
	public double getSl2() {
		return this.sl2;
	}

	@Override
	public void setSl2(double sl2) {
		this.sl2 = sl2;
	}

	@Override
	public void setSl1(String sl1) {
		try { this.sl1 = Double.parseDouble(sl1.trim().replaceAll(",","")); } catch(Exception e) { }
	}

	@Override
	public void setSl2(String sl2) {
		try { this.sl2 = Double.parseDouble(sl2.trim().replaceAll(",","")); } catch(Exception e) { }
	}
}
