package geso.traphaco.erp.beans.tinhgiathanh;
import java.sql.ResultSet;
import java.util.List;

public interface IErp_Tinhgiathanh {
	public String getUserId();
	public void setUserId(String userId);
	
	public String getthang();
	public void setthang(String thang);
	
	public String getNhaMayId();
	public void setNhaMayId(String nhamayid);
	
	
	public String getCongtyId();
	public void setCongtyId(String congtyid);
	
	public String getNam();
	public void setNam(String nam);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void Init ();
	
	public void DbClose();
	public boolean TinhGiaThanh();
	
	public boolean TinhGiaThanh_TheoMaTP( String isImport );
	
	public ResultSet getRsGiaThanh();
	public void  setRsGiaThanh(ResultSet rs);
					 
	public ResultSet getRsGiaThanhBTP_TieuThu();
	public void  setRsgetRsGiaThanhBTP_TieuThu(ResultSet rs);
	
	
	public ResultSet getRsGiaThanh_ThanhPham();
	public void  setRsgetRsGiaThanh_ThanhPham(ResultSet rs);
	
	public ResultSet getRsXuatNhapTon_BTP();
	public void  setRsXuatNhapTon_BTP(ResultSet rs);
	
	
	public ResultSet getRsTapHopNL_HC();
	public void  setRsTapHopNL_HC(ResultSet rs);
	
	
	public ResultSet getRsBangGiaBinhQuanNL();
	public void  setRsBangGiaBinhQuanNL(ResultSet rs);
	
	
	public ResultSet getRsTieuThuNL_LSX();
	public void  setRsTieuThuNL_LSX(ResultSet rs);
	//B3
	public ResultSet getRsHoaChatTieuthuThang();
	public void  setRsHoaChatTieuthuThang(ResultSet rs);
	
	//B4
	public ResultSet getRsPhanBoHoaChatTT_Thang();
	public void  setPhanBoHoaChatTT_Thang(ResultSet rs);
	
	//B7
	public ResultSet getRsPhanBoChiPhi_Thang();
	public void  setPhanBoChiPhi_Thang(ResultSet rs);
	
	public ResultSet getRsChiPhi();
	public void  setRsChiPhi(ResultSet rs);
	
	public ResultSet getRsNhaMay();
	public void  setRsNhaMay(ResultSet rs);
	
	public ResultSet getRsTieuhaoNL_Chinh();
	public void  setRsTieuhaoNl_CHinh(ResultSet rs);

	public ResultSet getRsTieuhaoNL_Phu();
	public void  setRsTieuhaoNl_Phu(ResultSet rs);
	
	
	public ResultSet getRsTinhGiaHoi();
	public void  setRsTinhGiaHoi(ResultSet rs);
	
	
	public ResultSet getRsTinhGiaHoi_CB6();
	public void  setRsTinhGiaHoi_CB6(ResultSet rs);
	public List<ResultSet> createView();
	public int getIsView();
	public void setIsView(int isView);
	
	public List<ResultSet> getListRs();
	public void setListRs(List<ResultSet> listRs);
	public void createView_XemKeHoach();
	
	//New
	public ResultSet getGiathanhTP();
	public void  setGiathanhTP(ResultSet giathanhTP);
	public ResultSet getGiathanhLSX();
	public void  setGiathanhLSX(ResultSet giathanhLSX);
	public ResultSet getChitietRs();
	public void  setChitietRs(ResultSet chitietRs);
	
 
}
