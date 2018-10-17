package geso.traphaco.erp.beans.thutien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thutien.IHoadon;

public class ErpThutienCanTru
{
	String userId;
	String id;
	String ngaychungtu;
	String ngayghiso;
	String tienteId;
	String nppId;
	ResultSet nppRs;
	
	String htttId;
	ResultSet htttRs;
	
	String ndId;
	ResultSet ndRs;
	
	String nganhangId;
	ResultSet nganhangRs;
	String chinhanhId;
	ResultSet chinhanhRs;
	
	String sotaikhoan;
	String noidungtt;
	String sotientt;
	
	String tthdCurrent = "";
	String nppdangnhap = "";
	String congtyId = "";
		
	String hoadonId;
	List<IHoadon> hoadonList;
	
	String msg;
	dbutils db;
	Utility util;
	private String trangthai = "";
	
	String isNpp = "";
	
	public boolean KiemTraTrungHoaDon() {
//		String sql  = "SELECT * FROM\n" +
//		"ERP_THUTIEN_HOADON\n" ;
		/*+
		"LEFT JOIN ERP_THUTIEN ON ERP_THUTIEN.PK_SEQ = ERP_THUTIEN_HOADON.THUTIEN_FK\n" +
		"LEFT JOIN ERP_CANTRUCONGNO ON ERP_CANTRUCONGNO.THUTIEN_FK = ERP_THUTIEN_HOADON.THUTIEN_FK\n" +
		"WHERE ERP_THUTIEN_HOADON.	HOADON_FK IN\n" +
		"	(\n" +
		"		SELECT ERP_THUTIEN_HOADON.HOADON_FK\n" +
		"		FROM ERP_THUTIEN_HOADON\n" +
		"		WHERE ERP_THUTIEN_HOADON.THUTIEN_FK = '" + 100003this.id+"'\n" +
		"	)\n" +
		"	\n" +
		"	AND ERP_THUTIEN_HOADON.THUTIEN_FK <> '" + 100003 this.id+"'\n" +
		"	AND ERP_THUTIEN.TRANGTHAI = 0";*/
		
//		ResultSet resultSet = db.get(sql);
		return false;
	}
	
	public ErpThutienCanTru()
	{
		this.id = "";
		this.ngaychungtu = "";
		this.ngayghiso = "";
		
		this.nppId = "";
		this.htttId = "";
		this.ndId = "";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.sotaikhoan = "";
		this.noidungtt = "";
		this.sotientt = "";
		this.hoadonId = "";
		this.msg = "";
		this.hoadonList = new ArrayList<IHoadon>();
		this.trangthai = "";
		this.db = new dbutils();
		this.util=new Utility();
	}
	
