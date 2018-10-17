package geso.traphaco.erp.beans.khauhaotaisancodinh;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface IKhauhaotaisan {
	
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
	
	public ResultSet getTaisanRs();

	public void setTaisanRs(ResultSet taisanRs); 
	
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

	public String getNtsId(); 

	public void setNtsId(String ntsId); 
	
	public String[] getThangthu(); 
	
	public String[] getThangthuTT();

	public void setThangthuTT(String[] thangthuTT) ;

	public void setThangthu(String[] thangthu); 

	public double[] getKhauhao(); 

	public void setKhauhao(double[] khauhao); 
	
	public double[] getKhauhaoluyke(); 

	public void setKhauhaoluyke(double[] khauhaoluyke); 

	public double[] getGiatriconlai(); 

	public void setGiatriconlai(double[] giatriconlai);
	
	public String getMsg(); 

	public void setMsg(String msg); 
	
	public void createRs();
	public String Delete();
	
	public boolean Cancel(String Id);
	
	public boolean save(HttpServletRequest request)throws ServletException, IOException;
	
	public String getnppdangnhap();
	
	public void setnppdangnhap(String nppdangnhap);
	
	public String getDienGiaiCT();
	
	public void setDienGiaiCT(String dienGiaiCT);
	public void DBClose();
	public String getFlag() ;

	public void setFlag(String flag) ;
	
	public void CheckKhauHao(String nam,String thang);
	
	public boolean isKhauhao() ;

	public void setKhauhao(boolean isKhauhao);

	public List<IGiaTriKhauHao> getKhauhaoList() ;

	public void setKhauhaoList(List<IGiaTriKhauHao> khauhaoList) ;
	
	public String getSoChungTu() ;

	public void setSoChungTu(String soChungTu);
	
	
	public String getId() ;

	public void setId(String id) ;
	
	public String getNppId();

	public void setNppId(String nppId);
	
	
	public String getDVKDID();

	public void setDVKDID(String dVKDID);
	
	public ResultSet getLoaiRs();

	public void setLoaiRs(ResultSet loaiRs);
		
	
}
