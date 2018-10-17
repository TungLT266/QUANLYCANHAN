package geso.traphaco.erp.beans.lapkehoach.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.lapkehoach.IErpKehoachsanxuatList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpKehoachsanxuatList implements IErpKehoachsanxuatList 
{
	String ctyId;
	String dvkdId;
	String userId;
	String trangthai; 

	String diengiai;
	String msg;
	String nam;
	String thang;
	String clId;
	String spId;
	String dtsxId;
	ResultSet khsxRs;
	ResultSet chungloaiRS;
	ResultSet sanphamRS;
	dbutils db;
	
	public ErpKehoachsanxuatList()
	{
		this.ctyId = "";
		this.dvkdId = "";
		this.userId = "";
		this.clId = "";
		this.spId = "";
		this.nam = this.getDateTime().substring(0, 4);
		this.thang = "" + (Integer.parseInt(this.getDateTime().substring(5, 7)) );
		this.trangthai = "";
		this.diengiai = "";
		this.msg = "";
		this.spId = "";
		this.dtsxId = "";
		this.db = new dbutils();
	}
	
	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;	
	}

	public String getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String spId) 
	{
		this.spId = spId;	
	}
	
	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;	
	}

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;	
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
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

	public String getDtsxId() 
	{
		return this.dtsxId;
	}

	public void setDtsxId(String dtsxId) 
	{
		this.dtsxId = dtsxId;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		try
		{
			String query = 	"SELECT CL.PK_SEQ, CL.TEN " +
							"FROM CHUNGLOAI CL " +
							"WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.ctyId + " " +
							"AND CL.PK_SEQ IN (SELECT DISTINCT CHUNGLOAI_FK FROM ERP_SANPHAM WHERE LOAISANPHAM_FK IN (100002, 100003, 100004, 100005, 100006, 100007))";
			
			this.chungloaiRS = this.db.get( query );
			
			query = 	"SELECT SP.PK_SEQ, SP.MA + ' - ' + SP.TEN AS TEN " +
						"FROM ERP_SANPHAM SP " +
						"WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.ctyId + " " +
						"AND LOAISANPHAM_FK IN (100002, 100003, 100004, 100005, 100006, 100007)";

			this.sanphamRS = this.db.get( query );

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception YC: " + e.getMessage() );
		}
	}

	public ResultSet getChungloaiRS(){
		return this.chungloaiRS;
	}
	
	public void setChungloaiRS(ResultSet clRS){
		this.chungloaiRS = clRS;
	}

	public ResultSet getSanphamRS(){
		return this.sanphamRS;
	}
	
	public void setSanphamRS(ResultSet spRS){
		this.sanphamRS = spRS;
	}
	
	
	public String getClId(){
		return this.clId;
	}
	
	public void setClId(String clId){
		this.clId = clId;
	}

	public ResultSet getDaytruyenSX(){
		String query = 	"SELECT DTSX.PK_SEQ AS ID, DTSX.TENDAYTRUYENSX + ' - ' + NM.TENNHAMAY AS DAYTRUYEN \n " +
						"FROM ERP_DAYTRUYENSANXUAT DTSX \n " +
						"INNER JOIN ERP_NHAMAY NM ON NM.PK_SEQ = DTSX.NHAMAY_FK \n " +
						"WHERE DTSX.TRANGTHAI = 1";
		return this.db.get(query);
	}
	
	public ResultSet getDaytruyenSX_2(){
		String query = 	"SELECT DTSX.PK_SEQ AS ID, DTSX.TENDAYTRUYENSX + ' - ' + NM.TENNHAMAY AS DAYTRUYEN \n " +
						"FROM ERP_DAYTRUYENSANXUAT DTSX \n " +
						"INNER JOIN ERP_NHAMAY NM ON NM.PK_SEQ = DTSX.NHAMAY_FK \n " +
						"WHERE DTSX.TRANGTHAI = 1 ";
		
		if(this.dtsxId.length() > 0){
			query += " AND DTSX.PK_SEQ = " + this.dtsxId + " ";
		}
		
		return this.db.get(query);
	}

	public ResultSet getKehoachSX_Ngay(String ngay, String dtsxId){
		String query = 	"SELECT KEHOACH.LOAI, DTSX.PK_SEQ AS DTSXID, DTSX.TENDAYTRUYENSX + ' - ' + NM.TENNHAMAY AS DAYTRUYEN, \n " +
						"KEHOACH.LSXDKID AS LSXID, KEHOACH.MA AS SPMA, KEHOACH.SOLUONG AS SOLUONG, KEHOACH.LOAI AS LOAI, \n " + 
						"ISNULL(KEHOACH.TUGIO_SX, '') AS TUGIO_SX, ISNULL(KEHOACH.DENGIO_SX, '') AS DENGIO_SX, " +
						"ISNULL(KEHOACH.TUGIO_VS, '') AS TUGIO_VS, ISNULL(KEHOACH.DENGIO_VS, '') AS DENGIO_VS, \n " +
						"ISNULL(KEHOACH.NGAYBATDAU, '') AS NGAYBATDAU, ISNULL(KEHOACH.NGAYKETTHUC, '') AS NGAYKETTHUC, " +
						"ISNULL(KEHOACH.NGAYTIEPNHAN,'') AS NGAYTIEPNHAN  \n " +
						"FROM ERP_DAYTRUYENSANXUAT DTSX \n " +
						"INNER JOIN ERP_NHAMAY NM ON NM.PK_SEQ = DTSX.NHAMAY_FK \n " +
						"INNER JOIN \n " +
						"( \n " +
						"	SELECT KHSX.DTSXID AS DTSXID, LSXDK.PK_SEQ AS LSXDKID, SP.MA, \n " + 
						"	(LSXDK.SOLUONG - LSXDK.SANXUAT) AS SOLUONG,  \n " +
						
						"CASE WHEN KHSX.THOIGIAN_SX > 0 THEN \n " + 
						"(SELECT GIO FROM ERP_GIOCHUAN WHERE PK_SEQ = KHSX.TUGIO_SX) \n " + 
						"ELSE '' END AS TUGIO_SX,  \n " +
						
						"CASE WHEN KHSX.THOIGIAN_SX > 0 THEN \n " +
						"(SELECT GIO FROM ERP_GIOCHUAN WHERE PK_SEQ = KHSX.DENGIO_SX) \n " +
						"ELSE '' END AS DENGIO_SX,  \n " +
						
						"KHSX.THOIGIAN_SX,  \n " +
						
						"CASE WHEN KHSX.THOIGIAN_SX > 0 THEN \n " +
						"(SELECT GIO FROM ERP_GIOCHUAN WHERE PK_SEQ = KHSX.DENGIO_SX) \n " + 
						"ELSE  \n " +
						"(SELECT GIO FROM ERP_GIOCHUAN WHERE PK_SEQ = KHSX.TUGIO_SX) \n " + 
						"END AS TUGIO_VS,  \n " +
						
						"(SELECT GIO FROM ERP_GIOCHUAN WHERE PK_SEQ = KHSX.DENGIO_VS) AS DENGIO_VS, \n " + 

						"	KHSX.THOIGIAN_VS, KHSX.NGAYBATDAU, KHSX.NGAYKETTHUC, KHSX.NGAYTIEPNHAN, \n " +
						"	1 AS LOAI \n " +
						"	FROM ERP_KEHOACHSANXUAT KHSX \n " +
						"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = KHSX.LSXID AND KHSX.LOAI = 1 \n " +
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXDK.SANPHAM_FK \n " +
						"	WHERE KHSX.NGAYBATDAU = '" + ngay + "' OR KHSX.NGAYKETTHUC = '" + ngay + "' OR KHSX.NGAYTIEPNHAN = '" + ngay + "' \n " +
						"   AND DTSXID = " + dtsxId + " \n " ;
		
		if(this.spId.length() > 0){
			query += " AND LSXDK.SANPHAM_FK = " + this.spId + " ";
		}
			query += ")KEHOACH ON KEHOACH.DTSXID = DTSX.PK_SEQ AND KEHOACH.DTSXID = " + dtsxId + " \n " ;
	
		//System.out.println(query);
		return this.db.get(query);
	}
	
	public void TaoKeHoachSanXuat()
	{
		try{
			
			String query =  "DELETE ERP_KEHOACHSANXUAT " +
							"WHERE SUBSTRING(NGAYBATDAU, 0, 8) = '" + this.nam + "-" + (this.thang.length() == 1?"0" + this.thang:this.thang) + "' \n " +
							"OR SUBSTRING(NGAYTIEPNHAN, 0, 8) = '" + this.nam + "-" + (this.thang.length() == 1?"0" + this.thang:this.thang) + "' \n " ;
			System.out.println(query);
			this.db.update(query);
			
			query = "SELECT PK_SEQ AS ID, SANPHAM_FK as SPID, NGAYBATDAU, SOLUONG, TRUCTHUOC_FK \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE SUBSTRING(NGAYBATDAU, 1, 7) = '" + this.nam + "-" + (this.thang.length() == 1?"0" + this.thang:this.thang) + "' " +
//					"AND SANPHAM_FK = 100112 \n " +
					"ORDER BY  NGAYBATDAU ASC, SOLUONG DESC ";	
					
			ResultSet rs = this.db.get(query);
			if(rs != null){
				while(rs.next()){
					String lsxId = rs.getString("ID");
					String loai = "1";
					String tructhuoc = rs.getString("TRUCTHUOC_FK"); 
					String option1 = this.getDayTruyenSX_LSXDK_1(lsxId) ;
					String option2 = this.getDayTruyenSX_LSXDK_2(lsxId) ;								
							
					System.out.println("option 1:" + option1);
					System.out.println("option 2:" + option2);
							
					if(option2.length() == 0 & option1.length() > 0){
						String[] d = option1.split(";");
						String dtsxId = d[0];
						String ngaybatdau = d[1];  	// đây là ngày kết thúc của LSX trước đó trên dây truyền. Ngày này sẽ là ngày bắt đầu cho LSX mới này
						String tugioSX = d[2];   	// đây là giờ kết thúc của LSX trước đó trên dây truyền. Giờ này sẽ là giờ bắt đầu cho LSX mới này
						String thoigianSX = d[3];	// đây là thời gian sx của lệnh sx
						String thoigianVS = d[4];   // đây là thời gian vệ sinh sau sx
						String BDgiolamviec = d[5];
						String KTgiolamviec = d[6];
						String BDnghitrua = d[7];
						String KTnghitrua = d[8];
								
						if(Long.parseLong(tugioSX) >= Long.parseLong(KTgiolamviec)){
							ngaybatdau = this.getNgayketiep(ngaybatdau, 1);
							tugioSX = BDgiolamviec;
						}
								
						TinhKHSX(lsxId, tructhuoc, loai, dtsxId, ngaybatdau, "", tugioSX, thoigianSX, thoigianVS, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );

					}else if(option2.length() > 0){
						String[] d = option2.split(";");
						String dtsxId = d[0];
						String ngaybatdau = d[1];  	// đây là ngày kết thúc của LSX trước đó trên dây truyền. Ngày này sẽ là ngày bắt đầu cho LSX mới này
						String tugioSX = d[2];   	// đây là giờ kết thúc của LSX trước đó trên dây truyền. Giờ này sẽ là giờ bắt đầu cho LSX mới này
						String thoigianSX = d[3];	// đây là thời gian sx của lệnh sx
						String thoigianVS = d[4];   // đây là thời gian vệ sinh sau sx
						String BDgiolamviec = d[5];
						String KTgiolamviec = d[6];
						String BDnghitrua = d[7];
						String KTnghitrua = d[8];

						if(Long.parseLong(tugioSX) >= Long.parseLong(KTgiolamviec)){
							ngaybatdau = this.getNgayketiep(ngaybatdau, 1);
							tugioSX = BDgiolamviec;
						}

						TinhKHSX_SS(lsxId, tructhuoc, loai, dtsxId, ngaybatdau, "", tugioSX, thoigianSX, thoigianVS, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );
								
					}
							
				}
			}
					
			
		}catch(java.sql.SQLException e){}

	}

	private void TinhKHSX(String lsxId, String tructhuoc, String loai, String dtsxId, String ngaybatdau, String ngaytiepnhan, String tugioSX, String thoigianSX, String thoigianVS, String BDgiolamviec, String KTgiolamviec, String BDnghitrua, String KTnghitrua ){
	try{
			String query = "";
			long SX_tu = Long.parseLong(tugioSX);
			double SX_tg = Double.parseDouble(thoigianSX);
			double VS_tg = Double.parseDouble(thoigianVS);
			long gioBD = Long.parseLong(BDgiolamviec);
			long gioKT = Long.parseLong(KTgiolamviec);
			long nghitruaBD = Long.parseLong(BDnghitrua);
			long nghitruaKT = Long.parseLong(KTnghitrua);
			String ngayBD = "";
			String ngayKT = "";
			String ngayTN = "";
			long dengioSX = 0;
			long dengioVS = 0;

			if(SX_tu == nghitruaBD){
				SX_tu = nghitruaKT;
			}

			dengioSX = SX_tu + (SX_tg*2 > Math.round(SX_tg*2)?(Math.round(SX_tg*2) + 1): Math.round(SX_tg*2));
			if(SX_tu <= nghitruaBD & dengioSX >= nghitruaKT){								 // SX_tu  nghitruaBD    nghitruaKT  dengioSX
			
				dengioSX += (nghitruaKT - nghitruaBD);
			
			}else if(SX_tu <= nghitruaBD & (dengioSX >= nghitruaBD & dengioSX <= nghitruaKT)){ // SX_tu  nghitruaBD  dengioSX  nghitruaKT
				
				dengioSX =  nghitruaKT + (dengioSX - nghitruaBD);
					
			}else if(SX_tu >= nghitruaBD & SX_tu <= nghitruaKT & dengioSX <= nghitruaKT){      // nghitruaBD   SX_tu  dengioSX  nghitruaKT
				
				dengioSX =  nghitruaKT + (dengioSX - SX_tu);
				
			}else if(SX_tu >= nghitruaBD & SX_tu <= nghitruaKT & dengioSX > nghitruaKT){		// nghitruaBD SX_tu   nghitrua KT  dengioSX
				
				dengioSX =  dengioSX + (nghitruaKT - SX_tu);
			}
			
			System.out.println("TugioSX: " + SX_tu);
			System.out.println("DengioSX: " + dengioSX);
			
			dengioVS = dengioSX + (VS_tg*2 > Math.round(VS_tg*2)?(Math.round(VS_tg*2) + 1): Math.round(VS_tg*2));
	
			if(dengioVS <= gioKT){
				ngayBD = ngaybatdau;
				ngayTN = ngaytiepnhan;
				ngayKT = (ngayBD.length() > 0)?ngayBD:ngayTN;
				query = "INSERT INTO ERP_KEHOACHSANXUAT " +
						"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
						"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxId + ", '" + ngayBD + "', " +
						"" + tugioSX + ", " + dengioSX + ", " + SX_tg + ", " + VS_tg + ", " + dengioVS + ", '" + ngayKT + "', '" + ngayTN + "') ";
				
				System.out.println("dengioVS <= gioKT: " + query);
				this.db.update(query);
//				this.db.update("commit");
				
			}else if(dengioSX <= gioKT & dengioVS > gioKT ){
				dengioVS = gioKT;
				ngayBD = ngaybatdau;
				ngayTN = ngaytiepnhan;
				ngayKT = "";
				
				query = "INSERT INTO ERP_KEHOACHSANXUAT " +
						"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
						"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxId + ", '" + ngayBD + "', " +
						"" + tugioSX + ", " + dengioSX + ", " + SX_tg + ", " + (gioKT - dengioSX)*0.5 + ", " + dengioVS + ", '" + ngayKT + "', '" + ngayTN + "' ) ";
				
				System.out.println("dengioSX <= gioKT & dengioVS > gioKT: " + query);
				this.db.update(query);
//				this.db.update("commit");
				
				SX_tg = 0;
				VS_tg = VS_tg - (gioKT - dengioSX)*0.5;
				
				if(ngayBD.length() > 0){
					ngayTN = getNgayketiep(ngayBD, 1);
				}else{
					ngayTN = getNgayketiep(ngayTN, 1);
				}
				ngayBD = "";
				
				TinhKHSX(lsxId, tructhuoc, loai, dtsxId, ngayBD, ngayTN, "" + BDgiolamviec, "" + SX_tg, "" + VS_tg, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );
			
			}else if(dengioSX > gioKT  ){
				ngayBD = ngaybatdau;
				ngayTN = ngaytiepnhan;
				ngayKT = "";
	
				double tgSX = 0;
				
				if(SX_tu < nghitruaBD){
				
					tgSX = (gioKT - SX_tu - 2)*0.5;
					SX_tg = SX_tg - tgSX;
					
				}else if(SX_tu > nghitruaKT){
					
					tgSX = (gioKT - SX_tu)*0.5;
					SX_tg = SX_tg - tgSX;				
					
				}else if(SX_tu > nghitruaBD & SX_tu < nghitruaKT){
	
					tgSX = (gioKT - nghitruaKT)*0.5;
					SX_tg = SX_tg - tgSX;				
					
				}
	
	
				query = "INSERT INTO ERP_KEHOACHSANXUAT " +
						"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
						"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxId + ", '" + ngayBD + "', " +
						"" + tugioSX + ", " + gioKT + ", " + tgSX + ", 0, NULL, '', '" + ngayTN + "' ) ";
				
				System.out.println("dengioSX <= gioKT & dengioVS > gioKT: " + query);
				this.db.update(query);
//				this.db.update("commit");
				
				if(ngayBD.length() > 0){
					ngayTN = getNgayketiep(ngayBD, 1);
				}else{
					ngayTN = getNgayketiep(ngayTN, 1);
				}
				ngayBD = "";
	
				TinhKHSX(lsxId, tructhuoc, loai, dtsxId, ngayBD, ngayTN, BDgiolamviec, "" + SX_tg, "" + VS_tg, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );
			}

			this.db.getConnection().commit();
	
		}catch(java.sql.SQLException e){}
	}
	
	private void TinhKHSX_SS(String lsxId, String tructhuoc, String loai, String dtsxId, String ngaybatdau, String ngaytiepnhan, String tugioSX, String thoigianSX, String thoigianVS, String BDgiolamviec, String KTgiolamviec, String BDnghitrua, String KTnghitrua ){
	try{
		String query = "";
		System.out.println(dtsxId);
		String[] dtsxIds = dtsxId.split("-");
		System.out.println("Length:" + dtsxIds.length);
		
		long SX_tu = Long.parseLong(tugioSX);
		double SX_tg = Double.parseDouble(thoigianSX);
		double VS_tg = Double.parseDouble(thoigianVS);
		long gioBD = Long.parseLong(BDgiolamviec);
		long gioKT = Long.parseLong(KTgiolamviec);
		long nghitruaBD = Long.parseLong(BDnghitrua);
		long nghitruaKT = Long.parseLong(KTnghitrua);
		String ngayBD = "";
		String ngayKT = "";
		String ngayTN = "";
		long dengioSX = 0;
		long dengioVS = 0;
		
//		dengioSX = SX_tu + (SX_tg*2 > Math.round(SX_tg*2)?(Math.round(SX_tg*2) + 1): Math.round(SX_tg*2))+ (nghitruaKT - nghitruaBD);
		
		if(SX_tu == nghitruaBD){
			SX_tu = nghitruaKT;
		}
		dengioSX = SX_tu + (SX_tg*2 > Math.round(SX_tg*2)?(Math.round(SX_tg*2) + 1): Math.round(SX_tg*2));
		dengioVS = dengioSX + (VS_tg*2 > Math.round(VS_tg*2)?(Math.round(VS_tg*2) + 1): Math.round(VS_tg*2));
		System.out.println("dengioSX: " + dengioSX);
		System.out.println("dengioVS: " + dengioVS);
		
		if(dengioVS <= gioKT){
			ngayBD = ngaybatdau;
			ngayTN = ngaytiepnhan;
			ngayKT = (ngayBD.length() > 0)?ngayBD:ngayTN;

			for(int i = 0; i < dtsxIds.length; i++){
				query = "INSERT INTO ERP_KEHOACHSANXUAT " +
						"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
						"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxIds[i] + ", '" + ngayBD + "', " +
						"" + tugioSX + ", " + dengioSX + ", " + SX_tg + ", " + VS_tg + ", " + dengioVS + ", '" + ngayKT + "', '" + ngayTN + "') ";
				
				System.out.println("dengioVS <= gioKT: " + query);
				this.db.update(query);
//				this.db.update("commit");			
			}
		}else if(dengioSX <= gioKT & dengioVS > gioKT ){
			dengioVS = gioKT;
			ngayBD = ngaybatdau;
			ngayTN = ngaytiepnhan;
			ngayKT = "";
			for(int i = 0; i < dtsxIds.length; i++){
			
				query = "INSERT INTO ERP_KEHOACHSANXUAT " +
						"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
						"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxIds[i] + ", '" + ngayBD + "', " +
						"" + tugioSX + ", " + dengioSX + ", " + SX_tg + ", " + (gioKT - dengioSX)*0.5 + ", " + dengioVS + ", '" + ngayKT + "', '" + ngayTN + "' ) ";
				
				System.out.println("dengioSX <= gioKT & dengioVS > gioKT: " + query);
				this.db.update(query);
//				this.db.update("commit");
			}	
			
			
			SX_tg = 0;
			VS_tg = VS_tg - (gioKT - dengioSX)*0.5;
			System.out.println("VS còn lại:" + VS_tg);
			
			if(ngayBD.length() > 0){
				ngayTN = getNgayketiep(ngayBD, 1);
			}else{
				ngayTN = getNgayketiep(ngayTN, 1);
			}
			ngayBD = "";
			
			TinhKHSX_SS(lsxId, tructhuoc, loai, dtsxId, ngayBD, ngayTN, "" + BDgiolamviec, "" + SX_tg, "" + VS_tg, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );
		}else if(dengioSX > gioKT  ){
			ngayBD = ngaybatdau;
			ngayTN = ngaytiepnhan;
			ngayKT = "";

			double tgSX = 0;
			
			if(SX_tu < nghitruaBD){
			
				tgSX = (gioKT - SX_tu - 2)*0.5;
				SX_tg = SX_tg - tgSX;
				
			}else if(SX_tu > nghitruaKT){
				
				tgSX = (gioKT - SX_tu)*0.5;
				SX_tg = SX_tg - tgSX;				
				
			}else if(SX_tu > nghitruaBD & SX_tu < nghitruaKT){

				tgSX = (gioKT - nghitruaKT)*0.5;
				SX_tg = SX_tg - tgSX;				
				
			}

			for(int i = 0; i < dtsxIds.length; i++){

				query = "INSERT INTO ERP_KEHOACHSANXUAT " +
						"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
						"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxIds[i] + ", '" + ngayBD + "', " +
						"" + tugioSX + ", NULL, " + tgSX + ", 0, NULL, '', '" + ngayTN + "' ) ";
				
				System.out.println("dengioSX > gioKT: " + query);
				this.db.update(query);
//				this.db.update("commit");
			}
//			this.db.getConnection().commit();
			if(ngayBD.length() > 0){
				ngayTN = getNgayketiep(ngayBD, 1);
			}else{
				ngayTN = getNgayketiep(ngayTN, 1);
			}
			ngayBD = "";

			TinhKHSX_SS(lsxId, tructhuoc, loai, dtsxId, ngayBD, ngayTN, BDgiolamviec, "" + SX_tg, "" + VS_tg, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );
		}

		this.db.getConnection().commit();

	}catch(java.sql.SQLException e){}
}

	String getNgayketiep(String ngaybatdau, int num){
		String nbd = ngaybatdau;
		String query = 	"SELECT \n " +
						"CASE WHEN DATENAME(DW, CONVERT(VARCHAR(10), DATEADD(DAY, 1, '" + ngaybatdau + "'), 120)) = 'Sunday'  \n " + 
						"THEN CONVERT(VARCHAR(10), DATEADD(DAY, " + (num + 1) + ", '" + ngaybatdau + "'), 120) \n " +
						"ELSE CONVERT(VARCHAR(10), DATEADD(DAY, " + num + ", '" + ngaybatdau + "'), 120) END AS NGAYBATDAU " ;
		
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			nbd = rs.getString("NGAYBATDAU");
		}catch(java.sql.SQLException e){}
		return nbd;	
	}
	
	private String getDayTruyenSX_LSXDK_1(String lsxId){
		String nbd = this.nam + "-" + (this.thang.length() == 1?"0" + this.thang:this.thang) + "-01";
		String tmp = "";
		String query = 	
					"SELECT TOP 1 * " + 
					"FROM( " +

						"SELECT LSXDK.PK_SEQ AS ID, LSXDK.TRUCTHUOC_FK AS TRUCTHUOC, LSXDK.SANPHAM_FK as SPID, \n " + 
						"(LSXDK.SOLUONG - LSXDK.SANXUAT) AS SOLUONG, 1 AS LOAI,  \n " +
						"TTDH_DTSX.DAYTRUYENSX_FK AS DTSXID,  \n " +
						"DTSX.TUGIO AS GIOBATDAU, DTSX.DENGIO AS GIORAVE, DTSX.BDNGHITRUA, DTSX.KTNGHITRUA, \n " +
				
						"CASE WHEN TTDH.LOTSIZE = (LSXDK.SOLUONG - LSXDK.SANXUAT)  \n " +
						"THEN TTDH_DTSX.THOIGIAN \n " +
						"ELSE LSXDK.SOLUONG*TTDH_DTSX.THOIGIAN/TTDH.LOTSIZE END AS THOIGIANSX, \n " + 
						"TTDH.CLEANUP AS THOIGIANVS, \n " +
						
						"ISNULL( \n " +
						"( \n " +
						"	SELECT  MAX(DENGIO_VS)AS DENGIO \n " +
						"	FROM ERP_KEHOACHSANXUAT \n " +
						"	WHERE NGAYKETTHUC =  \n " +
						"	( \n " +
						"		SELECT MAX(NGAYKETTHUC) \n " +
						"		FROM ERP_KEHOACHSANXUAT  \n " +
						"		WHERE DTSXID = DTSX.PK_SEQ \n " +
						"		GROUP BY DTSXID \n " +
						"	) \n " +
						"), DTSX.TUGIO) AS GIOKETTHUC, \n " +		
						
						"ISNULL( \n " +
						"( \n " +
						"		SELECT MAX(NGAYKETTHUC) \n " +
						"		FROM ERP_KEHOACHSANXUAT  \n " +
						"		WHERE DTSXID = DTSX.PK_SEQ \n " +
						"		GROUP BY DTSXID \n " +
						"), '" + nbd + "') AS NGAYKETTHUC \n " +
							
						"FROM ERP_LENHSANXUATDUKIEN LSXDK \n " +
						"INNER JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = LSXDK.SANPHAM_FK \n " +
						"INNER JOIN ERP_THONGTINDATHANG_DAYTRUYENSX TTDH_DTSX ON TTDH_DTSX.THONGTINDATHANG_FK = TTDH.PK_SEQ AND TTDH_DTSX.LOAI = 1 \n " +
						"INNER JOIN ERP_DAYTRUYENSANXUAT DTSX ON DTSX.PK_SEQ = TTDH_DTSX.DAYTRUYENSX_FK \n " +
						"WHERE LSXDK.PK_SEQ = " + lsxId + " \n " +
					")DATA	ORDER BY (NGAYKETTHUC + ';' + CONVERT(VARCHAR, GIOKETTHUC)) ASC ";
			//System.out.println(query);
			ResultSet rs = this.db.get(query);
			if(rs != null){
				try{
					rs.next();
					if(rs.getString("NGAYKETTHUC").length() > 0){
						tmp = rs.getString("DTSXID") + ";" + rs.getString("NGAYKETTHUC") + ";" + rs.getString("GIOKETTHUC") + ";" + rs.getString("THOIGIANSX")+ ";" + 
							  "" + rs.getString("THOIGIANVS") + ";" + rs.getString("GIOBATDAU") + ";" + rs.getString("GIORAVE") + ";" + 
							  "" + rs.getString("BDNGHITRUA") + ";" + rs.getString("KTNGHITRUA") + "";
					}			
					rs.close();
				}catch(java.sql.SQLException e){}
			}
			return tmp;
	}

	private String getDayTruyenSX_LSXDK_2(String lsxId)
	{
		String nbd = this.nam + "-" + (this.thang.length() == 1?"0" + this.thang:this.thang) + "-01";

		String tmp = "";
		String query = 	
					"SELECT * FROM ( \n " +
						"SELECT LSXDK.PK_SEQ AS ID, LSXDK.TRUCTHUOC_FK AS TRUCTHUOC, LSXDK.SANPHAM_FK as SPID, \n " + 
						"(LSXDK.SOLUONG - LSXDK.SANXUAT) AS SOLUONG, 1 AS LOAI,  \n " +
						"TTDH_DTSX.DAYTRUYENSX_FK AS DTSXID,  \n " +
						"DTSX.TUGIO AS GIOBATDAU, DTSX.DENGIO AS GIORAVE, DTSX.BDNGHITRUA, DTSX.KTNGHITRUA, \n " +
	
						"CASE WHEN TTDH.LOTSIZE = (LSXDK.SOLUONG - LSXDK.SANXUAT)  \n " +
						"THEN TTDH_DTSX.THOIGIAN \n " +
						"ELSE LSXDK.SOLUONG*TTDH_DTSX.THOIGIAN/TTDH.LOTSIZE END AS THOIGIANSX, \n " + 
						"TTDH.CLEANUP AS THOIGIANVS, \n " +
						"ISNULL( \n "	+
						"( \n " +
						"	SELECT  MAX(DENGIO_VS)AS DENGIO \n " +
						"	FROM ERP_KEHOACHSANXUAT \n " +
						"	WHERE NGAYKETTHUC =  \n " +
						"	( \n " +
						"		SELECT MAX(NGAYKETTHUC) \n " +
						"		FROM ERP_KEHOACHSANXUAT  \n " +
						"		WHERE DTSXID = DTSX.PK_SEQ \n " +
						"		GROUP BY DTSXID \n " +
						"	)" +
						"), DTSX.TUGIO) \n " +
						"GIOKETTHUC, \n " +		
						
						"ISNULL( \n " +
						"( \n " +
						"		SELECT MAX(NGAYKETTHUC) \n " +
						"		FROM ERP_KEHOACHSANXUAT  \n " +
						"		WHERE DTSXID = DTSX.PK_SEQ \n " +
						"		GROUP BY DTSXID \n " +
						"), '" + nbd +  "') \n " +
						"  AS NGAYKETTHUC \n " +
								
						"FROM ERP_LENHSANXUATDUKIEN LSXDK \n " +
						"INNER JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = LSXDK.SANPHAM_FK \n " +
						"INNER JOIN ERP_THONGTINDATHANG_DAYTRUYENSX TTDH_DTSX ON TTDH_DTSX.THONGTINDATHANG_FK = TTDH.PK_SEQ AND TTDH_DTSX.LOAI = 2 \n " +
						"INNER JOIN ERP_DAYTRUYENSANXUAT DTSX ON DTSX.PK_SEQ = TTDH_DTSX.DAYTRUYENSX_FK \n " +
						"WHERE LSXDK.PK_SEQ = " + lsxId + " \n " +
					")DATA	ORDER BY (NGAYKETTHUC + ';' + CONVERT(VARCHAR, GIOKETTHUC)) DESC ";
		//System.out.println(query);
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				String daytruyen = "";
				String ngaybatdau = "";
				String giobatdau = "";
				String thoigianSX = "";
				String thoigianVS = "";
				String gioBD = "";
				String gioKT = "";
				String nghitruaBD = "";
				String nghitruaKT = "";
				
				boolean flag = true;
				while(rs.next()){
					if(daytruyen.length() > 0){
						daytruyen += "-" + rs.getString("DTSXID");
					}else{
						daytruyen += rs.getString("DTSXID");
					}
					
					if(ngaybatdau.length() == 0){	
						ngaybatdau = rs.getString("NGAYKETTHUC");
						giobatdau = rs.getString("GIOKETTHUC");
						thoigianSX = rs.getString("THOIGIANSX");
						thoigianVS = rs.getString("THOIGIANVS");
						gioBD = rs.getString("GIOBATDAU");
						gioKT = rs.getString("GIORAVE");
						nghitruaBD = rs.getString("BDNGHITRUA");
						nghitruaKT = rs.getString("KTNGHITRUA");
					}else{
						if(!ngaybatdau.equals(rs.getString("NGAYKETTHUC"))){
						
							flag = false;
						}
					}
				}
				if(flag & ngaybatdau.length() > 0){
					
					tmp = daytruyen + ";" + ngaybatdau + ";" + giobatdau + ";" + thoigianSX+ ";" + 
						  "" + thoigianVS + ";" + gioBD + ";" + gioKT + ";" + 
						  "" + nghitruaBD + ";" + nghitruaKT + "";
	
				}
			}catch(java.sql.SQLException e){}
		}
		return tmp;
	}

	public void DbClose() 
	{
		try 
		{
			if(this.chungloaiRS != null) this.chungloaiRS.close();
			if(this.khsxRs != null) this.khsxRs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.db.shutDown();
		}
	}

	public ResultSet getKehoachSXRs() 
	{
		return this.khsxRs;
	}

	public void setKehoachSXRs(ResultSet khsxRs) 
	{
		this.khsxRs = khsxRs;
	}

	public int getSongay(){
		int n  = 30;
		if(this.thang.equals("1") || this.thang.equals("3") || this.thang.equals("5") || this.thang.equals("7") || this.thang.equals("8") || this.thang.equals("10") || this.thang.equals("12")){
			n = 31;
		}
		
		if(this.thang.equals("4") || this.thang.equals("6") || this.thang.equals("9") || this.thang.equals("11")){
			n = 30;
		}
		
		if(this.thang.equals("2")){
			float m = Float.parseFloat(this.nam)/4;
			if(Long.parseLong(this.nam) / 4 == m){
				n = 29;
			}else{
				n = 28;
			}
			
		}
		
		return n;
	}
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
