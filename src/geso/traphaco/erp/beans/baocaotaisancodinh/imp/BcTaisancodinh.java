package geso.traphaco.erp.beans.baocaotaisancodinh.imp;
import geso.traphaco.erp.beans.baocaotaisancodinh.*;
import geso.traphaco.erp.db.sql.dbutils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;

public class BcTaisancodinh implements IBcTaisancodinh {
	String userId;
	String ctyId;	
	String ctyTen;
	String tungay;
	String denngay;
	String thang;
	String nam;
	String diachi;
	String masothue;	
	String ltsId;
	String tsId;
	String loai;
	ResultSet ts;
	ResultSet loaits;
	ResultSet khauhao;
		
	String msg;
	dbutils db;
	
	
	public BcTaisancodinh() {
		this.userId = "";
		this.ctyId = "100005";
		this.ctyTen = "";
		this.denngay = "";		
		this.tungay = "";
		this.thang = "";
		this.nam = "";
		this.diachi = "";
		this.masothue = "";
		this.ltsId = "";
		this.tsId = "";

		this.msg = "";
		this.db = new dbutils();
	}

	public void setuserId(String userId) {

		this.userId = userId;
	}

	public String getuserId() {

		return this.userId;
	}

	public void setCtyId(String ctyId) {

		this.ctyId = ctyId;
	}

	public String getCtyId() {

		return this.ctyId;
	}

	public String getCtyTen() {
		return this.ctyTen;
	}

	public String getDiachi() {

		return this.diachi;
	}

	public String getMasothue() {

		return this.masothue;
	}
	
	public void setTungay(String tungay) {

		this.tungay = tungay;
	}

	public String getTungay() {
		return this.tungay;	
	}

	public String getDenngay() {
		return this.denngay;			
	}

	public void setDenngay(String denngay) {

		this.denngay = denngay;
	}

	public String getThang() {
		return this.thang;			
	}

	public void setThang(String thang) {

		this.thang = thang;
	}

	public String getNam() {
		return this.nam;			
	}

	public void setNam(String nam) {

		this.nam = nam;
	}

	public void setMsg(String msg) {

		this.msg = msg;
	}

	public String getMsg() {

		return this.msg;
	}

	public String getLtsId() {

		return this.ltsId;
	}

	public void setLtsId(String ltsId) {

		this.ltsId = ltsId;
	}
	
	public String getTsId() {

		return this.tsId;
	}

	public void setTsId(String tsId) {

		this.tsId = tsId;
	}

	public ResultSet getTaisan(){
		return this.ts;
	}
	
	public ResultSet getLoaiTS(){
		return this.loaits;
	}

	public ResultSet getKhauhao(){
		return this.khauhao;
	}
	


	public ResultSet getTscdlist(){
		return this.ts;
	}
	public void init(){
		this.loaits = this.db.get("SELECT PK_SEQ AS LTSID, DIENGIAI AS LTS FROM Erp_LOAITAISAN WHERE TRANGTHAI = 1" );
		String query = 	"";
		
		query = 	"SELECT	LTS.DIENGIAI AS LTS, TSCD.MA, TSCD.DIENGIAI AS TENTAISAN, TSCD.DONVI \n" + 
					", TSCD.SOLUONG, TSCD.THANGBATDAUKH AS NGAYBATDAUDUNG, THANHTIEN AS NGUYENGIA ,\n" +
					"TSCD.SOTHANGKH, (SELECT TOP 1 KHAUHAODUKIEN FROM ERP_TAISANCODINH_CHITIET WHERE TAISAN_FK = TSCD.PK_SEQ AND KHAUHAOTHUCTE = 0) AS KHAUHAOTHANG \n" +
					"FROM ERP_TAISANCODINH TSCD \n" +
					"INNER JOIN ERP_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n";
		
		this.ts = this.db.get(query);
		
		if(this.tsId.length() >0){
			
			String taisanId = "0";
			String[] taisan_chuoi = this.tsId.split("-");			
			taisanId = taisan_chuoi[0];
			
			query = query + "WHERE TSCD.PK_SEQ = " + taisanId ;
			System.out.println("query lay bc theo doi:" +query);
	
			this.ts = this.db.get(query);
			String [] param = new String [2];
			param[0]= taisanId;
			param[1]= getDate();
					
			System.out.println(query);
			this.khauhao = this.db.getRsByPro("LAYBANGKHAUHAO", param);
		}
				
	}
	
