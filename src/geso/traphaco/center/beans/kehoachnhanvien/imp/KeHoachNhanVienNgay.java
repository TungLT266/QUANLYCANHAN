package geso.traphaco.center.beans.kehoachnhanvien.imp;

import geso.traphaco.center.beans.kehoachnhanvien.*;
import java.util.List;
import java.util.ArrayList;

public class KeHoachNhanVienNgay implements IKeHoachNhanVienNgay
{
	private static final long serialVersionUID = -9217977546733610214L;
	String khId = "", ngay = "", ghichu = "";
	List<IKeHoachNhanVienChiTiet> nppList, thitruongList;
	
	public KeHoachNhanVienNgay()
	{
		this.nppList = new ArrayList<IKeHoachNhanVienChiTiet>();	
		this.thitruongList = new ArrayList<IKeHoachNhanVienChiTiet>();		
	}
	
	public KeHoachNhanVienNgay(String id)
	{
		this.khId = id;
		this.nppList = new ArrayList<IKeHoachNhanVienChiTiet>();	
		this.thitruongList = new ArrayList<IKeHoachNhanVienChiTiet>();	
	}
	
	public String getKeHoachId() 
	{
		return this.khId;
	}

	public void setKeHoachId(String khId) 
	{
		this.khId = khId;
	}
	
	public String getNgay() 
	{
		return this.ngay;
	}

	public void setNgay(String ngay) 
	{
		this.ngay = ngay;
	}

	@Override
	public List<IKeHoachNhanVienChiTiet> getNppList() {
		return this.nppList;
	}

	@Override
	public void setNppList(List<IKeHoachNhanVienChiTiet> nppList) {
		this.nppList = nppList; 
	}

	@Override
	public List<IKeHoachNhanVienChiTiet> getThiTruongList() {
		return this.thitruongList;
	}

	@Override
	public void setThiTruongList(List<IKeHoachNhanVienChiTiet> thitruongList) {
		this.thitruongList = thitruongList; 
	}
}
