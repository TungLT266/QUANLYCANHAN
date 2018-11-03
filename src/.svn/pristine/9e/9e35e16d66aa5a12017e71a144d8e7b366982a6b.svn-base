package geso.traphaco.distributor.util;

import geso.traphaco.center.beans.Router.IDRouter;
import geso.traphaco.center.beans.lotrinh.ILoTrinh;
import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTLList;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.distributor.beans.reports.imp.Reports;
import geso.traphaco.distributor.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.lang.Math;
import java.net.URLDecoder;

import javax.servlet.http.HttpSession;

public class Utility implements Serializable
{
	private static final long serialVersionUID = 1L;
	String nppId="";
	String nppTen="";
	String sitecode="";
	String dangnhap="";
	String loaiNpp="";
	private String khoSAP;
	
	public static final int THEM = 0;
	public static final int XOA = 1;
	public static final int SUA = 2;
	public static final int XEM = 3;
	public static final int CHOT = 4;
	public static final int HUYCHOT = 5;
	public static final int CHUYEN = 6;
	public static final int SMS = 7;
	public static final int FAX = 8;
	public static final int HIENTHIALL = 9;
	public static final int XUATEXCEL = 10;
	
	public HashMap  <String , String > hmSearch  = new HashMap<String, String>();
	
	public String getLoaiNpp()
    {
		return loaiNpp;
    }

	public void setLoaiNpp(String loaiNpp)
    {
		this.loaiNpp = loaiNpp;
    }

	public String ChietKhau(String nam)
	{
		if(nam.trim().equals("2013"))
			return "0.97";
			else return  "0.965";
	}
	
	public String Check_CK_DaHuong(String hoadonId,String userId, dbutils db)
	{
		String		query=	 
			"	select  "+  
			"	STUFF      "+  
			"	(     "+
			"		(    "+
			"				select DISTINCT TOP 100 PERCENT ' , ' +  hd.SOHOADON "+  
			"				from HOADON_CHIETKHAU ck inner join HOADON hd on hd.PK_SEQ=ck.hoadon_fk "+  
			"				where  hd.LOAIHOADON=0 and DATEPART(MONTH,hd.ngayxuathd)=DATEPART(MONTH,a.ngayxuathd)+1 "+  
			"				and ck.tichluyQUY=0 and ROUND(ck.chietkhau,0) >0    "+
			"				and hd.KHACHHANG_FK=a.KHACHHANG_FK and hd.TRANGTHAI not in (3,5) "+ 
			"			ORDER BY ' , ' + hd.SOHOADON   "+
			"			FOR XML PATH('')       "+
			"		 ), 1, 2, ' '    "+
			"	) + ' '  as SoHoaDon , "+
			"	STUFF      "+  
					"	(     "+
					"		(    "+
					"				select DISTINCT TOP 100 PERCENT ' , ' +  cast(hd.pk_seq as varchar(20) ) "+  
					"				from HOADON_CHIETKHAU ck inner join HOADON hd on hd.PK_SEQ=ck.hoadon_fk "+  
					"				where  hd.LOAIHOADON=0 and DATEPART(MONTH,hd.ngayxuathd)=DATEPART(MONTH,a.ngayxuathd)+1 "+  
					"				and ck.tichluyQUY=0 and ROUND(ck.chietkhau,0) >0    "+
					"				and hd.KHACHHANG_FK=a.KHACHHANG_FK and hd.TRANGTHAI not in (3,5) "+ 
					"			ORDER BY ' , ' + cast(hd.pk_seq as varchar(20) )   "+
					"			FOR XML PATH('')       "+
					"		 ), 1, 2, ' '    "+
					"	) + ' '  as hoadonck "+
			" from HoaDon a  "+
			" where pk_Seq='"+hoadonId+"' ";
		System.out.println("[Check_CK_DaHuong]"+query);
		String msg = "";
		String sohoadon="";
		String hoadonck="";
		ResultSet rs = db.get(query);
		try
	    {
		    while(rs.next())
		    {
		    	sohoadon += rs.getString("SoHoaDon")==null?"":rs.getString("SoHoaDon") + " ,";
		    	hoadonck += rs.getString("hoadonck")==null?"":rs.getString("hoadonck") + " ,";
			    System.out.println("[Check_CK_DaHuong]"+sohoadon);
		    }
		    rs.close();
		    if(sohoadon.length()>0)
		    {
		    	msg= "DOANH SỐ TRONG BIÊN BIỂN BÙ TRỪ CHIẾT KHẤU THÁNG SẼ THAY ĐỔI NẾU BẠN XÓA/ HỦY HÓA ĐƠN NÀY!";
		    	query=
		    			" INSERT INTO HuyHoaDon(HoaDonHuy_Fk,SoHoaDon,HoaDon_FK,GhiChu,NguoiSua) " +
		    			" select '"+hoadonId+"','"+sohoadon+"','"+hoadonck+"',N'"+msg+"','"+userId+"'  ";
		    			db.update(query);
		    }
	    } 
		catch (Exception e)
	    {
		    e.printStackTrace();
		    return "Lỗi phát sinh khi check ck tháng đã hưởng !;";
	    }
		return msg;
	}
	
	public String getUrl(String servlet,String parametes)
	{
		String query=
		"	select   B.PK_SEQ as DanhMuc_FK,a.PK_SEQ as pk_Seq  , c.ten + ' > '+  b.ten as TENDANHMUC, a.ten , a.servlet,  "+  
		"			a.parameters, c.sott as stt1, b.sott as stt2, a.sott           "+
		"	from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq        "+       
		"	inner join ungdung c on b.ungdung_fk = c.pk_seq               "+
		"	where a.level = '3' and b.level = '2'  and a.TrangThai=1 and a.servlet='"+servlet+"' and a.parameters='"+parametes+"' "+           
		"	union all            "+
		"	select c.PK_SEQ as DanhMuc_FK ,a.PK_SEQ ,  c.ten   as TENDANHMUC, a.ten, a.servlet, a.parameters, c.sott as stt1, a.sott as stt2, a.sott "+ 
		"	from ungdung a  inner join ungdung c on a.ungdung_fk = c.pk_seq               "+
		"	where a.level = '3' and c.level = '1'    and a.TrangThai=1  and a.servlet='"+servlet+"' and a.parameters='"+parametes+"'    "+         
		"	order by stt1 asc, stt2 asc, sott asc  " ;
		dbutils db = new dbutils();
		String url="";
		ResultSet rs =db.get(query);
		try
    {
	    while(rs.next())
	    {
	    	url=rs.getString("TENDANHMUC")+ " > " + rs.getString("ten");
	    }
	    if(rs!=null)rs.close();
    } catch (SQLException e)
    {
	    e.printStackTrace();
    }
		finally
    {
    	db.shutDown();
    }
		return url;
	}
	
	public String getIdNhapp(String userId, dbutils db) 
	{
		String sql=
	"			select nv.dangnhap,npp.LoaiNPP, npp.khosap, npp.pk_seq,npp.sitecode,npp.ten  "+ 
	"			from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode=sitecode   "+
	"			where nv.pk_seq='"+userId+"' and nv.trangthai='1'  and nv.PHANLOAI=1 and npp.isKHACHHANG = '0'  "+ 
	"			union all  "+
	"			select nv.dangnhap,npp.LoaiNPP, npp.khosap, npp.pk_seq,npp.sitecode,gs.ten  "+ 
	"			from nhanvien nv inner join GIAMSATBANHANG gs on nv.GSBH_FK=gs.PK_SEQ  "+
	"				inner join NHAPHANPHOI npp on npp.SITECODE=nv.CONVSITECODE  "+
	"			where nv.pk_seq='"+userId+"' and nv.trangthai='1'  and nv.PHANLOAI=2 and gs.TRANGTHAI=1  "+
	"			and npp.TRANGTHAI=1 ";
		
		//System.out.println("Get Thong Tin NPP :"+sql);
		ResultSet rs= db.get(sql);
		try{
			if(rs.next()){
			 this.nppId=rs.getString("pk_seq");
			 this.nppTen= rs.getString("ten");
			 this.sitecode=rs.getString("sitecode");
			 this.dangnhap = rs.getString("dangnhap");
			 this.setKhoSAP(rs.getString("khosap"));
			 this.loaiNpp = rs.getString("LoaiNPP")==null?"": rs.getString("LoaiNPP");
			 rs.close();
			}
		}catch(Exception er){
			
		}
		return this.nppId;
	}
	
