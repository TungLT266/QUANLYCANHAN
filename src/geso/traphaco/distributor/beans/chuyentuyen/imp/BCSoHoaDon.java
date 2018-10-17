package geso.traphaco.distributor.beans.chuyentuyen.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.chuyentuyen.IBCSoHoaDon;
import geso.traphaco.distributor.db.sql.dbutils;

public class BCSoHoaDon implements IBCSoHoaDon
{
	String msg, userId, nppId, tuyenFromId, tuyenToId, ddkdFromId, ddkdToId,nppTen;
	String sitecode;
	ResultSet kh_tbh_dpt,tuyenFromRs, tuyenToRs,ddkdFromRs,ddkdToRs;
	String type;
	
	String[] nvId;
	String[] nvMa;
	String[] nvTen;
	String[] nvKyhieu;
	String[] nvMauHD;
	String[] nvSotu;
	String[] nvSoden;
	String[] nvngayHD;

	String[] nvMauHD2;
	String[] nvKyhieu2;
	String[] nvSotu2;
	String[] nvSoden2;
	String[] nvngayHD2;
	
	String[] SohoadonUsed;
	
	dbutils db;
	
	Object loainhanvien;
	Object doituongId;
	
	public BCSoHoaDon()
	{
		this.msg="";
		this.nppId="";
		this.userId="";
		this.tuyenFromId="";
		this.tuyenToId="";
		this.ddkdFromId="";
		this.ddkdToId="";
		this.nppTen="";
		this.sitecode="";
		this.type = "";
		this.db=new dbutils();
		
	}
	public ResultSet getDdkdFromRs()
	{
		return ddkdFromRs;
	}

