package geso.traphaco.erp.beans.donbanhang.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.donbanhang.IErp_DonbanhangList;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Erp_DonbanhangList extends Phan_Trang implements IErp_DonbanhangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	private String congtyId;
	private String userId;
	private String ten;
	private String sku;
	private String tungay;
	private String denngay;
	private String loaidonhang = "";
	private String trangthai, kbhid;
	private String maspstr;
	private String kvId;	
	private String so;
	private String nguoitaoten="";
	private String khodatid;
	private ResultSet kvList;
	private ResultSet dhList;
	private ResultSet dhKTlist, kbhRs;
	
	ResultSet dvkdRs;
	ResultSet khodatRs;
	
	private String malist;
	private dbutils db;
	
	ResultSet hopdongRs;
	String hopdongId = "";
	
	ResultSet khachHangRs;
	String khachhangId = "";
	
	ResultSet nguoiTaoRs;
	String nguoiTaoId = "";

	String msg;
	String dvkd;
	String isDhKhuyenMai;
	String nguoiduyet;
	
	String duyetdh;
	
	String IDkenh, sohoadon, phieuxuatkhoId, ngaytaodh;
	
	private Utility util;
	
	public Erp_DonbanhangList(String[] param)
	{
		this.db = new dbutils();
		this.sku = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.msg = "";
		this.isDhKhuyenMai = "0";
		this.nguoiduyet = "";
		this.hopdongId = "";
		this.duyetdh="";
		this.khodatid="";
		this.kbhid = "";
		this.sohoadon = "";
		this.phieuxuatkhoId = "";
		this.ngaytaodh = "";
		util = new Utility();
	}
	
	public Erp_DonbanhangList()
	{
		this.db = new dbutils();
		this.sku = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.dvkd="";
		this.kvId	= "";
		this.so = "";
		this.khodatid="";
		this.msg = "";
		this.isDhKhuyenMai = "0";
		this.maspstr = "";
		this.IDkenh="";
		this.nguoiduyet = "";
		this.hopdongId = "";
		this.duyetdh="";
		this.kbhid = "";
		this.sohoadon = "";
		this.phieuxuatkhoId = "";
		this.ngaytaodh = "";
		util = new Utility();
	}
	
	
	public String getIDKenhBanHang(){
		return this.IDkenh;
	}
	public void setIDKenhBanHang(String IDkenh){
		this.IDkenh = IDkenh;
	}
	
	
	
	
	
	public String getDvkd() {
		return dvkd;
	}
	public void setDvkd(String dvkd) {
		this.dvkd = dvkd;
	}
	
	public ResultSet getDvkdRs() {
		return dvkdRs;
	}
	public void setDvkdRs(ResultSet dvkdRs) {
		this.dvkdRs = dvkdRs;
	}
	
	public ResultSet getDhList()
	{
		return this.dhList;
	}
	
	public void setDhList(ResultSet dhList)
	{
		this.dhList = dhList;
	}

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getTen()
	{
		return this.ten;
	}
	
	public void setTen(String ten)
	{
		this.ten = ten;
	}

	public String getSKU()
	{
		return this.sku;
	}
	
	public void setSKU(String sku)
	{
		this.sku = sku;
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
	
	public void setLoaiDonHang(String loaidonhang)
	{
		this.loaidonhang = loaidonhang;
	}
	
	public String getLoaiDonHang()
	{
		return this.loaidonhang;
	}
	
	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getMalist()
	{
		return this.malist;
	}
	
	public void setMalist(String malist)
	{
		this.malist = malist;
	}
	
	
	public String getSO() 
	{	
		return so;
	}

	
	public void setSO(String so) {
		
		this.so = so;
	}
	
	
	public ResultSet getDdhKTList() 
	{		
		return this.dhKTlist;
	}

	public String getMaspstr() 
	{
		String query = "select pk_seq as id, ma, ten from sanpham order by pk_seq";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				while(rs.next())
				{
					if (this.maspstr.length()==0)
					{
						this.maspstr = "\"" + rs.getString("ma") +  " - " + rs.getString("ten") + "\"";
					}
					else
					{
						this.maspstr = this.maspstr + ",\"" + rs.getString("ma") +  " - " + rs.getString("ten") + "\"";
					}
				}
				rs.close();
			}
			catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
		this.maspstr = maspstr;
	}
	
	public ResultSet getKvList(){
		
		return this.kvList;
	}
	
	public void setKvList(ResultSet kvList){
		this.kvList = kvList;
	}
	
	public String getKvId(){
		return this.kvId;
	}
	
	public void setKvId(String kvId){
		this.kvId = kvId;
	}
	
	public void createDdhlist(String querystr)
	{
		String query;
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();	
		
		if (querystr.length() > 0 )
		{
			query = querystr;
		}
		else
		{
			  query =   " SELECT   A.NGAYDAT, A.PREFIX, A.PK_SEQ AS CHUNGTU, F.TEN AS NPPTEN, E.DONVIKINHDOANH, A.GHICHU,  \n"+
			    		" A.SOTIENAVAT, B.TEN AS NGUOITAO, C.TEN AS NGUOISUA, A.TRANGTHAI, ISNULL( A.SOID ,'0') AS SOID,   ISNULL(A.SOTIENAVAT,0 ) AS TIENHD,  \n"+
			    		" ISNULL(A.SOTIENBVAT, '0') AS SOTIENBVAT, ( ISNULL(A.SOTIENBVAT, '0') * ISNULL(A.CHIETKHAU, 0) / 100 ) AS TIENCK, ISNULL(A.VAT, 10) AS VAT,  \n"+
			    		" ( SELECT ISNULL(SUM(TONGGIATRI), 0) AS TONGTIENKM FROM ERP_DONDATHANG_CTKM_TRAKM WHERE DONDATHANGID = A.PK_SEQ AND SPMA IS  NULL )  AS TIENKM,   \n"+
			    		" A.KBH_FK AS IDKENH, CASE A.LOAIDONHANG WHEN 1 THEN N'ĐƠN HÀNG BÁN' WHEN 2 THEN N'BIẾU TẶNG PHỤC VỤ CHO BÁN HÀNG' WHEN 3 THEN N'BIẾU TẶNG PHỤC VỤ CHO QUẢN LÝ' WHEN 4 THEN 'BIẾU TẶNG PHỤC VỤ CHO SẢN XUẤT' WHEN 5 THEN N'ĐƠN HÀNG NỘI BỘ' WHEN 6 THEN N'ĐƠN HÀNG KHUYẾN MÃI'  WHEN 7 THEN N'ĐƠN HÀNG ĐẢO GỐI ĐẦU' END AS LOAIDONHANG, A.NGAYTAO \n"+   
			    		" FROM ERP_DONDATHANG A INNER JOIN \n"+  
			    		" NHANVIEN B ON  A.NGUOITAO = B.PK_SEQ  \n"+ 
			    		" INNER JOIN  NHANVIEN C ON A.NGUOISUA = C.PK_SEQ  \n"+ 
			    		" INNER JOIN DONVIKINHDOANH E ON A.DVKD_FK = E.PK_SEQ  \n"+  
			    		" INNER JOIN  ERP_KHACHHANG  F  ON A.KHACHHANG_FK = F.PK_SEQ \n"+  
			    		" WHERE ( A.TRANGTHAI <> '0')  AND A.ISKM = '0'  \n"+
						" and f.pk_seq in  " + util.quyen_npp(userId) +
						" and a.KBH_FK in  " + util.quyen_kenh(userId);
			  
			  	String query2 = " select isnull(is_viewSO, 0) as is_viewSO from nhanvien where pk_seq="+userId;
				
				ResultSet rs1 = db.get(query2);
				if(rs1!=null){ 
					try{
						while(rs1.next()){
							if(!rs1.getString("is_viewSO").equals("1")){
								query  +=  " and (a.nguoitao in "+util.quyen_xemdon_mua_ban(userId)+" or a.nguoitao="+userId+")  ";
							}
						}rs1.close();
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
			  
			  
			  
			  System.out.println("achfhrg");
		}
		
		//System.out.println("Khoi tao don hang - ban dau 12333: " + query);
		this.dhList =  createSplittingDataNew(this.db, 50, 10, "trangthai asc, ngaydat desc, chungtu desc", query); 
		
		
		this.dvkdRs= db.get("SELECT PK_SEQ,DONVIKINHDOANH as ten FROM DONVIKINHDOANH WHERE TRANGTHAI = '1' ");
//		this.khodatRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and pk_seq in "+util.quyen_khott(this.userId)+" and pk_seq in (100003,100004,100012,100013,100014,100023,100024) order by loai asc ");
		query = " SELECT PK_SEQ, CASE WHEN PK_SEQ=100003 THEN N'Kho thành phẩm - Hải Phòng' ELSE "+ 
		 " TEN END AS TEN  FROM ERP_KHOTT WHERE TRANGTHAI=1 AND PK_SEQ IN (SELECT KHOTT_FK FROM NHANVIEN_KHOTT WHERE NHANVIEN_FK="+this.userId+" ) "+ 
		 " AND PK_SEQ IN (100003 ,100012,100013,100014,100000,100001,100002,100023,100024) ";
		this.khodatRs = db.get(query);
		this.nguoiTaoRs = db.get(" select pk_seq, ten from NHANVIEN where trangthai = 1 and pk_seq in ( select distinct nguoitao from ERP_DonDatHang ) order by ten, pk_seq ");
		this.khachHangRs = db.get(" select pk_seq, ma , ten   from ERP_KHACHHANG where trangthai = 1 and pk_seq in  " + ut.quyen_npp(this.userId) + " order by ten, pk_seq ");
		
		 

	}
	
	public void DBclose(){
			try{
				if(kvList!=null){
					kvList.close();
				}
				if(khodatRs!=null){
					khodatRs.close();
				}
				if(dhKTlist!=null){
					dhKTlist.close();
				}
				if(dhList!=null){
					dhList.close();
				}
				if(dvkdRs!=null){
					dvkdRs.close();
				}
				if(khachHangRs!=null){
					khachHangRs.close();
				}
				if(nguoiTaoRs!=null){
					nguoiTaoRs.close();
				}
				if(kbhRs!=null){
					kbhRs.close();
				}
				if(hopdongRs!=null){
					hopdongRs.close();
				}
				
				
			}
			catch(Exception err){
				err.printStackTrace();
			}
			finally{
				System.out.println("da closeeeeeeeeeeeeeeeeeeeee");
				if(!(this.db == null)){
					this.db.shutDown();
				}
			}
	}

	
	public void createDdhKTlist(String querystr) {
		
		String query;
		
		if (querystr.length() > 0)
		{
			query = querystr;
		}
		else
		{			
			query =     " select distinct a.ngaydat, a.PREFIX, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh, a.ghichu, "+   
						"  cast(isnull(a.sotienavat, 0) as float) as sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, "+ 
						" isnull (nh.ngaychungtu,'0')as ngayhd,  cast(isnull(nh.sotienbvat,'0') as float) as tienhd , ISNULL(ISKM, 0) as loai  " +
						" from ERP_DonDatHang a "+ 
						" inner join  nhanvien b on  a.nguoitao = b.pk_seq    inner join  nhanvien c on a.nguoisua = c.pk_seq "+  
						" inner join donvikinhdoanh e on a.dvkd_fk = e.pk_seq   inner join  ERP_KhachHang  f  on a.khachhang_fk = f.pk_seq    "+
						" left join nhaphang nh on nh.dathang_fk=a.pk_seq   where ( a.trangthai  > 1  and a.trangthai <=3 and isnull(a.DaDuyet,0)=1 ) and a.congty_fk = '" + this.congtyId + "' " +
				 		" and f.pk_seq  in " + util.quyen_npp(this.userId) +
						" and a.KBH_FK in  " + util.quyen_kenh(this.userId) +
						" union all " +
						" select distinct dth.ngaytra as ngaydat, dth.PREFIX, dth.pk_seq as chungtu,npp.ten as  "+
						" nppTen,dvkd.donvikinhdoanh, '' as ghichu, cast(  dth.sotienavat as float) as sotienavat,  nt.ten as nguoitao,ns.ten as nguoisua,dth.trangthai,'' "+ 
						" as soid,'' as ngayhd,cast (0 as float)  as tienhd, 2 as loai  " +
						" from dontrahang dth inner join Erp_KhachHang npp on  "+
						" dth.khachhang_fk = npp.pk_seq  inner join nhanvien nt on nt.pk_seq=dth.nguoitao  inner join nhanvien ns "+
						" on ns.pk_seq = dth.nguoisua  inner join donvikinhdoanh dvkd on dvkd.pk_Seq = dth.dvkd_fk  " +
						" where dth.trangthai >= 2 and dth.trangthai <= 3  and isnull(dth.DaDuyet,0)=1  and dth.congty_fk = '" + this.congtyId + "' " +
				 		" and dth.khachhang_fk in  " + util.quyen_npp(this.userId) +
						" and dth.KBH_FK in  " + util.quyen_kenh(this.userId);
		}
	
		
		this.dhKTlist =  createSplittingDataNew(this.db, 50, 10, "ngaydat desc, trangthai asc", query); //this.db.msget2(query);
		this.dvkdRs= db.get("SELECT PK_SEQ,DONVIKINHDOANH as ten FROM DONVIKINHDOANH ");
//		this.khodatRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and pk_seq in "+util.quyen_khott(this.userId)+" and pk_seq in (100003,100004,100012,100013,100014,100023,100024) order by loai asc ");
		String sql= " SELECT PK_SEQ, CASE WHEN PK_SEQ=100003 THEN N'Kho thành phẩm - Hải Phòng' ELSE "+ 
		 " TEN END AS TEN  FROM ERP_KHOTT WHERE TRANGTHAI=1 AND PK_SEQ IN (SELECT KHOTT_FK FROM NHANVIEN_KHOTT WHERE NHANVIEN_FK="+this.userId+" ) "+ 
		 " AND PK_SEQ IN (100003 ,100012,100013,100014,100000,100001,100002,100023,100024) ";
		this.khodatRs = db.get(sql);
		query = "select ten from nhanvien where pk_seq='" + this.userId + "'";
		
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				if (rs.next())
					this.ten = rs.getString("ten");
				rs.close();
			}
			catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see geso.traphaco.erp.beans.donbanhang.IErp_DonbanhangList#DuyetDonHangRs(java.lang.String)
	 * Hiển thị những đơn hàng đã chốt,để user có quyền duyệt
	 * Sau đó kế toán mới vào duyệt tiếp theo
	 */
	
	public void createDuyetDonHangRs(String querystr) {
		
		String query;
		
		if (querystr.length() > 0)
		{
			query = querystr;
		}
		else
		{			
			  	query =      " SELECT   A.NGAYDAT, A.PREFIX, A.PK_SEQ AS CHUNGTU, F.TEN AS NPPTEN, E.DONVIKINHDOANH, kbh.ten as kenhbanhang, A.GHICHU,  "+
        	 " CAST(ISNULL(A.SOTIENAVAT, 0) AS FLOAT) AS SOTIENAVAT, B.TEN AS NGUOITAO, C.TEN AS NGUOISUA, A.TRANGTHAI, ISNULL(A.YEUCAUGDDUYET,0) YEUCAUGDDUYET ,1 AS TYPE, ISNULL(A.THOAHANMUC,1) THOAHANMUC "+  
        	 " FROM   ERP_DONDATHANG A  "+
        	 " left JOIN  NHANVIEN B ON  A.NGUOITAO = B.PK_SEQ "+   
        	 " left JOIN  NHANVIEN C ON A.NGUOISUA = C.PK_SEQ   "+ 
        	 " left JOIN DONVIKINHDOANH E ON A.DVKD_FK = E.PK_SEQ    "+
        	 " left JOIN  ERP_KHACHHANG  F  ON A.KHACHHANG_FK = F.PK_SEQ "+ 
        	 " left join KENHBANHANG kbh on kbh.PK_SEQ=A.KBH_FK " +
        	 " WHERE  ( A.TRANGTHAI IN ('2','3','4') )  UNION ALL  " +	
			  " SELECT   A.NGAYTRA, A.PREFIX, A.PK_SEQ AS CHUNGTU, F.TEN AS NPPTEN, E.DONVIKINHDOANH, kbh.ten as kenhbanhang, A.GHICHU, "+
			 " CAST(ISNULL(A.SOTIENAVAT, 0) AS FLOAT) AS SOTIENAVAT, B.TEN AS NGUOITAO, C.TEN AS NGUOISUA, A.TRANGTHAI, 0 YEUCAUGDDUYET ,2 AS TYPE, 1 THOAHANMUC "+  
			 " FROM   DONTRAHANG A  "+
			 " left JOIN  NHANVIEN B ON  A.NGUOITAO = B.PK_SEQ "+   
			 " left JOIN  NHANVIEN C ON A.NGUOISUA = C.PK_SEQ    "+
			 " left JOIN DONVIKINHDOANH E ON A.DVKD_FK = E.PK_SEQ   "+ 
			 " left JOIN  ERP_KHACHHANG  F  ON A.KHACHHANG_FK = F.PK_SEQ "+ 
			 " left join KENHBANHANG kbh on kbh.PK_SEQ=A.KBH_FK " +
			 " WHERE  ( A.TRANGTHAI IN ('2','3','4') )";	
		}
		System.out.println(query);
		
		this.duyetDonHangRs =  createSplittingDataNew(this.db, 50, 10, " ngaydat desc, trangthai asc ", query); //this.db.msget2(query);
		this.dvkdRs= db.get("SELECT PK_SEQ,DONVIKINHDOANH as ten FROM DONVIKINHDOANH ");
		this.hopdongRs = db.get("select pk_seq, mahopdong from ERP_HOPDONG where trangthai in( 1, 2 ) order by pk_seq desc ");
		
		System.out.println("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and pk_seq in "+util.quyen_khott(this.userId)+" and pk_seq in (100003,100004,100012,100013,100014,100023,100024) order by loai asc ");
//		this.khodatRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and pk_seq in "+util.quyen_khott(this.userId)+" and pk_seq in (100003,100004,100012,100013,100014,100023,100024) order by loai asc ");
		String sql= " SELECT PK_SEQ, CASE WHEN PK_SEQ=100003 THEN N'Kho thành phẩm - Hải Phòng' ELSE "+
		 " TEN END AS TEN  FROM ERP_KHOTT WHERE TRANGTHAI=1 AND PK_SEQ IN (SELECT KHOTT_FK FROM NHANVIEN_KHOTT WHERE NHANVIEN_FK="+this.userId+" ) "+ 
		 " AND PK_SEQ IN (100003 ,100012,100013,100014,100000,100001,100002,100023,100024) ";
		this.khodatRs = db.get(sql);
		this.kbhRs = db.get("select pk_seq, diengiai from KENHBANHANG WHERE TRANGTHAI = 1");
		
		query = "select ten from nhanvien where pk_seq='" + this.userId + "'";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				if (rs.next())
					this.ten = rs.getString("ten");
				rs.close();
			}
			catch(java.sql.SQLException e){e.printStackTrace();}
		}
		
		query = " select DISTINCT d.TEN as nvTen  " +
				" from PHANQUYEN a inner join DANHMUCQUYEN b on a.DMQ_fk = b.PK_SEQ inner join NHOMQUYEN c on a.DMQ_fk = c.DMQ_fk " +
				" inner join NHANVIEN d on a.Nhanvien_fk = d.PK_SEQ  " +
				" where c.CHOT = 1 and c.UngDung_fk = '68' and d.trangthai = 1 ";
		
		System.out.println("Du lieu : "+query);
		ResultSet nvRs = db.get(query);
		if(nvRs != null)
		{
			String nvTen = "";
			try 
			{
				while(nvRs.next())
				{
					nvTen += nvRs.getString("nvTen") + ", ";
				}
				nvRs.close();
				
				if(nvTen.trim().length() > 0)
				{
					nvTen = nvTen.substring(0, nvTen.length() - 2);
					this.nguoiduyet = nvTen;
				}
				nvRs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	public void SetMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setIsDhKhuyenMai(String isKm) 
	{
		this.isDhKhuyenMai = isKm;
	}

	public String geIsDhKhuyenMai()
	{
		return this.isDhKhuyenMai;
	}

	public void createDthlist(String querystr) 
	{
		String query;
		if (querystr.length() > 0 )
		{
			query = querystr;
		}
		else
		{
			query = " select distinct a.ngaytra, a.PREFIX, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,  "+
						" a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai, '' as soid, '' as ngayhd, "+
						" 0 as tienhd " +
					" from DonTraHang a inner join "+ 
					" nhanvien b on  a.nguoitao = b.pk_seq   " +
					" inner join  nhanvien c on a.nguoisua = c.pk_seq  " +
					" inner join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  " +
					" inner join  ERP_KHACHHANG  f  on a.khachhang_fk = f.pk_seq  " + 
					" where ( a.trangthai <> '0') and a.congty_fk = '" + this.congtyId + "' and f.pk_Seq in   "+ util.quyen_npp(this.userId) ;
		}
		
		System.out.println("Khoi tao don tra hang: " + query);
		this.dhList =  createSplittingDataNew(this.db, 50, 10, "ngaytra desc, trangthai asc, chungtu desc", query); //this.db.msget2(query);
	
		
		query = "select ten from nhanvien where pk_seq='" + this.userId + "'";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				if (rs.next())
					this.ten = rs.getString("ten");
				rs.close();
			}
			catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}		
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public void setNguoiduyet(String nguoiduyet) {
		
		this.nguoiduyet = nguoiduyet;
	}


	public String getNguoiduyet() {
		
		return this.nguoiduyet;
	}
	
	public ResultSet getHopDongRs() {
		
		return this.hopdongRs;
	}

	
	public void setHopDongRs(ResultSet hdRs) {
		
		this.hopdongRs = hdRs;
	}

	
	public String getHopdongId() {
		
		return this.hopdongId;
	}

	
	public void setHopdongId(String hdId) {
		
		this.hopdongId = hdId;
	}

	
	public ResultSet getKhachHangRs() {
		return this.khachHangRs;
	}

	
	public void setKhachHangRs(ResultSet rs) {
		this.khachHangRs = rs;
	}

	
	public String getKhachHangId() {
		return this.khachhangId;
	}

	
	public void setKhachHangId(String id) {
		this.khachhangId = id;
	}

	
	public ResultSet getNguoiTaoRs() {
		return this.nguoiTaoRs;
	}

	
	public void setNguoiTaoRs(ResultSet rs) {
		this.nguoiTaoRs = rs;
	}

	
	public String getNguoiTaoId() {
		return this.nguoiTaoId;
	}

	
	public void setNguoiTaoId(String id) {
		this.nguoiTaoId = id;
	}
	ResultSet duyetDonHangRs;

	public ResultSet getDuyetDonHangRs()
	{
		return duyetDonHangRs;
	}

	public void setDuyetDonHangRs(ResultSet duyetDonHangRs)
	{
		this.duyetDonHangRs = duyetDonHangRs;
	}

	
	public void createNguoiDuyet(String quyen) {
		
		try{
		String	query = " select DISTINCT d.TEN as nvTen  " +
			" from PHANQUYEN a inner join DANHMUCQUYEN b on a.DMQ_fk = b.PK_SEQ inner join NHOMQUYEN c on a.DMQ_fk = c.DMQ_fk " +
			" inner join NHANVIEN d on a.Nhanvien_fk = d.PK_SEQ  " +
			" where c.CHOT = 1 and c.UngDung_fk = '"+quyen+"' and d.trangthai = 1 ";
	
				System.out.println("Danh Muc Quyen : "+query);
				ResultSet nvRs = db.get(query);
				if(nvRs != null)
				{
					String nvTen = "";
					try 
					{
						while(nvRs.next())
						{
							nvTen += nvRs.getString("nvTen") + ", ";
						}
						nvRs.close();
						
						if(nvTen.trim().length() > 0)
						{
							nvTen = nvTen.substring(0, nvTen.length() - 2);
							this.nguoiduyet = nvTen;
						}
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
					
				}
		}catch(Exception err){
			 err.printStackTrace();
		}
	}

	
	public String getNguoitaoTen() {
		return this.nguoitaoten;
	}

	
	public void setNguoitaoTen(String ten) {
		
		this.nguoitaoten=ten;
	}
	
	
	public String getduyetDH() {
		
		return this.duyetdh;
	}

	
	public void setduyetDH(String duyetdh) {
		
		this.duyetdh = duyetdh;
	}

	
	public ResultSet getKhoDatRs() {
		
		return this.khodatRs;
	}

	
	public void setKhoDatRs(ResultSet khodatrs) {
		
		this.khodatRs = khodatrs;
	}

	
	public String getKhodatId() {
		
		return this.khodatid;
	}

	
	public void setKhodatId(String khodatid) {
		
		this.khodatid = khodatid;
		
	}

	
	public ResultSet getKenhbhRs() {
	
		return this.kbhRs;
	}

	
	public void setKenhbhRs(ResultSet kenhbhrs) {
	
		this.kbhRs = kenhbhrs;
	}

	
	public String getKenhbhId() {
	
		return this.kbhid;
	}

	
	public void setKenhbhId(String kenhbhid) {
	
		this.kbhid = kenhbhid;
	}

	
	public String getSohoadon() {
		
		return this.sohoadon;
	}

	
	public void setSohoadon(String sohoadon) {
		this.sohoadon = sohoadon;
		
	}

	
	public String getPhieuxuatkhoId() {
		
		return this.phieuxuatkhoId;
	}

	
	public void setPhieuxuatkhoId(String phieuxuatkhoId) {
		
		this.phieuxuatkhoId = phieuxuatkhoId;
	}

	
	public String getngaytaodh() {
		
		return this.ngaytaodh;
	}

	
	public void setngaytaodh(String ngaytaodh) {
		
		this.ngaytaodh = ngaytaodh;
	}
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}