package geso.traphaco.center.beans.chuyentuyen.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.beans.chuyentuyen.IChuyenTuyen;
import geso.traphaco.center.db.sql.dbutils;

public class ChuyenTuyen implements IChuyenTuyen
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
	
	dbutils db;
	
	public ChuyenTuyen()
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
		try
		{
			this.db.getConnection().setAutoCommit(false);
			String query="";
			query=
			"	select KHACHHANG_FK,(select smartId From KHACHHANG WHERE PK_SEQ=KHACHHANG_FK) AS SmartId, "+ 
			"			(select TEN From KHACHHANG WHERE PK_SEQ=KHACHHANG_FK) AS khTen,  "+
			"			(select DIACHI From KHACHHANG WHERE PK_SEQ=KHACHHANG_FK) AS diachi "+ 
			"	from KHACHHANG_TUYENBH  "+
			"			where TBH_FK in(select PK_SEQ from TUYENBANHANG where DDKD_FK='"+this.ddkdFromId+"') "+
			"group by KHACHHANG_FK "+
			"having COUNT(*)>1" ;
			ResultSet rs=this.db.get(query);
			System.out.println("[Query From ]"+query);
			while(rs.next())
			{
				query= "delete from KHACHHANG_TUYENBH where KHACHHANG_FK="+rs.getString("KHACHHANG_FK")+" and tbh_fk!='"+this.tuyenFromId+"'";
				if( !this.db.update(query))
				{
		    		this.db.update("rollback");
					this.msg = "Khong the di chuyen sang tuyen ban hang moi , " + query;
					return false;
				}
			}rs.close();
			query=
			"	select KHACHHANG_FK,(select smartId From KHACHHANG WHERE PK_SEQ=KHACHHANG_FK) AS SmartId, "+ 
			"			(select TEN From KHACHHANG WHERE PK_SEQ=KHACHHANG_FK) AS khTen,  "+
			"			(select DIACHI From KHACHHANG WHERE PK_SEQ=KHACHHANG_FK) AS diachi "+ 
			"	from KHACHHANG_TUYENBH  "+
			"			where TBH_FK in(select PK_SEQ from TUYENBANHANG where DDKD_FK='"+this.ddkdToId+"') "+
			"group by KHACHHANG_FK "+
			"having COUNT(*)>1" ;
			rs=this.db.get(query);
			System.out.println("[Query To ]"+query);
			while(rs.next())
			{
				query= "delete from KHACHHANG_TUYENBH where KHACHHANG_FK="+rs.getString("KHACHHANG_FK")+" and tbh_fk!='"+this.tuyenToId+"'";
				if( !this.db.update(query))
				{
		    		this.db.update("rollback");
					this.msg = "Khong the di chuyen sang tuyen ban hang moi , " + query;
					return false;
				}
			}
			rs.close();
			query=
				"insert into tmp(KHACHHANG_FK,tbh_fk,ddkd_fk,TANSO) "+
				"select KHACHHANG_FK,TBH_FK,tbh.DDKD_FK,kh.TANSO "+
				" from KHACHHANG_TUYENBH kh "+
				"		inner join TUYENBANHANG tbh on tbh.PK_SEQ=kh.TBH_FK "+
				"	where tbh.PK_SEQ in("+this.tuyenFromId+","+this.tuyenToId+"	) ";
			
			if( !this.db.update(query))
			{
	    		this.db.update("rollback");
				this.msg = "Khong the di chuyen sang tuyen ban hang moi , " + query;
				return false;
			}
			
			query="delete from KHACHHANG_TUYENBH  where TBH_FK in("+this.tuyenFromId+","+this.tuyenToId+"	)" ;
			if( !this.db.update(query))
			{
	    		this.db.update("rollback");
				this.msg = "Khong the di chuyen sang tuyen ban hang moi , " + query;
				return false;
			}
			
			query=
			" select khachhang_fk,tbh_fk,tanso "+
			"from tmp "+
			"where tbh_fk="+this.tuyenFromId+" ";
			rs=this.db.get(query);
			int sott=0;
			while(rs.next())
			{
				String khId=rs.getString("khachhang_fk");
				String tanso=rs.getString("tanso");
				query=
					"insert KHACHHANG_TUYENBH (khachhang_fk,tbh_fk,tanso,sott) " +
					" select "+khId+","+this.tuyenToId+",'"+tanso+"',"+sott+"    ";
				if( !this.db.update(query))
				{
		    		this.db.update("rollback");
					this.msg = "Khong the di chuyen sang tuyen ban hang moi , " + query;
					return false;
				}
				sott++;
			}
			rs.close();
			sott=0;
			query=
			" select khachhang_fk,tbh_fk,tanso "+
			"from tmp "+
			"where tbh_fk="+this.tuyenToId+" ";
			rs=this.db.get(query);
			while(rs.next())
			{
				String khId=rs.getString("khachhang_fk");
				String tanso=rs.getString("tanso");
				query=
						"insert KHACHHANG_TUYENBH (khachhang_fk,tbh_fk,tanso,sott) " +
								" select "+khId+","+this.tuyenFromId+",'"+tanso+"',"+sott+"    ";
				if( !this.db.update(query))
				{
		    		this.db.update("rollback");
					this.msg = "Khong the di chuyen sang tuyen ban hang moi , " + query;
					return false;
				}
				sott++;
			}
			rs.close();
			
			query="delete from tmp ";
			if( !this.db.update(query))
			{
	    		this.db.update("rollback");
				this.msg = "Khong the di chuyen sang tuyen ban hang moi , " + query;
				return false;
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
	

	/*
	Thuc hien DAO TUYEN toan bo the nao?
			Bang TEMP luu cac dong thong tin cua Huynh Kiem Minh Sanh gom: PK_SEQ
			CurA luu 1 dong thong tin cua Hoang Trung Hieu gom: PK_SEQ,TEN_DDKD,DDKD_FK,NGAYID
			CurB luu 1 dong thong tin cua Huynh Kien Minh Sanh gom: PK_SEQ,TEN_DDKD,DDKD_FK,NGAYID
			Cap nhat toan bo du lieu Hoang Trung Hieu bang CurB
			Cap nhat toan bo du lieu cua Huynh Kiem Minh Sanh bang CurA va bang TEMP
	--
	VD:2 NV ban hang co maNV va danh sach Ma cac tuyen ban hang tuong ung.		
	Hoang Trung Hieu (100213): 101078,101079
	Huynh Kien Minh Sanh (100214): 101084,101085
	*/	
	public boolean MoveTbh_All()
	{
		boolean _flag1 = true;
		try 
		{
			this.db.getConnection().setAutoCommit(false);	
			String query="declare @maNVBHTuyenCu numeric(18,0) \n"
					+ "set @maNVBHTuyenCu= '" + this.ddkdFromId + "' \n"
					+ "declare @maNVBHTuyenMoi numeric(18,0) \n"
					+ "set @maNVBHTuyenMoi= '"+ this.ddkdToId + "' \n"
					//Bang TEMP...
					+ "declare @TEMP table \n"
					+ "( \n"
					+ "PK_SEQ numeric(18,0) \n"
					+ ") \n"
					+ "insert into @TEMP(PK_SEQ) \n"
					+ "select tbh.PK_SEQ from TUYENBANHANG tbh \n"
					+ "where tbh.DDKD_FK=@maNVBHTuyenMoi \n"
					//CurA luu 1 dong thong tin cua...
					+ "declare OldSR_cursor CURSOR \n"
					+ "for select tbh.PK_SEQ,dd.TEN,tbh.DDKD_FK,tbh.NGAYID from TUYENBANHANG tbh, DAIDIENKINHDOANH dd \n"
					+ "where tbh.DDKD_FK=dd.PK_SEQ and tbh.DDKD_FK=@maNVBHTuyenCu \n"
					+ "open OldSR_cursor \n"
					+ "declare @pk_seq1 numeric(18,0) \n"
					+ "declare @ten1 nvarchar(100) \n"
					+ "declare @ddkd1 numeric(18,0) \n"
					+ "declare @ngay1 numeric(18,0) \n"
					+ "fetch next from OldSR_cursor into @pk_seq1,@ten1,@ddkd1,@ngay1 \n"
					//CurB luu 1 dong thong tin cua...
					+ "declare NewSR_cursor CURSOR \n"
					+ "for select tbh.PK_SEQ,dd.TEN,tbh.DDKD_FK,tbh.NGAYID from TUYENBANHANG tbh, DAIDIENKINHDOANH dd \n"
					+ "where tbh.DDKD_FK=dd.PK_SEQ and tbh.DDKD_FK=@maNVBHTuyenMoi \n"
					+ "open NewSR_cursor \n"
					+ "declare @pk_seq2 numeric(18,0) \n"
					+ "declare @ten2 nvarchar(100) \n"
					+ "declare @ddkd2 numeric(18,0) \n"
					+ "declare @ngay2 numeric(18,0) \n"
					+ "fetch next from NewSR_cursor into @pk_seq2,@ten2,@ddkd2,@ngay2 \n"
					//Cap nhat toan bo du lieu cua...bang CurB
					+ "update tbh set tbh.DDKD_FK=@maNVBHTuyenMoi,tbh.DIENGIAI='T'+convert(varchar(2),tbh.NGAYID)+'_'+@ten2 \n"
					+ "from TUYENBANHANG tbh \n"
					+ "where tbh.DDKD_FK=@maNVBHTuyenCu \n"
					//Cap nhat toan bo du lieu cua...bang CurA va bang TEMP
					+ "update tbh set tbh.DDKD_FK=@maNVBHTuyenCu, tbh.DIENGIAI='T'+CONVERT(varchar(2),tbh.NGAYID)+'_'+@ten1 \n"
					+ "from TUYENBANHANG tbh, @TEMP t \n"
					+ "where tbh.PK_SEQ=t.PK_SEQ \n"
					+ "CLOSE OldSR_cursor \n"
					+ "DEALLOCATE  OldSR_cursor \n"
					+ "CLOSE NewSR_cursor \n"
					+ "DEALLOCATE NewSR_cursor \n"
					+ "";
			System.out.println(query);
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			if(!this.db.update(query))
			{
				this.db.update("rollback");
				this.msg = "Khong the di chuyen sang  tuyen ban hang moi , " + query;
				_flag1 = false;
			}			
			
		} catch (SQLException e) {
			this.db.update("rollback");
			this.msg="Loi : "+ e.toString();
			e.printStackTrace();
		}		
		return _flag1;
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
		this.getNppInfo();
		String query="";
		//query="select PK_SEQ,ten +' - ' +isnull(maNhanVien,'')  as Ten from DAIDIENKINHDOANH where NPP_FK='"+this.nppId+"' ";
		
		query = "select PK_SEQ,ten +' - ' +isnull(maNhanVien,'')  as Ten from DAIDIENKINHDOANH where pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
		if(this.ddkdToId!="")
			query+=" and pk_seq!='"+this.ddkdToId+"' ";
		this.ddkdFromRs=this.db.get(query);
		System.out.println("[DaiDienFrom]"+query);
		
		//query="select PK_SEQ,ten +' - ' +isnull(maNhanVien,'')  as Ten from DAIDIENKINHDOANH where NPP_FK='"+this.nppId+"' ";
		
		query="select PK_SEQ,ten +' - ' +isnull(maNhanVien,'')  as Ten from DAIDIENKINHDOANH where pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
		if(this.ddkdFromId!="")
			query+=" and pk_seq!='"+this.ddkdFromId+"' ";
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
		
		query =
		"select distinct a.pk_seq as khId,a.smartid, a.ten, a.diachi, b.tanso,isnull(b.sott,0) as sott," +
		" (select ngayid from TUYENBANHANG where PK_SEQ =b.TBH_FK) as Ngay  \n"+
		 " from khachhang a inner join khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk " ;
		 if(this.tuyenFromId!="")
				query+=" and  b.tbh_fk='" + this.tuyenFromId + "'";
		 query+=" where b.tbh_fk in(select pk_seq from tuyenbanhang where ddkd_fk='"+this.ddkdFromId+"' and npp_fk='"+this.nppId+"')  " ;
		
		query+=" order by ngay,sott \n";
		
		System.out.println("2.Query tuyen: " + query);
		this.kh_tbh_dpt = this.db.get(query);
		
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String query="";
			
				query="select nv.pk_seq, nv.dangnhap, nv.ten, isnull(nv.sohoadontu, ' ') as sohoadontu, isnull(nv.sohoadonden, ' ') as sohoadonden, "+
				      "   isnull(ngayhoadon,' ') as ngayhoadon, isnull(kyhieu,'') as kyhieu, isnull(mauhoadon,'') as mauhoadon,   "+     
				      "   isnull(ngayhoadon2,' ') as ngayhoadon2, isnull(kyhieu2,'') as kyhieu2, isnull(mauhoadon2,'') as mauhoadon2,     "+   
				      "   isnull(nv.sohoadontu2, ' ') as sohoadontu2, isnull(nv.sohoadonden2, ' ') as sohoadonden2  "+
				      "   from nhanvien nv  "+
					  "   where nv.PHANLOAI=2 AND ISNULL(NV.LOAI,0) NOT IN (3,4) and CONVSITECODE not in (select SITECODE from NHAPHANPHOI )  "+
				      "   ORDER BY DANGNHAP DESC";
			
		
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
				for(int i = 0; i < this.nvId.length; i++)
				{
						//TUY NPP THIET LAP, KHI LAM HOA DON MOI CHECK
						
						/*query = " select max(cast(sohoadon as numeric(18,0))) as somax  " +
								" from HOADON" +
								" where nguoisua = "+ this.nvId[i] +" and trangthai not in (3)" +
								"       and kyhieu = '"+ nvKyhieu[i] +"'	";
					
						ResultSet LaySHDMAX = db.get(query);
						String somax= "";
						String somax_OTC= "";
						String somax_ETC= "";
						if(LaySHDMAX!= null)
						{
							while(LaySHDMAX.next())
							{
								somax_OTC = LaySHDMAX.getString("somax")== null ? "0" : LaySHDMAX.getString("somax");
							}
							LaySHDMAX.close();
						}*/
					if(this.nvSoden[i].length()>0)
						{
						String somax= "";
							String somax_ETC= "";
							query = " select max(cast(sohoadon as numeric(18,0))) as somax  " +
									" from ERP_HOADON " +
									" where nguoisua = "+ this.nvId[i] +" and trangthai not in (3)" +
									"       and kyhieu = '"+ nvKyhieu[i] +"' and isnumeric(sohoadon)=1	";
							
							System.out.println("Câu query "+query);
							ResultSet LaySHDMAX_ETC = db.get(query);
	
							if(LaySHDMAX_ETC!= null)
							{
								while(LaySHDMAX_ETC.next())
								{
									somax_ETC = LaySHDMAX_ETC.getString("somax")== null ? "0" : LaySHDMAX_ETC.getString("somax");
								}
								LaySHDMAX_ETC.close();
							}
							
							somax=somax_ETC;
					
							if ( Integer.parseInt(this.nvSoden[i]==""?"0":this.nvSoden[i]) < Integer.parseInt(somax) )
							{
								this.msg = "Số hóa đơn đến ("+this.nvSoden[i]+") của nhân viên ("+this.nvTen[i]+")  phải lớn hơn số hóa đơn đến ("+somax+") trong Hóa đơn của   ";
								db.getConnection().rollback();
								return false;
							}
							else if ( this.nvSoden[i].trim().length() < 7 || this.nvSoden[i].trim().length() > 7 || this.nvSotu[i].trim().length() < 7 || this.nvSotu[i].trim().length() > 7  )
							{
								this.msg = "Số hóa đơn  phải đủ 7 ký tự  ";
								db.getConnection().rollback();
								return false;
							}
						
					/*	if(!this.nppId.equals("100002") && this.nvSoden[i].trim().length() > 0 && this.nvSotu[i].trim().length() > 0)
						{
							if ( this.nvSoden[i].trim().length() < 7 || this.nvSoden[i].trim().length() > 7 || this.nvSotu[i].trim().length() < 7 || this.nvSotu[i].trim().length() > 7  )
							{
								this.msg = "Số hóa đơn  phải đủ 7 ký tự  ";
								db.getConnection().rollback();
								return false;
							}
							
						}
						
						if( (this.nppId.equals("106210")|| this.nppId.equals("100002")) && this.nvSoden2[i].trim().length() > 0 && this.nvSotu2[i].trim().length() > 0 )
						{														
							if ( this.nvSoden2[i].trim().length() != 7 || this.nvSotu2[i].trim().length() != 7  )
							{
								this.msg = "Số hóa đơn  phải đủ 7 ký tự  ";
								db.getConnection().rollback();
								return false;
							}
						}
						
						if(this.nppId.equals("100002")) // CN HÀ NỘI : CHỈ LƯU MẪU 2
						{
						query = "Update NHANVIEN set sohoadontu2 = '" + this.nvSotu2[i].trim() + "', sohoadonden2 = '" + this.nvSoden2[i].trim() + "'," +
								"                    ngayhoadon2 = '"+ this.nvngayHD2[i] +"', kyhieu2 = '"+ this.nvKyhieu2[i] +"', mauhoadon = '2' " +
								"where pk_seq = '" + this.nvId[i] + "'  ";
						}					
						else if(this.nppId.equals("106210")) //CN HCM : LƯU 2 MẪU 
						{						
							query = "Update NHANVIEN set sohoadontu = '" + this.nvSotu[i].trim() + "', sohoadonden = '" + this.nvSoden[i].trim() + "', ngayhoadon = '"+ this.nvngayHD[i] +"', kyhieu = '"+ this.nvKyhieu[i] +"', mauhoadon = '1' ," +
									"                    sohoadontu2 = '" + this.nvSotu2[i].trim() + "', sohoadonden2 = '" + this.nvSoden2[i].trim() + "', ngayhoadon2 = '"+ this.nvngayHD2[i] +"', kyhieu2 = '"+ this.nvKyhieu2[i] +"', mauhoadon2 = '2' " +
									"where pk_seq = '" + this.nvId[i] + "'  ";
						}*/
						//else  // CÒN LẠI LƯU MẪU 1
						{
							query = "Update NHANVIEN set sohoadontu = '" + this.nvSotu[i].trim() + "', sohoadonden = '" + this.nvSoden[i].trim() + "'," +
							"                    ngayhoadon = '"+ this.nvngayHD[i] +"', kyhieu = '"+ this.nvKyhieu[i] +"', mauhoadon = '1' " +
							"where pk_seq = '" + this.nvId[i] + "'  ";
						}
						
						if(!db.update(query))
						{
							this.msg = "Lỗi cập nhật số hóa đơn: " + query;
							db.getConnection().rollback();
							return false;
						}
				}
				}
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

	
}
