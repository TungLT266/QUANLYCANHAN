package geso.traphaco.erp.beans.hoadonkhac.imp;

import geso.traphaco.erp.beans.hoadonkhac.IErpHoadonkhacList;
import geso.dms.center.util.DinhKhoanKeToan;
import geso.dms.center.util.IDinhKhoanKeToan;
import geso.dms.center.util.IThongTinHienThi;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.ThongTinHienThi;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErpHoadonkhacList extends Phan_Trang implements IErpHoadonkhacList {
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	String sohoadon;
	String khachhang;
	String tennguoitao="";
	
	String khoanmucDTId;
	ResultSet khoanmucDTRs;
	ResultSet giamgiaRs;
	ResultSet nhanVienRs;
	String nhanVienId;
	
	dbutils db;
	List<IThongTinHienThi> hienthiList;
	
	String tungay;
	String denngay;
	
	public ErpHoadonkhacList()
	{
		this.userId = "";
		this.tennguoitao="";
		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		this.sohoadon= "";
		this.khachhang="";
		this.khoanmucDTId= "";
		this.msg = "";
		this.nhanVienId = "";
		
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
		this.db = new dbutils();
		this.tungay ="";
		this.denngay ="";
	}
	
	
	
	public String getNhanVienId() {
		return nhanVienId;
	}



	public void setNhanVienId(String nhanVienId) {
		this.nhanVienId = nhanVienId;
	}



	public ResultSet getNhanVienRs() {
		return nhanVienRs;
	}



	public void setNhanVienRs(ResultSet nhanVienRs) {
		this.nhanVienRs = nhanVienRs;
	}



	public String getTennguoitao() {
		return tennguoitao;
	}
	public void setTennguoitao(String tennguoitao) {
		this.tennguoitao = tennguoitao;
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

	public String getKhoanmucDTId() 
	{
		return this.khoanmucDTId;
	}

	public void setKhoanmucDTId(String khoanmucDTId) 
	{
		this.khoanmucDTId = khoanmucDTId;
	}
	
	public void setKhoanmucDTRs(ResultSet khoanmucDTRs) 
	{
		this.khoanmucDTRs = khoanmucDTRs;
	}
	
	public ResultSet getKhoanmucDTRs()
	{
		return khoanmucDTRs;
	}
	
	private String LayDuLieu(String id) {
		String query =
		" SELECT a.doanhthu_fk, isnull(a.loaick,'')as loaick, a.ngayhoadon, a.vat, isnull(c.ma,'') as trungtamdt, b.PK_SEQ KHACHHANG_FK, " +
		"		 case a.doanhthu_fk " +
		"			when 400004 then isnull(a.avat,0) " +
		"			when 400005 then isnull(a.avat,0) " +
		"			else ( select SUM( thanhtien ) from erp_HOADONKHAC_sanpham where HOADONKHAC_fk = a.pk_seq ) end as SOTIEN, b.TEN \n " +
		
		" FROM ERP_HOADONKHAC a " +
		" inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n " +
		" left join ERP_TRUNGTAMDOANHTHU c on a.trungtamdoanhthu_fk = c.pk_seq \n " +
		
		" where a.pk_seq = '" + id + "' \n ";
		
		System.out.println("Query: "+query);
		ResultSet rs = db.get(query);
		
		String taikhoanCO = "";
		String taikhoanCO_VAT = "";
		String taikhoanNO = "";
		String taikhoanNO_VAT = "";
		
		String laytk = "";
		try{
		while(rs.next())
		{
			double TONGSOTIEN = Math.round(rs.getDouble("SOTIEN"));
			double TONGVAT = Math.round(TONGSOTIEN * rs.getDouble("VAT") / 100 );
			
								
			if(rs.getString("doanhthu_fk").equals("400000")) { // HOA DON CK
				query = "SELECT CASE WHEN HD.KBH_FK = 100000 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000') \n " + //GT
						"ELSE CASE WHEN  HD.KBH_FK = 100001 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52120000') \n " +  //MT
						"ELSE CASE WHEN  HD.KBH_FK = 100007 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52140000') \n " +  //XK
						"ELSE (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52130000') \n " +									   //Khác
						"END END END AS TAIKHOAN_NO, KH.TAIKHOAN_FK AS TAIKHOAN_CO, \n " +
					
						"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_NO_VAT, \n " +
						"KH.TAIKHOAN_FK AS TAIKHOAN_CO_VAT \n " +
					
						"FROM ERP_HOADONKHAC HD \n " +
						"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
						"WHERE HD.PK_SEQ = '" + id + "' \n ";
		
				ResultSet rsTK = db.get(query);
				if(rsTK != null){
					rsTK.next();
					taikhoanNO =  rs.getString("TAIKHOAN_NO");
					taikhoanCO = rs.getString("TAIKHOAN_CO");
					taikhoanNO_VAT = rs.getString("TAIKHOAN_NO_VAT");
					taikhoanCO_VAT = rs.getString("TAIKHOAN_CO_VAT");
					rsTK.close();
				}else{
					taikhoanNO = "";
					taikhoanCO = "";
					taikhoanNO_VAT = "";
					taikhoanCO_VAT = "";
				}

				if(TONGSOTIEN < 0) TONGSOTIEN = TONGSOTIEN*(-1);
				if(TONGVAT < 0) TONGVAT = TONGVAT*(-1);			

				if(TONGSOTIEN > 0){
						laytk +=
							"	SELECT 	N'NỢ' NO_CO, a.pk_seq id,  " + taikhoanNO + " as SOHIEUTAIKHOAN, " + TONGSOTIEN + " as SOTIEN, \n"+
							"	b.TEN DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
							"	FROM 	ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							"   WHERE 	a.pk_seq = '"+id+"' \n"+
	
							" 	UNION ALL \n"+
	
							" 	SELECT 	N'CÓ' NO_CO, a.pk_seq id, "+taikhoanCO+" as SOHIEUTAIKHOAN, " +TONGSOTIEN+ " as SOTIEN, \n"+
							"	'' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+ 
							" 	FROM 	ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" 	WHERE 	a.pk_seq = '"+id+"' \n";
					}
					
				if(TONGVAT > 0){
						if(laytk.trim().length()>0) laytk += " UNION ALL \n";
						
						laytk +=
							" 	SELECT 	N'NỢ' NO_CO, a.pk_seq id, " + taikhoanNO_VAT+" as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+ 
							"	b.TEN DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
							" 	FROM 	ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" 	WHERE 	a.pk_seq = '"+id+"' \n"+
	
							" UNION ALL \n"+
	
							" SELECT N'CÓ' NO_CO, a.pk_seq id, "+taikhoanCO_VAT+"  as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+
							" '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"; 
							
					}
					
				}
				else if(rs.getString("doanhthu_fk").equals("400001")) // THU HOI CK
				{				
					query = "SELECT KH.TAIKHOAN_FK AS TAIKHOAN_NO, " +
							"CASE WHEN HD.KBH_FK = 100000 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000') \n " + //GT
							"ELSE CASE WHEN  HD.KBH_FK = 100001 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52120000') \n " +  //MT
							"ELSE CASE WHEN  HD.KBH_FK = 100007 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52140000') \n " +  //XK
							"ELSE (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52130000') \n " +									   //Khác
							"END END END AS TAIKHOAN_CO,  \n " +
					
							"KH.TAIKHOAN_FK AS TAIKHOAN_NO_VAT,  \n " +
							"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_CO_VAT " +
												
							"FROM ERP_HOADONKHAC HD \n " +
							"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
							"WHERE HD.PK_SEQ = '" + id + "' \n ";
			
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						rsTK.next();
						taikhoanNO =  rs.getString("TAIKHOAN_NO");
						taikhoanCO = rs.getString("TAIKHOAN_CO");
				
						taikhoanNO_VAT =  rs.getString("TAIKHOAN_NO_VAT");
						taikhoanCO_VAT = rs.getString("TAIKHOAN_CO_VAT");
						rsTK.close();
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}
			
										
					if(TONGSOTIEN > 0){ 
						laytk +=
							"	SELECT N'NỢ' NO_CO, a.pk_seq id, " + taikhoanNO + " as SOHIEUTAIKHOAN, \n"+
							"     	   " + TONGSOTIEN + " as SOTIEN, \n"+
							"	b.Ten as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
							"	FROM   ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							"   WHERE  a.pk_seq = '"+id+"' \n"+
	
							" UNION ALL \n"+
	
							" SELECT N'CÓ' NO_CO, a.pk_seq id, " + taikhoanCO +"  as SOHIEUTAIKHOAN, " + TONGSOTIEN + " as SOTIEN, \n"+
							"	  '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+ 
							" from ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n";
					}
					
					if(TONGVAT > 0){
						if(laytk.trim().length()>0) laytk += " UNION ALL \n";
						
						laytk +=
							" SELECT N'NỢ' NO_CO, a.pk_seq id, "+taikhoanNO_VAT+" as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+ 
							"	   b.Ten as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"+
	
							" UNION ALL \n"+
	
							" SELECT N'CÓ' NO_CO, a.pk_seq id, "+taikhoanCO_VAT+" as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+
							"	   '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"; 
							
					}
				}
				else if(rs.getString("doanhthu_fk").equals("400002")) { // HÓA ĐƠN ĐIỀU CHỈNH TĂNG
					
					query = "SELECT KH.TAIKHOAN_FK AS TAIKHOAN_NO, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = HD.TAIKHOANDOANHTHU_FK) AS TAIKHOAN_CO, \n " +
							"KH.TAIKHOAN_FK AS TAIKHOAN_NO_VAT,  " +
							"'33311000' AS TAIKHOAN_CO_VAT \n " +
							"FROM ERP_HOADONKHAC HD \n " +
							"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +

							"WHERE HD.PK_SEQ =  '" + id + "' \n ";
					
					System.out.println(query);
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						rsTK.next();
						taikhoanNO =  rsTK.getString("TAIKHOAN_NO");
						taikhoanCO = rsTK.getString("TAIKHOAN_CO");
				
						taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
						taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
						rsTK.close();
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}
//					System.out.println("" + TONGSOTIEN);
					if(TONGSOTIEN > 0){
						laytk+=
							" SELECT N'NỢ' NO_CO, a.pk_seq id, "+taikhoanNO+" as SOHIEUTAIKHOAN, " +TONGSOTIEN+ " as SOTIEN, \n"+
							"	   b.Ten DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
							" from ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"+
							
							" UNION ALL \n"+
							
							"	SELECT N'CÓ' NO_CO, a.pk_seq id, "+taikhoanCO+" as SOHIEUTAIKHOAN, \n"+
							"     " +TONGSOTIEN+ " as SOTIEN, \n"+
							"	   '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+ 
							"	FROM ERP_HOADONKHAC a  \n"+ 
							"   WHERE a.pk_seq = '"+id+"' \n";
						
						System.out.println(laytk);
					}
					
					if(TONGVAT > 0){
						if(laytk.trim().length()>0) laytk += " UNION ALL \n";
						
						laytk +=
							" SELECT N'NỢ' NO_CO, a.pk_seq id, "+taikhoanNO_VAT+" as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+ 
							"	   b.Ten DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"+
	
							" UNION ALL \n"+
	
							" SELECT N'CÓ' NO_CO, a.pk_seq id, "+taikhoanCO_VAT+"  as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+
							"	   '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"; 
						
						System.out.println(laytk);	
					}
					
				}
				else if(rs.getString("doanhthu_fk").equals("400003")){ //LOAI HD: HÓA ĐƠN ĐIỀU CHỈNH GIẢM
					query = "SELECT (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = HD.TAIKHOANDOANHTHU_FK) AS TAIKHOAN_NO, KH.TAIKHOAN_FK AS TAIKHOAN_CO, \n " +
					
							"'33311000' AS TAIKHOAN_NO_VAT, \n " +
							"KH.TAIKHOAN_FK AS TAIKHOAN_CO_VAT " +
					
							"FROM ERP_HOADONKHAC HD \n " +
							"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +

							"WHERE HD.PK_SEQ =  '" + id + "' \n ";
					
					System.out.println(query);
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						rsTK.next();
						taikhoanNO =  rsTK.getString("TAIKHOAN_NO");
						taikhoanCO = rsTK.getString("TAIKHOAN_CO");
		
						taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
						taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
						rsTK.close();
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}
	
					if(TONGSOTIEN < 0) TONGSOTIEN = TONGSOTIEN*(-1);
					if(TONGVAT < 0) TONGVAT = TONGVAT*(-1);
					
//					System.out.println("TONGSOTIEN: " + TONGSOTIEN);
					
					if(TONGSOTIEN > 0){
						laytk+=
							" SELECT N'NỢ' NO_CO, a.pk_seq id, " + taikhoanNO +" as SOHIEUTAIKHOAN, " + TONGSOTIEN + " as SOTIEN, \n"+
							" b.TEN AS DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
							" from ERP_HOADONKHAC a  inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+  
							" WHERE a.pk_seq = '"+id+"' \n"+
							
							" UNION ALL \n"+
							
							"	SELECT N'CÓ' NO_CO, a.pk_seq id, "+taikhoanCO+" as SOHIEUTAIKHOAN, \n"+
							"     " +TONGSOTIEN+ " as SOTIEN, \n"+
							"	'' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+ 
							"	FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							"   WHERE a.pk_seq = '"+id+"' \n";
						
						System.out.println(laytk);
					}
					
					if(TONGVAT > 0){
						if(laytk.trim().length()>0) laytk += " UNION ALL \n";
						
						laytk +=
							" SELECT N'NỢ' NO_CO, a.pk_seq id, "+taikhoanNO_VAT+" as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+ 
							" b.TEN AS DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"+
	
							" UNION ALL \n"+
	
							" SELECT N'CÓ' NO_CO, a.pk_seq id, "+taikhoanCO_VAT+"  as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+
							" '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"; 
						
						System.out.println(laytk);
					}
				}
				else if(rs.getString("doanhthu_fk").equals("400004"))//TĂNG GIẢM THUẾ SUẤT
				{	
					TONGVAT = TONGSOTIEN;

					if(TONGVAT > 0)// HÓA ĐƠN TĂNG THUẾ
					{
						
						query = "SELECT KH.TAIKHOAN_FK AS TAIKHOAN_NO_VAT,  \n " +
								
								"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_CO_VAT \n " +
								
								"FROM ERP_HOADONKHAC HD \n " +
								"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +

								"WHERE HD.PK_SEQ =  '" + id + "' \n ";
						
						ResultSet rsTK = db.get(query);
						if(rsTK != null){
							rsTK.next();
							taikhoanNO =  "";
							taikhoanCO = "";
					
							taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
							taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
							rsTK.close();
						}else{
							taikhoanNO = "";
							taikhoanCO = "";
							taikhoanNO_VAT = "";
							taikhoanCO_VAT = "";
						}
						
						if(laytk.trim().length()>0) laytk += " UNION ALL \n";
						
						laytk +=
							" SELECT N'NỢ' NO_CO, a.pk_seq id, '"+taikhoanNO_VAT+"' as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+ 
							"	   b.Ten DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"+
	
							" UNION ALL \n"+
	
							" SELECT N'CÓ' NO_CO, a.pk_seq id, '"+taikhoanCO_VAT+"'  as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+
							"	  '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"; 
						
					}					
					else{// HÓA ĐƠN GIẢM THUẾ
						query = "SELECT   \n " +
						
								"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_NO_VAT, \n " +
						
								"KH.TAIKHOAN_FK AS TAIKHOAN_CO_VAT \n " +
				
								"FROM ERP_HOADONKHAC HD \n " +
								"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +

								"WHERE HD.PK_SEQ =  '" + id + "' \n ";
		
						ResultSet rsTK = db.get(query);
						if(rsTK != null){
							if (rsTK.next())
							{
								taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
								taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
							}
							taikhoanNO =  "";
							taikhoanCO = "";
							rsTK.close();
						}else{
							taikhoanNO = "";
							taikhoanCO = "";
							taikhoanNO_VAT = "";
							taikhoanCO_VAT = "";
						}

						TONGVAT= TONGVAT*(-1);
						
						if(laytk.trim().length()>0) laytk += " UNION ALL \n";
						
						laytk +=
							" SELECT N'NỢ' NO_CO, a.pk_seq id, "+taikhoanNO_VAT+" as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+ 
							"	 b.Ten DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"+
	
							" UNION ALL \n"+
	
							" SELECT N'CÓ' NO_CO, a.pk_seq id, "+taikhoanCO_VAT+"  as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+
							"	   '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"; 
					}
					
				}
				else if(rs.getString("doanhthu_fk").equals("400005"))//TĂNG GIẢM HDKM
				{	
					TONGVAT = TONGSOTIEN;
					if(TONGVAT > 0)// Điều chỉnh tăng thuế suất hóa đơn khuyến mãi
					{
						query = "SELECT HD.TAIKHOANGHINO_FK AS TAIKHOAN_NO_VAT,  \n " +
						
								"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_CO_VAT \n " +
						
								"FROM ERP_HOADONKHAC HD \n " +
								"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +

								"WHERE HD.PK_SEQ =  '" + id + "' \n ";
				
						ResultSet rsTK = db.get(query);
						if(rsTK != null){
							if (rsTK.next())
							{
								taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
								taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
							}
							taikhoanNO =  "";
							taikhoanCO = "";
							rsTK.close();
						}else{
							taikhoanNO = "";
							taikhoanCO = "";
							taikhoanNO_VAT = "";
							taikhoanCO_VAT = "";
						}
						
						if(laytk.trim().length()>0) laytk += " UNION ALL \n";
						
						laytk +=
							" SELECT N'NỢ' NO_CO, a.pk_seq id, '"+taikhoanNO_VAT+"' as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+ 
							" b.TEN AS DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"+
	
							" UNION ALL \n"+
	
							" SELECT N'CÓ' NO_CO, a.pk_seq id, '"+taikhoanCO_VAT+"'  as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+
							"	  '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
							" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
							" WHERE a.pk_seq = '"+id+"' \n"; 
							

					}
					
					
					
					
					else{// Điều chỉnh giảm thuế suất hóa đơn khuyến mãi
						query = "SELECT   \n " +
						
								"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_NO_VAT, \n " +
								"HD.TAIKHOANGHINO_FK AS TAIKHOAN_CO_VAT \n " +
								
								"FROM ERP_HOADONKHAC HD \n " +
								"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +

								"WHERE HD.PK_SEQ =  '" + id + "' \n ";
		
						ResultSet rsTK = db.get(query);
						if(rsTK != null){
							if (rsTK.next())
							{
								taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
								taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
							}
							taikhoanNO =  "";
							taikhoanCO = "";
	
							rsTK.close();
						}else{
							taikhoanNO = "";
							taikhoanCO = "";
							taikhoanNO_VAT = "";
							taikhoanCO_VAT = "";
						}
					}	
					
					TONGVAT = TONGVAT*(-1);
					
					if(laytk.trim().length()>0) laytk += " UNION ALL \n";
					
					laytk +=
						" SELECT N'NỢ' NO_CO, a.pk_seq id, "+taikhoanNO_VAT+" as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+ 
						" b.TEN AS DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
						" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
						" WHERE a.pk_seq = '"+id+"' \n"+

						" UNION ALL \n"+

						" SELECT N'CÓ' NO_CO, a.pk_seq id, "+taikhoanCO_VAT+"  as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+
						"	   '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
						" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
						" WHERE a.pk_seq = '"+id+"' \n"; 
				}else if(rs.getString("doanhthu_fk").equals("100003"))//
				{
					// Nếu loại hóa đơn là:
					// Doanh thu tài chính
					// Doanh thu cho thuê nhà, kho, kiot
					// Doanh thu dịch vụ nhà hàng
					// Doanh thu bán phế liệu
					// Thu nhập khác
					// Doanh thu bán hàng khác
					// Thu nhập bán thanh lý TSCĐ

					if(TONGSOTIEN > 0){
						laytk +=
						"	SELECT N'NỢ' NO_CO, a.pk_seq id, b.TAIKHOAN_FK as SOHIEUTAIKHOAN, " +
						"  ( select SUM( thanhtien ) from erp_HOADONKHAC_sanpham where HOADONKHAC_fk = a.pk_seq ) as SOTIEN, \n"+
						"	   b.Ten DOITUONG, '' TRUNGTAMCHIPHI, c.MA TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
						"	FROM ERP_HOADONKHAC a \n " +
						" 	inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
						"	left join ERP_TRUNGTAMDOANHTHU c on a.trungtamdoanhthu_fk= c.pk_seq \n"+ 
						"   WHERE a.pk_seq = '"+id+"' \n"+
	
						" UNION ALL \n"+
	
						" SELECT N'CÓ' NO_CO, a.pk_seq id, " +
						"(select sohieutaikhoan from ERP_taikhoankt where pk_seq= a.taikhoandoanhthu_fk) as SOHIEUTAIKHOAN, " +
						"( select SUM( thanhtien ) from erp_HOADONKHAC_sanpham where HOADONKHAC_fk = a.pk_seq ) as SOTIEN, \n"+
						"	   '' DOITUONG, '' TRUNGTAMCHIPHI, c.MA TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+ 
						" from ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
						"	 left join ERP_TRUNGTAMDOANHTHU c on a.trungtamdoanhthu_fk= c.pk_seq \n"+ 
						" WHERE a.pk_seq = '"+id+"' \n";
					}
					
					if(TONGVAT > 0)
					{	
						if(laytk.trim().length()>0) laytk += " UNION ALL \n";
						
						laytk+=
						" SELECT N'NỢ' NO_CO, a.pk_seq id, (b.TAIKHOAN_FK ) as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+ 
						"	   b.Ten DOITUONG, '' TRUNGTAMCHIPHI, isnull(c.MA,'') TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
						" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
						"	 left join ERP_TRUNGTAMDOANHTHU c on a.trungtamdoanhthu_fk= c.pk_seq \n"+ 
						" WHERE a.pk_seq = '"+id+"' \n"+
	
						" UNION ALL \n"+
	
						" SELECT N'CÓ' NO_CO, a.pk_seq id, '33311000' as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+
						"	   '' DOITUONG, '' TRUNGTAMCHIPHI, isnull(c.MA,'') TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
						" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
						"	 left join ERP_TRUNGTAMDOANHTHU c on a.trungtamdoanhthu_fk= c.pk_seq \n"+ 
						" WHERE a.pk_seq = '"+id+"' \n";
					}
				
				}
				else
				{
					// Nếu loại hóa đơn là:
					// Doanh thu tài chính
					// Doanh thu cho thuê nhà, kho, kiot
					// Doanh thu dịch vụ nhà hàng
					// Doanh thu bán phế liệu
					// Thu nhập khác
					// Doanh thu bán hàng khác
					// Thu nhập bán thanh lý TSCĐ

					if(TONGSOTIEN > 0){
						laytk +=
						"	SELECT N'NỢ' NO_CO, a.pk_seq id, b.TAIKHOAN_FK as SOHIEUTAIKHOAN, " +
						"  ( select SUM( thanhtien ) from erp_HOADONKHAC_sanpham where HOADONKHAC_fk = a.pk_seq ) as SOTIEN, \n"+
						"	   b.Ten DOITUONG, '' TRUNGTAMCHIPHI, c.MA TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
						"	FROM ERP_HOADONKHAC a \n " +
						" 	inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
						"	left join ERP_TRUNGTAMDOANHTHU c on a.trungtamdoanhthu_fk= c.pk_seq \n"+ 
						"   WHERE a.pk_seq = '"+id+"' \n"+
	
						" UNION ALL \n"+
	
						" SELECT N'CÓ' NO_CO, a.pk_seq id, " +
						"(select taikhoan_fk from ERP_DOANHTHU where pk_seq= a.doanhthu_fk) as SOHIEUTAIKHOAN, " +
						"( select SUM( thanhtien ) from erp_HOADONKHAC_sanpham where HOADONKHAC_fk = a.pk_seq ) as SOTIEN, \n"+
						"	   '' DOITUONG, '' TRUNGTAMCHIPHI, c.MA TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+ 
						" from ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
						"	 left join ERP_TRUNGTAMDOANHTHU c on a.trungtamdoanhthu_fk= c.pk_seq \n"+ 
						" WHERE a.pk_seq = '"+id+"' \n";
					}
					
					if(TONGVAT > 0)
					{	
						if(laytk.trim().length()>0) laytk += " UNION ALL \n";
						
						laytk+=
						" SELECT N'NỢ' NO_CO, a.pk_seq id, (b.TAIKHOAN_FK ) as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+ 
						"	   b.Ten DOITUONG, '' TRUNGTAMCHIPHI, isnull(c.MA,'') TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+ 
						" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
						"	 left join ERP_TRUNGTAMDOANHTHU c on a.trungtamdoanhthu_fk= c.pk_seq \n"+ 
						" WHERE a.pk_seq = '"+id+"' \n"+
	
						" UNION ALL \n"+
	
						" SELECT N'CÓ' NO_CO, a.pk_seq id, '33311000' as SOHIEUTAIKHOAN, "+TONGVAT+" as SOTIEN, \n"+
						"	   '' DOITUONG, '' TRUNGTAMCHIPHI, isnull(c.MA,'') TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+ 
						" FROM ERP_HOADONKHAC a inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n"+ 
						"	 left join ERP_TRUNGTAMDOANHTHU c on a.trungtamdoanhthu_fk= c.pk_seq \n"+ 
						" WHERE a.pk_seq = '"+id+"' \n";
					}
				
				}
			
			
				
			}
				rs.close();
			
		}
		catch (Exception e) {}
		
		
		if(laytk.trim().length()>0) laytk += " ORDER BY ID, STT, SAPXEP ";
		
		if(laytk.trim().length()<=0) 
			laytk = 
				" SELECT '' NO_CO, '' id , ''  SOHIEUTAIKHOAN, '' SOTIEN, \n"+
				"	   '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
				" FROM ERP_HOADONKHAC "+
				" WHERE pk_seq = '"+id+"' \n"; 
		//System.out.println(laytk);
		
		return laytk;
	}
	
	
	public void init(String query) 
	{
		String sql = "";
		System.out.println("__Nha may : " + query);
		
		sql = " SELECT pk_seq as doanhthuId, diengiai as doanhthuTen FROM ERP_DOANHTHU where  TRANGTHAI = '1' and pk_seq in (100003, 100012	) \n " +
//		"  UNION \n " +
//		" SELECT '400000' as doanhthuId, N'Hóa đơn chiết khấu' as doanhthuTen from ERP_DOANHTHU \n " +
//		"  UNION \n "+
//		" SELECT '400001' as doanhthuId, N'Thu hồi CK' as doanhthuTen from ERP_DOANHTHU \n"+
		"  UNION \n "+
		" SELECT '400002' as doanhthuId, N'Hóa đơn điều chỉnh tăng ' as doanhthuTen from ERP_DOANHTHU \n "+
		"  UNION \n "+
		" SELECT '400003' as doanhthuId, N'Hóa đơn điều chỉnh giảm ' as doanhthuTen from ERP_DOANHTHU \n " ;
/*		"  UNION \n"+
		" SELECT '400004' as doanhthuId, N'Hóa đơn điều chỉnh thuế suất hóa đơn bán hàng ' as doanhthuTen from ERP_DOANHTHU \n"+
		"  UNION \n"+
		" SELECT '400005' as doanhthuId, N'Hóa đơn điều chỉnh thuế suất hóa đơn khuyến mãi ' as doanhthuTen from ERP_DOANHTHU \n";
*/		
		this.khoanmucDTRs = db.get(sql);
		
		sql = " select PK_SEQ nhanvienId, TEN nhanvienTen from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_HOADONKHAC ) ";
		
		this.nhanVienRs = db.get(sql);
		
		if(query.length() > 0){
			sql = query;
		}
		else
		{	
			sql = " SELECT a.pk_seq, ISNULL(d.TEN, d.tenchuNPP) as nccTen, isnull(e.ma,'') as poTen, \n " +
				  "        a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua, a.sohoadon, a.ngayhoadon, \n " +
				  "        a.vat ,a.avat as tongtien, " +
				  "		 (" +
				  "		   case a.doanhthu_fk " +
				  "		   when '400002' then N'Hóa đơn điều chỉnh tăng ' " +
				  "		   when '400003'	then N'Hóa đơn điều chỉnh giảm ' " +
				  "		   else g.diengiai end " +
				  "		 ) as doanhthu_fk \n " +
				  " FROM   ERP_HOADONKHAC a " +
				  " INNER JOIN NhanVien b on a.nguoitao = b.pk_seq     \n " +
				  " INNER JOIN nhanvien c on a.nguoisua = c.pk_seq 	\n " +
				  " INNER JOIN NHAPHANPHOI d on a.khachhang_fk = d.pk_seq  \n " +
				  " LEFT JOIN ERP_TRUNGTAMDOANHTHU e on a.trungtamdoanhthu_fk = e.pk_seq \n  " +
				  "	LEFT JOIN ERP_DOANHTHU g on a.doanhthu_fk = g.pk_seq \n " +
				  " WHERE A.CONGTY_FK = " + this.congtyId + "";
			
			
			if(tennguoitao.length() > 0)
				sql += " and b.pk_seq = '" + tennguoitao + "' ";
			if(diengiai.length() > 0)
				sql += " and a.diengiai like N'%" + diengiai + "%' ";
			
			if(trangthai.length() > 0)
				sql += " and a.trangthai = '" + trangthai + "' ";
			
			if(sohoadon.length() > 0)
			{
				sql += " and a.sohoadon like N'%" + sohoadon + "%' ";
			}
			if(this.khoanmucDTId.length() > 0)
			{
				sql += " and a.doanhthu_fk = " + this.khoanmucDTId + " ";
			}
			if(khachhang.length() > 0)
			{
				sql += " and ((d.ten)) like N'%" + this.khachhang + "%'  " +
						"or  (d.ma) like N'%"+ this.khachhang+"%'";
				
			}
			
			if(this.tungay.length() > 0)
			{
				sql += " and a.ngayhoadon >= '" + this.tungay + "' ";
			}
			
			if(this.denngay.length() > 0)
			{
				sql += " and a.ngayhoadon <= '" + this.denngay + "' ";
			}
			//sql += " order by a.pk_seq desc ";
			
			if(this.ma.trim().length() > 0){
				sql += " and a.pk_seq like '%" + this.ma + "%' " ;
			}
			
			System.out.println(sql);
			
				  
		}
		
		System.out.println("__INIT11: " + sql);
		
		String sql1 = createSplittingData_ListNew(this.db, 50, 10, " pk_seq desc", sql) ;

		ResultSet rs = db.get(sql1);
		
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		if(rs!= null)
		{
			try
			{
				IThongTinHienThi ht = null;
				
				while(rs.next())
				{
					//LAY DINH KHOAN KE TOAN
					String pk_seq = rs.getString("PK_SEQ");
					String dk = LayDuLieu(pk_seq);
//					System.out.println(dk);
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{
								kt = new DinhKhoanKeToan(rsKT.getString("ID"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
										 rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"),"");
								ktList.add(kt);
							}
							rsKT.close();
						}
						
						ht = new ThongTinHienThi();	
						
						ht.setId(pk_seq);
						ht.setNgaychungtu(rs.getString("ngayhoadon"));
						ht.setTrangthai(rs.getString("TRANGTHAI"));
						ht.setPoTen(rs.getString("doanhthu_fk"));
						ht.setSohoadon(rs.getString("SOHOADON"));
						ht.setKhachhang(rs.getString("NCCTEN"));
						ht.setNgaytao(rs.getString("NGAYTAO"));
						ht.setNguoitao(rs.getString("NGUOITAO"));
						ht.setNgaysua(rs.getString("NGAYSUA"));
						ht.setNguoisua(rs.getString("NGUOISUA"));
						ht.setTongtien(Double.toString(rs.getDouble("TONGTIEN")));
						
						ht.setLayDinhkhoanKT(ktList);
						htList.add(ht);	
						
				}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
				
		this.hienthiList = htList;
	}

	public void DbClose() 
	{
		try 
		{
			if (this.hienthiList != null)
				this.hienthiList.clear();
			if(this.giamgiaRs != null)
				this.giamgiaRs.close();
			if(this.khoanmucDTRs != null)
				this.khoanmucDTRs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(this.db!=null)
			this.db.shutDown();
		}
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

	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
	}

	public String getTungay() {
		return tungay;
	}

	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

	public String getDenngay() {
		return denngay;
	}

	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}

	

	

}
