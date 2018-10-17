package geso.traphaco.erp.beans.bcthekho;

import java.sql.ResultSet;
import java.util.List;

public interface IBaocao 
{
	public ResultSet getChungloaiRs();
	public void setChungloaiRs(ResultSet clRs);
	public String getChungloaiIds();
	public void setChungloaiIds(String loaispIds);
	
	public ResultSet getLoaiSanPhamRs();
	public void setLoaiSanPhamRs(ResultSet loaisp);
	public String getLoaiSanPhamIds();
	public void setLoaiSanPhamIds(String loaispIds);
	
	public ResultSet getMaSanPhamRs();
	public void setMaSanPhamRs(ResultSet loaisp);
	public String getMaSanPhamIds();
	public void setMaSanPhamIds(String maspIds);
	public void initview_BOOKED(String khott_fk, String sanphamId, String tungay, String denngay);
	
	public ResultSet getSP_booked_detail();
	public void setSP_booked_detail(ResultSet sp_detail);
	public ResultSet getNdnhapRs();
	public void setNdnhapRs(ResultSet ndnhapRs);
	public String getNdnhapIds();
	public void setNdnhapIds(String ndnhapIds);
	
	public ResultSet getNdxuatRs();
	public void setNdxuatRs(ResultSet ndxuatRs);
	public String getNdxuatIds();
	public void setNdxuatIds(String ndxuatIds);
	
	public ResultSet getSanPhamRs();
	public void setSanPhamRs(ResultSet sanpham);
	public String getSanPhamIds();
	public void setSanPhamIds(String spIds);
	
	public String getCheck_SpCoPhatSinh();
	public void setCheck_SpCoPhatSinh(String sp_cophatsinh);
	
	
	public ResultSet getKhoRs();
	public void setKhoRs(ResultSet khoRs);
	public String getKhoIds();
	public void setKhoIds(String khoId);
	public String getKhoTen();
	public void setKhoTen(String khoTen);
	
	public String getDvkdIds();
	
	public void setDvkdIds(String dvkdIds);

	public ResultSet getDvkdRs();
	
	public void setDvkdRs(ResultSet dvkdRs); 

	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen();
	public void setUserTen(String userTen);
	
	public String getTuNgay();
	public void setTuNgay(String tungay);
	public String getDenNgay();
	public void setDenNgay(String denngay);
	
	public String getFlag();
	public void setFlag(String flag);
	
 
	
	public String getHangchokiem();
	public void setHangchokiem(String chokiem);
	public String getPivot();
	public void setPivot(String pivot);
	
	public String getXemtheolo();
	public void setXemtheolo(String Xemtheolo);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void createRs();
	public void createRsBCKHO();
	public void close();
	
	public String getspId();
	public void setspId(String lspId);
	public String getLspId();
	public void setLspId(String lspId);
	
	

	public ResultSet getSP_detailRs();
	public void setSP_detailRs(ResultSet sp_detail);
	
	public ResultSet getTondau();
	public void setTondau(ResultSet tondau);
	
	public String getSolo();
	public void setSolo(String solo);
	
	public void initview();
	
	public ResultSet get_dvtRs();
	public void set_dvtRs(ResultSet dvtRs);
	
	public String  get_dvtId();
	public void set_dvtId(String  dvtId);
	
	public Double  get_quydoi_dvt();
	public void set_quydoi_dvt(double  quydoi_dvt);
	
	public List<ISanpham> get_chitiet_thekho();
	public void set_chitiet_thekho(List<ISanpham> chitiet_thekho);
		
}
