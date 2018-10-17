package geso.traphaco.erp.beans.bangchitiettaikhoan.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.bangchitiettaikhoan.INhatkytaikhoan;

public class Nhatkytaikhoan implements INhatkytaikhoan 
{
	String userId;
	String ctyId;	
	String ctyTen;
	String year;
	String month;
	String diachi;
	String masothue;
	String tkId;
	private String nhomTaiKhoanId;
	private String loaiNghiepVu;
	String sohieu;
	String daukyno;
	String daukyco;
	String timkiem;
	
	String tungay;
	String denngay;
	
	ResultSet rs;
	ResultSet tk;
	
	private List<Erp_Item> nhomTaiKhoanList;
	private List<Erp_Item> loaiNghiepVuList;
	

	String tongNO;
	String tongCO;
	
	ResultSet congtyRs;
	String[] ctyIds;
	
	String ErpCongTyId;
	String view;
	
	String msg;
	dbutils db;
	
	
	public Nhatkytaikhoan() {
		this.userId = "";
		this.ctyId = "";
		this.ctyTen = "";
		this.sohieu = "";
		this.daukyno = "0";
		this.daukyco = "0";
		this.tongCO = "0";
		this.tongNO = "0";
		this.tkId = "";
		this.nhomTaiKhoanId = "";
		this.loaiNghiepVu = "";
		this.timkiem="";
		this.tungay = "";
		this.denngay = "";
		this.month = "";
		
		this.year = "";

		this.nhomTaiKhoanList = new ArrayList<Erp_Item>();
		this.loaiNghiepVuList = new ArrayList<Erp_Item>();
		
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

	public void setTongNO(String tongNO) {

		this.tongNO = tongNO;
	}

	public String getTongNO() {

		return this.tongNO;
	}

	public void setTongCO(String tongCO) {

		this.tongCO = tongCO;
	}

	public String getTongCO() {

		return this.tongCO;
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

	
	public void init(){
		//init_0();
		
		// tạm thời bỏ công ty ra. Khi đã đồng bộ bán hàng lên thì chỉnh lại
		initRs();
		
		System.out.println("SELECT PK_SEQ AS TKID, SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN AS TAIKHOAN FROM ERP_TAIKHOANKT " +
						   "ORDER BY SOHIEUTAIKHOAN");
		String soHieuTaiKhoan="";
		if (this.tkId != ""){
			try{
				String sql = "SELECT ISNULL(SOHIEUTAIKHOAN,0) AS SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ ="+ this.tkId;
				ResultSet rsSoHieu = db.get(sql);
				if(rsSoHieu != null){
					while(rsSoHieu.next()){
						soHieuTaiKhoan = rsSoHieu.getString("SOHIEUTAIKHOAN");
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		String query = "";
	
		if(this.tungay.trim().length() >0 || this.denngay.trim().length() >0)
		{
			
			try{
			/*query = "\n SELECT * FROM ( " +
				
					"\n SELECT ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU like N'Thu tiền%' THEN (select PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) from ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU)" +
					"\n 		WHEN  PHATSINH.LOAICHUNGTU like N'Thanh toán hóa đơn%' THEN (select PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) from ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU)" +
					"\n 		ELSE CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) END , '' ) AS PK_SEQ," +
					"\n			ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select NGAYXUATHD from " + Utility.prefixDMS + "ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) " +
					"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select NGAYXUATHD from erp_hoadonnpp where PK_SEQ=PHATSINH.SOCHUNGTU) " +
					"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select NGAYHOADON from ERP_HoaDonPheLieu where PK_SEQ=PHATSINH.SOCHUNGTU) " +
					"\n            WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select NGAYXUATHD from ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) " +
					"\n            WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select NGAYHOADON from ERP_HOADONNCC where PARK_FK =PHATSINH.SOCHUNGTU) " +
					"\n            WHEN PHATSINH.LOAICHUNGTU like N'Giảm/Tăng giá hàng bán' THEN (select NGAYHOADON from erp_giamgiahangban where PK_SEQ=PHATSINH.SOCHUNGTU) " +
					"\n            WHEN PHATSINH.LOAICHUNGTU like N'Thu tiền%' THEN (select NGAYCHUNGTU from ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU) " +
					"\n       ELSE PHATSINH.NGAYCHUNGTU END , PHATSINH.NGAYCHUNGTU )  AS NGAYCHUNGTU,  "+
					"\n    ISNULL(PHATSINH.SOCHUNGTU, '0') AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN_2.SOHIEUTAIKHOAN AS TAIKHOAN, " +
					"\n   TAIKHOAN.SOHIEUTAIKHOAN AS DOIUNG, ISNULL(PHATSINH.NO, 0) AS NO, ISNULL(PHATSINH.CO, 0) AS CO,  " +
					"\n ISNULL(	case when  DOITUONG = N'Nhà cung cấp' then ( select MA from ERP_NHACUNGCAP where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) " +
					"\n	when  DOITUONG = N'Khách hàng' then ( select MAFAST from NHAPHANPHOI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) " +
					"\n	when  DOITUONG = N'Nhân viên' then ( select MA from ERP_NHANVIEN where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
					"\n	when  DOITUONG = N'Ngân hàng' then ( select MA from ERP_NGANHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +					
					"\n	when  DOITUONG = N'Sản phẩm' then ( select MA from " + Utility.prefixDMS + "SANPHAM where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
					"\n	when  DOITUONG = N'Chi phí' then ( select MA from ERP_NHOMCHIPHI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
					"\n	when  DOITUONG = N'Tài sản' then ( select MA from ERP_TAISANCODINH where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
					"\n	when  DOITUONG = N'Công cụ dụng cụ' then ( select MA from ERP_CONGCUDUNGCU where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
					"\n	when  DOITUONG = N'Trung tâm chi phí' then ( select MA from ERP_TRUNGTAMCHIPHI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  " +
					"\n else ( case when LEN(DOITUONG) <= 0 then KHOANMUC else DOITUONG end ) end ,'') as DOITUONG,  " +
					"\n    ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select isnull(SOHOADON,'') from " + Utility.prefixDMS + "ERP_HOADON where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select isnull(SOHOADON,'') from erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select isnull(SOHOADON,'') from erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					"\n         WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select isnull(SOHOADON,'') from erp_hoadonncc where park_fk =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					"\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select isnull(SOHOADON,'') from ERP_HoaDonPheLieu where pk_seq =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					"\n  	  WHEN PHATSINH.LOAICHUNGTU like N'Giảm/Tăng giá hàng bán' THEN  (select sohoadon from erp_giamgiahangban where PK_SEQ=PHATSINH.SOCHUNGTU)	"+
					"\n   	  WHEN PHATSINH.LOAICHUNGTU like N'Nhận hàng' THEN  (select isnull(c.SOHOADON,'') from ERP_NHANHANG a inner join ERP_HOADONNCC c on a.hdNCC_fk = c.pk_seq where a.PK_SEQ=PHATSINH.SOCHUNGTU)		"+ 
					"\n    ELSE '' END , '') AS SOHOADON , "+						
					"\n	(SELECT CTY.TEN " +
					"\n	FROM ERP_TAIKHOANKT TK  " + 
					"\n	INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = TK.CONGTY_FK  AND CONGTY_FK IN (" + this.ctyId + ") " +
					"\n	WHERE TK.PK_SEQ = PHATSINH.TAIKHOAN_FK  ) AS CONGTY " +
					"\n FROM " +
					"\n	(SELECT PK_SEQ,LOAICHUNGTU,TAIKHOAN_FK,TAIKHOANDOIUNG_FK,NO,CO,DOITUONG,MADOITUONG,KHOANMUC,SOCHUNGTU,NGAYCHUNGTU,NGAYGHINHAN,NOIDUNGNHAPXUAT_FK"
					+ "\n FROM ERP_PHATSINHKETOAN A " +
					"\n	 UNION ALL " +
					"\n  SELECT PK_SEQ,LOAICHUNGTU,TAIKHOAN_FK,TAIKHOANDOIUNG_FK,NO,CO,DOITUONG,MADOITUONG,KHOANMUC,SOCHUNGTU,NGAYCHUNGTU,NGAYGHINHAN,NOIDUNGNHAPXUAT_FK"
					+ "\n FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN "+
					"\n	)PHATSINH " +					
					"\n INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN.pk_seq " +
					"\n INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOAN_FK = TAIKHOAN_2.pk_seq " +
					"\n LEFT JOIN ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK " +
					"\n WHERE 1 = 1 "+
					"\n AND TAIKHOAN.CONGTY_FK IN (" + this.ctyId + ")  AND TAIKHOAN_2.CONGTY_FK IN (" + this.ctyId + ") "; // this.ErpCongTyId chứa id những công ty chọn trong báo cáo
					
			if(this.tungay.trim().length() >0 )
				query += " AND PHATSINH.NGAYGHINHAN >= '"+this.tungay +"'\n";
			
			if(this.denngay.trim().length() >0 )
				query += " AND PHATSINH.NGAYGHINHAN <= '"+this.denngay +"'\n";
		
			if(this.tkId.length() > 0){
				query = query + "\n AND PHATSINH.TAIKHOAN_FK IN ( " +this.tkId+ " ) \n" ;
			}
			
			if(this.loaiNghiepVu.length() > 0){
				query = query + "\n AND PHATSINH.LOAICHUNGTU like N'" +this.loaiNghiepVu + "'\n" ;
			}
			
			if(this.nhomTaiKhoanId.length() > 0){
				query = query + "\n AND PHATSINH.TAIKHOAN_FK in (select taikhoan_fk from GROUP_TAIKHOAN_NHOM_TK where group_taikhoan_nhom_fk = " +this.nhomTaiKhoanId + ") \n" ;
			}
			
			query += ")DATA \n" +
			 	" ORDER BY DATA.PK_SEQ desc  \n" ;*/
			String congTy = this.getErpCongtyId();
			if (congTy.trim().endsWith(",")){
				congTy = congTy.substring(0,congTy.length() -1);
			}
			query = "SELECT * FROM \n"
			+ " ( \n"
			+ "  SELECT PK_SEQ,NGAYCHUNGTU,SOCHUNGTU,NOIDUNG,TAIKHOAN,DOIUNG,NO,CO,DOITUONG,SOHOADON,CONGTY,DIENGIAI FROM (  \n"
			+ "  SELECT ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'THU TIỀN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \n"
			+ "  		WHEN  PHATSINH.LOAICHUNGTU LIKE N'THANH TOÁN HÓA ĐƠN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \n"
			+ "  		ELSE CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) END , '' ) AS PK_SEQ, \n"
			+ " 			ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT NGAYXUATHD FROM ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU)  \n"
			+ "             WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT NGAYXUATHD FROM ERP_HOADONNPP WHERE PK_SEQ=PHATSINH.SOCHUNGTU)  \n"
			+ "             WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT NGAYHOADON FROM ERP_HOADONPHELIEU WHERE PK_SEQ=PHATSINH.SOCHUNGTU)  \n"
			+ "             WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT NGAYXUATHD FROM ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU)  \n"
			+ "             WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT NGAYHOADON FROM ERP_HOADONNCC WHERE PARK_FK =PHATSINH.SOCHUNGTU)  \n"
			+ "             WHEN PHATSINH.LOAICHUNGTU LIKE N'GIẢM/TĂNG GIÁ HÀNG BÁN' THEN (SELECT NGAYHOADON FROM ERP_GIAMGIAHANGBAN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)  \n"
			+ "             WHEN PHATSINH.LOAICHUNGTU LIKE N'THU TIỀN%' THEN (SELECT NGAYCHUNGTU FROM ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)  \n"
			+ "        ELSE PHATSINH.NGAYCHUNGTU END , PHATSINH.NGAYCHUNGTU )  AS NGAYCHUNGTU,   \n"
			+ "     ISNULL(PHATSINH.SOCHUNGTU, '0') AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN.SOHIEUTAIKHOAN AS TAIKHOAN,  \n"
			+ "    TAIKHOANDU.SOHIEUTAIKHOAN AS DOIUNG, ROUND(ISNULL(PHATSINH.NO, 0),0) AS NO, ROUND(ISNULL(PHATSINH.CO, 0),0) AS CO,   \n"
			+ "  ISNULL(	CASE WHEN  DOITUONG = N'NHÀ CUNG CẤP' THEN ( SELECT MA FROM ERP_NHACUNGCAP WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )  \n"
			+ " 	WHEN  DOITUONG = N'KHÁCH HÀNG' THEN ( SELECT MAFAST FROM NHAPHANPHOI WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )  \n"
			+ " 	WHEN  DOITUONG = N'NHÂN VIÊN' THEN ( SELECT MA FROM ERP_NHANVIEN WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )   \n"
			+ " 	WHEN  DOITUONG = N'NGÂN HÀNG' THEN ( SELECT MA FROM ERP_NGANHANG WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )   \n"
			+ " 	WHEN  DOITUONG = N'SẢN PHẨM' THEN ( SELECT MA FROM " + Utility.prefixDMS + "SANPHAM WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )   \n"
			+ " 	WHEN  DOITUONG = N'CHI PHÍ' THEN ( SELECT MA FROM ERP_NHOMCHIPHI WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )   \n"
			+ " 	WHEN  DOITUONG = N'TÀI SẢN' THEN ( SELECT MA FROM ERP_TAISANCODINH WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )   \n"
			+ " 	WHEN  DOITUONG = N'CÔNG CỤ DỤNG CỤ' THEN ( SELECT MA FROM ERP_CONGCUDUNGCU WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )   \n"
			+ " 	WHEN  DOITUONG = N'TRUNG TÂM CHI PHÍ' THEN ( SELECT MA FROM ERP_TRUNGTAMCHIPHI WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )   \n"
			+ "  ELSE ( CASE WHEN LEN(DOITUONG) <= 0 THEN KHOANMUC ELSE DOITUONG END ) END ,'') AS DOITUONG,   \n"
			+ "     ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0'))  \n"
			+ "          WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0'))  \n"
			+ "          WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0'))  \n"
			+ "          WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADONNCC WHERE PARK_FK =  ISNULL(PHATSINH.SOCHUNGTU, '0'))  \n"
			+ "          WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT ISNULL(SOHOADON,'') FROM ERP_HOADONPHELIEU WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0'))  \n"
			+ "   	  WHEN PHATSINH.LOAICHUNGTU LIKE N'GIẢM/TĂNG GIÁ HÀNG BÁN' THEN  (SELECT SOHOADON FROM ERP_GIAMGIAHANGBAN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)	 \n"
			+ "    	  WHEN PHATSINH.LOAICHUNGTU LIKE N'NHẬN HÀNG' THEN  (SELECT ISNULL(C.SOHOADON,'') FROM ERP_NHANHANG A INNER JOIN ERP_HOADONNCC C ON A.HDNCC_FK = C.PK_SEQ WHERE A.PK_SEQ=PHATSINH.SOCHUNGTU)		 \n"
			+ "     ELSE '' END , '') AS SOHOADON ,  \n"
			+ " 	(SELECT NPP.TEN  \n"
			+ " 	FROM ERP_TAIKHOANKT TK   \n"
			+ " 	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = TK.NPP_FK  AND NPP_FK IN ("+congTy+")  \n"
			+ " 	WHERE TK.PK_SEQ = PHATSINH.TAIKHOAN_FK  ) AS CONGTY,ISNULL(PHATSINH.DIENGIAI,'') AS DIENGIAI  \n"
			+ "  FROM  \n"
			+ " 	(SELECT PK_SEQ,LOAICHUNGTU,TAIKHOAN_FK,TAIKHOANDOIUNG_FK,NO,CO,DOITUONG,MADOITUONG,KHOANMUC,SOCHUNGTU,NGAYCHUNGTU,NGAYGHINHAN,NOIDUNGNHAPXUAT_FK,DIENGIAI \n"
			+ "  FROM ERP_PHATSINHKETOAN  \n"
			+ " 	)PHATSINH \n"
			+ "  INNER JOIN ERP_TAIKHOANKT TAIKHOAN ON PHATSINH.TAIKHOAN_FK = TAIKHOAN.PK_SEQ  \n"
			+ "  INNER JOIN ERP_TAIKHOANKT TAIKHOANDU ON PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOANDU.PK_SEQ  \n"
			+ "  \n"
			+ "  LEFT JOIN ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK  \n"
			+ "  WHERE 1 = 1  \n"
			+ "  AND TAIKHOAN.NPP_FK IN ("+congTy+") \n";
			if(this.tungay.trim().length() >0 )
				query += " AND PHATSINH.NGAYGHINHAN >= '"+this.tungay +"'\n";
			
			if(this.denngay.trim().length() >0 )
				query += " AND PHATSINH.NGAYGHINHAN <= '"+this.denngay +"'\n";
			
			if(this.tkId.length() > 0)
				query = query + "\n AND TAIKHOAN.SOHIEUTAIKHOAN IN ( " +soHieuTaiKhoan+ " ) \n" ;
			if(this.loaiNghiepVu.length() > 0){
				query = query + "\n AND PHATSINH.LOAICHUNGTU like N'" +this.loaiNghiepVu + "'\n" ;
			}
			
			if(this.nhomTaiKhoanId.length() > 0){
				query = query + "\n AND PHATSINH.TAIKHOAN_FK in (select taikhoan_fk from GROUP_TAIKHOAN_NHOM_TK where group_taikhoan_nhom_fk = " +this.nhomTaiKhoanId + ") \n" ;
			}
			
			query+= " )DATA  \n"
			+ " UNION ALL \n"
			+ "  SELECT PK_SEQ,NGAYCHUNGTU,SOCHUNGTU,NOIDUNG,TAIKHOAN,DOIUNG,NO,CO,DOITUONG,SOHOADON,CONGTY,DIENGIAI \n"
			+ "  FROM (  \n"
			+ "  SELECT ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'THU TIỀN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM " + Utility.prefixDMS + "ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \n"
			+ "  		WHEN  PHATSINH.LOAICHUNGTU LIKE N'THANH TOÁN HÓA ĐƠN%' THEN (SELECT PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) FROM " + Utility.prefixDMS + "ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU) \n"
			+ "  		ELSE CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) END , '' ) AS PK_SEQ, \n"
			+ " 			ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT NGAYXUATHD FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU)  \n"
			+ "             WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT NGAYXUATHD FROM " + Utility.prefixDMS + "ERP_HOADONNPP WHERE PK_SEQ=PHATSINH.SOCHUNGTU)  \n"
			+ "             WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT NGAYHOADON FROM " + Utility.prefixDMS + "ERP_HOADONPHELIEU WHERE PK_SEQ=PHATSINH.SOCHUNGTU)  \n"
			+ "             WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT NGAYXUATHD FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ=PHATSINH.SOCHUNGTU)  \n"
			+ "             WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT NGAYHOADON FROM " + Utility.prefixDMS + "ERP_HOADONNCC WHERE PARK_FK =PHATSINH.SOCHUNGTU)  \n"
			+ "             WHEN PHATSINH.LOAICHUNGTU LIKE N'THU TIỀN%' THEN (SELECT NGAYCHUNGTU FROM " + Utility.prefixDMS + "ERP_THUTIEN WHERE PK_SEQ=PHATSINH.SOCHUNGTU)  \n"
			+ "        ELSE PHATSINH.NGAYCHUNGTU END , PHATSINH.NGAYCHUNGTU )  AS NGAYCHUNGTU,   \n"
			+ "     ISNULL(PHATSINH.SOCHUNGTU, '0') AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN.SOHIEUTAIKHOAN AS TAIKHOAN,  \n"
			+ "    TAIKHOANDU.SOHIEUTAIKHOAN AS DOIUNG, ROUND(ISNULL(PHATSINH.NO, 0),0) AS NO, ROUND(ISNULL(PHATSINH.CO, 0),0) AS CO,   \n"
			+ "  ISNULL(	CASE WHEN  DOITUONG = N'NHÀ CUNG CẤP' THEN ( SELECT MA FROM ERP_NHACUNGCAP WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )  \n"
			+ " 	WHEN  DOITUONG = N'KHÁCH HÀNG' THEN ( SELECT MAFAST FROM " + Utility.prefixDMS + "NHAPHANPHOI WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )  \n"
			+ " 	WHEN  DOITUONG = N'NHÂN VIÊN' THEN ( SELECT MA FROM " + Utility.prefixDMS + "ERP_NHANVIEN WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )   \n"
			+ " 	WHEN  DOITUONG = N'NGÂN HÀNG' THEN ( SELECT MA FROM " + Utility.prefixDMS + "ERP_NGANHANG WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )   \n"
			+ " 	WHEN  DOITUONG = N'SẢN PHẨM' THEN ( SELECT MA FROM " + Utility.prefixDMS + "SANPHAM WHERE CAST(PK_SEQ AS VARCHAR(10) ) = PHATSINH.MADOITUONG )   \n"
			+ "  ELSE ( CASE WHEN LEN(DOITUONG) <= 0 THEN KHOANMUC ELSE DOITUONG END ) END ,'') AS DOITUONG,   \n"
			+ "     ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TÀI CHÍNH' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0'))  \n"
			+ "          WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0'))  \n"
			+ "          WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADON WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0'))  \n"
			+ "          WHEN PHATSINH.LOAICHUNGTU LIKE N'DUYỆT HÓA ĐƠN NCC' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADONNCC WHERE PARK_FK =  ISNULL(PHATSINH.SOCHUNGTU, '0'))  \n"
			+ "          WHEN PHATSINH.LOAICHUNGTU LIKE N'HÓA ĐƠN PHẾ LIỆU' THEN (SELECT ISNULL(SOHOADON,'') FROM " + Utility.prefixDMS + "ERP_HOADONPHELIEU WHERE PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0'))  \n"
			+ "    	  WHEN PHATSINH.LOAICHUNGTU LIKE N'NHẬN HÀNG' THEN  ''		 \n"
			+ "     ELSE '' END , '') AS SOHOADON ,  \n"
			+ " 	(SELECT NPP.TEN  \n"
			+ " 	FROM " + Utility.prefixDMS + "ERP_TAIKHOANKT TK   \n"
			+ " 	INNER JOIN " + Utility.prefixDMS + "NHAPHANPHOI NPP ON NPP.PK_SEQ = TK.NPP_FK  AND NPP_FK IN ("+congTy+")  \n"
			+ " 	WHERE TK.PK_SEQ = PHATSINH.TAIKHOAN_FK  ) AS CONGTY,ISNULL(PHATSINH.DIENGIAI,'') AS DIENGIAI  \n"
			+ "  FROM  \n"
			+ " 	(SELECT PK_SEQ,LOAICHUNGTU,TAIKHOAN_FK,TAIKHOANDOIUNG_FK,NO,CO,DOITUONG,MADOITUONG,KHOANMUC,SOCHUNGTU,NGAYCHUNGTU,NGAYGHINHAN,NOIDUNGNHAPXUAT_FK,DIENGIAI \n"
			+ "  FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN  \n"
			+ " 	)PHATSINH \n"
			+ "  INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOAN ON PHATSINH.TAIKHOAN_FK = TAIKHOAN.PK_SEQ  \n"
			+ "  INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TAIKHOANDU ON PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOANDU.PK_SEQ  \n"
			+ "  \n"
			+ "  LEFT JOIN " + Utility.prefixDMS + "ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK  \n"
			+ "  WHERE 1 = 1  \n"
			+ "  AND TAIKHOAN.NPP_FK IN ("+congTy+") \n";
			if(this.tungay.trim().length() >0 )
				query += " AND PHATSINH.NGAYGHINHAN >= '"+this.tungay +"'\n";
			
			if(this.denngay.trim().length() >0 )
				query += " AND PHATSINH.NGAYGHINHAN <= '"+this.denngay +"'\n";
			if(this.tkId.length() > 0)
				query = query + "\n AND TAIKHOAN.SOHIEUTAIKHOAN IN ( " +soHieuTaiKhoan+ " ) \n" ;
			query += " )DATA  \n"
			+ " )FINAL \n"
			+ "  ORDER BY FINAL.CONGTY, FINAL.NGAYCHUNGTU, FINAL.SOCHUNGTU, FINAL.NO DESC, FINAL.CO DESC ";
				
				
			this.timkiem = query;
			System.out.println("init nhat ki tai khoan:\n" + query + "\n------------------------------------------------");
			
			
			
			this.rs = this.db.get(query);
			}catch (Exception ex){
				System.out.println("Lỗi SQL :" + query);
				ex.printStackTrace();
			}
		}
		
	// Lấy Tổng Nợ && Tổng có
		
		/*String sql = "";
		sql = "SELECT SUM(ROUND(ISNULL(NO,0),0))AS TONGNO, SUM(ROUND(ISNULL(CO,0),0)) AS TONGCO  \n"
			+ " FROM  ERP_PHATSINHKETOAN PS \n"
			+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n"
			+ " WHERE 1 = 1  \n";
		if(this.tungay.trim().length() >0 )
			sql += " AND NGAYGHINHAN >= '"+this.tungay +"'\n";
				
		if(this.denngay.trim().length() >0 )
			sql += " AND NGAYGHINHAN <= '"+this.denngay +"'\n";
		if(this.tkId.length() > 0)
			sql = sql + "\n AND SOHIEUTAIKHOAN IN ( " +soHieuTaiKhoan+ " ) \n" ;
		String congTy = this.getErpCongtyId();
		if (congTy.trim().endsWith(",")){
			congTy = congTy.substring(0,congTy.length() -1);
		}
		sql += " AND NPP_FK IN ("+congTy+") \n"
			+ " UNION ALL \n"
			+ " SELECT SUM(ROUND(ISNULL(NO,0),0))AS TONGNO, SUM(ROUND(ISNULL(CO,0),0)) AS TONGCO \n"
			+ " FROM " + Utility.prefixDMS + "ERP_PHATSINHKETOAN PS \n"
			+ " INNER JOIN " + Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n"
			+ " WHERE 1 = 1 \n";
			if(this.tungay.trim().length() >0 )
				sql += " AND NGAYGHINHAN >= '"+this.tungay +"'\n";
					
			if(this.denngay.trim().length() >0 )
				sql += " AND NGAYGHINHAN <= '"+this.denngay +"'\n";
			if(this.tkId.length() > 0)
				sql = sql + "\n AND SOHIEUTAIKHOAN IN ( " +soHieuTaiKhoan+ " ) \n" ;
			sql += " AND NPP_FK IN ("+congTy+") \n";
		
		System.out.println("Câu lấy tổng: \n" + sql + "\n-------------------------------------------------");
		ResultSet rs = db.get(sql);
		try {
			if(rs!= null)
			{					
				while(rs.next())
				{
					this.tongNO = rs.getString("TONGNO");
					this.tongCO = rs.getString("TONGCO");
				}
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	private void initRs() {
		this.getCongTyBaoCao();
		this.tk = 	this.db.get("SELECT PK_SEQ AS TKID, SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN AS TAIKHOAN FROM ERP_TAIKHOANKT WHERE CONGTY_FK = "+this.ctyId+" " +
		"ORDER BY SOHIEUTAIKHOAN");
		
		this.nhomTaiKhoanList.clear();
		String query = "select pk_seq, ma + ' - '  + diengiai as TEN from GROUP_TAIKHOAN_NHOM where trangthai = 1";
		Erp_Item.getListFromQuery(db, query, this.nhomTaiKhoanList);
		
		this.loaiNghiepVuList.clear();
		query = "select distinct LOAICHUNGTU as PK_SEQ, LOAICHUNGTU as TEN from ERP_PHATSINHKETOAN";
		Erp_Item.getListFromQuery(db, query, this.loaiNghiepVuList);
	}
	public void getCongTyBaoCao() {

		try {
			String sql = "SELECT PK_SEQ,TEN FROM NHAPHANPHOI WHERE isKHACHHANG = 0 AND TRANGTHAI = 1";
			this.congtyRs = db.get(sql);
			sql = "SELECT PK_SEQ,TEN FROM NHAPHANPHOI WHERE PK_SEQ =1 AND TRANGTHAI = 1";
			ResultSet rs = db.get(sql);
			if (this.ctyIds != null) {
				if (this.ctyIds.length > 0) {
					this.ErpCongTyId = "";
					for (int i = 0; i < this.ctyIds.length; i++) {
						this.ErpCongTyId = this.ErpCongTyId + this.ctyIds[i] + ",";
					}
				}
			} else {
				if (rs != null) {
					while (rs.next()) {
						this.ErpCongTyId = rs.getString("PK_SEQ");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void DBClose(){
		if(db != null) 
			db.shutDown();
	}

	public String getTimkiem() 
	{
		return timkiem;
	}

	public void setTimkiem(String timkiem) {
		this.timkiem = timkiem;
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
	
	public String getNhomTaiKhoanId() {
		return nhomTaiKhoanId;
	}

	public void setNhomTaiKhoanId(String nhomTaiKhoanId) {
		this.nhomTaiKhoanId = nhomTaiKhoanId;
	}

	public String getLoaiNghiepVu() {
		return loaiNghiepVu;
	}

	public void setLoaiNghiepVu(String loaiNghiepVu) {
		this.loaiNghiepVu = loaiNghiepVu;
	}
	
	public List<Erp_Item> getNhomTaiKhoanList() {
		return nhomTaiKhoanList;
	}

	public void setNhomTaiKhoanList(List<Erp_Item> nhomTaiKhoanList) {
		this.nhomTaiKhoanList = nhomTaiKhoanList;
	}

	public List<Erp_Item> getLoaiNghiepVuList() {
		return loaiNghiepVuList;
	}

	public void setLoaiNghiepVuList(List<Erp_Item> loaiNghiepVuList) {
		this.loaiNghiepVuList = loaiNghiepVuList;
	}
}