package geso.traphaco.center.beans.tieuchithuong;

import java.sql.ResultSet;

public interface ITieuchithuong {
	
	public void setDiengiai(String diengiai);
	
	public String getDiengiai();
	
	public void setDvkd(String dvkd);
	
	public String getDvkd();

	public void setKbh(String kbh);
	
	public String getKbh();
	
	public void setNam(String nam);
	
	public String getNam();
	
	public void setThang(String thang);
	
	public String getThang();
	
	public void setTct(ResultSet tct);
	
	public ResultSet getTct();

	public void setTctId(String tctId);
	
	public String getTctId();
	
	public void setLoai(String loai);
	public String getLoai();
	
	public void setHtbhRs(ResultSet htbhRs);
	public ResultSet getHtbhRs();
	
	public void sethethongBH(String htbh);
	public String gethethongBH();
	
	public void setLoaiDS(String loaids);
	public String getLoaiDS();
	
	public void setThuongnsp(String thuongnsp);
	public String getThuongnsp();
	
	public void setDstoithieuDH(String dstoithieudh);
	public String getDstoithieuDH();
	
	
	public void setTieuchi(String tc);
	public String getTieuchi();
	
	public void setTieuchiFK(String tcfk);
	public String getTieuchiFK();
	
	public void setTongThuong(String tongthuong);
	public String getTongThuong();
	public void setTileDStoithieu(String tldstoithieu);
	public String getTileDStoithieu();
	
	public void setUserId(String userId);
	
	public String getUserId();

	public void setMsg(String msg);
	
	public String getMsg();
	
	public void setAction(String action);
	
	public String getAction();
	
	public void setTieuchiRS(ResultSet tieuchi);
	
	public ResultSet getTieuchiRS();
	
	public void setNhomtcRS(ResultSet nhomtc);
	public ResultSet getNhomtcRS();
	
	public void setTCNhomId(String nhomtcid) ;
	public String getTCNhomId();
	
	public void setTCDiengiai(String tcdiengiai) ;
	public String getTCDiengiai();

	public boolean Save();
	
	public boolean xoaTC();

	public void init();
	
	public void setData(String[] ctct, String[] stt, String[] tu, String[] den, String[] thuong);

	public void closeDB();

	public void SetLoaiTieuChi(String loaitieuchi) ;
	public String GetLoaiTieuChi() ;

	public void SetCongty(String cty);
	public String GetCongty();
	
	public void setCongtyRs(ResultSet ctyRs);
	public ResultSet getCongtyRs();
	
	public ResultSet GetRsKenh();
	public ResultSet GetRsDVKD();
	public ResultSet GetRsNhomSp();
	
	public String getNhomsp();
	public void setNhomsp(String nhomsp);
	
	public void setToithieu(String toithieu);
	public String getToithieu();
	
	public void setToida(String toida);
	public String getToida();
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);

}
