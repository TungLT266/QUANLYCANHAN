package geso.traphaco.erp.beans.tieuchuankiemnghiem.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.tieuchuankiemnghiem.IItemLoader;

public class ItemLoader implements IItemLoader {
	
	String pk_seq;
	String ma;
	String ten;
	String chon;
	String ghiChu;
	String pp_fk;
	List<IItemLoader> ppThuNghiemList;
	
	public ItemLoader() {
		this.pk_seq = "";
		this.ma = "";
		this.ten = "";
		this.chon = "0";
		this.ghiChu = "";
		this.pp_fk = "";
		this.ppThuNghiemList = new ArrayList<IItemLoader>();
	}

	public String getPk_seq() {
		return pk_seq;
	}

	public void setPk_seq(String pk_seq) {
		this.pk_seq = pk_seq;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getChon() {
		return chon;
	}

	public void setChon(String chon) {
		this.chon = chon;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public List<IItemLoader> getPpThuNghiemList() {
		return ppThuNghiemList;
	}

	public void setPpThuNghiemList(List<IItemLoader> ppThuNghiemList) {
		this.ppThuNghiemList = ppThuNghiemList;
	}
}
