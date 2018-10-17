package geso.traphaco.erp.beans.kiemdinhchatluong.imp;
 
import geso.traphaco.erp.beans.kiemdinhchatluong.*;
import geso.traphaco.center.util.Phan_Trang;
 
public class ErpSanphamduyet extends Phan_Trang implements IErpSanphamduyet 
{
	
	String Userid;
	String YcId;
	String Ngayduyet;
	String Nguoiduyet;
	String Landuyet;
	String Soluongduyet;
	String soluongdat;
	String Soluonghong;
	String Soluonglaymau;
	
	
	public ErpSanphamduyet()
	{ 
		
		  YcId="";
		  Ngayduyet="";
		  Nguoiduyet="";
		  Soluongduyet="";
		  soluongdat="";
		  Soluonghong="";
		  Soluonglaymau="";
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.Userid;
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		this.Userid=userId;
		
	}

	@Override
	public String getYcId() {
		// TODO Auto-generated method stub
		return YcId;
	}

	@Override
	public void setYcId(String ycId) {
		// TODO Auto-generated method stub
		this.YcId=ycId;
	}

	@Override
	public String getLanDuyet() {
		// TODO Auto-generated method stub
		return this.Landuyet;
	}

	@Override
	public void setLanDuyet(String landuyet) {
		// TODO Auto-generated method stub
		this.Landuyet=landuyet;
	}

	@Override
	public String getNgayDuyet() {
		// TODO Auto-generated method stub
		return this.Ngayduyet;
	}

	@Override
	public void setNgayDuyet(String ngayduyet) {
		// TODO Auto-generated method stub
		this.Ngayduyet=ngayduyet;
	}

	@Override
	public String getNguoiDuyet() {
		// TODO Auto-generated method stub
		return this.Nguoiduyet;
	}

	@Override
	public void setNguoiDuyet(String nguoiduyet) {
		// TODO Auto-generated method stub
		this.Nguoiduyet=nguoiduyet;
	}

	@Override
	public String getSoluongduyet() {
		// TODO Auto-generated method stub
		return this.Soluongduyet;
	}

	@Override
	public void setSoluongduyet(String soluong) {
		// TODO Auto-generated method stub
		this.Soluongduyet=soluong;
	}

	@Override
	public String getSoluongmau() {
		// TODO Auto-generated method stub
		return this.Soluonglaymau;
	}

	@Override
	public void setSoluongmau(String soluong) {
		// TODO Auto-generated method stub
		this.Soluonglaymau=soluong;
	}

	@Override
	public String getSoluongdat() {
		// TODO Auto-generated method stub
		return this.soluongdat;
	}

	@Override
	public void setSoluongdat(String Soluong) {
		// TODO Auto-generated method stub
		this.soluongdat=Soluong;
	}

	@Override
	public String getSoluonghong() {
		// TODO Auto-generated method stub
		return this.Soluonghong;
	}

	@Override
	public void setSoluonghong(String Soluong) {
		// TODO Auto-generated method stub
		this.Soluonghong=Soluong;
	}

}
