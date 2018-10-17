package geso.traphaco.center.beans.dieuchuyenkhuyenmai.imp;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import com.rp.util.DateTime;

import geso.traphaco.center.beans.dieuchuyenkhuyenmai.IDieuchuyenkhuyenmai;
import geso.traphaco.center.db.sql.dbutils;

public class Dieuchuyenkhuyenmai implements IDieuchuyenkhuyenmai{
	String userId;
	String schemeId;
	String scheme;
	String msg;
	String totalbudget;
	String usedbudget;
	String rest;
	String tieuchi;
	String nppstr;
	ResultSet schemeRS;
	ResultSet kv;
	String kvId;
	ResultSet nppRS;
	dbutils db ;
	String size;
	String[] dieuchuyen;
	Hashtable<String, String> usedPro;
	Hashtable<String, String> adjust;
	public Dieuchuyenkhuyenmai()
	{
		this.userId = "";
		this.schemeId = "0";
		this.scheme = "";
		this.msg = "";
		this.totalbudget = "0";
		this.usedbudget = "0";
		this.rest = "0";
		this.tieuchi = "0";
		this.kvId = "0";
		this.nppRS = null;
		this.usedPro = null;
		this.db = new dbutils();
		this.dieuchuyen = new String[1000000];
		//xemngay();
	}
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getSchemeId()
	{
		return this.schemeId;
	}

	public void setSchemeId(String schemeId)
	{
		this.schemeId = schemeId;
	}
		
	public String getTieuchi()
	{
		return this.tieuchi;
	}

	public void setTieuchi(String tieuchi)
	{
		this.tieuchi = tieuchi;
	}

	public String getNppstr()
	{
		return this.nppstr;
	}

	public void setNppstr(String nppstr)
	{
		this.nppstr = nppstr;
	}

	public String getSize()
	{
		return this.size;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public String getRest()
	{
		if(this.rest.equals("0")){
			return "" + Math.round(Float.valueOf(this.totalbudget).floatValue() - Float.valueOf(this.usedbudget).floatValue());
		}else{
			return this.rest;
		}
	}

	public void setRest(String rest)
	{
		this.rest = rest;
	}

	public Hashtable<String, String> getAdjust()
	{
		return this.adjust;
	}
	
	public void createAdjust(){
		if(!this.schemeId.equals("0")){
			this.adjust = this.getAdjustment(schemeId);
		}else{
			this.adjust = null;
		}
	}
	
	public void setAdjust(Hashtable<String, String> adjust)
	{
		this.adjust = adjust;
	}

	public String getBudget(){
		String query;
		if(this.schemeId.equals("0")){
			this.totalbudget = "0";
		}else{
			if (this.kvId.equals("0")){
				query = "select sum(a.ngansach) as budget from phanbokhuyenmai a, nhaphanphoi b where ctkm_fk='"+ this.schemeId + "' and b.pk_seq=a.npp_fk and b.pk_seq in (select pk_seq from nhaphanphoi where trangthai='1' and pk_seq in (select npp_fk from phamvihoatdong where nhanvien_fk='"+ this.userId +"'))";
			}else{
				query = "select sum(a.ngansach) as budget from phanbokhuyenmai a, nhaphanphoi b where ctkm_fk='"+ this.schemeId + "' and b.pk_seq=a.npp_fk and b.pk_seq in (select pk_seq from nhaphanphoi where trangthai='1' and khuvuc_fk='"+ this.kvId +"' and pk_seq in (select npp_fk from phamvihoatdong where nhanvien_fk='"+ this.userId +"'))";
			}
			ResultSet rs = this.db.get(query);
		
			try{
				if(rs != null){
					rs.next();
					if(rs.getString("budget") != null){
						this.totalbudget = rs.getString("budget");
					}
				}
			}catch(Exception e){}
		}
		return this.totalbudget; 
	}

	public String getUsedBudget(){
		String query;
		if(this.schemeId.equals("0")){
			this.usedbudget = "0";
		}else{
			if (this.kvId.equals("0")){
				query = "select sum(a.tonggiatri) as amount from donhang_ctkm_trakm a, donhang b where a.donhangid=b.pk_seq and b.trangthai=3 and a.ctkmId = '"+ schemeId +"' and b.npp_fk in (select npp_fk from phamvihoatdong where nhanvien_fk='"+ this.userId + "')";
			}else{
				query = "select sum(a.tonggiatri) as amount from donhang_ctkm_trakm a, donhang b where a.donhangid=b.pk_seq and b.trangthai=3 and a.ctkmId = '"+ schemeId +"' and b.npp_fk in (select pk_seq from nhaphanphoi where trangthai='1' and khuvuc_fk='"+ this.kvId +"' and pk_seq in (select npp_fk from phamvihoatdong where nhanvien_fk='"+ this.userId + "'))";
			}
			System.out.print(query);
			ResultSet rs = this.db.get(query);
			try{
				if(rs != null){
					rs.next();
					if(rs.getString("amount") != null){
						this.usedbudget = rs.getString("amount");
					}
				}
			}catch(Exception e){}
		}
		return this.usedbudget; 
	}

	public Hashtable<String, String> getusedPro()
	{
		return this.usedPro;
	}

	public void setusedPro(Hashtable<String, String> usedPro)
	{
		this.usedPro = usedPro;
	}

	public String getScheme()
	{
		ResultSet rs = this.db.get("select scheme from ctkhuyenmai where pk_seq='" + this.schemeId + "'");
		try{
			rs.next();
			this.scheme = rs.getString("scheme");
		}catch(Exception e){}
		return this.scheme;
	}

	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public ResultSet getSchemeRS() 
	{
		return this.schemeRS;
	}

	public void setSchemeRS(ResultSet schemeRS) 
	{
		this.schemeRS = schemeRS;
	}
	
	public ResultSet getKv() 
	{
		return this.kv;
	}

	public void setKv(ResultSet khuvuc) 
	{
		this.kv = khuvuc;
	}

	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}

