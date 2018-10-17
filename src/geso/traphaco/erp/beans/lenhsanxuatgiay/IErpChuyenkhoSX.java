package geso.traphaco.erp.beans.lenhsanxuatgiay;

import geso.traphaco.erp.beans.nhapkho.IKhu_Vitri;

import java.sql.ResultSet;
import java.util.List;

public interface IErpChuyenkhoSX
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);
	
	public String getLydoyeucau();
	public void setLydoyeucau(String lydoyeucau);
	
	public String getKhoXuatId();
	public void setKhoXuatId(String khoxuattt);
	public ResultSet getKhoXuatRs();
	public void setKhoXuatRs(ResultSet khoxuatRs);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getLsxIds();
	public void setLsxIds(String lsxIds);
	public ResultSet getLsxRs();
	public void setLsxRs(ResultSet lsxRs);
	
	public List<IYeucau> getSpList();
	public void setSpList(List<IYeucau> spList);
	
	public List<IYeucau> getSpChoNhapList();
	public void setSpChoNhapList(List<IYeucau> spchoNhapList);
	
	public String getMsg();
	public void setMsg(String msg);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getIsnhanHang();
	public void setIsnhanHang(String ischuyenKho);
	
	public List<IKhu_Vitri> getKhuList();
	public void setKhuList(List<IKhu_Vitri> khuList);

	public List<IKhu_Vitri> getVitriList();
	public void setVitriList(List<IKhu_Vitri> vitriList);
	
	public boolean createCK();
	public boolean updateCK();
	public boolean nhanHang();
	
	public boolean chuyenNL();
	public void createRs();

	public void init();
	public void initNhanhang();
	public void initView();
	
	public void initYeucauNLPdf();
	public void initChuyenNLPdf();
	
	public void DBclose();
}
