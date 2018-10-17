package geso.traphaco.erp.beans.thongtinsanphamgiay.imp;

import geso.traphaco.erp.beans.thongtinsanphamgiay.IErp_TaiSanCoDinh;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IMayMoc_SanPham;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinNCC;

public class MayMoc_SanPham implements IMayMoc_SanPham{
	private String PK_SEQSanPhamKiemDinh;
	private IErp_TaiSanCoDinh mayMocKiemDinh;
	private IThongtinNCC nhaCungCap;
	
	public MayMoc_SanPham() {
		super();
	}
	public MayMoc_SanPham(String pK_SEQSanPhamKiemDinh,
			IErp_TaiSanCoDinh mayMocKiemDinh) {
		super();
		PK_SEQSanPhamKiemDinh = pK_SEQSanPhamKiemDinh;
		this.mayMocKiemDinh = mayMocKiemDinh;
	}
	@Override
	public String getPK_SEQSanPhamKiemDinh() {
		return PK_SEQSanPhamKiemDinh;
	}
	@Override
	public void setPK_SEQSanPhamKiemDinh(String pK_SEQSanPhamKiemDinh) {
		PK_SEQSanPhamKiemDinh = pK_SEQSanPhamKiemDinh;
	}
	@Override
	public IErp_TaiSanCoDinh getMayMocKiemDinh() {
		return mayMocKiemDinh;
	}
	@Override
	public void setMayMocKiemDinh(IErp_TaiSanCoDinh mayMocKiemDinh) {
		this.mayMocKiemDinh = mayMocKiemDinh;
	}
	@Override
	public IThongtinNCC getNhaCungCap() {
		return nhaCungCap;
	}
	@Override
	public void setNhaCungCap(IThongtinNCC nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}
	
	
	
}
