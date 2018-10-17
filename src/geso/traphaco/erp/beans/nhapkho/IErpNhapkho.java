package geso.traphaco.erp.beans.nhapkho;

import java.sql.ResultSet;
import java.util.List;

public interface IErpNhapkho
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String userId);

	public String getNgaynhapkho();
	public void setNgaynhapkho(String ngaynhap);

	public String getSoPnh();
	public void setSoPnh(String soPnh);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public boolean getQuanLyBean();
	public void setQuanLyBean(boolean quanlybean);

	public void setmsg(String msg);
	public String getmsg();

	public void setKhoId(String khoId);
	public String getKhoId();

	public ResultSet getKhoList();
	public void setKhoList(ResultSet khoList);

	public void setNdnId(String ndnId);
	public String getNdnId();

	public ResultSet getNdnList();
	public void setNdnList(ResultSet ndnList);

	public void setLhhId(String lhhId);
	public String getLhhId();

	public ResultSet getLhhList();
	public void setLhhList(ResultSet lhhList);

	public void setVtkId(String lhhId);
	public String getVtkId();

	public ResultSet getVtkList();
	public void setVtkList(ResultSet vtkList);

	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);

	public List<IKhu_Vitri> getKhuList();
	public void setKhuList(List<IKhu_Vitri> khuList);

	public List<IKhu_Vitri> getVitriList();
	public void setVitriList(List<IKhu_Vitri> vitriList);

	public boolean createNhapKho();
	public boolean updateNhapKho();
	public String chotNhapKho(String userId);
	public boolean createNhapKhoLSX();

	public void updateDonnhanhang(String sonhanhang, String sodontrahang, String solenhsanxuat);

	public String getNgaychotNV();
	public void setNgaychotNV(String ngaychotNV);
	public String getSoDontrahang();
	public void setSoDontrahang(String soDth);
	public String getSoLenhsx();
	public void setSoLenhsx(String soLenhsx);
	
	// pdf
	public String getNguoigiaohang();
	public void setNguoinhanhang(String nguoinhanhang);

	public String getDiachi();
	public void setDiachi(String diachi);

	public String getNhaptaikho();
	public void setNhaptaikho(String nhaptaikho);

	public String getGhichu();
	public void setGhichu(String ghichu);

	public String getDiem();
	public void setDiem(String Diem);

	public void init();
	public void initPdf();
	public void createRs();
	
	
	//Nhap kho tu LSX
	public void checkLSX();

	public void DBclose();
	public void setCongDoanId(String ctidcurrent);
	public String GetCongDoanId();
}
