package geso.traphaco.center.beans.donmuahang;
import java.sql.ResultSet;
import java.util.List;
public interface ITaodonhangKm {
	public String getId();
	public void setId(String id);	
	
	public String getNam();
	public void setNam(String nam);	
	
	
	public String getThang();
	public void setThang(String thang);	
	
	public String getUserId();
	public void setUserId(String userId);	
	
	public ResultSet getRsCTKM();
	public void getRsCTKM(ResultSet rs);
	
	public String getNppChon();
	public void setNppChon(String NppChon);
	
	

	public String getNppChon_ThanhToanTien();
	public void setNppChon_ThanhToanTien(String NppChon_ThanhToanTien);

	
	public ResultSet getRsNpp();
	public void setRsNpp(ResultSet rs);

	public ResultSet getRsKenh();
	public void getRsKenh(ResultSet rs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);	
	
	public ResultSet getRsSp();
	public void getRsSp(ResultSet rs);
	
	public void Init();
	public void LoadSpKm();
	
	public boolean ThucHien();
	
	public String getMsg();
	
	public void setMsg(String msg);	
	
	public void setCTKMChon(String str);
	
	public String getCTKMChon();

	public void setListSanpham( List<ISanPhamTraKM> list);
	
	public List<ISanPhamTraKM>  getListSanPham();
	
	
}
