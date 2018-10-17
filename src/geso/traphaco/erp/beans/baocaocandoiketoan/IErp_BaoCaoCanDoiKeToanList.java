package geso.traphaco.erp.beans.baocaocandoiketoan;

import geso.dms.center.util.IPhan_Trang;
import geso.traphaco.center.util.DKLocBaoCaoKeToan;
import geso.traphaco.center.util.PhatSinhKeToanItem;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.baocaocandoiketoan.IErp_BaoCaoCanDoiKeToanList;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletOutputStream;

public interface IErp_BaoCaoCanDoiKeToanList  extends Serializable, IPhan_Trang{
	public void init();
	
	public boolean exportExcel(ServletOutputStream outputStream, String fileName);
	
	public void DBClose();
	
	public int getNum();
	public void setNum(int num);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public int getCurrentPages();
	public void setCurrentPages(int currentPages);
	
	public dbutils getDb();
	public void setDb(dbutils db);

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
	
	public void setMonth(String month);
	
	public String getMonth();

	public void setYear(String year);

	public String getYear();	
	
	public void setMonthDK(String monthdk);
	
	public String getMonthDK();

	public void setYearDK(String yeardk);

	public String getYearDK();
	
	public ResultSet getRsBCCDKT();
	
	public ResultSet getRsBCHDKD();
	
	public ResultSet getRsBCLCTT();
	
	public ResultSet getRsBCCDPS();
}