	public String getIdNhapp(String userId) 
	{
		dbutils db = new dbutils();
		String sql =
						"			select nv.dangnhap,npp.LoaiNPP, npp.khosap, npp.pk_seq,npp.sitecode,npp.ten,npp.tructhuoc_fk  "+ 
						"			from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode=sitecode   "+
						"			where nv.pk_seq='"+userId+"' and nv.trangthai='1'  and nv.PHANLOAI=1 and npp.isKHACHHANG = '0'  "+ 
						"			union all  "+
						"			select nv.dangnhap,npp.LoaiNPP, npp.khosap, npp.pk_seq,npp.sitecode,gs.ten,npp.tructhuoc_fk  "+ 
						"			from nhanvien nv inner join GIAMSATBANHANG gs on nv.GSBH_FK=gs.PK_SEQ  "+
						"				inner join NHAPHANPHOI npp on npp.SITECODE=nv.CONVSITECODE  "+
						"			where nv.pk_seq='"+userId+"' and nv.trangthai='1'  and nv.PHANLOAI=2 and gs.TRANGTHAI=1 and npp.isKHACHHANG = '0'  "+
						"			and npp.TRANGTHAI=1 ";
		
		System.out.println("____ID_"+sql);
		
		ResultSet rs = db.get(sql);
		try
		{
			while(rs.next())
			{
				 this.nppId=rs.getString("pk_seq");
				 this.nppTen= rs.getString("ten");
				 this.sitecode=rs.getString("tructhuoc_fk"); //Dùng biến sitecode cũ ICP để lưu lại loại NPP (CN1, CN2, đối tác, quầy...)
				 this.dangnhap = rs.getString("dangnhap");
				 this.setKhoSAP(rs.getString("khosap"));
				 this.loaiNpp = rs.getString("loaiNPP")==null?"": rs.getString("loaiNPP");
			}
			if(rs!=null)rs.close();
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
		return this.nppId;
	}
	
	public String getTenNhaPP(){
		return this.nppTen;
	}
	public String getSitecode(){
		return this.sitecode;
	}
	
	public String getDangNhap(){
		return this.dangnhap;
	}
	
	public int[] GetquyenNew(String servlet, String parameters, String userId)
	{
		int[] quyen = new int[11];
		int them =0;
		int xoa=0;
		int sua=0;
		int xem=0;
		int chot=0;
		int huychot=0;
		int chuyen=0;
		
		int sms=0;
		int fax=0;
		int hienthiALL=0;
		int xuatexcel=0;
		
		String query =	
				/*		"	select 1 as Them,1 as Xoa,1 as Sua,1 AS Xem,1 as Chot,1 as HuyChot from nhanvien where pk_Seq='"+userId+"'  ";*/
				/*	"	union  select 1 as Them,1 as Xoa,1 as Sua,1 AS Xem,1 as Chot,1 as HuyChot from nhanvien WHERE  PK_SEQ='"+userId+"' and IsAdmin=1  "+*/ 		
				" 	SELECT ISNULL(THEM,0) AS THEM,ISNULL(XOA,0) AS XOA,ISNULL(SUA,0) AS SUA,ISNULL(XEM,0) AS XEM,ISNULL(CHOT,0) AS CHOT, "+
				"	ISNULL(HUYCHOT,'0') AS HUYCHOT, ISNULL(CHUYEN,'0') AS CHUYEN, ISNULL(SMS,'0') AS SMS, ISNULL(FAX,'0') AS FAX, ISNULL(HIENTHIALL,'0') AS HIENTHIALL, ISNULL(xuatexcel,'0') AS xuatexcel "+
				"	FROM NHOMQUYEN  A INNER JOIN PHANQUYEN B ON A.DMQ_FK = B.DMQ_FK  "+ 
				"	INNER JOIN UNGDUNG UD ON UD.PK_SEQ=A.UNGDUNG_FK  "+
				" WHERE B.NHANVIEN_FK='"+userId+"' AND UD.SERVLET='"+servlet+"'  ";
		if( parameters.trim().length() > 0 )
			query += " AND UD.PARAMETERS='" + parameters + "' ";
		System.out.println("Lay quyen: " + query);

		dbutils db = new  dbutils();
		ResultSet rscheck= db.get(query);
		
//		String quyenSTR = "";
		try
		{
			while(rscheck.next())
			{
				//if(rscheck.getInt("THEM")!=0)
				them=rscheck.getInt("THEM");

				//if(rscheck.getInt("XOA")!=0)
				xoa=rscheck.getInt("XOA");

				//if(rscheck.getInt("SUA")!=0)
				sua=rscheck.getInt("SUA");

				//if(rscheck.getInt("XEM")!=0)
				xem=rscheck.getInt("XEM");

				//if(rscheck.getInt("CHOT")!=0)
				chot=rscheck.getInt("CHOT");

				//if(rscheck.getInt("HuyChot")!=0)
				huychot=rscheck.getInt("HuyChot");

				chuyen=rscheck.getInt("Chuyen");
				
				sms=rscheck.getInt("SMS");
				fax=rscheck.getInt("FAX");
				hienthiALL=rscheck.getInt("HIENTHIALL");
				xuatexcel=rscheck.getInt("xuatexcel");

			}
			rscheck.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		
		quyen[THEM]=them;
		quyen[XOA]=xoa;
		quyen[SUA]=sua;
		quyen[XEM]=xem;
		quyen[CHOT]=chot;
		quyen[HUYCHOT]=huychot;
		quyen[CHUYEN]=chuyen;
		
		quyen[SMS]=sms;
		quyen[FAX]=fax;
		quyen[HIENTHIALL]=hienthiALL;
		quyen[XUATEXCEL]=xuatexcel;

		return quyen;
		
	}
	
	public int[] Getquyen(String servlet, String parameters, String userId)
	{
		int[] quyen = new int[6];
		int them =0;
		int xoa=0;
		int sua=0;
		int xem=0;
		int chot=0;
		int huychot=0;
		String query =	
				/*		"	select 1 as Them,1 as Xoa,1 as Sua,1 AS Xem,1 as Chot,1 as HuyChot from nhanvien where pk_Seq='"+userId+"'  ";*/
				/*	"	union  select 1 as Them,1 as Xoa,1 as Sua,1 AS Xem,1 as Chot,1 as HuyChot from nhanvien WHERE  PK_SEQ='"+userId+"' and IsAdmin=1  "+*/ 		
				" 	SELECT ISNULL(THEM,0) AS THEM,ISNULL(XOA,0) AS XOA,ISNULL(SUA,0) AS SUA,ISNULL(XEM,0) AS XEM,ISNULL(CHOT,0) AS CHOT, "+
				"	ISNULL(HUYCHOT,'0') AS HUYCHOT, ISNULL(CHUYEN,'0') AS CHUYEN, ISNULL(SMS,'0') AS SMS, ISNULL(FAX,'0') AS FAX, ISNULL(HIENTHIALL,'0') AS HIENTHIALL "+
				"	FROM NHOMQUYEN  A INNER JOIN PHANQUYEN B ON A.DMQ_FK = B.DMQ_FK  "+ 
				"	INNER JOIN UNGDUNG UD ON UD.PK_SEQ=A.UNGDUNG_FK  "+
				" WHERE B.NHANVIEN_FK='"+userId+"' AND UD.SERVLET='"+servlet+"'  ";
		if( parameters.trim().length() > 0 )
			query += " AND UD.PARAMETERS='" + parameters + "' ";
		System.out.println("Lay quyen: " + query);

		dbutils db = new  dbutils();
		ResultSet rscheck= db.get(query);
		
//		String quyenSTR = "";
		try
		{
			while(rscheck.next())
			{
				//if(rscheck.getInt("THEM")!=0)
				them=rscheck.getInt("THEM");

				//if(rscheck.getInt("XOA")!=0)
				xoa=rscheck.getInt("XOA");

				//if(rscheck.getInt("SUA")!=0)
				sua=rscheck.getInt("SUA");

				//if(rscheck.getInt("XEM")!=0)
				xem=rscheck.getInt("XEM");

				//if(rscheck.getInt("CHOT")!=0)
				chot=rscheck.getInt("CHOT");

				//if(rscheck.getInt("HuyChot")!=0)
				huychot=rscheck.getInt("HuyChot");
				
			}
			rscheck.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		
		quyen[THEM]=them;
		quyen[XOA]=xoa;
		quyen[SUA]=sua;
		quyen[XEM]=xem;
		quyen[CHOT]=chot;
		quyen[HUYCHOT]=huychot;

		return quyen;
		
	}
	
	
	public boolean isValidDate(String inDate) {

	    if (inDate == null)
	      return false;

	    //set the format to use as a constructor argument
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	    
	    if (inDate.trim().length() != dateFormat.toPattern().length())
	      return false;

	    dateFormat.setLenient(false);
	    
	    try {
	      //parse the inDate parameter
	      dateFormat.parse(inDate.trim());
	    }
	    catch (ParseException pe) {
	      return false;
	    }
	    return true;
	  }
	
	public String getUserId(String querystring){
	    String userId;
	    String tmp;
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		tmp = querystring.split("&")[0];
	    	}else{
	    		tmp = querystring;
	    	}
	    	userId = tmp.split("=")[1];
		}else{
			userId = "";
		}
	    return userId;
	}
	public String getAction(String querystring){
	    String action;
	    String tmp;
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		tmp = querystring.split("&")[1];
	    		action = tmp.split("=")[0];
	    	}else{
	    		action = "";
	    	}
		}else{
			action = "";
		}
	    return action;
		
	}
	
	public String getParameter(String querystring, String action){
	    
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		String [] ls = querystring.split("&");
	    		if(ls != null){
	    			for(String l : ls){
	    				if(l.contains(action))
	    					return l.split("=")[1];
	    			}
	    		}
	    	}
		}
	    return "";
		
	}

	public String getId(String querystring){
	    String id;
	    String tmp;
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		tmp = querystring.split("&")[1];
	    		id = tmp.split("=")[1];
	    	}else{
	    		id = "";
	    	}
		}else{
			id = "";
		}
	    return id;
		
	}

	public Hashtable<Integer, String>  ArraystringToHashtable(String[] s){
		Hashtable<Integer, String> h = new Hashtable<Integer, String>();
		if(s != null){
			int size = s.length;
			int m = 0;
			while(m < size){
				h.put(new Integer(m), s[m]) ;
				m++;
			}
		}else{
			h.put(new Integer(0), "null");
		}
		return h;
	}

	public String[]  ResultSetToArrayString(ResultSet rs){
		String[] s = new String[10];
		try{
			int m = rs.getFetchSize();
			s = new String[m+1];		 	
			while(rs.next()){
				s[1] = rs.getString(1);
			}
		}catch(Exception e){}
		return s;
	}

	// tra ve nhung thanh phan cua s1 khong nam trong s2
	public String[] compareArrayString(String[] s1, String[] s2)
	{
		int i = s1.length;
		int j = s2.length;	
		
		String[] s = new String[i];
		int k = 0;
		for (int m = 0; m < i; m++){
			boolean result = true;
			for (int n = 0; n < j; n++){
				if (s1[m].equals(s2[n])){
					result = false;
				}
				if (result){
					s[k++]=s1[m];
				}
			}
		}
		return s;
	}
	
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public static String getCurrentDay() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public static String getFirstDayOfMonth(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate;
		Calendar c = null;
		try {
			convertedDate = dateFormat.parse(date);
			c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Date lastDayOfMonth = c.getTime();  
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		return sdf.format(lastDayOfMonth);
	}
	
	public static String getLastDayOfMonth(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate;
		Calendar c = null;
		try {
			convertedDate = dateFormat.parse(date);
			c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Date lastDayOfMonth = c.getTime();  
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		return sdf.format(lastDayOfMonth);
	}
	
	public	boolean isNumeric(String input)
	{ 
		boolean result = true;
		char[] all = input.toCharArray();
		
		for(int i = 0; i < all.length;i++) {
		   if(!(Character.isDigit(all[i]))) {
			   result = false;
		   }
		}
		return result;
	}
	
	public boolean checkHopLe(String userId)
	{
		dbutils db = new dbutils();
		String query = "select npp.pk_seq from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode = sitecode  where nv.pk_seq = '" + userId + "'";
		ResultSet rs = db.get(query);
		String nppId = "";
		int dakhoaso30 = 0;
		int dacodctk01 = 0;
		try 
		{
			if(rs.next())
			{
				nppId = rs.getString("pk_seq");
				rs.close();
			}
			query = "select count(*) as dakhoaso from khoasongay where ngayks = '2012-04-30' and npp_fk = '" + nppId + "'";
			rs = db.get(query);
			
			if(rs.next())
			{
				dakhoaso30 = rs.getInt("dakhoaso");
				rs.close();
			}
			
			if(dakhoaso30 == 0)  //chua khoa so ngay nay
				return true;
			
			query = "select count(npp_fk) as sodong from dieuchinhtonkho where npp_fk = '" + nppId + "' and trangthai = '1' and ngaydc = '2012-05-01'";
			rs = db.get(query);
			
			if(rs.next())
			{
				dacodctk01 = rs.getInt("sodong");
				rs.close();
			}
			
			if(dacodctk01 == 0)
				return false;
				
		} 
		catch(Exception e) { return false; }
		return true;
	}
	
	public String ngaykhoaso(String nhaphanphoi)
	{   
		String ngay = "";
		dbutils db = new dbutils();
		String sql ="select isnull(max(ngayks), '') as ngay from khoasongay where npp_fk ='"+ nhaphanphoi+"'";
		ResultSet rs = db.get(sql);
		try
		{
			if(rs != null)
			{
		        rs.next();
				ngay = rs.getString("ngay");	
			}
			db.shutDown();
		}
		catch(Exception e){ db.shutDown(); }
		
		return ngay;
	}
	
	public void setKhoSAP(String khoSAP) {
		this.khoSAP = khoSAP;
	}
	public String getKhoSAP() {
		return khoSAP;
	}
	
	//chuyen tieng viet khong dau
	
	private static char[] SPECIAL_CHARACTERS = { ' ', '!', '"', '#', '$', '%',
		   '*', '+', ',', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^',
		   '`', '|', '~', 'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò',
		   'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê',
		   'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ', 'đ',
		   'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ',
		   'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ',
		   'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế',
		   'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị',
		   'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ', 'Ộ',
		   'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ',
		   'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự', 'Ý', 'Ỳ', 'Ỵ', 'ỳ', 'ỵ', 'ý'};

	 private static char[] REPLACEMENTS = { '-', '\0', '\0', '\0', '\0', '\0',
		   '\0', '_', '\0', '_', '\0', '\0', '\0', '\0', '\0', '\0', '_',
		   '\0', '\0', '\0', '\0', '\0', 'A', 'A', 'A', 'A', 'E', 'E', 'E',
		   'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a',
		   'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A',
		   'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a',
		   'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
		   'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e',
		   'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I',
		   'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
		   'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
		   'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
		   'U', 'u', 'Y', 'Y', 'Y', 'y', 'y', 'y'};
		 
	 public String replaceAEIOU(String s) 
	 {
		  int maxLength = Math.min(s.length(), 236);
		  char[] buffer = new char[maxLength];
		  int n = 0;
		  for (int i = 0; i < maxLength; i++) 
		  {
			  char ch = s.charAt(i);
			  int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
			  if (index >= 0) 
			  {
				  buffer[n] = REPLACEMENTS[index];
			  } 
			  else 
			  {
				  buffer[n] = ch;
			  }
			   // skip not printable characters
			   if (buffer[n] > 31) {
			    n++;
			   }
		  }
		  
		  // skip trailing slashes
		  while (n > 0 && buffer[n - 1] == '/') 
		  {
			  n--;
		  }
		  
		  String kq = String.valueOf(buffer, 0, n);
		  kq = kq.replaceAll("---", "-");
		  kq = kq.replaceAll("--", "-");
		  kq = kq.replaceAll("--", "-");
		  
		  return kq;
	 }
	  
	public String antiSQLInspection(String param){
		String tmp = param;
		//System.out.println("Chuoi moi:" + tmp);
		
		String[] keywords = {" or ","delete","insert","update","create", "alter","drop","=","--", "select","\\(","\\)"};

		boolean trbl = false;
		
		if(tmp != null){
			tmp = tmp.toLowerCase();
			for (int i = 0; i < keywords.length; i++){
				if(tmp.contains(keywords[i])){
					tmp = tmp.replaceAll(keywords[i], "--");
					trbl = true;
					break;
				}
				////System.out.println("Chuoi moi:" + tmp);
			}
			
		}
		
		//System.out.println("Chuoi moi:" + tmp);
		if(trbl == true)
			return tmp;
		else return param;
	}
	
	public String getTieuDe(String table,String column,String id ,geso.traphaco.distributor.db.sql.dbutils db)
	{
		String query=" select  dbo.ftBoDau("+column+")  from "+table+" where pk_seq in ("+id+")";
		ResultSet rs= db.get(query);
		String tieude="";
		
		try
		{
			while(rs.next())
			{
			  tieude +=java.net.URLDecoder.decode(rs.getString(1).replaceAll("%(?![0-9a-fA-F]{2})", "%25").replace(" ", "-"), "UTF-8");
			}
			rs.close();
		}catch(Exception er)
		{
			er.printStackTrace();
		}		
		return tieude;
	}
	 
	public static String replacer(StringBuffer outBuffer) 
	{
		String data = outBuffer.toString();
		try {
			data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			data = data.replaceAll("+", "%2B");
			data = URLDecoder.decode(data, "utf-8");
		 } catch (Exception e) {
         e.printStackTrace();
		 }
		 return data;
    }
	
	public String setTieuDe(IStockintransit obj )
	{
		String tieude="";
	    dbutils db = new dbutils();
	   
	    if(obj.getkenhId()!=null && obj.getkenhId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KenhBanHang","Ten", obj.getkenhId(), db )+ "_"; 
	    }
	    if(obj.getdvkdId()!=null && obj.getdvkdId().length()>0)
	    {
	    	tieude += this.getTieuDe( "DonViKinhDoanh","DienGiai", obj.getdvkdId(),db ) + "_" ;
	    }
	    
	    if(obj.getvungId()!=null && obj.getvungId().length()>0)
	    {
	    	tieude += this.getTieuDe( "Vung","Ten", obj.getvungId(),db ) + "_" ;
	    }
	    
	    if(obj.getkhuvucId()!=null && obj.getkhuvucId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getkhuvucId(),db ) + "_" ;
	    }
	    
	    if(obj.getnppId()!=null && obj.getnppId().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getnppId(), db ) + "_" ;
	    }
	    
	    if(obj.getASMId()!=null && obj.getASMId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "ASM", "Ten", obj.getASMId(), db )+ "_"; 
	    }
	    if(obj.getBMId()!=null && obj.getBMId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "BM","Ten", obj.getBMId(), db )+ "_"; 
	    }
	    
	    if(obj.getgsbhId()!=null && obj.getgsbhId().length()>0)
	    {
	    	tieude += this.getTieuDe( "GiamSatBanHang", "Ten", obj.getgsbhId(),db ) + "_" ;
	    }
	    if(obj.getDdkd()!=null && obj.getDdkd().length()>0)
	    {
	    	tieude += this.getTieuDe( "DaiDienKinhDoanh", "Ten", obj.getDdkd(),db ) + "_" ;
	    }
	    if(obj.getnhanhangId()!=null && obj.getnhanhangId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "NhanHang","Ten", obj.getnhanhangId(),db ) + "_" ;
	    }
	    if(obj.getchungloaiId()!=null && obj.getchungloaiId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "ChungLoai","Ten", obj.getchungloaiId(),db ) + "_" ;
	    }
	    if(obj.getNspId()!=null && obj.getNspId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "NhomSanPham","Ten", obj.getNspId(),db ) + "_" ;
	    }
	    if(obj.getsanphamId()!=null && obj.getsanphamId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "SanPham","Ten", obj.getsanphamId(),db ) + "_" ;
	    }
	    if(obj.getctkmtlId()!=null && obj.getctkmtlId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "CTKHUYENMAI","DienGiai", obj.getctkmtlId(), db )+ "_"; 
	    }
	    if(obj.getPrograms()!=null && obj.getPrograms().length()>0)
	    {
	    	tieude +=this.getTieuDe( "CTKHUYENMAI","DienGiai", obj.getPrograms(), db )+ "_"; 
	    }
	    if(obj.getpromotion()!=null && obj.getpromotion().length()>0)
	    {
	    	tieude +=this.getTieuDe( "CTKHUYENMAI","DienGiai", obj.getpromotion(), db )+ "_"; 
	    }
	    
	    if(obj.getdvdlId()!=null && obj.getdvdlId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "DonViDoLuong","DonVi", obj.getdvdlId(), db )+ "_"; 
	    }
    	 if(obj.gettungay()!=null && obj.gettungay().length()>0)
	    {
	    	tieude += obj.gettungay() + "_";
	    }
    	 if(obj.getdenngay()!=null && obj.getdenngay().length()>0)
	    {
	    	tieude += obj.getdenngay() + "_";
	    }
    	 if(obj.getFromMonth()!=null && obj.getFromMonth().length()>0)
	    {
	    	tieude += obj.getFromMonth() + "_";
	    }
    	 if(obj.getFromYear()!=null && obj.getFromYear().length()>0)
	    {
	    	tieude += obj.getFromYear() + "_";
	    }
    	 if(obj.getToMonth()!=null && obj.getToMonth().length()>0)
	    {
	    	tieude += obj.getToMonth() + "_";
	    }
    	 if(obj.getToYear()!=null && obj.getToYear().length()>0)
	    {
	    	tieude += obj.getToYear() + "_";
	    }
	 
	    db.shutDown();
	    return tieude;
	}
	
	public String setTieuDe(Reports obj )
	{
		String tieude="";
	    dbutils db = new dbutils();
	   
	    if(obj.getKenhId()!=null && obj.getKenhId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "KenhBanHang","Ten", obj.getKenhId(), db )+ "_"; 
	    }
	    
	    if(obj.getVungId()!=null && obj.getVungId().length()>0)
	    {
	    	tieude += this.getTieuDe( "Vung","Ten", obj.getVungId(),db ) + "_" ;
	    }
	    
	    if(obj.getKhuVucId()!=null && obj.getKhuVucId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getKhuVucId(),db ) + "_" ;
	    }
	    
	    if(obj.getNppId()!=null && obj.getNppId().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getNppId(), db ) + "_" ;
	    }
	    
	    if(obj.getcttbid()!=null && obj.getcttbid().length()>0)
	    {
	    	tieude +=this.getTieuDe( "CTTRUNGBAY","DienGiai", obj.getcttbid(), db )+ " "; 
	    }
	    if(obj.getNhanHang()!=null && obj.getNhanHang().length()>0)
	    {
	    	tieude +=this.getTieuDe( "NhanHang","Ten", obj.getNhanHang(), db )+ "_"; 
	    }
	    
	    if(obj.getSanPhamId()!=null && obj.getSanPhamId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "SanPham","Ten", obj.getSanPhamId(),db ) + "_" ;
	    }
	    if(obj.getSKU()!=null && obj.getSKU().length()>0 )
	    {
	    	tieude += this.getTieuDe( "SanPham","Ten", obj.getSKU(),db ) + "_" ;
	    }		    
    	 if(obj.getTuNgay()!=null && obj.getTuNgay().length()>0)
	    {
	    	tieude += obj.getTuNgay() + "_";
	    }
    	 
	    db.shutDown();
	    return tieude;
	}
	
	public String setTieuDe(ITieuchithuongTLList obj )
	{
		String tieude="_";
	    dbutils db = new dbutils();
	   
	    if(obj.getVungId()!=null && obj.getVungId().length()>0)
	    {
	    	tieude += this.getTieuDe( "Vung","Ten", obj.getVungId(),db ) + "_" ;
	    }
	    
	    if(obj.getKvId()!=null && obj.getKvId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getKvId(),db ) + "_" ;
	    }
	    
	    if(obj.getNppIds()!=null && obj.getNppIds().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getNppIds(), db ) + "_" ;
	    }
	    if(obj.getSchemeIds()!=null && obj.getSchemeIds().length()>0)
	    {
	    	tieude += this.getTieuDe( "CTKhuyenMai","DienGiai", obj.getSchemeIds(), db ) + "_" ;
	    }
	    if(obj.getNam()!=null && obj.getNam().length()>0)
	    {
	    	tieude += obj.getNam() +"_";
	    }
	    if(obj.getThang()!=null && obj.getThang().length()>0)
	    {
	    	tieude += obj.getThang() +"_";
	    }

	    if(obj.getTungay()!=null && obj.getTungay().length()>0)
	    {
	    	tieude += obj.getTungay() +"_";
	    }
	    if(obj.getDenngay()!=null && obj.getDenngay().length()>0)
	    {
	    	tieude += obj.getDenngay() +"_";
	    }
	    db.shutDown();
	    return tieude;
	}
	
	public String setTieuDe(IDRouter obj)
	{
		 String tieude="_";
		    dbutils db = new dbutils();
	     if(obj.getnppId()!=null && obj.getnppId().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getnppId(),db ) + "_" ;
	    }
	    
	    if(obj.getkhuvucId()!=null && obj.getkhuvucId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getkhuvucId(),db ) + "_" ;
	    }
	    
	    if(obj.getddkdId()!=null && obj.getddkdId().length()>0)
	    {
	    	tieude += this.getTieuDe( "DaiDienKinhDonh","Ten", obj.getddkdId(), db ) + "_" ;
	    }
		 db.shutDown();
		return tieude;
	}
	
	public String setTieuDe(ILoTrinh obj) 
	{
		String tieude="_";
		dbutils db = new dbutils();
	    if(obj.getnppId()!=null && obj.getnppId().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getnppId(),db ) + "_" ;
	    }
	    
	    if(obj.getkhuvucId()!=null && obj.getkhuvucId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getkhuvucId(),db ) + "_" ;
	    }
	    
	    if(obj.getddkdId()!=null && obj.getddkdId().length()>0)
	    {
	    	tieude += this.getTieuDe( "DaiDienKinhDonh","Ten", obj.getddkdId(), db ) + "_" ;
	    }
		 db.shutDown();
		return tieude;
	}

	
	//CAP NHAT TONG GIA TRI DON HANG
	public void Update_GiaTri_DonHang(String dhId, dbutils db)
	{
		String query =  
						"update DH set " +
						"		DH.tongtienTRUOCCHIETKHAU = TGT.tongTIEN, " +
						"		DH.tongtienCHIETKHAU = TGT.tongTL, " +
						"		DH.TONGGIATRI = case TGT.thanhtoan when 1 then TGT.tongTIEN - TGT.tongTL else TGT.tongTIEN end, " +
						"		DH.LOAIKHACHHANG = KH.xuatkhau	  " +
						"from DONHANG DH inner join KHACHHANG KH on DH.khachhang_fk = KH.pk_seq " +
						"inner join " +
						"( " +
						"	select d.thanhtoanQUY as thanhtoan, a.pk_seq as donhangID,  " +
						" (isnull( (          "+
						"		select sum( round ( ( ( round ( (soluong * giamua), 0  ) - chietkhau ) * ( 1 + thueVAT / 100 ) ), 0 ) )  as tienBvat       "+   
						"		from donhang_sanpham           "+
						"		where donhang_fk = a.pk_seq  and ( soluong * giamua ) != 0 ) ,0) "+
						"		- isnull( ( select sum(tonggiatri) from donhang_ctkm_trakm where donhangId = a.pk_seq and SPMA is null ), 0 )     " +
						"		- isnull( ( select sum( round(thanhtien, 0) )  from DONHANG_CHIETKHAUBOSUNG where donhang_fk = a.pk_seq )	, 0 )  ) as tongTIEN, " +
						"		isnull( ( select sum( round(thanhtoan, 0) )  from DUYETTRAKHUYENMAI_DONHANG where donhang_fk = a.pk_seq and HIENTHI = '1' )	, 0 ) 	as tongTL					         " +
						"	from donhang a inner join khachhang d on a.khachhang_fk = d.pk_seq         " +
						"	where a.import = '0' and a.trangthai != 2 and a.pk_seq = '" + dhId + "'  " +
						") " +
						"TGT on DH.pk_seq = TGT.donhangID where DH.pk_seq = '" + dhId + "' ";
		
		System.out.println("--CAP NHAT TIEN DON HANG: " + query);
		db.update(query);
		
	}
	
	//CAP NHAT TONG GIA TRI DON HANG
	public int CompareDATE(String _date1, String _date2)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	//Date date1 = sdf.parse("2014-10-01");
	    	//Date date2 = sdf.parse("2014-10-01");
	    	
	    	Date date1 = sdf.parse(_date1);
	    	Date date2 = sdf.parse(_date2);
	
	    	//System.out.println(sdf.format(date1));
	    	//System.out.println(sdf.format(date2));
	
	    	return date1.compareTo(date2);
		}
		catch (Exception e) {
			return 0;
		}
	}


	public String Check_Kho_Tong_VS_KhoCT(String nppId, geso.traphaco.distributor.db.sql.dbutils db)
	{
		//CHECK TRƯỚC 1 số kho hàng bán đang dùng
		String query =  " select count( * ) as soDONG  "+
						" from  "+ 
						" ( " +
						"	 select npp_fk, nhomkenh_fk, kho_fk, sanpham_fk, sum(available) as available, sum(soluong) as soluong, sum(booked) as booked_ct  "+
						"	 from nhapp_kho_chitiet where npp_fk = '" + nppId + "' and kho_fk in ( 100000, 100001, 100002, 100007, 100017, 100016, 100018 ) "+
						"	 group by nhomkenh_fk, npp_fk, kho_fk, sanpham_fk	  "+
						" ) " +
						" CT full outer join nhapp_kho total on total.npp_fk=ct.npp_fk and total.nhomkenh_fk=ct.nhomkenh_fk  "+
						"		and total.sanpham_fk=ct.sanpham_fk and total.kho_fk=ct.kho_fk   "+
						" where  total.kho_fk in ( 100000, 100001, 100002, 100007, 100017, 100016, 100018 ) and  "+
						"		( isnull(ct.available,0) + isnull(ct.booked_ct,0) != isnull(total.available,0) + isnull(total.booked ,0)  "+ 
						"			or isnull(total.soluong,0) != isnull(ct.soluong,0)  or isnull(total.BOOKED,0) != isnull(ct.booked_ct,0) " + 
						"		) and  isnull(total.npp_fk, ct.npp_fk) = '" + nppId + "' ";
		
		System.out.println("Check_Kho_Tong_VS_KhoCT " + query);
		String msg = "";
		ResultSet rs = db.get(query);
		try
		{
			if(rs.next())
			{
				if(rs.getInt("soDONG") > 0 )
				{
					//msg = "Lỗi phát sinh do lệch Số lượng kho tổng và chi tiết của sản phẩm trong kho. Vui lòng liên hệ Admin để xử lý ";
					
					//GHI NHAN SO LIEU TAI THOI DIEM LECH
					query = "  insert LOG_LECH_TONG_VS_CHITIET( npp_fk, nhomkenh_fk, kho_fk, sanpham_fk, soluong_total, booked_total, avai_total, soluong_chitiet, booked_chitiet, avai_chitiet ) " + 
							"  select ISNULL( total.NPP_FK, CT.NPP_FK ) as npp_fk, ISNULL( total.nhomkenh_fk, CT.nhomkenh_fk ) as nhomkenh_fk, ISNULL( total.kho_fk, CT.kho_fk ) as kho_fk, ISNULL( total.sanpham_fk, CT.sanpham_fk ) as sanpham_fk,  " + 
							"  	total.SOLUONG, total.BOOKED, total.AVAILABLE, CT.soluong, CT.booked_ct, CT.available " + 
							"  from    " + 
							"  (  " + 
							"  	 select npp_fk, nhomkenh_fk, kho_fk, sanpham_fk, sum(available) as available, sum(soluong) as soluong, sum(booked) as booked_ct   " + 
							"  	 from nhapp_kho_chitiet where npp_fk = '" + nppId + "' and kho_fk in ( 100000, 100002 )  " + 
							"  	 group by nhomkenh_fk, npp_fk, kho_fk, sanpham_fk	   " + 
							"  )  " + 
							"  CT full outer join nhapp_kho total on total.npp_fk=ct.npp_fk and total.nhomkenh_fk=ct.nhomkenh_fk   " + 
							"  		and total.sanpham_fk=ct.sanpham_fk and total.kho_fk=ct.kho_fk    " + 
							"  where  total.kho_fk in ( 100000, 100002 ) and   " + 
							"  		(  " + 
							"  			isnull(ct.available,0) + isnull(ct.booked_ct,0) != isnull(total.available,0) + isnull(total.booked ,0)    " + 
							"  			or isnull(total.soluong,0) != isnull(ct.soluong,0) or isnull(total.BOOKED,0) != isnull(ct.booked_ct,0)    " + 
							"  		)  " + 
							"  		and  isnull(total.npp_fk, ct.npp_fk) = '" + nppId + "' ";
					db.update(query);
					
					/*//Sendmai để xử lý ngay
					try
					{
						SendMail mail = new SendMail();
						mail.postMailWARNING("luonghv@geso.us,phuctnh@geso.us", "", "Theo dõi tồn kho PHANAM", "Vào thời điểm: " + this.getDateTime() + " nhà phân phối: " + nppId + " đã xuất hiện hiện tượng kho Tổng != Chi tiết. VUi lòng kiểm tra và xử lý gấp." );
					}
					catch(Exception ex1){ ex1.printStackTrace(); }*/
					
					//TẠM THỜI BỊ THÌ XỬ LÝ TRƯỚC ĐỂ RA ĐƠN HÀNG KHÔNG BỊ NGƯNG
					//OTC
					query = "  update kho set kho.BOOKED = ISNULL( book.soluong, 0 ),  " + 
							"  			   kho.AVAILABLE = case when kho.soluong - ISNULL( book.soluong, 0 ) < 0 then 0 else kho.soluong - ISNULL( book.soluong, 0 ) end " + 
							"  from NHAPP_KHO_CHITIET kho left join dbo.ufn_BOOKED_CHITIET( 100000, " + nppId + ", null ) book  " + 
							"  	on kho.KHO_FK = book.kho_fk and kho.NPP_FK = book.npp_fk and kho.SANPHAM_FK = book.sanpham_fk " + 
							"  			and kho.nhomkenh_fk = book.nhomkenh_fk  " + 
							"  			and kho.SOLO = book.solo and kho.NGAYHETHAN = book.ngayhethan " + 
							"  where kho.NPP_FK = '" + nppId + "' and kho.kho_fk = '100000' and kho.BOOKED != ISNULL( book.soluong, 0 ) ";
					db.update(query);
					
					query = "  update kho set kho.BOOKED = ISNULL( book.soluong, 0 ),  " + 
							"  			   kho.AVAILABLE = case when kho.soluong - ISNULL( book.soluong, 0 ) < 0 then 0 else kho.soluong - ISNULL( book.soluong, 0 ) end " + 
							"  from NHAPP_KHO kho left join  " + 
							"  	(	 " + 
							"  		select kho_fk, npp_fk, sanpham_fk, nhomkenh_fk, SUM(soluong) as soluong  " + 
							"  		from dbo.ufn_BOOKED_CHITIET( 100000, " + nppId + ", null ) " + 
							"  		group by kho_fk, npp_fk, sanpham_fk, nhomkenh_fk " + 
							"  	) book  " + 
							"  	on kho.KHO_FK = book.kho_fk and kho.NPP_FK = book.npp_fk and kho.SANPHAM_FK = book.sanpham_fk " + 
							"  			and kho.nhomkenh_fk = book.nhomkenh_fk  " + 
							"  where kho.NPP_FK = '" + nppId + "' and kho.kho_fk = '100000' and kho.BOOKED != ISNULL( book.soluong, 0 ) ";
					db.update(query);
					
					//ETC
					query = "  update kho set kho.BOOKED = ISNULL( book.soluong, 0 ),  " + 
							"  			   kho.AVAILABLE = case when kho.soluong - ISNULL( book.soluong, 0 ) < 0 then 0 else kho.soluong - ISNULL( book.soluong, 0 ) end " + 
							"  from NHAPP_KHO_CHITIET kho left join dbo.ufn_BOOKED_CHITIET( 100002, " + nppId + ", null ) book  " + 
							"  	on kho.KHO_FK = book.kho_fk and kho.NPP_FK = book.npp_fk and kho.SANPHAM_FK = book.sanpham_fk " + 
							"  			and kho.nhomkenh_fk = book.nhomkenh_fk  " + 
							"  			and kho.SOLO = book.solo and kho.NGAYHETHAN = book.ngayhethan " + 
							"  where kho.NPP_FK = '" + nppId + "' and kho.kho_fk = '100002' and kho.BOOKED != ISNULL( book.soluong, 0 ) ";
					db.update(query);
					
					query = "  update kho set kho.BOOKED = ISNULL( book.soluong, 0 ),  " + 
							"  			   kho.AVAILABLE = case when kho.soluong - ISNULL( book.soluong, 0 ) < 0 then 0 else kho.soluong - ISNULL( book.soluong, 0 ) end " + 
							"  from NHAPP_KHO kho left join  " + 
							"  	(	 " + 
							"  		select kho_fk, npp_fk, sanpham_fk, nhomkenh_fk, SUM(soluong) as soluong  " + 
							"  		from dbo.ufn_BOOKED_CHITIET( 100002, " + nppId + ", null ) " + 
							"  		group by kho_fk, npp_fk, sanpham_fk, nhomkenh_fk " + 
							"  	) book  " + 
							"  	on kho.KHO_FK = book.kho_fk and kho.NPP_FK = book.npp_fk and kho.SANPHAM_FK = book.sanpham_fk " + 
							"  			and kho.nhomkenh_fk = book.nhomkenh_fk  " + 
							"  where kho.NPP_FK = '" + nppId + "' and kho.kho_fk = '100002' and kho.BOOKED != ISNULL( book.soluong, 0 ) ";
					db.update(query);
				}
			}
		    rs.close();
		} 
		catch (Exception e)
		{
		    e.printStackTrace();
		    return "Lỗi phát sinh khi check Tồn Kho";
		    //return "";
		}
		
		return msg;
	}
	
	
	public String Check_Kho_Tong_VS_KhoCT(String nppId)
	{
		/*String query =  " select count( * ) as soDONG  "+
						" from  "+ 
						" ( " +
						"	 select npp_fk, kbh_fk, kho_fk, sanpham_fk, sum(available) as available, sum(soluong) as soluong, sum(booked) as booked_ct  "+
						"	 from nhapp_kho_chitiet where npp_fk = '" + nppId + "' "+
						"	 group by kbh_fk, npp_fk, kho_fk, sanpham_fk	  "+
						" ) " +
						" CT full outer join nhapp_kho total on total.npp_fk=ct.npp_fk and total.kbh_fk=ct.kbh_fk  "+
						"		and total.sanpham_fk=ct.sanpham_fk and total.kho_fk=ct.kho_fk   "+
						" where    "+
						"		( isnull(ct.available,0) + isnull(ct.booked_ct,0) != isnull(total.available,0) + isnull(total.booked ,0)  "+ 
						"			or isnull(total.soluong,0) != isnull(ct.soluong,0)  "+ 
						"		) and  isnull(total.npp_fk, ct.npp_fk) = '" + nppId + "' ";
		
		System.out.println("Check_Kho_Tong_VS_KhoCT " + query);
		String msg = "";
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		try
		{
	    	if(rs.next())
			{
				if(rs.getInt("soDONG") > 0 )
				{
					msg = "Lỗi phát sinh do lệch Số lượng của sản phẩm ";
				}
			}
		    rs.close();
	    } 
		catch (Exception e)
	    {
		    e.printStackTrace();
		    return "Lỗi phát sinh khi check Tồn Kho";
	    }

		return msg;*/
		
		return "";
	}
	
	public String Check_Huy_NghiepVu_KhoaSo(String table, String id, String column, String nppId, dbutils db)
	{
		String query = " select DATEPART(MONTH, " + column + ") as Thang, DATEPART(YEAR, " + column + ") as Nam "+
						" from " + table + "  a  "+
						" where PK_SEQ = '" + id + "' and exists  "+
						" (  "+
						"	select 1 from KHOASOTHANG where THANGKS= DATEPART(MONTH,"+column+") "+
						"	and NAM=DATEPART(YEAR, " + column + ") and NPP_FK = '" + nppId + "'  "+
						" )  ";
		
		System.out.println("Check_Huy_NghiepVu_KhoaSo: " + query);
		String msg = "";
		ResultSet rs = db.get(query);
		try
	    {
		    while(rs.next())
		    {
		    	msg = "Bạn không được thực hiện nghiệp vụ trong tháng đã khóa sổ !";
		    }
		    rs.close();
	    } 
		catch (Exception e)
	    {
		    e.printStackTrace();
		    return "Lỗi phát sinh khi check khóa sổ;";
	    }
		return msg;
	}
	
	public String Check_Huy_NghiepVu_KhoaSo(String table, String id, String column, String nppId)
	{
		String query = " select DATEPART(MONTH, " + column + ") as Thang, DATEPART(YEAR, " + column + ") as Nam "+
						" from " + table + "  a  "+
						" where PK_SEQ = '" + id + "' and exists  "+
						" (  "+
						"	select 1 from KHOASOTHANG where THANGKS= DATEPART(MONTH,"+column+") "+
						"	and NAM=DATEPART(YEAR, " + column + ") and NPP_FK = '" + nppId + "'  "+
						" )  ";
		
		System.out.println("Check_Huy_NghiepVu_KhoaSo: " + query);
		String msg = "";
		dbutils db=new dbutils();
		ResultSet rs = db.get(query);
		try
	    {
		    while(rs.next())
		    {
		    	msg = "Bạn không được thực hiện nghiệp vụ trong tháng đã khóa sổ !";
		    }
		    rs.close();
	    } 
		catch (Exception e)
	    {
		    e.printStackTrace();
		    return "Lỗi phát sinh khi check khóa sổ;";
	    }
		return msg;
	}
	
	
	public String Check_Huy_NghiepVu_KhoaSo(String table, String id, String column, dbutils db)
	{
		String query =  "		select DATEPART(MONTH,"+column+") as Thang,DATEPART(YEAR,"+column+") as Nam "+
						"		from "+table+"  a  "+
						"		where PK_SEQ='"+id+"' and exists  "+
						"		(  "+
						"			select 1 from KHOASOTHANG where THANGKS= DATEPART(MONTH,"+column+") "+
						"			and NAM=DATEPART(YEAR,"+column+") and NPP_FK=a.NPP_FK  "+
						"		)  ";
		String msg = "";
		System.out.println("______"+query);
		ResultSet rs = db.get(query);
		try
	    {
			if(rs != null )
			{
			    while(rs.next())
			    {
			    	msg= "Bạn không được thực hiện nghiệp vụ trong tháng đã khóa sổ !";
			    }
			    rs.close();
			}
	    } 
		catch (Exception e)
	    {
		    e.printStackTrace();
		    return "Lỗi phát sinh khi check khóa sổ;";
	    }
		return msg;
	}
	
	public String Check_Huy_NghiepVu_KhoaSo(String table, String id, String column)
	{
		String query =  "		select DATEPART(MONTH,"+column+") as Thang,DATEPART(YEAR,"+column+") as Nam "+
						"		from "+table+"  a  "+
						"		where PK_SEQ='"+id+"' and exists  "+
						"		(  "+
						"			select 1 from KHOASOTHANG where THANGKS= DATEPART(MONTH,"+column+") "+
						"			and NAM=DATEPART(YEAR,"+column+") and NPP_FK=a.NPP_FK  "+
						"		)  ";
		String msg = "";
		dbutils db=new dbutils();
		ResultSet rs = db.get(query);
		try
	    {
		    while(rs.next())
		    {
		    	msg= "Bạn không được thực hiện nghiệp vụ trong tháng đã khóa sổ !";
		    }
		    rs.close();
	    } 
		catch (Exception e)
	    {
		    e.printStackTrace();
		    return "Lỗi phát sinh khi check khóa sổ;";
	    }
		return msg;
	}
	
	
	//Khi sửa bắt kỳ ghiệp vụ gì liên quan tới tồn kho trong quá khú, mà khác ngày hiện tại đều phải check NXT tới ngày đó có ok không
	////Cái này sẽ gài khi kho đã được xử lý hết
	public String Check_NghiepVu_QuaKhu(String tableNAME, String nppId, String nghiepvuId, String ngaynghiepvu, String spId, String soluong, dbutils db)
	{
		//Chỉ cần check các nghiệp vụ liên quan tới xuất, nhập
		/*String spIds = "";
		if(tableNAME.toUpperCase().equals("DONHANG"))
		{
			spIds = " select sanpham_fk from DONHANG_SANPHAM where donhang_fk = '" + nghiepvuId + "' ";
		}
		else if(tableNAME.toUpperCase().equals("HOADON"))
		{
			spIds = " select sanpham_fk from HOADON_SANPHAM where hoadon_fk = '" + nghiepvuId + "' ";
		}
		else if(tableNAME.toUpperCase().equals("NHAPHANG"))
		{
			spIds = " select sanpham_fk from NHAPHANG_SP where nhaphang_fk = '" + nghiepvuId + "' ";
		}
		
		if(spId.trim().length() > 0)
			spIds += " AND sanpham_fk = '" + spIds + "' ";*/
		
		//B1. Check NXT tới thời điểm hiện tại không được âm -> NXT tới hiện tại chính là tồn kho hiện tại
		/*String query = " select XNT from ufn_XNT_Check_NV_QuaKhu_Total ( " + nppId + ", " + spId + ", '', '' ) ";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					if( rs.getDouble("XNT") < Double.parseDouble(soluong) )
					{
						rs.close();
						return "Theo nhập xuất tồn hiện tại bạn chỉ có thể cập nhật số lượng của sản phẩm ( " + spId + " ) tới ( " + rs.getDouble("XNT") + " ) ";
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return e.getMessage();
			}
		}*/
		
		//B2. Check NXT từ ngày làm nghiệp vụ tới ngày hiện tại không được âm ( từng ngày 1 )
		String now = this.getDateTime();
//		String query = "";
		while( now.length() > 0 )
		{
			//CHECK NXT
			String tungay = ngaynghiepvu;
			String dengay = TangNgay(1, ngaynghiepvu);
			System.out.println("--TU NGAY::: " + tungay + " -- DEN NGAY::: " + dengay);
			
			//CHECK TUNG NGAY CO BI AM NXT KHONG
//			query = "";
			
			ngaynghiepvu = TangNgay(1, ngaynghiepvu);	
			if(ngaynghiepvu.equals(now))
				break;
		}
		
		return "";
	}

	private String TangNgay(int songay, String ngaythang) 
	{
		String kq = "";
	
		String[] arr = ngaythang.split("-");
		
		// lấy thời điểm bây giờ: 
		Calendar c = Calendar.getInstance(); 
		
		c.set(Calendar.YEAR, Integer.parseInt(arr[0]));
		c.set(Calendar.MONTH, Integer.parseInt(arr[1]) - 1);
		c.set(Calendar.DATE, Integer.parseInt(arr[2]));
		
		c.add(Calendar.DATE, songay);
		
		//System.out.println("---THANG NEW::: " + ( c.get(Calendar.MONTH) + 1 ) + " -- NAM NEW::: " + c.get(Calendar.YEAR)  +  "  -- NGAY NEW::: " + ( c.get(Calendar.DATE) + 1 ) );
		
		String thangNEW = ( c.get(Calendar.MONTH) + 1 ) < 10 ? ( "0" + Integer.toString(( c.get(Calendar.MONTH) + 1 ) )) : Integer.toString( c.get(Calendar.MONTH) + 1 );
		String ngayNEW = ( c.get(Calendar.DATE) ) < 10 ? ( "0" + Integer.toString(c.get(Calendar.DATE))) : Integer.toString( c.get(Calendar.DATE) );
		
		kq = c.get(Calendar.YEAR) + "-" + thangNEW + "-" + ngayNEW;
		
		//System.out.println("---KQ::: " + kq);
		return kq;
	}
	
	
	public static void main(String[] arg)
	 {
//		 Utility util = new Utility();
		 dbutils db = new dbutils();
		 
		 String query = " SELECT PK_SEQ FROM NHAPHANPHOI WHERE isKHACHHANG = 0 AND PK_SEQ != 1 ";
		 
		 ResultSet rs = db.get(query);
		 String npp_fk = "";
		 String msg = "";
		 
		 try
		 {
			 if(rs!=null)
			 {
				 while(rs.next())
				 {
					 npp_fk = rs.getString("PK_SEQ");
					 
					 query = " INSERT ERP_TAIKHOANKT (SOHIEUTAIKHOAN, TENTAIKHOAN, LOAITAIKHOAN_FK, TAIKHOANCOCHITIET, NGUOITAO, NGUOISUA, NGAYSUA, TRANGTHAI, CONGTY_FK, \n"+
							 " DUNGCHOKHO, DUNGCHONGANHANG, DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTDOANHTHU, DUNGCHOCOPHIEU, DUNGCHOCONGTYCON, DUNGCHONHANVIEN, DUNGCHODOITUONGKYQUY,  npp_fk) \n"+
							 " select SOHIEUTAIKHOAN, TENTAIKHOAN, LOAITAIKHOAN_FK, TAIKHOANCOCHITIET, NGUOITAO, NGUOISUA, NGAYSUA, TRANGTHAI, NULL CONGTY_FK, \n"+
							 " DUNGCHOKHO, DUNGCHONGANHANG, DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTDOANHTHU, DUNGCHOCOPHIEU, DUNGCHOCONGTYCON, DUNGCHONHANVIEN, DUNGCHODOITUONGKYQUY,  "+npp_fk+" \n"+
							 " from ERP_TAIKHOANKT \n"+
							 " WHERE npp_fk = 1 " ;
					 
					 System.out.println(query);
					 if(!db.update(query))
					 {
						msg = "3.Không thể cập nhật tài khoản kế toán " + query;
					 }
				 }
			 }
		 }
		 catch(Exception ex)
		 {
			ex.printStackTrace(); 
		 }
		 
		 
		/* String query = "select pk_seq hdId from ERP_HOADONNPP WHERE TRANGTHAI NOT IN (3,5) and pk_seq = 139456";
		 
		 ResultSet rs = db.get(query);
		 
		 String hdId = "";
		 String msg = ""; 
		 if(rs!=null)
		 {
			 try
			 {
				 while( rs.next() )
					{
					 	hdId = rs.getString("hdId");
					 	msg = util.CapNhat_ThanhTien_HoaDon(db, hdId );
					}
				 rs.close();
			 }
			 catch (Exception e) {
					e.printStackTrace();
			 }
			 
		 }*/
		 
		 
		 
		 //util.Check_NghiepVu_QuaKhu("", "", "", "2015-05-10", "", "", db);
		 
		/* //CHỐT LẠI HÓA ĐƠN
		 String query = ""+
		 				// XÓA CHỨNG TỪ
		 
		 				
		 				//" SELECT sochungtu PK_SEQ, N'Xóa Hóa đơn tài chính' nghiepvu FROM ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'Hóa đơn tài chính'" +
		 				//" AND SOCHUNGTU IN (SELECT PK_SEQ FROM ERP_HOADONNPP WHERE TRANGTHAI IN (2,4) AND KYHIEU != 'HDDK'  )  ";
		 				//" SELECT PK_SEQ, N'Xóa Hóa đơn tài chính' nghiepvu FROM ERP_HOADONNPP WHERE KYHIEU!='HDDK' " +
						//" AND TRANGTHAI IN ( 2 , 4 ) AND PK_SEQ = 199942 ";
		 				// HÓA ĐƠN TÀI CHÍNH
			 			//" SELECT PK_SEQ, N'Hóa đơn tài chính' nghiepvu FROM ERP_HOADONNPP " + 
				 		//" WHERE TRANGTHAI IN (2,4)  "+
				 		//" AND KYHIEU != 'HDDK' ";
		 				
						//" SELECT sochungtu PK_SEQ, N'Xóa Hóa đơn NCC' nghiepvu FROM ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'Duyệt hóa đơn NCC'" +
						//" AND SOCHUNGTU = 100627  ";
		 
		 				//" SELECT sochungtu PK_SEQ, N'Xóa Hóa đơn trả hàng khách hàng' nghiepvu FROM ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'Hóa đơn trả hàng khách hàng'" +
						//" AND SOCHUNGTU IN ( 101647 )  ";
		 				
				 		//THU TIEN 
				 		" SELECT pk_seq, N'Thu tiền' nghiepvu  FROM ERP_THUTIEN " +
				 		" WHERE TRANGTHAI = 1 AND PK_SEQ = 101036 ";
		 
						" SELECT sochungtu PK_SEQ, N'Xóa Thu tiền' nghiepvu FROM ERP_PHATSINHKETOAN WHERE LOAICHUNGTU in ( N'Thu tiền theo hóa đơn' )" +
						" AND SOCHUNGTU IN  ( 101827, 101960, 101965 )  ";
				 		
						//" SELECT sochungtu PK_SEQ, N'Hóa đơn trả hàng khách hàng' nghiepvu FROM ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'Hóa đơn trả hàng khách hàng'" +
						//" AND SOCHUNGTU = 101647  ";
		 				
		 System.out.println(query);
		 ResultSet rs = db.get(query);
		 if(rs != null)
		 {
			 try 
			 {
				while( rs.next() )
				{
					String pk_seq = rs.getString("pk_seq");
					String nghiepvu = rs.getString("nghiepvu");
					
					String msg = ""; 
					
					if(nghiepvu.equals("Hóa đơn tài chính"))
					{
						msg = util.chot_HoaDonTaiChinh(db, pk_seq );
						query = "Insert HOADON_KETOAN_LOG ( hoadon_fk, msg ) values ( '" + pk_seq + "', N'" + msg.replaceAll("'", "''") + "' ) ";
						db.update(query);
					}
					
					else 
					
					if(nghiepvu.equals("Xóa Hóa đơn tài chính"))
					{
						msg = util.XOA(db, pk_seq, "Hóa đơn tài chính" );
						query = "Insert HOADON_KETOAN_LOG ( hoadon_fk, msg ) values ( '" + pk_seq + "', N'" + msg.replaceAll("'", "''") + "' ) ";
						db.update(query);
					}
					else 
						
						if(nghiepvu.equals("Xóa Thu tiền"))
						{
							msg = util.XOA(db, pk_seq, "Thu tiền theo hóa đơn" );
							query = "Insert HOADON_KETOAN_LOG ( hoadon_fk, msg ) values ( '" + pk_seq + "', N'" + msg.replaceAll("'", "''") + "' ) ";
							db.update(query);
						}
					else 
						
						if(nghiepvu.equals("Xóa Hóa đơn NCC"))
						{
							msg = util.XOA(db, pk_seq, "Duyệt hóa đơn NCC" );
							query = "Insert HOADON_KETOAN_LOG ( hoadon_fk, msg ) values ( '" + pk_seq + "', N'" + msg.replaceAll("'", "''") + "' ) ";
							db.update(query);
						}
					else 
						
						if(nghiepvu.equals("Phiếu chi"))
						{
							msg = util.XOA(db, pk_seq, "Trả khác" );
							query = "Insert HOADON_KETOAN_LOG ( hoadon_fk, msg ) values ( '" + pk_seq + "', N'" + msg.replaceAll("'", "''") + "' ) ";
							db.update(query);
						}
					else 
						
						if(nghiepvu.equals("Xóa Hóa đơn trả hàng khách hàng"))
						{
							msg = util.XOA(db, pk_seq, "Hóa đơn trả hàng khách hàng" );
							query = "Insert HOADON_KETOAN_LOG ( hoadon_fk, msg ) values ( '" + pk_seq + "', N'" + msg.replaceAll("'", "''") + "' ) ";
							db.update(query);
						}
				
					else 
					if(nghiepvu.equals("Thu tiền"))
					{						
						msg = util.XOA(db, pk_seq , "Thu tiền theo bảng kê");
						query = "Insert THUTIEN_KETOAN_LOG ( thutien_fk, msg ) values ( '" + pk_seq + "', N'" + msg.replaceAll("'", "''") + "' ) ";
						db.update(query);
					}
										
				}
				rs.close();
			} 
			 catch (Exception e) {
				e.printStackTrace();
			}
		 }*/
		 
		 query = "Xong rồi!";
		 System.out.println(query);
	 }
	
	String checkThangKhoaSoHopLe(Idbutils db, String thang, String nam)
	{
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
		String thangKS = "1";
		String namKS = "2013";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					thangKS = rsCheck.getString("THANGKS");
					namKS = rsCheck.getString("NAM");
				}
				rsCheck.close();
			} 
			catch (Exception e) {}
		}
		
		String thangHopLe = "";
		String namHopLe = "";
		if(Integer.parseInt(thangKS) == 12 )
		{
			thangHopLe =  "1";
			namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
		}
		else
		{
			thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
			namHopLe = namKS;
		}
		System.out.println("namHopLe :"+namHopLe);
		System.out.println("nam :"+nam);
		System.out.println("thangHopLe :"+thangHopLe);
		System.out.println("thang :"+thang);