	public ResultSet getDdkdToRs()
	{
		return ddkdToRs;
	}

	
	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getNppId()
	{
		return nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getTuyenFromId()
	{
		return tuyenFromId;
	}

	public void setTuyenFromId(String tuyenFromId)
	{
		this.tuyenFromId = tuyenFromId;
	}

	public String getTuyenToId()
	{
		return tuyenToId;
	}

	public void setTuyenToId(String tuyenToId)
	{
		this.tuyenToId = tuyenToId;
	}

	public String getDdkdFromId()
	{
		return ddkdFromId;
	}

	public void setDdkdFromId(String ddkdFromId)
	{
		this.ddkdFromId = ddkdFromId;
	}

	public String getDdkdToId()
	{
		return ddkdToId;
	}

	public void setDdkdToId(String ddkdToId)
	{
		this.ddkdToId = ddkdToId;
	}

	
	public String getDddkdFromId()
	{
		return ddkdFromId;
	}

	
	public ResultSet getTuyenFromRs()
	{

		return this.tuyenFromRs;
	}

	
	public void setTuyenFromRs(ResultSet tuyenRs)
	{
		this.tuyenFromRs = tuyenRs;
	}

	
	public void setTuyenToRs(ResultSet tuyenRs)
	{
		this.tuyenToRs = tuyenRs;
	}

	
	public ResultSet getTuyenToRs()
	{
		return this.tuyenToRs;
	}

	
	public String getMessage()
	{

		return msg;
	}

	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	
	/****************************************************************************************
	 **********		Chuyển tuyến giữa 2 ĐDKD
	 **********		1 Khách Hàng thuộc 1 npp nên phải xóa các khách hàng 
	 ********** 		mà nằm trong những tuyến còn lại của ĐDKD đó rồi mới cập nhật lại
	 ********** 		Lưu thêm cột TBH_OLD để chuyển tuyến   (A_B_C)
	 ****************************************************************************************/
	
	public boolean MoveTbh(String[] khIds, String[] tansoIds,String [] sotts ) //Move cac khach hang thuoc tuyen ban hang cu sang tuyen ban hang moi 
	{
		if( this.ddkdFromId.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn trình dược viên muốn chuyển";
			return false;
		}
		
		if( this.ddkdToId.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn trình dược viên muốn chuyển đến";
			return false;
		}
		
		try
		{
			if( this.tuyenFromId.trim().length() > 0 )
			{
				if( this.tuyenToId.trim().length() <= 0 )
				{
					this.msg = "Vui lòng chọn tuyến muốn chuyển qua";
					return false;
				}
			}

			this.db.getConnection().setAutoCommit(false);
			String query="";

			//Nếu TDV mới chưa được phân cho khach hàng, thì phân qua
			query = " insert KhachHang_DaiDienKinhDoanh( KhachHang_FK, DDKD_FK ) "+
					" select distinct a.PK_SEQ, '" + this.ddkdToId + "' "+
					" from KHACHHANG a inner join KHACHHANG_TUYENBH b on a.PK_SEQ = b.KHACHHANG_FK"+
					" 		inner join TUYENBANHANG c on b.TBH_FK = c.PK_SEQ"+
					" where c.DDKD_FK = '" + this.ddkdFromId + "' and a.PK_SEQ not in ( select KhachHang_FK from KhachHang_DaiDienKinhDoanh where DDKD_FK = '" + this.ddkdToId + "' )";

			if( !db.update(query) )
			{
				this.db.update("rollback");
				this.msg = "Khong the di chuyen sang  tuyen ban hang moi , " + query;
				return false;
			}

			//CHUYEN SANG TUYEN MOI
			query = " insert KHACHHANG_TUYENBH( KHACHHANG_FK, TBH_FK, TANSO, SOTT ) "+
					 " select KHACHHANG_FK, '" + this.tuyenToId + "' as TBH_FK, TANSO, SOTT"+
					 " from KHACHHANG_TUYENBH"+
					 " where TBH_FK = '" + this.tuyenFromId + "' and KHACHHANG_FK not in ( select khachhang_fk from KHACHHANG_TUYENBH where TBH_FK = '" + this.tuyenToId + "' )";
			System.out.println(":::: CHUYEN SANG TUYEN MOI: " + query);
			if(!this.db.update(query))
			{
				this.db.update("rollback");
				this.msg = "Khong the di chuyen sang  tuyen ban hang moi , " + query;
				return false;
			}	
			
			query = " delete KHACHHANG_TUYENBH where TBH_FK in ( " + this.tuyenFromId + "  ) ";
			System.out.println(":::: XOA TUYEN CU: " + query);
			if(!this.db.update(query))
			{
				this.db.update("rollback");
				this.msg = "Khong the di chuyen sang  tuyen ban hang moi , " + query;
				return false;
			}	

			//NEU KHAC TDV THI CAP NHAT HET TRONG DLN HOP DONG
			if( !this.ddkdFromId.equals( this.ddkdToId ) )
			{
				query = "update ERP_HOPDONGNPP_SANPHAM set ddkd_fk = '" + this.ddkdToId + "' " + 
						" where ddkd_fk = '" + this.ddkdFromId + "' and hopdong_fk in ( select PK_SEQ from ERP_HOPDONGNPP where KHACHHANG_FK in ( select KHACHHANG_FK from KHACHHANG_TUYENBH where TBH_FK in ( select PK_SEQ from TUYENBANHANG where PK_SEQ = '" + this.tuyenToId + "' and DDKD_FK = '" + this.ddkdToId + "' ) ) ) ";
				System.out.println(":::: CAP NHAT LAI TDV: " + query);
				if(!this.db.update(query))
				{
					this.db.update("rollback");
					this.msg = "Khong the di chuyen sang tuyen ban hang moi , " + query;
					return false;
				}	
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception er)
		{
			this.db.update("rollback");
			this.msg="Loi : "+ er.toString();
			er.printStackTrace();
			return false;
		}
	}
	
	public boolean MoveTbh_All()
	{
		if( this.ddkdFromId.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn trình dược viên muốn chuyển";
			return false;
		}
		
		if( this.ddkdToId.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn trình dược viên muốn chuyển đến";
			return false;
		}
		
		boolean _flag1 = true;
		try 
		{
			this.db.getConnection().setAutoCommit(false);	
			
			//Nếu TDV mới chưa được phân cho khach hàng, thì phân qua
			String query = " insert KhachHang_DaiDienKinhDoanh( KhachHang_FK, DDKD_FK ) "+
					 " select distinct a.PK_SEQ, '" + this.ddkdToId + "' "+
					 " from KHACHHANG a inner join KHACHHANG_TUYENBH b on a.PK_SEQ = b.KHACHHANG_FK"+
					 " 		inner join TUYENBANHANG c on b.TBH_FK = c.PK_SEQ"+
					 " where c.DDKD_FK = '" + this.ddkdFromId + "' and a.PK_SEQ not in ( select KhachHang_FK from KhachHang_DaiDienKinhDoanh where DDKD_FK = '" + this.ddkdToId + "' )";
			
			if( !db.update(query) )
			{
				this.db.update("rollback");
				this.msg = "Khong the di chuyen sang  tuyen ban hang moi , " + query;
				return false;
			}
			
			//CHECK XEM CO TUYEN NAO CHUA DUOC KHAI BAO BEN TDV MOI CHUA
			query = "select NGAYID from TUYENBANHANG " + 
					" where DDKD_FK  = '" + this.ddkdFromId + "' and NGAYID not in ( select NGAYID from TUYENBANHANG where  DDKD_FK = '" + this.ddkdToId + "' )";
			String ngayIds = "";
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next())
				{
					ngayIds += rs.getString("NGAYID") + ", ";
				}
				rs.close();
			}
			
			if( ngayIds.trim().length() > 0 )
			{
				this.db.update("rollback");
				this.msg = "Các tuyến sau " + ngayIds + " chưa có trong dữ liệu nền của trình dược viên muốn chuyển qua";
				return false;
			}
			
			//CHUYEN HET KHACH HANG CUA TDV QUA
			query =  " insert KHACHHANG_TUYENBH( KHACHHANG_FK, TBH_FK, TANSO, SOTT ) "+
					 " select KHACHHANG_FK, ( select top(1) PK_SEQ from TUYENBANHANG where DDKD_FK = '" + this.ddkdToId + "' and ngayID = b.ngayId ) as TBH_FK, TANSO, SOTT "+
					 " from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on a.TBH_FK = b.PK_SEQ"+
					 " where b.DDKD_FK = '" + this.ddkdFromId + "' and KHACHHANG_FK not in ( select khachhang_fk from KHACHHANG_TUYENBH "+
					 " 					where TBH_FK in ( select PK_SEQ from TUYENBANHANG where DDKD_FK = '" + this.ddkdToId + "' ) )";

			System.out.println(":::: CHEN TUYEN: " + query);
			if(!this.db.update(query))
			{
				this.db.update("rollback");
				this.msg = "Khong the di chuyen sang  tuyen ban hang moi , " + query;
				return false;
			}
			
			//LUU LAI LOG CHUYEN TUYEN
			
			//XOA TUYEN CU
			query = " delete KHACHHANG_TUYENBH where TBH_FK in ( select pk_seq from TUYENBANHANG where ddkd_fk = '" + this.ddkdFromId + "'  ) ";
			System.out.println(":::: XOA HET TUYEN CU: " + query);
			if(!this.db.update(query))
			{
				this.db.update("rollback");
				this.msg = "Khong the di chuyen sang  tuyen ban hang moi , " + query;
				return false;
			}	
			
			//NEU KHAC TDV THI CAP NHAT HET TRONG DLN HOP DONG
			if( !this.ddkdFromId.equals( this.ddkdToId ) )
			{
				query = "update ERP_HOPDONGNPP_SANPHAM set ddkd_fk = '" + this.ddkdToId + "' " + 
						" where ddkd_fk = '" + this.ddkdFromId + "' and hopdong_fk in ( select PK_SEQ from ERP_HOPDONGNPP where KHACHHANG_FK in ( select KHACHHANG_FK from KHACHHANG_TUYENBH where TBH_FK in ( select PK_SEQ from TUYENBANHANG where DDKD_FK = '" + this.ddkdToId + "' ) ) ) ";
				System.out.println(":::: CAP NHAT LAI TDV: " + query);
				if(!this.db.update(query))
				{
					this.db.update("rollback");
					this.msg = "Khong the di chuyen sang tuyen ban hang moi , " + query;
					return false;
				}	
			}
				
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		} 
		catch (Exception e) 
		{
			this.db.update("rollback");
			this.msg="Loi : "+ e.toString();
			e.printStackTrace();
		}
		
		return true;
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
	

	
	public void createRs()
	{
		Utility util = new Utility();
		this.getNppInfo();
		
		String query = "";
		//query="select PK_SEQ,ten +' - ' +isnull(maNhanVien,'')  as Ten from DAIDIENKINHDOANH where NPP_FK='"+this.nppId+"' ";
		
		query = "select PK_SEQ,ten + ' - ' + isnull(maFast, maNhanVien)  as Ten from DAIDIENKINHDOANH a where pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
		//if(this.ddkdToId!="")
			//query+=" and pk_seq!='"+this.ddkdToId+"' ";
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		this.ddkdFromRs=this.db.get(query);
		System.out.println("[DaiDienFrom]"+query);
		
		//query="select PK_SEQ,ten +' - ' +isnull(maNhanVien,'')  as Ten from DAIDIENKINHDOANH where NPP_FK='"+this.nppId+"' ";
		
		query="select PK_SEQ, ten + ' - ' + isnull(maFast, maNhanVien)  as Ten from DAIDIENKINHDOANH where pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
		//if(this.ddkdFromId!="")
			//query+=" and pk_seq!='"+this.ddkdFromId+"' ";
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
				
		this.ddkdToRs=this.db.get(query);
		System.out.println("[DaiDienTo]"+query);
		
	
		if(this.ddkdFromId!="")
		{
			query="select PK_SEQ ,DIENGIAI as Ten,NGAYLAMVIEC,NGAYID from TUYENBANHANG  where DDKD_FK='"+this.ddkdFromId+"' and NPP_FK='"+this.nppId+"' order by NGAYID ";
			this.tuyenFromRs=this.db.get(query);
			System.out.println("[TuyenFrom]"+query);
		}
		
		if(this.ddkdToId!="")
		{
			query="select PK_SEQ ,DIENGIAI as Ten,NGAYLAMVIEC,NGAYID from TUYENBANHANG  where DDKD_FK='"+this.ddkdToId+"' and NPP_FK='"+this.nppId+"'  order by NGAYID";
			this.tuyenToRs=this.db.get(query);
			System.out.println("[TuyenTo]"+query);
		}
		
		if( this.ddkdFromId.trim().length() > 0 )
		{
			query =
			"select distinct a.pk_seq as khId, a.maFAST as smartid, a.ten, a.diachi, b.tanso,isnull(b.sott,0) as sott," +
			" (select ngayid from TUYENBANHANG where PK_SEQ =b.TBH_FK) as Ngay  \n"+
			 " from khachhang a inner join khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk " ;
			 if(this.tuyenFromId!="")
					query+=" and  b.tbh_fk='" + this.tuyenFromId + "'";
			 
			query+=" where b.tbh_fk in(select pk_seq from tuyenbanhang where ddkd_fk='"+this.ddkdFromId+"' and npp_fk='"+this.nppId+"')  " ;
			
			query+=" order by ngay,sott \n";
			
			System.out.println("2.Query tuyen: " + query);
			this.kh_tbh_dpt = this.db.get(query);
		}
		
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
	
	public ResultSet getDkdFromRs()
	{
		return this.ddkdFromRs;
	}

	
	public void setDdkdFromRs(ResultSet ddkdRs)
	{
		this.ddkdFromRs=ddkdRs;
	}

	
	public void setDdkdToRs(ResultSet ddkdRs)
	{
		this.ddkdToRs=ddkdRs;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public ResultSet getKh_Tbh_DptList() 
	{
		return this.kh_tbh_dpt;
	}

	public void setKh_Tbh_DptList(ResultSet kh_tbh_dpt) 
	{
		this.kh_tbh_dpt = kh_tbh_dpt;
	}
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	public String getType() {

		return this.type;
	}

	public void setType(String type) {
	
		this.type = type;
	}
	
	public String[] getNvId() {
		
		return this.nvId;
	}
	
	public void setNvId(String[] nvId) {
		
		this.nvId = nvId;
	}
	
	public String[] getNvMa() {
		
		return this.nvMa;
	}
	
	public void setNvMa(String[] nvMa) {
		
		this.nvMa = nvMa;
	}
	
	public String[] getNvTen() {
		
		return this.nvTen;
	}
	
	public void setNvTen(String[] nvTen) {
		
		this.nvTen = nvTen;
	}
	
	public String[] getNvSotu() {
		
		return this.nvSotu;
	}
	
	public void setNvSotu(String[] nvSotu) {
		
		this.nvSotu = nvSotu;
	}
	
	public String[] getNvSoden() {
		
		return this.nvSoden;
	}
	
	public void setNvSoden(String[] nvSoden) {
		
		this.nvSoden = nvSoden;
	}
	
	public void initSohoadon() 
	{
		this.getNppInfo();
			String info=" select count(*) as sl from nhanvien nv where nv.PHANLOAI=2  and ISNULL(nv.LOAI,0) not in(3,4) and nv.pk_seq='"+this.userId+"'";
			System.out.println("info "+info);
			ResultSet checkinfo=db.get(info);
			int kq=0;
			try {
				while(checkinfo.next())
				{
					kq=checkinfo.getInt("sl");
				}
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			String query="";
			if(kq==1){
				query="select nv.pk_seq, nv.dangnhap, nv.ten, isnull(nv.sohoadontu, ' ') as sohoadontu, isnull(nv.sohoadonden, ' ') as sohoadonden, \n"+
				      "   isnull(ngayhoadon,' ') as ngayhoadon, isnull(kyhieu,'') as kyhieu, isnull(mauhoadon,'') as mauhoadon,   \n"+     
				      "   isnull(ngayhoadon2,' ') as ngayhoadon2, isnull(kyhieu2,'') as kyhieu2, isnull(mauhoadon2,'') as mauhoadon2,     \n"+   
				      "   isnull(nv.sohoadontu2, ' ') as sohoadontu2, isnull(nv.sohoadonden2, ' ') as sohoadonden2,  "+
				      " isnull(( select A.SOHOADON + ',' from ERP_XUATHOADONKM  A WHERE A.NGUOISUA = nv.PK_SEQ AND TRANGTHAI!=2 AND ISNUMERIC(A.sohoadon) =1 \n"+
			       		"	 AND cast(A.sohoadon as int) >= cast(ISNULL(nv.SOHOADONTU, -1) as int) \n"+
			       		"	 AND cast(A.sohoadon as int) <= cast(ISNULL(nv.SOHOADONDEN, -1) as int) \n"+
			       		"	 AND A.kyhieu = nv.KYHIEU \n"+
			       		"	 FOR XML PATH ('')), '') SOHOADONKM, \n"+
			       		"   isnull(( select A.SOHOADON + ',' from ERP_HOADONNPP  A WHERE A.NGUOISUA = nv.PK_SEQ AND TRANGTHAI!=2 AND ISNUMERIC(A.sohoadon) =1 \n"+
			       		"	 AND cast(A.sohoadon as int) >= cast(ISNULL(nv.SOHOADONTU, -1) as int)  \n"+
			       		"	 AND cast(A.sohoadon as int) <= cast(ISNULL(nv.SOHOADONDEN, -1) as int) \n"+
			       		"	 AND A.kyhieu = nv.KYHIEU \n"+					       			
			       		"   FOR XML PATH ('')),'') SOHOADON	\n"+
				      "   from nhanvien nv  "+
					  "   where nv.PHANLOAI=2 AND ISNULL(NV.LOAI,0) NOT IN (3,4) "+
				      "   ORDER BY DANGNHAP DESC";
			}
			else
			{
				if(this.userId.equals("100336"))
				{
					 query = 	"select nv.pk_seq, nv.dangnhap, nv.ten, isnull(nv.sohoadontu, ' ') as sohoadontu, isnull(nv.sohoadonden, ' ') as sohoadonden," +
					       		"       isnull(ngayhoadon,' ') as ngayhoadon, isnull(kyhieu,'') as kyhieu, isnull(mauhoadon,'') as mauhoadon, " +
					       		"       isnull(ngayhoadon2,' ') as ngayhoadon2, isnull(kyhieu2,'') as kyhieu2, isnull(mauhoadon2,'') as mauhoadon2, " +
					       		"       isnull(nv.sohoadontu2, ' ') as sohoadontu2, isnull(nv.sohoadonden2, ' ') as sohoadonden2, " +
					       		" isnull(( select A.SOHOADON + ',' from ERP_XUATHOADONKM  A WHERE A.NGUOISUA = nv.PK_SEQ AND TRANGTHAI!=2 \n"+
					       		"	 AND cast(A.sohoadon as int) >= cast(ISNULL(nv.SOHOADONTU, -1) as int) AND ISNUMERIC(A.sohoadon) =1 \n"+
					       		"	 AND cast(A.sohoadon as int) <= cast(ISNULL(nv.SOHOADONDEN, -1) as int) \n"+
					       		"	 AND A.kyhieu = nv.KYHIEU \n"+
					       		"	 FOR XML PATH ('')), '') SOHOADONKM, \n"+
					       		"   isnull(( select A.SOHOADON + ',' from ERP_HOADONNPP  A WHERE A.NGUOISUA = nv.PK_SEQ AND TRANGTHAI!=2 AND ISNUMERIC(A.sohoadon) =1  \n"+
					       		"	 AND cast(A.sohoadon as int) >= cast(ISNULL(nv.SOHOADONTU, -1) as int) \n"+
					       		"	 AND cast(A.sohoadon as int) <= cast(ISNULL(nv.SOHOADONDEN, -1) as int) \n"+
					       		"	 AND A.kyhieu = nv.KYHIEU \n"+					       			
					       		"   FOR XML PATH ('')),'') SOHOADON	\n"+					     	   
					       		"from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode = sitecode  " +
					       		"where  nv.trangthai = '1' and npp.isKHACHHANG = '0' and xuathoadon = 1";
				}
				else
					 query =   "select nv.pk_seq, nv.dangnhap, nv.ten, isnull(nv.sohoadontu, ' ') as sohoadontu, isnull(nv.sohoadonden, ' ') as sohoadonden," +
						       "       isnull(ngayhoadon,' ') as ngayhoadon, isnull(kyhieu,'') as kyhieu, isnull(mauhoadon,'') as mauhoadon, " +
						       "       isnull(ngayhoadon2,' ') as ngayhoadon2, isnull(kyhieu2,'') as kyhieu2, isnull(mauhoadon2,'') as mauhoadon2, " +
						       "       isnull(nv.sohoadontu2, ' ') as sohoadontu2, isnull(nv.sohoadonden2, ' ') as sohoadonden2, " +
						       " isnull(( select A.SOHOADON + ',' from ERP_XUATHOADONKM  A WHERE A.NGUOISUA = nv.PK_SEQ AND TRANGTHAI!=2 AND ISNUMERIC(A.sohoadon) =1 \n"+
					       		"	 AND cast(A.sohoadon as int) >= cast(ISNULL(nv.SOHOADONTU, -1) as int) \n"+
					       		"	 AND cast(A.sohoadon as int) <= cast(ISNULL(nv.SOHOADONDEN, -1) as int) \n"+
					       		"	 AND A.kyhieu = nv.KYHIEU \n"+
					       		"	 FOR XML PATH ('')), '') SOHOADONKM, \n"+
					       		"   isnull(( select A.SOHOADON + ',' from ERP_HOADONNPP  A WHERE A.NGUOISUA = nv.PK_SEQ AND TRANGTHAI!=2 AND ISNUMERIC(A.sohoadon) =1  \n"+
					       		"	 AND cast(A.sohoadon as int) >= cast(ISNULL(nv.SOHOADONTU, -1) as int) \n"+
					       		"	 AND cast(A.sohoadon as int) <= cast(ISNULL(nv.SOHOADONDEN, -1) as int) \n"+
					       		"	 AND A.kyhieu = nv.KYHIEU \n"+					       			
					       		"   FOR XML PATH ('')),'') SOHOADON	\n"+
							   "from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode = sitecode  " +
							   "where nv.pk_seq = '" + this.userId + "' and nv.trangthai = '1' and npp.isKHACHHANG = '0' and xuathoadon = 1";
			}
			
				
		System.out.println("--LAY NHAN VIEN: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nvId = "";
				String nvMA = "";
				String nvTEN = "";
				String nvSohoadontu = "";
				String nvSohoadonden = "";
				String nvNgayhd = "";
				String nvKyhieu = "";
				String nvMauHD = "";
				
				String nvSohoadontu2 = "";
				String nvSohoadonden2 = "";
				String nvNgayhd2 = "";
				String nvKyhieu2 = "";
				String nvMauHD2 = "";
				
				String nvSohoadonUsed = "";
				
				while(rs.next())
				{
					nvId += rs.getString("pk_seq") + "__";
					nvMA += rs.getString("dangnhap") + "__";
					nvTEN += rs.getString("ten") + "__";
					
					if(rs.getString("sohoadontu").trim().length() > 0 )
						nvSohoadontu += rs.getString("sohoadontu") + "__";
					else
						nvSohoadontu += " __";
					
					if(rs.getString("sohoadonden").trim().length() > 0 )
						nvSohoadonden += rs.getString("sohoadonden") + "__";
					else
						nvSohoadonden += " __";
					
					if(rs.getString("ngayhoadon").trim().length() > 0 )
						nvNgayhd += rs.getString("ngayhoadon") + "__";
					else
						nvNgayhd += " __";
					
					if(rs.getString("kyhieu").trim().length() > 0 )
						nvKyhieu += rs.getString("kyhieu") + "__";
					else
						nvKyhieu += " __";
					
					if(rs.getString("mauhoadon").trim().length() > 0 )
						nvMauHD += rs.getString("mauhoadon") + "__";
					else
						nvMauHD += " __";
					
					// KYHIEU2
					if(rs.getString("sohoadontu2").trim().length() > 0 )
						nvSohoadontu2 += rs.getString("sohoadontu2") + "__";
					else
						nvSohoadontu2 += " __";
					
					if(rs.getString("sohoadonden2").trim().length() > 0 )
						nvSohoadonden2 += rs.getString("sohoadonden2") + "__";
					else
						nvSohoadonden2 += " __";
					
					if(rs.getString("ngayhoadon2").trim().length() > 0 )
						nvNgayhd2 += rs.getString("ngayhoadon2") + "__";
					else
						nvNgayhd2 += " __";
					
					if(rs.getString("kyhieu2").trim().length() > 0 )
						nvKyhieu2 += rs.getString("kyhieu2") + "__";
					else
						nvKyhieu2 += " __";
					
					if(rs.getString("mauhoadon2").trim().length() > 0 )
						nvMauHD2 += rs.getString("mauhoadon2") + "__";
					else
						nvMauHD2 += " __";
					
					nvSohoadonUsed += rs.getString("SOHOADONKM");
					nvSohoadonUsed += rs.getString("SOHOADON");
					
					nvSohoadonUsed += " __";
					
				}
				
				if(nvId.trim().length() > 0)
				{
					nvId = nvId.substring(0, nvId.length() - 2);
					this.nvId = nvId.split("__");
					
					nvMA = nvMA.substring(0, nvMA.length() - 2);
					this.nvMa = nvMA.split("__");
					
					nvTEN = nvTEN.substring(0, nvTEN.length() - 2);
					this.nvTen = nvTEN.split("__");
					
					nvSohoadontu = nvSohoadontu.substring(0, nvSohoadontu.length() - 2);
					this.nvSotu = nvSohoadontu.split("__");
					
					nvSohoadonden = nvSohoadonden.substring(0, nvSohoadonden.length() - 2);
					this.nvSoden = nvSohoadonden.split("__");
					
					nvNgayhd = nvNgayhd.substring(0, nvNgayhd.length() - 2);
					this.nvngayHD = nvNgayhd.split("__");
					
					nvKyhieu = nvKyhieu.substring(0, nvKyhieu.length() - 2);
					this.nvKyhieu = nvKyhieu.split("__");
					
					nvMauHD = nvMauHD.substring(0, nvMauHD.length() - 2);
					this.nvMauHD = nvMauHD.split("__");
					
					// KYHIEU2
					nvSohoadontu2 = nvSohoadontu2.substring(0, nvSohoadontu2.length() - 2);
					this.nvSotu2 = nvSohoadontu2.split("__");
					
					nvSohoadonden2 = nvSohoadonden2.substring(0, nvSohoadonden2.length() - 2);
					this.nvSoden2 = nvSohoadonden2.split("__");
					
					nvNgayhd2 = nvNgayhd2.substring(0, nvNgayhd2.length() - 2);
					this.nvngayHD2 = nvNgayhd2.split("__");
					
					nvKyhieu2 = nvKyhieu2.substring(0, nvKyhieu2.length() - 2);
					this.nvKyhieu2 = nvKyhieu2.split("__");
					
					nvMauHD2 = nvMauHD2.substring(0, nvMauHD2.length() - 2);
					this.nvMauHD2 = nvMauHD2.split("__");
					
					nvSohoadonUsed = nvSohoadonUsed.substring(0, nvSohoadonUsed.length() - 2);
					this.SohoadonUsed = nvSohoadonUsed.split("__");
				}
				
				System.out.println("---SO HOA DON TU: " + nvSohoadontu);
			} 
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
	}

	public boolean saveSohoadon()
	{
		this.getNppInfo();
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			if(this.nvId != null)
			{
				String query = "";
				String nvIds = "";
				for(int i = 0; i < this.nvId.length; i++)
				{}
				
				//CHECK KHÔNG CHO PHÉP TỒN TẠI TRÙNG SỐ HÓA ĐƠN
				if( nvIds.trim().length() > 0 )
				{}
			}
			
			db.getConnection().commit();
		} 
		catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String[] getNvNgayHD() 
	{
		return nvngayHD;
	}
	public void setNvNgayHD(String[] nvngayHD) 
	{
		this.nvngayHD = nvngayHD;
	}

	public String[] getNvKyhieu() 
	{
		return this.nvKyhieu;
	}

	public void setNvKyhieu(String[] nvKyhieu) 
	{
		this.nvKyhieu = nvKyhieu;
	}

	public String[] getNvKyhieu2()
	{
		return this.nvKyhieu2;
	}

	public void setNvKyhieu2(String[] nvKyhieu2) 
	{
		this.nvKyhieu2 = nvKyhieu2 ;
	}

	public String[] getNvSotu2() 
	{
		return this.nvSotu2;
	}

	public void setNvSotu2(String[] nvSotu2) 
	{
		this.nvSotu2 = nvSotu2 ;
	}

	public String[] getNvSoden2() 
	{
		return this.nvSoden2;
	}

	public void setNvSoden2(String[] nvSoden2) 
	{
		this.nvSoden2 = nvSoden2 ;
	}

	public String[] getNvNgayHD2() 
	{
		return this.nvngayHD2;
	}

	public void setNvNgayHD2(String[] nvngayHD2) 
	{
		this.nvngayHD2 = nvngayHD2 ;
	}

	public String[] getNvMauHD() 
	{
		return this.nvMauHD;
	}

	public void setNvMauHD(String[] nvMauHD) 
	{
		this.nvMauHD = nvMauHD;
	}

	public String[] getNvMauHD2() 
	{
		return this.nvMauHD2;
	}

	public void setNvMauHD2(String[] nvMauHD2) 
	{
		this.nvMauHD2 = nvMauHD2;
	}

	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}
	
	public String[] getSoHoaDonUsed() {
		
		return this.SohoadonUsed;
	}
	
	public void setSoHoaDonUsed(String[] sohoadonUsed) {
		
		this.SohoadonUsed = sohoadonUsed;
	}
	
}
