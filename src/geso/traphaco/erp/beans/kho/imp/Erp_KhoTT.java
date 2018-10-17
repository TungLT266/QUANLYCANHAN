package geso.traphaco.erp.beans.kho.imp;

import geso.traphaco.erp.beans.kho.IErp_KhoTT;
import geso.traphaco.erp.beans.xuatdungccdc.Erp_Item;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Erp_KhoTT implements IErp_KhoTT 
{
	private String MSG, PK_SEQ, CONGTY_FK, TEN, DIACHI, QUANLYBIN, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, MA, TRANGTHAI, LOAI;
	private ResultSet rsCongTy;
	
	ResultSet nhamayRs;
	String nhamayId;
	
	ResultSet khottRs;
	String khottId;
	
	ResultSet loaiSpRs;
	String loaiSpIds;
	
	ResultSet sanphamRs;
	String spIds;
	
	private List<Erp_Item> loaiKhoList;
	
	private dbutils db;
	
	public Erp_KhoTT()
	{
		this.PK_SEQ = "";
		this.MSG = "";
		this.MA = "";
		this.TEN = "";
		this.DIACHI = "";
		this.NGAYSUA = "";
		this.NGAYTAO = "";
		this.NGUOISUA = "";
		this.NGUOITAO = "";
		this.QUANLYBIN = "";
		this.TRANGTHAI = "";
		
		this.nhamayId = "";
		this.khottId = "";
		this.loaiSpIds = "";
		this.spIds = "";
		this.LOAI="";
		
		this.loaiKhoList = new ArrayList<Erp_Item>();
		
		this.db = new dbutils();
	}
	
	public Erp_KhoTT(String pk_seq)
	{
		this.LOAI = "0";
		this.PK_SEQ = pk_seq;
		this.MA = "";
		this.TEN = "";
		this.DIACHI = "";
		this.NGAYSUA = "";
		this.NGAYTAO = "";
		this.NGUOISUA = "";
		this.NGUOITAO = "";
		this.QUANLYBIN = "";
		this.TRANGTHAI = "";
		this.MSG = "";
		this.nhamayId = "";
		this.khottId = "";
		this.loaiSpIds = "";
		this.spIds = "";
		
		this.loaiKhoList = new ArrayList<Erp_Item>();
		
		this.db = new dbutils();
	}
	
	public void setMsg(String msg)
	{
		this.MSG=msg;
	}
	
	public String getMsg()
	{
		return this.MSG;
	}
	
	public void setLoai(String loai)
	{
		this.LOAI=loai;
	}
	
	public String getLoai()
	{
		return this.LOAI;
	}

	public void setPK_SEQ(String pk_seq) {
		this.PK_SEQ=pk_seq;
	}

	public String getPK_SEQ() {
		return this.PK_SEQ;
	}

	public void setCongty_fk(String congty_fk) {
		this.CONGTY_FK=congty_fk;
	}

	public String getCongty_fk() {
		return this.CONGTY_FK;
	}

	public void setTen(String ten) {
		this.TEN=ten;
	}

	public String getTen() {
		return this.TEN;
	}

	public void setDiachi(String diachi) {
		this.DIACHI=diachi;
	}

	public String getDiachi() {
		return this.DIACHI;
	}

	public void setQuanlybin(String quanlybin) {
		this.QUANLYBIN=quanlybin;
	}

	public String getQuanlybin() {
		return this.QUANLYBIN;
	}

	public void setNguoitao(String nguoitao) {
		this.NGUOITAO=nguoitao;
	}

	public String getNguoitao() {
		return this.NGUOITAO;
	}

	public void setNguoisua(String nguoisua) {
		this.NGUOISUA=nguoisua;
	}

	public String getNguoisua() {
		return this.NGUOISUA;
	}

	public void setNgaytao(String ngaytao) {
		this.NGAYTAO=ngaytao;
	}

	public String getNgaytao() {
		return this.NGAYTAO;
	}

	public void setNgaysua(String ngaysua) {
		this.NGAYSUA=ngaysua;
	}

	public String getNgaysua() {
		return this.NGAYSUA;
	}

	public void setMa(String ma) {
		this.MA=ma;
	}

	public String getMa() {
		return this.MA;
	}

	public void setTrangthai(String trangthai) {
		this.TRANGTHAI=trangthai;
	}

	public String getTrangthai() 
	{
		return this.TRANGTHAI;
	}
	
	public void setRSCongty(ResultSet rs)
	{
		this.rsCongTy=rs;
	}
	
	public ResultSet getRSCongty()
	{
		return this.rsCongTy;
	}
	
	public void CreateRs()
	{
		/*String query = "SELECT * FROM ERP_CONGTY WHERE TRANGTHAI = '1'";
		this.rsCongTy = this.db.get(query);*/
		
		String query = "select pk_seq, ma + ', ' + ten as lspTen from ERP_LoaiSanPham where trangthai = '1' ";
		this.loaiSpRs = db.get(query);
		
		if(this.loaiSpIds.trim().length() > 0)
		{
			query = "select pk_seq, ma, ten from Erp_SanPham where congty_fk = '" + this.CONGTY_FK + "' and loaisanpham_fk in ( " + this.loaiSpIds + " )";
			System.out.println("Init SanPham: " + query);
			this.sanphamRs = db.getScrol(query);
			
			if(this.spIds.trim().length() <= 0)
			{
				try 
				{
					this.sanphamRs.first();
					
					while(this.sanphamRs.next())
					{
						this.spIds += this.sanphamRs.getString("pk_seq") + ",";
					}
					
					if(this.spIds.trim().length() > 0)
						this.spIds = this.spIds.substring(0, this.spIds.length() - 1);
				} 
				catch (Exception e) {}	
			}
		}
		
		query = "select pk_seq, manhamay + ', ' + tennhamay as nhamayTen from ERP_NhaMay where trangthai = '1' and congty_fk = '" + this.CONGTY_FK + "'";
		this.nhamayRs = db.get(query);
		
		query = " select pk_seq, ma + ', ' + ten as khoTen,loai from ERP_KhoTT where loai not in  ('10','1','7','8') " +
				" and PK_SEQ not in (100054,100058,100047)  and congty_fk = '" + this.CONGTY_FK + "'";
		this.khottRs = db.get(query);
		
		query = "select MA as PK_SEQ, TEN from ERP_LOAIKHOTT where trangThai = 1";
		Erp_Item.getListFromQuery(this.db, query, loaiKhoList);
	}
	
	public boolean init() 
	{
		String query =" SELECT K.trangthai,K.CONGTY_FK AS CONGTY_FK,K.PK_SEQ AS PK_SEQ,C.TEN AS CONGTY,K.TEN AS TEN,K.DIACHI AS DIACHI," +
					  " K.QUANLYBIN AS QUANLYBIN,K.NGUOITAO AS NGUOITAO,K.NGUOISUA AS NGUOISUA,K.NGAYTAO AS NGAYTAO,K.NGAYSUA AS NGAYSUA," +
					  " K.MA AS MA, K.LOAI AS LOAI, K.NHAMAY_FK, K.KHONHANNGUYENLIEU_FK " +
					  " FROM ERP_KHOTT K INNER JOIN ERP_CONGTY C ON K.CONGTY_FK=C.PK_SEQ " +
					  " WHERE K.PK_SEQ='" + this.PK_SEQ + "'";
		ResultSet rs = this.db.get(query);
		
		if(rs != null)
		{
			try
			{
				while(rs.next())
				{
					this.MA = rs.getString("MA");
					this.TEN = rs.getString("TEN");
					this.DIACHI = rs.getString("DIACHI");
					this.QUANLYBIN = rs.getString("QUANLYBIN") == null ? "" : rs.getString("QUANLYBIN");
					this.CONGTY_FK=rs.getString("CONGTY_FK") == null ? "" : rs.getString("CONGTY_FK");
					this.LOAI = rs.getString("LOAI") == null ? "" : rs.getString("LOAI");
					
					System.out.println("LOAI: "+this.LOAI);
					
					if(this.LOAI.equals("10")|| this.LOAI.equals("7") || this.LOAI.equals("13"))
					{
						 this.nhamayId = rs.getString("NHAMAY_FK");
						 query="SELECT KHOTT_NL_FK FROM ERP_KHOSX_KHONHANNL WHERE  KHOTT_SX_FK ="+this.PK_SEQ;
						 ResultSet rskhonl=db.get(query);
						 this.khottId="0";
						 while (rskhonl.next()){
							 this.khottId+=","+rskhonl.getString("KHOTT_NL_FK");
						 }
						 rskhonl.close();
					}
					
					this.TRANGTHAI = rs.getString("trangthai") == null ? "0" : rs.getString("trangthai");
				}
				rs.close();
			}
			catch (SQLException e)
			{
				System.out.println( "Không thể lấy dữ liệu " + query);
				return false;
			}
		}
		
		//Init loaisanpham
		//query = "select DISTINCT SP.loaisanpham_fk from ERP_KHOTT_SANPHAM A INNER JOIN ERP_SANPHAM SP ON SP.PK_sEQ=A.SANPHAM_FK where khott_fk = '" + this.PK_SEQ + "'";
		query = "select loaisanpham_fk from ERP_KHOTT_LOAISANPHAM where khott_fk = '" + this.PK_SEQ + "'";
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.loaiSpIds += rs.getString("loaisanpham_fk") + ",";
				}
				rs.close();
				
				if(this.loaiSpIds.trim().length() > 0)
					this.loaiSpIds = this.loaiSpIds.substring(0, this.loaiSpIds.length() - 1);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//Init sanpham
		query = "select sanpham_fk from ERP_Khott_SanPham where khott_fk = '" + this.PK_SEQ + "'";
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.spIds += rs.getString("sanpham_fk") + ",";
				}
				rs.close();
				
				if(this.spIds.trim().length() > 0)
					this.spIds = this.spIds.substring(0, this.spIds.length() - 1);
			} 
			catch (Exception e) {}
		}
		
		this.CreateRs();
		
		return true;
	}

	public boolean Create() 
	{
		if(this.MA.trim().length() <= 0)
		{
			this.MSG = "Vui lòng nhập mã kho";
			return false;
		}
		
		if(this.TEN.trim().length() <= 0)
		{
			this.MSG = "Vui lòng nhập tên kho";
			return false;
		}
		
		if(this.LOAI.trim().length() <= 0)
		{
			this.MSG = "Vui lòng chọn loại kho";
			return false;
		}
		/*else
		{
			if(this.LOAI.equals("1"))
			{
				if(this.nhamayId.trim().length() <= 0 )// || this.khottId.trim().length() <= 0)
				{
					this.MSG = "Vui lòng chọn nhà máy ";//và kho nhận nguyên liệu";
					return false;
				}
			}
		}*/
		
		if(this.loaiSpIds.trim().length() <= 0)
		{
			this.MSG = "Vui lòng chọn loại sản phẩm";
			return false;
		}
		
		if(this.spIds.trim().length() <= 0)
		{
			this.MSG = "Vui lòng chọn sản phẩm";
			return false;
		}
		
		if(CheckUnique() == false)
		{
			this.MSG = "Mã kho này đã tồn tại trong hệ thống.";
			return false;
		}
		
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String nhamay_fk = this.nhamayId.trim().length() <= 0 ? "null" : this.nhamayId.trim();
			String khott_fk = this.khottId.trim().length() <= 0 ? "null" : this.khottId.trim();
			
			String query = "INSERT INTO ERP_KHOTT(CONGTY_FK, TEN, DIACHI, QUANLYBIN, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, MA, TRANGTHAI, LOAIKHO, LOAI, NHAMAY_FK) " +
					   "VALUES('" + this.CONGTY_FK + "', N'" + this.TEN + "', N'" + this.DIACHI + "','" + this.QUANLYBIN + "', '" + this.NGUOITAO + "','" + this.NGUOITAO + "','" + getDateTime() + "','" + getDateTime() + "','" + this.MA + "','" + this.TRANGTHAI +"', '" + this.LOAI + "', '" + this.LOAI + "', " + nhamay_fk + "  )";
		
			if(!db.update(query))
			{
				this.MSG = "Không thể tạo mới ERP_KHOTT " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " Insert ERP_KHOTT_LOAISANPHAM (khott_fk, loaisanpham_fk) " +
					" select IDENT_CURRENT('ERP_KHOTT'), pk_seq from ERP_LoaiSanPham where pk_seq in ( " + this.loaiSpIds + " )";
			
			if(!db.update(query))
			{
				this.MSG = "Không thể tạo mới ERP_KHOTT_LOAISANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert ERP_Khott_SanPham(khott_fk, sanpham_fk, giaton, soluong, booked, available, masp, thanhtien) " +
					"select IDENT_CURRENT('ERP_KHOTT'), pk_seq, 0, 0, 0, 0, ma, 0 from ERP_SanPham where pk_seq in (" + this.spIds + ") ";
			System.out.println("query insert san pham: \n" + query + "\n================================================");
			if(!db.update(query))
			{
				this.MSG = "Không thể tạo mới ERP_Khott_SanPham " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( this.khottId.trim().length() > 9 )
			{
				query=" INSERT INTO ERP_KHOSX_KHONHANNL(KHOTT_SX_FK,KHOTT_NL_FK) "+
					  " SELECT   IDENT_CURRENT('ERP_KHOTT') ,PK_SEQ FROM ERP_KHOTT WHERE PK_SEQ IN ("+this.khottId+")";
				if(!db.update(query))
				{
					this.MSG = "5.Không thể tạo mới ERP_Khott_SanPham " + query;
					System.out.println(this.MSG);
					db.getConnection().rollback();
					return false;
				}
			}
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			this.db.update("roolback");
			this.MSG = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean Update() 
	{
		if(this.MA.trim().length() <= 0)
		{
			this.MSG = "Vui lòng nhập mã kho";
			return false;
		}
		
		if(this.TEN.trim().length() <= 0)
		{
			this.MSG = "Vui lòng nhập tên kho";
			return false;
		}
		
		if(this.LOAI.trim().length() <= 0)
		{
			this.MSG = "Vui lòng chọn loại kho";
			return false;
		}
		/*else
		{
			if(this.LOAI.equals("1"))
			{
				if(this.nhamayId.trim().length() <= 0 )// || this.khottId.trim().length() <= 0)
				{
					this.MSG = "Vui lòng chọn nhà máy ";//và kho nhận nguyên liệu";
					return false;
				}
			}
		}*/
		
		if( this.khottId.trim().length() > 9 )
		{
			
			if(!this.checkNoidungxuatSx()){
				return false;
			}
		}
		
		if(this.loaiSpIds.trim().length() <= 0)
		{
			this.MSG = "Vui lòng chọn loại sản phẩm";
			return false;
		}
		
		if(this.spIds.trim().length() <= 0)
		{
			this.MSG = "Vui lòng chọn sản phẩm";
			return false;
		}
		
		if(CheckUnique() == false)
		{
			this.MSG = "Mã kho này đã tồn tại trong hệ thống.";
			return false;
		}
		
		//Check xem san pham da phat sinh nghiep vu chua
		
		String query = "select sanpham_fk from ERP_Khott_SanPham where khott_fk = '" + this.PK_SEQ + "'";
		ResultSet rs = db.get(query);
		
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String spId = rs.getString("sanpham_fk");
					
					//SanPham trong kho cu, khong co trong san pham chon lai, neu da phat sinh nghiep vu thi thong bao loi
					if( this.spIds.indexOf(spId) < 0 )
					{
						query = "select ERP_SanPham.pk_seq, ERP_SanPham.ma, sum(phatsinh.sodong) as sodong  " +
								"from " +
								"( " +
									"select b.sanpham_fk,  count(*) as sodong   " +
									"from ERP_MuaHang a inner join ERP_MuaHang_SP b on a.pk_seq = b.muahang_fk  " +
									"where b.khott_fk = '" + this.PK_SEQ + "' and b.sanpham_fk = '" + spId + "'   " +
									"group by b.sanpham_fk  " +
								"union all " +
									"select b.sanpham_fk,  count(*) as sodong    " +
									"from ERP_NhanHang a inner join ERP_NhanHang_SanPham b on a.pk_seq = b.nhanhang_fk  " +
									"where a.KHONHAN_FK = '" + this.PK_SEQ + "' and b.sanpham_fk = '" + spId + "'    " +
									"group by b.sanpham_fk  " +
								"union all " +
									"select b.sanpham_fk,  count(*) as sodong   " +
									"from ERP_DonDatHang a inner join ERP_DonDatHang_SP b on a.pk_seq = b.dondathang_fk  " +
									"where a.KHOTT_FK = '" + this.PK_SEQ + "' and b.sanpham_fk = '" + spId + "'   " +
									"group by b.sanpham_fk  " +
									"union all " +
									"select sanpham_fk,  count(*) as sodong from ERP_KHOTT_SANPHAM a  " +
									"where a.KHOTT_FK = '" + this.PK_SEQ + "' and sanpham_fk = '" + spId + "' and ( soluong > 0 or available <> 0 ) " +
									"group by sanpham_fk " +
								")  " +
								"phatsinh inner join ERP_SanPham on phatsinh.sanpham_fk = ERP_SanPham.pk_seq  " +
								"group by ERP_SanPham.pk_seq, ERP_SanPham.ma  " +
								"having sum(phatsinh.sodong) > 0";
						
						ResultSet rsCheck = db.get(query);
						if (rsCheck != null)
						{
							if(rsCheck.next())
							{
								if(rsCheck.getString("ma") != null)
								{
									/*this.MSG = "Sản phẩm ( " + rsCheck.getString("ma") + " ) đã phát sinh dữ liệu. Bạn không thể bỏ ra khỏi kho.";
									rsCheck.close();
									rs.close();
									return false;*/
								}
							}
							rsCheck.close();
						}
					}
				}
				rs.close();
			} 
			catch (Exception e) {}
		}
		
		
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String nhamay_fk = this.nhamayId.trim().length() <= 0 ? "null" : this.nhamayId.trim();
			String khott_fk = this.khottId.trim().length() <= 0 ? "null" : this.khottId.trim();
			
			query=  " UPDATE ERP_KHOTT SET TEN = N'" + this.TEN + "', DIACHI = N'" + this.DIACHI + "', MA = '" + this.MA + "', NGUOISUA = '" + this.NGUOISUA + "', NGAYSUA = '" + getDateTime() + "', " +
					" TRANGTHAI = '" + this.TRANGTHAI + "', LOAIKHO = '" + this.LOAI + "', LOAI = '" + this.LOAI + "', QUANLYBIN = '" + this.QUANLYBIN + "', nhamay_fk = " + nhamay_fk + "  " +
					" WHERE PK_SEQ = '" + this.PK_SEQ + "'";
			
			System.out.println("::: CAP NHAT: " + query );
			if(!db.update(query))
			{
				this.MSG = "1.Không thể cập nhật ERP_KHOTT " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_KHOTT_LOAISANPHAM where khott_fk = '" + this.PK_SEQ + "'";
			if(!db.update(query))
			{
				this.MSG = "2.Không thể cập nhật ERP_KHOTT_LOAISANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " Insert ERP_KHOTT_LOAISANPHAM (khott_fk, loaisanpham_fk) " +
					" select '" + this.PK_SEQ + "', pk_seq from ERP_LoaiSanPham where pk_seq in ( " + this.loaiSpIds + " )";
			
			System.out.println(":: CHEN LOAI SP: " + query);
			if(!db.update(query))
			{
				this.MSG = "3.Không thể tạo mới ERP_KHOTT_LOAISANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//delete nhung sanpham chua phat sinh du lieu ma nguoi dung bo
			query = "delete ERP_Khott_SanPham where khott_fk = '" + this.PK_SEQ + "'  " +
					"and sanpham_fk in ( " +
											"select sanpham_fk  " +
											"from ERP_KhoTT_SanPham where khott_fk = '" + this.PK_SEQ + "' and sanpham_fk not in  " +
											"( " +
												"select ERP_SanPham.pk_seq  " +
												"from " +
												"( " +
													"select b.sanpham_fk,  count(*) as sodong   " +
													"from ERP_MuaHang a inner join ERP_MuaHang_SP b on a.pk_seq = b.muahang_fk  " +
													"where b.khott_fk = '" + this.PK_SEQ + "'   " +
													"group by b.sanpham_fk  " +
												"union all " +
													"select b.sanpham_fk,  count(*) as sodong    " +
													"from ERP_NhanHang a inner join ERP_NhanHang_SanPham b on a.pk_seq = b.nhanhang_fk  " +
													"where a.KHONHAN_FK = '" + this.PK_SEQ + "'    " +
													"group by b.sanpham_fk  " +
												"union all " +
													"select b.sanpham_fk,  count(*) as sodong   " +
													"from ERP_DonDatHang a inner join ERP_DonDatHang_SP b on a.pk_seq = b.dondathang_fk  " +
													"where a.KHOTT_FK = '" + this.PK_SEQ + "'   " +
													"group by b.sanpham_fk " +
												"union all " +
													"select sanpham_fk,  count(*) as sodong from ERP_KHOTT_SANPHAM  " +
													"where KHOTT_FK = '" + this.PK_SEQ + "'  and ( soluong > 0 or available <> 0 ) " +
													"group by sanpham_fk " +
												")  " +
												"phatsinh inner join ERP_SanPham on phatsinh.sanpham_fk = ERP_SanPham.pk_seq  " +
												"group by ERP_SanPham.pk_seq  " +
												"having sum(phatsinh.sodong) > 0  ) " +
										" ) ";
			if(!db.update(query))
			{
				this.MSG = "4.Không thể tạo mới ERP_Khott_SanPham " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " insert ERP_Khott_SanPham(khott_fk, sanpham_fk, giaton, soluong, booked, available, masp, thanhtien) " +
					" select '" + this.PK_SEQ + "', pk_seq, 0, 0, 0, 0, ma, 0 " +
					" from ERP_SanPham where pk_seq in (" + this.spIds + ") " +
					" and pk_seq not in ( select sanpham_fk from ERP_Khott_SanPham where khott_fk = '" + this.PK_SEQ + "' ) ";
			
			//System.out.println("5.Insett kho: " + query);
			if(!db.update(query))
			{
				this.MSG = "5.Không thể tạo mới ERP_Khott_SanPham " + query;
				System.out.println(this.MSG);
				db.getConnection().rollback();
				return false;
			}
			
			query="delete ERP_KHOSX_KHONHANNL where KHOTT_SX_FK="+this.PK_SEQ;
			if(!db.update(query))
			{
				this.MSG = "5.Không thể tạo mới ERP_Khott_SanPham " + query;
				System.out.println(this.MSG);
				db.getConnection().rollback();
				return false;
			}
			
			if( this.khottId.trim().length() > 9 )
			{
				
			 
				
				query=" INSERT INTO ERP_KHOSX_KHONHANNL(KHOTT_SX_FK,KHOTT_NL_FK) "+
					  " SELECT "+this.PK_SEQ+",PK_SEQ FROM ERP_KHOTT WHERE PK_SEQ IN ("+this.khottId+")";
				if(!db.update(query))
				{
					this.MSG = "5.Không thể tạo mới ERP_Khott_SanPham " + query;
					System.out.println(this.MSG);
					db.getConnection().rollback();
					return false;
				}
				
				
				
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			this.db.update("rollback");
			this.MSG = "Exception: " + e.getMessage();
			System.out.println(this.MSG);
			return false;
		}
		
		return true;
			
	}


	private boolean checkNoidungxuatSx() {
		// TODO Auto-generated method stub
		try{
			
			String query="SELECT LOAIKHO_XUAT FROM ERP_NOIDUNGNHAP WHERE PK_SEQ=100066";
			String loaikho_xuat="";
			ResultSet rs=db.get(query);
			if(rs.next()){
				loaikho_xuat=rs.getString("LOAIKHO_XUAT");
			}
			rs.close();
			query="SELECT ten   FROM ERP_KHOTT WHERE PK_SEQ IN ("+this.khottId+") AND LOAIKHO  NOT IN ("+loaikho_xuat+")" ;
			 rs=db.get(query);
			 String khoten="";
			while(rs.next()){
				khoten+=khoten+ "," +rs.getString("TEN");
			}
			rs.close();
			
			if(khoten.length() >0){
				this.MSG="Kho :"+khoten +" không nằm trong cấu hình kho xuất của nội dung xuất :XK10	Xuất chuyển sản xuất,vui lòng báo admin để cấu hình lại "  ;
				return false;
				
			}
			return true;
			
		}catch(Exception er){
			this.MSG=er.getMessage();
			return false;
		}
	  
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		return dateFormat.format(date);
	}
	
	public boolean CheckUnique()
	{
		String query = "Select count(*) as count From erp_khott Where MA = '" + this.MA + "'";
		
		if(this.PK_SEQ.trim().length() > 0)
			query += " and pk_seq != '" + this.PK_SEQ + "' ";
		
		System.out.println("Check EXITS: " + query);
		
		int count = 0;
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{	
			try
			{
				if (rs.next())
				{
					count = rs.getInt("count");
				}
				rs.close();
			} 
			catch (Exception e){ 
				e.printStackTrace();
			}
		}
		
		if(count <= 0)
			return true;
		
		return false;
	}
	
	public void DBClose(){
		try{
			if (this.rsCongTy != null) this.rsCongTy.close();
			if (this.nhamayRs != null)
				this.nhamayRs.close();
			if (this.khottRs != null)
				this.khottRs.close();
			if (this.loaiSpRs != null)
				this.loaiSpRs.close();
			if (this.sanphamRs != null)
				this.sanphamRs.close();
			if (this.db != null) this.db.shutDown();
		}catch (java.sql.SQLException e){
			e.printStackTrace();
		}
	}

	
	public void setNhamayRs(ResultSet nhamayRs) {
		
		this.nhamayRs = nhamayRs;
	}

	
	public ResultSet getNhamayRs() {
		
		return this.nhamayRs;
	}

	
	public void setNhamayId(String nhamayId) {
		
		this.nhamayId = nhamayId;
	}

	
	public String getNhamayId() {
		
		return this.nhamayId;
	}

	
	public void setKhoTTRs(ResultSet khottRs) {
		
		this.khottRs = khottRs;
	}

	
	public ResultSet getKhoTTRs() {
		
		return this.khottRs;
	}

	
	public void setKhoTTIds(String khoIds) {
		
		this.khottId = khoIds;
	}

	
	public String getKhoTTIds() {
		
		return this.khottId;
	}

	
	public void setLoaiSPRs(ResultSet loaispRs) {
		
		this.loaiSpRs = loaispRs;
	}

	
	public ResultSet getLoaiSpRs() {
		
		return this.loaiSpRs;
	}

	
	public void setLoaispIds(String loaiSpIds) {
		
		this.loaiSpIds = loaiSpIds;
	}

	
	public String getLoaispIds() {
		
		return this.loaiSpIds;
	}

	
	public void setSanphamRs(ResultSet sanphamRs) {
		
		this.sanphamRs = sanphamRs;
	}

	
	public ResultSet getSanphamRs() {
		
		return this.sanphamRs;
	}

	
	public void setSpIds(String spIds) {
		
		this.spIds = spIds;
	}

	
	public String getSpIds() {
		
		return this.spIds;
	}

	public List<Erp_Item> getLoaiKhoList() {
		return loaiKhoList;
	}

	public void setLoaiKhoList(List<Erp_Item> loaiKhoList) {
		this.loaiKhoList = loaiKhoList;
	}
}