package geso.traphaco.erp.beans.soquytienmat.imp;
import geso.traphaco.erp.beans.soquytienmat.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;

public class Soquytienmat implements ISoquytienmat {
	String userId;
	String ctyId;	
	String ctyTen;
	String tungay;
	String denngay;
	String diachi;
	String masothue;	
	String sodudau;
	String chiNhanh;
	String chiNhanhTen;
	String chiNhanhMaSoThue;
	String chiNhanhDiaChi;
	
	ResultSet rs;
	ResultSet dauky;
	
	String msg;
	dbutils db;
	
	ResultSet congtyRs;
	String[] ctyIds;
	
	ResultSet chiNhanhRs;
	
	String ErpCongTyId;
	String view;
	
	public Soquytienmat() {
		this.userId = "";
		this.ctyId = "";
		this.ctyTen = "";
		this.sodudau = "0";
		this.denngay = "";		
		this.tungay = "";

		this.msg = "";
		this.db = new dbutils();
		this.chiNhanh = "";
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

	public void setMsg(String msg) {

		this.msg = msg;
	}

	public String getMsg() {

		return this.msg;
	}

	public String getSodudau() {

		return this.sodudau;
	}


	public ResultSet getData(){
		return this.rs;
	}
	
	public ResultSet getDauky(){
		return this.dauky;
	}

	public void init_0(){
		/*if(this.ctyIds != null){
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
		this.congtyRs = db.get(sql);*/	
		
		String query = "SELECT PK_SEQ, TEN \n" + 
				"FROM NHAPHANPHOI  \n" + 
				"WHERE ISKHACHHANG = '0' \n" + 
				"  AND LOAINPP IN (0, 1, 2, 3)";
		System.out.println(query);
		this.chiNhanhRs = this.db.get(query);
	}


	public void init(){
		init_0();
		String query;
		
		// TẠM THỜI BỎ RÀNG CÔNG TY. KHI NÀO ĐỒNG BỘ THÌ CHECK TIẾP
		
		ResultSet rs;
		try{
			query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ this.tungay + "'), 0)) AS NGAY";
			rs = this.db.get(query);			
			rs.next();
			Utility util = new Utility();
			String ngayDuaSoDu=util.getNgayKhoaSo(db, this.tungay);
			String  thang = "" + (Integer.parseInt(rs.getString("NGAY").substring(5, 7)));
			String nam = "" + (Integer.parseInt(rs.getString("NGAY").substring(0, 4)));
			rs.close();
			/*query = 	"SELECT SUM(DAUKY) AS DAUKY  \n" +
						"FROM (  \n" +
						"	SELECT SUM(GIATRINOVND-GIATRICOVND) AS DAUKY \n" +
						"	FROM ERP_TAIKHOAN_NOCO \n" +
						"	WHERE THANG = '" + thang + "' AND NAM = '" + nam + "' AND \n" +
						"	TAIKHOANKT_FK IN \n" +
											
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' ) \n" +
				
						//"   AND TAIKHOANKT_FK IN \n" +
						//"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN (" + this.ctyId + ")  ) \n" +

						
						"	UNION \n" +
				
						"	SELECT ISNULL(SUM(TONGGIATRI), 0) AS DAUKY \n" + 
						"	FROM ERP_PHATSINHKETOAN PSTK  \n" +
						"	WHERE TAIKHOAN_FK IN \n" +
						"	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' ) \n" +  
						
						//"   AND TAIKHOAN_FK IN \n" +
						//"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN (" + this.ctyId + ")  ) \n" +

						"	AND PSTK.NGAYGHINHAN >= '" + this.tungay.substring(0, 8) + "01' AND PSTK.NGAYGHINHAN < '" + this.tungay + "' \n" +
						"	AND PSTK.LOAICHUNGTU LIKE N'Thu tiền%' \n" +  
				
						"	UNION  \n" +
				
						"	SELECT (-1)*ISNULL(SUM(TONGGIATRI), 0) AS DAUKY \n" +
						"	FROM ERP_PHATSINHKETOAN PSTK  \n" +
						"	WHERE TAIKHOAN_FK IN \n" +
						"	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' ) \n" +  
						
						//"   AND TAIKHOAN_FK IN \n" +
						//"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN (" + this.ctyId + ")  ) \n" +
						
						"	AND PSTK.NGAYGHINHAN >=  '" + this.tungay.substring(0, 8) + "01' AND PSTK.NGAYGHINHAN < '" + this.tungay + "'  \n" +
						"	AND PSTK.LOAICHUNGTU LIKE N'Thanh toán%' \n" +  
						
						"	UNION  \n" +

						"	SELECT (-1)*ISNULL(SUM(TONGGIATRI), 0) AS DAUKY \n" +
						"	FROM ERP_PHATSINHKETOAN PSTK  \n" +
						"	WHERE TAIKHOAN_FK IN \n" +
						"	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' ) \n" +  
						
						//"   AND TAIKHOAN_FK IN \n" +
						//"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN (" + this.ctyId + ")  ) \n" +
						
						"	AND PSTK.NGAYGHINHAN >=  '" + this.tungay.substring(0, 8) + "01' AND PSTK.NGAYGHINHAN < '" + this.tungay + "'  \n" +
						"	AND PSTK.LOAICHUNGTU LIKE N'Trả khác%' \n" +  
						
						")DAUKY \n";*/
			if (!this.chiNhanh.equals("")) {
			query = 
					" \n" + 
					"SELECT GIATRINOVND AS DAUKY \n" + 
					"FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" + 
					"WHERE TAIKHOANKT_FK =  \n" + 
					"	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN (" + this.chiNhanh + ") )  \n" + 
					"	AND THANG = "+thang+" AND NAM = "+nam+"";
			
			query = "SELECT SUM( ISNULL(DATA.DAUKY,0)) AS DAUKY FROM ( \r\n" + 
					"SELECT GIATRINOVND - GIATRICOVND AS DAUKY \r\n" + 
					"FROM ERP_TAIKHOAN_NOCO_KHOASO KS\r\n" + 
					"WHERE TAIKHOANKT_FK =	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN (" + this.chiNhanh + ") )  \r\n" + 
					"AND THANG = Month('"+ngayDuaSoDu+"') AND NAM = YEAR('"+ngayDuaSoDu+"') ";
	
			query+=	" UNION ALL " +
				" SELECT SUM(NO) - SUM(CO) DAUKY FROM ERP_PHATSINHKETOAN WHERE TAIKHOAN_FK =  \n" + 
				"	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN (" + this.chiNhanh + ") )  \n" + 
				" AND NGAYGHINHAN <'"+this.tungay+"'  " +
				" AND NGAYGHINHAN > '"+ngayDuaSoDu+"' " +
		
				" UNION ALL " +
				" SELECT SUM(NO) - SUM(CO) DAUKY FROM "+util.prefixDMS+"ERP_PHATSINHKETOAN WHERE TAIKHOAN_FK =  \n" + 
				"	(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN (" + this.chiNhanh + ") )  \n" + 
				" AND NGAYGHINHAN <'"+this.tungay+"'  " +
				" AND NGAYGHINHAN > '"+ngayDuaSoDu+"' " ;
			query+= " ) data " ;
			
		
			System.out.println("dau ky:" + query);
			rs = this.db.get(query);
			if (rs != null)
			{
				if (rs.next())
					this.sodudau = rs.getString("DAUKY");
				rs.close();
			}
			}
			
/*			this.dauky = this.db.get("SELECT DAUKY_THU - DAUKY_CHI AS DAUKY " +
								 	 "FROM ( " +
								 	 "	SELECT ISNULL(SUM(TONGGIATRI), 0) AS DAUKY_THU, 0 AS DAUKY_CHI " +
								 	 "	FROM ERP_PHATSINHKETOAN PSTK " +
								 	 "	WHERE TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%') " +
								 	 "  AND PSTK.NGAYGHINHAN < '" + this.tungay + "' AND PSTK.LOAICHUNGTU LIKE N'Thu tiền%' " +
								 	 "UNION " +
								 	 "SELECT 0 AS DAUKYTHU, ISNULL(SUM(TONGGIATRI), 0) AS DAUKY_CHI " +
								 	 "FROM ERP_PHATSINHKETOAN PSTK " +
								 	 "WHERE TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%') " +
								 	 "AND PSTK.NGAYGHINHAN < '" + this.tungay + "' AND PSTK.LOAICHUNGTU LIKE N'Thanh toán%' " +
								 	 ")DAUKY ");*/
		
			rs = this.db.get("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
			System.out.println("SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId);
		
			if(rs != null) {
				rs.next();
				this.ctyTen = rs.getString("TEN");
				this.diachi = rs.getString("DIACHI");
				System.out.println(this.diachi);
				
				this.masothue = rs.getString("MASOTHUE");
				System.out.println(this.masothue);
				
				rs.close();
			}
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		
		
		if(this.tungay.length() > 0 & this.denngay.length() > 0){
						
			/*query = " SELECT * \n"+
					" FROM \n"+
					" ( \n"+
					" SELECT TAIKHOAN_2.SOHIEUTAIKHOAN TAIKHOAN ,PHATSINH.NGAYCHUNGTU NGAY, \n"+ 
					" ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) SOCHUNGTU_THU, '' SOCHUNGTU_CHI, \n"+
//					" PHATSINH.DIENGIAI AS DIENGIAI_THU, '' AS DIENGIAI_CHI, \n"+
					" isNull(phatSinh.dienGiai, '') AS DIENGIAI_THU \n" +
					//Đang chuyễn tất cả diễn giải vào bảng ERP_PHATSINHKETOAN nên không cần lấy diễn giải ở nghiệp vụ
					" (\n" +	
					" case when (phatSinh.loaiChungTu like N'Thu tiền KH trả trước' or phatSinh.loaiChungTu like N'Thu tiền') then (select ghiChu from ERP_THUTIEN where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Thanh toán hóa đơn' or phatSinh.loaiChungTu like N'Phiếu chi' or phatSinh.loaiChungTu like N'Ủy nhiệm chi') then (select noiDungTT from ERP_THANHTOANHOADON where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Thuế nhập khẩu') then (select dienGiai from ERP_THUENHAPKHAU where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Nhận hàng') then (select dienGiai from ERP_NHANHANG where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Điều chuyển tiền') then (select '' from ERP_DIEUCHUYENTIEN where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Bút toán tổng hợp') then (select dienGiai from ERP_BUTTOANTONGHOP where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Thuế VAT nhập khẩu' or phatSinh.loaiChungTu like N'Thuế nhập khẩu') then (select dienGiai from ERP_THUENHAPKHAU where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Duyệt đề nghị thanh toán') then (select isNull(ghiChu, '') + ' - ' + ISNULL(lyDoTT, '') from ERP_MUAHANG where PK_SEQ = phatSinh.sochungtu)\n" +
					" end) AS DIENGIAI_THU\n" +
					", '' AS DIENGIAI_CHI, \n"+
					" TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG, SUM(ISNULL(PHATSINH.NO, 0)) AS THU, SUM(ISNULL(PHATSINH.CO, 0)) AS CHI \n"+
					" FROM ERP_PHATSINHKETOAN PHATSINH \n"+
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq \n"+
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq \n"+
					" WHERE 1 = 1 AND (PHATSINH.NO > 0 OR PHATSINH.CO > 0) \n"+
					//" AND TAIKHOAN.CONGTY_FK IN (" + this.ctyId + ") \n"+
					//" AND TAIKHOAN_2.CONGTY_FK IN (" + this.ctyId + ") \n"+
					" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"' \n"+
					" AND PHATSINH.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN ("+this.chiNhanh+") ) " +
					//" AND CONGTY_FK IN (" + this.ctyId + ")) \n"+
					" GROUP BY TAIKHOAN_2.SOHIEUTAIKHOAN, PHATSINH.NGAYCHUNGTU, PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU, PHATSINH.DIENGIAI, TAIKHOAN.SOHIEUTAIKHOAN \n" +
					" , PHATSINH.DIENGIAI, PHATSINH.LOAICHUNGTU\n"+ 
					 
					" UNION ALL \n"+
					 
					" SELECT  TAIKHOAN_2.SOHIEUTAIKHOAN TAIKHOAN, PHATSINH.NGAYCHUNGTU NGAY, '' AS SOCHUNGTU_THU, ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) AS SOCHUNGTU_CHI, '' DIENGIAI_THU, \n"+
//					" PHATSINH.DIENGIAI AS DIENGIAI_CHI\n" +
					" isNull(phatSinh.dienGiai, '') + ' - ' +\n" +
					" (\n" +
					" case when (phatSinh.loaiChungTu like N'Thu tiền KH trả trước' or phatSinh.loaiChungTu like N'Thu tiền') then (select ghiChu from ERP_THUTIEN where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Thanh toán hóa đơn' or phatSinh.loaiChungTu like N'Phiếu chi' or phatSinh.loaiChungTu like N'Ủy nhiệm chi') then (select noiDungTT from ERP_THANHTOANHOADON where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Thuế nhập khẩu') then (select dienGiai from ERP_THUENHAPKHAU where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Nhận hàng') then (select dienGiai from ERP_NHANHANG where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Điều chuyển tiền') then (select '' from ERP_DIEUCHUYENTIEN where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Bút toán tổng hợp') then (select dienGiai from ERP_BUTTOANTONGHOP where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Thuế VAT nhập khẩu' or phatSinh.loaiChungTu like N'Thuế nhập khẩu') then (select dienGiai from ERP_THUENHAPKHAU where PK_SEQ = phatSinh.sochungtu)\n" +
					" when (phatSinh.loaiChungTu like N'Duyệt đề nghị thanh toán') then (select isNull(ghiChu, '') + ' - ' + ISNULL(lyDoTT, '') from ERP_MUAHANG where PK_SEQ = phatSinh.sochungtu)\n" +
					" end)\n" +
					" AS DIENGIAI_CHI\n" +
					", TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG, SUM(ISNULL(PHATSINH.NO, 0)) AS THU, SUM(ISNULL(PHATSINH.CO, 0)) AS CHI \n"+ 
					" FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PHATSINH \n"+
					" INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq \n"+
					" INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq \n"+
					" WHERE 1 = 1 AND (PHATSINH.NO > 0 OR PHATSINH.CO > 0) \n"+
					" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"' \n"+
					" AND PHATSINH.TAIKHOAN_FK IN ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN ("+this.chiNhanh+") )\n" +
					//" AND CONGTY_FK IN (" + this.ctyId + ")) \n"+
					" GROUP BY TAIKHOAN_2.SOHIEUTAIKHOAN, PHATSINH.NGAYCHUNGTU, PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU, PHATSINH.DIENGIAI, TAIKHOAN.SOHIEUTAIKHOAN  \n" +
					" , PHATSINH.DIENGIAI, PHATSINH.LOAICHUNGTU\n"+
					" ) A \n"+
					" ORDER BY A.NGAY \n";*/
			
		/*	query = " SELECT  PHATSINH.NGAYCHUNGTU NGAY,  \n"+
					" CASE WHEN PHATSINH.NO > 0 THEN ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) ELSE NULL END AS SOCHUNGTU_THU, \n"+
					" CASE WHEN PHATSINH.CO > 0 THEN ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) ELSE NULL END AS SOCHUNGTU_CHI, \n"+
					" CASE WHEN PHATSINH.NO > 0 THEN PHATSINH.DIENGIAI ELSE NULL END AS DIENGIAI_THU, \n"+
					" CASE WHEN PHATSINH.CO > 0 THEN PHATSINH.DIENGIAI END AS DIENGIAI_CHI, \n"+
				    " TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG, SUM(ISNULL(PHATSINH.NO, 0)) AS THU, SUM(ISNULL(PHATSINH.CO, 0)) AS CHI \n"+
			 		 
					" FROM ERP_PHATSINHKETOAN PHATSINH \n"+
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq \n"+
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq \n"+
					" WHERE 1 = 1 \n"+
					" AND TAIKHOAN.CONGTY_FK IN (" + this.ctyId + ") \n"+   
					" AND TAIKHOAN_2.CONGTY_FK IN (" + this.ctyId + ") \n"+ 
					" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"' \n"+
					" AND PHATSINH.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND CONGTY_FK IN ("+ctyId+" )) \n"+
					" GROUP BY PHATSINH.NGAYCHUNGTU, PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU, PHATSINH.DIENGIAI, TAIKHOAN.SOHIEUTAIKHOAN  \n"+
					" ORDER BY PHATSINH.NGAYCHUNGTU \n";*/
			
				/*	query = "SELECT * \n" + 
							" FROM \n" + 
							" (  \n" + 
							" SELECT TAIKHOAN_2.SOHIEUTAIKHOAN TAIKHOAN ,PHATSINH.NGAYCHUNGTU NGAY,  \n" + 
							" CASE WHEN (PHATSINH.NO > 0) THEN ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) ELSE '' END AS SOCHUNGTU_THU,  \n" + 
							" CASE WHEN (PHATSINH.CO > 0) THEN ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) ELSE '' END AS SOCHUNGTU_CHI,  \n" + 
							" CASE WHEN (PHATSINH.NO > 0) THEN isNull(phatSinh.dienGiai, '') ELSE '' END AS DIENGIAI_THU,  \n" + 
							" CASE WHEN (PHATSINH.CO > 0) THEN isNull(phatSinh.dienGiai, '') ELSE '' END AS DIENGIAI_CHI,  \n" + 
							" TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG, SUM(ISNULL(PHATSINH.NO, 0)) AS THU, SUM(ISNULL(PHATSINH.CO, 0)) AS CHI, \n" + 
							" CASE WHEN (PHATSINH.NO > 0) THEN 'A' ELSE 'B' END AS KYTU,1 AS STT  \n" + 
							" FROM ERP_PHATSINHKETOAN PHATSINH  \n" + 
							" INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq  \n" + 
							" INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq  \n" + 
							" WHERE 1 = 1 AND (PHATSINH.NO > 0 OR PHATSINH.CO > 0)  \n" + 
							" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"'  \n" + 
							" AND PHATSINH.TAIKHOAN_FK IN ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN ("+this.chiNhanh+") ) \n" + 
							" GROUP BY TAIKHOAN_2.SOHIEUTAIKHOAN, PHATSINH.NGAYCHUNGTU, PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU, PHATSINH.DIENGIAI, TAIKHOAN.SOHIEUTAIKHOAN  \n" + 
							" , PHATSINH.DIENGIAI, PHATSINH.LOAICHUNGTU, PHATSINH.CO, PHATSINH.NO,STT \n" + 
							" UNION ALL  \n" + 
							" SELECT  TAIKHOAN_2.SOHIEUTAIKHOAN TAIKHOAN, PHATSINH.NGAYCHUNGTU NGAY,  \n" + 
							" CASE WHEN (PHATSINH.NO > 0) THEN ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) ELSE '' END AS SOCHUNGTU_THU,  \n" + 
							" CASE WHEN (PHATSINH.CO > 0) THEN ISNULL(PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU) ELSE '' END AS SOCHUNGTU_CHI,  \n" + 
							"CASE WHEN (PHATSINH.NO > 0) THEN isNull(phatSinh.dienGiai, '') + ' - ' + \n" + 
							" ( \n" + 
							" case when (phatSinh.loaiChungTu like N'Thu tiền KH trả trước' or phatSinh.loaiChungTu like N'Thu tiền') then (select ghiChu from ERP_THUTIEN where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Thanh toán hóa đơn' or phatSinh.loaiChungTu like N'Phi?u chi' or phatSinh.loaiChungTu like N'?y nhi?m chi') then (select noiDungTT from ERP_THANHTOANHOADON where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Thuế nhập khẩu') then (select dienGiai from ERP_THUENHAPKHAU where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Nhận hàng') then (select dienGiai from ERP_NHANHANG where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Điều chuyển tiền') then (select '' from ERP_DIEUCHUYENTIEN where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Bút toán tổng hợp') then (select dienGiai from ERP_BUTTOANTONGHOP where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Thuế VAT nhập khẩu' or phatSinh.loaiChungTu like N'Thu? nh?p kh?u') then (select dienGiai from ERP_THUENHAPKHAU where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Duyệt đề nghị thanh toán') then (select isNull(ghiChu, '') + ' - ' + ISNULL(lyDoTT, '') from ERP_MUAHANG where PK_SEQ = phatSinh.sochungtu) \n" + 
							" end) ELSE '' END AS DIENGIAI_THU,  \n" + 
							" CASE WHEN (PHATSINH.CO > 0) THEN isNull(phatSinh.dienGiai, '') + ' - ' + \n" + 
							" ( \n" + 
							" case when (phatSinh.loaiChungTu like N'Thu tiền KH trả trước' or phatSinh.loaiChungTu like N'Thu tiền') then (select ghiChu from ERP_THUTIEN where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Thanh toán hóa đơn' or phatSinh.loaiChungTu like N'Phi?u chi' or phatSinh.loaiChungTu like N'?y nhi?m chi') then (select noiDungTT from ERP_THANHTOANHOADON where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Thuế nhập khẩu') then (select dienGiai from ERP_THUENHAPKHAU where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Nhận hàng') then (select dienGiai from ERP_NHANHANG where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Điều chuyển tiền') then (select '' from ERP_DIEUCHUYENTIEN where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Bút toán tổng hợp') then (select dienGiai from ERP_BUTTOANTONGHOP where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Thuế VAT nhập khẩu' or phatSinh.loaiChungTu like N'Thu? nh?p kh?u') then (select dienGiai from ERP_THUENHAPKHAU where PK_SEQ = phatSinh.sochungtu) \n" + 
							" when (phatSinh.loaiChungTu like N'Duyệt đề nghị thanh toán') then (select isNull(ghiChu, '') + ' - ' + ISNULL(lyDoTT, '') from ERP_MUAHANG where PK_SEQ = phatSinh.sochungtu) \n" + 
							" end) ELSE '' END AS DIENGIAI_CHI \n" + 
							", TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG, SUM(ISNULL(PHATSINH.NO, 0)) AS THU, SUM(ISNULL(PHATSINH.CO, 0)) AS CHI, \n" + 
							" CASE WHEN (PHATSINH.NO > 0) THEN 'A' ELSE 'B' END AS KYTU,2 AS STT   \n" + 
							" FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PHATSINH  \n" + 
							" INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq  \n" + 
							" INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq  \n" + 
							" WHERE 1 = 1 AND (PHATSINH.NO > 0 OR PHATSINH.CO > 0)  \n" + 
							" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"'  \n" + 
							" AND PHATSINH.TAIKHOAN_FK IN ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN ("+this.chiNhanh+") ) \n" + 
							" GROUP BY TAIKHOAN_2.SOHIEUTAIKHOAN, PHATSINH.NGAYCHUNGTU, PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU, PHATSINH.DIENGIAI, TAIKHOAN.SOHIEUTAIKHOAN   \n" + 
							" , PHATSINH.DIENGIAI, PHATSINH.LOAICHUNGTU, PHATSINH.CO, PHATSINH.NO,STT \n" + 
							" ) A  \n" + 
//							" ORDER BY A.NGAY, KYTU \n" +
							"ORDER BY A.NGAY,A.STT ASC,A.SOCHUNGTU_THU,A.SOCHUNGTU_CHI " ;
							
			
			System.out.println("cau lenh so quy tien mat: \n" + query + "\n------------------------------------------------");*/
			
			
			query = " SELECT DISTINCT * \n"+
					" FROM \n"+
					" ( \n"+
					" SELECT TAIKHOAN_2.SOHIEUTAIKHOAN TAIKHOAN ,PHATSINH.NGAYCHUNGTU NGAY, \n"+ 
					" 		  CASE WHEN LEN(CAST( ISNULL(TT.SOCHUNGTU, '') AS NVARCHAR(50)) ) <= 0 THEN ISNULL(PHATSINH.MACHUNGTU, '')   ELSE CAST( ISNULL(TT.SOCHUNGTU, '') AS NVARCHAR(50)) END AS SOCHUNGTU_THU, '' SOCHUNGTU_CHI,  \n"+
					" ISNULL(PHATSINH.DIENGIAI, PHATSINH.LOAICHUNGTU) AS DIENGIAI_THU, '' AS DIENGIAI_CHI, \n"+
					" SUM(ISNULL(PHATSINH.NO, 0)) AS THU, SUM(ISNULL(PHATSINH.CO, 0)) AS CHI,1  AS STT ,  TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG\n "+
					" FROM ERP_PHATSINHKETOAN PHATSINH \n"+
					"  LEFT JOIN ERP_THUTIEN TT on TT.PK_SEQ = PHATSINH.SOCHUNGTU "+ 
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq \n"+
					"   INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq "+
					" WHERE 1 = 1 and ISNULL(PHATSINH.NO, 0) <> 0 \n"+
					//" AND TAIKHOAN_2.CONGTY_FK IN (" + this.ErpCongTyId + ") \n"+
					" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"' \n"+
					" AND PHATSINH.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN (" + this.chiNhanh + ")) \n"+
					" GROUP BY TAIKHOAN_2.SOHIEUTAIKHOAN, PHATSINH.NGAYCHUNGTU, PHATSINH.MACHUNGTU, TT.SOCHUNGTU, PHATSINH.DIENGIAI,  PHATSINH.LOAICHUNGTU, TAIKHOAN.SOHIEUTAIKHOAN\n"+ 
					" UNION ALL " + 
					" SELECT TAIKHOAN_2.SOHIEUTAIKHOAN TAIKHOAN ,PHATSINH.NGAYCHUNGTU NGAY, \n"+ 
					" 		  CASE WHEN LEN(CAST( ISNULL(TT.SOCHUNGTU, '') AS NVARCHAR(50)) ) <= 0 THEN ISNULL(PHATSINH.MACHUNGTU, '')   ELSE CAST( ISNULL(TT.SOCHUNGTU, '') AS NVARCHAR(50)) END AS SOCHUNGTU_THU, '' SOCHUNGTU_CHI,  \n"+
					" ISNULL(PHATSINH.DIENGIAI, PHATSINH.LOAICHUNGTU) AS DIENGIAI_THU, '' AS DIENGIAI_CHI, \n"+
					" SUM(ISNULL(PHATSINH.NO, 0)) AS THU, SUM(ISNULL(PHATSINH.CO, 0)) AS CHI,2  AS STT ,  TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG\n "+
					" FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PHATSINH \n"+
					"  LEFT JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_THUTIEN TT on TT.PK_SEQ = PHATSINH.SOCHUNGTU "+
					" INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq \n"+
					"   INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq "+
					" WHERE 1 = 1 and ISNULL(PHATSINH.NO, 0) <> 0 \n"+
					" AND TAIKHOAN_2.CONGTY_FK IN (" + this.ErpCongTyId + ") \n"+
					" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"' \n"+
					" AND PHATSINH.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN (" + this.chiNhanh + ")) \n"+
					" GROUP BY TAIKHOAN_2.SOHIEUTAIKHOAN, PHATSINH.NGAYCHUNGTU, PHATSINH.MACHUNGTU, TT.SOCHUNGTU, PHATSINH.DIENGIAI,  PHATSINH.LOAICHUNGTU, TAIKHOAN.SOHIEUTAIKHOAN\n"+ 
					
					
					
					" UNION ALL \n"+
					
					
					" SELECT  TAIKHOAN_2.SOHIEUTAIKHOAN TAIKHOAN, PHATSINH.NGAYCHUNGTU NGAY, '' AS SOCHUNGTU_THU, " +
					" 		 CASE WHEN LEN(ISNULL(PHATSINH.MACHUNGTU, '')) <=0 THEN CAST( ISNULL(PHATSINH.SOCHUNGTU, '0') AS NVARCHAR(50)) "+
					"		 ELSE ISNULL(PHATSINH.MACHUNGTU, '') END AS SOCHUNGTU_CHI, " +
					" 		 '' DIENGIAI_THU, \n"+
					" ISNULL(PHATSINH.DIENGIAI, PHATSINH.LOAICHUNGTU) AS DIENGIAI_CHI,  SUM(ISNULL(PHATSINH.NO, 0)) AS THU, SUM(ISNULL(PHATSINH.CO, 0)) AS CHI ,3 AS STT,  TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG\n"+ 
					" FROM ERP_PHATSINHKETOAN PHATSINH \n"+
					" INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq \n"+
					"   INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq "+
					" WHERE 1 = 1 and ISNULL(PHATSINH.CO, 0) <> 0 \n"+
					//" AND TAIKHOAN_2.CONGTY_FK IN (" + this.ErpCongTyId + ") \n"+ 
					" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"' \n"+
					" AND PHATSINH.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN (" + this.chiNhanh + ")) \n"+
					" GROUP BY TAIKHOAN_2.SOHIEUTAIKHOAN, PHATSINH.NGAYCHUNGTU, PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU, PHATSINH.DIENGIAI,  PHATSINH.LOAICHUNGTU , TAIKHOAN.SOHIEUTAIKHOAN\n"+
					
					" UNION ALL \n"+
					
					
					" SELECT  TAIKHOAN_2.SOHIEUTAIKHOAN TAIKHOAN, PHATSINH.NGAYCHUNGTU NGAY, '' AS SOCHUNGTU_THU, " +
					" 		 CASE WHEN LEN(ISNULL(PHATSINH.MACHUNGTU, '')) <=0 THEN CAST( ISNULL(PHATSINH.SOCHUNGTU, '0') AS NVARCHAR(50)) "+
					"		 ELSE ISNULL(PHATSINH.MACHUNGTU, '') END AS SOCHUNGTU_CHI, " +
					" 		 '' DIENGIAI_THU, \n"+
					" ISNULL(PHATSINH.DIENGIAI, PHATSINH.LOAICHUNGTU) AS DIENGIAI_CHI,  SUM(ISNULL(PHATSINH.NO, 0)) AS THU, SUM(ISNULL(PHATSINH.CO, 0)) AS CHI ,4 AS STT,  TAIKHOAN.SOHIEUTAIKHOAN AS TK_DOIUNG\n"+ 
					" FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PHATSINH \n"+
					" INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq \n"+
					"   INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq "+
					" WHERE 1 = 1 and ISNULL(PHATSINH.CO, 0) <> 0 \n"+
					//" AND TAIKHOAN_2.CONGTY_FK IN (" + this.ErpCongTyId + ") \n"+ 
					" AND PHATSINH.NGAYGHINHAN >= '"+this.tungay+"' AND PHATSINH.NGAYGHINHAN <= '"+this.denngay+"' \n"+
					" AND PHATSINH.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '111%' AND NPP_FK IN (" + this.chiNhanh + ")) \n"+
					" GROUP BY TAIKHOAN_2.SOHIEUTAIKHOAN, PHATSINH.NGAYCHUNGTU, PHATSINH.MACHUNGTU, PHATSINH.SOCHUNGTU, PHATSINH.DIENGIAI,  PHATSINH.LOAICHUNGTU , TAIKHOAN.SOHIEUTAIKHOAN\n"+
					
					
					" ) A \n"+
					" ORDER BY A.NGAY,A.STT,A.SOCHUNGTU_THU,A.SOCHUNGTU_CHI \n";
			
			
			System.out.println("::: SO QUY TIEN MAT: " + query);
			
			this.rs = this.db.get(query);
		}
	}

	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void DBClose(){
		if(db != null) db.shutDown();
	}

	public String getChiNhanh() {
		return chiNhanh;
	}

	public void setChiNhanh(String chiNhanh) {
		this.chiNhanh = chiNhanh;
	}

	public String getChiNhanhTen() {
		return chiNhanhTen;
	}

	public void setChiNhanhTen(String chiNhanhTen) {
		this.chiNhanhTen = chiNhanhTen;
	}

	public String getChiNhanhMaSoThue() {
		return chiNhanhMaSoThue;
	}

	public void setChiNhanhMaSoThue(String chiNhanhMaSoThue) {
		this.chiNhanhMaSoThue = chiNhanhMaSoThue;
	}

	public String getChiNhanhDiaChi() {
		return chiNhanhDiaChi;
	}

	public void setChiNhanhDiaChi(String chiNhanhDiaChi) {
		this.chiNhanhDiaChi = chiNhanhDiaChi;
	}

	public ResultSet getChiNhanhRs() {
		return chiNhanhRs;
	}

	public void setChiNhanhRs(ResultSet chiNhanhRs) {
		this.chiNhanhRs = chiNhanhRs;
	}
}