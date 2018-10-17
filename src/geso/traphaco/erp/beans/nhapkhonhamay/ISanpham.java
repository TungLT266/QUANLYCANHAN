package geso.traphaco.erp.beans.nhapkhonhamay;

import geso.traphaco.erp.beans.nhapkhonhamay.ISpDetail;

import java.sql.ResultSet;
import java.util.List;

public interface ISanpham
{
	public String getStt();
	public void setStt(String stt);
	
	public String getId();
	public void setId(String id);
	public String getMa();
	public void setMa(String masp);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getNgaynhandukien();
	public void setNgaynhandukien(String ngaynhandukien);
	
	public String getSoluongdat();
	public void setSoluongdat(String soluongdat);
	public String getDungsai();
	public void setDungsai(String dungsai);
	public String getGia();
	public void setGia(String gia);
	public String getSoluongnhan();
	public void setSoluongnhan(String soluongnhan);
		
	public String getDvdl();
	public void setDvdl(String Dvdl);	
	
	public String getSoPO();
	public void setSoPO(String soPO);
	
	public String getMuahang_fk();
	public void setMuahang_fk(String muahang_fk);
	
	public List<ISpDetail> getSpDetail();
	public void setSpDetail(List<ISpDetail> spDetail);
	
	public String getNhomkenh();
	public void setNhomkenh(String nhomkenh);
	
	public String getThuexuat();
	public void setThuexuat(String thuexuat);
	
	public String getHansudung();
	public void setHansudung(String hansudung);
}
