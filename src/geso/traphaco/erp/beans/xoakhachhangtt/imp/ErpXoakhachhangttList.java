package geso.traphaco.erp.beans.xoakhachhangtt.imp;

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
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.xoakhachhangtt.IErpXoakhachhangttList;

public class ErpXoakhachhangttList  extends Phan_Trang  implements IErpXoakhachhangttList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String maphieu;
	String nccId;
	ResultSet nppRs;
	String htttId;
	ResultSet htttRs;   
	ResultSet xnttRs; 

		
	String trangthai;
	String msg;
	
	ResultSet tthdRs;
	
	String Kbhid;
	ResultSet kbhRs;
	
	String Nhomkhid;
	ResultSet nhomkhRs;
	
	String sotien;
	String congtyId;
	
	String nppdangnhap;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	List<IThongTinHienThi> hienthiList;
	
	dbutils db;
	
	public ErpXoakhachhangttList(String tungay, String denngay, String maphieu, String khachhang, String kenhbanhang, String nhomkhachhang, String sotien, String trangthai)
	{
		this.tungay = tungay;
		this.denngay = denngay;
		this.nccId = khachhang;
		System.out.println("khachhang12321421:"+khachhang);
		this.htttId = "";
		this.trangthai = trangthai;
		this.msg = "";
		this.maphieu = maphieu;
		this.sotien = sotien;
		this.Kbhid =  kenhbanhang;
		this.Nhomkhid = nhomkhachhang;
		
		currentPages = 1;
		num = 1;
		
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
		return this.nppRs;
	}

	public void setNccList(ResultSet ncclist) 
	{
		this.nppRs = ncclist;
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
		ResultSet rs = db.get("select count(*) as c from ERP_xoakhachhangtt");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	private String LayDuLieu(String id) {
		String query = "";
		
		if(query.trim().length()<=0){
			query = "SELECT '' NO_CO, '' PK_SEQ, '' NGAYHOADON, '' SOTIEN, '' SOHIEUTAIKHOAN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n " +
					"FROM ERP_THUENHAPKHAU \n " +
					"WHERE PK_SEQ = '"+id+"'";
		}
		return query;
	}
	
	
	public void init(String search)
	{		
		Utility util = new Utility();
		// Load danh sách khách hàng còn tiền trả trước
		String query =		
				" SELECT KH.PK_SEQ, KH.MA , KH.TENXUATHD TEN, SUM(TT.SOTIENTT - ISNULL(DATHANHTOAN.TIENTHANHTOAN,0)) as SOTIEN \n"+
				" FROM ERP_THUTIEN TT INNER JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
				"	 LEFT JOIN  \n"+
				"	 ( \n"+
				"	  select XKH.KHACHHANG_FK, XKHTT.cttt_fk, SUM(XKHTT.tienthanhtoan) as TIENTHANHTOAN \n"+
				"	  from ERP_XOAKHTRATRUOC_CTTT XKHTT INNER JOIN ERP_XOAKHTRATRUOC XKH ON XKH.PK_SEQ = XKHTT.xoakhtratruoc_fk  \n"+
				"	       INNER JOIN ERP_KHACHHANG KKH ON XKH.KHACHHANG_FK = KKH.PK_SEQ \n"+
				"	  where  XKH.LOAIXOATRATRUOC = 0 AND XKH.TRANGTHAI != 2 \n"+
				"	  group by XKH.KHACHHANG_FK, XKHTT.cttt_fk \n"+
		        "   UNION "+
				"	select d.KHACHHANG_FK, c.HOADON_FK cttt_fk, sum(c.SOTIENTT) as TIENTHANHTOAN \n" +
				"	from ERP_THANHTOANHOADON_HOADON c \n" +
				"	inner join ERP_THANHTOANHOADON d on c.TTHOADON_FK = d.PK_SEQ  \n" +
				"	where d.trangthai not in (2,3) and c.LOAIHD = 7 \n"+
				"   group by d.KHACHHANG_FK, c.HOADON_FK "+
				"	 )DATHANHTOAN ON TT.KHACHHANG_FK = DATHANHTOAN.KHACHHANG_FK AND TT.PK_SEQ = DATHANHTOAN.cttt_fk \n"+
				" WHERE TT.TRANGTHAI = '1' AND TT.NOIDUNGTT_FK = 100001 \n";
		
		
		
		
		
				if(this.Kbhid.trim().length()>0){
					query+=" and TT.KBH_FK ="+this.Kbhid+"";
				}		
				
				if(this.tungay.trim().length()>0)
				{
					query+=" and TT.NGAYCHUNGTU >='"+this.tungay+"'";
				}
				
				if(this.denngay.trim().length()>0)
				{
					query+=" and TT.NGAYCHUNGTU <='"+this.denngay+"'";
				}
				
				if(this.nccId.trim().length()>0)
				{
					query+=" and TT.KHACHHANG_FK ="+this.nccId+"";
				}
				
				if(this.Nhomkhid.trim().length()>0)
				{
					query += " and TT.khachhang_fk in (SELECT KHACHHANG_FK FROM ERP_KHACHHANG WHERE NHOMKHACHHANG_FK = "+this.Nhomkhid+")";
				}
		query+=
				" GROUP BY KH.PK_SEQ, KH.MA, KH.TENXUATHD \n"+
				" HAVING SUM(TT.SOTIENTT - ISNULL(DATHANHTOAN.TIENTHANHTOAN,0)) > 0 \n";
		if(this.sotien.trim().length()>0)
		{
			query+=" and SUM(TT.SOTIENTT - ISNULL(DATHANHTOAN.TIENTHANHTOAN,0)) = "+this.sotien+"";
		}
		
		
		if(this.tungay.length() > 0)
			query += " and a.ngaychungtu >= '" + this.tungay + "'";
		
		if(this.denngay.length() > 0)
			query += " and a.ngaychungtu <= '" + this.denngay + "'";
		
		if(this.nccId.length() > 0)
			query += " and b.pk_seq = '" + this.nccId + "'";
		

		if(this.maphieu.length() > 0)
			query += " and a.pk_seq like '%" + this.maphieu + "%'";
		
		if(this.Kbhid.length()>0)
			query += " and a.khachhang_fk in (SELECT KHACHHANG_FK FROM ERP_KHACHHANG_KENHBANHANG WHERE KENHBANHANG_FK = "+this.Kbhid+")";
		
		if(this.Nhomkhid.length()>0)
			query += " and a.khachhang_fk in (SELECT KHACHHANG_FK FROM ERP_KHACHHANG WHERE NHOMKHACHHANG_FK = "+this.Nhomkhid+")";
		
		if(this.trangthai.length()>0)
			query+= " and a.trangthai = "+ this.trangthai;
		
		
		
		
		System.out.println("Câu lấy KH  "+ query);
		ResultSet rsKH = db.get(query);
		this.xnttRs = rsKH;
		
		String sql = "";
		if(search.length() <= 0)
		{
		/*	query = "select a.pk_seq as tthdId, a.trangthai, a.ngaychungtu, a.ngayghiso, a.ngaytao, a.ngaysua, \n" +
					" case when a.khachhang_fk is not null then b.ten \n" +
					"      when a.nhanvien_fk is not null then nv.ten \n" +
					"      when a.ncc_fk is not null then ncc.ten end as tendoituong, \n" +
					" 	   d.ten as nguoitao, e.ten as nguoisua, a.ISHUYCHUNGTU \n" +
					" from ERP_XOAKHTRATRUOC a left join KHACHHANG b on a.khachhang_fk = b.pk_seq \n" +
					"     left join ERP_NHANVIEN nv on a.NHANVIEN_FK=nv.PK_SEQ \n" +
					"     left join ERP_NHACUNGCAP ncc on a.NCC_FK= ncc.PK_SEQ  \n" +
					"     inner join NHANVIEN d on a.nguoitao = d.pk_seq inner join NHANVIEN e on a.nguoisua = e.pk_seq \n"+
					" where 1 = 1 AND a.CONGTY_FK = "+this.congtyId;*/
			
			query = "select a.pk_seq as tthdId, a.trangthai,isnull(tt.sochungtu, '') sochungtu, a.ngaychungtu, a.ngayghiso, a.ngaytao, a.ngaysua,isnull(tt.ghichu,'') ghichu, \n"+
					" case when a.khachhang_fk is not null then b.ten \n" +
					" when a.nhanvien_fk is not null then nv.ten \n"+
					" when a.ncc_fk is not null then ncc.ten end as tendoituong, \n"+
					" d.ten as nguoitao, e.ten as nguoisua, a.ISHUYCHUNGTU, \n"+
//					" CASE WHEN a.TIENTE_FK = 100000 \n"+
//					" THEN (case when tt.noidungtt_fk != 100002 then ISNULL(tt.THUDUOC, 0) \n"+
//					" else isnull((select sum(sotien) from erp_thutien_dinhkhoanco where thutien_fk = a.pk_seq),0) end ) \n"+
//					" ELSE (case when tt.noidungtt_fk != 100002 then ISNULL(tt.THUDUOCNT, 0) \n"+
//					" else isnull((select sum(sotiennt) from erp_thutien_dinhkhoanco where thutien_fk = a.pk_seq),0) end ) \n"+
//					" END AS THUCTHU ,\n"+
					" isnull(cttt.tienthanhtoan, '0') AS THUCTHU,isnull(a.tongthanhtoan,0) as tongthanhtoan," +
					"  CASE WHEN ( tt.noidungtt_fk=100006) then b.maFAST \n"+
					" when tt.NHANVIEN_FK is not null then (select TEN from ERP_NHANVIEN where PK_SEQ = a.NHANVIEN_FK ) "+
					" when npp.PK_SEQ is not null then npp.MaFAST "+
					" when nppk.PK_SEQ is not null then nppk.MaFAST  when nvk.PK_SEQ is not null then nvk.MA  when b.PK_SEQ is not null then b.MaFAST    when ncc.PK_SEQ is not null then ncc.MA   when ncck.PK_SEQ is not null then ncck.ma  when  nhk.PK_SEQ is not null then nhk.ma when  spk.PK_SEQ is not null then spk.ma when  dtk.PK_SEQ is not null then dtk.MADOITUONG  when  tkk.PK_SEQ is not null then tkk.sohieutaikhoan  else ( case when tt.noidungtt_fk=100006 then  (select ma from KhachHang_ThuTien_ERP khq where khq.LOAI = 1 and khq.npp_fk=tt.NPPCN_FK and cast(a.khachhang_fk as nvarchar) = khq.pk_seq  ) "+ 
					" when tt.NPPCN_FK is null then isnull(npp.maFast,'')    else  ( select ma from KhachHang_ThuTien_ERP khq where khq.LOAI = 1 and cast(a.khachhang_fk as nvarchar) = khq.pk_seq ) end  "+
					" ) end as madoituong "+
					" from ERP_XOAKHTRATRUOC a left join KHACHHANG b on a.khachhang_fk = b.pk_seq \n"+
					" left join ERP_XOAKHTRATRUOC_CTTT cttt on cttt.xoakhtratruoc_fk = a.PK_SEQ \n"+
					 " left join ERP_THUTIEN tt on tt.pk_seq = cttt.cttt_fk "+
					" left join nhaphanphoi npp on tt.NPPCN_FK is null and npp.pk_seq= a.khachhang_fk and a.isnpp=1 "+
					"   LEFT JOIN(  SELECT ROW_NUMBER ( ) OVER ( PARTITION BY THUTIEN_FK order by THUTIEN_FK )as STT,  THUTIEN_FK,DOITUONGDINHKHOAN,DOITUONG_FK,ISNPP from ERP_THUTIEN_DINHKHOANCO TT  )DKC ON A.PK_SEQ=DKC.THUTIEN_FK AnD DKC.STT=1 "+
					" left join NHAPHANPHOI nppk on nppk.PK_SEQ=dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=5  and DKC.ISNPP=1 and nppk.TRANGTHAI = 1 AND nppk.PK_SEQ != 1 "+
				    " left join ERP_NHANVIEN nvk on nvk.PK_SEQ = dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=6 and nvk.TRANGTHAI = 1 "+
					" left join erp_nhacungcap ncck on ncck.PK_SEQ= dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=3 and ncck.TRANGTHAI = 1 "+
				    " left join ERP_NGANHANG nhk on nhk.PK_SEQ = dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=2 and nhk.TRANGTHAI = 1 "+
					" left join ERP_DOITUONGKHAC dtk on dtk.PK_SEQ = dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=8 and dtk.TRANGTHAI = 1 "+
				    " left join ERP_TAIKHOANKT tkk on tkk.PK_SEQ = dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=4 and tkk.trangthai = 1 and tkk.CONGTY_FK = 100000 "+
					" left join SANPHAM spk on spk.PK_SEQ = dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=1 and spk.TRANGTHAI = 1 "+

				     " left join ERP_NHANVIEN nv on a.NHANVIEN_FK=nv.PK_SEQ "+
				     " left join ERP_NHACUNGCAP ncc on a.NCC_FK= ncc.PK_SEQ "+  
				     " inner join NHANVIEN d on a.nguoitao = d.pk_seq inner join NHANVIEN e on a.nguoisua = e.pk_seq "+
					" where 1 = 1  AND a.CONGTY_FK = " + this.congtyId;
			
			if(tungay.trim().length() > 0)
				query += " and a.ngaychungtu >= '" + tungay + "'";
			
			if(denngay.trim().length() > 0)
				query += " and a.ngaychungtu <= '" + denngay + "'";
			
			if(nccId.trim().length() > 0)
				query += " and b.pk_seq = '" + nccId + "'";
			

			if(maphieu.trim().length() > 0)
				query += " and a.pk_seq like '%" + maphieu + "%'";
			
			if(Kbhid.trim().length()>0)
				query += " and a.khachhang_fk in (SELECT KHACHHANG_FK FROM ERP_KHACHHANG_KENHBANHANG WHERE KENHBANHANG_FK = "+Kbhid+")";
			
			if(Nhomkhid.trim().length()>0)
				query += " and a.khachhang_fk in (SELECT KHACHHANG_FK FROM ERP_KHACHHANG WHERE NHOMKHACHHANG_FK = "+Nhomkhid+")";
						
		}
		else
			query = search;
		
		System.out.println("Query init1: " + query);
		
		String query_init = createSplittingData_ListNew(this.db, 30, 10, "tthdId desc ", query);
		
		ResultSet rs = db.get(query_init);
		
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{			
					ht = new ThongTinHienThi();
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("tthdId"));					
					
					
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{
								kt = new DinhKhoanKeToan(rsKT.getString("PK_SEQ"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),
											rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
											rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
								ktList.add(kt);
							}
							
							rsKT.close();
						}
												
					// INIT					
						
						ht.setId(rs.getString("tthdId"));
						ht.setNgaychungtu(rs.getString("ngaychungtu"));
						ht.settendoituong(rs.getString("madoituong"));
						ht.setTrangthai(rs.getString("trangthai"));
						ht.setNGAYTAO(rs.getString("NGAYTAO"));
						ht.setNGUOITAO(rs.getString("NGUOITAO"));
						ht.setNGUOISUA(rs.getString("NGUOISUA"));
						ht.setNGAYSUA(rs.getString("NGAYSUA"));
						ht.setSOTIENVND(rs.getString("tongthanhtoan"));
						ht.setGhichu(rs.getString("ghichu"));
						ht.setSochungtu(rs.getString("sochungtu"));
						ht.setIsHuyChungTu(rs.getString("ISHUYCHUNGTU"));
						
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
		
		
		this.kbhRs = db.get("select pk_seq, diengiai from KENHBANHANG where trangthai = '1'");
		this.nhomkhRs =  db.get("select pk_seq, ma from Erp_NhomKhachHang where trangthai = '1'");
		
		String sql1 = " select cast(PK_SEQ as nvarchar(50)) + ' -- ' + '0' PK_SEQ, mafast + ', ' + isnull(Ten,'') nppTen from KhachHang a where trangthai = '1' AND CONGTY_FK ="+ this.congtyId + " \n"
		//PHAN QUYEN
		+ util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId())
		+ " union all \n"
		+ " select cast(PK_SEQ as nvarchar(50)) + ' -- ' + '1' PK_SEQ, MaFAST + ', ' + isnull(Ten,'') nppTen from NHAPHANPHOI where trangthai = '1' and TRUCTHUOC_FK = "+this.nppdangnhap+" \n"
		+ " and pk_seq in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) "
		+ " union all \n"
		+ " select cast(PK_SEQ as nvarchar(50)) + ' -- ' + '2' PK_SEQ, MA + ', ' + isnull(Ten,'') nppTen from ERP_NHANVIEN where trangthai = '1' AND CONGTY_FK ="+ this.congtyId + "  \n";

		System.out.println(sql1);
		this.nppRs = db.get(sql1);

		//this.nppRs = db.get("select pk_seq, ma + ', ' + TENXUATHD as nppTen from ERP_KHACHHANG where trangthai = '1'");
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1'");
		
	}

	public void DBclose() 
	{
		
			try {
				if(this.nppRs != null)
					this.nppRs.close();
				
				if(this.htttRs != null)
					this.htttRs.close();
				
				if(this.tthdRs != null)
					this.tthdRs.close();
				this.db.shutDown();		
				
			} catch (SQLException e) {e.printStackTrace();
}
		
		
	}
	
	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}


	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}


	public String getMaPhieu() {
		return this.maphieu;
	}

	
	public void setMaPhieu(String maphieu) {
		this.maphieu=maphieu;
		
	}

	
	public ResultSet getxnttList() {
		
		return this.xnttRs;
	}

	
	public void setxnttList(ResultSet xnttList) {
		
		this.xnttRs = xnttList;
	}

	
	public String getKbhId() {
	
		return this.Kbhid;
	}

	
	public void setKbhId(String Kbhid) {
	
		this.Kbhid = Kbhid;
	}

	
	public ResultSet getKbhRs() {
	
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet KbhRs) {
	
		this.kbhRs = KbhRs;
	}

	
	public String getNhomkhId() {
	
		return this.Nhomkhid;
	}

	
	public void setNhomkhId(String Nhomkhid) {
	
		this.Nhomkhid = Nhomkhid;
	}

	
	public ResultSet getNhomkhRs() {
	
		return this.nhomkhRs;
	}

	
	public void setNhomkhRs(ResultSet NhomkhRs) {
	
		this.nhomkhRs = NhomkhRs;
	}


	public String getSotien() {
		
		return this.sotien;
	}


	public void setSotien(String Sotien) {
		
		this.sotien = Sotien;
	}

	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	
	public void setCongtyId(String CongtyId) {
		
		this.congtyId = CongtyId;
	}
	
	Object loainhanvien;
	Object doituongId;

	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}
}
