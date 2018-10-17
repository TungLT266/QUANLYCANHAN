package geso.traphaco.center.beans.capnhatnhanvien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;




import geso.traphaco.center.beans.capnhatnhanvien.ICapnhatnhanvien;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

public class Capnhatnhanvien implements ICapnhatnhanvien {

	String Ten;
	String Ngaysinh;
	String Diachi;
	String Dienthoai;
	String Email;
	String Tendangnhap;
	String matkhau;
	//String Trangthai;
	String Phanloai;
	String userId;
	ResultSet quyen;
	String nppId;
	String userTen;
	String trangthai;
	ResultSet quyenchon;
	String Xuathoadon ="";
	
	String Id;
	String msg;
	ResultSet kenh;
	ResultSet kenhchon;
	ResultSet npp;
	ResultSet nppchon;
	ResultSet sanpham;
	ResultSet sanphamchon;
	ResultSet kho;
	ResultSet khochon;
	String vungId;
	ResultSet vung;
	String khuvucId;
	ResultSet khuvuc;
	String nhanhangId;
	ResultSet nhanhang;
	String chungloaiId;
	ResultSet chungloai;
	ResultSet nhaphanphoi;
	
	//dành cho lập kế hoạch nhân viên
	String loai = "0"; //Loại nhân viên: 0: nhân viên | 1: RSM | 2: ASM | 3: SS
	String loaiId = ""; //Mã RSM | ASM | SS tùy loại nhân viên
	ResultSet loaiRs;
	ResultSet DanhmucquyenRs;
	
	String sohoadontu;
	String sohoadonden;
	
	dbutils db;
	String chon;
	String nppIds;
	
	ResultSet diabanRs;
	String diabanIds;
	
	ResultSet tinhthanhRs;
	String tinhthanhIds;
	
	String quyenId;
	
	ResultSet phongbanRs;
	String phongbanId;
	
	public Capnhatnhanvien()
	{   
		this.Id ="";
		this.Ten="";
		this.Ngaysinh="";
		this.Dienthoai="";
		this.Email="";
		this.Tendangnhap="";
		this.matkhau = " ";
		this.Phanloai="";
		this.userId="";
		this.nppId="";
		this.userTen="";
		this.trangthai="";
		this.vungId ="";
		this.khuvucId ="";
		this.nhanhangId ="";
		this.chungloaiId="";
		this.Phanloai ="";
		this.chon ="1";
		this.msg="";
		this.nppIds="";
		this.kenhId="";
		this.activeTab="0";
		
		this.quyenId = "";
		this.sohoadontu="";
		this.sohoadonden = "";
		this.qhId="";
		this.ttId="";
		this.diabanIds = "";
		this.tinhthanhIds = "";
		this.phongbanId = "";
		db = new dbutils();
	}
	public Capnhatnhanvien(String Id)
	{   
		this.Id = Id;
		this.userId="";
		this.nppId="";
		this.matkhau = " ";
		this.userTen="";
		this.trangthai="";
		this.vungId ="";
		this.khuvucId ="";
		this.nhanhangId ="";
		this.chungloaiId="";
		this.chon ="1";
		this.Phanloai="";
		this.msg="";
		this.nppIds="";
		this.kenhId="";
		this.activeTab="0";
		
		this.sohoadontu="";
		this.sohoadonden = "";
		this.quyenId = "";
		this.qhId="";
		this.ttId="";
		this.diabanIds = "";
		this.tinhthanhIds = "";
		
		this.phongbanId = "";
		
		db = new dbutils();
	}
	
	
	public void setuserId(String userId) {
		
		this.userId = userId;
	}
	
	public String getuserId() {
		
		return userId;
	}
	
	public void setnppId(String nppId) {
		
		this.nppId = nppId;
	}
	
	public String getnppId() {
		
		return this.nppId;
	}
	
	public void setTen(String Ten) {
		
		this.Ten = Ten;
	}
	
	public String getTen() {
		
		return this.Ten;
	}
	
	public void setngaysinh(String ngaysinh) {
		
		this.Ngaysinh = ngaysinh;
	}
	
	public String getngaysinh() {
		
		return this.Ngaysinh;
	}
	
	public void setemail(String email) {
		
		this.Email = email;
	}
	
	public String getemail() {
		
		return this.Email;
	}
	
	public void setdienthoai(String dienthoai) {
		
		this.Dienthoai = dienthoai;
	}
	
	public String getdienthoai() {
		
		return this.Dienthoai;
	}
	
	public void settendangnhap(String tendangnhap) {
		
		this.Tendangnhap = tendangnhap;
	}
	
	public String gettendangnhap() {
		
		return this.Tendangnhap;
	}
	
	public void setmatkhau(String matkhau) {
		
		this.matkhau = matkhau;
	}
	
	public String getmatkhau() {
		
		return this.matkhau;
	}

	public void setphanloai(String phanloai) {
		
		this.Phanloai = phanloai;
	}
	
	public String getphanloai() {
		
		return this.Phanloai;
	}
	
