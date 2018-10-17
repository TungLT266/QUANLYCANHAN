package geso.traphaco.center.beans.chuyennpp;

import geso.traphaco.center.beans.stockintransit.IStockintransit;

public interface IChuyenNpp extends IStockintransit {

	public String getNppIdFrom();

	public void setNppIdFrom(String nppIdFrom);

	public String getNppIdTo();

	public void setNppIdTo(String nppIdTo);

	public String getMsg();

	public void setMsg(String msg);

	public String getId();

	public void setId(String id);

	public String getNgayKs();

	public void setNgayKs(String ngayKs);

	public boolean TransferData();

	public void closeDB();

}