	public ErpThutienCanTru(String id)
	{
		this();
		this.id = id;
	}
	
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}
	
	public boolean createTTHD() 
	{
//		this.getNppInfo();
		
		if(this.ndId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung thu tiền";
			return false;
		}
		
		if(this.ndId.equals("100000")) //Thu tien ban hang
		{
			if(this.hoadonList.size() < 0)
			{
				this.msg = "Vui lòng chọn hóa đơn thu tiền";
				return false;
			}
			
			if(this.htttId.length() <= 0)
			{
				this.msg = "Vui lòng chọn hình thức thanh toán";
				return false;
			}
			
			if(this.htttId.equals("100001"))
			{
				if(this.nganhangId.length() <= 0)
				{
					this.msg = "Vui lòng chọn ngân hàng";
					return false;
				}
				
				if(this.chinhanhId.length() <= 0)
				{
					this.msg = "Vui lòng chọn chi nhánh";
					return false;
				}
				
				if(this.sotaikhoan.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn số tài khoản ngân hàng";
					return false;
				}
			}
		}
		
		if(this.sotientt.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số tiền thanh toán";
			return false;
		}
		
		try 
		{
			String ngaytao = Utility.getCurrentDate();
			
			db.getConnection().setAutoCommit(false);
			
			if(this.nganhangId.trim().length() <= 0)
				this.nganhangId = "NULL";
			if(this.chinhanhId.trim().length() <= 0)
				this.chinhanhId = "NULL";
			if(this.sotaikhoan.trim().length() <= 0)
				this.sotaikhoan = "NULL";
			
			String loaithutien = "";
			if(this.isNpp.equals("0"))
				loaithutien = "1";
			if(this.isNpp.equals("1"))
				loaithutien = "0";
			
			String query = "Insert ERP_THUTIEN(NGAYCHUNGTU, NGAYGHISO, TRANGTHAI, KHACHHANG_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT_FK, GHICHU, SOTIENTT, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, NPP_FK, LOAIPHIEUTHU) " +
						   "values('" + this.ngaychungtu + "', '" + this.ngayghiso + "', '0', '" + this.nppId + "', '100002', " + this.nganhangId + ", " + this.chinhanhId + ", " + sotaikhoan + ", " +
									" '" + this.ndId + "', N'" + this.noidungtt + "', '" + this.sotientt.replaceAll(",", "") + "', '"  + ngaytao + "', '" + this.userId + "', '" + ngaytao + "', '" + this.userId + "',"+this.congtyId+", "+this.nppdangnhap+", "+loaithutien+")";
			
			System.out.println(query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_THUTIEN: " + query;
				System.out.println("[ErpThutien.createTTHD] error message:" + this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			System.out.println("ndId " + this.ndId);
			if(this.ndId.equals("100000"))
			{
				
				query = "select scope_identity() as tthdId";
				
				ResultSet rsTthd = db.get(query);						
				if(rsTthd.next())
				{
					this.id = rsTthd.getString("tthdId");
					rsTthd.close();
				}
				
				System.out.println("Thutien: this.hoadonList.size() " + this.hoadonList.size());
				for(int i = 0; i < this.hoadonList.size(); i++)
				{
					IHoadon hoadon = this.hoadonList.get(i);
	
					String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					
					if(thanhtoan.length() > 0)
					{
						if(Float.parseFloat(thanhtoan) != 0)
						{
							query = "Insert ERP_THUTIEN_HOADON(THUTIEN_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, LOAIHOADON, isNPP) " +
									"values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "', '"+ hoadon.getLoaihd() +"', "+this.isNpp+")";
							
							System.out.println(query);
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi ERP_THUTIEN_HOADON: " + query;
								System.out.println("[ErpThutien.createTTHD] error message: " + this.msg);
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			}
			catch (Exception e1) {}
			this.msg = "Lỗi khi tạo mới thu tiền: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean updateTTHD() 
	{
		if(this.ndId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung thu tiền";
			return false;
		}
		
		if(this.ndId.equals("100000")) //Thu tien ban hang
		{
			if(this.hoadonList.size() < 0)
			{
				this.msg = "Vui lòng chọn hóa đơn thu tiền";
				return false;
			}
			
			if(this.htttId.length() <= 0)
			{
				this.msg = "Vui lòng chọn hình thức thanh toán";
				return false;
			}
			
			if(this.htttId.equals("100001"))
			{
				if(this.nganhangId.length() <= 0)
				{
					this.msg = "Vui lòng chọn ngân hàng";
					return false;
				}
				
				if(this.chinhanhId.length() <= 0)
				{
					this.msg = "Vui lòng chọn chi nhánh";
					return false;
				}
				
				if(this.sotaikhoan.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn số tài khoản ngân hàng";
					return false;
				}
			}
		}
		
		if(this.sotientt.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số tiền thanh toán";
			return false;
		}
		
		//Tinh lai tong tien
		/*long tongthanhtoan = 0;
		for(int i = 0; i < this.hoadonList.size(); i++)
		{
			IHoadon hd = this.hoadonList.get(i);
			if(hd.getThanhtoan().length() > 0)
				tongthanhtoan += Long.parseLong(hd.getThanhtoan().replaceAll(",", ""));
		}*/
		
		try 
		{
			String ngaysua = Utility.getCurrentDate();
			
			db.getConnection().setAutoCommit(false);
			
			if(this.nganhangId.trim().length() <= 0)
				this.nganhangId = "NULL";
			if(this.chinhanhId.trim().length() <= 0)
				this.chinhanhId = "NULL";
			if(this.sotaikhoan.trim().length() <= 0)
				this.sotaikhoan = "NULL";
			
			
			String loaithutien = "";
			if(this.isNpp.equals("0"))
				loaithutien = "1";
			if(this.isNpp.equals("1"))
				loaithutien = "0";

			String query = "update ERP_THUTIEN set NGAYCHUNGTU = '" + this.ngaychungtu + "', NGAYGHISO = '" + this.ngayghiso + "', " +
						    "KHACHHANG_FK = '" + this.nppId + "', HTTT_FK = '100002', " +
						    "NGANHANG_FK = " + this.nganhangId + ", CHINHANH_FK = " + this.chinhanhId + ", SOTAIKHOAN = " + this.sotaikhoan + "," +
						    " NOIDUNGTT_FK = '" + this.ndId + "', GHICHU = N'" + this.noidungtt + "', SOTIENTT = '" + this.sotientt.replaceAll(",", "") + "', " +
							"NGAYSUA = '" + ngaysua + "', NGUOISUA = '" + this.userId + "', LOAIPHIEUTHU = "+loaithutien+" where PK_SEQ = '"  + this.id + "'";
			
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_THUTIEN: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_THUTIEN_HOADON where THUTIEN_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_THUTIEN_HOADON: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			
			if(this.ndId.equals("100000"))
			{
				for(int i = 0; i < this.hoadonList.size(); i++)
				{
					System.out.println("I am here ");
					IHoadon hoadon = this.hoadonList.get(i);
	
					String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					
					System.out.println("Thanh toan " + thanhtoan);
					if(thanhtoan.length() > 0)
					{
						if(Float.parseFloat(thanhtoan) != 0)
						{
							query = "Insert ERP_THUTIEN_HOADON(THUTIEN_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, LOAIHOADON, isNPP) " +
									"values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "', '"+ hoadon.getLoaihd() +"', "+this.isNpp+")";
							
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi ERP_THUTIEN_HOADON: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	public boolean chotTTHD(String userId)
	{
		try 
		{
			/**
			 * Kiểm tra xem các hóa đơn trong phiếu thu tiền có hóa đơn nào thuộc 1 phiếu thu tiền khác mà chưa được chốt hay ko
			 * Nếu có thì báo lỗi.
			 */
						
			String query;
//			CheckChot();
			
			String ngaysua = Utility.getCurrentDate();
			
			db.getConnection().setAutoCommit(false);
			
			query = "update ERP_THUTIEN set TRANGTHAI = '1', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "' where PK_SEQ = '"  + this.id + "'";
			System.out.println("1.Cap nhat ERP_THUTIEN: " + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the chot ERP_THUTIEN: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			//cap nhat trang thai hoa don la hoan tat neu da thanh toan het hoa don
			query = "select thanhtoan.hoadon_fk, hoadon.tongtienAVat as sotienAVAT, thanhtoan.SOTIENTT " +
				"from ( " +
						"select hoadon_fk, sum(SOTIENTT) as SOTIENTT " +
						"from ERP_THUTIEN_HOADON " +
						"where hoadon_fk in (select hoadon_fk from ERP_THUTIEN_HOADON where thutien_fk = '" + this.id + "') " +
						"group by hoadon_fk " +
					") thanhtoan inner join ERP_HOADON hoadon on thanhtoan.hoadon_fk = hoadon.pk_seq " +
					"where hoadon.tongtienAVat - thanhtoan.SOTIENTT <= 0";
			
			System.out.println("__Check trang thai hoa don: " + query);
			ResultSet hoadonRs = db.get(query);
			if(hoadonRs != null)
			{
				while(hoadonRs.next())
				{
//					String hoadon_fk = hoadonRs.getString("hoadon_fk");
					/*query = "update ERP_HOADON set trangthai = '3' where pk_seq = '" + hoadon_fk + "'";
					System.out.println("Cap nhat ERP_HOADON " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the cap nhat ERP_HOADON: " + query;
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}*/
				}
				hoadonRs.close();
			}			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			
			this.msg = "Lỗi chốt thu tiền: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean CheckChot() {
		try {
			init();
			
			String query ="SELECT ERP_THUTIEN_HOADON.THUTIEN_FK, ERP_THUTIEN_HOADON.HOADON_FK,ERP_HOADON.SOHOADON, ERP_CANTRUCONGNO.PK_SEQ AS CTCN_FK  FROM\n" +
			"ERP_THUTIEN_HOADON\n" +
			"LEFT JOIN ERP_THUTIEN ON ERP_THUTIEN.PK_SEQ = ERP_THUTIEN_HOADON.THUTIEN_FK\n" +
			"LEFT JOIN ERP_CANTRUCONGNO ON ERP_CANTRUCONGNO.THUTIEN_FK = ERP_THUTIEN_HOADON.THUTIEN_FK\n" +
			"LEFT JOIN ERP_HOADON ON ERP_HOADON.PK_SEQ = ERP_THUTIEN_HOADON.HOADON_FK\n" +
			"WHERE ERP_THUTIEN_HOADON.HOADON_FK IN\n" +
			"	(\n" +
			"		SELECT ERP_THUTIEN_HOADON.HOADON_FK\n" +
			"		FROM ERP_THUTIEN_HOADON\n" +
			"		WHERE ERP_THUTIEN_HOADON.THUTIEN_FK = '" +this.id+	"'\n" +
			"	)\n" +
			"	\n" +
			"	AND ERP_THUTIEN_HOADON.THUTIEN_FK <> '" + this.id+"'\n" +
			"	AND ERP_THUTIEN.TRANGTHAI = 0";
			ResultSet resultSet = db.get(query);
			
				if(resultSet.next())
				{
					if(resultSet.getString("CTCN_FK")==null)
					{
					this.msg = "Hóa đơn số:" +resultSet.getString("SOHOADON")+" đã tồn tại trong phiếu thu tiền số: " +
							resultSet.getString("THUTIEN_FK") +" ở trạng thái chưa chốt Vui lòng kiểm tra lại.";
					return false;
					}else{
						this.msg = "Hóa đơn số:" +resultSet.getString("SOHOADON")+" đã tồn tại trong cấn trừ công nợ số: " +
						resultSet.getString("CTCN_FK") +" ở trạng thái chưa chốt Vui lòng kiểm tra lại.";
						return false;
					}
				}
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkChot2(){
		init();
		
		for (IHoadon hd : this.hoadonList) 
		{
			if (hd.getTongtiencoVAT().length()>0&&hd.getThanhtoan().length()>0) {
				double tongVAT = Double.parseDouble(hd.getTongtiencoVAT()
						.replaceAll(",", ""));
				double thanhtoan = Double.parseDouble(hd.getThanhtoan()
						.replaceAll(",", ""));
				if (tongVAT < thanhtoan) {
					this.msg = " Số tiền thanh toán lớn hơn số tiền gốc";
					return false;
				}
			}
		}
		return true;
	}
	
	public void init()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query =  "select pk_seq, ngaychungtu, ngayghiso, trangthai, khachhang_fk, httt_fk, nganhang_fk, chinhanh_fk, " +
						"sotaikhoan, noidungtt_fk, ghichu, sotientt " +
						"from ERP_THUTIEN where pk_seq = '" + this.id + "'";
		
		System.out.println("115.Khoi tao thu tien11: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.ngayghiso = rs.getString("ngayghiso");
					this.nppId = rs.getString("khachhang_fk");
					this.htttId = rs.getString("httt_fk");
					this.ndId = rs.getString("noidungtt_fk");
					this.trangthai = rs.getString("trangthai");
					
					if(this.htttId.equals("100001"))
					{
						this.nganhangId = rs.getString("nganhang_fk");
						this.chinhanhId = rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan");
					}
					this.noidungtt = rs.getString("ghichu");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		this.createRs();
	}

	public void init2()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query =  "select pk_seq, ngaychungtu, ngayghiso, trangthai, khachhang_fk, httt_fk, nganhang_fk, chinhanh_fk, " +
						"sotaikhoan, noidungtt_fk, ghichu, sotientt " +
						"from ERP_THUTIEN where pk_seq = '" + this.id + "'";
		
		System.out.println("115.Khoi tao thu tien: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.ngayghiso = rs.getString("ngayghiso");
					this.nppId = rs.getString("khachhang_fk");
					this.htttId = rs.getString("httt_fk");
					this.ndId = rs.getString("noidungtt_fk");
					this.trangthai = rs.getString("trangthai");
					
					if(this.htttId.equals("100001"))
					{
						this.nganhangId = rs.getString("nganhang_fk");
						this.chinhanhId = rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan");
					}
					this.noidungtt = rs.getString("ghichu");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		this.createRs();
	}
	
	public void initPdf() 
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = "select a.pk_seq as tthdId, a.sotientt, a.trangthai, a.ngaychungtu, a.ngayghiso, " +
							"b.ten as nppTen, isnull(b.diachi, 'na') as diachi, c.ten as htttTen, f.ten as noidungTen, a.ghichu as ghichu " +
						"from ERP_THUTIEN a inner join NHAPHANPHOI b on a.khachhang_fk = b.pk_seq " +
							"inner join ERP_HINHTHUCTHANHTOAN c on a.HTTT_FK = c.pk_seq " +
							"inner join ERP_NOIDUNGTHUTIEN f on a.noidungtt_fk = f.pk_seq where a.pk_seq = '" + this.id + "' ";
		
		System.out.println("[ErpThutien.initPdf] query = " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.ngayghiso = rs.getString("ngayghiso");
					
					this.nppId = rs.getString("nppTen") + " --- " + rs.getString("diachi");
					this.htttId = rs.getString("htttTen");
					this.ndId = rs.getString("noidungTen");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));
					this.noidungtt = rs.getString("ghichu");
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		//khoi tao hoa don
		query = "select hoadon_fk from erp_thutien_hoadon a inner join ERP_HOADON b on a.hoadon_fk = b.pk_seq where thutien_fk = '" + this.id + "'";
		String sohoadon = "";
		ResultSet hoadonRs = db.get(query);
		if(hoadonRs != null)
		{
			try
			{
				while(hoadonRs.next())
				{
					sohoadon += hoadonRs.getString("hoadon_fk") + ", ";
				}
				hoadonRs.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(sohoadon.length() > 0)
			this.noidungtt = sohoadon.substring(0, sohoadon.length() - 2);
	}

	public void initUnc() 
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = "select a.pk_seq, a.ngaychungtu, b.ten as nppTen, b.diachi, a.httt_fk, c.ten as nganhang_fk, a.chinhanh_fk, a.sotaikhoan, a.noidungtt, a.sotientt " +
						"from ERP_THANHTOANHOADON a inner join ERP_NHACUNGCAP b on a.npp_fk = b.pk_seq left join erp_nganhang c on a.nganhang_fk = c.pk_seq" +
						" where a.pk_seq = '" + this.id + "'";
		System.out.println("Khoi tao Unc: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.nppId = rs.getString("nppTen") + " --- " + rs.getString("diachi");
					this.htttId = rs.getString("httt_fk");
					if(this.htttId.equals("100001"))
					{
						this.nganhangId = rs.getString("nganhang_fk");
						this.chinhanhId = rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan");
					}
					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void createRs() 
	{
		this.getNppInfo();
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		this.nppRs = db.get("select pk_seq, ma + ', ' + ten as nppTen from NHAPHANPHOI where trangthai = '1'" );
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1' and ma in ('TIENMAT', 'CHUYENKHOAN') ");
		this.ndRs = db.get("select pk_seq, ma, ten from ERP_NOIDUNGTHUTIEN where trangthai = '1' ");
		this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang where PK_SEQ in ( select NganHang_FK from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' ) ");
		
		if(this.htttId.equals("100001"))
		{
			if(this.nganhangId.length() > 0)
			{
				this.chinhanhRs = db.get("select pk_seq, ma + ', ' + ten as cnTen from erp_chinhanh where PK_SEQ in ( select ChiNhanh_FK from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' )");
			}
			
			if(this.chinhanhId.length() > 0)
			{
				ResultSet rs = db.get("select sotaikhoan from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' and chinhanh_fk = '" + this.chinhanhId + "' and nganhang_fk = '" + this.nganhangId + "' ");
				if(rs != null)
				{
					try
					{
						if(rs.next())
						{
							this.sotaikhoan = rs.getString("sotaikhoan");
						}
						rs.close();
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		String query = "";
		
		if(this.nppId != null && this.nppId.trim().length() > 0 
				&& this.htttId.length() > 0 && this.hoadonList.size() <= 0 
				&& this.tienteId != null && this.tienteId.trim().length() > 0)
		{
			
			query = "EXEC GET_CANTRUCONGNO_HOADON_KH "
				+ this.nppId + ","
				+ (this.id!=null && this.id.trim().length()==0? "0" : this.id) + " ,"
				+ this.tienteId + " ," + this.congtyId + ", '"+this.ngaychungtu+"'";
		/*	if(this.id.length() > 0)
			{
				// Lấy thu tiền HÓA ĐƠN KH 
				 query = 	" SELECT 0 as LOAIHD, ISNULL( HD.TYGIA,1) AS TYGIA  ,HD.PK_SEQ, ISNULL(HOPDONG.MAHOPDONG, '') AS MAHOPDONG, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n" + 
			 	   			"		HD.NGAYXUATHD AS NGAYHOADON, \n" +
			 	   			"   	ISNULL(HD.TONGTIENAVAT,0) AS SOTIENGOC, \n" +
			 	   			"		ISNULL(HD.TONGTIENAVAT,0) - ISNULL(DATHU.TONGTHU, '0') AS SOTIENNO, \n" +
			 	   			"		TT_HD.SOTIENTT, HD.TIENTE_FK AS TTID \n" +
			 	   			" FROM 	ERP_THUTIEN_HOADON TT_HD \n" +
			 	   			" 		INNER JOIN ERP_HOADONNPP HD ON TT_HD.HOADON_FK = HD.PK_SEQ \n" +
			 	   			" 		LEFT JOIN \n" +
			 	   			" 		(SELECT A.HOADONNPP_FK, B.HOPDONG_FK, C.MAHOPDONG \n" +
			 	   			"  		FROM 	ERP_HOADONNPP_DDH A INNER JOIN ERP_DONDATHANGNPP B ON A.DDH_FK = B.PK_SEQ \n" +
			 	   			"  				INNER JOIN ERP_HOPDONGNPP C ON B.HOPDONG_FK = C.PK_SEQ		\n"+
			 	   			"  		WHERE B.NPP_FK = " + this.nppdangnhap + "  \n"+
			 	   			"  		)HOPDONG ON HOPDONG.HOADONNPP_FK = HD.PK_SEQ \n" +
			 	   			" 		LEFT JOIN	\n" +
			 	   			" 		( 	\n" +
			 	   			"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n" + 		
			 	   			"			FROM ERP_THUTIEN_HOADON TTHD \n" +
			 	   			"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n" + 		
			 	   			"			WHERE TT.TRANGTHAI != '2' AND TTHD.THUTIEN_FK != '" + this.id + "' \n" + 		
			 	   			"		  	AND TTHD.HOADON_FK IN (SELECT HOADON_FK FROM ERP_THUTIEN_HOADON WHERE THUTIEN_FK = '" + this.id + "') \n" + 	
			 	   			"			GROUP BY HOADON_FK \n" +
			 	   			" 		)DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK \n" +
			 	   			"WHERE TT_HD.THUTIEN_FK = '" + this.id + "' AND ISNULL(HD.TIENTE_FK,100000) = " + this.tienteId + " AND HD.NPP_FK ="+this.nppdangnhap+" AND HD.LOAIHOADON = 0 \n" ;
			 	   							 
				
					if(this.tienteId.equals("100000"))
					{
						// HOA DON PHE LIEU
						
						query += "UNION ALL \n"+
						
								 "SELECT 1 as LOAIHD, '1' AS TYGIA  ,HDPL.PK_SEQ, '' AS MAHOPDONG, HDPL.kyhieuhoadon AS KYHIEU, HDPL.SOHOADON, HDPL.NGAYHOADON , \n"+
								 "       (ISNULL(PLSP.SOTIEN, 0) + ISNULL(PLSP.SOTIEN, 0)*HDPL.VAT/100) AS SOTIENGOC, \n " +
								 "	     (ISNULL(PLSP.SOTIEN, 0) + ISNULL(PLSP.SOTIEN, 0)*HDPL.VAT/100) - ISNULL(DATHU.TONGTHU, '0') AS SOTIENNO, \n"+
								 "    	 TT_HD.SOTIENTT, '100000' AS TTID \n"+
								 " FROM ERP_THUTIEN_HOADON TT_HD \n"+
								 " 		LEFT JOIN ERP_HoaDonPheLieu HDPL ON TT_HD.HOADON_FK = HDPL.PK_SEQ and TT_HD.LOAIHOADON= 1 \n"+
								 " 		INNER JOIN (SELECT hoadonphelieu_fk, SUM(thanhtien)AS SOTIEN \n"+
								 "			   		FROM erp_hoadonphelieu_sanpham \n"+
								 "			   		GROUP BY hoadonphelieu_fk \n"+
								 "					)PLSP ON HDPL.pk_seq= PLSP.hoadonphelieu_fk \n"+
								 " 		LEFT JOIN	\n"+
								 " 		( 	\n"+
								 "			SELECT 	TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
								 "			FROM 	ERP_THUTIEN_HOADON TTHD \n"+
								 "					INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
								 "			WHERE 	TT.TRANGTHAI != '2' AND TTHD.THUTIEN_FK !='" + this.id + "' \n"+
								 "					AND TTHD.HOADON_FK IN (SELECT HOADON_FK FROM ERP_THUTIEN_HOADON WHERE THUTIEN_FK = '" + this.id + "' ) \n"+
								 "			GROUP BY HOADON_FK \n"+
								 " 		)DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK \n"+
								 " WHERE TT_HD.THUTIEN_FK = '" + this.id + "' AND HDPL.CONGTY_FK = "+this.congtyId+" \n" ;									
					 }		
					
					query += "UNION ALL \n";
			}			
				
			//HÓA ĐƠN TÀI CHÍNH
				query += 	"(	SELECT 	0 as LOAIHD, ISNULL(HOADON.TYGIA, 1) as TYGIA , HOADON.PK_SEQ, ISNULL(HOADON.MAHOPDONG, '') AS MAHOPDONG, HOADON.KYHIEU, HOADON.SOHOADON, HOADON.NGAYHOADON, \n" +	
							"			ISNULL(HOADON.TONGTIENAVAT, 0) AS SOTIENGOC, \n" +
							"			ISNULL(HOADON.TONGTIENAVAT, 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') AS SOTIENNO, \n" +
							"			0 AS DATHANHTOAN, HOADON.TTID \n" +	
							"	FROM ( \n" +		
							"			SELECT 	HD.PK_SEQ, ISNULL(HOPDONG.MAHOPDONG, '') AS MAHOPDONG, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON, \n" + 
							"					HD.TONGTIENAVAT, ISNULL(HD.TYGIA,1) as TYGIA, ISNULL(HD.TIENTE_FK,100000) AS TTID \n" +		
							"			FROM ERP_HOADONNPP HD 	\n" +	
							" 			LEFT JOIN \n" +
			 	   			" 			(SELECT A.HOADONNPP_FK, B.HOPDONG_FK, C.MAHOPDONG \n" +
			 	   			"  			FROM ERP_HOADONNPP_DDH A INNER JOIN ERP_DONDATHANGNPP B ON A.DDH_FK = B.PK_SEQ \n" +
			 	   			"    	 	INNER JOIN ERP_HOPDONGNPP C ON B.HOPDONG_FK = C.PK_SEQ		\n"+
			 	   			"   	 	WHERE B.NPP_FK = "+this.nppdangnhap+" \n"+
			 	   			"   		)HOPDONG ON HOPDONG.HOADONNPP_FK = HD.PK_SEQ \n";
					if(this.isNpp.equals("0")) // isNPP =0 => KHÁCH HÀNG
			 	   		query+=	"		WHERE HD.KHACHHANG_FK = '" + this.nppId + "' AND HD.TRANGTHAI in (2,4) AND HD.LOAIHOADON = '0' \n";
					else if(this.isNpp.equals("1")) // isNPP = 1 => NHÀ PHÂN PHỐI
						query+=	"		WHERE HD.NPP_FK = '" + this.nppId + "' AND HD.TRANGTHAI in (2,4) AND HD.LOAIHOADON = '0' \n";
					else if(this.isNpp.equals("2")) // isNPP = 2 => NHÂN VIÊN
						query+=	"		WHERE HD.NHANVIEN_FK = '" + this.nppId + "' AND HD.TRANGTHAI in (2,4) AND HD.LOAIHOADON = '0' \n";
			 	   		
				query += "				AND HD.PK_SEQ NOT IN (SELECT HOADON_FK FROM ERP_THUTIEN_HOADON WHERE THUTIEN_FK = '" + ( this.id.trim().length() <=0 ? "0": this.id )+ "') \n";					
							
				query += 	"		) HOADON \n" + 
							"		LEFT JOIN ( \n" +	
							"					SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
							"					FROM  \n" +	
							"						( 	\n" +	
							"							SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
							"							FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
							"							INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +  		
							"							WHERE TT.TRANGTHAI NOT IN (2)  AND TT.CONGTY_FK = " + ( this.id.trim().length() <=0 ? "0": this.id )+" AND TT.isNPP = "+this.isNpp+		
							"							GROUP BY HOADON_FK  \n" +		
			
							"						 UNION ALL \n" +
			
							"							SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
							"							FROM ERP_THUTIEN_HOADON TTHD \n" +
							"							INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n" +   		
							"							WHERE TT.TRANGTHAI NOT IN (2) AND TT.CONGTY_FK ="+this.congtyId+ " AND TTHD.isNPP = "+this.isNpp;	
				query += 	" 							AND TT.PK_SEQ  != '" + ( this.id.trim().length() <=0 ? "0": this.id )+ "' \n";				
			
				query +=	" 							GROUP BY HOADON_FK \n" +  	
							"						) HOADONDATT  \n" +
							"					GROUP BY HOADON_FK \n" +   
							"				   )DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADON_FK \n" +
							" WHERE HOADON.TONGTIENAVAT - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') > 0 AND HOADON.TTID = " + this.tienteId + " ) \n" ;
			
			
			// HD PHE LIEU
			if(this.tienteId.equals("100000") && this.isNpp != null && this.isNpp.trim().length() > 0) // HOADONPHELIEU
			{				
				query +=" UNION ALL ";
				
				query+=	" (SELECT 1 as LOAIHD, '1' AS TYGIA , HDPHELIEU.PK_SEQ ,HDPHELIEU.MAHOPDONG, HDPHELIEU.KYHIEU, \n" +
						"         HDPHELIEU.SOHOADON, HDPHELIEU.NGAYHOADON, ISNULL(HDPHELIEU.SOTIENVND,0) AS SOTIENGOC, \n " +
						"         ISNULL(HDPHELIEU.SOTIENVND,0) - ISNULL(DATHANHTOAN.DATHANHTOAN,0) AS SOTIENNO, 0 AS DATHANHTOAN, '100000' AS TTID \n"+
						"  FROM \n" +
						" 		(" +
						" 			SELECT '1' AS TYGIA,PL.PK_SEQ,'' AS MAHOPDONG, PL.KYHIEUHOADON AS KYHIEU, PL.SOHOADON, PL.NGAYHOADON, (PLSP.SOTIENVND + PLSP.SOTIENVND*PL.VAT/100) AS SOTIENVND, 0 AS SOTIENNT,0 AS DATHANHTOAN ,'100000' AS TTID \n" +
						" 			FROM ERP_HOADONPHELIEU PL INNER JOIN \n" +
						"    		(SELECT HOADONPHELIEU_FK, SUM(THANHTIEN)AS SOTIENVND \n" +
						"     		FROM ERP_HOADONPHELIEU_SANPHAM \n" +
						"     		GROUP BY HOADONPHELIEU_FK)AS PLSP  ON PL.PK_SEQ = PLSP.HOADONPHELIEU_FK \n" +
						" 			WHERE PL.KHACHHANG_FK = '" + this.nppId + "' and TRANGTHAI = '1' AND PL.isNPP = "+this.isNpp + "\n";
				query += "			AND PL.PK_SEQ NOT IN (SELECT HOADON_FK FROM ERP_THUTIEN_HOADON WHERE THUTIEN_FK = '" + ( this.id.trim().length() <=0 ? "0": this.id )+ "') \n";
					
				query +=" 		)HDPHELIEU \n "+
						" 		LEFT JOIN ( \n" +
						
						"		SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
						"		FROM  \n" +	
						"			( 	\n" +	
						"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
						"			FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
						"			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +  		
						"			WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' \n" +
						"			AND TTHD.LOAIHD = '1' AND TT.isNPP = "+this.isNpp+" \n" +		
						"			GROUP BY HOADON_FK \n" +
						"                             \n"+
						"       	UNION ALL                      \n"+
						
						"			SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
						"			FROM ERP_THUTIEN_HOADON TTHD \n" +
						"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n" +   		
						"			WHERE TTHD.LOAIHOADON= '1' AND TT.TRANGTHAI NOT IN (2) AND TTHD.isNPP = "+this.isNpp+" \n"	; 
				query += " 			AND TT.PK_SEQ  != '" + ( this.id.trim().length() <=0 ? "0": this.id )+ "' \n";					
					
				query +=" 			GROUP BY HOADON_FK \n" +  	
						"			) HOADONDATT  \n" +
						"		GROUP BY HOADON_FK \n" +   
						"		)DATHANHTOAN ON HDPHELIEU.PK_SEQ = DATHANHTOAN.HOADON_FK \n" +
						" WHERE HDPHELIEU.SOTIENVND - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') > 0  \n" +
						")\n" ;
			}
				
			query +=    " ORDER BY NGAYHOADON ASC "; 	*/
							
				System.out.println("1.Khach hang - Khoi tao hoa don: \n" + query + "\n--------------------------------------------");
			
				ResultSet rsHoadon = db.get(query);
				List<IHoadon> hdList = new ArrayList<IHoadon>();
				if(rsHoadon != null)
				{
					try 
					{
						IHoadon hd = null;
						while(rsHoadon.next())
						{
							String id = rsHoadon.getString("PK_SEQ");
							String mahopdong ="";
							String kyhieu = rsHoadon.getString("KYHIEU");
							String sohoadon = rsHoadon.getString("SOHOADON");
							String ngayhd = rsHoadon.getString("NGAYHOADON");
							String loaihd = rsHoadon.getString("LOAIHD");
							String avat = formatter.format(Double.parseDouble(rsHoadon.getString("SOTIENGOC")));
							String sotienno = formatter.format(Double.parseDouble(rsHoadon.getString("sotienAVATVND")));
							String ttId =("100000");
							String tygia="1";

							String dangthanhtoan = "0";
							if(this.id.length() > 0)
							{
								if(rsHoadon.getDouble("DANGTHANHTOAN") > 0)
									dangthanhtoan = "" + formatter.format(rsHoadon.getDouble("DANGTHANHTOAN"))	;
							}
						
						
							hd = new Hoadon(id, mahopdong, kyhieu, sohoadon, ngayhd, avat, sotienno, dangthanhtoan, ttId, tygia);
							hd.setLoaihd(loaihd);
							hdList.add(hd);
						}
						rsHoadon.close();
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
				this.hoadonList = hdList;
			}
	}

	public void DBclose() 
	{
		if (this.db != null)
		{
			this.db.shutDown();
			this.db = null;
		}
	}

	public String getNgayghiso() 
	{
		return this.ngayghiso;
	}

	public void setNgayghiso(String ngayghiso) 
	{
		this.ngayghiso = ngayghiso;
	}

	public String getNoidungId()
	{
		return this.ndId;
	}

	public void setNoidungId(String ndId) 
	{
		this.ndId = ndId;
	}

	public ResultSet getNoidungRs()
	{
		return this.ndRs;
	}

	public void setNoidungRs(ResultSet ndRs) 
	{
		this.ndRs = ndRs;	
	}

	public String delete() {
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			
			String query = "DELETE FROM ERP_ThuTien_HOADON where THUTIEN_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return  "Khong the xoa ERP_ThuTien_HOADON ";
			}
			
			query = "DELETE FROM ERP_ThuTien where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return  "Khong the xoa ERP_ThuTien ";
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (SQLException e)
		{ 
			e.printStackTrace();
			db.shutDown(); 
			return "Khong the xoa ERP_ThuTien"; 
		}
	}
	
	public void createRs2() 
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		this.nppRs = db.get("select pk_seq, ma + ', ' + ten as nppTen from NHAPHANPHOI where trangthai = '1' " );
		
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1' and ma in ('TIENMAT', 'CHUYENKHOAN') ");
		this.ndRs = db.get("select pk_seq, ma, ten from ERP_NOIDUNGTHUTIEN where trangthai = '1' ");
		this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang where PK_SEQ in ( select NganHang_FK from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' ) ");
		
		if(this.htttId.equals("100001"))
		{
			if(this.nganhangId.length() > 0)
			{
				this.chinhanhRs = db.get("select pk_seq, ma + ', ' + ten as cnTen from erp_chinhanh where PK_SEQ in ( select ChiNhanh_FK from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' )");
			}
			
			if(this.chinhanhId.length() > 0)
			{
				ResultSet rs = db.get("select sotaikhoan from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' and chinhanh_fk = '" + this.chinhanhId + "' and nganhang_fk = '" + this.nganhangId + "' ");
				if(rs != null)
				{
					try
					{
						if(rs.next())
						{
							this.sotaikhoan = rs.getString("sotaikhoan");
						}
						rs.close();
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		
		String query = "";
		if(this.nppId.length() > 1 && this.htttId.length() > 0 && this.hoadonList.size() <= 0)
		{
			if(this.id.length() > 0)
			{
				// Lấy thu tiền this.id
				 query = 	"SELECT 0 as LOAIHD, ISNULL( HD.TYGIA,1) AS TYGIA  ,HD.PK_SEQ, ISNULL(HOPDONG.MAHOPDONG, '') AS MAHOPDONG, HD.KYHIEU AS KYHIEU, HD.SOHOADON, " + 
			 	   			"	HD.NGAYXUATHD AS NGAYHOADON, " +
			 	   			"   ISNULL(HD.TONGTIENAVAT,0) AS SOTIENGOC, " +
			 	   			"	ISNULL(HD.TONGTIENAVAT,0) - ISNULL(DATHU.TONGTHU, '0') AS SOTIENNO, " +
//			 	   			"	ISNULL(HD.TONGTIENAVAT, 0) - ISNULL(DATHU.TONGTHU, '0') AS SOTIENNT, " +
			 	   			"	TT_HD.SOTIENTT, ISNULL(HD.TIENTE_FK,100000) AS TTID " +
			 	   			" FROM ERP_THUTIEN_HOADON TT_HD " +
			 	   			" INNER JOIN ERP_HOADON HD ON TT_HD.HOADON_FK = HD.PK_SEQ " +
			 	   			" LEFT JOIN ERP_HOPDONG HOPDONG ON HOPDONG.PK_SEQ = HD.HOPDONG_FK " +
			 	   			" LEFT JOIN	" +
			 	   			" ( 	" +
			 	   			"	SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU " + 		
			 	   			"	FROM ERP_THUTIEN_HOADON TTHD " +
			 	   			"	INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ " + 		
			 	   			"	WHERE TT.TRANGTHAI != '2' AND TTHD.THUTIEN_FK != '" + this.id + "' " + 		
			 	   			"	AND TTHD.HOADON_FK IN " +
			 	   			"		(SELECT HOADON_FK FROM ERP_THUTIEN_HOADON WHERE THUTIEN_FK = '" + this.id + "' " + 						
			 	   			" ) 	" +	
			 	   			"	GROUP BY HOADON_FK " +
			 	   			" )DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK " +
			 	   			"WHERE TT_HD.THUTIEN_FK = '" + this.id + "' AND ISNULL(HD.TIENTE_FK,100000) = " + this.tienteId + " " ;
				 
				// HOA DON PHE LIEU
					if(this.tienteId.equals("100000"))
					{
					query += "UNION ALL \n"+
							 "SELECT 1 as LOAIHD, '1' AS TYGIA  ,HDPL.PK_SEQ, '' AS MAHOPDONG, HDPL.kyhieuhoadon AS KYHIEU, HDPL.SOHOADON, HDPL.NGAYHOADON , \n"+
							 "       (ISNULL(PLSP.SOTIEN, 0) + ISNULL(PLSP.SOTIEN, 0)*HDPL.VAT/100) AS SOTIENGOC, \n " +
							 "	     (ISNULL(PLSP.SOTIEN, 0) + ISNULL(PLSP.SOTIEN, 0)*HDPL.VAT/100) - ISNULL(DATHU.TONGTHU, '0') AS SOTIENNO, \n"+
							 "    	 TT_HD.SOTIENTT, '100000' AS TTID \n"+
							 " FROM ERP_THUTIEN_HOADON TT_HD \n"+
							 " LEFT JOIN ERP_HoaDonPheLieu HDPL ON TT_HD.HOADON_FK = HDPL.PK_SEQ and TT_HD.LOAIHOADON= 1 \n"+
							 " INNER JOIN (SELECT hoadonphelieu_fk, SUM(thanhtien)AS SOTIEN \n"+
							 "			 FROM erp_hoadonphelieu_sanpham \n"+
							 "			 GROUP BY hoadonphelieu_fk )PLSP ON HDPL.pk_seq= PLSP.hoadonphelieu_fk \n"+
							 " LEFT JOIN	\n"+
							 " ( 	\n"+
							 "	SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
							 "	FROM ERP_THUTIEN_HOADON TTHD \n"+
							 "	INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
							 "	WHERE TT.TRANGTHAI != '2' AND TTHD.THUTIEN_FK !='" + this.id + "' \n"+
							 "	AND TTHD.HOADON_FK IN \n"+
							 "		(SELECT HOADON_FK FROM ERP_THUTIEN_HOADON WHERE THUTIEN_FK = '" + this.id + "' \n"+
							 ") 	\n"+
							 "	GROUP BY HOADON_FK \n"+
							 " )DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK \n"+
							 " WHERE TT_HD.THUTIEN_FK = '" + this.id + "'  \n" ;
					}
			}			
							
				System.out.println("1.Khach hang - Khoi tao hoa don 2..: " + query);
			
				ResultSet rsHoadon = db.get(query);
				List<IHoadon> hdList = new ArrayList<IHoadon>();
				if(rsHoadon != null)
				{
					try 
					{
						IHoadon hd = null;
						while(rsHoadon.next())
						{
							String id = rsHoadon.getString("PK_SEQ");
							String mahopdong = rsHoadon.getString("MAHOPDONG");
							String kyhieu = rsHoadon.getString("KYHIEU");
							String sohoadon = rsHoadon.getString("SOHOADON");
							String ngayhd = rsHoadon.getString("NGAYHOADON");
							String loaihd = rsHoadon.getString("LOAIHD");
							String avat = formatter.format(Double.parseDouble(rsHoadon.getString("SOTIENGOC")));
							String sotienno = formatter.format(Double.parseDouble(rsHoadon.getString("SOTIENNO")));
							String ttId = rsHoadon.getString("TTID");
							String tygia=rsHoadon.getString("TYGIA");
//							
							String dathanhtoan = "0";
							if(this.id.length() > 0)
							{
								if(rsHoadon.getDouble("SOTIENTT") > 0)
									dathanhtoan = "" + formatter.format(rsHoadon.getDouble("SOTIENTT"))	;
							}
						
					
							hd = new Hoadon(id, mahopdong, kyhieu, sohoadon, ngayhd, avat, sotienno, dathanhtoan, ttId, tygia);
							hd.setLoaihd(loaihd);
							hdList.add(hd);
						}
						rsHoadon.close();
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
				this.hoadonList = hdList;
			}
	}

	public String delete(String IdThuTien) {
		// TODO Auto-generated method stub
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update ERP_ThuTien set trangthai = '2' where pk_seq = '" + IdThuTien + "'");
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (SQLException e)
		{ 
			e.printStackTrace();
			db.shutDown(); 
			return "Khong the xoa ERP_ThuTien"; 
		}
	}

	public static void main(String[] args) {
		ErpThutienCanTru canTru = new ErpThutienCanTru();
		canTru.KiemTraTrungHoaDon();
	}

	public String getcongtyId()
	{
		return this.congtyId;
	}

	public void setcongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	
	public String getnppdangnhap()
	{
		return this.nppdangnhap;
	}

	public void setnppdangnhap(String nppdangnhap) 
	{
		this.nppdangnhap = nppdangnhap;
	}
	
	public String getIsNPP()
	{
		return this.isNpp;
	}

	public void setIsNPP(String isnpp) 
	{
		this.isNpp = isnpp;
	}
	
	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getTienteId() {
		return this.tienteId;
	}
	
	public void setTienteId(String ttId) {
		this.tienteId = ttId;
	}
	
	public String getTthdCurrent() {
		return tthdCurrent;
	}

	public void setTthdCurrent(String tthdCurrent) {
		this.tthdCurrent = tthdCurrent;
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

	public String getNgaychungtu() 
	{
		return this.ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) 
	{
		this.ngaychungtu = ngaychungtu;
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;	
	}

	public ResultSet getNppRs()
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}

	public String getHtttId() 
	{
		return this.htttId;
	}

	public void setHtttId(String htttId)
	{
		this.htttId = htttId;
	}

	public ResultSet getHtttRs() 
	{
		return this.htttRs;
	}

	public void setHtttRs(ResultSet htttRs)
	{
		this.htttRs = htttRs;	
	}

	public String getNganhangId()
	{
		return this.nganhangId;
	}

	public void setNganhangId(String nganhangId)
	{
		this.nganhangId = nganhangId;
	}

	public ResultSet getNganhangRs() 
	{
		return this.nganhangRs;
	}

	public void setNganhangRs(ResultSet nganhangRs) 
	{
		this.nganhangRs = nganhangRs;
	}

	public String getChinhanhId() 
	{
		return this.chinhanhId;
	}

	public void setChinhanhId(String cnId)
	{
		this.chinhanhId = cnId;
	}

	public ResultSet getChinhanhRs()
	{
		return this.chinhanhRs;
	}

	public void setChinhanhRs(ResultSet chinhanhRs) 
	{
		this.chinhanhRs = chinhanhRs;
	}

	public String getSotaikhoan() 
	{
		return this.sotaikhoan;
	}

	public void setSotaikhoan(String sotk)
	{
		this.sotaikhoan = sotk;
	}

	public String getNoidungtt()
	{
		return this.noidungtt;
	}

	public void setNoidungtt(String ndtt) 
	{
		this.noidungtt = ndtt;
	}

	public String getSotientt() 
	{
		return this.sotientt;
	}

	public void setSotientt(String sotientt) 
	{
		this.sotientt = sotientt;
	}

	public String getHoadonIds() 
	{
		return this.hoadonId;
	}

	public void setHoadonIds(String hdIds) 
	{
		this.hoadonId = hdIds;
	}

	public List<IHoadon> getHoadonRs() 
	{
		return this.hoadonList;
	}

	public void setHoadonRs(List<IHoadon> hoadonRs)
	{
		this.hoadonList = hoadonRs;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
}