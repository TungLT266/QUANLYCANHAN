package geso.traphaco.distributor.beans.hoadontaichinh.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hoadontaichinh.IBCTongHopSDCongNo;
import geso.traphaco.distributor.db.sql.dbutils;

public class BCTongHopSDCongNo implements IBCTongHopSDCongNo
{ 
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String ptVAT;
	String query;
	String sohoadontu;
	String sohoadonden;
	String khId;
	String nppID;
	String nppTen;
	String msg;
	String activeTab;
	
	ResultSet nppRs;
	ResultSet hoadonRs;
	ResultSet EtcRs;
	ResultSet OtcRs;
	ResultSet kmRs;
	
	ResultSet ttRs;
	String ttId="";
	
	String khTen;
	ResultSet khRs;
	String nppId;
	
	String nvbhId;
	ResultSet nvbhRs;
	String maFAST;
	
	String queryexcel;
	String sohoadon;
	String loaidonhang;
	
	String vungId;
	String khuvucId;
	ResultSet vungRs;
	ResultSet khuvucRs;
	
	String doitacId;
	ResultSet doitacRs;
	
	String type;
	
	dbutils db;
	
	public BCTongHopSDCongNo()
	{
		this.tungay = "";
		this.denngay = "";
		
		/*this.tungay = "";
		this.denngay = "";*/
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "0";
	    this.khTen= "";
	    this.nppId="";
	    this.nppID="";
	    this.sohoadon="";
	    this.ptVAT = "";
	    this.khId="";
	    this.activeTab="0";
	    this.ttId="";
	    
	    this.nvbhId = "";
	    this.maFAST = "";
	    
	    this.sohoadontu = "";
	    this.sohoadonden = "";
		
	    this.nvbhId="";
	    this.ddkdId="";
	    this.nvgnId="";
	    this.vungId="";
		this.khuvucId ="";
		this.type = "0";
		this.doitacId ="";
		this.db = new dbutils();
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
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
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
	
	public String getPhanloai()
  {
  	return phanloai;
  }

	public void setPhanloai(String phanloai)
  {
  	this.phanloai = phanloai;
  }

	public String getLoaiNv()
  {
  	return loaiNv;
  }

	public void setLoaiNv(String loaiNv)
  {
  	this.loaiNv = loaiNv;
  }



	String phanloai="";
	String loaiNv="";

	
	Utility Ult = new Utility();
	private void getNppInfo()
	{		

		String sql = "";
		try
		{

			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					if (rs.getString("phanloai").equals("1")||( this.phanloai.equals("2")   && loaiNv.equals("3")   )  )
					{
						this.nppId = Ult.getIdNhapp(this.userId);
						this.nppTen = Ult.getTenNhaPP();
					}
					rs.close();
				}
			}
		} catch (Exception er)
		{

		}
	
