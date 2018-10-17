package geso.traphaco.center.beans.daidienkinhdoanh.imp;
import geso.traphaco.center.beans.daidienkinhdoanh.IDaidienkinhdoanh;
import geso.traphaco.center.db.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Daidienkinhdoanh implements IDaidienkinhdoanh
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String ten;
	String sodienthoai;
	String diachi;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	boolean isdelete;	
	String kenhbanhang;
	String nhaphanphoi;
	String Macty;
	String Vitri;
	String KhuvucTT;
	String Sonamlamviec;
	String SoDTCty;
	String NgayvaoCty;
	String HDLD;
	String LoaiHD;
	String NgaykyHD;
	String NgayketthucHD;
	String TeamLeader;
	String SoTaiKhoan;
	String Email;
	String GhiChu;
	String matkhau;
	ResultSet nppList;
	String nppId;
	
	ResultSet gsbhList;
	String gsbhId;
	
	ResultSet RsDDKD;
	String ddkdTn;
	dbutils db;
	String PhanTramChuyen;

	String nganhang, chinhanh, manhanvien, mathuviec, cothechonTN;
	
	String maFAST;
	String isNVTT;

	public Daidienkinhdoanh(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.ten = param[1];
		this.sodienthoai = param[2];
		this.diachi = param[3];
		this.trangthai = param[4];
		this.ngaytao = param[5];
		this.nguoitao = param[6];
		this.ngaysua = param[7];
		this.nguoisua = param[8];
		this.kenhbanhang = param[9];
		this.nhaphanphoi = param[11];
		this.isdelete = true;
		this.msg = "";
		this.maFAST = "";
		this.nppId = "";
		this.isNVTT = "";
		

		this.nganhang = "";
		this.chinhanh = "";
		this.manhanvien = "";
		this.mathuviec = "";
		this.matkhau = "";
		this.cothechonTN = "0";
	}
	
	public Daidienkinhdoanh(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.ten = "";
		this.sodienthoai = "";
		this.diachi = "";
		this.trangthai = "2";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua ="";
		this.kenhbanhang = "";
		this.nhaphanphoi = "";
		this.nppId ="";
		this.msg = "";
		this.ddkdTn="";
		this.PhanTramChuyen="0";
		///////////////////////
		this.Macty="";
		this.Vitri="";
		this.KhuvucTT="";
		this.Sonamlamviec="0";
		this.SoDTCty="";
		this.NgayvaoCty="";
		this.HDLD="";
		this.LoaiHD="";
		this.NgaykyHD="";
		this.NgayketthucHD="";
		this.TeamLeader="";
		this.SoTaiKhoan="";
		this.Email="";
		this.GhiChu="";
		this.maFAST = "";
		this.isNVTT = "";
		

		this.nganhang = "";
		this.chinhanh = "";
		this.manhanvien = "";
		this.mathuviec = "";
		this.matkhau = "";
		this.cothechonTN = "0";
		
		/////////////////////////
		this.isdelete = true;
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
	
	public String getSodt()
	{
		return this.sodienthoai;
	}
	
	public void setSodt(String sodienthoai)
	{
		this.sodienthoai = sodienthoai;
	}
	
	public String getDiachi()
	{
		return this.diachi;
	}
	
	public void setDiachi(String diachi)
	{
		this.diachi = diachi;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getNppId() 
	{		
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
	}
	
	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
	}
	
	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
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
	
	public String getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(String kenhbanhang)
	{
		this.kenhbanhang = kenhbanhang;
	}

	public String getNhaphanphoi() 
	{
		return this.nhaphanphoi;
	}

	public void setNhaphanphoi(String nhaphanphoi) 
	{
		this.nhaphanphoi = nhaphanphoi;
	}

	/*#####################Thêm mới##########################*/
	public void setMaCongTy(String macty)
	{
		this.Macty=macty;
	}
	public String getMaCongTy()
	{
		return this.Macty;
	}
	public void setViTri(String vitri)
	{
		this.Vitri=vitri;
	}
	public String getViTri()
	{
		return this.Vitri;
	}
	public void setKhuVucTT(String khuvuctt)
	{
		this.KhuvucTT=khuvuctt;
	}
	public String getKhuVucTT()
	{
		return this.KhuvucTT;
	}
	public void setSoNamLamViec(String nam)
	{
		this.Sonamlamviec=nam;
	}
	public String getSoNamLamViec()
	{
		return this.Sonamlamviec;
	}
	public void setSoDTCty(String sodt)
	{
		this.SoDTCty=sodt;
	}
	public String getSoDTCty()
	{
		return this.SoDTCty;
	}
	public void setNgayVaoCty(String ngay)
	{
		this.NgayvaoCty=ngay;
	}
	public String getNgayVaoCty()
	{
		return this.NgayvaoCty;
	}
	public void setHDLD(String hdld)
	{
		this.HDLD=hdld;
	}
	public String getHDLD()
	{
		return this.HDLD;
	}
	public void setLoaiHD(String loaihd)
	{
		this.LoaiHD=loaihd;
	}
	public String getLoaiHD()
	{
		return this.LoaiHD;
	}
	public void setNgayKyHD(String ngay)
	{
		this.NgaykyHD=ngay;
	}
	public String getNgayKyHD()
	{
		return this.NgaykyHD;
	}
	public void setNgayKetThucHD(String ngay)
	{
		this.NgayketthucHD=ngay;
	}
	public String getNgayKetThucHD()
	{
		return this.NgayketthucHD;
	}
	public void setTeamLeader(String teamleader)
	{
		this.TeamLeader=teamleader;
	}
	public String getTeamLeader()
	{
		return this.TeamLeader;
	}
	public void setSoTaiKhoan(String sotk)
	{
		this.SoTaiKhoan=sotk;
	}
	public String getSoTaiKhoan()
	{
		return this.SoTaiKhoan;
	}
	public void setEmail(String email)
	{
		this.Email=email;
	}
	public String getEmail()
	{
		return this.Email;
	}
	public void setGhiChu(String ghichu)
	{
		this.GhiChu=ghichu;
	}
	public String getGhiChu()
	{
		return this.GhiChu;
	}
	/*#######################################################*/
	
	public ResultSet getGsbhList() 
	{
		return this.gsbhList;
	}
	
	public void setGsbhList(ResultSet gsbhList) 
	{
		this.gsbhList = gsbhList;
	}

	public void setNppList(ResultSet npplist)
	{
		this.nppList = npplist;
	}
	
	public ResultSet getNppList() 
	{
		return this.nppList;
	}
	
	public boolean getIsDelete() 
	{
		return this.isdelete;
	}

	public void setIsDelete(boolean isDelete) 
	{
		this.isdelete = isDelete;
	}
	
	public void createNppList()
	{
		/*String query = "select 1 as ID, N'Traphaco' as nppTen, 1 as STT ";
	    query += " union select a.pk_seq as ID, a.ten as nppTen, 2 as STT " +
				 "from nhaphanphoi a where isKHACHHANG = '0'  ";*/
	    
	    String query =  " select a.pk_seq as ID, a.ten as nppTen, 2 as STT " +

				 		"from nhaphanphoi a where isKHACHHANG = '0'  and trangthai = 1 ";

	    
		this.nppList = this.db.get(query);
		System.out.println("Danh sach NPP lay duoc: " + query + "\n");
	}
	
	public void createGsbhList()
	{
		//String query = " select a.pk_seq as id, a.ten as gsbhTen, b.ten as kbhTen from giamsatbanhang a, kenhbanhang b, nhapp_giamsatbh c where a.kbh_fk = b.pk_seq and a.pk_seq= c.gsbh_fk and c.npp_fk='" + this.nppId + "'";
		System.out.println("npp id"+this.nppId);
		if(this.nppId.trim().length() > 0)
		{
			String query = "select pk_seq as ID, TEN from GiamSatBanHang where trangthai = '1'";
			if(!this.isNVTT.equals("1"))
				query += " and pk_seq in ( select gsbh_fk from nhapp_giamsatbh where NPP_FK in ( " + this.nppId + " ) ) ";
			System.out.println("::::::::"+query);
			this.gsbhList = this.db.get(query);
		}
	}
	
	public void createRS() 
	{
		createNppList();
		createGsbhList();
		CreateDDKDList();
			
		//CHECK CO THE CHON NHAN VIEN TIEN NHIEM HAY KHONG
		if(this.id.trim().length() > 0)
		{
			String query = " select COUNT(*) as soDong " +
					       "from " +
					       "( " +
					       		"select PK_SEQ, DDKDTIENNHIEM from DAIDIENKINHDOANH where  PK_SEQ = '" + this.id + "' and DDKDTIENNHIEM is not null " +
					       "union all " +
					       		"select PK_SEQ, DDKD_FK from DONHANG where DDKD_FK = '" + this.id + "' " +
					       	") " +
					       	"TienNhiem ";
			
			System.out.println("___CHECK CO THE CHON TIEN NHIEM: " + query);
			ResultSet rsTN = db.get(query);
			try 
			{
				if(rsTN.next())
				{
					if(rsTN.getInt("soDong") <= 0)
					{
						this.cothechonTN = "1";
					}
				}
				rsTN.close();
			} 
			catch (Exception e) {}
			
		}
		
	}
	
	
	private void CreateDDKDList() {

		if(this.nppId.trim().length() > 0)
			this.RsDDKD = this.db.get("select pk_seq, ten+' - ' +manhanvien as Ten from daidienkinhdoanh where npp_fk="+ this.nppId);
	}

	public boolean CreateDdkd() 
	{
		try
		{
			 this.db.getConnection().setAutoCommit(false);
			 
			 if(userId==null||userId=="")
			 {
				this.db.update("rollback");
				this.msg = "User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			 }
			 
			 if(this.isNVTT.equals("0") && this.nppId.trim().length() <= 0 )
			 {
				 this.db.update("rollback");
				 this.msg = "Bạn phải chọn chi nhánh trực thuộc cho TRÌNH DƯỢC VIÊN";
				 return false;
			 }
			
			 this.ngaytao = getDateTime();
			 this.nguoitao = this.userId;
		
			 
			 String maGSBH = "";
			ResultSet rsMANPP = db.get("select max( cast( manhanvien as numeric(18, 0) ) + 1 ) as maNPP from DAIDIENKINHDOANH  ");
			if(rsMANPP.next())
			{
				maGSBH = rsMANPP.getString("maNPP");
			}
			rsMANPP.close();
			
		 String query = "insert into daidienkinhdoanh(manhanvien, ten,dienthoai,diachi, trangthai,nguoitao,nguoisua,ngaytao,ngaysua,ddkdtiennhiem,phantramchuyen,macty,vitri,khuvuctt,sonamlamviec,sodtcongty,ngayvaocongty,hdld,loaihd,ngaykyhd,ngayketthuchd,teamleader,sotaikhoan,email,ghichu, nganhang, chinhanh,  mathuviec, matkhau, tructhuoc_fk , mafast) " +
		  				"  values('" + maGSBH + "', N'" + this.ten +"','" + this.sodienthoai + "',N'"+ this.diachi + "', '1', '" + this.nguoitao + "','" + this.nguoitao + "','" + this.ngaytao + "','" + this.ngaytao + "',"+(this.ddkdTn.trim().length()<=0?"NULL":this.ddkdTn)+",'0','"+this.Macty+"'," +
		  						" N'"+this.Vitri+"',N'"+this.KhuvucTT+"',N'"+this.Sonamlamviec+"','"+this.SoDTCty+"','"+this.NgayvaoCty+"',N'"+this.HDLD+"',N'"+this.LoaiHD+"','"+this.NgaykyHD+"','"+this.NgayketthucHD+"',N'"+this.TeamLeader+"','"+this.SoTaiKhoan+"','"+this.Email+"',N'"+this.GhiChu+"', N'"+this.nganhang+"', N'"+this.chinhanh+"',  N'"+this.mathuviec+"', PWDENCRYPT('"+this.matkhau+"'), '" + this.isNVTT + "', '"+ this.maFAST + "')";		
		 if(!(this.db.update(query))){
				this.msg="khong the chuyen Chi Tieu"+ query;
				this.db.update("rollback");
				return false;
			}
		 this.msg = query;	
		 	query = "select IDENT_CURRENT('daidienkinhdoanh') as ddkdId";
		 	ResultSet rs = this.db.get(query);
			rs.next();
			this.id = rs.getString("ddkdId");
			try
			{
				String sql_update_smartid="update daidienkinhdoanh set smartid='"+this.id+"' where pk_seq=" + this.id;
				if(!db.update(sql_update_smartid)){
					//db.update("rollback");
					System.out.println("Khong The Thuc Hien Luu Dai Dien Kinh Doanh Nay,Vui Long Lien He Voi Admin De Sua Loi Nay");
					//return false;
				}
			}
			catch(Exception err){ }
			rs.close();
			
			if(this.gsbhId!=null && this.gsbhId.length()>0)
			{		
				query = "insert into ddkd_gsbh values('"+ this.id + "','" + this.gsbhId + "')";
				if(!(this.db.update(query)))
				{
					this.db.update("rollback");
					return false;
				}
			}
			
			if(this.nppId.length()>0)
			{		
				query = "insert DAIDIENKINHDOANH_NPP(ddkd_fk, npp_fk) select '" + this.id + "', pk_seq " +
						"from NHAPHANPHOI where pk_seq in ( " + this.nppId + " ) ";
				if(!(this.db.update(query)))
				{
					this.db.update("rollback");
					this.msg = "Lỗi: " + query;
					return false;
				}
			}
			
			rs.close();
			
			
			String	command=" UPDATE DAIDIENKINHDOANH SET TIMKIEM =upper(dbo.ftBoDau(ten))+' '+UPPER(dbo.ftBoDau(diachi))+' '+UPPER(dbo.ftBoDau(manhanvien)) +' '+UPPER(dbo.ftBoDau(Email)) + ' '+DIENTHOAI where pk_seq='"+this.id+"'";
			if (!db.update(command))
			{
				this.msg = command;			
				this.db.update("rollback");
				return false;
			}
			
			if(this.isNVTT.equals("0")  )  //NVBH THUOC 1 NPP MOI TU TAO
			{
				command =   "	INSERT INTO TUYENBANHANG(DIENGIAI, NGAYLAMVIEC, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, DDKD_FK, NPP_FK, NGAYID) "+
							"select 'T' + cast(ngay.ngayId  as varchar(10)) + '_' + a.TEN ,  " +
							"	 ngay.ngaylv AS NGAYLAMVIEC, '" + getDateTime() + "', '" + getDateTime() + "', '" + this.userId + "', '" + this.userId + "', A.PK_SEQ AS DDKD, B.NPP_FK, ngay.ngayId   " +
							"from DAIDIENKINHDOANH a inner join DAIDIENKINHDOANH_NPP b on a.pk_seq = b.ddkd_fk,  " +
							"(  " +
							"	select 2 as ngayId,'Thu hai' as ngaylv union   " +
							"	select 3 as ngayId ,'Thu ba' union  " +
							"	select 4 as ngayId ,'Thu tu'  " +
							"	union   " +
							"	select 5 as ngayId,'Thu nam'   " +
							"	union   " +
							"	select 6 as ngayId,'Thu sau' union   " +
							"	select 7 as ngayId ,'Thu bay'  " +
							") AS ngay   " +
							"where a.PK_SEQ = '" + this.id + "' and b.npp_fk not in (( select pk_seq from NHAPHANPHOI where loaiNPP in ( 2, 3 ) ))  ";
				
				if (!db.update(command))
				{
					this.msg = command;			
					this.db.update("rollback");
					return false;
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.db.update("rollback");
			this.msg="Khong The Thuc Hien Luu Dai Dien Kinh Doanh Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
			return false;
		}
		return true;
	}

	public boolean UpdateDdkd() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		if(userId==null||userId=="")
		 {
			this.db.update("rollback");
			this.msg = "User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
			return false;
		 }
		/*String sql="select count(*) from DAIDIENKINHDOANH where manhanvien=N'"+this.manhanvien+"' ";
	 	if(this.id.length()>0)
	 		sql+=" and pk_seq!='"+this.id+"'";
		ResultSet rsCheck=db.get(sql);
		System.out.print(sql);
			try
			{
				while(rsCheck.next())
				{
					if(rsCheck.getInt(1)>0)
					{
						this.msg="Mã nhân viên này đã có vui lòng nhập mã khác !";
						return false;
					}
				}
				if(rsCheck!=null)
				{
					rsCheck.close();
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}*/
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String query = "update daidienkinhdoanh set ten = N'" + this.ten + "', dienthoai ='" + this.sodienthoai + "', diachi = N'"+ this.diachi + "', trangthai = '" + this.trangthai + "', nguoisua = '" + this.userId + "', ngaysua = '" + this.ngaysua + "', " +
						   " macty='"+this.Macty+"',vitri=N'"+this.Vitri+"',khuvucTT=N'"+this.KhuvucTT+"',sonamlamviec=N'"+this.Sonamlamviec+"',sodtcongty='"+this.SoDTCty+"',ngayvaocongty='"+this.NgayvaoCty+"',hdld=N'"+this.HDLD+"',loaihd=N'"+this.LoaiHD+"',ngaykyhd='"+this.NgaykyHD+"', " +
						   " nganhang = N'"+this.nganhang+"', chinhanh = N'"+this.chinhanh+"',  mathuviec = N'"+this.mathuviec+"', " +
						   " ngayketthuchd='"+this.NgayketthucHD+"', teamleader=N'"+this.TeamLeader+"',sotaikhoan='"+this.SoTaiKhoan+"', email='"+this.Email+"',ghichu=N'"+this.GhiChu+"', tructhuoc_fk = '" + this.isNVTT + "' , mafast =  N'"+ this.maFAST +"' ";
	
			if(this.matkhau.trim().length() > 0) 
			{
				query += ", matkhau = PWDENCRYPT('" + this.matkhau.trim() +"') ";
			}
			query += " where pk_seq = '" + this.id + "'" ;
			
			System.out.println("1. " + query);
			if(!this.db.update(query))
			{
				this.msg = "Lỗi: " + query;
				this.db.update("rollback");
				return false;
			}
			query="delete ddkd_gsbh where ddkd_fk = '" + this.id + "'";
			if(!this.db.update(query))
			{
				this.msg = "Lỗi: " + query;
				this.db.update("rollback");
				return false;
			}
			
			if(this.gsbhId!=null && this.gsbhId.length()>0)
			{		
				query = "insert into ddkd_gsbh values('"+ this.id + "','" + this.gsbhId + "')";
				if(!(this.db.update(query)))
				{
					this.db.update("rollback");
					return false;
				}
			}
	
			query="delete DAIDIENKINHDOANH_NPP where ddkd_fk = '" + this.id + "'";
			if(!this.db.update(query))
			{
				this.msg = "Lỗi: " + query;
				this.db.update("rollback");
				return false;
			}
			
			query = "update TUYENBANHANG set DIENGIAI = SUBSTRING ( diengiai, 0, CHARINDEX ( '_', diengiai ) ) + N'_' + N'" + this.ten + "' where ddkd_fk = '" + this.id + "'";
			if(!this.db.update(query))
			{
				this.msg = "Lỗi: " + query;
				this.db.update("rollback");
				return false;
			}
			
			if(this.nppId.length()>0)
			{		
				query = "insert DAIDIENKINHDOANH_NPP(ddkd_fk, npp_fk) select '" + this.id + "', pk_seq " +
						"from NHAPHANPHOI where pk_seq in ( " + this.nppId + " ) ";
				System.out.println("---CHEN NPP: " + query );
				if(!(this.db.update(query)))
				{
					this.db.update("rollback");
					this.msg = "Lỗi: " + query;
					return false;
				}
			}
			
			String	command=" UPDATE DAIDIENKINHDOANH SET TIMKIEM =upper(dbo.ftBoDau(ten))+' '+UPPER(dbo.ftBoDau(diachi))+' '+UPPER(dbo.ftBoDau(manhanvien)) +' '+UPPER(dbo.ftBoDau(Email)) + ' '+DIENTHOAI where pk_seq='"+this.id+"'";
			if (!db.update(command))
			{
				this.msg = command;			
				this.db.update("rollback");
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception e)
		{
			this.msg = "Lỗi: " + e.getMessage();
			e.printStackTrace();
			this.db.update("rollback");
			return false;
		}
	}	
	
	private void init() 
	{
		String query = "select isnull(a.mafast,'') as mafast, a.pk_seq as id, a.ten, a.dienthoai, a.diachi, a.trangthai,  "+
					   " ISNULL(a.macty,'') as macty, ISNULL(a.vitri,'') as vitri, " +
					   " ISNULL(a.khuvuctt,'') as khuvuctt, " +
					   " ISNULL(a.sonamlamviec,0) as sonamlamviec, ISNULL(a.sodtcongty,'') as sodtcongty, ISNULL(a.ngayvaocongty,'') as ngayvaocongty, " +
					   " ISNULL(a.hdld,'') as hdld, ISNULL(a.loaihd,'') as loaihd, ISNULL(a.ngaykyhd,'') as ngaykyhd, ISNULL(a.ngayketthuchd,'') as ngayketthuchd, " +
					   " ISNULL(a.teamleader,'') as teamleader, ISNULL(a.sotaikhoan,'') as sotaikhoan, ISNULL(a.email,'') as email, ISNULL(a.ghichu,'') as ghichu, " +
					   " ISNULL(a.NGANHANG,'') as nganhang, ISNULL(a.CHINHANH,'') as chinhanh, A.PK_SEQ as manhanvien, ISNULL(a.MATHUVIEC,'') as mathuviec, a.DDKDTIENNHIEM, isnull(a.tructhuoc_fk, 0) as tructhuoc_fk " +
					   " from daidienkinhdoanh a  "+ 
					   " where  a.pk_seq ="+this.id ;
		
		System.out.println("1/ In ra nhân viên: " +query);
		ResultSet rs =  this.db.get(query);
        try
        {
            rs.next();     
        	this.id = rs.getString("id");
        	this.ten = rs.getString("ten");
        	this.sodienthoai = rs.getString("dienthoai");
        	this.diachi = rs.getString("diachi");
        	this.trangthai = rs.getString("trangthai");
        	 this.Macty = rs.getString("macty");
             this.Vitri = rs.getString("vitri");
             
             this.KhuvucTT = rs.getString("khuvuctt");
             this.Sonamlamviec = rs.getString("sonamlamviec");
             this.SoDTCty = rs.getString("sodtcongty");
             this.NgayvaoCty = rs.getString("ngayvaocongty");
             this.HDLD = rs.getString("hdld");
             this.LoaiHD = rs.getString("loaihd");
             this.NgaykyHD = rs.getString("ngaykyhd");
             this.NgayketthucHD = rs.getString("ngayketthuchd");
             this.TeamLeader = rs.getString("teamleader");
             this.SoTaiKhoan = rs.getString("sotaikhoan");
             this.Email = rs.getString("email");
             this.GhiChu = rs.getString("ghichu");
             this.isNVTT = rs.getString("tructhuoc_fk");
         	
         	this.nganhang = rs.getString("nganhang");
         	this.chinhanh = rs.getString("chinhanh");
         	this.manhanvien = rs.getString("manhanvien");
         	this.mathuviec = rs.getString("mathuviec");
         	this.maFAST = rs.getString("mafast");
         	
         	this.ddkdTn = rs.getString("DDKDTIENNHIEM") == null ? "" : rs.getString("DDKDTIENNHIEM");
         	
        	query = "select gsbh_fk as gsbhId from ddkd_gsbh where ddkd_fk = '" + this.id + "'";
        	ResultSet rs2 = this.db.get(query);

        	if (rs2 != null)
        	{
        		while(rs2.next())
        		{
        			this.gsbhId = rs2.getString("gsbhId");
        		}
        		rs2.close();
        	}
        	
        	//KHONG PHAI THUOC HO, 1 TDV CO THE THUOC 2 NVBH
        	//if(!rs.getString("tructhuoc_fk").equals("1"))
        	//{
        		query = "select npp_fk from DAIDIENKINHDOANH_NPP where ddkd_fk = '" + this.id + "'";
        		rs2 = this.db.get(query);
        		if (rs2 != null)
            	{
            		while(rs2.next())
            		{
            			this.nppId += rs2.getString("npp_fk") + ",";
            		}
            		rs2.close();
            		
            		if(this.nppId.trim().length() > 0)
            		{
            			this.nppId = this.nppId.substring(0, this.nppId.length() - 1);
            		}
            	}
        	//}
        	
       	}
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
       	createRS(); 
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public void DBClose() {
		try{

		if(nppList!=null){
		 nppList.close();
		
		}
		
		if(gsbhList!=null){
			gsbhList.close();
			
			}
		if(RsDDKD!=null){
			RsDDKD.close();
		}
		if(db!=null){
			db.shutDown();
		}
		}catch(Exception er){
			
		}
	}

	
	public ResultSet GetRsDDKDTienNhiem() {

		return this.RsDDKD;
	}

	
	public void setDDKDTn(String id) {

		this.ddkdTn=id;
	}

	
	public String getDDKDTn() {

		return this.ddkdTn;
	}

	
	public void setPhantramChuyen(String phantram) {

		this.PhanTramChuyen=phantram;
	}

	
	public String getPhanTramChuyen() {

		return this.PhanTramChuyen;
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

	
	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}

	
	public String getMatkhau() {
		return this.matkhau;
	}

	
	public void setCothechonTN(String mathuviec) {
		
		this.cothechonTN = mathuviec;
	}

	
	public String getCothechonTN() {
		
		return this.cothechonTN;
	}

	public String getGsbanhang() {

		return this.gsbhId;
	}

	public void setGsbanhang(String gsbanhang) {

		this.gsbhId = gsbanhang;
	}

	
	public String getIsNVTT() {

		return this.isNVTT;
	}


	public void setIsNVTT(String nvtt) {
		
		this.isNVTT = nvtt;
		
	}
}