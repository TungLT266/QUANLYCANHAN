package geso.traphaco.erp.beans.kehoachkinhdoanh;

import java.util.List;

public interface IKenhbanhang
{
	public String getId();
	public void setId(String id);

	public String getMakenh();
	public void setMakenh(String makenh);

	public String getTenkenh();
	public void setTenkenh(String tenkenh);

	public List<ISanpham> getSanpham();
	public void setSanpham(List<ISanpham> list);
}