	System.out.println("nppId"+nppId);
	}


	public void init()
	{
		getNppInfo();
		
		String query = "";
       
		if(this.tungay.trim().length() > 0 && this.denngay.trim().length() > 0  )
		{			
			/*String condition = "";
			
			if(this.tungay.trim().length() > 0)
			{
				condition += " and HD.ngayxuatHD >= '" + this.tungay + "' ";
			}
			if(this.denngay.trim().length() > 0)
			{
				condition += " and HD.ngayxuatHD <= '" + this.denngay + "' ";
			}		
			
			System.out.println("---CONDITION::::: "  + condition );*/

			/*query = 
				"SELECT KH.PK_SEQ AS KHID, KH.maFAST, KH.TEN AS TENKH, ISNULL(TIENHD.TONGTIEN,0) AS TONGTIEN,   \n"+
				"       CASE WHEN CAST( ISNULL(KH.PT_CHIETKHAU, 0) AS NUMERIC(18,0)) > 0 THEN ISNULL(KH.PT_CHIETKHAU, 0)  " +
				"            ELSE (case when (select loaiNPP from NHAPHANPHOI where pk_seq ='"+ this.nppId +"'  ) in (4,5) then 5 " +
				"                       when KH.XUATKHAU = '1' or KH.XUATKHAU = '2' then 5 else 3 end) END AS CHIETKHAU \n"+
				"FROM KHACHHANG KH INNER JOIN \n"+
				"		(SELECT HD.KHACHHANG_FK AS KHID, SUM(HD.TONGTIENAVAT) AS TONGTIEN \n"+
				"		 FROM HOADON HD  \n"+
				"		 WHERE  ISNULL(HD.LOAIHOADON,0) = 0 AND HD.TRANGTHAI = 2 " +
				"               AND NPP_FK = '"+ this.nppId +"' "+ condition +" \n"+
				"		 GROUP BY HD.KHACHHANG_FK \n"+
				"		) TIENHD ON KH.PK_SEQ = TIENHD.KHID ";*/
			
			int thangTRUOC = Integer.parseInt(this.tungay);
			int namTRUOC = Integer.parseInt(this.denngay);
			if(thangTRUOC == 1)
			{
				thangTRUOC = 12;
				namTRUOC = namTRUOC - 1;
			}
			else
			{
				thangTRUOC = thangTRUOC - 1;
			}
			
			if( thangTRUOC == 6 && namTRUOC == 2014 )
			{
				query = "select dsTHANGTRUOC.*,  " +
						"	case loaikhachhang when 0 then 0 else isnull(TL.tongCHIETKHAU, 0) end as banBUON, " +
						"	case loaikhachhang when 0 then isnull(TL.tongCHIETKHAU, 0) else 0 end as banLE " +
						"from " +
						"( " +
						"	select khachhang_fk, a.loaikhachhang, maFAST, ten, tongthucthu  " +
						"	from TICHLUYQUY_DAUKY a inner join KHACHHANG b on a.khachhang_fk = b.pk_seq " +
						") " +
						"dsTHANGTRUOC left join   " +
						"(  " +
						"	 select khachhang_fk, sum(thanhtoan) as tongCHIETKHAU   " +
						"	 from DUYETTRAKHUYENMAI_DONHANG  " +
						"	 where tichluyQUY = '0'   " +
						"				and donhang_fk in ( select ddh_fk from HOADON_DDH " +
						"									where hoadon_fk in ( select pk_seq from HOADON where  trangthai not in ( 1 , 3, 5 ) and month(ngayxuatHD) = '" + this.tungay + "' and year(ngayxuatHD) <= '" + this.denngay + "' and isnull(loaihoadon, '0') = '0' " ;
					
					if(this.nppId.length()>0)
					{
						query+=" and npp_fk='"+nppId+"'";  
					}
					if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
					{
						query+= " and npp_fk in " + Ult.quyen_npp(userId)+"";
					}
					
					query+=
								
								"  )  ) " +
						"	 group by khachhang_fk  " +
						" )  " +
						" TL on dsTHANGTRUOC.khachhang_fk = TL.khachhang_fk  ";
			}
			else
			{
				query = "select dsTHANGTRUOC.*,  " +
						"	case loaikhachhang when 0 then 0 else isnull(TL.tongCHIETKHAU, 0) end as banBUON, " +
						"	case loaikhachhang when 0 then isnull(TL.tongCHIETKHAU, 0) else 0 end as banLE " +
						"from " +
						"( " +
						"	select khachhang_fk, loaikhachhang, maFAST, ten, sum( tongtiensauVAT ) as tongtiensauVAT " +
						"	from " +
						"	( " +
						"		select a.khachhang_fk, c.loaikhachhang, d.maFAST, d.ten,   " +
						"			(   select cast( sum( (soluong * giamua -  ( soluong * giamua * cast( chietkhau * 100 / ( soluong * giamua ) as numeric(18, 0) ) / 100 ) ) * ( 1 + thueVAT / 100 ) ) as numeric(18, 0) )           " +
						"				from donhang_sanpham           " +
						"				where donhang_fk = c.pk_seq          " +
						"				group by donhang_fk ) as tongtiensauVAT " +
						"		from HOADON a inner join HOADON_DDH b on a.pk_seq = b.hoadon_fk  " +
						"			inner join DONHANG c on b.ddh_fk = c.pk_seq  " +
						"			inner join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
						"		where   ISNULL(a.loaihoadon, 0) = 0 AND a.trangthai not in ( 1, 3, 5 )  " +
						"				and month(a.ngayxuatHD) = '" + thangTRUOC + "' and year(a.ngayxuatHD) = '" + namTRUOC + "'  " ;
							
						
						if(this.nppId.length()>0)
						{
							query+=" and  a.npp_fk = '" + this.nppId + "' ";  
						}
						if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
						{
							query+= " and  a.npp_fk in " + Ult.quyen_npp(userId)+"";
						}
						
					query+=		
						"	) " +
						"	ds	 " +
						"	group by khachhang_fk, loaikhachhang, maFAST, ten " +
						") " +
						"dsTHANGTRUOC left join   " +
						"(  " +
						"	 select khachhang_fk, sum(thanhtoan) as tongCHIETKHAU   " +
						"	 from DUYETTRAKHUYENMAI_DONHANG  " +
						"	 where tichluyQUY = '0'   " +
						"				and donhang_fk in ( select ddh_fk from HOADON_DDH " +
						"									where hoadon_fk in ( select pk_seq from HOADON where   trangthai not in ( 1 , 3, 5 ) and month(ngayxuatHD) = '" + this.tungay + "' and year(ngayxuatHD) <= '" + this.denngay + "' and isnull(loaihoadon, '0') = '0' " ;
								
						if(this.nppId.length()>0)
						{
							query+=" and  npp_fk = '" + this.nppId + "' ";  
						}
						if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
						{
							query+= " and npp_fk in " + Ult.quyen_npp(userId)+"";
						}
						
						query+=		
						"  )  ) " +
						"	 group by khachhang_fk  " +
						" )  " +
						" TL on dsTHANGTRUOC.khachhang_fk = TL.khachhang_fk  ";
			}
		
			System.out.println("---INIT CHIET KHAU THANG: " + query);
			this.hoadonRs = db.get(query);
			
		}
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public ResultSet getHoadonRs() 
	{
		return this.hoadonRs;
	}

	public void setHoadonRs(ResultSet hdRs) 
	{
		this.hoadonRs = hdRs;
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
	
	public String getSoHoaDon()
	{
		return this.sohoadon;
	}
	public void setSoHoaDon(String sohoadon)
	{
		this.sohoadon =sohoadon;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public String getPtVat() {

		return this.ptVAT;
	}


	public void setPtVat(String ptVat) {
		
		this.ptVAT = ptVat;
	}

	
	public String getNvbhId() {
		
		return this.nvbhId;
	}

	
	public void setNvbhId(String nvbhId) {
		
		this.nvbhId = nvbhId;
	}

	
	public ResultSet getNvbhRs() {
		
		return this.nvbhRs;
	}

	
	public void setNvbhRs(ResultSet nvbhRs) {
		
		this.nvbhRs = nvbhRs;
	}

	
	public String getMaFast() {
		
		return this.maFAST;
	}

	
	public void setMaFast(String maFAST) {
		
		this.maFAST = maFAST;
	}


	public void createRs() 
	{
		Utility ut = new Utility();		
		getNppInfo();
		
		String query="";
		query="select  PK_SEQ, MAFAST + ', ' + TEN AS TEN from KHACHHANG ";
		
		if(this.nppId!=null)		
			query+=" where NPP_FK = '" + this.nppId + "' ";
		System.out.println("KH__: "+query);
		this.khRs=this.db.get(query);
		
		query="select pk_Seq,ten,ma from nhaphanphoi where iskhachhang=0 and trangthai=1 ";
		System.out.println("NPP__: "+query);
		this.nppRs = this.db.get(query);
		
		
		
/*		if(this.nppId!=null){
			this.khRs = db.get("select  PK_SEQ, MAFAST + ', ' + TEN AS TEN from KHACHHANG where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");
		}
		
		else
		{
			//GET NHA PHAN PHOI
			String query="";
			query="select pk_Seq,ten,ma from nhaphanphoi where iskhachhang=0 and trangthai=1 and pk_Seq in (" + ut.quyen_npp(userId)+")";	
			this.nppRs = this.db.get(query);
			
			//GET KHACH HANG
			query ="select distinct  kh.PK_SEQ, kh.Ten " +
									"	from KHACHHANG kh inner join KHACHHANG_TUYENBH kh_tbh on kh.PK_SEQ=kh_tbh.KHACHHANG_FK "+
														" inner join TUYENBANHANG tbh on tbh.PK_SEQ=kh_tbh.TBH_FK" +
														" where 1=1 and kh.TrangThai ='1'";
			
			if(nppID.trim().length()>0)
			{
				query+= " and kh.NPP_FK='"+nppID+"'";
			}
			
			System.out.println("cau kh:" + query);
			this.khRs =  this.db.get( query);
		}*/
		
		query="select PK_SEQ,TEN ,DIACHI from nhanviengiaonhan where 1=1  ";
		if(this.nppId.length()>0)
		{
			query+=" and npp_fk='"+nppId+"' ";
		}
		if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
		{
			query+= " and npp_fk in " + Ult.quyen_npp(userId)+"";
		}
		this.nvgnRs=this.db.get(query);
		
		query="select PK_SEQ,TEN ,DIACHI from daidienkinhdoanh where 1=1  ";
		if(this.nppId.length()>0)
		{
			query+=" and pk_seq in (select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk='"+this.nppId+"' )  ";
		}
		if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
		{
			query+= " and pk_seq in " + Ult.Quyen_Ddkd(userId)+"";
		}
		this.ddkdRs=this.db.get(query);
		
		query="select PK_SEQ,TEN ,DIACHI from nhanviengiaonhan where 1=1  ";
		if(this.nppId.length()>0)
		{
			query+=" and npp_fk='"+nppId+"' ";
		}
		if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
		{
			query+= " and npp_fk in " + Ult.quyen_npp(userId)+"";
		}
		System.out.println("KH: "+query);
		this.nvgnRs=this.db.get(query);
		
		
		query="select PK_SEQ,TEN ,DIACHI from daidienkinhdoanh where 1=1  ";
		if(this.nppId.length()>0)
		{
			query+=" and pk_seq in (select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk='"+this.nppId+"' )  ";
		}
		if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
		{
			query+= " and pk_seq in " + Ult.Quyen_Ddkd(userId)+"";
		}
		System.out.println("KH: "+query);
		this.ddkdRs=this.db.get(query);
		
		
		this.vungRs = db.get("select pk_seq,ten,diengiai from vung ");
		
		if (this.vungId.length() > 0)
		{			
			this.khuvucRs = db.get("select pk_seq,ten from khuvuc where vung_fk ='" + this.vungId + "'");
		} else
		{
			String query_khuvuc=" select PK_SEQ, TEN from KHUVUC "
					+ "	where PK_SEQ in (select KHUVUC_Fk from NHAPHANPHOI "
					+ "	where pk_seq in "+ Ult.quyen_npp(userId)+")"; 			
			this.khuvucRs= db.get(query_khuvuc);		
		}
		
		query="select pk_Seq,ten,ma from nhaphanphoi where iskhachhang=0 and trangthai=1 ";

		if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
		{
				query+= " and pk_Seq in " + Ult.quyen_npp(userId)+"";
		}
		if (this.khuvucId.length() > 0)
		{
			query = query + " and khuvuc_fk ='" + this.khuvucId + "'";
		}
		if (this.vungId.length() > 0)
		{
			query = query + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		}
		this.nppRs = this.db.get(query);
		
		query="select  kh.PK_SEQ, kh.MAFAST + ', ' + kh.TEN AS TEN from KHACHHANG kh inner join NHAPHANPHOI npp on kh.NPP_FK=npp.PK_SEQ where 1=1";
		
		if(this.nppId.trim().length()>0)
			query+=	" and kh.NPP_FK = '" + this.nppId + "' ";
		if (this.khuvucId.length() > 0)
		{
			query = query + " and npp.khuvuc_fk ='" + this.khuvucId + "'";
		}
		if (this.vungId.length() > 0)
		{
			query = query + " and npp.khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		}
		System.out.println("KH "+query);
		this.khRs = db.get(query);
		
		query="select PK_SEQ,TEN from tinhthanh   where 1=1 ";
		if(vungId.length()>0)
			query+=" and vung_fk='"+vungId+"'";;
		
		this.ttRs= this.db.get(query);
	
		query = 
			"	select PK_SEQ,  isnull(maFAST, '') + ', ' + TEN as TEN  " +
			"	from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk = '" + this.nppId + "' ";
		
		this.doitacRs = this.db.get(query);
	}

	
	
	public String getFormatDate(String date) 
	{
		String[] arr = date.split("-");
		
		return arr[2] + "-" + arr[1] + "-" + arr[0];
	}


	public String getActiveTab() {
	
		return this.activeTab;
	}

	
	public void setActiveTab(String active) {
		this.activeTab= active ;
		
	}

	
	public void setQuery(String searchQuery) {
	 this.query = searchQuery;
		
	}

	public void searchQuery_ETC(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.EtcRs= db.get(sql);
		System.out.println("[Danh sach ETC] :" + sql);
	}

	public void searchQuery_OTC(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.OtcRs= db.get(sql);
		System.out.println("[Danh sach OTC] :" + sql);
		
	}

	public void searchQuery_KM(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.kmRs= db.get(sql);
		System.out.println("[Danh sach KM] :" + sql);
		
	}

	public ResultSet getETCRs() {
	
		return this.EtcRs;
	}


	public void setETCRS(ResultSet ETCRs) {
		this.EtcRs= ETCRs;
		
	}


	public ResultSet getOTCRs() {
		
		return this.OtcRs;
	}

	public void setOTCRS(ResultSet OTCRs) {
		this.OtcRs = OTCRs;
	}


	public ResultSet getKMRs() {

		return this.kmRs;
	}


	public void setKMRS(ResultSet KMRs) {
		this.kmRs= KMRs;
		
	}

	
	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String KhId) {
		
		this.khId=KhId;
	}


	String nvgnId,ddkdId;
	ResultSet nvgnRs,ddkdRs;

	public String getNvgnId()
  {
  	return nvgnId;
  }

	public void setNvgnId(String nvgnId)
  {
  	this.nvgnId = nvgnId;
  }

	public String getDdkdId()
  {
  	return ddkdId;
  }

	public void setDdkdId(String ddkdId)
  {
  	this.ddkdId = ddkdId;
  }

	public ResultSet getNvgnRs()
  {
  	return nvgnRs;
  }

	public void setNvgnRs(ResultSet nvgnRs)
  {
  	this.nvgnRs = nvgnRs;
  }

	public ResultSet getDdkdRs()
  {
  	return ddkdRs;
  }

	public void setDdkdRs(ResultSet ddkdRs)
  {
  	this.ddkdRs = ddkdRs;
  }

	
	public String getnppId() {
		
		return this.nppId;
	}

	
	public void setnppId(String nppID) {
		
		this.nppId = nppID;
	}

	
	public void setvungId(String vungId) {
		
		this.vungId= vungId;
	}

	
	public String getvungId() {
		
		return this.vungId;
	}

	
	public void setvung(ResultSet vung) {
		
		this.vungRs= vung;
	}

	
	public ResultSet getvung() {
		
		return this.vungRs;
	}

	
	public void setkhuvucId(String khuvucId) {
		
		this.khuvucId= khuvucId;
	}

	
	public String getkhuvucId() {
		
		return this.khuvucId;
	}

	
	public void setkhuvuc(ResultSet khuvuc) {
		
		this.khuvucRs=khuvuc;
	}

	
	public ResultSet getkhuvuc() {
		
		return this.khuvucRs;
	}

	
	public ResultSet getTtRs() {
		
		return this.ttRs;
	}

	
	public void setTtRs(ResultSet ttRs) {
		
		this.ttRs=ttRs;
	}

	
	public String getTtId() {
		
		return this.ttId;
	}

	
	public void setTtId(String ttId) {
		
		this.ttId=ttId;
	}

	
	public ResultSet getDoiTacRs() {
		
		return this.doitacRs;
	}

	
	public void setDoiTacRs(ResultSet DoiTacRs) {
		
		this.doitacRs = DoiTacRs;
	}

	
	public String getDoiTacId() {
		
		return this.doitacId;
	}

	
	public void setDoiTacId(String doitacId) {
		
		this.doitacId = doitacId;
	}

	
	public void settype(String type) {
		
		this.type = type;		
	}

	
	public String gettype() {
		
		return this.type;
	}

	
	public String getBCTheoKH() {
		
			String query = "";		
	
	    	dbutils db = new dbutils();
	    	
	    	String condition="";
	    	String condition1="";
	    	String condition2="";
	    	if(khId.trim().length()>0)
	    		condition =" and kh.PK_SEQ='"+khId+"'";
	    	if(nppId.trim().length()>0)
	    		condition1=" and kh.NPP_FK='"+nppId+"'";	    	
	    	
	    	String conditionDetails_OTC ="";
	    	String conditionDetails_ETC ="";
	    	String condition_DuNoDauKy="";
	    	
	    	String conditionDetails_OTC2 ="";
	    	String conditionDetails_ETC2 ="";
	    	String condition_DuNoDauKy2="";
	    	
	    	String Search_ddkd="";
	    	String Search_kh="";
	    	String Search_npp="";
	    	String Search_ddkd_DNDK="";
	    	String Search_vung_mien="";
	    	if(this.vungId.trim().length()>0)
	    		Search_vung_mien+="  and v.PK_SEQ='"+this.vungId+"' \n";
	    	if(this.khuvucId.trim().length()>0)
	    		Search_vung_mien+="  and kv.PK_SEQ='"+this.khuvucId+"' \n";
	    	 if(this.ddkdId.trim().length()>0)
			   {
	    		 Search_ddkd=
					"		  and hd.KHACHHANG_FK in \n"+
					"		  						( \n"+
					"			 						SELECT  c.KHACHHANG_FK \n"+ 
					"									FROM 	DAIDIENKINHDOANH a INNER JOIN TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
					"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
					"									WHERE a.PK_SEQ ='"+this.ddkdId+"'  \n"+
					"		  						) \n";
	    		 Search_ddkd_DNDK= " and dnkh.ddkd_fk='"+this.ddkdId+"' ";
			   }
	    	 
	    	 if(this.khId.trim().length() >0){
	    		 Search_kh += " 						\n	and kh.PK_SEQ in ("+this.khId+") \n";
			 }
	    	 
				
	    	 if(this.nppId.trim().length()>0)
	    	 {
					Search_npp+= " 						\n  and kh.NPP_FK ='"+this.nppId+"' \n";
			 }
	    	/*if(ddkdId.length()>0)
	    	{
	    		conditionDetails_OTC+=
		    	" and hd.PK_SEQ in "+ 
				  " (  "+
					"   select HOADON_FK from HOADON_DDH where DDH_FK in (select pk_seq from DONHANG where ddkd_fk='"+ddkdId+"' )  "+
					"	) ";
	    		
	    		
	    		conditionDetails_ETC+=
	    		"	and hd.PK_SEQ in" +
	    		" ( " +
	    		"		select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK in (select pk_seq from ERP_DONDATHANGNPP where DDKD_FK='"+ddkdId+"' )" +
	    		"  ) " +  
	    		" ";
	    		
	    		condition_DuNoDauKy+= " and dnkh.ddkd_fk='"+ddkdId+"' ";
	    		
	    		conditionDetails_OTC2+=		    	" and a.PK_SEQ in "+ 
				  " (  "+
					"   select HOADON_FK from HOADON_DDH where DDH_FK in (select pk_seq from DONHANG where ddkd_fk='"+ddkdId+"' )  "+
					"	) ";
	 	    }*/
	    	
	    	if(this.nvgnId.length()>0)
	    	{
	    		conditionDetails_ETC=" and hd.khachhang_Fk   in  (select KHACHHANG_FK from NVGN_KH where nvgn_fk='"+this.nvgnId+"' ) ";
	    	
	    		condition_DuNoDauKy+= " and dnkh.khachhang_Fk in (select KHACHHANG_FK from NVGN_KH where nvgn_fk='"+this.nvgnId+"' )  ";
	    		
	    		
	    		conditionDetails_OTC+=
	    			" and hd.khachhang_Fk   in  (select KHACHHANG_FK from NVGN_KH where nvgn_fk='"+this.nvgnId+"' ) ";
	    		
	    	}	    	
	      	    	
	    	query = "SELECT 	lkh.ten LoaiKH,kh.DIACHI,kh.maFAST, kh.maFAST, kh.TEN,   \n" +
	    	"		isnull(dndk.tongtienAVAT, 0) as dunodauky,  isnull(dcdk.SOTIENTT, 0) as  ducodauky,  \n" +
	    	"		isnull(psntk.tongtienAVAT, 0) as phatsinhno, isnull(psctk.sotienTT, 0)  as phatsinhco, \n" +
	    	"		( \n" +
	    	"			 select top(1)  isnull(bb.TEN,'') NVGN from  NVGN_KH aa inner join NHANVIENGIAONHAN bb on bb.PK_SEQ = aa.NVGN_FK  \n" +
	    	"			 where aa.KHACHHANG_FK = kh.PK_SEQ  " + ( this.nvbhId.trim().length() > 0 ? " and bb.pk_seq = '" + this.nvbhId.trim() + "' " : "" ) +
	    	"		) as NVGN, \n" +
	    	"		( \n" +
	    	"			SELECT  top(1) isnull(a.TEN,'') DDKD FROM 	DAIDIENKINHDOANH a INNER JOIN TUYENBANHANG b on b.DDKD_FK = a.PK_SEQ    \n" +
	    	"					inner join KHACHHANG_TUYENBH c on c.TBH_FK = b.PK_SEQ  " + ( this.ddkdId.trim().length() > 0 ? " and a.pk_seq = '" + this.ddkdId.trim() + "' " : "" ) +
	    	"			WHERE c.KHACHHANG_FK = kh.PK_SEQ \n" +
	    	"		) as DDKD \n , isnull(cantrutk.tongtienTT,0) ckthang \n" +
	    	"FROM KHACHHANG kh left join LOAIKHACHHANG lkh on kh.XuatKhau = lkh.pk_seq LEFT JOIN   \n" +
	    	"(   \n" +
	    	"	SELECT psn_dk.PK_SEQ KHACHHANG_FK, SUM(ISNULL(psn_dk.tongtienAVAT,0)) as tongtienAVAT   \n" +
	    	"	FROM   \n" +
	    	"	(	  \n" +
	    	"		SELECT psn.PK_SEQ, sum(tongtienAVAT) as tongtienAVAT   \n" +
	    	"		FROM   \n" +
	    	"		(   \n" +
	    	"			SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT   \n" +
	    	"			FROM HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   \n" +
	    	"			WHERE 1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD < '" + this.tungay + "' ";
	
	    	if(this.nppId.trim().length()>0) //DÙNG CHO PAGE TRUNG TÂM NẾU CHỌN NHÀ PHÂN PHỐI
    		query += "and kh.NPP_FK = '" + this.nppId + "'  \n";
    		query+=			    			
	    	"           GROUP BY hd.KHACHHANG_FK   \n" +
	    	"		UNION ALL \n" +
	    	"			SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT   \n" +
	    	"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK    \n" +
	    	"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '" + this.tungay + "' " ;
    		
	    	if(this.nppId.trim().length()>0) //DÙNG CHO PAGE TRUNG TÂM NẾU CHỌN NHÀ PHÂN PHỐI
	    		{
	    			query += " and btcn.NPP_FK = '" + this.nppId + "'                                 ";
	    		}
	    	query+=
	    	"			GROUP BY btcn_hd.KHACHHANG_FK   \n" +
	    	"		UNION ALL	  \n" +
	    	"			SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat   \n" +
	    	"			FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
	    	"					inner join (  \n" +
	    	"									SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat   \n" +
	    	"									FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
	    	"									WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
	    	"									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
	    	"								)   \n" +
	    	"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
	    	"					 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n" +
	    	"			WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD <'" + this.tungay + "' " ;
	    	if(this.nppId.trim().length()>0){ // DÙNG CHO PAGE TRUNG TÂM NẾU CHỌN NHÀ PHÂN PHỐI
		    	query += "and kh.NPP_FK ='" + this.nppId + "'   \n";
		    }
	    	
	    	query+=
	    	"		UNION ALL	  \n" +
	    	"			SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat   \n" +
	    	"			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
	    	"					INNER JOIN (   " +
	    	"									SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat   \n" +
	    	"									FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
	    	"									WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
	    	"									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
	    	"								)   \n" +
	    	"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
	    	"					 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n" +
	    	"			WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD <'" + this.tungay + "' ";
	    	if(this.nppId.trim().length()>0) // DÙNG CHO PAGE TRUNG TÂM NẾU CHỌN NHÀ PHÂN PHỐI
		    	query += "and kh.NPP_FK ='" + this.nppId + "'   \n";
	    	query+=
	    	"		UNION ALL   \n" +
	    	"			SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat    \n" +
	    	"			FROM   \n" +
	    	"			(   \n" +
	    	"				SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk,   \n" +
	    	"						sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,      \n" +
	    	"						sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT,    \n" +
	    	"						sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   \n" +
	    	"						round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,             " +
	    	"						sum(isnull(thuexuat,0)) as BVAT_CK       \n" +
	    	"				FROM (     \n" +
	    	"						SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,   " +
	    	"							(     \n" +
	    	"								SELECT top(1) bb.DDKD_FK     \n" +
	    	"								FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK     \n" +
	    	"								WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n" +
	    	"							) as ddkd_fk , 													case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,    \n" +
	    	"								case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n" +
	    	"						FROM  ERP_HOADONNPP a     \n" +
	    	"							  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk    \n" +
	    	"							  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n" +
	    	"							  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n" +
	    	"						WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD <'" + this.tungay + "'   \n" +
	    	"					)ETC									  " +
	    	"				GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk								  \n" +
	    	"			)   \n" +
	    	"			hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK   \n" +
	    	"					  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK   \n" +
	    	"			WHERE 1=1 ";
	    	if(this.nppId.trim().length()>0) // DÙNG CHO PAGE TRUNG TÂM NẾU CHỌN NHÀ PHÂN PHỐI
		    	query += "and hd.NPP_FK ='" + this.nppId + "'   \n";
	    	query+=
	    	"            GROUP BY hd.khachhang_fk   \n" +
	    	"		)   \n" +
	    	"		psn   \n" +
	    	"		GROUP BY psn.PK_SEQ   \n" +
	    	"	UNION ALL   \n" +
	    	"		SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT   \n" +
	    	"		FROM   	DUNO_KHACHHANG dnkh   \n" +
	    	"				LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   \n" +
	    	"				LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   \n" +
	    	"				LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK   \n" +
	    	"	    WHERE 	1=1 and dnkh.NgayDuNo < '" + this.tungay + "' and dnkh.SONO >= 0  ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	" and  dnkh.NPP_FK='" + this.nppId + "'			";
	    	query+=	
	    	"		GROUP BY dnkh.KHACHHANG_FK   \n" +
	    	" \n"+
	    	"	UNION ALL \n"+
	    	"   SELECT a.khachhang_fk PK_SEQ, sum(round(isnull(a.avat,0),0)) as tongtienAVAT   \n"+
	    	"   FROM ERP_HoaDonPheLieu a \n"+
	    	"   WHERE 1 = 1 AND trangthai = 1 AND a.ngayhoadon < '"+this.tungay+"' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	" and a.npp_fk = '"+this.nppId+"' \n";
	    	query+=
	    	"   GROUP BY a.khachhang_fk \n"+ 
	    	"	)   \n" +
	    	"	psn_dk   \n" +
	    	"	GROUP BY psn_dk.PK_SEQ   \n" +
	    	")  \n" +
	    	"dndk on kh.PK_SEQ = dndk.KHACHHANG_FK LEFT JOIN   \n" +
	    	"(   \n" +
	    	"	SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT   \n" +
	    	"	FROM   \n" +
	    	"	(   \n" +
	    	"		SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   \n" +
	    	"		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
	    	"				INNER JOIN (   " +
	    	"  								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   " +
	    	"  								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   " +
	    	"  								WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '" + this.tungay + "' and tthd.LOAIHD = 0  \n" +
	    	"  								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
	    	" 							) 	  \n" +
	    	" 							tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   \n" +
	    	"		WHERE 	1=1 ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and kh.NPP_FK = '" + this.nppId + "'   \n";			    	
	    	query+=
	    	"		GROUP BY hd.KHACHHANG_FK   \n" +		    	
	    	
	    	"	UNION ALL   \n" +
	    	"		SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT   \n" +
	    	"		FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK   \n" +
	    	"		WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '" + this.tungay + "'   \n";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"		  and btcn.NPP_FK='" + this.nppId + "' \n";
	        query+=
	    	"  		GROUP BY btcn_hd.KHACHHANG_FK   \n" +
	    	
	    	"	UNION ALL  \n" +
	    	"		SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   \n" +
	    	"		FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ   \n" +
	    	"	 				INNER JOIN   \n" +
	    	"	 				(   \n" +
	    	"						SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   \n" +
	    	"						FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
	    	"						WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '" + this.tungay + "' and tthd.LOAIHD = 2 ";
	    	if(this.nppId.trim().length()>0)
	    						query+=	"			and tt.NPP_FK = '" + nppId + "'						  \n";
	    	query+=			    						
	    	"						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
	    	"		 			)  \n" +
	    	"		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK   \n" +
	    	"		 WHERE 1=1 " ;
	    	if(this.nppId.trim().length()>0)
	    		query+=	" and kh.NPP_FK = '" + this.nppId + "'   \n" ;
	    	query+=
	    	"		 GROUP BY dnkh.KHACHHANG_FK   \n" +
	    	"	UNION ALL	 \n" +
	    	"		SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT 		  \n" +
	    	"		FROM   	Erp_HangTraLaiNpp  tttl				 \n" +
	    	"		WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA < '" + this.tungay + "' and KHACHHANG_FK is not null ";
	    	if(this.nppId.trim().length()>0)
	    		query+="and tttl.NPP_FK = '" + this.nppId + "' 	  \n";
	    	query+=		
	    	"		GROUP BY tttl.KHACHHANG_FK   \n" +
	    	"	UNION ALL	  \n" +
	    	"		SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT   \n" +
	    	"		FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ  \n" +
	    	"			   INNER JOIN (   \n" +
	    	" 								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT   \n" +
	    	" 								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
	    	" 								WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '" + this.tungay + "'  and tthd.LOAIHD = 0 \n" +
	    	" 								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
	    	"  							)   \n" +
	    	"  							tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK   \n" +
	    	"		WHERE 1=1 ";
	    	if(this.nppId.trim().length()>0)
	    		query+="and kh.NPP_FK = '" + nppId + "'   \n";
	    	query+=
	    	"		GROUP BY  hd.KHACHHANG_FK   \n" +
	    	
	    	"	UNION ALL	  \n" +
	    	"		SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT   \n" +
	    	"		FROM   ERP_HOADONPHELIEU hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ  \n" +
	    	"			   INNER JOIN (   \n" +
	    	" 								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT   \n" +
	    	" 								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
	    	" 								WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '" + this.tungay + "'  and tthd.LOAIHD = 1 \n" +
	    	" 								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
	    	"  							)   \n" +
	    	"  							tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   \n" +
	    	"		WHERE 1=1 ";
	    	if(this.nppId.trim().length()>0)
	    		query+="and kh.NPP_FK = '" + nppId + "'   \n";
	    	query+=
	    	"		GROUP BY  hd.KHACHHANG_FK   \n" +
	    	
	    	"	UNION ALL   \n" +
	    	"		SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA   \n" +
	    	"		FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK   \n" +
	    	"		   			INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0)   \n" +
	    	"		WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '" + this.tungay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and xnkh.NPP_FK = '" + this.nppId + "'		  \n";
	    	query+=			    			
	    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +
	    	"	UNION ALL   \n" +
	    	"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
	    	"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  \n" +
	    	"				INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0)   \n" +
	    	"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '" + this.tungay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and xnkh.NPP_FK = '" + this.nppId + "'		  \n";
	    	query+=
	    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +
	    	"	UNION ALL   \n" +
	    	"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
	    	"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  \n" +
	    	"				INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2)   \n" +
	    	"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '" + this.tungay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and xnkh.NPP_FK = '" + this.nppId + "'		  \n";
	    	query+=			    			
	    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +
	    	
	    	"	UNION ALL   \n" +
	    	"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
	    	"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  \n" +
	    	"				INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1)   \n" +
	    	"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '" + this.tungay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and xnkh.NPP_FK = '" + this.nppId + "'		  \n";
	    	
	    	query+=			    			
	    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +
	    	
	    	"	UNION ALL   \n" +
	    	" 		SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT   \n" +
	    	"  		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
	    	"   				INNER JOIN (   \n" +
	    	"	  						SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   \n" +
	    	"	  						FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
	    	"	  						WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
	    	"	  						GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
	    	"	 					   )   \n" +
	    	"	 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
	    	" 		WHERE 	1=1 and hd.NGAYXUATHD < '" + this.tungay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and kh.NPP_FK = '" + this.nppId + "'   \n";
	    	query+=				    			
	    	" 		GROUP BY hd.KHACHHANG_FK   \n" +
	    	"  \n" +
	    	"	UNION ALL   \n" +
	    	"		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT   \n" +
	    	"  		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
	    	"   				INNER JOIN (   \n" +
	    	"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   \n" +
	    	"	  							FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
	    	"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
	    	"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
	    	"	 						)   \n" +
	    	"					 		bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
	    	" 		WHERE 	1=1 and hd.NGAYXUATHD < '" + this.tungay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and kh.NPP_FK = '" + this.nppId + "'   \n";
	    	query+=				    			
	    	" 		GROUP BY hd.KHACHHANG_FK   \n" +
	    	" \n" +
	    	"	UNION ALL   \n" +
	    	"		SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT   \n" +
	    	"		FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ   \n" +
	    	"		WHERE 1 =1 and tt.NGAYCHUNGTU < '" + this.tungay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and ttna.NPP_FK = '" + this.nppId + "'   	  \n";
	    	query+=				    			
	    	"		GROUP BY ttna.KHACHHANG_FK   \n" +
	    	"	UNION ALL   \n" +
	    	"		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT  \n" +
	    	"		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ   \n" +
	    	"				INNER JOIN (   \n" +
	    	"								SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU   \n" +
	    	"								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK   \n" +
	    	"								WHERE  ct.TRANGTHAI = 1   \n" +
	    	"								GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK   \n" +
	    	"							)   \n" +
	    	"							ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK   \n" +
	    	"		WHERE 	1=1 and hd.NGAYXUATHD <'" + this.tungay + "'  " ;
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and hd.NPP_FK='" + this.nppId + "'	  \n";
	    	query+=					    			
	    	"		GROUP BY hd.KHACHHANG_FK   \n" +			    	
			"   UNION ALL \n"+ 
			"		SELECT 	dnkh.KHACHHANG_FK , sum(round(isnull(dnkh.SONO*(-1),0),0)) as tongtienTT   \n"+ 
			"		FROM   	DUNO_KHACHHANG dnkh   \n"+ 
			"				LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   \n"+ 
			"				LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   \n"+ 
			"				LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK   \n"+ 
		    "		WHERE 	1=1 and dnkh.NgayDuNo < '" + this.tungay + "' and dnkh.SONO < 0 ";
		    if(this.nppId.trim().length()>0)
	    		query+=	" and  dnkh.NPP_FK = '"+ this.nppId +"' \n";
	    	query+=					    					
		    "		GROUP BY dnkh.KHACHHANG_FK  \n"+ 			    	
	    	"	)   " +
	    	"	psc group by psc.KHACHHANG_FK   \n" +
	    	")   \n" +
	    	"dcdk on kh.PK_SEQ = dcdk.KHACHHANG_FK left join  \n" +
	    	"(   \n" +
	    	"	SELECT psn.PK_SEQ as khachhang_fk, isnull( sum(tongtienAVAT), 0 ) as tongtienAVAT   \n" +
	    	"	FROM   \n" +
	    	"	(   \n" +
	    	"		SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT   \n" +
	    	"		FROM HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   \n" +
	    	"		WHERE 1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '" + this.tungay + "' and hd.NGAYXUATHD <= '" + this.denngay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and kh.NPP_FK ='" + this.nppId + "'   \n";
	    	query+=
	    	"        GROUP BY hd.KHACHHANG_FK   \n" +
	    	"	UNION ALL  \n" +
	    	"		SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT   \n" +
	    	"		FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK    \n" +
	    	"		WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '" + this.tungay + "' and btcn.NGAYCHUNGTU <= '" + this.denngay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and btcn.NPP_FK='" + this.nppId + "'                                 \n";
	    	query+=		
	    	"		GROUP BY btcn_hd.KHACHHANG_FK   \n" +
	    	"	UNION ALL	  \n" +
	    	"		SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat   \n" +
	    	"		FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
	    	"				inner join (   " +
	    	"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat   \n" +
	    	"								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
	    	"								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
	    	"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
	    	"							)   \n" +
	    	"							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
	    	"				 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n" +
	    	"		WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD >= '" + this.tungay + "' and hd.NGAYXUATHD <= '" + this.denngay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and kh.NPP_FK ='" + this.nppId + "'   \n";
	    	query+=				    			
	    	"	UNION ALL	  \n" +
	    	"		SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat   \n" +
	    	"		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
	    	"				INNER JOIN (   \n" +
	    	"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat  \n" +
	    	"								FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
	    	"								WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   \n" +
	    	"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
	    	"							)   \n" +
	    	"							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
	    	"				 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n" +
	    	"		WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '" + this.tungay + "' and hd.NGAYXUATHD <= '" + this.denngay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and kh.NPP_FK ='" + this.nppId + "'   \n";
	    	query+=				    			
	    	"	UNION ALL   \n" +
	    	"		SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat    \n" +
	    	"		FROM   \n" +
	    	"		(   \n" +
	    	"			SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk,   \n" +
	    	"					sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       \n" +
	    	"					sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT,    \n" +
	    	"					sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,  \n" +
	    	"					round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,            \n" +
	    	"					sum(isnull(thuexuat,0)) as BVAT_CK       \n" +
	    	"			FROM (     \n" +
	    	"					SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,   \n" +
	    	"						(     \n" +
	    	"							SELECT top(1) bb.DDKD_FK     \n" +
	    	"							FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK     \n" +
	    	"							WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n" +
	    	"						) as ddkd_fk , case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,    \n" +
	    	"							case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n" +
	    	"					FROM  ERP_HOADONNPP a     \n" +
	    	"						  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk    \n" +
	    	"						  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n" +
	    	"						  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n" +
	    	"					WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD >= '" + this.tungay + "' and a.NGAYXUATHD <= '" + this.denngay + "'   \n" +
	    	"				)ETC									  " +
	    	"			GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk								  \n" +
	    	"		)   \n" +
	    	"		hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK   \n" +
	    	"				  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK   \n" +
	    	"		WHERE 1=1 ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and hd.NPP_FK ='" + this.nppId + "'   \n";
	    	query+=				    	
	    	"        GROUP BY hd.khachhang_fk   \n" +
	    	" \n"+
	    	"	UNION ALL \n"+
	    	
	    	"   SELECT a.khachhang_fk PK_SEQ, sum(round(isnull(a.avat,0),0)) as tongtienAVAT   \n"+
	    	"   FROM ERP_HoaDonPheLieu a \n"+
	    	"   WHERE 1 = 1 AND trangthai = 1 AND a.ngayhoadon >= '"+this.tungay+"' and a.ngayhoadon <= '"+this.denngay+"' \n ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	" and a.npp_fk = '"+this.nppId+"' \n";
	    	query+=
	    	"   GROUP BY a.khachhang_fk \n"+ 
	    	"	)   \n" +
	    	"	psn   \n" +
	    	"	GROUP BY psn.PK_SEQ   \n" +
	    	")  \n" +
	    	"psntk on kh.PK_SEQ = psntk.KHACHHANG_FK LEFT JOIN   \n" +
	    	"(   \n" +
	    	"	SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT  \n" +
	    	"	FROM   \n" +
	    	"	(   \n" +
	    	"		SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   \n" +
	    	"		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   \n" +
	    	"				INNER JOIN (   \n" +
	    	"  								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   \n" +
	    	"  								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
	    	"  								WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '" + this.tungay + "'  and tt.NGAYCHUNGTU <= '" + this.denngay + "'  and tthd.LOAIHD = 0 \n" +
	    	"  								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
	    	" 							) 	  \n" +
	    	" 							tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   \n"+
	    	"		WHERE 	1=1 ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"		and kh.NPP_FK = '" + this.nppId + "'   \n";
	    	query+=				    	
	    	"		GROUP BY hd.KHACHHANG_FK   \n" +
	    	"	UNION ALL   \n" +
	    	"		SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT   \n" +
	    	"		FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK   \n" +
	    	"		WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '" + this.tungay + "' and btcn.NGAYCHUNGTU <= '" + this.denngay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and btcn.NPP_FK='" + this.nppId + "'   \n";
	    	query+=				    			
	    	"		GROUP BY btcn_hd.KHACHHANG_FK   \n" +
	    	"	UNION ALL  \n" +
	    	"		SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   \n" +
	    	"		FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ   \n" +
	    	"	 				INNER JOIN   \n" +
	    	"	 				(   \n" +
	    	"						SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   \n" +
	    	"						FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
	    	"						WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '" + this.tungay + "' and tt.NGAYCHUNGTU <= '" + this.denngay + "' and tthd.LOAIHD = 2 ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and tt.NPP_FK = '" + this.nppId + "'   \n";
	    	query+=
	    	"						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
	    	"		 			) \n" +
	    	"		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK   \n" +
	    	"		 WHERE 1=1 ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and kh.NPP_FK ='" + this.nppId + "'   \n";
	    	query+=				    	
	    	"		 GROUP BY dnkh.KHACHHANG_FK   \n" +
	    	"	UNION ALL	  \n" +
	    	"		SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT 		 \n" +
	    	"		FROM   	Erp_HangTraLaiNpp  tttl				 \n" +
	    	"		WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA >= '" + this.tungay + "' and tttl.NGAYTRA <= '" + this.denngay + "' and KHACHHANG_FK is not null ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and tttl.NPP_FK = '" + this.nppId + "' 	  \n";
	    	query+=				    			
	    	"		GROUP BY tttl.KHACHHANG_FK   \n" +
	    	"	UNION ALL	 \n" +
	    	"		SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT   \n" +
	    	"		FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   \n" +
	    	"			   INNER JOIN (   \n" +
	    	" 								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT   \n" +
	    	" 								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
	    	" 								WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '" + this.tungay + "' and tt.NGAYCHUNGTU <= '" + this.denngay + "' and tthd.LOAIHD = 0 \n" +
	    	" 								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
	    	"  							)   \n" +
	    	"  							tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK  \n" +
	    	"		WHERE 1=1 ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and kh.NPP_FK = '" + this.nppId + "'   \n";
	    	query+=	
	    	"		GROUP BY  hd.KHACHHANG_FK   \n" +
	    	
	    	"	UNION ALL	 \n" +
	    	"		SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT   \n" +
	    	"		FROM   ERP_HOADONPHELIEU hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   \n" +
	    	"			   INNER JOIN (   \n" +
	    	" 								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT   \n" +
	    	" 								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
	    	" 								WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '" + this.tungay + "' and tt.NGAYCHUNGTU <= '" + this.denngay + "' and tthd.LOAIHD = 1 \n" +
	    	" 								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
	    	"  							)   \n" +
	    	"  							tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK  \n" +
	    	"		WHERE 1=1 ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and kh.NPP_FK = '" + this.nppId + "'   \n";
	    	query+=	
	    	"		GROUP BY  hd.KHACHHANG_FK   \n" +
	    	
	    	"	UNION ALL   \n" +
	    	"		SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA   \n" +
	    	"		FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK   \n" +
	    	"		   			INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 )   \n" +
	    	"		WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '" + this.tungay + "' and XNKH.NGAYCHUNGTU <= '" + this.denngay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+=	"and xnkh.NPP_FK='" + this.nppId + "'		  \n";
	    	query+=				    			
	    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   " +
	    	"	UNION ALL   " +
	    	"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
	    	"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK   \n" +
	    	"				INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0)   \n" +
	    	"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '" + this.tungay + "' and XNKH.NGAYCHUNGTU <= '" + this.denngay + "' ";
	    	if(nppId.trim().length()>0)
	    		query+="and xnkh.NPP_FK='" + this.nppId + "'		 \n";
	    	query+=				    			
	    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +
	    	"   UNION ALL  " +
	    	"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
	    	"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  \n" +
	    	"				INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2)   \n" +
	    	"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '" + this.tungay + "' and XNKH.NGAYCHUNGTU <= '" + this.denngay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+="and xnkh.NPP_FK='" + this.nppId + "'		 \n";
	    	query+=				    			
	    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +
	    	
	    	"   UNION ALL  " +
	    	"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
	    	"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  \n" +
	    	"				INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1)   \n" +
	    	"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '" + this.tungay + "' and XNKH.NGAYCHUNGTU <= '" + this.denngay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+="and xnkh.NPP_FK='" + this.nppId + "'		 \n";
	    	query+=				    			
	    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +		    	
	    	
	    	"	UNION ALL   \n" +
	    	" 		SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT   \n" +
	    	"  		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
	    	"   				INNER JOIN (   \n" +
	    	"	  						SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   \n" +
	    	"	  						FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
	    	"	  						WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
	    	"	  						GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
	    	"	 					   )   \n" +
	    	"	 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
	    	" 		WHERE 	1=1 and hd.NGAYXUATHD >= '" + this.tungay + "' and hd.NGAYXUATHD <= '" + this.denngay+ "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+="and kh.NPP_FK = '" + this.nppId + "'   ";
	    	query+=				    			
	    	" 		GROUP BY hd.KHACHHANG_FK   \n" +
	    	"  \n" +
	    	"	UNION ALL   \n" +
	    	"		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT   \n" +
	    	"  		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
	    	"   				INNER JOIN (   \n" +
	    	"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   \n" +
	    	"	  							FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
	    	"	  							WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
	    	"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
	    	"	 						)   \n" +
	    	"					 		bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
	    	" 		WHERE 	1=1 and hd.NGAYXUATHD >= '" + this.tungay + "' and hd.NGAYXUATHD <= '" + this.denngay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+="and kh.NPP_FK = '" + this.nppId + "'   ";
	    	query+=				    			
	    	" 		GROUP BY hd.KHACHHANG_FK   \n" +
	    	"  \n" +
	    	"	UNION ALL   \n" +
	    	"		SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT   \n" +
	    	"		FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ   " +
	    	"		WHERE 1 =1 and tt.NGAYCHUNGTU >= '" + this.tungay + "' and tt.NGAYCHUNGTU <= '" + this.denngay + "' ";
	    	if(this.nppId.trim().length()>0)
	    		query+="and ttna.NPP_FK = '" + this.nppId + "'   	  \n";
	    	query+=				    			
	    	"		GROUP BY ttna.KHACHHANG_FK   \n" +			    	
	    	"	)   " +
	    	"	psc group by psc.KHACHHANG_FK   \n" +
	    	")   \n" +
	    	"psctk on kh.PK_SEQ = psctk.KHACHHANG_FK   \n" +
	    	" LEFT JOIN \n"+ 
	    	" ( \n"+
	    	"	SELECT  hd.KHACHHANG_FK, isnull(SUM(round(ISNULL(SOTIENCANTRU,0),0)),0) as tongtienTT \n"+   
	    	"		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+   
	    	"				INNER JOIN ( \n"+   
	    	"								SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+   
	    	"								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+   
	    	"								WHERE  ct.TRANGTHAI = 1 \n"+   
	    	"								GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK \n"+   
	    	"							) \n"+   
	    	"							ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+   
	    	"		WHERE 	1=1 and hd.NGAYXUATHD >= '"+this.tungay+"' and hd.NGAYXUATHD <= '"+this.denngay+"' ";
	    	if(this.nppId.trim().length()>0)
	    		query+="and hd.NPP_FK = '" + this.nppId+ "'	  \n";
	    	query+=
	    	"		GROUP BY hd.KHACHHANG_FK \n"+   
	    	" ) cantrutk on kh.PK_SEQ = cantrutk.KHACHHANG_FK \n"+			    	
	    	"where 1 = 1 \n";
	
	if(this.ddkdId.trim().length() > 0)
	{
		query += "   and kh.pk_seq in \n"+
				 "             (\n"+
				 "                SELECT c.KHACHHANG_FK \n"+
				 "                FROM   DAIDIENKINHDOANH a INNER JOIN TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+
				 "                        INNER JOIN KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
				 "                WHERE  a.PK_SEQ = '"+this.ddkdId+"' \n"+
				 "              ) \n";
	}
	if(this.nvgnId.trim().length() > 0)
	{
		query += "  and kh.pk_seq in ( select KHACHHANG_FK from NVGN_KH where nvgn_fk = '" + this.nvgnId + "' ) \n";
	}
	if(this.vungId.trim().length()>0)
	{
		query+=" and kh.npp_fk in (select pk_Seq  from nhaphanphoi  where khuvuc_fk in (select pk_seq from KHUVUC WHERE VUNG_FK='"+this.vungId+"' ))";
	}
		
	if(this.ttId.trim().length() > 0 )//địa bàn
	{
		query+=" and kh.NPP_FK in ( select pk_seq from nhaphanphoi  where tinhthanh_fk = '" + ttId + "' ) \n";
	}
	if(this.khId.trim().length() > 0)
		query += "  and kh.pk_seq = '" + this.khId + "' \n";
 	if(this.nppId.trim().length()>0)
 		query += " and kh.NPP_FK = '" + this.nppId + "' \n";
 	
    query += " ORDER BY kh.TEN asc \n";    	

	System.out.println("BC_TheoKH \n ____"+query);
    	
			return query;
	}

	
	public String getBCTheoDoiTac() {
		
		String query = ""+
		"	SELECT npp.PK_SEQ, npp.TEN, isnull(npp.MaFAST,'') MAFAST, ISNULL(dndk.tongtienAVAT,0) as dunodauky, \n"+ 
		"		   ISNULL (dcdk.SOTIENTT,0) as ducodauky, ISNULL (psntk.tongtienAVAT,0) as phatsinhno, ISNULL(psctk.SOTIENTT,0) as phatsinhco, \n"+
		"		   ISNULL(cantrutk.tongtienTT,0) ckthang, npp.Diachi \n"+
		"	FROM NHAPHANPHOI npp LEFT JOIN \n"+
		"	( \n"+
		"		SELECT psn_dk.NPP_DAT_FK , SUM(ISNULL(psn_dk.tongtienAVAT,0)) as tongtienAVAT \n"+ 
		"		FROM   \n"+
		"			(	\n"+
		"				SELECT psn.NPP_DAT_FK, sum(tongtienAVAT) as tongtienAVAT \n"+ 
		"				FROM \n"+
		"					(  \n"+
		"						SELECT	hd.NPP_DAT_FK , SUM(tongtienavat) tongtienAVAT \n"+ 
		"						FROM	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK = npp.PK_SEQ \n"+ 
		"						WHERE	1=1 and  hd.LOAIXUATHD = 0 and hd.TRANGTHAI  not in ( 1, 3, 5 ) \n"+
		"								and hd.NGAYXUATHD < '"+this.tungay+"' and hd.NPP_FK ='"+this.nppId+"' and hd.NPP_DAT_FK is not null \n"+ 
		"						GROUP BY hd.NPP_DAT_FK   \n"+
		"					    \n"+
		"						UNION ALL \n"+ 
		"					    \n"+
		"						SELECT  btcn_hd.NPP_DAT_FK , SUM(btcn_hd.GHINO) as tongtienAVAT \n"+ 
		"						FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+ 
		"						WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+this.tungay+"' and btcn_hd.NPP_DAT_FK is not null \n"+
		"								and btcn.NPP_FK = '"+this.nppId+"' \n"+
		"						GROUP BY btcn_hd.NPP_DAT_FK \n"+
		"						\n"+
		"						UNION ALL \n"+
		"						\n"+
		"						SELECT 	hd.NPP_DAT_FK, round(bt.tongtienavat,0) tongtienavat \n"+ 
		"						FROM    ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK = npp.PK_SEQ \n"+ 
		"								inner join (  \n"+
		"												SELECT bthd.NPP_DAT_FK, bthd.HOADON_FK, SUM(ROUND(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+
		"												FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
		"												WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1  and bthd.NPP_DAT_FK is not null and bt.NPP_FK = '"+this.nppId+"' \n"+
		"												GROUP BY bthd.NPP_DAT_FK, bthd.HOADON_FK   \n"+
		"											)   \n"+
		"											bt on hd.NPP_DAT_FK = bt.NPP_DAT_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
		"						WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIXUATHD = 0 and hd.NGAYXUATHD <'"+this.tungay+"'  \n"+
		"								   and hd.NPP_DAT_FK is not null and hd.NPP_FK = '"+this.nppId+"' \n"+
		"					) psn \n"+
		"					GROUP BY psn.NPP_DAT_FK \n"+ 
		"					\n"+
		"					UNION ALL \n"+ 
		"					\n"+
		"					SELECT 	dnkh.NPP_DAT_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT \n"+ 
		"					FROM   	DUNO_KHACHHANG dnkh LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_DAT_FK = npp.PK_SEQ \n"+
		"					WHERE 	1=1 and MONTH(dnkh.NgayDuNo) <= ( month('"+this.tungay+"') - 1 ) and dnkh.SONO >= 0  and  dnkh.NPP_FK='"+this.nppId+"' \n"+
		"							and dnkh.NPP_DAT_FK is not null \n"+	
		"					GROUP BY dnkh.NPP_DAT_FK   \n"+
		"		) psn_dk   \n"+
		"		GROUP BY psn_dk.NPP_DAT_FK  \n"+
		"	)dndk ON npp.PK_SEQ = dndk.NPP_DAT_FK LEFT JOIN \n"+
		"	( \n"+
		"		SELECT 	psc.NPP_DAT_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT \n"+
		"		FROM ( \n"+
		"				SELECT 	hd.NPP_DAT_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+
		"				FROM 	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK=npp.PK_SEQ   \n"+
		"						INNER JOIN (    \n"+
		"										SELECT tthd.NPP_FK NPP_DAT_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+
		"										FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK		\n"+	
		"										WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '"+this.tungay+"'  and tthd.NPP_FK is not null and tt.NPP_FK ='"+this.nppId+"' \n"+
		"	  									GROUP BY tthd.NPP_FK, tthd.HOADONNPP_FK   \n"+
		"	 								) 	  \n"+
		"	 								tt on hd.NPP_DAT_FK = tt.NPP_DAT_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+ 
		"				WHERE 	1=1 and hd.NPP_FK = '"+this.nppId+"' and hd.NPP_DAT_FK is not null \n"+ 
		"				GROUP BY hd.NPP_DAT_FK   \n"+
		"				\n"+
		"				UNION ALL \n"+
		"				\n"+
		"				SELECT  btcn_hd.NPP_DAT_FK PK_SEQ, SUM(btcn_hd.GHICO) as tongtienAVAT \n"+ 
		"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+ 
		"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+this.tungay+"'  \n"+
		"						and btcn.NPP_FK = '"+this.nppId+"' and btcn_hd.NPP_DAT_FK is not null \n"+ 
		"				GROUP BY btcn_hd.NPP_DAT_FK \n"+
		"				\n"+
		"				UNION ALL \n"+
		"				\n"+
		"				SELECT dnkh.NPP_DAT_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+
		"				FROM   DUNO_KHACHHANG dnkh INNER JOIN NHAPHANPHOI npp on dnkh.NPP_DAT_FK=npp.PK_SEQ \n"+
		"		 			   INNER JOIN   \n"+
		"		 				(   \n"+
		"							SELECT	tthd.NPP_FK NPP_DAT_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+
		"							FROM	ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK		\n"+
		"							WHERE	tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '"+this.tungay+"'  and tthd.NPP_FK is not null and tt.NPP_FK ='"+this.nppId+"' \n"+
		"							GROUP BY tthd.NPP_FK, tthd.HOADONNPP_FK   \n"+
		"			 			)  \n"+
		"			 			tt on dnkh.NPP_DAT_FK = tt.NPP_DAT_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+
		"				 WHERE 1=1  and dnkh.NPP_FK ='"+this.nppId+"'  and dnkh.NPP_DAT_FK is not null \n"+ 
		"				 GROUP BY dnkh.NPP_DAT_FK   \n"+
		"				\n"+
		"				UNION ALL \n"+
		"			  	\n"+
		"				SELECT	xnkh_hd.NPP_DAT_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+ 
		"				FROM	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+ 
		"				WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+this.tungay+"' and xnkh.NPP_FK = '"+this.nppId+"' and xnkh_hd.NPP_DAT_FK is not null \n"+ 
		"				GROUP BY xnkh_hd.NPP_DAT_FK \n"+
		"				\n"+
		"				UNION ALL \n"+ 
		"				\n"+
		"				SELECT  hd.NPP_DAT_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+ 
		"	  			FROM 	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK=npp.PK_SEQ \n"+
		"	   					INNER JOIN (   \n"+
		"		  							SELECT bthd.NPP_DAT_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+ 
		"		  							FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
		"		  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1 and bthd.NPP_DAT_FK is not null and bt.NPP_FK = '"+this.nppId+"' \n"+ 
		"		  							GROUP BY bthd.NPP_DAT_FK, bthd.HOADON_FK   \n"+
		"		 						   )   \n"+
		"		 							bt on hd.KHACHHANG_FK = bt.NPP_DAT_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
		"	 			WHERE 	1=1 and hd.NGAYXUATHD < '"+this.tungay+"' and hd.NPP_FK = '"+this.nppId+"' and hd.NPP_DAT_FK is not null \n"+ 
		"	 			GROUP BY hd.NPP_DAT_FK  \n"+
		"				\n "+
		"				UNION ALL \n"+				
		"				SELECT	tttl.NppTra_FK NPP_DAT_FK, SUM(tttl.TienSauThue) tongtienAVAT \n"+
		"				FROM	Erp_HangTraLaiNpp tttl INNER JOIN NHAPHANPHOI npp on tttl.NPP_FK = npp.PK_SEQ \n"+
		"				WHERE	1=1 and tttl.TRANGTHAI = 1 AND tttl.NGAYTRA < '"+this.tungay+"' AND tttl.NPP_FK ='"+this.nppId+"' \n"+ 
		"						and tttl.NppTra_FK is not null \n"+
		"				GROUP BY tttl.NppTra_FK \n"+
		"				\n "+
		"		)  psc \n"+
		"		GROUP BY psc.NPP_DAT_FK \n"+  
		"	)dcdk ON dcdk.NPP_DAT_FK = npp.PK_SEQ LEFT JOIN \n " +		
		"	( \n"+
		"		SELECT psn.PK_SEQ as NPP_DAT_FK, isnull( sum(tongtienAVAT), 0 ) as tongtienAVAT \n"+ 
		"		FROM   \n"+
		"		( \n"+
		"			SELECT	hd.NPP_DAT_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+ 
		"			FROM	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK = npp.PK_SEQ \n"+ 
		"			WHERE	1=1 and  hd.LOAIXUATHD = 0 and hd.TRANGTHAI  not in ( 1, 3, 5 ) \n"+
		"					and hd.NGAYXUATHD >= '"+this.tungay+"' and  hd.NGAYXUATHD <= '"+this.denngay+"' and hd.NPP_FK ='"+this.nppId+"' and hd.NPP_DAT_FK is not null \n"+
		"			GROUP BY hd.NPP_DAT_FK  \n"+
		"			\n"+
		"			UNION ALL \n"+ 
		"			\n"+
		"			SELECT  btcn_hd.NPP_DAT_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+ 
		"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+ 
		"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '"+this.tungay+"' and btcn.NGAYCHUNGTU <= '"+this.denngay+"'  \n"+
		"					and btcn.NPP_FK = '"+this.nppId+"' and btcn_hd.NPP_DAT_FK is not null \n"+ 
		"			GROUP BY btcn_hd.NPP_DAT_FK \n"+
		"			\n"+
		"			UNION ALL \n"+ 
		"			\n"+
		"			SELECT 	hd.NPP_DAT_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+ 
		"			FROM    ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK=npp.PK_SEQ \n"+ 
		"					inner join (  \n"+
		"									SELECT bthd.NPP_DAT_FK, bthd.HOADON_FK, SUM(ROUND(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+
		"									FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
		"									WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1  and bthd.NPP_DAT_FK is not null and bt.NPP_FK = '"+this.nppId+"' \n"+ 
		"									GROUP BY bthd.NPP_DAT_FK, bthd.HOADON_FK   \n"+
		"								)   \n"+
		"								bt on hd.NPP_DAT_FK = bt.NPP_DAT_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
		"			WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIXUATHD = 0 and hd.NGAYXUATHD <= '"+this.denngay+"' and hd.NGAYXUATHD >= '"+this.tungay+"' \n"+
		"				   and hd.NPP_DAT_FK is not null and hd.NPP_FK = '"+this.nppId+"'	 \n"+
		"		)psn   \n"+
		"		GROUP BY psn.PK_SEQ \n"+
		"	) psntk ON psntk.NPP_DAT_FK = npp.PK_SEQ LEFT JOIN \n"+
		"	( \n"+
		"		SELECT 	psc.NPP_DAT_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT \n"+ 
		"		FROM   \n"+
		"		( \n"+
		"			SELECT 	hd.NPP_DAT_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+ 
		"			FROM 	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK=npp.PK_SEQ \n"+ 
		"					INNER JOIN (    \n"+
		"									SELECT tthd.NPP_FK NPP_DAT_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+ 
		"									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK			\n"+
		"									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+this.tungay+"' and tt.NGAYCHUNGTU <= '"+this.denngay+"'  and tthd.NPP_FK is not null and tt.NPP_FK ='"+this.nppId+"' \n"+ 
		"									GROUP BY tthd.NPP_FK, tthd.HOADONNPP_FK   \n"+
		"								) 	  \n"+
		"								tt on hd.NPP_DAT_FK = tt.NPP_DAT_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+ 
		"			WHERE 	1=1 and hd.NPP_FK = '"+this.nppId+"' and hd.NPP_DAT_FK is not null \n"+
		"			GROUP BY hd.NPP_DAT_FK   \n"+
		"			\n"+
		"			UNION ALL \n"+ 
		"			\n"+
		"			SELECT  btcn_hd.NPP_DAT_FK PK_SEQ, SUM(btcn_hd.GHICO) as tongtienAVAT \n"+ 
		"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+ 
		"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU <= '"+this.denngay+"' and btcn.NGAYCHUNGTU >= '"+this.tungay+"' \n"+ 
		"					and btcn.NPP_FK = '"+this.nppId+"' and btcn_hd.NPP_DAT_FK is not null \n"+ 
		"			GROUP BY btcn_hd.NPP_DAT_FK \n"+
		"			\n"+
		"			UNION ALL \n"+ 
		"			\n"+
		"			SELECT  hd.NPP_DAT_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+ 
		"			FROM 	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK=npp.PK_SEQ \n"+
		"					INNER JOIN (   \n"+
		"	  							SELECT bthd.NPP_DAT_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+ 
		"	  							FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
		"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1 and bthd.NPP_DAT_FK is not null and bt.NPP_FK = '"+this.nppId+"' \n"+ 
		"	  							GROUP BY bthd.NPP_DAT_FK, bthd.HOADON_FK  \n"+ 
		"	 						   )   \n"+
		"	 							bt on hd.NPP_DAT_FK = bt.NPP_DAT_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
		"			WHERE 	1=1 and hd.NGAYXUATHD <= '"+this.tungay+"' and hd.NGAYXUATHD >= '"+this.denngay+"' and hd.NPP_FK = '"+this.nppId+"' and hd.NPP_DAT_FK is not null \n"+ 
		"			GROUP BY hd.NPP_DAT_FK  \n"+
		"			\n"+
		"			UNION ALL \n"+
		"			\n"+
		"			SELECT	xnkh_hd.NPP_DAT_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+
		"			FROM	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+
		"			WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU <= '"+this.denngay+"' and XNKH.NGAYCHUNGTU >= '"+this.tungay+"' and xnkh.NPP_FK = '"+this.nppId+"' and xnkh_hd.NPP_DAT_FK is not null \n"+ 
		"			GROUP BY xnkh_hd.NPP_DAT_FK		 \n"+
		" 			\n "+
		"			UNION ALL \n"+
		"			\n "+
		"			SELECT	tttl.NppTra_FK NPP_DAT_FK, SUM(tttl.TienSauThue) tongtienAVAT \n"+
		"			FROM	Erp_HangTraLaiNpp tttl INNER JOIN NHAPHANPHOI npp on tttl.NPP_FK = npp.PK_SEQ \n"+
		"			WHERE	1=1 and tttl.TRANGTHAI = 1 AND tttl.NGAYTRA >= '"+this.tungay+"' AND tttl.NGAYTRA <= '"+this.denngay+"' AND tttl.NPP_FK ='"+this.nppId+"' \n"+ 
		"					and tttl.NppTra_FK is not null \n"+
		" 			GROUP BY tttl.NppTra_FK \n"+
		"		) 	psc group by psc.NPP_DAT_FK   \n"+
		"	)psctk ON npp.PK_SEQ = psctk.NPP_DAT_FK   \n"+
		"	LEFT JOIN \n"+
		"	 ( \n"+
		"		SELECT  hd.NPP_DAT_FK, isnull(SUM(round(ISNULL(SOTIENCANTRU,0),0)),0) as tongtienTT \n"+ 
		"		FROM 	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK=npp.PK_SEQ \n"+
		"				INNER JOIN ( \n"+
		"								SELECT cthd.NPP_DAT_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+ 
		"								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+
		"								WHERE  ct.TRANGTHAI = 1 and ct.NPP_FK = '"+this.nppId+"' and cthd.NPP_DAT_FK is not null \n"+ 
		"								GROUP BY cthd.NPP_DAT_FK, cthd.HOADON_FK \n"+
		"							) \n"+
		"							ct on hd.NPP_DAT_FK = ct.NPP_DAT_FK and hd.PK_SEQ = ct.HOADON_FK \n"+
		"		WHERE 	1=1 and hd.NGAYXUATHD >= '"+this.tungay+"' and hd.NGAYXUATHD <= '"+this.denngay+"' and hd.NPP_FK = '"+this.nppId+"' and hd.NPP_DAT_FK is not null \n"+ 
		"		GROUP BY hd.NPP_DAT_FK \n"+
		"	 ) cantrutk on npp.PK_SEQ = cantrutk.NPP_DAT_FK \n"+ 
		"	 WHERE 1 = 1  \n";
				
		if(this.doitacId.trim().length() > 0)
    		query += " and npp.PK_SEQ= '"+this.doitacId+"' \n";
	 	if(this.nppId.trim().length()>0)
	 		query += "  and npp.TRUCTHUOC_FK = '"+this.nppId+"' \n";
	 	
	 	query += "	 ORDER BY npp.TEN asc \n";
		
	 	System.out.println(query);
		return query;
	}


}
