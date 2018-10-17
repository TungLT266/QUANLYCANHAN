package geso.traphaco.erp.beans.phieuphache;

import geso.traphaco.erp.beans.phieuphache.imp.ErpPhieuPhaChe_SP_ChiTiet;

import java.util.List;

public interface IErpPhieuPhaChe_SanPham {
	public String getIdsp();
	public void setIdsp(String idsp);

	public String getMasp();
	public void setMasp(String masp);

	public String getTensp();
	public void setTensp(String tensp);

	public String getDvt();
	public void setDvt(String dvt);

	public String getKhoxuat();
	public void setKhoxuat(String khoxuat);

	public String getSoluonglythuyet();
	public void setSoluonglythuyet(String soluonglythuyet);
	
	public List<ErpPhieuPhaChe_SP_ChiTiet> getSpChitietList();
	public void setSpChitietList(List<ErpPhieuPhaChe_SP_ChiTiet> spChitietList);
	
	public void createSpChitietList();
	
	public String getTongxuat();
	public void setTongxuat(String tongxuat);
	
	public String getTlpcTtId();
	public void setTlpcTtId(String tlpcTtId);
}