	public void setquyen(ResultSet quyen) {
		
		this.quyen = quyen;
	}
	
	
	public void init() 
	{	
		if(this.Id.length() > 0)
		{
			String sql =" select ten,isnull(ngaysinh, ' ') as ngaysinh, isnull(dienthoai,' ') as dienthoai, isnull(email, ' ' ) as email,dangnhap,isnull(phanloai,'') as phanloai,isnull(convsitecode,'') as convsitecode,trangthai, isnull(loai, 0) as loai, bm_fk, asm_fk, gsbh_fk, ddkd_fk, nvgn_fk, " +
					" isnull(sohoadontu,'') as sohoadontu, isnull(sohoadonden,'') as sohoadonden, isnull(xuathoadon,'') as xuathoadon, phongban_fk   " +
					" from nhanvien where pk_seq ='"+ this.Id+"'";
			System.out.println(sql);
			ResultSet rs = db.get(sql);
			try {
				while(rs.next())
				{
					this.Ten= rs.getString("ten");
					if(rs.getString("ngaysinh") == null || rs.getString("ngaysinh").equals("null") || rs.getString("ngaysinh").equals("NULL") )
						this.Ngaysinh = " ";
					else
						this.Ngaysinh = rs.getString("ngaysinh");

					if(rs.getString("dienthoai") == null || rs.getString("dienthoai").equals("null") || rs.getString("dienthoai").equals("NULL") )
						this.Dienthoai = " ";
					else
						this.Dienthoai=rs.getString("dienthoai");

					if(rs.getString("email") == null || rs.getString("email").equals("null") || rs.getString("email").equals("NULL") )
						this.Email = " ";
					else
						this.Email = rs.getString("email");

					this.Tendangnhap=rs.getString("dangnhap");

					this.Phanloai=rs.getString("phanloai");

					this.sohoadontu = rs.getString("sohoadontu");
					this.sohoadonden = rs.getString("sohoadonden");
					this.Xuathoadon = rs.getString("Xuathoadon");
					this.phongbanId = rs.getString("phongban_fk");

					this.nppId = rs.getString("convsitecode");
					System.out.println("NPPID "+this.nppId );

					this.userId="";

					this.userTen="";

					this.trangthai=rs.getString("trangthai");

					//if(this.Phanloai.equals("2")) {
					this.loai = rs.getString("loai");
					if(this.loai == null) this.loai = "0";
					if(this.loai.equals("1")) {
						this.loaiId = rs.getString("bm_fk") == null ? "" :  rs.getString("bm_fk");
					} else if(this.loai.equals("2")) {
						this.loaiId = rs.getString("asm_fk") == null ? "" :  rs.getString("asm_fk");
					} else if(this.loai.equals("3")) {
						this.loaiId = rs.getString("gsbh_fk") == null ? "" :  rs.getString("gsbh_fk");
					}else if(this.loai.equals("4")) {
						this.loaiId = rs.getString("ddkd_fk") == null ? "" :  rs.getString("ddkd_fk");
					}
					else if(this.loai.equals("6")) {
						this.loaiId = rs.getString("nvgn_fk") == null ? "" :  rs.getString("nvgn_fk");
					}
					//}

					CreateLoaiRs();

					this.msg="";
				}
				rs.close();
				
				String	query = "select npp_fk from phamvihoatdong where nhanvien_fk='"+this.Id+"'";
				rs = db.get(query);
				while(rs.next())
				{
					this.nppIds += rs.getString("npp_fk")+",";
				}
				rs.close();
				
				if(this.nppIds.length() > 0)
				{
					this.nppIds = this.nppIds.substring(0,this.nppIds.length()-1);
				}
				
				//NHÓM QUYỀN
				query = "select diaban_fk from NHANVIEN_DIABAN where nhanvien_fk = '" + this.Id + "'";
				rs = db.get(query);
				while(rs.next())
				{
					this.diabanIds += rs.getString("diaban_fk") + ",";
				}
				rs.close();
				
				if(this.diabanIds.length() > 0)
				{
					this.diabanIds = this.diabanIds.substring(0,this.diabanIds.length()-1);
				}
				
				query = "select tinhthanh_fk from NHANVIEN_TINHTHANH where nhanvien_fk = '" + this.Id + "'";
				rs = db.get(query);
				while(rs.next())
				{
					this.tinhthanhIds += rs.getString("tinhthanh_fk") + ",";
				}
				rs.close();
				
				if(this.tinhthanhIds.length() > 0)
				{
					this.tinhthanhIds = this.tinhthanhIds.substring(0,this.tinhthanhIds.length()-1);
				}
	

				if(this.Phanloai.equals("1"))
				{
					query = " SELECT a.Ten, a.PK_SEQ FROM DANHMUCQUYEN a inner join PHANQUYEN b on a.PK_SEQ = b.DMQ_fk \n"+
							"  where Nhanvien_fk ='"+this.Id+"' \n";
					rs=db.get(query);

					while(rs.next())
					{
						this.quyenId+=rs.getString("PK_SEQ");
					}

					if(rs!=null)
					{
						rs.close();
					}
				}	
			} 
			catch(Exception e) {

				e.printStackTrace();
			}
		}
	}
   
	public ResultSet getquyen() {
		
		return this.quyen;
	}
	public void setquyenchon(ResultSet quyenchon) {
	
		this.quyenchon = quyenchon;
	}

	public ResultSet getquyenchon() {
	
		return this.quyenchon;
	}
	
	public void settrangthai(String trangthai) {
		
		this.trangthai = trangthai;
	}
	
	public String gettrangthai() {
		
		return this.trangthai;
	}
	
	public void setmsg(String msg) 
	{
		this.msg = msg;
	}
	
	public String getmsg() {
		
		return this.msg;
	}
	
	
	boolean kiemtra()
	{ 
		String sql = " select count(*) as num  from nhanvien where pk_seq ='"+ this.Id +"' and pk_seq ='"+ this.userId +"'";
		ResultSet rs = db.get(sql);
		try {
			if(rs.next())
			{
				if(rs.getString("num").equals("0"))
					return false;
			}
			rs.close();
		} catch(Exception e) {
			
		}
		return true;
	}
	
