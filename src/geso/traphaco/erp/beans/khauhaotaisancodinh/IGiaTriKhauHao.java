package geso.traphaco.erp.beans.khauhaotaisancodinh;

import java.sql.ResultSet;

public interface IGiaTriKhauHao {

	public String getThang() ;
	public void setThang(String thang) ;
	public String getTaisanid() ;
	public void setTaisanid(String taisanid) ;
	public String getKhauhaodukien() ;
	public void setKhauhaodukien(String khauhaodukien);
	public String getKhauhaoluykedukien() ;
	public void setKhauhaoluykedukien(String khauhaoluykedukien) ;
	public String getGiatriconlaidukien();
	public void setGiatriconlaidukien(String giatriconlaidukien) ;
	public String getKhauhaothucte() ;
	public void setKhauhaothucte(String khauhaothucte);
	public String getKhauhaoluykethucte() ;
	public void setKhauhaoluykethucte(String khauhaoluykethucte);
	public String getGiatriconlaithucte();
	public void setGiatriconlaithucte(String giatriconlaithucte);
	public String getMataisan() ;
	public void setMataisan(String mataisan) ;
	public String getDiengiai() ;
	public void setDiengiai(String diengiai) ;
	public String getThangKhauHaoTrenHeThong() ;
	public void setThangKhauHaoTrenHeThong(String thangKhauHaoTrenHeThong);
	public String getThangKhauHaoThucTe() ;
	public void setThangKhauHaoThucTe(String thangKhauHaoThucTe) ;
	public String getNhomTaiSan() ;
	public void setNhomTaiSan(String nhomTaiSan) ;
	public String getTonggiatri() ;
	public void setTonggiatri(String tonggiatri) ;
	
	public String getLoaiTaiSan();
	public void setLoaiTaiSan(String loaiTaiSan);
	
	

}
