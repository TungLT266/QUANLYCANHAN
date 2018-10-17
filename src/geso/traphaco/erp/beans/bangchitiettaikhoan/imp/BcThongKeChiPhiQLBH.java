package geso.traphaco.erp.beans.bangchitiettaikhoan.imp;
import geso.traphaco.erp.beans.bangchitiettaikhoan.*;
import geso.traphaco.center.db.sql.dbutils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;

public class BcThongKeChiPhiQLBH implements IBcThongKeChiPhiQLBH {
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
	
	String ErpCongTyId;
	String view;
	
	String tknhomId;
	
	public BcThongKeChiPhiQLBH() {
		this.userId = "";
		this.ctyId = "";
		this.ctyTen = "";
		this.sohieu = "";
		this.daukyno = "0";
		this.daukyco = "0";
		this.tkId = "100001";
		this.tungay =  "";
		this.denngay =  "";
		this.month = Integer.toString(Integer.parseInt(getDate().substring(5, 7)));
		System.out.println(this.month);
		
		this.year = getDate().substring(0, 4);
		this.tknhomId = "";

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


	public void init(){
		init_0();
		
		// this.ctyId chứa Công ty ID của công ty được login
		// this.ErpcongtyId chứa công ty ID của những công ty được chọn để ra báo cáo, cách nhau bởi dấu ";"
		
		String query = 	"SELECT distinct LEFT(SOHIEUTAIKHOAN,3) SOHIEUTAIKHOAN " +
						"FROM ERP_TAIKHOANKT WHERE CONGTY_FK = "+this.ctyId+" ORDER BY SOHIEUTAIKHOAN";
				
		this.taikhoanNhom = this.db.get(query);
		
		query = "SELECT PK_SEQ AS TKID, SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN AS TAIKHOAN " +
		  		"FROM ERP_TAIKHOANKT WHERE CONGTY_FK = " + this.ctyId ;
		
		if(tknhomId.trim().length() > 0)
		{
			query += " AND ( SOHIEUTAIKHOAN LIKE N'641%'  OR SOHIEUTAIKHOAN LIKE N'642%' )";
		}
		
		this.tk = this.db.get(query);
		
		
				
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
		
		if(this.tkId.length() > 0){
			query = 	"SELECT SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN AS TAIKHOAN " +
						"FROM ERP_TAIKHOANKT WHERE CONGTY_FK = " + this.ctyId + " AND PK_SEQ = " + this.tkId + "";
			System.out.println(query);
			rs = this.db.get(query);
			try{
				rs.next();
				this.sohieu = rs.getString("TAIKHOAN");
				rs.close();
			}catch(java.sql.SQLException e){}
			
		}
		
		if(this.year.length() > 0 & this.month.length() > 0){
			int lastyear = Integer.parseInt(this.year) - 1;
			int lastmonth = 0;
			
			if (Integer.parseInt(this.month) > 1){
				lastmonth = Integer.parseInt(this.month) - 1;
			}else{
				lastmonth = 12;
			}
						

			query = 
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
			"\n    ELSE '' END AS SOHOADON, "+						
			"\n	(SELECT CTY.TEN " +
			"\n	FROM ERP_TAIKHOANKT TK  " + 
			"\n	INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = TK.CONGTY_FK  AND CONGTY_FK IN (" + this.ctyId + ") " +
			"\n	WHERE TK.PK_SEQ = PHATSINH.TAIKHOAN_FK  ) AS CONGTY, ISNULL(PHATSINH.DIENGIAI,'') DIENGIAI, ISNULL(PHATSINH.SOLUONG, 0) SOLUONG, " +
			"\n ISNULL(PHATSINH.MAHANG, '') MAHANG, ISNULL(PHATSINH.TENHANG, '') TENHANG, ISNULL(DONVI, '') DONVI, ISNULL(PHATSINH.VAT, 0) VAT  " +
			"\n FROM ERP_PHATSINHKETOAN PHATSINH " +
			
			"\n INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOAN_FK = TAIKHOAN.pk_seq " +
			"\n INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN_2.pk_seq " +
			"\n LEFT JOIN ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK " +
			"\n WHERE 1 = 1 " +
			"\n AND TAIKHOAN.CONGTY_FK IN (" + this.ctyId + ")  AND TAIKHOAN_2.CONGTY_FK IN (" + this.ctyId + ") "; // this.ErpCongTyId chứa id những công ty chọn trong báo cáo
			
			if(this.tungay.trim().length() >0 )
				query += " AND PHATSINH.NGAYGHINHAN < '"+this.tungay +"'";
					
			
			query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN " +
							"\n	( SELECT PK_SEQ FROM ERP_TAIKHOANKT " +
							"\n	  WHERE ( SOHIEUTAIKHOAN LIKE N'641%' OR SOHIEUTAIKHOAN LIKE N'642%' ) AND CONGTY_FK = '" + this.ctyId + "' " + //this.ctyId chứa id công ty đăng nhập
							"\n ) " ;
			
			query += ")DATA ";
			
			System.out.println("Query DK: "+query);
			this.rs = this.db.get(query);
			try{
				
				if(rs.next()){
					this.daukyco = rs.getString("GIATRICOVND");
					this.daukyno = rs.getString("GIATRINOVND");			
					rs.close();
				}else{
					this.daukyco = "0";
					this.daukyno = "0";								
				}
				
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
				"\n    	  ELSE '' END AS SOHOADON, "+						
				"\n	(SELECT CTY.TEN " +
				"\n	FROM ERP_TAIKHOANKT TK  " + 
				"\n	INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = TK.CONGTY_FK  AND CONGTY_FK IN (" + this.ErpCongTyId + ") " +
				"\n	WHERE TK.PK_SEQ = PHATSINH.TAIKHOAN_FK  ) AS CONGTY, ISNULL(PHATSINH.DIENGIAI,'') DIENGIAI , ISNULL(PHATSINH.SOLUONG, 0) SOLUONG, " +
				"\n ISNULL(PHATSINH.MAHANG, '') MAHANG, ISNULL(PHATSINH.TENHANG, '') TENHANG, ISNULL(DONVI, '') DONVI, ISNULL(PHATSINH.VAT, 0) VAT ,  " +
				"\n ISNULL(PHATSINH.TIENHANG, 0) TIENHANG, ISNULL(PHATSINH.MAHOADON, '') MAHOADON, ISNULL(PHATSINH.MAUHOADON,'') MAUHOADON, " +
				"\n ISNULL(PHATSINH.KYHIEU, '') KYHIEU, ISNULL(PHATSINH.NGAYHOADON, '') NGAYHOADON, ISNULL(PHATSINH.TENNCC, '') TENNCC," +
				"\n ISNULL(PHATSINH.MST, '') MST, ISNULL(PHATSINH.TEN_SANPHAM, '') TEN_SANPHAM, ISNULL(PHATSINH.TEN_BENHVIEN,'') TEN_BENHVIEN," +
				"\n ISNULL(PHATSINH.TEN_TINHTHANH, '') TEN_TINHTHANH, ISNULL(PHATSINH.TEN_DIABAN, '' ) TEN_DIABAN, ISNULL(PHATSINH.TEN_VV, '') TEN_VV," +
				"\n ISNULL(PHATSINH.TEN_PB, '') TEN_PB, ISNULL(PHATSINH.TEN_KBH, '') TEN_KBH, ISNULL(PHATSINH.TEN_DT, '') TEN_DT, " +
				"\n ISNULL(PHATSINH.THUESUAT, 0) THUESUAT, ISNULL(PHATSINH.DIENGIAI_CT, '') DIENGIAI_CT "+
				"\n FROM ERP_PHATSINHKETOAN PHATSINH " +
				
				"\n INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOAN_FK = TAIKHOAN.pk_seq " +
				"\n INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN_2.pk_seq " +
				"\n LEFT JOIN ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK " +
				"\n WHERE 1 = 1 " +
				"\n AND TAIKHOAN.CONGTY_FK IN (" + this.ctyId + ")  AND TAIKHOAN_2.CONGTY_FK IN (" + this.ctyId + ") "; // this.ErpCongTyId chứa id những công ty chọn trong báo cáo
				
				if(this.tungay.trim().length() >0 )
					query += " AND PHATSINH.NGAYGHINHAN >= '"+this.tungay +"'";
				
				if(this.denngay.trim().length() >0 )
					query += " AND PHATSINH.NGAYGHINHAN <= '"+this.denngay +"'";
			
				
				query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN " +
								"\n	( SELECT PK_SEQ FROM ERP_TAIKHOANKT " +
								"\n	  WHERE ( SOHIEUTAIKHOAN LIKE N'641%' OR SOHIEUTAIKHOAN LIKE N'642%' ) AND CONGTY_FK = '" + this.ctyId + "' " + //this.ctyId chứa id công ty đăng nhập
								"\n ) " ;
				
				
				query += ")DATA " +
				 	" ORDER BY DATA.PK_SEQ desc  " ;
			}
			this.rs = this.db.get(query);
			System.out.println("Query TK: "+query);
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
}
