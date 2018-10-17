package geso.traphaco.erp.beans.phongbansx;

import java.sql.ResultSet;

public interface IErpPhongbanSX {
	public String getUserId();

	public void setUserId(String userId);

	public String getCongtyId();

	public void setCongtyId(String congtyId);

	public String getId();

	public void setId(String Id);

	public String getMa();

	public void setMa(String ma);

	public String getDiengiai();

	public void setDiengiai(String diengiai);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getMsg();

	public void setMsg(String msg);

	public boolean create();

	public boolean update();

	public void init();

	public void creaters();

	public void DbClose();

	public String[] getSpDiengiai();

	public void setSpDiengiai(String[] spDiengiai);

	public String[] getSpDinhmuctu();

	public void setSpDinhmuctu(String[] spDinhmuctu);

	public String[] getSpDinhmucden();

	public void setSpDinhmucden(String[] spDinhmucden);

	public String[] getSpDonvi();

	public void setSpDonvi(String[] spDonvi);

	public ResultSet getDvtRs();

	public void setDvtRs(ResultSet dvtRs);
	
	public String getIsChangeMa();
	public void setIsChangeMa(String isChangeMa);
}
