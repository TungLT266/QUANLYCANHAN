package geso.traphaco.erp.beans.thutien.imp;

import geso.traphaco.erp.beans.thutien.IDinhkhoanco;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Dinhkhoanco implements IDinhkhoanco
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String taikhoanId;
	String doituongId;
	
	String doituongdinhkhoan;
	String sotien;
	String sotienNT;

	dbutils db;
	
	public Dinhkhoanco()
	{
		this.taikhoanId = "";
		this.doituongId ="";
		
		this.doituongdinhkhoan = "";
		this.sotien = "";
		this.sotienNT ="";
		
	}

	public Dinhkhoanco(String taikhoanId, String doituongdinhkhoan,String doituongId, String sotienNT, String sotien)
	{
		this.taikhoanId = taikhoanId;
		this.doituongId = doituongId;		
		this.doituongdinhkhoan = doituongdinhkhoan;
		this.sotienNT = sotienNT;
		this.sotien = sotien;
	}

	public String getTaikhoanId()
	{
		return this.taikhoanId ;
	}

	public void setTaikhoanId(String taikhoanId) 
	{
		this.taikhoanId = taikhoanId ;
	}


	public String getDoituongId()
	{
		return this.doituongId ;
	}

	public void setDoituongId(String doituongId) 
	{
		this.doituongId = doituongId;
	}

	public String getDoituongdinhkhoan() 
	{
		return this.doituongdinhkhoan ;
	}

	public void setDoituongdinhkhoan(String doituongdinhkhoan) 
	{
		this.doituongdinhkhoan = doituongdinhkhoan ;
	}

	public String getSotien() 
	{
		return this.sotien ;
	}

	public void setSotien(String sotien) 
	{
		this.sotien = sotien;
	}

	public String CheckDoiTuongDinhKhoan(String dinhkhoanco) {
		db = new dbutils();
		
	
		String dungchokho = "";
		String dungchonganhang = "";
		String dungchoncc = "";
		String dungchotaisan = "";
		String dungchokhachhang = "";
		String dungchonhanvien = "";
		String dungchodoituongkhac = "";
		String dungnoibo="";
		String query= "";
		this.doituongdinhkhoan = "0";
		
		if(this.taikhoanId.trim().length() > 0)
		{
			query = "Select isnull(dungchokho,0) dungchokho,isnull(dungchonganhang,0) dungchonganhang,isnull(dungchoncc,0) dungchoncc " +
					",isnull(dungchotaisan,0) dungchotaisan,isnull(dungchokhachhang,0) dungchokhachhang,isnull(dungchonhanvien,0) dungchonhanvien, " +
					"isnull(dungchodoituongkhac,0) dungchodoituongkhac, isnull(dungnoibo,0) dungnoibo from ERP_TAIKHOANKT where  PK_SEQ = '" + dinhkhoanco +"'";
			System.out.println(query);
			ResultSet dtrs = this.db.get(query);
			try {
				while(dtrs.next())
				{
					dungchokho = dtrs.getString("dungchokho");
					dungchonganhang = dtrs.getString("dungchonganhang");
					dungchoncc = dtrs.getString("dungchoncc");
					dungchotaisan = dtrs.getString("dungchotaisan");
					dungchokhachhang = dtrs.getString("dungchokhachhang");
					dungchonhanvien = dtrs.getString("dungchonhanvien");
					dungchodoituongkhac = dtrs.getString("dungchodoituongkhac");	
					dungnoibo = dtrs.getString("dungnoibo");	
				}
				dtrs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(dungchokho.equals("1"))
			{
				this.doituongdinhkhoan = "1";
			}
			else if(dungchonganhang.equals("1"))
			{
				this.doituongdinhkhoan = "2";
			}
			else if(dungchoncc.equals("1"))
			{
				this.doituongdinhkhoan = "3";
			}
			else if(dungchotaisan.equals("1"))
			{
				this.doituongdinhkhoan = "4";
			}
			else if(dungchokhachhang.equals("1"))
			{
				this.doituongdinhkhoan = "5";
			}
			else if(dungchonhanvien.equals("1"))
			{
				this.doituongdinhkhoan = "6";
			}else if(dungnoibo.equals("1"))
			{
				this.doituongdinhkhoan = "7";
			}else if(dungchodoituongkhac.equals("1"))
			{
				this.doituongdinhkhoan = "8";
			}
		}
		return this.doituongdinhkhoan;
	}
	
	public String getSotienNT() 
	{
		return this.sotienNT ;
	}

	public void setSotienNT(String sotienNT) 
	{
		this.sotienNT = sotienNT;
	}
	public ResultSet getDoiTuong()
	{
		ResultSet doituongrs = null;
		String query="";
		if(this.doituongdinhkhoan.equals("1"))  query ="select PK_SEQ, MA , TEN from SANPHAM where TRANGTHAI = 1 ";
		else if (this.doituongdinhkhoan.equals("2"))query= "select PK_SEQ, MA , TEN from ERP_NGANHANG where TRANGTHAI = 1 ";
		else if (this.doituongdinhkhoan.equals("3")) query= "select PK_SEQ, MA , TEN from ERP_NHACUNGCAP where TRANGTHAI = 1 and duyet = '1'";
   	   	else if (this.doituongdinhkhoan.equals("4")) query= "select PK_SEQ, MA , TEN from ERP_TAISANCODINH where TRANGTHAI = 1 ";
   	   	else if (this.doituongdinhkhoan.equals("5")) query ="select PK_SEQ, MAFAST AS MA, TEN from NHAPHANPHOI where TRANGTHAI = 1 AND PK_SEQ != 1 ";
   	   	else if (this.doituongdinhkhoan.equals("6")) query="select PK_SEQ, MA , TEN from ERP_NHANVIEN where TRANGTHAI = 1 ";
   	 	else if (this.doituongdinhkhoan.equals("7")) query="select PK_SEQ, MA , TEN from NHAPHANPHOI where TRANGTHAI = 1 and ISNULL(congnochung,0)=1";
   	 	else if (this.doituongdinhkhoan.equals("8")) query="select PK_SEQ, MADOITUONG AS MA , TENDOITUONG AS TEN from ERP_DOITUONGKHAC where TRANGTHAI = 1 ";
		
		System.out.println("query :"+query);
		doituongrs= db.getScrol(query);
		return doituongrs;
	}

}