	public boolean save( String[] quyenSelected ) 
	{
		if(this.Id.length() > 0 )
		{
			if(kiemtra())
			{
				this.msg ="Bạn không được cập nhật quyền cho chính mình";
				return false;
			}
		}
		
		/*if(this.Ten ==""|| this.Ngaysinh =="" ||this.Tendangnhap=="")
		{  
			this.msg ="Bạn phải nhập đầy đủ thông tin";
			return false;
		}*/
		
		if( this.Tendangnhap == "" )
		{  
			this.msg = "Vui lòng nhập tên đăng nhập ";
			return false;
		}
		
		if(KiemTra_TenDangNhap() != 0 )
		{
			this.msg = "Tên đăng nhập này đã có người sử dụng,vui lòng đổi lại";
			return false;
		}			
		
		if(this.loai == null || this.loai.trim().length() <= 0 ) {
			this.loai = "0";
		}
		
		String convSite = this.nppId.trim().length() <= 0 ? "NULL" : this.nppId;
		
		if( this.Id.length() > 0 )
		{	
			String sql;
			String bm_fk = null, asm_fk = null, gsbh_fk = null, ddkd_fk = null, nvgn_fk = null;
			boolean hople = true;
			
			if(loai.equals("1")) 
			{
				bm_fk = loaiId;
				if( loaiId.trim().length() <= 0 )
					hople = false;
			} 
			else if (loai.equals("2")) 
			{
				asm_fk = loaiId;
				if( loaiId.trim().length() <= 0 )
					hople = false;
			} 
			else if (loai.equals("3")) 
			{
				gsbh_fk = loaiId;
				if( loaiId.trim().length() <= 0 )
					hople = false;
			} 
			else if (loai.equals("4")) 
			{
				ddkd_fk = loaiId;
				if( loaiId.trim().length() <= 0 )
					hople = false;
			}
			else if (loai.equals("6")) 
			{
				nvgn_fk = loaiId;
				if( loaiId.trim().length() <= 0 )
					hople = false;
			}
			
			if( !hople )
			{
				this.msg = "Vui lòng chọn đối tượng cho loại nhân viên tương ứng";
				return false;
			}
			
			sql = " update nhanvien set dangnhap = N'" + this.Tendangnhap + "'" ;
			if( this.matkhau.trim().length() > 0 ) {
				sql += " , matkhau = pwdencrypt('" + this.matkhau.trim() + "') " ;
			}
					
			sql += " ,ten = N'" + this.Ten + "', ngaysinh = '" + this.Ngaysinh + "', email = '" + this.Email + "', dienthoai = '" + this.Dienthoai + "', phongban_fk = "+ (this.phongbanId.trim().length() <= 0 ? "NULL" : this.phongbanId) ;
			sql += " ,trangthai = '"+this.trangthai+"', ngaysua ='"+ this.getDateTime() +"', nguoisua ='"+ this.userId+"', phanloai= '"+ this.Phanloai +"', sessionid='"+this.getDateTime()+"', loai = "+loai+", bm_fk = "+bm_fk+", asm_fk = "+asm_fk+", gsbh_fk = "+gsbh_fk+", ddkd_fk = " + ddkd_fk + ", nvgn_fk = " + nvgn_fk + ", convsitecode= "+ convSite +", xuathoadon = '" + this.Xuathoadon + "' where pk_seq ='"+ this.Id +"'";
			
			System.out.println("lenh update: " + sql);
			try 
			{
				db.getConnection().setAutoCommit(false);
				
				if(!db.update(sql))
				{	
					db.update("rollback");
					this.msg = sql;
					return false;
				}
				
				if(!createUpdate(db, quyenSelected))
				{
					return false;
				}
					
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} 
			catch(Exception e) 
			{
				db.get("rollback");
				e.printStackTrace();
			}
		}
		else
		{      
			String bm_fk = null, asm_fk = null, gsbh_fk = null, ddkd_fk = null, nvgn_fk = null;
			boolean hople = true;
			
			if(loai.equals("1")) 
			{
				bm_fk = loaiId;
				if( loaiId.trim().length() <= 0 )
					hople = false;
			} 
			else if (loai.equals("2")) 
			{
				asm_fk = loaiId;
				if( loaiId.trim().length() <= 0 )
					hople = false;
			} 
			else if (loai.equals("3")) 
			{
				gsbh_fk = loaiId;
				if( loaiId.trim().length() <= 0 )
					hople = false;
			} 
			else if (loai.equals("4")) 
			{
				ddkd_fk = loaiId;
				if( loaiId.trim().length() <= 0 )
					hople = false;
			}
			else if (loai.equals("6")) 
			{
				nvgn_fk = loaiId;
				if( loaiId.trim().length() <= 0 )
					hople = false;
			}
			
			if( !hople )
			{
				this.msg = "Vui lòng chọn đối tượng cho loại nhân viên tương ứng";
				return false;
			}

			String sql = "insert into nhanvien(ten,ngaysinh,dangnhap,matkhau,email,dienthoai,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,phanloai, sessionid, loai, bm_fk, asm_fk, gsbh_fk, ddkd_fk, nvgn_fk, xuathoadon, convsitecode, phongban_fk) " +
						 "values(N'"+ this.Ten +"','"+ this.Ngaysinh +"','"+this.Tendangnhap.trim()+"',pwdencrypt('"+ this.matkhau.trim() + "'),'"+ this.Email+"','"+this.Dienthoai+"','"+this.trangthai+"','"+ this.getDateTime() +"','"+ this.getDateTime() +"','"+ this.userId+"','"+ this.userId+"','"+ this.Phanloai +"', '2012-01-01', " + this.loai + ", " + bm_fk + ", " + asm_fk + ", " + gsbh_fk + ", " + ddkd_fk + ", " + nvgn_fk + ",'"+this.Xuathoadon+"', " + convSite + ", "+(this.phongbanId.trim().length() <= 0 ? "NULL" : this.phongbanId)+") ";

			System.out.println("[Capnhatnhanvien.save] sql = " + sql);
			try 
			{
				db.getConnection().setAutoCommit(false);
				if(!db.update(sql))
				{	
					db.update("rollback");
					this.msg =sql;
					return false;
				}

				sql = "select IDENT_CURRENT('nhanvien') as nv";
				ResultSet rsDh = this.db.get(sql);						
				rsDh.next();
				this.Id = rsDh.getString("nv");
				rsDh.close();

				if(!createUpdate(db, quyenSelected))
				{  
					this.Id = "";
					return false;
				}
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} 
			catch(Exception e) 
			{
				this.db.update("rollback");
				e.printStackTrace();
			}
		}
		
		
		/*if( loaiId.trim().length() > 0 )
		{
			sql = "";
			if(loai.equals("1")) //BM
			{
				bm_fk = loaiId;
			} 
			else if (loai.equals("2")) //ASM
			{
				asm_fk = loaiId;
			} 
			else if (loai.equals("3")) //GSBH
			{
				sql = "update a set  a.TEN = dbo.XuLyData( a.TEN, b.TEN ), a.DIENTHOAI = dbo.XuLyData( a.DIENTHOAI, b.DIENTHOAI ), a.EMAIL = dbo.XuLyData( a.EMAIL, b.EMAIL )  "+
					  "from NHANVIEN a inner join DAIDIENKINHDOANH b on a.DDKD_FK = b.PK_SEQ "+
					  "where a.PK_SEQ = '" + this.Id + "' ";
			} 
			else if (loai.equals("4")) //NVBH
			{
				sql =  "update a set  a.TEN = dbo.XuLyData( a.TEN, b.TEN ), a.NGAYSINH = dbo.XuLyData( a.NGAYSINH, b.NgaySinh ), a.DIENTHOAI = dbo.XuLyData( a.DIENTHOAI, b.DIENTHOAI ), a.EMAIL = dbo.XuLyData( a.EMAIL, b.EMAIL ) "+
					   "from NHANVIEN a inner join GIAMSATBANHANG b on a.GSBH_FK = b.PK_SEQ "+
					   "where a.PK_SEQ = '" + this.Id + "' ";
			}
			
			if( sql.trim().length() > 0 )
			{
				if( !db.update(sql) )
				{
					db.update("rollback");
					this.msg = sql;
					return false;
				}
			}
		}*/
		
		/*sql = "	update nhanvien set timkiem=  "+
				"			upper(dbo.ftBoDau(isnull(kh.TEN,''))) +   '-' +  "+
				"							upper(dbo.ftBoDau(isnull(kh.DANGNHAP,''))) +   '-' +  "+
				"							upper(dbo.ftBoDau(isnull(kh.DIENTHOAI,''))) +   '-' +  "+
				"							upper(dbo.ftBoDau(isnull(kh.EMAIL,''))) +   '-' +  "+
				"							upper(dbo.ftBoDau(isnull(kh.CONVSITECODE,'')))  "+
				"			from NHANVIEN kh				  "+
				"			where pk_Seq='"+this.Id+"'";
		
		if(!db.update(sql))
		{	
			db.update("rollback");
			this.msg =sql;
			return false;
		}*/
		
		db.execProceduce2("CapNhatThongTinNhanVien", new String[]{ this.Id });
		
		return true;
	}
	
