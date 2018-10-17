package geso.traphaco.erp.beans.phieuxuatkho.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.park.IErpHoadon;
import geso.traphaco.erp.beans.phieuxuatkho.IErpPhieuxuatkhoList;

public class ErpPhieuxuatkhoList extends Phan_Trang implements
		IErpPhieuxuatkhoList {
	private static final long serialVersionUID = 1L;

	String userId;
	String tungay;
	String denngay;
	String xuatkhoId;
	String trangthai;
	String msg;
	String sochungtu;
	String khoxuatId;
	String noidungxuatId;
	String sohoadon;
	ResultSet pxkRs;
	ResultSet khoxuatRs, noidungxuatRs;
	ResultSet rsdh_daduyet;
	
	
	List<IThongTinHienThi> hienthiList;
	
	dbutils db;
	Utility util;
	
	public ErpPhieuxuatkhoList() {
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.msg = "";
		this.sochungtu = "";
		this.xuatkhoId = "";
		this.khoxuatId ="";
		this.noidungxuatId = "";
		this.sohoadon = "";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		this.db = new dbutils();
		this.util = new Utility();
	}
	
	
	
	
	public ResultSet get_khoxuatRs(){
		return this.khoxuatRs;
	}
	public void set_khoxuatRs(ResultSet khoxuatRs){
		this.khoxuatRs = khoxuatRs;
	}
	
	public String get_khoxuatId(){
		return this.khoxuatId;
	}
	public void set_khoxuatId(String khoxuatId){
		this.khoxuatId = khoxuatId;
	}
	
	

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public ResultSet getPxkList() {
		return this.pxkRs;
	}

	public void setPxkList(ResultSet pxkList) {
		this.pxkRs = pxkList;
	}

	public void setmsg(String msg) {
		this.msg = msg;
	}

	public String getmsg() {
		return this.msg;
	}

	/*public String getDDH(String ddhIds){
		String result = "";
		String tmp[] = ddhIds.split(",");
		String query;
		ResultSet rs;
		
		if(tmp.length > 0){
			for(int i = 0; i < tmp.length; i++){
				query = "SELECT PREFIX + '" + tmp[i] + "' AS SOCHUNGTU FROM DONDATHANG WHERE PK_SEQ = " + tmp[i] + "";
				if(this.sochungtu.length() > 0){
					query += " AND PREFIX + '" + tmp[i] + "' LIKE '%" + this.sochungtu + "%' ";
				}
				
				////System.out.println(query);
				
				rs = this.db.get(query);
				if(rs != null){
					try{
						
						rs.next();
						result += rs.getString("SOCHUNGTU") + ", ";
						rs.close();
						
					}catch(java.sql.SQLException e){}
				}
			}
			if(result.length() > 0) result = result.substring(0, result.length()-2); 
		}
		return result;
	}*/
	
	private String LayDuLieu(String id) {
		
		String query =
			" select a.NPP_FK, a.PK_SEQ as pxkId, a.NGAYXUAT, a.HOADON_FK, a.KHO_FK, c.ten as khoten, a.GHICHU, a.NOIDUNGXUAT, " +
			" a.LYDOXUAT, b.PK_SEQ as ndxId , a.TRANGTHAI " +
			" from ERP_XUATKHO a " +
			" inner join ERP_NOIDUNGNHAP b on a.NOIDUNGXUAT = b.PK_SEQ " +
			" inner join ERP_KHOTT c on a.kho_fk = c.pk_seq " +			
			" where a.pk_seq = '"  + id + "'";
		
		//System.out.println(query);
		
		String ngayxuatkho = "";
		String khoId = "";
		String hdtcId = "";
		String ndxId ="";
		String lydoxuat ="";
		String ghichu = "";
		String trangthai = "";
		String nppId = "";
		
		String laytk = "";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					ngayxuatkho = rs.getString("ngayxuat");
					khoId = rs.getString("khoten");
					hdtcId = rs.getString("HOADON_FK");
					ndxId = rs.getString("ndxId");
					lydoxuat = rs.getString("LYDOXUAT");
					ghichu = rs.getString("GHICHU");
					trangthai = rs.getString("trangthai");
					nppId=rs.getString("NPP_FK");
					//this.nppIds=rs.getString("NPPIDS");
				}
				rs.close();
			} 
			catch (SQLException e) {}
		}
		
		
		query = "SELECT NGAYCHOT FROM ERP_XUATKHO WHERE PK_SEQ = " + id;
		System.out.println( query);
		rs = db.get(query);
		String ngaychotnv = "";
		
		if(rs != null){
			try {
				if(rs.next()){
					  ngaychotnv=rs.getString("ngaychot");
				}
				rs.close();
			}
			catch (SQLException e) {}
			
		}
		
		
		int thangtruoc = Integer.parseInt(ngaychotnv.substring(5, 7));
		int namtruoc = Integer.parseInt(ngaychotnv.substring(0, 4));
		
		if(thangtruoc == 1){
			namtruoc = namtruoc-1;
			thangtruoc = 12;
			
		}else{
			thangtruoc = thangtruoc-1;
		}
		
		query	=	
		"SELECT SP.LOAISANPHAM_FK, NPP_FK, (SP.MA +'- '+ SP.TEN) AS TENSP, " +
		" 		XK.KHO_FK, A.SANPHAM_FK, A.SOLO, A.SOLUONG, A.KHUVUCKHO_FK ,A.NGAYBATDAU  "+
		"FROM 	ERP_XUATKHO_SP_CHITIET A " +
		"		INNER JOIN ERP_XUATKHO XK ON XK.PK_SEQ = A.XUATKHO_FK  " +
		"		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = A.SANPHAM_FK  " +
		"WHERE 	XK.PK_SEQ = " + id;
		
		System.out.println(query);
		
		rs = this.db.get(query);
		
		int i = 1;
		if(rs!=null){
			try{
				while (rs.next()){
					String loaisanpham = rs.getString("LOAISANPHAM_FK");
					String tensanpham = rs.getString("TENSP");
					String spid = rs.getString("SANPHAM_FK");
					String idkhott = rs.getString("KHO_FK");
				
					String ngaybatdau= rs.getString("ngaybatdau");
					String khuvuckhoId = rs.getString("KHUVUCKHO_FK");
					String solo = rs.getString("SOLO");
					double soluongct = rs.getDouble("SOLUONG");
								
					query = " SELECT SANPHAM_FK, GIATON from ERP_TONKHOTHANG " +
							" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
							" AND NAM = '" + namtruoc + "' AND KHOTT_FK = '" + idkhott + "'";
					
					System.out.println(query);
					
					ResultSet rsgia = db.get(query);
					double dongia = 0;
					if(rsgia.next()){
						dongia = rsgia.getDouble("GIATON");
					}
					rsgia.close();
					double thanhtien = dongia*soluongct;
					
					System.out.println("ndxId"+ ndxId);
					if( ndxId.equals("100007") || ndxId.equals("100008") || ndxId.equals("100011") || ndxId.equals("100002") || ndxId.equals("100003")  || ndxId.equals("100030")|| ndxId.equals("100027")||ndxId.equals("100028"))
					{
						if (laytk.trim().length() >0) laytk += " UNION ALL \n";
						
						if(ndxId.equals("100007")){
							laytk +=
								" SELECT N'NỢ' NO_CO, "+id+" ID, (SELECT distinct SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = b.PK_SEQ) SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,  N'"+tensanpham+"'  DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a, ERP_NHACUNGCAP b \n"+
								" WHERE a.PK_SEQ = '"+loaisanpham+"' and b.PK_SEQ in (SELECT NCC_FK FROM ERP_HOADON WHERE PK_SEQ IN (SELECT HOADON_FK FROM ERP_XUATKHO WHERE PK_SEQ ="+id+")) \n"+

								" UNION ALL \n"+

								" SELECT N'CÓ' NO_CO, "+id+" ID,  (SELECT distinct SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = a.TaiKhoanKt_fk) SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,  N'"+tensanpham+"'  DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a, ERP_NHACUNGCAP b \n"+
								" WHERE a.PK_SEQ = '"+loaisanpham+"' and b.PK_SEQ in (SELECT NCC_FK FROM ERP_HOADON WHERE PK_SEQ IN (SELECT HOADON_FK FROM ERP_XUATKHO WHERE PK_SEQ ="+id+")) \n";
							//System.out.println(laytk);
						}
						
						if(ndxId.equals("100008")){
							laytk +=
								" SELECT N'NỢ' NO_CO, "+id+" ID, '63230000' SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,  N'"+tensanpham+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a \n"+
								" WHERE a.PK_SEQ = '"+loaisanpham+"' \n"+

								" UNION ALL \n"+

								" SELECT N'CÓ' NO_CO, "+id+" ID, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = a.TaiKhoanKt_fk) SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,  N'"+tensanpham+"'  DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a \n"+
								" WHERE a.PK_SEQ = '"+loaisanpham+"'  \n";
							System.out.println(laytk);
						}
						
						if(ndxId.equals("100002")){
							
							laytk +=
								" SELECT N'NỢ' NO_CO, "+id+" ID, (SELECT distinct SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = \n"+ 
								"	   (case when a.PK_SEQ IN (100041,100042)  then '63211000' \n"+ 
								"			 when a.PK_SEQ = 100043  then '63212000' \n"+
								"	         WHEN (SELECT KBH_FK FROM ERP_HOADON WHERE PK_SEQ = (SELECT HOADON_FK FROM ERP_XUATKHO WHERE PK_SEQ ="+id+")) = '100007' then '63220000' \n"+
								"	    END)) SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,  N'"+tensanpham+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a \n"+
								" WHERE a.PK_SEQ = '"+loaisanpham+"' \n"+

								" UNION ALL \n"+

								" SELECT N'CÓ' NO_CO, "+id+" ID, (SELECT distinct SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = a.TaiKhoanKt_fk) SOHIEUTAIKHOAN,"+thanhtien+" SOTIEN,    N'"+tensanpham+"'   DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
								" FROM  ERP_LOAISANPHAM a \n"+
								" WHERE a.PK_SEQ = '"+loaisanpham+"' \n";
							System.out.println(laytk);
						}
						
						if(ndxId.equals("100003")) {
							
							laytk +=
								" SELECT N'NỢ' NO_CO, "+id+" ID, ( select distinct SOHIEUTAIKHOAN from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '63260000' )   as SOHIEUTAIKHOAN,"+thanhtien+" SOTIEN,    N'"+tensanpham+"'   DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
								" from ERP_LOAISANPHAM a \n"+ 
								" where a.pk_seq = '" + loaisanpham + "' \n"+

								" UNION ALL \n"+

								" SELECT N'CÓ' NO_CO, "+id+" ID,  (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = a.TaiKhoanKt_fk) SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,  N'"+tensanpham+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
								" from ERP_LOAISANPHAM a \n"+ 
								" where a.pk_seq = '" + loaisanpham + "' \n";
							System.out.println(laytk);
						}
						
						if(ndxId.equals("100030")){
							
							laytk +=

								" SELECT N'NỢ' NO_CO, "+id+" ID, ( select distinct SOHIEUTAIKHOAN from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '63280000' )   as SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,   N'"+tensanpham+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a \n"+ 
								" WHERE a.pk_seq = '" + loaisanpham + "' \n"+ 
																					  
								" UNION ALL \n"+

								" SELECT N'CÓ' NO_CO, "+id+" ID, ( select distinct SOHIEUTAIKHOAN from ERP_TAIKHOANKT where PK_SEQ = a.TAIKHOANKT_FK )  as SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,  N'"+tensanpham+"'  DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a \n"+ 
								" WHERE a.pk_seq = '" + loaisanpham + "' \n"; 	
							System.out.println(laytk);
						}
						
						if(ndxId.equals("100022")){
							laytk += 
								" SELECT N'NỢ' NO_CO, "+id+" ID,  '63230000'  SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,  N'"+tensanpham+"'  DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a \n"+
								" WHERE a.PK_SEQ ='"+loaisanpham+"' \n"+

								" UNION ALL \n"+

								" SELECT N'CÓ' NO_CO, "+id+" ID,(SELECT distinct SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = a.TaiKhoanKt_fk ) SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,   N'"+tensanpham+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a \n"+
								" WHERE a.PK_SEQ ='"+loaisanpham+"' \n";
							System.out.println(laytk);
						}	
						
						if(ndxId.equals("100027")||ndxId.equals("100028")){
							laytk +=
								" SELECT N'NỢ' NO_CO, "+id+" ID, '63230000' SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,  N'"+tensanpham+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a \n"+
								" WHERE a.PK_SEQ = '"+loaisanpham+"' \n"+

								" UNION ALL \n"+

								" SELECT N'CÓ' NO_CO, "+id+" ID, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = a.TaiKhoanKt_fk) SOHIEUTAIKHOAN, "+thanhtien+" SOTIEN,  N'"+tensanpham+"'  DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a \n"+
								" WHERE a.PK_SEQ = '"+loaisanpham+"'  \n";
							System.out.println(laytk);
						}						
						
						i++;
					}
				}
			}
			catch (SQLException e) {}
		}
		
			if(laytk.trim().length()>0) {
				laytk += " ORDER BY ID, STT, SAPXEP ";
			}				
			
			if(laytk.trim().length() <=0 ){
				laytk += 
					" SELECT '' NO_CO, '' ID, '' SOHIEUTAIKHOAN, '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n" +
					" FROM ERP_XUATKHO a" +
					" WHERE a.PK_SEQ ='"+id+"'";
			}
			
			//System.out.println(laytk);
		return laytk;
	}
	
	public void init(String search) {
		String query = "";
		
		
		if (search.length() <= 0) {
			query = "		SELECT DISTINCT a.PK_SEQ as pxkId,a.noidungxuat, a.NGAYXUAT, b.MA + ' - ' + b.TEN as ndxTen, \n"
				+ 	"		a.PREFIX + cast(a.PK_SEQ as varchar(20)) as soxuatkho, \n"
				+ 	"		case when a.TRAHANGNCC_FK is null \n"  
				+ 	"		then (cast('' as varchar(20)))  "  
				+ 	"		else (isnull(g.PREFIX,'') + isnull(f.PREFIX,'') + cast(a.TRAHANGNCC_fk as varchar(20)) ) \n"
				+ 	"		end as HOADON_FK, case when a.TRAHANGNCC_FK is not null then cast(a.TRAHANGNCC_FK as varchar(20))   "+
				"  			when a.dondathang_fk is not null then '130'+cast(a.dondathang_fk as varchar(20))   \n"
				+	"		else    isnull(HD.SOHOADON,'')  end as SOHOADON, \n"
				+ 	"		a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua  \n"
				+ 	"       FROM 	ERP_XUATKHO a inner join ERP_NOIDUNGNHAP b on a.NOIDUNGXUAT = b.PK_SEQ \n"
				+ 	"		INNER JOIN NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ \n"
				+ 	"		LEFT JOIN ERP_HOADON HD on a.HOADON_FK = HD.PK_SEQ \n"
				+ 	"		LEFT JOIN ERP_MUAHANG f on a.TRAHANGNCC_FK = f.pk_seq \n"
				+ 	"		LEFT JOIN ERP_DONVITHUCHIEN g on f.donvithuchien_fk = g.pk_seq  " +
					"   	WHERE 1 =  1 ";
			
			
			
 
		} else{
			query = search;
		}
		
		query+=" and a.kho_fk in "+this.util.quyen_khott(this.userId);
		System.out.println("Query init xuat kho: " + query);

		String query_init = createSplittingData_List(25, 10, "pxkId desc,NGAYXUAT desc, trangthai asc ", query);
				
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
					String dk = LayDuLieu(rs.getString("pxkId"));
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{
								kt = new DinhKhoanKeToan(rsKT.getString("id"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
										 rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
								ktList.add(kt);
							}
							rsKT.close();
						}
												
					// INIT
					
						ht = new ThongTinHienThi();
						ht.setId(rs.getString("pxkId"));
						ht.setNoidungxuat(rs.getString("noidungxuat"));
						ht.setTrangthai(rs.getString("trangthai"));
						ht.setSoxuatkho(rs.getString("SOXUATKHO"));
						ht.setNgayxuat(rs.getString("NGAYXUAT"));
						ht.setSoxuatkho(rs.getString("SOXUATKHO"));
						ht.setHoadonId(rs.getString("HOADON_FK"));
						ht.setSohoadon(rs.getString("SOHOADON"));
						ht.setndxTen(rs.getString("NDXTEN"));
						ht.setNgaytao(rs.getString("NGAYTAO"));
						ht.setNgaysua(rs.getString("NGAYSUA"));
						ht.setNguoitao(rs.getString("NGUOITAO"));
						ht.setNguoisua(rs.getString("NGUOISUA"));
						
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
		 
		
		this.khoxuatRs= db.get("select * from erp_khott where pk_seq in "+ this.util.quyen_khott(this.userId)+"  and pk_seq in(100003, 100004, 100012, 100013, 100014) " );
		this.noidungxuatRs = db.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('XK')");
		
		  query=" SELECT distinct 100008 as ndxid, " +
		  		"  A.PK_SEQ,  A.PREFIX + CAST(A.PK_SEQ AS NVARCHAR(10))  AS SOHOADON ,A.TRANGTHAI,A.NGAYDAT +' (KM)' AS NGAYDAT,  ISNULL(KH.TenXuatHD,'')  AS TENKH ,A.SOTIENAVAT "+ 
				" ,ND.TEN AS NGUOIDUYET,  NT.TEN AS NGUOITAO, CASE YeuCauGDDuyet when '0' then A.NGAYSUA else '' end  AS NGAYKTDUYET \n" +
				" from ERP_DONDATHANG A "+
				" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=A.NGUOITAO "+
				" LEFT JOIN NHANVIEN ND ON ND.PK_SEQ=A.NGUOIDUYETKT "+
				" INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=A.KHACHHANG_FK  "+
				" INNER JOIN ERP_DONDATHANG_CTKM_TRAKM DH_KM ON DH_KM.DONDATHANGID=A.PK_SEQ   "+
				" where A.TRANGTHAI >=4 and isnull(IS_HOANTATXK,'0')='0'   AND DH_KM.SPMA IS NOT NULL  AND NGAYDAT >='2015-04-01'  and a.trangthai <>7 "+  
				" AND LOAIDONHANG in ( '6','1','2','3','4') "+ 
				" AND KH.PK_SEQ IN  "+this.util.quyen_npp(userId)+
				" AND DH_KM.KHOTT_FK in "+this.util.quyen_khott(this.userId)+
				" AND NOT EXISTS (	SELECT PK_SEQ FROM ERP_XUATKHO XK WHERE XK.DONDATHANG_FK= A.PK_SEQ AND XK.KHO_FK=DH_KM.KHOTT_FK AND XK.TRANGTHAI NOT IN ('2') AND XK.NOIDUNGXUAT =100008) " +
				" union     "+
				" SELECT  distinct case   LOAIDONHANG  when 1  then 100002 when 2 then 100027 when 3 then 100028 when 4 then 100029 when 6  then 100008 end as ndxid  , " +
				"  A.PK_SEQ,  A.PREFIX + CAST(A.PK_SEQ AS NVARCHAR(10))  AS SOHOADON ,A.TRANGTHAI,A.NGAYDAT, ISNULL(KH.TenXuatHD,'')  AS TENKH,A.SOTIENAVAT "+
				"  ,ND.TEN AS NGUOIDUYET,  NT.TEN AS NGUOITAO, CASE YeuCauGDDuyet when '0' then A.NGAYSUA else '' end AS NGAYKTDUYET " +
				"  from ERP_DONDATHANG  A   "+
				" INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=A.KHACHHANG_FK "+
				" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=A.NGUOITAO "+
				" LEFT JOIN NHANVIEN ND ON ND.PK_SEQ=A.NGUOIDUYETKT "+
				" INNER JOIN ERP_DONDATHANG_SP DHSP ON A.PK_SEQ=DHSP.DONDATHANG_FK "+
				" where A.TRANGTHAI >=4 and isnull(IS_HOANTATXK,'0')='0'  "+
				" AND NGAYDAT >='2015-04-01' and LOAIDONHANG in ( '6','1','2','3','4') and a.trangthai <>7 "+
				" AND KH.PK_SEQ IN  "+this.util.quyen_npp(userId)+
				" AND DHSP.KHOTT_FK IN "+this.util.quyen_khott(this.userId) +
				" AND NOT EXISTS (	SELECT PK_SEQ FROM ERP_XUATKHO XK WHERE XK.DONDATHANG_FK= A.PK_SEQ AND XK.KHO_FK=DHSP.KHOTT_FK AND XK.TRANGTHAI NOT IN ('2')  AND XK.NOIDUNGXUAT =(case   LOAIDONHANG  when 1  then 100002 when 2 then 100027 when 3 then 100028 when 4 then 100029 when 6  then 100008 end) ) "  ;
		  
		  
		  System.out.println(query);
		this.rsdh_daduyet=db.get(query);
		
		
	}

	public void DBclose() {
		this.db.shutDown();
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSoChungTu() {

		return this.sochungtu;
	}

	public void setSoChungTu(String sochungtu) {
		this.sochungtu = sochungtu;

	}

	public String getXuatKhoId() {

		return this.xuatkhoId;
	}

	public void setXuatKhoId(String xuatkhoId) {

		this.xuatkhoId = xuatkhoId;
	}

	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
	}




	
	public ResultSet getNoidungxuatRs() {
		
		return this.noidungxuatRs;
	}




	
	public void setNoidungxuatRs(ResultSet noidungxuatRs) {
		
		this.noidungxuatRs = noidungxuatRs;
	}




	
	public String getnoidungxuatId() {
		
		return this.noidungxuatId;
	}




	
	public void setnoidungxuatId(String noidungxuatId) {
		
		this.noidungxuatId = noidungxuatId;
	}




	
	public String getSohoadon() {
		
		return this.sohoadon;
	}




	
	public void setSohoadon(String sohoadon) {
		
		this.sohoadon = sohoadon;
	}




	@Override
	public ResultSet getrsDonhang_daduyet() {
		// TODO Auto-generated method stub
		return rsdh_daduyet;
	}

}
