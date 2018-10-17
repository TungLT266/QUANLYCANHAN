package geso.traphaco.erp.beans.baocaosudungccdc;

import java.sql.ResultSet;

public interface IErp_BaoCaoSuDungCCDC {
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

	public ResultSet getNhomCCDCRs();

	public void setNhomCCDCRs(ResultSet nhomCCDCRs);

	public String getLoaiBaoCao();

	public void setLoaiBaoCao(String loaiBaoCao);

	public String getNhomCCDCId();

	public void setNhomCCDCId(String nhomCCDCId);
	


	public void createRs();
	
	public void createRsBaoCao(String sqlBaoCao);
	
	public ResultSet getRsBaoCao();

	public void DBClose();

	public int[] getNam();

	public void setNam(int[] nam);
	public void getDataCongty();
	
	public ResultSet getDvthRs() ;


	public void setDvthRs(ResultSet dvthRs) ;


	public String getDvthId() ;

	public void setDvthId(String dvthId) ;
	
	public String getDonViTen();
	

	public String getNppId();


	public void setNppId(String nppId);


}
