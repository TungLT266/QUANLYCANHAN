package geso.traphaco.erp.beans.tamung;

import java.sql.ResultSet;

public interface IErpTamUng {
	public String getId();
	public String getTiGiaTheoTienTe();
	public void setId(String Id);
	public String getTigia();

	public void setTigia(String tigia);
	public String getNgayTamUng();

	public void setNgayTamUng(String NgayTamUng);

	public String getNhanVienId();

	public void setNhanVienId(String NhanVienId);

	public String getNccId();

	public void setNccId(String NccId);

	public String getDoiTuongTamUng();

	public void setDoiTuongTamUng(String DoiTuongTamUng);

	public String getLyDoTamUng();

	public void setLyDoTamUng(String LyDoTamUng);

	public String getSoTienTamUng();

	public void setSoTienTamUng(String SoTienTamUng);

	public String getTienTeId();

	public void setTienTeId(String TienTeId);

	public String getThoiGianHoanUng();

	public void setThoiGianHoanUng(String ThoiGianHoanUng);

	public String getUserId();

	public void setUserId(String UserId);

	public String getTenHienThi();

	public void setTenHienThi(String TenHienThi);

	public String getMsg();

	public void setMsg(String Msg);

	public ResultSet getRsTienTe();

	public void setRsTienTe(ResultSet rsTienTe);

	public void Init();
	public void InitPDFNhanVien();
	
	public void InitPDFNhaCungCap();

	public boolean Save();

	public boolean Edit();

	public void createRs();

	public void DBClose();

	public String Chot();

	public String Delete();

	public String getHinhThucHoanUng();
	public void setHinhThucHoanUng(String hinhThucHoanUng);

	public String getBoPhan();
	public void setBoPhan(String bophan);

	public String getNguoitao();

	public void setNguoitao(String nguoitao);
	
	public String getPoId();

	public void setPoId(String PoId);	
	
	public ResultSet getRsPo();

	public void setRsPo(ResultSet rsPo);
	
	public String getnccId();

	public void setnccId(String nccId);	
	
	public ResultSet getRsNcc();

	public void setRsNcc(ResultSet rsNcc);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
	public String getHtttId();
	public void setHtttId(String Httt);
	public ResultSet getHtttRs();
	public void setHtttRs(ResultSet HtttRs);
	
	public String getdiachincc();
	public void setdiachincc(String diachincc);
	
	public ResultSet getTtDuyetRs();
	public void setTtDuyetRs(ResultSet TtDuyetRs);
	
	public String getduyettu();
	public void setduyettu(String duyettu);

	public void changeNCC();
	public String getSoTienNT() ;

	public void setSoTienNT(String soTienNT);
}
