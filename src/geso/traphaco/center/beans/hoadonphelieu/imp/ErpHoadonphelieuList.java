package geso.traphaco.center.beans.hoadonphelieu.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.beans.hoadonphelieu.IErpHoadonphelieuList;
import geso.traphaco.center.db.sql.dbutils;


public class ErpHoadonphelieuList extends Phan_Trang implements IErpHoadonphelieuList
{
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	String sohoadon;
	String khachhang;
	String tennguoitao="";
	
	String nppId;
	ResultSet khRs;
	
	ResultSet giamgiaRs;
	
	dbutils db;
	
	public ErpHoadonphelieuList()
	{
		this.userId = "";
		this.tennguoitao="";
		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		this.sohoadon= "";
		this.khachhang="";
		this.nppId = "";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public String getTennguoitao() {
		return tennguoitao;
	}
	public void setTennguoitao(String tennguoitao) {
		this.tennguoitao = tennguoitao;
	}
	
	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	public String getNppId() 
	{
		return this.nppId;
	}
	
	public void setKhachhang(String khachhang) 
	{
		this.khachhang = khachhang;
	}
	public String getKhachhang() 
	{
		return khachhang;
	}
	
	public void setSohoadon(String sohoadon)
	{
		this.sohoadon = sohoadon;
	}
	public String getSohoadon() 
	{
		return sohoadon;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
	}
	
	public void init(String query) 
	{
		this.getNppInfo();
		
		/*String query1 = " select PK_SEQ, MAFAST + '-' + TEN as TEN" +
		       	   " from KHACHHANG" +
		       	   " where TRANGTHAI = '1' and NPP_FK= "+ this.nppId +"  ";*/
		String query1="select  PK_SEQ, MAFAST + '-' + TEN AS TEN from nhaphanphoi where TRANGTHAI = '1' and tructhuoc_fk=1 and loainpp=4 ";
		this.khRs = db.get(query1);
		
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = " select a.pk_seq, d.ten as khTen, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua , " +
				  "        a.sohoadon, a.ngayhoadon, a.vat , a.avat as tongtien     " +
				  " from ERP_HoaDonPheLieuTT a inner join NhanVien b on a.nguoitao = b.pk_seq      " +
				  "     inner join nhanvien c on a.nguoisua = c.pk_seq" +
				  "     inner join nhaphanphoi d on a.khachhang_fk = d.pk_seq   " ;

		}
		
		this.giamgiaRs = createSplittingData(50, 10, "pk_seq desc", sql);
		
	}

	public void DbClose() 
	{
		try 
		{
			if(this.giamgiaRs != null)
				this.giamgiaRs.close();
			
			if(this.khRs != null)
				this.khRs.close();
			
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getKhRs() 
	{
		return this.khRs;
	}

	public void setKhRs(ResultSet khRs) 
	{
		this.khRs = khRs;
	}
	
	public ResultSet getGiamgiaRs() 
	{
		return this.giamgiaRs;
	}

	public void setGiamgiaRs(ResultSet giamgiaRs) 
	{
		this.giamgiaRs = giamgiaRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}



}
