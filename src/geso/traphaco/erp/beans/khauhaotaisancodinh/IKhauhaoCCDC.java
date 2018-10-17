package geso.traphaco.erp.beans.khauhaotaisancodinh;

import geso.traphaco.center.db.sql.Idbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface IKhauhaoCCDC {
	
	public String getKhId();

	public void setKhId(String Id); 
	
	public String getUserId(); 

	public void setUserId(String userId); 
	
	public String getCongty();

	public void setCongty(String cty);
	
	public String getCtyId();

	public void setCtyId(String ctyId); 

	public ResultSet getThangRs();

	public void setThangRs(ResultSet thangRs);

	public String getThang();

	public void setThang(String thang);

	public ResultSet getNamRs();

	public void setNamRs(ResultSet namRs);

	public String getNam();

	public void setNam(String nam);
	
	public ResultSet getCCDCRs();

	public void setCCDCRs(ResultSet CCDCRs); 
	
	public String getMatsstr(); 

	public void setMatsstr(String matsstr); 

	public String[] getMa(); 

	public void setMa(String[] ma); 

	public String[] getDiengiai(); 

	public void setDiengiai(String[] diengiai); 
	
	public String[] getNhomTS(); 

	public void setNhomTS(String[] nhom);
	
	public ResultSet getNhomRs(); 

	public void setNhomRs(ResultSet nhomRs); 

	public String getNccdcId(); 

	public void setNccdcId(String nccdcId); 
	
	public String[] getThangthu(); 

	public void setThangthu(String[] thangthu); 

	public String[] getKhauhao(); 

	public void setKhauhao(String[] khauhao); 
	
	public String[] getKhauhaoluyke(); 

	public void setKhauhaoluyke(String[] khauhaoluyke); 

	public String[] getGiatriconlai(); 

	public void setGiatriconlai(String[] giatriconlai);
	
	public String getMsg(); 

	public void setMsg(String msg); 
	
	public void DBClose();
	public void createRs();
	

	public boolean save(HttpServletRequest request)throws ServletException, IOException;
	
	public boolean Cancel(Idbutils db,String Id);
	
	public void setThangCuoiHopLe(String[] thangCuoiHopLe);
	public String[] getThangCuoiHopLe();
	

	public String[] getThangthutt() ;

	public void setThangthutt(String[] thangthutt) ;
	
	public String getDienGiaiCT();
	
	public void setDienGiaiCT(String dienGiaiCT);
	
	public List<IGiaTriKhauHao> getKhauhaoList() ;

	public void setKhauhaoList(List<IGiaTriKhauHao> khauhaoList) ;
	
	public String getSoChungTu() ;

	public void setSoChungTu(String soChungTu);
	
	
	public String getFlag() ;

	public void setFlag(String flag);

	public boolean isKhauhao() ;

	public void setKhauhao(boolean isKhauhao) ;
	public void CheckKhauHao(String nam,String thang);
	
	public String getId() ;

	public void setId(String id) ;

	public String getNppId();

	public void setNppId(String nppId);
	

	public String getDvkdId();

	public void setDvkdId(String dvkdId);
}