	public void init_TheodoiTSCD(){
		this.nam = this.getDate().substring(0, 4);
		
		ResultSet rs = this.db.get("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
		System.out.println("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
		try{
			if(rs != null) {
				if (rs.next())
				{
					this.ctyTen = rs.getString("TEN");
					this.diachi = rs.getString("DIACHI");
					System.out.println(this.diachi);
					
					this.masothue = rs.getString("MASOTHUE");
					System.out.println(this.masothue);
				}				
				rs.close();
			}
						
		}catch(java.sql.SQLException e){}
		String query = "";
		if(this.nam.length() > 0){
			if(loai.equals("1"))
			{
			query = "SELECT	TSCD.MA, TSCD.DIENGIAI, ISNULL(TSCD.DONVI,'') DONVI, \n" + 
					"TSCD.SOLUONG, ISNULL(NH.NGAYNHAN, '') AS NGAY, ISNULL(NH.SOHOADON, '') AS SOCHUNGTU, \n" + 
					"ISNULL(TK1.SOHIEUTAIKHOAN, '') AS TK, ISNULL(TK2.SOHIEUTAIKHOAN, '') AS TKKH, \n" +
					"ISNULL(TK3.SOHIEUTAIKHOAN, '') AS TKNKH, \n" +
					"ISNULL(TSCD.THANGBATDAUKH, '') AS NGAYBATDAUDUNG, ISNULL(TSCD.THANHTIEN, 0) + ISNULL(THAYDOI.DIEUCHINHNGUYENGIA,0) AS VND, \n" +
					"ISNULL(MHSP.DONGIA*MHSP.SOLUONG, 0) AS NGUYENTE, \n" +
					"ISNULL(TSCD.SOTHANGKH, 0) AS SOTHANGSUDUNG, \n" +
					"ISNULL(THAYDOI.DIEUCHINHNGUYENGIA,0) as DIEUCHINHNGUYENGIA ,\n"+
					"ISNULL(THAYDOI.DIEUCHINHSOTHANGKHAUHAO,0) as DIEUCHINHSOTHANGKHAUHAO, \n"+
					"CASE WHEN (ISNULL(TSCD.SOTHANGKH, 0) + ISNULL(THAYDOI.DIEUCHINHSOTHANGKHAUHAO,0) - ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOTAISAN WHERE KHAUHAO > 0 AND TAISAN_FK = TSCD.PK_SEQ GROUP BY  TAISAN_FK),0) - ISNULL(TSCD.SOTHANGDAKHAUHAO,0) !=0) THEN  \n"+
					"ROUND((ISNULL(TSCD.THANHTIEN, 0) + ISNULL(THAYDOI.DIEUCHINHNGUYENGIA,0) - ISNULL(TSCD.GIATRIDAKHAUHAO,0)  - ISNULL(LUYKEDAUKY.KHAUHAO, 0))/(ISNULL(TSCD.SOTHANGKH, 0) + ISNULL(THAYDOI.DIEUCHINHSOTHANGKHAUHAO,0) - ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOTAISAN WHERE KHAUHAO > 0 AND TAISAN_FK = TSCD.PK_SEQ GROUP BY  TAISAN_FK),0) - ISNULL(TSCD.SOTHANGDAKHAUHAO,0)),0) \n"+
					"WHEN (ISNULL(TSCD.SOTHANGKH, 0) + ISNULL(THAYDOI.DIEUCHINHSOTHANGKHAUHAO,0) - ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOTAISAN WHERE KHAUHAO > 0 AND TAISAN_FK = TSCD.PK_SEQ GROUP BY  TAISAN_FK),0) - ISNULL(TSCD.SOTHANGDAKHAUHAO,0) =0) THEN 0  \n"+
					"WHEN (ISNULL(TSCD.SOTHANGKH, 0) + ISNULL(THAYDOI.DIEUCHINHSOTHANGKHAUHAO,0) - ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOTAISAN WHERE KHAUHAO > 0 AND TAISAN_FK = TSCD.PK_SEQ GROUP BY  TAISAN_FK),0) - ISNULL(TSCD.SOTHANGDAKHAUHAO,0) =1) THEN  \n"+
					"ROUND((ISNULL(TSCD.THANHTIEN, 0) + ISNULL(THAYDOI.DIEUCHINHNGUYENGIA,0) - ISNULL(TSCD.GIATRIDAKHAUHAO,0) - ISNULL(LUYKEDAUKY.KHAUHAO, 0))-(ISNULL(ISNULL(TSCD.THANHTIEN, 0) - ISNULL(TSCD.GIATRIDAKHAUHAO,0) - ISNULL(LUYKEDAUKY.KHAUHAO, 0)/(ISNULL(TSCD.SOTHANGKH, 0) + ISNULL(THAYDOI.DIEUCHINHSOTHANGKHAUHAO,0) - ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOTAISAN WHERE KHAUHAO > 0 AND TAISAN_FK = TSCD.PK_SEQ GROUP BY  TAISAN_FK),0) - ISNULL(TSCD.SOTHANGDAKHAUHAO,0)),0) *(ISNULL(TSCD.SOTHANGDAKHAUHAO, 0) + ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOTAISAN WHERE KHAUHAO > 0 AND TAISAN_FK = TSCD.PK_SEQ GROUP BY TAISAN_FK),0))),0)  \n"+
					"ELSE 0 END AS KHAUHAOTHANG,  \n"+
					"ISNULL(TSCD.thangbatdauKH,0) AS THANGBATDAUKH, \n" +
					"ISNULL(TSCD.SOTHANGDAKHAUHAO, 0) + ISNULL((SELECT TOP 1 THANGTHU FROM ERP_KHAUHAOTAISAN WHERE KHAUHAO > 0 AND TAISAN_FK = TSCD.PK_SEQ ORDER BY NAM DESC, THANG DESC),0) AS SOTHANGDAKH, \n" +
					"ISNULL(TSCD.SOTHANGKH, 0) + ISNULL(THAYDOI.DIEUCHINHSOTHANGKHAUHAO,0) - ISNULL((SELECT TOP 1 THANGTHU FROM ERP_KHAUHAOTAISAN WHERE KHAUHAO > 0 AND TAISAN_FK = TSCD.PK_SEQ ORDER BY NAM DESC, THANG DESC ),0) - ISNULL(TSCD.SOTHANGDAKHAUHAO,0) AS SOTHANGCONLAI, \n" +
					"ISNULL(TSCD.THANHTIEN, 0) - ISNULL(TSCD.GIATRIDAKHAUHAO,0) - ISNULL(LUYKEDAUKY.KHAUHAO, 0) AS GIATRIDAUKY, \n" +
					"ISNULL(LUYKEDAUKY.KHAUHAO, 0) AS LUYKEDAUKY, \n" +
					"ISNULL(LUYKETRONGKY.KHAUHAO, 0) AS LUYKETRONGKY, \n" +
					"ISNULL(T1.KHAUHAO, 0) AS T1, ISNULL(T2.KHAUHAO, 0) AS T2, ISNULL(T3.KHAUHAO, 0) AS T3, ISNULL(T4.KHAUHAO, 0) AS T4, ISNULL(T5.KHAUHAO, 0) AS T5, ISNULL(T6.KHAUHAO, 0) AS T6, \n" + 
					"ISNULL(T7.KHAUHAO, 0) AS T7, ISNULL(T8.KHAUHAO, 0) AS T8, ISNULL(T9.KHAUHAO, 0) AS T9, ISNULL(T10.KHAUHAO, 0) AS T10, ISNULL(T11.KHAUHAO, 0) AS T11, ISNULL(T12.KHAUHAO, 0) AS T12, \n" + 
					"ISNULL(TSCD.THANHTIEN, 0) + ISNULL(THAYDOI.DIEUCHINHNGUYENGIA,0)- ISNULL(LUYKEDAUKY.KHAUHAO, 0) - ISNULL(LUYKETRONGKY.KHAUHAO, 0) - ISNULL(TSCD.GIATRIDAKHAUHAO,0) AS CONLAICUOIKY \n" +
		
					"FROM ERP_TAISANCODINH TSCD \n" +
					"LEFT JOIN ERP_MUAHANG_SP MHSP ON MHSP.TAISAN_FK = TSCD.PK_SEQ \n" +
					"LEFT JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MHSP.MUAHANG_FK AND MH.PK_SEQ = MHSP.MUAHANG_FK \n" +
					"LEFT JOIN ERP_NHANHANG NH ON NH.MUAHANG_FK = MHSP.MUAHANG_FK \n" +
					"LEFT JOIN ERP_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n" +
					"LEFT JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = LTS.TAIKHOAN_FK \n" +
					"LEFT JOIN ERP_TAIKHOANKT TK2 ON TK2.PK_SEQ = LTS.TAIKHOANKH_FK \n" +
					"LEFT JOIN ERP_TAISANCODINH_CONGDUNG TSCD_CD ON TSCD.pk_seq = TSCD_CD.TAISAN_FK \n"+
					"LEFT JOIN Erp_CONGDUNG CD ON CD.PK_SEQ = TSCD_CD.CONGDUNG_FK \n" +
					"LEFT JOIN ERP_TAIKHOANKT TK3 ON TK3.PK_SEQ = CD.TAIKHOAN_FK \n" +
					"LEFT JOIN " +
					"( " +
					" SELECT TSCD_FK, SUM(GIATRI) AS DIEUCHINHNGUYENGIA ,SUM(SOTHANG) AS DIEUCHINHSOTHANGKHAUHAO \n" +
					" FROM ERP_TAISANCODINH_DIEUCHINH \n" +
					" where YEAR(NGAYDIEUCHINH) <= '"+this.nam +"' \n" +
					" GROUP BY TSCD_FK \n"+
					") THAYDOI ON THAYDOI.TSCD_FK = TSCD.PK_SEQ \n" +
					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM < " + this.nam + " \n" + 
					"	GROUP BY TAISAN_FK \n" +
					")LUYKEDAUKY ON LUYKEDAUKY.TSID = TSCD.PK_SEQ \n" +

					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + " \n" +
					"	GROUP BY TAISAN_FK \n" +
					")LUYKETRONGKY ON LUYKETRONGKY.TSID = TSCD.PK_SEQ \n" +
					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + " AND THANG = 1 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T1 ON T1.TSID = TSCD.PK_SEQ \n" +

					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + "  AND THANG = 2 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T2 ON T2.TSID = TSCD.PK_SEQ \n" +
					
					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + "  AND THANG = 3 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T3 ON T3.TSID = TSCD.PK_SEQ \n" +
					
					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + "  AND THANG = 4 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T4 ON T4.TSID = TSCD.PK_SEQ \n" +
					
					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + " AND THANG = 5 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T5 ON T5.TSID = TSCD.PK_SEQ \n" +
					
					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + " AND THANG = 6 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T6 ON T6.TSID = TSCD.PK_SEQ \n" +
					
					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + " AND THANG = 7 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T7 ON T7.TSID = TSCD.PK_SEQ \n" +

					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + "  AND THANG = 8 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T8 ON T8.TSID = TSCD.PK_SEQ \n" +

					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + " AND THANG = 9 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T9 ON T9.TSID = TSCD.PK_SEQ \n" +

					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + " AND THANG = 10 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T10 ON T10.TSID = TSCD.PK_SEQ \n" +

					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + " AND THANG = 11 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T11 ON T11.TSID = TSCD.PK_SEQ \n" +

					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n" + 
					"	FROM ERP_KHAUHAOTAISAN \n" +
					"	WHERE NAM = " + this.nam + " AND THANG = 12 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")T12 ON T12.TSID = TSCD.PK_SEQ \n" +
					"WHERE \n" +
					//"(ISNULL(TSCD.THANHTIEN, 0) - ISNULL(LUYKEDAUKY.KHAUHAO, 0)  > 0)  " +
					//"AND " +
					" ( ISNULL(TSCD.ISDATHANHLY,0) !='1')  \n" + // TS NÀO ĐÃ THANH LÝ RỒI KHÔNG HIỆN LÊN NỮA
					"ORDER BY TSCD.MA ";
			}
			else 
			{
				query = " SELECT	CCDC.MA, CCDC.DIENGIAI, ISNULL(CCDC.DONVI,'') DONVI, \n " +
						"	CCDC.SOLUONG, ISNULL(NH.NGAYNHAN, '') AS NGAY, ISNULL(NH.SOHOADON, '') AS SOCHUNGTU, \n  " +
						"	ISNULL(TK1.SOHIEUTAIKHOAN, '') AS TK, ISNULL(TK2.SOHIEUTAIKHOAN, '') AS TKKH,\n " +
						"	ISNULL(TK3.SOHIEUTAIKHOAN, '') AS TKNKH, " +
						"	ISNULL(CCDC.THANGBATDAUKH, '') AS NGAYBATDAUDUNG,ISNULL(CCDC.THANHTIEN,0) + ISNULL(NGUYENGIA.GIATRI, 0) + ISNULL(DIEUCHINH.GIATRI, 0) AS VND,\n " +
						"	ISNULL(CCDC.THANHTIEN,0)+ISNULL(NGUYENGIA.GIATRI, 0) AS NGUYENTE,\n " +
						"	ISNULL(CCDC.SOTHANGKH, 0) AS SOTHANGSUDUNG,\n " +
						"   ISNULL(DIEUCHINH.GIATRI, 0) as DIEUCHINHNGUYENGIA ,\n"+
						"	0 as DIEUCHINHSOTHANGKHAUHAO, \n"+
						"	CASE WHEN (ISNULL(CCDC.SOTHANGKH, 0) - ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOCCDC WHERE KHAUHAO > 0 AND CCDC_FK = CCDC.PK_SEQ GROUP BY  CCDC_FK),0) - ISNULL(CCDC.SOTHANGDAKHAUHAO,0) !=0) THEN  \n"+
						"	ROUND((ISNULL(CCDC.THANHTIEN, 0)  - ISNULL(CCDC.GIATRIDAKHAUHAO,0)  - ISNULL(LUYKEDAUKY.KHAUHAO, 0))/(ISNULL(CCDC.SOTHANGKH, 0)  - ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOCCDC WHERE KHAUHAO > 0 AND CCDC_FK = CCDC.PK_SEQ GROUP BY  CCDC_FK),0) - ISNULL(CCDC.SOTHANGDAKHAUHAO,0)),0) \n"+
						"	WHEN (ISNULL(CCDC.SOTHANGKH, 0)  - ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOCCDC WHERE KHAUHAO > 0 AND CCDC_FK = CCDC.PK_SEQ GROUP BY  CCDC_FK),0) - ISNULL(CCDC.SOTHANGDAKHAUHAO,0) =0) THEN 0  \n"+
						"	WHEN (ISNULL(CCDC.SOTHANGKH, 0)  - ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOCCDC WHERE KHAUHAO > 0 AND CCDC_FK = CCDC.PK_SEQ GROUP BY  CCDC_FK),0) - ISNULL(CCDC.SOTHANGDAKHAUHAO,0) =1) THEN  \n"+
						"	ROUND((ISNULL(CCDC.THANHTIEN, 0)  - ISNULL(CCDC.GIATRIDAKHAUHAO,0) - ISNULL(LUYKEDAUKY.KHAUHAO, 0))-(ISNULL(ISNULL(CCDC.THANHTIEN, 0) - ISNULL(CCDC.GIATRIDAKHAUHAO,0) - ISNULL(LUYKEDAUKY.KHAUHAO, 0)/(ISNULL(CCDC.SOTHANGKH, 0) - ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOCCDC WHERE KHAUHAO > 0 AND CCDC_FK = CCDC.PK_SEQ GROUP BY  CCDC_FK),0) - ISNULL(CCDC.SOTHANGDAKHAUHAO,0)),0) *(ISNULL(CCDC.SOTHANGDAKHAUHAO, 0) + ISNULL((SELECT COUNT(KHAUHAO) FROM ERP_KHAUHAOCCDC WHERE KHAUHAO > 0 AND CCDC_FK = CCDC.PK_SEQ GROUP BY CCDC_FK),0))),0)  \n"+
						"   ELSE 0 END AS KHAUHAOTHANG,  \n"+
			
						"	ISNULL(CCDC.thangbatdauKH,0) AS THANGBATDAUKH,\n " +
						"	ISNULL(CCDC.SOTHANGDAKHAUHAO, 0) + ISNULL((SELECT TOP 1 THANGTHU FROM ERP_KHAUHAOCCDC WHERE CCDC_FK = CCDC.PK_SEQ AND KHAUHAO > 0 ORDER BY NAM DESC, THANG DESC),0)  AS SOTHANGDAKH,\n " +
						"	ISNULL(CCDC.SOTHANGKH, 0) - ISNULL((SELECT TOP 1 THANGTHU FROM ERP_KHAUHAOCCDC WHERE KHAUHAO > 0 AND CCDC_FK = CCDC.PK_SEQ ORDER BY NAM DESC, THANG DESC ),0) - ISNULL(CCDC.SOTHANGDAKHAUHAO,0) AS SOTHANGCONLAI,\n " +
						"	ISNULL(CCDC.THANHTIEN,0)+ ISNULL(NGUYENGIA.GIATRI, 0) + ISNULL(DIEUCHINH.GIATRI, 0) - ISNULL(LUYKEDAUKY.KHAUHAO, 0) - ISNULL(CCDC.GIATRIDAKHAUHAO,0) AS GIATRIDAUKY,\n " +
						"	ISNULL(LUYKEDAUKY.KHAUHAO, 0) AS LUYKEDAUKY,\n " +
						"	ISNULL(LUYKETRONGKY.KHAUHAO, 0) AS LUYKETRONGKY, " +
						"	ISNULL(T1.KHAUHAO, 0) AS T1, ISNULL(T2.KHAUHAO, 0) AS T2, ISNULL(T3.KHAUHAO, 0) AS T3, ISNULL(T4.KHAUHAO, 0) AS T4, ISNULL(T5.KHAUHAO, 0) AS T5, ISNULL(T6.KHAUHAO, 0) AS T6, \n " +
						"	ISNULL(T7.KHAUHAO, 0) AS T7, ISNULL(T8.KHAUHAO, 0) AS T8, ISNULL(T9.KHAUHAO, 0) AS T9, ISNULL(T10.KHAUHAO, 0) AS T10, ISNULL(T11.KHAUHAO, 0) AS T11, ISNULL(T12.KHAUHAO, 0) AS T12, \n " +
						"	ISNULL(CCDC.THANHTIEN,0)+ ISNULL(NGUYENGIA.GIATRI, 0) + ISNULL(DIEUCHINH.GIATRI, 0) - ISNULL(LUYKEDAUKY.KHAUHAO, 0) - ISNULL(LUYKETRONGKY.KHAUHAO, 0) - ISNULL(CCDC.GIATRIDAKHAUHAO,0) AS CONLAICUOIKY \n " +
				
						"	FROM ERP_CONGCUDUNGCU CCDC \n " +
						"	LEFT JOIN ERP_MUAHANG_SP MHSP ON MHSP.CCDC_FK = CCDC.PK_SEQ \n " +
						"	LEFT JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MHSP.MUAHANG_FK \n " +
						"	LEFT JOIN ERP_NHANHANG NH ON NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +
						"	LEFT JOIN Erp_LOAICCDC LCC ON LCC.PK_SEQ = CCDC.LOAICCDC_FK \n " +
						"	LEFT JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = LCC.TAIKHOAN_FK \n " +
						"	LEFT JOIN ERP_TAIKHOANKT TK2 ON TK2.PK_SEQ = LCC.TAIKHOAN_FK \n " +
						"	LEFT JOIN ERP_NHOMCCDC NCC ON NCC.PK_SEQ = CCDC.NHOMCCDC_FK \n " +
						"   LEFT JOIN ERP_CONGCUDUNGCU_CONGDUNG CC_CD ON CC_CD.CCDC_FK=CCDC.PK_SEQ \n " +
						"	LEFT JOIN ERP_CONGDUNGCCDC CD ON CD.PK_SEQ = CC_CD.CONGDUNG_FK \n " +
						"	LEFT JOIN ERP_TAIKHOANKT TK3 ON TK3.PK_SEQ = CD.TAIKHOAN_FK \n " +
						"	LEFT JOIN \n " +
						"	( " +
						"	 SELECT CCDC_FK, SUM(GIATRI) AS GIATRI  \n" +
						"	 FROM ERP_CONGCUDUNGCU_DIEUCHINH \n" +
						" 	 where YEAR(NGAYDIEUCHINH) <= '"+this.nam +"' \n" +
						"    AND ISNULL(ISNGUYENGIA,'0') = '0' \n" + 
						"	 GROUP BY CCDC_FK \n"+
						"	) DIEUCHINH ON DIEUCHINH.CCDC_FK = CCDC.PK_SEQ \n" +
						"	LEFT JOIN \n " +
						"	( " +
						"	 SELECT CCDC_FK, SUM(GIATRI) AS GIATRI  \n" +
						"	 FROM ERP_CONGCUDUNGCU_DIEUCHINH \n" +
						" 	 where YEAR(NGAYDIEUCHINH) <= '"+this.nam +"' \n" +
						"	 AND ISNULL(ISNGUYENGIA,'0')='1' \n"+
						"	 GROUP BY CCDC_FK \n"+
						"	) NGUYENGIA ON NGUYENGIA.CCDC_FK = CCDC.PK_SEQ \n" +
						"	LEFT JOIN \n " +
						"	( " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM < " + this.nam + " \n " + 
						"		GROUP BY CCDC_FK \n " +
						"	)LUYKEDAUKY ON LUYKEDAUKY.TSID = CCDC.PK_SEQ \n " +
			
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM = " + this.nam + " \n " + 
						"		GROUP BY CCDC_FK \n " +
						"	)LUYKETRONGKY ON LUYKETRONGKY.TSID = CCDC.PK_SEQ \n " +
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC  \n " +
						"	WHERE NAM = " + this.nam + " AND THANG = 1 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T1 ON T1.TSID = CCDC.PK_SEQ \n " +
			
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM = " + this.nam + " AND THANG = 2 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T2 ON T2.TSID = CCDC.PK_SEQ \n " +
							
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM = " + this.nam + " AND THANG = 3 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T3 ON T3.TSID = CCDC.PK_SEQ \n " +
							
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM = " + this.nam + " AND THANG = 4 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T4 ON T4.TSID = CCDC.PK_SEQ \n " +
							
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM = " + this.nam + " AND THANG = 5 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T5 ON T5.TSID = CCDC.PK_SEQ \n " +
							
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM = " + this.nam + " AND THANG = 6 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T6 ON T6.TSID = CCDC.PK_SEQ \n " +
							
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM = " + this.nam + " AND THANG = 7 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T7 ON T7.TSID = CCDC.PK_SEQ \n " +
			
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM = " + this.nam + " AND THANG = 8 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T8 ON T8.TSID = CCDC.PK_SEQ \n " +
			
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM = " + this.nam + " AND THANG = 9 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T9 ON T9.TSID = CCDC.PK_SEQ \n " +
			
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM = " + this.nam + " AND THANG = 10 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T10 ON T10.TSID = CCDC.PK_SEQ \n " +
			
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO  \n " +
						"		FROM ERP_KHAUHAOCCDC \n " + 
						"	WHERE NAM = " + this.nam + " AND THANG = 11 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T11 ON T11.TSID = CCDC.PK_SEQ \n " +
			
						"	LEFT JOIN \n " +
						"	( \n " +
						"		SELECT CCDC_FK AS TSID, SUM(KHAUHAO) AS KHAUHAO \n " +
						"		FROM ERP_KHAUHAOCCDC \n " +
						"	WHERE NAM = " + this.nam + " AND THANG = 12 \n " +
						"		GROUP BY CCDC_FK \n " +
						"	)T12 ON T12.TSID = CCDC.PK_SEQ \n " +
						"	WHERE ISNULL(CCDC.ISDATHANHLY,'0') !='1'  \n" +
						//" (ISNULL(CCDC.THANHTIEN, 0) - ISNULL(LUYKEDAUKY.KHAUHAO, 0)  > 0) \n " +
						"	ORDER BY CCDC.MA ;\n " ;
				
				
		
			}
			System.out.println(query);
			this.ts = this.db.get(query);
		}	
	}
	
	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void DBClose(){
		try{
			if(this.ts != null) this.ts.close();
			if(this.loaits != null) this.loaits.close();
			if (this.khauhao != null) this.khauhao.close(); 
					
			if(db != null) db.shutDown();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
	}

	@Override
	public void setLoai(String loai) {
		// TODO Auto-generated method stub
		this.loai=loai;
	}

	@Override
	public String getLoai() {
		// TODO Auto-generated method stub
		return this.loai;
	}
}
