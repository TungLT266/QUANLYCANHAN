package geso.traphaco.distributor.beans.chitieunpp.imp;
import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.chitieunpp.IChitieusku;

public class ChitieuSku implements IChitieusku
{
	String id,tensp,masp,soluong,npp,nppten;
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id=id;
	}

	public String getMasanpham() {
		return this.masp;
	}


	public void setMasanpham(String masp) {
		this.masp=masp;
	}

	
	public String getTensanpham() 
	{	
		return this.tensp;
	}	
	public void setTensanpham(String tensp) {
		this.tensp=tensp;
		
	}
	public String getSoluong() {
		
		return this.soluong;
	}
	public void setSoluong(String soluong) 
	{
		
		this.soluong=soluong;
	}


	public String getNpp() {

		return this.npp;
	}

	public void setNpp(String npp) {
		this.npp=npp;
	}

	public String getNppten() {
		return this.nppten;
	}

	public void setNppten(String nppten) 
	{
		this.nppten=nppten;
	}

}