//		if(( Integer.parseInt(namHopLe) > Integer.parseInt(nam) ) || ( Integer.parseInt(thangHopLe) >= Integer.parseInt(thang) ) && ( Integer.parseInt(namHopLe) == Integer.parseInt(nam) ) )
//		{
//			//TAM THOI CHUA CHECK
//			return "Bạn chỉ có thể đóng nghiệp vụ sau tháng khóa sổ gần nhất ( " + thangKS + "-" + namKS + " ) 1 tháng";
//		}
		
		return "";
	}
	
	public String Update_TaiKhoan(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		
		
		String query;
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}

	public String Update_TaiKhoan_SP_ERP(Idbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, 
			String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, 
			String TONGGIATRINT, String khoanmuc,String SpId , String masp, String tensp, String donvi ,String Solo,String KhoNhanId,String KhoXuatId,String Sohoadon,String Ngayhoadon,String KhoanmucchiphiId )
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		
		msg = "";
		if(taikhoanCO_fk==null || taikhoanCO_fk.length()==0){
			return "Chưa xác định được tài khoản có để cập nhật bút toán";
		}
		if(taikhoanNO_fk==null || taikhoanNO_fk.length()==0){
			return "Chưa xác định được tài khoản nợ để cập nhật bút toán";
		}
		
		if(ngayghinhan==null || ngayghinhan.length()==0){
			return "Chưa xác định được ngày ghi nhận bút toán ";
		}
		if(sochungtu==null || sochungtu.length()==0){
			return "Chưa xác định được số chứng từ ";
		}
		if(KhoNhanId==null || KhoNhanId.length()==0){
			KhoNhanId="NULL";
		}
		if(KhoXuatId==null || KhoXuatId.length()==0){
			KhoXuatId="NULL";
		}
		
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		if(SpId!=null && SpId.equals("")){
			SpId="NULL";
		}
		
		if(KhoanmucchiphiId != null && KhoanmucchiphiId.equals("")){
			KhoanmucchiphiId="NULL";
		}
		
		//GHI CO
		  
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
		
			
		String	query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, " +
													 " KHOANMUC,SOHOADON,NGAYHOADON,SANPHAM_FK,KHOANMUCCHIPHI_FK,KHO_FK,KHONHAN_FK ,MAHANG,TENHANG,DONVI,SOLO,IS_CO ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " +
											" " + _thanhtienNT + ", N'" + khoanmuc + "','"+Sohoadon+"','"+Ngayhoadon+"',"+SpId+", "+KhoanmucchiphiId+" ,"+KhoXuatId+","+KhoNhanId+",'"+masp+"',N'"+tensp+"',N'"+donvi+"','"+Solo+"','1' ) ";
			
			 System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		 
		//GHI NO
	  
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = " insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
				    " DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC , SOHOADON,NGAYHOADON,SANPHAM_FK,KHOANMUCCHIPHI_FK,KHO_FK,KHONHAN_FK ,MAHANG,TENHANG,DONVI,SOLO,IS_NO) " +
					" values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' " +
							" ,'"+Sohoadon+"','"+Ngayhoadon+"',"+SpId+", "+KhoanmucchiphiId+" ,"+KhoXuatId+","+KhoNhanId+",'"+masp+"',N'"+tensp+"',N'"+donvi+"','"+Solo+"','1' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
		 
		return msg;
		
	}
	
	
	
	public String Update_TaiKhoan_SP(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String masp, String tensp, String donvi )
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_DienGiai(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String Diengiai, String Machungtu)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, DIENGIAI, MACHUNGTU) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', N'"+Diengiai+"', N'"+Machungtu+"' ) ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_TheoSoHieu(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, String NO, String CO, String DOITUONG,  
								String MADOITUONG, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
		
		
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
	
		
		//GHI CO
		if(Float.parseFloat(_CO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
										" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
										" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select pk_seq, " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' " +
						"from ERP_TAIKHOANKT " +
						"where SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan CO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
			if(taikhoanNO_SoHieu.trim().length() > 0)
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'" +
						"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b  " +
						"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ";
			}
			else
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
										 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
						" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "'" +
						"from ERP_TAIKHOANKT a" +
						"where a.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "'  ";
			}
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
		
		}
		
		//GHI NO
		if(Float.parseFloat(_NO) >= 0) 
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ) and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select pk_seq, '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "' " +
						"from ERP_TAIKHOANKT " +
						"where SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO ( MOT SO TRUONG HOP SE KHONG CO DOI UNG... )
			if(taikhoanCO_SoHieu.trim().length() > 0)
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
													 "  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, b.pk_seq, " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' " +
						"from ERP_TAIKHOANKT a, ERP_TAIKHOANKT b " +
						"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' and b.SOHIEUTAIKHOAN = '" + taikhoanCO_SoHieu + "' ";
			}
			else
			{
				query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
										 		"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC) " +
						"select '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", a.pk_seq, null, " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
									" N'" + DOITUONG + "', N'" + MADOITUONG + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "' " +
						"from ERP_TAIKHOANKT a " +
						"where a.SOHIEUTAIKHOAN = '" + taikhoanNO_SoHieu + "' ";
			}
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
		
		}
		
		return msg;
	
	}
	
	public String getLoaidonhang(String loaiSELECTED)
	{
		String ldh = "<option value='' ></option>";
		
		if(loaiSELECTED.equals("0"))
			ldh += " <option value='0' selected>Đơn hàng NPP</option> ";
		else
			ldh += " <option value='0' >Đơn hàng NPP</option> ";
		
		if(loaiSELECTED.equals("1"))
			ldh += " <option value='1' selected>Đơn hàng thầu</option> ";
		else
			ldh += " <option value='1' >Đơn hàng thầu</option> ";
		
		if(loaiSELECTED.equals("2"))
			ldh += " <option value='2' selected>Đơn hàng không thầu</option> ";
		else
			ldh += " <option value='2' >Đơn hàng không thầu</option> ";
		
		if(loaiSELECTED.equals("3"))
			ldh += " <option value='3' selected>Đơn hàng nội bộ</option> ";
		else
			ldh += " <option value='3' >Đơn hàng nội bộ</option> ";
			
		return ldh;
	}
	
	public String CapNhatKho( dbutils db, String nppId, String khoId, String nhomkenhId, String spId, String kyguiId, String soluong, String booked, String available, String solo, String ngayhethan, String capnhatkhoTONG, String capnhatkhoCT )
	{
		String query = "";
		
		if(capnhatkhoTONG.equals("1"))
		{
			if( kyguiId.trim().length() <= 0 )
			{
				query = " Update NHAPP_KHO set soluong = soluong + '" + soluong + "', booked = booked + '" + booked + "', available = available + '" + available + "' " + 
						" where npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and nhomkenh_fk = '" + nhomkenhId + "' and sanpham_fk = '" + spId + "' ";
			}
			else
			{
				query = " Update NHAPP_KHO_KYGUI set soluong = soluong + '" + soluong + "', booked = booked + '" + booked + "', available = available + '" + available + "' " + 
						" where npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and nhomkenh_fk = '" + nhomkenhId + "' and sanpham_fk = '" + spId + "' and khachhang_fk = '" + kyguiId + "' and isNPP = '0' ";
			}
			
			if(!db.update(query))
			{
				return "Lỗi cập nhật tồn kho: " + query;
			}
		}
		
		//Nếu có truyền số lô thì sẽ cập nhật cả kho chi tiêt
		if(capnhatkhoCT.equals("1"))
		{
			if( kyguiId.trim().length() <= 0 )
			{
				query = " Update NHAPP_KHO_CHITIET set soluong = soluong + '" + soluong + "', booked = booked + '" + booked + "', available = available + '" + available + "' " + 
						" where npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and nhomkenh_fk = '" + nhomkenhId + "' and sanpham_fk = '" + spId + "' and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
			}
			else
			{
				query = " Update NHAPP_KHO_KYGUI_CHITIET set soluong = soluong + '" + soluong + "', booked = booked + '" + booked + "', available = available + '" + available + "' " + 
						" where npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and nhomkenh_fk = '" + nhomkenhId + "' and sanpham_fk = '" + spId + "' and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and khachhang_fk = '" + kyguiId + "' and isNPP = '0' ";
			}
			
			if(!db.update(query))
			{
				return "Lỗi cập nhật tồn kho CHI TIET: " + query;
			}
		}
		
		return "";
	}
	
	public static String XOA( dbutils db, String hdId, String loaict ) 
	{
		String msg = "";
//		String congtyId = "100001";
		
		try
		{
//			Utility util = new Utility();
			
			db.getConnection().setAutoCommit(false);
			
			//CHECK KHOA SO THANG
			String query = "";			
			
			//CÀI KẾ TOÁN
			
			String nam = "";
			String thang = "";
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO NO, CO CO, TIENTEGOC_FK, TONGGIATRINT, NGAYGHINHAN  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + hdId + "' ";

			System.out.println(query); // round( ( TIENBVAT - isnull(CHIETKHAU, 0) ) * VAT / 100.0, 0 )
			ResultSet rsPSKT = db.get(query);
			
			try 
			{
				while(rsPSKT.next())
				{
					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
					String ngayghinhan = rsPSKT.getString("NGAYGHINHAN");
					double NO = rsPSKT.getDouble("NO");
					double CO = rsPSKT.getDouble("CO");
					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
					
					nam = ngayghinhan.substring(0, 4);
					thang = ngayghinhan.substring(5, 7);
					
					//NEU LA CO THI BAY GIO GHI GIAM CO LAI
					if( NO > 0 )
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					else
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					
					System.out.println("1.REVERT NO-CO: " + query);
					
					if(db.updateReturnInt(query)<0)
					{
						msg = "KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
						db.getConnection().rollback();
						return msg;
					}
					
				}
				rsPSKT.close();
				

				//HỦY KẾ TOÁN ĐÃ GHI NHẬN
				query = " DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '"+hdId+"'";			
				if(!db.update(query))
				{
					msg = "Không thể hủy ERP_PHATSINHKETOAN " + query;
					db.getConnection().rollback();
					return msg;
				}
			} 
			catch (Exception e) { e.printStackTrace();}
			
			
			//LUU LAI THONG TIN
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			
			try {
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return e.getMessage();
		}
		
		try {
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return "";
		
	}
	
	public static String chot_HoaDonTaiChinh( dbutils db, String hdId ) 
	{
		String msg = "";
		String congtyId = "100001";
		
		try
		{
			Utility util = new Utility();
			
			db.getConnection().setAutoCommit(false);
			
			//CHECK KHOA SO THANG
			String query = "";			
			
			//CÀI KẾ TOÁN
			
			String nam = "";
			String thang = "";
			
			query = "\n SELECT	A.NGAYXUATHD,A.PK_SEQ, B.SANPHAM_FK, B.SOLUONG, ISNULL(B.DONGIA,0) DONGIA,ISNULL(B.CHIETKHAU,0) CHIETKHAU, ISNULL(A.NGAYGHINHAN, A.NGAYXUATHD) as NGAYGHINHAN, "+
					"\n ISNULL( ISNULL(A.KHGHINO, A.NPP_DAT_FK), A.nhanvien_fk ) KHACHHANG_FK, "+
					"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13100000' AND CONGTY_FK = "+congtyId+" ) TAIKHOANNO, "+
					"\n isnull(B.THUEVAT,0) VAT, (SELECT LOAISP.TAIKHOANKT_FK FROM ERP_LOAISANPHAM LOAISP INNER JOIN ERP_TAIKHOANKT TAIKHOAN ON LOAISP.TAIKHOANKT_FK = TAIKHOAN.SOHIEUTAIKHOAN "+
					"\n WHERE C.LOAISANPHAM_FK =  LOAISP.PK_SEQ AND TAIKHOAN.SOHIEUTAIKHOAN = LOAISP.TAIKHOANKT_FK AND TAIKHOAN.CONGTY_FK =  "+ congtyId +" ) LOAISP, "+
					"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51110000' AND CONGTY_FK =  "+ congtyId +" ) as a51110000, "+
					"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51120000' AND CONGTY_FK =  "+ congtyId +" ) as a51120000, "+
					"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000' AND CONGTY_FK =  "+ congtyId +" ) as a52110000, "+
					"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK =  "+ congtyId +" ) as a33311000, "+
					"\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51180000' AND CONGTY_FK =  "+ congtyId +" ) as a51180000, ISNULL(A.TIENTE_FK, 100000) TIENTE_FK, ISNULL(A.TYGIA, 1) TYGIA, ISNULL(B.THANHTIEN,0) THANHTIEN, ROUND(ISNULL(B.TIENTHUE,0),0) TIENVAT, ISNULL(B.CHIETKHAU,0) CHIETKHAU, "+
					"\n ( SELECT TK.PK_SEQ FROM ERP_NHOMCHIPHI CP INNER JOIN ERP_TAIKHOANKT TK ON CP.TAIKHOAN_FK = TK.SOHIEUTAIKHOAN WHERE TK.CONGTY_FK = "+ congtyId +" AND B.KMCP_FK = CP.PK_SEQ ) KMCP, C.TEN TENSP, C.MA_FAST MASP, B.DONVI DONVITINH, "+
					"\n CASE WHEN A.KHACHHANG_FK IS NOT NULL THEN 0 WHEN A.NPP_DAT_FK IS NOT NULL THEN 1 ELSE 2 END AS ISNPP, A.KBH_FK, B.Kho_FK, B.SOLO, B.NGAYHETHAN  "+
					"\n	FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_SP_CHITIET B ON A.PK_SEQ = B.HOADON_FK "+
					"\n	INNER JOIN SANPHAM C ON B.SANPHAM_FK = C.PK_SEQ	 "+
					"\n	LEFT JOIN KHACHHANG D ON A.KHACHHANG_FK = D.PK_SEQ "+
					"\n	LEFT JOIN NHAPHANPHOI E ON A.NPP_DAT_FK = E.PK_SEQ "+
					"\n LEFT JOIN KHACHHANG F ON A.KHGHINO = F.PK_SEQ LEFT JOIN ERP_NHANVIEN G ON A.nhanvien_fk = G.PK_SEQ "+
					"\n WHERE A.PK_SEQ = "+hdId+" AND LEN(ISNULL(SCHEME,'')) = 0  ";
							
			System.out.println(query); // round( ( TIENBVAT - isnull(CHIETKHAU, 0) ) * VAT / 100.0, 0 )
			ResultSet kt  = db.get(query);
			
			if(kt!=null)
			{
				while(kt.next())
				{
					String khachhang_fk = kt.getString("KHACHHANG_FK");
					double soluong = kt.getDouble("SOLUONG");
//					double dongia = kt.getDouble("DONGIA"); // ĐƠN GIÁ SAU KHI GIẢM
					double tienhang = kt.getDouble("THANHTIEN");
					String SOHIEUTAIKHOAN = kt.getString("LOAISP")== null ? "": kt.getString("LOAISP") ;
					double tienthue = kt.getDouble("TIENVAT");
					
					String sanpham_fk = kt.getString("SANPHAM_FK");
					
					double tienchietkhau = kt.getDouble("CHIETKHAU");
					
					String ngaychungtu = kt.getString("NGAYXUATHD");
					String ngayghinhan = kt.getString("NGAYGHINHAN");
					
					String solo = kt.getString("SOLO")== null ? "": kt.getString("SOLO") ;
					String khoxuat = kt.getString("Kho_FK")== null ? "": kt.getString("Kho_FK") ;
					String ngayhethan = kt.getString("NGAYHETHAN")== null ? "": kt.getString("NGAYHETHAN") ;
					
					String isNPP = kt.getString("ISNPP");					
					String kbh_fk = kt.getString("KBH_FK");
					
					nam = ngayghinhan.substring(0, 4);
					thang = ngayghinhan.substring(5, 7);
					
					String tiente_fk = kt.getString("TIENTE_FK");
					double tygia = kt.getDouble("TYGIA");
					
					String doituong_no = "";
					String madoituong_no =  "";
					
					String doituong_co = "";
					String madoituong_co = "";
					
					String TAIKHOANNO = "";
					String TAIKHOANCO ="";
					
					String masp = kt.getString("MASP");
					String tensp = kt.getString("TENSP");
					String donvitinh = kt.getString("DONVITINH");
					
					if(SOHIEUTAIKHOAN.trim().length()<=0 || SOHIEUTAIKHOAN.trim().length() <=0 || SOHIEUTAIKHOAN == null || SOHIEUTAIKHOAN == null)
					{
						kt.close();
						msg = "Loại sản phẩm / Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						System.out.println(msg);
						db.getConnection().rollback();
						return msg;
					}
						
						
					if(SOHIEUTAIKHOAN.equals("15610000")) // SẢN PHẨM LÀ LOẠI HÀNG HÓA
					{
						if(tienhang>0)
						{
							doituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;
							
							doituong_co = "Sản phẩm";
						    madoituong_co = sanpham_fk;
						    
						    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
						    TAIKHOANCO = kt.getString("a51110000") == null ? "": kt.getString("a51110000") ;
						    
						    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
						    {						    	
								msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(msg);
								kt.close();
								db.getConnection().rollback();
								return msg;
						    }		
						 					   
						    msg = util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, "", 
							Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienhang), 
							Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(tienthue) , "" , hdId ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan, Double.toString(tienhang));
													   
							if(msg.trim().length()>0)
							{
								msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								kt.close();
								db.getConnection().rollback();
								return msg;
							}
						}
							
					}
					else if(SOHIEUTAIKHOAN.equals("15510000")) // SẢN PHẨM LÀ THÀNH PHẨM
					{
						if(tienhang>0)
						{
							doituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;
							
							doituong_co = "Sản phẩm";
						    madoituong_co = sanpham_fk;
						    
						    
						    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
						    TAIKHOANCO = kt.getString("a51120000")== null ? "": kt.getString("a51120000") ;
						    
						    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
						    {
						    	msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						    	System.out.println(msg);
						    	kt.close();								
								db.getConnection().rollback();
								return msg;
						    }
						    
						    msg = 	util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, "", 
										Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienhang), 
										Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(tienthue) , "" , hdId ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan, Double.toString(tienhang));
											
						  
							if(msg.trim().length()>0)
							{
								msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(msg);
								kt.close();
								db.getConnection().rollback();
								return msg;
							}
						}
					}
					else
					{
						if(tienhang>0)
						{
							doituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;
							
							doituong_co = "Sản phẩm";
						    madoituong_co = sanpham_fk;
						    
						    
						    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
						    TAIKHOANCO = kt.getString("a51180000") == null ? "": kt.getString("a51180000") ;
						    
						    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
						    {						    	
								msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(msg);
								kt.close();
								db.getConnection().rollback();
								return msg;
						    }
						    
						    msg = 	util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, "", 
									Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienhang), 
									Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(tienthue) , "" , hdId ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan, Double.toString(tienhang));
													   
							if(msg.trim().length()>0)
							{								
								msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(msg);
								kt.close();
								db.getConnection().rollback();
								return msg;
							}
						}
					}
					
					if(tienthue>0)
					{
						doituong_no = "Khách hàng";
						madoituong_no = khachhang_fk;
						
						doituong_co = "Sản phẩm";
					    madoituong_co = sanpham_fk;
					    
					    
					    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
					    TAIKHOANCO = kt.getString("a33311000") == null ? "": kt.getString("a33311000") ;
					    
					    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
					    {
							msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(msg);
							kt.close();
							db.getConnection().rollback();
							return msg;
					    }
					    
					    msg = 	util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, "", 
						Double.toString(tienthue), Double.toString(tienthue), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienthue), 
						Double.toString(tienthue), "Hóa đơn - Tiền thuế", Double.toString(tienthue) , "" , hdId ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan, Double.toString(tienhang));
					
						if(msg.trim().length()>0)
						{							
							msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(msg);
							kt.close();
							db.getConnection().rollback();
							return msg;
						}
					}
					
					if(tienchietkhau>0) // CHIẾT KHẤU THEO DÒNG HÀNG
					{
						doituong_no = "Sản phẩm";
						madoituong_no = sanpham_fk;
						
						doituong_co = "Khách hàng";
					    madoituong_co = khachhang_fk;
					    
					    TAIKHOANNO = kt.getString("KMCP")== null ? "": kt.getString("KMCP") ;
					    TAIKHOANCO = kt.getString("TAIKHOANNO")== null ? "": kt.getString("TAIKHOANNO") ;
					    
					    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
					    {
							msg = "Khách hàng tương ứng hoặc chiết khấu chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(msg);
							kt.close();
							db.getConnection().rollback();
							return msg;
					    }
					    
					    msg = 	util.Update_TaiKhoan_FULL( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, "", 
						Double.toString(tienchietkhau), Double.toString(tienchietkhau), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Double.toString(soluong), "", tiente_fk, "", Double.toString(tygia), Double.toString(tienchietkhau), 
						Double.toString(tienchietkhau), "Hóa đơn - Tiền chiết khấu", Double.toString(tienthue) , "" , hdId ,isNPP ,masp , tensp, donvitinh, kbh_fk, khoxuat, solo, ngayhethan, Double.toString(tienhang));
						
						if(msg.trim().length()>0)
						{							
							msg = "Khách hàng tương ứng hoặc chiết khấu chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(msg);
							kt.close();							
							db.getConnection().rollback();
							return msg;
						}
					}
				}
				kt.close();
			}
			

			// CHIẾT KHẤU TÍCH LŨY
			
			query = " SELECT A.HOADON_FK, round(round(isnull(A.tienchuaVAT, 0),0)*isnull(A.VAT,0)/100,0) tichluy_tienvat , \n" +
					" ISNULL(B.NGAYGHINHAN, B.NGAYXUATHD) as NGAYGHINHAN, \n"+
					" B.NGAYXUATHD, ISNULL(B.TIENTE_FK, 100000) TIENTE_FK, ISNULL(B.TYGIA, 1) TYGIA , \n"+
					" round(isnull(A.tienchuaVAT,0),0) tichluy_tienBVAT, \n"+
					" ISNULL( ISNULL(B.KHGHINO, B.NPP_DAT_FK), B.nhanvien_fk ) KHACHHANG_FK, \n"+
					" CASE WHEN B.KHGHINO IS NOT NULL THEN F.TAIKHOAN_FK  WHEN B.NPP_DAT_FK IS NOT NULL THEN E.TAIKHOAN_FK when B.nhanvien_fk is not null then G.TAIKHOAN_FK END TAIKHOANNO, \n"+
					" ( SELECT TK.PK_SEQ FROM ERP_NHOMCHIPHI CP INNER JOIN ERP_TAIKHOANKT TK ON CP.TAIKHOAN_FK = TK.SOHIEUTAIKHOAN WHERE TK.CONGTY_FK = "+ congtyId +" AND A.KMCP_FK = CP.PK_SEQ ) KMCP, \n"+
					" ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK = "+ congtyId +") as a33311000,  \n"+
					" CASE WHEN B.KHACHHANG_FK IS NOT NULL THEN 0 WHEN B.NPP_DAT_FK IS NOT NULL THEN 1 ELSE 2 END AS ISNPP, B.KBH_FK \n"+
				    " FROM  ERP_HOADONNPP_CHIETKHAU A INNER JOIN ERP_HOADONNPP B ON A.HOADON_FK = B.PK_SEQ \n"+
				    " LEFT JOIN KHACHHANG D ON B.KHACHHANG_FK = D.PK_SEQ \n"+
					" LEFT JOIN NHAPHANPHOI E ON B.NPP_DAT_FK = E.PK_SEQ \n"+
					" LEFT JOIN KHACHHANG F ON B.KHGHINO = F.PK_SEQ \n " +
					" LEFT JOIN ERP_NHANVIEN G ON B.nhanvien_fk = G.PK_SEQ  \n"+
				    " WHERE A.HOADON_FK = "+hdId ;
			
				    System.out.println(query);
					kt = db.get(query);
					
					if(kt!=null)
					{
						double tichluy_tienvat = 0;
						double tichluy_tienBVAT = 0;
						
						String doituong_no = "";
						String doituong_co = "";
						
						String madoituong_no = "";
						String madoituong_co = "";
						
						String khachhang_fk = "";
						
						String TAIKHOANNO = "";
						String TAIKHOANCO = "";
						
						String isNPP = "";
						String kbh_fk = "";
						
						String tiente_fk = "";
						double tygia = 0;
						
						while(kt.next())
						{
							tichluy_tienvat = kt.getDouble("tichluy_tienvat");
							tichluy_tienBVAT = kt.getDouble("tichluy_tienBVAT");
													
							khachhang_fk = kt.getString("KHACHHANG_FK");
							kbh_fk = kt.getString("KBH_FK");
							tygia = kt.getDouble("TYGIA");
							tiente_fk = kt.getString("TIENTE_FK");
							
							String ngaychungtu = kt.getString("NGAYXUATHD");
							String ngayghinhan = kt.getString("NGAYGHINHAN");
							
							isNPP = kt.getString("isNPP");
							
							nam = ngayghinhan.substring(0, 4);
							thang = ngayghinhan.substring(5, 7);
							
							if(tichluy_tienBVAT > 0)
							{
								doituong_no = "";
								madoituong_no = "";
								
								doituong_co = "Khách hàng";
								madoituong_co = khachhang_fk;
								
								TAIKHOANNO = kt.getString("KMCP")== null ? "": kt.getString("KMCP") ;
							    TAIKHOANCO = kt.getString("TAIKHOANNO")== null ? "": kt.getString("TAIKHOANNO") ;
							    
							    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
							    {
									msg = "Khách hàng tương ứng hoặc chiết khấu chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
									System.out.println(msg);
									kt.close();
									db.getConnection().rollback();
									return msg;
							    }
							
							    msg = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, "", 
										Double.toString(tichluy_tienBVAT), Double.toString(tichluy_tienBVAT), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "", "", tiente_fk, "", Double.toString(tygia), Double.toString(tichluy_tienBVAT), 
										Double.toString(tichluy_tienBVAT), "Hóa đơn - chiết khấu tích lũy", "0" , "" ,  hdId ,isNPP ,"" , "", "", kbh_fk);
									    
							}
							
							if(tichluy_tienvat > 0)
							{						
								doituong_no = "";
							    madoituong_no = "";
							    
							    doituong_co = "Khách hàng";
								madoituong_co = khachhang_fk;
							    
							    
							    TAIKHOANCO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
							    TAIKHOANNO = kt.getString("a33311000") == null ? "": kt.getString("a33311000") ;
							    
							    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
							    {
									msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
									System.out.println(msg);
									kt.close();
									db.getConnection().rollback();
									return msg;
							    }
							    
							    msg = 	util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, "", 
								Double.toString(tichluy_tienvat), Double.toString(tichluy_tienvat), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "", "", tiente_fk, "", Double.toString(tygia), Double.toString(tichluy_tienvat), 
								Double.toString(tichluy_tienvat), "Hóa đơn - Tiền thuế tích lũy", "0" , "" , hdId ,isNPP ,"" , "", "", kbh_fk);
							 
								if(msg.trim().length()>0)
								{							
									msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
									System.out.println(msg);
									kt.close();
									db.getConnection().rollback();
									return msg;
								}
							}
						}
						kt.close();
					}
					
			
			//LUU LAI THONG TIN
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			
			try {
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return e.getMessage();
		}
		
		try {
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return "";
		
	}
	
	public static String chot_ThuTien( dbutils db, String thutienId ) 
	{
		String msg = "";
		String congtyId = "100001";
		
		try
		{
			Utility util = new Utility();
			
			db.getConnection().setAutoCommit(false);
			
			//CHECK KHOA SO THANG
			String query = "";
			
			query = "select noidungtt_fk, httt_fk, isnull(bangke_fk,0) bangke_fk from ERP_THUTIEN WHERE PK_SEQ = "+thutienId ;
			System.out.println(query);
			ResultSet ktr = db.get(query);
			
			String noidungtt_fk = "";
			String bangke_fk = "";
			String httt_fk = "";
			
			String doituong_NO = "";
			String madoituong_NO = "";
			String doituong_CO = "";
			String madoituong_CO = "";
			
			if(ktr!=null)
			{
				while (ktr.next()) {
					noidungtt_fk = ktr.getString("noidungtt_fk");
					bangke_fk = ktr.getString("bangke_fk");
					httt_fk = ktr.getString("httt_fk");
				}
				ktr.close();
			}
			
			//CÓ SỬ DỤNG BẢNG KÊ, NỘI DUNG THANH TOÁN LÀ CHỌN THU TIỀN THEO BẢNG KÊ, HÌNH THỨC THANH TOÁN LÀ TIỀN MẶT
			
			if(!bangke_fk.equals("0")&&noidungtt_fk.equals("100004")&&httt_fk.equals("100000")) // SỬ DỤNG BẢNG KÊ
			{
				query = 
					  "\n select tt.ngayghiso, tt.tiente_fk, TTHOADON.sotienTT, tt.tiente_fk, isnull(tt.chietkhau,0) as chietkhau, "
					+ "\n isnull(tt.phinganhang, 0) as phinganhang, isnull(tt.chenhlech, 0) as chenhlech, "
					+ "\n TTHOADON.khachhang_fk, tt.httt_fk, tt.nganhang_fk, tt.chinhanh_fk, tt.noidungtt_fk ,"
					+ "\n ( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where Sotaikhoan = tt.sotaikhoan AND CONGTY_FK = "+congtyId+" ) as taikhoanNO_SoHieu, "
					+ "\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13100000' AND CONGTY_FK = "+congtyId+" ) as taikhoanCO_KH_SoHieu,  "
					+ "\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = "+congtyId+") HTTHANHTOAN "
					+ "\n from erp_thutien tt inner join ( "
					+ "\n                                select tthd.THUTIEN_FK,tthd.KHACHHANG_FK, SUM(tthd.SOTIENTT) as SOTIENTT "
					+ "\n                                 from ERP_THUTIEN_HOADON tthd inner join ERP_HOADONNPP hd on tthd.HOADON_FK = hd.PK_SEQ "
					+ "\n                                 group by tthd.THUTIEN_FK,tthd.KHACHHANG_FK "
					+ "\n                                 ) TTHOADON on TTHOADON.THUTIEN_FK = tt.PK_SEQ "
					+ "\n where tt.pk_seq = '" + thutienId + "'";
				
				System.out.println(query);
				ResultSet thutien = db.get(query);
				
				String kh_fk = "";
				String taikhoanNO_SoHieu = "";
				String taikhoanCO_KH_SoHieu = "";
				
				double sotienTT = 0;
				if(thutien!=null)
				{
					while (thutien.next()) {
						kh_fk = thutien.getString("khachhang_fk");
						sotienTT = thutien.getDouble("sotienTT");
						taikhoanNO_SoHieu = thutien.getString("HTTHANHTOAN");
						taikhoanCO_KH_SoHieu =  thutien.getString("taikhoanCO_KH_SoHieu");
						
						String nam = thutien.getString("ngayghiso").substring(0,4);
						String thang = thutien.getString("ngayghiso").substring(5, 7);
						String tiente_fk =thutien.getString("tiente_fk");
						
						doituong_NO = "Tiền mặt";
						madoituong_NO = "";

						doituong_CO = "Khách hàng";
						madoituong_CO = kh_fk;
						
						// GHI NHAN SO TIEN THU DUOC

						System.out.println("ralooi");
						
						if (taikhoanNO_SoHieu.trim().length() <= 0|| taikhoanCO_KH_SoHieu.trim().length() <= 0) {
							msg = "2.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return msg;
						}
						if(sotienTT>0)
						{
							msg = util.Update_TaiKhoan(db, thang, nam,	thutien.getString("ngayghiso"),thutien.getString("ngayghiso"), "Thu tiền theo bảng kê",
								  thutienId, taikhoanNO_SoHieu,	taikhoanCO_KH_SoHieu, "",	Double.toString(sotienTT),Double.toString(sotienTT), doituong_NO,
								  madoituong_NO, doituong_CO, madoituong_CO,"0", "", "", tiente_fk, "", "1",Double.toString(sotienTT),
								  Double.toString(sotienTT),"Thu tiền - Thực thu");
							if (msg.trim().length() > 0) {
								db.getConnection().rollback();
								return msg;
							}
						}
						
					}
					
					thutien.close();
					
				}
				
			}
			
			if(!bangke_fk.equals("0")&&noidungtt_fk.equals("100004")&&httt_fk.equals("100001"))
			{
				query = 
					  "\n select tt.ngayghiso, tt.tiente_fk, TTHOADON.sotienTT, tt.tiente_fk, isnull(tt.chietkhau,0) as chietkhau, "
					+ "\n isnull(tt.phinganhang, 0) as phinganhang, isnull(tt.chenhlech, 0) as chenhlech, "
					+ "\n TTHOADON.khachhang_fk, tt.httt_fk, tt.nganhang_fk, tt.chinhanh_fk, tt.noidungtt_fk ,"
					+ "\n ( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where Sotaikhoan = tt.sotaikhoan AND CONGTY_FK = "+congtyId+" ) as taikhoanNO_SoHieu, "
					+ "\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13100000' AND CONGTY_FK = "+congtyId+" ) as taikhoanCO_KH_SoHieu,  "
					+ "\n ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = "+congtyId+") HTTHANHTOAN "
					+ "\n from erp_thutien tt inner join ("
					+ "\n                                 select tthd.THUTIEN_FK,tthd.KHACHHANG_FK, SUM(tthd.SOTIENTT) as SOTIENTT "
					+ "\n                                 from ERP_THUTIEN_HOADON tthd inner join ERP_HOADONNPP hd on tthd.HOADON_FK = hd.PK_SEQ "
					+ "\n                                 group by tthd.THUTIEN_FK,tthd.KHACHHANG_FK "
					+ "\n                                 ) TTHOADON on TTHOADON.THUTIEN_FK = tt.PK_SEQ "
					+ "\n where tt.pk_seq = '" + thutienId + "'";
				
				System.out.println(query);
				ResultSet thutien = db.get(query);
				
				String kh_fk = "";
				String taikhoanNO_SoHieu = "";
				String taikhoanCO_KH_SoHieu = "";
				
				double sotienTT = 0;
				if(thutien!=null)
				{
					while (thutien.next()) {
						kh_fk = thutien.getString("khachhang_fk");
						sotienTT = thutien.getDouble("sotienTT");
						taikhoanNO_SoHieu = thutien.getString("taikhoanNO_SoHieu");
						taikhoanCO_KH_SoHieu =  thutien.getString("taikhoanCO_KH_SoHieu");
						
						String nam = thutien.getString("ngayghiso").substring(0,4);
						String thang = thutien.getString("ngayghiso").substring(5, 7);
						String tiente_fk =thutien.getString("tiente_fk");
						
						doituong_NO = "Ngân hàng";
						madoituong_NO = "";

						doituong_CO = "Khách hàng";
						madoituong_CO = kh_fk;
						
						// GHI NHAN SO TIEN THU DUOC

						System.out.println("ralooi");
						
						if (taikhoanNO_SoHieu.trim().length() <= 0|| taikhoanCO_KH_SoHieu.trim().length() <= 0) {
							msg = "2.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return msg;
						}
						if(sotienTT>0)
						{
							msg = util.Update_TaiKhoan(db, thang, nam,	thutien.getString("ngayghiso"),thutien.getString("ngayghiso"), "Thu tiền theo bảng kê",
									thutienId, taikhoanNO_SoHieu,	taikhoanCO_KH_SoHieu, "",	Double.toString(sotienTT),Double.toString(sotienTT), doituong_NO,
									madoituong_NO, doituong_CO, madoituong_CO,"0", "", "", tiente_fk, "", "1",Double.toString(sotienTT),
									Double.toString(sotienTT),"Thu tiền - Thực thu");
							if (msg.trim().length() > 0) {
								db.getConnection().rollback();
								return msg;
							}
						}						
					}					
					thutien.close();					
				}
			}
		
			
			
			//LUU LAI THONG TIN
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			
			try {
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return e.getMessage();
		}
		
		try {
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return "";
		
	}

	public int CheckKhoaSoKinhDoanh( geso.traphaco.distributor.db.sql.dbutils db, String nppId, String ngaychungtu, String tableNAME, String columnNAME, String id )
	{
		// = -1 tháng này chưa khóa sổ kinh doanh
		// = 0  ngày chứng từ trong tháng khóa sổ kinh doanh và trước ngày khóa sổ kinh doanh
		// = 1  ngày chứng từ trong tháng khóa sổ kinh doanh và sau ngày khóa sổ kinh doanh
		
		int chenhLECH = -1;
		
		String query =  "";
		
		//CHECK TỚI PHÚT LUÔN
		query =  "select DATEDIFF(mi, thoidiem, '" + ngaychungtu + "') as chenhLECH  "+
				 "from "+
				 "( "+
				 "	select thoidiem, cast(NAM as varchar(10)) + '-' + case when THANGKS < 10 then '0' + cast(THANGKS as varchar(10)) else cast(THANGKS as varchar(10)) end "+
				 "									+ '-' + case when NGAYKS < 10 then '0' + cast(NGAYKS as varchar(10)) else cast(NGAYKS as varchar(10)) end as ngayks "+
				 "	from ERP_KHOASOKINHDOANH  "+
				 "	where npp_fk = '" + nppId + "' and NAM = SUBSTRING('" + ngaychungtu + "', 0, 5 ) and THANGKS = cast( SUBSTRING('" + ngaychungtu + "', 6, 2 ) as int ) "+
				 ") "+
				 "KS ";
		
		/*if(tableNAME.trim().length() <= 0)
		{
			query =  "select DATEDIFF(dd, ngayks, '" + ngaychungtu + "') as chenhLECH  "+
					 "from "+
					 "( "+
					 "	select cast(NAM as varchar(10)) + '-' + case when THANGKS < 10 then '0' + cast(THANGKS as varchar(10)) else cast(THANGKS as varchar(10)) end "+
					 "									+ '-' + case when NGAYKS < 10 then '0' + cast(NGAYKS as varchar(10)) else cast(NGAYKS as varchar(10)) end as ngayks "+
					 "	from ERP_KHOASOKINHDOANH  "+
					 "	where npp_fk = '" + nppId + "' and NAM = SUBSTRING('" + ngaychungtu + "', 0, 5 ) and THANGKS = cast( SUBSTRING('" + ngaychungtu + "', 6, 2 ) as int ) "+
					 ") "+
					 "KS ";
		}
		else
		{
			query =  "select DATEDIFF(dd, ngayks, ( select " + columnNAME + " from " + tableNAME + " where pk_seq = " + id + "  ) ) as chenhLECH  "+
					 "from "+
					 "( "+
					 "	select cast(NAM as varchar(10)) + '-' + case when THANGKS < 10 then '0' + cast(THANGKS as varchar(10)) else cast(THANGKS as varchar(10)) end "+
					 "									+ '-' + case when NGAYKS < 10 then '0' + cast(NGAYKS as varchar(10)) else cast(NGAYKS as varchar(10)) end as ngayks "+
					 "	from ERP_KHOASOKINHDOANH  "+
					 "	where npp_fk = '" + nppId + "' and NAM = SUBSTRING( ( select " + columnNAME + " from " + tableNAME + " where pk_seq = " + id + "  ) , 0, 5 ) and THANGKS = cast( SUBSTRING( ( select " + columnNAME + " from " + tableNAME + " where pk_seq = " + id + "  ) , 6, 2 ) as int ) "+
					 ") "+
					 "KS ";
		}*/
		
		System.out.println("::: CHECK KSKD: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					if(rs.getInt("chenhLECH") > 0)
						chenhLECH = 1;
					else
						chenhLECH = 0;
				}
				rs.close();
			} 
			catch (Exception e)
			{
				
				e.printStackTrace();
			}
		}
		
		return chenhLECH;
	}
	
	public String CapNhat_ThanhTien_HoaDon( dbutils db, String hdId ) 
	{
		/*String query = "  update ERP_HOADONNPP_SP set THANHTIEN = round( SOLUONG * DONGIA, 0), TIENBVAT = round( SOLUONG * DONGIA, 0 ), CHIETKHAU = ISNULL( CHIETKHAU, 0 ), " + 
					   "  TIENVAT = SOLUONG * round( DONGIA * VAT / 100.0, 0), TIENAVAT = round( ( SOLUONG * DONGIA ), 0 ) + round( ( SOLUONG * DONGIA * VAT / 100.0 ), 0 ) " +
					   "  where hoadon_fk = '" + hdId + "'";*/
		
		/*String query = "  update ERP_HOADONNPP_SP set THANHTIEN = round( SOLUONG * DONGIA, 0), TIENBVAT = round( SOLUONG * DONGIA, 0 ) - round( ISNULL( CHIETKHAU, 0 ), 0 ), CHIETKHAU = round( ISNULL( CHIETKHAU, 0 ), 0 ), " + 
				   	   "  	TIENVAT = round( ( round( SOLUONG * DONGIA, 0 ) - round( ISNULL( CHIETKHAU, 0 ), 0 ) ) * VAT / 100.0, 0 ), " + 
				   	   "	TIENAVAT = round( ( round( SOLUONG * DONGIA, 0 ) - round( ISNULL( CHIETKHAU, 0 ), 0 ) ) * ( 1 + VAT / 100.0 ), 0 ) " +
				   	   "  where hoadon_fk = '" + hdId + "'";
		if( !db.update(query) )
			return "Lỗi khi cập nhật tiền hóa đơn: " + query;
		
		query = "update hd set hd.tongtienbvat = hd_dh.tongtienBVAT,    "+
				 "			  hd.chietkhau = hd_dh.chietkhau,   "+
				 "			  hd.vat = hd_dh.tienVAT,   "+
				 "			  hd.tongtienavat = hd_dh.tongtienBVAT - hd_dh.chietkhau + hd_dh.tienVAT   "+
				 "from ERP_HOADONNPP hd inner join   "+
				 "(   "+
				 "	select HOADON_FK, SUM( isnull(CHIETKHAU, 0) ) as chietkhau, SUM( TIENBVAT ) as tongtienBVAT,    "+
				 "					  SUM( round( ( TIENBVAT - isnull(CHIETKHAU, 0) ) * VAT / 100.0, 0 ) ) as tienVAT    "+
				 "	from ERP_HOADONNPP_SP where dbo.Trim( SCHEME ) = '' and HOADON_FK = '" + hdId + "'	" +
				 "	group by HOADON_FK   "+
				 ")   "+
				 "hd_dh on hd.PK_SEQ = hd_dh.HOADON_FK  " +
				 "where hd.pk_seq = '" + hdId + "' ";
		
		System.out.println(":::: CAP NHAT TIEN: " + query );
		if( !db.update(query) )
			return "Lỗi khi cập nhật tiền hóa đơn: " + query;*/
		
		//SU DUNG TRONG CODE TOOLTIP CHO NHANH
		return "";
	}
	
	public String CapNhat_MaChungTu( dbutils db, String id, String tableNAME, String columnNAME ) 
	{
		String prefix = "";
		if( tableNAME.equals("ERP_DONDATHANGNPP") )
			prefix = "DH";
		else if( tableNAME.equals("ERP_SOXUATHANGNPP") )
			prefix = "SXH";
		else if( tableNAME.equals("ERP_YCXUATKHONPP") )
			prefix = "PGH";
		else if( tableNAME.equals("ERP_XACNHANNHANHANG") )
			prefix = "BBNH";
		else if( tableNAME.equals("DONHANG") )
			prefix = "DH";
			
		String query = "" ;
		if( !tableNAME.equals("ERP_XACNHANNHANHANG") )
		{
			query = "update " + tableNAME + " set machungtu = '" + prefix + "' + SUBSTRING(" + columnNAME + ", 6, 2) + SUBSTRING(" + columnNAME + ", 0, 5) + '-' + CAST( PK_SEQ as varchar(10) ) " + 
					" where pk_seq = '" + id + "' ";
		}
		else
		{
			query = "update " + tableNAME + " set machungtu = '" + prefix + "' + SUBSTRING(" + columnNAME + ", 6, 2) + SUBSTRING(" + columnNAME + ", 0, 5) + '-' + dbo.LaySoChungTu( " + id + " ) " + 
					" where pk_seq = '" + id + "' ";
		}
		
		if( !db.update(query) )
			return "Lỗi khi cập nhật mã chứng từ: " + query;
		
		return "";
	}
	
	public String getSelectBox( ResultSet rs, String style, String selectNAME, String selectEVENT, String selectedValue, String valueCOLUMN, String displayCOLUMN, String showALL, boolean closeRs )
	{
		String str = "";
		
		str = "<SELECT name='" + selectNAME + "' id='" + selectNAME + "' onchange = '" + selectEVENT + "' style='" + style + "' > ";
		
		if( showALL.equals("1") )
			str += "<option value='' >ALL</option> ";
		else if( showALL.equals("0") )
			str += "<option value='' ></option> ";
		
		try 
		{
			if( rs != null )
			{
				while( rs.next() )
				{
					if( selectedValue.contains( rs.getString(valueCOLUMN) ) )
						str += "<option value='" + rs.getString(valueCOLUMN) + "' selected >" + rs.getString(displayCOLUMN) + "</option> ";
					else
						str += "<option value='" + rs.getString(valueCOLUMN) + "' >" + rs.getString(displayCOLUMN) + "</option> ";
				}
			}
		} 
		catch (Exception e) { }
		
		str += "</SELECT>";
		
		if( closeRs )
		{
			if( rs != null )
			{
				try 
				{
					rs.close();
					rs = null;
				} 
				catch (Exception e) { }
			}
		}
		
		return str;
	}
	
	public String getPhanQuyen_TheoNhanVien(String tableName, String tableAlias, String columnName, String loainhanvien, String doituongId)
	{
		if( loainhanvien == null || doituongId == null )
			return "";
		
		tableName = tableName.toUpperCase();
		String condition = "";
		if( tableName.equals("ERP_DONDATHANGNPP") || tableName.equals("ERP_HOPDONGNPP") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select DDKD_FK from DDKD_GSBH where GSBH_FK in ( select PK_SEQ from GIAMSATBANHANG where ASM_FK = '" + doituongId + "' ) ) \n";
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select DDKD_FK from DDKD_GSBH where GSBH_FK = '" + doituongId + "' ) \n";
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( " + doituongId + " ) \n";
			}
		}
		else if( tableName.equals("KHACHHANG") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select khachhang_fk from KhachHang_DaiDienKinhDoanh where DDKD_FK in ( select DDKD_FK from DDKD_GSBH where GSBH_FK in ( select PK_SEQ from GIAMSATBANHANG where ASM_FK = '" + doituongId + "' ) ) ) \n";
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select khachhang_fk from KhachHang_DaiDienKinhDoanh where DDKD_FK in ( select DDKD_FK from DDKD_GSBH where GSBH_FK = '" + doituongId + "' ) ) \n";
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select khachhang_fk from KhachHang_DaiDienKinhDoanh where DDKD_FK = '" + doituongId + "' ) \n";
			}
			else if( loainhanvien.equals("6") ) //NVGN
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '" + doituongId + "' ) \n";
			}
			else if( loainhanvien.equals("7") ) //CS - PHAN THEO DIA BAN
			{
				//query += " and a.diaban in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + this.userId + "' ) ";
				
				String condition01 = "  " + tableAlias + "." + columnName + " in ( select pk_seq from KHACHHANG where diaban in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) ";
				String condition02 = "  " + tableAlias + "." + columnName + " in ( select pk_seq from KHACHHANG where tinhthanh_fk in ( select tinhthanh_fk from nhanvien_tinhthanh where nhanvien_fk = '" + doituongId + "' ) ) ";
				
				//condition += " and " + tableAlias + "." + columnName + " in ( select pk_seq from KHACHHANG where diaban in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) ";
				
				condition += "\n and ( ( " + condition01 + " ) or ( " + condition02 + " ) ) \n";
			}
		}
		else if( tableName.equals("TUYENBANHANG") || tableName.equals("DAIDIENKINHDOANH") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select DDKD_FK from DDKD_GSBH where GSBH_FK in ( select PK_SEQ from GIAMSATBANHANG where ASM_FK = '" + doituongId + "' )  ) \n";
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select DDKD_FK from DDKD_GSBH where GSBH_FK = '" + doituongId + "' ) \n";
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( " + doituongId + " ) \n";
			}
		}
		else if( tableName.equals("GIAMSATBANHANG") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select PK_SEQ from GIAMSATBANHANG where ASM_FK = '" + doituongId + "'  ) \n";
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( " + doituongId + " ) \n";
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select GSBH_FK from DDKD_GSBH where DDKD_FK = '" + doituongId + "' ) \n";
			}
		}
		else if( tableName.equals("NHANVIENGIAONHAN") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				
			}
			else if( loainhanvien.equals("6") ) //NVGN
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( " + doituongId + " ) \n";
			}
		}
		else if( tableName.equals("NHANVIENGIAONHAN_ALL") )
		{
			if( loainhanvien.equals("1") ) //BM
			{
				
			}
			else if( loainhanvien.equals("2") ) //ASM
			{
				
			}
			else if( loainhanvien.equals("3") ) //GSBH
			{
				
			}
			else if( loainhanvien.equals("4") ) //DDKD
			{
				
			}
			else if( loainhanvien.equals("6") ) //NVGN
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select nvgn_fk from NHANVIEN where nvgn_fk is not null " + 
																				" 	and pk_seq in ( select nhanvien_fk from nhanvien_diaban where diaban_fk in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) ) \n";
			}
		}
		else if( tableName.equals("DLPP") )
		{
			if( loainhanvien.equals("6") ) //NVGN
			{
				condition += "\n and " + tableAlias + "." + columnName + " in ( select NPP_FK from NHAPP_DIABAN where NVGN_FK in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) \n";
			}
		}
		else if( tableName.equals("CS_BAOCAO") || tableName.equals("CS_BAOCAO_NPP") )
		{
			if( loainhanvien.equals("7") ) //CS - PHAN THEO DIA BAN
			{
				//query += " and a.diaban in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + this.userId + "' ) ";
				
				String condition01 = "  ";
				String condition02 = "  ";
				if( tableName.equals("CS_BAOCAO") )
				{
					condition01 = "  " + tableAlias + "." + columnName + " in ( select pk_seq from KHACHHANG where diaban in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) ";
					condition02 = "  " + tableAlias + "." + columnName + " in ( select pk_seq from KHACHHANG where tinhthanh_fk in ( select tinhthanh_fk from nhanvien_tinhthanh where nhanvien_fk = '" + doituongId + "' ) ) ";
				}
				else  //CS_BAOCAO_NPP
				{
					condition01 = "  " + tableAlias + "." + columnName + " in ( select NPP_FK from NHAPP_DIABAN where DIABAN_FK in ( select diaban_fk from nhanvien_diaban where nhanvien_fk = '" + doituongId + "' ) ) ";
					condition02 = "  " + tableAlias + "." + columnName + " in ( select pk_seq from NHAPHANPHOI where tinhthanh_fk in ( select tinhthanh_fk from nhanvien_tinhthanh where nhanvien_fk = '" + doituongId + "' ) ) ";
				}
				
				condition += " and ( ( " + condition01 + " ) or ( " + condition02 + " ) ) \n";
			}
		}
		
		System.out.println("::: CONDITION: " + condition );
		return condition;
	}
	
	public String quyen_kenh(String userId)
	{
		String sql ="( select kenh_fk as kbh_fk from nhanvien_kenh where nhanvien_fk ='"+ userId +"'    )";
	//	String sql ="( select pk_seq from KENHBANHANG where trangthai ='1' )";
		return sql;
	}
	
	public String Update_TaiKhoan_Vat_DienGiai_KBH( dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, String kbh_fk )
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _KBH  = "NULL";
		if(kbh_fk.trim().length()>0)
			_KBH = kbh_fk;
		
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_Vat_DienGiai(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_Vat_DienGiai(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP_No, String isNPP_Co)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}

		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+ isNPP_Co +") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+ isNPP_No +") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
		}
		
		return msg;
		
	}
	
	public String DMS_Update_TaiKhoan_Vat_DienGiai(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP_No, String isNPP_Co)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong TRAPHACODMS.DBO.from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update TRAPHACODMS.DBO.ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert TRAPHACODMS.DBO.ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert TRAPHACODMS.DBO.ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+ isNPP_Co +") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from TRAPHACODMS.DBO.ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update TRAPHACODMS.DBO.ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert TRAPHACODMS.DBO.ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert TRAPHACODMS.DBO.ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+ isNPP_No +") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
		}
		
		return msg;
		
	}
	
	public String Update_TaiKhoan_Vat_DienGiai_SP_KBH (geso.traphaco.distributor.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, String masp, String tensp, String donvi, String kbh_fk)
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		
		
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _MASP = "";
		if(masp.trim().length()>0)
			_MASP = masp;
		
		String _TENSP = "";
		if(tensp.trim().length()>0)
			_TENSP = tensp;
		
		String _DONVI = "";
		if(donvi.trim().length()>0)
			_DONVI = donvi;
		
		String _KBH = "NULL";
		if(kbh_fk!=null)
		{
			if(kbh_fk.trim().length()>0)
				_KBH = kbh_fk;
		}
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI, KBH_FK ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}	
		
	public String Update_TaiKhoan_FULL (geso.traphaco.distributor.db.sql.dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc, String VAT, String DienGiai, String MaChungTu, String isNPP, String masp, String tensp, String donvi, String kbh_fk, String kho_fk, String Solo, String Ngayhethan, String tienhang )
	{
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		
		
		String query;
		
		String _ndnhapxuat_fk = "null";
		if(NOIDUNGNHAPXUAT_FK.trim().length() > 0)
			_ndnhapxuat_fk = NOIDUNGNHAPXUAT_FK;
		
		String _sochungtu = "null";
		if(sochungtu.trim().length() > 0)
			_sochungtu = sochungtu;
		
		String _soluong = "null";
		if(SOLUONG.trim().length() > 0)
			_soluong = SOLUONG.trim();
		
		String _dongia = "null";
		if(DONGIA.trim().length() > 0)
			_dongia = DONGIA.trim();
		
		String _thanhtienViet = "null";
		if(TONGGIATRI.trim().length() > 0)
			_thanhtienViet = TONGGIATRI.trim();
		
		String _dongiaNT = "null";
		if(DONGIANT.trim().length() > 0)
			_dongiaNT = DONGIANT.trim();
		
		String _thanhtienNT = "null";
		if(TONGGIATRINT.trim().length() > 0)
			_thanhtienNT = TONGGIATRINT.trim();
				
		String _NO = "0";
		if(NO.trim().length() > 0)
			_NO = NO;
		
		String _CO = "0";
		if(CO.trim().length() > 0)
			_CO = CO;
		
		String _VAT = "0";
		if(VAT.trim().length() > 0)
			_VAT = VAT;
		
		String _TIENHANG = "0";
		if(tienhang.trim().length() > 0)
			_TIENHANG = tienhang;
		
		String _DIENGIAI = "";
		if(DienGiai.trim().length()>0)
			_DIENGIAI = DienGiai;
		
		String _MACHUNGTU = "";
		if(MaChungTu.trim().length()>0)
			_MACHUNGTU = MaChungTu;
		
		String _ISNPP = "null";
		if(isNPP.trim().length()>0)
			_ISNPP = isNPP;
		
		String _MASP = "null";
		if(masp.trim().length()>0)
			_MASP = masp;
		
		String _TENSP = "null";
		if(tensp.trim().length()>0)
			_TENSP = tensp;
		
		String _DONVI = "null";
		if(donvi.trim().length()>0)
			_DONVI = donvi;
		
		String _KBH = "null";
		if(kbh_fk.trim().length()>0)
			_KBH = kbh_fk;
		
		String _KHO = "null";
		if(kho_fk.trim().length()>0)
			_KHO = kho_fk;
		
		String _SOLO = "null";
		if(Solo.trim().length()>0)
			_SOLO = Solo;
		
		String _NGAYHETHAN = "null";
		if(Ngayhethan.trim().length()>0)
			_NGAYHETHAN = Ngayhethan;
		
		//String kho_fk, String Solo, String Ngayhethan
		
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKNo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKNo.next())
				{
					sodong = rsTKNo.getInt("sodong");
				}
				rsTKNo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRICONGUYENTE = GIATRICONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanCO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanCO_fk + "', " + _thanhtienViet + ", '0', '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", " + _thanhtienNT + ", 0,'" + thang + "', '" + nam + "' ";
			}
			
			System.out.println("1.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "1.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
			
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"DOITUONG, MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT, DIENGIAI , MACHUNGTU, ISNPP, MAHANG, TENHANG, DONVI, KBH_FK, SOLO, NGAYHETHAN, KHO_FK, TIENHANG ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanCO_fk + "', '" + taikhoanNO_fk + "', " + _ndnhapxuat_fk + ", '0', " + _CO + ", " +
					" N'" + DOITUONG_CO + "', N'" + MADOITUONG_CO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"', "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+", '"+_SOLO+"', '"+_NGAYHETHAN+"', "+_KHO+", "+_TIENHANG+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
					"where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
			ResultSet rsTKCo = db.get(query);
			int sodong = 0;
			try 
			{
				if(rsTKCo.next())
				{
					sodong = rsTKCo.getInt("sodong");
				}
				rsTKCo.close();
			} 
			catch (Exception e) { }
			
			if(sodong > 0) //daco
			{
				query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + _thanhtienViet + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + _thanhtienNT + ", " +
												" GIATRINONGUYENTE = GIATRINONGUYENTE + "  + _thanhtienNT + 
						" where taikhoankt_fk = '" + taikhoanNO_fk + "' and nguyente_fk = '" + TIENTEGOC_FK + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			}
			else
			{
				query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, GIATRICONGUYENTE, GIATRINONGUYENTE, THANG, NAM) " +
						"select '" + taikhoanNO_fk + "', '0', " + _thanhtienViet + ", '" + TIENTEGOC_FK + "', " + _thanhtienNT + ", 0, " + _thanhtienNT + ", '" + thang + "', '" + nam + "'";
			}
			
			System.out.println("2.Cap nhat tai khoan NO: " + query);
			if(!db.update(query))
			{
				msg = "2.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
						
			//GHI PHAT SINH VA DOI UNG CHO TAO KHOAN CO
			query = "insert ERP_PHATSINHKETOAN ( ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO, CO, " +
					"  DOITUONG,  MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC, VAT , DIENGIAI , MACHUNGTU, ISNPP , MAHANG, TENHANG, DONVI, KBH_FK , SOLO, NGAYHETHAN, KHO_FK , TIENHANG ) " +
					"values ( '" + ngaychungtu + "', '" + ngayghinhan + "', N'" + loaichungtu + "', " + _sochungtu + ", '" + taikhoanNO_fk + "', '" + taikhoanCO_fk + "', " + _ndnhapxuat_fk + ", " + _NO + ", '0', " +
					" N'" + DOITUONG_NO + "', N'" + MADOITUONG_NO + "', '" + LOAIDOITUONG + "', " + _soluong + ", " + _dongia + ", '" + TIENTEGOC_FK + "', " + _dongiaNT + ", '" + TIGIA_FK + "', " + _thanhtienViet + ", " + _thanhtienNT + ", N'" + khoanmuc + "', "+_VAT+", N'"+_DIENGIAI+"', N'"+_MACHUNGTU+"' , "+_ISNPP+", N'"+_MASP+"', N'"+_TENSP+"', N'"+_DONVI+"', "+_KBH+", '"+_SOLO+"', '"+_NGAYHETHAN+"', "+_KHO+", "+_TIENHANG+") ";
			
			System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật tài khoản kế toán " + query;
				return msg;
			}
			
		}
		
		return msg;
		
	}
	
	/********************************* XỬ LÝ TỒN KHO PHANAM ********************************/
	public String KiemTraTonKho(dbutils db, String nppId, String khoId, String nhomkenhId, String sanphamId, 
			String solo, String ngayhethan, String soluong, String ngaychungtu)
	{
		String msg = "";
		/*String query = "";
		 
		//THÔNG BÁO CỤ THỂ SỐ LÔ NÀO CÓ THỂ XUẤT BAO NHIÊU
		query = " select tensanpham as ten, tonkho " + 
				" from dbo.ufn_Lay_TonHienTai( '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', '" + sanphamId + "', '" + solo + "', '" + ngayhethan + "', '" + ngaychungtu + "' ) ";
		System.out.println("::: KIEM TRA TON KHO: " + query);
		ResultSet rs = db.get(query);
		msg = "Các sản phẩm sau không đủ tồn kho: ";
		if( rs != null )
		{
			try 
			{
				while( rs.next() )
				{
					if( Double.parseDouble(soluong) < rs.getDouble("tonkho") )
					{
						msg += "+ Sản phẩm (" + rs.getString("ten") + "), với số lô (" + solo + "), ngày hết hạn (" + ngayhethan + "), tới ngày (" + ngaychungtu + "), " + 
							   " chỉ được xuất tối đa (" + rs.getString("tonkho") + "), không đủ số lượng xuất (" + soluong + ") \n" ;
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "Lỗi khi check kho: " + e.getMessage();
			}
		}
		
		if( msg.equals("Các sản phẩm sau không đủ tồn kho: ") )
			msg = "";*/
		
		return msg;
	}
	
	public String KiemTraTonKho_NgayNhapKho(dbutils db, String nppId, String khoId, String nhomkenhId, String sanphamId, 
			String solo, String ngayhethan, String soluong, String ngaynhapkho)
	{
		String msg = "";
		String query = "";
		 
		//THÔNG BÁO CỤ THỂ SỐ LÔ NÀO CÓ THỂ XUẤT BAO NHIÊU
		query = " select tensanpham as ten, tonkho " + 
				" from dbo.ufn_Lay_TonHienTai_NgayNhapKho( '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', '" + sanphamId + "', '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "' ) ";
		System.out.println("::: KIEM TRA TON KHO: " + query);
		ResultSet rs = db.get(query);
		msg = "Các sản phẩm sau không đủ tồn kho: ";
		if( rs != null )
		{
			try 
			{
				while( rs.next() )
				{
					if( Double.parseDouble(soluong) < rs.getDouble("tonkho") )
					{
						msg += "+ Sản phẩm (" + rs.getString("ten") + "), với số lô (" + solo + "), ngày hết hạn (" + ngayhethan + "), tới ngày (" + ngaynhapkho + "), " + 
							   " chỉ được xuất tối đa (" + rs.getString("tonkho") + "), không đủ số lượng xuất (" + soluong + ") \n" ;
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "Lỗi khi check kho: " + e.getMessage();
			}
		}
		
		if( msg.equals("Các sản phẩm sau không đủ tồn kho: ") )
			msg = "";
		
		return msg;
	}
	
	/****************************** SỬ DỤNG NGÀY NHẬP KHO TỰ ĐỀ XUẤT ***************************/
	public String CapNhatKhoNew(dbutils db, String nppId, String khoId, String nhomkenhId, String sanphamId, 
			String solo, String ngayhethan, String soluong,
			String ngaychungtu, String sochungtu, String diengiai, String bean_svl, String NHAP_XUAT, boolean ghinhanNXT_TRONGNGAY  )
	{
		String msg = "";
		String query = "";
		
		//CHECK XEM NGÀY HẾT HẠN NÀY CÒN NHỮNG NGÀY NHẬP KHO NÀO CÓ THỂ SỬ DỤNG
		query = " select ngaynhapkho, soluong - booked as AVAILABLE " + 
				" from dbo.ufn_Lay_TonHienTai_NgayNhapKho( '" + nppId + "', '" + khoId + "', '" + sanphamId + "', '" + ngaychungtu + "' ) " + 
				" where ngayhethan = '" + ngayhethan + "' and solo = '" + solo + "'	 " + 
				" order by ngaynhapkho asc ";
		System.out.println("::: LAY TON HIEN TAI NGAYNHAPKHO: " + query);
		ResultSet rs = db.get(query);
		msg = "";
		double total = 0;
		if( rs != null )
		{
			try 
			{
				while( rs.next() )
				{
					double slg = 0;
					double avai = Math.round(rs.getDouble("AVAILABLE") * 100.0 ) / 100.0;
					String ngaynhapkho = rs.getString("ngaynhapkho");

					total += avai;					
					if(total < Double.parseDouble(soluong))
					{
						slg = avai;
					}
					else
					{
						slg =  Double.parseDouble(soluong) - ( total - avai );
					}
					
					if( slg > 0 ) //LƯU LẠI NGÀY NHẬP KHO SẼ SỬ DỤNG
					{
						query = "insert LOG_NGAYNHAPKHO( NGAYCHUNGTU, SOCHUNGTU, DIENGIAI, NPP_FK, KHO_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, NGAYNHAPKHO ) " +
							    "values( '" + ngaychungtu + "', '" + sochungtu + "', N'" + diengiai + "', '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', " + 
							   			" '" + sanphamId + "', '" + soluong + "', '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "' )";
						
						System.out.println("::: CHEN LOG NGAY NHAP KHO: " + query);
						if( db.updateReturnInt(query) < 1 )
						{
							msg = "Lỗi khi ghi nhận ngày nhập kho: " + query;
							return msg;
						}
						
						msg = this.CapNhatKho_NgayNhapKho(db, nppId, khoId, nhomkenhId, sanphamId, solo, ngayhethan, soluong, ngaychungtu, sochungtu, diengiai, bean_svl, NHAP_XUAT, ngaynhapkho, ghinhanNXT_TRONGNGAY);
						if( msg.trim().length() > 0 )
						{
							return msg;
						}
						
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "Lỗi khi lấy ngày nhập kho: " + e.getMessage();
			}
		}
		
		if(total != Double.parseDouble(soluong))
		{
			msg = "Lỗi lấy tổng lượng theo ngày nhập kho (" + total + ") khác với tổng lượng yêu cầu (" + soluong + ")";
		}
		
		return msg;
	}
	
	/****************************** SỬ DỤNG NGÀY NHẬP KHO ĐÃ BIẾT ***************************/
	public String CapNhatKho_NgayNhapKho(dbutils db, String nppId, String khoId, String nhomkenhId, String sanphamId, 
			String solo, String ngayhethan, String soluong,
			String ngaychungtu, String sochungtu, String diengiai, String bean_svl, String NHAP_XUAT, String ngaynhapkho, boolean ghinhanNXT_TRONGNGAY  )
	{
		String msg = "";
		String query = "";
		
		//GHI NHẬN SỐ NHẬP NHẬP XUẤT TRONG NGÀY
		int phanloai = 0;
		if( ghinhanNXT_TRONGNGAY )
		{
			phanloai = 1;
			query = " insert NHAP_XUAT_TON_TRONGNGAY( NGAYCHUNGTU, NPP_FK, KHO_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT ) " +
					" values( '" + ngaychungtu + "', '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', '" + sanphamId + "', '" + soluong + "', '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "', '" + NHAP_XUAT + "' )";
			
			System.out.println("::: INSERT NHAP_XUAT_TON_TRONGNGAY: " + query);
			if( db.updateReturnInt(query) < 1 )
			{
				msg = "Lỗi khi ghi nhận NXT trong ngày: " + query;
				return msg;
			}
		}
		
		//SAU KHI CẬP NHẬT NHẬP XUẤT TRONG NGÀY, MỚI CHECK SỐ CÓ ĐỦ ĐỂ LÀM NGHIỆP VỤ HAY KHÔNG
		if( NHAP_XUAT.equals("-1") ) //XUẤT MỚI CHECK
		{
			msg = this.KiemTraTonKho(db, nppId, khoId, nhomkenhId, sanphamId, solo, ngayhethan, soluong, ngaynhapkho);
			if( msg.trim().length() > 0 )
			{
				return msg;
			}
		}
		
		//GHI NHẬN LOG NGHIỆP VỤ TRỪ KHO  PHANLOAI = 1 -> CỘT SỐ LƯỢNG, PHANLOAI = 0 -> CỘT BOOKED
		query = "insert LOG_NGHIEPVUKHO( PHANLOAI, NGAYCHUNGTU, SOCHUNGTU, DIENGIAI, bean_svl, NPP_FK, KHO_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT ) " +
			    "values('" + phanloai + "', '" + ngaychungtu + "', '" + sochungtu + "', N'" + diengiai + "', N'" + bean_svl + "', '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', " + 
			   			" '" + sanphamId + "', '" + soluong + "', '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "', '" + NHAP_XUAT + "' )";
		
		System.out.println("::: INSERT LOG_NGHIEPVUKHO: " + query);
		if( db.updateReturnInt(query) < 1 )
		{
			msg = "Lỗi khi ghi nhận LOG tồn kho: " + query;
			return msg;
		}
		
		return msg;
	}
	
	//ĐỀ XUẤT NGÀY NHẬP KHO SỬ DỤNG CHO CÁC NGHIỆP VỤ XUẤT CÓ SỬ DỤNG
	public String DeXuatNgayNhapKho(dbutils db, String nppId, String khoId, String nhomkenhId, String sanphamId, 
			String solo, String ngayhethan, String soluong, String ngaychungtu, String sochungtu, String diengiai  )
	{
		String msg = "";
		String query = "";
		
		//CHECK XEM NGÀY HẾT HẠN NÀY CÒN NHỮNG NGÀY NHẬP KHO NÀO CÓ THỂ SỬ DỤNG
		query = " select ngaynhapkho, soluong as AVAILABLE " + 
				" from dbo.ufn_Lay_NgayNhapKho( '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', '" + sanphamId + "', '" + solo + "', '" + ngayhethan + "' ) " + 
				" order by ngaynhapkho asc ";
		ResultSet rs = db.get(query);
		msg = "";
		double total = 0;
		if( rs != null )
		{
			try 
			{
				while( rs.next() )
				{
					double slg = 0;
					double avai = Math.round(rs.getDouble("AVAILABLE") * 100.0 ) / 100.0;
					String ngaynhapkho = rs.getString("ngaynhapkho");

					total += avai;					
					if(total < Double.parseDouble(soluong))
					{
						slg = avai;
					}
					else
					{
						slg =  Double.parseDouble(soluong) - ( total - avai );
					}
					
					if( slg > 0 ) //LƯU LẠI NGÀY NHẬP KHO SẼ SỬ DỤNG
					{
						query = "insert LOG_NGAYNHAPKHO( NGAYCHUNGTU, SOCHUNGTU, DIENGIAI, NPP_FK, KHO_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, NGAYNHAPKHO ) " +
							    "values( '" + ngaychungtu + "', '" + sochungtu + "', N'" + diengiai + "', '" + nppId + "', '" + khoId + "', '" + nhomkenhId + "', " + 
							   			" '" + sanphamId + "', '" + soluong + "', '" + solo + "', '" + ngayhethan + "', '" + ngaynhapkho + "' )";
						
						System.out.println("::: CHEN LOG NGAY NHAP KHO: " + query);
						if( db.updateReturnInt(query) < 1 )
						{
							msg = "Lỗi khi ghi nhận ngày nhập kho: " + query;
							return msg;
						}
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return "Lỗi khi lấy ngày nhập kho: " + e.getMessage();
			}
		}
		
		if(total != Double.parseDouble(soluong))
		{
			msg = "Lỗi lấy tổng lượng theo ngày nhập kho (" + total + ") khác với tổng lượng yêu cầu (" + soluong + ")";
		}
		
		return msg;
	}
		
	public String GhiNhanLOG(dbutils db, String NPPID, String KHOID, String khachhangKG_FK, String NHOMKENHID, String SPID, 
			String SOLO, String NGAYHETHAN, double SOLUONG, double BOOKED, double AVAILABLE,
			String NGAYCHUNGTU, String SOCHUNGTU, String LOAICHUNGTU, String DIENGIAI, String bean_svl, String NHAP_XUAT, String NGAYNHAPKHO, boolean ghinhanNXT_TRONGNGAY  )
	{
		String msg = "";
		String query = "";
		
		if( khachhangKG_FK.trim().length() <= 0 )
			khachhangKG_FK = "NULL";
		
		//GHI NHẬN SỐ NHẬP NHẬP XUẤT TRONG NGÀY
		int phanloai = 0;
		if( ghinhanNXT_TRONGNGAY )
		{
			phanloai = 1;
			/*query = " insert NHAP_XUAT_TON_TRONGNGAY( NGAYCHUNGTU, SOCHUNGTU, LOAICHUNGTU, NPP_FK, KHO_FK, khachhangKG_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT ) " +
					" values( '" + NGAYCHUNGTU + "', '" + SOCHUNGTU + "', N'" + LOAICHUNGTU + "', '" + NPPID + "', '" + KHOID + "', " + khachhangKG_FK + ", '" + NHOMKENHID + "', '" + SPID + "', '" + SOLUONG + "', '" + SOLO + "', '" + NGAYHETHAN + "', '" + NGAYNHAPKHO + "', '" + NHAP_XUAT + "' )";
			
			System.out.println("::: INSERT NHAP_XUAT_TON_TRONGNGAY: " + query);
			if( db.updateReturnInt(query) < 1 )
			{
				msg = "Lỗi khi ghi nhận NXT trong ngày: " + query;
				return msg;
			}*/
		}
		
		
		//GHI NHẬN LOG NGHIỆP VỤ TRỪ KHO  PHANLOAI = 1 -> TRỪ CỘT SỐ LƯỢNG, PHANLOAI = 0 -> CỘT BOOKED
		/*query = "insert LOG_NGHIEPVUKHO( PHANLOAI, NGAYCHUNGTU, SOCHUNGTU, LOAICHUNGTU, DIENGIAI, bean_svl, NPP_FK, KHO_FK, khachhangKG_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT ) " +
			    "values('" + phanloai + "', '" + NGAYCHUNGTU + "', '" + SOCHUNGTU + "', N'" + LOAICHUNGTU + "', N'" + DIENGIAI + "', N'" + bean_svl + "', '" + NPPID + "', '" + KHOID + "', " + khachhangKG_FK + ", '" + NHOMKENHID + "', " + 
			   			" '" + SPID + "', '" + SOLUONG + "', '" + BOOKED + "', '" + AVAILABLE + "', '" + SOLO + "', '" + NGAYHETHAN + "', '" + NGAYNHAPKHO + "', '" + NHAP_XUAT + "' )";*/
		
		//GHI NHẬN LOG NGHIỆP VỤ TRỪ KHO  PHANLOAI = 1 -> TRỪ CỘT SỐ LƯỢNG, PHANLOAI = 0 -> CỘT BOOKED
		String queryCHECK = "";
		boolean exits = true;
		if( khachhangKG_FK.equals("NULL") )
		{
			queryCHECK = "select count(*) as sodong " +
				   		 "from NHAPP_KHO_CHITIET " + 
				   		 "where NPP_FK = '" + NPPID + "' and KHO_FK = '" + KHOID + "' and SANPHAM_FK = '" + SPID + "' and nhomkenh_fk = '" + NHOMKENHID + "' and SOLO = '" + SOLO + "' and NGAYHETHAN = '" + NGAYHETHAN + "' ";
		}
		else
		{
			queryCHECK = "select count(*) as sodong " +
				   		 "from NHAPP_KHO_KYGUI_CHITIET " + 
				   		 "where khachhang_fk = '" + khachhangKG_FK + "' and NPP_FK = '" + NPPID + "' and KHO_FK = '" + KHOID + "' and SANPHAM_FK = '" + SPID + "' and nhomkenh_fk = '" + NHOMKENHID + "' and SOLO = '" + SOLO + "' and NGAYHETHAN = '" + NGAYHETHAN + "' ";
		}
		ResultSet rs = db.get(queryCHECK);
		if( rs != null )
		{
			try
			{
				if( rs.next() )
				{
					if( rs.getInt("sodong") <= 0 )
						exits = false;
				}
				rs.close();
			}
			catch(Exception ex) { }
		}
		
		if( khachhangKG_FK.equals("NULL") )
		{
			if( !exits )
			{
				query = "insert LOG_NGHIEPVUKHO( PHANLOAI, NGAYCHUNGTU, SOCHUNGTU, LOAICHUNGTU, DIENGIAI, bean_svl, NPP_FK, KHO_FK, khachhangKG_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT, SOLUONG_TAITD, BOOKED_TAITD, AVAI_TAITD ) " +
					    "select '" + phanloai + "', '" + NGAYCHUNGTU + "', '" + SOCHUNGTU + "', N'" + LOAICHUNGTU + "', N'" + DIENGIAI + "', N'" + bean_svl + "', '" + NPPID + "', '" + KHOID + "', " + khachhangKG_FK + ", '" + NHOMKENHID + "', " + 
					   			" '" + SPID + "', '" + SOLUONG + "', '" + BOOKED + "', '" + AVAILABLE + "', '" + SOLO + "', '" + NGAYHETHAN + "', '" + NGAYNHAPKHO + "', '" + NHAP_XUAT + "', 0 as SOLUONG, 0 as BOOKED, 0 as AVAILABLE ";
			}
			else
			{
				query = "insert LOG_NGHIEPVUKHO( PHANLOAI, NGAYCHUNGTU, SOCHUNGTU, LOAICHUNGTU, DIENGIAI, bean_svl, NPP_FK, KHO_FK, khachhangKG_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT, SOLUONG_TAITD, BOOKED_TAITD, AVAI_TAITD ) " +
					    "select '" + phanloai + "', '" + NGAYCHUNGTU + "', '" + SOCHUNGTU + "', N'" + LOAICHUNGTU + "', N'" + DIENGIAI + "', N'" + bean_svl + "', '" + NPPID + "', '" + KHOID + "', " + khachhangKG_FK + ", '" + NHOMKENHID + "', " + 
					   			" '" + SPID + "', '" + SOLUONG + "', '" + BOOKED + "', '" + AVAILABLE + "', '" + SOLO + "', '" + NGAYHETHAN + "', '" + NGAYNHAPKHO + "', '" + NHAP_XUAT + "', SOLUONG, BOOKED, AVAILABLE " +
					   	"from NHAPP_KHO_CHITIET " + 
					   	"where NPP_FK = '" + NPPID + "' and KHO_FK = '" + KHOID + "' and SANPHAM_FK = '" + SPID + "' and nhomkenh_fk = '" + NHOMKENHID + "' and SOLO = '" + SOLO + "' and NGAYHETHAN = '" + NGAYHETHAN + "' ";
			}
		}
		else
		{
			if( !exits )
			{
				query = "insert LOG_NGHIEPVUKHO( PHANLOAI, NGAYCHUNGTU, SOCHUNGTU, LOAICHUNGTU, DIENGIAI, bean_svl, NPP_FK, KHO_FK, khachhangKG_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT, SOLUONG_TAITD, BOOKED_TAITD, AVAI_TAITD ) " +
					    "select '" + phanloai + "', '" + NGAYCHUNGTU + "', '" + SOCHUNGTU + "', N'" + LOAICHUNGTU + "', N'" + DIENGIAI + "', N'" + bean_svl + "', '" + NPPID + "', '" + KHOID + "', " + khachhangKG_FK + ", '" + NHOMKENHID + "', " + 
					   			" '" + SPID + "', '" + SOLUONG + "', '" + BOOKED + "', '" + AVAILABLE + "', '" + SOLO + "', '" + NGAYHETHAN + "', '" + NGAYNHAPKHO + "', '" + NHAP_XUAT + "', 0 as SOLUONG, 0 as BOOKED, 0 as AVAILABLE ";
			}
			else
			{
				query = "insert LOG_NGHIEPVUKHO( PHANLOAI, NGAYCHUNGTU, SOCHUNGTU, LOAICHUNGTU, DIENGIAI, bean_svl, NPP_FK, KHO_FK, khachhangKG_FK, nhomkenh_fk, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYHETHAN, NGAYNHAPKHO, NHAP_XUAT, SOLUONG_TAITD, BOOKED_TAITD, AVAI_TAITD ) " +
					    "select '" + phanloai + "', '" + NGAYCHUNGTU + "', '" + SOCHUNGTU + "', N'" + LOAICHUNGTU + "', N'" + DIENGIAI + "', N'" + bean_svl + "', '" + NPPID + "', '" + KHOID + "', " + khachhangKG_FK + ", '" + NHOMKENHID + "', " + 
					   			" '" + SPID + "', '" + SOLUONG + "', '" + BOOKED + "', '" + AVAILABLE + "', '" + SOLO + "', '" + NGAYHETHAN + "', '" + NGAYNHAPKHO + "', '" + NHAP_XUAT + "', SOLUONG, BOOKED, AVAILABLE " +
					   	"from NHAPP_KHO_KYGUI_CHITIET " + 
					   	"where khachhang_fk = '" + khachhangKG_FK + "' and NPP_FK = '" + NPPID + "' and KHO_FK = '" + KHOID + "' and SANPHAM_FK = '" + SPID + "' and nhomkenh_fk = '" + NHOMKENHID + "' and SOLO = '" + SOLO + "' and NGAYHETHAN = '" + NGAYHETHAN + "' ";
			}
		}
		
		System.out.println("::: INSERT LOG_NGHIEPVUKHO: " + query);
		if( db.updateReturnInt(query) < 1 )
		{
			msg = "Lỗi khi ghi nhận LOG tồn kho: " + query;
			return msg;
		}
		
		return msg;
	}
	
	@SuppressWarnings("unchecked")
	public String getSearchFromHM(String userId,String ServerletName, HttpSession session)   
	{  
		String searchQuery="";
		ServerletName=ServerletName.toLowerCase();
		ServerletName=ServerletName.replaceAll("update", "");
	    String keyHM= ServerletName+'_'+userId;
		this.hmSearch = (HashMap<String,String>) session.getAttribute("hmSearch");
		if(null==this.hmSearch)
		{
			System.out.println("bị null rồi");
			this.hmSearch= new HashMap<String,String>();
		}
	    searchQuery=hmSearch.get(keyHM);
	    System.out.println("CÂY SEARCH LẤY RA TỪ HM LÀ :"+searchQuery);
	    return searchQuery;
	}
	
	@SuppressWarnings("unchecked")
	public void setSearchToHM(String userId,HttpSession session,String ServerletName,String searchQuery)   
	{  
		ServerletName=ServerletName.toLowerCase();
		ServerletName=ServerletName.replaceAll("update", "");
	    String keyHM= ServerletName+'_'+userId;
		this.hmSearch= (HashMap<String,String>) session.getAttribute("hmSearch");
		if(null==this.hmSearch)
		{
			this.hmSearch= new HashMap<String,String>();
		}
	    hmSearch.put(keyHM, searchQuery);
	    session.setAttribute("hmSearch", this.hmSearch);
	}
	
	
	public String Check_NgayNghiepVu_KeToan(Idbutils db,String thang,String nam)
	{

		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
		String thangKS = "12";
		String namKS = "2016";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					thangKS = rsCheck.getString("THANGKS");
					namKS = rsCheck.getString("NAM");
				}
				rsCheck.close();
			} 
			catch (Exception e) {}
		}
		System.out.println("nam :"+nam);
		System.out.println("namKS :"+namKS);
		System.out.println("thang :"+thang);
		System.out.println("thangKS :"+thangKS);
		
		if( (Integer.parseInt(nam)<Integer.parseInt(namKS))  || (( Integer.parseInt(nam) == Integer.parseInt(namKS)) && (Integer.parseInt(thang) <= Integer.parseInt(thangKS) )))
		{
			return "Không được thực hiện nghiệp vụ kế toán trong thời gian đã khóa sổ";
		}
	
		return "";
		
	}
	public String HuyUpdate_TaiKhoan(Idbutils db, String soChungTu, String loaiChungTu)
	{
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )

		
		//Tạm thời chỉ xóa phát sinh kế toán
		String query = 
			"delete erp_PhatSinhKeToan\n" +
			"where soChungTu = " + soChungTu + " and loaiChungTu = N'" + loaiChungTu +"'\n";
		System.out.println("xoa hach toan: \n" + query + "\n----------------------------------------");
		try {
			if (!db.update(query))
				return "HUTK1.1 Không hủy được định khoản";
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return "";
	}
}