package geso.traphaco.distributor.beans.tuyenbanhang.imp;

import geso.traphaco.distributor.beans.tuyenbanhang.ITuyenbanhang;
import geso.traphaco.distributor.db.sql.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Tuyenbanhang implements ITuyenbanhang, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String diengiai;
	String ddkd;
	String ngaylamviec;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String mafast;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet daidienkd;
	String ddkdId;
	
	String ddkdnewId; //dai dien kinh doanh moi (Move tuyen ban hang)
	String diengiainew; //dien giai cua dai dien kinh doanh moi (Move tuyen ban hang)
	String nlvnew; //ngay lam viec New
	
	String tenkhachhang;
	String diachi;
	
	ResultSet kh_tbh_dpt;
	ResultSet kh_tbh_cdpt;
	String[] kh_tbh_dptIds;
	String[] kh_tbh_cdptIds;
	String[] sott;
	String[] tansoList;
	
	//move tuyen ban hang
	ResultSet nlvList;
	ResultSet diengiaiList;
	ResultSet tanso;
	
	dbutils db;
	
	public Tuyenbanhang(String[] param)
	{
		this.id = param[0];
		this.diengiai = param[1];
		this.ddkd = param[2];
		this.ngaylamviec = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.ddkdId = param[8];
		
		this.ddkdnewId = "";
		this.diengiainew = "";
		this.nlvnew = "";
		this.tenkhachhang = "";
		this.diachi = "";
		this.msg = "";
		this.mafast="";
		db = new dbutils();
	}
	
	public Tuyenbanhang(String id)
	{
		this.id = id;
		this.diengiai = "";
		this.ddkd = "";
		this.ngaylamviec = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.ddkdId = "";
		
		this.ddkdnewId = "";
		this.diengiainew = "";
		this.nlvnew = "";
		this.tenkhachhang = "";
		this.diachi = "";
		this.msg = "";
		this.mafast = "";
		db = new dbutils();
	
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getDiengiai() 
	{
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	
	public String getDdkd() 
	{
		return this.ddkd;
	}

	public void setDdkd(String ddkd) 
	{
		this.ddkd = ddkd;
	}
	
	public String getNgaylamviec() 
	{
		return this.ngaylamviec;
	}

	public void setNgaylamviec(String ngaylamviec) 
	{
		this.ngaylamviec = ngaylamviec;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public String getTenkhachhang() 
	{
		return this.tenkhachhang;
	}

	public void setTenkhachhang(String tenkhachhang) 
	{
		this.tenkhachhang = tenkhachhang;
	}

	public String getDiachi() 
	{
		return this.diachi;
	}

	public void setDiachi(String diachi) 
	{
		this.diachi = diachi;
	}

	public ResultSet getDaidienkd() 
	{
		return this.daidienkd;
	}

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	public void setDaidienkd(ResultSet daidienkd) 
	{
		this.daidienkd = daidienkd;
	}
	
	public String getDdkdId() 
	{
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId)
	{
		this.ddkdId = ddkdId;
	}
	
	public ResultSet getKh_Tbh_DptList() 
	{
		return this.kh_tbh_dpt;
	}

	public void setKh_Tbh_DptList(ResultSet kh_tbh_dpt) 
	{
		this.kh_tbh_dpt = kh_tbh_dpt;
	}
	
	public ResultSet getKh_Tbh_CdptList() 
	{
		return this.kh_tbh_cdpt;
	}
	
	public void setKh_Tbh_CdptList(ResultSet kh_tbh_cdpt)
	{
		this.kh_tbh_cdpt = kh_tbh_cdpt;
	}
	
	public Hashtable<Integer, String> getKh_Tbh_DptIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.kh_tbh_dptIds != null){
			int size = (this.kh_tbh_dptIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.kh_tbh_dptIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	public void setKh_Tbh_DptIds(String[] kh_tbh_dptIds) 
	{
		this.kh_tbh_dptIds = kh_tbh_dptIds;
	}
	
	public Hashtable<Integer, String> getKh_Tbh_CdptIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.kh_tbh_cdptIds != null){
			int size = (this.kh_tbh_cdptIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.kh_tbh_cdptIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	public void setTansoList(String[] tansoList)
	{
		this.tansoList = tansoList;
	}
	
	public String[] getTansoList()
	{
		return this.tansoList;
	}
	
	public void setKh_Tbh_CdptIds(String[] kh_tbh_cdptIds) 
	{
		this.kh_tbh_cdptIds = kh_tbh_cdptIds;
	}
	
	public String getDdkdNewId() 
	{
		return this.ddkdnewId;
	}
	
	public void setDdkdNewId(String ddkdnewId) 
	{
		this.ddkdnewId = ddkdnewId;
	}

	public String getDiengiaiNew()
	{
		return this.diengiainew;
	}
	
	public void setDiengiaiNew(String diengiainew)
	{
		this.diengiainew = diengiainew;
	}
	
	public String getNlvNew() 
	{
		return this.nlvnew;
	}

	public void setNlvNew(String nlvnew) 
	{
		this.nlvnew = nlvnew;
	}
	
	private void getNppInfo()
	{		
		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}
	
	public boolean CreateTbh() 
	{ 
		if(this.ddkdId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn trình dược viên";
			return false;
		}
		
		if(!kiemtrakhongtrungthutu ()){
			this.msg="Vui Lòng Nhập Số Thứ Tự Không Được Trùng";
			return false;
		}
		try
		 {
			 db.getConnection().setAutoCommit(false);
			 
			 this.ngaytao = getDateTime();
			 this.nguoisua = this.userId;
			 String ngayid = get_datenumber(this.ngaylamviec);
			 
			 String sql = "select count(*) as num from tuyenbanhang where ngayid = '" + ngayid + "' and ddkd_fk = '" + this.ddkdId +"' AND NPP_FK='"+this.nppId+"' ";
			 ResultSet rs = db.get(sql);
			
			 if(rs.next())
			 {
				if(rs.getInt("num") > 0)
				{
					rs.close();
					this.msg = "Dai dien kinh doanh nay da duoc phan tuyen trong ngay lam viec...";
					db.getConnection().rollback();
					return false;
				}
				
			 }
			if(rs!=null){
				rs.close();
			}
			 String query ="insert into tuyenbanhang(diengiai, ngaylamviec, ngaytao, ngaysua, nguoitao, nguoisua, ddkd_fk, npp_fk,ngayid)";
			 query = query + " values(N'" + this.diengiai + "','" + this.ngaylamviec + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoisua + "','" + this.nguoisua + "','" + this.ddkdId + "','" + this.nppId + "',"+ngayid+")";
			 System.out.println("Tao TBH : "+query);
			 if (!db.update(query))
			 {
				db.update("rollback");
				this.msg = "Khong the luu vao table 'Tuyen Ban Hang' , " + query;
				return false;			
			 } 
			
			 query = "select IDENT_CURRENT('tuyenbanhang') as tbhId";
			 ResultSet rsTbh = this.db.get(query);
			 rsTbh.next();
			 this.id = rsTbh.getString("tbhId");
			 rsTbh.close();
			 
			 //System.out.println("So kahch hang: " + Integer.toString(this.kh_tbh_dptIds.length) + "\n");
			 if(this.kh_tbh_dptIds != null)
			 {
				boolean flag = CheckKhachHang(this.kh_tbh_dptIds);
				if(flag == true)
				{
					db.getConnection().rollback();
					this.msg = "Khong the cho khach hang trung nhau vao tuyen, vui long chon lai";
					return false;	
				}
				
				
				int size = this.kh_tbh_dptIds.length;
				int m = 0;
			
				while (m < size )
				{
					String khIds = this.kh_tbh_dptIds[m].substring(0, this.kh_tbh_dptIds[m].indexOf(";"));
					System.out.println("Kh ID : "+khIds);
					String tsIds = this.tansoList[m];
				
					if(tsIds == null)
						tsIds = "";
						int thutu=0;
					
						try{
							thutu=Integer.parseInt(this.sott[m]);
							
						}catch(Exception er){
							
						}
					query = "insert into khachhang_tuyenbh(khachhang_fk,tbh_fk,tanso,sott) values('" + khIds + "','" + this.id + "','" + tsIds + "','"+thutu+"')";					
					if( !db.update(query))
					{
						db.update("rollback");
						this.msg = "Khong the luu vao table 'KhachHang_TuyenBH' , " + query;
						return false;
					}
					m++;	
				}
			}
			 
			//Neu TDV thuoc 2 doi tac, thi tu tao tuyen ma chua co khach hang
			 query = "select npp_fk, ( select count(*) from TUYENBANHANG where ddkd_fk = a.ddkd_fk and npp_fk = a.npp_fk and ngayId = '2' ) as  exitsTBH " +
			 		 "from DAIDIENKINHDOANH_NPP a where ddkd_fk = '" + this.ddkdId + "' and npp_fk != '" + this.nppId + "' ";
			 rs = db.get(query);
			 if(rs != null)
			 {
				 if(rs.next())
				 {
					 if(rs.getInt("exitsTBH") <= 0 )
					 {
						 query = "insert into tuyenbanhang(diengiai, ngaylamviec, ngaytao, ngaysua, nguoitao, nguoisua, ddkd_fk, npp_fk, ngayid) " +
						 		 "values(N'" + this.diengiai + "','" + this.ngaylamviec + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoisua + "','" + this.nguoisua + "','" + this.ddkdId + "','" + rs.getString("npp_fk") + "',"+ngayid+")";
						 System.out.println("Tao TBH cua NPP thu 2: "+query);
						 db.update(query);
					 }
				 }
				 
				 rs.close();
			 }
		
			 
			String check=" select d.KHACHHANG_FK, COUNT(d.DDKD_FK) as num from  \n"+
						 "	 (  \n"+ 
						 "	  select distinct k.KHACHHANG_FK, t.DDKD_FK  \n"+
						 "	  from KHACHHANG_TUYENBH k inner join TUYENBANHANG t on k.TBH_FK = t.PK_SEQ  \n"+
						 "	  where exists (select 1 from DAIDIENKINHDOANH where PK_SEQ = t.DDKD_FK and TRANGTHAI = 1)  \n"+
						 "	 ) d   \n"+
						 "	 where exists (select 1 from KHACHHANG where PK_SEQ = d.KHACHHANG_FK and TRANGTHAI = 1)   \n"+
						 "	 group by d.KHACHHANG_FK having COUNT(d.DDKD_FK) > 1";
			ResultSet rscheck=db.get(check);
				if(rscheck.next())
				{
					db.update("rollback");
					this.msg = "tồn tại một khách hàng thuộc 2 trình dược viên";
					return false;
				}
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);	
		}
		catch(Exception e){
			db.update("rollback");
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false;
			}
		return true;
	}
	
	private boolean CheckKhachHang(String[] khIds) 
	{
		List<String> kq = new ArrayList<String>();
		for(int  i = 0; i < khIds.length; i++)
		{
			if(!kq.contains(khIds[i].trim()))
			{
				kq.add(khIds[i]);
			}
		}
		if (kq.size() < khIds.length)
			return true;
		return false;
	}

	private String get_datenumber(String ngaylamviec2) {
		
		if(ngaylamviec2.equals("Thu hai")){
			return "2";
		}else if(ngaylamviec2.equals("Thu ba")){
			return "3";
		}else if(ngaylamviec2.equals("Thu tu")){
			return "4";
		}else if(ngaylamviec2.equals("Thu nam")){
			return "5";
		}else if(ngaylamviec2.equals("Thu sau")){
			return "6";
		}else if(ngaylamviec2.equals("Thu bay")){
			return "7";
		}
		return "";
	}
	/*private String get_datenumber1(String ngaylamviec2) {
		
		if(ngaylamviec2.equals("2")){
			return "Thu hai";
		}else if(ngaylamviec2.equals("3")){
			return "Thu ba";
		}else if(ngaylamviec2.equals("4")){
			return "Thu tu";
		}else if(ngaylamviec2.equals("5")){
			return "Thu nam";
		}else if(ngaylamviec2.equals("6")){
			return "Thu sau";
		}else if(ngaylamviec2.equals("7")){
			return "Thu bay";
		}
		return "";
	}*/
	
	public boolean UpdateTbh() 
	{
		if(this.ddkdId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn trình dược viên";
			return false;
		}
		
		if(!kiemtrakhongtrungthutu ()){
			this.msg="Vui Lòng Nhập Số Thứ Tự Không Được Trùng";
			return false;
		}
		String ngayid=get_datenumber(this.ngaylamviec);
		if(ngayid.equals("")){
			this.msg="Khong the lay ngay lam viec,vui long thu lai,neu khong duoc lien he voi admin de xu ly loi nay";
			return false;
		}
	   if(!kiemtra_duynhat(ngayid))
	   {
			try
			{
				db.getConnection().setAutoCommit(false);
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			
			// Update table "Tuyen Ban Hang"
			String query = "update tuyenbanhang set diengiai= N'" + this.diengiai + "', ngaylamviec='" + this.ngaylamviec +"', ddkd_fk='"+ this.ddkdId + "', nguoisua='" + this.nguoisua + "', ngayid="+ngayid+"  where pk_seq = '" + this.id + "'" ;
			System.out.println("Cau lenh cap nhat tuyen update tuyen:"+query );
			if(!this.db.update(query)){
				db.update("rollback");
				this.msg = "Khong the cap nhat table 'Tuyen Ban Hang' , " + query;
				return false; 
			}
			query = "delete from khachhang_tuyenbh where tbh_fk= '" + this.id + "'" ;
			
			if(!this.db.update(query)){
				db.update("rollback");
				this.msg = "Loi khi cap nhat bang "+query;
				return false; 
			}
			
			// Update table "khachhang_tuyenbh"
			if(this.kh_tbh_dptIds != null)
			{
				int size = this.kh_tbh_dptIds.length;
				int m = 0;
			
				while (m < size )
				{
					String khIds = this.kh_tbh_dptIds[m].substring(0, this.kh_tbh_dptIds[m].indexOf(";"));
					System.out.println("Khachhang Id : "+khIds );
					String tsIds = this.tansoList[m];
					int thutu=0;
					
					try{
						thutu=Integer.parseInt(this.sott[m]);
						
					}catch(Exception er){
						
					}
					if(tsIds == null)
						tsIds = "";
				query = "insert into khachhang_tuyenbh(khachhang_fk,tbh_fk,tanso,sott) values('" + khIds + "','" + this.id + "','" + tsIds + "','"+thutu+"')";
				System.out.println("Insert Khasch Hang tuyen :"+query);
				if( !db.update(query))
				{
					db.update("rollback");
					this.msg = "Khong the luu vao table 'KhachHang_TuyenBH' , " + query;
					return false;
				}
				m++;	
				}
			}
		
			String check=" select d.KHACHHANG_FK, COUNT(d.DDKD_FK) as num from  \n"+
					 "	 (  \n"+ 
					 "	  select distinct k.KHACHHANG_FK, t.DDKD_FK  \n"+
					 "	  from KHACHHANG_TUYENBH k inner join TUYENBANHANG t on k.TBH_FK = t.PK_SEQ  \n"+
					 "	  where exists (select 1 from DAIDIENKINHDOANH where PK_SEQ = t.DDKD_FK and TRANGTHAI = 1)  \n"+
					 "	 ) d   \n"+
					 "	 where exists (select 1 from KHACHHANG where PK_SEQ = d.KHACHHANG_FK and TRANGTHAI = 1)   \n"+
					 "	 group by d.KHACHHANG_FK having COUNT(d.DDKD_FK) > 1";
		ResultSet rscheck=db.get(check);
			if(rscheck.next())
			{
				db.update("rollback");
				this.msg = "tồn tại một khách hàng thuộc 2 trình dược viên";
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			}catch(Exception er){
				db.update("rollback");
				this.msg="Khong The Sua Tuyen Ban Hang Nay,Vui Long Thu Lai,Neu Khong Duoc Lien He Voi Admin De Duoc Giup Do. Loi Dong Lenh Sau :" +er.toString() ;
				return false;
			}
	   }
	   else
		{   
			this.msg="khach hang nay da phan tuyen ngay lam "+ this.ngaylamviec.trim()+" viec roi";
			return false;
		}
		return true;
	}
	
	private boolean kiemtrakhongtrungthutu() {
		// TODO Auto-generated method stub
		if(sott!=null)
		{
			for(int i=0;i<this.sott.length-1 ;i++){
				for(int j=i+1;j<this.sott.length;j++){
					System.out.println("a"+sott[i]);
					System.out.println("b"+sott[j]);
					if(sott[i].equals(sott[j])){
						return false;
					}
				}
			}
		}
		return true;
	}

	boolean kiemtra()
	{
		String sql ="select count(*) as num from tuyenbanhang where ngaylamviec = '"+ this.ngaylamviec.trim() + "' and ddkd_fk = '"+ this.ddkdId +"' AND NPP_FK='"+this.nppId+"' ";
		System.out.println("tuyen " + sql);
		ResultSet rs = db.get(sql);
		try 
		{
			if(rs.next())
			{
				if(rs.getInt("num") > 0)
					return true;
			}
			rs.close();
		} 
		catch(Exception e) {}
		
		return false;
	}
	boolean kiemtra_duynhat(String ngayid )
	{
		String sql ="select count(*) as num from tuyenbanhang where ngayid ='"+ngayid+"' and ddkd_fk ='"+ this.ddkdId +"' and npp_fk='"+this.nppId+"' and pk_seq <>'"+ this.id +"'";
		System.out.println("tuyen " + sql);
		ResultSet rs = db.get(sql);
		try 
		{
			rs.next();
			if(Integer.parseInt(rs.getString("num")) >0)
				return true;
			rs.close();
		} 
		catch(Exception e) {}
		
		return false;
	}
	public boolean MoveTbh(String tbhOld, String tbhNew, String[] khIds, String[] tansoIds,String [] sotts ) //Move cac khach hang thuoc tuyen ban hang cu sang tuyen ban hang moi 
	{
		
		try{
			this.db.getConnection().setAutoCommit(false);
			
			String query="delete from khachhang_tuyenbh where tbh_fk= '" + tbhOld + "'";
			if( !this.db.update(query))
			{
	    		this.db.update("rollback");
				this.msg = "Khong the di chuyen sang tuyen ban hang moi , " + query;
				return false;
			}
	
		if(khIds != null)
		{
			int size = khIds.length;
			int m = 0;	
			//"Thu hai", "Thu ba", "Thu tu", "Thu nam", "Thu sau", "Thu bay"
			
			//lay số thứ tự lớn nhất của tbh moi
			query="select max(sott) as sottmax from khachhang_tuyenbh where tbh_fk="+tbhNew;
			System.out.println(query);
			
			ResultSet rs=this.db.get(query);
			int sottmax=0;
			if(rs.next()){
				sottmax=rs.getInt("sottmax");
				
			}
			rs.close();
			
			while (m < size )
			{
				
				 if(!kiemtratrungtuyen(khIds[m]))	 
				 {
					sottmax=sottmax+1;
			    	query = "insert into khachhang_tuyenbh(khachhang_fk,tbh_fk,tanso,sott) values('" + khIds[m] + "','" + tbhNew + "','" + tansoIds[m] + "','"+sottmax+"')";
			    	System.out.println("luu thong tin:"+ query);
			    	if( !this.db.update(query))
					{
			    		this.db.update("rollback");
						this.msg = "Khong the di chuyen sang tuyen ban hang moi , " + query;
						return false;
					}
				 }
				m++;	
			}
		}
		
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		return true;
		}catch(Exception er){
			this.db.update("rollback");
			this.msg="Loi : "+ er.toString();
			return false;
		
		}
		
		
	}
	boolean kiemtratrungtuyen(String kh)
	{
		String sql ="select count(*) as num from khachhang_tuyenbh where khachhang_fk ='"+ kh +"'";
		ResultSet rs= db.get(sql);
		try {
			rs.next();
			if(rs.getString("num").equals("0"))
				return false;
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
		}}
		
		
		return true;
	}
	
	public void createDdkdRS()
	{
		/*this.daidienkd = this.db.get("select pk_seq as ddkdId, ten +' - ' +isnull(maNhanVien,'')  as ddkdTen  " +
									 "from daidienkinhdoanh where npp_fk='" + this.nppId + "' order by ten");*/
		
		this.daidienkd = this.db.get("select pk_seq as ddkdId, ten +' - ' +isnull(maNhanVien,'')  as ddkdTen  " +
				 						"from daidienkinhdoanh where pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' )  order by ten");
	}
	
	public void createKh_TbhList()
	{ 
		String query = "select a.pk_seq as khId,a.smartid, a.maFAST, a.ten, a.diachi, b.tanso,isnull(b.sott,0) as sott \n";
		query = query + " from khachhang a inner join khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk where b.tbh_fk='" + this.id + "' order by b.sott \n";
		
		System.out.println("2.Query tuyen: " + query);
		this.kh_tbh_dpt = this.db.get(query);
		
		String query2 = "select pk_seq as khId, smartid, maFAST, ten, diachi from khachhang where  trangthai = '1' and npp_fk='" + this.nppId + "' \n";
		query2+=" and pk_seq not in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in(select pk_seq from tuyenbanhang where ddkd_fk!='"+this.ddkdId+"')) ";
		
		if(this.id != "")
			query2 = query2 + " and pk_seq not in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk='" + this.id + "') \n";
	
		query2 += " order by pk_seq desc";
		
		System.out.println("3.Query lay khach hang: " + query2 + "\n");
		
		this.kh_tbh_cdpt = this.db.get(query2);
	}
	public void createRS() 
	{
		this.getNppInfo();
		this.createDdkdRS();
		this.createKh_TbhList();
	}

	public void init()
	{
		String query = "select a.pk_seq as tbhId, a.diengiai as tbhTen, a.ngaytao, a.ngaysua, a.ngaylamviec, b.ten as nguoitao, c.ten as nguoisua, d.pk_seq as ddkdId, d.ten as ddkdTen, e.ten as nppTen, e.pk_seq as nppId";
	    query = query + " from tuyenbanhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq inner join daidienkinhdoanh d on a.ddkd_fk = d.pk_seq";
	    query = query + " inner join nhaphanphoi e on a.npp_fk = e.pk_seq where a.pk_seq='" + this.id + "'";
        
	    System.out.println("1.Khoi tao tuyen ban hang: " + query);
	    ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("tbhId");
        	this.ddkd = rs.getString("ddkdTen");
        	this.diengiai = rs.getString("tbhTen");
        	this.ngaylamviec = rs.getString("ngaylamviec");
        	this.ngaytao = rs.getDate("ngaytao").toString();
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getDate("ngaysua").toString();
        	this.nguoisua = rs.getString("nguoisua");       	
        	this.ddkdId = rs.getString("ddkdId");       	
        	//this.msg = "";
       	}
        catch(Exception e){}
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {

		}}
       	this.createRS(); 
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public ResultSet getNlvList()
	{
		return this.nlvList;
	}

	public void setNlvList(ResultSet nlvList) 
	{
		this.nlvList = nlvList;
	}

	public ResultSet getDiengiaiList() 
	{
		return this.diengiaiList;
	}

	public void setDiengiaiList(ResultSet diengiaiList) 
	{
		this.diengiaiList = diengiaiList;
	}

	public void createNlvList()
	{
		String query = "select distinct ngaylamviec as nlvNewTen from tuyenbanhang";
		if(this.ddkdnewId != "")
			query = query + " where ddkd_fk='" + this.ddkdnewId + "'";
		this.nlvList = this.db.get(query + " order by ngaylamviec");
	}

	public void createDiengiaiList()
	{
		String query = "select diengiai as tbhNewTen from tuyenbanhang where npp_fk='" + this.nppId + "'";
		if(this.nlvnew != "")
			query = query + " and ngaylamviec like '%" + this.nlvnew + "%'";
		if(this.ddkdnewId != "")
			query = query + " and ddkd_fk = '" + this.ddkdnewId + "'";
		this.diengiaiList = this.db.get(query + " order by diengiai");
	}

	@Override
	public void DBclose() {
		
		try {

			if(this.daidienkd != null)
				this.daidienkd.close();
			if(this.diengiaiList != null)
				this.diengiaiList.close();
			if(this.kh_tbh_cdpt != null)
				this.kh_tbh_cdpt.close();
			if(this.kh_tbh_dpt != null)
				this.kh_tbh_dpt.close();
			if(this.nlvList != null)
				this.nlvList.close();
			if(this.tanso != null)
				this.tanso.close();
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
			
		}
		
	}
	
	@Override
	public void setSoTT(String[] _sott) {
		
		this.sott=_sott;
	}


	public String getmaFast() {
		
		return this.mafast;
	}


	public void setmaFast(String mafast) {
		this.mafast = mafast;
		
	}

	
	
}