	public ResultSet getNpp() 
	{
		return this.nppRS;
	}

	public void setNpp(ResultSet nppRS) 
	{
		this.nppRS = nppRS;
	}
	
	private ResultSet createSchemeRS(){
		ResultSet schemeRS = this.db.get("select pk_seq, scheme from ctkhuyenmai where tungay <='"+ getDateTime() +"' and denngay >= '"+ getDateTime() + "'");
		
		return schemeRS;
	}
		
	
	private ResultSet createKvRS(){
		ResultSet kvRS;
		kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where pk_seq in (select khuvuc_fk from nhaphanphoi where trangthai='1' and pk_seq in (select npp_fk from phamvihoatdong where nhanvien_fk='" + this.userId +"'))");
		System.out.print("select pk_seq, diengiai from khuvuc where pk_seq in (select khuvuc_fk from nhaphanphoi where trangthai='1' and pk_seq in (select npp_fk from phamvihoatdong where nhanvien_fk='" + this.userId +"'))");	
		return kvRS;
		
	}

	private ResultSet createNppRS(){
		ResultSet nppRS = null;
		String query = "";
		if (this.kvId.equals("0")){
			if(!this.schemeId.equals("0")){
				query = "select b.pk_seq, b.ten, a.ngansach,a.dasudung,a.ngansach - a.dasudung as conlai from phanbokhuyenmai a, nhaphanphoi b where ctkm_fk='" + this.schemeId + "' and b.pk_seq=a.npp_fk and b.pk_seq in (select pk_seq from nhaphanphoi where trangthai='1' and pk_seq in (select npp_fk from phamvihoatdong where nhanvien_fk='"+ this.userId + "'))";
				nppRS = this.db.get(query);
			}
		}else{
			if(!this.schemeId.equals("0")){
				query = "select b.pk_seq, b.ten, a.ngansach,a.dasudung,a.ngansach - a.dasudung as conlai from phanbokhuyenmai a, nhaphanphoi b where ctkm_fk='" + this.schemeId + "' and b.pk_seq=a.npp_fk and b.pk_seq in (select pk_seq from nhaphanphoi where trangthai='1' and khuvuc_fk = '" + this.kvId + "' and pk_seq in (select npp_fk from phamvihoatdong where nhanvien_fk='"+ this.userId + "'))";
				System.out.println(query);
				nppRS = this.db.get(query);
			}
		}
		init(this.tieuchi.trim(),query);
		return nppRS;
	}
	int xemngay(String ngaybatdau,String ngayketthuc)
	{
		String[]ngay1 = ngaybatdau.split("-");
		String[] ngay2= ngayketthuc.split("-");
		Calendar c1 = Calendar.getInstance(); //new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); //new GregorianCalendar();

		//c1.set(2008, 02, 20);
        c1.set(Integer.parseInt(ngay1[0]),Integer.parseInt(ngay1[1]),Integer.parseInt(ngay1[2]));
        c2.set(Integer.parseInt(ngay2[0]),Integer.parseInt(ngay2[1]),Integer.parseInt(ngay2[2]));
		//c2.set(2008, 02 , 22);
		System.out.println("khoang cach 2 ngay"+ (c2.getTime().getTime() - c1.getTime().getTime())/(24*3600*1000));
		int h = Math.round((c2.getTime().getTime() - c1.getTime().getTime())/(24*3600*1000));
		return h;
	}

	
	  public void init(String tieuchi,String sql)
	  {
		  int songay=1;
		  int songaydung=1;
		  String tungay="";
		  String denngay="";
		 // String query ="select tungay,denngay,DATEDIFF(day,year(tungay)+'-'+ day(tungay)+'-'+ month(tungay),year(denngay)+'-'+ day(denngay)+'-'+ month(denngay)) tongsongay ,DATEDIFF(day,year(denngay)+'-'+ day(denngay)+'-'+ month(denngay),year(getdate())+'-'+ day(getdate())+'-'+ month(getdate())) as dadung from ctkhuyenmai where pk_seq ='"+ this.schemeId +"'";
		 
		  String query ="select tungay,denngay,year(getdate())as nam,month(getdate()) as thang,day(getdate()) as ngay  from ctkhuyenmai where pk_seq ='"+ this.schemeId +"'";
		  System.out.println(query);
		  ResultSet tg = db.get(query);
		  
		  try {
			if(tg!=null)  
			while(tg.next())
			  {
				 // songay = Integer.parseInt(tg.getString("tongsongay"));
				 // songaydung = Integer.parseInt(tg.getString("dadung"));
				  tungay = tg.getString("tungay");
				  denngay =tg.getString("denngay");
				  String ngay =tg.getString("nam")+"-"+ tg.getString("thang")+"-"+tg.getString("ngay");
				  songaydung = xemngay(tungay,denngay);
				  songay = xemngay(tungay,ngay);
				  
			  }
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("tong so ngay:" + songay +" tungay :" + tungay +"  denngay "+ denngay);
	 if(sql.length()>0) 
	  { 
		 System.out.println("cau :" + sql);
		 String ds[];
	     int[] mangtam ;
	     mangtam = new int[500];
	     mangtam[0] = 0;
		  ResultSet rs = db.get(sql);
		try {
			if(tieuchi.equals("0"))
			{
					  long tong = 0;
					  int dem = 0;
					  //tieu chi 1
					while(rs.next())
					  {  
						   int dasudung = Integer.parseInt(rs.getString("dasudung"));
						   int ngansach = Integer.parseInt(rs.getString("ngansach"));
						   int pk_seq = Integer.parseInt(rs.getString("pk_seq"));
						   if(dasudung==0)
						   {
							   tong = tong + ngansach;
							   dieuchuyen[pk_seq] = "0";
							 
						   }
						   else
						   {   mangtam[0]++;
						       mangtam[mangtam[0]] = pk_seq;
						       System.out.println(" pkseq :" + pk_seq+ "  ngansach :" + ngansach);
							   dieuchuyen[pk_seq] = ngansach+"";
						   }
						   
					  }
					
					System.out.println("so phan tu mang:"+ mangtam[0]);
					if(mangtam[0] > 0)
					{
				        long so = tong /mangtam[0];
				        for(int i =1;i<=mangtam[0];i++)
				        {    
				        	dieuchuyen[mangtam[i]] = (Integer.parseInt(dieuchuyen[mangtam[i]]) + so)+"";
				        }
					}
		}
		else if(tieuchi.equals("1")) {
			//ket thuc tieu chi 1
		  
			    int tong = 0;
				int h=0;
				mangtam[0] = 0;
				while(rs.next())
				  {   
					   int dasudung = Integer.parseInt(rs.getString("dasudung"));
					   int ngansach = Integer.parseInt(rs.getString("ngansach"));
					   int pk_seq = Integer.parseInt(rs.getString("pk_seq"));
					   if(dasudung * songaydung < ngansach)
					   {
						  h =  ngansach - dasudung*songaydung;
						 tong = tong + h;
						 dieuchuyen[pk_seq] = h+"";
					   }
					   else
					   {
						   dieuchuyen[pk_seq] =rs.getString("ngansach");
						   mangtam[0]++;
						   mangtam[mangtam[0]] = pk_seq;
					   }
					   
				  }
				if(mangtam[0] > 0)
				{
			        long so = tong /mangtam[0];
			        for(int i =1;i<=mangtam[0];i++)
			        {    
			        	dieuchuyen[mangtam[i]] = (Integer.parseInt(dieuchuyen[mangtam[i]]) + so)+"";
			        }
				}
			}
			else
			{
			//chitieu 3
				    int conlai =conkhuyenmai();
					int tongsl = tongsoluong(tungay, denngay);
					while(rs.next())
					{  int dasudung = Integer.parseInt(rs.getString("dasudung"));
					   int ngansach = Integer.parseInt(rs.getString("ngansach"));
					   int pk_seq = Integer.parseInt(rs.getString("pk_seq"));
					   
					   query= "select convert(int,((sum(a.soluong*a.giamua) * '"+ conlai +"')/'"+ tongsl +"')/100) as num from donhang_sanpham a,donhang b where b.npp_fk ='"+ pk_seq +"" +"' and b.ngaynhap >='"+ tungay +"' and b.ngaynhap <= '"+ denngay +"' and a.donhang_fk = b.pk_seq and a.sanpham_fk in (select sanpham_fk from dieukienkm_sanpham a,ctkm_dkkm where a.dieukienkhuyenmai_fk = dkkhuyenmai_fk and ctkhuyenmai_fk ='"+ this.schemeId +"') group by b.npp_fk ";
					 //     System.out.println(" pkseq :"+ query);
					   ResultSet th = db.get(query);
					    
					   if(th.getRow()==0)
					   {
						   dieuchuyen[pk_seq] = dasudung +"";
					   }
					   else
					   {
						   th.next();
						   String tt = th.getString("num");
						   System.out.println("pk_seq:" + tt);
						 
						   dieuchuyen[pk_seq] = (Integer.parseInt(tt) + dasudung)+"";
						  
					   }	
				  }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
			
	}
	  
	  int tongsoluong(String tungay, String denngay)
	  {
		  String query ="select sum(a.soluong)as num from donhang_sanpham a,donhang b where b.ngaynhap >='"+ tungay +"' and b.ngaynhap <= '"+ denngay +"'";
			ResultSet bt = db.get(query);
			try {
				bt.next();
				return Integer.parseInt(bt.getString("num"));
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return 1;
	  }
	  int conkhuyenmai()
	  {
		  String sql ="select sum(ngansach - dasudung) as num from phanbokhuyenmai where ctkm_fk ='"+ this.schemeId +"'";
		  ResultSet rs = db.get(sql);
		  try {
			rs.next();
			return Integer.parseInt(rs.getString("num"));
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		  return 1;
	  }
	public void init(){
		this.schemeRS = this.createSchemeRS();
		this.kv = this.createKvRS();
		this.nppRS = this.createNppRS();
		this.nppstr = this.createNppList();
		if(!this.schemeId.equals("0")){
			this.usedPro = this.getUsedPromotion(this.schemeId);
			this.totalbudget = this.getBudget();
			this.usedbudget = this.getUsedBudget();
		
		}
	}
	
	private String createNppList(){
		ResultSet rs =  this.createNppRS();
		String nppstr = "";
		try{
			int i = 0;
			if(rs != null){
				while(rs.next()){
					if (nppstr.length()==0){
						nppstr = "'" + rs.getString("pk_seq") + "'";
					}else{
						nppstr = nppstr + ",'" + rs.getString("pk_seq") + "'";
					}				
					i++;
				}
				this.size = "" + i;
			}else{
				this.size = "0";				
			}
		}catch(Exception e){}
		return nppstr;
	}
	
	private Hashtable<String, String> getUsedPromotion(String schemeId){
		Hashtable<String, String> ht = new Hashtable<String, String>();
		dbutils db = new dbutils();
		String query;
		if (this.kvId.equals("0")){
			query = "select b.npp_fk as nppId, sum(a.tonggiatri) as amount from donhang_ctkm_trakm a, donhang b where a.donhangid=b.pk_seq and b.trangthai=3 and a.ctkmId = '"+ schemeId +"' and b.npp_fk in (select npp_fk from phamvihoatdong where nhanvien_fk='"+ this.userId + "') group by b.npp_fk";
		}else{
			query = "select b.npp_fk as nppId, sum(a.tonggiatri) as amount from donhang_ctkm_trakm a, donhang b where a.donhangid=b.pk_seq and b.trangthai=3 and a.ctkmId = '"+ schemeId +"' and b.npp_fk in (select pk_seq from nhaphanphoi where trangthai='1' and khuvuc_fk='"+ this.kvId +"' and pk_seq in (select npp_fk from phamvihoatdong where nhanvien_fk='"+ this.userId + "')) group by b.npp_fk";
		}
		ResultSet rs = db.get(query);
		System.out.println(query);
		if(rs != null){
			try{
				while(rs.next()){
					if(rs.getString("nppId") != null){
						ht.put(rs.getString("nppId"), rs.getString("amount"));
					}
				}
					
			}catch(Exception e){}
		}else{
			System.out.print("Error in getUsedPromotion/rs==null");
		}

		db.shutDown();
			
		return ht;
		
	}

	private Hashtable<String, String> getAdjustment(String schemeId){
		Hashtable<String, String> ad = new Hashtable<String, String>();
		if(this.tieuchi.equals("0")){
			ResultSet rs = this.createNppRS();
			float total = Float.valueOf(this.totalbudget).floatValue() - Float.valueOf(this.usedbudget).floatValue();

		
			try{
				while(rs.next()){
					if(this.usedPro.containsKey(rs.getString("pk_seq"))){
						float tmp = total*(Float.valueOf(this.usedPro.get(rs.getString("pk_seq"))).floatValue()/Float.valueOf(this.usedbudget).floatValue());
						ad.put(rs.getString("pk_seq"), ""+ Math.round(tmp));
					}else{				
						ad.put(rs.getString("pk_seq"), "0");
					}
				}
			
			}catch(Exception e){}
			}
		return ad;
	}
	
	public boolean save(HttpServletRequest request){
		//ResultSet rs = this.createNppRS();
	///	String total = "0";
		for(int i = 100000;i < 900000;i++ )
		{   if(this.dieuchuyen[i] != null)
		    {
			  String sql ="update phanbokhuyenmai set ngansach='" + dieuchuyen[i] + "' where ctkm_fk ='" + this.schemeId + "' and npp_fk='"+ i+"" + "'";
			  System.out.println(sql);
			  this.db.update(sql);
		    }
		}
		/*try{
			while(rs.next()){
					
				String adjust = request.getParameter("dc" + rs.getString("pk_seq"));
				while(adjust.contains(",")){
					adjust = adjust.replace(",", "");
				}

				if(this.usedPro.contains(rs.getString("pk_seq"))){
					total = "" + Float.valueOf(this.usedPro.get(rs.getString("pk_seq"))).floatValue() + Float.valueOf(adjust).floatValue();	
				}else{
					total = adjust;
				}
				  
				this.db.update("update phanbokhuyenmai set ngansach='" + total + "' where ctkm_fk ='" + this.schemeId + "' and npp_fk='"+ rs.getString("pk_seq")+ "'");
			}
		}catch(Exception e){
			return false;			
		}
		*/
		return true;
	}
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
   public void setdieuchuyen(String[] dieuchuyen) {
		
		this.dieuchuyen = dieuchuyen;
	}

  public String[] getdieuchuyen() {
		
		return this.dieuchuyen;
	}
  int songay(String ngay1, String ngay2)
  { String[] ngayketthuc = ngay2.split("-");
    String ngaybatdau = ngay2;
    int i =10;
     while(i>1)
     {
    	 if(ngaybatdau.equals(ngayketthuc[0]+"-" + ngayketthuc[1] +"-"+ ngayketthuc[2] ))
    	 {
    		 break;
    	 }else
    	 {
    		 int ngay = Integer.parseInt(ngayketthuc[2])+1;
    		 if(ngay <=9) 
    		 {
    			 ngayketthuc[2] = "0"+ngay;
    		 }
    		 else
    		 {
    			 ngayketthuc[2] = ngay+"";
    		 }
    		 if(ngay==30)
    		 {
    		  	 
    		 }
    		 
    	 }
     }
	  return 0;
  }

}
