package qlcn.pages.taikhoan.beans;

import java.util.List;

import qlcn.center.util.IPhan_Trang;
import qlcn.pages.taikhoan.beans.imp.TaiKhoanLogList;

public interface ITaiKhoanLog extends IPhan_Trang {
	public void init();

	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);

	public String getMsg();
	public void setMsg(String msg);

	public List<TaiKhoanLogList> getLogList();
	public void setLogList(List<TaiKhoanLogList> logList);

	public String getID();
	public void setID(String iD);
}
