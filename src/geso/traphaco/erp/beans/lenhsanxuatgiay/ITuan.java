package geso.traphaco.erp.beans.lenhsanxuatgiay;

import java.util.List;

public interface ITuan
{
	public int getSTT();
	public void setSTT(int stt);
	
	public long getTBBan();
	public void setTBBan(long tbBan);
	public int getTonkhoantoan();
	public void setTonkhoantoan(int tkan);
	
	public String getNgaybatdau();
	public void setNgaybatdau(String ngaybatdau);
	
	public long getTondau();
	public void setTondau(long tondau);
	
	public long getMaxDemand();
	public void setMaxDemand(long maxDemand);
	public long getMaxStock();
	public void setMaxStock(long maxStock);
	public long getMaxPlan();
	public void setMaxPlan(long maxPlan);
	public long getMaxProc();
	public void setMaxProc(long maxProc);
	
	public List<IChungtu> getAllChungTu();
	public void setAllChungTu(List<IChungtu> ctList);
	
	public void init();
	
}
