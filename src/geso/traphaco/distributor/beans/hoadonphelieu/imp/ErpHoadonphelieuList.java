package geso.traphaco.distributor.beans.hoadonphelieu.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.beans.hoadonphelieu.IErpHoadonphelieuList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;


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
		
		String query1 = " select PK_SEQ, MAFAST + '-' + TEN as TEN" +
		       	   " from KHACHHANG" +
		       	   " where TRANGTHAI = '1' and NPP_FK= "+ this.nppId +"  ";
		this.khRs = db.get(query1);
		
		String sql = "";
		
//		if(query.length() > 0)
//			sql = query;
//		else
//		{	
			sql = " select a.pk_seq, case when isnull(a.isnpp,0) = 0 then d.ten when isnull(a.isnpp,0) = 1 then e.ten end as khTen, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua , \n" +
				  "        a.sohoadon, a.ngayhoadon, a.vat , a.avat as tongtien     \n" +
				  " from ERP_HoaDonPheLieu a inner join NhanVien b on a.nguoitao = b.pk_seq      \n" +
				  "     inner join nhanvien c on a.nguoisua = c.pk_seq \n" +
				  "     left join KhachHang d on a.khachhang_fk = d.pk_seq   \n" +
				  "		left join NHAPHANPHOI e on a.khachhang_fk = e.PK_SEQ \n"+
				  " where a.congty_fk = "+ this.congtyId +" " ;
			
			Utility util = new Utility();
			if(this.tennguoitao.length() > 0)
				sql += " and b.ten like N'%" + this.tennguoitao + "%' ";
			if(this.diengiai.length() > 0)
				sql += " and a.diengiai like N'%" + this.diengiai + "%' ";
			
			if(this.trangthai.length() > 0)
				sql += " and a.trangthai = '" + this.trangthai + "' ";
			
			if(this.sohoadon.length() > 0)
			{
				sql += " and a.sohoadon like N'%" + this.sohoadon + "%' ";
			}
			if(this.ma.length() > 0)
			{
				sql += " and a.pk_seq like N'%" + this.ma + "%' ";
			}
			if(this.khachhang.length() > 0)
			{
				sql += " and (dbo.ftBoDau(d.ten)) like N'%" + util.replaceAEIOU(this.khachhang) + "%'  " +
						"or  dbo.ftBoDau(d.mafast) like N'%"+ util.replaceAEIOU(this.khachhang) +"%' or dbo.ftBoDau(d.pk_seq) like N'%"+ util.replaceAEIOU(this.khachhang) +"%'";
				
			}
			
			
			
//			System.out.println(sql);

//		}
		
		this.giamgiaRs = createSplittingDataNew(this.db, 50, 10, "pk_seq desc", sql);
		
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
