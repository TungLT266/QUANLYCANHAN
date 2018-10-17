package geso.traphaco.center.beans.banggiablcnpp.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import geso.traphaco.center.beans.banggiablcnpp.IBanggiablcNpp;
import geso.traphaco.center.db.sql.*;

public class BanggiablcNpp implements IBanggiablcNpp
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String userTen;
	String id;
	String ten;

	String trangthai;

	String msg;
	dbutils db;

	ResultSet dvkdRs;
	String dvkdId;
	
	ResultSet kenhRs;
	String kenhId;
	
	ResultSet nppRs;
	//String doitacIds;
	
	ResultSet sanphamRs;
	String spIds;
	Hashtable<String, String> spChietkhau;
	Hashtable<String, String> spDG_TruocCK;
	Hashtable<String, String> spDG_SauCK;

	String tungay, denngay;
	
	String nppIds;
	String nppTen;
	String sitecode;
	private ResultSet banggiaRs;
	private String bgId;
	private String ck;
	
	public BanggiablcNpp(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.ten = "";
		this.msg = "";
		this.dvkdId = "";
		this.kenhId = "";
		this.tungay="";
		this.denngay="";
		this.trangthai = "1";
		this.spIds = "";
		this.nppIds = "";
		this.bgId = "";
		this.ck = "0";
		this.spChietkhau = new Hashtable<String, String>();
		this.spDG_SauCK = new Hashtable<String, String>();
	}
	
	public BanggiablcNpp()
	{	
		this.db = new dbutils();
		this.id = "";
		this.ten = "";
		this.msg = "";
		this.dvkdId = "";
		this.kenhId = "";
		this.tungay="";
		this.denngay="";
		this.trangthai = "1";
		this.spIds = "";
		this.nppIds = "";
		this.bgId = "";
		this.ck = "0";
		this.spChietkhau = new Hashtable<String, String>();
		this.spDG_SauCK = new Hashtable<String, String>();
	}

	public String getUserId() 
	{
		return this.userId;
	}
	
	public String getUserTen() 
	{
		return this.userTen;
	}
	
	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
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
	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public String getKenhId() 
	{
		return this.kenhId;
	}

	public void setKenhId(String kenhId) 
	{
		this.kenhId = kenhId;
	}
	
	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}
	
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	public void init()
	{
		String query = "select ten, dvkd_fk as dvkdId, trangthai, NGAYBATDAU, chietkhau, BANGGIABANLECHUAN_FK " +
				       "from BANGGIABANLENPP where pk_seq ='" + this.id + "' ";
		ResultSet rs = this.db.get(query);
		try
		{		
			rs.next();
			this.ten = rs.getString("ten");
			this.dvkdId = rs.getString("dvkdId");
			this.ck = rs.getString("chietkhau");
			this.trangthai = rs.getString("trangthai");
			this.tungay = rs.getString("NGAYBATDAU");
			this.bgId = rs.getString("BANGGIABANLECHUAN_FK");	
			rs.close();
			
			
			//INIT DOI TAC
			query = "select NPP_FK from BANGGIABANLENPP_NPP where BANGGIABLNPP_FK = '" + this.id + "' ";
			ResultSet doitacRS = db.get(query);
			String khIds = "";
			if(doitacRS != null)
			{
				while(doitacRS.next())
				{
					khIds += doitacRS.getString("NPP_FK") + ",";
				}
				doitacRS.close();
			}
			
			if(khIds.trim().length() > 0)
			{
				khIds = khIds.substring(0, khIds.length() - 1);
				this.nppIds = khIds;
			}
			
			//INIT CHIET KHAU
			query = " select SANPHAM_FK, isnull(CHIETKHAU,0) as CHIETKHAU, ISNULL(DONGIA, 0) as DONGIA " + 
					" from BANGGIABANLENPP_SANPHAM where BANGGIABLNPP_FK = '" + this.id + "' ";
			ResultSet spRS = db.get(query);
			if(spRS != null)
			{
				while(spRS.next())
				{
					this.spChietkhau.put(spRS.getString("SANPHAM_FK"), spRS.getString("CHIETKHAU"));
					this.spDG_SauCK.put(spRS.getString("SANPHAM_FK"), spRS.getString("DONGIA"));
				}
				spRS.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		createRS();
	}

	
	public void closeDB()
	{
		
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	public String getTungay() 
	{
		return tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay() 
	{
		return denngay;
	}

	
	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public ResultSet getKenhRs() {
		
		return this.kenhRs;
	}

	
	public void setKenhRs(ResultSet kenhRs) {
		
		this.kenhRs = kenhRs;
	}

	
	public ResultSet getSanPhamList() {
		
		return this.sanphamRs;
	}

	
	public void setSanPhamList(ResultSet sanphamlist) {
		
		this.sanphamRs = sanphamlist;
	}

	
	public boolean CreateBg() 
	{
		if(this.ten.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập tên bảng giá";
			return false;
		}
		
		if(this.dvkdId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if(this.bgId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn bảng giá";
			return false;
		}
		
		if(this.nppIds.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn khách hàng áp dụng";
			return false;
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			String query = "select * from BANGGIABANLENPP_NPP where NPP_FK IN (" + this.nppIds + ")";
			ResultSet rs = db.get(query);
			if(rs.next()){
				this.msg = "Có nhà phân phối đã tồn tại trong bảng giá khác.";
				db.getConnection().rollback();
				return false;
			}
			query = "insert BANGGIABANLENPP(ten, dvkd_fk, BANGGIABANLECHUAN_FK, CHIETKHAU, trangthai, NGAYBATDAU, ngaytao, ngaysua, nguoitao, nguoisua) " +
						   "values(N'" + this.ten + "', '" + this.dvkdId + "', '" + this.bgId + "', "+this.ck+", '" + this.trangthai + "', '" + this.tungay + "', '" + getDateTime() + "', '" + getDateTime() + "', '" + this.userId + "', '" + this.userId + "')";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo bảng giá (1)";
				db.getConnection().rollback();
				return false;
			}
			
			String bgid = "";
			query = "select ident_current('BANGGIABANLENPP') as bgId ";
			rs = db.get(query);
			rs.next();
			bgid = rs.getString("bgId");
			rs.close();
			
			query = "insert BANGGIABANLENPP_NPP(BANGGIABLNPP_FK, NPP_FK) " +
					"select '" + bgid + "', pk_seq from NHAPHANPHOI where pk_seq in ( " + this.nppIds + " )";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo bảng giá(2)";
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert BANGGIABANLENPP_SANPHAM(BANGGIABLNPP_FK, SANPHAM_FK, CHIETKHAU, DONGIA) " +
					"select '" + bgid + "', pk_seq, 0, 0 from SANPHAM ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo bảng giá(3)";
				db.getConnection().rollback();
				return false;
			}
			
			if(this.spChietkhau.size() > 0)
			{
				Enumeration<String> keys = this.spChietkhau.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					String ck = this.spChietkhau.get(key);
					if(ck.trim().length() <= 0)
						ck = "0";
					
					String dg = this.spDG_SauCK.get(key);
					if(dg.trim().length() <= 0)
						dg = "0";
					
					query = "update BANGGIABANLENPP_SANPHAM set CHIETKHAU = '" + ck + "', DONGIA = '" + dg + "' " +
							"where BANGGIABLNPP_FK = '" + bgid + "' and sanpham_fk = '" + key + "' ";
					if(!db.update(query))
					{
						this.msg = "Lỗi khi tạo bảng giá(4) ";
						db.getConnection().rollback();
						return false;
					}
				}
			}
			query = "update npp_sp set DONGIA_TRUOC_CK = sp.GIABANLECHUAN " +
					"from BANGGIABANLENPP_SANPHAM npp_sp inner join BANGGIABANLENPP bg on bg.PK_SEQ = npp_sp.BANGGIABLNPP_FK " +
					"inner join BANGGIABLC_SANPHAM sp on sp.BGBLC_FK = bg.BANGGIABANLECHUAN_FK and sp.SANPHAM_FK = npp_sp.SANPHAM_FK " +
					"where bg.PK_SEQ = '"+bgid+"'";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo bảng giá (5) ";
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Lỗi khi tạo mới bảng giá: " + e.getMessage();
			return false;
		}
		
		return true;
		
	}

	
	public boolean UpdateBg() 
	{
		if(this.ten.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập tên bảng giá";
			return false;
		}
		
		if(this.dvkdId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		/*if(this.kenhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}*/
		
		if(this.nppIds.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đối tác áp dụng";
			return false;
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update BANGGIABANLENPP set ten = N'" + this.ten + "', dvkd_fk = '" + this.dvkdId + "', trangthai = '" + this.trangthai + "', " +
							"NGAYBATDAU = '" + this.tungay + "', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " +
						   "where pk_seq = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete BANGGIABANLENPP_NPP where BANGGIABLNPP_FK = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete BANGGIABANLENPP_SANPHAM where BANGGIABLNPP_FK = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert BANGGIABANLENPP_NPP(BANGGIABLNPP_FK, NPP_FK) " +
			"select '" + this.id + "', pk_seq from NHAPHANPHOI where pk_seq in ( " + this.nppIds + " )";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert BANGGIABANLENPP_SANPHAM(BANGGIABLNPP_FK, SANPHAM_FK, CHIETKHAU, DONGIA) " +
					"select '" + this.id + "', pk_seq, 0, 0 from SANPHAM ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tạo bảng giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.spChietkhau.size() > 0)
			{
				Enumeration<String> keys = this.spChietkhau.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					String ck = this.spChietkhau.get(key);
					if(ck.trim().length() <= 0)
						ck = "0";
					
					String dg = this.spDG_SauCK.get(key);
					if(dg.trim().length() <= 0)
						dg = "0";
					String dg_truocck = this.spDG_TruocCK.get(key);
					if(dg_truocck.trim().length() <= 0)
						dg_truocck = "0";
					
					query = "update BANGGIABANLENPP_SANPHAM set CHIETKHAU = '" + ck + "', DONGIA = '" + dg + "', DONGIA_TRUOC_CK = " + dg_truocck + " " +
							"where BANGGIABLNPP_FK = '" + this.id + "' and sanpham_fk = '" + key + "' ";
					if(!db.update(query))
					{
						this.msg = "Lỗi khi tạo bảng giá: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			//Khi có thay đổi giá trong bảng giá bán lẻ chuẩn thì tự động cập nhật giá vào hợp đồng CLC
			//LUU LOG
			query = "  insert LOG_GIAHOPDONG( hopdong_fk, sanpham_fk, soluong, dongia, chietkhau, dvdl_fk, tungay, denngay, thueVAT, ddkd_fk, stt, dongiaNEW )  " + 
					"  select hd_sp.*, " + 
					"  		 	ISNULL( (   " + 
					"  		 		select case when d.dongia is not null then d.dongia else 	  " + 
					"  		 				ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = hd.NPP_FK ) ) ), 0) end as giamua    " + 
					"  		 		from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq   " + 
					"  		 			inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq   " + 
					"  		 			left join BANGGIABANLEKH_SANPHAM d on a.PK_SEQ = d.SANPHAM_FK and d.KHACHHANG_FK = hd.KHACHHANG_FK " + 
					"  		 		where a.DVKD_FK = '100001' and a.PK_SEQ = hd_sp.sanpham_fk " + 
					"  		 	), 0 ) as dongiaNEW   " + 
					"  from ERP_HOPDONGNPP hd inner join ERP_HOPDONGNPP_SANPHAM hd_sp on hd.PK_SEQ = hd_sp.hopdong_fk " + 
					"  where hd.LoaiDonHang = '2' and hd_sp.dongia != ISNULL( (   " + 
					"  		 											select case when d.dongia is not null then d.dongia else 	  " + 
					"  		 													ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = hd.NPP_FK ) ) ), 0) end as giamua    " + 
					"  		 											from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq   " + 
					"  		 												inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq   " + 
					"  		 												left join BANGGIABANLEKH_SANPHAM d on a.PK_SEQ = d.SANPHAM_FK and d.KHACHHANG_FK = hd.KHACHHANG_FK " + 
					"  		 											where a.DVKD_FK = '100001' and a.PK_SEQ = hd_sp.sanpham_fk " + 
					"  		 										), 0 )  ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "  update hd_sp  " + 
					"  set hd_sp.dongia = ISNULL( (   " + 
					"  		 				select case when d.dongia is not null then d.dongia else 	  " + 
					"  		 						ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = hd.NPP_FK ) ) ), 0) end as giamua    " + 
					"  		 				from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq   " + 
					"  		 					inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq   " + 
					"  		 					left join BANGGIABANLEKH_SANPHAM d on a.PK_SEQ = d.SANPHAM_FK and d.KHACHHANG_FK = hd.KHACHHANG_FK " + 
					"  		 				where a.DVKD_FK = '100001' and a.PK_SEQ = hd_sp.sanpham_fk " + 
					"  		 			), 0 )  " + 
					"  from ERP_HOPDONGNPP hd inner join ERP_HOPDONGNPP_SANPHAM hd_sp on hd.PK_SEQ = hd_sp.hopdong_fk " + 
					"  where hd.LoaiDonHang = '2' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi cập nhật giá: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Lỗi khi tạo mới bảng giá: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public void createRS() 
	{
		
		String query =  "select pk_seq, maFAST, TEN, DIACHI, DIENTHOAI from NHAPHANPHOI " +
						"where TRANGTHAI = 1 order by TEN asc ";
		this.nppRs = db.get(query);
		query = "select * from BANGGIABANLECHUAN where TRANGTHAI = 1 and DADUYET = 1";
		this.banggiaRs = this.db.get(query);
		/*if(this.bgId.length() > 0 && this.id.length() == 0)
		{
			
			query = "select a.pk_seq, a.ma, a.ten, b.donvi, " +
					"	isnull( (SELECT GIABANLECHUAN FROM BANGGIABLC_SANPHAM where sanpham_fk = a.pk_seq and BGBLC_FK = '" + this.bgId + "'), 0) as gblc,  " +
					"	0 as giasauCK  " +
					"from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
					"order by a.ten asc";
			
			this.sanphamRs = db.get(query);
		}
		else if(this.id.length() > 0){
			
			query = "select a.pk_seq, a.ma, a.ten, b.donvi, bg.DONGIA_TRUOC_CK as gblc, bg.DONGIA as giasauCK " +
					"from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
					"left join BANGGIABANLENPP_SANPHAM bg on bg.SANPHAM_FK = a.PK_SEQ " +
					"where bg.BANGGIABLNPP_FK = '"+this.id+"'" +
					"order by a.ten asc";
	
			this.sanphamRs = db.get(query);
		}*/
		
		if(this.bgId.length() > 0 )
		{
			if( this.id.length() <= 0 )
			{
				query = "select a.pk_seq, a.ma,a.ma_fast, a.ten, b.donvi, " +
						"	isnull( (SELECT GIABANLECHUAN FROM BANGGIABLC_SANPHAM where sanpham_fk = a.pk_seq and BGBLC_FK = '" + this.bgId + "'), 0) as gblc,  " +
						"	0 as giasauCK  " +
						"from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq "
						+ " where a.loaisanpham_fk = 10045 " +
						"order by a.ten asc";
			}
			else
			{
				query = "select a.pk_seq, a.ma,a.ma_fast, a.ten, b.donvi, isnull( (SELECT GIABANLECHUAN FROM BANGGIABLC_SANPHAM where sanpham_fk = a.pk_seq and BGBLC_FK = '" + this.bgId + "'), 0) as gblc, bg.DONGIA as giasauCK " +
						"from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
						"left join BANGGIABANLENPP_SANPHAM bg on bg.SANPHAM_FK = a.PK_SEQ " +
						"where bg.BANGGIABLNPP_FK = '"+this.id+"' and a.loaisanpham_fk = 10045" +
						"order by a.ten asc";
			}
			
			this.sanphamRs = db.get(query);
		}
		
		this.dvkdRs = db.get("select pk_seq, diengiai from DONVIKINHDOANH");
		//this.kenhRs = db.get("select pk_seq, diengiai from KENHBANHANG");
		
	}

	
	public String getSanphamId() {
		
		return this.spIds;
	}

	
	public void setSanphamId(String sanphamId) {
		
		this.spIds = sanphamId;
	}

	
	public Hashtable<String, String> getSanphamCK() {

		return this.spChietkhau;
	}


	public void setSanphamCK(Hashtable<String, String> spCK) {

		this.spChietkhau = spCK;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String getNppIds() 
	{
		return this.nppIds;
	}

	public void setNppIds(String nppIds) 
	{
		this.nppIds = nppIds;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}

	public Hashtable<String, String> getSanphamDG_SauCK() 
	{
		return this.spDG_SauCK;
	}

	public void setSanphamDG_SauCK(Hashtable<String, String> spDG_SauCK) 
	{
		this.spDG_SauCK = spDG_SauCK;
	}

	@Override
	public ResultSet getNppRs() {
		// TODO Auto-generated method stub
		return this.nppRs;
	}

	@Override
	public String getBanggiaId() {
		// TODO Auto-generated method stub
		return this.bgId;
	}

	@Override
	public void setBanggiaId(String value) {
		this.bgId = value;
	}

	@Override
	public ResultSet getBanggiaRs() {
		return this.banggiaRs;
	}

	@Override
	public void setCk(String ck) {
		this.ck = ck;
	}

	@Override
	public String getCk() {
		// TODO Auto-generated method stub
		return this.ck;
	}

	@Override
	public void setSanphamDG_TruocCK(Hashtable<String, String> sanphamDG_TruocCK) {
		this.spDG_TruocCK = sanphamDG_TruocCK;
	}
	
	
	
}