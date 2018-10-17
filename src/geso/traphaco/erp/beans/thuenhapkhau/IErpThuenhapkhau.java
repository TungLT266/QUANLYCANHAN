package geso.traphaco.erp.beans.thuenhapkhau;

import geso.traphaco.erp.beans.thuenhapkhau.imp.ErpSanPhamNhapKhau;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IErpThuenhapkhau
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String Id);
	
	public String getCqt(); 

	public void setCqt(String cqt); 

	public String getCqtId(); 

	public void setCqtId(String cqtId); 

	public String getPO(); 

	public void setPO(String po); 

	public String getPOId(); 

	public void setPOId(String poId); 

	public String getPtThueNK(); 

	public void setPtThueNK(String ptThueNK); 

	public String getThueNK(); 

	public void setThueNK(String ThueNK); 

	public String getSoHD(); 

	public void setSoHD(String soHD); 
	
	public String getPtVAT(); 

	public void setPtVAT(String ptVAT); 

	public String getVAT(); 

	public void setVAT(String VAT); 
	
	public String getNgaynhap();
	public void setNgaynhap(String tungay);

	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getPnkId();
	public void setPnkId(String khId);
	
	public ResultSet getPhieunhapRs();
	public void setPhieunhapRs(ResultSet pnRs);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean Create();
	public boolean Update();
	
	public void createRs();
	public void init();
	
	public String getNcc(); 

	public void setNcc(String ncc); 

	public String getNccId(); 

	public void setNccId(String nccId); 
	
	public ResultSet getTnkRs();
	
	public void setTnkRs(ResultSet tnkRs);

	public Hashtable getThuesuatHashtable(); 

	public void setThuesuatHashtable(Hashtable tsht); 

	public Hashtable getThueHashtable(); 

	public void setThueHashtable(Hashtable tht);
	
	public String[] getSpId(); 

	public void setSpId(String[] spId); 

	public String[] getSoluong(); 

	public void setSoluong(String[] soluong); 

	public String[] getDongia(); 

	public void setDongia(String[] dongia); 

	public String[] getThuesuat(); 

	public void setThuesuat(String[] thuesuat); 

	public String[] getThue(); 

	public void setThue(String[] thue); 		

	public String getNgaychungtu();
	
	public void setNgaychungtu(String ngaychungtu);
	
	public String getSochungtu();
	
	public void setSochungtu(String sochungtu);
	
	public boolean UpdateVAT();
	
	public void DbClose();

	public ResultSet getPoList();
	
	public ResultSet getNccList();
	
	public ResultSet getCoquanthue();
	
	public String getTienhang();
	
	public void setTienhang(String tienhang);
	
	public String getLoaihinh();
	public void setLoaihinh(String loaihinh);
	
	public ResultSet getSoHoaDonNew();
	public ResultSet getSoHoaDonUpdate();
	public ResultSet getSoHoaDonDisplay();
	
	public String getHoadonIds();
	public void setHoadonIds(String hoadonIds);
	
	public List<IErpHoadon> getHdList();
	public void setHdList(List<IErpHoadon> hdList);
	
	public void setRequest(HttpServletRequest request);
	
	public String getTienteId(); 

	public void setTienteId(String tienteId); 
	
	public String getTigia(); 

	public void setTigia(String tigia); 
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
	public void setMaHS(String maHS);
	public String getMaHS();
	
	public void setSanPhamList(List<ErpSanPhamNhapKhau> sanPhamList);
	public List<ErpSanPhamNhapKhau> getSanPhamList();
	
	public void setMaTienTe(String maTienTe);
	public String getMaTienTe();
	
	public void setLoaiMh(String loaimh);
	public String getLoaiMh();
	
}