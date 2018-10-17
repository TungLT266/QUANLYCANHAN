package geso.traphaco.erp.beans.nhanhangsanxuat.imp;
//
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
import geso.traphaco.erp.beans.nhanhangsanxuat.*;

public class ErpNhanhangList_Giay extends Phan_Trang implements IErpNhanhangList_Giay
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
	String mactSp = "";
	
	ResultSet nhRs;
	ResultSet spRs;
	public ResultSet getSpRs() {
		return spRs;
	}
	public void setSpRs(ResultSet spRs) {
		this.spRs = spRs;
	}
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
	
	String lsxId="";
	
	public String getLsxId() {
		return lsxId;
	}
	public void setLsxId(String lsxId) {
		this.lsxId = lsxId;
	}
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
		String query = "";
		if(search.length() <= 0)
		{
				query =   "\n  SELECT   A.PK_SEQ AS NHID, '' AS NCCTEN,   " + 
						  "\n ''  AS SOHOADON,   " + 
						  "\n  A.NGAYNHAN, B.TEN AS DVTHTEN,   " + 
						  "\n LSX.PK_SEQ AS POID,   " + 
						  "\n CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU,   " + 
						  "\n A.TRANGTHAI, A.NGAYSUA, A.NGAYTAO, D.TEN AS NGUOITAO, E.TEN AS NGUOISUA, A.LOAIHANGHOA_FK,   " + 
						  "\n CASE WHEN A.HDNCC_FK IS NULL THEN 0  WHEN A.HDNCC_FK IS NOT NULL THEN 1  ELSE -1 END DARAHD, A.TOOLTIP,  SP.MA AS MASANPHAM, SP.TEN AS TENSANPHAM   " + 
						  "\n FROM ERP_NHANHANG A    " + 
						  "\n INNER JOIN ERP_DONVITHUCHIEN B ON A.DONVITHUCHIEN_FK = B.PK_SEQ   " + 
						  "\n LEFT JOIN ERP_LENHSANXUAT_GIAY  LSX ON A.LENHSANXUAT_FK = LSX.PK_SEQ   " + 
						  "\n LEFT JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.NHANHANG_FK= A.PK_SEQ" + 
						  "\n LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK  " + 
						  "\n INNER JOIN NHANVIEN D ON A.NGUOITAO = D.PK_SEQ    " + 
						  "\n INNER JOIN NHANVIEN E ON A.NGUOISUA = E.PK_SEQ    " + 			 
						  "\n WHERE   a.congty_fk = '" + this.congtyId + "' AND LENHSANXUAT_FK IS NOT NULL" ;
						//" ORDER BY a.PK_SEQ DESC ";			
			
		}
		else {
			query = search;
		}
		
 
		
		System.out.println(" query init :" + query);
		//String query_init = createSplittingData_ListNew(this.db, 30, 10, "nhId DESC, NGAYNHAN asc ", query);
		String query_init = createSplittingData_ListNew(this.db, 30, 10, "NGAYNHAN DESC, SOCHUNGTU desc ", query);
		System.out.println(" query_init :" + query_init);
		ResultSet rs = db.get(query_init);
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
						
						ht.setMasanpham(rs.getString("MASANPHAM"));
						ht.setTensanpham(rs.getString("tensanpham"));
					
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
		query="select pk_seq, ten from ERP_DONVITHUCHIEN where congty_fk ="+this.congtyId;
		this.dvthRs = db.get(query);
		
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_NHANHANG where congty_fk ="+this.congtyId+" ) ";
		this.nguoitaoRs = db.get(query);
		
		/*query ="select PK_SEQ, TEN from KHO where trangthai ='1'";*/
		query ="select PK_SEQ,TEN from ERP_KHOTT where   ISKHONHANTP_DAT='1' and congty_fk = "+this.congtyId;
		this.khonhanRs = db.get(query);
		
		
		//get spRS
		query ="select PK_SEQ,MA, TEN from ERP_SANPHAM where trangthai ='1' and congty_fk="+this.congtyId;
		this.spRs = db.get(query);
		
		
		
		// Lấy thông tin hóa đơn => đơn hàng nằm chờ
		getHoaDonNamCho();
		
	}
	// tạo ra 1 danh sách các hóa đơn đang nằm chờ
	private void getHoaDonNamCho() {/*
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

				" FROM 	 ERP_PARK A   	\n"+
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

		sql += " WHERE HDNCC.CONGTY_FK =" + this.congtyId + " AND A.LOAIHD = 1 AND A.TRANGTHAI  in (1, 2)  " +
			   "  AND b.pk_seq not in (select hdNCC_fk from ERP_NHANHANG where trangthai in (0))"+
				"AND (isnull(A.HOANTAT_NHANHANG,0)) <> 1 ";
		
		if( !this.userId.equals("100002")){
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
	*/}
	
	public void DBclose() 
	{
		try{
			if(dvthRs!=null) dvthRs.close();
			if(nhRs!=null) nhRs.close();
			if(spRs!=null) spRs.close();
			if(nguoitaoRs!=null) nguoitaoRs.close();
			if(hienthiList!=null) hienthiList.clear();
			if(khonhanRs!=null) khonhanRs.close();
			if(rsHoaDonNamCho!=null) rsHoaDonNamCho.close();
		}catch(Exception e){
		}finally{
			if(this.db!=null) this.db.shutDown();
		}
		
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
	public void setNppId(String nppId){
		this.nppId=nppId;
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
