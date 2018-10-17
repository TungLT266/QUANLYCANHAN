package geso.traphaco.erp.beans.congty;

import java.sql.ResultSet;
import java.util.List;

public interface IErpCongTy
{
	public String getVonDieuLe();
	public void setVonDieuLe(String vonDieuLe);
	
	public String getGiayPhepKinhDoanh();
	public void setGiayPhepKinhDoanh(String giayPhepKD);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getUserId();
	public void setUserId(String userId);

	public String getID();
	public void setID(String pk_seo);

	public String getMA();
	public void setMA(String ma);

	public String getTEN();

	public void setTEN(String ten);
	
	public String getTENTIENGANH();

	public void setTENTIENGANH(String tentienganh);

	public String getDIACHI();

	public void setDIACHI(String diachi);
	
	public String getDIACHITIENGANH();

	public void setDIACHITIENGANH(String diachitienganh);

	public String getMASOTHUE();

	public void setMASOTHUE(String masothue);

	public String getNGANHNGHEKINHDOANH();

	public void setNGANHNGHEKINHDOANH(String nganhnghekinhdoanh);

	public String getDIENTHOAI();

	public void setDIENTHOAI(String dienthoai);

	public String getFAX();

	public void setFAX(String fax);

	public String getGIAMDOC();

	public void setGIAMDOC(String giamdoc);

	public String getKETOANTRUONG();

	public void setKETOANTRUONG(String ketoantruong);

	public String getTRANGTHAI();

	public void setTRANGTHAI(String trangthai);

	public void init();

	public String getMessage();

	public void setMessage(String msg);

	public String getNganHang_FK();

	public void setNganHang_FK(String nganhang_id);

	public IErpNganHang getNganHang();

	public void setNganHang(IErpNganHang nganhang);

	public String getSoTaiKhoan();

	public void setSoTaiKhoan(String sotaikhoan);

	public List<IErpNganHang> getNganHangList();

	public void setNganHangList(List<IErpNganHang> nganhanglist);

	public boolean CreateCongty();

	public boolean UpdateCongty();
	
	public boolean CheckUnique(String command);
	public boolean Delete();

	public String getTkCtyId();
	
	public void setTkCtyId(String id);

	public ResultSet getCtyRs();
	
	public void setCtyRs(ResultSet ctyRs);

	public String getIsTongCty();
	public void setIsTongCty(String tongcty);
	
	public String getngaybatdautc();
	public void setngaybatdautc(String ngaybatdautc);
	
	public String getngayketthuctc();
	public void setngayketthuctc(String ngayketthuctc);
	
	public void closeDB();
}