	public boolean update() 
	{
		return false;
	}
	
	
	public void CreateQuyen(String[] chuoi) 
	{
		String st = "(";
		if(chuoi!=null)
		{
			for(int i =0;i< chuoi.length;i++)
				st =st + chuoi[i]+",";
			st =st.substring(0,st.length()-1);
			st = st +")";	               
			
		}
		String sql;
			
		if(this.Id.length()>0)
	    	{
		     sql =" select * from danhmucquyen where pk_seq not in (select dmq_fk from phanquyen where nhanvien_fk ='"+ this.Id +"')";
			}
		else
			 sql = "select * from danhmucquyen";
		 if(chuoi != null)
			sql = "select * from danhmucquyen where pk_seq not in " +st;
		 
		 sql += " order by ten asc ";
		 
		 System.out.println("::: LAY QUYEN: " + sql);
		 this.quyen = db.get(sql);
		   
		 if(this.Id.length() > 0)
		 {
		     sql = "select * from danhmucquyen a, phanquyen b where a.pk_seq = b.dmq_fk and b.nhanvien_fk='"+ this.Id +"'";
		     sql += " order by a.ten asc ";
		     
		     this.quyenchon = db.get(sql);
		 }
		   
		 if(chuoi != null)
		 {
			 sql = "select * from danhmucquyen where pk_seq in " +st;
			 sql += " order by ten asc ";
			 
			 this.quyenchon = db.get(sql);
		 }
	}

	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void setId(String Id) {
		this.Id = Id;
		
	}
	
	public String getId() {
		
		return this.Id;
	}
	
	public void setkenh(ResultSet kenh) {
		
		this.kenh= kenh;
	}
	
	public ResultSet getkenh() {
		
		return this.kenh;
	}
	
	public void setkenhchon(ResultSet kenhchon) {
		
		this.kenhchon = kenhchon;
	}
	
	public ResultSet getkenhchon() {
		
		return this.kenhchon;
	}
	
	public void CreateKenh(String[] chuoi) {
		
		String st="";
		if(chuoi!=null)
		{ st="(";
			for(int i =0;i< chuoi.length;i++)
				st =st + chuoi[i]+",";
			st =st.substring(0,st.length()-1);
			st = st +")";	               
			
		}
		System.out.println("dieu kien"+ st);
		String sql;
			
		if(this.Id.length()>0)
	    	{
		     sql =" select * from kenhbanhang where pk_seq not in (select kenh_fk from NHANVIEN_KENH where nhanvien_fk ='"+ this.Id +"')";
			}
		else
			 sql = "select * from kenhbanhang";
		 if(chuoi !=null)
			sql = "select * from kenhbanhang where pk_seq not in " +st;
		 
		 kenh = db.get(sql);
		  if(this.Id.length()>0)
		   {
		     sql = "select * from kenhbanhang a ,NHANVIEN_KENH b where a.pk_seq = b.kenh_fk and b.nhanvien_fk='"+ this.Id +"'";
		     System.out.println(sql);
		     kenhchon = db.get(sql);
		   }
		   if(chuoi !=null)
			 {
			   sql = "select * from kenhbanhang where pk_seq in " +st;
			   System.out.println(sql);
			   kenhchon = db.get(sql);
			 }
		   System.out.println("ngoai:"+sql);
	}
	
	public void setnpp(ResultSet npp) {
		
		this.npp = npp;
	}
	
	public ResultSet getnpp() {
		
		return this.npp;
	}
	
	public void setnppchon(ResultSet nppchon) {
		
		this.nppchon = nppchon;
	}
	
	public ResultSet getnppchon() {
		
		return this.nppchon;
	}
	
