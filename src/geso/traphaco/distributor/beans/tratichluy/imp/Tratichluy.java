package geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluy;
import geso.traphaco.distributor.db.sql.dbutils;

public class Dangkykhuyenmaitichluy implements IDangkykhuyenmaitichluy, Serializable
{
	private static final long serialVersionUID = 1L;
	
	String Id;
	String userId;
    String nppId;
    String nppTen;
    String tungay;
    String denngay;
    
    ResultSet ctkmRs;
    String ctkmId;
    
    ResultSet nvbhRS;
	String nvbhIds;
	
    ResultSet khRs;
	String khId;

	String Msg;
	String mucdangky;
	
	String[] khMa;
	String[] khTen;
	String[] khDiachi;
	String[] khSohopdong;
	String[] khTungay;
	String[] khDenngay;
	String[] khDoanhso;
	
	dbutils db;
	
	public Dangkykhuyenmaitichluy()
	{
		 this.userId="";
	     this.nppId="";
	     this.nppTen="";
	     this.tungay="";
	     this.denngay="";
		 this.ctkmId ="";
		 this.nvbhIds = "";
		 this.khId = "";
		 this.Msg ="";
		 this.Id = "";
		 this.mucdangky = "";
		 this.db = new dbutils();
		
	}
	public String getUserId() {
		
		return this.userId;
	}
	
	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	
	public String getNppId() {
		
		return this.nppId;
	}

	
    public void setNppId(String nppId) {
		
	   this.nppId = nppId;	
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	private void getNppInfo(){
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
	
	}
	
	
	public String getNppTen() {
		
		return this.nppTen;
	}
	
	public void init() 
	{
		getNppInfo();
		
		if( this.Id.length() > 0 )
		{
			String sqlgetinfo = "select * from DANGKYKM_TICHLUY where pk_seq = " + this.Id;
			
			ResultSet rs = db.get(sqlgetinfo);
			if(rs != null)
			{
				try
				{
					if(rs.next())
					{
						this.ctkmId = rs.getString("tieuchiTL_fk");
						this.nppId = rs.getString("npp_fk");
						this.mucdangky = rs.getString("muc");
					}
					rs.close();
					
					//
					/*String query = "select khachhang_fk from DANGKYKM_TICHLUY_KHACHHANG where dkkmtichluy_fk = '" + this.Id + "' ";
					ResultSet rsKh = db.get(query);
					String khId = "";
					while(rsKh.next())
					{
						khId += rsKh.getString("khachhang_fk") + ",";
					}
					rsKh.close();
					
					if(khId.trim().length() > 0)
					{
						khId = khId.substring(0, khId.length() - 1);
						this.khId = khId;
					}*/
					
					
					String query = "select b.maFAST, b.TEN, b.DIACHI, a.sohopdong, a.ngaykyhd, a.ngayketthuchd, ISNULL(a.doanhso, 0) as doanhso " +
								   " from DANGKYKM_TICHLUY_KHACHHANG a inner join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ " +
								   " where a.DKKMTICHLUY_FK = '" + this.Id + "' ";
					ResultSet rsKh = db.get(query);
					String khMA = "";
					String khTen = "";
					String khDiachi = "";
					String khSohopdong = "";
					String khTungay = "";
					String khDenngay = "";
					String khDoanhso = "";
					
					NumberFormat formater = new DecimalFormat("#,###,###");
					while(rsKh.next())
					{
						khMA += rsKh.getString("maFAST") + "__";
						khTen += rsKh.getString("TEN") + "__";
						khDiachi += rsKh.getString("DIACHI") + "__";
						khSohopdong += ( rsKh.getString("sohopdong").trim().length() <= 0 ? " " : rsKh.getString("sohopdong") ) + "__";
						khTungay += rsKh.getString("ngaykyhd") + "__";
						khDenngay += rsKh.getString("ngayketthuchd") + "__";
						khDoanhso += formater.format( rsKh.getDouble("doanhso") ) + "__";
					}
					rsKh.close();
					
					if(khMA.trim().length() > 0)
					{
						khMA = khMA.substring(0, khMA.length() - 2);
						this.khMa = khMA.split("__");
						
						khTen = khTen.substring(0, khTen.length() - 2);
						this.khTen = khTen.split("__");
						
						khDiachi = khDiachi.substring(0, khDiachi.length() - 2);
						this.khDiachi = khDiachi.split("__");
						
						khSohopdong = khSohopdong.substring(0, khSohopdong.length() - 2);
						this.khSohopdong = khSohopdong.split("__");
						
						khTungay = khTungay.substring(0, khTungay.length() - 2);
						this.khTungay = khTungay.split("__");
						
						khDenngay = khDenngay.substring(0, khDenngay.length() - 2);
						this.khDenngay = khDenngay.split("__");
						
						khDoanhso = khDoanhso.substring(0, khDoanhso.length() - 2);
						this.khDoanhso = khDoanhso.split("__");
					}
				}
				catch(Exception er)
				{
					System.out.println("Error :"+er.toString());
					er.printStackTrace();
				}
			}
		}
		
		this.createRs();
	}
	
	public void setMessage(String Msg) {
		
		this.Msg = Msg;
	}
	
	public String getMessage() {
		
		return this.Msg;
	}

	public void setctkmId(String ctkmId) {
	
		this.ctkmId = ctkmId;
	}
	
	public String getctkmId() {
		
		return this.ctkmId;
	}

	
	
	public boolean save() 
	{
		try
		{
			if(this.mucdangky.trim().length() <= 0)
			{
				this.Msg = "Vui lòng chọn mức đăng ký";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String sql_checkexit =  "select pk_seq from DANGKYKM_TICHLUY where tieuchiTL_fk="+this.ctkmId +" " +
									"	and  npp_fk="+ this.nppId + " and trangthai <> '2' and muc = '" + this.mucdangky + "' ";
			System.out.println(sql_checkexit);
			ResultSet rs_check=db.get(sql_checkexit);
			if(rs_check!=null){
				if(rs_check.next()){
					this.Msg="Da Dang Ky Khuyen Mai Tich Luy Cho Nha Phan Phoi Nay Voi Muc ( " + ( Integer.parseInt(this.mucdangky) + 1 ) + " ),Vui Long Chon CT Khac.";
					db.update("rollback");
				    return false;
				}
			}
			
			String insert_="insert into DANGKYKM_TICHLUY (tieuchiTL_fk, npp_fk, trangthai,ngaytao,nguoitao,ngaysua,nguoisua, muc) " +
							" values("+this.ctkmId+","+this.nppId+",'0','"+this.getDateTime()+"',"+this.userId+",'"+this.getDateTime()+"',"+this.userId+", '" + this.mucdangky + "')";
				
			if(!db.update(insert_)){
				
				    this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ insert_;
					db.update("rollback");
				    return false;
				    
			}
			 
			/*if(this.khId.trim().length() > 0)
			{
				String query = "insert DANGKYKM_TICHLUY_KHACHHANG(DKKMTICHLUY_FK, KHACHHANG_FK) " +
								"select IDENT_CURRENT('DANGKYKM_TICHLUY'), pk_seq from KhachHang where pk_seq in (" + this.khId + ") ";
				if(!db.update(query))
				{
				    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ query;
					db.update("rollback");
				    return false;
				}
			}*/
			
			if( this.khMa != null )
			{
				String query = "";
				for( int i = 0; i < this.khMa.length; i++ )
				{
					if( this.khMa[i].trim().length() > 0 && this.khDoanhso[i].trim().length() > 0 )
					{
						query += "select pk_seq as khId, N'" + this.khSohopdong[i] + "' as sohopdong, '" + this.khTungay[i] + "' as ngaykyhd, '" + this.khDenngay[i] + "' as ngayketthuchd, '"  + this.khDoanhso[i].replaceAll(",", "") + "' as doanhso " + 
								 " from KHACHHANG "+ 
								 " where maFAST = '" + this.khMa[i] + "' and npp_fk = '" + this.nppId + "' ";
						query += " union ALL ";
					}
				}
				
				if( query.trim().length() > 0 )
				{
					query += query.substring(0, query.length() - 11);
					
					String sql = "insert DANGKYKM_TICHLUY_KHACHHANG( DKKMTICHLUY_FK, KHACHHANG_FK, sohopdong, ngaykyhd, ngayketthuchd, doanhso ) " + 
								" select IDENT_CURRENT('DANGKYKM_TICHLUY'), kh.* from ( " + query + " ) as kh ";
					if(!db.update(sql))
					{
					    this.Msg = "Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi " + query;
						db.update("rollback");
					    return false;
					}
					
					sql = " update DANGKYKM_TICHLUY_KHACHHANG set ngaykyhd = convert(varchar(10), CAST( ngaykyhd as datetime ), 120 ) " + 
						  " where DKKMTICHLUY_FK = IDENT_CURRENT('DANGKYKM_TICHLUY') and CHARINDEX('/', ngaykyhd, 0) > 0 ";
					if(!db.update(sql))
					{
					    this.Msg = "Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi " + query;
						db.update("rollback");
					    return false;
					}
					
					sql = " update DANGKYKM_TICHLUY_KHACHHANG set ngayketthuchd = convert(varchar(10), CAST( ngayketthuchd as datetime ), 120 ) " + 
						  " where DKKMTICHLUY_FK = IDENT_CURRENT('DANGKYKM_TICHLUY') and CHARINDEX('/', ngayketthuchd, 0) > 0 ";
					if(!db.update(sql))
					{
					    this.Msg = "Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi " + query;
						db.update("rollback");
					    return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			db.update("rollback");
			return false;
		}
		return true;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose() 
	{	
		try {
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(db!=null)
		db.shutDown();
	}
	public void setId(String Id) {
		
		this.Id = Id;
	}
	
	public String getId() {
		
		return this.Id;
	}

	public boolean edit() 
	{
		try{
			
			if(this.mucdangky.trim().length() <= 0)
			{
				this.Msg = "Vui lòng chọn mức đăng ký";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String sql_checkexit = "select pk_seq from DANGKYKM_TICHLUY where tieuchiTL_fk="+this.ctkmId +" and  npp_fk="+ this.nppId + " and trangthai <> '2' and muc = '" + this.mucdangky + "' and pk_seq <>"+ this.Id;
			System.out.println("::: CHECK EXIT: " + sql_checkexit );
			ResultSet rs_check = db.get(sql_checkexit);
			if(rs_check!=null)
			{
				if(rs_check.next())
				{
					this.Msg="Da Dang Ky Khuyen Mai Tich Luy Cho Nha Phan Phoi Nay Voi Muc ( " + ( Integer.parseInt(this.mucdangky) + 1 ) + " ),Vui Long Chon CT Khac.";
					db.update("rollback");
				    return false;
				}
			}
			
			String query = "delete DANGKYKM_TICHLUY_KHACHHANG where dkkmtichluy_fk = '" + this.Id + "' ";
			if(!db.update(query))
			{
			    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ query;
				db.update("rollback");
			    return false;
			}
			
			String insert_="update DANGKYKM_TICHLUY set tieuchiTL_fk="+this.ctkmId+" ,npp_fk="+this.nppId+" , muc = '" + this.mucdangky + "', ngaysua="+this.getDateTime()+",nguoisua="+this.userId+
					" where pk_seq="+ this.Id;		
			if(!db.update(insert_)){
				    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ insert_;
					db.update("rollback");
				    return false;
			}
			
			/*if(this.khId.trim().length() > 0)
			{
				query = "insert DANGKYKM_TICHLUY_KHACHHANG(DKKMTICHLUY_FK, KHACHHANG_FK) " +
								"select IDENT_CURRENT('DANGKYKM_TICHLUY'), pk_seq from KhachHang where pk_seq in (" + this.khId + ") ";
				if(!db.update(query))
				{
				    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ query;
					db.update("rollback");
				    return false;
				}
			}*/
			
			if( this.khMa != null )
			{
				query = "";
				for( int i = 0; i < this.khMa.length; i++ )
				{
					if( this.khMa[i].trim().length() > 0 && this.khDoanhso[i].trim().length() > 0 )
					{
						query += "select pk_seq as khId, N'" + this.khSohopdong[i] + "' as sohopdong, '" + this.khTungay[i] + "' as ngaykyhd, '" + this.khDenngay[i] + "' as ngayketthuchd, '"  + this.khDoanhso[i].replaceAll(",", "") + "' as doanhso " + 
								 " from KHACHHANG "+ 
								 " where maFAST = '" + this.khMa[i] + "' and npp_fk = '" + this.nppId + "' ";
						query += " union ";
					}
				}
				
				if( query.trim().length() > 0 )
				{
					query += query.substring(0, query.length() - 7);
					
					String sql = "insert DANGKYKM_TICHLUY_KHACHHANG( DKKMTICHLUY_FK, KHACHHANG_FK, sohopdong, ngaykyhd, ngayketthuchd, doanhso ) " + 
								" select distinct '" + this.Id + "', kh.* from ( " + query + " ) as kh ";
					if(!db.update(sql))
					{
					    this.Msg = "Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi " + query;
						db.update("rollback");
					    return false;
					}
					
					sql = " update DANGKYKM_TICHLUY_KHACHHANG set ngaykyhd = convert(varchar(10), CAST( ngaykyhd as datetime ), 120 ) " + 
						  " where DKKMTICHLUY_FK = '" + this.Id + "' and CHARINDEX('/', ngaykyhd, 0) > 0 ";
					if(!db.update(sql))
					{
					    this.Msg = "Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi " + sql;
						db.update("rollback");
					    return false;
					}
					
					sql = " update DANGKYKM_TICHLUY_KHACHHANG set ngayketthuchd = convert(varchar(10), CAST( ngayketthuchd as datetime ), 120 ) " + 
						  " where DKKMTICHLUY_FK = '" + this.Id + "' and CHARINDEX('/', ngayketthuchd, 0) > 0 ";
					if(!db.update(sql))
					{
					    this.Msg = "Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi " + sql;
						db.update("rollback");
					    return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			er.printStackTrace();
			db.update("rollback");
			return false;
		}
		
		return true;
	}

	public boolean Chot() 
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			String insert_="update DANGKYKM_TICHLUY set trangthai=1 ,ngaysua="+this.getDateTime()+",nguoisua="+this.userId+
					" where pk_seq="+ this.Id;		
			if(!db.update(insert_))
			{
				    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ insert_;
					db.update("rollback");
				    return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			db.update("rollback");
			return false;
		}
		return true;
	}

	public boolean Delete() {

		try{
			db.getConnection().setAutoCommit(false);
			String insert_="update DANGKYKM_TICHLUY set trangthai=2 ,ngaysua="+this.getDateTime()+",nguoisua="+this.userId+
					" where pk_seq="+ this.Id;		
			if(!db.update(insert_)){
				    this.Msg="Khong The Thuc Hien Huy  ,Vui Long Thu Lai. Loi "+ insert_;
					db.update("rollback");
				    return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.Msg="Khong The Thuc Hien Huy ,Vui Long Thu Lai. Loi "+ er.toString();
			db.update("rollback");
			return false;
		}
		return true;
	}
	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}
	
	public void setctkmIds(ResultSet ctkmIds) {
		
		this.ctkmRs = ctkmIds;
	}
	
	public ResultSet getctkmIds() {
		
		return this.ctkmRs;
	}
	
	public String getNvbhIds() {
		
		return this.nvbhIds;
	}
	
	public void setNvbhIds(String nvbdIds) {
		
		this.nvbhIds = nvbdIds;
	}
	
	public String getKhId() {
		
		return this.khId;
	}
	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}
	
	public void createRs() 
	{
		this.getNppInfo();
		
		//Lay MAX ngay khoa so
		/*String query = "select isnull(max(NGAYKS), '" + getDateTime() + "') as ngayKS from KHOASONGAY where NPP_FK = '" + this.nppId + "'";
		ResultSet rsKsn = db.get(query);
		String ngayKS = "";
		try 
		{
			if(rsKsn.next())
			{
				ngayKS = rsKsn.getString("ngayKS");
			}
			rsKsn.close();
		} 
		catch (Exception e) {}*/
		
		String ngayKS = this.getDateTime();
		String query =  "select pk_seq, scheme  " +
						"from TIEUCHITHUONGTL where  '" + ngayKS + "' >= THANG and '" + ngayKS + "' <= nam and trangthai = '1' " +
							   "and PK_SEQ in ( select thuongtl_fk from TIEUCHITHUONGTL_NPP where npp_fk = '" + this.nppId + "' ) ";
					   //"and pk_seq not in ( select TIEUCHITL_FK from DANGKYKM_TICHLUY where npp_fk = '" + this.nppId + "' and trangthai != 2 ) ";
		
		if(this.Id != null)
		{
			if( this.Id.trim().length() > 0 )
			{
				query += " union " +
						 "select pk_seq, scheme from TIEUCHITHUONGTL where pk_seq = ( select TIEUCHITL_FK from DANGKYKM_TICHLUY where pk_seq = '" + this.Id + "' ) ";
		
			}
		}
		
		System.out.println("__LAY SCHEME: " + query);
		this.ctkmRs = db.get(query);
		
		if(this.ctkmId.trim().length() > 0)
		{
			query = "select thang, nam from TIEUCHITHUONGTL where pk_seq = '" + this.ctkmId + "' ";
			
			ResultSet rsTL = db.get(query);
			try 
			{
				if(rsTL.next())
				{
					this.tungay = rsTL.getString("thang");
					this.denngay = rsTL.getString("nam");
				}
				rsTL.close();
			} 
			catch (Exception e) {}
			
			//Get NVBH
			query = "select PK_SEQ, SMARTID, TEN, isnull(MANHANVIEN, '') as maNV from DAIDIENKINHDOANH where NPP_FK = '" + this.nppId + "' and TRANGTHAI = '1'";
			this.nvbhRS = db.get(query);
			
			query = "select PK_SEQ, SMARTID, TEN, DIACHI from KHACHHANG where NPP_FK = '" + this.nppId + "' and TRANGTHAI = '1'";
			if(this.nvbhIds.trim().length() > 0)
			{
				query += " and pk_seq in ( select KHACHHANG_FK from KHACHHANG_TUYENBH where TBH_FK in ( select PK_SEQ from TUYENBANHANG where DDKD_FK = '" + this.nvbhIds + "' ) ) ";
			}
			
			query += " order by SMARTID asc ";
			
			this.khRs = db.get(query);
			
		}
		
		
	}
	
	public void setCtkmRs(ResultSet ctkmIds) {
		
		this.ctkmRs = ctkmIds;
	}
	
	public ResultSet getCtkmRs() {
		
		return this.ctkmRs;
	}
	
	public void setCtkmId(String ctkmId) {
		
		this.ctkmId = ctkmId;
	}
	
	public ResultSet getNvBanhang() {
		
		return this.nvbhRS;
	}
	
	public void setNvBanhang(ResultSet nvbanhang) {
		
		this.nvbhRS = nvbanhang;
	}
	
	public void setKhList(ResultSet KhList) {
		
		this.khRs = KhList;
	}
	
	public ResultSet getKhList() {
		
		return this.khRs;
	}
	
	public String getMucdangky() {
		
		return this.mucdangky;
	}

	
	public void setMucdangky(String mucdangky) {
		
		this.mucdangky = mucdangky;
	}
	
	public String[] getKhMa() {
		
		return this.khMa;
	}
	
	public void setKhMa(String[] khMa) {
		
		this.khMa = khMa;
	}
	
	public String[] getKhTen() {
		
		return this.khTen;
	}
	
	public void setKhTen(String[] khTen) {
		
		this.khTen = khTen;
	}
	
	public String[] getKhDiachi() {
		
		return this.khDiachi;
	}
	
	public void setKhDiachi(String[] khDiachi) {
		
		this.khDiachi = khDiachi;
	}
	
	public String[] getKhTungay() {
		
		return this.khTungay;
	}
	
	public void setKhTungay(String[] khTungay) {
		
		this.khTungay = khTungay;
	}
	
	public String[] getKhDenngay() {
		
		return this.khDenngay;
	}
	
	public void setKhDenngay(String[] khDenngay) {
		
		this.khDenngay = khDenngay;
	}
	
	public String[] getKhDoanhso() {
		
		return this.khDoanhso;
	}
	
	public void setKhDoanhso(String[] khDoanhso) {
		
		this.khDoanhso = khDoanhso;
	}
	
	public String[] getKhSohopdong() {

		return this.khSohopdong;
	}

	public void setKhSohopdong(String[] khSohopdong) {
		
		this.khSohopdong = khSohopdong;
	}
	
	
}
