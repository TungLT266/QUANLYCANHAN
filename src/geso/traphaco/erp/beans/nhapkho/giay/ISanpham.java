package geso.traphaco.erp.beans.nhapkho.giay;

import geso.traphaco.erp.beans.nhapkho.giay.ISanphamCon;

import java.sql.ResultSet;
import java.util.List;

public interface ISanpham 
{
	public String getNhapKhoId();
	public void setNhapKhoId(String nhapkhoId);
	
	public String getId();
	public void setId(String id);
	
	public String getMa();
	public void setMa(String masp);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getSoluongSx();
	public void setSoluongSx(String SoluongSx);
	

	
	
	public String getSoluongnhapkho();
	public void setSoluongnhapkho(String soluongnhap);

	public String getSoluonglaymau();
	public void setSoluonglaymau(String soluonglaymau);
 	
	public String getSolo();
	public void setSolo(String solo);
	
	public String getNgayhethan();
	public void setNgayhethan(String ngayhethan);
	
	public String getNgayNhapKho();
	public void setNgayNhapKho(String ngaynhapkho);
	
	public String getNgaySanXuat();
	public void setNgaySanXuat(String ngaysanxuat);
	
	public String getDVT();
	public void setDVT(String dvt);
	
	public String getQuycach();
	public void setQuycach(String quycach);
	
	public String getThung();
	public void setThung(String thung);
	
	public String getLe();
	public void setLe(String le);
	
	public String getTrongluong();
	public void setTrongluong(String trongluong);
	
	public String getThetich();
	public void setThetich(String thetich);
	
	public String getDongia();
	public void setDongia(String dongia);
	
	public String getTiente();
	public void setTiente(String tiente);
	
	public String getDongiaViet();
	public void setDongiaViet(String dongiaViet);
	
	public List<ISanphamCon> getSpConList();
	public void setSpConList(List<ISanphamCon> spConList);
	
	public String getKhuvucId();
	public void setKhuvucId(String value);
	
	public void setKhuvucRs(ResultSet rs);
	public ResultSet getKhuvucRs();
	
	public void setRsDvld(ResultSet rs);
	public ResultSet getRsDvld();
	public String getDvdlId();
	public void setDvdlId(String DvdlId);
	
	public String getDvdl_Mau();
	public void setDvdl_Mau(String Dvdl_Mau);
	
	public String getDvdl_Mau_Id();
	public void setDvdl_Mau_Id(String Dvdl_Mau_Id);
	
}
