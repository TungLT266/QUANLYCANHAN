package geso.traphaco.erp.beans.khoasothang.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.beans.khoasothang.IErpTaphopdulieuDMS;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpTaphopdulieuDMS implements IErpTaphopdulieuDMS
{
	String userId;
	String thang;
	String nam;
	
	ResultSet giavonRs;
	ResultSet giavonCTRs;
	
	String msg;
	
	public ErpTaphopdulieuDMS()
	{
		this.thang = Integer.toString( Integer.parseInt( getDateTime().split("-")[1] ) );
		this.nam = getDateTime().split("-")[0];
		this.msg = "";
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getThang()
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;
	}

	public String getNam()
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
	
	public void createRs() 
	{
		
	}

	public ResultSet getGiavonRs()
	{
		return this.giavonRs;
	}
	
	public ResultSet getGiavonCTRs() 
	{
		return this.giavonCTRs;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void DbClose() 
	{
	}

	String congtyId;
	String nppId;
	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}

	public String getNppId() {

		return this.nppId;
	}

	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	public void TapHopDuLieu() 
	{
		try
		{
			dbutils db = new dbutils();
			
			//Tập hợp dữ liệu xuất bán bên DMS qua, gồm các định khoản xuất kho
			String query = "delete ERP_PHATSINHKETOAN where loaichungtu = N'Xuất kho bán hàng' " + 
						   " 	and month( ngaychungtu ) = '" + this.thang + "' and year ( NGAYCHUNGTU ) = '" + this.nam + "' ";
			db.update(query);
			
			query =  " insert ERP_PHATSINHKETOAN( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, noidungnhapxuat_fk, NO, CO, doituong, madoituong, LOAIDOITUONG,  "+
					 " 		SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, tigia_fk, tonggiatri, tonggiatriNT, khoanmuc, mahang, tenhang, donvi, solo, SANPHAM_FK, IS_NO, IS_CO ) "+
					 " select ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, noidungnhapxuat_fk, NO, CO, doituong, madoituong, LOAIDOITUONG, "+
					 " 		SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, tigia_fk, tonggiatri, tonggiatriNT, khoanmuc, mahang, tenhang, donvi, solo, SANPHAM_FK, IS_NO, IS_CO "+
					 " from LINK_DMS.DataCenter.dbo.ERP_PHATSINHKETOAN  "+
					 " where loaichungtu = N'Xuất kho bán hàng' and month( ngaychungtu ) = '" + this.thang + "' and year ( NGAYCHUNGTU ) = '" + this.nam + "' ";
			
			System.out.println(":: TAP HOP DU LIEU DMS: " + query);
			if( !db.update(query) )
				this.msg = "Lỗi tập hợp dữ liệu.";
			else
				this.msg = "Tập hợp dữ liệu DMS tháng " + this.thang + ", năm " + this.nam  + " thành công.";
			
			db.shutDown();
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
			this.msg = "Lỗi tập hợp dữ liệu " + ex.getMessage();
		}
		
	}


	
}
