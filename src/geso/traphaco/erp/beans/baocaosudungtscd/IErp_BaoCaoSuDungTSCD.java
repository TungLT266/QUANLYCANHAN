package geso.traphaco.erp.beans.baocaosudungtscd;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IErp_BaoCaoSuDungTSCD {
	public void setuserId(String userId);

	public String getuserId();

	public void setCtyId(String ctyId);

	public String getCtyId();

	public void setMsg(String msg);

	public String getMsg();

	public String getDate();

	public void setMonth(String month);

	public String getMonth();

	public void setYear(String year);

	public String getYear();

	public String getCtyTen();

	public String getDiachi();

	public String getMasothue();

	public ResultSet getLoaiTaiSanRs();

	public void setLoaiTaiSanRs(ResultSet loaiTaiSanRs);

	public String getLoaiBaoCao();

	public void setLoaiBaoCao(String loaiBaoCao);

	public String getLoaiTaiSanId();

	public void setLoaiTaiSanId(String loaiTaiSanId);
	


	public void createRs();
	
	public void createRsBaoCao(String sqlBaoCao);
	
	public ResultSet getRsBaoCao();

	public void DBClose();

	public int[] getNam();

	public void setNam(int[] nam);
	public void getDataCongty();
	public String getDvthId() ;


	public void setDvthId(String dvthId);


	public ResultSet getDvthRs();


	public void setDvthRs(ResultSet dvthRs) ;
	
	public String getDonViTen();

}
