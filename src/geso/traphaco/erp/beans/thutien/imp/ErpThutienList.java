package geso.traphaco.erp.beans.thutien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thutien.IErpThutienList;

public class ErpThutienList  extends Phan_Trang  implements IErpThutienList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	
	String nccId;
	ResultSet nppRs;
	String htttId;
	ResultSet htttRs;   
	String ctyId;
	String trangthai;
	String msg;
	String nguoisuaId;
	String nppdangnhap;
	ResultSet chinhanhRs;
	ResultSet doituongRs;
	ResultSet nvtuRs;
	ResultSet tthdRs;	
	ResultSet nguoisuaRs;
	
	List<IThongTinHienThi> hienthiList;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	Utility util;
	
	String Sochungtu="";
	String Sohoadon="";
	String sotien = "";
	String maChungTu;
	String khId;
	ResultSet khRs;
	String tusotien;
	String densotien;
	String nvId;
	ResultSet nvRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	String nhomkhId;
	ResultSet nhomkhRs;
	
	String khohangId;
	ResultSet khohangRs;
	
	String nvgnId;
	ResultSet nvgnRs;
	
	String ghichu;
	String sobangke;
	
	String ctyTen;
	String diachi;
	String masothue;
	
	public ErpThutienList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nccId = "";
		this.htttId = "";
		this.trangthai = "";
		this.nguoisuaId = "";
		this.msg = "";
		this.ctyId = "";
		this.khId = "";
		this.nvId = "";
		this.sotien ="";
		this.kbhId = "";
		this.nhomkhId = "";
		this.nppdangnhap = "";
		this.khohangId = "";
		this.nvgnId = "";
		this.ghichu = "";	
		this.sobangke = "";
		this.maChungTu = "";
		this.ctyTen = "";
		this.diachi = "";
		this.masothue = "";
		this.tusotien = "";
		this.densotien="";
		currentPages = 1;
		num = 1;
		util=new Utility();
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

	public String getCtyId()
	{
		return this.ctyId;
	}

	
	public String getTusotien() {
		return tusotien;
	}

	public void setTusotien(String tusotien) {
		this.tusotien = tusotien;
	}

	public String getDensotien() {
		return densotien;
	}

	public void setDensotien(String densotien) {
		this.densotien = densotien;
	}

	
	public ResultSet getDoituongRs( String madoituong, String doituongdinhkhoan) {
		String query = "select * from ( " ;
		System.out.println("QUERY DOITUONG :"+doituongdinhkhoan);
		String taikhoanRs = "Select pk_seq, sohieutaikhoan as ma,TENTAIKHOAN as ten from ERP_TAIKHOANKT where trangthai = 1 and CONGTY_FK = "+this.ctyId+" ";
		String SanphamList = ("select PK_SEQ, MA , TEN from SANPHAM where TRANGTHAI = 1 ");
		String NganhangList = ("select PK_SEQ, MA , TEN from ERP_NGANHANG where TRANGTHAI = 1 ");
		String NccList = ("select PK_SEQ, MA , TEN from ERP_NHACUNGCAP where TRANGTHAI = 1  and duyet = '1'");
		String TaisanList = ("select PK_SEQ, MA , TEN from ERP_TAISANCODINH where TRANGTHAI = 1 ");
		String KhachhangList = ("select PK_SEQ, MAFAST AS MA, TEN from NHAPHANPHOI where TRANGTHAI = 1 AND PK_SEQ != 1 ");
		String NhanvienList = ("select PK_SEQ, MA , TEN from ERP_NHANVIEN where TRANGTHAI = 1 ");			
		String DoituongkhacList = ("select PK_SEQ, MADOITUONG AS MA , TENDOITUONG AS TEN from ERP_DOITUONGKHAC where TRANGTHAI = 1 ");			
		if(doituongdinhkhoan.equals("1")){
			query += SanphamList;
		}
		else if(doituongdinhkhoan.equals("2")){
			query += NganhangList;
		}
		else if(doituongdinhkhoan.equals("3")){
			query += NccList;
		}
		else if(doituongdinhkhoan.equals("4")){
			query += taikhoanRs;
		}
		else if(doituongdinhkhoan.equals("5")){
			query += KhachhangList;
		}
		else if(doituongdinhkhoan.equals("6")){
			query += NhanvienList;
		}
		else if(doituongdinhkhoan.equals("7")){
			query += SanphamList;
		}
		else if(doituongdinhkhoan.equals("8")){
			query += DoituongkhacList;
		}else {
			return null;
		}
		query +=	") as r" +
						" where r.pk_seq = " + madoituong ;
		System.out.println("QUERY DOITUONG :"+query);
		return db.getScrol(query);
	}

	public void setDoituongRs(ResultSet doituongRs) {
		this.doituongRs = doituongRs;
	}

	public ResultSet getChinhanhRs() {
		return db.get(" select pk_seq, MaFAST + ' - ' + TEN  as nppTen from NHAPHANPHOI where trangthai = '1' and PK_SEQ!=1 and loaiNPP in (0,1) ");
	}
	
	public String getChinhanh(String npp_fk){
		String result = "";
		if(npp_fk.length() <= 0)
			return "";
		ResultSet rs = db.get(" select MaFAST  as nppTen from NHAPHANPHOI where trangthai = '1' and PK_SEQ!=1 and loaiNPP in (0,1) and pk_seq =  "+npp_fk);
		if(rs != null){
			try {
				if(rs.next()){
					result = rs.getString("nppTen");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public void setChinhanhRs(ResultSet chinhanhRs) {
		this.chinhanhRs = chinhanhRs;
	}

	
	public ResultSet getNvtuRs() {
		return db.get(" select pk_seq, ma, ten from erp_nhanvien where trangthai=1 ");
	}

	public void setNvtuRs(ResultSet nvtuRs) {
		this.nvtuRs = nvtuRs;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
		
		String query = "SELECT TEN, DIACHI, MASOTHUE FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId;
		
		ResultSet rs = this.db.get(query);
		
		try{
			if(rs != null) {
				rs.next();
				this.ctyTen = rs.getString("TEN");
				this.diachi = rs.getString("DIACHI");				
				this.masothue = rs.getString("MASOTHUE");
				
				rs.close();
			}
			
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
	}

	
	public String getMaChungTu() {
		return maChungTu;
	}

	public void setMaChungTu(String maChungTu) {
		this.maChungTu = maChungTu;
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
		ResultSet rs = db.get("select count(*) as c from ERP_thutien");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	public void init(String search)
	{
		//this.getNppInfo();  
		//this.chinhanhRs = db.get(" select pk_seq, MaFAST + ' - ' + TEN  as nppTen from NHAPHANPHOI where trangthai = '1' and PK_SEQ!=1 and loaiNPP in (0,1) ");
		//this.nvtuRs = db.get(" select pk_seq, ma, ten from erp_nhanvien where trangthai=1 ");
		String query = "";
		
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1'");
		this.nguoisuaRs = db.get("select pk_seq, ten from nhanvien where trangthai=1");
		this.khRs = db.get("select pk_Seq, ma + ', '+ ten as khTen from ERP_NHACUNGCAP where trangthai = 1 and duyet = '1'");
		this.nvRs = db.get("select pk_seq, ma + ', '+ ten as nvTen from ERP_NHANVIEN where trangthai = 1");
		this.kbhRs =  db.get("select pk_seq, diengiai from KENHBANHANG where trangthai = 1");
		this.khohangRs=db.get("select pk_seq ,ten from kho where 1 = 1 and PK_SEQ IN ( " + util.quyen_kho(this.userId)+ ") " );
		
		

		query = " select a.pk_seq  as tthdId,isnull(a.sochungtu,'') sochungtu ,a.trangthai, a.ngaychungtu, a.ngayghiso, a.ngaytao, a.ngaysua,a.ghichu,isnull(convert(nvarchar, a.NPPCN_FK), '') NPPCN_FK,a.NHANVIEN_FK, \n" +
		" case when a.noidungtt_fk=100006 then isnull(quay.ma,'') \n"+
		//" when a.NPPCN_FK is null then isnull(npp.maFast,'') +' - ' + isnull(npp.ten,'')  " +
		" else '' end  as nppTen,  \n"+
		/*"(SELECT top (1) " +
		" CASE WHEN DOITUONGDINHKHOAN = 1 THEN (SELECT MA +'-' + TEN FROM SANPHAM WHERE PK_SEQ=ttct.DOITUONG_FK ) " +
		"  WHEN DOITUONGDINHKHOAN = 2 THEN (SELECT MA +'-' + TEN FROM ERP_NGANHANG WHERE PK_SEQ=ttct.DOITUONG_FK ) " +
		"  WHEN DOITUONGDINHKHOAN = 3 THEN (SELECT MA +'-' + TEN FROM ERP_NHACUNGCAP WHERE PK_SEQ=ttct.DOITUONG_FK ) " +
		"  WHEN DOITUONGDINHKHOAN = 4 THEN (SELECT sohieutaikhoan +'-' + TENTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ=ttct.DOITUONG_FK ) " +
		"  WHEN DOITUONGDINHKHOAN = 5 THEN (SELECT MA +'-' + TEN FROM NHAPHANPHOI WHERE PK_SEQ=ttct.DOITUONG_FK ) " +
		"  WHEN DOITUONGDINHKHOAN = 6 THEN (SELECT MA +'-' + TEN FROM ERP_NHANVIEN WHERE PK_SEQ=ttct.DOITUONG_FK ) " +
		"  WHEN DOITUONGDINHKHOAN = 8 THEN (SELECT MADOITUONG +'-' + TENDOITUONG FROM ERP_DOITUONGKHAC WHERE PK_SEQ=ttct.DOITUONG_FK ) " +
		" ELSE '' END " +
		 "FROM ERP_THUTIEN_DINHKHOANCO ttct " + 
		 "WHERE ttct.THUTIEN_FK = a.pk_seq) as madoituong,"+*/
		"CASE WHEN ( a.noidungtt_fk=100006) then b.maFAST \n"+
		"when a.NHANVIEN_FK is not null then (select TEN from ERP_NHANVIEN where PK_SEQ = a.NHANVIEN_FK )\n"+
		//" WHEN ( a.noidungtt_fk=100002) then 'haizzz'" +
		/*" WHEN ( a.noidungtt_fk=100002) then (" +
		" SELECT top(1) MADOITUONG +'-' + TENDOITUONG  FROM ERP_DOITUONGKHAC dtk " +
		" inner join ERP_THUTIEN_DINHKHOANCO ttct on dtk.PK_SEQ=ttct.DOITUONG_FK  " +
		"  WHERE ttct.THUTIEN_FK = a.pk_seq " +
		" ) \n"+*/
	     "when npp.PK_SEQ is not null then npp.MaFAST \n"+
	    "  when nppk.PK_SEQ is not null then nppk.MaFAST "+
	    " when nvk.PK_SEQ is not null then nvk.MA " +
	    " when b.PK_SEQ is not null then b.MaFAST  "+
	    "  when ncc.PK_SEQ is not null then ncc.MA  " +
	    " when ncck.PK_SEQ is not null then ncck.ma "+
	    " when  nhk.PK_SEQ is not null then nhk.ma" +
	    " when  spk.PK_SEQ is not null then spk.ma" +
	    " when  dtk.PK_SEQ is not null then dtk.MADOITUONG " +
	    " when  tkk.PK_SEQ is not null then tkk.sohieutaikhoan " +
	    " else (" +
	    " case when a.noidungtt_fk=100006 then " +
		" (select ma from KhachHang_ThuTien_ERP khq where khq.LOAI = 1 and khq.npp_fk=a.NPPCN_FK and cast(a.khachhang_fk as nvarchar) = khq.pk_seq  ) \n"+
		" when a.NPPCN_FK is null then isnull(npp.maFast,'')   " +
		" else " +
		" ( select ma from KhachHang_ThuTien_ERP khq " +
		"where khq.LOAI = 1 and cast(a.khachhang_fk as nvarchar) = khq.pk_seq )" +
		" end  \n"+
	    ") end as madoituong ,"+
					"	c.ten as htttTen, \n" +
					" d.ten as nguoitao, e.ten as nguoisua, f.ten as noidungTen, ISNULL(a.ISKTTDUYET,0) ISKTTDUYET, \n" +
					" CASE WHEN a.TIENTE_FK = 100000 \n"+
					"      THEN (case when a.noidungtt_fk != 100002 then ISNULL(a.THUDUOC, 0) \n"+
					"                 else isnull((select sum(sotien) from erp_thutien_dinhkhoanco where thutien_fk = a.pk_seq),0) end ) \n"+
					"      ELSE (case when a.noidungtt_fk != 100002 then ISNULL(a.THUDUOCNT, 0) \n"+
					"                 else isnull((select sum(sotiennt) from erp_thutien_dinhkhoanco where thutien_fk = a.pk_seq),0) end ) \n"+
					" END AS THUCTHU, isnull(a.machungtu, '') machungtu \n" +	
					" from ERP_THUTIEN a " +
					 /* " left join ( \n"+
				      " select loai,pk_seq,ten,ma from KhachHang_ThuTien_ERP  \n"+
					  "		where LOAI in  (1) "+
				      " ) khachhang_thutien on ( CONVERT(VARCHAR, a.khachhang_fk) + ' -- ' + CONVERT(VARCHAR, ISNULL(a.isNPP,0)) ) = khachhang_thutien.pk_seq \n"+
				      " left join (  \n"+
				      "	    select loai,  substring (pk_seq,0,7) + ' -- 1' as pk_Seq,ten,ma,npp_fk from KhachHang_ThuTien_ERP   \n"+
					  "		where LOAI = 1  "+
					  "    ) khachhang_thutien_quay on khachhang_thutien_quay.npp_fk=a.NPPCN_FK and ( CONVERT(VARCHAR, a.khachhang_fk) + ' -- ' + CONVERT(VARCHAR, ISNULL(a.isNPP,0)) ) =   khachhang_thutien_quay.pk_seq  \n"+*/
					 " INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = a.TIENTE_FK \n" +
					" LEFT JOIN KhachHang b on a.KHACHHANG_FK = b.pk_seq \n" +
					" LEFT JOIN ERP_NhaCungCap ncc on a.NCC_FK = ncc.pk_seq \n" +
					 " left join nhaphanphoi npp on a.NPPCN_FK is null and npp.pk_seq= a.khachhang_fk and a.isnpp=1 \n"+
					" inner join ERP_HINHTHUCTHANHTOAN c on a.HTTT_FK = c.pk_seq \n" +
					" inner join ERP_NOIDUNGTHUTIEN f on a.noidungtt_fk = f.pk_seq \n" +
					" inner join NHANVIEN d on a.nguoitao = d.pk_seq  \n" +
					" inner join NHANVIEN e on a.nguoisua = e.pk_seq  \n"+
					
					" left join nhaphanphoi quay on a.NPPCN_FK is null and quay.pk_seq= a.NPPCN_FK "+
					
					"  LEFT JOIN( " +
					" SELECT ROW_NUMBER ( ) OVER ( PARTITION BY THUTIEN_FK order by THUTIEN_FK )as STT, " +
					" THUTIEN_FK,DOITUONGDINHKHOAN,DOITUONG_FK,ISNPP from ERP_THUTIEN_DINHKHOANCO TT " +
					" )DKC ON A.PK_SEQ=DKC.THUTIEN_FK AnD DKC.STT=1 " +
					"  left join NHAPHANPHOI nppk on nppk.PK_SEQ=dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=5  and DKC.ISNPP=1 and nppk.TRANGTHAI = 1 AND nppk.PK_SEQ != 1 \n" +
					" left join erp_nhacungcap ncck on ncck.PK_SEQ= dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=3 and ncck.TRANGTHAI = 1 \n" +
					" left join ERP_NHANVIEN nvk on nvk.PK_SEQ = dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=6 and nvk.TRANGTHAI = 1 \n" +
					" left join ERP_NGANHANG nhk on nhk.PK_SEQ = dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=2 and nhk.TRANGTHAI = 1 \n"+
					" left join ERP_DOITUONGKHAC dtk on dtk.PK_SEQ = dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=8 and dtk.TRANGTHAI = 1 \n"+
					" left join ERP_TAIKHOANKT tkk on tkk.PK_SEQ = dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=4 and tkk.trangthai = 1 and tkk.CONGTY_FK = "+this.ctyId+ "\n"+
					" left join SANPHAM spk on spk.PK_SEQ = dkc.DOITUONG_FK and DKC.DOITUONGDINHKHOAN=1 and spk.TRANGTHAI = 1"+
					
					
					" where a.congty_fk = "+this.ctyId+" and a.HTTT_FK = 100000 ";

		if(tungay.length() > 0)
			query += " and a.ngaychungtu >= '" + tungay + "'\n";
		
		if(denngay.length() > 0)
			query += " and a.ngaychungtu <= '" + denngay + "'\n";
		
		if(nccId.length() > 0)
			query += " and isnull(b.pk_seq, ncc.pk_seq) = '" + nccId + "'\n";
				
		if(nvId.length()>0)
			query += " and nv.pk_seq = '"+nvId+"'\n";
				
		if(trangthai.length() > 0)			
			query += " and a.trangthai = "+trangthai + "\n";
		
		if(this.Sochungtu.length() >0)
			query += " and (A.MACHUNGTU like '%"+Sochungtu+"%' ) \n";
		
		if(this.nguoisuaId.length() >0)
			query += " and a.nguoisua = '"+nguoisuaId+"' \n";
		
		if(ghichu.length() > 0)
			query += " and a.GhiChu like N'%"+ghichu+"%' \n";
			
		if(kbhId.length()>0)
			query += " and a.PK_SEQ IN ( SELECT THUTIEN_FK FROM ERP_THUTIEN_HOADON TTHD INNER JOIN ERP_HOADONNPP HD ON TTHD.HOADON_FK = HD.PK_SEQ AND TTHD.LOAIHOADON = 0 WHERE HD.KBH_FK =  "+kbhId+" )\n";
		
		if(this.maChungTu.length() >0)
			query += " and (A.SOCHUNGTU like '%"+maChungTu+"%' ) \n";
		
		if(khohangId.length()>0)
			query += " and a.PK_SEQ IN ( SELECT THUTIEN_FK FROM ERP_THUTIEN_HOADON TTHD INNER JOIN ERP_HOADONNPP HD ON TTHD.HOADON_FK = HD.PK_SEQ AND TTHD.LOAIHOADON = 0 WHERE HD.KHO_FK =  "+khohangId+" )\n";
		
//		if(khId.length()>0)
//			query += " and a.PK_SEQ IN ( SELECT THUTIEN_FK FROM ERP_THUTIEN_HOADON TTHD \n" +
//					"					 INNER JOIN ERP_HOADONNPP HD ON TTHD.HOADON_FK = HD.PK_SEQ AND TTHD.LOAIHOADON = 0 \n" +
//					" 					 LEFT JOIN KHACHHANG KH on HD.KHACHHANG_FK = kh.PK_SEQ \n"+
//					" 					 LEFT JOIN NHAPHANPHOI NPP on HD.NPP_DAT_FK = npp.PK_SEQ \n"+
//					" 					 LEFT JOIN ERP_NHANVIEN NV on HD.nhanvien_fk = nv.PK_SEQ \n"+
//					"					 WHERE 1 = 1 \n" +
//					" and ( ( kh.ten like N'%" + khId+ "%' or kh.ma like N'%" + khId+ "%' or kh.mafast = N'%"+khId+"%' ) \n" +
//					" or ( npp.ma like N'%" + khId + "%' or npp.ten like N'%" + khId+ "%' or npp.mafast like N'%"+khId+"%' ) ) ) \n";
		
		
		/*if(khId.length()>0)
			query += " and a.PK_SEQ IN ( SELECT TT.PK_SEQ FROM ERP_THUTIEN TT \n" +
//		     "      INNER JOIN ERP_HOADONNPP HD ON TTHD.HOADON_FK = HD.PK_SEQ AND TTHD.LOAIHOADON = 0 \n" +
		     "       LEFT JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "KHACHHANG KH on TT.KHACHHANG_FK = kh.PK_SEQ  and isnpp=0 \n"+
		     "       LEFT JOIN NHAPHANPHOI NPP on TT.KHACHHANG_FK = npp.PK_SEQ and isnpp=1 \n"+
		     "       LEFT JOIN ERP_NHANVIEN NV on TT.nhanvien_fk = nv.PK_SEQ \n"+
		     "       LEFT JOIN ERP_nhacungcap ncc on TT.ncc_fk = ncc.PK_SEQ \n"+
		     "      WHERE 1 = 1 \n" +
		     " and ( ( kh.ten like N'%" + khId + "%' or kh.mafast = N'%"+khId+"%' ) \n" +
		     " or ( npp.ma like N'%" + khId + "%' or npp.ten like N'%" + khId+ "%' or npp.mafast like N'%"+khId+"%' )" +
		     " or ( ncc.ma like N'%" + khId + "%' or ncc.ten like N'%" + khId+ "%'  )" +
		       "or ( nv.ma like N'%" + khId + "%' or nv.ten like N'%" + khId+ "%' or nv.mafast like N'%"+khId+"%' ) ) ) \n";*/
		
		if(sobangke.length()>0)
			query += " and A.BANGKE_FK LIKE '%"+sobangke+"%' \n";
	
		//PHAN QUYEN
		//query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "b", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		query = "select * from ( "+ query +") as a where 1=1 ";
		if(tusotien.length()>0){
			query += " and (THUCTHU >= "+tusotien+" ) \n";
		}
		if(densotien.length()>0){
			query += " and (THUCTHU <= "+densotien+" ) \n";
		}
		if(khId.length()>0){
			String temp = " a.tthdId IN ( SELECT TT.PK_SEQ FROM ERP_THUTIEN TT \n" +
//		     "      INNER JOIN ERP_HOADONNPP HD ON TTHD.HOADON_FK = HD.PK_SEQ AND TTHD.LOAIHOADON = 0 \n" +
		     "       LEFT JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "KHACHHANG KH on TT.KHACHHANG_FK = kh.PK_SEQ  and isnpp=0 \n"+
		     "       LEFT JOIN NHAPHANPHOI NPP on TT.KHACHHANG_FK = npp.PK_SEQ and isnpp=1 \n"+
		     "       LEFT JOIN ERP_NHANVIEN NV on TT.nhanvien_fk = nv.PK_SEQ \n"+
		     "       LEFT JOIN ERP_nhacungcap ncc on TT.ncc_fk = ncc.PK_SEQ \n"+
		     "      WHERE 1 = 1 \n" +
		     " and ( ( kh.ten like N'%" + khId + "%' or kh.mafast = N'%"+khId+"%' ) \n" +
		     " or ( npp.ma like N'%" + khId + "%' or npp.ten like N'%" + khId+ "%' or npp.mafast like N'%"+khId+"%' )" +
		     " or ( ncc.ma like N'%" + khId + "%' or ncc.ten like N'%" + khId+ "%'  )" +
		       "or ( nv.ma like N'%" + khId + "%' or nv.ten like N'%" + khId+ "%' or nv.mafast like N'%"+khId+"%' ) ) ) \n";
			query+= " and ( \n (a.madoituong like N'%"+this.khId+"%' ) or \n " +
			" ( " + temp+")"+
			" )";
		}
		
		System.out.println("Init "+ query);
		String query_init = createSplittingData_ListNew(this.db, 25, 10, " NGAYCHUNGTU DESC,NGAYGHISO DESC,MACHUNGTU DESC,TRANGTHAI DESC  ", query) ;
		System.out.println("Init_list :"+ query);
		this.tthdRs = db.get(query_init);
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
			
			if(this.nguoisuaRs != null)
				this.nguoisuaRs.close();
			
			if(this.nvgnRs != null)
				this.nvgnRs.close();
			
			if(this.khohangRs != null)
				this.khohangRs.close();
			
			if(this.khRs != null)
				this.khRs.close();
			
			if(this.nhomkhRs != null)
				this.nhomkhRs.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		this.db.shutDown();		
	}

	public String getsochungtu() {
		return this.Sochungtu;
	}

	public void setsochungtu(String sochungtu) {
		this.Sochungtu=sochungtu;
	}

	public String getsohoadon() {
		return this.Sohoadon;
	}

	public void setsohoadon(String sohoadon) {
		this.Sohoadon=sohoadon;
	}

	public ResultSet getNguoisuaRs() 
	{
		return this.nguoisuaRs;
	}

	public void setNguoisuaRs(ResultSet nguoisuaRs) 
	{
		this.nguoisuaRs = nguoisuaRs;
	}

	public String getNguoisuaId()
	{
		return this.nguoisuaId;
	}

	public void setNguoisuaId(String nguoisuaId) 
	{
		this.nguoisuaId = nguoisuaId ;
	}

	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
	}

	public String getKhId() {
		return this.khId;
	}

	public void setKhId(String khid) {
		this.khId = khid;
	}

	public ResultSet getKhList() {
		return this.khRs;
	}

	public void setKhList(ResultSet khrs) {
		this.khRs = khrs; 
	}

	public String getNvId() {
		return this.nvId;
	}

	public void setNvId(String nvid) {
		this.nvId = nvid;
	}

	public ResultSet getNvList() {
		return this.nvRs;
	}

	public void setNvList(ResultSet nvrs) {
		this.nvRs = nvrs;
	}

	public String getSotien() {
		return this.sotien;
	}

	public void setSotien(String sotien) {
		this.sotien = sotien;
	}
	
	public String getKbhId() {
		return this.kbhId;
	}
	
	public void setKbhId(String kbhid) {
		this.kbhId = kbhid;
	}
	
	public ResultSet getKbhRs() {
		return this.kbhRs;
	}

	public void setKbhRs(ResultSet kbhrs) {
		this.kbhRs = kbhrs;
	}
	
	public String getNhomkhId() {
		return this.nhomkhId;
	}

	public void setNhomkhId(String nhomkhid) {
		this.nhomkhId = nhomkhid;
	}

	public ResultSet getNhomkhRs() {
		return this.nhomkhRs;
	}
	
	public void setNhomkhRs(ResultSet Nhomkhrs) {
		this.nhomkhRs = Nhomkhrs;
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
	
	public String getKhohangId() {
		return this.khohangId;
	}
	
	public void setKhohangId(String khohangid) {
		this.khohangId = khohangid;
	}
	
	public ResultSet getKhohangRs() {
		return this.khohangRs;
	}
	
	public void setKhohangRs(ResultSet khohangrs) {
		this.khohangRs = khohangrs;
	}
	
	public String getNvgnId() {
		return this.nvgnId;
	}
	
	public void setNvgnId(String Nvgnid) {
		this.nvgnId = Nvgnid;
	}
	
	public ResultSet getNvgnRs() {
		return nvgnRs;
	}
	
	public void setNvgnRs(ResultSet nvgnrs) {
		this.nvgnRs = nvgnrs;
	}

	public String getGhichu() {
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}
	
	public String getsobangke() {
		return this.sobangke;
	}
	
	public void setsobangke(String sobangke) {
		this.sobangke = sobangke;
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
}