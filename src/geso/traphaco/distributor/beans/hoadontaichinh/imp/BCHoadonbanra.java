package geso.traphaco.distributor.beans.hoadontaichinh.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.distributor.beans.hoadontaichinh.IBCHoadonbanra;
import geso.traphaco.distributor.db.sql.dbutils;

public class BCHoadonbanra implements IBCHoadonbanra
{ 
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String ptVAT;
	String query;
	String sohoadontu;
	String sohoadonden;
	String vung;
	String khuvuc;
	String npp;
	
	String nppTen;
	String msg;
	String activeTab;
	
	ResultSet nppRs;
	ResultSet hoadonRs;
	ResultSet hoadon10PtRs;
	ResultSet EtcRs;
	ResultSet EtcHoRs;
	
	ResultSet OTCCN;
	ResultSet ETCDT;
	
	ResultSet rsHM;

	





	String search_ETC_total;	
	String search_ETC_HO_total;
  String search_OTC_total;
  String search_KM_total;
  String search_HDKHAC_total; 
  String search_OTCCN_total;
  String search_ETCDT_total;
  String search_HM_total;
  
  
  public String getSearch_HM_total() {
	return search_HM_total;
}

public void setSearch_HM_total(String search_HM_total) {
	this.search_HM_total = search_HM_total;
}

public ResultSet getRsHM() {
		return rsHM;
	}

	public void setRsHM(ResultSet rsHM) {
		this.rsHM = rsHM;
	}
  
  public String getSearch_ETC_total() {
		return search_ETC_total;
	}

	public void setSearch_ETC_total(String search_ETC_total) {
		this.search_ETC_total = search_ETC_total;
	}

	public String getSearch_ETC_HO_total() {
		return search_ETC_HO_total;
	}

	public void setSearch_ETC_HO_total(String search_ETC_HO_total) {
		this.search_ETC_HO_total = search_ETC_HO_total;
	}

	public String getSearch_OTC_total() {
		return search_OTC_total;
	}

	public void setSearch_OTC_total(String search_OTC_total) {
		this.search_OTC_total = search_OTC_total;
	}

	public String getSearch_KM_total() {
		return search_KM_total;
	}

	public void setSearch_KM_total(String search_KM_total) {
		this.search_KM_total = search_KM_total;
	}

	public String getSearch_HDKHAC_total() {
		return search_HDKHAC_total;
	}

	public void setSearch_HDKHAC_total(String search_HDKHAC_total) {
		this.search_HDKHAC_total = search_HDKHAC_total;
	}

	public String getSearch_OTCCN_total() {
		return search_OTCCN_total;
	}

	public void setSearch_OTCCN_total(String search_OTCCN_total) {
		this.search_OTCCN_total = search_OTCCN_total;
	}

	public String getSearch_ETCDT_total() {
		return search_ETCDT_total;
	}

	public void setSearch_ETCDT_total(String search_ETCDT_total) {
		this.search_ETCDT_total = search_ETCDT_total;
	}

  
	


	ResultSet OtcRs;
	ResultSet kmRs;
	ResultSet khuvucRs;
	ResultSet HDKhacRs;

	String khTen;
	ResultSet khRs;
	String nppId;
	
	String nvbhId;
	ResultSet nvbhRs;
	String maFAST;
	
	String sohoadon;
	String loaidonhang;
	String view;
	String cn;
	String mauhoadon;
	public String getMauhoadon() {
		return mauhoadon;
	}

	public void setMauhoadon(String mauhoadon) {
		this.mauhoadon = mauhoadon;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}


	dbutils db;
	geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
	
	public BCHoadonbanra()
	{
		//this.tungay = this.getDateTime().split("-")[0] + "-" + this.getDateTime().split("-")[1] + "-01";
		//this.denngay = this.getDateTime();		
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "0";
	    this.khTen= "";
	    this.nppId="";
	    this.sohoadon="";
	    this.ptVAT = "";
	    this.view="";
	    
	    this.activeTab="0";
	    
	    this.nvbhId = "";
	    this.maFAST = "";
	    
	    this.sohoadontu = "";
	    this.sohoadonden = "";

	    this.vung = "";
	    this.khuvuc="";
	    this.npp="";
	    this.muclay="0";
	    this.tdvId="";
	    this.khoId="";
	    
	    this.kh="1";
	    this.cndt="1";
	    this.cn="0";
		this.db = new dbutils();
	}

	public ResultSet getETCDT() {
		return ETCDT;
	}

	public void setETCDT(ResultSet eTCDT) {
		ETCDT = eTCDT;
	}

	public ResultSet getOTCCN() {
		return OTCCN;
	}

