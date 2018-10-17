package geso.traphaco.erp.beans.dieuchuyentien;

import java.sql.ResultSet;

public interface IErpDieuchuyentien 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String Id);
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);
	public String getTtId();
	public void setTtId(String ttId);
	public String getTigia();
	public void setTigia(String tigia);	
	public String getNhchuyenId();
	public void setNhchuyenId(String nhchuyenId);
	public String getNhnhanId();
	public void setNhnhanId(String nhnhanId);
	public String getSotienNT();
	public void setSotienNT(String sotienNT);
	public String getSotienVND();
	public void setSotienVND(String sotienVND);
	public String getTrichphi();
	public void setTrichphi(String trichphi);
	public String getNHTrichphiId();
	public void setNHTrichphiId(String NHtrichphiId);
	public String getPhiNT();
	public void setPhiNT(String phiNT);
	public String getPhiVND();
	public void setPhiVND(String phiVND);
	public String getThueVND();
	public void setThueVND(String thueVND);
	public String getThueNT();
	public void setThueNT(String thueNT);
	public String getGhichu();
	public void setGhichu(String ghichu);	
	public String getMsg();
	public void setMsg(String msg);
	public ResultSet getTienteRs();
	public void setTienteRs(ResultSet ttRs);
	public ResultSet getNHChuyenRs();
	public void setNHChuyenRs(ResultSet nhchuyenRs);
	public ResultSet getNHNhanRs();
	public void setNHNhanRs(ResultSet nhnhanRs);
	public ResultSet getNHTrichphiRs();
	public void setNHTrichphiRs(ResultSet nhtrichphiRs);

	public String getNccId(); 
	public void setNccId(String nccId);
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	
	public String getPOId(); 
	public void setPOId(String POId);
	public ResultSet getPORs();
	public void setPORs(ResultSet PORs);
	
	public String getNhKyquyId(); 
	public void setNhKyquyId(String nhkyquyId);
	public ResultSet getNHKyquyRs();
	public void setNHKyquyRs(ResultSet nhkyquyRs);
	
	public String getMahoadon(); 

	public void setMahoadon(String mahoadon);

	public String getMauhoadon(); 
	
	public void setMauhoadon(String mauhoadon);

	public String getKyhieu(); 

	public void setKyhieu(String kyhieu);

	public String getSohoadon(); 

	public void setSohoadon(String sohoadon);

	public String getNgayhoadon(); 

	public void setNgayhoadon(String ngayhoadon);

	public String getTenNH_VAT(); 

	public void setTenNH_VAT(String tenNH_VAT);

	public String getMST(); 

	public void setMST(String mst);

	public void init();	
	
	public boolean Taodieutien();	
	
	public boolean Capnhatdieutien();
	
	public boolean Chot();
	
	public String getLoaidc();
	
	public void setLoaidc(String loaidc);
	
	public void createRs();
	
	public void DBclose(); 
}
