package geso.traphaco.center.beans.giamsatbanhang.imp;

import geso.traphaco.center.beans.giamsatbanhang.IGiamsatbanhang;
import geso.traphaco.center.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;


public class Giamsatbanhang implements IGiamsatbanhang
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String ten;
	String diachi;
	String sodienthoai;
	String email;
	String nhacungcap;
	String kenhbanhang;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;	
		
	String[] npp;
	String[] nppUpdate;
	ResultSet Dsnpp;
	
	String mact, vitri, vungtt, sonamlamviec, sodtct, ngayvaoct, loaihd, ngaykthd, ngaykyhd, sotk, ghichu, dakyhd;

	String nganhang, chinhanh, manhanvien, mathuviec;
	
	dbutils db;
	
	ResultSet nhacungcaplist;
	String nccId;
	
	ResultSet dvkdlist;
	String dvkdId;
	
	ResultSet kenhbanhanglist;
	String kbhId;
	String vungId;
	ResultSet vung;
	String kvId;
	ResultSet khuvuc;

	private String[] kvIds;
	
	String maFAST;
	
	public Giamsatbanhang(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.ten = param[1];
		this.diachi = param[2];
		this.sodienthoai = param[3];
		this.email = param[4];
		this.nhacungcap = param[5];
		this.kenhbanhang = param[6];
		this.trangthai = param[7];
		this.ngaytao = param[8];
		this.nguoitao = param[9];
		this.ngaysua = param[10];
		this.nguoisua = param[11];
		this.nccId = param[12];
		this.kbhId = param[13];
		this.msg = "";
	
		this.nganhang = "";
		this.chinhanh = "";
		this.manhanvien = "";
		this.mathuviec = "";
		this.ngaysinh="";
		this.quequan="";
		this.matkhau="";
		this.cmnd ="";
		this.nppIds="";
		
		this.maFAST = "";
	}
	
	public Giamsatbanhang(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.ten = "";
		this.diachi = "";
		this.sodienthoai = "";
		this.email = "";
		this.nhacungcap = "";
		this.kenhbanhang = "";
		this.trangthai = "0";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nccId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.vungId = "";
		this.kvId = "";
		this.msg = "";
		
		this.mact = "";
		this.vungtt = "";
		this.dakyhd = "";
		this.ghichu = "";
		this.loaihd = "";
		this.ngaykthd = "";
		this.ngaykyhd = "";
		this.ngayvaoct = "";
		this.sodtct = "";
		this.sotk = "";
		this.sonamlamviec = "";
		this.vitri = "";
		

		this.nganhang = "";
		this.chinhanh = "";
		this.manhanvien = "";
		this.mathuviec = "";
		
		this.tendangnhap="";
		this.ngaysinh="";
		this.quequan="";
		this.matkhau="";
		this.cmnd ="";
		
		this.nppIds="";
		
		this.maFAST = "";
		
		if(id.length() > 0)
			this.init();
		else
			this.createRS();
	}
	
	public String getMaFAST() {
		return maFAST;
	}

	public void setMaFAST(String maFAST) {
		this.maFAST = maFAST;
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

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}
	
	public String getDiachi() 
	{
		return this.diachi;
	}

	public void setDiachi(String diachi) 
	{
		this.diachi = diachi;
	}
	
	public String getSodienthoai() 
	{
		return this.sodienthoai;
	}

	public void setSodienthoai(String sodienthoai) 
	{
		this.sodienthoai = sodienthoai;
	}

	public String getEmail() 
	{
		return this.email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getNhacungcap() 
	{
		return this.nhacungcap;
	}

	public void setNhacungcap(String nhacungcap) 
	{
		this.nhacungcap = nhacungcap;
	}
	
	public String getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(String kenhbanhang) 
	{
		this.kenhbanhang = kenhbanhang;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
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
	
	public ResultSet getNhacungcapList() 
	{
		return this.nhacungcaplist;
	}

	public void setNhacungcapList(ResultSet nhacungcaplist) 
	{
		this.nhacungcaplist = nhacungcaplist;
	}
	
	public ResultSet getDvkdList() 
	{
		return this.dvkdlist;
	}

	public void setDvkdList(ResultSet dvkdlist) 
	{
		this.dvkdlist = dvkdlist;
	}
	
	public ResultSet getKenhbanhangList() 
	{
		return this.kenhbanhanglist;
	}

	public void setKenhbanhangList(ResultSet kenhbanhanglist) 
	{
		this.kenhbanhanglist = kenhbanhanglist;
	}
	
	public String getNccId()
	{
		return this.nccId;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}
	
	public String getDvkdId()
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public String getKbhId() 
	{
		return this.kbhId;
	}

	public void setKbhId(String kbhId) 
	{
		this.kbhId = kbhId;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId = vungId;
	}
	
	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}
	
	public void setKvIds(String[] kvIds)
	{
		this.kvIds = kvIds;
	}


	public ResultSet createNccRS()
	{  			
		ResultSet nccRS = db.get("select pk_seq as nccId, isnull(tenviettat,ten) as nccTen from nhacungcap where trangthai='1' order by ten");

		return nccRS;
	}
	
	public ResultSet createDvkdRS()
	{  			
		ResultSet dvkdRS = null;
		if (this.nccId.length() > 0){
			dvkdRS = db.get("select a.pk_seq as dvkdId, a.donvikinhdoanh as dvkdTen from donvikinhdoanh a, nhacungcap_dvkd b where a.trangthai='1' and a.pk_seq = b.dvkd_fk and b.ncc_fk = '" + this.nccId + "' order by donvikinhdoanh");
		}else{
			dvkdRS = db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkdTen from donvikinhdoanh where trangthai='1' order by donvikinhdoanh");
		}
		return dvkdRS;
	}

	public ResultSet createKbhRS(){  			
		
		ResultSet kbhRS = db.get("select pk_seq as kbhId, diengiai as kbhTen from kenhbanhang where trangthai='1' order by diengiai");

		return kbhRS;
	}
	
	public ResultSet createDsnpp()
	{  			
		String query = "";
		String kv = "";
		if(this.id.length()<=0)
		{
			
			query = "select distinct a.pk_seq, a.ten, isnull(b.ngaybatdau, '') as ngaybatdau, isnull(b.ngayketthuc, '') as ngayketthuc from nhaphanphoi a " +
					"left join nhapp_giamsatbh b on a.pk_seq = b.npp_fk " +
					"where a.trangthai = '1'  ";
		}
		else if(this.id.length()>0)
		{
			query = "select distinct a.pk_seq, a.ten, isnull(b.ngaybatdau, '') as ngaybatdau, isnull(b.ngayketthuc, '') as ngayketthuc from nhaphanphoi a " +
					"left join nhapp_giamsatbh b on a.pk_seq = b.npp_fk " +
					"where a.trangthai = '1' ";
		}
		
		if(this.kvId.trim().length() > 0)
			query += " And a.khuvuc_fk = '" + this.kvId + "' ";
		
		query += " order by ten asc ";
		System.out.println("query npp : "+query);
		ResultSet nppRs = db.get(query);
		
		return nppRs;
	}
	
	public ResultSet createVungRS(){  			
		
		ResultSet vungRS = db.get("select pk_seq as vungId, ten as vung from vung where trangthai='1' order by ten");

		return vungRS;
	}

	public ResultSet createKhuvucRS()
	{  			
		ResultSet kvRS;
		
		if(this.vungId.length() > 0){
			kvRS = db.get("select pk_seq as kvId, ten as khuvuc from khuvuc where trangthai='1' and vung_fk = '"+ this.vungId + "' order by ten");
		}else{
			kvRS = db.get("select pk_seq as kvId, ten as khuvuc from khuvuc where trangthai='1' order by ten");
		}
		
		return kvRS;
	}
	
	public ResultSet createKVByGSBHRS()
	{
		ResultSet kvRS=null;
		
		if(this.id.length() > 0)
		{
			String query = "select khuvuc_fk from GSBH_khuvuc where gsbh_fk='"+this.id+"'";	
			kvRS = db.get(query);
			System.out.println("[GSBH_khuvuc]"+query);
		}
		return kvRS;
	}
	
	public ResultSet createNPPByNPPGSBH()
	{
		ResultSet kvRS=null;
		
		if(this.id.length() > 0){
			kvRS = db.get("select * from NHAPP_GIAMSATBH where gsbh_fk='"+this.id+"'");
		}
		return kvRS;
	}
		
	public void setNppUpdate(String[] nppupdate) {
			
			this.nppUpdate = nppupdate;
		}

	
	public Hashtable<Integer, String> getnppUpdate() {
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		ResultSet kv = createNPPByNPPGSBH();
		int m = 0;
		if(kv != null)
		try {while (kv.next()) {
			
				selected.put(m++, kv.getString("npp_fk"));
						
		}} catch(Exception e) 
		{
				e.printStackTrace();
			}
		return selected;
	}
	
	public Hashtable<Integer, String> getKvIds(){
		
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		ResultSet kv = createKVByGSBHRS();
		int m = 0;
		if(kv != null)
		try {while (kv.next()) {
			
				selected.put(m++, kv.getString("khuvuc_fk"));
			
			
		}} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return selected;
	}
	

	public void createRS() 
	{		
		this.nhacungcaplist = this.createNccRS();
		this.dvkdlist = this.createDvkdRS();
		this.kenhbanhanglist = this.createKbhRS();		
		this.Dsnpp = this.createDsnpp();
		
		String query="select PK_SEQ,MANHANVIEN, DIENTHOAI,TEN from bm where trangthai=1 ";
		this.bmRs =this.db.get(query);
		
		query="select PK_SEQ,MANHANVIEN, DIENTHOAI,TEN from asm where trangthai=1";
	}
	
	public boolean CreateGsbh( HttpServletRequest request ) 
	{		
		try
		{
			
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		String query="";
		/*String query=
				" select COUNT(*) AS SODONG "+
				" from GIAMSATBANHANG  "+
				" where MANHANVIEN=N'"+this.manhanvien+"'";
				if(this.id.length()>0)
					query+=" and pk_seq!='"+this.id+"'";
				
				System.out.print("[Check]"+query);
				ResultSet rsCheck=db.get(query);
				while(rsCheck.next())
				{
					if(rsCheck.getInt(1)>0)
					{
						this.msg="Mã nhân viên này đã có,vui lòng nhập mã khác !";
						return false;
					}
				}	
				if(rsCheck!=null)rsCheck.close();*/
				
			this.db.getConnection().setAutoCommit(false);	 
			
			
			String maGSBH = "";
			ResultSet rsMANPP = db.get("select max( cast( manhanvien as numeric(18, 0) ) + 1 ) as maNPP from GIAMSATBANHANG  ");
			if(rsMANPP.next())
			{
				maGSBH = rsMANPP.getString("maNPP");
			}
			rsMANPP.close();
			
			String command = "insert into giamsatbanhang(manhanvien, ten,dienthoai,email,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,ncc_fk,diachi,dvkd_fk, vitri, vungtt, dakyhd, ghichu, ngayvaocongty, sodtcongty, sonamlamviec,VUNG_FK,BM_FK,MatKhau , mafast) " + 
							" values('"+ maGSBH +"', N'" + this.ten + "','" + this.sodienthoai + "','" + this.email + "','" + this.trangthai + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao + "','" + this.nguoitao + "','" + this.nccId + "',N'" + this.diachi + "','" + this.dvkdId + "', '"+this.vitri+"', '"+this.vungtt+"', '"+this.dakyhd+"', '"+this.ghichu+"',  '"+this.ngayvaoct+"', '"+this.sodtct+"', '"+this.sonamlamviec+"',"+this.vungId+","+(this.bmId.length()<=0?"NULL":this.bmId)+",'"+this.matkhau+"','"+this.maFAST+"')";
			
			System.out.println("Cau luu "+command);
			if (!this.db.update(command))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query; 		
				this.db.update("rollback");
				return false;
			}
		     query = "select IDENT_CURRENT('giamsatbanhang') as id";
		    ResultSet rs = this.db.get(query);
			rs.next();
			this.id = rs.getString("id");
		
			command="update giamsatbanhang set smartid='"+this.id+"' where pk_seq=" + this.id;
			if(!db.update(command))
			{
				db.update("rollback");
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query; 		
				return false;
			}
			
			if(this.kvId.length()>0)
			{
				query="insert into gsbh_khuvuc(gsbh_fk, khuvuc_fk) select "+this.id+",pk_seq from KhuVuc where pk_Seq in ("+this.kvId+") ";
				if(!db.update(query))
				{
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
					this.db.update("rollback");
					return false;
				}
			}
				
			query="delete from DDKD_GSBH WHERE DDKD_FK IN (SELECT PK_SEQ FROM DAIDIENKINHDOANH WHERE NPP_FK  IN ( select npp_fk from NHAPP_GIAMSATBH where gsbh_fk="+this.id+"  ) )";
			if(!db.update(query))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				this.db.update("rollback");
				return false;
			}
			
			query="delete from NHAPP_GIAMSATBH WHERE GSBH_fK IN ("+this.id+")";
			if(!db.update(query))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				this.db.update("rollback");
				return false;
			}

			if(this.nppIds.length()>0)
			{
				String[] nppId = request.getParameterValues("nppId");
				if(nppId!=null)
				{
					int n=0;
					int size=nppId.length;
					while(n < size)
					{
						String ngaybatdau=request.getParameter("ngaybatdau"+nppId[n])==null?"":request.getParameter("ngaybatdau"+nppId[n]).trim();
						String ngayketthuc=request.getParameter("ngayketthuc"+nppId[n])==null?"":request.getParameter("ngayketthuc"+nppId[n]).trim();
						
						if(ngaybatdau.length()==0)ngaybatdau=getDateTime();
						if(ngayketthuc.length()==0) ngayketthuc="2100-12-01";
						
						int SoDong=0;
						query="select count(*) as SoDong,npp.ma as NppMa,npp.Ten as nppTen  From DonHang DH inner join NhaPhanPhoi npp on npp.pk_Seq=dh.npp_fk Where tranthai in (0,3) and NgayNhap < '"+ngaybatdau+"' and npp_fk='"+nppId[n]+"'";
						 rs =db.get(query);
						 while(rs.next())
						 {
							 SoDong=rs.getInt("SoDong");
						 }
						
						query="insert into nhapp_giamsatbh(npp_fk, gsbh_fk, ngaybatdau, ngayketthuc) values('" + nppId[n] + "','" + this.id + "', '"+ ngaybatdau +"', '"+ ngayketthuc +"')";
						if(!db.update(query))
						{
							this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
							this.db.update("rollback");
							return false;
						}
						n++;
					}
				}
				
				query=
				"insert into DDKD_GSBH(DDKD_FK,GSBH_FK) "+
				" SELECT ddkd_fk,"+this.id+" " +
				" FROM DAIDIENKINHDOANH_NPP dd WHERE NPP_FK IN ("+this.nppIds+") " +
				" and not exists    "+
				"		 (   "+
				"		 select * from DDKD_GSBH a where DDKD_FK=dd.ddkd_fk   "+ 
				"		 and GSBH_FK="+this.id+" "+
				"		 )  ";
				if(!db.update(query))
				{
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
					this.db.update("rollback");
					return false;
				}					
			}
			
			query=
			" INSERT INTO LichSu_GSBH_NPP(NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC) "+
			"	SELECT NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC FROM NHAPP_GIAMSATBH A  "+
			"	WHERE NOT EXISTS  "+
			"	(  "+
			"		SELECT * FROM LichSu_GSBH_NPP B  "+
			"		WHERE B.NPP_FK=A.NPP_FK AND A.GSBH_FK=B.GSBH_FK "+
			"	) AND A.GSBH_FK="+this.id+" " ;
			if(!db.update(query))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				this.db.update("rollback");
				return false;
			}	
			
			/*if(KiemTra_TenDangNhap()!=0)
			{
				this.msg = "Tên đăng nhập này đã có người sử dụng,vui lòng đổi lại";
				this.db.update("rollback");
				return false;
			}*/			
			

			 /*query = "INSERT INTO NHANVIEN (TEN,NGAYSINH,DANGNHAP,MATKHAU,EMAIL,DIENTHOAI,TRANGTHAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,PHANLOAI,ISLOGIN,SESSIONID,GSBH_FK,LOAI) "+
			 		 " VALUES(N'"+this.getTen()+"','"+this.ngaysinh+"',N'GDCN"+ maGSBH +"',pwdencrypt('"+this.matkhau+"'),'"+this.getEmail()+"',N'"+this.sodienthoai+"','1','"+this.getDateTime()+"','"+this.getDateTime()+"','"+this.userId+"','"+this.userId+"','2','0','"+getDateTime()+"',"+this.id+",1 )";
			 if(!this.db.update(query))
			 {
				 this.db.update("rollback");
				 this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				 return false; 
			 }

			 query= "insert into NHANVIEN_KENH(Nhanvien_fk, Kenh_fk )  select ident_current('NHANVIEN'), pk_seq from KENHBANHANG";
			 if(!this.db.update(query))
			 {
				this.db.update("rollback");
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				return false; 
			 }
			 
			 query=
			 "insert into PHAMVIHOATDONG(Npp_fk,Nhanvien_fk) "+
			 "select b.NPP_FK,a.PK_SEQ "+
			 " from  NHANVIEN a inner join NHAPP_GIAMSATBH  b on b.GSBH_FK=a.GSBH_FK " +
			 " where a.gsbh_fk="+this.id+"  ";
			 if(!this.db.update(query))
			 {
				this.db.update("rollback");
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				return false; 
			 }*/
			 /*query=
			 "insert into NHANVIEN_SANPHAM(Sanpham_fk,Nhanvien_fk) "+
			 " select sp.PK_SEQ,nv.PK_SEQ "+
			 " from SANPHAM sp,NHANVIEN nv "+
			 " where nv.GSBH_FK='"+this.id+"' ";
			 if(!this.db.update(query))
			 {
				this.db.update("rollback");
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				return false; 
			 }*/
			 
			command=" UPDATE GIAMSATBANHANG SET TIMKIEM =upper(dbo.ftBoDau(ten))+' '+UPPER(dbo.ftBoDau(diachi))+' '+UPPER(dbo.ftBoDau(manhanvien)) +' '+UPPER(dbo.ftBoDau(Email)) + ' '+DIENTHOAI where pk_seq='"+this.id+"'";
			if (!db.update(command))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				this.db.update("rollback");
				return false;
			}
			if(this.bmId.length()>0)
			{
				command ="insert into GiamSatBanHang_BM(GSBH_FK,BM_FK) " +
						" SELECT "+this.id+",pk_seq" +
						" from BM WHERE PK_sEQ IN ("+this.bmId+") ";
				if (!db.update(command))
				{
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
					this.db.update("rollback");
					return false;
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception e)
		{
			this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+e.getMessage(); 		
			this.db.update("rollback");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean UpdateGsbh(HttpServletRequest request)  
	{
		try
		{
			String query="";
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			//cap nhat bang gsbh_khuvuc
			
			/*String query=
			" select COUNT(*) AS SODONG "+
			" from GIAMSATBANHANG  "+
			" where MANHANVIEN=N'"+this.manhanvien+"'";
			if(this.id.length()>0)
				query+=" and pk_seq!='"+this.id+"'";
			
			ResultSet rsCheck=db.get(query);
			while(rsCheck.next())
			{
				if(rsCheck.getInt(1)>0)
				{
					this.msg="Mã nhân viên này đã có,vui lòng nhập mã khác !";
					return false;
				}
			}	
			if(rsCheck!=null)rsCheck.close();*/
			this.db.getConnection().setAutoCommit(false);	
			
			String sql = "delete gsbh_khuvuc where gsbh_fk='"+this.id+"'";
			if(!db.update(sql))
			{
				this.msg = "Lỗi cập nhật GSBH "+sql;
				this.db.update("rollback");
				return false;
			}
			System.out.println("115. Delete : "+sql);
			
			
			if(this.kvIds!=null)
			{
				for (String kv : this.kvIds) 
				{
					String qr = "insert into gsbh_khuvuc(gsbh_fk, khuvuc_fk) values('"+this.id+"', '"+kv+"')";
					if(!db.update(qr))
					{
						System.out.println("1. Update GSBH : "+qr);
						this.msg = "loi cap nhat bang gsbh_khuvuc";
						this.db.update("rollback");
						return false;
					}
				}
			}
			query="delete from DDKD_GSBH WHERE DDKD_FK IN (SELECT PK_SEQ FROM DAIDIENKINHDOANH WHERE NPP_FK  IN ( select npp_fk from NHAPP_GIAMSATBH where gsbh_fk="+this.id+"  ) )";
			if(!db.update(query))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				this.db.update("rollback");
				return false;
			}
			
			query="delete from NHAPP_GIAMSATBH WHERE GSBH_fK IN ("+this.id+")";
			if(!db.update(query))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				this.db.update("rollback");
				return false;
			}

			
			if(this.nppIds.length()>0)
			{
				String[] nppId = request.getParameterValues("npp");
				if(nppId!=null)
				{
					int n=0;
					int size=nppId.length;
					while(n < size)
					{
						String ngaybatdau=request.getParameter("ngaybatdau"+nppId[n])==null?"":request.getParameter("ngaybatdau"+nppId[n]).trim();
						String ngayketthuc=request.getParameter("ngayketthuc"+nppId[n])==null?"":request.getParameter("ngayketthuc"+nppId[n]).trim();
						
						if(ngaybatdau.length()==0)ngaybatdau=getDateTime();
						if(ngayketthuc.length()==0) ngayketthuc="2100-12-01";
						query="insert into nhapp_giamsatbh(npp_fk, gsbh_fk, ngaybatdau, ngayketthuc) values('" + nppId[n] + "','" + this.id + "', '"+ ngaybatdau +"', '"+ ngayketthuc +"')";
						if(!db.update(query))
						{
							this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
							this.db.update("rollback");
							return false;
						}
						n++;
					}
				}
				query=
						"insert into DDKD_GSBH(DDKD_FK,GSBH_FK) "+
						" SELECT distinct ddkd_fk,"+this.id+" " +
						" FROM DAIDIENKINHDOANH_NPP dd WHERE NPP_FK IN ("+this.nppIds+") " +
						" and not exists    "+
						"		 (   "+
						"		 select * from DDKD_GSBH a where DDKD_FK=dd.ddkd_fk   "+ 
						"		 and GSBH_FK="+this.id+" "+
						"		 )  ";
						if(!db.update(query))
						{
							this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
							this.db.update("rollback");
							return false;
						}					
			}
			
			query=
			" INSERT INTO LichSu_GSBH_NPP(NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC) "+
			"	SELECT NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC FROM NHAPP_GIAMSATBH A  "+
			"	WHERE NOT EXISTS  "+
			"	(  "+
			"		SELECT * FROM LichSu_GSBH_NPP B  "+
			"		WHERE B.NPP_FK=A.NPP_FK AND A.GSBH_FK=B.GSBH_FK "+
			"	) AND A.GSBH_FK="+this.id+" " ;
			if(!db.update(query))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				this.db.update("rollback");
				return false;
			}
			
			query=
			"UPDATE A SET NGAYBATDAU=B.NGAYBATDAU,NGAYKETTHUC=B.NGAYKETTHUC FROM LichSu_GSBH_NPP A INNER JOIN "+ 
			"		NHAPP_GIAMSATBH B ON B.NPP_FK=A.NPP_FK  AND B.GSBH_FK=A.GSBH_FK "+
			"		WHERE A.GSBH_FK="+this.id+" ";
			if(!db.update(query))
			{
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				this.db.update("rollback");
				return false;
			}
			/*if(KiemTra_TenDangNhap()!=0)
			{
				this.msg = "Tên đăng nhập này đã có người sử dụng,vui lòng đổi lại";
				this.db.update("rollback");
				return false;
			}*/
			
			/*query="delete from  NHANVIEN_KENH where Nhanvien_fk in (select pk_seq from NHANVIEN where GSBH_FK='"+this.id+"')" ;
			 if(!this.db.update(query))
			 {
				this.db.update("rollback");
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				return false; 
			 }
			 
			 query="delete from  PHAMVIHOATDONG where Nhanvien_fk in (select pk_seq from NHANVIEN where GSBH_FK='"+this.id+"')" ;
			 if(!this.db.update(query))
			 {
				this.db.update("rollback");
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				return false; 
			 }
			 
			 query="delete from  NHANVIEN_SANPHAM where Nhanvien_fk in (select pk_seq from NHANVIEN where GSBH_FK='"+this.id+"')" ;
			 if(!this.db.update(query))
			 {
				this.db.update("rollback");
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				return false; 
			 }
			 
			 
			 query=
			 "insert into NHANVIEN_KENH(Nhanvien_fk, Kenh_fk) " +
			 " select nv.pk_seq,'100025' from nhanvien nv inner join giamsatbanhang gs on gs.pk_Seq=nv.gsbh_fk where gs.pk_Seq='"+this.id+"' ";
			 if(!this.db.update(query))
			 {
				this.db.update("rollback");
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				return false; 
			 }
			 
			 query=
				 "insert into NHANVIEN_KENH(Nhanvien_fk,Kenh_fk ) " +
				 " select nv.pk_seq,'100052'  from nhanvien nv inner join giamsatbanhang gs on gs.pk_Seq=nv.gsbh_fk where gs.pk_Seq='"+this.id+"' ";
				 if(!this.db.update(query))
				 {
					this.db.update("rollback");
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
					return false; 
				 }
			 
			 query=
			 "insert into PHAMVIHOATDONG(Npp_fk,Nhanvien_fk) "+
			 "select b.NPP_FK,a.PK_SEQ "+
			 " from  NHANVIEN a inner join NHAPP_GIAMSATBH  b on b.GSBH_FK=a.GSBH_FK " +
			 " where a.gsbh_fk="+this.id+"  ";
			 if(!this.db.update(query))
			 {
				this.db.update("rollback");
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				return false; 
			 }
			 query=
			 "insert into NHANVIEN_SANPHAM(Sanpham_fk,Nhanvien_fk) "+
			 " select sp.PK_SEQ,nv.PK_SEQ "+
			 " from SANPHAM sp,NHANVIEN nv "+
			 " where nv.GSBH_FK='"+this.id+"' ";
			 if(!this.db.update(query))
			 {
				this.db.update("rollback");
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				return false; 
			 }
			 
			 query="update NHANVIEN set TEN=N'"+this.ten+"',NGAYSINH='"+this.ngaysinh+"',DANGNHAP='"+this.id+"',MATKHAU=pwdencrypt('"+this.matkhau+"'),EMAIL='"+this.email+"',DIENTHOAI='"+this.sodienthoai+"',TRANGTHAI='"+this.trangthai+"',NGAYSUA='"+getDateTime()+"',NGUOISUA ='"+this.userId+"'" +
			 		",SESSIONID = case when '"+this.trangthai+"'='0' then  (SELECT CONVERT(VARCHAR(10),DATEADD(day,-65,GETDATE()),120)) end,Loai=1  where gsbh_fk='"+this.id+"' ";
			 if(!this.db.update(query))
			 {
				this.db.update("rollback");
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
				return false; 
			 }*/
			
				String command =" update giamsatbanhang set ten =N'" + this.ten + "', dienthoai = '" + this.sodienthoai + "', email='" + this.email + "', trangthai='" + this.trangthai + "', " +
						" ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "', ncc_fk = '" + this.nccId + "', diachi = N'" + this.diachi + "', " +
						"  vitri = '"+this.vitri+"', vungtt = '"+this.vungtt+"', sonamlamviec = '"+this.sonamlamviec+"', sodtcongty = '"+this.sodtct+"', " +
						" ngayvaocongty = '"+this.ngayvaoct+"', ghichu = N'"+this.ghichu+"', " +
						"  dakyhd = '"+this.dakyhd+"', " +
						" dvkd_fk='" + this.dvkdId + "',BM_FK="+(this.bmId.length()<=0?"NULL":this.bmId)+",MatKhau='"+this.matkhau+"' ,mafast='"+this.maFAST+"' where pk_seq = '" + this.id + "'";//, khuvuc_fk='" + this.kvId + "'
		System.out.println("2. Update GSBH : "+command);
		if (!db.update(command))
		{
			this.msg = command;			
			this.db.update("rollback");
			return false;
		}
		command=" UPDATE GIAMSATBANHANG SET TIMKIEM =upper(dbo.ftBoDau(ten))+' '+UPPER(dbo.ftBoDau(diachi))+' '+UPPER(dbo.ftBoDau(manhanvien)) +' '+UPPER(dbo.ftBoDau(Email)) + ' '+DIENTHOAI where pk_seq='"+this.id+"'";
		if (!db.update(command))
		{
			this.msg = command;			
			this.db.update("rollback");
			return false;
		}
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		this.db.shutDown();
		}catch(Exception er)
		{
			er.printStackTrace();
			this.msg="Loi cap nhat giam sat ban hang "+er.getMessage();
			this.db.update("rollback");
			return false;
		}
		
		return true; 
	}

	public void init()
	{
		String query = 
		"select isnull(a.mafast,'') as mafast,  ISNULL(a.VITRI,'') AS VITRI, 	 \n  "   +        
		"	ISNULL(a.VUNGTT,'') AS VUNGTT, \n  "   +        
		"	ISNULL(a.DAKYHD,'') AS DAKYHD, ISNULL(a.GHICHU,'') AS GHICHU,\n  "   +              
		"	   ISNULL(a.NGAYVAOCONGTY,'') AS NGAYVAOCONGTY, \n  "   +        
		"	ISNULL(a.SODTCONGTY,'') AS SODTCONGTY,   ISNULL(a.SONAMLAMVIEC,'') AS SONAMLAMVIEC,\n  "   +        
		"	a.pk_seq as id , isnull(a.ten,'') as ten, isnull(a.dienthoai,'') as sodienthoai, \n  "   +        
		"	ISNULL(a.email,'') AS email, isnull(a.diachi,'') as diachi, isnull(a.trangthai,'') as trangthai,\n  "   +              
		"	a.pk_seq as manhanvien, \n  "   +        
		"	a.ngaytao, a.ngaysua, a.dvkd_fk as dvkdId, a.vung_Fk as vungId, \n  "   +        
		"	a.ncc_fk  as nccId,a.cmnd,a.NgaySinh,a.MatKhau,a.BM_FK AS BMID \n  "   +  
		"from giamsatbanhang a  " +
		"WHERE a.pk_seq= '"+this.id+"' " ;     
		
		System.out.println("Khoi tao : "+query);
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.ten = rs.getString("ten");
        	this.sodienthoai = rs.getString("sodienthoai");
        	this.email = rs.getString("email");
        	this.diachi = rs.getString("diachi");
        	this.trangthai = rs.getString("trangthai");
        	this.nccId = rs.getString("nccId");
        	this.dvkdId = rs.getString("dvkdId");
        	this.vitri = rs.getString("vitri");
        	this.vungtt = rs.getString("vungtt");
        	this.vungId = rs.getString("vungId")==null?"":rs.getString("vungId");
        	this.dakyhd = rs.getString("dakyhd");
        	this.ghichu = rs.getString("ghichu");
        	this.ngayvaoct = rs.getString("ngayvaocongty");
        	this.sodtct = rs.getString("sodtcongty");
        	this.sonamlamviec = rs.getString("sonamlamviec");
        	this.manhanvien = rs.getString("manhanvien");
        	
        	this.ngaysinh = rs.getString("NgaySinh")==null?"":rs.getString("NgaySinh");
        	this.cmnd = rs.getString("cmnd")==null?"":rs.getString("cmnd");
        	this.matkhau = rs.getString("matkhau")==null?"":rs.getString("matkhau");
        	this.bmId =  rs.getString("bmiD")==null?"":rs.getString("bmiD");
        	this.maFAST = rs.getString("mafast");
       	}catch(Exception e){e.printStackTrace();}
       	createRS(); 
       	
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public void setMaCt(String mact) {
		
		this.mact = mact;
	}

	
	public String getMaCt() {
		
		return this.mact;
	}

	
	public void setVitri(String vt) {
		
		this.vitri = vt;
	}

	
	public String getVitri() {
		
		return this.vitri;
	}

	
	public void setVungTT(String vungtt) {
		
		this.vungtt = vungtt;
	}

	
	public String getVungTT() {
		
		return this.vungtt;
	}

	
	public void setSonamlamviec(String sonam) {
		
		this.sonamlamviec = sonam;
	}

	
	public String getSonamlamviec() {
		
		return this.sonamlamviec;
	}

	
	public void setSoDTcongty(String sdt) {
		
		this.sodtct = sdt;
	}

	
	public String getSoDTcongty() {
		
		return this.sodtct;
	}

	
	public void setNgayvaoct(String ngayvaoct) {
		
		this.ngayvaoct = ngayvaoct;
	}

	
	public String getNgayvaoct() {
		
		return this.ngayvaoct;
	}

	
	public void setLoaiHD(String loaihd) {
		
		this.loaihd = loaihd;
	}

	
	public String getLoaiHD() {
		
		return this.loaihd;
	}

	
	public void setNgayketthucHD(String ngaykt) {
		
		this.ngaykthd = ngaykt;
	}

	
	public String getNgayketthucHD() {
		
		return this.ngaykthd;
	}

	
	public void setSotk(String stk) {
		
		this.sotk = stk;
	}

	
	public String getSotk() {
		
		return this.sotk;
	}

	
	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String getGhichu() {
		
		return this.ghichu;
	}

	
	public void setNgaykyHD(String ngaykyhd) {
		
		this.ngaykyhd = ngaykyhd;
	}

	
	public String getNgaykyHD() {
		
		return this.ngaykyhd;
	}

	
	public void setDakyHD(String dakyhd) {
		
		this.dakyhd = dakyhd;
	}

	
	public String getDakyHD() {
		
		return this.dakyhd;
	}
	

	
	
	public void setNganHang(String nganhang) {
		this.nganhang = nganhang;
	}

	
	public String getNganHang() {
		return this.nganhang;
	}

	
	public void setChiNhanh(String chinhanh) {
		this.chinhanh = chinhanh;
	}

	
	public String getChiNhanh() {
		return this.chinhanh;
	}

	
	public void setMaNhanVien(String manhanvien) {
		this.manhanvien = manhanvien;
	}

	
	public String getMaNhanVien() {
		return this.manhanvien;
	}

	
	public void setMaThuViec(String mathuviec) {
		this.mathuviec = mathuviec;
	}

	
	public String getMaThuViec() {
		return this.mathuviec;
	}

	
	public void setDsnpp(ResultSet Dsnpp) {
		
		this.Dsnpp = Dsnpp;
	}

	
	public ResultSet getDsnpp() {
		
		return this.Dsnpp;
	}
	
	public void setNpp(String[] npp) {
		this.npp = npp;
	}

	public Hashtable<Integer, String> getnpp() {
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.npp != null) {
			int size = (this.npp).length;
			int m = 0;
			while (m < size) {
				selected.put(new Integer(m), this.npp[m]);
				m++;
			}
		} else {
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	ResultSet asmRs,bmRs;
	public ResultSet getAsmRs() 
	{
		return asmRs;
	}

	public void setAsmRs(ResultSet asmRs) 
	{
		this.asmRs = asmRs;
	}

	public ResultSet getBmRs() 
	{
		return bmRs;
	}

	public void setBmRs(ResultSet bmRs) 
	{
		this.bmRs = bmRs;
	}

	public String getAsmId() 
	{
		return asmId;
	}

	public void setAsmId(String asmId) 
	{
		this.asmId = asmId;
	}

	public String getBmId() 
	{
		return bmId;
	}

	public void setBmId(String bmId) 
	{
		this.bmId = bmId;
	}

	String asmId,bmId;
	
	String nppIds;

	public String getNppIds() {
		return nppIds;
	}

	public void setNppIds(String nppIds) {
		this.nppIds = nppIds;
	}
	String tendangnhap,matkhau;
	
	@Override
	public String getTenDangNhap()
	{
		
		return tendangnhap;
	}

	String ngaysinh,cmnd,quequan;
	@Override
	public void setTenDangNhap(String dangnhap)
	{
		this.tendangnhap =dangnhap;
	}

	@Override
	public String getMatKhau()
	{
		
		return this.matkhau;
	}

	@Override
	public void setMatKhau(String matkhau)
	{
		this.matkhau =matkhau;
	}
	
public String getCmnd() {
		
		return this.cmnd;
	}

	
	public void setCmnd(String cmnd) {
		
		this.cmnd=cmnd;
	}

	
	public String getNgaysinh() {
		
		return this.ngaysinh;
	}

	
	public void setNgaysinh(String ngaysinh) {
		
		this.ngaysinh=ngaysinh;
	}

	
	public String getQuequan() {
		
		return this.quequan;
	}

	
	public void setQuequan(String quequan) {
		this.quequan=quequan;
		
	}
	
	public int KiemTra_TenDangNhap()
	{
		int soDong=0;
		String	query=			
		"	select COUNT(*) as SoDong "+
		"	from NHANVIEN  "+
		"	where DANGNHAP=N'"+this.tendangnhap+"'   ";
		
		if(this.id.length()>0)
			query+=" and dangnhap!=(select tendangnhap from GiamSatBanHang where pk_Seq='"+this.id+"') ";
		
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
	
}

