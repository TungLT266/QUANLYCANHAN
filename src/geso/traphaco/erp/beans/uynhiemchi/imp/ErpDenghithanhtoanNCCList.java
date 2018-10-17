package geso.traphaco.erp.beans.uynhiemchi.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.erp.beans.uynhiemchi.IErpDenghithanhtoanNCCList;
import geso.traphaco.erp.beans.uynhiemchi.IErpUynhiemchiList;
import geso.traphaco.center.db.sql.dbutils;

public class ErpDenghithanhtoanNCCList  extends Phan_Trang  implements IErpDenghithanhtoanNCCList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	
	String nccId;
	ResultSet nccRs;
	String htttId;
	ResultSet htttRs;
	
	String nvId;
	ResultSet nvRs;
	
	String loaihoadon;
	ResultSet loaihoadonRs;
	
	String trangthai;
	String msg;
	
	ResultSet tthdRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	String Sochungtu;
	String Sohoadon;
	
	String congtyId;
	String nppdangnhap;
	
	List<IThongTinHienThi> hienthiList;
	
	dbutils db;
	
	public ErpDenghithanhtoanNCCList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nccId = "";
		this.htttId = "";
		this.nvId = "";
		this.loaihoadon = "";
		this.trangthai = "";
		this.msg = "";
		this.congtyId = "";
		this.nppdangnhap =  "";
		
		currentPages = 1;
		num = 1;
		this.Sochungtu="";
		this.Sohoadon="";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		this.db = new dbutils();
		
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

	public String getNccId() 
	{
		return this.nccId;
	}

	public void setNccId(String nccid) 
	{
		this.nccId = nccid;
	}

	public ResultSet getNccList() 
	{
		return this.nccRs;
	}

	public void setNccList(ResultSet ncclist) 
	{
		this.nccRs = ncclist;
	}

	public String getHtttId() 
	{
		return this.htttId;
	}

	public void setHtttId(String htttid) 
	{
		this.htttId = htttid;
	}

	public ResultSet getHtttList()
	{
		return this.htttRs;
	}

	public void setHtttList(ResultSet htttlist)
	{
		this.htttRs = htttlist;	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public void setmsg(String msg)
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getTThoadonList() 
	{
		return this.tthdRs;
	}

	public void setTThoadonList(ResultSet tthdlist) 
	{
		this.tthdRs = tthdlist;	
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
		ResultSet rs = db.get("select count(*) as c from ERP_THANHTOANHOADON");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	private String LayDuLieu(String id) 
	{
		String layKT = "";
	
		try
		{
		  String tiente_fk = "";
		  double tigia = 0;
		  String trichphi_fk = "";
		  
		  String query =" select TTHD.CHENHLECHVND, ISNULL(TTHD.PHINGANHANG, 0) AS PHINGANHANG , TTHD.TRICHPHI, \n" +
					   "         ISNULL(TTHD.VAT,0) AS VAT,  TTHD.HTTT_FK as HINHTHUCTT, TTHD.TIENTE_FK, TTHD.KHACHHANG_FK, TTHD.DVTH_FK,  \n"+
					   "	     TTHD.NGAYGHINHAN ,TTHD.NCC_FK , TTHD.NGANHANG_FK , TTHD.NGANHANG_TP_FK , isnull(TTHD.TIGIA,1) as TIGIA, \n" +
					   "         ISNULL(TTHD.CHENHLECHVND,0) AS CHENHLECHVND , TTHD.NHANVIEN_FK, TTHD.SOTAIKHOAN_TP, TTHD.SOTAIKHOAN \n"+
				       " from   ERP_THANHTOANHOADON TTHD  left join ERP_NHACUNGCAP NCC on TTHD.NCC_FK= NCC.PK_SEQ \n" +
				       "                                  left join ERP_NHANVIEN NV on TTHD.NHANVIEN_FK = NV.PK_SEQ \n"+
				       " where TTHD.PK_SEQ = "+ id +"  ";
		  		
			ResultSet rs= db.get(query);
			if(rs!= null)
			{
				while(rs.next())
				{
					tiente_fk = rs.getString("tiente_fk");
					tigia = rs.getDouble("tigia");
					trichphi_fk = rs.getString("TRICHPHI")== null ?"":rs.getString("TRICHPHI") ;
					
					String nganhang_fk= rs.getString("NGANHANG_FK")== null ?"":rs.getString("NGANHANG_FK") ;
					String nganhangTP_fk= rs.getString("NGANHANG_TP_FK")== null ?"":rs.getString("NGANHANG_TP_FK") ;
					
					String sotaikhoan = rs.getString("SOTAIKHOAN");
					String sotaikhoanNH_TP =  rs.getString("SOTAIKHOAN_TP");
					
					String bophanId = rs.getString("DVTH_FK") == null ? "":rs.getString("DVTH_FK");
					String ncc_fk = rs.getString("NCC_FK") == null ? "":rs.getString("NCC_FK");
					String nhanvien_fk = rs.getString("NHANVIEN_FK") == null ? "":rs.getString("NHANVIEN_FK");
					String khachhang_fk = rs.getString("KHACHHANG_FK") == null ? "":rs.getString("KHACHHANG_FK");
					
					double phinganhang= rs.getDouble("PHINGANHANG");					
					double vat = rs.getDouble("VAT");					
					double chenhlech = rs.getDouble("CHENHLECHVND");
					
					int i =1;
					
				// TIỀN HÓA ĐƠN
					if(ncc_fk.trim().length() > 0)
					{
						layKT = " select N'NỢ' as NO_CO, "+ id +" as id, b.SOHIEUTAIKHOAN,  "
							  +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) " 
							  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) * "+ tigia +" " 
							  + "              end as SOTIEN, a.MA + '-' + a.TEN as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"
							  + " from ERP_NHACUNGCAP a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
							  + " where a.pk_seq  = "+ ncc_fk +" and "
							  +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) " 
							  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) * "+ tigia +" " 
							  + "              end > 0 "
					  + " UNION ALL "
					  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  (select tk.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY nh inner join ERP_TAIKHOANKT tk on nh.TAIKHOAN_FK= tk.PK_SEQ  where nh.SOTAIKHOAN = a.SOTAIKHOAN) as SOHIEUTAIKHOAN, "
					  		    +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) " 
							  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) * "+ tigia +" " 
							  + "              end as SOTIEN, \n"
							  +"               (select MA + '-' + TEN from ERP_NGANHANG where pk_seq = a.NGANHANG_FK ) as DOITUONG, "
					  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP "
					  		  + " from ERP_THANHTOANHOADON a "
					  		  + " where a.pk_seq = " + id +" and "
							  +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) " 
							  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) * "+ tigia +" " 
							  + "              end > 0 ";
						
						 // THUẾ NHẬP KHẨU
						query = "select (select MA + '-' + TEN from ERP_NGANHANG where PK_SEQ = a.NGANHANG_FK) as maNH, \n" +
						" 	    (select sum(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK= "+ id +" and LOAIHD = 4 and LOAITHUE= N'Thuế nhập khẩu') as sotienThueNK, \n" +
						" 	    (select sum(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK= "+ id +" and LOAIHD = 4 and LOAITHUE= N'VAT nhập khẩu') as sotienVATNK, \n" +
						"	     a.SOTAIKHOAN   as taikhoanCO_NH, \n" +
						"        '33330000' as taikhoanNO_ThueNK, \n"+
						"        '33312000' as taikhoanNO_VATNK, \n"+
						"        '11110000' as taikhoanCO_Tienmat, \n" +
						"       a.ngayghinhan \n"+
						"from ERP_THANHTOANHOADON a  left join ERP_NHACUNGCAP d on a.ncc_fk = d.pk_seq \n" +
						"where a.PK_SEQ = '" + id + "'";
						ResultSet rsTNK = db.get(query);
						if(rsTNK!= null)
						{
							while(rsTNK.next())
							{
								if(rsTNK.getDouble("sotienThueNK") > 0)
								{
									if(layKT.trim().length() > 0) layKT += "UNION ALL ";
									
									layKT +=    " select N'NỢ' as NO_CO, "+ id +" as id,  " + rsTNK.getString("taikhoanNO_ThueNK") + " as SOHIEUTAIKHOAN, "
									  		  +	"       "+  rsTNK.getDouble("sotienThueNK") +" as SOTIEN, \n"
											  +"         a.MA + '-' + a.TEN as DOITUONG, "
									  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 3 SAPXEP "
											  + " from ERP_NHACUNGCAP a "
											  + " where a.pk_seq  = "+ ncc_fk +" "
									  + " UNION ALL "
									  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  " + rsTNK.getString("taikhoanCO_NH") + " as SOHIEUTAIKHOAN, "
									  		  +	"       "+  rsTNK.getDouble("sotienThueNK") +" as SOTIEN, \n"
											  +"            N'" + rsTNK.getString("maNH") + "' as DOITUONG, "
									  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 4 SAPXEP ";
								} 
								if(rsTNK.getDouble("sotienVATNK") > 0)
								{
									if(layKT.trim().length() > 0) layKT += "UNION ALL ";
									
									layKT +=    " select N'NỢ' as NO_CO, "+ id +" as id,  " + rsTNK.getString("taikhoanNO_VATNK") + " as SOHIEUTAIKHOAN, "
									  		  +	"       "+  rsTNK.getDouble("sotienVATNK") +" as SOTIEN, \n"
											  +"         a.MA + '-' + a.TEN as DOITUONG, "
									  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 5 SAPXEP "
											  + " from ERP_NHACUNGCAP a "
											  + " where a.pk_seq  = "+ ncc_fk +" "
									  + " UNION ALL "
									  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  " + rsTNK.getString("taikhoanCO_NH") + " as SOHIEUTAIKHOAN, "
									  		  +	"       "+  rsTNK.getDouble("sotienVATNK") +" as SOTIEN, \n"
											  +"            N'" + rsTNK.getString("maNH") + "' as DOITUONG, "
									  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 6 SAPXEP ";
								}
								
								  		 
									
							}
							rsTNK.close();
						}
						
					}
					else if(nhanvien_fk.trim().length() > 0)
					{
						layKT = " select N'NỢ' as NO_CO, "+ id +" as id, b.SOHIEUTAIKHOAN,  "
								  +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (1,5,6,8) ),0) " 
								  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (1,5,6,8) ),0) * "+ tigia +" " 
								  + "              end as SOTIEN, a.MA + '-' + a.TEN as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"
								  + " from ERP_NHANVIEN a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
								  + " where a.pk_seq  = "+ nhanvien_fk +" "
						  + " UNION ALL "
						  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  (select tk.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY nh inner join ERP_TAIKHOANKT tk on nh.TAIKHOAN_FK= tk.PK_SEQ  where nh.SOTAIKHOAN = a.SOTAIKHOAN)  as SOHIEUTAIKHOAN, "
						  		    +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (1,5,6,8) ),0) " 
								  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (1,5,6,8)),0) * "+ tigia +" " 
								  + "              end as SOTIEN, \n"
								  +"               (select MA + '-' + TEN from ERP_NGANHANG where pk_seq = a.NGANHANG_FK )  as DOITUONG, "
						  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP "
						  		  + " from ERP_THANHTOANHOADON a "
						  		  + " where a.pk_seq = " + id +" ";
					}else if(khachhang_fk.trim().length() > 0) // Khách hàng
					{
						layKT = " select N'NỢ' as NO_CO, "+ id +" as id, b.SOHIEUTAIKHOAN,  "
								  +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (7) ),0) " 
								  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (7) ),0) * "+ tigia +" " 
								  + "              end as SOTIEN, a.MA + '-' + a.TEN as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"
								  + " from ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
								  + " where a.pk_seq  = "+ khachhang_fk +" "
						  + " UNION ALL "
						  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  (select tk.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY nh inner join ERP_TAIKHOANKT tk on nh.TAIKHOAN_FK= tk.PK_SEQ  where nh.SOTAIKHOAN = a.SOTAIKHOAN) as SOHIEUTAIKHOAN, "
						  		    +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (7) ),0) " 
								  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (7)),0) * "+ tigia +" " 
								  + "              end as SOTIEN, \n"
								  +"               '' as DOITUONG, "
						  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP "
						  		  + " from ERP_THANHTOANHOADON a "
						  		  + " where a.pk_seq = " + id +" ";
						
					}
					else if(bophanId.trim().length() > 0)
					{

						query =
								 " select distinct (select MA from ERP_NGANHANG where PK_SEQ = TTHD.NGANHANG_FK) as MANGANHANG , NV.MA + ' - ' + NV.TEN as MANV, KT.SOHIEUTAIKHOAN as taikhoanNO_NV,  TTHD.NGAYGHINHAN , \n"+
									       "        ( select b.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ where  a.SoTaiKhoan = TTHD.SOTAIKHOAN ) as taikhoanCO_NH , \n"+
									       "        '11110000' as taikhoan_TIENVND, " +
									       "         case when TTHD.TIENTE_FK = 100000 then (select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADONBOPHAN where THANHTOANHD_FK = "+ id +" and NHANVIEN_FK = NV.PK_SEQ and LOAIHD in (6)  ) " +
									       "              else (select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADONBOPHAN where THANHTOANHD_FK = "+ id +" and NHANVIEN_FK = NV.PK_SEQ  and LOAIHD in (6) ) * TTHD.TIGIA " +
									       "              end as tonggiatri \n"+
									       " from   ERP_THANHTOANHOADON TTHD  inner join ERP_THANHTOANHOADON_HOADONBOPHAN TTHD_HD on TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK \n"+
									       "        INNER JOIN ERP_NHANVIEN NV ON TTHD_HD.NHANVIEN_FK = NV.PK_SEQ \n"+
									       "        INNER JOIN ERP_TAIKHOANKT KT ON NV.TAIKHOAN_FK = KT.PK_SEQ \n"+
									       " where TTHD.PK_SEQ = "+ id +" ";
						ResultSet rsBP = db.get(query);

						if(rsBP != null)
						{
							while(rsBP.next())
							{
								if(layKT.trim().length()> 0) layKT += "UNION ALL ";
								layKT +=   " select 'NỢ' as NO_CO, "+ id +" as id,  "+ rsBP.getString("taikhoanNO_NV") +" as SOHIEUTAIKHOAN, "
							  		     +	"        "+ rsBP.getDouble("tonggiatri") +" as SOTIEN,  '"+ rsBP.getString("MANV") +"' as DOITUONG,  '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+ i +" STT, 1 SAPXEP "

								  + " UNION ALL "+
								          " select 'CÓ' as NO_CO, "+ id +" as id,  '"+ rsBP.getString("taikhoanCO_NH") +"' as SOHIEUTAIKHOAN, "
						  		     +	"        "+ rsBP.getDouble("tonggiatri") +" as SOTIEN,  '"+ rsBP.getString("MANGANHANG") +"' as DOITUONG,  '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+ i +" STT, 2 SAPXEP ";

								i++;
							}
							rsBP.close();
						}
					;
					}
					else  // Khác
					{

						layKT =   " select 'NỢ' as NO_CO, "+ id +" as id,  (select pk_seq from erp_taikhoankt where sohieutaikhoan = a.dinhkhoanno) as SOHIEUTAIKHOAN, "
					  		     +	"       a.SOTIENHD as SOTIEN,  '' as DOITUONG,  '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP "
						  		  + " from ERP_THANHTOANHOADON a "
						  		  + " where a.pk_seq = " + id +" "
						  + " UNION ALL "
						  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  (select tk.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY nh inner join ERP_TAIKHOANKT tk on nh.TAIKHOAN_FK= tk.PK_SEQ  where nh.SOTAIKHOAN = a.SOTAIKHOAN) as SOHIEUTAIKHOAN, "
						  		  +	"       a.SOTIENHD as SOTIEN,  '' as DOITUONG,  '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP "
						  		  + " from ERP_THANHTOANHOADON a "
						  		  + " where a.pk_seq = " + id +" ";
						
					
					}
					
					
				// PHÍ NGÂN HÀNG
					// THANHTOAN: NGOAI TE, TRICH PHI BANG VND						     
					  if(!tiente_fk.equals("100000")&& trichphi_fk.equals("1") )
					  {	
						  if(sotaikhoanNH_TP.trim().length() > 0 && phinganhang > 0)
						  {
							  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
							  layKT +=     " select N'NỢ' as NO_CO, "+ id +" as id , '64250000' as SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
								  		+ "        '' as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a "
								  		+ " where a.pk_seq = '"+ id +"' "
							  + " UNION ALL "
									    + " select 'CÓ' as NO_CO, "+ id +" as id , b.SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
								  		+ "        ISNULL((select MA + '-' + TEN from ERP_NGANHANG where PK_SEQ = a.NGANHANG_FK ), '') as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP    "
								  		+ " from ERP_NGANHANG_CONGTY a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
								  		+ " where SOTAIKHOAN = '"+ sotaikhoanNH_TP +"' ";
							  		
						  }
						  
						  if(vat > 0)
						  {
							  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
							  layKT +=    " select N'NỢ' as NO_CO, "+ id +" as id , '13311000' as SOHIEUTAIKHOAN , "+ vat +" as SOTIEN, "
								  		+ "        '' as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 3 STT, 1 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a "
								  		+ " where a.pk_seq = '"+ id +"' "
							  + " UNION ALL "
									    + " select 'CÓ' as NO_CO, "+ id +" as id ,"
									    + "        (select tk.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY nh inner join ERP_TAIKHOANKT tk on nh.TAIKHOAN_FK = tk.PK_SEQ  where nh.SOTAIKHOAN = '"+ sotaikhoanNH_TP +"' ) as SOHIEUTAIKHOAN ,"
									    + "        "+ vat +" as SOTIEN, "
								  		+ "         (select MA + '-' + TEN from ERP_NGANHANG where PK_SEQ = a.NGANHANG_TP_FK )  as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 3 STT, 2 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a  "
								  		+ " where a.PK_SEQ = '"+ id +"' ";
						  }
						  
					  }
					  else
					  {
						  if(sotaikhoan.trim().length() > 0 && phinganhang > 0)
						  {
							  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
							  layKT +=     " select N'NỢ' as NO_CO, "+ id +" as id , '64250000' as SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
								  		+ "        '' as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a "
								  		+ " where a.pk_seq = '"+ id +"' "
							  + " UNION ALL "
									    + " select 'CÓ' as NO_CO, "+ id +" as id , b.SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
								  		+ "        ISNULL((select MA + '-' + TEN from ERP_NGANHANG where PK_SEQ = a.NGANHANG_FK ), '') as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP    "
								  		+ " from ERP_NGANHANG_CONGTY a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
								  		+ " where SOTAIKHOAN = '"+ sotaikhoan +"' ";
							  		
						  }
						  
						  if(vat > 0)
						  {
							  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
							  layKT +=    " select N'NỢ' as NO_CO, "+ id +" as id , '13311000' as SOHIEUTAIKHOAN , "+ vat +" as SOTIEN, "
								  		+ "        '' as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 3 STT, 1 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a "
								  		+ " where a.pk_seq = '"+ id +"' "
							  + " UNION ALL "
									    + " select 'CÓ' as NO_CO, "+ id +" as id ,"
									    + "       ( select tk.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY nh inner join ERP_TAIKHOANKT tk on nh.TAIKHOAN_FK = tk.PK_SEQ  where nh.SOTAIKHOAN = '"+ sotaikhoan +"' ) as SOHIEUTAIKHOAN ,"
									    + "        "+ vat +" as SOTIEN, "
								  		+ "        (select MA + '-' + TEN from ERP_NGANHANG where PK_SEQ = a.NGANHANG_FK ) as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 3 STT, 2 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a  "
								  		+ " where a.PK_SEQ = '"+ id +"' ";
						  } 
					  }
					  
					  // CHÊNH LỆCH
					  if(chenhlech != 0)
					  {
						  if(chenhlech > 0)
						  {
							  if(nhanvien_fk.trim().length() > 0)
							  {
								  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
								 layKT += " select N'NỢ' as NO_CO , "+ id +" as id, '63580000' as SOHIEUTAIKHOAN, "+ chenhlech +" as SOTIEN, '' as DOITUONG,"
								 	   + "        '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 1 SAPXEP  "
								 	   + " from  ERP_THANHTOANHOADON a "
								 	   + " where a.pk_seq = " + id +" "
								 + " UNION ALL "
								       + " select  'CÓ' as NO_CO , "+ id +" as id, b.SOHIEUTAIKHOAN ,"+ chenhlech +" as SOTIEN,"
								       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 2 SAPXEP "
								       + " from ERP_NHANVIEN a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
								       + " where a.pk_seq = "+ nhanvien_fk +" ";
										 
							  }else if(ncc_fk.trim().length() > 0)
							  {
								  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
								  layKT += " select N'NỢ' as NO_CO , "+ id +" as id, '63580000' as SOHIEUTAIKHOAN, "+ chenhlech +" as SOTIEN, '' as DOITUONG,"
									 	   + "        '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 1 SAPXEP  "
									 	   + " from  ERP_THANHTOANHOADON a "
									 	   + " where a.pk_seq = " + id +" "
									 + " UNION ALL "
									       + " select  'CÓ' as NO_CO , "+ id +" as id, b.SOHIEUTAIKHOAN ,"+ chenhlech +" as SOTIEN,"
									       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 2 SAPXEP "
									       + " from ERP_NHACUNGCAP a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
									       + " where a.pk_seq = "+ ncc_fk +" ";
							  }
							  
						  }
						  else
						  {
							  if(nhanvien_fk.trim().length() > 0)
							  {
								  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
								  layKT +=  " select  N'NỢ' as NO_CO , "+ id +" as id, b.SOHIEUTAIKHOAN ,"+ chenhlech*(-1) +" as SOTIEN,"
									       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 1 SAPXEP "
									       + " from ERP_NHANVIEN a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
									       + " where a.pk_seq = "+ nhanvien_fk +" "+
								
								  " UNION ALL "
										   + " select 'CÓ' as NO_CO , "+ id +" as id, '51580000' as SOHIEUTAIKHOAN, "+ chenhlech*(-1) +" as SOTIEN, '' as DOITUONG,"
									 	   + "        '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 2 SAPXEP  "
									 	   + " from  ERP_THANHTOANHOADON a "
									 	   + " where a.pk_seq = " + id +" ";
										 
							  }else if(ncc_fk.trim().length() > 0)
							  {
								  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
								  layKT +=  " select  N'NỢ' as NO_CO , "+ id +" as id, b.SOHIEUTAIKHOAN ,"+ chenhlech*(-1) +" as SOTIEN,"
									       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 1 SAPXEP  "
									       + " from ERP_NHACUNGCAP a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
									       + " where a.pk_seq = "+ ncc_fk +" "+
								
								  " UNION ALL "
										   + " select 'CÓ' as NO_CO , "+ id +" as id, '51580000' as SOHIEUTAIKHOAN, "+ chenhlech*(-1) +" as SOTIEN, '' as DOITUONG,"
									 	   + "        '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 2 SAPXEP  "
									 	   + " from  ERP_THANHTOANHOADON a "
									 	   + " where a.pk_seq = " + id +" ";
							  }
						  }
						  
					  }
					  
			    	  
					
				}
				rs.close();
			}
		
		
		//TIỀN VAT, PHÍ NGÂN HÀNG, CHÊNH LỆCH
			
			
			if(layKT.trim().length()<=0)
			{
				layKT +=
				 " SELECT N'' NO_CO, '' id, '' SOHIEUTAIKHOAN, '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+
				 " FROM ERP_HOADONNCC a " +
				 "	WHERE a.PK_SEQ = '"+id+"'" ;
			}
					                             
			layKT += "ORDER BY id, STT, SAPXEP ";
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return layKT;
	}
	
	public void init(String search)
	{
		this.getNppInfo();
		
		String query = "";
		if(search.length() <= 0)
		{
			query = "SELECT distinct a.loaithanhtoan,  a.pk_seq as tthdId, a.trangthai, a.ngayghinhan, a.ngaytao, a.ngaysua, a.phinganhang, a.vat , isnull(a.thanhtoantutienvay,0) as thanhtoantutienvay,  \n" +
					" case when a.ncc_fk is not null then  isnull(b.ten,'') \n" +
					"      else (case when nv.ten is not null then isnull(nv.ten,'') \n" +
					"                 when kh.ten is not null then isnull(kh.ten,'') \n" +
					"                 when dvth.ten is not null then isnull(dvth.ten,'') \n" +
					"                 else isnull(f.DIENGIAI,'') end) end  as nccTen, \n" +
					"c.ten as htttTen , isnull(a.iskttduyet,0) as iskttduyet, \n" +
					"d.ten as nguoitao, e.ten as nguoisua, a.PREFIX \n" +
					"    ,case \n" +
					"   	when a.NCC_FK is not null then \n" +
					"   			(isnull((select cast(isnull(AA.sohoadon,'') as varchar(20)) +', ' as [text()] \n" +
					"   	   		from  \n" +
					"   	   		(  select hd.sohoadon, hd.PK_SEQ, tthd.loaihd " +
					"                  from ERP_HOADONNCC hd inner join erp_thanhtoanhoadon_hoadon tthd on hd.pk_seq = tthd.hoadon_fk " +
					"                  where tthd.loaihd = 0 and tthd.thanhtoanhd_fk = a.pk_seq  and hd.CONGTY_FK = "+this.congtyId+"  " +
					"   	   		) AA \n" +
					"   	   		For XML PATH ('')),'')) \n" +
					"   	else '' \n" +
					"   	end	as sohoadon \n" +
					"FROM ERP_THANHTOANHOADONNCC a left  join ERP_NHACUNGCAP b on a.ncc_fk = b.pk_seq  \n" +
					"LEFT JOIN NHOMNHACUNGCAPCN f on a.NHOMNCCCN = f.PK_SEQ \n" +
					"LEFT JOIN NHOMNHACUNGCAPCN_NCC g on f.PK_SEQ = g.NHOMNHACUNGCAPCN_FK \n" +
					"LEFT JOIN ERP_NHANVIEN nv on nv.pk_seq =  a.nhanvien_fk  \n" +
					"LEFT JOIN ERP_KHACHHANG kh on kh.pk_seq = a.khachhang_fk \n" +
					"LEFT JOIN ERP_DONVITHUCHIEN dvth on dvth.pk_seq = a.dvth_fk \n" +
					"INNER JOIN ERP_HINHTHUCTHANHTOAN c on a.HTTT_FK = c.pk_seq \n" +
					"INNER JOIN NHANVIEN d on a.nguoitao = d.pk_seq inner join NHANVIEN e on a.nguoisua = e.pk_seq \n" +
					"WHERE ((c.PK_SEQ != 100002) or (c.PK_SEQ = 100002 and a.trangthai = 1) ) and a.HTTT_FK = '100001' and a.CONGTY_FK = "+this.congtyId+" \n";

			
		}
		else
			query = search;
		
		System.out.println("Query init: " + query);
		
		
		String query_init = createSplittingData_List(30, 10, "tthdId desc, NGAYGHINHAN desc, trangthai asc ", query);
		
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
					String dk = LayDuLieu(rs.getString("tthdId"));
					System.out.println("lẤY dl"+dk);
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{

								kt = new DinhKhoanKeToan(rsKT.getString("id"), rsKT.getString("no_co"),rsKT.getString("sohieutaikhoan"),rsKT.getString("sotien"),rsKT.getString("doituong"),
										 rsKT.getString("trungtamchiphi"),rsKT.getString("trungtamdoanhthu"), "");
								ktList.add(kt);
							}
							rsKT.close();
						}
												
					// INIT
					
						ht = new ThongTinHienThi();
						ht.setId(rs.getString("tthdId"));
						ht.setPREFIX(rs.getString("PREFIX"));
						ht.setNgayghinhan(rs.getString("ngayghinhan"));
						ht.setSohoadon(rs.getString("sohoadon"));
						ht.setKhachhang(rs.getString("nccTen"));
						ht.setHttt(rs.getString("htttTen"));
						ht.setTrangthai(rs.getString("trangthai"));
						ht.setNgaytao(rs.getString("NGAYTAO"));
						ht.setNgaysua(rs.getString("NGAYSUA"));
						ht.setNguoitao(rs.getString("NGUOITAO"));
						ht.setNguoisua(rs.getString("NGUOISUA"));
						ht.setvat(rs.getString("vat"));
						ht.setphinganhang(rs.getString("phinganhang"));
						ht.setthanhtoantutienvay(rs.getString("thanhtoantutienvay"));
						ht.setIsKTTDuyet(rs.getString("iskttduyet"));
						
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
		
		//this.tthdRs = createSplittingData(50, 10, "tthdId desc, NGAYGHINHAN desc, trangthai asc ", query);

		this.nccRs = db.get("select pk_seq, ma + ', ' + ten as nccTen from erp_nhacungcap where trangthai = '1' AND CONGTY_FK = "+this.congtyId+"");
		this.nvRs = db.get("select pk_seq, ma + ', ' + ten as nvTen from erp_nhanvien where trangthai = '1' AND CONGTY_FK = "+this.congtyId);
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1'");
		
		String sql = "select 0 as pk_seq, N'Hóa đơn NCC' as ten \n"+
				     "union all \n"+
					 "select 1 as pk_seq, N'Đề nghị tạm ứng' as ten \n"+
					 "union all \n"+
					 "select 2 as pk_seq, N'Chi phí nội bộ' as ten \n"+
					 "union all \n"+
					 "select 3 as pk_seq, N'Chi phí nhận hàng' as ten \n"+
					 "union all \n"+
					 "select 4 as pk_seq, N'Thuế nhập khẩu' as ten \n"+
					 "union all \n"+
					 "select 5 as pk_seq, N'Chi phí khác' as ten \n"+
					 "union all \n"+
					 "select 6 as pk_seq, N'Đề nghị thanh toán' as ten \n"+
					 "union all \n"+
					 "select 7 as pk_seq, N'Khách hàng trả trước' as ten \n"+
					 "union all \n"+
					 "select 8 as pk_seq, N'Chi đề nghị thanh toán' as ten \n"+
					 "union all \n"+
					 "select 9 as pk_seq, N'Chi đề nghị tạm ứng' as ten \n"+
				     "";
		this.loaihoadonRs = db.get(sql);
	}

	public void DBclose() 
	{
		
			try {
				if(this.nccRs != null)
					this.nccRs.close();
				
				if(this.htttRs != null)
					this.htttRs.close();
				
				if(this.tthdRs != null)
					this.tthdRs.close();
				this.db.shutDown();		
				
			} catch (SQLException e) {e.printStackTrace();}
	}

	
	public String getSochungtu() {
		
		return this.Sochungtu;
	}

	
	public void setSochungtu(String sochungtu) {
		
		this.Sochungtu=sochungtu;
	}

	
	public String getSohoadon() {
		
		return this.Sohoadon;
	}

	
	public void setSohoadon(String sohoadon) {
		
		this.Sohoadon=sohoadon;
	}

	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
	}
	
	public void setNvId(String nvid) 
	{
		this.nvId = nvid;
	}
	
	public String getNvId() 
	{
		return this.nvId;
	}

	public ResultSet getNvList() 
	{
		return this.nvRs;
	}

	public void setNvList(ResultSet nvlist) 
	{
		this.nvRs = nvlist;
	}
	
	public String getLoaihoadon() 
	{
		return this.loaihoadon;
	}

	public void setLoaihoadon(String loaihoadon) 
	{
		this.loaihoadon = loaihoadon;
	}

	public ResultSet getLoaihoadonList()
	{
		return this.loaihoadonRs;
	}

	public void setLoaihoadonList(ResultSet loaihoadonRs) 
	{
		this.loaihoadonRs = loaihoadonRs;
		
	}
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	
	public String getnppdangnhap() 
	{
		return this.nppdangnhap;
	}

	public void setnppdangnhap(String nppdangnhap) 
	{
		this.nppdangnhap = nppdangnhap;
	}
	
}
