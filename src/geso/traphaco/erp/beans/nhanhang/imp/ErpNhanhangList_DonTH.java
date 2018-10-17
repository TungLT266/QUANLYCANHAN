package geso.traphaco.erp.beans.nhanhang.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.erp.beans.nhanhang.*;

public class ErpNhanhangList_DonTH extends Phan_Trang implements IErpNhanhangList_DonTH
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	
	String dvthId;
	ResultSet dvthRs;
	
	String trangthai;
	String nccTen;
	String soPO;
	String msg;
	
	String sonhanhang;
	String sohoadon;
	
	ResultSet nhRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	List<IThongTinHienThi> hienthiList;
	ResultSet  RsDontrahang_daduyet;
	
	
	dbutils db;
	Utility util;
	
	public ErpNhanhangList_DonTH()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.dvthId = "";
		this.trangthai = "";
		this.soPO = "";
		this.sonhanhang = "";
		this.sohoadon = "";
		this.nccTen = "";
		this.msg = "";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		this.util=new Utility();
		currentPages = 1;
		num = 1;
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

	public String getDvthId() 
	{
		return this.dvthId;
	}

	public void setDvthId(String dvthid) 
	{
		this.dvthId = dvthid;
	}

	public ResultSet getDvthList() 
	{
		return this.dvthRs;
	}

	
	public void setDvthList(ResultSet dvthlist) 
	{
		this.dvthRs = dvthlist;
	}


	public void setmsg(String msg) 
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getNhList() 
	{
		return this.nhRs;
	}

	public void setNhList(ResultSet nhlist) 
	{
		this.nhRs = nhlist;
	}

	private String LayDuLieu(String id) {
		
		String query =  "select a.ngayNHAN as ngaychungtu, a.NGAYCHOT,c.khonhan,  b.khachhang_fk, c.sanpham_fk as spId, d.loaisanpham_fk , "  +
			          	" 		c.soluongnhan , c.dongiaviet,   " +
			          	"		(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = e.TAIKHOAN_FK) as TAIKHOANKTCO_TH,  "+
			          	"		(case  d.loaisanpham_fk when 100041 then '53111000' "+
			          	"							    when 100042 then '53111000' "+
			          	"							    when 100043 then '53112000'  end ) as TAIKHOANKTNO_TH, "+
			          	"		(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = d.loaisanpham_fk ) as TAIKHOANKTNO_GV, "+
			          	"       (case d.loaisanpham_fk when 100041 then '63211000'   " +
						"         					   when 100042 then '63211000'   " +
					    " 						       when 100043 then '63212000'  end ) as TAIKHOANKTCO_GV "+
						"from 	ERP_NHANHANG a  " +
						"     	inner join DonTraHang b on a.trahang_fk = b.pk_seq   "+
					    "     	inner join ERP_NHANHANG_SANPHAM c on a.PK_SEQ = c.NHANHANG_FK  "
					    + "   	inner join ERP_SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
					    " 		INNER JOIN ERP_KHACHHANG e on a.NCC_KH_FK = e.PK_SEQ "+
						" where a.PK_SEQ = '" + id + "'";	
		  
		String ngaychotNV = "";
		String khId = "";
		String khonhan = "";
		String layTK = "";
		String spId= "";
		String loaispId = "" ;
		double soluong = 0;
		double dongiaviet = 0;
		double giaton = 0;
		double thanhtien = 0;
		double thanhtienGT = 0;
		
		String TAIKHOANKTCO_TH ="";
		String TAIKHOANKTNO_TH ="";
		String TAIKHOANKTNO_GV ="";
		String TAIKHOANKTCO_GV ="";
		
		ResultSet rs = db.get(query);
		try
		{
			if(rs!= null)
			{
				while(rs.next())
				{
					khonhan = rs.getString("khonhan");
					ngaychotNV = rs.getString("ngaychot");
					khId = rs.getString("khachhang_fk");
					
					TAIKHOANKTCO_TH = rs.getString("TAIKHOANKTCO_TH");
					TAIKHOANKTNO_TH = rs.getString("TAIKHOANKTNO_TH");
					TAIKHOANKTNO_GV = rs.getString("TAIKHOANKTNO_GV");
					TAIKHOANKTCO_GV = rs.getString("TAIKHOANKTCO_GV");					
					
					int thangtruoc = Integer.parseInt(ngaychotNV.substring(5, 7));
					int namtruoc = Integer.parseInt(ngaychotNV.substring(0, 4));
					
					if(thangtruoc == 1){
						namtruoc = namtruoc-1;
						thangtruoc = 12;
						
					}else{
						thangtruoc = thangtruoc-1;
					}
					
					spId = rs.getString("spId");
					loaispId = rs.getString("loaisanpham_fk");
					soluong = rs.getDouble("soluongnhan");
					dongiaviet = rs.getDouble("dongiaviet");
					
					thanhtien = soluong*dongiaviet;
					
					// LẤY GIATON
					String queryGV = " SELECT GIATON FROM ERP_TONKHOTHANG " +
									" WHERE SANPHAM_FK = " + spId + " AND  THANG = '" + thangtruoc + "' " +
									" AND NAM = '" + namtruoc + "' AND KHOTT_FK = '" + khonhan + "'";
					
					ResultSet rsrs = db.get(queryGV);
					if(queryGV!= null)
					{
						while(rsrs.next())
						{
							giaton = rsrs.getDouble("GIATON");
						}rsrs.close();
					}
					
					thanhtienGT = soluong*giaton;
					
					// GHI NHẬN KẾ TOÁN : TIỀN HÀNG && GIÁ TỒN
					
					//TIỀN HÀNG
					if(thanhtien > 0)
					{
						layTK +=
						" select  N'NỢ' as NO_CO, "+ id +" as id, '"+TAIKHOANKTNO_TH+"' as SOHIEUTAIKHOAN , "+
					    "         "+thanhtien+" as SOTIEN ,  '' as DOITUONG , '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP "+							  
						" from  ERP_LOAISANPHAM b  " +
						" where  b.pk_seq = '" + loaispId + "' "+
	
						" UNION ALL \n"+
	
						" select  N'CÓ' as NO_CO, "+ id +" as id, '"+TAIKHOANKTCO_TH+ "' as SOHIEUTAIKHOAN, "+
						"        "+thanhtien+" as SOTIEN, b.TEN as DOITUONG , '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP "+							  
						" from  erp_khachhang b  " +
						" where  b.pk_seq = '" + khId + "' ";
					}
					
					//GIÁ TỒN
					if(thanhtienGT > 0)
					{
						if(layTK.trim().length() > 0) layTK += "UNION ALL ";
						
						layTK += 
				        " SELECT  N'NỢ' as NO_CO, "+ id +" as id, '"+TAIKHOANKTNO_GV+"' as SOHIEUTAIKHOAN  , "+
				       	"          "+ thanhtienGT + " as SOTIEN , a.MA + '-' + a.TEN as DOITUONG,'' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP  "+
					    " FROM ERP_SANPHAM a " +
						" WHERE a.PK_SEQ = "+ spId +" "+
										
						" UNION ALL "+
						
						"  SELECT  N'NỢ' as NO_CO, "+ id +" as id,'"+TAIKHOANKTCO_GV+"' as SOHIEUTAIKHOAN, "+
					    "          "+ thanhtienGT + " as SOTIEN , '' as DOITUONG,'' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP  "+
						" FROM ERP_NHANHANG a " +
						" WHERE a.PK_SEQ = "+ id +" ";

					}
					
					
				}
			}
			
			if(layTK.trim().length() <= 0)
			{ 
				layTK = "select '' NO_CO, '' id, '' SOHIEUTAIKHOAN, '' as SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP "
						+ "from ERP_NHANHANG "
                        + "where PK_SEQ = "+ id +" ";
			}
					                             
			layTK += "ORDER BY id, STT, SAPXEP ";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
     
		
		return layTK;
	}
	
	public void init(String search)
	{
		String query = "";
		if(search.length() <= 0)
		{
			query = " select   a.PK_SEQ as nhId, a.SOHOADON, a.NGAYNHAN, b.TEN as dvthTen, " +
					" case a.NoiDungNhan_FK when 100000 then CAST(c.PK_SEQ as varchar(10)) + '/' + SUBSTRING(c.NGAYMUA, 6, 2) + '/' + SUBSTRING(c.NGAYMUA, 0, 5) " +
					" else isnull(b.PREFIX,'') + isnull(th.PREFIX,'') + cast(th.PK_SEQ as varchar(10)) end  as PoId, " +
					" isnull(b.PREFIX,'') + isnull(a.PREFIX,'') + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU," +
					"  case  when a.NGAYNHAN > GETDATE() OR NH.SLNhan < NH.SLDat then 1 else 0 end as ktra, " +
					" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, a.loaihanghoa_fk  " +
					" from erp_nhanhang a  inner join ( Select  b.NHANHANG_FK, SUM(b.SOLUONGDAT)as SLDat, SUM(b.SOLUONGNHAN)as SLNhan "+
					"			 from  ERP_NHANHANG_SANPHAM b "+
					"			 group by  b.NHANHANG_FK) NH on a.PK_SEQ= NH.NHANHANG_FK " +
					"  left join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ " +
					" left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ  " +
					" left join DonTraHang th on a.TRAHANG_FK = th.PK_SEQ " +
					" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  " +
					" inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  " +
					" where a.congty_fk = '" + this.congtyId + "'  and  a.TRAHANG_FK is not null " +					 
					" and (  ( ( c.pk_seq is not null ) and ( c.NHACUNGCAP_FK in " + this.util.quyen_nhacungcap(this.userId) + " ) )              " +
					" or ( ( th.pk_seq is not null ) and ( th.KHACHHANG_FK in  " + this.util.quyen_npp(this.userId)  + "  ) ) )   ";
		}
		else
			query = search;
		
	
		System.out.println("query_init : " + query);
		String query_init = createSplittingData_List(50, 10, " NGAYNHAN desc, trangthai asc, nhId desc ", query);
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
					String dk = LayDuLieu(rs.getString("nhId"));
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
 
						ht = new ThongTinHienThi();
						ht.setId(rs.getString("nhId"));
						ht.setNgaynhan(rs.getString("ngaynhan"));
						ht.setSochungtu(rs.getString("sochungtu"));
						//ht.setTenNCC(rs.getString("nccTen"));
						ht.setdvthTen(rs.getString("dvthTen"));
						ht.setPoId(rs.getString("PoId"));
						//ht.setDaRaHd(rs.getString("DaRaHd"));
						ht.setSohoadon(rs.getString("sohoadon"));
						ht.setTrangthai(rs.getString("trangthai"));
						ht.setNgaytao(rs.getString("ngaytao"));
						ht.setNguoitao(rs.getString("nguoitao"));
						ht.setNgaysua(rs.getString("ngaysua"));
						ht.setNguoisua(rs.getString("nguoisua"));
						ht.setloaihanghoaId(rs.getString("loaihanghoa_fk"));
						
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
		
		
		//this.nhRs = createSplittingData(50, 10, "NGAYNHAN desc, trangthai asc, nhId desc ", query);
		query="select pk_seq, ten from ERP_DONVITHUCHIEN where pk_seq in  "+ this.util.quyen_donvithuchien(this.userId);
		this.dvthRs = db.get(query);
		
		 
		
		query = " select isnull(a.ngayduyet,'') as ngayduyet  , isnull(nguoiduyet.ten,'') as nguoiduyet ,  a.ngaytao, a.ngaysua, a.ngaytra, a.PREFIX, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,  "+
		" a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai, '' as soid, '' as ngayhd, "+
		" 0 as tienhd " +
		" from DonTraHang a inner join "+ 
		" nhanvien b on  a.nguoitao = b.pk_seq   " +
		" inner join  nhanvien c on a.nguoisua = c.pk_seq  " +
		" inner join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  " +
		" left join  nhanvien nguoiduyet on a.nguoiduyet = nguoiduyet.pk_seq  " +
		" inner join  ERP_KHACHHANG  f  on a.khachhang_fk = f.pk_seq  " + 
		" where ( a.trangthai = '4') and a.congty_fk = '" + this.congtyId + "' " +
		" and f.pk_Seq in   "+ util.quyen_npp(this.userId) ;
		
		if(this.tungay.length()>0){
			query+=" and  isnull(a.ngayduyet,'') >='"+this.tungay+"'";
		}
		if(this.denngay.length()>0){
			query+=" and  isnull(a.ngayduyet,'') <='"+this.denngay+"'";
			
		}
		if(this.getSoPO().length()>0){
			query += " and   '140'+ CAST(a.PK_SEQ as varchar(10))   like '%" + this.getSoPO() + "%'  ";
		}
		 
		if(this.getNCC().length() >0){
			query+=" and a.khachhang_fk in (select pk_seq from erp_khachhang where timkiem like N'%"+util.replaceAEIOU(this.getNCC())+"%')";
		}
		
		this.RsDontrahang_daduyet=db.get(query);
	}

	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getSoPO() 
	{
		return this.soPO;
	}

	public void setSoPO(String soPO) 
	{
		this.soPO = soPO;
	}

	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

	}

	
	public int getCurrentPage() {
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		this.currentPages = current;
	}

	
	public int[] getListPages() {
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		ResultSet rs = db.get("select count(*) as c from ERP_NHANHANG");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getSoNhanhang() {
		
		return this.sonhanhang;
	}

	
	public void setSoNhanhang(String soNhanhang) {
		
		this.sonhanhang = soNhanhang;
	}

	
	public String getSoHoadon() {
		
		return this.sohoadon;
	}

	
	public void setSoHoadon(String soHoadon) {
		
		this.sohoadon = soHoadon;
	}
	
	public String getNCC() 
	{
		return this.nccTen;
	}

	public void setNCC(String ncc) 
	{
		this.nccTen = ncc;
	}

	
	public List<IThongTinHienThi> getHienthiList() {		
		return this.hienthiList;
	}

	
	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		this.hienthiList = hienthiList;
		
	}

	@Override
	public ResultSet getRsDontrahang_daduyet() {
		// TODO Auto-generated method stub
		return this.RsDontrahang_daduyet;
	}

	@Override
	public void setRsDontrahang_daduyet(ResultSet rs) {
		// TODO Auto-generated method stub
		 this.RsDontrahang_daduyet=rs;
	}
}
