package geso.traphaco.erp.beans.nhanhangnhapkhau.imp;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import geso.traphaco.erp.beans.nhanhangnhapkhau.*;

public class ErpNhanhangList_Giay extends Phan_Trang implements IErpNhanhangList_Giay
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	
	String dvthId;
	ResultSet dvthRs;
	ResultSet spRS;
	
	public ResultSet getSpRS() {
		return spRS;
	}

	public void setSpRS(ResultSet spRS) {
		this.spRS = spRS;
	}
	String trangthai;
	String nccTen;
	String soPO;
	String msg;
	
	String sonhanhang;
	String sohoadon;
	String mactSp = "";
	
	ResultSet nhRs;
	
	ResultSet nguoitaoRs;    
	String nguotaoIds;
	
	String loaimh = "";
	
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	
	List<IThongTinHienThi> hienthiList;
	
	dbutils db;
	Utility util;
	String nppId;
	ResultSet khonhanRs;
	String khonhanId;
	ResultSet rsHoaDonNamCho;
	
	public ErpNhanhangList_Giay()
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
		this.nguotaoIds = "";
		this.loaimh = "";
		
		this.soItems = 25;
		this.util=new Utility();
		currentPages = 1;
		num = 1;
		
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		this.khonhanId = "";
		this.db = new dbutils();
	}
	
	public ResultSet getRsHoaDonNamCho() {
		return rsHoaDonNamCho;
	}

	public void setRsHoaDonNamCho(ResultSet rsHoaDonNamCho) {
		this.rsHoaDonNamCho = rsHoaDonNamCho;
	}

	public ResultSet getKhonhanRs() {
		return khonhanRs;
	}

	public void setKhonhanRs(ResultSet khonhanRs) {
		this.khonhanRs = khonhanRs;
	}

	public String getKhonhanId() {
		return khonhanId;
	}

	public void setKhonhanId(String khonhanId) {
		this.khonhanId = khonhanId;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
		this.nppId=util.getIdNhapp(userId);
		
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

	private String LayDuLieu(String id) 
	{
		String layTK = "";
		
		try
		{
			
			
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
		System.out.println("search is: " + search);
		String query = "";
		if(search == null || search.length() <= 0)
		{
			query = " SELECT distinct a.PK_SEQ as nhId, ncc.Ten as nccTen, \n"+
					" isnull(case when a.hdNCC_fk is null then isnull(a.SOHOADON,'') else ( select isnull(kyhieu,'') + ' ' + CAST(sohoadon as varchar(50)) as ten  from ERP_HOADONNCC where pk_seq = a.hdNCC_fk ) end,'') as SOHOADON, a.NGAYNHAN, b.TEN as dvthTen, \n"+
					" c.sopo as PoId, \n"+
					" b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, \n"+
					" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, a.loaihanghoa_fk, \n"+
					" case when a.hdNCC_fk IS null then 0  when a.hdNCC_fk IS NOt null then 1  else -1 end DaRaHd, a.tooltip \n"+
					" FROM erp_nhanhang a "+
					" inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ \n"+
					" left join DonTraHang th on a.TRAHANG_FK = th.PK_SEQ \n"+
					" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  \n"+
					" inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n"+
					" inner join ERP_MUAHANG c on  a.muahang_fk= c.pk_seq \n"+  
					" INNER JOIN ERP_HOADONNCC f ON A.HDNCC_FK = f.PK_SEQ \n" +
					" INNER JOIN ERP_PARK g ON f.PARK_FK = g.PK_SEQ  \n" +  
					" left join ERP_NHACUNGCAP ncc on g.NCC_FK = ncc.pk_seq   \n"+
					" WHERE c.LOAI = "+ this.loaimh +" and a.congty_fk = '" + this.congtyId + "' " ;
//					a.trangthai=0 and 
					//" ORDER BY a.PK_SEQ DESC ";			
			
			if( khonhanId.length() >0){
				query +=" and a.khonhan_fk ='"+ khonhanId+"'";
			}
			if(tungay.length() > 0)
				query += " and a.ngaynhan >= '" + tungay + "'";
			
			if(denngay.length() > 0)
				query += "\n and a.ngaynhan <= '" + denngay + "'";
			
			if(dvthId.length() > 0)
				query += "\n and b.pk_seq = '" + dvthId + "'";
			
			if(this.nguotaoIds.trim().length() > 0)
				query += "\n AND a.nguoitao = '" + this.nguotaoIds + "' ";
			
			
			if(this.soPO.length() > 0)
			{
				//query += " and ( CAST(c.PK_SEQ as varchar(10)) + '/' + SUBSTRING(c.NGAYMUA, 6, 2) + '/' + SUBSTRING(c.NGAYMUA, 0, 5) like '%" + soPo + "%' ) ";
			
				query += "\n  AND ( c.sopo like '%" + this.soPO.trim() + "%' ) ";
			}
			
			if(trangthai.length() > 0)
				query += "\n and a.trangthai = '" + trangthai + "'";
			
			if(sonhanhang.trim().length() > 0)
			{
				query += "\n and b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) like N'%" + sonhanhang + "%'  ";
			}
			
			if(sohoadon.trim().length() > 0)
			{
				query += "\n and ( select isnull(kyhieu,'') + ' ' + CAST(sohoadon as varchar(50)) as ten  from ERP_HOADONNCC where pk_seq = a.hdNCC_fk ) like N'%" + sohoadon + "%' ";
			}
			
			if(this.nccTen.trim().length() > 0)
			{
				query += "\n and (  ( ( c.pk_seq is not null ) and ( c.NHACUNGCAP_FK in ( select pk_seq from ERP_NHACUNGCAP where ma like N'%" + this.nccTen + "%' or ten like N'%" + this.nccTen + "%' ) ) )              " +
						 "\n     or ( ( th.pk_seq is not null ) and ( th.KHACHHANG_FK in  ( select pk_seq from ERP_KHACHHANG where ma like N'%" + this.nccTen + "%' and ten like N'%" + this.nccTen + "%'  )  ) ) )   ";
			}
			if(this.mactSp.trim().length() > 0)
			{
			query +="\n and a.pk_seq in (" +
			"\n     select distinct nhanhang_fk from erp_nhanhang_sanpham where sanpham_fk = "+ this.mactSp + " ) " ;
			
		}
		}
		
		
		
		//query += " order by a.NGAYNHAN desc, a.trangthai asc, a.pk_seq desc;";


		else {
			query = search;
		}
		// Phân quyền nhân viên theo phòng ban, mặc định admin được nhìn thấy
		if( !this.userId.equals("100002") && util.GetquyenNew("ErpNhanhangnhapkhau_GiaySvl", "&loai="+loaimh,userId)[9]==0){
			query += " and d.PHONGBAN_FK in ( select  " +
					" distinct PHONGBAN_FK from NHANVIEN where PK_SEQ ="+this.userId+" )";
			query += " and ((a.KHOCHOXULY_FK in "+util.quyen_khott(this.userId) +") " +
					" or(a.KHONHAN_FK in "+util.quyen_khott(this.userId) +"))";
		}
		
		
		System.out.println(" query init :\n" + query + "\n---end-------------------------------");
		ResultSet rs = createSplittingData(30, 10, "nhId DESC, NGAYNHAN asc ", query);
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		NumberFormat formatter = new DecimalFormat("#,###,###");
		
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
								String sotien = rsKT.getString("sotien");
								if(sotien.trim().length() > 0){ sotien = formatter.format(Double.parseDouble(sotien)) ;}
									
								kt = new DinhKhoanKeToan(rsKT.getString("id"), rsKT.getString("no_co"),rsKT.getString("sohieutaikhoan"),sotien,rsKT.getString("doituong"),
										 rsKT.getString("trungtamchiphi"),rsKT.getString("trungtamdoanhthu"), "");
								ktList.add(kt);
							}
							rsKT.close();
						}
												
					// INIT
					
						ht = new ThongTinHienThi();
						ht.setId(rs.getString("nhId"));
						ht.setNgaynhan(rs.getString("ngaynhan"));
						ht.setSochungtu(rs.getString("sochungtu"));
						ht.setSohoadon(rs.getString("SOHOADON"));
						ht.setTenNCC(rs.getString("nccTen"));
						ht.setdvthTen(rs.getString("dvthTen"));
						ht.setPoId(rs.getString("PoId"));
						ht.setDaRaHd(rs.getString("DaRaHd"));
						ht.setTrangthai(rs.getString("trangthai"));
						ht.setNgaytao(rs.getString("ngaytao"));
						ht.setNguoitao(rs.getString("nguoitao"));
						ht.setNgaysua(rs.getString("ngaysua"));
						ht.setNguoisua(rs.getString("nguoisua"));
						ht.setloaihanghoaId(rs.getString("loaihanghoa_fk"));
						
						ht.setLayDinhkhoanKT(ktList);
						
						ht.setTooltip(rs.getString("tooltip")==null?"":rs.getString("tooltip"));
					
					htList.add(ht);																	
				}
				rs.close();
			}
			
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		this.hienthiList = htList;
		
		/*this.nhRs = createSplittingData(50, 10, "NGAYNHAN desc, trangthai asc, nhId desc ", query);*/
		query="select pk_seq, ten from ERP_DONVITHUCHIEN where 1=1 ";
		this.dvthRs = db.get(query);
		
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_NHANHANG ) ";
		this.nguoitaoRs = db.get(query);
		
		query ="select PK_SEQ, TEN from KHO where trangthai ='1'";
		this.khonhanRs = db.get(query);
		
		
		//get spRS
		query ="select PK_SEQ,MA, TEN from ERP_SANPHAM where trangthai ='1'";
		this.spRS = db.get(query);
		
		// Lấy thông tin hóa đơn => đơn hàng nằm chờ
		getHoaDonNamCho();
	}
	// tạo ra 1 danh sách các hóa đơn đang nằm chờ
	private void getHoaDonNamCho() {
		String sql = " SELECT  A.PK_SEQ , A.NGAYGHINHAN, NCC.TEN AS TENNHACUNGCAP, A.TRANGTHAI ,     	NV.TEN AS TENNV,A.NGAYSUA,A.NGAYTAO,NV.PK_SEQ AS MANV,NV2.TEN AS \n"
				+ "  		 TENNVS,NV2.PK_SEQ AS MANVS, A.PREFIX     	, HOANTAT.SODEM  AS DAHOANTAT , CASE WHEN THANHTOAN.PARK_FK IS NULL THEN 0 ELSE 1 END AS DACOTHANHTOAN, ISNULL(HDNCC.SOHOADON,'') AS SOHOADON, NCC.PK_SEQ NCCID,	 \n"
				+ "        HDNCC. PK_SEQ HDNCCID, \n"
				+ "		 isnull(A.HOANTAT_NHANHANG,0) DANHANHANG, A.LOAIHD, \n"
				+ " ( SELECT CASE WHEN HDMH1.SANPHAM_FK IS NOT NULL THEN 0 WHEN HDMH1.TAISAN_FK IS NOT NULL THEN 1 WHEN HDMH1.CCDC_FK IS NOT NULL THEN 3 ELSE 2 END LOAIHH \n"
				+ " FROM \n"
				+ " (SELECT TOP (1) * "
				+ "  FROM ERP_HOADONNCC_DONMUAHANG HDMH "
				+ "  WHERE HDMH.HOADONNCC_FK = HDNCC.PK_SEQ ) HDMH1 ) LOAIHH \n"
				+

				" FROM 	 ERP_PARK A   	\n" +
				"   LEFT join ERP_HOADONNCC b on b.park_fk = a.pk_seq  \n"
				+ " INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = A.NCC_FK   	\n"
				+ " INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   \n"
				+ " INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ	     \n"
				+ " LEFT JOIN 	(   \n"
				+ "   				 SELECT A.PARK_FK , (DEMA - ISNULL (DEMB,0)) AS SODEM \n"
				+ "					 FROM 	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMA  	\n"
				+ "							 FROM ERP_HOADONNCC   \n"
				+ "							 WHERE CONGTY_FK = "
				+ this.congtyId
				+ "   				 		 GROUP BY PARK_FK) AS A  	\n"
				+ "							 LEFT JOIN   	\n"
				+ "							(SELECT PARK_FK , COUNT(PARK_FK) AS DEMB  	\n"
				+ "							 FROM ERP_HOADONNCC  	 \n"
				+ "   				 		 WHERE TRANGTHAI = 2 AND CONGTY_FK = "
				+ this.congtyId
				+ "							 GROUP BY PARK_FK) AS B ON B.PARK_FK = A.PARK_FK  \n"
				+ "  					) HOANTAT ON HOANTAT.PARK_FK = A.PK_SEQ   	 \n"
				+ "  		 LEFT JOIN 	(  \n"
				+ "   				SELECT 	DISTINCT HD.PARK_FK 		\n"
				+ "   				FROM 	ERP_THANHTOANHOADON_HOADON TT		\n"
				+ "   					 	INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT.HOADON_FK AND TT.LOAIHD = 0 \n"
				+ "   				WHERE 	TT.THANHTOANHD_FK IN (SELECT PK_SEQ FROM ERP_THANHTOANHOADON WHERE TRANGTHAI != 2 AND CONGTY_FK = "
				+ this.congtyId
				+ "  )  \n"
				+ "							AND HD.CONGTY_FK = "
				+ this.congtyId
				+ "  					) THANHTOAN ON THANHTOAN.PARK_FK = A.PK_SEQ "
				+ " INNER JOIN ERP_HOADONNCC HDNCC ON A.PK_SEQ=HDNCC.PARK_FK ";

		sql += " WHERE HDNCC.CONGTY_FK =" + this.congtyId + " AND A.LOAIHD = 0 " +
			   " AND A.TRANGTHAI in(1,2) " +
			   "  AND b.pk_seq not in (select hdNCC_fk from ERP_NHANHANG where trangthai in (0) and hdNCC_fk is not null )"+
			   " AND (isnull(A.HOANTAT_NHANHANG,0)) <> 1 ";
		
		if( !this.userId.equals("100002") && util.GetquyenNew("ErpNhanhangnhapkhau_GiaySvl", "&loai="+loaimh,userId)[9]==0){
			// hai loại
			// loại 1 là kiểm định
			sql += " and (( (HDNCC.KHOBIETTRU_FK in "+util.quyen_khott(this.userId) +")";
			sql += " and ( select COUNT(*) from ERP_HOADONNCC_DONMUAHANG qa inner join ERP_SANPHAM qb on " +
				   " qa.SANPHAM_FK = qb.PK_SEQ where qb.BATBUOCKIEMDINH = '1' and qa.HOADONNCC_FK = HDNCC.pk_seq ) >0)";
			sql += " or ";
			
			// loại 2 là không kiểm định
			sql += " ( (HDNCC.KHOTONTRU_FK in "+util.quyen_khott(this.userId) +")";
			sql += " and ( select COUNT(*) from ERP_HOADONNCC_DONMUAHANG qa inner join ERP_SANPHAM qb on " +
				   " qa.SANPHAM_FK = qb.PK_SEQ where qb.BATBUOCKIEMDINH = '0' and qa.HOADONNCC_FK = HDNCC.pk_seq ) >0))";
		
		}
		
		sql += " order by A.PK_SEQ desc";
		
		this.rsHoaDonNamCho = this.db.get(sql);
		System.out.println("hóa đơn nằm chờ:"+ sql);
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
	
	public ResultSet getNguoitaoRs() {
		
		return this.nguoitaoRs;
	}

	
	public void setNguoitaoRs(ResultSet nguoitaoRs) {
		
		this.nguoitaoRs = nguoitaoRs;
	}

	
	public void setNguoitaoIds(String nspIds) {
		
		this.nguotaoIds = nspIds;
	}

	
	public String getNguoitaoIds() {
		
		return this.nguotaoIds;
	}

	
	public void setMaCtSp(String mact) {
		this.mactSp = mact;
	}

	
	public String getMaCtSp() {
		return this.mactSp;
	}

	
	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}

	
	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}

	public String getNppId() {
		return this.nppId;
	}

	public void setLoaimh(String loaidmh) 
	{
		this.loaimh = loaidmh ;
	}

	public String getLoaimh() 
	{
		return this.loaimh;
	}
	@Override
	public void setSoItems(int soItems) {
		
		this.soItems = soItems;
	}
	@Override
	public int getSoItems() {
		
		return this.soItems;
	}
	
	
}
