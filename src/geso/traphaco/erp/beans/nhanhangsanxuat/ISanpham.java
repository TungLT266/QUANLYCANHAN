package geso.traphaco.erp.beans.nhanhangsanxuat;

import geso.traphaco.erp.beans.nhanhangsanxuat.imp.DetailMeSp;

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
	
	public String getSoluongdat();
	public void setSoluongdat(String soluongdat);
	public String getSoluongnhan();
	public void setSoluongnhan(String soluongnhan);
	
	public String getNgaynhandukien();
	public void setNgaynhandukien(String ngaynhandukien);
	public String getHansudung();
	public void setHansudung(String hansudung);
	
	public String getDvdl();
	public void setDvdl(String Dvdl);
	
	public String getDungsai();
	public void setDungsai(String dungsai);
	public String getConlai();
	public void setCOnlai(String conlai);
	public String getDongia();
	public void setDongia(String dongia);
	
	public String getTiente();
	public void setTiente(String tiente);
	public String getTigiaquydoi();
	public void setTigiaquydoi(String tigiaquydoi);
	public String getDongiaViet();
	public void setDongiaViet(String dongiaViet);
	
	public String getKhonhanId();
	public void setKhonhanId(String khonhan);
	public String getKhonhanTen();
	public void setKhonhanTen(String khonhanTen);
	
	public String getSoluongDaNhan();
	public void setSoluongDaNhan(String soluongDaNhan);
	public String getSoluongMaxNhan();
	public void setSoluongMaxNhan(String soluongMaxNhan);
	
	public String getTrongluong();
	public void setTrongluong(String trongluong);
	public String getThetich();
	public void setThetich(String thetich);
	
	public String getSoPO();
	public void setSoPO(String soPO);
	
	public String getMuahang_fk();
	public void setMuahang_fk(String muahang_fk);
	
	public String getloaisp();
	public void setloaisp(String loaisp);
	
	public String getthanhtien();
	public void setthanhtien(String thanhtien);
	
	//Truong hop tra hang, phai co kho ( phai la nhung kho thiet lap trong du lieu nen KHO-SP )
	public ResultSet getKhoNhanRs();
	public void setKhoNhanRs(ResultSet khonhanRs); 
	
	public ResultSet getKhuRs();
	public void setKhoKhuRs(ResultSet KhuRs); 
	public String getKhuId();
	public void setKhuId(String khuId);
	
	public boolean getIskhoQL_khuvuc();
	public void setIskhoQL_khuvuc(boolean bien);
	public List<ISpDetail> getSpDetail();
	public void setSpDetail(List<ISpDetail> spDetail);
	
	public List<DetailMeSp> getListDetailMeSp();
	public void setListDetailMeSp(List<DetailMeSp> listDetailMeSp);
	
	public String getIdmarquette();
	public void setIdmarquette(String idmarquette) ;
	
	public String getIsKiemDinh();
	public void setIsKiemDinh(String isKiemDinh);
	
	public double getTiLeQuyDoiDv();
	public void setTiLeQuyDoiDv(double tiLeQuyDoiDv);
}
