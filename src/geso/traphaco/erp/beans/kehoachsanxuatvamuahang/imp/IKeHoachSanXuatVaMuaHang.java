package geso.traphaco.erp.beans.kehoachsanxuatvamuahang.imp;

import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface IKeHoachSanXuatVaMuaHang {
	public String getNhaMay();
	public void setNhaMay(String nhaMay) ;
	public String getQueryDK();
	public void setQueryDK(String queryDK) ;
	public String getLoaihangHoa() ;
	public void setLoaihangHoa(String loaihangHoa);
	public String getSanPham();
	public void setSanPham(String sanPham);
	public ResultSet getRsGiaCong();
	public void setRsGiaCong(ResultSet rsGiaCong) ;
	public ResultSet getRsMuaHang() ;
	public void setRsMuaHang(ResultSet rsMuaHang) ;
	public ResultSet getRs() ;
	public void setRs(ResultSet rs) ;
	public dbutils getDb() ;
	public void setDb(dbutils db) ;
	public List<ISanPham> getListSP() ;
	public void setListSP(List<ISanPham> listSP) ;
	public String getThang();
	public void setThang(String thang) ;
	public String getNam() ;
	public void setNam(String nam);
	public List<ISanPham> getListSPGiaCong();
	public void setListSPGiaCong(List<ISanPham> listSPGiaCong);
	public List<ISanPham> getListSPMuaHang();
	public void setListSPMuaHang(List<ISanPham> listSPMuaHang) ;

	//tạo danh sách sản phẩm sản xuất tại Traphaco
	public void setDieuKien();
	
	public void createRS();
	
	//tạo danh sách sản phẩm gia công
	public void createRSGiaCong();
	
	//tạo danh sách sản phẩm sản xuất mua hàng
	public void createRSMuaHang();
	
}
