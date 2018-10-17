package geso.traphaco.distributor.beans.hoadontaichinhNPP.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.distributor.beans.hoadontaichinhNPP.IErpHoadontaichinhNPPList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.util.Utility;

public class ErpHoadontaichinhNPPList extends Phan_Trang implements IErpHoadontaichinhNPPList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	
	String sohoadon;
	String sodonhang;

	String nppTen;
	String msg;
	
	String nppId;
	ResultSet nppRs;
	ResultSet DondathangRs;
	
	String khTen;
	ResultSet khRs;
	
	String loaidonhang;
	String phanloai;
	String tensp;
	String giasp;

	ResultSet rstien;
	String thuegtgt;
	private int num;
	private int[] listPages;
	private int currentPages;
	
	String maHoaDon;
	String tenxuatHD;
	
	String tinhthanhId;
	String quanhuyenId;
	String sitecode;
	
	dbutils db;
	
	List<IThongTinHienThi> hienthiList;
	String KbhId = "",KvId = "",Spid = "",HtbhId ="";
	
	String nguoitao;
	String nguoisua;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	ResultSet KbhRs,KvRs,SpRs,HtbhRs, TinhthanhRs, QuanhuyenRs;
	public ErpHoadontaichinhNPPList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.sohoadon="";
		this.sodonhang="";
		this.msg = "";
		this.loaidonhang = "";
	    this.khTen= "";
		currentPages = 1;
		num = 1;
		this.nppId ="";
		this.phanloai = "0";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		this.thuegtgt="";
		this.tensp="";
		this.giasp="";
		this.tenxuatHD = "";
		this.tinhthanhId = "";
		this.quanhuyenId = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.db = new dbutils();
	}

	public String getnppId()
	{
		return this.nppId;
	}

	public void setnppId(String nppId) 
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
		this.getNppInfo();
		ResultSet rs = db.get("select count(*) as c from ERP_HOADONNPP where npp_fk ='"+ this.nppId +"'");
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

	private void getNppInfo()
	{		
		/*//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		
		System.out.println("nppId:"+this.nppId);*/
		
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppId = util.getIdNhapp(this.userId);
			this.nppTen = util.getTenNhaPP();
			this.sitecode = util.getSitecode();
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
	}
	
	double tongTruoc = 0 ;
	double tongCK = 0;
	double tongSau = 0;
	
	public void getSumBySearch(String sumqr) {
		//System.out.println("câu ck: "+sumqr);
		if(isSearch){
		ResultSet rs = db.get(sumqr);
			try 
			{
				rs.next();
				this.tongTruoc = rs.getDouble("DOANHSO");
				this.tongCK = rs.getDouble("THUE");
				this.tongSau = rs.getDouble("DOANHTHU");
				rs.close();
			}
			catch(Exception e) {}
		}
		else{
			//System.out.println("vàafsa ");			
			this.tongTruoc = 0;
			this.tongCK = 0;
			this.tongSau = 0;
		}
	}
	
	public void init(String search)
	{
		this.getNppInfo();
		
		Utility util = new Utility();
		
		String query = "";
        
		if(search.length() > 0)
			query = search + " and a.npp_fk ='"+ this.nppId +"'  ";
		else
		{
			query = "select a.PK_SEQ, a.trangthai, a.ngayxuatHD, a.sohoadon + a.kyhieu as sohoadon, NV.TEN as nguoitao, isnull(a.tongtienavat, 0) as tongtien ,isnull(kb.ten, '') as kenhbanhang, a.tooltip, \n" +
					" case when a.NPP_DAT_FK is not null then npp.TEN when a.NHANVIEN_FK is not null then nvct.TEN else KH.TEN end as khTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.capdogiaohang  \n" +
					"from ERP_HOADONNPP a \n" +
					"left join KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ  \n" +
					"left join NHAPHANPHOI npp on a.NPP_DAT_FK=npp.PK_SEQ  \n" +
					"left join ERP_NHANVIEN nvct on a.NHANVIEN_FK=nvct.PK_SEQ  \n" +
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   \n" +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ \n"+
					" left join KENHBANHANG kb on kb.pk_seq = a.kbh_fk \n"+
					"where a.pk_seq > 0 and a.npp_fk = '" + this.nppId + "' and a.TRANGTHAI NOT IN (3,5) AND a.LoaiHoaDon IN( 0 ,1) ";
		} 
		
		//query += " AND a.LOAIXUATHD = '" + this.phanloai + "' ";
		//System.out.println("ERP_HOADONNPP1111: " + query);
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "khachhang_fk", this.getLoainhanvien(), this.getDoituongId() );
		query += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or ( a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ) ) ";
				
		query += " and a.kbh_fk in  " + util.quyen_kenh(this.userId);
		
		this.DondathangRs = createSplittingDataNew(this.db, 50, 10, " ngayxuatHD desc,trangthai asc, sohoadon desc ", query);
		
		
		if (this.tinhthanhId != null && this.tinhthanhId.trim().length() > 0 )
		{	
			System.out.println("select ten , pk_seq  from quanhuyen where tinhthanh_fk ='"+ this.tinhthanhId +"' order by ten");
			
			this.QuanhuyenRs = this.db.get("select ten , pk_seq  from quanhuyen where tinhthanh_fk ='"+ this.tinhthanhId +"' order by ten");
		}
				
		if( this.phanloai.equals("0") ) 
		{
			query = "	select PK_SEQ, isnull(maFAST,'') + '-' + TEN as TEN  " +
					"	from NHAPHANPHOI where TRANGTHAI = '1'  ";
			query += " and pk_seq in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "'  )  ";
		}
		else if( this.phanloai.equals("1") ) 
		{
			query = "select PK_SEQ, isnull(maFAST,'') + '-' + TEN as TEN " +
					"	from KHACHHANG KH where TRANGTHAI = '1' and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100000' ) and npp_fk = '" + this.nppId + "' ";
			
			query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		}
		else
		{
			query = "select PK_SEQ, isnull(maFAST,'') + '-' + TEN as TEN " +
					"	from KHACHHANG KH where TRANGTHAI = '1' and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' ) and npp_fk = '" + this.nppId + "' ";
			
			query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		}

		System.out.println(query);
		if(query.trim().length() > 0)
			this.khRs = db.get(query);
		
		this.HtbhRs = db.get("select pk_seq,diengiai from HETHONGBANHANG where trangthai = 1");
		if(this.HtbhId.length() > 0)
		{
			this.KbhRs = db.get("select pk_seq,ten from kenhbanhang a inner join hethongbanhang_kenhbanhang b on a.PK_SEQ = b.kbh_fk where a.TRANGTHAI = 1 and b.htbh_fk = '"+this.HtbhId+"' ");
			System.out.println("HTBH: select pk_seq,ten from kenhbanhang a inner join hethongbanhang_kenhbanhang b on a.PK_SEQ = b.kbh_fk where a.TRANGTHAI = 1 and b.htbh_fk = '"+this.HtbhId+"' ");
		}
		else		
			this.KbhRs = db.get("select pk_seq,ten from kenhbanhang where trangthai = 1");
		this.KvRs = db.get("select pk_seq, ten from khuvuc where trangthai = 1");
		
		this.TinhthanhRs = db.get("select PK_SEQ, TEN from TINHTHANH where trangthai = 1 order by TEN asc ");
		this.QuanhuyenRs = db.get("select PK_SEQ, TEN from QUANHUYEN where trangthai = 1  order by TEN asc ");
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public ResultSet getDondathangRs() 
	{
		return this.DondathangRs;
	}

	public void setDondathangRs(ResultSet nkRs) 
	{
		this.DondathangRs = nkRs;
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

	
	public String getSohoadon() {
		
		return this.sohoadon;
	}

	
	public void setSohoadon(String sohoadon) {
		
		this.sohoadon=sohoadon;
	}

	
	public String getSodonhang() {
		
		return this.sodonhang;
	}

	
	public void setSodonhang(String sodonhang) {
		
		this.sodonhang=sodonhang;
	}
	boolean isSearch = false;
	
	public boolean getIsSearch() {
		return this.isSearch;
	}

	
	public void setIsSearch(boolean search) {
		this.isSearch = search;
		
	}

	
	public double getTongTruoc() {
		return this.tongTruoc;
	}

	
	public double getTongCK() {
		return this.tongCK;
	}

	
	public double getTongSau() {
		return this.tongSau;
	}
	
	public String getPhanloai() 
	{
		return this.phanloai;
	}

	public void setPhanloai(String phanloai) 
	{
		this.phanloai = phanloai;
	}


	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}


	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}
	
	private String LayDuLieu(String id) {
			//GHI NHẬN TÀI KHOẢN KẾ TOÁN
			 
			String query="";
			String laytk="";
			String ngayghinhan = "";
			String ngaychungtu= "";
			
			//CÀI KẾ TOÁN
			
			String nam = "";
			String thang = "";
			
			query =
			"	SELECT	A.NGAYXUATHD,A.PK_SEQ, B.SANPHAM_FK, B.SOLUONG, ISNULL(B.DONGIASAUCK ,B.DONGIA) DONGIA,ISNULL(B.CHIETKHAU,0) CHIETKHAU, ISNULL(A.NGAYGHINHAN, A.NGAYXUATHD) NGAYGHINHAN, \n"+ 
			"			ISNULL(A.KHGHINO, A.NPP_DAT_FK) KHACHHANG_FK, \n"+
			"			CASE WHEN A.KHGHINO IS NOT NULL THEN ( SELECT distinct SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ =  F.TAIKHOAN_FK ) WHEN A.NPP_DAT_FK IS NOT NULL THEN (SELECT distinct SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = E.TAIKHOAN_FK ) END TAIKHOANNO, \n"+
			"			B.VAT, (SELECT distinct LOAISP.TAIKHOANKT_FK " +
			"					FROM ERP_LOAISANPHAM LOAISP INNER JOIN ERP_TAIKHOANKT TAIKHOAN ON LOAISP.TAIKHOANKT_FK = TAIKHOAN.SOHIEUTAIKHOAN \n"+
			"			 		WHERE C.LOAISANPHAM_FK =  LOAISP.PK_SEQ AND TAIKHOAN.SOHIEUTAIKHOAN = LOAISP.TAIKHOANKT_FK  ) LOAISP, \n"+
			"			'51110000' as a51110000, '52110000' as a52110000, '33311000' as a33311000, "+
			"			'51180000' as a51180000, ISNULL(A.TIENTE_FK, 100000) as TIENTE_FK, ISNULL(A.TYGIA, 1)  as  TYGIA,  "+
			"			CASE WHEN A.KHGHINO IS NOT NULL THEN D.TEN  WHEN A.NPP_DAT_FK IS NOT NULL THEN E.TEN END TENKH, C.TEN TENSP "+
			"   FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_SP B ON A.PK_SEQ = B.HOADON_FK \n"+
			" 	INNER JOIN SANPHAM C ON B.SANPHAM_FK = C.PK_SEQ \n"+	 
			" 	LEFT JOIN KHACHHANG D ON A.KHACHHANG_FK = D.PK_SEQ \n"+
			" 	LEFT JOIN NHAPHANPHOI E ON A.NPP_DAT_FK = E.PK_SEQ \n"+
			"	LEFT JOIN KHACHHANG F ON A.KHGHINO = F.PK_SEQ \n"+
			"	WHERE A.PK_SEQ = "+id+" AND LEN(ISNULL(SCHEME,'')) = 0  ";
			
			//System.out.println(query);
			ResultSet kt  = db.get(query);
			int i =0;
			
			if(kt!=null)
			{
				try {
					while(kt.next())
					{
						String khachhang_fk = kt.getString("KHACHHANG_FK");
						String tenkh = kt.getString("TENKH");
						double soluong = kt.getDouble("SOLUONG");
						double dongia = kt.getDouble("DONGIA"); // ĐƠN GIÁ SAU KHI GIẢM
						double tienhang = soluong*dongia;
						String SOHIEUTAIKHOAN = kt.getString("LOAISP")== null ? "": kt.getString("LOAISP") ;
						double tienthue = soluong*dongia*kt.getDouble("VAT")/100;
						
						String tensanpham = kt.getString("TENSP");
						
						double tienchietkhau = kt.getDouble("CHIETKHAU");
						
						ngaychungtu = kt.getString("NGAYXUATHD");
						ngayghinhan = kt.getString("NGAYGHINHAN");
						
						if(ngayghinhan.trim().length() > 0 )
						{
							nam = ngayghinhan.substring(0, 4);
							thang = ngayghinhan.substring(5, 7);
						}
						
						String tiente_fk = kt.getString("TIENTE_FK");
						double tygia = kt.getDouble("TYGIA");
						
						String doituong_no = "";
						String madoituong_no =  "";
						
						String doituong_co = "";
						String madoituong_co = "";
						
						String TAIKHOANNO = "";
						String TAIKHOANCO ="";
																	
							
						if(SOHIEUTAIKHOAN.equals("15610000")) // SẢN PHẨM LÀ LOẠI HÀNG HÓA
						{
							if(tienhang>0)
							{
								doituong_no = "Khách hàng";
								madoituong_no = khachhang_fk;
								
								doituong_co = "";
							    madoituong_co = "";
							    
							    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
							    TAIKHOANCO = kt.getString("a51110000") == null ? "": kt.getString("a51110000") ;
								
							}
								
						}
						else if(SOHIEUTAIKHOAN.equals("15510000"))
						{
							if(tienhang>0)
							{
								doituong_no = "Khách hàng";
								madoituong_no = khachhang_fk;
								
								doituong_co = "";
							    madoituong_co = "";
							    
							    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
							    TAIKHOANCO = kt.getString("a51120000")== null ? "": kt.getString("a51120000") ;
							}
							
						}
						else
						{
							if(tienhang>0)
							{
								doituong_no = "Khách hàng";
								madoituong_no = khachhang_fk;
								
								doituong_co = "";
							    madoituong_co = "";
							    
							    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
							    TAIKHOANCO = kt.getString("a51180000") == null ? "": kt.getString("a51180000") ;
							}
						}
						
						if(tienhang>0)
						{
							if(laytk.trim().length()>0) laytk += " UNION ALL \n";
							
							 laytk  += 
							 	 	" SELECT N'NỢ' NO_CO, PK_SEQ,"+tienhang+" as  SOTIEN, "+TAIKHOANNO+" AS SOHIEUTAIKHOAN, N'"+tenkh+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
									" FROM ERP_HOADONNPP WHERE PK_SEQ = '"+id+"' \n" +				
									
									" UNION ALL \n"+
					
									" SELECT N'CÓ' NO_CO, PK_SEQ,"+tienhang+" as SOTIEN, "+TAIKHOANCO+" AS SOHIEUTAIKHOAN, N'"+tensanpham+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
									" FROM ERP_HOADONNPP  WHERE PK_SEQ = '"+id+"' \n" ;
							 i++;
						}
						
						if(tienthue>0)
						{
							doituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;
							
							doituong_co = "";
						    madoituong_co = "";
						    
						    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
						    TAIKHOANCO = kt.getString("a33311000") == null ? "": kt.getString("a33311000") ;
						    
						    if(laytk.trim().length()>0) laytk += " UNION ALL \n";
							
							 laytk  += 
							 	 	" SELECT N'NỢ' NO_CO, PK_SEQ,"+tienthue+" SOTIEN, "+TAIKHOANNO+" AS SOHIEUTAIKHOAN, N'"+tenkh+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
									" FROM ERP_HOADONNPP WHERE PK_SEQ = '"+id+"' \n" +				
									
									" UNION ALL \n"+
					
									" SELECT N'CÓ' NO_CO, PK_SEQ,"+tienthue+" SOTIEN, "+TAIKHOANCO+" AS SOHIEUTAIKHOAN, N'"+tensanpham+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
									" FROM ERP_HOADONNPP  WHERE PK_SEQ = '"+id+"' \n" ;
							 i++;
						}
						
						if(tienchietkhau>0)
						{
							doituong_no = "";
							madoituong_no = "";
							
							doituong_co = "Khách hàng";
						    madoituong_co = khachhang_fk;
						    
						    TAIKHOANNO = kt.getString("a52110000")== null ? "": kt.getString("a52110000") ;
						    TAIKHOANCO = kt.getString("TAIKHOANNO")== null ? "": kt.getString("TAIKHOANNO") ;
						    
						    if(laytk.trim().length()>0) laytk += " UNION ALL \n";
							
							 laytk  += 
							 	 	" SELECT N'NỢ' NO_CO, PK_SEQ,"+tienchietkhau+" SOTIEN, "+TAIKHOANNO+" AS SOHIEUTAIKHOAN, N'"+tensanpham+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
									" FROM ERP_HOADONNPP WHERE PK_SEQ = '"+id+"' \n" +				
									
									" UNION ALL \n"+
					
									" SELECT N'CÓ' NO_CO, PK_SEQ,"+tienchietkhau+" SOTIEN, "+TAIKHOANCO+" AS SOHIEUTAIKHOAN, N'"+tenkh+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
									" FROM ERP_HOADONNPP  WHERE PK_SEQ = '"+id+"' \n" ;
							 i++;
						}
						
					}
					kt.close();
				} catch (Exception e) {					
					e.printStackTrace();
				}
				
			}
			
			
			
			if(laytk.trim().length()>0) laytk += " ORDER BY PK_SEQ, STT, SAPXEP ";
			
			if(laytk.trim().length()<=0){
				laytk += 
					" SELECT '' NO_CO, '' PK_SEQ, '' SOHIEUTAIKHOAN, '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP " +
					" FROM ERP_HOADON HD "+
					" WHERE HD.PK_SEQ = '"+id+"'";
			}
			//System.out.println(laytk);
			return laytk;
		
	}
	
	
	public String getTensp() {
		return tensp;
	}

	public void setTensp(String tensp) {
		this.tensp = tensp;
	}

	public String getThuegtgt() {
		return thuegtgt;
	}

	public void setThuegtgt(String thuegtgt) {
		this.thuegtgt = thuegtgt;
	}
	public String getGiasp() {
		return giasp;
	}

	public void setGiasp(String giasp) {
		this.giasp = giasp;
	}
	public void laytien(String sql){
		this.rstien=db.get(sql);
	}
	public ResultSet getRstien() {
		return rstien;
	}

	public void setRstien(ResultSet rstien) {
		this.rstien = rstien;
	}

	public void setTenxuatHD(String tenxuatHD) {

		this.tenxuatHD = tenxuatHD;
	}

	public String getTenxuatHD() {

		return this.tenxuatHD;
	}
	public String getKbhId() {
		return KbhId;
	}

	public void setKbhId(String kbhId) {
		KbhId = kbhId;
	}

	public String getKvId() {
		return KvId;
	}

	public void setKvId(String kvId) {
		KvId = kvId;
	}

	public ResultSet getKbhRs() {
		return KbhRs;
	}

	public ResultSet getKvRs() {
		return KvRs;
	}
	public String getSpid() {
		return Spid;
	}

	public void setSpid(String spid) {
		Spid = spid;
	}

	public ResultSet getSpRs() {
		return SpRs;
	}

	public String getHtbhId() {
		return HtbhId;
	}

	public void setHtbhId(String htbhId) {
		HtbhId = htbhId;
	}

	public ResultSet getHtbhRs() {
		return HtbhRs;
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

	
	public String getTinhthanhId() {
	
		return this.tinhthanhId;
	}

	
	public void setTinhthanhId(String TinhthanhId) {
	
		this.tinhthanhId = TinhthanhId;
	}

	
	public ResultSet getTinhthanhRs() {
	
		return this.TinhthanhRs;
	}

	
	public void setTinhthanhRs(ResultSet tinhthanhRs) {
	
		this.TinhthanhRs = tinhthanhRs;
	}

	
	public String getQuanhuyenId() {
	
		return this.quanhuyenId;
	}

	
	public void setQuanhuyenId(String QuanhuyenId) {
	
		this.quanhuyenId = QuanhuyenId;
	}

	
	public ResultSet getQuanhuyenRs() {
	
		return this.QuanhuyenRs;
	}

	
	public void setQuanhuyenRs(ResultSet quanhuyenRs) {
	
		this.QuanhuyenRs = quanhuyenRs;
	}

	
	public String getNguoitao() {
		
		return this.nguoitao;
	}

	
	public void setNguoitao(String nguoitao) {
		
		this.nguoitao = nguoitao;
	}

	
	public String getNguoisua() {
		
		return this.nguoisua;
	}

	
	public void setNguoisua(String nguoisua) {
		
		this.nguoisua = nguoisua;
	}

	public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}
}
