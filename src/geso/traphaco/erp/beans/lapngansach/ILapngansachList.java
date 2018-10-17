package geso.traphaco.erp.beans.lapngansach;

import java.sql.ResultSet;

public interface ILapngansachList {
	public String getId();
	
	public void setId(String Id);
	
	public String getUserId();
	
	public void setUserId(String userId);

	public String getNgay();
	
	public void setNgay(String ngay);
	
	public String getThang();
	
	public ResultSet getTtcplist();
	public void setTtcplist(ResultSet ttcplist);
	
	public String getTtcpId();
	public void setTtcpId(String ttcpId);
	
	public void setThang(String thang);	
	
	public String getCtyId();
	
	public void setCtyId(String ctyId);

	public String getDiengiai();
	
	public void setDiengiai(String diengiai);
	
	public void createBudget();
	
	public ResultSet getCtylist();
	
	public void setCtylist(ResultSet ctylist);

	public String getMsg();
	
	public void setMsg(String msg);

	public ResultSet getNgansach();
	
	public void setNgansach(ResultSet nslist);
	
	public void init();
	
	public void initTTCP();
	
	public void initUpdate();
	
	public void Delete(String Id);
	
	public String getNamNew();

	public String getNamUpdate();
		
	public void setHieuluc(String hieuluc);
	
	public void init_duyetngansach();

	public ResultSet getNamlist();
	
	public void setNamlist(ResultSet namlist);

	public String getNam();
	
	public void setNam(String nam);
	
	public void Duyet(String Id);
	
	public boolean Hieuluc(String Id);
	
	public String getHieuluc();

	public String getCopyId();
	
	public void setCopyId(String copyId);
	
	public ResultSet getNgansachCopy();
	
	public void CopyNgansach();
	
	public String[] getCP_DuocChapNhan();
	public void setCP_DuocChapNhan(String[] CP_DuocChapNhan);
	public String[] getCP_BiLoai();
	public void setCP_BiLoai(String[] CP_BiLoai);
	public String[] getCP_ThueThuNhap();
	public void setCP_ThueThuNhap(String[] CP_ThueThuNhap);
	
	
	public void DBClose();

}
