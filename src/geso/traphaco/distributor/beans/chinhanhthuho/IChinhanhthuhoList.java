package geso.traphaco.distributor.beans.chinhanhthuho;

import geso.traphaco.center.util.Erp_ListItem;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.PhanTrang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IChinhanhthuhoList extends Serializable, IPhan_Trang
{
	public void DBclose() ;
	public void init();
	public String getTungay();
	public void setTungay(String tungay);

	
	public String getDenngay();

	
	public void setDenngay(String denngay);
	
		
		
		
		public String getUserId();

		public void setUserId(String userId);

		

		public int getLastPage() ;

		public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage);

		public String getMsg();

		public void setMsg(String msg);
		
		public String getTenKH();


		public void setTenKH(String tenKH);
		
		public List<Erp_ListItem> getViewList();

		public void setViewList(List<Erp_ListItem> viewList);

		public int[] getListPages();
		public void setListPages(int[] listPages);
		
		public int getCurrentPages();
		
		public void setCurrentPages(int currentPages);
		

		public ResultSet getCnthRs();


		public void setCnthRs(ResultSet cnthRs);
		
		public void setChiNhanhRs(ResultSet chiNhanhRs);
		public ResultSet getChiNhanhRs();
		public void setChiNhanhId(String chiNhanhId);
		public String getChiNhanhId();


		public String getCongtyId();


		public void setCongtyId(String congtyId);

		
}


	
