package geso.traphaco.erp.beans.thutien;

import geso.traphaco.erp.beans.thutien.IHoadonct;

import java.util.List;

public interface IHoadon extends Comparable<IHoadon>
{
	public String getId();
	public void setId(String id);
	public String getTtId();
	
	public void setTtId(String ttId); 
	
	public String getSotienNT();
	
	public void setSotienNT(String sotienNT); 
	
	public String getMahopdong();
	
	public void setMahopdong(String mahopdong); 

	public String getKyhieu();
	public void setKyhieu(String kyhieu);
	public String getSo();
	public void setSo(String so);
	public String getNgay();
	public void setNgay(String ngay);
	public String getSotiengoc();

	public void setSotiengoc(String sotiengoc);

	public String getTongtiencoVAT();
	public void setTongtiencoVAT(String aVat);
	
	public String getSotienno();

	public void setSotienno(String sotienno); 
	
	public String getThanhtoan();
	public void setThanhtoan(String thanhtoan);
	public String getConlai();
	public void setConlai(String conlai);
	public String getTrahet();
	public void setTrahet(String trahet);
	public String getThutienId();
	public void setThutienId(String thutienId);
	
	public String getTygia();
	public void setTygia(String Tygia);
	public String getConlai_CantruCN();
	
	public String getLoaihd();
	public void setLoaihd(String loaihd);
	public String getTenloaihd();
	public void setTenloaihd(String tenloaihd);
	
	public String getNgaydenhanTT();
	public void setNgaydenhanTT(String ngaydenhanTT);
	
	public String getKhId();
	public void setKhId(String KhId);
	
	public String getTenkh();
	public void setTenkh(String Tenkh);
	
	public String getMaKH();
	public void setMaKH(String makh);
	
	public List<IHoadonct> getHoadonList();
	public void setHoadonList(List<IHoadonct> spList);
	
	public String getptck();
	public void setptck(String ptck);
	
	public String getthucthu();
	public void setthucthu(String thucthu);
	
	public String gettienck();
	public void settienck(String tienck);
	
	public String getTiengocHD();
	public void setTiengocHD(String TiengocHD);
	
	public String getChenhlech();
	public void setChenhlech(String chenhlech);
	
	public String getkbhId();
	public void setkbhId(String kbhId);
		
	public String getkbhTen();
	public void setkbhTen(String kbhTen);
	
	public String getxoacl();
	public void setxoacl(String xoacl);
	
	public String getmacp();
	public void setmacp(String macp);
	
	public String gettencp();
	public void settencp(String tencp);
	
	public String getisNPP();
	public void setisNPP(String isNPP);
}
