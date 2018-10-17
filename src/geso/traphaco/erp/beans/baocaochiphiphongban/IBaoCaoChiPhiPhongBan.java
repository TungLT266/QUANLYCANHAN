package geso.traphaco.erp.beans.baocaochiphiphongban;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IBaoCaoChiPhiPhongBan extends Serializable{

	
	public String getUserId();
	public void setUserId(String userId);

	public String getCtyId();
	public void setCtyId(String ctyId);

	public String getCtyTen();
	public void setCtyTen(String ctyTen);

	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay) ;

	public String[] getPhongBanIds();
	public void setPhongBanIds(String[] phongBanIds) ;

	
	public String getQueryTH();
	public void setQueryTH(String queryTH) ;

	public String getQueryCT();

	public void setQueryCT(String queryCT);

	public ResultSet getRsBaoCaoTongHop() ;
	public void setRsBaoCaoTongHop(ResultSet rsBaoCaoTongHop) ;


	public ResultSet getRsBaoCaoChiTiet() ;
	public void setRsBaoCaoChiTiet(ResultSet rsBaoCaoChiTiet) ;

	public String getDvthId();
	public void setDvthId(String dvthId);

	public ResultSet getDvthRs() ;
	public void setDvthRs(ResultSet dvthRs);

	public String getMsg();
	public void setMsg(String msg);

	public String getDvthTen();
	public void setDvthTen(String dvthTen) ;
	public void init();
	public void DBClose();
}
