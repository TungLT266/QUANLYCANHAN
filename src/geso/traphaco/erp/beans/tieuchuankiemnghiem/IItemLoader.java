package geso.traphaco.erp.beans.tieuchuankiemnghiem;

import java.util.List;

public interface IItemLoader {
	public String getPk_seq();
	public void setPk_seq(String pk_seq);
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
	public String getChon();
	public void setChon(String chon);
	public String getGhiChu();
	public void setGhiChu(String ghiChu);
	public List<IItemLoader> getPpThuNghiemList();
	public void setPpThuNghiemList(List<IItemLoader> ppThuNghiemList);
}
