package geso.traphaco.erp.beans.thanhtoanhoadon;

public interface IHoadon extends Comparable<IHoadon>
{
	public String getId();
	public void setId(String id);
	public String getTienteId();
	public void setTienteId(String ttId); 
	
	public String getTenCQT();
	public void setTenCQT(String tenCQT);
	
	public String getKyhieu();
	public void setKyhieu(String kyhieu);
	public String getSo();
	public void setSo(String so);
	public String getNgay();
	public void setNgay(String ngay);
	public String getSoPO();
	public void setSoPO(String soPO);
	public String getTongtiencoVAT();
	public void setTongtiencoVAT(String aVat);
	public String getSotienNT();
	public void setSotienNT(String sotienNT); 
	
	public String getThanhtoan();
	public void setThanhtoan(String thanhtoan);
	public String getConlai();
	public void setConlai(String conlai);
	public String getTrahet();
	public void setTrahet(String trahet);
	public String getSoTienGoc() ;
	
	
	public String getSoTienGoc2(); 
	public void setSoTienGoc2(String trahet);;
	
	public void setMancc(String mancc);
	public String getMancc() ;
	
	public String getTigia();
	public void setTigia(String tigia); 
			
	public String getSotienno();

	public void setSotienno(String sotienno); 
	
	public String getConlai_2();
	public void setConlai_2(String conlai);

	public String getLoaihd();
	public void setLoaihd(String loaihd);
	public String getTenloaihd();
	public void setTenloaihd(String tenloaihd);
	
	public String getLoaihd1();
	public void setLoaihd1(String loaihd1);
	public String getTenloaihd1();
	public void setTenloaihd1(String tenloaihd1);
	
	public String getNgaydenhanTT();
	public void setNgaydenhanTT(String ngaydenhanTT);
	
	public String getDoituong() ;
	public void setDoituong(String doituong) ;
	public String getDoituongId() ;
	public void setDoituongId(String doituongId) ;
	public String getMadoituong() ;
	public void setMadoituong(String madoituong) ;
	
	public String getSohopdong() ;
	public void setSohopdong(String sohopdong) ;
	
	public String getLoaiThue();
	public void setLoaiThue(String loaiThue);
	
	public String getSohopdongNGOAI() ;
	public void setSohopdongNGOAI(String sohopdongngoai) ;
	String getDiaChi();
	void setDiaChi(String diaChi);

}
