package geso.traphaco.erp.beans.huychungtu;

import java.sql.ResultSet;

public interface IErpHuychungtumuahang 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getSoThuenhapkhau();
	public void setSoThuenhapkhau(String SoThuenhapkhau);
	
	
	
	public String getSophieuchuyenkho();
	public void setSophieuchuyenkho(String sophieuchuyenkho);
	
	
	public String getSoThanhtoan();
	public void setSoThanhtoan(String sothanhtoan);
	public String getSoHoadon();
	public void setSoHoadon(String sohoadon);
	public String getSophieunhapkho();
	public void setSoPhieunhapkho(String sonhapkho);
	public String getSoPhieunhanhang();
	public void setSoPhieunhanhang(String sonhanhang);
	public String getSoDonMuahang();
	public void setSoDonmuahang(String somuahang);
	
	public String getSoDeNghiThanhToan();
	public void setSoDeNghiThanhToan(String sodenghithanhtoan);
	
	public String getSophieukiemdinh();
	public void setSoPhieukiemdinh(String sophieukiemdinh);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	
	public String getNgayghinhan();
	public void setNgayghinhan(String Ngayghinhan);
 
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getSochungtuRequest();
	public void setSochungtuRequest(ResultSet soctRequest);
	public ResultSet getSochungtuDetail();
	public void setSochugntuDetail(ResultSet soctdetail);
	
	public void setSochungtu_chonhuy(String sct); 
	public String getSochungtu_chonhuy();
	
	public void createRs();
	public void init();

	public String getLoaictId();
	public void setLoaictId(String loaictId);
		
	public String getSoxuatkhotrave();
	public void setSoxuatkhotrave(String Soxuatkhotrave);
		
	public String getSoDontrahang();
	public void setSoDontrahang(String SoDontrahang);
	
	public String getSohoadontrahang();
	public void setSohoadontrahang(String Sohoadontrahang);
	
	public String getSotieuhaogiacong();
	public void setSotieuhaogiacong(String Sotieuhaogiacong);
	
	public String getSohoadondieuchinhncc();
	public void setSohoadondieuchinhncc(String Sohoadondieuchinhncc);
	
	
	public String getSochiphinhanhang();

	public void setSochiphinhanhang(String sochiphinhanhang);
	
	public void DbClose();
	public boolean createHct(String[] sochungtuhuy, String[] soct,
			String[] ngaychungtu, String[] trangthai, String[] loaichungtu,
			String[] ngaytao, String[] thutu, String[] type, String[] chon);
}
