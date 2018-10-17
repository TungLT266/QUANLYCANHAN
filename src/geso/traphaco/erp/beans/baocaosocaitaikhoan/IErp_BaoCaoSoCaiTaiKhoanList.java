package geso.traphaco.erp.beans.baocaosocaitaikhoan;

import geso.dms.center.util.IPhan_Trang;
import geso.traphaco.center.util.DKLocBaoCaoKeToan;
import geso.traphaco.center.util.PhatSinhKeToanItem;


import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

public interface IErp_BaoCaoSoCaiTaiKhoanList  extends Serializable, IPhan_Trang{
	public void init();
	public void DBClose();
	
	public int getNum();
	public void setNum(int num);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public int getCurrentPages();
	public void setCurrentPages(int currentPages);
	


	public void setMsg(String msg);
	public String getMsg();

	public void setCongTyId(String congTyId);
	public String getCongTyId();

	public void setDieuKienLoc(DKLocBaoCaoKeToan dieuKienLoc);
	public DKLocBaoCaoKeToan getDieuKienLoc();

	public void setViewList(List<PhatSinhKeToanItem> viewList);
	public List<PhatSinhKeToanItem> getViewList();
	
	public void setColunmNameList(List<String> colunmNameList);
	public List<String> getColunmNameList();
	public boolean exportExcel(ServletOutputStream outputStream, String fileName);
	
	public void getLoaiTaiKhoan();
}