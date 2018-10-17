package geso.traphaco.erp.beans.phieuxuatkhoTH.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;
 
import geso.traphaco.erp.beans.phieuxuatkhoTH.IErpPhieuxuatkhoList;
 
public class ErpPhieuxuatkhoList extends Phan_Trang implements
		IErpPhieuxuatkhoList {
	private static final long serialVersionUID = 1L;
	String ctyId;
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
	
	public String getCtyId() {
		return this.ctyId;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
		 
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
			" inner join KHO c on a.kho_fk = c.pk_seq " +			
			" where a.pk_seq = '"  + id + "'";
		
		System.out.println(query);
		
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
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		query = "SELECT NGAYXUAT FROM ERP_XUATKHO WHERE PK_SEQ = " + id;
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
		int thangtruoc = 0;
		int namtruoc =0;
		
		if(ngaychotnv.length()>5){
			  thangtruoc = Integer.parseInt(ngaychotnv.substring(5, 7));
			  namtruoc = Integer.parseInt(ngaychotnv.substring(0, 4));
		}
		
		
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
		"		INNER JOIN SANPHAM SP ON SP.PK_SEQ = A.SANPHAM_FK  " +
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
								
					double giavon = 0;
					query = " SELECT SANPHAM_FK, AVG(ISNULL(GIATON, 0)) AS GIAVON  " +
							" FROM ERP_TONKHOTHANG " +
							" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
							" AND NAM = '" + namtruoc + "' AND CONGTY_FK = " + this.ctyId + "" +
							" GROUP BY SANPHAM_FK " ;
					
					System.out.println(query);
					ResultSet rsgia = db.get(query); 
					if(rsgia.next()){
						giavon = rsgia.getDouble("GIAVON");
					}
					rsgia.close();								
					
					double thanhtien = giavon*soluongct;
					
					System.out.println("ndxId"+ ndxId);
					if( ndxId.equals("100007"))
					{
						if (laytk.trim().length() > 0) laytk += " UNION ALL \n";
						
						
						laytk +=
								" SELECT N'NỢ' NO_CO, " + id + " ID, " +
								"'63212000' AS SOHIEUTAIKHOAN, " +
								" " + thanhtien + " SOTIEN,  b.TEN AS DOITUONG, " +
								"'' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, " + i + " STT, 1 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a, ERP_NHACUNGCAP b \n"+
								" WHERE a.PK_SEQ = '"+loaisanpham+"' " +
								"and b.PK_SEQ in (select NHACUNGCAP_FK from erp_muahang where pk_seq in (select TRAHANGNCC_FK from ERP_XUATKHO where pk_seq = " + id + ")) \n"+

								" UNION ALL \n"+

								" SELECT N'CÓ' NO_CO, "+id+" ID,  " +
								"( a.TaiKhoanKt_fk) SOHIEUTAIKHOAN, " +
								""+thanhtien+" SOTIEN, N'" + tensanpham + "' AS  DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
								" FROM ERP_LOAISANPHAM a, ERP_NHACUNGCAP b \n"+
								" WHERE a.PK_SEQ = '"+loaisanpham+"' " +
								"and b.PK_SEQ in (select NHACUNGCAP_FK from erp_muahang where pk_seq in (select TRAHANGNCC_FK from ERP_XUATKHO where pk_seq = " + id + ")) \n";
						System.out.println(laytk);
						
						
						
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
			query = "		SELECT DISTINCT a.PK_SEQ as pxkId,a.noidungxuat, a.NGAYXUAT, b.MA + ' - ' + b.TEN as ndxTen, c.SOHOADON as sohoadon1 ,\n"
				+ 	"		a.PREFIX + cast(a.PK_SEQ as varchar(20)) as soxuatkho, \n"
				+ 	"		case when a.TRAHANGNCC_FK is null \n"  
				+ 	"		then (cast('' as varchar(20)))  "  
				+ 	"		else (isnull(g.PREFIX,'') + isnull(f.PREFIX,'') + cast(a.TRAHANGNCC_fk as varchar(20)) ) \n"
				+ 	"		end as HOADON_FK, case when a.TRAHANGNCC_FK is not null then cast(a.TRAHANGNCC_FK as varchar(20))   "+
				"  			when a.dondathang_fk is not null then '130'+cast(a.dondathang_fk as varchar(20))   \n"
				+	"		else    isnull(HD.SOHOADON,'')  end as SOHOADON, \n"
				+ 	"		a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, isnull(a.machungtu,'') machungtu  \n"
				+ 	"       FROM 	ERP_XUATKHO a " +
					"		inner join ERP_NOIDUNGNHAP b on a.NOIDUNGXUAT = b.PK_SEQ \n"
				+ 	"		INNER JOIN NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ \n"
				+ 	"		LEFT JOIN ERP_HOADON HD on a.HOADON_FK = HD.PK_SEQ \n"
				+ 	"		LEFT JOIN ERP_MUAHANG f on a.TRAHANGNCC_FK = f.pk_seq \n"
				+	"		left join ERP_HOADON_DDH dh on f.PK_SEQ=dh.DDH_FK left join ERP_HOADON c on dh.HOADON_FK=c.PK_SEQ "
				+ 	"		LEFT JOIN ERP_DONVITHUCHIEN g on f.donvithuchien_fk = g.pk_seq  " +
					"   	WHERE  c.TRANGTHAI<>2 and 1 =  1 ";			
 
		} else{
			query = search;
		}
		
		//query+=" and a.kho_fk in "+this.util.quyen_khott(this.userId);
		System.out.println("Query init xuat kho: " + query);

		String query_init = createSplittingData_ListNew(this.db, 25, 10, "pxkId desc,NGAYXUAT desc, trangthai asc ", query);
				
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
					String dk = LayDuLieu(rs.getString("pxkId") );
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
						ht.setmachungtu(rs.getString("machungtu"));
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
						ht.setSohoadon(rs.getString("sohoadon1"));
						
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
		
		// Lấy nội dung xuất mặc định là Xuất trả NCC
		query=" SELECT distinct 100062 as ndxid, "+
				"(SELECT SOHOADON FROM ERP_HOADON HD INNER JOIN  ERP_HOADON_DDH HD_DDH ON  HD.PK_SEQ=HD_DDH.HOADON_FK \n"+
				 "  WHERE HD.TRANGTHAI=1 AND HD_DDH.DDH_FK=a.pk_seq )  AS SOHOADON1, \n"+
			  " A.PK_SEQ,  A.PREFIX + CAST(A.PK_SEQ AS NVARCHAR(10))  AS SOHOADON , \n"
			  + "A.TRANGTHAI,A.NGAYMUA  AS NGAYDAT, '' as nguoiduyet,   \n" +
			  " ISNULL(KH.TEN,'')  AS TENKH ,A.TONGTIENAVAT as sotienavat \n"+  
			  " ,NT.TEN AS NGUOITAO,  NT.TEN AS NGUOITAO, '' AS  NGAYKTDUYET   \n"+
			  " from ERP_MUAHANG A  \n "+
			  " INNER JOIN NHANVIEN NT ON NT.PK_SEQ=A.NGUOITAO  \n"+ 
			  " INNER JOIN ERP_NHACUNGCAP KH ON KH.PK_SEQ=A.NHACUNGCAP_FK  \n"+  
			  " where A.TYPE = '2' and A.TRANGTHAI  in ('1','2','3')  \n " +
			  " And A.PK_SEQ not in (select TRAHANGNCC_FK from ERP_XUATKHO where TRANGTHAI not in (2)) \n "
			  + " and  exists  (SELECT PK_SEQ FROM ERP_HOADON HD INNER JOIN  ERP_HOADON_DDH HD_DDH ON  HD.PK_SEQ=HD_DDH.HOADON_FK \n "
			  + "  WHERE HD.TRANGTHAI=1 AND HD_DDH.DDH_FK=a.pk_seq )  \n";
		  
		System.out.println("Ds duyet:"+query);
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
