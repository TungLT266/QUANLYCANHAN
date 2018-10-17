package geso.traphaco.distributor.beans.tuyenbanhang;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface ITuyenbanhangList extends IPhan_Trang, Serializable
{

	public String getUserId(); // neu dang nhap la nha phan phoi thi hieu ngam
								// la nppID

	public void setUserId(String userId);

	public String getTuyenbh();

	public void setTuyenbh(String tuyenbh);

	public ResultSet getDdkd();

	public void setDdkd(ResultSet ddkd);

	public String getDdkdId();

	public void setDdkdId(String ddkdId);
	
	public String getMafast();

	public void setMafast(String mafast);
	
	public String getMakh();

	public void setMakh(String makh);

	public String getNppId();

	public void setNppId(String nppId);

	public String getNppTen();

	public void setNppTen(String nppTen);

	public String getSitecode();

	public void setSitecode(String sitecode);

	public ResultSet getTbhRs();

	public void setTbhRs(ResultSet tbhRs);

	public void init(String search);

	public void DBclose();

}
