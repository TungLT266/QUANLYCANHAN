package geso.traphaco.distributor.beans.inchietkhautruckthang.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.inchietkhautruckthang.IInKhauTruCKThang;
import geso.traphaco.distributor.db.sql.dbutils;

public class InKhauTruCKThang implements IInKhauTruCKThang
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
	ResultSet khuvuc;
	ResultSet vung;
	
	ResultSet ttRs;
	String ttId="";
	
	String khIds;
	String xemtheo;
	
	dbutils db;
	
	public InKhauTruCKThang()
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
	    this.sohoadon="";
	    this.ptVAT = "";
	    this.khId="";
	    this.activeTab="0";
	    
	    this.nvbhId = "";
	    this.maFAST = "";
	    
	    this.sohoadontu = "";
	    this.sohoadonden = "";
	    this.nppID="";
		
	    this.nvbhId="";
	    this.nvgnId="";
	    this.ddkdId="";
		this.vungId="";
		this.khuvucId ="";
		this.ttId="";
		this.khIds = "" ;
		this.xemtheo = "0";
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
	
	
	}
	
	public void init()
	{
		getNppInfo();
		
		String query = "";
       
		if(this.tungay.trim().length() > 0 && this.denngay.trim().length() > 0  )
		{			
						
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
		getNppInfo();
		
		System.out.println("vao day");
		String query="";		
		
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
		
		this.vung = db.get("select pk_seq,ten,diengiai from vung ");
		
		if (this.vungId.length() > 0)
		{			
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk ='" + this.vungId + "'");
		} else
		{
			String query_khuvuc=" select PK_SEQ, TEN from KHUVUC "
					+ "	where PK_SEQ in (select KHUVUC_Fk from NHAPHANPHOI "
					+ "	where pk_seq in "+ Ult.quyen_npp(userId)+")"; 			
			this.khuvuc= db.get(query_khuvuc);		
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
		if(this.ddkdId.trim().length()>0)
		{
			query = query + " and kh.PK_SEQ in ( \n"+
					"			 						SELECT  c.KHACHHANG_FK \n"+ 
					"									FROM 	DAIDIENKINHDOANH a INNER JOIN TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
					"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
					"									WHERE a.PK_SEQ ='"+ddkdId+"'  \n"+
					"		  						) \n";
		}
		System.out.println("KH "+query);
		this.khRs = db.get(query);
		
		query="select PK_SEQ,TEN from tinhthanh   where 1=1 ";
		if(vungId.length()>0)
			query+=" and vung_fk='"+vungId+"'";;
		
		this.ttRs= this.db.get(query);
			
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

	
	public String getnppId() {
		
		return this.nppId;
	}

	
	public void setnppId(String nppID) {
		
		this.nppId=nppID;
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

	
	public void setvungId(String vungId) {
		
		this.vungId=vungId;
	}

	
	public String getvungId() {
		
		return this.vungId;
	}

	
	public void setvung(ResultSet vung) {
		
		this.vung=vung;
	}

	
	public ResultSet getvung() {
		
		return this.vung;
	}

	
	public void setkhuvucId(String khuvucId) {
		
		this.khuvucId=khuvucId;
	}

	
	public String getkhuvucId() {
		
		return this.khuvucId;
	}

	
	public void setkhuvuc(ResultSet khuvuc) {
		
		this.khuvuc=khuvuc;
	}

	
	public ResultSet getkhuvuc() {
		
		return this.khuvuc;
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

	public void setKhIds(String khIds) 
	{
		this.khIds= khIds;
	}


	public String getKhIds() 
	{
		return this.khIds;
	}

	
	public String getXemtheo() {

		return this.xemtheo;
	}


	public void setXemtheo(String xemtheo) {
		
		this.xemtheo = xemtheo;
	}
	 

}
