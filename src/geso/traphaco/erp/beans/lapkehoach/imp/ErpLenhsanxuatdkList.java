package geso.traphaco.erp.beans.lapkehoach.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.lapkehoach.IErpLenhsanxuatdkList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpLenhsanxuatdkList implements IErpLenhsanxuatdkList 
{
	String ctyId;
	String dvkdId;
	String userId;
	String trangthai; 

	String diengiai;
	String msg;
	String nam;
	String thang;
	String nmId;
	String spId;
	String loaihh;
	ResultSet dnsxRs;
	ResultSet nmRS;
	ResultSet sanphamRS;
	dbutils db;
	
	public ErpLenhsanxuatdkList()
	{
		this.ctyId = "";
		this.dvkdId = "";
		this.userId = "";
		this.nmId = "";
		this.spId = "";
		this.nam = this.getDateTime().substring(0, 4);
		this.thang = "" + (Integer.parseInt(this.getDateTime().substring(5, 7)) );
		this.trangthai = "";
		this.diengiai = "";
		this.msg = "";
		this.spId = "";
		this.loaihh = "";
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
			String query = "SELECT * \n " +
						   "FROM(  \n " +
							"SELECT LSXDK.PK_SEQ AS ID, LSXDK.SANPHAM_FK AS SPID, SP.MA AS MA, SP.TEN AS TEN,  DVDL.DONVI, \n " +
							"SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) AS NAM, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2) AS THANG,  \n " +
							"LSXDK.SOLUONG_BOM AS TUAN_1, 0 AS TUAN_2, 0 AS TUAN_3, 0 AS TUAN_4, LSXDK.SANXUAT, ISNULL(LSXDK.SOLO, '') AS SOLO, ISNULL(NM.MANHAMAY, '') AS NHAMAY  \n " +
							"FROM ERP_LENHSANXUATDUKIEN LSXDK  \n " +
							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXDK.SANPHAM_FK \n " +
							"LEFT JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = LSXDK.DVDL_FK \n " +
							"LEFT JOIN ERP_NHAMAY NM ON NM.PK_SEQ = LSXDK.NHAMAY_FK \n " +
							"WHERE LSXDK.CONGTY_FK = " + this.ctyId + " AND NGAYBATDAU >= SUBSTRING(NGAYBATDAU, 1, 8) + '01'  \n " +
							"AND NGAYKETTHUC <= SUBSTRING(NGAYBATDAU, 1, 8) + '07' \n " +
							
							"UNION ALL \n " +
							"SELECT LSXDK.PK_SEQ AS ID, LSXDK.SANPHAM_FK AS SPID, SP.MA AS MA, SP.TEN AS TEN,  DVDL.DONVI, \n " +
							"SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) AS NAM, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2) AS THANG,  \n " +
							"0 AS TUAN_1, LSXDK.SOLUONG_BOM AS TUAN_2, 0 AS TUAN_3, 0 AS TUAN_4, LSXDK.SANXUAT, ISNULL(LSXDK.SOLO, '') AS SOLO, ISNULL(NM.MANHAMAY, '') AS NHAMAY  \n " +
							"FROM ERP_LENHSANXUATDUKIEN LSXDK  \n " +
							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXDK.SANPHAM_FK \n " +
							"LEFT JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = LSXDK.DVDL_FK \n " +
							"LEFT JOIN ERP_NHAMAY NM ON NM.PK_SEQ = LSXDK.NHAMAY_FK \n " +
							"WHERE LSXDK.CONGTY_FK = " + this.ctyId + " AND NGAYBATDAU >= SUBSTRING(NGAYBATDAU, 1, 8) + '08'  \n " +
							"AND NGAYKETTHUC <= SUBSTRING(NGAYBATDAU, 1, 8) + '14' \n " +
							
							"UNION ALL \n " +
							"SELECT LSXDK.PK_SEQ AS ID, LSXDK.SANPHAM_FK AS SPID, SP.MA AS MA, SP.TEN AS TEN,  DVDL.DONVI,  \n " +
							"SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) AS NAM, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2) AS THANG,  \n " +
							"0 AS TUAN_1, 0 AS TUAN_2, LSXDK.SOLUONG_BOM AS TUAN_3, 0 AS TUAN_4, LSXDK.SANXUAT, ISNULL(LSXDK.SOLO, '') AS SOLO, ISNULL(NM.MANHAMAY, '') AS NHAMAY  \n " +
							"FROM ERP_LENHSANXUATDUKIEN LSXDK  \n " +
							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXDK.SANPHAM_FK \n " +
							"LEFT JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = LSXDK.DVDL_FK \n " +
							"LEFT JOIN ERP_NHAMAY NM ON NM.PK_SEQ = LSXDK.NHAMAY_FK \n " +
							"WHERE LSXDK.CONGTY_FK = " + this.ctyId + " AND NGAYBATDAU >= SUBSTRING(NGAYBATDAU, 1, 8) + '15'  \n " +
							"AND NGAYKETTHUC <= SUBSTRING(NGAYBATDAU, 1, 8) + '21' \n " +
							
							"UNION ALL \n " +
							"SELECT LSXDK.PK_SEQ AS ID, LSXDK.SANPHAM_FK AS SPID, SP.MA AS MA, SP.TEN AS TEN, DVDL.DONVI, \n " +
							"SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) AS NAM, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2) AS THANG,  \n " +
							"0 AS TUAN_1, 0 AS TUAN_2, 0 AS TUAN_3, LSXDK.SOLUONG_BOM AS TUAN_4, LSXDK.SANXUAT, ISNULL(LSXDK.SOLO, '') AS SOLO, ISNULL(NM.MANHAMAY, '') AS NHAMAY  \n " +
							"FROM ERP_LENHSANXUATDUKIEN LSXDK  \n " +
							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXDK.SANPHAM_FK \n " +
							"LEFT JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = LSXDK.DVDL_FK \n " +
							"LEFT JOIN ERP_NHAMAY NM ON NM.PK_SEQ = LSXDK.NHAMAY_FK \n " +
							"WHERE LSXDK.CONGTY_FK = " + this.ctyId + " AND NGAYBATDAU >= SUBSTRING(NGAYBATDAU, 1, 8) + '22'  \n " +
							"AND NGAYKETTHUC <= SUBSTRING(NGAYBATDAU, 1, 8) + '28' ";

			query += " )DATA WHERE 1 = 1 " ;

			if(this.nam.length() > 0){
				query += " AND DATA.NAM = " + this.nam + " \n ";
			}
			
			if(this.thang.length() > 0){
				query += " AND CONVERT(INT, DATA.THANG) = " + this.thang + " \n ";
			}

			if(this.nmId.length() > 0){
				query += " AND SPID IN ( SELECT SANPHAM_FK FROM ERP_KICHBANSANXUAT_GIAY WHERE NHAMAY_FK = " + this.nmId + ") \n ";
			}
			
			if(this.spId.length() > 0){
				query += " AND SPID = " + this.spId + " \n ";
			}
			
			if( this.loaihh.trim().length() > 0 )
			{
				query += " and SPID in ( select pk_seq from ERP_SANPHAM where loaihanghoa = '" + this.loaihh + "' ) ";
			}
			
			//query += "ORDER BY SPID, NAM, THANG, TUAN_1 DESC, TUAN_2 DESC, TUAN_3 DESC, TUAN_4 DESC ";
			query += "ORDER BY SPID, NAM, THANG, TUAN_1 DESC, TUAN_2 DESC, TUAN_3 DESC, TUAN_4 DESC ";

			System.out.println("::: LSX DU KIEN: " + query);
			this.dnsxRs = this.db.get(query);
			
			query = 	" SELECT PK_SEQ AS NMID, MANHAMAY + ' - ' + TENNHAMAY AS TEN FROM ERP_NHAMAY WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.ctyId + " ";
			
			this.nmRS = this.db.get( query );
			
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

	public String[] getSX_Tuan(String nam_thang, String spId)
	{
		String[] tmp = new String[4];
		tmp[0] = "0";
		tmp[1] = "0";
		tmp[2] = "0";
		tmp[3] = "0";

		String query =
				"SELECT ISNULL(SUM(ISNULL(SX_TUAN_1, 0)), 0) AS SX_TUAN_1, ISNULL(SUM(ISNULL(SX_TUAN_2, 0)), 0) AS SX_TUAN_2, \n " +
						"ISNULL(SUM(ISNULL(SX_TUAN_3, 0)), 0) AS SX_TUAN_3, ISNULL(SUM(ISNULL(SX_TUAN_4, 0)), 0) AS SX_TUAN_4 \n " +

					"FROM( \n " +
					"SELECT	ISNULL((SUM(ISNULL(LSXSP0.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)), 0)  AS SX_TUAN_1, \n " +
					"		0 AS SX_TUAN_2, \n " +
					"		0 AS SX_TUAN_3, \n " +
					"		0 AS SX_TUAN_4   \n " +		
					"FROM ERP_LENHSANXUAT_GIAY LSX0 \n " +    
					"INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP0 ON LSXSP0.LENHSANXUAT_FK = LSX0.PK_SEQ \n " +  
					"LEFT JOIN(   \n " +
					"	SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG \n " +  
					"	FROM ERP_NHAPKHO NK   \n " +
					"	INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ \n " +   
					"	WHERE NK.TRANGTHAI = 1   \n " +
					"	AND NK.KHONHAP IN (100005, 100006) \n " +  
					"	GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT \n " +  
					")NH ON NH.SANPHAM_FK = LSXSP0.SANPHAM_FK AND NH.SOLENHSANXUAT = LSX0.PK_SEQ \n " +  
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXSP0.SANPHAM_FK AND SP.PK_SEQ = " + spId + " \n " +

						"WHERE LSX0.NGAYDUKIENHT >=  '" + nam_thang + "01'  \n " +   
						"AND LSX0.NGAYDUKIENHT <= '" + nam_thang + "07'  AND LSX0.TRANGTHAI < 6    \n " +
						"GROUP BY LSXSP0.SANPHAM_FK, NH.NHANHANG   \n " +

						"UNION ALL \n " +
						"SELECT	0 AS SX_TUAN_1, \n " +
						"		ISNULL((SUM(ISNULL(LSXSP0.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)), 0)  AS SX_TUAN_2,   \n " +		
						"		0 AS SX_TUAN_3, \n " +
						"		0 AS SX_TUAN_4   \n " +

						"FROM ERP_LENHSANXUAT_GIAY LSX0     \n " +
						"INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP0 ON LSXSP0.LENHSANXUAT_FK = LSX0.PK_SEQ   \n " +
						"LEFT JOIN(   \n " +
						"	SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG  \n " + 
						"	FROM ERP_NHAPKHO NK   \n " +
						"	INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ    \n " +
						"	WHERE NK.TRANGTHAI = 1   \n " +
						"	AND NK.KHONHAP IN (100005, 100006)   \n " +
						"	GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT   \n " +
						")NH ON NH.SANPHAM_FK = LSXSP0.SANPHAM_FK AND NH.SOLENHSANXUAT = LSX0.PK_SEQ  \n " + 
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXSP0.SANPHAM_FK AND SP.PK_SEQ = " + spId + " \n " +

						"WHERE LSX0.NGAYDUKIENHT >=  '" + nam_thang + "08'    \n " +
						"AND LSX0.NGAYDUKIENHT <= '" + nam_thang + "14'  AND LSX0.TRANGTHAI < 6    \n " +
						"GROUP BY LSXSP0.SANPHAM_FK, NH.NHANHANG   \n " +

						"UNION ALL \n " +
						"SELECT	0 AS SX_TUAN_1, \n " +
						"		0 AS SX_TUAN_2, \n " +
						"		ISNULL((SUM(ISNULL(LSXSP0.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)), 0)  AS SX_TUAN_3, \n " +
						"		0 AS SX_TUAN_4 \n " +
						"FROM ERP_LENHSANXUAT_GIAY LSX0     \n " +
						"INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP0 ON LSXSP0.LENHSANXUAT_FK = LSX0.PK_SEQ   \n " +
						"LEFT JOIN(   \n " +
						"	SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG   \n " +
						"	FROM ERP_NHAPKHO NK   \n " +
						"	INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ    \n " +
						"	WHERE NK.TRANGTHAI = 1   \n " +
						"	AND NK.KHONHAP IN (100005, 100006)   \n " +
						"	GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT  \n " + 
						")NH ON NH.SANPHAM_FK = LSXSP0.SANPHAM_FK AND NH.SOLENHSANXUAT = LSX0.PK_SEQ  \n " + 
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXSP0.SANPHAM_FK AND SP.PK_SEQ = " + spId + " \n " +

						"WHERE LSX0.NGAYDUKIENHT >=  '" + nam_thang + "15'    \n " +
						"AND LSX0.NGAYDUKIENHT <= '" + nam_thang + "21'  AND LSX0.TRANGTHAI < 6    \n " +
						"GROUP BY LSXSP0.SANPHAM_FK, NH.NHANHANG   \n " +

						"UNION ALL \n " +
						"SELECT	0 AS SX_TUAN_1, \n " +
						"		0 AS SX_TUAN_2, \n " +
						"		0 AS SX_TUAN_3, \n " +
						"		ISNULL((SUM(ISNULL(LSXSP0.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)), 0) AS SX_TUAN_4 \n " +  		
						"FROM ERP_LENHSANXUAT_GIAY LSX0     \n " +
						"INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP0 ON LSXSP0.LENHSANXUAT_FK = LSX0.PK_SEQ \n " +  
						"LEFT JOIN(   \n " +
						"	SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG \n " +  
						"	FROM ERP_NHAPKHO NK   \n " +
						"	INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ \n " +   
						"	WHERE NK.TRANGTHAI = 1   \n " +
						"	AND NK.KHONHAP IN (100005, 100006) \n " +  
						"	GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT \n " +  
						")NH ON NH.SANPHAM_FK = LSXSP0.SANPHAM_FK AND NH.SOLENHSANXUAT = LSX0.PK_SEQ \n " +  
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXSP0.SANPHAM_FK AND SP.PK_SEQ = " + spId + " \n " +
						"WHERE LSX0.NGAYDUKIENHT >=  '" + nam_thang + "22'    \n " +
						"AND LSX0.NGAYDUKIENHT <= '" + nam_thang + "28'  AND LSX0.TRANGTHAI < 6 \n " +   
						"GROUP BY LSXSP0.SANPHAM_FK, NH.NHANHANG   \n " +
						")DATA ";

		//System.out.println("::: LSX TUAN: " + query);
		try
		{
			ResultSet rs = this.db.get(query);
			if(rs != null){
				rs.next();
				tmp[0] = rs.getString("SX_TUAN_1");
				tmp[1] = rs.getString("SX_TUAN_2");
				tmp[2] = rs.getString("SX_TUAN_3");
				tmp[3] = rs.getString("SX_TUAN_4");
				rs.close();
			}

		}
		catch(java.sql.SQLException e){}

		return tmp;
	}

	public void delete(String id ){
		String query = "DELETE FROM ERP_LENHSANXUATDUKIEN WHERE PK_SEQ = '" + id + "'";
		this.db.update(query);
	}

	public ResultSet getNhamayRS(){
		return this.nmRS;
	}
	
	public void setNhamayRS(ResultSet nmRS){
		this.nmRS = nmRS;
	}

	public ResultSet getSanphamRS(){
		return this.sanphamRS;
	}
	
	public void setSanphamRS(ResultSet spRS){
		this.sanphamRS = spRS;
	}
	
	
	public String getNhamayId(){
		return this.nmId;
	}
	
	public void setNhamayId(String nmId){
		this.nmId = nmId;
	}

	
	public void DbClose() 
	{
		try 
		{
			if(this.nmRS != null) this.nmRS.close();
			if(this.dnsxRs != null) this.dnsxRs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.db.shutDown();
		}
	}

	public ResultSet getLenhsanxuatdkRs() 
	{
		return this.dnsxRs;
	}

	public void setLenhsanxuatdkRs(ResultSet dnsxRs) 
	{
		this.dnsxRs = dnsxRs;
	}

	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public String getLoaihh() {

		return this.loaihh;
	}


	public void setLoaihh(String loaihh) {

		this.loaihh = loaihh;
	}
	
}
