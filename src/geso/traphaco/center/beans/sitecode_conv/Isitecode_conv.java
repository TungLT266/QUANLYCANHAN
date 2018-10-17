package geso.traphaco.center.beans.sitecode_conv;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
/*
----	THONG TIN BANG SITECODE_CONV
----	SITECODE:		MA BEN ERP
----	CONVSITECODE(NPP_DMS):	CHUYEN TOAN BO THONG TIN CUA NPP DMS SANG NPP ERP NÀY
*/
public interface Isitecode_conv  extends Serializable, IPhan_Trang
{
	public void setsitecode(String sitecode);
	public String getsitecode();
	public void setconvsitecode(String convsitecode);
	public String getconvsitecode();
	public void setngaytao(String ngaytao);
	public String getngaytao();
	public void setngaysua(String ngaysua);
	public String getngaysua();
	public void setten(String ten);
	public String getten();
	public void settennpptn(String tennpptn);
	public String gettennpptn();
	public void settrangthai(String trangthai);
	public String gettrangthai();
	public void setnguoitao(String nguoitao);
	public String getnguoitao();
	public void setnguoisua(String nguoisua);
	public String getnguoisua();
	public void Init(String sql);
	public ResultSet getsistecode_conv();
	public void setUserid(String userid);
	public String getUserid();
	public void setMsg(String smg);
	public String getMsg();
	public void setRsNppTienNhiem();
	public ResultSet getRsNppTienNhiem(); 
	public void setIdNppTienNhiem(String idnpptiennhiem);
	public String getIdNppTienNhiem();
	public boolean save();
	public boolean chot();
	public ResultSet getRsloctheokhuvuc();
	public void setRsKhuvuc();
	public String getKhuVucId();
	public void setkhuvucId(String khuvucid);
	public boolean TaoNPPMoi();
	public void NgayKhoaSo(String ngayks);
	public String getNgaykhoaso();

	public void DBClose();
	
	public String getDiachi();
	public void setDiachi(String diachi);
}
