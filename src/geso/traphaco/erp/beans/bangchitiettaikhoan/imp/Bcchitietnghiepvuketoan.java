package geso.traphaco.erp.beans.bangchitiettaikhoan.imp;
import geso.traphaco.erp.beans.bangchitiettaikhoan.*;
import geso.traphaco.center.db.sql.dbutils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;

public class Bcchitietnghiepvuketoan implements IBcchitietnghiepvuketoan {
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
	
	public Bcchitietnghiepvuketoan() {
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
			query += " AND SOHIEUTAIKHOAN LIKE N'"+tknhomId+"%'";
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
						
			if(this.tungay.trim().length() >0 || this.denngay.trim().length() >0)
			{
				query = 				
					 "\n SELECT  CASE WHEN PHATSINH.LOAICHUNGTU like N'Thu tiền%' THEN (select PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) from ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU) "+
					 "\n		 WHEN  PHATSINH.LOAICHUNGTU like N'Thanh toán hóa đơn%' THEN (select PREFIX + '' + CAST(PK_SEQ AS NVARCHAR(50)) from ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU) "+
					 "\n		 ELSE CAST(PHATSINH.PK_SEQ AS NVARCHAR(50)) END AS PK_SEQ, "+
					 "\n		 CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select NGAYXUATHD from ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) "+
				     "\n       	 WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select NGAYXUATHD from ERP_HOADONNPP where PK_SEQ=PHATSINH.SOCHUNGTU) "+
				     "\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select NGAYHOADON from ERP_HoaDonPheLieu where PK_SEQ=PHATSINH.SOCHUNGTU) "+ 
				     "\n         WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select NGAYXUATHD from ERP_HOADON where PK_SEQ=PHATSINH.SOCHUNGTU) "+ 
				     "\n         WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select NGAYHOADON from ERP_HOADONNCC where PARK_FK =PHATSINH.SOCHUNGTU) "+
				     "\n         WHEN PHATSINH.LOAICHUNGTU like N'Giảm/Tăng giá hàng bán' THEN (select NGAYHOADON from erp_giamgiahangban where PK_SEQ=PHATSINH.SOCHUNGTU) "+ 
				     "\n         WHEN PHATSINH.LOAICHUNGTU like N'Thu tiền%' THEN (select NGAYCHUNGTU from ERP_THUTIEN where PK_SEQ=PHATSINH.SOCHUNGTU) "+
					 "\n         ELSE PHATSINH.NGAYCHUNGTU END  AS NGAYCHUNGTU, PHATSINH.NGAYGHINHAN , ISNULL(PHATSINH.mahang,'') MAHANG, ISNULL(PHATSINH.tenhang, '') TENHANG, "+
					 "\n         ISNULL(PHATSINH.SOLO, '') SOLO, ISNULL(PHATSINH.NGAYHETHAN, '') HANDUNG , ISNULL(PHATSINH.VAT,0) TIENVAT, "+
					 "\n 		 ISNULL(PHATSINH.SOCHUNGTU, '0') AS SOCHUNGTU, PHATSINH.LOAICHUNGTU AS NOIDUNG, TAIKHOAN.SOHIEUTAIKHOAN AS TAIKHOAN, "+ 
					 "\n	     TAIKHOAN_2.SOHIEUTAIKHOAN AS DOIUNG, ISNULL(PHATSINH.NO, 0) AS NO, ISNULL(PHATSINH.CO, 0) AS CO,  "+
					 "\n		 case when  DOITUONG = N'Nhà cung cấp' then ( select MA from ERP_NHACUNGCAP where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+
					 "\n		 when  DOITUONG = N'Khách hàng' and ISNULL(PHATSINH.isNPP,0) = 0 then ( select TEN from KHACHHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+
					 "\n		 when  DOITUONG = N'Khách hàng' and ISNULL(PHATSINH.isNPP,0) = 1 then ( select TEN from NHAPHANPHOI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+
					 "\n		 when  DOITUONG = N'Khách hàng' and ISNULL(PHATSINH.isNPP,0) = 2 then ( select TEN from ERP_NHANVIEN where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+
					 "\n		 when  DOITUONG = N'Nhân viên' then ( select TEN from ERP_NHANVIEN where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  "+
					 "\n		 when  DOITUONG = N'Ngân hàng' then ( select TEN from ERP_NGANHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  	"+				
					 "\n		 when  DOITUONG = N'Sản phẩm' then ( select TEN from SANPHAM where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  "+
					 "\n		 when  DOITUONG = N'Chi phí' then ( select TEN from ERP_NHOMCHIPHI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  "+
					 "\n		 when  DOITUONG = N'Tài sản' then ( select TEN from ERP_TAISANCODINH where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  "+
					 "\n		 when  DOITUONG = N'Công cụ dụng cụ' then ( select DIENGIAI from ERP_CONGCUDUNGCU where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+ 
					 "\n		 when  DOITUONG = N'Trung tâm chi phí' then ( select TEN from ERP_TRUNGTAMCHIPHI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  "+
					 "\n		 else ( case when LEN(DOITUONG) <= 0 then KHOANMUC else DOITUONG end ) end as DOITUONG,  "+
					 "\n		 case when  DOITUONG = N'Nhà cung cấp' then ( select MA from ERP_NHACUNGCAP where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+
				     "\n		 when  DOITUONG = N'Khách hàng' and ISNULL(PHATSINH.isNPP,0) = 0 then ( select maFAST from KHACHHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+
					 "\n		 when  DOITUONG = N'Khách hàng' and ISNULL(PHATSINH.isNPP,0) = 1 then ( select maFAST from NHAPHANPHOI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+
					 "\n		 when  DOITUONG = N'Khách hàng' and ISNULL(PHATSINH.isNPP,0) = 2 then ( select MA from ERP_NHANVIEN where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+
					 "\n		 when  DOITUONG = N'Nhân viên' then ( select MA from ERP_NHANVIEN where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG )  "+
					 "\n		 when  DOITUONG = N'Ngân hàng' then ( select MA from ERP_NGANHANG where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+				
					 "\n		 when  DOITUONG = N'Sản phẩm' then ( select MA from SANPHAM where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+
					 "\n		 when  DOITUONG = N'Chi phí' then ( select MA from ERP_NHOMCHIPHI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+  
					 "\n		 when  DOITUONG = N'Tài sản' then ( select MA from ERP_TAISANCODINH where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+ 
					 "\n		 when  DOITUONG = N'Công cụ dụng cụ' then ( select MA from ERP_CONGCUDUNGCU where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+  
					 "\n		 when  DOITUONG = N'Trung tâm chi phí' then ( select MA from ERP_TRUNGTAMCHIPHI where cast(PK_SEQ as varchar(10) ) = PHATSINH.MADOITUONG ) "+  
					 "\n		 else ( case when LEN(DOITUONG) <= 0 then KHOANMUC else DOITUONG end ) end as MADOITUONG,  "+			 
					 "\n 		 ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select isnull(SOHOADON,'') from erp_hoadonnpp where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
			         "\n		 WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng NCC' THEN (select isnull(SOHOADON,'') from erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
			         "\n		 WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn trả hàng khách hàng' THEN (select isnull(SOHOADON,'') from erp_hoadon where PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+ 
			         "\n		 WHEN PHATSINH.LOAICHUNGTU like N'Duyệt hóa đơn NCC' THEN (select isnull(SOHOADON,'') from erp_hoadonncc where park_fk =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
			         "\n		 WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn phế liệu' THEN (select isnull(SOHOADON,'') from ERP_HoaDonPheLieu where pk_seq =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
			  	  	 "\n		 WHEN PHATSINH.LOAICHUNGTU like N'Giảm/Tăng giá hàng bán' THEN  (select sohoadon from erp_giamgiahangban where PK_SEQ=PHATSINH.SOCHUNGTU)	"+
			   	  	 "\n 		 WHEN PHATSINH.LOAICHUNGTU like N'Nhận hàng' THEN  (select isnull(c.SOHOADON,'') from ERP_NHANHANG a inner join ERP_HOADONNCC c on a.hdNCC_fk = c.pk_seq where a.PK_SEQ=PHATSINH.SOCHUNGTU)	"+	 
					 "\n 		 ELSE '' END , '') AS SOHOADON , KHO.TEN KHO , ISNULL(KHONHAN.TEN,'')  KHONHAN,	"+
					 "\n		(SELECT CTY.TEN "+
					 "\n		FROM ERP_TAIKHOANKT TK "+   
					 "\n		INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = TK.CONGTY_FK  AND CONGTY_FK IN (" + this.ctyId + ") " +
					 "\n		WHERE TK.PK_SEQ = PHATSINH.TAIKHOAN_FK  ) AS CONGTY, PHATSINH.KHOANMUC, PHATSINH.SOLUONG, ISNULL(PHATSINH.VAT,0) TIENVAT, KBH.TEN KBH , "+
					 "\n 		ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select isnull(c.diengiai,'') from erp_hoadonnpp a inner join khachhang b on a.khachhang_fk = b.pk_seq inner join diaban c on b.diaban = c.pk_seq where a.PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					 "\n 		ELSE '' END , '') AS DIABAN, "+
					 "\n 		ISNULL( CASE WHEN PHATSINH.LOAICHUNGTU like N'Hóa đơn tài chính' THEN (select isnull(c.diengiai,'') from erp_hoadonnpp a inner join khachhang b on a.khachhang_fk = b.pk_seq inner join tinhthanh c on b.tinhthanh_fk = c.pk_seq where a.PK_SEQ =  ISNULL(PHATSINH.SOCHUNGTU, '0')) "+
					 "\n 		ELSE '' END , '') AS TINHTHANH, ISNULL(PHATSINH.TIENHANG,0) TIENHANG "+
					 "\n		FROM ERP_PHATSINHKETOAN PHATSINH "+
					 "\n		INNER JOIN ERP_TAIKHOANKT TAIKHOAN on PHATSINH.TAIKHOAN_FK = TAIKHOAN.pk_seq "+
					 "\n		INNER JOIN ERP_TAIKHOANKT TAIKHOAN_2 on PHATSINH.TAIKHOANDOIUNG_FK = TAIKHOAN_2.pk_seq "+
					 "\n		LEFT JOIN ERP_NOIDUNGNHAP NOIDUNG ON NOIDUNG.PK_SEQ = PHATSINH.NOIDUNGNHAPXUAT_FK "+
					 "\n		LEFT JOIN KHO ON PHATSINH.KHO_FK = KHO.PK_SEQ "+
					 "\n		LEFT JOIN KHO KHONHAN ON PHATSINH.KHONHAN_FK = KHONHAN.PK_SEQ "+
					 "\n		LEFT JOIN KENHBANHANG KBH ON PHATSINH.KBH_FK = KBH.PK_SEQ "+	
					 "\n		WHERE 1 = 1 " +
					 "\n 		AND TAIKHOAN.CONGTY_FK IN (" + this.ctyId + ")  AND TAIKHOAN_2.CONGTY_FK IN (" + this.ctyId + ") ";
											
	
				if(this.tungay.trim().length() >0 )
					query += " AND PHATSINH.NGAYGHINHAN >= '"+this.tungay +"'";
				
				if(this.denngay.trim().length() >0 )
					query += " AND PHATSINH.NGAYGHINHAN <= '"+this.denngay +"'";
							
						
				if(this.tkId.length() > 0){
					query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN " +
									"\n (SELECT PK_SEQ FROM ERP_TAIKHOANKT " +
									"\n WHERE SOHIEUTAIKHOAN IN " +
									"\n	(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT " +
									"\n	 WHERE PK_SEQ = '" + this.tkId + "' AND CONGTY_FK = '" + this.ctyId + "') " + //this.ctyId chứa id công ty đăng nhập
									"\n ) " ;
				}
				
				if(tknhomId.trim().length() > 0)
				{
					query = query + "\n AND PHATSINH.TAIKHOANDOIUNG_FK IN " +
									"\n (SELECT PK_SEQ FROM ERP_TAIKHOANKT " +
									"\n WHERE SOHIEUTAIKHOAN IN " +
									"\n	(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT " +
									"\n	 WHERE SOHIEUTAIKHOAN LIKE N'"+tknhomId+"%' AND CONGTY_FK = '" + this.ctyId + "') " + //this.ctyId chứa id công ty đăng nhập
									"\n ) " ;
				}
				
				query += "ORDER BY PHATSINH.PK_SEQ desc ";

				/*query +=
				 "\n UNION ALL "+
				 
				// HÓA ĐƠN TÀI CHÍNH
				 "\n SELECT A.PK_SEQ, A.NGAYXUATHD NGAYCHUNGTU, A.NGAYGHINHAN, C.MA_FAST MAHANG, isnull(C.tenviettat, '') TENHANG , ISNULL(B.SOLO, '') SOLO, "+
				 "\n ISNULL(B.NGAYHETHAN, '') HANDUNG, B.TIENTHUE TIENVAT, A.PK_SEQ SOCHUNGTU, N'Hóa đơn tài chính' NOIDUNG, '13100000' TAIKHOAN, '51110000' DOIUNG, "+
				 "\n B.THANHTIEN NO, 0 CO,ISNULL(ISNULL(KH.MAFAST, NPP.MAFAST), NV.MA) DOITUONG ,ISNULL(ISNULL(KH.MAFAST, NPP.MAFAST), NV.MA) MADOITUONG, A.SOHOADON, KHO.DIENGIAI, "+
				 "\n '' KHONHAN, CTY.TEN, '' KHOANMUC, B.SOLUONG, B.TIENTHUE TIENVAT,  KBH.DIENGIAI KBH"+
				 "\n FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_SP_CHITIET B ON A.PK_SEQ = B.hoadon_fk "+
				 "\n LEFT JOIN KHACHHANG KH ON A.KHACHHANG_FK = KH.PK_SEQ "+
				 "\n LEFT JOIN NHAPHANPHOI NPP ON A.NPP_DAT_FK = NPP.PK_SEQ "+
				 "\n LEFT JOIN ERP_NHANVIEN NV ON A.NHANVIEN_FK = NV.PK_SEQ "+
				 "\n INNER JOIN KHO ON B.Kho_FK = KHO.PK_SEQ "+
				 "\n INNER JOIN SANPHAM C ON B.MA = C.MA "+
				 "\n INNER JOIN ERP_CONGTY CTY ON A.congty_fk = CTY.PK_SEQ "+
				 "\n INNER JOIN KENHBANHANG KBH ON A.KBH_FK = KBH.PK_SEQ "+
				 "\n WHERE A.TRANGTHAI IN (2,4) ";
				 
				if(this.tungay.trim().length() >0 )
					query += " AND A.NGAYGHINHAN >= '"+this.tungay +"'";
				
				if(this.denngay.trim().length() >0 )
					query += " AND A.NGAYGHINHAN <= '"+this.denngay +"'";*/
							
				
				this.rs = this.db.get(query);
				
			}
			
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