	public void setOTCCN(ResultSet oTCCN) {
		OTCCN = oTCCN;
	}
	
	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
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
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
	}
	
	public void init()
	{
		this.getNppInfo();
		
		String query_npp="select PK_SEQ, TEN from NHAPHANPHOI  "+
				" where  pk_seq in "+ util.quyen_npp(userId)+ "" ;
		
		this.nppRs= db.get(query_npp);
	
		
		String query = "";
       
		// TRƯỜNG HỢP KHÔNG PHẢI LÀ HÓA ĐƠN KHÁC
			if(this.tungay.trim().length() > 0 || this.denngay.trim().length() > 0  )
			{
				String condition = "";
				String conditionETC = "";
				
				String conditionHDKhac = "";
				String conditionHDKhac_CongNoPhaiThu = "";
				
				if(this.tungay.trim().length() > 0)
				{
					condition += " and a.ngayxuatHD >= '" + this.tungay + "' ";
					conditionETC += " and a.ngayxuatHD >= '" + this.tungay + "' ";
					conditionHDKhac += " and b.ngayhoadon >= '"+this.tungay+"'";
					conditionHDKhac_CongNoPhaiThu += " and HD.ngayhoadon >= '"+this.tungay+"'";
				}
				if(this.denngay.trim().length() > 0)
				{
					condition += " and a.ngayxuatHD <= '" + this.denngay + "' ";
					conditionETC += " and a.ngayxuatHD <= '" + this.denngay + "' ";
					conditionHDKhac += " and b.ngayhoadon <= '"+this.denngay+"'";
					conditionHDKhac_CongNoPhaiThu += " and HD.ngayhoadon <= '"+this.denngay+"'";
				}
				
				if(this.sohoadontu.trim().length() > 0)
				{
					condition += " and cast(a.sohoadon as numeric(18, 0)) >= '" + Integer.parseInt(this.sohoadontu) + "' ";
					conditionETC += " and cast(a.sohoadon as numeric(18, 0)) >= '" + Integer.parseInt(this.sohoadontu) + "' ";
					conditionHDKhac  += " and cast(b.sohoadon as numeric(18, 0)) >= '" + Integer.parseInt(this.sohoadontu) + "' ";
					conditionHDKhac_CongNoPhaiThu += " and cast(HD.sohoadon as numeric(18, 0)) >= '" + Integer.parseInt(this.sohoadontu) + "' ";
				}
				
				if(this.sohoadonden.trim().length() > 0)
				{
					condition += " and cast(a.sohoadon as numeric(18, 0)) <= '" + Integer.parseInt(this.sohoadonden) + "' ";
					conditionETC += " and cast(a.sohoadon as numeric(18, 0)) <= '" + Integer.parseInt(this.sohoadonden) + "' ";
					conditionHDKhac  += " and cast(b.sohoadon as numeric(18, 0)) <= '" + Integer.parseInt(this.sohoadonden) + "' ";
					conditionHDKhac_CongNoPhaiThu += " and cast(HD.sohoadon as numeric(18, 0)) <= '" + Integer.parseInt(this.sohoadonden) + "' ";
				}
				if(this.mauhoadon.trim().length() > 0)
				{
					condition += " and a.mauhoadon="+this.mauhoadon+" ";
					conditionETC += " and a.mauhoadon="+this.mauhoadon+" ";
				}
				
				System.out.println("---LOAI DON HANG: " + this.loaidonhang);
	
				if(this.loaidonhang.trim().length() > 0)
				{
					if(this.loaidonhang.equals("0"))//HOA DON BAN
					{
						condition += " and a.trangthai not in ( 1 , 3, 5 ) and isnull(a.loaihoadon, '0') = '0' ";
						conditionETC += " and a.trangthai not in ( 1 , 3, 5 ) and isnull(a.loaihoadon, '0') = '0'  ";
					}
					else if(this.loaidonhang.equals("1"))//KHUYEN MAI
					{
						condition += " and a.trangthai not in (1, 3, 5 ) and isnull(a.loaihoadon, '0') = '1' ";
						conditionETC += " and a.trangthai not in (1, 3, 5 ) and isnull(a.loaihoadon, '0') = '1'  "; //ETC KHONG CO KM
					}
					else if (this.loaidonhang.equals("3"))//HOADON KHAC
					{
						conditionHDKhac += " and b.trangthai = 1 ";
					}
					else if (this.loaidonhang.equals("4"))//Quản lý CN phải thu > HOADON KHAC 
					{
						conditionHDKhac_CongNoPhaiThu += " and HD.trangthai = 1 ";
					}
					else //TRƯỜNG HỢP HÓA ĐƠN HỦY
					{
						condition += " and a.trangthai in ( 3, 5 ) ";
						conditionETC += " and a.trangthai in ( 3, 5 ) ";
						conditionHDKhac  += " and b.trangthai in (2) ";
						conditionHDKhac_CongNoPhaiThu  += " and HD.trangthai in (2) ";
					}
				}
				else //LẤY TẤT CẢ CÁC TRƯỜNG HỢP
				{
					condition += " and a.trangthai not in ( 1, 3, 5 ) ";
					conditionETC += " and a.trangthai not in ( 1, 3, 5 ) ";
					conditionHDKhac  += " and b.trangthai not in (2) ";
				}
				
				if(this.nppId.trim().length() > 0)
				{
					condition += " and a.npp_fk = '" + this.nppId + "' ";
					conditionETC += " and a.npp_fk = '" + this.nppId + "' ";
					conditionHDKhac += " and b.npp_fk = '"+this.nppId+"'";
					conditionHDKhac_CongNoPhaiThu += " and (HD.NPP_FK = '"+this.nppId+"')";
				}
				
				
				/*String 
				ck5=
				"			select b.hoadon_fk, sum(chietkhau) as chietkhau  " +
				"			from DONHANG_SANPHAM  a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk " +
				"			where thueVAT = '5' group by b.hoadon_fk  " +
				"		union  " +
				"			select b.hoadon_fk, sum( a.thanhtoan / ( 1 + ptTHUE / 100 )  ) as chietkhau " +
				"			from DUYETTRAKHUYENMAI_DONHANG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk    " +
				"			where ptthue = '5' group by b.hoadon_fk  " +
				"		union  " +
				"			select b.hoadon_fk, sum(a.chietkhau) as chietkhau " +
				"			from DONHANG_CHIETKHAUBOSUNG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk " +
				"			where chietkhau != 0 and ptVAT = '5' " + 
				"			group by b.hoadon_fk " ;
				
				if(!this.tungay.contains("2014-07"))
				{*/
				//round(sum( chietkhau), 0 )
					String	ck5=
					" select b.hoadon_fk, sum(ROUND( b.tienbvat,0))  as chietkhau, sum( round(b.tienvat,0)  ) as tienVAT "+   
					"  from HOADON_CHIETKHAU b  "+ 
					"  where thueVAT='5' and b.hienthi = '1' "+
					"  group by hoadon_fk  ";
				//}
				
				
			/*String	
			  ck10=
						"			select b.hoadon_fk, sum(chietkhau) as chietkhau  " +
						"			from DONHANG_SANPHAM  a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk " +
						"			where thueVAT = '10' group by b.hoadon_fk  " +
						"		union  " +
						"			select b.hoadon_fk, sum( a.thanhtoan / ( 1 + ptTHUE / 100 )  ) as chietkhau " +
						"			from DUYETTRAKHUYENMAI_DONHANG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk    " +
						"			where ptthue = '10' group by b.hoadon_fk  " +
						"		union  " +
						"			select b.hoadon_fk, sum(a.chietkhau) as chietkhau " +
						"			from DONHANG_CHIETKHAUBOSUNG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk " +
						"			where chietkhau != 0 and ptVAT = '10' " + 
						"			group by b.hoadon_fk " ;
			
			if(!this.tungay.contains("2014-07"))
			{*/
				String ck10=
				" select b.hoadon_fk, round(sum( b.tienbvat), 0 )  as chietkhau, sum( round(b.tienvat, 0 ) ) as tienVAT  "+   
				"  from HOADON_CHIETKHAU b  "+ 
				"  where thueVAT='10' and b.hienthi = '1' "+
				"  group by hoadon_fk  ";
			//}
				
				
				System.out.println("---CONDITION::::: "  + condition );
				System.out.println("---CONDITION ETC::::: "  + conditionETC );
				
				System.out.println("---CONDITION HOADONKHAC::::: "  + conditionHDKhac );
				
				if(this.loaidonhang.equals("3")){ //HOA DON KHAC
					
					query = " SELECT a.pk_seq, b.sohoadon, b.kyhieuhoadon AS KYHIEU, b.trangthai, b.ngayhoadon ngayxuatHD, c.maFAST ,c.TEN khTen, c.MASOTHUE, a.diengiai spTEN, (a.dongia*a.soluong) tongtienBVAT, a.vat tienVat \n"+
					 		" from erp_hoadonphelieu_sanpham a INNER JOIN ERP_HoaDonPheLieu b ON a.hoadonphelieu_fk = b.pk_seq \n"+
					 		"      INNER JOIN KHACHHANG c on b.khachhang_fk = c.PK_SEQ \n"+
					 		" WHERE b.trangthai = 1 and  a.Thuevat = 5 " +
					 		" ORDER BY cast(b.sohoadon as numeric(18, 0)) asc \n";
					
					System.out.println("---INIT HOA DON KHAC 5%: " + query);
					this.hoadonRs = db.get(query);
					
					query = " SELECT a.pk_seq, b.sohoadon, b.kyhieuhoadon AS KYHIEU, b.trangthai, b.ngayhoadon ngayxuatHD, c.maFAST ,c.TEN khTen, c.MASOTHUE, a.diengiai spTEN, (a.dongia*a.soluong) tongtienBVAT, a.vat tienVat \n"+
			 		" from erp_hoadonphelieu_sanpham a INNER JOIN ERP_HoaDonPheLieu b ON a.hoadonphelieu_fk = b.pk_seq \n"+
			 		"      INNER JOIN KHACHHANG c on b.khachhang_fk = c.PK_SEQ \n"+
			 		" WHERE b.trangthai = 1 and  a.Thuevat = 10 " +
			 		" ORDER BY cast(b.sohoadon as numeric(18, 0)) asc \n";
			
					System.out.println("---INIT HOA DON KHAC 10%: " + query);
					
					this.hoadon10PtRs = db.get(query);
					
				}else if(this.loaidonhang.equals("4")){ //HOA DON KHAC
					
					query = "		--BEGIN HOA DON KHAC DMS \n" + 
							"		SELECT 4 AS LOAI, HD.PK_SEQ, ISNULL(HD.SOHOADON,'') SOHOADON, isnull(HD.KYHIEUHOADON, '') KYHIEU, HD.TRANGTHAI,   \r\n" + 
							"						ISNULL(HD.NGAYHOADON,'') AS NGAYXUATHD,     \r\n" + 
							"				                  ISNULL(NPP.MaFAST, KH.MaFAST) AS MAFAST, \r\n" + 
							"								  ISNULL(NPP.TEN,KH.TEN) AS khTEN, \r\n" + 
							"								  LTRIM(ISNULL(NPP.MASOTHUE,KH.MASOTHUE)) MASOTHUE,     \r\n" + 
							"								   BVAT AS TONGTIENBVAT,     \r\n" + 
							"								   BVAT*VAT/100 AS TIENVAT,     \r\n" + 
							"								  ( select TEN from ERP_SANPHAM where pk_seq = ( select top(1) sanpham_fk from ERP_HOADONKHAC_SANPHAM where HOADONKHAC_FK = HD.pk_seq and VAT = '5'  ) ) as spTEN    \n" + 
							" 		FROM ERP_HOADONKHAC HD     \n" + 
							" 		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK       \n" + 
							" 		LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_DAT_FK   \n" +
							" 		WHERE VAT = '5' "+ conditionHDKhac_CongNoPhaiThu +" \n" + 
							"		--END HOA DON KHAC DMS \n";
					
					System.out.println("---INIT HOA DON KHAC 5%: " + query);
					this.hoadonRs = db.get(query);
					
					query ="		--BEGIN HOA DON KHAC DMS \n" + 
							"		SELECT 4 AS LOAI, HD.PK_SEQ, ISNULL(HD.SOHOADON,'') SOHOADON, isnull(HD.KYHIEUHOADON, '') KYHIEU, HD.TRANGTHAI,   \r\n" + 
							"						ISNULL(HD.NGAYHOADON,'') AS NGAYXUATHD,     \r\n" + 
							"				                  ISNULL(NPP.MaFAST, KH.MaFAST) AS MAFAST, \r\n" + 
							"								  ISNULL(NPP.TEN,KH.TEN) AS khTEN, \r\n" + 
							"								  LTRIM(ISNULL(NPP.MASOTHUE,KH.MASOTHUE)) MASOTHUE,     \r\n" + 
							"								   BVAT AS TONGTIENBVAT,     \r\n" + 
							"								   BVAT*VAT/100 AS TIENVAT,     \r\n" + 
							"								  ( select TEN from ERP_SANPHAM where pk_seq = ( select top(1) sanpham_fk from ERP_HOADONKHAC_SANPHAM where HOADONKHAC_FK = HD.pk_seq and VAT = '10'  ) ) as spTEN    \n" + 
							" 		FROM ERP_HOADONKHAC HD     \n" + 
							" 		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK       \n" + 
							" 		LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_DAT_FK   \n" +
							" 		WHERE VAT = '10' "+ conditionHDKhac_CongNoPhaiThu +" \n" + 
							"		--END HOA DON KHAC DMS \n";
			
					System.out.println("---INIT HOA DON KHAC 10%: " + query);
					
					this.hoadon10PtRs = db.get(query);
					
				}
				else{						
					
					query = 
					"SELECT * \n" +
					"FROM ( \n" +
							"select  HD5.PK_SEQ, HD5.sohoadon, HD5.kyhieu, HD5.trangthai, HD5.ngayxuatHD, HD5.MAFAST, HD5.khTEN , HD5.masothue,  \n" +
							"		 HD5.spTEN,  \n" +
							"		 HD5.tongtienBVAT - isnull( CK.chietkhau, 0) as tongtienBVAT,  \n" +
							"		 HD5.tienVAT - isnull( CK.tienVAT, 0) as tienVAT  \n" +
							"from  \n" +
							"(  \n" +
							"	select 1 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN as khTEN, KH.masothue,    \n" +
							"		 sum( round ( spct.soluong * c.dongia, 0 ) ) as tongtienBVAT, \n" +
							"		 sum( round ( round ( spct.soluong * c.dongia, 0 ) * 0.05, 0 ) ) as tienVAT,  	\n" +
							"		( select TEN from ERP_SANPHAM where pk_seq = ( select top(1) sanpham_fk from ERP_HOADON_SP where HOADON_FK = a.pk_seq and VAT = '5'  ) ) as spTEN  \n" +
							"	from ERP_HOADON a      \n" +
							"		inner join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ    \n" +
							"		inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk    \n" +
							"		inner join ERP_SANPHAM sp on sp.pk_seq = c.sanpham_fk    \n" +
							"		inner join (select hoadon_fk,sum(SOLUONG) as soluong,dongia,SOLO,NGAYHETHAN,isnhapkhau,THUEVAT,MA from ERP_HOADON_SP_CHITIET "+
							"			group by dongia,SOLO,NGAYHETHAN ,hoadon_fk,isnhapkhau,THUEVAT,MA ) as spct on spct.hoadon_fk=a.pk_seq and spct.MA=sp.Ma " +
							"	where   isnull(a.loaihoadon,0)=0 and  c.VAT = '5'  \n" + condition +
							"	group by a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN, KH.masothue  \n" +
						"UNION ALL		 \n" +
							"	select 2 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN as khTEN, KH.masothue,   \n" +
							"		 sum( round ( c.soluong * c.dongia, 0 ) * (case when f.donquatang=1 or f.DONHANGKHAC=1 Then 1 else 0 end  ) ) as tongtienBVAT, \n" +
							"		  sum( round ( round ( c.soluong * c.dongia, 0 ) * 0.05, 0 ) *(case when f.donquatang=1 or f.DONHANGKHAC=1 Then 1 else 0 end  ) ) as tienVAT, 	\n" +
							"		( select TEN from ERP_SANPHAM where PK_SEQ = ( select top(1) sanpham_fk from HOADON_CTKM_TRAKM where HOADON_FK = a.pk_seq and VAT = '5'  ) ) as spTEN        \n" +
							"	from ERP_HOADON a      \n" +
							"		inner join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ     \n" +
							"		inner join HOADON_CTKM_TRAKM c on a.pk_seq = c.hoadon_fk    \n" +
							" inner join HOADON_DDH d on d.HOADON_FK=a.PK_SEQ  \n" +
							" inner join DONHANG f on f.PK_SEQ=d.DDH_FK \n" +
							"	where    isnull(a.loaihoadon,0)=1 and c.VAT = '5' and a.SOHOADON <> 'NA'  \n" + condition +
							"	group by a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN, KH.masothue \n" +
						"UNION ALL \n" +
							"select loai, PK_SEQ, sohoadon, kyhieu, trangthai, ngayxuatHD, MAFAST, khTEN, masothue,   \n" +
							//"		round( sum(soluong) * ( sum( soluong * dongia ) / sum(soluong) )  - SUM(CK) , 0 )  as tongtienBVAT,  \n" +
							"		sum( round ( soluong * dongia - ck, 0 ) ) as tongtienBVAT, 	" +
							//"		round( round( sum(soluong) * ( sum( soluong * dongia ) / sum(soluong) )  - SUM(CK) , 0 ) * 0.05, 0 )  end as tienVAT,  \n" +
							"		sum( case when tienthue=0 then round( round(soluong * DonGia - CK ,0) * 0.05, 0 ) else tienthue end )   as tienVAT,  \n" +
							"		 spTEN \n" +
							"from \n" +
							"( \n" +
							"	select 3 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai,a.NGAYXUATHD,case when a.KHACHHANG_FK = null then KH.MAFAST else npp.MaFAST end MAFAST, a.TENKHACHHANG khTEN, a.MASOTHUE, ISNULL(b.tienthue,0) TIENTHUE,   \n" +
							"			case     when c.donvitinh = e.donvi then b.soluong     \n" +
							"							  else b.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  \n" +
							"			case     when c.donvitinh = e.donvi then c.dongia     \n" +
							"						  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia,     \n" +
							"		( select TEN from ERP_SANPHAM where pk_seq = ( select top(1) sanpham_fk from ERP_HOADONNPP_SP where HOADON_FK = a.pk_seq and VAT = '5'  ) ) as spTEN, isnull(c.ChietKhau,0) as CK     \n" +
							"	from ERP_HOADONNPP a       \n" +
							"		left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ     \n" +
							"       left join NHAPHANPHOI npp on NPP.PK_SEq = a.NPP_DAT_FK \n "+
							"		inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  \n" +
							"		inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq   \n" +
							"\n inner join  (     "+
							"\n			select b.hoadon_fk,b.MA,solo,ngayhethan,SUM(soluong) as soluong,kho_fk,sum(isnull(b.TIENTHUE,0)) tienthue    "+  
							"\n			from ERP_HOADONNPP_SP_CHITIET b      "+
							"\n			group by hoadon_fk,b.MA,solo,ngayhethan,kho_fk) b on a.pk_seq = b.hoadon_fk	 and b.MA=d.MA "+
							
							"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq      \n" +
							"	where 1=1  and c.SOLUONG > 0 and c.VAT = '5'     \n" + conditionETC +
							
						
							") \n" +
							"HD group by loai, PK_SEQ, sohoadon, kyhieu, trangthai, ngayxuatHD, MAFAST, khTEN, masothue, spTEN  \n" +
							"UNION ALL  \n" + 
							"		--BEGIN HOA DON KHAC DMS \n" + 
							"		SELECT 4 AS LOAI, HD.PK_SEQ, ISNULL(HD.SOHOADON,'') SOHOADON, isnull(HD.KYHIEUHOADON, '') KYHIEU, HD.TRANGTHAI,   \r\n" + 
							"						ISNULL(HD.NGAYHOADON,'') AS NGAYXUATHD,     \r\n" + 
							"				                  ISNULL(NPP.MaFAST, KH.MaFAST) AS MAFAST, \r\n" + 
							"								  ISNULL(NPP.TEN,KH.TEN) AS khTEN, \r\n" + 
							"								  LTRIM(ISNULL(NPP.MASOTHUE,KH.MASOTHUE)) MASOTHUE,     \r\n" + 
							"								   BVAT AS TONGTIENBVAT,     \r\n" + 
							"								   BVAT*VAT/100 AS VAT,     \r\n" + 
							"								  ( select TEN from ERP_SANPHAM where pk_seq = ( select top(1) sanpham_fk from ERP_HOADONKHAC_SANPHAM where HOADONKHAC_FK = HD.pk_seq and VAT = '5'  ) ) as spTEN    \n" + 
							" 		FROM ERP_HOADONKHAC HD     \n" + 
							" 		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK       \n" + 
							" 		LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_DAT_FK   \n" +
							" 		WHERE VAT = '5' "+ conditionHDKhac_CongNoPhaiThu +" \n" + 
							"		--END HOA DON KHAC DMS \n"+
						")  " +
						"HD5 left join \n" +
						"( \n" +
						"	select 1 as loai, hoadon_fk, sum(chietkhau) as chietkhau, sum ( tienVAT ) as tienVAT  \n" +
						"	from \n" +
						"	( " + ck5 +") \n" +
						"	ck group by hoadon_fk \n" +
						")  \n" +
						"CK on HD5.PK_SEQ = CK.hoadon_fk and HD5.loai = CK.loai \n" +
					"UNION ALL \n"+
						" SELECT a.pk_seq, b.sohoadon, b.kyhieuhoadon AS KYHIEU, b.trangthai, ngayhoadon, c.maFAST ,c.TEN khTen, c.MASOTHUE, \n"+ 
						"		 a.diengiai spTEN, (a.dongia*a.soluong) tongtienBVAT, a.vat tienVat \n"+ 
						" FROM 	 erp_hoadonphelieu_sanpham a INNER JOIN ERP_HoaDonPheLieu b ON a.hoadonphelieu_fk = b.pk_seq \n"+
						"		 INNER JOIN KHACHHANG c on b.khachhang_fk = c.PK_SEQ \n"+ 
						" WHERE 1=1 and a.Thuevat = 5 "+conditionHDKhac +
					") hd  order by hd.NGAYXUATHD,sohoadon \n"/*+
					" order by cast(hd.sohoadon as numeric(18, 0)) asc "*/;
					
					System.out.println("---INIT HOA DON 5%: " + query);
					this.hoadonRs = db.get(query);
					
					
					/*query = 
					" SELECT * \n" +
					" FROM ( \n"+	
						"select  HD10.PK_SEQ, HD10.sohoadon, HD10.kyhieu, HD10.trangthai, HD10.ngayxuatHD, HD10.MAFAST, HD10.khTEN , HD10.masothue,  " +
						"		 HD10.spTEN,  " +
						"		 HD10.tongtienBVAT - isnull( CK.chietkhau, 0) as tongtienBVAT,  " +
						"		 HD10.tienVAT - isnull( CK.tienVAT, 0) as tienVAT  " +
						"from  " +
						"(  " +
						"	select 1 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN as khTEN, KH.masothue,    " +
						"		 sum( round ( spct.soluong * c.dongia, 0 ) ) as tongtienBVAT, " +
						"		 sum( round ( round ( spct.soluong * c.dongia, 0 ) * 0.1, 0 ) ) as tienVAT,  	" +
						"		( select TEN from SANPHAM where pk_seq = ( select top(1) sanpham_fk from HOADON_SP where HOADON_FK = a.pk_seq and VAT = '10'  ) ) as spTEN  " +
						"	from HOADON a      " +
						"		inner join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ    " +
						"		inner join HOADON_SP c on a.pk_seq = c.hoadon_fk    " +
						"		inner join sanpham sp on sp.pk_seq = c.sanpham_fk    \n" +
						"		inner join (select hoadon_fk,sum(SOLUONG) as soluong,dongia,SOLO,NGAYHETHAN,isnhapkhau,THUEVAT,MA from HOADON_SP_CHITIET "+
						"			group by dongia,SOLO,NGAYHETHAN ,hoadon_fk,isnhapkhau,THUEVAT,MA ) as spct on spct.hoadon_fk=a.pk_seq and spct.MA=sp.Ma " +
					
						"	where   isnull(a.loaihoadon,0)=0 and  c.VAT = '10'  " + condition +
						"	group by a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN, KH.masothue  " +
						"UNION ALL		 " +
						"	select 2 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN as khTEN, KH.masothue,   " +
						"		 sum( round ( c.soluong * c.dongia, 0 ) * (case when f.donquatang=1 or f.DONHANGKHAC=1 Then 1 else 0 end  )) as tongtienBVAT, " +
						"		  sum( round ( round ( c.soluong * c.dongia, 0 ) * 0.1, 0 ) * (case when f.donquatang=1 or f.DONHANGKHAC=1 Then 1 else 0 end  )) as tienVAT, 	" +
						"		( select TEN from SANPHAM where PK_SEQ = ( select top(1) sanpham_fk from HOADON_CTKM_TRAKM where HOADON_FK = a.pk_seq and VAT = '10'  ) ) as spTEN        " +
						"	from HOADON a      " +
						"		inner join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ     " +
						"		inner join HOADON_CTKM_TRAKM c on a.pk_seq = c.hoadon_fk    " +
						" inner join HOADON_DDH d on d.HOADON_FK=a.PK_SEQ  "+
						" inner join DONHANG f on f.PK_SEQ=d.DDH_FK  "+
						"	where    isnull(a.loaihoadon,0)=1 and c.VAT = '10' and a.SOHOADON <> 'NA' " + condition +
						"	group by a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN, KH.masothue " +
						"UNION ALL " +
						"select loai, PK_SEQ, sohoadon, kyhieu, trangthai, ngayxuatHD, MAFAST, khTEN, masothue,   " +
						"		round( sum(soluong) * ( sum( soluong * dongia ) / sum(soluong) )  - SUM(CK) , 0 )  as tongtienBVAT,  " +
						"		case when sum(HD.TIENTHUE) > 0 then sum(HD.TIENTHUE) else " +
						"		round( round( sum(soluong) * ( sum( soluong * dongia ) / sum(soluong) )  - SUM(CK) , 0 ) * 0.1, 0 )  end as tienVAT,  " +
						"		 spTEN " +
						"select loai, PK_SEQ, sohoadon, kyhieu, trangthai, ngayxuatHD, MAFAST, khTEN, masothue,   \n" +
						//"		round( sum(soluong) * ( sum( soluong * dongia ) / sum(soluong) )  - SUM(CK) , 0 )  as tongtienBVAT,  \n" +
						"		sum( round ( soluong * dongia - ck, 0 ) ) as tongtienBVAT, 	" +
						//"		round( round( sum(soluong) * ( sum( soluong * dongia ) / sum(soluong) )  - SUM(CK) , 0 ) * 0.05, 0 )  end as tienVAT,  \n" +
						"		sum( round( round(soluong * DonGia - CK ,0) * 0.1, 0 ) )   as tienVAT,  \n" +
						"		 spTEN \n" +
						"from " +
						"( " +
						"	select 3 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, case when a.KHACHHANG_FK = null then KH.MAFAST else npp.MaFAST end MAFAST, a.TENKHACHHANG khTEN, a.MASOTHUE, ISNULL(c.TIENVAT,0) TIENTHUE,   " +
						"			case     when c.donvitinh = e.donvi then b.soluong     " +
						"							  else b.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  " +
						"			case     when c.donvitinh = e.donvi then c.dongia     " +
						"						  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia,     " +
						"		( select TEN from SANPHAM where pk_seq = ( select top(1) sanpham_fk from ERP_HOADONNPP_SP where HOADON_FK = a.pk_seq and VAT = '10'  ) ) as spTEN, isnull(c.ChietKhau,0) as CK     " +
						"	from ERP_HOADONNPP a       " +
						"		left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ     " +
						"       left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ  "+
						"		inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  " +
						"		inner join SANPHAM d on c.sanpham_fk = d.pk_seq   " +
						"\n inner join  (     "+
						"\n			select b.hoadon_fk,b.MA,solo,ngayhethan,SUM(soluong) as soluong,kho_fk    "+  
						"\n			from ERP_HOADONNPP_SP_CHITIET b      "+
						"\n			group by hoadon_fk,b.MA,solo,ngayhethan,kho_fk) b on a.pk_seq = b.hoadon_fk	 and b.MA=d.MA "+
					
						"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq      " +
						"	where 1=1  and c.SOLUONG > 0 and c.VAT = '10'     " + conditionETC +
						") " +
						"HD group by loai, PK_SEQ, sohoadon, kyhieu, trangthai, ngayxuatHD, MAFAST, khTEN, masothue, spTEN  " +
						"UNION ALL  \n" + 
						"		--BEGIN HOA DON KHAC DMS \n" + 
						"		SELECT 4 AS LOAI, HD.PK_SEQ, ISNULL(HD.SOHOADON,'') SOHOADON, isnull(HD.KYHIEUHOADON, '') KYHIEU, HD.TRANGTHAI,   \r\n" + 
						"						ISNULL(HD.NGAYHOADON,'') AS NGAYXUATHD,     \r\n" + 
						"				                  ISNULL(NPP.MaFAST, KH.MaFAST) AS MAFAST, \r\n" + 
						"								  ISNULL(NPP.TEN,KH.TEN) AS khTEN, \r\n" + 
						"								  LTRIM(ISNULL(NPP.MASOTHUE,KH.MASOTHUE)) MASOTHUE,     \r\n" + 
						"								   BVAT AS TONGTIENBVAT,     \r\n" + 
						"								   BVAT*VAT/100 AS VAT,     \r\n" + 
						"								  ( select TEN from SANPHAM where pk_seq = ( select top(1) sanpham_fk from ERP_HOADONKHAC_SANPHAM where HOADONKHAC_FK = HD.pk_seq and VAT = '10'  ) ) as spTEN    \n" + 
						" 		FROM ERP_HOADONKHAC HD     \n" + 
						" 		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK       \n" + 
						" 		LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_DAT_FK   \n" +
						" 		WHERE VAT = '10' "+ conditionHDKhac_CongNoPhaiThu +" \n" + 
						"		--END HOA DON KHAC DMS \n"+
						")  " +
						"HD10 left join " +
						"( " +
						"	select 1 as loai, hoadon_fk, sum(chietkhau) as chietkhau, sum ( tienVAT ) as tienVAT  " +
						"	from " +
						"	( " + ck10 +") " +
						"	ck group by hoadon_fk " +
						")  " +
						"CK on HD10.PK_SEQ = CK.hoadon_fk and HD10.loai = CK.loai " +
					"UNION ALL \n"+
						" SELECT a.pk_seq, b.sohoadon, b.kyhieuhoadon AS KYHIEU, b.trangthai, ngayhoadon, c.maFAST ,c.TEN khTen, c.MASOTHUE, \n"+ 
						"		 a.diengiai spTEN, (a.dongia*a.soluong) tongtienBVAT, a.vat tienVat \n"+ 
						" FROM 	 erp_hoadonphelieu_sanpham a INNER JOIN ERP_HoaDonPheLieu b ON a.hoadonphelieu_fk = b.pk_seq \n"+
						"		 INNER JOIN KHACHHANG c on b.khachhang_fk = c.PK_SEQ \n"+ 
						" WHERE 1=1 AND a.Thuevat = 10 "+conditionHDKhac +
					") hd \n"+
					" order by cast(hd.sohoadon as numeric(18, 0)) asc ";*/
					query = 
							"SELECT * \n" +
							"FROM ( \n" +
									"select  HD10.PK_SEQ, HD10.sohoadon, HD10.kyhieu, HD10.trangthai, HD10.ngayxuatHD, HD10.MAFAST, HD10.khTEN , HD10.masothue,  \n" +
									"		 HD10.spTEN,  \n" +
									"		 HD10.tongtienBVAT - isnull( CK.chietkhau, 0) as tongtienBVAT,  \n" +
									"		 HD10.tienVAT - isnull( CK.tienVAT, 0) as tienVAT  \n" +
									"from  \n" +
									"(  \n" +
									"	select 1 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN as khTEN, KH.masothue,    \n" +
									"		 sum( round ( spct.soluong * c.dongia, 0 ) ) as tongtienBVAT, \n" +
									"		 sum( round ( round ( spct.soluong * c.dongia, 0 ) * 0.10, 0 ) ) as tienVAT,  	\n" +
									"		( select TEN from ERP_SANPHAM where pk_seq = ( select top(1) sanpham_fk from ERP_HOADON_SP where HOADON_FK = a.pk_seq and VAT = '10'  ) ) as spTEN  \n" +
									"	from HOADON a      \n" +
									"		inner join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ    \n" +
									"		inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk    \n" +
									"		inner join ERP_SANPHAM sp on sp.pk_seq = c.sanpham_fk    \n" +
									"		inner join (select hoadon_fk,sum(SOLUONG) as soluong,dongia,SOLO,NGAYHETHAN,isnhapkhau,THUEVAT,MA from ERP_HOADON_SP_CHITIET "+
									"			group by dongia,SOLO,NGAYHETHAN ,hoadon_fk,isnhapkhau,THUEVAT,MA ) as spct on spct.hoadon_fk=a.pk_seq and spct.MA=sp.Ma " +
									"	where   isnull(a.loaihoadon,0)=0 and  c.VAT = '10'  \n" + condition +
									"	group by a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN, KH.masothue  \n" +
								"UNION ALL		 \n" +
									"	select 2 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN as khTEN, KH.masothue,   \n" +
									"		 sum( round ( c.soluong * c.dongia, 0 ) * (case when f.donquatang=1 or f.DONHANGKHAC=1 Then 1 else 0 end  ) ) as tongtienBVAT, \n" +
									"		  sum( round ( round ( c.soluong * c.dongia, 0 ) * 0.10, 0 ) *(case when f.donquatang=1 or f.DONHANGKHAC=1 Then 1 else 0 end  ) ) as tienVAT, 	\n" +
									"		( select TEN from ERP_SANPHAM where PK_SEQ = ( select top(1) sanpham_fk from HOADON_CTKM_TRAKM where HOADON_FK = a.pk_seq and VAT = '10'  ) ) as spTEN        \n" +
									"	from HOADON a      \n" +
									"		inner join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ     \n" +
									"		inner join HOADON_CTKM_TRAKM c on a.pk_seq = c.hoadon_fk    \n" +
									" inner join HOADON_DDH d on d.HOADON_FK=a.PK_SEQ  \n" +
									" inner join DONHANG f on f.PK_SEQ=d.DDH_FK \n" +
									"	where    isnull(a.loaihoadon,0)=1 and c.VAT = '10' and a.SOHOADON <> 'NA'  \n" + condition +
									"	group by a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN, KH.masothue \n" +
								"UNION ALL \n" +
									"select loai, PK_SEQ, sohoadon, kyhieu, trangthai, ngayxuatHD, MAFAST, khTEN, masothue,   \n" +
									//"		round( sum(soluong) * ( sum( soluong * dongia ) / sum(soluong) )  - SUM(CK) , 0 )  as tongtienBVAT,  \n" +
									"		sum( round ( soluong * dongia - ck, 0 ) ) as tongtienBVAT, 	" +
									//"		round( round( sum(soluong) * ( sum( soluong * dongia ) / sum(soluong) )  - SUM(CK) , 0 ) * 0.010, 0 )  end as tienVAT,  \n" +
									"		sum( case when tienthue=0 then round( round(soluong * DonGia - CK ,0) * 0.1, 0 ) else tienthue end )     as tienVAT,  \n" +
									"		 spTEN \n" +
									"from \n" +
									"( \n" +
									"	select 3 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai,a.NGAYXUATHD,case when a.KHACHHANG_FK = null then KH.MAFAST else npp.MaFAST end MAFAST, a.TENKHACHHANG khTEN, a.MASOTHUE, ISNULL(b.tienthue,0) TIENTHUE,   \n" +
									"			case     when c.donvitinh = e.donvi then b.soluong     \n" +
									"							  else b.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  \n" +
									"			case     when c.donvitinh = e.donvi then c.dongia     \n" +
									"						  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia,     \n" +
									"		( select TEN from ERP_SANPHAM where pk_seq = ( select top(1) sanpham_fk from ERP_HOADONNPP_SP where HOADON_FK = a.pk_seq and VAT = '10'  ) ) as spTEN, isnull(c.ChietKhau,0) as CK     \n" +
									"	from ERP_HOADONNPP a       \n" +
									"		left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ     \n" +
									"       left join NHAPHANPHOI npp on NPP.PK_SEq = a.NPP_DAT_FK \n "+
									"		inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  \n" +
									"		inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq   \n" +
									"\n inner join  (     "+
									"\n			select b.hoadon_fk,b.MA,solo,ngayhethan,SUM(soluong) as soluong,kho_fk,isnull(tienthue,0) tienthue    "+  
									"\n			from ERP_HOADONNPP_SP_CHITIET b      "+
									"\n			group by hoadon_fk,b.MA,solo,ngayhethan,kho_fk,tienthue) b on a.pk_seq = b.hoadon_fk	 and b.MA=d.MA "+
									
									"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq      \n" +
									"	where 1=1  and c.SOLUONG > 0 and c.VAT = '10'     \n" + conditionETC +
									
								
									") \n" +
									"HD group by loai, PK_SEQ, sohoadon, kyhieu, trangthai, ngayxuatHD, MAFAST, khTEN, masothue, spTEN  \n" +
									"UNION ALL  \n" + 
									"		--BEGIN HOA DON KHAC DMS \n" + 
									"		SELECT 4 AS LOAI, HD.PK_SEQ, ISNULL(HD.SOHOADON,'') SOHOADON, isnull(HD.KYHIEUHOADON, '') KYHIEU, HD.TRANGTHAI,   \r\n" + 
									"						ISNULL(HD.NGAYHOADON,'') AS NGAYXUATHD,     \r\n" + 
									"				                  ISNULL(NPP.MaFAST, KH.MaFAST) AS MAFAST, \r\n" + 
									"								  ISNULL(NPP.TEN,KH.TEN) AS khTEN, \r\n" + 
									"								  LTRIM(ISNULL(NPP.MASOTHUE,KH.MASOTHUE)) MASOTHUE,     \r\n" + 
									"								   BVAT AS TONGTIENBVAT,     \r\n" + 
									"								   BVAT*VAT/100 AS VAT,     \r\n" + 
									"								  ( select TEN from ERP_SANPHAM where pk_seq = ( select top(1) sanpham_fk from ERP_HOADONKHAC_SANPHAM where HOADONKHAC_FK = HD.pk_seq and VAT = '10'  ) ) as spTEN    \n" + 
									" 		FROM ERP_HOADONKHAC HD     \n" + 
									" 		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK       \n" + 
									" 		LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_DAT_FK   \n" +
									" 		WHERE VAT = '10' "+ conditionHDKhac_CongNoPhaiThu +" \n" + 
									"		--END HOA DON KHAC DMS \n"+
								")  " +
								"HD10 left join \n" +
								"( \n" +
								"	select 1 as loai, hoadon_fk, sum(chietkhau) as chietkhau, sum ( tienVAT ) as tienVAT  \n" +
								"	from \n" +
								"	( " + ck10 +") \n" +
								"	ck group by hoadon_fk \n" +
								")  \n" +
								"CK on HD10.PK_SEQ = CK.hoadon_fk and HD10.loai = CK.loai \n" +
							"UNION ALL \n"+
								" SELECT a.pk_seq, b.sohoadon, b.kyhieuhoadon AS KYHIEU, b.trangthai, ngayhoadon, c.maFAST ,c.TEN khTen, c.MASOTHUE, \n"+ 
								"		 a.diengiai spTEN, (a.dongia*a.soluong) tongtienBVAT, a.vat tienVat \n"+ 
								" FROM 	 erp_hoadonphelieu_sanpham a INNER JOIN ERP_HoaDonPheLieu b ON a.hoadonphelieu_fk = b.pk_seq \n"+
								"		 INNER JOIN KHACHHANG c on b.khachhang_fk = c.PK_SEQ \n"+ 
								" WHERE 1=1 and a.Thuevat = 10 "+conditionHDKhac +
							") hd  order by hd.NGAYXUATHD,sohoadon \n"/*+
							" order by cast(hd.sohoadon as numeric(18, 0)) asc "*/;
					
							/*"select  HD10.PK_SEQ, HD10.sohoadon, HD10.kyhieu, HD10.trangthai, HD10.ngayxuatHD, HD10.MAFAST, HD10.khTEN , HD10.masothue,  " +
							"	HD10.spTEN, " +
							"	HD10.tongtienBVAT - isnull( CK.chietkhau, 0) as tongtienBVAT," +
							"	HD10.tienVAT - isnull( CK.tienVAT, 0) as tienVAT  " +
							"from  " +
							"(  " +
							"	select 1 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN as khTEN, KH.masothue,   " +
							"		 sum( round ( c.soluong * c.dongia, 0) ) as tongtienBVAT,   " +
							"		 sum( round ( round ( c.soluong * c.dongia, 0 ) * 0.1, 0 ) ) as tienVAT, 	" +
							"		( select TEN from SANPHAM where pk_seq = ( select top(1) sanpham_fk from HOADON_SP where HOADON_FK = a.pk_seq and VAT = '10'  ) ) as spTEN   " +
							"	from HOADON a     " +
							"		inner join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ   " +
							"		inner join HOADON_SP c on a.pk_seq = c.hoadon_fk    " +
							"	where isnull(a.loaihoadon,0)=0 and  c.VAT = '10'   " + condition +
							"	group by a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN, KH.masothue" +
						" UNION ALL  " +
							"	select 2 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN as khTEN, KH.masothue,   " +
							"		 sum( round ( c.soluong * c.dongia, 0) ) as tongtienBVAT,   " +
							"		 sum( round ( round ( c.soluong * c.dongia, 0 ) * 0.1, 0 ) ) as tienVAT, 	" +
							"		( select TEN from SANPHAM where pk_seq = ( select top(1) sanpham_fk from HOADON_CTKM_TRAKM where HOADON_FK = a.pk_seq and VAT = '10'  ) ) as spTEN   " +
							"	from HOADON a     " +
							"		inner join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ   " +
							"		inner join HOADON_CTKM_TRAKM c on a.pk_seq = c.hoadon_fk    " +
							"	where   isnull(a.loaihoadon,0)=1 and c.VAT = '10'   " + condition +
							"	group by a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN, KH.masothue  " +
						"UNION ALL " +
							"select loai, PK_SEQ, sohoadon, kyhieu, trangthai, ngayxuatHD, MAFAST, khTEN, masothue,  " +
							"		sum(soluong) * ( sum( soluong * dongia ) / sum(soluong) ) -SUM(CK) as tongtienBVAT, " +
							"		round( round( sum(soluong) * ( sum( soluong * dongia ) / sum(soluong) )  - SUM(CK) , 0 ) * 0.1, 0 )  as tienVAT, " +
							" spTEN " +
							"from " +
							"( " +
							"	select 3 as loai, a.PK_SEQ, a.sohoadon, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST, KH.TEN as khTEN, KH.masothue,    " +
							"			case     when c.donvitinh = e.donvi then c.soluong     " +
							"							  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  " +
							"			case     when c.donvitinh = e.donvi then c.dongia     " +
							"						  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia,     " +
							"		( select TEN from SANPHAM where pk_seq = ( select top(1) sanpham_fk from ERP_HOADONNPP_SP where HOADON_FK = a.pk_seq and VAT = '10'  ) ) as spTEN ,ISNULL(c.ChietKhau,0) as CK    " +
							"	from ERP_HOADONNPP a       " +
							"		left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ     " +
							"		inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  " +
							"		inner join SANPHAM d on c.sanpham_fk = d.pk_seq   " +
							"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq      " +
							"	where 1=1  and c.SOLUONG > 0 and c.VAT = '10'   " + conditionETC +
							") " +
							"HD group by loai, PK_SEQ, sohoadon, kyhieu, trangthai, ngayxuatHD, MAFAST, khTEN, masothue, spTEN " +
							")  " +
							"HD10 left join " +
							"( " +
							"	select 1 as loai, hoadon_fk, sum(chietkhau) as chietkhau, sum ( tienVAT ) as tienVAT  " +
							"	from " +
							"	( " + ck10+" )" +
							"	ck group by hoadon_fk " +
							")  " +
							"CK on HD10.PK_SEQ = CK.hoadon_fk and HD10.loai = CK.loai " +
							"order by cast(sohoadon as numeric(18, 0)) asc ";*/
		
					System.out.println("---INIT HOA DON 10%: " + query);
					this.hoadon10PtRs = db.get(query);
					
			}	
		 }
		
		/*System.out.println("--- LAY NVBH: " + "select  PK_SEQ, TEN AS TEN from DaiDienKinhDoanh where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");
		this.nvbhRs = db.get("select  PK_SEQ, TEN AS TEN from DaiDienKinhDoanh where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");
		this.khRs = db.get("select  PK_SEQ, MAFAST + ', ' + TEN AS TEN from KHACHHANG where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");*/
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public ResultSet getHoadonRs() 
	{
		return this.hoadonRs;
	}

	public void setHoadonRs(ResultSet hdRs) 
	{
		this.hoadonRs = hdRs;
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

	
	public String getNppTen() {
		
		return this.nppTen;
	}

	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getKhTen() {
		return this.khTen;
	}

	
	public void setKhTen(String KhTen) {
		this.khTen = KhTen;
		
	}

	
	public ResultSet getKhRs() {
		return this.khRs;
	}

	
	public void setKhRs(ResultSet KhRs) {
		this.khRs = KhRs;
		
	}
	
	public String getSoHoaDon()
	{
		return this.sohoadon;
	}
	public void setSoHoaDon(String sohoadon)
	{
		this.sohoadon =sohoadon;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
	public String getSohoadontu() {
		
		return this.sohoadontu;
	}

	
	public void setSohoadontu(String sohoadontu) {
		
		this.sohoadontu = sohoadontu;
	}

	
	public String getSohoadonden() {
		
		return this.sohoadonden;
	}

	
	public void setSohoadonden(String sohoadonden) {
		
		this.sohoadonden = sohoadonden;
	}

	
	public String getPtVat() {

		return this.ptVAT;
	}


	public void setPtVat(String ptVat) {
		
		this.ptVAT = ptVat;
	}

	
	public String getNvbhId() {
		
		return this.nvbhId;
	}

	
	public void setNvbhId(String nvbhId) {
		
		this.nvbhId = nvbhId;
	}

	
	public ResultSet getNvbhRs() {
		
		return this.nvbhRs;
	}

	
	public void setNvbhRs(ResultSet nvbhRs) {
		
		this.nvbhRs = nvbhRs;
	}

	
	public String getMaFast() {
		
		return this.maFAST;
	}

	
	public void setMaFast(String maFAST) {
		
		this.maFAST = maFAST;
	}


	public void createRs() 
	{
		if(this.view.length()<=0)
		{
			this.getNppInfo();
		}
		String query="select  PK_SEQ, TEN AS TEN from DaiDienKinhDoanh where TRANGTHAI = '1'  ";
		if(this.nppId.length()>0)
		{
			query+=" and pk_seq in (select ddkd_fk from DaiDienKinhDoanh_npp where npp_fk ="+this.nppId+" ) ";
			
			System.out.println("______"+query);
			
			this.nvbhRs = db.get(query);
			
			this.khRs = db.get("select  PK_SEQ, MAFAST + ', ' + TEN AS TEN from KHACHHANG where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");
		}		
		this.khoRs=db.get("select pk_seq,ten from kho where TRANGTHAI=1 ");
		
	}

	
	public ResultSet getHoadon10PtRs() {

		return this.hoadon10PtRs;
	}


	public void setHoadon10PtRs(ResultSet hd10PtRs) {
		
		this.hoadon10PtRs = hd10PtRs;
	}

	
	public String getFormatDate(String date) 
	{
		String[] arr = date.split("-");
		
		return arr[2] + "/" + arr[1] + "/" + arr[0];
	}


	public String getActiveTab() {
	
		return this.activeTab;
	}

	
	public void setActiveTab(String active) {
		this.activeTab= active ;
		
	}

	
	public void setQuery(String searchQuery) {
	 this.query = searchQuery;
		
	}


	public void searchQuery_ETC(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.EtcRs= db.get(sql);
		System.out.println("searchquery etc"+searchquery);
		
	
		
	}
	
	public void searchQuery_ETCHO(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.EtcHoRs= db.get(sql);
		System.out.println("searchquery etcHO"+searchquery);
		
	
		
	}
	
	public void searchQuery_OTCCN(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.OTCCN= db.get(sql);
		System.out.println("searchquery OTCCN"+searchquery);
		
	
		
	}
	
	public void searchQuery_ETCDT(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.ETCDT= db.get(sql);
		System.out.println("searchquery ETCDT"+searchquery);
		
	
		
	}
	public void searchQuery_HM(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.rsHM= db.get(sql);
		System.out.println("searchquery ETCDT"+searchquery);
		
	
		
	}
	

	public void searchQuery_OTC(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		System.out.println("OTC gop la "+sql);
		this.OtcRs= db.get(sql);
	
		
	}
	
	public void searchQuery_HDKhac(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.HDKhacRs= db.get(sql);
	
		
	}

	public void searchQuery_KM(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.kmRs= db.get(sql);
	
		
	}


	public ResultSet getETCRs() {
	
		return this.EtcRs;
	}


	public void setETCRS(ResultSet ETCRs) {
		this.EtcRs= ETCRs;
		
	}


	public ResultSet getOTCRs() {
		
		return this.OtcRs;
	}

	public void setOTCRS(ResultSet OTCRs) {
		this.OtcRs = OTCRs;
	}


	public ResultSet getKMRs() {

		return this.kmRs;
	}


	public void setKMRS(ResultSet KMRs) {
		this.kmRs= KMRs;
		
	}

	public String getVung() {

		return this.vung;
	}


	public void setVung(String vung) {
		this.vung= vung;
	}


	public String getKhuvuc() {
	
		return this.khuvuc;
	}


	public void setKhuvuc(String khuvuc) {
		this.khuvuc= khuvuc;
		
	}

	
	public ResultSet getKhuvucRs() {
	
		return this.khuvucRs;
	}


	public void setKhuvucRs(ResultSet khuvucRs) {
		this.khuvucRs= khuvucRs;
		
	}

	
	public void search_NPP() 
	{
		String query_khuvuc="select PK_SEQ, TEN from KHUVUC ";
		if(this.vung != null && this.vung.length() != 0)
			query_khuvuc += "where VUNG_FK = '" + this.vung + "'";
		
	
		this.khuvucRs= db.get(query_khuvuc);
		
		String query_npp="select PK_SEQ, TEN from NHAPHANPHOI  "+ " WHERE pk_seq in "+ util.quyen_npp(userId)+ "" ;
		if (this.khuvuc.length() > 0)
		{
			query_npp = query_npp + " and khuvuc_fk ='" + this.khuvuc + "'";
		}
		if (this.vung.length() > 0)
		{
			query_npp = query_npp + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vung + "')";
		}
		this.nppRs= db.get(query_npp);
		
		String query="select pk_seq,ten,diachi  from daidienkinhdoanh where 1=1 ";
		
		if(this.view.length()>0)
		{
			query +=" and pk_seq in "+util.Quyen_Ddkd(userId)+"";
		}
		else 
		{
			query+=" and pk_Seq in  (select ddkd_fk from daidienkinhdoanh_npp where npp_fk='"+this.nppId+"') ";
		}
		System.out.println("__TDV__"+query);
		this.tdvRs=this.db.get(query);
		
		
	}


	public ResultSet getnppRs() {
	
		return this.nppRs;
	}


	public void setnppRs(ResultSet nppRs) {
		this.nppRs= nppRs;
		
	}


	public String getnpp() {
		
		return this.npp;
	}


	public void setnpp(String npp) {
		this.npp=npp;
		
	}

	
	public String getView() {
		
		return this.view;
	}

	
	public void setView(String view) {
		
		this.view = view;
	}
	


	public void setTotal_Query(String searchquery) 
	{
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.totalRs= db.get(sql);
	
	}
	
	
	ResultSet totalRs;
	public ResultSet getTotalRs()
	{
		return totalRs;
	}
	public void setTotalRs(ResultSet totalRs)
	{
		this.totalRs=totalRs;
	}
	 

	String muclay;
	public String getMuclay()
  {
  	return muclay;
  }
	public void setMuclay(String muclay)
  {
  	this.muclay = muclay;
  }
	
	String tdvId;

	public String getTdvId()
  {
  	return tdvId;
  }

	public void setTdvId(String tdvId)
  {
  	this.tdvId = tdvId;
  }
	
	ResultSet tdvRs;

	public ResultSet getTdvRs()
  {
  	return tdvRs;
  }

	public void setTdvRs(ResultSet tdvRs)
  {
  	this.tdvRs = tdvRs;
  }
	
	public String getKhoId()
  {
  	return khoId;
  }

	public void setKhoId(String khoId)
  {
  	this.khoId = khoId;
  }


	String khoId;
	ResultSet khoRs;

	public ResultSet getKhoRs()
  {
  	return khoRs;
  }

	public void setKhoRs(ResultSet khoRs)
  {
  	this.khoRs = khoRs;
  }
	
	String cndt,kh;
	
	public String 	getMucCN_DT()
	{
	  return 	this.cndt;
	}
	
	public void setMucCN_DT(String cndt)
	{
		this.cndt=cndt;
	}
	
	public String getMuc_KhachHang()
	{
		return 	this.kh;
	}
	
	public void setMuc_KhachHang(String cndt)
	{
		this.kh=cndt;
	}

	
	public ResultSet getHDKhacRs() {
		
		return this.HDKhacRs;
	}

	
	public void setHDKhacRS(ResultSet HDKhacRs) {
		
		this.HDKhacRs = HDKhacRs;
	}
	public ResultSet getEtcHoRs() {
		return EtcHoRs;
	}

	public void setEtcHoRs(ResultSet etcHoRs) {
		EtcHoRs = etcHoRs;
	}
}
