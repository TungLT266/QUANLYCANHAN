package geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp;

import geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpHoso;
 
public class ErpHoso  implements IErpHoso 
{
	 
	private static final long serialVersionUID = 1L;
	String userid="";
	String YcId;
	String Hoso;
	
	public ErpHoso()
	{
		 this.YcId="";
		 this.Hoso="";
	}
	
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.userid;
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		this.userid=userId;
	}

	@Override
	public String getYcId() {
		// TODO Auto-generated method stub
		return this.YcId;
	}

	@Override
	public void setYcId(String ycId) {
		// TODO Auto-generated method stub
		this.YcId=ycId;
	}

	@Override
	public String getHoso() {
		// TODO Auto-generated method stub
		return this.Hoso;
	}

	@Override
	public void setHoso(String hoso) {
		// TODO Auto-generated method stub
		this.Hoso=hoso;
	}
 
}
