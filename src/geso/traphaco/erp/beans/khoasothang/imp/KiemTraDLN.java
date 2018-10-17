package geso.traphaco.erp.beans.khoasothang.imp;

import geso.traphaco.erp.beans.khoasothang.*;

public class KiemTraDLN implements IKiemTraDLN
{
	String thang;
	String nam;
	String loai;
	String id;
	String ma;
	String ten;
	String trangthai;
	String trangthainew;
	
	public KiemTraDLN()
	{
		this.thang = "";
		this.nam = "";
		this.loai = "";
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.trangthai = "";
		this.trangthainew = "";
	}
	
	public KiemTraDLN(String thang, String nam, String loai, String trangthai)
	{
		this.thang = thang;
		this.nam = nam;
		this.loai = loai;
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.trangthai = trangthai;
		this.trangthainew = "";
	}

	@Override
	public String getThang() {
		return this.thang;
	}

	@Override
	public void setThang(String thang) {
		this.thang = thang;
	}

	@Override
	public String getNam() {
		return this.nam;
	}

	@Override
	public void setNam(String nam) {
		this.nam = nam;
	}

	@Override
	public String getLoai() {
		return this.loai;
	}

	@Override
	public void setLoai(String loai) {
		this.loai = loai;
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
	public String getMa() {
		return this.ma;
	}

	@Override
	public void setMa(String ma) {
		this.ma = ma;
	}

	@Override
	public String getTen() {
		return this.ten;
	}

	@Override
	public void setTen(String ten) {
		this.ten = ten;
	}

	@Override
	public String getTrangthai() {
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	@Override
	public String getTrangthaiNew() {
		return this.trangthainew;
	}

	@Override
	public void setTrangthaiNew(String trangthainew) {
		this.trangthainew = trangthainew;
	}
	
	@Override
    public boolean equals(Object object) {

        if (object != null && object instanceof KiemTraDLN) {
        	KiemTraDLN thing = (KiemTraDLN) object;
            if (id == null) {
                return (thing.id == null);
            }
            else {
            	if(id.equals(thing.id) && loai.equals(thing.loai))
            		return true;
            }
        }

        return false;
    }
}
