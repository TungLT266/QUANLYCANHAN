package geso.traphaco.erp.beans.thanhtoanhoadon.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.thanhtoanhoadon.IErpThanhtoanhoadonList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErpThanhtoanhoadonList  extends Phan_Trang  implements IErpThanhtoanhoadonList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String congtyId;
	String nppdangnhap;
	
	String nccId;
	String nccTen;
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
	String machungtu;
	String soPost;
	String Sohoadon;
	String doiTuongChiPhiKhac;
	
	String sotien;
	
	List<IThongTinHienThi> hienthiList;
	private List<Erp_Item> doiTuongKhacList;
	private List<Erp_Item> nppList;
	dbutils db;
	String nguoiNhanTien;
	private ResultSet nhomNCCRs;
	private ResultSet khachhangRs;
	private ResultSet nganhangRs;
	private ResultSet PhongBanRs;
	private ResultSet KenhBhRs;
	
	public ErpThanhtoanhoadonList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nccId = "";
		this.nccTen = "";
		this.nvId = "";
		this.loaihoadon = "";
		this.htttId = "";
		this.trangthai = "";
		this.msg = "";
		this.machungtu="";
		this.soPost = "";
		currentPages = 1;
		num = 1;
		this.Sochungtu="";
		this.Sohoadon="";
		this.sotien = "";
		this.congtyId = "";
		this.nppdangnhap = "";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		this.db = new dbutils();
		this.doiTuongChiPhiKhac = "";
		this.nguoiNhanTien = "";
		this.doiTuongKhacList = new ArrayList<Erp_Item>();
		this.nppList = new ArrayList<Erp_Item>();
	}
	

	public ResultSet getNhomNCCRs() {
		return db.get("SELECT PK_SEQ, DIENGIAI AS TEN FROM NHOMNHACUNGCAPCN where TRANGTHAI = 1 and CONGTY_FK = "+this.congtyId+"" ) ;
	}
	public void setNhomNCCRs(ResultSet nhomNCCRs) {
		this.nhomNCCRs = nhomNCCRs;
	}
	public ResultSet getKhachhangRs() {
		return db.get("select pk_seq, MAFAST + ', ' + ten as TEN from NHAPHANPHOI where trangthai = '1' iskhachhang = 1");
	}
	public void setKhachhangRs(ResultSet khachhangRs) {
		this.khachhangRs = khachhangRs;
	}
	public ResultSet getNganhangRs() {
		return db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang  ");
	}
	public void setNganhangRs(ResultSet nganhangRs) {
		this.nganhangRs = nganhangRs;
	}
	public ResultSet getPhongBanRs() {
		return db.getScrol("select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1' ");
	}
	public void setPhongBanRs(ResultSet phongBanRs) {
		PhongBanRs = phongBanRs;
	}
	public ResultSet getKenhBhRs() {
		return db.getScrol("select pk_seq, diengiai ten from KENHBANHANG where trangthai = '1' ");
	}
	public void setKenhBhRs(ResultSet kenhBhRs) {
		KenhBhRs = kenhBhRs;
	}
	@Override
	public String getNccTen() {
		return nccTen;
	}

	@Override
	public void setNccTen(String nccTen) {
		this.nccTen = nccTen;
	}

	@Override
	public String getDoiTuongChiPhiKhac() {
		return doiTuongChiPhiKhac;
	}

	@Override
	public void setDoiTuongChiPhiKhac(String doiTuongChiPhiKhac) {
		this.doiTuongChiPhiKhac = doiTuongChiPhiKhac;
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
		return db.get("select pk_seq, ma + ', ' + ten as nccTen,ten from erp_nhacungcap where trangthai = '1' AND CONGTY_FK = "+this.congtyId+"");
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
		return db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1'");
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
	@Override
	public String getSoPost() {
		return soPost;
	}


	@Override
	public void setSoPost(String soPost) {
		this.soPost = soPost;
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
		  		
		  int i =0;
			ResultSet rs= db.get(query);
			if(rs!= null)
			{
				while(rs.next())
				{
					tiente_fk = rs.getString("tiente_fk");
					tigia = rs.getDouble("tigia");
					trichphi_fk = rs.getString("TRICHPHI")== null ?"":rs.getString("TRICHPHI") ;
					
//					String nganhang_fk= rs.getString("NGANHANG_FK")== null ?"":rs.getString("NGANHANG_FK") ;
//					String nganhangTP_fk= rs.getString("NGANHANG_TP_FK")== null ?"":rs.getString("NGANHANG_TP_FK") ;
					
					String sotaikhoan = rs.getString("SOTAIKHOAN")== null ?"":rs.getString("SOTAIKHOAN") ;
					String sotaikhoanNH_TP =  rs.getString("SOTAIKHOAN_TP");
					
					String bophanId = rs.getString("DVTH_FK") == null ? "":rs.getString("DVTH_FK");
					String ncc_fk = rs.getString("NCC_FK") == null ? "":rs.getString("NCC_FK");
					String nhanvien_fk = rs.getString("NHANVIEN_FK") == null ? "":rs.getString("NHANVIEN_FK");
					String khachhang_fk = rs.getString("KHACHHANG_FK") == null ? "":rs.getString("KHACHHANG_FK");
					
					double phinganhang= rs.getDouble("PHINGANHANG");					
					double vat = rs.getDouble("VAT");					
					double chenhlech = rs.getDouble("CHENHLECHVND");
					
				// TIỀN HÓA ĐƠN
					if(ncc_fk.trim().length() > 0)
					{
						layKT = " select N'NỢ' as NO_CO, "+ id +" as id, b.SOHIEUTAIKHOAN,  "
							  +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) " 
							  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) * "+ tigia +" " 
							  + "              end as SOTIEN, a.MA + '-' + a.TEN as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"
							  + " from ERP_NHACUNGCAP a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
							  + " where a.pk_seq  = "+ ncc_fk
							  
					  + " UNION ALL "
					  		  + " select 'CÓ' as NO_CO, "+ id +" as id, '11110000' as SOHIEUTAIKHOAN, "
					  		    +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) " 
							  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (0,1,2,3,5,6,8) ),0) * "+ tigia +" " 
							  + "              end as SOTIEN, \n"
							  +"               '' as DOITUONG, "
					  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP "
					  		  + " from ERP_THANHTOANHOADON a "
					  		  + " where a.pk_seq = " + id ;
						
						i++;
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
									  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 3 SAPXEP "
											  + " from ERP_NHACUNGCAP a "
											  + " where a.pk_seq  = "+ ncc_fk +" "
									  + " UNION ALL "
									  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  " + rsTNK.getString("taikhoanCO_NH") + " as SOHIEUTAIKHOAN, "
									  		  +	"       "+  rsTNK.getDouble("sotienThueNK") +" as SOTIEN, \n"
											  +"            N'' as DOITUONG, "
									  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU,"+i+" STT, 4 SAPXEP ";
									i++;
								} 
								if(rsTNK.getDouble("sotienVATNK") > 0)
								{
									if(layKT.trim().length() > 0) layKT += "UNION ALL ";
									
									layKT +=    " select N'NỢ' as NO_CO, "+ id +" as id,  " + rsTNK.getString("taikhoanNO_VATNK") + " as SOHIEUTAIKHOAN, "
									  		  +	"       "+  rsTNK.getDouble("sotienVATNK") +" as SOTIEN, \n"
											  +"         a.MA + '-' + a.TEN as DOITUONG, "
									  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 5 SAPXEP "
											  + " from ERP_NHACUNGCAP a "
											  + " where a.pk_seq  = "+ ncc_fk +" "
									  + " UNION ALL "
									  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  " + rsTNK.getString("taikhoanCO_NH") + " as SOHIEUTAIKHOAN, "
									  		  +	"       "+  rsTNK.getDouble("sotienVATNK") +" as SOTIEN, \n"
											  +"            N'' as DOITUONG, "
									  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 6 SAPXEP ";
									i++;
								}
								
								  		 
									
							}
							rsTNK.close();
						}
						
					}else if(nhanvien_fk.trim().length() > 0)
					{
						layKT = " select N'NỢ' as NO_CO, "+ id +" as id, b.SOHIEUTAIKHOAN,  "
								  +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (1,5,6,8) ),0) " 
								  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (1,5,6,8) ),0) * "+ tigia +" " 
								  + "              end as SOTIEN, a.MA + '-' + a.TEN as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"
								  + " from ERP_NHANVIEN a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
								  + " where a.pk_seq  = "+ nhanvien_fk
						  + " UNION ALL "
						  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  '11110000' as SOHIEUTAIKHOAN, "
						  		    +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (1,5,6,8) ),0) " 
								  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (1,5,6,8)),0) * "+ tigia +" " 
								  + "              end as SOTIEN, \n"
								  +"               '' as DOITUONG, "
						  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP "
						  		  + " from ERP_THANHTOANHOADON a "
						  		  + " where a.pk_seq = " + id +" ";
						
						i++;
					}else if(khachhang_fk.trim().length() > 0) // Khách hàng
					{
						layKT = " select N'NỢ' as NO_CO, "+ id +" as id, b.SOHIEUTAIKHOAN,  "
								  +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (6,7,8) ),0) " 
								  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (6,7,8) ),0) * "+ tigia +" " 
								  + "              end as SOTIEN, a.MA + '-' + a.TEN as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"
								  + " from KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
								  + " where a.pk_seq  = "+ khachhang_fk
						  + " UNION ALL "
						  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  '11110000' as SOHIEUTAIKHOAN, "
						  		    +	"         case when "+ tiente_fk +" = 100000 then ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (6,7,8) ),0) " 
								  + "              else ISNULL((select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ id +" and LOAIHD in (6,7,8)),0) * "+ tigia +" " 
								  + "              end as SOTIEN, \n"
								  +"               '' as DOITUONG, "
						  		  + "     '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP "
						  		  + " from ERP_THANHTOANHOADON a "
						  		  + " where a.pk_seq = " + id +" ";
						i++;
						
					}
					else if(bophanId.trim().length() > 0)
					{
						query =
								   " select distinct TTHD.NGANHANG_FK , NV.MA + ' - ' + NV.TEN as MANV, KT.SOHIEUTAIKHOAN as taikhoanNO_NV,  TTHD.NGAYGHINHAN , \n"+
							       "        ( select b.SOHIEUTAIKHOAN from ERP_NGANHANG_CONGTY a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ where  a.SoTaiKhoan = TTHD.SOTAIKHOAN AND a.CONGTY_FK = "+this.congtyId+" ) as taikhoanCO_NH , \n"+
							       "        '11110000' as taikhoan_TIENVND, " +
							       "         case when TTHD.TIENTE_FK = 100000 then (select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADONBOPHAN where THANHTOANHD_FK = "+ id +" and NHANVIEN_FK = NV.PK_SEQ and LOAIHD in (6)  ) " +
							       "              else (select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADONBOPHAN where THANHTOANHD_FK = "+ id +" and NHANVIEN_FK = NV.PK_SEQ  and LOAIHD in (6) ) * TTHD.TIGIA " +
							       "              end as tonggiatri \n"+
							       " from   ERP_THANHTOANHOADON TTHD  inner join ERP_THANHTOANHOADON_HOADONBOPHAN TTHD_HD on TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK \n"+
							       "        INNER JOIN ERP_NHANVIEN NV ON TTHD_HD.NHANVIEN_FK = NV.PK_SEQ \n"+
							       "        INNER JOIN ERP_TAIKHOANKT KT ON NV.TAIKHOAN_FK = KT.PK_SEQ \n"+
							       " where TTHD.PK_SEQ = "+ id;
						ResultSet rsBP = db.get(query);
						if(rsBP != null)
						{
							while(rsBP.next())
							{
								if(layKT.trim().length()> 0) layKT += "UNION ALL ";
								layKT +=   " select N'NỢ' as NO_CO, "+ id +" as id,  "+ rsBP.getString("taikhoanNO_NV") +" as SOHIEUTAIKHOAN, "
							  		     +	"        "+ rsBP.getDouble("tonggiatri") +" as SOTIEN,  '"+ rsBP.getString("MANV") +"' as DOITUONG,  '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+ i +" STT, 1 SAPXEP "

								  + " UNION ALL "+
								          " select 'CÓ' as NO_CO, "+ id +" as id,  "+ rsBP.getString("taikhoan_TIENVND") +" as SOHIEUTAIKHOAN, "
						  		     +	"        "+ rsBP.getDouble("tonggiatri") +" as SOTIEN,  '' as DOITUONG,  '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+ i +" STT, 2 SAPXEP ";

								i++;
							}
							rsBP.close();
						}
					}
					else  // Khác
					{
						query = "select  a.httt_fk, a.SOTIENHD, \n" +
								"    	 '11110000' as taikhoanCO_tienhang, \n"+
								"    	 '13310000' as taikhoanNO_tienthue, \n"+
								"		 isnull(b.TIENHANG, 0) tienhang, isnull(b.TIENTHUE,0) tienthue, 	\n"+
								"    	 ( SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = b.TAIKHOAN_FK ) as taikhoanNO,  \n"
								+ "	  	 case when b.KHACHHANG_FK is not null then kh.ten \n"
								+ "		   when b.KHO_FK is not null then kho.TEN \n"
								+ "		   when b.NCC_FK is not null then ncc.TEN \n"
								+ "		   when b.NGANHANG_FK is not null then nh.TEN \n"
								+ "		   when b.NHANVIEN_FK is not null then nv.TEN \n"
								+ "		   when b.TAISAN_FK   is not null then ts.TEN else '' \n"
								+ "	  end as doituong,   \n" +
								"	  case \n" +
								"		   when b.KHACHHANG_FK is not null then CAST(b.KHACHHANG_FK as nvarchar(50)) \n" +
								"		   when b.KHO_FK is not null then CAST( b.KHO_FK as nvarchar(50)) \n" +
								"		   when b.NCC_FK is not null then CAST (b.NCC_FK as nvarchar(50)) \n" +
								"		   when b.NGANHANG_FK is not null then CAST( b.NGANHANG_FK as nvarchar(50)) \n" +
								"		   when b.NHANVIEN_FK is not null then CAST( b.NHANVIEN_FK as nvarchar(50)) \n" +
								"		   when b.TAISAN_FK is not null then CAST( b.TAISAN_FK as nvarchar(50)) \n" +
								"		   else '' \n" +
								"	  end as madoituong, a.NGAYGHINHAN , a.TIENTE_FK , isnull(a.TIGIA,1) as TIGIA \n" +
								 
								"from ERP_THANHTOANHOADON a inner join ERP_THANHTOANHOADON_PHINGANHANG b on a.PK_SEQ = b.THANHTOANHD_FK \n" +
								"left join KHACHHANG kh on b.KHACHHANG_FK = kh.PK_SEQ \n"
								+ "left join KHO kho on b.KHO_FK = kho.PK_SEQ \n"
								+ "left join ERP_NHACUNGCAP ncc on b.NCC_FK = ncc.PK_SEQ \n"
								+ "left join ERP_NHANVIEN nv on b.NHANVIEN_FK = nv.PK_SEQ \n"
								+ "left join ERP_TAISANCODINH ts on b.TAISAN_FK = ts.PK_SEQ \n"
								+ "left join ERP_NGANHANG nh on nh.PK_SEQ = b.NGANHANG_FK \n"
								+ "where a.PK_SEQ = '"
								+ id + "'";
						System.out.println("Chi khac "+query);
						
						ResultSet ktRs = db.get(query);
						if(ktRs != null)
						{
							
							while(ktRs.next())
							{						
								double tienhang = ktRs.getDouble("TIENHANG");
								double tienthue = ktRs.getDouble("tienthue");
								
								String taikhoanCO = "";
								String taikhoanNO = "";
								
								String loaidoituongco = "";
								String madoiduongco = "";
								
								String loaidoituongno = "";
								String madoiduongno = "";
								
							
								// GHI NHAN SO TIEN TT
								
								if(tienhang > 0)
								{
									taikhoanCO =ktRs.getString("taikhoanCO_tienhang")== null?"": ktRs.getString("taikhoanCO_tienhang");
									taikhoanNO = ktRs.getString("taikhoanNO")== null?"": ktRs.getString("taikhoanNO");
										
									madoiduongno = ktRs.getString("madoituong");
									loaidoituongno = ktRs.getString("doituong");
									
									loaidoituongco = "";
									madoiduongco = "";
									
									if(layKT.trim().length() > 0) layKT += "UNION ALL ";
									 
									layKT +=   " select N'NỢ' as NO_CO, "+ id +" as id, "+taikhoanNO+"  as SOHIEUTAIKHOAN, "
								  		     + tienhang + " as SOTIEN,  N'"+loaidoituongno+"' as DOITUONG,  '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP "
									  		  + " from ERP_THANHTOANHOADON a "
									  		  + " where a.pk_seq = " + id +" "
									  + " UNION ALL "
									  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  '"+taikhoanCO+"' as SOHIEUTAIKHOAN, "
									  		  +	"      "+tienhang+" as SOTIEN,  '"+loaidoituongco+"' as DOITUONG,  '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP "
									  		  + " from ERP_THANHTOANHOADON a "
									  		  + " where a.pk_seq = " + id +" ";
									i++;
								}
								
								
								if(tienthue > 0)
								{
									taikhoanCO =ktRs.getString("taikhoanCO_tienhang")== null?"": ktRs.getString("taikhoanCO_tienhang");
									taikhoanNO = ktRs.getString("taikhoanNO_tienthue")== null?"": ktRs.getString("taikhoanNO_tienthue");
										
									madoiduongno = ktRs.getString("madoituong");
									loaidoituongno = ktRs.getString("doituong");
									
									loaidoituongco = "";
									madoiduongco = "";
									
									loaidoituongco = "";
									madoiduongco = "";
									
									 if(layKT.trim().length() > 0) layKT += "UNION ALL ";
									layKT +=   " select N'NỢ' as NO_CO, "+ id +" as id, "+taikhoanNO+"  as SOHIEUTAIKHOAN, "
								  		     + tienthue + " as SOTIEN,  N'"+loaidoituongno+"' as DOITUONG,  '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP "
									  		  + " from ERP_THANHTOANHOADON a "
									  		  + " where a.pk_seq = " + id +" "
									  + " UNION ALL "
									  		  + " select 'CÓ' as NO_CO, "+ id +" as id,  '"+taikhoanCO+"' as SOHIEUTAIKHOAN, "
									  		  +	"       "+tienthue+" as SOTIEN,  '"+loaidoituongco+"' as DOITUONG,  '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP "
									  		  + " from ERP_THANHTOANHOADON a "
									  		  + " where a.pk_seq = " + id +" ";
									i++;
								}
								
							}
								
							
							ktRs.close();
						}
									
					
					}
					
					
				// PHÍ NGÂN HÀNG
					// THANHTOAN: NGOAI TE, TRICH PHI BANG VND		
					
					if(ncc_fk.trim().length() > 0 || nhanvien_fk.trim().length() > 0 || khachhang_fk.trim().length() > 0 || bophanId.trim().length() > 0)
					{
					  if(!tiente_fk.equals("100000")&& trichphi_fk.equals("1") )
					  {	
						  if(sotaikhoanNH_TP.trim().length() > 0 && phinganhang > 0)
						  {
							  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
							  layKT +=     " select N'NỢ' as NO_CO, "+ id +" as id , '64250000' as SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
								  		+ "        '' as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a "
								  		+ " where a.pk_seq = '"+ id +"' "
							  + " UNION ALL "
									    + " select 'CÓ' as NO_CO, "+ id +" as id , b.SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
								  		+ "        ISNULL((select MA + '-' + TEN from ERP_NGANHANG where PK_SEQ = a.NGANHANG_FK ), '') as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP    "
								  		+ " from ERP_NGANHANG_CONGTY a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
								  		+ " where SOTAIKHOAN = '"+ sotaikhoanNH_TP;
							  		
							  i++;
						  }
						  
						  if(vat > 0)
						  {
							  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
							  layKT +=    " select N'NỢ' as NO_CO, "+ id +" as id , '13311000' as SOHIEUTAIKHOAN , "+ vat +" as SOTIEN, "
								  		+ "        '' as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a "
								  		+ " where a.pk_seq = '"+ id +"' "
							  + " UNION ALL "
									    + " select 'CÓ' as NO_CO, "+ id +" as id ,"
									    + "       '11110000' as SOHIEUTAIKHOAN ,"
									    + "        "+ vat +" as SOTIEN, "
								  		+ "        '' as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a  "
								  		+ " where a.PK_SEQ = '"+ id +"' ";
							  i++;
						  }
						  
					  }
					  else
					  {
						  if(sotaikhoan.trim().length() > 0 && phinganhang > 0)
						  {
							  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
							  layKT +=     " select N'NỢ' as NO_CO, "+ id +" as id , '64250000' as SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
								  		+ "        '' as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU,"+i+" STT, 1 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a "
								  		+ " where a.pk_seq = '"+ id +"' "
							  + " UNION ALL "
									    + " select 'CÓ' as NO_CO, "+ id +" as id , b.SOHIEUTAIKHOAN , "+ phinganhang +" as SOTIEN, "
								  		+ "        ISNULL((select MA + '-' + TEN from ERP_NGANHANG where PK_SEQ = a.NGANHANG_FK ), '') as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP    "
								  		+ " from ERP_NGANHANG_CONGTY a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
								  		+ " where SOTAIKHOAN = '"+ sotaikhoan ;
							  i++;
							  		
						  }
						  
						  if(vat > 0)
						  {
							  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
							  layKT +=    " select N'NỢ' as NO_CO, "+ id +" as id , '13311000' as SOHIEUTAIKHOAN , "+ vat +" as SOTIEN, "
								  		+ "        '' as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a "
								  		+ " where a.pk_seq = '"+ id +"' "
							  + " UNION ALL "
									    + " select 'CÓ' as NO_CO, "+ id +" as id ,"
									    + "        '11110000' as SOHIEUTAIKHOAN ,"
									    + "        "+ vat +" as SOTIEN, "
								  		+ "        '' as DOITUONG,"
								  		+ "        '' TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP    "
								  		+ " from ERP_THANHTOANHOADON a  "
								  		+ " where a.PK_SEQ = '"+ id +"' ";
							  i++;
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
								       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP "
								       + " from ERP_NHANVIEN a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
								       + " where a.pk_seq = "+ nhanvien_fk;
								 i++;
										 
							  }else if(ncc_fk.trim().length() > 0)
							  {
								  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
								  layKT += " select N'NỢ' as NO_CO , "+ id +" as id, '63580000' as SOHIEUTAIKHOAN, "+ chenhlech +" as SOTIEN, '' as DOITUONG,"
									 	   + "        '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, 4 STT, 1 SAPXEP  "
									 	   + " from  ERP_THANHTOANHOADON a "
									 	   + " where a.pk_seq = " + id +" "
									 + " UNION ALL "
									       + " select  'CÓ' as NO_CO , "+ id +" as id, b.SOHIEUTAIKHOAN ,"+ chenhlech +" as SOTIEN,"
									       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP "
									       + " from ERP_NHACUNGCAP a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
									       + " where a.pk_seq = "+ ncc_fk ;
								  i++;
							  }
							  
						  }
						  else
						  {
							  if(nhanvien_fk.trim().length() > 0)
							  {
								  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
								  layKT +=  " select  N'NỢ' as NO_CO , "+ id +" as id, b.SOHIEUTAIKHOAN ,"+ chenhlech*(-1) +" as SOTIEN,"
									       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP "
									       + " from ERP_NHANVIEN a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
									       + " where a.pk_seq = "+ nhanvien_fk +
								
								  " UNION ALL "
										   + " select 'CÓ' as NO_CO , "+ id +" as id, '51580000' as SOHIEUTAIKHOAN, "+ chenhlech*(-1) +" as SOTIEN, '' as DOITUONG,"
									 	   + "        '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP  "
									 	   + " from  ERP_THANHTOANHOADON a "
									 	   + " where a.pk_seq = " + id +" ";
								  i++;
										 
							  }else if(ncc_fk.trim().length() > 0)
							  {
								  if(layKT.trim().length() > 0) layKT += "UNION ALL ";
								  layKT +=  " select  N'NỢ' as NO_CO , "+ id +" as id, b.SOHIEUTAIKHOAN ,"+ chenhlech*(-1) +" as SOTIEN,"
									       + "        a.MA + '-' + a.TEN as DOITUONG, '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP  "
									       + " from ERP_NHACUNGCAP a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ "
									       + " where a.pk_seq = "+ ncc_fk +
								
								  " UNION ALL "
										   + " select 'CÓ' as NO_CO , "+ id +" as id, '51580000' as SOHIEUTAIKHOAN, "+ chenhlech*(-1) +" as SOTIEN, '' as DOITUONG,"
									 	   + "        '' as TRUNGTAMCHIPHI, '' as TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP  "
									 	   + " from  ERP_THANHTOANHOADON a "
									 	   + " where a.pk_seq = " + id +" ";
								  i++;
							  }
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

			String query = "select distinct a.loaithanhtoan,  a.pk_seq as tthdId, a.trangthai, a.ngayghinhan, a.ngaytao, a.ngaysua, a.phinganhang, a.vat , isnull(a.thanhtoantutienvay,0) as thanhtoantutienvay,  \n" +
					" case when len(a.noidungtt) >0 then isnull(a.noidungtt,'') \n" +  
					" else ( \n" +
					" case when a.ncc_fk is not null then  isnull(b.ten,'') \n" +
					"      else (case when nv.ten is not null then isnull(nv.ten,'') \n" +
					"                 when kh.ten is not null then isnull(kh.ten,'') \n" +
					"                 when dvth.ten is not null then isnull(dvth.ten,'') \n" +
					"                 when a.nguoinhantien is not null then isnull(nguoinhantien, '')" +
					"				  when a.NHOMNCCCN is not null then isnull(f.DIENGIAI,'') \n"+
					"                 else isnull(a.NOIDUNGTT,'') end) end) end  as nccTen, \n" +
					" c.ten as htttTen , isnull(a.iskttduyet,0) as iskttduyet,  \n" +
					" d.ten as nguoitao, e.ten as nguoisua, a.PREFIX \n" +
					"    ,CASE \n" +
					"   	WHEN a.NCC_FK is not null or a.NHANVIEN_FK is not null or a.KHACHHANG_FK is not null THEN \n" +
					"   			(isnull((select cast(isnull(CT.sohoadon,'') as varchar(20)) +', ' as [text()] \n" +
					"   	   		from  ERP_THANHTOANHOADON_HOADON CT \n" +
					"               where CT.THANHTOANHD_FK = A.PK_SEQ \n"+
					"   	   		For XML PATH ('')),'')) \n" +
					"   	WHEN a.DVTH_FK is not null THEN \n" +
					"   			(isnull((select cast(isnull(CT.sohoadon,'') as varchar(20)) +', ' as [text()] \n" +
					"   	   		from  ERP_THANHTOANHOADON_HOADONBOPHAN CT \n" +
					"               where CT.THANHTOANHD_FK = A.PK_SEQ \n"+
					"   	   		For XML PATH ('')),'')) \n" +
					"   	ELSE '' \n" +
					"   	END	as sohoadon, A.SOTIENTT, isnull(A.SOCHUNGTU,'') SOCTTUDONG, isnull(a.machungtu,'') machungtu, \n" +
					"  a.ncc_fk , a.httt_fk, a.nganhang_fk, a.chinhanh_fk,a.nhanvien_fk,isnull(a.NHOMNCCCN,'0') nhomncccn ,  " +
					" a.khachhang_fk,a.dvth_fk,a.doiTuongKhac_FK, a.khachHang_NPP_FK, isnull(a.nguoinhantien, '') as nguoinhantien," +
					" case when a.ncc_fk is not null then '1' \n" +
					" when a.nhanvien_fk is not null and a.ncc_fk is null then '0' \n" +
					" when a.khachhang_fk is not null and isnull(a.isTICHLUY,0) = 1 then 5 \n" +
					" when a.khachhang_fk is not null and isnull(a.isTICHLUY,0) != 1 then 2 \n" +
					" when a.dvth_fk is not null then 4 \n" +
					" when a.doiTuongKhac_FK is not null then 6 \n" +
					" when a.khachHang_NPP_FK is not null then 7" +
					" else 3 " +
					"end as doituongId \n" +
					"from ERP_THANHTOANHOADON a left join ERP_NHACUNGCAP b on a.ncc_fk = b.pk_seq  \n" +
					"  left join NHOMNHACUNGCAPCN f on a.NHOMNCCCN = f.PK_SEQ \n" +
					"  left join NHOMNHACUNGCAPCN_NCC g on f.PK_SEQ = g.NHOMNHACUNGCAPCN_FK \n" +
					"  left join ERP_NHANVIEN nv on nv.pk_seq =  a.nhanvien_fk  \n" +
					"  left join nhaPhanPhoi kh on kh.pk_seq = a.khachhang_fk \n" +
					"  left join ERP_DONVITHUCHIEN dvth on dvth.pk_seq = a.dvth_fk \n" +
					"  inner join ERP_HINHTHUCTHANHTOAN c on a.HTTT_FK = c.pk_seq \n" +
					"  inner join NHANVIEN d on a.nguoitao = d.pk_seq inner join NHANVIEN e on a.nguoisua = e.pk_seq \n" +
					/********************************************************************/
					" left join ERP_THANHTOANHOADON_PHINGANHANG PNH on PNH.THANHTOANHD_FK = a.PK_SEQ \n" +						
					" left join erp_nhacungcap ncc on ncc.pk_Seq=a.ncc_fk \n" +		
					" left join khachhang khk on khk.pk_seq = a.khachhang_fk \n" +
					" left join NHOMNHACUNGCAPCN nck on nck.PK_SEQ = a.Nhomncccn \n" +
					" left join ERP_TAIKHOANKT tk on tk.SOHIEUTAIKHOAN = a.dinhkhoanno \n" +
					
					
					
					/********************************************************************/
					
					"where a.HTTT_FK IN ( '100000', '100003') and a.CONGTY_FK = "+this.congtyId+"  \n";

			
			if(this.getTungay().length() > 0)
				query += " and a.ngayghinhan >= '" + this.getTungay() + "'";
			
			if(this.getDenngay().length() > 0)
				query += " and a.ngayghinhan <= '" + this.getDenngay() + "'";
			
		
			
			
			if(this.getNccId().trim().length() > 0){
				query += " AND CASE\r\n" + 
						"		WHEN A.NCC_FK IS NOT NULL THEN\r\n" + 
						"			CONVERT(NVARCHAR, B.PK_SEQ) \r\n" + 
						"		WHEN A.KHACHHANG_FK IS NOT NULL THEN\r\n" + 
						"			CONVERT(NVARCHAR,KH.PK_SEQ) \r\n" + 
						"		WHEN A.NHANVIEN_FK IS NOT NULL THEN\r\n" + 
						"			CONVERT(NVARCHAR,NV.PK_SEQ) \r\n" + 
						"		WHEN A.DVTH_FK IS NOT NULL THEN\r\n" + 
						"			CONVERT(NVARCHAR,DVTH.PK_SEQ) \r\n" + 
						"		WHEN A.NGUOINHANTIEN IS NOT NULL THEN CONVERT(NVARCHAR,NGUOINHANTIEN) \r\n" + 
						"END LIKE N'" + this.getNccId() + "'";
			} else {
				if(this.getNccTen().trim().length() > 0)
					query += " AND CASE\r\n" + 
							"		WHEN A.NCC_FK IS NOT NULL THEN\r\n" + 
							"			B.TEN \r\n" + 
							"		WHEN A.KHACHHANG_FK IS NOT NULL THEN\r\n" + 
							"			KH.TEN \r\n" + 
							"		WHEN A.NHANVIEN_FK IS NOT NULL THEN\r\n" + 
							"			NV.TEN \r\n" + 
							"		WHEN A.DVTH_FK IS NOT NULL THEN\r\n" + 
							"			DVTH.TEN \r\n" + 
							"		WHEN A.NGUOINHANTIEN IS NOT NULL THEN NGUOINHANTIEN \r\n" + 
							"END LIKE N'" + this.getNccTen() + "'";
			}
			//Chi tien khac
			if (this.getDoiTuongChiPhiKhac().trim().equals("3")) {
				query += " AND A.NGUOINHANTIEN IS NOT NULL";
			} else if (this.getDoiTuongChiPhiKhac().trim().equals("1")) {
				//Chi tien nha cung cap
				query += " AND A.NCC_FK IS NOT NULL";
				
				
			} else if (this.getDoiTuongChiPhiKhac().trim().equals("2")) {
				//Chi tien khach hang
				query += " AND A.KHACHHANG_FK IS NOT NULL";
				
			} else if (this.getDoiTuongChiPhiKhac().trim().equals("0")) {
				//Chi tien nhan vien
				query += " AND A.NHANVIEN_FK IS NOT NULL";
				
			}
			if(this.getNvId().length() > 0)
				query += " and nv.pk_seq = '" + this.getNvId() + "'";
			
			if(this.getLoaihoadon().trim().length() > 0)
				query += " and a.pk_seq in ( select thanhtoanhd_fk from ERP_THANHTOANHOADON_HOADON where loaihd in ( " + this.getLoaihoadon() + " ) ) ";
			
			if(this.getTrangthai().length() > 0)
			{
				if(this.getTrangthai().equals("0"))
					query += " and a.trangthai = '0' and isnull(a.iskttduyet,0) = 0 ";
				if(this.getTrangthai().equals("-1"))
					query += " and a.trangthai = '0' and isnull(a.iskttduyet,0) = 1 ";
				if(this.getTrangthai().equals("1"))
					query += " and a.trangthai = '1' ";
				if(this.getTrangthai().equals("2"))
					query += " and a.trangthai = '2' ";
				if(this.getTrangthai().equals("3"))
					query += " and a.trangthai = '3' ";
			}
//			
//			if(this.getSochungtu().length() >0){
//				query+=" and (cast( a.pk_seq as varchar(10)) like '%"+this.getSochungtu()+"%' or cast(a.prefix as varchar(10)) + cast( a.pk_seq as varchar(10)) like '%"+this.getSochungtu()+"%') ";
//				
//			}	
			if(this.Sochungtu.length()>0){
				query+=" and a.sochungtu = '"+this.getSochungtu()+"'";
			}
			
			
//			if(this.machungtu.length()>0){
//				query+=" and a.machungtu = '"+this.getMachungtu()+"'";
//			}
			
			if (this.soPost.length()>0) {
				query+=" and a.machungtu LIKE '%"+this.soPost.trim()+"%'";
			}
			
			if(this.getSohoadon().length() >0){
				query += "  and ( a.pk_seq in (select tt_hd.thanhtoanhd_fk from ERP_THANHTOANHOADON_HOADON tt_hd where tt_hd.sohoadon like '%"+ this.getSohoadon() +"%')"
					   + "       or a.pk_seq in (select tt_hd.thanhtoanhd_fk from ERP_THANHTOANHOADON_HOADONBOPHAN tt_hd where tt_hd.sohoadon like '%"+ this.getSohoadon() +"%') )";
			}	

			
			if(this.getSotien().length()>0){
				query+=" and A.sotientt = "+this.getSotien();
			}
			
//			if(this.getSochungtu().length()>0){
//				query+=" and A.sochungtu = "+this.getSochungtu();
//			}
			
		
			
			
//			if(this.getSohoadon().length() >0){
//				query += "  and ( a.pk_seq in (select tt_hd.thanhtoanhd_fk from ERP_THANHTOANHOADON_HOADON tt_hd where tt_hd.sohoadon like '%"+ this.getSohoadon() +"%')"
//					   + "       or a.pk_seq in (select tt_hd.thanhtoanhd_fk from ERP_THANHTOANHOADON_HOADONBOPHAN tt_hd where tt_hd.sohoadon like '%"+ this.getSohoadon() +"%') )";
//			}
			
		System.out.println("aaaaaaaaaaaaaa: \n"+query);
		
		String query_init = createSplittingData_ListNew(this.db, 30, 10, "NGAYGHINHAN desc, MACHUNGTU desc, trangthai asc ", query);
		
		this.tthdRs = db.get(query_init);
		
		//this.tthdRs = createSplittingData(50, 10, "tthdId desc, NGAYGHINHAN desc, trangthai asc ", query);

		//this.nccRs = db.get("select pk_seq, ma + ', ' + ten as nccTen from erp_nhacungcap where trangthai = '1' AND CONGTY_FK = "+this.congtyId+"");
		//this.nvRs = db.get("select pk_seq, ma + ', ' + ten as nvTen from erp_nhanvien where trangthai = '1' and CONGTY_FK = "+this.congtyId+"");
		//this.nhomNCCRs = db.get("SELECT PK_SEQ, DIENGIAI AS TEN FROM NHOMNHACUNGCAPCN where TRANGTHAI = 1 and CONGTY_FK = "+this.congtyId+"" ) ;
		//this.khachhangRs = db.get("select pk_seq, ma + ', ' + ten as TEN from KhachHang where trangthai = '1' and CONGTY_FK = "+this.congtyId+"");
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1'");
		//this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang  ");
		 
		//this.PhongBanRs = db.getScrol("select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1' ");
		
		//this.KenhBhRs = db.getScrol("select pk_seq, diengiai ten from KENHBANHANG where trangthai = '1' ");
		
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
					 "union all \n"+
					 "select 10 as pk_seq, N'Bút toán tổng hợp' as ten \n";
		System.out.println("loaihd: "+sql);
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
				this.db = null;
			} catch (SQLException e) {e.printStackTrace();}
	}
	public void NewDbUtil()
	{
		  if(this.db == null )
		  {
			   this.db  = new dbutils();
		  }
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
	
	
	public String getNvId() 
	{
		return this.nvId;
	}

	public void setNvId(String nvid) 
	{
		this.nvId = nvid;
	}

	public ResultSet getNvList() 
	{
		return  db.get("select pk_seq, ma + ', ' + ten as nvTen,pk_seq, ten from erp_nhanvien where trangthai = '1' and CONGTY_FK = "+this.congtyId+"");
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

	
	public String getSotien() {
		
		return this.sotien;
	}

	
	public void setSotien(String sotien) {
		
		this.sotien = sotien;
	}

	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
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
	public String getMachungtu() {
		return machungtu;
	}
	public void setMachungtu(String machungtu) {
		this.machungtu = machungtu;
	}
	public void setDoiTuongKhacList(List<Erp_Item> doiTuongKhacList) {
		this.doiTuongKhacList = doiTuongKhacList;
	}

	public List<Erp_Item> getDoiTuongKhacList() {
		return doiTuongKhacList;
	}
	
	public void initDoiTuongKhacList() {
		String query = "";
		this.doiTuongKhacList = new ArrayList<Erp_Item>();
		query = 
			"select PK_SEQ, dt.MADOITUONG + ' - ' + dt.TENDOITUONG ten\n" +
			"from ERP_DoiTuongKhac dt\n" +
			"where dt.TRANGTHAI = 1\n";
		
		System.out.println(query);
		Erp_Item.getListFromQuery(db, query, this.doiTuongKhacList);
	}
	public List<Erp_Item> getNppList() {
		return nppList;
	}

	public void setNppList(List<Erp_Item> nppList) {
		this.nppList = nppList;
	}
	public void initNppList() {
		String query = "";
		query = 
			"select PK_SEQ, isNull(npp.ma, '') + ' - ' + isNull(npp.maFast, '') + ' - ' + isNull(npp.TEN, '') ten\n" +
			"from nhaPhanPhoi npp\n" +
			"where npp.TRANGTHAI = 1 and isKhachHang = 0 and npp.pk_seq != 1\n";
		
		System.out.println(query);
		Erp_Item.getListFromQuery(db, query, this.nppList);
	}
}
