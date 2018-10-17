package geso.traphaco.erp.beans.kiemnghiemchitiet.imp;

import java.util.ArrayList;
import java.util.List;

public class PhuongPhap {
	private List<PhieuKiemDinh> dsPhieuKiemDinhs;
	private int __sizePhuongPhap;
	private String dienGiai;
	private String tenPhuongPhap;
	private int sott;
	private String id;
	
	
	public PhuongPhap() {
		super();
		// TODO Auto-generated constructor stub
		this.__sizePhuongPhap = 0;
		this.tenPhuongPhap = "";
		this.dienGiai = "";
		this.sott = 0;
		this.id = "";
		this.dsPhieuKiemDinhs = new ArrayList<PhieuKiemDinh>();
	}
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public int getSott() {
		return sott;
	}



	public void setSott(int sott) {
		this.sott = sott;
	}



	public static String _null(String s){
		if(s == null) return "";
		return s;
	}
	
	public static String _isNumeric(String s){
		if(s == null) return "0";
		if (s.trim().length() == 0) return  "0";
		return s.replaceAll(",", "");
	}
	
	public String getTenPhuongPhap() {
		return tenPhuongPhap;
	}

	public void setTenPhuongPhap(String tenPhuongPhap) {
		this.tenPhuongPhap = tenPhuongPhap;
	}

	public List<PhieuKiemDinh> getDsPhieuKiemDinhs() {
		return dsPhieuKiemDinhs;
	}
	public void setDsPhieuKiemDinhs(List<PhieuKiemDinh> dsPhieuKiemDinhs) {
		this.dsPhieuKiemDinhs = dsPhieuKiemDinhs;
	}
	public int get__sizePhuongPhap() {
		return __sizePhuongPhap;
	}
	public void set__sizePhuongPhap(int __sizePhuongPhap) {
		this.__sizePhuongPhap = __sizePhuongPhap;
	}
	public String getDienGiai() {
		return dienGiai;
	}
	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}
	
	
}