	public void CreateNpp(String[] chuoi) {
		
		String st="";
		if(chuoi!=null)
		{
			for(int i =0;i< chuoi.length;i++)
				st =st + chuoi[i]+",";
			st =st.substring(0,st.length()-1);
			st = st +"";	               
			System.out.println("[DanhSachNppChon]"+st);
		}

		String sql;
		sql=
				"select isnull(v.ten,'') as Vung,isnull(kv.ten,'') as KhuVuc,npp.ma as nppMa,npp.Ten as NppTen,npp.pk_seq as nppId "+
						"from nhaphanphoi npp  "+ 
						"	left join khuvuc kv  on kv.pk_seq=npp.khuvuc_Fk " +
						"	left join vung v on v.pk_seq=kv.vung_fk  "+
						"where npp.trangthai=1	 and npp.loainpp<>4 ";
		String v="";
		if(this.vungId.length()>0)
			v = " and npp.khuvuc_fk in (select pk_seq from khuvuc where vung_fk = ("+ this.vungId +")) ";
		if(this.khuvucId.length()>0)
			v = " and npp.khuvuc_fk in("+ this.khuvucId +") ";
		if(v.length()>0)
			sql = sql + v;

		if(st.length()>0)
		{
			sql +=" and npp.pk_seq not in ("+st+")  ";
			sql += " union  "+
					"select v.ten as Vung,kv.ten as KhuVuc,npp.ma as nppMa,npp.Ten as NppTen,npp.pk_seq as nppId "+
					"from nhaphanphoi npp  "+ 
					"	left join khuvuc kv  on kv.pk_seq=npp.khuvuc_Fk " +
					"	left join vung v on v.pk_seq=kv.vung_fk  "+
					"where npp.pk_Seq in ("+st+")  ";
		}
		//sql+="order by kv.ten,v.ten,npp.ma ";
		System.out.println(":::: LAY NPP: " + sql);
		this.npp = db.get(sql);
	}
	
	public void setsanpham(ResultSet sanpham) {
		
		this.sanpham = sanpham;
	}
	
	public ResultSet getsanpham() {
		
		return this.sanpham;		
	}
	
	public void setsanphamchon(ResultSet sanphamchon) 
	{
		
		this.sanphamchon = sanphamchon;
	}
	
	public ResultSet getsanphamchon() {
		
		return this.sanphamchon;
	}
	
	public void CreateSanpham(String[] chuoi) {
		
		String st="(";
		if(chuoi!=null)
		{
			for(int i =0;i< chuoi.length;i++)
				st =st + chuoi[i]+",";
			st =st.substring(0,st.length()-1);
			st = st +")";	               		
		}
		String sql;
		String nh ="";
		if(this.nhanhangId.length()>0)
		{
			nh = nh +" and nhanhang_fk ='"+ this.nhanhangId +"'";
		}
		if(this.chungloaiId.length()>0)
		{
			nh = nh + " and chungloai_fk ='"+ this.chungloaiId +"'";
		}
		if(this.Id.length()>0)
	    	{
		     sql =" select * from ERP_SANPHAM where trangthai ='1' and pk_seq not in (select sanpham_fk from nhanvien_sanpham where nhanvien_fk ='"+ this.Id +"')";
			}
		else
			 sql = "select * from ERP_SANPHAM where trangthai ='1' ";
		 if(chuoi !=null)
			sql = "select * from ERP_SANPHAM where trangthai ='1' and pk_seq not in " +st;
		 if(nh.length()>0)
			 sql =sql + nh;
		 this.sanpham = db.get(sql);
		   
		   if(this.Id.length()>0)
		   {
		     sql = "select * from ERP_SANPHAM a ,nhanvien_sanpham b where a.trangthai ='1' and a.pk_seq = b.sanpham_fk and b.nhanvien_fk='"+ this.Id +"'";
		   //  if(nh.length()>0)
			//	 sql =sql + nh;
		     this.sanphamchon = db.get(sql);
		   }
		   if(chuoi !=null)
			 {
			   sql = "select * from ERP_SANPHAM where trangthai ='1' and pk_seq in " +st;
			 //  if(nh.length()>0)
				//	 sql =sql + nh;
			  this.sanphamchon = db.get(sql);
			 }
	}
	
