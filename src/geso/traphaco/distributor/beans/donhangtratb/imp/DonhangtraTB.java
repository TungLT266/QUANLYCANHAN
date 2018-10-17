package geso.traphaco.distributor.beans.donhangtratb.imp;

import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.donhangtratb.IDonhangtraTB;

public class DonhangtraTB implements IDonhangtraTB 
{
	String userId;
	String id;

	String ngaynhap;
	String diengiai;
	
	ResultSet cttbRs;
	String cttbId;
	
	ResultSet khRs;
	Hashtable<String, Double> khachhang_thanhtoan;
	
	String[] khId;
	String[] spId;
	String[] khTen;
	String[] spTen;
	String[] soxuat;
	String[] total;
	String[] thanhtoan;
	
	ResultSet thuongDhRs;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	int solantt;
	int lanthanhtoan;
	
	String msg;

	dbutils db;
	
	public DonhangtraTB()
	{
		this.id = "";
		
		this.ngaynhap = "";
		this.diengiai = "";
		this.cttbId = "";
		this.solantt = 0;
		this.lanthanhtoan = 0;
		this.msg = "";
		
		this.khachhang_thanhtoan = new Hashtable<String, Double>();
		this.db = new dbutils();
	}
	
	public DonhangtraTB(String id)
	{
		this.id = id;
		
		this.ngaynhap = "";
		this.diengiai = "";
		this.cttbId = "";
		this.solantt = 0;
		this.lanthanhtoan = 0;
		this.msg = "";

		this.khachhang_thanhtoan = new Hashtable<String, Double>();
		this.db = new dbutils();
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

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		String query = "select cttb_fk, ngaydonhang, lanthanhtoan, ghichu  " +
					   "from DONHANGTRATRUNGBAY where pk_seq = '" + this.id + "'";
		
		System.out.println("__Khoi tao DONHANGTRATRUNGBAY: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.cttbId = rs.getString("cttb_fk");
					this.ngaynhap = rs.getString("ngaydonhang");
					this.lanthanhtoan = rs.getInt("lanthanhtoan");
					this.diengiai = rs.getString("ghichu");
				}
				rs.close();
			} 
			catch (Exception e)
			{
				System.out.println("115.Error Meg Init: " + e.getMessage());
			}
		}
		
		this.createRs();
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

	
	public String getNgaynhap() {
		
		return this.ngaynhap;
	}

	
	public void setNgaynhap(String ngaynhap) {
		
		this.ngaynhap = ngaynhap;
	}

	
	public void setCttbRs(ResultSet cttbRs) {
		
		this.cttbRs = cttbRs;
	}

	
	public ResultSet getCttbRs() {
		
		return this.cttbRs;
	}

	
	public String getCttbId() {
		
		return this.cttbId;
	}

	
	public void setCttbId(String cttbId) {
		
		this.cttbId = cttbId;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhachhang_Thanhtoan(Hashtable<String, Double> khachhang_thanhtoan) {
		
		this.khachhang_thanhtoan = khachhang_thanhtoan;
	}

	
	public Hashtable<String, Double> getKhachhang_thanhtoan() {
		
		return this.khachhang_thanhtoan;
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

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	

	public void createRs() 
	{
		this.getNppInfo();
		
		String query = "select pk_seq as cttbId, Scheme from CTTRUNGBAY " +
						"where pk_seq in ( select CTTRUNGBAY_FK from DENGHITRATRUNGBAY where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ) ";
		this.cttbRs = db.get(query);
		
		if(this.cttbId.trim().length() > 0)
		{
			query = "select ISNULL(solanthanhtoan, 0) as solantt from CTTRUNGBAY where PK_SEQ = '" + this.cttbId + "'";
			System.out.println("__Lay so lan TT: " + query);
			ResultSet rsTB = db.get(query);
			try 
			{
				if(rsTB.next())
				{
					this.solantt = rsTB.getInt("solantt");
				}
				rsTB.close();
			} 
			catch (Exception e) {}
			
			//
			if(this.lanthanhtoan > 0)
			{
				/*query = "select c.PK_SEQ as khId, c.SMARTID + ' - ' + c.TEN as khTen, b.XUATDANGKY, b.XUATDENGHI, b.XUATDUYET, ISNULL(g.ten, ' ') as spTen, g.pk_seq as spId,  " +
							"case when e.hinhthuc = 2 then b.XUATDUYET * isnull(e.TONGTIEN, 0) else b.XUATDUYET * isnull(f.SOLUONG, 0) end as total, " +
							"isnull( ( select thanhtoan from DONHANGTRATRUNGBAY_CHITIET where donhang_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and KHACHHANG_FK = c.PK_SEQ and ISNULL(sanpham_fk, -1) = ISNULL(g.pk_seq, -1) ), 0) as thanhtoan  " +
						"from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK " +
							" inner join KHACHHANG c on b.KHACHHANG_FK = c.PK_SEQ " +
							" inner join CTTB_TRATB d on a.CTTRUNGBAY_FK = d.CTTRUNGBAY_FK " +
							" inner join TRATRUNGBAY e on d.TRATRUNGBAY_FK = e.PK_SEQ " +
							" left join TRATRUNGBAY_SANPHAM f on e.PK_SEQ = f.TRATRUNGBAY_FK  " +
							" left join SANPHAM g on f.SANPHAM_FK = g.PK_SEQ  " +
						"where a.TRANGTHAI = '1' and a.NPP_FK = '" + this.nppId + "' and b.XUATDUYET > 0 " +
								"and a.LANTHANHTOAN = '" + this.lanthanhtoan + "' and a.cttrungbay_fk = '" + this.cttbId + "' ";*/
				
				query = "select c.PK_SEQ as khId, c.SMARTID + ' - ' + c.TEN as khTen, b.XUATDANGKY, b.XUATDENGHI, b.XUATDUYET, ISNULL(g.ten, ' ') as spTen, g.pk_seq as spId,  " +
							"case when e.hinhthuc = 2 then b.XUATDUYET * isnull(e.TONGTIEN, 0) else b.XUATDUYET * isnull(f.SOLUONG, 0) end as total " +
						"from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK " +
							" inner join KHACHHANG c on b.KHACHHANG_FK = c.PK_SEQ " +
							" inner join CTTB_TRATB d on a.CTTRUNGBAY_FK = d.CTTRUNGBAY_FK " +
							" inner join TRATRUNGBAY e on d.TRATRUNGBAY_FK = e.PK_SEQ " +
							" left join TRATRUNGBAY_SANPHAM f on e.PK_SEQ = f.TRATRUNGBAY_FK  " +
							" left join SANPHAM g on f.SANPHAM_FK = g.PK_SEQ  " +
						"where a.TRANGTHAI = '1' and a.NPP_FK = '" + this.nppId + "' and b.XUATDUYET > 0 " +
								"and a.LANTHANHTOAN = '" + this.lanthanhtoan + "' and a.cttrungbay_fk = '" + this.cttbId + "' ";
				
				System.out.println("__Lay khach hang: " + query);
				ResultSet khachhangRs = db.get(query);
				if(khachhangRs != null)
				{
					try 
					{
						String khTen = "";
						String khId = "";
						String spTen = "";
						String spId = "";
						String soxuat = "";
						String total = "";
						//String thanhtoan = "";
						
						NumberFormat formatter = new DecimalFormat("#,###,###");
						while(khachhangRs.next())
						{
							khTen += khachhangRs.getString("khTen") + "__";
							khId += khachhangRs.getString("khId") + "__";
							spTen += khachhangRs.getString("spTen") + "__";
							spId += ( khachhangRs.getString("spId") == null ? " " : khachhangRs.getString("spId") )  + "__";
							
							soxuat += formatter.format(khachhangRs.getDouble("XUATDUYET")) + "__";
							total += formatter.format(khachhangRs.getDouble("total")) + "__";
							
							/*if(khachhangRs.getDouble("thanhtoan") > 0)
							{
								thanhtoan += formatter.format(khachhangRs.getDouble("thanhtoan")) + "__";
							}
							else
							{
								thanhtoan += " " + "__";
							}*/
						}
						khachhangRs.close();
						
						/*System.out.println("___KH TEN: " + khTen);
						System.out.println("___KH ID: " + khId);
						System.out.println("___SP TEN: " + spTen);
						System.out.println("___SP ID: " + spId);
						System.out.println("___SO XUAT: " + soxuat);
						System.out.println("___TOTAL: " + total);*/
						
						if(khTen.trim().length() > 0)
						{
							khTen = khTen.substring(0, khTen.length() - 2);
							this.khTen = khTen.split("__");
							
							khId = khId.substring(0, khId.length() - 2);
							this.khId = khId.split("__");
							
							spTen = spTen.substring(0, spTen.length() - 2);
							this.spTen = spTen.split("__");
							
							spId = spId.substring(0, spId.length() - 2);
							this.spId = spId.split("__");
							
							soxuat = soxuat.substring(0, soxuat.length() - 2);
							this.soxuat = soxuat.split("__");
							
							total = total.substring(0, total.length() - 2);
							this.total = total.split("__");
							
							//Thanh toan bang so xuat duyet
							//thanhtoan = thanhtoan.substring(0, thanhtoan.length() - 2);
							//this.thanhtoan = thanhtoan.split("__");
							
							this.thanhtoan = total.split("__");
						}
						
					} 
					catch (Exception e) 
					{
						System.out.println("__EXCEPTION khach hang: " + e.getMessage());
					}
				}
				
				
				if(this.lanthanhtoan == 1)  //Thuong don hang dau tien chi Ap Dung cho lan thanh toan thu 1
				{
					query = "select c.TEN as khTen, b.PK_SEQ as dhId, b.NGAYNHAP, b.TONGGIATRI as tongtien,  " +
							"case when ISNULL(donvithuong, 0) = 0 then ISNULL(thuongDhDautien, 0) * b.TONGGIATRI / 100 else ISNULL(thuongDhDautien, 0) end as thuong    " +
							"from CTTRUNGBAY_DonHangDauTien a inner join  DONHANG b on a.DONHANG_FK = b.PK_SEQ   " +
								"inner join KHACHHANG c on b.KHACHHANG_FK = c.PK_SEQ   " +
								"inner join CTTRUNGBAY d on a.cttb_fk = d.PK_SEQ    " +
							"where a.CTTB_FK = '" + this.cttbId + "'  and b.TRANGTHAI = '1' and a.npp_fk = '" + this.nppId + "' " +
								"and c.pk_seq in ( select KHACHHANG_FK from DENGHITRATB_KHACHHANG " +
												  "where XUATDUYET > 0 and DENGHITRATB_FK in ( select pk_seq from DENGHITRATRUNGBAY where NPP_FK = '" + this.nppId + "' and TRANGTHAI = '1' and CTTRUNGBAY_FK = '" + this.cttbId + "' )  )  " +
								"and CAST(b.pk_seq as varchar(10)) + b.NGAYNHAP  = ( select cast(MIN(dh.pk_seq) as varchar(20)) + cast(min(dh.NGAYNHAP) as varchar(10))  " +
																					"from CTTRUNGBAY_DonHangDauTien tb_dh inner join  DONHANG dh on tb_dh.DONHANG_FK = dh.PK_SEQ    " +
																					"where tb_dh.CTTB_FK = '" + this.cttbId + "' and dh.npp_fk = '" + this.nppId + "' and dh.TRANGTHAI = '1' and tb_dh.khachhang_fk = c.PK_SEQ )";
					
					this.thuongDhRs = db.get(query);
					
				}
				
			}
			
		}
		
	}
	
	public boolean create() 
	{
		if(this.ngaynhap.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày nhập";
			return false;
		}
		
		if(this.cttbId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn chương trình trưng bày";
			return false;
		}
		
		if(this.lanthanhtoan <= 0)
		{
			this.msg = "Vui lòng chọn lần thanh toán";
			return false;
		}
		
		//Check Khach hang
		if(this.khId == null || this.khId.length <= 0)
		{
			this.msg = "Không có khách hàng nào được chọn.";
			return false;
		}
		else
		{
			
			boolean flag = false;
			for(int i = 0; i < this.khId.length; i++)
			{
				if(this.thanhtoan[i].trim().length() > 0)
				{
					flag = true;
				}
			}
			
			if(!flag)
			{
				this.msg = "Không có khách hàng nào được nhập thanh toán.";
				return false;
			}
		}
		
		try 
		{
			this.getNppInfo();
			
			db.getConnection().setAutoCommit(false);
			
			String query =  "insert DONHANGTRATRUNGBAY(ngaydonhang, cttb_fk, lanthanhtoan, ghichu, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, npp_fk) " +
							"values('" + this.ngaynhap + "', '" + this.cttbId + "', '" + this.lanthanhtoan + "', N'" + this.diengiai + "', '0', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + this.nppId + "')";
			System.out.println("__Insert DONHANG: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới DONHANGTRATRUNGBAY: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.khId.length > 0)
			{
				String sqlInsert = "";
				for(int i = 0; i < this.khId.length; i++)
				{
					if(this.thanhtoan[i].trim().length() > 0)
					{
						String sanpham_fk = "null";
						if(this.spId[i].trim().length() > 0)
							sanpham_fk = this.spId[i].trim();
						
						sqlInsert += " select '" + this.khId[i] + "' as khId, " + sanpham_fk + " as spId, '" + this.soxuat[i].replaceAll(",", "") + "' as soXuat, '" + this.total[i].replaceAll(",", "") + "' as total, '" + this.thanhtoan[i].replaceAll(",", "") + "' as thanhtoan union ";
					}
				}
				
				if(sqlInsert.trim().length() > 0)
				{
					sqlInsert = sqlInsert.substring(0, sqlInsert.length() - 6);
					
					query = "insert DONHANGTRATRUNGBAY_CHITIET(donhang_fk, khachhang_fk, sanpham_fk, soxuat, total, thanhtoan) " +
							"select IDENT_CURRENT('DONHANGTRATRUNGBAY'), chitiet.* from ( " + sqlInsert + " ) chitiet ";
					System.out.println("___CHI TIET: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới DONHANGTRATRUNGBAY_CHITIET: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			}
			catch (Exception e1) {}
			
			this.msg = "Lỗi khi tạo mới đơn hàng: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public boolean update() 
	{
		if(this.ngaynhap.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày nhập";
			return false;
		}
		
		if(this.cttbId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn chương trình trưng bày";
			return false;
		}
		
		if(this.lanthanhtoan <= 0)
		{
			this.msg = "Vui lòng chọn lần thanh toán";
			return false;
		}
		
		//Check Khach hang
		if(this.khId == null || this.khId.length <= 0)
		{
			this.msg = "Không có khách hàng nào được chọn.";
			return false;
		}
		else
		{
			
			boolean flag = false;
			for(int i = 0; i < this.khId.length; i++)
			{
				if(this.thanhtoan[i].trim().length() > 0)
				{
					flag = true;
				}
			}
			
			if(!flag)
			{
				this.msg = "Không có khách hàng nào được nhập thanh toán.";
				return false;
			}
		}
		
		try 
		{
			this.getNppInfo();
			
			db.getConnection().setAutoCommit(false);
			
			String query =  "update DONHANGTRATRUNGBAY set ngaydonhang = '" + this.ngaynhap + "', cttb_fk = '" + this.cttbId + "', lanthanhtoan = '" + this.lanthanhtoan + "', ghichu = N'" + this.diengiai + "', " +
							"ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', npp_fk = '" + this.nppId + "' " +
							"where pk_seq = '" + this.id + "' ";
							
			System.out.println("__update DONHANG: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật DONHANGTRATRUNGBAY: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete DONHANGTRATRUNGBAY_CHITIET where donhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật DONHANGTRATRUNGBAY_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.khId.length > 0)
			{
				String sqlInsert = "";
				for(int i = 0; i < this.khId.length; i++)
				{
					if(this.thanhtoan[i].trim().length() > 0)
					{
						String sanpham_fk = "null";
						if(this.spId[i].trim().length() > 0)
							sanpham_fk = this.spId[i].trim();
						
						sqlInsert += " select '" + this.khId[i] + "' as khId, " + sanpham_fk + " as spId, '" + this.soxuat[i].replaceAll(",", "") + "' as soXuat, '" + this.total[i].replaceAll(",", "") + "' as total, '" + this.thanhtoan[i].replaceAll(",", "") + "' as thanhtoan union ";
					}
				}
				
				if(sqlInsert.trim().length() > 0)
				{
					sqlInsert = sqlInsert.substring(0, sqlInsert.length() - 6);
					
					query = "insert DONHANGTRATRUNGBAY_CHITIET(donhang_fk, khachhang_fk, sanpham_fk, soxuat, total, thanhtoan) " +
							"select IDENT_CURRENT('DONHANGTRATRUNGBAY'), chitiet.* from ( " + sqlInsert + " ) chitiet ";
					System.out.println("___CHI TIET: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới DONHANGTRATRUNGBAY_CHITIET: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			}
			catch (Exception e1) {}
			
			this.msg = "Lỗi khi tạo mới đơn hàng: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public int getLanthanhtoan() {

		return this.lanthanhtoan;
	}

	public void setLanthanhtoan(int lantt) {
		
		this.lanthanhtoan = lantt;
	}

	public int getSoLanthanhtoan() {

		return this.solantt;
	}

	public void setSoLanthanhtoan(int solantt) {
	
		this.solantt = solantt;
	}

	
	public String[] getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String[] khId) {
		
		this.khId = khId;
	}

	
	public String[] getSpId() {
		
		return this.spId;
	}

	
	public void setSpId(String[] spId) {
		
		this.spId = spId;
	}

	
	public String[] getThanhtoan() {
		
		return this.thanhtoan;
	}

	
	public void setThanhtoan(String[] thanhtoan) {
		
		this.thanhtoan = thanhtoan;
	}

	
	public String[] getKhTen() {
		
		return this.khTen;
	}

	
	public void setKhTen(String[] khTen) {
		
		this.khTen = khTen;
	}

	
	public String[] getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String[] spTen) {
		
		this.spTen = spTen;
	}

	
	public String[] getSoxuat() {
		
		return this.soxuat;
	}

	
	public void setSoxuat(String[] soxuat) {
		
		this.soxuat = soxuat;
	}

	
	public String[] getTotal() {
		
		return this.total;
	}

	
	public void setTotal(String[] total) {
		
		this.total = total;
	}

	public void setThuongDhdautien(ResultSet thuongRs) {
		
		this.thuongDhRs = thuongRs;
	}

	public ResultSet getThuongDhdautien() {

		return this.thuongDhRs;
	}

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		this.db.shutDown();
	}

	
	
	

}
