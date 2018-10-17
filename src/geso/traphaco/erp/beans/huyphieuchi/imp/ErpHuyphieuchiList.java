package geso.traphaco.erp.beans.huyphieuchi.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.erp.beans.huyphieuchi.IErpHuyphieuchiList;

public class ErpHuyphieuchiList extends Phan_Trang implements IErpHuyphieuchiList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String nppdangnhap;
	String tungay;
	String denngay;
	String trangthai;
	String msg;
	String nguoitao;
	String soCT;
	
	List<IThongTinHienThi> hienthiList;
	
	ResultSet hctmhRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpHuyphieuchiList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.msg = "";
		this.nguoitao="";
		this.nppdangnhap = "";
		this.soCT="";
		
		currentPages = 1;
		num = 1;
		this.db = new dbutils();
		this.hienthiList = new ArrayList<IThongTinHienThi>();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;
	}
	
	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	public int getCurrentPage()
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages() 
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as c from ERP_HUYCHUNGTUMUAHANG");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public ResultSet getHctMhRs() 
	{
		return this.hctmhRs;
	}

	public void setHctMhRs(ResultSet hctmhRs) 
	{
		this.hctmhRs = hctmhRs;
	}
	
	private String LayDuLieu(String sochungtu, String loaichungtu) {
		String query = "";
		
		String laykt = "";
		//sochungtu = sochungtu.substring(3, sochungtu.length());
		
	//PHIẾU CHI
	try{
		if(loaichungtu.equals("2")){
			  String tiente_fk = "";
			  double tigia = 0;
			  String trichphi_fk = "";
			  String httt = "";
			  query =	   " SELECT  TTHD.CHENHLECHVND, ISNULL(TTHD.PHINGANHANG, 0) AS PHINGANHANG , TTHD.TRICHPHI, \n"+
						   "         ISNULL(TTHD.VAT,0) AS VAT,  TTHD.HTTT_FK as HINHTHUCTT, TTHD.TIENTE_FK, TTHD.KHACHHANG_FK,  \n"+
						   "	     TTHD.NGAYGHINHAN ,TTHD.NCC_FK , TTHD.NGANHANG_FK , TTHD.NGANHANG_TP_FK , isnull(TTHD.TIGIA,1) as TIGIA, \n" +
						   "         ISNULL(TTHD.CHENHLECHVND,0) AS CHENHLECHVND , TTHD.NHANVIEN_FK, TTHD.SOTAIKHOAN_TP, TTHD.SOTAIKHOAN \n"+
					       " from   ERP_THANHTOANHOADON TTHD  left join ERP_NHACUNGCAP NCC on TTHD.NCC_FK= NCC.PK_SEQ \n" +
					       "                                  left join ERP_NHANVIEN NV on TTHD.NHANVIEN_FK = NV.PK_SEQ \n"+
					       " where TTHD.PK_SEQ = "+ sochungtu +"  ";
			  	
			  System.out.println(query);
				ResultSet rs= db.get(query);
				if(rs!= null)
				{
					while(rs.next())
					{
						httt = rs.getString("HINHTHUCTT");
						tiente_fk = rs.getString("tiente_fk")== null ?"100000":rs.getString("tiente_fk")  ;
						tigia = rs.getDouble("tigia");
						trichphi_fk = rs.getString("TRICHPHI")== null ?"0":rs.getString("TRICHPHI") ;
						
						String nganhang_fk= rs.getString("NGANHANG_FK")== null ?"":rs.getString("NGANHANG_FK") ;
						String nganhangTP_fk= rs.getString("NGANHANG_TP_FK")== null ?"":rs.getString("NGANHANG_TP_FK") ;
						
						String sotaikhoan = rs.getString("SOTAIKHOAN")== null ?"":rs.getString("SOTAIKHOAN") ;
						String sotaikhoanNH_TP =  rs.getString("SOTAIKHOAN_TP")== null ?"":rs.getString("SOTAIKHOAN_TP") ;
						
						String ncc_fk = rs.getString("NCC_FK") == null ? "":rs.getString("NCC_FK");
						String nhanvien_fk = rs.getString("NHANVIEN_FK") == null ? "":rs.getString("NHANVIEN_FK");
						String khachhang_fk = rs.getString("KHACHHANG_FK") == null ? "":rs.getString("KHACHHANG_FK");
						
						double phinganhang= rs.getDouble("PHINGANHANG");					
						double vat = rs.getDouble("VAT");					
						double chenhlech = rs.getDouble("CHENHLECHVND");
						
					// TIỀN HÓA ĐƠN
						if(ncc_fk.trim().length() > 0)
						{
							laykt = " select N'NỢ' as NO_CO, "+ sochungtu +" as PK_SEQ, b.SOHIEUTAIKHOAN,  "
								  +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ sochungtu +" and LOAIHD in (0,1,2,3,5,6,8) ),0) " 
								  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ sochungtu +" and LOAIHD in (0,1,2,3,5,6,8) ),0) * "+ tigia +" " 
								  + "              end as SOTIEN, a.MA + '-' + a.TEN as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"
								  + " from ERP_NHACUNGCAP a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
								  + " where a.pk_seq  = "+ ncc_fk +" "
						  + " UNION ALL "
						  		  + " select 'CÓ' as NO_CO, "+ sochungtu +" as PK_SEQ, case "+ httt +" when '100001' then (select tk.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY nh inner join ERP_TAIKHOANKT tk on nh.TAIKHOAN_FK= tk.PK_SEQ  where nh.SOTAIKHOAN = a.SOTAIKHOAN)  else '11110000' end as SOHIEUTAIKHOAN, "
						  		    +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ sochungtu +" and LOAIHD in (0,1,2,3,5,6,8) ),0) " 
								  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ sochungtu +" and LOAIHD in (0,1,2,3,5,6,8) ),0) * "+ tigia +" " 
								  + "              end as SOTIEN, \n"
								  +"              case when "+ httt +" = '100001' then (select MA + '-' + TEN from ERP_NGANHANG where pk_seq = a.NGANHANG_FK ) else '' end as DOITUONG, "
						  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP "
						  		  + " from ERP_THANHTOANHOADON a "
						  		  + " where a.pk_seq = " + sochungtu +" ";
							
						}else if(nhanvien_fk.trim().length() > 0)
						{
							laykt = " select N'NỢ' as NO_CO, "+ sochungtu +" as PK_SEQ, b.SOHIEUTAIKHOAN,  "
									  +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ sochungtu +" and LOAIHD in (1,5,6,8) ),0) " 
									  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ sochungtu +" and LOAIHD in (1,5,6,8) ),0) * "+ tigia +" " 
									  + "              end as SOTIEN, a.MA + '-' + a.TEN as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"
									  + " from ERP_NHANVIEN a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
									  + " where a.pk_seq  = "+ nhanvien_fk +" "
							  + " UNION ALL "
							  		  + " select 'CÓ' as NO_CO, "+ sochungtu +" as PK_SEQ, case "+ httt +" when '100001' then (select tk.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY nh inner join ERP_TAIKHOANKT tk on nh.TAIKHOAN_FK= tk.PK_SEQ  where nh.SOTAIKHOAN = a.SOTAIKHOAN)  else '11110000' end as SOHIEUTAIKHOAN, "
							  		    +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ sochungtu +" and LOAIHD in (1,5,6,8) ),0) " 
									  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ sochungtu +" and LOAIHD in (1,5,6,8)),0) * "+ tigia +" " 
									  + "              end as SOTIEN, \n"
									  +"              case when "+ httt +" = '100001' then (select MA + '-' + TEN from ERP_NGANHANG where pk_seq = a.NGANHANG_FK ) else '' end as DOITUONG, "
							  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP "
							  		  + " from ERP_THANHTOANHOADON a "
							  		  + " where a.pk_seq = " + sochungtu +" ";
						}
						
						
					// PHÍ NGÂN HÀNG
						// THANHTOAN: NGOAI TE, TRICH PHI BANG VND						     
						  if(!tiente_fk.equals("100000")&& trichphi_fk.equals("1") )
						  {	
							  if(sotaikhoanNH_TP.trim().length() > 0 && phinganhang > 0)
							  {
								  if(laykt.trim().length() > 0) laykt += "UNION ALL ";
								  laykt +=     " select N'NỢ' as NO_CO, "+ sochungtu +" as PK_SEQ , '64250000' as SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
									  		+ "        '' as DOITUONG,"
									  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP    "
									  		+ " from ERP_THANHTOANHOADON a "
									  		+ " where a.pk_seq = '"+ sochungtu +"' "
								  + " UNION ALL "
										    + " select N'CÓ' as NO_CO, "+ sochungtu +" as PK_SEQ , b.SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
									  		+ "        ISNULL((select MA + '-' + TEN from ERP_NGANHANG where PK_SEQ = a.NGANHANG_FK ), '') as DOITUONG,"
									  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP    "
									  		+ " from ERP_NGANHANG_CONGTY a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
									  		+ " where SOTAIKHOAN = '"+ sotaikhoanNH_TP +"' ";
								  		
							  }
							  
							  if(vat > 0)
							  {
								  if(laykt.trim().length() > 0) laykt += "UNION ALL ";
								  laykt +=    " select N'NỢ' as NO_CO, "+ sochungtu +" as PK_SEQ , '13311000' as SOHIEUTAIKHOAN , "+ vat +" as SOTIEN, "
									  		+ "        '' as DOITUONG,"
									  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 3 STT, 1 SAPXEP    "
									  		+ " from ERP_THANHTOANHOADON a "
									  		+ " where a.pk_seq = '"+ sochungtu +"' "
								  + " UNION ALL "
										    + " select N'CÓ' as NO_CO, "+ sochungtu +" as PK_SEQ ,"
										    + "       ( case "+ httt +" when '100001' then (select tk.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY nh inner join ERP_TAIKHOANKT tk on nh.TAIKHOAN_FK = tk.PK_SEQ  where nh.pk_seq = "+ nganhangTP_fk +" )  else '11110000' end ) as SOHIEUTAIKHOAN ,"
										    + "        "+ vat +" as SOTIEN, "
									  		+ "        ( case "+ httt +" when '100001' then (select MA + '-' + TEN from ERP_NGANHANG where PK_SEQ = a.NGANHANG_TP_FK ) else '' end) as DOITUONG,"
									  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 3 STT, 2 SAPXEP    "
									  		+ " from ERP_THANHTOANHOADON a  "
									  		+ " where a.PK_SEQ = '"+ sochungtu +"' ";
							  }
							  
						  }
						  else
						  {
							  if(sotaikhoan.trim().length() > 0 && phinganhang > 0)
							  {
								  if(laykt.trim().length() > 0) laykt += "UNION ALL ";
								  laykt +=     " select N'NỢ' as NO_CO, "+ sochungtu +" as PK_SEQ , '64250000' as SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
									  		+ "        '' as DOITUONG,"
									  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP    "
									  		+ " from ERP_THANHTOANHOADON a "
									  		+ " where a.pk_seq = '"+ sochungtu +"' "
								  + " UNION ALL "
										    + " select 'CÓ' as NO_CO, "+ sochungtu +" as PK_SEQ , b.SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
									  		+ "        ISNULL((select MA + '-' + TEN from ERP_NGANHANG where PK_SEQ = a.NGANHANG_FK ), '') as DOITUONG,"
									  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP    "
									  		+ " from ERP_NGANHANG_CONGTY a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
									  		+ " where SOTAIKHOAN = '"+ sotaikhoan +"' ";
								  		
							  }
							  
							  if(vat > 0)
							  {
								  if(laykt.trim().length() > 0) laykt += "UNION ALL ";
								  laykt +=    " select N'NỢ' as NO_CO, "+ sochungtu +" as PK_SEQ , '13311000' as SOHIEUTAIKHOAN , "+ vat +" as SOTIEN, "
									  		+ "        '' as DOITUONG,"
									  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 3 STT, 1 SAPXEP    "
									  		+ " from ERP_THANHTOANHOADON a "
									  		+ " where a.pk_seq = '"+ sochungtu +"' "
								  + " UNION ALL "
										    + " select 'CÓ' as NO_CO, "+ sochungtu +" as PK_SEQ ,"
										    + "       ( case "+ httt +" when '100001' then (select tk.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY nh inner join ERP_TAIKHOANKT tk on nh.TAIKHOAN_FK = tk.PK_SEQ  where nh.pk_seq = "+ nganhang_fk +" )  else '11110000' end ) as SOHIEUTAIKHOAN ,"
										    + "        "+ vat +" as SOTIEN, "
									  		+ "        ( case "+ httt +" when '100001' then (select MA + '-' + TEN from ERP_NGANHANG where PK_SEQ = a.NGANHANG_FK ) else '' end) as DOITUONG,"
									  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 3 STT, 2 SAPXEP    "
									  		+ " from ERP_THANHTOANHOADON a  "
									  		+ " where a.PK_SEQ = '"+ sochungtu +"' ";
							  } 
						  }
						  
						  // CHÊNH LỆCH
						  if(chenhlech != 0)
						  {
							  if(chenhlech > 0)
							  {
								  if(nhanvien_fk.trim().length() > 0)
								  {
									  if(laykt.trim().length() > 0) laykt += "UNION ALL ";
									  laykt += " select N'NỢ' as NO_CO , "+ sochungtu +" as PK_SEQ, '63580000' as SOHIEUTAIKHOAN, "+ chenhlech +" as SOTIEN, '' as DOITUONG,"
									 	   + "        '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 1 SAPXEP  "
									 	   + " from  ERP_THANHTOANHOADON a "
									 	   + " where a.pk_seq = " + sochungtu +" "
									 + " UNION ALL "
									       + " select  'CÓ' as NO_CO , "+ sochungtu +" as PK_SEQ, b.SOHIEUTAIKHOAN ,"+ chenhlech +" as SOTIEN,"
									       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 2 SAPXEP "
									       + " from ERP_NHANVIEN a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
									       + " where a.pk_seq = "+ nhanvien_fk +" ";
											 
								  }else if(ncc_fk.trim().length() > 0)
								  {
									  if(laykt.trim().length() > 0) laykt += "UNION ALL ";
									  laykt += " select N'NỢ' as NO_CO , "+ sochungtu +" as PK_SEQ, '63580000' as SOHIEUTAIKHOAN, "+ chenhlech +" as SOTIEN, '' as DOITUONG,"
										 	   + "        '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 1 SAPXEP  "
										 	   + " from  ERP_THANHTOANHOADON a "
										 	   + " where a.pk_seq = " + sochungtu +" "
										 + " UNION ALL "
										       + " select  'CÓ' as NO_CO , "+ sochungtu +" as PK_SEQ, b.SOHIEUTAIKHOAN ,"+ chenhlech +" as SOTIEN,"
										       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 2 SAPXEP "
										       + " from ERP_NHACUNGCAP a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
										       + " where a.pk_seq = "+ ncc_fk +" ";
								  }
								  
							  }
							  else
							  {
								  if(nhanvien_fk.trim().length() > 0)
								  {
									  if(laykt.trim().length() > 0) laykt += "UNION ALL ";
									  laykt +=  " select  N'NỢ' as NO_CO , "+ sochungtu +" as PK_SEQ, b.SOHIEUTAIKHOAN ,"+ chenhlech +" as SOTIEN,"
										       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 1 SAPXEP "
										       + " from ERP_NHANVIEN a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
										       + " where a.pk_seq = "+ nhanvien_fk +" "+
									
									  " UNION ALL "
											   + " select 'CÓ' as NO_CO , "+ sochungtu +" as PK_SEQ, '51580000' as SOHIEUTAIKHOAN, "+ chenhlech +" as SOTIEN, '' as DOITUONG,"
										 	   + "        '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 2 SAPXEP  "
										 	   + " from  ERP_THANHTOANHOADON a "
										 	   + " where a.pk_seq = " + sochungtu +" ";
											 
								  }else if(ncc_fk.trim().length() > 0)
								  {
									  if(laykt.trim().length() > 0) laykt += "UNION ALL ";
									  laykt +=  " select  N'NỢ' as NO_CO , "+ sochungtu +" as PK_SEQ, b.SOHIEUTAIKHOAN ,"+ chenhlech +" as SOTIEN,"
										       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 1 SAPXEP  "
										       + " from ERP_NHACUNGCAP a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
										       + " where a.pk_seq = "+ ncc_fk +" "+
									
									  " UNION ALL "
											   + " select 'CÓ' as NO_CO , "+ sochungtu +" as PK_SEQ, '51580000' as SOHIEUTAIKHOAN, "+ chenhlech +" as SOTIEN, '' as DOITUONG,"
										 	   + "        '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 2 SAPXEP  "
										 	   + " from  ERP_THANHTOANHOADON a "
										 	   + " where a.pk_seq = " + sochungtu +" ";
								  }
							  }
							  
						  }
						  
				    	  
						
					}
					rs.close();
				}
			
			
			//TIỀN VAT, PHÍ NGÂN HÀNG, CHÊNH LỆCH
				
				
				if(laykt.trim().length()<= 0)
				{
					laykt +=
					 " SELECT N'' NO_CO, '' PK_SEQ, '' SOHIEUTAIKHOAN, '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+
					 " FROM ERP_HOADONNCC a " +
					 "	WHERE a.PK_SEQ = '"+sochungtu+"'" ;
				}
						                             
				laykt += "ORDER BY PK_SEQ, STT, SAPXEP ";
				
			}
		
		//HÓA ĐƠN NHÀ CUNG CẤP
		if(loaichungtu.equals("3")){
			
			query = "";
			
			try 
			{
				
				query = " select  a.pk_seq, a.sohoadon, b.ngayghinhan, b.ncc_fk,  " +
				"    		d.SANPHAM_FK, d.TAISAN_FK, d.CCDC_FK, d.SOLUONG, d.DONGIA , d.CHIPHI_FK,   " +
				" 			isnull(a.sotienchietkhau, 0) as tienCK_HoaDon,  " +
				" 			a.VAT as VAT_HOADON, a.park_fk,  isnull(b.tinhthuenhapkhau, 0) as tinhthueNK,   " +
				" 			0 as loaihanghoa_fk, c.taikhoan_fk as taikhoanNCC ,  " +
				" 			ISNULL(b.tiente_fk, '100000') as tiente_fk,  "+
				" 			ISNULL(b.tigia,1 ) as tigia, " +
				"			(SELECT distinct B.TAIKHOANKT_FK FROM SANPHAM A INNER JOIN ERP_LOAISANPHAM B ON A.LOAISANPHAM_FK = B.PK_SEQ WHERE A.PK_SEQ = d.SANPHAM_FK) TAIKHOANNO_SANPHAM, \n"+ 
				"			(select distinct B.taikhoan_fk from ERP_TAISANCODINH A INNER JOIN Erp_LOAITAISAN B ON A.LOAITAISAN_FK = B.pk_seq WHERE A.PK_SEQ = d.TAISAN_FK AND B.CONGTY_FK ="+this.congtyId+" ) TAIKHOANNO_TAISAN, \n"+ 
				"			( select distinct B.taikhoan_fk from ERP_CONGCUDUNGCU A  INNER JOIN Erp_LOAICCDC B ON A.LOAICCDC_FK = B.pk_seq WHERE A.PK_SEQ = d.CCDC_FK AND B.CONGTY_FK = "+this.congtyId+"  ) TAIKHOANNO_CCDK, \n"+
				"			(  select distinct TAIKHOAN_FK from ERP_NHOMCHIPHI WHERE PK_SEQ =  d.CHIPHI_FK AND CONGTY_FK = "+this.congtyId+" ) TAIKHOANNO_CHIPHI, \n"+
				"			( select  PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13310000' AND CONGTY_FK = "+this.congtyId+") TAIKHOANNO_VAT, "+
				"			( select PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13320000' AND CONGTY_FK = "+this.congtyId+") TAIKHOANNO_VAT_TSCD, "+
				"			( select MA from SANPHAM WHERE PK_SEQ = d.SANPHAM_FK ) TENSP, (select MA FROM ERP_TAISANCODINH WHERE PK_SEQ = d.TAISAN_FK) TENTS, "+
				"			( select MA from ERP_CONGCUDUNGCU WHERE PK_SEQ = d.CCDC_FK ) TENCCDC, (select TEN FROM ERP_NHOMCHIPHI WHERE PK_SEQ = d.CHIPHI_FK ) TENCHIPHI, "+
				"			( select MA from ERP_NHACUNGCAP WHERE PK_SEQ = b.ncc_fk ) TENNCC "+
				" 	from 	ERP_HOADONNCC a inner join ERP_PARK b on a.park_fk = b.pk_seq   " +
				" 			inner join ERP_NHACUNGCAP c on b.ncc_fk = c.pk_seq   "
				+ " 		inner join ERP_HOADONNCC_DONMUAHANG d on d.HOADONNCC_FK = a.PK_SEQ " +
				" where b.pk_seq = '" + sochungtu + "'  ";
				
				
				ResultSet psktRs = db.get(query);
				
				String TAIKHOANNO_TIENHANG = "";
		        String TAIKHOANCO_TIENHANG = "";
		        
		        String TAIKHOANNO_VAT = "";
		        String TAIKHOANCO_VAT = "";
		        
		        if(psktRs != null)
		        {
					int  i = 0;
					while(psktRs.next())
		              {
						String hoadonncc_fk = psktRs.getString("pk_seq");
	                    
	                    String namNV = psktRs.getString("ngayghinhan").substring(0, 4);
	        			String thangNV = psktRs.getString("ngayghinhan").substring(5, 7);
	        			
	        			String ncc_fk = psktRs.getString("ncc_fk");
	        			String taikhoanncc_fk = psktRs.getString("taikhoanNCC");
	                    
	        			String tiente_fk = psktRs.getString("tiente_fk");
	        			double tygia = (psktRs.getDouble("tigia"));
	        			if(tiente_fk.equals("100000"))
	        				tygia = 1;
		        			
	        			String sanphamId= "";
	        			String taisanId = "";
	        			String ccdcId = "";
	        			String chiphiId = "";
	        			
	        			double soluong = 0;
	        			double dongia =  0;       			
	        		    double sotienBVAT = 0;
	        		    
	    				sanphamId = psktRs.getString("sanpham_fk") == null ? "":psktRs.getString("sanpham_fk")  ;
	    				taisanId = psktRs.getString("taisan_fk") == null ? "":psktRs.getString("taisan_fk");
	    				ccdcId = psktRs.getString("ccdc_fk") == null ? "":psktRs.getString("ccdc_fk");
	    				chiphiId = psktRs.getString("CHIPHI_FK") == null ? "":psktRs.getString("CHIPHI_FK");
	    				
	        			soluong = psktRs.getDouble("SOLUONG");
	        			dongia =  psktRs.getDouble("DONGIA");        			
	        		    sotienBVAT = Math.round(soluong*dongia);
	        			
	        		    double VAT = Math.round( psktRs.getDouble("VAT_HOADON") * tygia ) ;    
	                    
	                    String doituongno = "";
	                    String madoituongno = "";
	                    String doituongco = "";
	                    String madoituongco ="";
	                    
	                    doituongco = "Nhà cung cấp";
	            		madoituongco = ncc_fk; 
	            		
	            		String NO_TENDOITUONG = "";
	            		String CO_TENDOITUONG = "";
	            		
	            		if(sanphamId.trim().length()>0)
	                	{
	                		doituongno = "Sản phẩm";
	                		madoituongno = sanphamId;
	                		
	                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_SANPHAM");
	                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
	                		
	                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT");
	                		TAIKHOANCO_VAT = taikhoanncc_fk;
	                		
	                		NO_TENDOITUONG = psktRs.getString("TENSP");
	                		CO_TENDOITUONG = psktRs.getString("TENNCC");
	                	}
	                	
	                	if(ccdcId.trim().length()>0)
	                	{
	                		doituongno = "Công cụ dụng cụ";
	                		madoituongno = ccdcId;
	                		
	                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_CCDK");
	                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
	                		
	                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT");
	                		TAIKHOANCO_VAT = taikhoanncc_fk;
	                		
	                		NO_TENDOITUONG = psktRs.getString("TENCCDC");
	                		CO_TENDOITUONG = psktRs.getString("TENNCC");
	                	}
	                	
	                	if(taisanId.trim().length()>0)
	                	{
	                		doituongno = "Tài sản";
	                		madoituongno = taisanId;
	                		
	                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_TAISAN");
	                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
	                		
	                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT_TSCD");
	                		TAIKHOANCO_VAT = taikhoanncc_fk;
	                		
	                		NO_TENDOITUONG = psktRs.getString("TENTS");
	                		CO_TENDOITUONG = psktRs.getString("TENNCC");
	                	}
	                	
	                	if(chiphiId.trim().length()>0)
	                	{
	                		doituongno = "Chi phí";
	                		madoituongno = chiphiId;
	                		
	                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_CHIPHI");
	                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
	                		
	                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT");
	                		TAIKHOANCO_VAT = taikhoanncc_fk;
	                		
	                		NO_TENDOITUONG = psktRs.getString("TENCHIPHI");
	                		CO_TENDOITUONG = psktRs.getString("TENNCC");
	                	}
	                	
	                	if(sotienBVAT>0)
	                	{
	                		if(laykt.trim().length()>0) laykt+= " UNION ALL ";
	                		laykt +=
	                   		 " SELECT N'NỢ' NO_CO, '" + sochungtu + "' PK_SEQ, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ ='"+TAIKHOANNO_TIENHANG+"' ) SOHIEUTAIKHOAN, "+sotienBVAT+" SOTIEN, '"+NO_TENDOITUONG+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
	                   		 " from ERP_PARK a  \n"+
	                   		 " WHERE a.PK_SEQ = '"+sochungtu+"'" +
	                   		 
	                   		 " UNION ALL \n"+
	                   		 
	                   		 " SELECT N'CÓ' NO_CO, '" + sochungtu + "' PK_SEQ, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+TAIKHOANCO_TIENHANG+"' ) SOHIEUTAIKHOAN, "+sotienBVAT+" SOTIEN, '"+CO_TENDOITUONG+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
	                   		 " from ERP_PARK a \n"+
	                   		 " WHERE a.PK_SEQ = '"+sochungtu+"'" ;
	                		i++;
	                	}
	                	
	                	if(VAT>0)
	                	{
	                		
	                		if(laykt.trim().length()>0) laykt+= " UNION ALL ";
	                		laykt +=
	                   		 " SELECT N'NỢ' NO_CO, '" + sochungtu + "' PK_SEQ, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ ='"+TAIKHOANNO_VAT+"' ) SOHIEUTAIKHOAN, "+sotienBVAT+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
	                   		 " from ERP_PARK a  \n"+
	                   		 " WHERE a.PK_SEQ = '"+sochungtu+"'" +
	                   		 
	                   		 " UNION ALL \n"+
	                   		 
	                   		 " SELECT N'CÓ' NO_CO, '" + sochungtu + "' PK_SEQ, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+TAIKHOANCO_VAT+"' ) SOHIEUTAIKHOAN, "+sotienBVAT+" SOTIEN, '"+CO_TENDOITUONG+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
	                   		 " from ERP_PARK a \n"+
	                   		 " WHERE a.PK_SEQ = '"+sochungtu+"'" ;
	                		i++;
	                	}
	                   
		            }
		        }
		       
				
				if(laykt.trim().length()>0) laykt += " ORDER BY PK_SEQ, STT, SAPXEP \n";
				
				if(laykt.trim().length()<=0)
					{laykt +=
						 " SELECT N'' NO_CO, '' PK_SEQ, '' SOHIEUTAIKHOAN, ' ' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+
						 " FROM ERP_HOADONNCC a inner join ERP_PARK b on a.park_fk = b.pk_seq  " +
						 "	WHERE b.PK_SEQ = '"+sochungtu+"'" ;
					}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
		
	return laykt;
}

	public void init(String search)
	{
		String query = "";
		this.getNppInfo();
		
		//LOAICHUNGTU = 2: HỦY PHIẾU CHI
		//LOAICHUNGTU = 3: HỦY HÓA ĐƠN NCC
		//LOAICHUNGTU = 1: HỦY PHIẾU THU
		
//		if(search.length() > 0)
//			query = search;
//		else
			query = "	SELECT a.PK_SEQ as SOPHIEU,isnull(a.SOCHUNGTU,'') SOCHUNGTU, a.SOCHUNGTUGOC, case a.LOAICHUNGTU when 2 then N'PHIẾU CHI' else N'HÓA ĐƠN NCC' end as LOAICHUNGTU ,a.TRANGTHAI, a.NGAYTAO, a.NGAYSUA, b.TEN as NGUOITAO, c.TEN as NGUOISUA, a.LOAICHUNGTU LOAI \n " +
			   		"	FROM ERP_HUYCHUNGTUKETOAN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq \n " +
			   		"	WHERE a.congty_fk = '" + congtyId + "' and a.LOAICHUNGTU IN (2,3) ";
			
			if(this.tungay.length() > 0)
				query += " and a.ngaytao >= '" + this.tungay + "'";
			
			if(this.denngay.length() > 0)
				query += " and a.ngaysua <= '" + this.denngay + "'";
			
			if(this.trangthai.length() > 0)
				query += " and a.trangthai = '" + this.trangthai + "'";
			
			if(this.nguoitao.length() > 0)
				query += " and b.TEN like N'%" + this.nguoitao + "%'";
			
			if(this.soCT.length() > 0)
				query += " and a.SOCHUNGTU like '%" + this.soCT + "%'";
			
			
		
		String query_init = createSplittingData_List(50, 10, "SOPHIEU desc ", query);
		
		System.out.println(" INIT___ : "+ query);
		
		ResultSet rs = db.get(query_init);
		
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{					
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("SOCHUNGTUGOC"), rs.getString("LOAI"));
					System.out.println("Dinh khoan "+dk);
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{	
								
								kt = new DinhKhoanKeToan(rsKT.getString("PK_SEQ"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
									 rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
								ktList.add(kt);
							}
							rsKT.close();
						}
												
					// INIT
					
						ht = new ThongTinHienThi();		
						
						ht.setId(rs.getString("SOPHIEU"));
						ht.setSOCHUNGTU(rs.getString("SOCHUNGTU"));
						ht.setloaichungtu(rs.getString("LOAI"));
						ht.setTRANGTHAI(rs.getString("trangthai"));
						ht.setNgaytao(rs.getString("NGAYTAO"));
						ht.setNgaysua(rs.getString("NGAYSUA"));
						ht.setNguoitao(rs.getString("NGUOITAO"));
						ht.setNgaysua(rs.getString("NGAYSUA"));
						ht.setNguoisua(rs.getString("NGUOISUA"));
						ht.setSoChungTuGoc(rs.getString("SOCHUNGTUGOC"));
						
						ht.setLayDinhkhoanKT(ktList);
					
					htList.add(ht);																	
				}
				rs.close();
			}
			
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		this.hienthiList = htList;
		System.out.println("Size:"+this.hienthiList.size());
	}
	
	public void DBclose() 
	{
		
			try 
			{
				if(this.hctmhRs != null)
				this.hctmhRs.close();
			} catch (SQLException e) {}
		
			this.db.shutDown();
	}

	public String getCongtyId()
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}


	public String getsochungtu() {

		return this.soCT;
	}


	public void setsochungtu(String sochungtu) {
		this.soCT= sochungtu;
		
	}

	
	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}

	
	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}
}