	boolean createUpdate(dbutils db, String[] quyenSelected)
	{
		String sql;
		try 
		{
			sql = "delete phanquyen where nhanvien_fk = '" + this.Id + "' ";
			System.out.println("::: XOA PHAN QUYEN: " + sql);
			if(!db.update(sql))
			{	
				db.update("rollback");
				this.msg =sql;
				return false;
			}
			if(quyenSelected != null)
			{
				while(quyenchon.next())
				{
					sql = "insert into phanquyen(dmq_fk, nhanvien_fk) values('" + quyenchon.getString("pk_seq") + " ', '" + this.Id + "')";
					if(!db.update(sql))
					{	
						db.update("rollback");
						this.msg =sql;
						return false;
					}
				}
			}
			
			sql = "delete from NhanVien_UngDung where NhanVien_fk='"+this.Id+"'" ;
			if (!db.update(sql))
			{
				db.update("rollback");
				this.msg = sql;
				return false;
			}
			sql= " insert into NhanVien_UngDung(UngDung_fk,NhanVien_fk)  "+
				 "  select distinct nq.UngDung_fk,pq.Nhanvien_fk from NHOMQUYEN nq inner join phanquyen pq on pq.DMQ_fk=nq.DMQ_fk  "+
				 "  inner join UNGDUNG ud on ud.PK_SEQ=nq.UngDung_fk  "+
				 "  	where nq.HienThi=1 and ud.TRANGTHAI=1 and pq.Nhanvien_fk ='"+this.Id+"' ";
			if (!db.update(sql))
			{
				db.update("rollback");
				this.msg = sql;
				return false;
			}
			
			sql = "delete nhanvien_kenh where nhanvien_fk = '" + this.Id + "' ";
			if(!db.update(sql))
			{	
				db.update("rollback");
				this.msg = sql;
				return false;
			}
			if(this.kenhchon != null )
			{ 
				while(kenhchon.next())
				{
					sql ="insert into nhanvien_kenh(nhanvien_fk,kenh_fk)values('"+ this.Id +"','"+ this.kenhchon.getString("pk_seq")+"')";
					//System.out.println(sql);
					if(!db.update(sql))
					{	
						db.update("rollback");
						this.msg =sql;
						return false;
					}
				}
			}

			sql = "delete phamvihoatdong where nhanvien_fk = '" + this.Id + "' ";
			if(!db.update(sql))
			{	
				db.update("rollback");
				this.msg = sql;
				return false;
			}
			if(this.nppId != null && this.nppIds.length() > 0)
			{
				sql ="insert into phamvihoatdong(nhanvien_fk,npp_fk) select '"+this.Id+"',pk_seq from nhaphanphoi where pk_Seq in ("+this.nppIds+")  ";
				System.out.println(sql);
				if(!db.update(sql))
				{	
					db.update("rollback");
					this.msg =sql;
					return false;
				}
			}

			sql = "delete nhanvien_sanpham where nhanvien_fk = '" + this.Id + "' ";
			if(!db.update(sql))
			{	
				db.update("rollback");
				this.msg = sql;
				return false;
			}
			if(this.sanphamchon != null)
			{ 
				while(sanphamchon.next())
				{
					sql = "insert into nhanvien_sanpham(nhanvien_fk,sanpham_fk)values('"+ this.Id+"','"+ sanphamchon.getString("pk_seq")+"')";
					if(!db.update(sql))
					{	
						db.update("rollback");
						this.msg =sql;
						return false;
					}
				}
			}
			
			sql = "delete nhanvien_kho where nhanvien_fk = '" + this.Id + "' ";
			if(!db.update(sql))
			{	
				db.update("rollback");
				this.msg = sql;
				return false;
			}
			if(this.khochon != null)
			{ 
				while(khochon.next())
				{
					sql = "insert into nhanvien_kho(nhanvien_fk,kho_fk) values ('"+ this.Id+"','"+ khochon.getString("pk_seq")+"')";
					if(!db.update(sql))
					{	
						db.update("rollback");
						this.msg =sql;
						return false;
					}
				}
				khochon.close();
			}
			
			sql = "delete nhanvien_diaban where nhanvien_fk = '" + this.Id + "' ";
			if(!db.update(sql))
			{	
				db.update("rollback");
				this.msg = sql;
				return false;
			}
			
			if(this.diabanIds.trim().length() > 0 )
			{ 
				sql = "insert into nhanvien_diaban(nhanvien_fk, diaban_fk) select '" + this.Id + "', pk_seq from DIABAN where pk_seq in ( " + this.diabanIds + " ) ";
				if(!db.update(sql))
				{	
					db.update("rollback");
					this.msg =sql;
					return false;
				}
			}
			
			sql = "delete nhanvien_tinhthanh where nhanvien_fk = '" + this.Id + "' ";
			if(!db.update(sql))
			{	
				db.update("rollback");
				this.msg = sql;
				return false;
			}
			if(this.tinhthanhIds.trim().length() > 0 )
			{ 
				sql = "insert into nhanvien_tinhthanh(nhanvien_fk, tinhthanh_fk) select '" + this.Id + "', pk_seq from TINHTHANH where pk_seq in ( " + this.tinhthanhIds + " ) ";
				if(!db.update(sql))
				{	
					db.update("rollback");
					this.msg =sql;
					return false;
				}
			}
			
		} 
		catch (Exception e)
		{
			db.update("rollback");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void setvungId(String vungId) {
		
		this.vungId = vungId;
	}
	
	public String getvungId() {
		
		return this.vungId;
	}
	
	public void setvung(ResultSet vung) {
		
		this.vung = vung;
	}
	
	public ResultSet getvung() {
		
		return this.vung;
	}
	
	public void setkhuvucId(String khuvucId) {
		
		this.khuvucId = khuvucId;
	}
	
	public String getkhuvucId() {
		
		return this.khuvucId;
	}
	
	public void setkhuvuc(ResultSet khuvuc) {
		
		this.khuvuc = khuvuc;
	}
	
	public ResultSet getkhuvuc() {
		
		return this.khuvuc;
	}
	
	void CreateVung()
	{
		String sql = "select * from vung";
		this.vung = db.get(sql);
		
		if(this.vungId.length()>0)
			sql ="select * from khuvuc where vung_fk ='"+ this.vungId+"'";
		else
			sql ="select * from khuvuc";
		this.khuvuc = db.get(sql);
		System.out.println("khu vuc "+sql);
	}

	public void setnhanhangId(String nhanhangId) {
		
		this.nhanhangId = nhanhangId;
	}

	public String getnhanhangId() {
		
		return this.nhanhangId;
	}

	public void setnhanhang(ResultSet nhanhang) {
		
		this.nhanhang = nhanhang;
	}

	public ResultSet getnhanhang() {
		
		return this.nhanhang;
	}

	public void setchungloaiId(String chungloaiId) {
		
		this.chungloaiId = chungloaiId;
	}

	public String getchungloaiId() {
		
		return this.chungloaiId;
	}

	public void setchungloai(ResultSet chungloai) {
		
		this.chungloai = chungloai;
	}

	public ResultSet getchungloai() {
		
		return this.chungloai;
	}
	
	public void CreateNhanhang()
	{
		String sql ="select * from nhanhang";
		this.nhanhang = db.get(sql);
		//if(this.nhanhangId.length()>0)
			sql ="select * from chungloai ";
			this.chungloai = db.get(sql);
	}
	
	public void setchon(String chon) {
		this.chon = chon;
		
	}
	
	public String getchon() {
		
		return this.chon;
	}
	
	public void setnhaphanphoi(ResultSet nhaphanphoi) {
		
		this.nhaphanphoi = nhaphanphoi;
	}
	
	public ResultSet getnhaphanphoi() {
		
		return this.nhaphanphoi;
	}
	
	public void CreateRS() 
	{
		CreateVung();
		CreateNhanhang();				
		String sql;

		sql = "select * from nhaphanphoi where  TRANGTHAI=1 ";
		String v="";
		if(this.vungId.length()>0)
			v = " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='"+ this.vungId +"')";
		if(this.khuvucId.length()>0)
			v = " and khuvuc_fk ='"+ this.khuvucId +"'";
		if(v.length()>0)
			sql = sql + v;

		sql += "  order by loaiNPP asc, TEN asc ";
		System.out.println("Lay danh sach NPP: " + sql);

		this.nhaphanphoi = db.get(sql);

		String query = " select PK_SEQ, DienGiai from DANHMUCQUYEN ";
		query += " order by DienGiai asc ";
		this.DanhmucquyenRs =  db.get(query);

		CreateLoaiRs();
		
		sql="select * from kenhbanhang ";
		this.kenhRs=this.db.get(sql);

		sql="select * from TinhThanh order by TEN asc ";
		this.ttRs=this.db.get(sql);
		this.tinhthanhRs = this.db.get(sql);

		sql="select * from QuanHuyen ";
		this.qhRs=this.db.get(sql);

		sql="select pk_seq, ten from DIABAN where trangthai = '1' order by TEN asc ";
		this.diabanRs = this.db.get(sql);
		
		sql="select * from ERP_DONVITHUCHIEN where trangthai = '1' order by TEN asc ";	
		this.phongbanRs = this.db.get(sql);
	}
	
	
	private void CreateLoaiRs() {
		
		String query = "";
		if(this.loai == null) 
			this.loai = "0";
		
		if(this.loai.equals("1")) 
		{
			query = " SELECT PK_SEQ, TEN FROM BM WHERE TRANGTHAI = 1 ";
			
			if( this.Id.trim().length() > 0 )
				query += " union SELECT PK_SEQ, TEN FROM BM WHERE pk_seq = ( select BM_FK from NHANVIEN where pk_seq = '" + this.Id + "' )  ";
		} 
		else if(this.loai.equals("2")) 
		{
			query = " SELECT PK_SEQ, TEN FROM ASM WHERE TRANGTHAI = 1 ";
			
			if( this.Id.trim().length() > 0 )
				query += " union SELECT PK_SEQ, TEN FROM ASM WHERE pk_seq = ( select ASM_FK from NHANVIEN where pk_seq = '" + this.Id + "' )  ";
		} 
		else if(this.loai.equals("3")) 
		{
			query = " SELECT PK_SEQ, TEN FROM GIAMSATBANHANG WHERE TRANGTHAI = 1 ";
			if( this.nppId.trim().length() > 0 )
				query += " and pk_seq in ( select GSBH_FK from NHAPP_GIAMSATBH where npp_fk = '" + this.nppId + "' ) ";
			
			if( this.Id.trim().length() > 0 )
				query += " union SELECT PK_SEQ, TEN FROM GIAMSATBANHANG WHERE pk_seq = ( select GSBH_FK from NHANVIEN where pk_seq = '" + this.Id + "' )  ";
		}
		else if(this.loai.equals("4"))
		{
			if( this.nppId.trim().length() > 0 )
			{
				query = " SELECT PK_SEQ, MAFAST + ', ' + TEN as TEN FROM DAIDIENKINHDOANH WHERE TRANGTHAI = 1 and pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' )  ";
			
				if( this.Id.trim().length() > 0 )
					query += " union SELECT PK_SEQ, MAFAST + ', ' + TEN as TEN FROM DAIDIENKINHDOANH WHERE pk_seq = ( select DDKD_FK from NHANVIEN where pk_seq = '" + this.Id + "' )  ";
		
			}
		}
		else if(this.loai.equals("6")) //CHI LAY NHUNG DDKD NAO CO CHECK LA NV CONG TY
		{
			if(this.nppId.trim().length() > 0)
			{
				query = "SELECT PK_SEQ, isnull(manhanvien, '') + ', ' + TEN as TEN FROM NHANVIENGIAONHAN WHERE TRANGTHAI = 1 and npp_fk = '" + this.nppId + "' ";
				
				if( this.Id.trim().length() > 0 )
					query += " union SELECT PK_SEQ, isnull(manhanvien, '') + ', ' + TEN FROM NHANVIENGIAONHAN WHERE pk_seq = ( select NVGN_FK from NHANVIEN where pk_seq = '" + this.Id + "' )  ";
			}
		}
		
		if(query.length() > 0) 
		{
			this.loaiRs = this.db.get(query);
		}
	}
	public void DBClose() {
		
		try{
		if(kenh!=null){
			kenh.close();
		}
		if(kenh!=null){
			kenh.close();
		}
	
		if(npp!=null){
			npp.close();
		}

		if(nppchon!=null){
			nppchon.close();
		}

		if(sanpham!=null){
			sanpham.close();
		}

		if(sanphamchon!=null){
			sanphamchon.close();
		}
	
		

		if(vung!=null){
			vung.close();
		}
	
	

		if(khuvuc!=null){
			khuvuc.close();
		}
		
	
		if(nhanhang!=null){
			nhanhang.close();
		}
		
	
		if(chungloai!=null){
			chungloai.close();
		}
		
		if(nhaphanphoi!=null){
			nhaphanphoi.close();
		}
		
		if(loaiRs != null){
			loaiRs.close();
		}
		
		if(phongbanRs != null)
		{
			phongbanRs.close();
		}
		
		if(db!=null){
			db.shutDown();
		}
		}catch(Exception er){
			
		}
		finally
		{
			db.shutDown();
		}
		
	}
	
	public String getNppIds()
	{
		return this.nppIds;
	}
	
	public void setNppIds(String nppId)
	{
		this.nppIds=nppId;
	}
	
	public void setLoai(String loai) {
		this.loai = loai;
	}
	
	public String getLoai() {
		return this.loai;
	}
	
	public void setLoaiId(String loaiId) {
		this.loaiId = loaiId;
	}
	
	public String getLoaiId() {
		return this.loaiId;
	}
	
	public void setLoaiRs(ResultSet loaiRs) {
		this.loaiRs = loaiRs;
	}
	
	public ResultSet getLoaiRs() {
		return this.loaiRs;
	}
	
	String kenhId;

	public String getKenhId()
	{
		return kenhId;
	}
	public void setKenhId(String kenhId)
	{
		this.kenhId = kenhId;
	}
	
	
	ResultSet kenhRs;

	public ResultSet getKenhRs()
	{
		return kenhRs;
	}
	public void setKenhRs(ResultSet kenhRs)
	{
		this.kenhRs = kenhRs;
	}
	
	String activeTab;

	public String getActiveTab()
	{
		return activeTab;
	}
	public void setActiveTab(String activeTab)
	{
		this.activeTab = activeTab;
	}
	
	public int KiemTra_TenDangNhap()
	{
		int soDong=0;
		String	query=			
		"	select COUNT(*) as SoDong "+
		"	from NHANVIEN  "+
		"	where DANGNHAP=N'"+this.Tendangnhap+"'   ";
		
		if(this.Id.length() > 0)
			query += " and pk_seq != '" + this.Id + "' ";
		
		System.out.println("[KiemTra]"+query);
		
		ResultSet rs = this.db.get(query);
		try
		{
			while(rs.next())
			{
				soDong=rs.getInt("SoDong");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			soDong=-1;
		}
		return soDong;
	}
	
	public String getSohoadonden() 
	{
		return sohoadonden;
	}
	public void setSohoadonden(String sohoadonden) 
	{
		this.sohoadonden = sohoadonden;
	}
	
	public String getSohoadontu()
	{
		return sohoadontu;
	}
	
	public void setSohoadontu(String sohoadontu) 
	{
		this.sohoadontu = sohoadontu;
	}
	
	public void setdmquyenId(String quyenId) {
		
		this.quyenId = quyenId;
	}
	
	public String getdmquyenId() {
		
		return this.quyenId;
	}
	
	public void setDanhmucquyenRs(ResultSet Rsdanhmucquyen) {
		
		this.DanhmucquyenRs = Rsdanhmucquyen;
	}
	
	public ResultSet getDanhmucquyenRs() {
		
		return this.DanhmucquyenRs;
	}
	String qhId,ttId;
	ResultSet qhRs,ttRs;

	public String getQhId()
  {
  	return qhId;
  }
	public void setQhId(String qhId)
  {
  	this.qhId = qhId;
  }
	public String getTtId()
  {
  	return ttId;
  }
	public void setTtId(String ttId)
  {
  	this.ttId = ttId;
  }
	public ResultSet getQhRs()
  {
  	return qhRs;
  }
	public void setQhRs(ResultSet qhRs)
  {
  	this.qhRs = qhRs;
  }
	public ResultSet getTtRs()
  {
  	return ttRs;
  }
	public void setTtRs(ResultSet ttRs)
  {
  	this.ttRs = ttRs;
  }

  public void closeDB()
  {
		
	    try
      {
	    	if(ttRs!=null)    ttRs.close();
	      if(qhRs!=null)	qhRs.close();
	      if(quyen!=null)quyen.close();
	      if(quyenchon!=null)quyenchon.close();
	      if(vung!=null)vung.close();
	      if(khuvuc!=null)khuvuc.close();
	      if(DanhmucquyenRs!=null)DanhmucquyenRs.close();
	      if(nhanhang!=null)nhanhang.close();
	      if(nhaphanphoi!=null)nhaphanphoi.close();
	      if(kho!=null)kho.close();
	      if(khochon!=null)khochon.close();
	      
      } catch (Exception e)
      {
	      e.printStackTrace();
      }
  }

	public void setKho(ResultSet khors) {
		
		this.kho=khors;
	}

	public ResultSet getKhoRs() {
		
		return this.kho;
	}

	public void setKhochon(ResultSet khochonrs) {
		
		this.khochon=khochonrs;
	}

	public ResultSet getKhochonrs() {
		
		return this.khochon;
	}
	public String getXuathoadon() {
		return Xuathoadon;
	}
	public void setXuathoadon(String xuathoadon) {
		Xuathoadon = xuathoadon;
	}
	
	public void CreateKho(String[] chuoi) {
		
		Utility util = new Utility(); 
		String st="";
		if(chuoi!=null)
		{
			for(int i =0;i< chuoi.length;i++)
				st = st + chuoi[i]+ ",";
			st =st.substring(0,st.length()-1);
			st = st +"";	               
			System.out.println("[DanhSachKhoChon]:" + st);
		}
		String sql="";		
		
		if(this.Id.length()>0)	
			sql =" select * from ERP_KHOTT where trangthai ='1' and pk_seq not in (select kho_fk from nhanvien_kho where nhanvien_fk ='"+ this.Id +"')";
		else
			sql = "select * from ERP_KHOTT where trangthai ='1' and pk_seq  in (select kho_fk from nhanvien_kho where nhanvien_fk ='"+ this.userId +"')";
		if(chuoi !=null)
			sql = "select * from ERP_KHOTT where trangthai ='1' and pk_seq not in "+ util.quyen_kho(this.Id);
		this.kho = db.get(sql);
	   
	   if(this.Id.length()>0)
	   {
	     sql = "select * from ERP_KHOTT a ,nhanvien_kho b where a.trangthai ='1' and a.pk_seq = b.kho_fk and b.nhanvien_fk='"+ this.Id +"'";
	     this.khochon = db.get(sql);
	   }
	   
	   if(chuoi !=null)
	   {
		   sql = "select * from ERP_KHOTT where trangthai ='1' and pk_seq in ( " +st+" )";
		  this.khochon = db.get(sql);
	   }
	}
	
	public void setDiabanRs(ResultSet diaban) {

		this.diabanRs = diaban;
	}

	public ResultSet getDiabanRs() {
		
		return this.diabanRs;
	}

	public String getDiabanId() {

		return this.diabanIds;
	}

	public void setDiabanId(String diabanId) {
		
		this.diabanIds = diabanId;
	}
	
	public void setTinhthanhRs(ResultSet tinhthanh) {
		
		this.tinhthanhRs = tinhthanh;
	}
	
	public ResultSet getTinhthanhRs() {
		
		return this.tinhthanhRs;
	}
	
	public String getTinhthanhId() {
		
		return this.tinhthanhIds;
	}
	
	public void setTinhthanhId(String tinhthanhId) {
		
		this.tinhthanhIds = tinhthanhId;
	}
	
	public void setphongbanRs(ResultSet phongbanRs) {
		
		this.phongbanRs = phongbanRs;
	}
	
	public ResultSet getphongbanRs() {
		
		return this.phongbanRs;
	}
	
	public String getphongbanId() {
		
		return this.phongbanId;
	}
	
	public void setphongbanId(String phongbanId) {
		
		this.phongbanId = phongbanId;
	}
	
}
