package geso.traphaco.erp.beans.masclon.imp;

import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.erp.beans.khauhaotaisancodinh.imp.Khauhaotaisan;
import geso.traphaco.erp.beans.xuatdungccdc.Erp_Item;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Erp_MaSCLon extends Phan_Trang
{
	private String id;
	private String ma;
	private String ten;
	private String taiKhoanId;
	private String taiSanId;//Tài sản cố định
	
	private String masclonId;
	private String trangThai;
	private String ngayTao;
	private String ngaySua;
	private String nguoiTao;
	private String nguoiSua;
	private String congTyId;
	private String msg;
	private String DvkdId;
	private String nppId;
	
	private Double giaTri;
	private Integer loaiId;
	private String ngayChuyen;
	
	private List<Erp_Item> taiKhoanList;
	private List<Erp_Item> taiSanCDList;
	private List<Erp_Item> loaiList;
	private List<Erp_Item> dvkdList;
	
	private dbutils db;

	public Erp_MaSCLon()
	{
		this.id = "";
		this.ma = "";
		this.giaTri = 0.0;
		this.loaiId = 0;
		this.ten = "";
		this.taiKhoanId = "";
		this.taiSanId = "";
		this.trangThai = "";
		this.ngayTao = "";
		this.ngaySua = "";
		this.nguoiTao = "";
		this.nguoiSua = "";
		this.ngayChuyen = "";
		this.DvkdId="";
		this.congTyId = "";
		this.msg = "";
		
		this.taiKhoanList = new ArrayList<Erp_Item>();
		this.taiSanCDList = new ArrayList<Erp_Item>();
		this.dvkdList= new ArrayList<Erp_Item>();
		this.loaiList = new ArrayList<Erp_Item>();

		this.db = new dbutils();
	}
	
	public String getNgayChuyen() {
		return ngayChuyen;
	}

	public void setNgayChuyen(String ngayChuyen) {
		this.ngayChuyen = ngayChuyen;
	}

	public void init()
	{
		if (this.id.trim().length() > 0)
		{
			String query = 
				"select msc.MA, msc.TEN, msc.TAIKHOAN_FK, msc.TAISAN_FK, msc.TRANGTHAI\n" +
				"	, msc.NGAYTAO, msc.NGUOITAO, msc.NGAYSUA, msc.NGUOISUA" +
				"	, isNull((select sum(giaTri) from ERP_MASCLON_DIEUCHINH where MASCLON_FK = msc.PK_SEQ group by MASCLON_FK), 0) as giaTri\n" +
				"	, msc.LOAI, isNull(msc.ngayChuyen, '') as ngayChuyen\n" +
				"from ERP_MASCLON msc\n" +
				"where msc.PK_SEQ = " + this.id + " and msc.CONGTY_FK = " + this.congTyId;
			
			System.out.println("init a sc:\n" + query + "\n-------------------------------------------------------");
			ResultSet rs = null;
			
			try {
				rs = this.db.get(query);
				
				if (rs != null)
					if (rs.next())
					{
						this.ma = rs.getString("ma");
						this.ten = rs.getString("ten");
						this.giaTri = rs.getDouble("giaTri");
						this.loaiId = rs.getInt("LOAI");
						this.taiKhoanId = rs.getString("TAIKHOAN_FK");
						this.taiSanId = rs.getString("TAISAN_FK");
						this.ngayTao = rs.getString("NGAYTAO");
						this.ngaySua = rs.getString("NGAYSUA");
						this.nguoiTao = rs.getString("NGUOITAO");
						this.nguoiSua = rs.getString("NGUOISUA");
						this.ngayChuyen = rs.getString("NGAYCHUYEN");
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
		initList();
	}
	
	private void initList() {
		//Tài khoản kế toán
		String query = 
			"select tk.PK_SEQ, isNull(tk.SOHIEUTAIKHOAN, '') + ' -- ' + isNull(tk.TENTAIKHOAN, '') as ten\n" +
			"from ERP_TAIKHOANKT tk\n" +
			"where tk.TRANGTHAI = 1 and CONGTY_FK =  100000 and tk.soHieuTaiKhoan like '241%'\n" +
			"order by tk.SOHIEUTAIKHOAN";
		System.out.println("query lay tai khoan ke toan : \n " + query + "\n===================");

		Erp_Item.getListFromQuery(db, query, this.taiKhoanList);
		
		//Tài sản cố định
		initTaiSanList();
		//DVKD
		//Loại
		this.loaiList.clear();
		Erp_Item item = new Erp_Item("0", "Sửa chữa");
		Erp_Item item1 = new Erp_Item("1", "Mua mới");
		Erp_Item item2 = new Erp_Item("2", "Xây dựng cơ bản");
		this.loaiList.add(item);
		this.loaiList.add(item1);
		this.loaiList.add(item2);
	}
	
	public void initTaiSanList()
	{
		//Tài sản cố định
		this.taiSanCDList.clear();
		String query = 
			"select ts.PK_SEQ, isNull(ts.ma, '') + ' -- ' + isNull(ts.diengiai, '') as ten\n" +
			"from ERP_TAISANCODINH ts\n" +
			"where (ts.TRANGTHAI = " + (this.loaiId == 0 ? "1" : "0") + "\n" +
			((this.taiSanId != null && this.taiSanId.trim().length() > 0) ? ("or ts.PK_SEQ = " + this.taiSanId) : "") + ")\n" +
			"and CONGTY_FK = " + this.congTyId + " and (ts.isDaThanhLy is Null or ts.isDaThanhLy = 0)" + 
			" order by ts.pk_seq";
		System.out.println("danh sach tai san: \n" + query + "\n-----------------------------------------------");
		Erp_Item.getListFromQuery(db, query, this.taiSanCDList);
	}
	
	/*public void initdvkdList()
	{
		//Tài sản cố định
		this.dvkdList.clear();
		String query = 
			" select PK_SEQ ,DONVIKINHDOANH as TEN" +
			" from DONVIKINHDOANH \n" +
			" where congty_fk=" + this.congTyId ;
		System.out.println("don vi kinh doanh: \n" + query + "\n-----------------------------------------------");
		Erp_Item.getListFromQuery(db, query, this.dvkdList);
	}*/
	
	public boolean newMaSCLon() {
		try {
			this.ngaySua = this.ngayTao = getDateTime();
			this.db.getConnection().setAutoCommit(false);
			
			if (check_MaSCLon() == false)
			{
				this.db.getConnection().rollback();
				return false;
			}
			
			String query = 
//				"insert into ERP_MASCLON (MA, TEN, TAIKHOAN_FK\n" +
//				", TAISAN_FK, CONGTY_FK, TRANGTHAI" +
//				", NGAYTAO, NGUOITAO, NGAYSUA,NGUOISUA\n" +
//				", loai,DVKD_FK,NPP_FK)\n" +
//				"values (N'" + this.ma + "', N'" + this.ten + "', " + this.taiKhoanId + "\n" +
//				", " + this.taiSanId + ", " + this.congTyId + ", '0'\n" +
//				", '" + this.ngayTao + "', " + this.nguoiTao + ", '" + this.ngaySua + "', " + this.nguoiSua + "\n" +
//				", " + this.loaiId + ")\n";
			
			"insert into ERP_MASCLON (MA, TEN, TAIKHOAN_FK\n" +
			", TAISAN_FK, CONGTY_FK, TRANGTHAI" +
			", NGAYTAO, NGUOITAO, NGAYSUA,NGUOISUA\n" +
			", loai)\n" +
			"values (N'" + this.ma + "', N'" + this.ten + "', " + this.taiKhoanId + "\n" +
			", " + this.taiSanId + ", " + this.congTyId + ", '0'\n" +
			", '" + this.ngayTao + "', " + this.nguoiTao + ", '" + this.ngaySua + "', " + this.nguoiSua + "\n" +
			", " + this.loaiId + ")\n";
		
			
			if (!this.db.update(query))
			{
				this.msg = "N1.1 Không thể tạo mới";
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	private boolean check_MaSCLon()
	{
		try {
			if (this.ma == null || this.ma.trim().length() == 0)
			{
				this.msg = "CMSCL1.0 Vui lòng nhập 'Mã'\n";
				this.db.getConnection().rollback();
				return false;
			}
			
//			if (this.giaTri == null || this.giaTri == 0)
//			{
//				this.msg = "CMSCL1.0.1 Vui lòng nhập 'Giá trị'\n";
//				this.db.getConnection().rollback();
//				return false;
//			}
			
			if (this.ten == null || this.ten.trim().length() == 0)
			{
				this.msg = "CMSCL1.1 Vui lòng nhập 'Tên hạng mục'\n";
				this.db.getConnection().rollback();
				return false;
			}
			
			if (this.taiKhoanId == null || this.taiKhoanId.trim().length() == 0)
			{
				this.msg = "CMSCL1.2 Vui lòng chọn 'Tài khoản kế toán'\n";
				this.db.getConnection().rollback();
				return false;
			}
			
			if (this.taiSanId == null || this.taiSanId.trim().length() == 0)
			{
				this.msg = "CMSCL1.3 Vui lòng chọn 'Tài sản cố định'\n";
				this.db.getConnection().rollback();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean editMaSCLon() {
		try {
			this.db.getConnection().setAutoCommit(false);
			this.ngaySua = getDateTime();
			
			if (check_MaSCLon() == false)
			{
				this.db.getConnection().rollback();
				return false;
			}
			
			String query = 
				"update ERP_MASCLON\n" +
				"set MA = N'" + this.ma + "', TEN = N'" + this.ten + "', TAIKHOAN_FK = " + this.taiKhoanId + "\n" +
				", TAISAN_FK = " + this.taiSanId + ",CONGTY_FK = " + this.congTyId + ", TRANGTHAI = '0'\n" +
				", NGAYSUA = '" + this.ngaySua + "', NGUOISUA = " + this.nguoiSua + "\n" +
				", loai = " + this.loaiId + "\n"+ 
				" where PK_SEQ = " + this.id ;
			
			
			
			if (!this.db.update(query))
			{
				this.msg = "E1.1 Không thể sửa";
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "E1 Không thể sửa phiếu xuất dùng";
			return false;
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	public boolean editNgayKetChuyen() {
		try {
			this.db.getConnection().setAutoCommit(false);
			this.ngaySua = getDateTime();
			
			if (check_MaSCLon() == false)
			{
				this.db.getConnection().rollback();
				return false;
			}
			if(this.ngayChuyen.length()<=0)
			{
				this.msg = "Vui lòng chọn ngày kết chuyển";
				this.db.getConnection().rollback();
				return false;
			}

			String query = 
				"update ERP_MASCLON\n" +
				"set ngaychuyen='"+this.ngayChuyen+"' " +
				"where PK_SEQ = " + this.id + " and CONGTY_FK = " + this.congTyId;
			
			if (!this.db.update(query))
			{
				this.msg = "E1.1 Không thể sửa";
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "E1 Không thể sửa phiếu xuất dùng";
			return false;
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	
	public boolean deleteMaSCLon()
	{
		try {
			this.db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_MASCLON set trangThai = 2 where trangThai = 0 and PK_SEQ = " + this.id + " and congTy_FK = " + this.congTyId;
			int num = this.db.updateReturnInt(query);
			if (num < 1)
			{
				this.msg = "D1.1 Không thể xóa phiếu xuất dùng";
				this.db.getConnection().rollback();
				return false;
			}

			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "D1 Không thể xóa phiếu xuất dùng";
			return false;
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean chotMASCLON() {
		try {
			this.db.getConnection().setAutoCommit(false);
			String query = null;

			//Update trạng thái xuất dùng
			query = "update ERP_MASCLON set TRANGTHAI = 1 where TRANGTHAI = 0 and  PK_SEQ = " + this.id + " and congTy_FK = " + this.congTyId;
			int num = this.db.updateReturnInt(query);
			if (num == 0)
			{
				this.msg = "C1.7 Không thể chốt\n";
				this.db.getConnection().rollback();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "C1 Không thể chốt phiếu";
			return false;
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean mochot() {
		//Lấy giá tồn kì gần nhất áp cho sản phẩm
		try {
			this.db.getConnection().setAutoCommit(false);
			String query = null;

			UtilityKeToan util = new UtilityKeToan();

			query = "select month(NGAYCHUYEN) thang, year(NGAYCHUYEN) nam from ERP_MASCLON WHERE PK_SEQ = " + this.id + "";
			System.out.println("ngay thang " +query);
			ResultSet rs = this.db.get(query);
			if (rs != null)
			{
				if (rs.next())
				{
					util.setThang(rs.getString("thang"));
					util.setNam(rs.getString("nam"));
				}
				rs.close();
			}
			String loaiChungTu="N'Chuyển mã SC lớn/XDCB TSCD' ";
			/*this.msg=util.HuyUpdate_TaiKhoan(db, this.id, loaiChungTu);*/
			//this.msg = util.HuyUpdate_TaiKhoan_NgayChungTu(db, this.id,loaiChungTu,"NGAYCHUYEN","ERP_MASCLON");
			this.msg=util.HuyUpdate_TaiKhoan(db, this.id, loaiChungTu);
			if(this.msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}
			
			
			//Update trạng thái xuất dùng
			query = "update ERP_MASCLON set TRANGTHAI = 1 where PK_SEQ = " + this.id + 
					" and congTy_FK = " + this.congTyId;
			
			System.out.println(" mo chuyen msc: \n" + query + "\n----------------------------------");
			int num = this.db.updateReturnInt(query);
			if (num == 0)
			{
				this.msg = "CH1.1 Không thể mở chuyển\n";
				this.db.getConnection().rollback();
				return false;
			}
		
			double giatri=0;
	
			query="SELECT MSCL.LOAI,MSCL.TAISAN_FK,sum(MSCLONDIEUCHINH.giatri) as giatri \n"
				+ "FROM  ERP_MASCLON MSCL \n"
				+ "LEFT JOIN ERP_MASCLON_DIEUCHINH MSCLONDIEUCHINH ON MSCL.PK_SEQ=MSCLONDIEUCHINH.MASCLON_FK  \n"
				+ " WHERE MSCL.PK_SEQ="+this.id +"\n"
				+" group by MSCL.LOAI,MSCL.TAISAN_FK \n";
			ResultSet rsCheck = db.get(query);
			try {
				if (rsCheck != null) {
					while (rsCheck.next()) {
						this.loaiId = rsCheck.getInt("LOAI");
						this.taiSanId=rsCheck.getString("TAISAN_FK");
						giatri=rsCheck.getDouble("giatri");
					}
					rsCheck.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			query="SELECT COUNT(*)  dem FROM ERP_KHAUHAOTAISAN WHERE TAISAN_FK="+this.taiSanId;
					//and congty_fk="+this.congTyId+" and npp_fk="+this.nppId
			System.out.println("query CheckKhauHao: "+query);
			ResultSet rsDem = db.get(query);
			int dem_trung = 0;
			if(rsDem!=null)
			{
				while(rsDem.next())
				{
					dem_trung = rsDem.getInt("dem");
				}rsDem.close();
			}
			
			if(dem_trung>=1)
			{
				this.msg = "CH1.2 Tài sản đã được khấu hao, không thể mở chốt!! \n";
				this.db.getConnection().rollback();
				return false;
			}
			//Mua mới: cập nhật nguyên giá tài sản
			if (this.loaiId == 1||this.loaiId==2)
			{
				query = "update ERP_TAISANCODINH \n" +
						"set DONGIA = " + this.giaTri +"-"+giatri + ", trangThai = 0, thanhTien = " +this.giaTri +"-"+giatri + ",soluong='0', ngayghitang='"+this.ngayChuyen +"' \n" +
						"where PK_SEQ = " + this.taiSanId + " \n" ;
				System.out.println("cau cap nhat" +query);
				num = this.db.updateReturnInt(query);
				if (num == 0)
				{
					this.msg = "CH1.2 Không thể chuyển\n";
					this.db.getConnection().rollback();
					return false;
				}
			}
			else{ //Sửa chữa: cập nhật nguyên giá vào điều chỉnh
			
				
				query = "DELETE ERP_TAISANCODINH_DIEUCHINH  WHERE TSCD_FK="+this.taiSanId+" and loaichungtu=N'Mã SCLớn/ XDCB TSCĐ'";
				System.out.println("DELETE ERP_TAISANCODINH_DIEUCHINH \n "+ query + "\n----------------------------------------");
				num = this.db.updateReturnInt(query);
				if (num == 0)
				{
					this.msg = "CH1.3 Không thể chuyển\n";
					this.db.getConnection().rollback();
					return false;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "CH1 Không thể chuyển";
			return false;
		}
		finally
		{	
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	

	
	boolean checkMaTaiSan()
	{
		String query = "select count(*) as num from ERP_TAISANCODINH where isDaThanhLy != 1 and PK_SEQ = " + this.taiSanId;
		try{
		ResultSet rs = this.db.get(query);
		
		if (rs != null)
			if (rs.next())
				if (rs.getInt("num") == 0)
				{
					this.msg = "CNNGCCDC1.2 Công cụ dụng cụ đã thanh lý";
					this.db.getConnection().rollback();
					return false;
				}
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean chuyenMASCLON() {
		//Lấy giá tồn kì gần nhất áp cho sản phẩm
		try {
			this.db.getConnection().setAutoCommit(false);
			String query = null;

			if(this.ngayChuyen.length()<=0)
			{
				this.msg = "Vui lòng cập nhật ngày kết chuyển \n";
				this.db.getConnection().rollback();
				return false;
			}
			//Update trạng thái xuất dùng
			query = "update ERP_MASCLON set TRANGTHAI = 3, ngayChuyen = '" + this.ngayChuyen + "'\n" +
					"where TRANGTHAI = 1 and PK_SEQ = " + this.id + 
					" and congTy_FK = " + this.congTyId;
			
			System.out.println("chuyen msc: \n" + query + "\n----------------------------------");
			int num = this.db.updateReturnInt(query);
			if (num == 0)
			{
				this.msg = "CH1.1 Không thể chuyển\n";
				this.db.getConnection().rollback();
				return false;
			}
			
			//Mua mới: cập nhật nguyên giá tài sản
			if (this.loaiId == 1||this.loaiId == 2)
			{
				query = "update ERP_TAISANCODINH \n" +
						"set DONGIA = " + this.giaTri + ", trangThai = 1, thanhTien = " + this.giaTri + ",soluong='1', ngayghitang='"+this.ngayChuyen +"' \n" +
						"where PK_SEQ = " + this.taiSanId + " \n" +
						"and trangThai = 0  and (isDaThanhLy is null or isDaThanhLy = 0)\n";
				System.out.println("cau cap nhat" +query);
				num = this.db.updateReturnInt(query);
				if (num == 0)
				{
					this.msg = "CH1.2 Không thể chuyển\n";
					this.db.getConnection().rollback();
					return false;
				}
			}
			else{ //Sửa chữa: cập nhật nguyên giá vào điều chỉnh
			
		
				query = 
					"insert into ERP_TAISANCODINH_DIEUCHINH (GIATRI, TSCD_FK, LOAICHUNGTU\n" +
					", SOCHUNGTU, BANGTHAMCHIEU, NGAYDIEUCHINH)\n" +
					"values(" + this.giaTri + ", " + this.taiSanId + ", N'Mã SCLớn/ XDCB TSCĐ'\n" +
					", " +this.id + ", 'ERP_MASCLON', '" + this.ngayChuyen + "')\n";
				System.out.println("dieu chinh tai san:\n" + query + "\n----------------------------------------");
				num = this.db.updateReturnInt(query);
				if (num == 0)
				{
					this.msg = "CH1.3 Không thể chuyển\n";
					this.db.getConnection().rollback();
					return false;
				}
				
				int result = checkTSCD();
				//Chạy lại phân bổ
				System.out.println("result : " + result);
				if (result == 1)
				{
					this.msg = "CH1.3.1 Không thể chuyển, tháng hiện tại đã khóa sổ\n";
					this.db.getConnection().rollback();
					return false;
				}
				else if (result == 2 )
				{
					boolean kq = capNhatPhanBo();
					if (kq == false)
					{
						this.msg = "Ch1.0.3 Lỗi xóa khấu hao\n" + this.msg;
						this.db.getConnection().rollback();
						return false;
					}
				}
				else if (result == -1)
				{
					this.msg = "Ch1.0.4 Lỗi xóa khấu hao\n" + this.msg;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			//Chạy kế toán
			if (!chayKeToan())
			{
				this.msg = "Ch1.0.5 Lỗi chạy kế toán\n" + this.msg;
				this.db.getConnection().rollback();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "CH1 Không thể chuyển";
			return false;
		}
		finally
		{	
			try {
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	public static void main ( String args [  ]  )   {
		  
  		try{
  			Erp_MaSCLon msl = new Erp_MaSCLon();
  			msl.db.getConnection().setAutoCommit(false);
  			String QUERY="";
  			QUERY="	select tkNo.SOHIEUTAIKHOAN as taiKhoanNo, tkCo.SOHIEUTAIKHOAN as taiKhoanCo,msc.pk_seq as masclonId,msc.NGAYCHUYEN,msc.TAISAN_FK  \n" +
			", isNull((select sum(giaTri) from ERP_MASCLON_DIEUCHINH where MASCLON_FK = msc.PK_SEQ group by MASCLON_FK), 0)as giaTri\n" + 
			"from ERP_MASCLON msc\n" + 
			"inner join ERP_TAIKHOANKT tkCo on tkCo.PK_SEQ = msc.TAIKHOAN_FK\n" + 
			"inner join ERP_TAISANCODINH ts on ts.pk_seq = msc.TAISAN_FK\n" + 
			"inner join Erp_LOAITAISAN lts on lts.pk_seq = ts.LOAITAISAN_FK\n" + 
			"inner join ERP_TAIKHOANKT tkNo on tkNo.PK_SEQ = lts.TAIKHOAN_FK\n" + 
			"where msc.trangthai =3 ";
  			ResultSet rsChayLai=msl.db.get(QUERY);
  			while(rsChayLai.next())
  			{
  				msl.setId(rsChayLai.getString("masclonId"));
  				msl.setNgayChuyen(rsChayLai.getString("NGAYCHUYEN"));
  				msl.setTaiSanId(rsChayLai.getString("TAISAN_FK"));
  				boolean check = msl.chayKeToan();
  	  			if(check == false)
  	  				msl.db.getConnection().rollback();
  	  			System.out.println("check:" + check + "\n-------------------------------------");
  	  	
  			}rsChayLai.close();
  			msl.db.getConnection().commit();
			msl.db.getConnection().setAutoCommit(true);
  			  		
  		
  		
  		
  			
  		}catch(Exception er){
  			er.printStackTrace();
  		}
  		
  		 
    }
	private boolean chayKeToan() {
		
		Utility util = new Utility();
		this.msg=util.HuyUpdate_TaiKhoan(db, this.id, "Chuyển mã SC lớn/XDCB TSCD");
		if(this.msg.length()>0)
		{
			try {
				db.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		String query = 
			"select tkNo.SOHIEUTAIKHOAN as taiKhoanNo, tkCo.SOHIEUTAIKHOAN as taiKhoanCo,msc.pk_seq as masclonId \n" +
			", isNull((select sum(giaTri) from ERP_MASCLON_DIEUCHINH where MASCLON_FK = msc.PK_SEQ group by MASCLON_FK), 0)as giaTri\n" + 
			"from ERP_MASCLON msc\n" + 
			"inner join ERP_TAIKHOANKT tkCo on tkCo.PK_SEQ = msc.TAIKHOAN_FK\n" + 
			"inner join ERP_TAISANCODINH ts on ts.pk_seq = msc.TAISAN_FK\n" + 
			"inner join Erp_LOAITAISAN lts on lts.pk_seq = ts.LOAITAISAN_FK\n" + 
			"inner join ERP_TAIKHOANKT tkNo on tkNo.PK_SEQ = lts.TAIKHOAN_FK\n" + 
			"where msc.PK_SEQ = " + this.id ;
		System.out.println("cau lenh lay tai khoan ket chuyen:\n" + query + "\n-------------------------------------");
		ResultSet rs = null;
		
		try {
			rs = this.db.get(query);
			
			if (rs != null)
			{
				
				while (rs.next())
				{
					String taiKhoanNo = rs.getString("taiKhoanNo");
					String taiKhoanCo = rs.getString("taiKhoanCo");
					String ngayChungTu = this.ngayChuyen;
					String ngayGhiNhan = this.ngayChuyen;
					String thang = this.ngayChuyen.split("-")[1];
					String nam = this.ngayChuyen.split("-")[0];
					String loaiChungTu = "Chuyển mã SC lớn/XDCB TSCD";
					String soChungTu = this.id;
					
					String co;
					String no = co = Double.toString(rs.getDouble("giaTri"));//
					
					String doiTuongNo = "Tài sản";
					String maDoiTuongNo = this.taiSanId;
					String loaiDoiTuongNo = "0";
					
					String doiTuongCo = "Mã sửa chữa lớn";
					String maDoiTuongCo =rs.getString("masclonId");
					String loaiDoiTuongCo = "0";
					
					String donGia = "";
					String soLuong = "";
					
					String tienTeId = "100000";
					this.DvkdId="100001";
					this.nppId="1";
					String tiGia = "1";
					util.setNppId("1");
					this.msg = util.Update_TaiKhoan_TheoSoHieu_DoiTuong_NoCo( this.db, thang, nam, ngayChungTu, ngayGhiNhan
					, loaiChungTu, soChungTu, taiKhoanNo, taiKhoanCo, ""
					, no, co
					, doiTuongNo, maDoiTuongNo, loaiDoiTuongNo
					, doiTuongCo, maDoiTuongCo, loaiDoiTuongCo
					, soLuong, donGia, tienTeId, donGia, tiGia, no, no, "1");
					
					if(this.msg.trim().length() > 0)
					{
						this.msg = "CKT1.1 Không thể chốt chuyển\n" + this.msg;
						System.out.println(this.msg);
						
						return false;
					}
				}
			}
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	private boolean capNhatPhanBo()
	{
		List<String> namList = new ArrayList<String>();
		List<String> thangList = new ArrayList<String>();
		List<String> thangThuList = new ArrayList<String>();
		
		if (xoaKhauHao(namList, thangList, thangThuList) == false)
			return false;
		
//		//Chạy lại kế hoạch phân bổ
//		if (chayKeHoachPhanBoTSCD() == false)
//		{
//			return false;
//		}
//		
//
//		if (themPhanBo(namList, thangList, thangThuList) == false)
//			return false;
		
		return true;
	}

	private boolean chayKeHoachPhanBoTSCD()
	{
		String[] param = new String[1];
		param[0] = this.taiSanId;
		try{
			String result = this.db.execProceduce2("KeHoachPhanBo_TSCD", param);
			if (result.trim().length() > 0)
			{
				this.setMsg("CKHPBTSCD1.2 Không thể tạo mới tài sản cố định \n" + result);
				this.db.getConnection().rollback();
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMsg("CKHPBTSCD1.3 Không thể tạo mới tài sản cố định \n");
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
	

	private boolean xoaKhauHao(List<String> namList, List<String> thangList, List<String> thangThuList) {
		boolean result = true;
		Khauhaotaisan khauHao = new Khauhaotaisan();
		String namXuatDung = this.ngayChuyen.split("-")[0];
		String thangXuatDung = this.ngayChuyen.split("-")[1];
		
		//Xóa những tháng phân bổ kể từ tháng xuất dùng
		String query = 
			"select THANG, NAM, THANGTHU, PK_SEQ \n" +
			"from ERP_KHAUHAOTAISAN \n" +
			"where TRANGTHAI = 1 and TAISAN_FK = " + this.taiSanId +
			" and (nam > " + namXuatDung + " or (nam = " + namXuatDung + " and thang >= " + thangXuatDung + ")) ";
		
		ResultSet rs = null;
		try {
			rs = this.db.get(query);
			
			if (rs != null)
			{
				while (rs.next())
				{
					String nam = rs.getString("nam");
					String thang = rs.getString("thang");
					String lanthu = rs.getString("thangThu");
					String soChungTu = nam + (rs.getInt("thang") > 9 ? thang : ("0" + thang));
					
					namList.add(nam);
					thangList.add(thang);
					thangThuList.add(lanthu);
					
					String str = nam + ";" + thang + ";" + this.taiSanId + ";" + lanthu + ";" + soChungTu;
					boolean kq = khauHao.Cancel(str);
					if (kq == false)
					{
						this.msg = "XKH1.0 Lỗi xóa khấu hao\n" + khauHao.getMsg();
						this.db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			}
			khauHao.DBClose();
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	private boolean themPhanBo(List<String> namList, List<String> thangList, List<String> thangThuList) 
	{
		try {
				for (int index = 0; index < namList.size(); index++)
				{
					String nam = namList.get(index);
					String thang = thangList.get(index);
					String lanthu = thangThuList.get(index);
					String khauHao = "";
					
					String query = "select khauHaoDuKien from ERP_TAISANCODINH_CHITIET where TaiSan_FK = " + this.taiSanId + " and thang = " + lanthu + "";
					
					ResultSet rs = this.db.get(query);
					
					if (rs != null)
					{
						if (rs.next())
							khauHao = rs.getString("khauHaoDuKien");
						rs.close();
					}
					else
						return false;
					
					if (savePhanBoTSCD(thang, nam, lanthu, khauHao) == false)
						return false;
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean savePhanBo(String thang, String nam, String thangThu, String khauHao)
	{	
		String query;
		try
		{
			db.getConnection().setAutoCommit(false);
			
			Utility util = new Utility();
			if(this.taiSanId != null)
			{
				query = "INSERT INTO ERP_KHAUHAOTAISAN(THANG, NAM, CCDC_FK, KHAUHAO, THANGTHU, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, TRANGTHAI) " +
						"VALUES(" + thang + "," + nam + "," + this.taiSanId + ", " + khauHao + ", " +
							"'" + thangThu + "','" + this.nguoiSua + "','" + this.getDateTime() + "','" + this.nguoiSua + "','" + this.getDateTime() + "', '1' )";
		
				System.out.println("1.Khau hao tai san: " + query);
				if(this.db.update(query))
				{
					query = "UPDATE ERP_TAISANCODINH_CHITIET " +
							"SET KHAUHAOTHUCTE = " + khauHao + "" +
							", KHAUHAOLUYKETHUCTE = KHAUHAOLUYKEDUKIEN" +
							", GIATRICONLAITHUCTE = GIATRICONLAIDUKIEN " +
							"WHERE CCDC_FK = " + this.taiSanId + " AND THANG = '" + thangThu + "' ";
		
					System.out.println(query);
					if(!this.db.update(query))
					{							
						this.msg="Không thể cập nhật ERP_TAISANCODINH_CHITIET";
						this.db.getConnection().rollback();
						return false;
				
					}						
				}
				

				//Chạy kế toán
				query = "SELECT CCDC.PK_SEQ as ccdcId, TK1.SOHIEUTAIKHOAN, CCDC.MA as MACCDC\n" +
						", SUM(ISNULL(CCDC_CD.PHANTRAM, 0) * KH.KHAUHAO / 100 ) AS TOTALKHAUHAO, \n" +  
						"TK2.SOHIEUTAIKHOAN AS SOHIEUTAIKHOANCO \n" +   
						"FROM ERP_CONGCUDUNGCU_CONGDUNG CCDC_CD \n" +
						"INNER JOIN ERP_CONGDUNGCCDC CD ON CCDC_CD.CONGDUNG_FK = CD.PK_SEQ \n" + 
						"INNER JOIN ERP_TAIKHOANKT TK1 ON CD.TAIKHOAN_FK = TK1.PK_SEQ \n" + 
						"INNER JOIN ERP_CONGCUDUNGCU CCDC ON CCDC_CD.CCDC_FK = CCDC.PK_SEQ " + 
						"INNER JOIN Erp_LOAICCDC LCCDC ON LCCDC.PK_SEQ = CCDC.LOAICCDC_FK \n" +
						"INNER JOIN ERP_TAIKHOANKT TK2 ON LCCDC.TAIKHOAN_FK = TK2.PK_SEQ  \n" +
						"INNER JOIN ERP_KHAUHAOTAISAN KH ON CCDC.PK_SEQ = KH.CCDC_FK  and kh.trangThai != 2 \n" +
						"WHERE CCDC.PK_SEQ = " + this.taiSanId + " AND KH.THANG = '" + thang + "' AND KH.NAM = '" + nam + "' \n" +  
						"GROUP BY TK1.SOHIEUTAIKHOAN, CCDC.MA, CCDC.LOAICCDC_FK, CD.TAIKHOAN_FK, TK2.SOHIEUTAIKHOAN,CCDC.PK_SEQ \n";					
				
				
				System.out.println("____LAY TAI KHOAN KHAU HAO: \n" + query  + "\n================================");
				ResultSet rsTk = this.db.get(query);
	
				if (rsTk != null)
				{
					while(rsTk.next())
					{
						double totalKhauHao = rsTk.getDouble("totalKhauHao");
						
						String taikhoanCO_SoHieu = rsTk.getString("sohieutaikhoanCO");
						String taikhoanNO_SoHieu = rsTk.getString("sohieutaikhoan");
						
						String ngayghinhan = nam + "-" + ( thang.trim().length() < 2 ? "0" + thang : thang ) + "-30";
						ngayghinhan = getLastDayOfMonth(ngayghinhan);
					
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return false;
						}
						
						String tiente_fk = "100000";
						
						String soChungTu = nam + "" + (thang.trim().length() < 2 ? ("0" + thang) : thang);
						this.msg = util.Update_TaiKhoan_TheoSoHieu( db, thang, nam, ngayghinhan, ngayghinhan, "Khấu hao công cụ dụng cụ", soChungTu, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
								Double.toString(totalKhauHao), Double.toString(totalKhauHao), "Công cụ dụng cụ", rsTk.getString("ccdcId"), "0", "", "", tiente_fk, "", "1", Double.toString(totalKhauHao), Double.toString(totalKhauHao), "" ,"1");
						if(this.msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return false;
						}

					}
					rsTk.close();
				}
				
				query =  "SELECT CCDC.TTCP_FK AS TTCPID, PB.TTCPNHAN_FK AS TTCPNHANID, PB.PHANTRAM, KHCCDC.KHAUHAO   \n" +
						 "FROM ERP_CONGCUDUNGCU CCDC \n" +
						 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = CCDC.TTCP_FK  \n" +
						 "INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TTCPCHO_FK = TTCP.PK_SEQ AND CCDC.PK_SEQ = PB.CCDC_FK \n" +
						 "INNER JOIN ERP_KHAUHAOTAISAN KHCCDC ON KHCCDC.CCDC_FK = CCDC.PK_SEQ  " +
						 "WHERE CCDC.PK_SEQ IN (" + this.taiSanId + ") AND KHCCDC.THANG = " + thang + " AND KHCCDC.NAM = " + nam + "  \n" +
						 "union ALL \n" +
						 "SELECT CCDC.TTCP_FK AS TTCPID, TTCP.PK_SEQ AS TTCPNHANID, 100 as PHANTRAM, KHCCDC.KHAUHAO   \n" +
						 "FROM ERP_CONGCUDUNGCU CCDC \n" +
						 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = CCDC.TTCP_FK  \n" +
						 "INNER JOIN ERP_KHAUHAOTAISAN KHCCDC ON KHCCDC.CCDC_FK = CCDC.PK_SEQ  \n" +
						 "WHERE CCDC.PK_SEQ IN (" + this.taiSanId + ") AND KHCCDC.THANG = " + thang + " AND KHCCDC.NAM = " + nam + " \n" ;
				
				System.out.println(query);
				
				ResultSet rs = this.db.get(query);
				
				if (rs != null)
				{
					while(rs.next()){

						query = "INSERT INTO ERP_TRUNGTAMCHIPHI_THUCCHI(TTCP_FK, THANG, NAM, THUCCHI) VALUES" +
								"(" + rs.getString("TTCPNHANID") + ", '" + thang + "','" + nam + "', CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100 )";
						
						System.out.println(query);
						
						if(!this.db.update(query)){
							query = "UPDATE ERP_TRUNGTAMCHIPHI_THUCCHI SET THUCCHI = THUCCHI + CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100  " +
									"WHERE TTCP_FK = " + rs.getString("TTCPNHANID") + " AND NAM = '" + nam + "' AND THANG = '" + thang + "'";
							
							System.out.println(query);
							this.db.update(query);
						}
						
					}
					rs.close();
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Không thể thực hiện khấu hao. " + e.getMessage();
			return false;
		}
	}

	public boolean savePhanBoTSCD(String thang, String nam, String thangThu, String khauHao)
	{	
		String query;
		try
		{
			Utility util = new Utility();
			
			query = "SELECT COUNT(*) AS NUM " +
					"FROM ERP_KHAUHAOTAISAN WHERE THANG = "  + thang + " " +
					"AND NAM = " + nam + " AND TAISAN_FK = " + this.taiSanId + " AND TRANGTHAI = 1 ";
			System.out.println(query);
			
			ResultSet rs = this.db.get(query);
			rs.next();
			if(rs.getString("NUM").equals("0")){
			
				query = "INSERT INTO ERP_KHAUHAOTAISAN(THANG, NAM, TAISAN_FK, KHAUHAO, THANGTHU, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, TRANGTHAI) " +
						"VALUES(" + thang + "," + nam + "," + this.taiSanId + ", " + khauHao + ", " +
						"'" + thangThu + "','" + this.nguoiSua + "','" + this.getDateTime() + "','" + this.nguoiSua + "','" + this.getDateTime() + "', '1' )";
								
				if(this.db.update(query))
				{
					query = "UPDATE ERP_TAISANCODINH_CHITIET SET KHAUHAOTHUCTE = '" + khauHao + "', KHAUHAOLUYKETHUCTE = KHAUHAOLUYKEDUKIEN, " +
							"GIATRICONLAITHUCTE = GIATRICONLAIDUKIEN WHERE TAISAN_FK = '" + this.taiSanId + "' AND THANG = '" + thangThu + "' ";
		
					System.out.println(query);
					if(!this.db.update(query))
					{							
						this.msg="Không thể cập nhật ERP_TAISANCODINH_CHITIET";
						this.db.getConnection().rollback();
						return false;
					}						
				}
				
			}
			else
			{
				this.msg="Tháng được chọn đã khấu hao tài sản. Vui lòng chọn tháng khác";
				this.db.getConnection().rollback();
				return false;						
			}

			String ngayghinhan = nam + "-" + ( thang.trim().length() < 2 ? "0" + thang : thang );
			ngayghinhan = getLastDayOfMonth(ngayghinhan);
			
			query = "SELECT KH.PK_SEQ SCT,TK1.PK_SEQ sohieutaikhoan , TSCD.PK_SEQ as MATAISAN, SUM(ISNULL(TS_CD.PHANTRAM, 0) * KH.KHAUHAO / 100 ) AS TOTALKHAUHAO, \n" +  
					"TK2.PK_SEQ AS sohieutaikhoanCO, ( SELECT DAY( DATEADD(DAY,-(DAY('"+ngayghinhan+"-01')),DATEADD(MONTH,1,'"+ngayghinhan+"-01')))) NGAYGHINHAN  \n" +   
					"FROM ERP_TAISANCODINH_CONGDUNG TS_CD \n" +
					"INNER JOIN ERP_CONGDUNG CD ON TS_CD.CONGDUNG_FK = CD.PK_SEQ \n" + 
					"INNER JOIN ERP_TAIKHOANKT TK1 ON CD.TAIKHOAN_FK = TK1.PK_SEQ \n" + 
					"INNER JOIN ERP_TAISANCODINH TSCD ON TS_CD.TAISAN_FK = TSCD.PK_SEQ \n" + 
					"INNER JOIN Erp_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n" +
					"INNER JOIN ERP_TAIKHOANKT TK2 ON ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = LTS.TAIKHOANKH_FK AND CONGTY_FK = TSCD.congty_fk ) =   TK2.PK_SEQ    \n" +
					"INNER JOIN ERP_KHAUHAOTAISAN KH ON TSCD.PK_SEQ = KH.TAISAN_FK  \n" +
					"WHERE TSCD.PK_SEQ IN  " + this.taiSanId + " AND KH.THANG = '" + thang + "' AND KH.NAM = '" + nam + "' \n" +  
					"GROUP BY KH.PK_SEQ, TK1.PK_SEQ, TSCD.PK_SEQ, TSCD.LOAITAISAN_FK, CD.TAIKHOAN_FK, TK2.PK_SEQ ";					
								
			System.out.println("____LAY TAI KHOAN KHAU HAO: \n" + query + "\n--------------------------------------");
			ResultSet rsTk = this.db.get(query);
		
			while(rsTk.next())
			{
				double totalKhauHao = rsTk.getDouble("totalKhauHao");
				
				String taikhoanCO_SoHieu = rsTk.getString("sohieutaikhoanCO");
				
				String sochungtu =  rsTk.getString("SCT");
				
				String taikhoanNO_SoHieu = rsTk.getString("sohieutaikhoan");	
				
				ngayghinhan = nam + "-" + ( thang.trim().length() < 2 ? "0" + thang : thang )+ "-"+rsTk.getString("NGAYGHINHAN");	
									
				if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
				{
					msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
					db.getConnection().rollback();
					return false;
				}
				
				String tiente_fk = "100000";
				
				this.msg = util.Update_TaiKhoan(db, thang, nam,ngayghinhan,ngayghinhan,	"Khấu hao tài sản", sochungtu ,taikhoanNO_SoHieu, taikhoanCO_SoHieu,"", Double.toString(totalKhauHao),
				Double.toString(totalKhauHao),	"Tài sản", rsTk.getString("MATAISAN"),  "" , "", "0", "", "", tiente_fk,"", "1", Double.toString(totalKhauHao),
				Double.toString(totalKhauHao),	"Khấu hao tài sản",this.congTyId);	
				
				if(this.msg.trim().length() > 0)
				{
					db.getConnection().rollback();
					return false;
				}
			}
			rsTk.close();
									
			query =  "SELECT TSCD.TTCP_FK AS TTCPID, PB.TTCPNHAN_FK AS TTCPNHANID, PB.PHANTRAM, KHTS.KHAUHAO   " +
					 "FROM ERP_TAISANCODINH TSCD " +
					 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK  " +
					 "INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TTCPCHO_FK = TTCP.PK_SEQ AND TSCD.PK_SEQ = PB.TAISAN_FK " +
					 "INNER JOIN ERP_KHAUHAOTAISAN KHTS ON KHTS.TAISAN_FK = TSCD.PK_SEQ  " +
					 "WHERE TSCD.PK_SEQ IN " + this.taiSanId + " AND KHTS.THANG = " + thang + " AND KHTS.NAM = " + nam + "  " +
					 "UNION ALL " +
					 "SELECT TSCD.TTCP_FK AS TTCPID, TTCP.PK_SEQ AS TTCPNHANID, 100 as PHANTRAM, KHTS.KHAUHAO   " +
					 "FROM ERP_TAISANCODINH TSCD " +
					 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK  " +
					 "INNER JOIN ERP_KHAUHAOTAISAN KHTS ON KHTS.TAISAN_FK = TSCD.PK_SEQ  " +
					 "WHERE TSCD.PK_SEQ IN " + this.taiSanId + " AND KHTS.THANG = " + thang + " AND KHTS.NAM = " + nam + " " ;
			
			System.out.println(query);
			
			rs = this.db.get(query);
			
			while(rs.next()){

				query = "INSERT INTO ERP_TRUNGTAMCHIPHI_THUCCHI(TTCP_FK, THANG, NAM, THUCCHI) VALUES" +
						"(" + rs.getString("TTCPNHANID") + ", '" + thang + "','" + nam + "', CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100 )";
				
				System.out.println(query);
				
				if(!this.db.update(query)){
					query = "UPDATE ERP_TRUNGTAMCHIPHI_THUCCHI SET THUCCHI = THUCCHI + CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100  " +
							"WHERE TTCP_FK = " + rs.getString("TTCPNHANID") + " AND NAM = '" + nam + "' AND THANG = '" + thang + "'";
					
					System.out.println(query);
					this.db.update(query);
				}
			}
			rs.close();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Không thể thực hiện khấu hao. " + e.getMessage();
			return false;
		}
	}

	private int checkTSCD()
	{
		int result = 0;
		
		//Lấy tháng năm khóa sổ max
		int thangKSMax = new Integer(0);
		int namKSMax = new Integer(0);
		
		List<Integer> l = new ArrayList<Integer>();
		geso.traphaco.center.util.Utility.GetThangKhoaSoMax(this.db, l);
		
		thangKSMax = l.get(0);
		namKSMax = l.get(1);
		System.out.println("thangKSMaxcheckCCDC: " + thangKSMax);
		System.out.println("namKSMaxcheckCCDC: " + namKSMax);
		
		//Mã TSCD đã khấu hao?
		String query = 
			"select * from (select top 1 THANG, NAM \n" +
			"from ERP_KHAUHAOTAISAN \n" +
			"where TRANGTHAI = 1 and TAISAN_FK = " + this.taiSanId + "\n" +
			"order by nam, thang) a\n" +
			"union all\n" +
			"select " + this.ngayChuyen.split("-")[1] + " as thang, " + this.ngayChuyen.split("-")[0] + " as nam\n";
		System.out.println("kt ngay chuyen: \n" + query + "\n------------------------------------");
		ResultSet rs = null;
		
		try{
			rs = this.db.get(query);

			if (rs != null)
			{
				while (rs.next())
				{
					int namKH_Min = rs.getInt("nam");
					int thangKH_Min = rs.getInt("thang");
//					this.msg = "Tháng " + thangKH_Min + " năm " + namKH_Min;

					if (namKH_Min < namKSMax)
						result = 1;
					else if (namKH_Min == namKSMax && thangKSMax >= thangKH_Min)
						result = 1;
					else
					{
						result = 2;
						while (rs.next())
						{
							namKH_Min = rs.getInt("nam");
							thangKH_Min = rs.getInt("thang");
							this.msg += "\nTháng " + thangKH_Min + " năm " + namKH_Min;
						}
					}
					if (result == 1)
						return result;
				}
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		
		return result;
	}

	public void DbClose()
	{
		if (this.db != null)
			this.db.shutDown();
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getTaiKhoanId() {
		return taiKhoanId;
	}

	public void setTaiKhoanId(String taiKhoanId) {
		this.taiKhoanId = taiKhoanId;
	}

	public String getTaiSanId() {
		return taiSanId;
	}

	public void setTaiSanId(String taiSanId) {
		this.taiSanId = taiSanId;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}

	public String getNgaySua() {
		return ngaySua;
	}

	public void setNgaySua(String ngaySua) {
		this.ngaySua = ngaySua;
	}

	public String getNguoiTao() {
		return nguoiTao;
	}

	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}

	public String getNguoiSua() {
		return nguoiSua;
	}

	public void setNguoiSua(String nguoiSua) {
		this.nguoiSua = nguoiSua;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Erp_Item> getTaiKhoanList() {
		return taiKhoanList;
	}

	public void setTaiKhoanList(List<Erp_Item> taiKhoanList) {
		this.taiKhoanList = taiKhoanList;
	}

	public List<Erp_Item> getTaiSanCDList() {
		return taiSanCDList;
	}

	public void setTaiSanCDList(List<Erp_Item> taiSanCDList) {
		this.taiSanCDList = taiSanCDList;
	}

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTen() {
		return ten;
	}

	public void setGiaTri(Double giaTri) {
		this.giaTri = giaTri;
	}

	public Double getGiaTri() {
		return giaTri;
	}

	public void setLoaiId(Integer loaiId) {
		this.loaiId = loaiId;
	}

	public Integer getLoaiId() {
		return loaiId;
	}

	public void setLoaiList(List<Erp_Item> loaiList) {
		this.loaiList = loaiList;
	}

	public List<Erp_Item> getLoaiList() {
		return loaiList;
	}
	
	public static String getLastDayOfMonth(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate;
		Calendar c = null;
		try {
			convertedDate = dateFormat.parse(date);
			c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Date lastDayOfMonth = c.getTime();  
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		return sdf.format(lastDayOfMonth);
	}
	
	public static String InsertDieuChinhMSCL(Idbutils db, String id, String ngayDieuChinh, String giaTri, String soChungTu, String loaiChungTu, String bangThamChieu )
	{
		String result = KiemTra(db, id, ngayDieuChinh);
		if (result.trim().length() > 0)
			return result;
		
		result = InsertDieuChinh(db, id, ngayDieuChinh, giaTri, soChungTu, loaiChungTu, bangThamChieu);
		if (result.trim().length() > 0)
			return result;
		
		return "";
	}
	
	//Hàm kiểm tra xem mã sửa chữa lớn có thể thêm vô điều chỉnh được không
	public static String KiemTra(Idbutils db, String id, String ngayDieuChinh)
	{
		//Kiểm tra xem ngày điều chỉnh có nằm trong tháng đã khóa sổ chưa
		List<Integer> thangNam = new ArrayList<Integer>();
		Utility.GetThangKhoaSoMax(db, thangNam);
		
		String ngayKhoaSo = thangNam.get(1) + "-" + (thangNam.get(0) > 9 ? thangNam.get(0) : ("0" + thangNam.get(0))) + "-31";
		
		if (ngayKhoaSo.compareTo(ngayDieuChinh) == 1)
		{
			return "KT1.1 Ngày điều chỉnh mã sửa chữa lớn tài sản nằm trong tháng đã khóa sổ";
		}
		
		//Kiểm tra xem
		String query =
		"select msc.TRANGTHAI as trangThaiMSC, isNull(convert(varchar, ts.pk_seq), '') as taiSanId\n" +
		" , isNull(ts.isDaThanhLy, 0) as isDaThanhLy\n" +
		" , msc.MA + ' - ' + msc.TEN as tenMSC\n" +
		" , convert(nvarchar, ts.pk_seq) + ' - ' + ts.ma + ' - ' + ts.diengiai as tenTaiSan\n" +
		" from ERP_MASCLON msc\n" +
		" left join ERP_TAISANCODINH ts on ts.pk_seq = msc.TAISAN_FK\n" +
		" where msc.PK_SEQ = " + id;
		
		ResultSet rs = null;
		
		try {
			rs = db.get(query);
			
			if (rs != null)
			{
				if (rs.next())
				{
					String trangThaiMSC = rs.getString("trangThaiMSC");
					String tenMSC = rs.getString("tenMSC");
					
					if (trangThaiMSC.trim().equals("0"))
						return "KT1.2 Mã sửa chữa lớn " + tenMSC + " chưa chốt";
					else if (trangThaiMSC.trim().equals("3"))
						return "KT1.3 Mã sửa chữa lớn " + tenMSC + " đã chuyển";
					else if (trangThaiMSC.trim().equals("2"))
						return "KT1.4 Mã sửa chữa lớn " + tenMSC + " đã xóa";
					
					String taiSanId = rs.getString("taiSanId");
					
					if (taiSanId.trim().length() == 0)
						return "KT1.5 Tài sản cố định không tồn tại";
					
					String tenTaiSan = rs.getString("tenTaiSan");
					String isDaThanhLy = rs.getString("isDaThanhLy");
					
					if (isDaThanhLy.trim().equals("1") == true)
						return "KT1.6 Tài sản cố định " + tenTaiSan + " đã thanh lý";
				}
				else
				{
					return "KT1.7 Mã sửa chữa lớn không tồn tại";
				}
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "KT1.8 Lỗi";
		}
		
		return "";
	}
	
	public static String InsertDieuChinh(Idbutils db, String id, String ngayDieuChinh, String giaTri, String soChungTu, String loaiChungTu, String bangThamChieu)
	{
		//Cập nhật mã sc lớn
		String query =
			"insert into ERP_MASCLON_DIEUCHINH\n" +
			"(MASCLON_FK, GIATRI, SOCHUNGTU, LOAICHUNGTU\n" +
			", BANGTHAMCHIEU, NGAYDIEUCHINH)\n" +
			"values(" + id + ", " + giaTri + ", " + soChungTu + ", N'" + loaiChungTu + "'\n" +
			", '" + bangThamChieu + "', '" + ngayDieuChinh + "')";
		
		System.out.println("them moi dieu chinh:\n" + query + "\n------------------------------------------");
		if(!db.update(query))
    	{
    		return "ISDC1.2 Không thể cập nhật mã sửa chữa lớn tài sản";
    	}
		return "";
	}

	public List<Erp_Item> getDvkdList() {
		return dvkdList;
	}

	public void setDvkdList(List<Erp_Item> dvkdList) {
		this.dvkdList = dvkdList;
	}
	
	public String getDvkdId() {
		return DvkdId;
	}

	public void setDvkdId(String dvkdId) {
		DvkdId = dvkdId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getMasclonId() {
		return masclonId;
	}

	public void setMasclonId(String masclonId) {
		this.masclonId = masclonId;
	}

}