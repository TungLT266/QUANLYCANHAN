package geso.traphaco.erp.beans.thongtinsanphamgiay;

import java.util.List;

import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.Erp_TaiSanCoDinh;

public interface IMayMoc_SanPham {

	public String getPK_SEQSanPhamKiemDinh();
	public void setPK_SEQSanPhamKiemDinh(String pK_SEQSanPhamKiemDinh);
	public void setMayMocKiemDinh(IErp_TaiSanCoDinh mayMocKiemDinh);
	IErp_TaiSanCoDinh getMayMocKiemDinh();
	IThongtinNCC getNhaCungCap();
	void setNhaCungCap(IThongtinNCC nhaCungCap);
}
