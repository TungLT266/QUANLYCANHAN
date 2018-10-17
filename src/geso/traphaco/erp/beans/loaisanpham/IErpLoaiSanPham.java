package geso.traphaco.erp.beans.loaisanpham;
import java.sql.ResultSet;
public interface IErpLoaiSanPham {
	public String getId();

	public void setId(String id);

	public String getMsg();

	public void setMsg(String msg);

	public String getMa();

	public void setMa(String ma);

	public String getTen();

	public void setTen(String ten);

	public void setUserId(String userId);

	public String getUserId();

	public String getTaiKhoan();

	public String getTrangThai();

	public void setTrangThai(String trangthai);

	public void setTaiKhoan(String taikhoan);

	public void setTaiKhoanRs(ResultSet taikhoan);

	public ResultSet getTaiKhoanRs();

	public ResultSet getLoaiSanPhamRs();

	public void setLoaiSanPhamRs(ResultSet loaisanpham);

	public void CreateRs();

	public void Init();

	public boolean Search();

	public boolean Update();

	public boolean Create();

	public boolean CheckUnique();

	public boolean CheckReferences(String column, String table);

	public void Close();

	public boolean Delete();

	public String getCtyId();
	
	public void setCtyId(String ctyId);
	
	public void setChixem(String chixem);
	public String getChixem();
	
	
	public String getIsSanxuat();
	public void setIsSanxuat(String isSanxuat);
	
	public String getThuoclsp();
	public void setThuoclsp(String thuoclsp);
}
