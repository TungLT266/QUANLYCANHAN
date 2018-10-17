package geso.traphaco.erp.beans.bangchitiettaikhoan.imp;
import geso.traphaco.erp.beans.bangchitiettaikhoan.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;

public class Bangchitiettaikhoan implements IBangchitiettaikhoan {
	String userId;
	String ctyId;	
	String ctyTen;
	String year;
	String month;
	String diachi;
	String masothue;
	String tkId;
	String sohieu;
	String daukyno;
	String daukyco;
	
	String tungay;
	String denngay;
	
	ResultSet rs;
	ResultSet tk;
	ResultSet taikhoanNhom;
	
	String msg;
	dbutils db;
	ResultSet congtyRs;
	String[] ctyIds;
	ResultSet chiNhanhRs;
	String chiNhanh;
	String chiNhanhTen;
	String chiNhanhMaSoThue;
	
	String ErpCongTyId;
	String view;
	
	String tknhomId;
	double cuoiKyCo;
	double cuoiKyNo;
	private String chiNhanhDiaChi;
	
	public Bangchitiettaikhoan() {
		this.userId = "";
		this.ctyId = "";
		this.ctyTen = "";
		this.chiNhanh = "1";
		this.chiNhanhTen = "";
		this.sohieu = "";
		this.daukyno = "0";
		this.cuoiKyCo = 0;
		this.cuoiKyNo = 0;
		this.tkId = "100001";
		this.tungay =  "";
		this.denngay =  "";
		this.month = Integer.toString(Integer.parseInt(getDate().substring(5, 7)));
		System.out.println(this.month);
		
		this.year = getDate().substring(0, 4);
		this.tknhomId = "111";

		this.msg = "";
		this.db = new dbutils();
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getView() {
		return this.view;
	}
	
	public void setCtyRs(ResultSet ctyRs) {

		this.congtyRs = ctyRs;
	}

	public ResultSet getCtyRs() {

		return this.congtyRs;
	}
	
	public void setCtyIds(String[] ctyIds) {

		this.ctyIds = ctyIds;
	}

	public String[] getCtyIds() {

		return this.ctyIds;
	}
	
	public String getErpCongtyId() {
		
		return this.ErpCongTyId;
	}

	
	public void setErpCongtyId(String id) {
		
		this.ErpCongTyId=id;
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

	public void setMonth(String month) {

		this.month = month;
	}

	public String getMonth() {
		if(this.month.length() > 0){
			return this.month;	
		}else{
			return this.getDate().substring(5, 7);
		}
		
	}
	
	public void setYear(String year) {

		this.year = year;
	}

	public String getYear() {
		if(this.year.length() > 0){
			return this.year;	
		}else{
			return this.getDate().substring(0, 4);
		}
	}


	public void setMsg(String msg) {

		this.msg = msg;
	}

	public String getMsg() {

		return this.msg;
	}

	public void setTkId(String tkId) {

		this.tkId = tkId;
	}

	public String getTkId() {

		return this.tkId;
	}


	public String getSohieu() {

		return this.sohieu;
	}

	public String getDaukyno() {

		return this.daukyno;
	}

	public String getDaukyco() {

		return this.daukyco;
	}

	
	
	public ResultSet getData(){
		return this.rs;
	}
	
	public ResultSet getTaikhoan(){
		return this.tk;
	}

	public void init_0(){
		if(this.ctyIds != null){
			String tmp = "";
			for(int i = 0; i < this.ctyIds.length; i++){
				tmp += this.ctyIds[i] + ",";
			}
			this.ErpCongTyId = tmp.substring(0, tmp.length() - 1);
			
			System.out.println("Công ty: " + this.ErpCongTyId);
		}else{
			
			String tmp = "";
			ResultSet rs = this.db.get("SELECT PK_SEQ, TEN FROM ERP_CONGTY WHERE isTongCongTy = 0 AND TRANGTHAI = 1");
			try{
				while(rs.next()){
					tmp += rs.getString("PK_SEQ") + ",";
				}
				
				this.ErpCongTyId = tmp.substring(0, tmp.length() - 1);
				
			}catch(java.sql.SQLException e){}
		}
		
		String sql = "SELECT PK_SEQ, TEN FROM ERP_CONGTY WHERE isTongCongTy = 0 AND TRANGTHAI = 1";
		this.congtyRs = db.get(sql);	
	}

	public String getQuery(int loai){
		/*0 dau ky
		 * 1 phat sinh
		 * 2 cuoi ky
		 * 3 phat sinh danh cho tinh cuoi ky
		 * 4 dau ky cho tinh cuoi ky
		 */
		String temp =  (this.tkId.length() > 0) ? this.tkId : "SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '"+this.tknhomId+"%' AND NPP_FK = "+this.chiNhanh+"";
		int thangDK = (this.tungay.length() > 0) ? Integer.parseInt(this.tungay.substring(5, 7)) : 0;
		int namDK = (this.tungay.length() > 0) ? Integer.parseInt(this.tungay.substring(0, 4)) : 0;
		namDK = (thangDK == 1) ? --namDK : namDK;
		thangDK = (thangDK == 1) ? 12 : --thangDK;
		String month = this.tungay.substring(5, 7);
		String year = this.tungay.substring(0, 4);
		
		String query = "";
		if (loai == 0) {
			query = "--TK BINH THUONG\r\n" + 
					"select ISNULL(SUM(NO), 0) AS NO, ISNULL(SUM(CO), 0) AS CO\r\n" + 
					"from\r\n" + 
					"("+
					"SELECT \r\n" + 
					"	CASE\r\n" + 
					"		WHEN TAIKHOAN LIKE '131%' OR TAIKHOAN LIKE '136%' OR TAIKHOAN LIKE '138%' OR\r\n" + 
					"			 TAIKHOAN LIKE '331%' OR TAIKHOAN LIKE '336%' OR TAIKHOAN LIKE '338%' \r\n" + 
					"		THEN\r\n" + 
					"			ISNULL(SUM(NO), 0)\r\n" + 
					"\r\n" + 
					"		WHEN (TAIKHOAN LIKE '1%' OR TAIKHOAN LIKE '2%' OR TAIKHOAN LIKE '6%' OR\r\n" + 
					"			 TAIKHOAN LIKE '8%' OR TAIKHOAN LIKE '521%' OR TAIKHOAN LIKE '531%' OR\r\n" + 
					"			 TAIKHOAN LIKE '532%') AND (TAIKHOAN NOT LIKE '214%' OR TAIKHOAN NOT LIKE '219%')\r\n" + 
					"		THEN\r\n" + 
					"			ISNULL(SUM(NO) - SUM(CO), 0)\r\n" + 
					"		\r\n" + 
					"		WHEN (TAIKHOAN LIKE '3%' OR TAIKHOAN LIKE '4%' OR TAIKHOAN LIKE '5%' OR\r\n" + 
					"			 TAIKHOAN LIKE '7%' OR TAIKHOAN LIKE '214%' OR TAIKHOAN LIKE '219%') \r\n" + 
					"			  AND (TAIKHOAN NOT LIKE '521%' OR TAIKHOAN NOT LIKE '531%' OR TAIKHOAN NOT LIKE '532%')\r\n" + 
					"		THEN\r\n" + 
					"			0\r\n" + 
					"	END AS NO,\r\n" + 
					"		\r\n" + 
					"	CASE\r\n" + 
					"		WHEN TAIKHOAN LIKE '131%' OR TAIKHOAN LIKE '136%' OR TAIKHOAN LIKE '138%' OR\r\n" + 
					"			 TAIKHOAN LIKE '331%' OR TAIKHOAN LIKE '336%' OR TAIKHOAN LIKE '338%' \r\n" + 
					"		THEN\r\n" + 
					"			ISNULL(SUM(CO), 0)\r\n" + 
					"		WHEN (TAIKHOAN LIKE '1%' OR TAIKHOAN LIKE '2%' OR TAIKHOAN LIKE '6%' OR\r\n" + 
					"			 TAIKHOAN LIKE '8%' OR TAIKHOAN LIKE '521%' OR TAIKHOAN LIKE '531%' OR\r\n" + 
					"			 TAIKHOAN LIKE '532%') AND (TAIKHOAN NOT LIKE '214%' OR TAIKHOAN NOT LIKE '219%')\r\n" + 
					"		THEN\r\n" + 
					"			0\r\n" + 
					"		WHEN (TAIKHOAN LIKE '3%' OR TAIKHOAN LIKE '4%' OR TAIKHOAN LIKE '5%' OR\r\n" + 
					"			 TAIKHOAN LIKE '7%' OR TAIKHOAN LIKE '214%' OR TAIKHOAN LIKE '219%') \r\n" + 
					"			  AND (TAIKHOAN NOT LIKE '521%' OR TAIKHOAN NOT LIKE '531%' OR TAIKHOAN NOT LIKE '532%')\r\n" + 
					"		THEN\r\n" + 
					"			ISNULL(SUM(CO) - SUM(NO), 0)\r\n" + 
					"\r\n" + 
					"	END AS CO\r\n" + 
					"FROM \r\n" + 
					"(\r\n" + 
					"	SELECT TAIKHOAN, MADOITUONG, DOITUONG, CASE WHEN SUM(ROUND(NO, 0)) - SUM(ROUND(CO, 0)) > 0 THEN SUM(ROUND(NO, 0)) - SUM(ROUND(CO, 0))\r\n" + 
					"	ELSE 0 END AS NO,\r\n" + 
					"	CASE WHEN SUM(ROUND(NO, 0)) - SUM(ROUND(CO, 0)) < 0 THEN SUM(ROUND(CO, 0)) - SUM(ROUND(NO, 0))\r\n" + 
					"	ELSE 0 END AS CO\r\n" + 
					"	FROM\r\n" + 
					"	(";
			query += "--LAY DAU KY\r\n" + 
					"		SELECT '' AS PK_SEQ, '' AS NGAYCHUNGTU, 0 AS SOCHUNGTU, '' AS NOIDUNG, \r\n" + 
					"		SOHIEUTAIKHOAN AS TAIKHOAN, '' AS DOIUNG, GIATRINOVND AS NO, GIATRICOVND AS CO, DOITUONG, 0 AS SOHOADON, MADOITUONG\r\n" + 
					"		FROM ERP_TAIKHOAN_NOCO_KHOASO KS\r\n" + 
					"		LEFT JOIN ERP_TAIKHOANKT TK ON KS.TAIKHOANKT_FK = TK.PK_SEQ\r\n" + 
					"		WHERE TAIKHOANKT_FK IN ("+temp+") AND THANG = "+thangDK+" AND NAM = "+namDK+" \n";
			query += " \r\n" + 
					" UNION ALL \n";
			query += "		SELECT '' AS PK_SEQ, '' AS NGAYCHUNGTU, 0 AS SOCHUNGTU, '' AS NOIDUNG, \r\n" + 
					"		SOHIEUTAIKHOAN AS TAIKHOAN, '' AS DOIUNG, NO AS NO, CO AS CO, DOITUONG, 0 AS SOHOADON, MADOITUONG\r\n" + 
					"		FROM ERP_PHATSINHKETOAN PS\r\n" + 
					"		LEFT JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ\r\n" + 
					"		WHERE TAIKHOAN_FK IN ("+temp+") AND NGAYGHINHAN >='"+year+"-"+month+"-01' AND NGAYGHINHAN < '"+ this.tungay +"' \n " ;
			query += " \r\n" + 
					" UNION ALL \n";
			query += "		SELECT '' AS PK_SEQ, '' AS NGAYCHUNGTU, 0 AS SOCHUNGTU, '' AS NOIDUNG, \r\n" + 
					"		SOHIEUTAIKHOAN AS TAIKHOAN, '' AS DOIUNG, NO AS NO, CO AS CO, DOITUONG, 0 AS SOHOADON, MADOITUONG\r\n" + 
					"		FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PS\r\n" + 
					"		LEFT JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ\r\n" + 
					"		WHERE TAIKHOAN_FK IN ("+temp+") AND NGAYGHINHAN >='"+year+"-"+month+"-01' AND NGAYGHINHAN < '"+ this.tungay +"' \n " ;
			query +="\r\n" + 
					"	) TEMP\r\n" + 
					"	GROUP BY TAIKHOAN, DOITUONG, MADOITUONG\r\n" + 
					") DATA\r\n" + 
					"GROUP BY TAIKHOAN"+
					"\r\n" + 
					")CUOIKY";
		} else if (loai == 1){
			query = "--LAY PHAT SINH\r\n" + 
					"		SELECT *\r\n" + 
					"		FROM\r\n" + 
					"		(\r\n" + 
					"			--DU LIEU ERP\r\n" + 
					"			SELECT  CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'THU TIỀN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)\r\n" + 
					" 			WHEN  PHATSINH.LOAICHUNGTU LIKE N'THANH TOÁN HÓA ĐƠN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)\r\n" + 
					" 			ELSE CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) END AS PK_SEQ,\r\n" + 
					"					CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT NGAYXUATHD FROM ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"				WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT NGAYXUATHD FROM ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"				WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT NGAYHOADON FROM ERP_HOADONPHELIEU WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"				WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT NGAYXUATHD FROM ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"				WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT NGAYHOADON FROM ERP_HOADONNCC WHERE PARK_FK =PHATSINH.SOCHUNGTU) \r\n" + 
					"				WHEN PHATSINH.LOAICHUNGTU LIKE N'GIẢM/TĂNG GIÁ HÀNG BÁN' THEN (SELECT NGAYHOADON FROM ERP_GIAMGIAHANGBAN WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"			ELSE PHATSINH.NGAYCHUNGTU END  AS NGAYCHUNGTU,  \r\n" + 
					"			ISNULL(PHATSINH.SOCHUNGTU, '0') AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN.SOHIEUTAIKHOAN AS TAIKHOAN, \r\n" + 
					"			TAIKHOAN_2.SOHIEUTAIKHOAN AS DOIUNG, ISNULL(PHATSINH.NO, 0) AS NO, ISNULL(PHATSINH.CO, 0) AS CO,  DOITUONG,  \r\n" + 
					"			CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADONNCC WHERE PARK_FK =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADONPHELIEU WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"  			  WHEN PHATSINH.LOAICHUNGTU LIKE N'GIẢM/TĂNG GIÁ HÀNG BÁN' THEN  (SELECT SOHOADON FROM ERP_GIAMGIAHANGBAN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)	\r\n" + 
					"   			  WHEN PHATSINH.LOAICHUNGTU LIKE N'NHẬN HÀNG' THEN  (SELECT ISNULL(C.SOHOADON,'') FROM ERP_NHANHANG A INNER JOIN ERP_HOADONNCC C ON A.HDNCC_FK = C.PK_SEQ WHERE A.PK_SEQ=PHATSINH.SOCHUNGTU)		\r\n" + 
					"			ELSE '' END AS SOHOADON, ISNULL(PHATSINH.DIENGIAI,'') DIENGIAI, ISNULL(PHATSINH.SOLUONG, 0) SOLUONG, \r\n" + 
					"				\n ISNULL(PHATSINH.MAHANG, '') MAHANG, ISNULL(PHATSINH.TENHANG, '') TENHANG, ISNULL(DONVI, '') DONVI, ISNULL(PHATSINH.VAT, 0) VAT,  " +
					"\n ISNULL(PHATSINH.TIENHANG, 0) TIENHANG, ISNULL(PHATSINH.MAHOADON, '') MAHOADON, ISNULL(PHATSINH.MAUHOADON,'') MAUHOADON, " +
					"\n ISNULL(PHATSINH.KYHIEU, '') KYHIEU, ISNULL(PHATSINH.NGAYHOADON, '') NGAYHOADON, ISNULL(PHATSINH.TENNCC, '') TENNCC," +
					"\n ISNULL(PHATSINH.MST, '') MST, ISNULL(PHATSINH.TEN_SANPHAM, '') TEN_SANPHAM, ISNULL(PHATSINH.TEN_BENHVIEN,'') TEN_BENHVIEN," +
					"\n ISNULL(PHATSINH.TEN_TINHTHANH, '') TEN_TINHTHANH, ISNULL(PHATSINH.TEN_DIABAN, '' ) TEN_DIABAN, ISNULL(PHATSINH.TEN_VV, '') TEN_VV," +
					"\n ISNULL(PHATSINH.TEN_PB, '') TEN_PB, ISNULL(PHATSINH.TEN_KBH, '') TEN_KBH, ISNULL(PHATSINH.TEN_DT, '') TEN_DT, " +
					"\n ISNULL(PHATSINH.THUESUAT, 0) THUESUAT, ISNULL(PHATSINH.DIENGIAI_CT, '') DIENGIAI_CT \n "+
					"		 FROM ERP_PHATSINHKETOAN PHATSINH \r\n" + 
					"		 INNER JOIN ERP_TAIKHOANKT TAIKHOAN ON PHATSINH.TAIKHOAN_FK = TAIKHOAN.PK_SEQ  AND TAIKHOAN.NPP_FK = "+this.chiNhanh+"\r\n" + 
					"		 INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 ON PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN_2.PK_SEQ  AND TAIKHOAN_2.NPP_FK = "+this.chiNhanh+"\r\n" + 
					"		 LEFT JOIN ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK \r\n" + 
					"		 WHERE 1 = 1  AND PHATSINH.NGAYGHINHAN BETWEEN '"+this.tungay+"' AND '"+this.denngay+"'\r\n" + 
					"		 AND PHATSINH.TAIKHOAN_FK IN ( "+temp+" ) \r\n" + 
					"\r\n" + 
					"		 UNION ALL\r\n" + 
					"		 --TICH HOP DMS\r\n" + 
					"		 SELECT  CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'THU TIỀN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM " + Utility.prefixDMS + "ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)\r\n" + 
					" 				WHEN  PHATSINH.LOAICHUNGTU LIKE N'THANH TOÁN HÓA ĐƠN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM " + Utility.prefixDMS + "ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)\r\n" + 
					" 				ELSE CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) END AS PK_SEQ,\r\n" + 
					"					 CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT NGAYXUATHD FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"					WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT NGAYXUATHD FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"					WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT NGAYHOADON FROM " + Utility.prefixDMS + "ERP_HOADONPHELIEU WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"					WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT NGAYXUATHD FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"					WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT NGAYHOADON FROM " + Utility.prefixDMS + "ERP_HOADONNCC WHERE PARK_FK =PHATSINH.SOCHUNGTU) \r\n" + 
					"			   ELSE PHATSINH.NGAYCHUNGTU END  AS NGAYCHUNGTU,  \r\n" + 
					"			ISNULL(PHATSINH.SOCHUNGTU, '0') AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN.SOHIEUTAIKHOAN AS TAIKHOAN, \r\n" + 
					"		   TAIKHOAN_2.SOHIEUTAIKHOAN AS DOIUNG, ISNULL(PHATSINH.NO, 0) AS NO, ISNULL(PHATSINH.CO, 0) AS CO,  DOITUONG,  \r\n" + 
					"			CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADONNCC WHERE PARK_FK =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADONPHELIEU WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"			ELSE '' END AS SOHOADON, ISNULL(PHATSINH.DIENGIAI,'') DIENGIAI, ISNULL(PHATSINH.SOLUONG, 0) SOLUONG, \r\n" + 
					"				\n ISNULL(PHATSINH.MAHANG, '') MAHANG, ISNULL(PHATSINH.TENHANG, '') TENHANG, ISNULL(DONVI, '') DONVI, ISNULL(PHATSINH.VAT, 0) VAT,  " +
					"\n ISNULL(PHATSINH.TIENHANG, 0) TIENHANG, ISNULL(PHATSINH.MAHOADON, '') MAHOADON, ISNULL(PHATSINH.MAUHOADON,'') MAUHOADON, " +
					"\n ISNULL(PHATSINH.KYHIEU, '') KYHIEU, ISNULL(PHATSINH.NGAYHOADON, '') NGAYHOADON, ISNULL(PHATSINH.TENNCC, '') TENNCC," +
					"\n ISNULL(PHATSINH.MST, '') MST, ISNULL(PHATSINH.TEN_SANPHAM, '') TEN_SANPHAM, ISNULL(PHATSINH.TEN_BENHVIEN,'') TEN_BENHVIEN," +
					"\n ISNULL(PHATSINH.TEN_TINHTHANH, '') TEN_TINHTHANH, ISNULL(PHATSINH.TEN_DIABAN, '' ) TEN_DIABAN, ISNULL(PHATSINH.TEN_VV, '') TEN_VV," +
					"\n ISNULL(PHATSINH.TEN_PB, '') TEN_PB, ISNULL(PHATSINH.TEN_KBH, '') TEN_KBH, ISNULL(PHATSINH.TEN_DT, '') TEN_DT, " +
					"\n ISNULL(PHATSINH.THUESUAT, 0) THUESUAT, ISNULL(PHATSINH.DIENGIAI_CT, '') DIENGIAI_CT "+
					"		 FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PHATSINH \r\n" + 
					"		 INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN ON PHATSINH.TAIKHOAN_FK = TAIKHOAN.PK_SEQ  AND TAIKHOAN.NPP_FK = "+this.chiNhanh+"\r\n" + 
					"		 INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN_2 ON PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN_2.PK_SEQ  AND TAIKHOAN_2.NPP_FK = "+this.chiNhanh+"\r\n" + 
					"		 LEFT JOIN " + Utility.prefixDMS + "ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK \r\n" + 
					"		 WHERE 1 = 1  AND PHATSINH.NGAYGHINHAN  BETWEEN '"+this.tungay+"' AND '"+this.denngay+"'\r\n" + 
					"		 AND PHATSINH.TAIKHOAN_FK IN ( "+temp+" ) \r\n" + 
					"		)PS ORDER BY NGAYCHUNGTU, SOCHUNGTU, NO DESC, CO DESC   ";
		}	else if (loai == 3) {
			query = "--LAY PHAT SINH\r\n" + 
					"		SELECT *\r\n" + 
					"		FROM\r\n" + 
					"		(\r\n" + 
					"			--DU LIEU ERP\r\n" + 
					"			SELECT  CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'THU TIỀN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)\r\n" + 
					" 			WHEN  PHATSINH.LOAICHUNGTU LIKE N'THANH TOÁN HÓA ĐƠN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)\r\n" + 
					" 			ELSE CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) END AS PK_SEQ,\r\n" + 
					"					CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT NGAYXUATHD FROM ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"				WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT NGAYXUATHD FROM ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"				WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT NGAYHOADON FROM ERP_HOADONPHELIEU WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"				WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT NGAYXUATHD FROM ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"				WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT NGAYHOADON FROM ERP_HOADONNCC WHERE PARK_FK =PHATSINH.SOCHUNGTU) \r\n" + 
					"				WHEN PHATSINH.LOAICHUNGTU LIKE N'GIẢM/TĂNG GIÁ HÀNG BÁN' THEN (SELECT NGAYHOADON FROM ERP_GIAMGIAHANGBAN WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"			ELSE PHATSINH.NGAYCHUNGTU END  AS NGAYCHUNGTU,  \r\n" + 
					"			ISNULL(PHATSINH.SOCHUNGTU, '0') AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN.SOHIEUTAIKHOAN AS TAIKHOAN, \r\n" + 
					"			TAIKHOAN_2.SOHIEUTAIKHOAN AS DOIUNG, ISNULL(PHATSINH.NO, 0) AS NO, ISNULL(PHATSINH.CO, 0) AS CO,   \r\n" + 
					"			DOITUONG,  \r\n" + 
					"			CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADONNCC WHERE PARK_FK =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADONPHELIEU WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"  			  WHEN PHATSINH.LOAICHUNGTU LIKE N'GIẢM/TĂNG GIÁ HÀNG BÁN' THEN  (SELECT SOHOADON FROM ERP_GIAMGIAHANGBAN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)	\r\n" + 
					"   			  WHEN PHATSINH.LOAICHUNGTU LIKE N'NHẬN HÀNG' THEN  (SELECT ISNULL(C.SOHOADON,'') FROM ERP_NHANHANG A INNER JOIN ERP_HOADONNCC C ON A.HDNCC_FK = C.PK_SEQ WHERE A.PK_SEQ=PHATSINH.SOCHUNGTU)		\r\n" + 
					"			ELSE '' END AS SOHOADON, MADOITUONG\r\n" + 
					"		 FROM ERP_PHATSINHKETOAN PHATSINH \r\n" + 
					"		 INNER JOIN ERP_TAIKHOANKT TAIKHOAN ON PHATSINH.TAIKHOAN_FK = TAIKHOAN.PK_SEQ  AND TAIKHOAN.NPP_FK = "+this.chiNhanh+"\r\n" + 
					"		 INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 ON PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN_2.PK_SEQ  AND TAIKHOAN_2.NPP_FK = "+this.chiNhanh+"\r\n" + 
					"		 LEFT JOIN ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK \r\n" + 
					"		 WHERE 1 = 1  AND PHATSINH.NGAYGHINHAN BETWEEN '"+this.tungay+"' AND '"+this.denngay+"'\r\n" + 
					"		 AND PHATSINH.TAIKHOAN_FK IN ( "+temp+" ) \r\n" + 
					"\r\n" + 
					"		 UNION ALL\r\n" + 
					"		 --TICH HOP DMS\r\n" + 
					"		 SELECT  CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'THU TIỀN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM " + Utility.prefixDMS + "ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)\r\n" + 
					" 				WHEN  PHATSINH.LOAICHUNGTU LIKE N'THANH TOÁN HÓA ĐƠN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM " + Utility.prefixDMS + "ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)\r\n" + 
					" 				ELSE CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) END AS PK_SEQ,\r\n" + 
					"					 CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT NGAYXUATHD FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"					WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT NGAYXUATHD FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"					WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT NGAYHOADON FROM " + Utility.prefixDMS + "ERP_HOADONPHELIEU WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"					WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT NGAYXUATHD FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \r\n" + 
					"					WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT NGAYHOADON FROM " + Utility.prefixDMS + "ERP_HOADONNCC WHERE PARK_FK =PHATSINH.SOCHUNGTU) \r\n" + 
					"			   ELSE PHATSINH.NGAYCHUNGTU END  AS NGAYCHUNGTU,  \r\n" + 
					"			ISNULL(PHATSINH.SOCHUNGTU, '0') AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN.SOHIEUTAIKHOAN AS TAIKHOAN, \r\n" + 
					"		   TAIKHOAN_2.SOHIEUTAIKHOAN AS DOIUNG, ISNULL(PHATSINH.NO, 0) AS NO, ISNULL(PHATSINH.CO, 0) AS CO, DOITUONG,  \r\n" + 
					"			CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADONNCC WHERE PARK_FK =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"				 WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADONPHELIEU WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) \r\n" + 
					"			ELSE '' END AS SOHOADON, MADOITUONG\r\n" + 
					"		 FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PHATSINH \r\n" + 
					"		 INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN ON PHATSINH.TAIKHOAN_FK = TAIKHOAN.PK_SEQ  AND TAIKHOAN.NPP_FK = "+this.chiNhanh+"\r\n" + 
					"		 INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN_2 ON PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN_2.PK_SEQ  AND TAIKHOAN_2.NPP_FK = "+this.chiNhanh+"\r\n" + 
					"		 LEFT JOIN " + Utility.prefixDMS + "ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK \r\n" + 
					"		 WHERE 1 = 1  AND PHATSINH.NGAYGHINHAN  BETWEEN '"+this.tungay+"' AND '"+this.denngay+"'\r\n" + 
					"		 AND PHATSINH.TAIKHOAN_FK IN ( "+temp+" ) \r\n" + 
					"		)PS  ";
			
		} else if (loai == 2){
			query = "--TK BINH THUONG\r\n" + 
					"select ISNULL(SUM(NO), 0) AS NO, ISNULL(SUM(CO), 0) AS CO\r\n" + 
					"from\r\n" + 
					"("+
					"SELECT \r\n" + 
					"	CASE\r\n" + 
					"		WHEN TAIKHOAN LIKE '131%' OR TAIKHOAN LIKE '136%' OR TAIKHOAN LIKE '138%' OR\r\n" + 
					"			 TAIKHOAN LIKE '331%' OR TAIKHOAN LIKE '336%' OR TAIKHOAN LIKE '338%' \r\n" + 
					"		THEN\r\n" + 
					"			ISNULL(SUM(NO), 0)\r\n" + 
					"\r\n" + 
					"		WHEN (TAIKHOAN LIKE '1%' OR TAIKHOAN LIKE '2%' OR TAIKHOAN LIKE '6%' OR\r\n" + 
					"			 TAIKHOAN LIKE '8%' OR TAIKHOAN LIKE '521%' OR TAIKHOAN LIKE '531%' OR\r\n" + 
					"			 TAIKHOAN LIKE '532%') AND (TAIKHOAN NOT LIKE '214%' OR TAIKHOAN NOT LIKE '219%')\r\n" + 
					"		THEN\r\n" + 
					"			ISNULL(SUM(NO) - SUM(CO), 0)\r\n" + 
					"		\r\n" + 
					"		WHEN (TAIKHOAN LIKE '3%' OR TAIKHOAN LIKE '4%' OR TAIKHOAN LIKE '5%' OR\r\n" + 
					"			 TAIKHOAN LIKE '7%' OR TAIKHOAN LIKE '214%' OR TAIKHOAN LIKE '219%') \r\n" + 
					"			  AND (TAIKHOAN NOT LIKE '521%' OR TAIKHOAN NOT LIKE '531%' OR TAIKHOAN NOT LIKE '532%')\r\n" + 
					"		THEN\r\n" + 
					"			0\r\n" + 
					"	END AS NO,\r\n" + 
					"		\r\n" + 
					"	CASE\r\n" + 
					"		WHEN TAIKHOAN LIKE '131%' OR TAIKHOAN LIKE '136%' OR TAIKHOAN LIKE '138%' OR\r\n" + 
					"			 TAIKHOAN LIKE '331%' OR TAIKHOAN LIKE '336%' OR TAIKHOAN LIKE '338%' \r\n" + 
					"		THEN\r\n" + 
					"			ISNULL(SUM(CO), 0)\r\n" + 
					"		WHEN (TAIKHOAN LIKE '1%' OR TAIKHOAN LIKE '2%' OR TAIKHOAN LIKE '6%' OR\r\n" + 
					"			 TAIKHOAN LIKE '8%' OR TAIKHOAN LIKE '521%' OR TAIKHOAN LIKE '531%' OR\r\n" + 
					"			 TAIKHOAN LIKE '532%') AND (TAIKHOAN NOT LIKE '214%' OR TAIKHOAN NOT LIKE '219%')\r\n" + 
					"		THEN\r\n" + 
					"			0\r\n" + 
					"		WHEN (TAIKHOAN LIKE '3%' OR TAIKHOAN LIKE '4%' OR TAIKHOAN LIKE '5%' OR\r\n" + 
					"			 TAIKHOAN LIKE '7%' OR TAIKHOAN LIKE '214%' OR TAIKHOAN LIKE '219%') \r\n" + 
					"			  AND (TAIKHOAN NOT LIKE '521%' OR TAIKHOAN NOT LIKE '531%' OR TAIKHOAN NOT LIKE '532%')\r\n" + 
					"		THEN\r\n" + 
					"			ISNULL(SUM(CO) - SUM(NO), 0)\r\n" + 
					"\r\n" + 
					"	END AS CO\r\n" + 
					"FROM \r\n" + 
					"(\r\n" + 
					"	SELECT TAIKHOAN, MADOITUONG, DOITUONG, CASE WHEN SUM(ROUND(NO, 0)) - SUM(ROUND(CO, 0)) > 0 THEN SUM(ROUND(NO, 0)) - SUM(ROUND(CO, 0))\r\n" + 
					"	ELSE 0 END AS NO,\r\n" + 
					"	CASE WHEN SUM(ROUND(NO, 0)) - SUM(ROUND(CO, 0)) < 0 THEN SUM(ROUND(CO, 0)) - SUM(ROUND(NO, 0))\r\n" + 
					"	ELSE 0 END AS CO\r\n" + 
					"	FROM\r\n" + 
					"	(";
			query += getQuery(4);
			query += " \r\n" + 
					" UNION ALL";
			query += getQuery(3);
			query +="\r\n" + 
					"	) TEMP\r\n" + 
					"	GROUP BY TAIKHOAN, DOITUONG, MADOITUONG\r\n" + 
					") DATA\r\n" + 
					"GROUP BY TAIKHOAN"+
					"\r\n" + 
					")CUOIKY";
		} else if (loai == 4) {
			query += "--LAY DAU KY\r\n" + 
					"		SELECT '' AS PK_SEQ, '' AS NGAYCHUNGTU, 0 AS SOCHUNGTU, '' AS NOIDUNG, \r\n" + 
					"		SOHIEUTAIKHOAN AS TAIKHOAN, '' AS DOIUNG, GIATRINOVND AS NO, GIATRICOVND AS CO, DOITUONG, 0 AS SOHOADON, MADOITUONG\r\n" + 
					"		FROM ERP_TAIKHOAN_NOCO_KHOASO KS\r\n" + 
					"		LEFT JOIN ERP_TAIKHOANKT TK ON KS.TAIKHOANKT_FK = TK.PK_SEQ\r\n" + 
					"		WHERE TAIKHOANKT_FK IN ("+temp+") AND THANG = "+thangDK+" AND NAM = "+namDK+" \n";
			query += " \r\n" + 
					" UNION ALL \n";
			query += "		SELECT '' AS PK_SEQ, '' AS NGAYCHUNGTU, 0 AS SOCHUNGTU, '' AS NOIDUNG, \r\n" + 
					"		SOHIEUTAIKHOAN AS TAIKHOAN, '' AS DOIUNG, NO AS NO, CO AS CO, DOITUONG, 0 AS SOHOADON, MADOITUONG\r\n" + 
					"		FROM ERP_PHATSINHKETOAN PS\r\n" + 
					"		LEFT JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ\r\n" + 
					"		WHERE TAIKHOAN_FK IN ("+temp+") AND NGAYGHINHAN >='"+year+"-"+month+"-01' AND NGAYGHINHAN < '"+ this.tungay +"' \n " ;
			query += " \r\n" + 
					" UNION ALL \n";
			query += "		SELECT '' AS PK_SEQ, '' AS NGAYCHUNGTU, 0 AS SOCHUNGTU, '' AS NOIDUNG, \r\n" + 
					"		SOHIEUTAIKHOAN AS TAIKHOAN, '' AS DOIUNG, NO AS NO, CO AS CO, DOITUONG, 0 AS SOHOADON, MADOITUONG\r\n" + 
					"		FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PS\r\n" + 
					"		LEFT JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ\r\n" + 
					"		WHERE TAIKHOAN_FK IN ("+temp+") AND NGAYGHINHAN >='"+year+"-"+month+"-01' AND NGAYGHINHAN < '"+ this.tungay +"' \n " ;
		}
		return query;
	}
	
	@Override
	public void createRs(){
		
		String query = 	"SELECT distinct LEFT(SOHIEUTAIKHOAN,3) SOHIEUTAIKHOAN " +
				"FROM ERP_TAIKHOANKT WHERE NPP_FK = "+this.chiNhanh+" ORDER BY SOHIEUTAIKHOAN";
		
		this.taikhoanNhom = this.db.get(query);
		
		query = "SELECT PK_SEQ AS TKID, SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN AS TAIKHOAN " +
		  		"FROM ERP_TAIKHOANKT WHERE NPP_FK = " + this.chiNhanh ;
		
		if(tknhomId.trim().length() > 0)
		{
			query += " AND SOHIEUTAIKHOAN LIKE N'"+tknhomId+"%'";
		}
		
		this.tk = this.db.get(query);

		query = "SELECT PK_SEQ, TEN\r\n" + 
				"FROM NHAPHANPHOI \r\n" + 
				"WHERE ISKHACHHANG = '0'\r\n" + 
				"  AND LOAINPP IN (0, 1, 2, 3)";
		System.out.println(query);
		this.chiNhanhRs = this.db.get(query);
		
	}
	public void init(){
		String query = 	"";
		ResultSet rs = this.db.get("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
		
		
		try{
			if(rs != null) {
				rs.next();
				this.ctyTen = rs.getString("TEN");
				this.diachi = rs.getString("DIACHI");
				System.out.println(this.diachi);
				
				this.masothue = rs.getString("MASOTHUE");
				System.out.println(this.masothue);
				
				rs.close();
			}
			
		}catch(java.sql.SQLException e){}		
		
		if (!this.chiNhanh.equals("1")) {
			rs = this.db.get("SELECT TEN, DIACHI, MASOTHUE FROM NHAPHANPHOI WHERE PK_SEQ = " + this.chiNhanh);
			
			
			try{
				if(rs != null) {
					rs.next();
					this.chiNhanhTen = rs.getString("TEN");
					this.chiNhanhDiaChi = rs.getString("DIACHI");
					System.out.println(this.diachi);
					
					this.chiNhanhMaSoThue = rs.getString("MASOTHUE");
					System.out.println(this.masothue);
					
					rs.close();
				}
				
			}catch(java.sql.SQLException e){}
		}
		//TINH DAU KY
		query = this.getQuery(0);
		System.out.println("Query DK: "+query);
		rs = this.db.get(query);
		try{
			if(rs.next()){
				this.daukyco = rs.getString("CO");
				this.daukyno = rs.getString("NO");	
			}else{
				this.daukyco = "0";
				this.daukyno = "0";								
			}
			rs.close();
		}catch(java.sql.SQLException e){}
		this.rs = this.db.get(query);
		
		//TINH PHAT SINH
		query = this.getQuery(1);
		this.rs = this.db.get(query);
		System.out.println("Query TK: "+query);
		
		//TINH Cuoi KY
		query = this.getQuery(2);
		System.out.println("Query CK: "+query);
		rs = this.db.get(query);
		try{
			if(rs.next()){
				this.cuoiKyCo = rs.getDouble("CO");
				this.cuoiKyNo = rs.getDouble("NO");	
			}else{
				this.cuoiKyCo = 0.0;
				this.cuoiKyNo = 0.0;								
			}
			rs.close();
		}catch(java.sql.SQLException e){}
		
	}
	/*public void init(){
		//init_0();
		
		// this.ctyId chứa Công ty ID của công ty được login
		// this.ErpcongtyId chứa công ty ID của những công ty được chọn để ra báo cáo, cách nhau bởi dấu ";"
		
		String query = 	"";
		ResultSet rs = this.db.get("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
		
		
		try{
			if(rs != null) {
				rs.next();
				this.ctyTen = rs.getString("TEN");
				this.diachi = rs.getString("DIACHI");
				System.out.println(this.diachi);
				
				this.masothue = rs.getString("MASOTHUE");
				System.out.println(this.masothue);
				
				rs.close();
			}
			
		}catch(java.sql.SQLException e){}		
		
		if (!this.chiNhanh.equals("1")) {
			rs = this.db.get("SELECT TEN, DIACHI, MASOTHUE FROM NHAPHANPHOI WHERE PK_SEQ = " + this.chiNhanh);
			
			
			try{
				if(rs != null) {
					rs.next();
					this.chiNhanhTen = rs.getString("TEN");
					this.chiNhanhDiaChi = rs.getString("DIACHI");
					System.out.println(this.diachi);
					
					this.chiNhanhMaSoThue = rs.getString("MASOTHUE");
					System.out.println(this.masothue);
					
					rs.close();
				}
				
			}catch(java.sql.SQLException e){}
		}
		
		if(this.tkId.length() > 0){
			query = 	"SELECT SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN AS TAIKHOAN " +
						"FROM ERP_TAIKHOANKT WHERE NPP_FK = "+this.chiNhanh+" AND CONGTY_FK = " + this.ctyId + " AND PK_SEQ = " + this.tkId + "";
			System.out.println(query);
			rs = this.db.get(query);
			try{
				rs.next();
				this.sohieu = rs.getString("TAIKHOAN");
				rs.close();
			}catch(java.sql.SQLException e){}
			
		}
		
		query = "SELECT PK_SEQ, TEN\r\n" + 
				"FROM NHAPHANPHOI \r\n" + 
				"WHERE ISKHACHHANG = '0'\r\n" + 
				"  AND LOAINPP IN (0, 1, 2, 3)";
		System.out.println(query);
		this.chiNhanhRs = this.db.get(query);
			
		
		if(this.year.length() > 0 & this.month.length() > 0){
			int lastyear = Integer.parseInt(this.year) - 1;
			int lastmonth = 0;
			
			if (Integer.parseInt(this.month) > 1){
				lastmonth = Integer.parseInt(this.month) - 1;
			}else{
				lastmonth = 12;
			}
			
			query = 
				"SELECT ISNULL(SUM(DAUKY.GIATRINOVND),0) GIATRINOVND , ISNULL(SUM(DAUKY.GIATRICOVND),0) GIATRICOVND\r\n" + 
				"FROM\r\n" + 
				"("	+
				"\n SELECT ISNULL(SUM(DATA.CO),0) GIATRINOVND , ISNULL(SUM(DATA.NO),0) GIATRICOVND " +
				"\n FROM ( " +
				
				"\n SELECT  CASE WHEN PHATSINH.LOAICHUNGTU like N'Thu tiền%' THEN (select PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) from ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU)" +
				"\n 		WHEN  PHATSINH.LOAICHUNGTU like N'Thanh toán hóa đơn%' THEN (select PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) from ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU)" +
				"\n 		ELSE CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) END AS PK_SEQ," +
				"\n			 CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select NGAYXUATHD from ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select NGAYXUATHD from erp_hoadonnpp where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select NGAYHOADON from ERP_HoaDonPheLieu where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select NGAYXUATHD from ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select NGAYHOADON from ERP_HOADONNCC where PARK_FK =PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Giảm/Tăng giá hàng bán' THEN (select NGAYHOADON from erp_giamgiahangban where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Thu tiền%' THEN (select NGAYCHUNGTU from ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n       ELSE PHATSINH.NGAYCHUNGTU END  AS NGAYCHUNGTU,  "+
				"\n    ISNULL(PHATSINH.SOCHUNGTU, '0') AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN_2.SOHIEUTAIKHOAN AS TAIKHOAN, " +
				"\n   TAIKHOAN.SOHIEUTAIKHOAN AS DOIUNG, ISNULL(PHATSINH.NO, 0) AS NO, ISNULL(PHATSINH.CO, 0) AS CO,  " +
				"\n 	case when  DOITUONG = N'Nhà cung cấp' then ( select MA from ERP_NHACUNGCAP where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) " +
				"\n	when  DOITUONG = N'Khách hàng' then ( select MA from KHACHHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) " +
				"\n	when  DOITUONG = N'Nhân viên' then ( select MA from ERP_NHANVIEN where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n	when  DOITUONG = N'Ngân hàng' then ( select MA from ERP_NGANHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +					
				"\n	when  DOITUONG = N'Sản phẩm' then ( select MA from SANPHAM where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n	when  DOITUONG = N'Chi phí' then ( select MA from ERP_NHOMCHIPHI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n	when  DOITUONG = N'Tài sản' then ( select MA from ERP_TAISANCODINH where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n	when  DOITUONG = N'Công cụ dụng cụ' then ( select MA from ERP_CONGCUDUNGCU where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n	when  DOITUONG = N'Trung tâm chi phí' then ( select MA from ERP_TRUNGTAMCHIPHI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n else ( case when LEN(DOITUONG) <= 0 then KHOANMUC else DOITUONG end ) end as DOITUONG,  " +
				"\n    CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select isnull(SOHOADON,'') from erp_hoadonnpp where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
				"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select isnull(SOHOADON,'') from erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
				"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select isnull(SOHOADON,'') from erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
				"\n         WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select isnull(SOHOADON,'') from erp_hoadonncc where park_fk =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
				"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select isnull(SOHOADON,'') from ERP_HoaDonPheLieu where pk_seq =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
				"\n  	  WHEN PHATSINH.LOAICHUNGTU like N'Giảm/Tăng giá hàng bán' THEN  (select sohoadon from erp_giamgiahangban where PK_SEQ=PHATSINH.SOCHUNGTU)	"+
				"\n   	  WHEN PHATSINH.LOAICHUNGTU like N'Nhận hàng' THEN  (select isnull(c.SOHOADON,'') from ERP_NHANHANG a inner join ERP_HOADONNCC c on a.hdNCC_fk = c.pk_seq where a.PK_SEQ=PHATSINH.SOCHUNGTU)		"+ 
				"\n    ELSE '' END AS SOHOADON, ISNULL(PHATSINH.DIENGIAI,'') DIENGIAI, ISNULL(PHATSINH.SOLUONG, 0) SOLUONG, " +
				"\n ISNULL(PHATSINH.MAHANG, '') MAHANG, ISNULL(PHATSINH.TENHANG, '') TENHANG, ISNULL(DONVI, '') DONVI, ISNULL(PHATSINH.VAT, 0) VAT  " +
				"\n FROM ERP_PHATSINHKETOAN PHATSINH " +
				
				"\n INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOAN_FK = TAIKHOAN.pk_seq  AND TAIKHOAN.NPP_FK = "+this.chiNhanh+"" +
				"\n INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN_2.pk_seq  AND TAIKHOAN_2.NPP_FK = "+this.chiNhanh+"" +
				"\n LEFT JOIN ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK " +
				"\n WHERE 1 = 1 ";
			
			if(this.tungay.trim().length() >0 )
				query += " AND PHATSINH.NGAYGHINHAN < '"+this.tungay +"'";
					
			if(this.tkId.length() > 0){
				query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN ( "+tkId+" ) ";
			}
			
			if(tknhomId.trim().length() > 0)
			{
				query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN " +
								"\n	( SELECT PK_SEQ FROM ERP_TAIKHOANKT " +
								"\n	  WHERE SOHIEUTAIKHOAN LIKE N'"+tknhomId+"%' AND NPP_FK = "+this.chiNhanh+"" + //this.ctyId chứa id công ty đăng nhập
								"\n ) " ;
			}
			
			query += ")DATA ";
			// BEGIN TICH HOP DMS
			query += "\n UNION ALL"+
					"\n SELECT ISNULL(SUM(DATA.CO),0) GIATRINOVND , ISNULL(SUM(DATA.NO),0) GIATRICOVND " +
					"\n FROM ( " +
					
					"\n SELECT  CASE WHEN PHATSINH.LOAICHUNGTU like N'Thu tiền%' THEN (select PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) from " + Utility.prefixDMS + "ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU)" +
					"\n 		WHEN  PHATSINH.LOAICHUNGTU like N'Thanh toán hóa đơn%' THEN (select PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) from " + Utility.prefixDMS + "ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU)" +
					"\n 		ELSE CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) END AS PK_SEQ," +
					"\n			 CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select NGAYXUATHD from " + Utility.prefixDMS + "ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) " +
					"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select NGAYXUATHD from " + Utility.prefixDMS + "erp_hoadonnpp where PK_SEQ=PHATSINH.SOCHUNGTU) " +
					"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select NGAYHOADON from " + Utility.prefixDMS + "ERP_HoaDonPheLieu where PK_SEQ=PHATSINH.SOCHUNGTU) " +
					"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select NGAYXUATHD from " + Utility.prefixDMS + "ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) " +
					"\n            WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select NGAYHOADON from " + Utility.prefixDMS + "ERP_HOADONNCC where PARK_FK =PHATSINH.SOCHUNGTU) " +
					"\n            WHEN PHATSINH.LOAICHUNGTU like N'Thu tiền%' THEN (select NGAYCHUNGTU from " + Utility.prefixDMS + "ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU) " +
					"\n       ELSE PHATSINH.NGAYCHUNGTU END  AS NGAYCHUNGTU,  "+
					"\n    ISNULL(PHATSINH.SOCHUNGTU, '0') AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN_2.SOHIEUTAIKHOAN AS TAIKHOAN, " +
					"\n   TAIKHOAN.SOHIEUTAIKHOAN AS DOIUNG, ISNULL(PHATSINH.NO, 0) AS NO, ISNULL(PHATSINH.CO, 0) AS CO,  " +
					"\n 	case when  DOITUONG = N'Nhà cung cấp' then ( select MA from " + Utility.prefixDMS + "ERP_NHACUNGCAP where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) " +
					"\n	when  DOITUONG = N'Khách hàng' then ( select MA from " + Utility.prefixDMS + "KHACHHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) " +
					"\n	when  DOITUONG = N'Nhân viên' then ( select MA from " + Utility.prefixDMS + "ERP_NHANVIEN where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
					"\n	when  DOITUONG = N'Ngân hàng' then ( select MA from " + Utility.prefixDMS + "ERP_NGANHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +					
					"\n	when  DOITUONG = N'Sản phẩm' then ( select MA from " + Utility.prefixDMS + "SANPHAM where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
					"\n else ( case when LEN(DOITUONG) <= 0 then KHOANMUC else DOITUONG end ) end as DOITUONG,  " +
					"\n    CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select isnull(SOHOADON,'') from " + Utility.prefixDMS + "erp_hoadonnpp where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select isnull(SOHOADON,'') from " + Utility.prefixDMS + "erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select isnull(SOHOADON,'') from " + Utility.prefixDMS + "erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					"\n         WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select isnull(SOHOADON,'') from " + Utility.prefixDMS + "erp_hoadonncc where park_fk =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select isnull(SOHOADON,'') from " + Utility.prefixDMS + "ERP_HoaDonPheLieu where pk_seq =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					"\n    ELSE '' END AS SOHOADON, ISNULL(PHATSINH.DIENGIAI,'') DIENGIAI, ISNULL(PHATSINH.SOLUONG, 0) SOLUONG, " +
					"\n ISNULL(PHATSINH.MAHANG, '') MAHANG, ISNULL(PHATSINH.TENHANG, '') TENHANG, ISNULL(DONVI, '') DONVI, ISNULL(PHATSINH.VAT, 0) VAT  " +
					"\n FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PHATSINH " +
					
					"\n INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOAN_FK = TAIKHOAN.pk_seq  AND TAIKHOAN.NPP_FK = "+this.chiNhanh+"" +
					"\n INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN_2.pk_seq  AND TAIKHOAN_2.NPP_FK = "+this.chiNhanh+"" +
					"\n LEFT JOIN " + Utility.prefixDMS + "ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK " +
					"\n WHERE 1 = 1 ";
					
					if(this.tungay.trim().length() >0 )
						query += " AND PHATSINH.NGAYGHINHAN < '"+this.tungay +"'";
							
					if(this.tkId.length() > 0){
						query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN ( "+tkId+" ) ";
					}
					
					if(tknhomId.trim().length() > 0)
					{
						query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN " +
										"\n	( SELECT PK_SEQ FROM ERP_TAIKHOANKT " +
										"\n	  WHERE SOHIEUTAIKHOAN LIKE N'"+tknhomId+"%' AND NPP_FK = "+this.chiNhanh+"" + //this.ctyId chứa id công ty đăng nhập
										"\n ) " ;
					}
					
					query += ")DATA ";
					//END TICH HOP DMS
					query += ")DAUKY ";
			
			query = this.getQuery(0);
			System.out.println("Query DK: "+query);
			rs = this.db.get(query);
			try{
				
				if(rs.next()){
					this.daukyco = rs.getString("CO");
					this.daukyno = rs.getString("NO");	
				}else{
					this.daukyco = "0";
					this.daukyno = "0";								
				}
				rs.close();
			}catch(java.sql.SQLException e){}
			
			if(this.tungay.trim().length() >0 || this.denngay.trim().length() >0)
			{
				query = 
				"\n SELECT * FROM ( " +
				
				"\n SELECT   CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) AS PK_SEQ," +
				"\n			 CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select NGAYXUATHD from ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select NGAYXUATHD from erp_hoadonnpp where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select NGAYHOADON from ERP_HoaDonPheLieu where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select NGAYXUATHD from ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select NGAYHOADON from ERP_HOADONNCC where PARK_FK =PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Giảm/Tăng giá hàng bán' THEN (select NGAYHOADON from erp_giamgiahangban where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n            WHEN PHATSINH.LOAICHUNGTU like N'Thu tiền%' THEN (select NGAYCHUNGTU from ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU) " +
				"\n       ELSE PHATSINH.NGAYCHUNGTU END  AS NGAYCHUNGTU,  "+
				"\n    ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN_2.SOHIEUTAIKHOAN AS TAIKHOAN, " +
				"\n   TAIKHOAN.SOHIEUTAIKHOAN AS DOIUNG, ISNULL(PHATSINH.CO, 0) AS NO, ISNULL(PHATSINH.NO, 0) AS CO,  " +
				"\n 	case when  DOITUONG = N'Nhà cung cấp' then ( select MA from ERP_NHACUNGCAP where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) " +
				"\n	when  DOITUONG = N'Khách hàng' then ( select MA from KHACHHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) " +
				"\n	when  DOITUONG = N'Nhân viên' then ( select MA from ERP_NHANVIEN where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n	when  DOITUONG = N'Ngân hàng' then ( select MA from ERP_NGANHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +					
				"\n	when  DOITUONG = N'Sản phẩm' then ( select MA from SANPHAM where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n	when  DOITUONG = N'Chi phí' then ( select MA from ERP_NHOMCHIPHI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n	when  DOITUONG = N'Tài sản' then ( select MA from ERP_TAISANCODINH where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n	when  DOITUONG = N'Công cụ dụng cụ' then ( select MA from ERP_CONGCUDUNGCU where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n	when  DOITUONG = N'Trung tâm chi phí' then ( select MA from ERP_TRUNGTAMCHIPHI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
				"\n else ( case when LEN(DOITUONG) <= 0 then KHOANMUC else DOITUONG end ) end as DOITUONG,  " +
				"\n    CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select isnull(SOHOADON,'') from erp_hoadonnpp where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
				"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select isnull(SOHOADON,'') from erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
				"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select isnull(SOHOADON,'') from erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
				"\n         WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select isnull(SOHOADON,'') from erp_hoadonncc where park_fk =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
				"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select isnull(SOHOADON,'') from ERP_HoaDonPheLieu where pk_seq =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
				"\n  	  WHEN PHATSINH.LOAICHUNGTU like N'Giảm/Tăng giá hàng bán' THEN  (select sohoadon from erp_giamgiahangban where PK_SEQ=PHATSINH.SOCHUNGTU)	"+
				"\n   	  WHEN PHATSINH.LOAICHUNGTU like N'Nhận hàng' THEN  (select isnull(c.SOHOADON,'') from ERP_NHANHANG a inner join ERP_HOADONNCC c on a.hdNCC_fk = c.pk_seq where a.PK_SEQ=PHATSINH.SOCHUNGTU)		"+
				"\n   	  WHEN PHATSINH.LOAICHUNGTU like N'Trả khác' THEN  ISNULL(PHATSINH.SOHOADON,'')	"+ 
				"\n    	  ELSE '' END AS SOHOADON, ISNULL(PHATSINH.DIENGIAI,'') DIENGIAI , ISNULL(PHATSINH.SOLUONG, 0) SOLUONG, " +
				"\n ISNULL(PHATSINH.MAHANG, '') MAHANG, ISNULL(PHATSINH.TENHANG, '') TENHANG, ISNULL(DONVI, '') DONVI, ISNULL(PHATSINH.VAT, 0) VAT ,  " +
				"\n ISNULL(PHATSINH.TIENHANG, 0) TIENHANG, ISNULL(PHATSINH.MAHOADON, '') MAHOADON, ISNULL(PHATSINH.MAUHOADON,'') MAUHOADON, " +
				"\n ISNULL(PHATSINH.KYHIEU, '') KYHIEU, ISNULL(PHATSINH.NGAYHOADON, '') NGAYHOADON, ISNULL(PHATSINH.TENNCC, '') TENNCC," +
				"\n ISNULL(PHATSINH.MST, '') MST, ISNULL(PHATSINH.TEN_SANPHAM, '') TEN_SANPHAM, ISNULL(PHATSINH.TEN_BENHVIEN,'') TEN_BENHVIEN," +
				"\n ISNULL(PHATSINH.TEN_TINHTHANH, '') TEN_TINHTHANH, ISNULL(PHATSINH.TEN_DIABAN, '' ) TEN_DIABAN, ISNULL(PHATSINH.TEN_VV, '') TEN_VV," +
				"\n ISNULL(PHATSINH.TEN_PB, '') TEN_PB, ISNULL(PHATSINH.TEN_KBH, '') TEN_KBH, ISNULL(PHATSINH.TEN_DT, '') TEN_DT, " +
				"\n ISNULL(PHATSINH.THUESUAT, 0) THUESUAT, ISNULL(PHATSINH.DIENGIAI_CT, '') DIENGIAI_CT "+
				"\n FROM ERP_PHATSINHKETOAN PHATSINH " +

				"\n INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOAN_FK = TAIKHOAN.pk_seq  AND TAIKHOAN.NPP_FK = "+this.chiNhanh+"" +
				"\n INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN_2.pk_seq  AND TAIKHOAN_2.NPP_FK = "+this.chiNhanh+"" +
				"\n LEFT JOIN ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK " +
				"\n WHERE 1 = 1 ";
				
				if(this.tungay.trim().length() >0 )
					query += " AND PHATSINH.NGAYGHINHAN >= '"+this.tungay +"'";
				
				if(this.denngay.trim().length() >0 )
					query += " AND PHATSINH.NGAYGHINHAN <= '"+this.denngay +"'";
			
				if(this.tkId.length() > 0){
					query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN ( "+tkId+" ) ";
				}
				
				if(tknhomId.trim().length() > 0)
				{
					query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN " +
									"\n	( SELECT PK_SEQ FROM ERP_TAIKHOANKT " +
									"\n	  WHERE SOHIEUTAIKHOAN LIKE N'"+tknhomId+"%'  AND NPP_FK = "+this.chiNhanh+"" + //this.ctyId chứa id công ty đăng nhập
									"\n ) " ;
				}
				
				query += ")DATA " +
				 	" ORDER BY DATA.PK_SEQ desc  " ;
				// BEGIN TICH HOP DMS
				query += "\n UNION ALL " +
						"\n SELECT * FROM ( " +
								
						"\n SELECT   CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) AS PK_SEQ," +
						"\n			 CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select NGAYXUATHD from " + Utility.prefixDMS + "ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) " +
						"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select NGAYXUATHD from " + Utility.prefixDMS + "erp_hoadonnpp where PK_SEQ=PHATSINH.SOCHUNGTU) " +
						"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select NGAYHOADON from " + Utility.prefixDMS + "ERP_HoaDonPheLieu where PK_SEQ=PHATSINH.SOCHUNGTU) " +
						"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select NGAYXUATHD from " + Utility.prefixDMS + "ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) " +
						"\n            WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select NGAYHOADON from " + Utility.prefixDMS + "ERP_HOADONNCC where PARK_FK =PHATSINH.SOCHUNGTU) " +
						"\n            WHEN PHATSINH.LOAICHUNGTU like N'Thu tiền%' THEN (select NGAYCHUNGTU from " + Utility.prefixDMS + "ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU) " +
						"\n       ELSE PHATSINH.NGAYCHUNGTU END  AS NGAYCHUNGTU,  "+
						"\n    ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN_2.SOHIEUTAIKHOAN AS TAIKHOAN, " +
						"\n   TAIKHOAN.SOHIEUTAIKHOAN AS DOIUNG, ISNULL(PHATSINH.CO, 0) AS NO, ISNULL(PHATSINH.NO, 0) AS CO,  " +
						"\n 	case when  DOITUONG = N'Nhà cung cấp' then ( select MA from " + Utility.prefixDMS + "ERP_NHACUNGCAP where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) " +
						"\n	when  DOITUONG = N'Khách hàng' then ( select MA from " + Utility.prefixDMS + "KHACHHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) " +
						"\n	when  DOITUONG = N'Nhân viên' then ( select MA from " + Utility.prefixDMS + "ERP_NHANVIEN where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
						"\n	when  DOITUONG = N'Ngân hàng' then ( select MA from " + Utility.prefixDMS + "ERP_NGANHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +					
						"\n	when  DOITUONG = N'Sản phẩm' then ( select MA from " + Utility.prefixDMS + "SANPHAM where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
						"\n else ( case when LEN(DOITUONG) <= 0 then KHOANMUC else DOITUONG end ) end as DOITUONG,  " +
						"\n    CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select isnull(SOHOADON,'') from " + Utility.prefixDMS + "erp_hoadonnpp where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
						"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select isnull(SOHOADON,'') from " + Utility.prefixDMS + "erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
						"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select isnull(SOHOADON,'') from " + Utility.prefixDMS + "erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
						"\n         WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select isnull(SOHOADON,'') from " + Utility.prefixDMS + "erp_hoadonncc where park_fk =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
						"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select isnull(SOHOADON,'') from " + Utility.prefixDMS + "ERP_HoaDonPheLieu where pk_seq =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
						"\n   	  WHEN PHATSINH.LOAICHUNGTU like N'Trả khác' THEN  ISNULL(PHATSINH.SOHOADON,'')	"+ 
						"\n    	  ELSE '' END AS SOHOADON, ISNULL(PHATSINH.DIENGIAI,'') DIENGIAI , ISNULL(PHATSINH.SOLUONG, 0) SOLUONG, " +
						"\n ISNULL(PHATSINH.MAHANG, '') MAHANG, ISNULL(PHATSINH.TENHANG, '') TENHANG, ISNULL(DONVI, '') DONVI, ISNULL(PHATSINH.VAT, 0) VAT ,  " +
						"\n ISNULL(PHATSINH.TIENHANG, 0) TIENHANG, ISNULL(PHATSINH.MAHOADON, '') MAHOADON, ISNULL(PHATSINH.MAUHOADON,'') MAUHOADON, " +
						"\n ISNULL(PHATSINH.KYHIEU, '') KYHIEU, ISNULL(PHATSINH.NGAYHOADON, '') NGAYHOADON, ISNULL(PHATSINH.TENNCC, '') TENNCC," +
						"\n ISNULL(PHATSINH.MST, '') MST, ISNULL(PHATSINH.TEN_SANPHAM, '') TEN_SANPHAM, ISNULL(PHATSINH.TEN_BENHVIEN,'') TEN_BENHVIEN," +
						"\n ISNULL(PHATSINH.TEN_TINHTHANH, '') TEN_TINHTHANH, ISNULL(PHATSINH.TEN_DIABAN, '' ) TEN_DIABAN, ISNULL(PHATSINH.TEN_VV, '') TEN_VV," +
						"\n ISNULL(PHATSINH.TEN_PB, '') TEN_PB, ISNULL(PHATSINH.TEN_KBH, '') TEN_KBH, ISNULL(PHATSINH.TEN_DT, '') TEN_DT, " +
						"\n ISNULL(PHATSINH.THUESUAT, 0) THUESUAT, ISNULL(PHATSINH.DIENGIAI_CT, '') DIENGIAI_CT "+
						"\n FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PHATSINH " +

						"\n INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOAN_FK = TAIKHOAN.pk_seq  AND TAIKHOAN.NPP_FK = "+this.chiNhanh+"" +
						"\n INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN_2.pk_seq  AND TAIKHOAN_2.NPP_FK = "+this.chiNhanh+"" +
						"\n LEFT JOIN " + Utility.prefixDMS + "ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK " +
						"\n WHERE 1 = 1 ";
						
						if(this.tungay.trim().length() >0 )
							query += " AND PHATSINH.NGAYGHINHAN >= '"+this.tungay +"'";
						
						if(this.denngay.trim().length() >0 )
							query += " AND PHATSINH.NGAYGHINHAN <= '"+this.denngay +"'";
					
						if(this.tkId.length() > 0){
							query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN ( "+tkId+" ) ";
						}
						
						if(tknhomId.trim().length() > 0)
						{
							query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN " +
											"\n	( SELECT PK_SEQ FROM ERP_TAIKHOANKT " +
											"\n	  WHERE SOHIEUTAIKHOAN LIKE N'"+tknhomId+"%'  AND NPP_FK = "+this.chiNhanh+" AND CONGTY_FK = '" + this.ctyId + "' " + //this.ctyId chứa id công ty đăng nhập
											"\n ) " ;
						}
						
						query += ")DATA " +
						 	" ORDER BY DATA.PK_SEQ desc  " ;
				// END TICH HOP DMS		
				
			}
			
			this.rs = this.db.get(query);
			System.out.println("Query TK: "+query);
		}
	}*/
	
	

	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void DBClose(){
		if(db != null) db.shutDown();
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public ResultSet getTaikhoanNhom() {
		
		return this.taikhoanNhom;
	}

	
	public void setTkNhomId(String tknhomId) {
		
		this.tknhomId =tknhomId;
	}

	
	public String getTkNhomId() {
		
		return this.tknhomId;
	}
	@Override
	public ResultSet getChiNhanhRs() {
		return chiNhanhRs;
	}

	@Override
	public void setChiNhanhRs(ResultSet chiNhanhRs) {
		this.chiNhanhRs = chiNhanhRs;
	}

	@Override
	public String getChiNhanh() {
		return chiNhanh;
	}

	@Override
	public void setChiNhanh(String chiNhanh) {
		this.chiNhanh = chiNhanh;
	}

	@Override
	public String getChiNhanhTen() {
		return chiNhanhTen;
	}

	@Override
	public void setChiNhanhTen(String chiNhanhTen) {
		this.chiNhanhTen = chiNhanhTen;
	}

	@Override
	public String getChiNhanhMaSoThue() {
		return chiNhanhMaSoThue;
	}

	@Override
	public void setChiNhanhMaSoThue(String chiNhanhMaSoThue) {
		this.chiNhanhMaSoThue = chiNhanhMaSoThue;
	}

	@Override
	public String getChiNhanhDiaChi() {
		return chiNhanhDiaChi;
	}

	@Override
	public void setChiNhanhDiaChi(String chiNhanhDiaChi) {
		this.chiNhanhDiaChi = chiNhanhDiaChi;
	}

	@Override
	public double getCuoiKyCo() {
		return cuoiKyCo;
	}

	@Override
	public void setCuoiKyCo(double cuoiKyCo) {
		this.cuoiKyCo = cuoiKyCo;
	}

	@Override
	public double getCuoiKyNo() {
		return cuoiKyNo;
	}

	@Override
	public void setCuoiKyNo(double cuoiKyNo) {
		this.cuoiKyNo = cuoiKyNo;
	}
	
	
	
}
