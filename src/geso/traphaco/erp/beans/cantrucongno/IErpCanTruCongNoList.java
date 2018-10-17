package geso.traphaco.erp.beans.cantrucongno;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.center.util.IThongTinHienThi;

import java.io.Serializable;
import java.util.List;

public interface IErpCanTruCongNoList extends Serializable, IPhan_Trang
{

	
	public List<IThongTinHienThi> getHienthiList();
	public void setHienthiList(List<IThongTinHienThi> hienthiList);
	
	public String getId();
	public void setId(String id) ;
	
	public String getNcc();
	public void setNcc(String ncc);
	
	public String getKh();
	public void setKh(String kh) ;
	
	public String getTrangthai() ;
	public void setTrangthai(String trangthai);

	public String getTongtien() ;
	public void setTongtien(String tongtien);

	public String getNgaytao() ;
	public void setNgaytao(String ngaytao) ;

	public String getNccId() ;
	public void setNccId(String nccId);
	
	public String getKhId() ;
	public void setKhId(String khId) ;
	
	public String getMsg() ;
	public void setMsg(String msg);
	
	public void init();
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	public void DBClose();
	public void NewDbUtil();
	public String getUserId();
	public void setUserId(String userId);
	
	
}
