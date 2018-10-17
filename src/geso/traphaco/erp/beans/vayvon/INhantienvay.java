package geso.traphaco.erp.beans.vayvon;

import java.sql.ResultSet;

public interface INhantienvay {
	public void setId(String Id);
	
	public String getId();

	public void setCtyId(String ctyId);

	public String getCtyId();
	
	public void setSoHD(String soHD);

	public String getSoHD();
	
	public void setNgay(String ngay);

	public String getNgay();
	
	public void setSotien(String sotien);

	public String getSoien();
	
	public void setLaisuat(String laisuat);

	public String getLaisuat();

	public void setHinhthuc(String hinhthuc);

	public String getHinhthuc();

	public void setGhichu(String ghichu);

	public String getGhichu();

	public void setTrangthai(String trangthai);

	public String getTrangthai();
	
	public ResultSet getHDRS();
	
	public void setHDRS(ResultSet HDRS);
		
	public void setUserId(String userId);
	
	public String getUserId();

	public void init();

	public void setmsg(String msg);
	
	public String getmsg();
	
	public void setThoihan(String thoihan);

	public String getThoihan();
	
	public void setNgaytravay(String[] ngaytravay);

	public String[] getNgaytravay();

	public void setTravay(String[] travay);

	public String[] getTravay();
	
    public boolean save();

	public boolean Xoa();
    
	public String Hoantat();

	public void Init_Array();
	
	public void setTkvay(String tkvay);

	public String getTkvay();
	
	public ResultSet getTtRs();
	
	public void setTtRs(ResultSet ttRs);
	
	public void setTtId(String ttId);
	
	public String getTtId();
	
	public void setTralai(String[] tralai);

	public String[] getTralai();

	public void setConlai(String[] conlai);

	public String[] getConlai();
	
	public void DbClose();
	
	public void setGiainganveTK(String giainganveTK);
	public String getGiainganveTK();
	
	public ResultSet getSotkRs();
	public void setSotkRs(ResultSet sotkRs);
	
	public String getSotaikhoan();	
	public void setSotaikhoan(String sotk);
	
	public String getTienteId();
	public void setTienteId(String ttId);
	public ResultSet getTienteRs();
	public void setTienteRs(ResultSet tienteRs);
	
	public String getTigia();
	public void setTigia(String tigia);
	
	public String getSotienNT();
	public void setSotienNT(String sotienNT);
	
	public String getSotienVND();
	public void setSotienVND(String sotienVND);
	
	public String getStConlai();
	public void setStConlai(String stConlai);
	
	public void createPhieuChi();
	
	public void XacdinhTienTe();
	
	public ResultSet getUNCRS();
	public void setUNCRs(ResultSet UNCRs);
	
	public void setUNCIds(String[] uncIds);

	public String[] getUNCIds();

	public void setNccIds(String[] nccIds);

	public String[] getNccIds();

	public void setNccTen(String[] nccTen);
	
	public String[] getNccTen();

	public void setSotienNTs(String[] sotienNTs);

	public String[] getSotienNTs();
	
	public void setTgs(String[] tgs);

	public String[] getTgs();
	
	public void setSotienVNDs(String[] sotienVNDs);

	public String[] getSotienVNDs();

	public void setPays(String[] pays);

	public String[] getPays();

	public void setRests(String[] rests);

	public String[] getRests();
	
	public String getNgayDaoHan() ;
	public void setNgayDaoHan(String ngayDaoHan) ;

	public void setDinhKhoanRs(ResultSet dinhKhoanRs);
	public ResultSet getDinhKhoanRs();
	
	public String getSotienvay() ;
	public void setSotienvay(String sotienvay) ;
	public String getNppId() ;
	public void setNppId(String nppId);
	public String[] getConlaiHD() ;
	public void setConlaiHD(String[] conlaiHD) ;
}
